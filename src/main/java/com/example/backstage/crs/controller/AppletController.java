package com.example.backstage.crs.controller;

import com.example.backstage.crs.entity.PersonalplanEntity;
import com.example.backstage.crs.service.AppletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/web/applet")
public class AppletController {
    @Autowired
    private AppletService appletService;
    @RequestMapping("/getUser")
    @ResponseBody
    public String getUser(String str){
       return appletService.getUser(str);
    }
    @RequestMapping(value = "/setPersonalplan",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String setPersonalplan(PersonalplanEntity personalplan){
        appletService.setPersonalplan(personalplan);
        return "ok";
    }
    @RequestMapping("/getPersonalplanByuserid")
    @ResponseBody
    public String getPersonalplanByuserid(String userid){
        return appletService.getPersonalplanByuserid(userid);
    }
    @RequestMapping(value = "/affirmPersonalplan",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String affirmPersonalplan(String planid){
        appletService.affirmPersonalplan(planid);
        return "ok";
    }
    @RequestMapping("/getPersonalplanBycreatedby")
    @ResponseBody
    public String getPersonalplanBycreatedby(String createdby){
        return appletService.getPersonalplanBycreatedby(createdby);
    }
    @RequestMapping("/getPersonalplanAll")
    @ResponseBody
    public String getPersonalplanAll(){
        return appletService.getPersonalplanAll();
    }
}
