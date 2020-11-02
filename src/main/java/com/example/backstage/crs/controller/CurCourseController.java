package com.example.backstage.crs.controller;
import com.example.backstage.crs.service.CurCourseService;
import com.example.backstage.crs.util.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.net.UnknownHostException;
import java.util.Date;

@Controller
@RequestMapping("/web/CCourse/")
public class CurCourseController {
    @Autowired
    private CurCourseService curCourseService;

    @RequestMapping(value = "getTotalAttendance",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getTotalAttendance(Param param) throws Exception {
        return curCourseService.getTotalAttendance(param);
    }

    @RequestMapping(value = "getGroupClassAttendance",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getGroupClassAttendance(Param param) throws Exception {
        return curCourseService.getGroupClassAttendance(param);
    }

    @RequestMapping(value = "getMonthlyAttendance",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getMonthlyAttendance(Param param){
        return curCourseService.getMonthlyAttendance(param);
    }

    @RequestMapping(value = "getTConversionRateOfGroupLessons",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getTConversionRateOfGroupLessons(Param param){
        return curCourseService.getTConversionRateOfGroupLessons(param);
    }

    @RequestMapping(value = "getPConversionRateOfGroupLessons",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getPConversionRateOfGroupLessons(Param param){
        return curCourseService.getPConversionRateOfGroupLessons(param);
    }

    @RequestMapping(value = "getNumberOfLessonsGroupLessons",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getNumberOfLessonsGroupLessons(Param param) throws Exception {
        return curCourseService.getNumberOfLessonsGroupLessons(param);
    }

    @RequestMapping(value = "getNumberOfPrivateLessons",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getNumberOfPrivateLessons(Param param) throws Exception {
        return curCourseService.getNumberOfPrivateLessons(param);
    }

    @RequestMapping(value = "RegUserSendvcode",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String RegUserSendvcode(Param param) throws Exception {
        String code=(Math.random()*100+"").substring(0,1)+(new Date().getTime()+"").substring(8);
        param.setCode(code);
        curCourseService.sendNotice(param.getPhonenum(),"您在颜身运动请求的注册绑定验证码是："+code);
        if (true) {
            return curCourseService.RegUserSendvcode(param);
        }else {
            return "{\"status\":false}";
        }
    }

    @RequestMapping(value = "getcourseinformation",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getcourseinformation(Param param){
        return curCourseService.getcourseinformation(param);
    }

    @RequestMapping(value = "privatelessonschedule",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String privatelessonschedule(Param param){
        return curCourseService.privatelessonschedule(param);
    }

    @RequestMapping(value = "goukasongjifen",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String goukasongjifen(Param param) throws UnknownHostException {
        return curCourseService.goukasongjifen(param);
    }




}

