package com.example.backstage.crs.controller;
import com.example.backstage.crs.service.CurriculumAnalysisService;
import com.example.backstage.crs.util.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;

@Controller
@RequestMapping("/web/CAnalysis/")
public class CurriculumAnalysisController {
    @Resource
    protected CurriculumAnalysisService cAnalysisService;

    @RequestMapping(value = "getCoursesNumber",produces = {"text/json;charset=UTF-8"},method = RequestMethod.POST)
    @ResponseBody
    public String getCoursesNumber(Param param) throws Exception {
        return cAnalysisService.getCoursesNumber(param);
    }


    @RequestMapping(value = "getctCoursereport",produces = {"text/json;charset=UTF-8"},method = RequestMethod.POST)
    @ResponseBody
    public String ctCoursereport(Param param) throws Exception {
        return cAnalysisService.ctCoursereport(param);
    }

    @RequestMapping(value = "getcpCoursereport",produces = {"text/json;charset=UTF-8"},method = RequestMethod.POST)
    @ResponseBody
    public String cpCoursereport(Param param) throws Exception {
        return cAnalysisService.cpCoursereport(param);
    }
}
