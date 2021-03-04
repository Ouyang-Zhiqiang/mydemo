package com.example.backstage.crs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.backstage.crs.entity.PersonalplanEntity;
import com.example.backstage.crs.mapper.AppletMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class AppletService {
    @Autowired
    protected AppletMapper appletMapper;
    public String getUser(String str){
        List<Map<Object, Object>> Users = appletMapper.getUser(str);
        return JSON.toJSONString(Users);
    }
    public void setPersonalplan(PersonalplanEntity personalplan){
        appletMapper.setPersonalplan(personalplan);
    }
    public String getPersonalplanByuserid(String userid){
        List<Map<Object, Object>> Personalplans = appletMapper.getPersonalplanByuserid(userid);
        return JSON.toJSONStringWithDateFormat(Personalplans, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
    }
    public void affirmPersonalplan(String planid){
        appletMapper.affirmPersonalplan(planid);
    }
    public String getPersonalplanBycreatedby(String createdby){
        List<Map<Object, Object>> Personalplans = appletMapper.getPersonalplanBycreatedby(createdby);
        return JSON.toJSONStringWithDateFormat(Personalplans, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
    }
    public String getPersonalplanAll(){
        List<Map<Object, Object>> Personalplans = appletMapper.getPersonalplanAll();
        return JSON.toJSONStringWithDateFormat(Personalplans, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
    }
}
