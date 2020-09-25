package com.example.backstage.crs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CrdMembershipcardcategoryBase {

  private long cardid;
  private String cardname;
  private String cardtype;
  private long logoid;
  private String resurl;
  private Boolean istransfer;
  private Boolean ismulmem;
  private Boolean isonlinebuy;
  private Boolean isopenedbyfirst;
  private String coursetype;
  private Boolean isfree;
  private String labels;
  private Integer seqno;
  private String storesjson;
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
  private String cardnumber;
  /*梯度*/
  private String faqjson;
}
