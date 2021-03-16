package com.example.backstage.crs.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class CardBaseEntity {
    private String resurl;
    private String isfree;
    private String cardtype;
    private String coursetype;
    private String cardname;
    private String cardid;
    private String istransfer;
    private String ismulmem;
    private String isonlinebuy;
    private String isopenedbyfirst;
    private String storesjson;
    private List<Map> courseId;
    private List<Map> tidu;
    private String logid;
    private String state;
}
