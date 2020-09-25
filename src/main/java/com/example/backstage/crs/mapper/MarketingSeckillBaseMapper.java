package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.MarketingSeckillBaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MarketingSeckillBaseMapper {

    int insertMarketingSeckillBase(MarketingSeckillBaseEntity marketingSeckillBaseEntity);

    int updateMarketingSeckillBase(MarketingSeckillBaseEntity marketingSeckillBaseEntity);

    List<Map<String,String>> selectMarketingSeckillBaseList(@Param("limit") Integer limit, @Param("pages") Integer pages);

    int selectMarketingSeckillBaseCount();


    MarketingSeckillBaseEntity selectMarketingSeckillBaseBySeckillid(int groupid);
}
