package com.example.backstage.crs.mapper;

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


}
