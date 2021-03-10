package com.example.backstage.crs.entity;

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
  private long storeid;
  private String storename;
  private long scheduleid;
  private long courseid;
  private String coursename;
  private String courseprice;
  private long coachid;
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


}
