package com.example.backstage.crs.entity;
import lombok.Data;

import java.util.Date;

@Data
public class LogMobileMetaEntity {
    /** ===ILvYou_Api_Mobile public.log_mobile_meta=== **/

    private Long id;
    private String platform;
    private String mobilenum;
    private String msg;
    private String localip;
    private String longitude;
    private String latitude;
    private Date createdon;
}
