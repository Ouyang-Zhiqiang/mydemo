package com.example.mydemo.pojo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class UserBase {
    private Long userid;

    private Long companyid;

    private Long storeid;

    private String tel;

    private String name;

    private String sex;

    private Long refuserid;

    private Long saleuserid;

    private String password;

    private String salt;

    private BigDecimal points;

    private String memgrade;

    private Short status;

    private String remarks;

    private Integer numberoflogins;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdon;

    private String createdby;

    private String createdname;

    private String createdip;

    private Date lastedon;

    private String lastedby;

    private String lastedname;

    private String lastedip;

    private Long inviterid;

    private Integer regfrom;

    private Date birthday;

    private String workaddress;

    private String homeaddress;

    private BigDecimal height;

    private BigDecimal weight;

    private BigDecimal bmi;

    private String fitnessgoal;

    private String fitnessexperience;

    private List<Cards> cardList;

    public List<Cards> getCardList() {
        return cardList;
    }

    public void setCardList(List<Cards> cardList) {
        this.cardList = cardList;
    }

    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    public Long getCompanyid() {
        return companyid;
    }

    public void setCompanyid(Long companyid) {
        this.companyid = companyid;
    }

    public Long getStoreid() {
        return storeid;
    }

    public void setStoreid(Long storeid) {
        this.storeid = storeid;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel == null ? null : tel.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex == null ? null : sex.trim();
    }

    public Long getRefuserid() {
        return refuserid;
    }

    public void setRefuserid(Long refuserid) {
        this.refuserid = refuserid;
    }

    public Long getSaleuserid() {
        return saleuserid;
    }

    public void setSaleuserid(Long saleuserid) {
        this.saleuserid = saleuserid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public BigDecimal getPoints() {
        return points;
    }

    public void setPoints(BigDecimal points) {
        this.points = points;
    }

    public String getMemgrade() {
        return memgrade;
    }

    public void setMemgrade(String memgrade) {
        this.memgrade = memgrade == null ? null : memgrade.trim();
    }

    public Short getStatus() {
        return status;
    }

    public void setStatus(Short status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks == null ? null : remarks.trim();
    }

    public Integer getNumberoflogins() {
        return numberoflogins;
    }

    public void setNumberoflogins(Integer numberoflogins) {
        this.numberoflogins = numberoflogins;
    }

    public Date getCreatedon() {
        return createdon;
    }

    public void setCreatedon(Date createdon) {
        this.createdon = createdon;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby == null ? null : createdby.trim();
    }

    public String getCreatedname() {
        return createdname;
    }

    public void setCreatedname(String createdname) {
        this.createdname = createdname == null ? null : createdname.trim();
    }

    public String getCreatedip() {
        return createdip;
    }

    public void setCreatedip(String createdip) {
        this.createdip = createdip == null ? null : createdip.trim();
    }

    public Date getLastedon() {
        return lastedon;
    }

    public void setLastedon(Date lastedon) {
        this.lastedon = lastedon;
    }

    public String getLastedby() {
        return lastedby;
    }

    public void setLastedby(String lastedby) {
        this.lastedby = lastedby == null ? null : lastedby.trim();
    }

    public String getLastedname() {
        return lastedname;
    }

    public void setLastedname(String lastedname) {
        this.lastedname = lastedname == null ? null : lastedname.trim();
    }

    public String getLastedip() {
        return lastedip;
    }

    public void setLastedip(String lastedip) {
        this.lastedip = lastedip == null ? null : lastedip.trim();
    }

    public Long getInviterid() {
        return inviterid;
    }

    public void setInviterid(Long inviterid) {
        this.inviterid = inviterid;
    }

    public Integer getRegfrom() {
        return regfrom;
    }

    public void setRegfrom(Integer regfrom) {
        this.regfrom = regfrom;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getWorkaddress() {
        return workaddress;
    }

    public void setWorkaddress(String workaddress) {
        this.workaddress = workaddress == null ? null : workaddress.trim();
    }

    public String getHomeaddress() {
        return homeaddress;
    }

    public void setHomeaddress(String homeaddress) {
        this.homeaddress = homeaddress == null ? null : homeaddress.trim();
    }

    public BigDecimal getHeight() {
        return height;
    }

    public void setHeight(BigDecimal height) {
        this.height = height;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    public BigDecimal getBmi() {
        return bmi;
    }

    public void setBmi(BigDecimal bmi) {
        this.bmi = bmi;
    }

    public String getFitnessgoal() {
        return fitnessgoal;
    }

    public void setFitnessgoal(String fitnessgoal) {
        this.fitnessgoal = fitnessgoal == null ? null : fitnessgoal.trim();
    }

    public String getFitnessexperience() {
        return fitnessexperience;
    }

    public void setFitnessexperience(String fitnessexperience) {
        this.fitnessexperience = fitnessexperience == null ? null : fitnessexperience.trim();
    }
}