package com.example.backstage.crs.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.example.backstage.crs.entity.GetUsersEntity;
import com.example.backstage.crs.entity.SetCrdMembershipCardTransferEntity;
import com.example.backstage.crs.mapper.NewMapper;
import com.example.backstage.crs.service.AppletService;
import com.example.backstage.crs.util.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/web/new")
public class NewController {
    @Autowired
    private NewMapper newMapper;
    @RequestMapping("/getStoreIdAll")
    @ResponseBody
    public String getStoreIdAll(){
        return JSON.toJSONString(newMapper.getStoreIdAll());
    }
    @RequestMapping("/getCoachAll")
    @ResponseBody
    public String getCoachAll(){
        return JSON.toJSONString(newMapper.getCoachAll());
    }
    @RequestMapping("/getFunction")
    @ResponseBody
    public String getFunction(){
        return JSON.toJSONString(newMapper.getFunction());
    }
    @RequestMapping("/getCourseAll")
    @ResponseBody
    public String getCourseAll(){
        return JSON.toJSONString(newMapper.getCourseAll());
    }
    @RequestMapping("/login")
    @ResponseBody
    public String login(String tel){
        return JSON.toJSONString(newMapper.login(tel));
    }
    @RequestMapping("/getTeamschedule")
    @ResponseBody
    public String getTeamschedule(Param param){
        List<Map<Object, Object>> teamschedule = newMapper.getTeamschedule(param.getStoreid(), param.getCoachid(),
                param.getDay1(), param.getDay2());
        return JSON.toJSONStringWithDateFormat(teamschedule, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
    }
    @RequestMapping("/getCurTeamStrength")
    @ResponseBody
    public String getCurTeamStrength(Param param){
        return JSON.toJSONString(newMapper.getCurTeamStrength(param.getCid(),param.getStrenth()));
    }
    @RequestMapping("/setCurTeamCourseBase")
    @ResponseBody
    public String setCurTeamCourseBase(Param param){
        newMapper.setCurTeamCourseBase(param);
        newMapper.setCurTeamSchedule(param);
        return "ok";
    }
    @RequestMapping("/upreservablenumber")
    @ResponseBody
    public String upreservablenumber(Param param){
        newMapper.upreservablenumber(param.getReservablenumber(),param.getScheduleid());
        return "ok";
    }
    @RequestMapping("/getReservablenumber")
    @ResponseBody
    public String getReservablenumber(Param param){
        return JSON.toJSONString(newMapper.getReservablenumber(param.getScheduleid()));
    }
    @RequestMapping("/getRoleid")
    @ResponseBody
    public String getRoleid(String tel){
        return JSON.toJSONString(newMapper.getRoleid(tel));
    }
    @RequestMapping("/setPartsjson")
    @ResponseBody
    public String setPartsjson(String scheduleid){
        return JSON.toJSONString(newMapper.setPartsjson(scheduleid));
    }
    @RequestMapping("/getXiaoshou")
    @ResponseBody
    public String getXiaoshou(){
        return JSON.toJSONString(newMapper.getXiaoshou());
    }
    @RequestMapping("/deletetk")
    @ResponseBody
    public String deletetk(Param param){
        newMapper.deletetk(param.getScheduleid());
        return "ok";
    }
    @RequestMapping("/cancelReservation")
    @ResponseBody
    public String cancelReservation(Param param){
        System.err.println(param);
        if(param.getCardtype().equals("S")){
            newMapper.quxiaoyuyue1(param.getTraineenum(),param.getCardno());
        }
        return "ok";
    }
    @RequestMapping("/cancelReservation2")
    @ResponseBody
    public String cancelReservation2(Param param){
        newMapper.quxiaoyuyue2(param.getOrdid());
        newMapper.quxiaoyuyue3(param.getTraineenum(),param.getScheduleid());
        return "ok";
    }
    @RequestMapping("/cancelReservation3")
    @ResponseBody
    public String cancelReservation3(Param param){
        newMapper.quxiaoyuyue2(param.getScheduleid());
        newMapper.quxiaoyuyuesj(param.getTraineenum(),param.getScheduleid());
        return "ok";
    }
    @RequestMapping("/signed")
    @ResponseBody
    public String signed(Param param){
        newMapper.signed1(param.getOrdid());
        newMapper.signed2(param.getTraineenum(),param.getScheduleid());
        return "ok";
    }
    @RequestMapping("/signedsj")
    @ResponseBody
    public String signedsj(Param param){
        newMapper.signed1(param.getOrdid());
        newMapper.signedsj(param.getScheduleid());
        return "ok";
    }
    @RequestMapping("/setUser")
    @ResponseBody
    public String setUser(String name){
        return JSON.toJSONString(newMapper.setUser(name));
    }
    @RequestMapping("/getAllcourse")
    @ResponseBody
    public String getAllcourse(){
        return JSON.toJSONString(newMapper.getAllcourse());
    }
    @RequestMapping("/getStrenth")
    @ResponseBody
    public String getStrenth(Param param){
        System.err.println(param);
        return JSON.toJSONString(newMapper.getStrenth(param.getCid(),param.getStrengthgrade(),param.getLimit(),param.getPage()));
    }
    @RequestMapping("/getUnits")
    @ResponseBody
    public String getUnits(){
        return JSON.toJSONString(newMapper.getUnits());
    }
    @RequestMapping("/getCategory")
    @ResponseBody
    public String getCategory(){
        return JSON.toJSONString(newMapper.getCategory());
    }
    @RequestMapping("/getStrength")
    @ResponseBody
    public String getStrength(){
        return JSON.toJSONString(newMapper.getStrength());
    }
    @RequestMapping("/getAims")
    @ResponseBody
    public String getAims(){
        return JSON.toJSONString(newMapper.getAims());
    }
    @RequestMapping("/getParts")
    @ResponseBody
    public String getParts(){
        return JSON.toJSONString(newMapper.getParts());
    }
    @RequestMapping("/getactionlibrary")
    @ResponseBody
    public String getactionlibrary(Param param){
        return JSON.toJSONString(newMapper.getactionlibrary(param.getCategory(),param.getStrength()));
    }
    @RequestMapping("/setStrength")
    @ResponseBody
    public String setStrength(Param param){
        newMapper.setStrength(param);
        return "ok";
    }
    @RequestMapping("/DeleteStrength")
    @ResponseBody
    public String DeleteStrength(String sid){
        newMapper.DeleteStrength(sid);
        return "ok";
    }
    @RequestMapping("/UpdateStrength")
    @ResponseBody
    public String UpdateStrength(Param param){
        newMapper.UpdateStrength(param);
        return "ok";
    }
    @RequestMapping("/getNumberofreservations")
    @ResponseBody
    public String getNumberofreservations(Param param){
        List<Map<Object, Object>> numberofreservations = newMapper.getNumberofreservations(param.getStoreid(), param.getDay1(), param.getDay2());
        return JSON.toJSONStringWithDateFormat(numberofreservations, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
    }
    @RequestMapping("/getPreCourse")
    @ResponseBody
    public String getPreCourse(){
        return JSON.toJSONString(newMapper.getPreCourse());
    }
    @RequestMapping("/setCurprivschedule")
    @ResponseBody
    public String setCurprivschedule(Param param){
        newMapper.setCurprivschedule(param);
        return "ok";
    }
    @RequestMapping("/getPivateuser")
    @ResponseBody
    public String getPivateuser(String scheduleid){
        List<Map<Object, Object>> pivateuser = newMapper.getPivateuser(scheduleid);
        return JSON.toJSONStringWithDateFormat(pivateuser, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
    }
    @RequestMapping("/deletesj")
    @ResponseBody
    public String deletesj(String scheduleid){
        newMapper.deletesj(scheduleid);
        return "ok";
    }
    @RequestMapping("/getAllhyk")
    @ResponseBody
    public String getAllhyk(){
        return JSON.toJSONString(newMapper.getAllhyk());
    }
    @RequestMapping("/getUsers")
    @ResponseBody
    public String getUsers(GetUsersEntity getUsersEntity){
        List<Map<Object, Object>> users = newMapper.getUsers(getUsersEntity);
        return JSON.toJSONStringWithDateFormat(users, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
    }
    @RequestMapping("/updateUser")
    @ResponseBody
    public String updateUser(Param param){
        newMapper.updateUser(param);
        return "ok";
    }
    @RequestMapping("/DisableUser")
    @ResponseBody
    public String DisableUser(Param param){
        newMapper.DisableUser(param);
        return "ok";
    }
    @RequestMapping("/updatePoints")
    @ResponseBody
    public String updatePoints(Param param){
        param.setLogid(String.valueOf(new Date().getTime()));
        newMapper.setLogUserPoints(param);
        if(param.getActionstate().equals("-")){
            param.setPoints(String.valueOf(Double.valueOf(param.getPoints())-(Double.valueOf(param.getPoints())*2)));
        }
        newMapper.updatePoints(param);
        return "ok";
    }
    @RequestMapping("/updateMemgrade")
    @ResponseBody
    public String updateMemgrade(Param param){
        newMapper.updateMemgrade(param);
        return "ok";
    }
    @RequestMapping("/getImage")
    @ResponseBody
    public String getImage(String userid){
        return JSON.toJSONString(newMapper.getImage(userid));
    }
    @RequestMapping("/getAmount")
    @ResponseBody
    public String getAmount(String userid){
        return JSON.toJSONString(newMapper.getAmount(userid));
    }
    @RequestMapping("/getCourseAmount")
    @ResponseBody
    public String getCourseAmount(String userid){
        return JSON.toJSONString(newMapper.getCourseAmount(userid));
    }
    @RequestMapping("/getCardByUserid")
    @ResponseBody
    public String getCardByUserid(String userid){
        List<Map<Object, Object>> cardByUserid = newMapper.getCardByUserid(userid);
        return JSON.toJSONStringWithDateFormat(cardByUserid, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);

    }
    @RequestMapping("/getCardByUseridsx")
    @ResponseBody
    public String getCardByUseridsx(String userid){
        List<Map<Object, Object>> cardByUserid = newMapper.getCardByUseridsx(userid);
        return JSON.toJSONStringWithDateFormat(cardByUserid, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
    }

    @RequestMapping("/xuka")
    @ResponseBody
    public String xuka(Param param){
        newMapper.xuka(param);
        newMapper.setCrdMembershipcardPurchase(param);
        return "ok";
    }
    @RequestMapping("/tingka")
    @ResponseBody
    public String tingka(Param param){
        newMapper.tingka(param);
        newMapper.setCrdMembershipcardStop(param);
        return "ok";
    }
    @RequestMapping("/huifu")
    @ResponseBody
    public String huifu(String cardno){
        newMapper.huifu(cardno);
        return "ok";
    }
    @RequestMapping("/kouci")
    @ResponseBody
    public String kouci(Param param){
        newMapper.kouci(param);
        newMapper.setCrdMembershipCardReduce(param);
        return "ok";
    }
    @RequestMapping("/qixianbiangeng")
    @ResponseBody
    public String qixianbiangeng(Param param){
        System.err.println(param);
        newMapper.qixianbiangeng(param);
        newMapper.setCrdMembershipCardChangePeriod(param);
        return "ok";
    }
    @RequestMapping("/pingzhang")
    @ResponseBody
    public String pingzhang(Param param){
        newMapper.setCrdMembershipCardReconciliation(param);
        newMapper.pingzhang1(param);
        newMapper.pingzhang2(param);
        return "ok";
    }
    @RequestMapping("/bufenzhuanka")
    @ResponseBody
    public String bufenzhuanka(Param param){
        newMapper.bufenzhuanka(param);
        newMapper.zhuankakouci(param);
        return "ok";
    }
    @RequestMapping("/quanbuzhuanka")
    @ResponseBody
    public String quanbuzhuanka(Param param){
        newMapper.quanbuzhuanka(param);
        newMapper.setCrdMembershipcardStop(param);
        newMapper.zhuankakouci(param);
        return "ok";
    }
    @RequestMapping("/bangka")
    @ResponseBody
    public String bangka(Param param){
        newMapper.bangka1(param);
        newMapper.bangka2(param);
        newMapper.bangka3(param);
        return "ok";
    }
    @RequestMapping("/setCrdMembershipCardTransfer")
    @ResponseBody
    public String setCrdMembershipCardTransfer(SetCrdMembershipCardTransferEntity s){
        newMapper.setCrdMembershipCardTransfer(s);
        return "ok";
    }
    @RequestMapping("/getUserSalesFollowup")
    @ResponseBody
    public String getUserSalesFollowup(String userid){
        List<Map<Object, Object>> userSalesFollowup = newMapper.getUserSalesFollowup(userid);
        return JSON.toJSONStringWithDateFormat(userSalesFollowup, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
    }
    @RequestMapping("/getOrdOrdercourse")
    @ResponseBody
    public String getOrdOrdercourse(Param param){
        List<Map<Object, Object>> userSalesFollowup = newMapper.getOrdOrdercourse(param.getUserid(),param.getLimit(),param.getPage());
        return JSON.toJSONStringWithDateFormat(userSalesFollowup, "yyyy-MM-dd HH:mm:ss", SerializerFeature.WriteDateUseDateFormat);
    }
    @RequestMapping("/getCaozuojilu")
    @ResponseBody
    public String getCaozuojilu(Param param){
        List<Map<Object, Object>> userSalesFollowup = newMapper.getCaozuojilu(param.getUserid(),param.getLimit(),param.getPage());
        return JSON.toJSONStringWithDateFormat(userSalesFollowup, "yyyy-MM-dd", SerializerFeature.WriteDateUseDateFormat);
    }
    @RequestMapping("/setUserSalesFollowup")
    @ResponseBody
    public String setUserSalesFollowup(Param param){
        newMapper.setUserSalesFollowup(param.getUserid(),param.getRemarks());
        return "ok";
    }
    @RequestMapping("/getCrdMembershipCardCategoryTypeCard")
    @ResponseBody
    public String getCrdMembershipCardCategoryTypeCard(String cardid){
        return JSON.toJSONString(newMapper.getCrdMembershipCardCategoryTypeCard(cardid));
    }
    @RequestMapping("/getAllCards")
    @ResponseBody
    public String getAllCards(Param param){
        return JSON.toJSONString(newMapper.getAllCards(param));
    }
    @RequestMapping("/getCrdMembershipCardCategoryTeamCourse")
    @ResponseBody
    public String getCrdMembershipCardCategoryTeamCourse(String cardid){
        return JSON.toJSONString(newMapper.getCrdMembershipCardCategoryTeamCourse(cardid));
    }
}
