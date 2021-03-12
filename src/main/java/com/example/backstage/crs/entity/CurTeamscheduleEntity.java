package com.example.backstage.crs.entity;

import lombok.Data;

import java.util.List;

@Data
public class CurTeamscheduleEntity {

  private String scheduleid;
  private java.sql.Date scheduledate;
  private java.sql.Time schedulebegin;
  private java.sql.Time scheduleend;
  private String storeid;
  private String storename;
  private String courseid;
  private String coursename;
  private String coachid;
  private String coachname;
  private long reservablenumber;
  private long reservednumber;
  private String isopened;
  private long signednumber;
  private String isremoved;
  private java.sql.Timestamp createdon;
  private String createdby;
  private String createdname;
  private String createdip;
  private java.sql.Timestamp lastedon;
  private String lastedby;
  private String lastedname;
  private String lastedip;
  private String cid;
  private long sid;
  private long courseduration;
  private long minpeople;
  private int counts;
  private List<OrdOrdercourseEntity> users;

  @Override
  public String toString() {
    return "CurTeamscheduleEntity{" +
            "scheduleid=" + scheduleid +
            ", scheduledate=" + scheduledate +
            ", schedulebegin=" + schedulebegin +
            ", scheduleend=" + scheduleend +
            ", storeid=" + storeid +
            ", storename='" + storename + '\'' +
            ", courseid=" + courseid +
            ", coursename='" + coursename + '\'' +
            ", coachid=" + coachid +
            ", coachname='" + coachname + '\'' +
            ", reservablenumber=" + reservablenumber +
            ", reservednumber=" + reservednumber +
            ", isopened='" + isopened + '\'' +
            ", signednumber=" + signednumber +
            ", isremoved='" + isremoved + '\'' +
            ", createdon=" + createdon +
            ", createdby='" + createdby + '\'' +
            ", createdname='" + createdname + '\'' +
            ", createdip='" + createdip + '\'' +
            ", lastedon=" + lastedon +
            ", lastedby='" + lastedby + '\'' +
            ", lastedname='" + lastedname + '\'' +
            ", lastedip='" + lastedip + '\'' +
            ", cid=" + cid +
            ", sid=" + sid +
            ", courseduration=" + courseduration +
            ", minpeople=" + minpeople +
            ", counts=" + counts +
            ", users=" + users +
            '}';
  }
}
