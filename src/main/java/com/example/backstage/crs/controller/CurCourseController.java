package com.example.backstage.crs.controller;
import com.example.backstage.crs.service.CurCourseService;
import com.example.backstage.crs.util.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
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

}

