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
    @RequestMapping("/setPartsjson")
    @ResponseBody
    public String setPartsjson(String scheduleid){
        return JSON.toJSONString(newMapper.setPartsjson(scheduleid));
    }
    @RequestMapping("/getXiaoshou")
    @ResponseBody
    public String getXiaoshou(){
        return JSON.toJSONString(newMapper.getXiaoshou());
    }
    @RequestMapping("/deletetk")
    @ResponseBody
    public String deletetk(Param param){
        newMapper.deletetk(param.getScheduleid());
        return "ok";
    }
    @RequestMapping("/cancelReservation")
    @ResponseBody
    public String cancelReservation(Param param){
        System.err.println(param);
        if(param.getCardtype().equals("S")){
            newMapper.quxiaoyuyue1(param.getTraineenum(),param.getCardno());
        }
        return "ok";
    }
    @RequestMapping("/cancelReservation2")
    @ResponseBody
    public String cancelReservation2(Param param){
        newMapper.quxiaoyuyue2(param.getTraineenum(),param.getScheduleid());
        newMapper.quxiaoyuyue3(param.getTraineenum(),param.getScheduleid());
        return "ok";
    }
    @RequestMapping("/signed")
    @ResponseBody
    public String signed(Param param){
        newMapper.signed1(param.getOrdid());
        newMapper.signed2(param.getTraineenum(),param.getScheduleid());
        return "ok";
    }

    @RequestMapping("/setUser")
    @ResponseBody
    public String setUser(String name){
        return JSON.toJSONString(newMapper.setUser(name));
    }
    @RequestMapping("/getAllcourse")
    @ResponseBody
    public String getAllcourse(){
        return JSON.toJSONString(newMapper.getAllcourse());
    }
    @RequestMapping("/getStrenth")
    @ResponseBody
    public String getStrenth(Param param){
        System.err.println(param);
        return JSON.toJSONString(newMapper.getStrenth(param.getCid(),param.getStrengthgrade(),param.getLimit(),param.getPage()));
    }
    @RequestMapping("/getUnits")
    @ResponseBody
    public String getUnits(){
        return JSON.toJSONString(newMapper.getUnits());
    }
    @RequestMapping("/getCategory")
    @ResponseBody
    public String getCategory(){
        return JSON.toJSONString(newMapper.getCategory());
    }
    @RequestMapping("/getStrength")
    @ResponseBody
    public String getStrength(){
        return JSON.toJSONString(newMapper.getStrength());
    }
    @RequestMapping("/getAims")
    @ResponseBody
    public String getAims(){
        return JSON.toJSONString(newMapper.getAims());
    }
    @RequestMapping("/getParts")
    @ResponseBody
    public String getParts(){
        return JSON.toJSONString(newMapper.getParts());
    }
    @RequestMapping("/getactionlibrary")
    @ResponseBody
    public String getactionlibrary(Param param){
        return JSON.toJSONString(newMapper.getactionlibrary(param.getCategory(),param.getStrength()));
    }
    @RequestMapping("/setStrength")
    @ResponseBody
    public String setStrength(Param param){
        newMapper.setStrength(param);
        return "ok";
    }
    @RequestMapping("/DeleteStrength")
    @ResponseBody
    public String DeleteStrength(String sid){
        newMapper.DeleteStrength(sid);
        return "ok";
    }
    @RequestMapping("/UpdateStrength")
    @ResponseBody
    public String UpdateStrength(Param param){
        newMapper.UpdateStrength(param);
        return "ok";
    }
    @RequestMapping("/getNumberofreservations")
    @ResponseBody
    public String getNumberofreservations(Param param){
        List<Map<Object, Object>> numberofreservations = newMapper.getNumberofreservations(param.getStoreid(), param.getDay1(), param.getDay2());
        return JSON.toJSONStringWithDateFormat(numberofreservations, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);

    }

}
