package com.example.backstage.crs.entity;

import lombok.Data;

@Data
public class CurTeamscheduleEntity {

  private long scheduleid;
  private java.sql.Date scheduledate;
  private java.sql.Time schedulebegin;
  private java.sql.Time scheduleend;
  private long storeid;
  private String storename;
  private long courseid;
  private String coursename;
  private long coachid;
  private String coachname;
  private long reservablenumber;
  private long reservednumber;
  private String isopened;
  private long signednumber;
  private String isremoved;
  private java.sql.Timestamp createdon;
  private String createdby;
  private String createdname;
  private String createdip;
  private java.sql.Timestamp lastedon;
  private String lastedby;
  private String lastedname;
  private String lastedip;
  private long cid;
  private long sid;
  private long courseduration;
  private long minpeople;



}
