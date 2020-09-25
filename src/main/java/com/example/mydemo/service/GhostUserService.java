package com.example.mydemo.service;

import com.example.mydemo.pojo.GhostUser;
import com.example.mydemo.pojo.Order;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;


public interface GhostUserService {

    public GhostUser findById();
    public int insert(GhostUser ghostUser);

    Object wxRefund(String number, BigDecimal price);

    Object wxPay(Order order, HttpServletRequest request);


}
