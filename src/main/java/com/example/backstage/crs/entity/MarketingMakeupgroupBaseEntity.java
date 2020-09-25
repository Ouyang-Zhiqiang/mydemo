package com.example.backstage.crs.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MarketingMakeupgroupBaseEntity {

  private long groupid;
  private double membershipcardprice;
  private double grouppurchaseprice;
  private long cardid;
  private String cardname;
  private long groupsize;
  private long grouptime;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date effectivestartdate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date effectiveenddate;
  private String grouptitle;
  private String groupdescription;
  private String logoid;
  private String resurl;
  private String sharetitle;
  private String sharesubtitle;
  private String sharesmallpictures;
  private String sharesmallpicturesurl;
  private long state;
  private long isremoved;
  private Date createdon;
  private String createdby;
  private String createdname;
  private String createdip;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date lastedon;
  private String lastedby;
  private String lastedname;
  private String lastedip;
  private String total;
  private String actype;

}
