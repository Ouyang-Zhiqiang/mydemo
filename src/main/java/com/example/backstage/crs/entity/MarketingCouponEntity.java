package com.example.backstage.crs.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MarketingCouponEntity {

  private String couponid;
  private String name;
  private long circulation;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date startdate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date enddate;
  private long usertype;
  private long userlimit;
  private long threshold;
  private long discount;
  private long useable;
  private String cards;
  private String storesjson;
  private String explain;
  private boolean isremoved;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createdon;
  private String createdby;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date updatedon;
  private String updatedby;



}
