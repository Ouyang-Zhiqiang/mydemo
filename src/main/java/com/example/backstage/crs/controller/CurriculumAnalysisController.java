package com.example.backstage.crs.controller;
import com.example.backstage.crs.service.CurriculumAnalysisService;
import com.example.backstage.crs.util.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.text.ParseException;

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

    @RequestMapping(value = "getPersontimesandClassnumber",produces = {"text/json;charset=UTF-8"},method = RequestMethod.POST)
    @ResponseBody
    public String getPersontimesandClassnumber(Param param){
        return cAnalysisService.getPersontimesandClassnumber(param);
    }

    @RequestMapping(value = "getAmountoflessonssoldpercard",produces = {"text/json;charset=UTF-8"},method = RequestMethod.POST)
    @ResponseBody
    public String getAmountoflessonssoldpercard(Param param){
       return cAnalysisService.getAmountoflessonssoldpercard(param);
    }

    @RequestMapping(value = "selectcost",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String selectcost(Param param){
        return cAnalysisService.selectcost(param.getAccountid());
    }

    @RequestMapping(value = "updatecost",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String updatecost(String jsonstr){
       return cAnalysisService.updatecost(jsonstr);
    }


    @RequestMapping(value = "selectrevenue",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String selectrevenue(Param param){
        return cAnalysisService.selectrevenue(param.getAccountid());
    }


    @RequestMapping(value = "getNumberofreservation",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getNumberofreservation(String array,Param param) throws ParseException {
        return cAnalysisService.getNumberofreservation(array,param);
    }

    @RequestMapping(value = "bindingvenue",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public void bindingvenue(Param param){
        cAnalysisService.bindingvenue(param);
    }


    @RequestMapping(value = "BuyCard",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String BuyCard(Param param) throws Exception {
        return cAnalysisService.BuyCard(param);
    }

    @RequestMapping(value = "selectstorename",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String selectstorename(Param param){
        System.err.println(param.getUserid());
        return cAnalysisService.selectstorename(param);
    }


}
