package com.example.backstage.crs.controller;

import com.example.backstage.crs.entity.MarketingSeckillBaseEntity;
import com.example.backstage.crs.service.CurCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/web/ordercourse/")
public class OrdOrderCourse {
    @Autowired
    private CurCourseService curCourseService;

    /*预约课程成功后对教练和会员发送短信通知*/
    @RequestMapping(value = "SendToMembersAndCoach",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String SendToMembersAndCoach(String phonenumber){
        String result=curCourseService.SendSMS(phonenumber);
        return result;
    }

}
