package com.example.backstage.crs.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class  OrdOrdercourseEntity {

  private long ordid;
  private String ordtype;
    private String cardtype;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date coursedate;
  private String storeid;
  private String storename;
  private String scheduleid;
  private long courseid;
  private String coursename;
  private String courseprice;
  private String coachid;
  private String coachname;
  private long userid;
  private int traineenum;
  private int curtimes;
  private String coursetime;
  private String cardno;
  private int usabletimes;
  private long ordtimes;
  private long ordstate;
  private long signstate;
  private String remarks;
  private String isremoved;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createdon;
  private String createdby;
  private String createdname;
  private String createdip;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  @JsonFormat(pattern ="yyyy-MM-dd", timezone = "GMT+8")
  private Date lastedon;
  private String lastedby;
  private String lastedname;
  private String lastedip;
  private String courseendtime;
  private String issenduser;
  private String issendcoach;
  private String issendwxevaluate;
  private String grades;
  private String reviews;
  private String tel;
  private String name;
  private String cardname;


}
