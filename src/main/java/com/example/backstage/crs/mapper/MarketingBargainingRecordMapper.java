package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.MarketingBargainingRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MarketingBargainingRecordMapper {
    int setMarketingBargainingRecord(MarketingBargainingRecordEntity m);
}
