package com.example.backstage.crs.controller;

import com.alibaba.fastjson.JSON;
import com.example.backstage.crs.entity.SpmIntegralgoodsBaseEntity;
import com.example.backstage.crs.entity.SpmIntegralgoodsOrderEntity;
import com.example.backstage.crs.service.SpmGoodService;
import com.example.backstage.crs.util.Param;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web/SpmGood/")
public class SpmGoodBaseController {
    @Autowired
    protected SpmGoodService spmGoodService;

    @RequestMapping(value = "spmGoodChange",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String spmGoodChange(@RequestParam("orderId")String orderId,@RequestParam("createdby")String createdby,@RequestParam("createdname")String createdname,@RequestParam("ip")String ip) throws ParseException {
        String result=spmGoodService.spmGoodChange(Long.parseLong(orderId),createdby,createdname,ip);
        return result;
    }

    @RequestMapping(value = "GetIntegralgoodsListByStoreId",produces = {"application/json; charset=utf-8"}, method = RequestMethod.POST)
    @ResponseBody
    public String GetIntegralgoodsListByStoreId(@RequestBody Param param) throws Exception {
        return spmGoodService.GetIntegralgoodsListByStoreId(param.getStoreid());
    }

//    @RequestMapping(value = "BuyIntegralgoods",produces = {"text/json;charset=UTF-8"})
//    @ResponseBody
//    public String BuyIntegralgoods(@RequestBody Param param) throws Exception {
//        return spmGoodService.BuyIntegralgoods(param);
//    }
//    @RequestMapping(value = "JudgeQuantity",produces = {"application/jso n; charset=utf-8"}, method = RequestMethod.POST)
//    @ResponseBody
//    public String JudgeQuantity(@RequestBody Param param) throws Exception {
//    //        return "param";
//        return spmGoodService.JudgeQuantity(param.getGoodscode());
//    }

    @RequestMapping(value = "GoodsList")
    @ResponseBody
    public PageInfo GoodsList(Param param) {
        PageHelper.startPage(Integer.parseInt(param.getPage()), Integer.parseInt(param.getLimit()));
        List<Map<String,Object>>  list=spmGoodService.getGoodsList(param.getStoreid());
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }


    @RequestMapping(value = "selectGoodsOrderList")
    @ResponseBody
    public PageInfo selectGoodsOrderList(Param param) {
        PageHelper.startPage(Integer.parseInt(param.getPage()), Integer.parseInt(param.getLimit()));
        List<SpmIntegralgoodsOrderEntity>  list=spmGoodService.selectGoodsOrderList(param.getStoreid());
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }

    @RequestMapping(value = "selectGoodsOrderExchangeList")
    @ResponseBody
    public PageInfo selectGoodsOrderExchangeList(Param param) {
        PageHelper.startPage(Integer.parseInt(param.getPage()), Integer.parseInt(param.getLimit()));
        List<Map<String,Object>>  list=spmGoodService.selectGoodsOrderExchangeList(param.getStoreid());
        PageInfo pageInfo=new PageInfo(list);
        return pageInfo;
    }
}
