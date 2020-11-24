package com.example.backstage.crs.service;

import com.alibaba.fastjson.JSON;
import com.example.backstage.crs.util.CommonUtil;
import com.example.backstage.crs.util.Param;
import com.example.backstage.crs.util.TemplateParam;
import com.example.backstage.crs.util.WxMssVo;
import net.sf.json.JSONObject;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class WxRemindService {
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
                return "error";
            }
        }else{
            return "error";
        }
    }

    public String setWxMssVo(Param param){
        WxMssVo wxMssVo = new WxMssVo();
        wxMssVo.setTouser(param.getOpenid());
        //点击消息进入到小程序页面
        wxMssVo.setPage("pages/page14/index");
        List<TemplateParam> list = new ArrayList<>();
        if (param.getTemplatetype().equals("1")){
            list.add(new TemplateParam("time3",param.getSchedulebegin()));
            list.add(new TemplateParam("thing6",param.getCoursename()));
            wxMssVo.setTemplate_id("ZROiQ06D5R5rNpSJW2eJYQwz1ha4d3PjgxLx0itJadQ");
        }else if(param.getTemplatetype().equals("2")){
            list.add(new TemplateParam("time3",param.getSchedulebegin()));
            list.add(new TemplateParam("name1",param.getCoursename()));
            wxMssVo.setTemplate_id("kAtadGTe9w_WA4VT3rMrDHj-ecj1aPt1AVBOzxOb0Tc");
        }else if(param.getTemplatetype().equals("3")){
            list.add(new TemplateParam("time3",param.getSchedulebegin()));
            list.add(new TemplateParam("thing1","飞燕"));
            list.add(new TemplateParam("thing4",param.getCoursename()));
            wxMssVo.setTemplate_id("t2wEM3FhCIQC6n4ibc6wjtzTIk1Llbodywa_RdQkNlw");
        }
        wxMssVo.setTemplateParamList(list);
        System.err.println(wxMssVo.toJSON());
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

}
