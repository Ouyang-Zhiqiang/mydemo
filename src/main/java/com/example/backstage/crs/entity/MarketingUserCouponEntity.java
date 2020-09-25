package com.example.backstage.crs.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MarketingUserCouponEntity {

  private String mycouponid;
  private String couponid;
  private long userid;
  private long status;
  private boolean isremoved;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createdon;
  private String createdby;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date updatedon;
  private String updatedby;

}
