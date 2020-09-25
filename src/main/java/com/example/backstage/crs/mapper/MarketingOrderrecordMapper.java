package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.MarketingOrderrecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MarketingOrderrecordMapper {
    int setMarketingOrderrecord(MarketingOrderrecordEntity marketingOrderrecordEntity);
}
