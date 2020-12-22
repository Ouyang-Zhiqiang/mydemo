package com.example.backstage.crs.service;
import com.alibaba.fastjson.JSON;
import com.example.backstage.crs.mapper.CurCourseMapper;
import com.example.backstage.crs.util.CommonUtil;
import com.example.backstage.crs.util.Param;
import com.example.backstage.crs.util.TemplateParam;
import com.example.backstage.crs.util.WxMssVo;
import com.example.mydemo.util.HttpRequest;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxRemindService {
    @Resource
    private CurCourseMapper courseMapper;

    public String geiWxRemind(Param param) {
        RestTemplate restTemplate = new RestTemplate();
        //https发送消息
        String url = "https://api.weixin.qq.com/cgi-bin/message/subscribe/send?access_token=" + getAccessToken();
        JSONObject jsonResult = CommonUtil.httpsRequest(url, "POST", setWxMssVo(param));
        if(jsonResult!=null){
            int errorCode=jsonResult.getInt("errcode");
            if(errorCode==0){
                System.err.println("ok");
                return "success";
            }else{
                return "发送通知时出现错误："+jsonResult.getString("errmsg");
            }
        }else{
            return "发送通知时出现错误："+jsonResult.getString("errmsg");
        }
    }

    public String setWxMssVo(Param param){
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTouser(courseMapper.getopenid(param.getUserid()).get("openid").toString());
        //点击消息进入到小程序页面
        wxMssVo.setPage("pages/page14/index");
        List<TemplateParam> list = new ArrayList<>();
        /*会员约课通知*/
        if (param.getTemplatetype().equals("1")){
            list.add(new TemplateParam("time3",param.getTime()));
            list.add(new TemplateParam("thing6",param.getCoursename()));
            wxMssVo.setTemplate_id("ZROiQ06D5R5rNpSJW2eJYQwz1ha4d3PjgxLx0itJadQ");
        }
        /*会员取消约课通知*/
        else if(param.getTemplatetype().equals("2")){
            list.add(new TemplateParam("time3",param.getTime()));
            list.add(new TemplateParam("thing6",param.getCoursename()));
            wxMssVo.setTemplate_id("kAtadGTe9w_WA4VT3rMrDCU8mQBVqOIz5iF3ddwoozE");
        }
        /*会员绑卡时通知*/
        else if(param.getTemplatetype().equals("3")){
            list.add(new TemplateParam("thing2",param.getCardname()));
            list.add(new TemplateParam("amount4",param.getFee()));
            wxMssVo.setTemplate_id("yIK0HdwSLKSJjl8wgvB2szmA52TSPtkY6Km2UBIuJx8");
        }
        /*会员约课通知教练*/
        else if(param.getTemplatetype().equals("4")){
            list.add(new TemplateParam("thing1",param.getName()));
            list.add(new TemplateParam("time3",param.getTime()));
            list.add(new TemplateParam("thing4",param.getCoursename()));
            wxMssVo.setTemplate_id("t2wEM3FhCIQC6n4ibc6wjtzTIk1Llbodywa_RdQkNlw");
        }
        /*会员取消约课通知教练*/
        else if(param.getTemplatetype().equals("5")){
            list.add(new TemplateParam("thing1",param.getName()));
            list.add(new TemplateParam("time3",param.getTime()));
            list.add(new TemplateParam("thing2",param.getCoursename()));
            wxMssVo.setTemplate_id("Or03ezRA7PtWVrilio_YpYlUtUZBMHj_7vFnHSCwswE");
        }
        /*会员购卡时通知门店店长*/
        else if(param.getTemplatetype().equals("6")){
            list.add(new TemplateParam("thing1",param.getName()));
            list.add(new TemplateParam("thing2",param.getCardname()));
            list.add(new TemplateParam("amount3",param.getFee()));
            wxMssVo.setTemplate_id("hClZVlmGN_wmRlymc47YPDY-U_iRz-MaFSNO8TjUXb0");
        }
        wxMssVo.setTemplateParamList(list);
        return wxMssVo.toJSON();
    }


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

    public String getOpenid(String code){
        String unionId="";
        String strAppId = "wx1aac047a48fb2c68";//就是appid
        String strSecret= "f568fe66b27e5e5f91d37e9c7994084a";//就是secret
        String strUrl =" https://api.weixin.qq.com/sns/oauth2/access_token";
        strUrl += "?appid=" + strAppId;
        strUrl += "&secret=" + strSecret;
        strUrl += "&code=" + code; //微信反馈的code
        strUrl += "&grant_type=authorization_code";
        byte[] res = null;
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
            System.err.println(jsonObject);
            unionId = jsonObject.getString("unionid");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return JSON.toJSONString(courseMapper.wxlogin(unionId));
    }

    public String getunionId(String code){
        String unionId="";
        String strAppId = "wx334468183654cf80";//就是appid
        String strSecret= "f5af5ed7bbaffe216534bb577b84b836";//就是secret
        String strUrl =" https://api.weixin.qq.com/sns/jscode2session";
        strUrl += "?appid=" + strAppId;
        strUrl += "&secret=" + strSecret;
        strUrl += "&js_code=" + code; //微信反馈的code
        strUrl += "&grant_type=authorization_code";
        byte[] res = null;
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
            unionId = jsonObject.getString("unionid");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return unionId;
    }




}
