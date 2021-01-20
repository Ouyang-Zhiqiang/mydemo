package com.example.backstage.crs.controller;


import com.example.backstage.crs.service.RevenueAnalysisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.text.ParseException;

@Controller
@RequestMapping("/web/RevenueAnalysis/")
public class RevenueAnalysisController {

    @Autowired
    protected RevenueAnalysisService revenueAnalysisService;

    @RequestMapping(value = "DrawChart1",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getRevenuenanl(@RequestParam("StoreId")String StoreId,@RequestParam("StartDate") String StartDate, @RequestParam("EndDate")String EndDate,@RequestParam("days") Integer days) throws ParseException {
        String result=revenueAnalysisService.DrawChart1(StoreId,StartDate,EndDate,days);
        return result;
    }

    @RequestMapping(value = "DrawChart2",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String DrawChart2(@RequestParam("StoreId")String StoreId,@RequestParam("StartDate") String StartDate, @RequestParam("EndDate")String EndDate,@RequestParam("days") Integer days) throws ParseException {
        String result=revenueAnalysisService.DrawChart2(StoreId,StartDate,EndDate,days);
        return result;
    }

    @RequestMapping(value = "BarChar2",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String BarChar2(@RequestParam("StoreId")String StoreId,@RequestParam("StartDate") String StartDate, @RequestParam("EndDate")String EndDate,@RequestParam("days") Integer days
    ,@RequestParam("SalerId") String SalerId,@RequestParam("BuyType") String BuyType) throws ParseException {
        String result=revenueAnalysisService.BarChar2(StoreId,StartDate,EndDate,days,SalerId,BuyType);
        return result;
    }

}
