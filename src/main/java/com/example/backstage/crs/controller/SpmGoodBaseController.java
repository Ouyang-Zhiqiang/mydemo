package com.example.backstage.crs.controller;

import com.example.backstage.crs.entity.SpmIntegralgoodsBaseEntity;
import com.example.backstage.crs.service.SpmGoodService;
import com.example.backstage.crs.util.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.spring.web.json.Json;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;

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
//    @RequestMapping(value = "JudgeQuantity",produces = {"application/json; charset=utf-8"}, method = RequestMethod.POST)
//    @ResponseBody
//    public String JudgeQuantity(@RequestBody Param param) throws Exception {
//    //        return "param";
//        return spmGoodService.JudgeQuantity(param.getGoodscode());
//    }
}
