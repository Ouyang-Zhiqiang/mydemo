package com.example.backstage.crs.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MarketingLuckdrawBaseRecordEntity {

  private long recordid;
  private long luckdrawid;
  private String goodsname;
  private String phonenumber;
  private String wechatnumber;
  private String wechatimgid;
  private String wechatimgurl;
  private long state;
  private long isremoved;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createdon;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date lastedon;
  private String expirydatecode;
  private String username;

}
