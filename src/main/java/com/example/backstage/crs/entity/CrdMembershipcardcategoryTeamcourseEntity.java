package com.example.backstage.crs.entity;

import lombok.Data;

import java.util.Date;

@Data
public class CrdMembershipcardcategoryTeamcourseEntity {

  private long tcid;
  private long cardid;
  private long courseid;
  private String coursename;
  private long constimes;
  private long preferconstimes;
  private long state;
  private String remarks;
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
