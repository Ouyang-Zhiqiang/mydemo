package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.GetUsersEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface NewMapper {
    List<Map<Object,Object>> getStoreIdAll();
    List<Map<Object,Object>> login(@Param("tel")String tel);
    List<Map<Object,Object>> getCoachAll();
    List<Map<Object,Object>> getFunction();
    List<Map<Object,Object>> getCourseAll();
    List<Map<Object,Object>> getTeamschedule(@Param("storeid")String storeid,@Param("coachid")String coachid,
                                             @Param("day1")String day1,@Param("day2")String day2
    );
    List<Map<Object,Object>> getCurTeamStrength(@Param("cid")String cid,@Param("strenth")String strenth);
    void setCurTeamCourseBase(com.example.backstage.crs.util.Param param);
    void setCurTeamSchedule(com.example.backstage.crs.util.Param param);
    void upreservablenumber(@Param("reservablenumber") String reservablenumber,@Param("scheduleid")String scheduleid);
    Map<Object,Object> getReservablenumber(@Param("scheduleid")String scheduleid);
    Map<Object,Object> getRoleid(@Param("tel")String tel);
    Map<Object,Object> setPartsjson(@Param("scheduleid")String scheduleid);
    List<Map<Object,Object>> getXiaoshou();
    void deletetk(@Param("scheduleid")String scheduleid);
    void quxiaoyuyue1(@Param("traineenum")String traineenum,@Param("cardno")String cardno);
    void quxiaoyuyue2(@Param("scheduleid")String scheduleid);
    void quxiaoyuyue3(@Param("traineenum")String traineenum,@Param("scheduleid")String scheduleid);
    void quxiaoyuyuesj(@Param("traineenum")String traineenum,@Param("scheduleid")String scheduleid);
    void signed1(@Param("ordid")String ordid);
    void signed2(@Param("traineenum")String traineenum,@Param("scheduleid")String scheduleid);
    void signedsj(@Param("scheduleid")String scheduleid);
    List<Map<Object,Object>> setUser(@Param("name")String name);
    List<Map<Object,Object>> getAllcourse();
    List<Map<Object,Object>> getStrenth(@Param("cid")String cid,@Param("strengthgrade")String strengthgrade,
    @Param("limit")String limit,@Param("page")String page);
    List<Map<Object,Object>> getUnits();
    List<Map<Object,Object>> getCategory();
    List<Map<Object,Object>> getStrength();
    List<Map<Object,Object>> getAims();
    List<Map<Object,Object>> getParts();
    List<Map<Object,Object>> getactionlibrary(@Param("category")String category,@Param("strength")String strength);
    void setStrength(com.example.backstage.crs.util.Param param);
    void DeleteStrength(@Param("sid")String sid);
    void UpdateStrength(com.example.backstage.crs.util.Param param);
    List<Map<Object,Object>> getNumberofreservations(@Param("storeid")String storeid,
                                                     @Param("day1")String day1,@Param("day2")String day2);
    List<Map<Object,Object>> getPreCourse();
    void setCurprivschedule(com.example.backstage.crs.util.Param param);
    List<Map<Object,Object>> getPivateuser(@Param("scheduleid")String scheduleid);
    void deletesj(@Param("scheduleid")String scheduleid);
    List<Map<Object,Object>> getAllhyk();
    List<Map<Object,Object>> getUsers(GetUsersEntity getUsersEntity);
    List<Map<Object,Object>> TimedTasktk(@Param("date")String date,@Param("time")String time);
    List<Map<Object,Object>> TimedTasksj(@Param("date")String date,@Param("time")String time);
    void setlog(@Param("logid")String logid,@Param("message")String message);
    void updateUser(com.example.backstage.crs.util.Param param);
    void DisableUser(com.example.backstage.crs.util.Param param);
    void setLogUserPoints(com.example.backstage.crs.util.Param param);
    void updatePoints(com.example.backstage.crs.util.Param param);
    void updateMemgrade(com.example.backstage.crs.util.Param param);
}
