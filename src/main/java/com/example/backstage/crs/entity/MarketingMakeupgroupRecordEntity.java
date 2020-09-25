package com.example.backstage.crs.entity;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class MarketingMakeupgroupRecordEntity {

  private String recordid;
  private long groupid;
  private long cardno;
  private String phonenumber;
  private String wechatnumber;
  private long paymentstatus;
  private String groupnumber;
  private String wechatimgid;
  private String wechatimgurl;
  private long memberStatus;
  private long state;
  private long isremoved;
  private Date createdon;
  private Date lastedon;
  private Integer total;
  private String unionid;
  private BigDecimal price;
  private Integer cardstatus;


}
