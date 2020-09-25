package com.example.backstage.crs.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MarketingShopcartEntity {
  private String shopcartid;
  private long cardid;
  private long userid;
  private long cardnumber;
  private boolean isremoved;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date createdon;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date updatedon;

}
