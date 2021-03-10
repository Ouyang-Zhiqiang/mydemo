package com.example.backstage.crs.entity;

import lombok.Data;

@Data
public class UserStaffEntity {

  private long userid;
  private String roleid;
  private String storeid;
  private String issupper;
  private String isapp;
  private String iscoach;
  private String intrhtml;
  private String intrtext;
  private long quantityofevaluation;
  private long status;
  private String remarks;
  private long coachseq;
  private java.sql.Timestamp createdon;
  private String createdby;
  private String createdname;
  private String createdip;
  private java.sql.Timestamp lastedon;
  private String lastedby;
  private String lastedname;
  private String lastedip;


}
