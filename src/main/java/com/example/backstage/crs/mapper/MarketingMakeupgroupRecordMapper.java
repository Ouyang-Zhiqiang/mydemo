package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.MarketingMakeupgroupBaseEntity;
import com.example.backstage.crs.entity.MarketingMakeupgroupRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface MarketingMakeupgroupRecordMapper {
    MarketingMakeupgroupRecordEntity selectGroupid(@Param("groupid") Long groupid);

    Integer selectNumberInGroup(String number);

    int insertMarketingMakeupgroupRecord(MarketingMakeupgroupRecordEntity makeupgroupRecordEntity);

    List<MarketingMakeupgroupRecordEntity> selectMakeupgroupBaseByGroupNumber(String groupnumber);

    int updateStateByGroupNumber(@Param("groupnumber") String groupnumber, @Param("paymentstatus") Integer paystamentIngrounp);

    List<MarketingMakeupgroupRecordEntity> selectMakeupgroupBaseRecord(@Param("limit") Integer limit, @Param("pages") Integer pages, @Param("groupid") Integer groupid);

    int selectMakeupgroupTotal(@Param("groupid") Integer groupid);

    List<MarketingMakeupgroupRecordEntity> selectMakeupgroupBaseRecordByEffectiveenddate();

    MarketingMakeupgroupBaseEntity selectmarketingMakeupgroupBaseById(int groupid);

    int updateStateByRecordId(@Param("groupnumber") String groupnumber, @Param("paymentstatus") Integer paystamentIngrounp);

    int updateStatusByRecordid(MarketingMakeupgroupRecordEntity marketingMakeupgroupRecordEntity);
}
