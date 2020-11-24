package com.example.backstage.crs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface CurCourseMapper {
    double getTotalAttendance(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    double getGroupClassAttendance(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("cid") String cid);
    double getMonthlyAttendance(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coursetime") String coursetime,@Param("courseendtime") String courseendtime);
    double getConversionRateOfGroupLessons(@Param("coursetype") String coursetype,@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    int getNumberOfLessonsGroupLessons(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    int getNumberOfPrivateLessons(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    int RegUserSendvcode(@Param("id")String id,@Param("num") String num,@Param("code") String code);
    List<Map> getcourseinformation(@Param("ScheduleId")String ScheduleId);
    List<Map> privatelessonschedule(@Param("begintime")String begintime,@Param("endtime")String endtime,@Param("storeid")String storeid,@Param("coachid")String coachid);
    Integer sijiaoyuyuerenshu(@Param("scheduleid")String scheduleid);
    int xiugaijifen(@Param("points")String points,@Param("userid")String userid);
    int goukasongjifen(@Param("userid")String userid,@Param("points")String points,@Param("remarks")String remarks,
    @Param("createdby")String createdby,@Param("createdname")String createdname,@Param("createdip")String createdip);
    List<Map> ticeliebiao(@Param("userid") String userid);
    int inserttice(@Param("userid")String userid,@Param("testtypeid")String testtypeid,@Param("testname")String testname,@Param("testunit")String testunit,
    @Param("testvalue")String testvalue,@Param("createdby")String createdby,@Param("createdname")String createdname);
    List<Map<String,Object>> xiaochengxukeyonghuiyanka(@Param("userid")String userid,@Param("scheduleid")String scheduleid);
    Map<String,Object> selectcardtime(@Param("cardno")String cardno);
    int deletetice(@Param("time")String time,@Param("userid")String userid);
    int updatetice(@Param("testvalue")String testvalue,@Param("lastedby")String lastedby,@Param("lastedname")String lastedname,@Param("userid")String userid,@Param("time")String time,@Param("testname")String testname);
    List<Map> selecttice(@Param("time")String time,@Param("userid")String userid);
    Map<String,Object> getopenid(@Param("userid")String userid);
    Map<String,Object> wxlogin(@Param("unionid")String unionid);
}
