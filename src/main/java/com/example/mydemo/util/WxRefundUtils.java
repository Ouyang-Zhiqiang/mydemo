package com.example.mydemo.util;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;


import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class WxRefundUtils {

    private static RequestConfig requestConfig;// 请求器的配置
    private static CloseableHttpClient httpClient;// HTTP请求器
    private int socketTimeout = 10000;// 连接超时时间，默认10秒
    private int connectTimeout = 30000;// 传输超时时间，默认30秒
    private String APP_ID = "wx1b98935219078af2";
    private String MCH_ID = "1493407572";
    private String MCH_KEY="facebodyyanshenyundonglifestyle8";
    private String REFUND_PATH="https://api.mch.weixin.qq.com/secapi/pay/refund";//微信退款api
    //线下
    //private String SSLCERT_PATH = "C:\\Users\\Aigo\\Desktop\\WXCertUtil\\1563272841_20191204_cert\\apiclient_cert.p12";
    //线上
    private String SSLCERT_PATH = "C:\\lianghaipro\\WXCertUtil\\1563272841_20191204_cert\\apiclient_cert.p12";

    private String SSLCERT_PASSWORD = "1563272841";
    private String TRADETYPE = "JSAPI";//交易类型
    private String PAY_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";

    /**
     * 微信退款
     * @Param refundId 退款单号
     * @Param totalFee 退款订单总金额
     * @Param refundFee 本次退款金额
     * @Param notifyURL 退款成功回调
     * */
    public Object wxRefund(String refundId, BigDecimal totalFee, BigDecimal refundFee, String notifyURL) {
        // 数据封装
        Map<String, String> data = new HashMap<String, String>();
        // 退款核心代码
        try {
            // 按原支付路径返回
            String nonce_str = PayUtil.getRandomStringByLength(32);
            data.put("appid", APP_ID);
            data.put("mch_id", MCH_ID);
            data.put("nonce_str", nonce_str);
            data.put("sign_type", "MD5");
            data.put("out_trade_no", refundId);// 商户订单号
            // 金额转为分
            totalFee = totalFee.multiply(new BigDecimal(100));
            long total_fee = totalFee.longValue();
            refundFee = refundFee.multiply(new BigDecimal(100));
            long refund_fee = refundFee.longValue();
            data.put("out_refund_no", UUID.randomUUID().toString());// 商户退款单号
            data.put("total_fee", total_fee + "");// 支付金额，微信支付提交的金额是不能带小数点的，且是以分为单位,这边需要转成字符串类型，否则后面的签名会失败
            data.put("refund_fee", refund_fee + "");// 退款总金额,订单总金额,单位为分,只能为整数
            data.put("notify_url", notifyURL);// 退款成功后的回调地址
            String preStr = PayUtil.createLinkString(data); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串 //
            // MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mySign = PayUtil.sign(preStr, MCH_KEY, "utf-8").toUpperCase();
            data.put("sign", mySign); // 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xmlStr = postData(REFUND_PATH, PayUtil.GetMapToXML(data)); // 支付结果通知的xml格式数据
            System.out.println("付款结果:" + xmlStr);
            Map notifyMap = PayUtil.doXMLParse(xmlStr);
            Map<String, Object> response = new HashMap<String, Object>();// 返回给小程序端需要的参数
            if ("SUCCESS".equals(notifyMap.get("return_code"))) {
                if ("SUCCESS".equals(notifyMap.get("result_code"))) {
                    // 退款成功的操作
                    String prepay_id = (String) notifyMap.get("prepay_id");// 返回的预付单信息
                    response.put("nonceStr", nonce_str);
                    response.put("package", "prepay_id=" + prepay_id);
                    Long timeStamp = System.currentTimeMillis() / 1000; // 拼接签名需要的参数
                    response.put("timeStamp", timeStamp + "");// 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                    String stringSignTemp = "appId=" + APP_ID + "&nonceStr=" + nonce_str + "&package=prepay_id="
                            + prepay_id + "&signType=MD5&timeStamp=" + timeStamp; // 签名算法生成签名
                    String paySign = PayUtil.sign(stringSignTemp, MCH_KEY, "utf-8").toUpperCase();
                    response.put("package", "prepay_id=" + prepay_id);
                    response.put("timeStamp", String.valueOf(timeStamp));
                    response.put("paySign", paySign);
                    response.put("refundId", refundId);
                    return response;
                } else {
                    return notifyMap.get("return_msg").toString();
                }
            } else {
                return notifyMap.get("return_msg").toString();
            }

        } catch (Exception e) {
            return "退款过程发生未知异常";
        }
    }


    /**
     * 微信支付
     * @Param payContent 支付说明
     * @Param payId 订单单号
     * @Param totalPay 付款总金额
     * @Param userId 付款用户
     * @Param openId 付款用户的openId
     * @Param notifyURL 支付成功回调
     * @Param request
     * */
    @SuppressWarnings("rawtypes")
    public Object wxPay(String payContent, String payId, BigDecimal totalPay
            , String openId, String notifyURL, HttpServletRequest request) {
        try {
            // 生成的随机字符串
            String nonce_str = PayUtil.getRandomStringByLength(32);
            // 商品名称
            String body = payContent;
            // 获取客户端的ip地址
            String spbill_create_ip = PayUtil.getIpAddr(request);
            // 元转分
            totalPay = totalPay.multiply(new BigDecimal(100));
            long total_fee = totalPay.longValue();
            // 组装参数，用户生成统一下单接口的签名
            Map<String, String> packageParams = new HashMap<>();
            packageParams.put("appid", APP_ID);
            packageParams.put("mch_id", MCH_ID);
            packageParams.put("nonce_str", nonce_str);
            packageParams.put("body", body);
            packageParams.put("out_trade_no", payId);// 商户订单号,自己的订单ID
            packageParams.put("total_fee", total_fee + "");// 支付金额，这边需要转成字符串类型，否则后面的签名会失败
            packageParams.put("spbill_create_ip", spbill_create_ip);
            packageParams.put("notify_url", notifyURL);// 支付成功后的回调地址
            packageParams.put("trade_type", TRADETYPE);// 支付方式
            packageParams.put("openid", openId + "");// 用户的openID，自己获取

            String prestr = PayUtil.createLinkString(packageParams); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串

            // MD5运算生成签名，这里是第一次签名，用于调用统一下单接口
            String mysign = PayUtil.sign(prestr, MCH_KEY, "utf-8").toUpperCase();

            // 拼接统一下单接口使用的xml数据，要将上一步生成的签名一起拼接进去
            String xml = "<xml>" + "<appid>" + APP_ID + "</appid>" + "<body><![CDATA[" + body + "]]></body>"
                    + "<mch_id>" + MCH_ID + "</mch_id>" + "<nonce_str>" + nonce_str + "</nonce_str>" + "<notify_url>"
                    + notifyURL + "</notify_url>" + "<openid>" + openId + "</openid>" + "<out_trade_no>" + payId
                    + "</out_trade_no>" + "<spbill_create_ip>" + spbill_create_ip + "</spbill_create_ip>"
                    + "<total_fee>" + total_fee + "</total_fee>"// 支付的金额，单位：分
                    + "<trade_type>" + TRADETYPE + "</trade_type>" + "<sign>" + mysign + "</sign>" + "</xml>";

            // 调用统一下单接口，并接受返回的结果
            String result = PayUtil.httpRequest(PAY_URL, "POST", xml);
            System.out.println("请求下单结果：" + result);

            // 将解析结果存储在HashMap中
            Map map = PayUtil.doXMLParse(result);

            String return_code = (String) map.get("return_code");// 返回状态码
            String result_code = (String) map.get("result_code");// 返回状态码
            System.out.println("返回状态码:" + return_code + ";返回状态码:" + result_code);

            Map<String, Object> response = new HashMap<String, Object>();// 返回给小程序端需要的参数
            if ("SUCCESS".equals(return_code) && return_code.equals(result_code)) {
                String prepay_id = (String) map.get("prepay_id");// 返回的预付单信息
                response.put("nonceStr", nonce_str);
                response.put("package", "prepay_id=" + prepay_id);
                Long timeStamp = System.currentTimeMillis() / 1000;
                response.put("timeStamp", timeStamp + "");// 这边要将返回的时间戳转化成字符串，不然小程序端调用wx.requestPayment方法会报签名错误
                // 拼接签名需要的参数
                String stringSignTemp = "appId=" + APP_ID + "&nonceStr=" + nonce_str + "&package=prepay_id=" + prepay_id
                        + "&signType=MD5&timeStamp=" + timeStamp;
                // 再次签名，这个签名用于小程序端调用wx.requesetPayment方法
                String paySign = PayUtil.sign(stringSignTemp, MCH_KEY, "utf-8").toUpperCase();
                response.put("paySign", paySign);
                response.put("orderId", payId);
                response.put("return_code",return_code);
                System.out.println(response);
                return response;
            }
            return "下单过程出现异常";
        } catch (Exception e) {
            e.printStackTrace();
            return "下单过程出现异常";
        }
    }


    /**
     * 通过Https往API post xml数据
     *
     * @param url    API地址
     * @param xmlObj 要提交的XML数据对象
     * @return
     */
    private String postData(String url, String xmlObj) {
        // 加载证书
        try {
            initCert();
        } catch (Exception e) {
            e.printStackTrace();
        }
        String result = null;
        HttpPost httpPost = new HttpPost(url);
        // 得指明使用UTF-8编码，否则到API服务器XML的中文不能被成功识别
        StringEntity postEntity = new StringEntity(xmlObj, "UTF-8");
        httpPost.addHeader("Content-Type", "text/xml");
        httpPost.setEntity(postEntity);
        // 根据默认超时限制初始化requestConfig
        requestConfig = RequestConfig.custom().setSocketTimeout(socketTimeout).setConnectTimeout(connectTimeout).build();
        // 设置请求器的配置
        httpPost.setConfig(requestConfig);
        try {
            HttpResponse response = null;
            try {
                response = httpClient.execute(httpPost);
            } catch (IOException e) {
                e.printStackTrace();
            }
            HttpEntity entity = response.getEntity();
            try {
                result = EntityUtils.toString(entity, "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } finally {
            httpPost.abort();
        }
        return result;
    }

    /**
     * 加载证书
     *
     */
    private void initCert() throws Exception {
        // 证书密码，默认为商户ID
        // 商户证书的路径
        // 指定读取证书格式为PKCS12
        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        // 读取本机存放的PKCS12证书文件
        FileInputStream instream = new FileInputStream(new File(SSLCERT_PATH));
        try {
            // 指定PKCS12的密码(商户ID)
            keyStore.load(instream, SSLCERT_PASSWORD.toCharArray());
        } finally {
            instream.close();
        }
        SSLContext sslcontext = SSLContexts.custom().loadKeyMaterial(keyStore, SSLCERT_PASSWORD.toCharArray()).build();
        // 指定TLS版本
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(sslcontext, new String[] { "TLSv1" }, null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        // 设置httpclient的SSLSocketFactory
        httpClient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
    }
}
