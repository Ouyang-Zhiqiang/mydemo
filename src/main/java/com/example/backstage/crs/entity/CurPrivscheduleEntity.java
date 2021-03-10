package com.example.backstage.crs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CurPrivscheduleEntity {

  private long scheduleid;
  private long storeid;
  private String storename;
  private long courseid;
  private String coursename;
  private Date scheduledate;
  private Date schedulebegin;
  private Date scheduleend;
  private long intervaltime;
  private long reservablenumber;
  private long reservednumber;
  private long signednumber;
  private String isremoved;
  private Date createdon;
  private String createdby;
  private String createdname;
  private String createdip;
  private Date lastedon;
  private String lastedby;
  private String lastedname;
  private String lastedip;




}
