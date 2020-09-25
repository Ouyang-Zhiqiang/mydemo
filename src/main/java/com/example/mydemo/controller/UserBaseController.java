package com.example.mydemo.controller;

import com.example.mydemo.service.UserBaseService;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class UserBaseController {

    @Resource
    UserBaseService userBaseService;

    @RequestMapping(value = "/getUsers",method = RequestMethod.GET)
    public Object getUsers(){
        return userBaseService.findById(2018090114101L);
    }


    @RequestMapping(value = "/getAllUsers",method = RequestMethod.GET)
    public Object getAllUsers(){
        return userBaseService.findAll();
    }

}
