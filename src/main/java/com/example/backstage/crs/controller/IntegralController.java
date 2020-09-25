package com.example.backstage.crs.controller;

import com.example.backstage.crs.entity.SpmIntegralgoodsBaseEntity;
import com.example.backstage.crs.service.IntegralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/web/integral/")
public class IntegralController {
    @Autowired
    protected IntegralService integralService;

    /*新增商品*/
    @RequestMapping(value = "addIntegral",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String addIntegral(@RequestBody SpmIntegralgoodsBaseEntity spmIntegralgoodsBaseEntity) throws Exception {
        return integralService.insertSPMIntegralGoodsBase(spmIntegralgoodsBaseEntity);
    }

    /*修改某一个商品*/
    @RequestMapping(value = "updateIntegral",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String updateIntegral(@RequestBody SpmIntegralgoodsBaseEntity spmIntegralgoodsBaseEntity) throws Exception {
        return integralService.updateSPMIntegralGoodsBase(spmIntegralgoodsBaseEntity);
    }

    /*删除某一个商品*/
    @RequestMapping(value = "deleteIntegral",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String deleteSPMIntegralGoodsBase(@RequestParam("goodscode") Integer goodscode) throws Exception {
        return integralService.deleteSPMIntegralGoodsBase(goodscode);
    }

    /*修改商品的上下架*/
    @RequestMapping(value = "updateIntegralSaleState",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String updateSPMIntegralGoodsBaseSalesState(SpmIntegralgoodsBaseEntity spmIntegralgoodsBaseEntity) throws Exception {
        return integralService.updateSPMIntegralGoodsBaseSalesState(spmIntegralgoodsBaseEntity);
    }

}
