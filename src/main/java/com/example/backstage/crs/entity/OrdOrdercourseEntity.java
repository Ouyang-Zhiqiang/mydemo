package com.example.backstage.crs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class OrdOrdercourseEntity {

  private long ordid;
  private String ordtype;
  private String cardtype;
  private java.sql.Date coursedate;
  private long storeid;
  private String storename;
  private long scheduleid;
  private long courseid;
  private String coursename;
  private String courseprice;
  private long coachid;
  private String coachname;
  private long userid;
  private long traineenum;
  private java.sql.Time coursetime;
  private String cardno;
  private long usabletimes;
  private long ordtimes;
  private long ordstate;
  private long signstate;
  private String remarks;
  private String isremoved;
  private Date createdon;
  private String createdby;
  private String createdname;
  private String createdip;
  private Date lastedon;
  private String lastedby;
  private String lastedname;
  private String lastedip;
  private java.sql.Time courseendtime;
  private String issenduser;
  private String issendcoach;
  private String issendwxevaluate;
  private String grades;
  private String reviews;


}
