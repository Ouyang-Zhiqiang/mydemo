package com.example.backstage.crs.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MarketingShopcartOrderEntity {

  private String shopcartorderid;
  private String orderid;
  private long userid;
  private long cardid;
  private String couponid;
  private double originalprice;
  private double netprice;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createon;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date updatedon;

}
