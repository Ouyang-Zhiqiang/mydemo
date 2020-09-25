package com.example.mydemo.controller;

import ch.qos.logback.core.util.FileUtil;
import com.UpYun;
import com.alibaba.fastjson.JSONObject;
import com.example.mydemo.mapper.OrderMapper;
import com.example.mydemo.pojo.GhostUser;
import com.example.mydemo.pojo.Order;
import com.example.mydemo.pojo.OrderItem;
import com.example.mydemo.service.GhostUserService;
import com.example.mydemo.util.AESUtil;
import com.example.mydemo.util.AesCbcUtil;
import com.example.mydemo.util.HttpRequest;
import com.example.mydemo.util.PayUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upyun.RestManager;
import com.upyun.UpYunUtils;
import com.upyun.*;
import net.sf.json.JSONArray;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.mydemo.util.CheckUtils;


@RestController
public class MyTestController {

    @Resource
    private GhostUserService ghostUserService;
    @Resource
    private OrderMapper orderMapper;
    private String appId = "wx1b98935219078af2";
    private String appSecret = "d36e95d4186e7974ae8c04b0b81a341d";

    private static final String USER_NAME="facebody";
    private static final String PASSWORD="4JmA3ylGHgIDmN9w26sQhwSHNMstkSrd";
    private static final String FILEURL="onlinevideo";
    private static final String SERVER_NAME="facebody-res";
    private static final String DIR_ROOT="/";


    @RequestMapping(value = "/getCode",method = RequestMethod.POST)
    public Object getOpenId(@RequestParam("code")String code){
        //页面获取openId接口
        System.out.println("code"+code);
        String getOpenidUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";
        String param =
                "appid=" + appId+ "&secret=" +appSecret + "&code=" + code + "&grant_type=authorization_code";
        //向微信服务器发送get请求获取openIdStr
        String openIdStr = HttpRequest.sendGet(getOpenidUrl, param);
        //转成Json格式
        JSONObject json = JSONObject.parseObject(openIdStr);
        System.out.println(json);
        //获取openId
        String openId = json.getString("openid");

        String accessToken = json.getString("access_token");
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("openId",openId);
        resultMap.put("accessToken",accessToken);
        System.out.println("openid"+openId);
        return json;
    }

    @RequestMapping(value = "/user/login",method = RequestMethod.POST)
    public Object login(
            @RequestParam("jsCode")String jsCode,HttpServletRequest request){

        if("".equals(jsCode)) {
           return "传入的参数存在空字符";
        }
        //创建session
        HttpSession session = request.getSession();

        //校验sessionId
        //System.out.println("当前登录的sessionId:"+session.getId());

        //url拼接参数
        String grantType = "authorization_code";
        //向微信服务器请求参数
        String params = "appid=" + appId + "&secret=" + appSecret + "&js_code=" + jsCode + "&grant_type=" + grantType;
        //通过jsCode获取用户openId、sessionKey和UnionId
        String sr = HttpRequest.sendGet("https://api.weixin.qq.com/sns/jscode2session", params);
        //解析微信服务器返回的结果
        ObjectMapper mapper = new ObjectMapper();
        String sessionKey = "";
        String openId = "";
        String unionId = "";
        /**
         * 向微信请求获取用户数据
         * */
        Map<String, Object> param = new HashMap<>();
        try {
            JsonNode json = mapper.readTree(sr);
            param.put("sessionKey",json.path("session_key").asText(""));
            param.put("openId", json.path("openid").asText(""));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "解析登录结果出现异常";
        } catch (IOException e) {
            e.printStackTrace();
            return "解析登录结果出现异常";
        }

        return param;
    }


