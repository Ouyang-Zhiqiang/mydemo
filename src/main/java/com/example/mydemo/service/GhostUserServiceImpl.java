package com.example.mydemo.service;

import com.example.mydemo.mapper.GhostUserMapper;
import com.example.mydemo.mapper.OrderItemMapper;
import com.example.mydemo.mapper.OrderMapper;
import com.example.mydemo.pojo.GhostUser;
import com.example.mydemo.pojo.Order;
import com.example.mydemo.pojo.OrderItem;
import com.example.mydemo.util.IDUtils;
import com.example.mydemo.util.UUIDGenerator;
import com.example.mydemo.util.WxRefundUtils;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import sun.misc.UUDecoder;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class GhostUserServiceImpl implements GhostUserService {
    @Resource
    private GhostUserMapper ghostUserMapper;
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private OrderItemMapper orderItemMapper;
    @Resource
    private WxRefundUtils wxRefundUtils;
    //线下
//    private String notifyURL = "http://127.0.0.1:8080/refund/return";
//    private String payURL = "https://127.0.0.1:8080/pay/return";

    //线上
    private String notifyURL = "https://www.facebodyfitness.com/refund/return";
    private String payURL = "http://http://fcbd.viphk.ngrok.org/pay/return";


    @Override
    public GhostUser findById() {
        return ghostUserMapper.selectByPrimaryKey("123456");
    }

    @Override
    public int insert(GhostUser ghostUser) {
        return ghostUserMapper.insert(ghostUser);
    }

    /**
     * 微信退款
     * */
    @Override
    public Object wxRefund(String number, BigDecimal price) {
        return  wxRefundUtils.wxRefund(number,price,price,notifyURL);
    }

    /**
     * 微信支付
     * */
    @Override
    public Object wxPay(Order order, HttpServletRequest request) {
        System.out.println("orderid:"+order.getId()+"实付："+order.getSfprice()+"openid"+order.getOpenid());
        Object result = wxRefundUtils.wxPay("活动报名", order.getId(), order.getSfprice(),order.getOpenid(), payURL, request);
        return result;
    }
}
