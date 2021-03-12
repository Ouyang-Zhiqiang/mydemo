package com.example.backstage.crs.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/web/test")
public class TestController {
    @RequestMapping(value = "fun1")
    @ResponseBody
    public String  fun(){

        return "ok";
    }
}