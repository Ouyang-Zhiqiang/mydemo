package com.example.backstage.crs.controller;

import com.example.backstage.crs.entity.CrdMembershipcardcategoryBase;
import com.example.backstage.crs.service.BackStageStatisticsService;
//import com.example.backstage.crs.util.NUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Controller
public class BsMembershipCardController {
    @Autowired
    protected BackStageStatisticsService backstagestatisticsService;
//    private Dao fbsDao;


    @RequestMapping(value = "GetTotalStatisticsByNameAndDate",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String GetTotalStatisticsByNameAndDate(@RequestParam("name")String name,@RequestParam("datebegin") String datebegin,@RequestParam("dateend")String dateend) throws Exception {
        return backstagestatisticsService.GetTotalStatisticsByNameAndDate(name,datebegin,dateend);
    }

    @RequestMapping(value = "selectMemberCardByDateAndSaleridAndStoreId",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String selectMemberCardByDateAndSalerNameAndStoreName(@RequestParam("storeid")String storeid,@RequestParam("saleuserid") String saleuserid,
                                                                 @RequestParam("datebegin") String datebegin,@RequestParam("dateend")String dateend) throws Exception {
        /*场馆名、销售人员名称、开始时间、结束时间*/
        return backstagestatisticsService.selectMemberCardByDateAndcreatednameAndStoreName(storeid,saleuserid,datebegin,dateend);
    }

    @RequestMapping(value = "selectMemberCardList",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String selectMemberCardList(@RequestParam("storeid")String storeid,@RequestParam("saleuserid") String saleuserid,
                                       @RequestParam("datebegin") String datebegin,@RequestParam("dateend")String dateend,@RequestParam("page")String page,@RequestParam("limit")String limit
    ,@RequestParam("name") String name) throws Exception {
        /*场馆名、销售人员名称、开始时间、结束时间*/
        return backstagestatisticsService.selectMemberCardList(storeid,saleuserid,datebegin,dateend,page,limit,name);
    }

    /*用户画像（单）*/
    @RequestMapping(value = "userPortraitSingle",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String userPortraitSingle(@RequestParam("CourseDatestart") String CourseDatestart, @RequestParam("CourseDateend") String CourseDateend
            , @RequestParam("storeid") String storeid, @RequestParam(value="n") String n, @RequestParam(value = "totalAmount") String totalAmount) throws Exception {
        String result=backstagestatisticsService.selectUserPortraitSingle(CourseDatestart,CourseDateend,storeid,n,totalAmount);
        return result;
    }

    /*用户画像（群）*/
    @RequestMapping(value = "userPortraitGroup",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String userPortraitGroup(@RequestParam("CourseDatestart") String CourseDatestart, @RequestParam("CourseDateend") String CourseDateend
            , @RequestParam("storeid") String storeid) throws Exception {
        String result=backstagestatisticsService.selectUserPortraitGroup(CourseDatestart,CourseDateend,storeid);
        return result;
    }

    /*活跃管理*/
    @RequestMapping(value = "activeManagement",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String activeManagement(@RequestParam("CourseDatestart") String CourseDatestart, @RequestParam("CourseDateend") String CourseDateend
            , @RequestParam("storeid") String storeid) throws Exception {
        String result=backstagestatisticsService.selectActiveManagement(CourseDatestart,CourseDateend,storeid);
        return result;
    }

    /*留存管理*/
    @RequestMapping(value = "retentionManagement",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String retentionManagement(@RequestParam("CourseDatestart") String CourseDatestart, @RequestParam("CourseDateend") String CourseDateend
            , @RequestParam("storeid") String storeid) throws Exception {
        String result=backstagestatisticsService.selectRetentionManagement(CourseDatestart,CourseDateend,storeid);
        return result;
    }

    /*基础模块定义*/
    @RequestMapping(value = "basicModule",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String basicModule(@RequestParam("CourseDatestart") String CourseDatestart, @RequestParam("CourseDateend") String CourseDateend
            , @RequestParam("storeid") String storeid) throws Exception {
        String result=backstagestatisticsService.selectBasicModule(CourseDatestart,CourseDateend,storeid);
        return result;
    }


    /*跟单管理*/
    @RequestMapping(value = "documentaryManagement",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String documentaryManagement(@RequestParam("CourseDatestart") String CourseDatestart, @RequestParam("CourseDateend") String CourseDateend
            , @RequestParam("storeid") String storeid) throws Exception {
        String result=backstagestatisticsService.selectDocumentaryManagement(CourseDatestart,CourseDateend,storeid);
        return result;
    }


    /*转化管理*/
    @RequestMapping(value = "transformationManagement",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String transformationManagement(@RequestParam("CourseDatestart") String CourseDatestart, @RequestParam("CourseDateend") String CourseDateend
            , @RequestParam("storeid") String storeid) throws Exception {
        String result=backstagestatisticsService.selectTransformationManagement(CourseDatestart,CourseDateend,storeid);
        return result;
    }


    /*拉新管理*/
    @RequestMapping(value = "innovationManagement",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String innovationManagement(@RequestParam("CourseDatestart") String CourseDatestart, @RequestParam("CourseDateend") String CourseDateend
            , @RequestParam("storeid") String storeid) throws Exception {
        String result=backstagestatisticsService.selectInnovationManagement(CourseDatestart,CourseDateend,storeid);
        return result;
    }


    /*新增会员卡*/
    @RequestMapping(value = "addMemberCard",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String addMemberCard(@RequestBody CrdMembershipcardcategoryBase crdMembershipcardcategoryBase) throws Exception {
        Boolean a=backstagestatisticsService.insertMemberCard(crdMembershipcardcategoryBase);
        return a+"";
    }

}












