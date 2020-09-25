package com.example.backstage.crs.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MarketingBargainingRecordEntity {

  private long recordid;
  private String groupid;
  private String cardno;
  private String phonenumber;
  private String wechatnumber;
  private long paymentstatus;
  private String wechatimgid;
  private double money;
  private String wechatimgurl;
  private long memberstatus;
  private long state;
  private long isremoved;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createdon;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date lastedon;
  private String unionid;
  private long bargainingid;


}
