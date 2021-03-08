package com.example.backstage.crs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.backstage.crs.mapper.NewMapper;
import com.example.backstage.crs.service.AppletService;
import com.example.backstage.crs.util.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web/new")
public class NewController {
    @Autowired
    private NewMapper newMapper;
    @RequestMapping("/getStoreIdAll")
    @ResponseBody
    public String getStoreIdAll(){
        return JSON.toJSONString(newMapper.getStoreIdAll());
    }
    @RequestMapping("/getCoachAll")
    @ResponseBody
    public String getCoachAll(){
        return JSON.toJSONString(newMapper.getCoachAll());
    }
    @RequestMapping("/getFunction")
    @ResponseBody
    public String getFunction(){
        return JSON.toJSONString(newMapper.getFunction());
    }
    @RequestMapping("/getCourseAll")
    @ResponseBody
    public String getCourseAll(){
        return JSON.toJSONString(newMapper.getCourseAll());
    }
    @RequestMapping("/login")
    @ResponseBody
    public String login(String tel){
        return JSON.toJSONString(newMapper.login(tel));
    }
    @RequestMapping("/getTeamschedule")
    @ResponseBody
    public String getTeamschedule(Param param){
        List<Map<Object, Object>> teamschedule = newMapper.getTeamschedule(param.getStoreid(), param.getCoachid(),
                param.getDay1(), param.getDay2());
        return JSON.toJSONStringWithDateFormat(teamschedule, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
    }
    @RequestMapping("/getCurTeamStrength")
    @ResponseBody
    public String getCurTeamStrength(Param param){
        return JSON.toJSONString(newMapper.getCurTeamStrength(param.getCid(),param.getStrenth()));
    }
    @RequestMapping("/setCurTeamCourseBase")
    @ResponseBody
    public String setCurTeamCourseBase(Param param){
        newMapper.setCurTeamCourseBase(param);
        newMapper.setCurTeamSchedule(param);
        return "ok";
    }
    @RequestMapping("/upreservablenumber")
    @ResponseBody
    public String upreservablenumber(Param param){
        newMapper.upreservablenumber(param.getReservablenumber(),param.getScheduleid());
        return "ok";
    }
    @RequestMapping("/getReservablenumber")
    @ResponseBody
    public String getReservablenumber(Param param){
        return JSON.toJSONString(newMapper.getReservablenumber(param.getScheduleid()));
    }
    @RequestMapping("/getRoleid")
    @ResponseBody
    public String getRoleid(String tel){
        return JSON.toJSONString(newMapper.getRoleid(tel));
    }
    @RequestMapping("/cancelReservation")
    @ResponseBody
    public String cancelReservation(Param param){
        System.err.println(param);
        return "ok";
    }
    @RequestMapping("/setPartsjson")
    @ResponseBody
    public String setPartsjson(String scheduleid){
        return JSON.toJSONString(newMapper.setPartsjson(scheduleid));
    }

}
