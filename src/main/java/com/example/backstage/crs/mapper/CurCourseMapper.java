package com.example.backstage.crs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CurCourseMapper {
    double getTotalAttendance(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    double getGroupClassAttendance(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("cid") String cid);
    double getMonthlyAttendance(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coursetime") String coursetime,@Param("courseendtime") String courseendtime);
    double getConversionRateOfGroupLessons(@Param("coursetype") String coursetype,@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    int getNumberOfLessonsGroupLessons(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    int getNumberOfPrivateLessons(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);

}
