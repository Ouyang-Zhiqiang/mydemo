package com.example.backstage.crs.entity;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MarketingSeckillBaseEntity {

  private Integer seckillid;
  private String membershipcardprice;
  private String seckillprice;
  private long seckillnumber;
  private String cardid;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date  effectivestartdate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date  effectiveenddate;
  private String seckilltitle;
  private String seckilldescription;
  private long logoid;
  private String resurl;
  private String sharetitle;
  private String sharesubtitle;
  private long sharesmallpictures;
  private String sharesmallpicturesurl;
  private long state;
  private int isremoved;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createdon;
  private String createdby;
  private String createdname;
  private String createdip;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date  lastedon;
  private String lastedby;
  private String lastedname;
  private String lastedip;
  private String cardname;
  private String total;


}
