package com.example.backstage.crs.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class SpmIntegralgoodsOrderExchangelogEntity {

  private long logid;
  private String orderid;
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
  private Date usedvaliditybegin;
  private Date usedvalidityend;
  private long salesstate;
  private long seqnum;
  private long opttype;
  private long state;
  private long logstate;
  private String isremoved;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createdon;
  private String createdby;
  private String createdname;
  private String createdip;


}
