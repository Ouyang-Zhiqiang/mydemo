package com.example.backstage.crs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.backstage.crs.entity.MarketingSeckillBaseEntity;
import com.example.backstage.crs.entity.TjTestEntity;
import com.example.backstage.crs.service.BackStageStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web/TjTest/")
public class TjTestController {
    @Autowired
    protected BackStageStatisticsService backStageStatisticsService;

    /*新增秒杀活动*/
    @RequestMapping(value = "selectAllTest",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String selectAllTest(){
        List<Map<String,String>> result=backStageStatisticsService.selectAllTest();
        return JSON.toJSONStringWithDateFormat(result,"yyyy-MM-dd").toString();
    }

    @RequestMapping(value = "updateAllTest",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String updateAllTest(String str){
        List<TjTestEntity> tjTestEntityList= JSONArray.parseArray(str,TjTestEntity.class);
        System.err.println(str);
        int result=backStageStatisticsService.updateAllTest(tjTestEntityList);
        if(result>0){
            return "true";
        }else{
            return "false";
        }
    }
}
