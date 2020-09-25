package com.example.mydemo.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order {
    private String id;

    private BigDecimal sfprice;

    private String openid;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public BigDecimal getSfprice() {
        return sfprice;
    }

    public void setSfprice(BigDecimal sfprice) {
        this.sfprice = sfprice;
    }


}
