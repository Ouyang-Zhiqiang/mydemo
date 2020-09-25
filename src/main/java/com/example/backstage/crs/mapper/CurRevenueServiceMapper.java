package com.example.backstage.crs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

@Mapper
public interface CurRevenueServiceMapper {
    Map<String, Object> getRevenuetotal(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    Map<String, Object> getCourseRevenuetotal(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    Map<String, Object> getCourseRevenueTotalFirst(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    Map<String, Object> getCourseRevenueTotalContinue(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    Map<String, Object> getSingleSectionAveragePrice(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("coursetype")String coursetype);
    Map<String, Object> getSubcardMoneyall(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("cardtype")String cardtype);
    double getAverageunit(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    double getAverageUnitPrice(@Param("CardType")String CardType,@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    Map<String, Object> getAveragePriceUnit(@Param("CourseType")String CourseType, @Param("endtime") String endtime, @Param("storeid") String storeid);
    Map<String, Object> getTotalAmountofGoods(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    Map<String, Object> getCustomerPrice(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    Map<String, Object> getGoodsnumber(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid);
    Map<String, Object> getSingleProductAmount(@Param("begintime") String begintime, @Param("endtime") String endtime, @Param("storeid") String storeid,@Param("sid") String sid,@Param("name") String name);

}
