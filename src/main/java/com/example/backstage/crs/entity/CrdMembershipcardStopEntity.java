package com.example.backstage.crs.entity;

import lombok.Data;

@Data
public class CrdMembershipcardStopEntity {

  private long stopid;
  private long userid;
  private String cardno;
  private long cardid;
  private String cardname;
  private long typeid;
  private long curtimes;
  private String stoptype;
  private java.sql.Timestamp begindate;
  private java.sql.Timestamp beginend;
  private String fee;
  private long payments;
  private String remarks;
  private java.sql.Timestamp createdon;
  private String createdby;
  private String createdname;
  private String createdip;


}
