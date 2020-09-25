package com.example.backstage.crs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CrdMembershipcardcategoryTypecardEntity {

  private long tid;
  private long cardid;
  private String cardtype;
  private long times;
  private Double fee;
  private long periodvalidity;
  private String preferintros;
  private long points;
  private long state;
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


}
