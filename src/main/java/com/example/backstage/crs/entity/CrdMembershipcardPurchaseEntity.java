package com.example.backstage.crs.entity;


import lombok.Data;

import java.util.Date;

@Data
public class CrdMembershipcardPurchaseEntity {

  private long purchaseid;
  private long cardid;
  private String cardname;
  private String cardno;
  private String buytype;
  private long buytimes;
  private long points;
  private String sellingfee;
  private long payment;
  private long storeid;
  private String storename;
  private long salerid;
  private String salername;
  private Date cardbegin;
  private Date cardend;
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
  private Integer seckillid;
  private Integer groupid;
  private Integer bargainingid;
  private Integer luckdrawid;
  private Double Amount;
  public String DateTime;

}