    //获取用户信息
    @RequestMapping(value = "/user/getUserInfo",method = RequestMethod.POST)
    public Object getUserInfo(  @RequestParam("encryptedData")String encryptedData,   @RequestParam("iv")String iv
            ,  @RequestParam("sessionKey") String sessionKey,  @RequestParam("openId")String openId
            ,HttpServletRequest request) {
        ObjectMapper mapper = new ObjectMapper();
        //通过encryptedData，iv，sessionKey获取用户信息
        if("".equals(sessionKey)) {
            return "登录失败，请重新登录";
        }
        String result = null;
        try {
            result = AesCbcUtil.decrypt(encryptedData, sessionKey, iv, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
            return  "解密失败";
        }
        if (null != result && result.length() > 0) {
            try {
                JsonNode resultNode = mapper.readTree(result);
                String nickName = resultNode.path("nickName").asText();//微信昵称
                String avatarUrl = resultNode.path("avatarUrl").asText();//微信头像
                byte gender = (byte)resultNode.path("gender").asInt();//性别
                String city = resultNode.path("city").asText();//城市
                String province = resultNode.path("province").asText();//省份
                String country = resultNode.path("country").asText();//国家

                String unionId = resultNode.path("unionId").asText();
                //这里写插入用户表的逻辑

                System.out.println(unionId);
                return unionId;

            } catch (JsonProcessingException e) {
                e.printStackTrace();
                return "解析用户信息出现异常1";
            } catch (IOException e) {
                e.printStackTrace();
                return "解析用户信息出现异常2";
            }
        }
        return "解析用户信息出现异常3";
    }




    /**
     * 下单
     * @Param price 购买商品的价格
     * @Param userId 用户id
     * @Param openId 用户的openId
     * @Param shopId 商品id
     * */
    @RequestMapping(value="/wxPay",method=RequestMethod.POST)
    public Object wxPay(Order order, HttpServletRequest request){
        return ghostUserService.wxPay(order,request);
    }

    //这里是支付成功后的回调
    @RequestMapping(value = "/pay/return", method = RequestMethod.POST)
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
            //这里写逻辑代码，比如更新订单状态为已支付
            int result = orderMapper.updateStatus(param);
            System.out.println(
                    result==1?"支付成功":"支付失败"
            );

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


    /**
     * 微信退款
     * @Param number 单号
     * @Param price 金额
     * */
    @RequestMapping(value="/refund",method=RequestMethod.POST)
    public Object wxRefund(@RequestParam("orderId")String orderId
            ,@RequestParam("price")BigDecimal price){
        return ghostUserService.wxRefund(orderId,price);
    }


    //这里是商家确认退款后的回调接口
    @RequestMapping(value = "/refund/return", method = RequestMethod.POST)
    public void wxRefundNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
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
        System.out.println(notityXml);
        String resXml = "";
        Map map = PayUtil.doXMLParse(notityXml);
        //System.out.println(map);
        String returnCode = (String) map.get("return_code");
        //解析req_info加密的回调结果
        //String passStr = AESUtil.decryptData((String)map.get("req_info"));
        //System.out.println("===="+passStr);
        //拿到解密信息
        //Map passMap = PayUtil.doXMLParse(passStr);
        //退款结果标识
        //String refund_status = (String)passMap.get("refund_status");
        //获取商户订单号
        String orderId = (String)map.get("out_trade_no");
        if ("SUCCESS".equals(returnCode) ) {
            /** 此处添加自己的业务逻辑代码start **/
//
//            //这里写逻辑代码，比如退款成功就更新订单状态
//            Map<String, Object> param = new HashMap<>();
//            param.put("orderId",orderId);
//            //状态为3，已退款
//            param.put("status",3);
//            //这里写逻辑代码，比如更新订单状态为已支付
//            int result = orderMapper.updateStatus(param);
//            System.out.println(
//                    result==1?"退款成功":"退款失败"
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



    @RequestMapping("/insertuser")
    public int inUser(){
        GhostUser ghostUser = new GhostUser();
        ghostUser.setUuid("12345678910");
        ghostUser.setName("hahah");
        ghostUser.setPhone("17862796388");
        ghostUser.setDateline(new Date());
        ghostUser.setDeleteflag(new BigDecimal(0));
        return ghostUserService.insert(ghostUser);
    }

    private static  final Logger logger = LoggerFactory.getLogger(MyTestController.class);


    // 项目根路径下的目录  -- SpringBoot static 目录相当于是根路径下（SpringBoot 默认）
    public final static String UPLOAD_PATH_PREFIX = "static/uploadFile/";

    @PostMapping("/upload")
    @CrossOrigin(origins = "http://localhost:9527/")
    public String upload(@RequestParam(value = "upload_file",required = false) MultipartFile uploadFile, HttpServletRequest request) {

        if (uploadFile.isEmpty()) {
            //返回选择文件提示
            return "请选择上传文件";

        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd/");
        //构建文件上传所要保存的"文件夹路径"--这里是相对路径，保存到项目根路径的文件夹下
        String realPath = new String("src/main/resources/" + UPLOAD_PATH_PREFIX);
        logger.info("-----------上传文件保存的路径【" + realPath + "】-----------");
        String format = sdf.format(new Date());
        //存放上传文件的文件夹
        File file = new File(realPath + format);
        logger.info("-----------存放上传文件的文件夹【" + file + "】-----------");
        logger.info("-----------输出文件夹绝对路径 -- 这里的绝对路径是相当于当前项目的路径而不是“容器”路径【" + file.getAbsolutePath() + "】-----------");
        if (!file.isDirectory()) {
            //递归生成文件夹
            file.mkdirs();
        }
        //获取原始的名字  original:最初的，起始的  方法是得到原来的文件名在客户机的文件系统名称
        String oldName = uploadFile.getOriginalFilename();
        logger.info("-----------文件原始的名字【" + oldName + "】-----------");
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        logger.info("-----------文件要保存后的新名字【" + newName + "】-----------");
        try {
            //构建真实的文件路径
            File newFile = new File(file.getAbsolutePath() + File.separator + newName);
            //转存文件到指定路径，如果文件名重复的话，将会覆盖掉之前的文件,这里是把文件上传到 “绝对路径”
            uploadFile.transferTo(newFile);
            String filePath = request.getScheme() + "://" + request.getServerName() +":"+8089+ "/uploadFile/" + format + newName;
//            String filePaths = filePath.replaceFirst("https","http");
            logger.info("-----------【" + filePath + "】-----------");
            return filePath;
        } catch (Exception e) {
            e.printStackTrace();

        }
        return "上传失败!";
    }

    //video上传
    @PostMapping("/uploadvideo")
    @CrossOrigin(origins = "http://localhost:9527/")
    public String uploadvideo(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request) {
//        RestManager restManager = new RestManager(FILEURL,USER_NAME,PASSWORD);
//        String strs = "Hello RestManager";
//        Map<String, String> params = new HashMap<String, String>();
//        params.put(RestManager.PARAMS.CONTENT_MD5.getValue(), UpYunUtils.md5(file, 1024));
//        Response result = restManager.writeFile("/path/to/file", strs, params);
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            try {
                InputStream ins = null;
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);
                ins.close();
            }catch (Exception e){
                logger.info("出错了");
            }
        }
        String httphead="https://res.facebodyfitness.com/onlinevideo/video/";
        UpYun upYun = new UpYun(SERVER_NAME,USER_NAME,PASSWORD);
        upYun.setTimeout(60);
        upYun.setApiDomain(UpYun.ED_AUTO);
        Map<String, String> params = new HashMap<String, String>();
        try {
            boolean result = upYun.writeFile("/onlinevideo/video/"+newName,toFile,true,params);
            String urlpath=httphead+newName;
            logger.info("-----------【" + urlpath + "】-----------");
            return urlpath+","+oldName;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return "500";
    }

    //上传音乐包
    @PostMapping("/uploadmusic")
    @CrossOrigin(origins = "http://localhost:9527/")
    public String uploadmusic(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request) {

        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            try {
                InputStream ins = null;
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                inputStreamToFile(ins, toFile);
                ins.close();
            }catch (Exception e){
                logger.info("出错了");
            }
        }
        String httphead="https://res.facebodyfitness.com/onlinevideo/music/";
        UpYun upYun = new UpYun(SERVER_NAME,USER_NAME,PASSWORD);
        upYun.setTimeout(60);
        upYun.setApiDomain(UpYun.ED_AUTO);
        Map<String, String> params = new HashMap<String, String>();
        try {
            boolean result = upYun.writeFile("/onlinevideo/music/"+newName,toFile,true,params);
            String urlpath=httphead+newName;
            logger.info("-----------【" + urlpath + "】-----------");
            return urlpath+','+oldName;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return "500";
    }


    //img上传
    @PostMapping("/uploadimg")
    @CrossOrigin(origins = "http://localhost:9527/")
    public String uploadimg(@RequestParam(value = "upload_file",required = false) MultipartFile file, HttpServletRequest request) {
        String oldName = file.getOriginalFilename();
        String newName = UUID.randomUUID().toString() + oldName.substring(oldName.lastIndexOf("."), oldName.length());
        File toFile = null;
        if (file.equals("") || file.getSize() <= 0) {
            file = null;
        } else {
            try {
                InputStream ins = null;
                ins = file.getInputStream();
                toFile = new File(file.getOriginalFilename());
                logger.info("aa"+toFile);
                inputStreamToFile(ins, toFile);
                ins.close();
            }catch (Exception e){
                logger.info("出错了");
            }
        }
        String httphead="https://res.facebodyfitness.com/onlinevideo/img/";
        UpYun upYun = new UpYun(SERVER_NAME,USER_NAME,PASSWORD);
        upYun.setTimeout(60);
        upYun.setApiDomain(UpYun.ED_AUTO);
        Map<String, String> params = new HashMap<String, String>();
        try {
            boolean result = upYun.writeFile("/onlinevideo/img/"+newName,toFile,true,params);
            String urlpath=httphead+newName;
            logger.info("-----------【" + urlpath +","+oldName+"】-----------");
            return urlpath+","+oldName;
        }catch (Exception e){
            logger.info(e.getMessage());
        }
        return "500";
    }

    private static void inputStreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead = 0;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    @RequestMapping("/test")
    public String getTest(){
        return "111test";
    }

    @RequestMapping("/toCheck")
    @ResponseBody
    public void openPushMsg(HttpServletRequest request,
                            HttpServletResponse response) {

        // 微信加密签名，signature结合了开发者填写的token参数和请求中的timestamp参数、nonce参数。
        String signature = request.getParameter("signature");
        // 时间戳
        String timestamp = request.getParameter("timestamp");
        // 随机数
        String nonce = request.getParameter("nonce");
        // 随机字符串  
        String echostr = request.getParameter("echostr");

        PrintWriter out = null;
        try {
            out = response.getWriter();
            if (CheckUtils.checkSignature(signature, timestamp, nonce)) {
                out.print(echostr);
                out.flush();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            out.close();
        }

    }



}
