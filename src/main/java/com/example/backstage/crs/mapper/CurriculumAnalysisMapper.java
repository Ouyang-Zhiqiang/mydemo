package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.Testcost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.relational.core.sql.In;

import java.util.List;
import java.util.Map;

@Mapper
public interface CurriculumAnalysisMapper {
    Map<String, Object> sql1(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coachid") String coachid);
    Map<String, Object> sql2(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coachid") String coachid);
    Map<String, Object> sql3(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coachid") String coachid);
    Map<String, Object> sql4(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coachid") String coachid);
    Map<String, Object> sql5(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coachid") String coachid);
    List<Integer> sql6(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coachid") String coachid,@Param("i") int i);
    List<Integer> sql7(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coachid") String coachid,@Param("i") int i);
    List<Map> cpCoursereport(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("coachid") String coachid, @Param("storeid") String storeid,@Param("limit") String limit,@Param("page") String page);
    List<Map> ctCoursereport(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("coachid") String coachid, @Param("storeid") String storeid,@Param("limit") String limit,@Param("page") String page);
    int getNumberofreservations(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coachid") String coachid,@Param("cardtype") String cardtype);
    int getNumberofsignin(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coachid") String coachid,@Param("cardtype") String cardtype);
    int getNumberofgrouplessons(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coachid") String coachid);
    int getNumberofprivatelessons(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coachid") String coachid);
    List<Map> getAmountoflessonssoldpercard(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coachid") String coachid);
    List<Map> selectcost(@Param("fenbu") long fenbu);
    List<Map> selectcostall();
    void updatecost(Testcost testcost);
    List<Map> selectrevenue();
    List<Map> selectrevenueall();
    int getNumberofreservation(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coachid") String coachid);
    void bindingvenue(@Param("storeid") String storeid,@Param("userid") String userid);
    Map<String,Object> qy1(@Param("cardid") String cardid);
    Map<String,Object> qytype(@Param("cardid") String cardid);

    Integer qy2(@Param("cardno")String cardno,@Param("userid")String userid,@Param("timefee")String timefee,@Param("tid")String tid,@Param("cardid")String cardid);
    Integer qy3(@Param("newid")String newid,@Param("cardno")String cardno,@Param("buytype")String buytype,@Param("fee")String fee,@Param("storeid")String storeid
            ,@Param("storename")String storename,@Param("userid")String userid,@Param("tid")String tid,@Param("cardid")String cardid
    );
    Map<String,Object> selectstorename(@Param("userid") String userid);
    Integer qy4(@Param("cardno")String cardno,@Param("userid")String userid,@Param("timefee")String timefee,@Param("tid")String tid,@Param("cardid")String cardid);
    Integer qy5(@Param("newid")String newid,@Param("cardno")String cardno,@Param("buytype")String buytype,@Param("fee")String fee,@Param("storeid")String storeid
            ,@Param("storename")String storename,@Param("userid")String userid,@Param("tid")String tid,@Param("cardid")String cardid
    );
    Integer qy7(@Param("cardno")String cardno,@Param("userid") String userid);
    Map<String,Object> points(@Param("tid") String tid);
    Map<String,Object> qysalespoint(@Param("userid") String userid);
    Map<String,Object> storeidOrTel(@Param("userid") String userid);
    Integer logqy(@Param("logid")String logid,@Param("userid")String userid,@Param("currentpoints")String currentpoints,
                  @Param("points")String points,@Param("surpluspoints")String surpluspoints,@Param("remarks")String remarks,
                  @Param("createdon")String createdon,@Param("createdby")String createdby,@Param("createdname")String createdname,
                  @Param("createdip")String createdip,@Param("actionstate")String actionstate,@Param("changeaction")String changeaction);
    Integer qy6(@Param("tid")String tid,@Param("userid") String userid);
    Map<String,Object> userCard(@Param("carno") String carno);
    Map<String,Object> isSendOrNot(@Param("tmpcode") String tmpcode);
    Map<String,Object> tmpNotic(@Param("tmpcode")String tmpcode);
    Integer insertLogMobileMeta(@Param("newid")String newid,@Param("platform")String platform,@Param("mobilenum")String mobilenum,
    @Param("msg")String msg,@Param("localip") String localip,@Param("longitude")String longitude,@Param("latitude")String latitude);
    Integer insertLogMobileReceived(@Param("newid")String newid,@Param("platform")String platform,@Param("mobilenum")String mobilenum,
    @Param("msg")String msg,@Param("localip") String localip,@Param("longitude")String longitude,
    @Param("latitude")String latitude,@Param("thridplatform")String thridplatform,@Param("recivedstate")String recivedstate,
    @Param("recivedinfos")String recivedinfos);
    Map<String,Object> userStaffPhone1(@Param("rolename")String rolename);
    List<Map<String,Object>> userStaffPhone2(@Param("storeid")String storeid,@Param("roleid")String roleid);
    Map<String,Object> isSendOrNotByRole(@Param("tmpcode")String tmpcode);
}
