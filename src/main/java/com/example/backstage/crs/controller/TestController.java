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
@RequestMapping("/web/test")
public class TestController {
    @RequestMapping(value = "fun1")
//    @ResponseBody
    public String  fun(){
        return "facebodywebsite/index";
    }
}