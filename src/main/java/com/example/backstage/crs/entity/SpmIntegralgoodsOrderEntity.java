package com.example.backstage.crs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SpmIntegralgoodsOrderEntity {

  private long orderid;
  private long userid;
  private String username;
  private String userphone;
  private long goodscode;
  private String name;
  private long imgid;
  private String imgurl;
  private long salespoint;
  private long storeid;
  private String storename;
  private java.sql.Date usedvaliditybegin;
  private java.sql.Date usedvalidityend;
  private long salesstate;
  private long seqnum;
  private long opttype;
  private long state;
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
