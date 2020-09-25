package com.example.backstage.crs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CrdMembershipcardUserEntity {

  private long userid;
  private String cardno;
  private Boolean ispoint;
  private String remarks;
  private long state;
  private Boolean isremoved;
  private Date createdon;
  private String createdby;
  private String createdname;
  private String createdip;
  private Date lastedon;
  private String lastedby;
  private String lastedname;
  private String lastedip;
  /*人数*/
  private Integer number;



}
