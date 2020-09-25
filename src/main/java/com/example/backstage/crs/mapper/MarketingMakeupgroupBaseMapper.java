package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.MarketingMakeupgroupBaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface MarketingMakeupgroupBaseMapper {

    int insertMarketingMakeupgroupBase(MarketingMakeupgroupBaseEntity marketingMakeupgroupBaseEntity);

    int updateMarketingMakeupgroupBase(MarketingMakeupgroupBaseEntity marketingMakeupgroupBaseEntity);

    List<Map<String,String>> marketingMakeupgroupBaseMapperList(@Param("limit") Integer limit, @Param("pages")Integer pages);

    MarketingMakeupgroupBaseEntity selectMarketingMakeupgroupBaseEntityByGroupId(@Param("groupid") Long groupid);

    int selectNumberInGroup(@Param("recordid") Long recordid);

    int selectTotalList();

}
