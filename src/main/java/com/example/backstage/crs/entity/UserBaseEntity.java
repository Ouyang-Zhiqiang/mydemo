package com.example.backstage.crs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class UserBaseEntity {

  private long userid;
  private long companyid;
  private long storeid;
  private String tel;
  private String name;
  private String sex;
  private long refuserid;
  private long saleuserid;
  private String password;
  private String salt;
  private String points;
  private String memgrade;
  private long status;
  private String remarks;
  private long numberoflogins;
  private Date createdon;
  private String createdby;
  private String createdname;
  private String createdip;
  private Date lastedon;
  private String lastedby;
  private String lastedname;
  private String lastedip;
  private long inviterid;
  private long regfrom;
  private Date birthday;
  private String workaddress;
  private String homeaddress;
  private String height;
  private String weight;
  private String bmi;
  private String fitnessgoal;
  private String fitnessexperience;
  private String sourcetype;
  private Integer cuserid;
  private Integer classnumber;
  private Integer memgradeone;
}
