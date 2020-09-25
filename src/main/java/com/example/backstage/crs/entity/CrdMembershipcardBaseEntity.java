package com.example.backstage.crs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CrdMembershipcardBaseEntity {

  private String cardno;
  private String cardpwd;
  private String cardid;
  private String cardname;
  private String cardtype;
  private Boolean isfree;
  private long typeid;
  private String originalfee;
  private Boolean isopen;
  private Date cardbegin;
  private long periodvalidity;
  private Date cardend;
  private long times;
  private long points;
  private long curtimes;
  private long totaltimes;
  private String totalfee;
  private Date disablebegin;
  private Date disableend;
  private String remarks;
  private long state;
  private String isremoved;
  private Date createdon;
  private String createdby;
  private String createdname;
  private String createdip;
  private Date lastedon;
  private String lastedby;
  private String lastedname;
  private String lastedip;
  private String timefee;

}