package com.example.backstage.crs.controller;

import com.example.backstage.crs.service.SpmGoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

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

}
