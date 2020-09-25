package com.example.backstage.crs.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MarketingLuckdrawBaseEntity {

  private long luckdrawid;
  private String luckdrawname;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date starttime;
  private String logoid;
  private String resurl;
  private String activityrules;
  private String luckdrawdescription;
  private String sharetitle;
  private String sharesmallpictures;
  private String sharesmallpicturesurl;
  private long winningtimes;
  private long state;
  private long isremoved;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createdon;
  private String createdby;
  private String createdname;
  private String createdip;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date lastedon;
  private String lastedby;
  private String lastedname;
  private String lastedip;
  private String awardsetting;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date endtime;
  private String actype;
}
