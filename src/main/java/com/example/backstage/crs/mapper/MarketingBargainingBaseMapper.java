package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.MarketingBargainingBaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MarketingBargainingBaseMapper {
    void setMarketingBargainingBase(MarketingBargainingBaseEntity m);

    void upMarketingBargainingBase(MarketingBargainingBaseEntity m);

    void deMarketingBargainingBase(@Param("bargainingid") long bargainingid, @Param("hostAddress") String hostAddress);

    void stopMarketingBargainingBase(@Param("bargainingid") long bargainingid, @Param("hostAddress") String hostAddress);

    MarketingBargainingBaseEntity selectMarketingBargainingBaseBybargainingid(int groupid);
}
