package com.example.backstage.crs.controller;

import com.example.backstage.crs.service.WxPayService;
import com.example.mydemo.pojo.Order;
import com.example.mydemo.util.PayUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Controller
public class WxPayController {
    @Autowired
    private WxPayService wxPayService;

    @RequestMapping(value="/wxPayShop",method=RequestMethod.POST)
    public Object wxPay(Order order, HttpServletRequest request){
        return wxPayService.wxPay(order,request);
    }


    //这里是支付成功后的回调
    @RequestMapping(value = "/shopPay/return", method = RequestMethod.POST)
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("回调函数启动");
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream()));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";

        Map map = PayUtil.doXMLParse(notityXml);
        String returnCode = (String) map.get("return_code");
        System.out.println("回调函数启动:"+returnCode);
        //获取商户订单号
        String orderId = (String)map.get("out_trade_no");
        if ("SUCCESS".equals(returnCode) ) {
            /** 此处添加自己的业务逻辑代码start **/
            Map<String, Object> param = new HashMap<>();
            param.put("orderId",orderId);
            //状态为1，已支付
            param.put("status",1);
//            //这里写逻辑代码，比如更新订单状态为已支付
//            int result = orderMapper.updateStatus(param);
//            System.out.println(
//                    result==1?"支付成功":"支付失败"
//            );

            /** 此处添加自己的业务逻辑代码end **/
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

        } else {
            //TODO 暂时不清楚会发生这种情况
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }

        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

}
