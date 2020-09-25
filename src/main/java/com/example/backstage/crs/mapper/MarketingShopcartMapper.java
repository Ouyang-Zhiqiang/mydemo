package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.MarketingShopcartEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MarketingShopcartMapper {
    void setMarketingShopcart(MarketingShopcartEntity marketingShopcart);
}
