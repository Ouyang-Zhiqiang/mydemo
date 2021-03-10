package com.example.backstage.crs.entity;

import lombok.Data;

@Data
public class GetUsersEntity {
    private String name;
    private String tel;
    private String cardid;
    private String saleuserid;
    private String storeid;
    private String status;
    private String limit;
    private String page;
}

