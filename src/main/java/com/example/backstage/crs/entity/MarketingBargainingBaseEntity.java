package com.example.backstage.crs.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MarketingBargainingBaseEntity {

  private long bargainingid;
  private double membershipcardprice;
  private double bargainingprice;
  private double bargainingminprice;
  private double bargainingmaxprice;
  private long bargainingnumber;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date effectivestartdate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date effectiveenddate;
  private String bargainingtitle;
  private String bargainingdescription;
  private String logoid;
  private String resurl;
  private String sharetitle;
  private String sharesubtitle;
  private String sharesmallpictures;
  private String sharesmallpicturesurl;
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
  private String cardname;
  private String cardid;
  private String actype;
}
