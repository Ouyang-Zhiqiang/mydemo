package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.MarketingLuckdrawBaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MarketingLuckdrawBaseMapper {
    void setMarketingLuckdrawBase(MarketingLuckdrawBaseEntity m);
    void upMarketingLuckdrawBase(MarketingLuckdrawBaseEntity m);
    void deMarketingLuckdrawBase(@Param("luckdrawid") long luckdrawid, @Param("hostAddress") String hostAddress);
    void stopMarketingLuckdrawBase(@Param("luckdrawid") long luckdrawid, @Param("hostAddress") String hostAddress);


}
