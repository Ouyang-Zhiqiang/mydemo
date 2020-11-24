package com.example.backstage.crs.controller;

import com.example.backstage.crs.service.CurCourseService;
import com.example.backstage.crs.service.WxRemindService;
import com.example.backstage.crs.util.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/web/wxremind")
public class WxRemindController {
    @Autowired
    private WxRemindService wxRemindService;

    @GetMapping(value = "/geiWxRemind",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String geiWxRemind(Param param){
        return wxRemindService.geiWxRemind(param);
    }

    @RequestMapping(value = "/wxLogin",produces = {"text/json;charset=UTF-8"},method = RequestMethod.POST)
    @ResponseBody
    public String wxLogin(Param param) throws Exception {
        System.err.println(param.getCode());
        if (null!=param.getCode()&&""!=param.getCode()){
            return wxRemindService.getOpenid(param.getCode());
        }else {
            return "登录异常";
        }

    }
}
