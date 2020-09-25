package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.MarketingSeckillBaseRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MarketingSeckillBaseRecordMapper {
        int insertMarketingSeckillBaseRecord(MarketingSeckillBaseRecordEntity marketingSeckillBaseRecordEntity);

        int updateStatus(Map<String, Object> param);

        List<MarketingSeckillBaseRecordEntity> selectSeckillBaseRecord(@Param("limit") Integer limit, @Param("pages") Integer pages, @Param("groupid") Integer groupid);

        int selectSeckillBaseTotal();
}
