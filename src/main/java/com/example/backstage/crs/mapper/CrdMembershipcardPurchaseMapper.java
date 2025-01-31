package com.example.backstage.crs.mapper;


import com.example.backstage.crs.entity.CrdMemberShipcardPurchaseResult;
import com.example.backstage.crs.entity.CrdMembershipcardPurchaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CrdMembershipcardPurchaseMapper {


     List<CrdMembershipcardPurchaseEntity> GetAllListByPayWay(String storeId, String startDate, String endDate) ;

    int insertCrdMembershipCardPurchase(CrdMembershipcardPurchaseEntity crdMembershipcardPurchaseEntity);

    Map<String, Object> selectSellingfeeSumByStoreidAndSaleridAndDate(@Param("storeid") String storeid, @Param("salerid") String salerid, @Param("datebegin") String datebegin, @Param("dateend") String dateend);

    List<Map<String, Object>> selectMembershipcardStop(@Param("storeid") String storeid, @Param("salerid") String salerid, @Param("datebegin") String datebegin, @Param("dateend") String dateend);

    Map<String, Object> selectMembershipcardTransfer(@Param("storeid") String storeid, @Param("salerid") String salerid, @Param("datebegin") String datebegin, @Param("dateend") String dateend);

    List<Map<String, Object>> selectMembershipcardReconciliation(@Param("storeid") String storeid, @Param("salerid") String salerid, @Param("datebegin") String datebegin, @Param("dateend") String dateend);

    Integer selectStaticListCount(@Param("storeid") String storeid, @Param("salerid") String salerid, @Param("datebegin") String datebegin, @Param("dateend") String dateend,@Param("name")String name);

    List<Map<String, Object>> selectStaticList(@Param("storeid") String storeid, @Param("salerid") String salerid, @Param("datebegin") String datebegin, @Param("dateend") String dateend, @Param("limits") Integer limits, @Param("pages") Integer pages,@Param("name")String name);

    Integer selectStaticListBySaleridCount(@Param("storeid") String storeid, @Param("salerid") String salerid, @Param("datebegin") String datebegin, @Param("dateend") String dateend,@Param("name")String name);

    List<Map<String, Object>> selectStaticListBySalerid(@Param("storeid") String storeid, @Param("salerid") String salerid, @Param("datebegin") String datebegin, @Param("dateend") String dateend, @Param("limits") Integer limits, @Param("pages") Integer page,@Param("name")String name);

    List<Map<String, Object>> selectThirdaccount(String openid);

    List<CrdMembershipcardPurchaseEntity> GetAllListSpecialChar(@Param("storeId") String storeId, @Param("startDate")String startDate, @Param("endDate")String endDate);

    List<CrdMemberShipcardPurchaseResult> GetCharBar2(@Param("startDate")String startDate, @Param("endDate")String endDate, @Param("storeId") String storeId, @Param("salerId") String salerId,@Param("buyType") String buyType);
}
