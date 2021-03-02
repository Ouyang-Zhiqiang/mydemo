package com.example.backstage.crs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class SpmIntegralgoodsBaseEntity {

  private long goodscode;
  private String name;
  private long imgid;
  private String imgurl;
  private long salespoint;
  private long storeid;
  private String storename;
  private Date usedvaliditybegin;
  private Date usedvalidityend;
  private long salesstate;
  private long seqnum;
  private Boolean isremoved;
  private Date createdon;
  private String createdby;
  private String createdname;
  private String createdip;
  private Date lastedon;
  private String lastedby;
  private String lastedname;
  private String lastedip;
  private Integer stock;
  private Integer purchased;
  private Integer totalpurchased;

  /*运动馆数组*/
  String store;

}
