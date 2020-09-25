package com.example.backstage.crs.controller;
import com.example.backstage.crs.service.CurRevenueService;
import com.example.backstage.crs.util.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/web/CRevenue/")
public class CurRevenueController {
    @Resource
    private CurRevenueService curRevenueService;
    @RequestMapping(value = "getRevenuetotal",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getRevenuetotal(Param param){
        return curRevenueService.getRevenuetotal(param);
    }

    @RequestMapping(value = "getCourseRevenuetotal",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getCourseRevenuetotal(Param param){
        return curRevenueService.getCourseRevenuetotal(param);
    }

    @RequestMapping(value = "getCourseRevenueTotalFirst",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getCourseRevenueTotalFirst(Param param){
        return curRevenueService.getCourseRevenueTotalFirst(param);
    }

    @RequestMapping(value = "getCourseRevenueTotalContinue",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getCourseRevenueTotalContinue(Param param){
        return curRevenueService.getCourseRevenueTotalContinue(param);
    }

    @RequestMapping(value = "getAveragePriceSingleGroupLesson",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getAveragePriceSingleGroupLesson(Param param){
        String[] params = new String[]{"CourseDatestart","CourseDateend","storeid"};
        return curRevenueService.getAveragePriceSingleGroupLesson(param);
    }

    @RequestMapping(value = "getPrivateEducationSingleSectionAveragePrice",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getPrivateEducationSingleSectionAveragePrice(Param param){
        return curRevenueService.getPrivateEducationSingleSectionAveragePrice(param);
    }

    @RequestMapping(value = "getSubcardMoney",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getSubcardMoney(Param param){
        return curRevenueService.getSubcardMoney(param);
    }



    @RequestMapping(value = "getTeamSubcardMoney",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getTeamSubcardMoney(Param param){
        return curRevenueService.getTeamSubcardMoney(param);
    }

    @RequestMapping(value = "getPrivateTeamSubcardMoney",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getPrivateTeamSubcardMoney(Param param){
        return curRevenueService.getPrivateSubcardMoney(param);
    }


    @RequestMapping(value = "getAverageunit",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getAverageunit(Param param){
        return curRevenueService.getAverageunit(param);
    }

    @RequestMapping(value = "getGroupAverageUnitPrice",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getGroupAverageUnitPrice(Param param){
        return curRevenueService.getGroupAverageUnitPrice(param);
    }

    @RequestMapping(value = "getPrivateAverageUnitPrice",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getPrivateAverageUnitPrice(Param param){
        return curRevenueService.getPrivateAverageUnitPrice(param);
    }

    @RequestMapping(value = "getGroupAveragePriceUnit",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getGroupAveragePriceUnit(Param param) throws Exception {
        return curRevenueService.getGroupAveragePriceUnit(param);
    }

    @RequestMapping(value = "getPrivateAveragePriceUnit",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getPrivateAveragePriceUnit(Param param) throws Exception {
        return curRevenueService.getPrivateAveragePriceUnit(param);
    }

    @RequestMapping(value = "getTotalAmountofGoods",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String getTotalAmountofGoods(Param param){
        return curRevenueService.getTotalAmountofGoods(param);
    }


    @RequestMapping(value = "getCustomerPrice",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String  getCustomerPrice(Param param){
        return curRevenueService.getCustomerPrice(param);
    }

    @RequestMapping(value = "getGoodsnumber",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String  getGoodsnumber(Param param){
        return curRevenueService.getGoodsnumber(param);
    }

    @RequestMapping(value = "getSingleProductAmount",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String  getSingleProductAmount(Param param){
        return curRevenueService.getSingleProductAmount(param);
    }



}
