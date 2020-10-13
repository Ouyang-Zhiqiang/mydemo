package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.Testcost;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

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
    List<Map> cpCoursereport(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("coachid") String coachid,@Param("limit") String limit,@Param("page") String page);
    List<Map> ctCoursereport(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("coachid") String coachid,@Param("limit") String limit,@Param("page") String page);
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
}
