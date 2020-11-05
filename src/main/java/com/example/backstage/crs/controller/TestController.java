package com.example.backstage.crs.controller;
import com.alibaba.fastjson.JSON;
import com.example.backstage.crs.util.CommonUtil;
import com.example.backstage.crs.util.TemplateParam;
import com.example.backstage.crs.util.WxMssVo;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import java.io.*;
import java.net.URI;
import java.net.URL;
import java.util.*;

@Controller
public class TestController {
    public String getopenid(String code){
        String openid="";
        String strAppId = "wx334468183654cf80";//就是appid
        String strSecret= "f5af5ed7bbaffe216534bb577b84b836";//就是secret
        String strUrl =" https://api.weixin.qq.com/sns/jscode2session";
        strUrl += "?appid=" + strAppId;
        strUrl += "&secret=" + strSecret;
        strUrl += "&js_code=" + code; //微信反馈的code
        strUrl += "&grant_type=authorization_code";
        byte[] res = null;
        System.err.println(strUrl);
        org.apache.http.client.HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet();
        try {
            URL url = new URL(strUrl);
            URI uri = new URI(url.getProtocol(), url.getHost(), url.getPath(), url.getQuery(), null);
            httpget = new HttpGet(uri);
            HttpResponse response = null;
            response = httpclient.execute(httpget);
            res = IOUtils.toByteArray(response.getEntity().getContent());
        } catch (Exception e) {

        } finally {
            if (httpget != null) {
                httpget.abort();
            }
            httpclient.getConnectionManager().shutdown();
        }
        try {
            JSONObject jsonObject = JSONObject.fromObject(new String(res, "utf-8"));
            openid = jsonObject.getString("openid");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return openid;
    }

    @RequestMapping(value = "/getAccessToken",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getAccessToken() {
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> params = new HashMap<>();
        params.put("APPID", "wx334468183654cf80");  //开发者的appid
        params.put("APPSECRET", "f5af5ed7bbaffe216534bb577b84b836");  //应用密匙微信公众平台可找到
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(
                "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid={APPID}&secret={APPSECRET}", String.class, params);
        String body = responseEntity.getBody();
        com.alibaba.fastjson.JSONObject object = JSON.parseObject(body);
        String Access_Token = object.getString("access_token");
        String expires_in = object.getString("expires_in");
        System.out.println("有效时长expires_in：" + expires_in);
        return Access_Token;
    }

    @GetMapping(value = "/push",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String push() {
        RestTemplate restTemplate = new RestTemplate();
        //https发送消息
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + getAccessToken();
        //拼接推送的模版
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTouser("oSDY348f0DkftiR643Gb9AtM0cnE");
        //模板id
        wxMssVo.setTemplate_id("ZROiQ06D5R5rNpSJW2eJYcfxjKARhVLRb87ZcKt53To");
        //点击消息进入到小程序页面
        wxMssVo.setPage("pages/page14/index");
        List<TemplateParam> list = new ArrayList<>();
        list.add(new TemplateParam("name2","教练"));
        list.add(new TemplateParam("thing8","课程名称"));
        wxMssVo.setTemplateParamList(list);
        System.err.println(wxMssVo.toJSON());
        JSONObject jsonResult = CommonUtil.httpsRequest(url, "POST", wxMssVo.toJSON());
        if(jsonResult!=null){
            System.out.println(jsonResult);
            int errorCode=jsonResult.getInt("errcode");
            if(errorCode==0){
                return "发送成功";
            }else{
                return "发送失败";
            }
        }else{
            return "发送失败";
        }
    }




}