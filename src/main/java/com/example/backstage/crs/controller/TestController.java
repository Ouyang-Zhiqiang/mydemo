package com.example.backstage.crs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
    @RequestMapping("/fun")
    @ResponseBody
    public String fun(){
        return "this is test.fun";
    }
}