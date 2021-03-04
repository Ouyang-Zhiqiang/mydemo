package com.example.backstage.crs.service;

import com.example.mydemo.pojo.Order;
import com.example.mydemo.service.GhostUserService;
import com.example.mydemo.util.WxRefundUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@Service
public class WxPayService{
    @Autowired private WxRefundUtils wxRefundUtils;

    //线下
    private String notifyURL = "http://127.0.0.1:8080/refund/return";
    private String payURL = "https://127.0.0.1:8080/pay/return";

    //线上
//    private String notifyURL = "https://www.facebodyfitness.com/refund/return";
//    private String payURL = "http://http://fcbd.viphk.ngrok.org/pay/return";
    /**
     * 微信支付
     * */
    public Object wxPay(Order order, HttpServletRequest request) {
        System.out.println("orderid:"+order.getId()+"实付："+order.getSfprice()+"openid"+order.getOpenid());
        Object result = wxRefundUtils.wxPay("支付商品", order.getId(), order.getSfprice(),order.getOpenid(), payURL, request);
        return result;
    }
}
