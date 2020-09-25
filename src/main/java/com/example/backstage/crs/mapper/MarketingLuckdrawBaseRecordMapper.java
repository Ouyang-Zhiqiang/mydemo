package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.MarketingLuckdrawBaseRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MarketingLuckdrawBaseRecordMapper {
    void setMarketingLuckdrawBaseRecord(MarketingLuckdrawBaseRecordEntity m);

    void ReceiveAward(@Param("luckdrawid")int recordid, @Param("phone")String phone);
}
