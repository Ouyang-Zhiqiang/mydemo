package com.example.backstage.crs.entity;


import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
public class MarketingOrderrecordEntity {

  private long orderrecordid;
  private long bindingstate;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date creadtime;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date lastedtime;
  private double ordermoney;
  private long category;
  private String remark;
  private String creadname;

}
