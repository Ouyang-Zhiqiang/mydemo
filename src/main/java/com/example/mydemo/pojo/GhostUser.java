package com.example.mydemo.pojo;

import java.math.BigDecimal;
import java.util.Date;

public class GhostUser {
    private String uuid;

    private String name;

    private String phone;

    private BigDecimal deleteflag;

    private Date dateline;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid == null ? null : uuid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public BigDecimal getDeleteflag() {
        return deleteflag;
    }

    public void setDeleteflag(BigDecimal deleteflag) {
        this.deleteflag = deleteflag;
    }

    public Date getDateline() {
        return dateline;
    }

    public void setDateline(Date dateline) {
        this.dateline = dateline;
    }
}