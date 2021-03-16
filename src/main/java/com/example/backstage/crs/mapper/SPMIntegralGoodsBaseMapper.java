package com.example.backstage.crs.mapper;


import com.example.backstage.crs.entity.SpmIntegralgoodsBaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface SPMIntegralGoodsBaseMapper {

    int insertSPMIntegralGoodsBase(SpmIntegralgoodsBaseEntity spmIntegralgoodsBaseEntity);

    int updateSPMIntegralGoodsBase(SpmIntegralgoodsBaseEntity spmIntegralgoodsBaseEntity);

    int deleteSPMIntegralGoodsBase(Integer goodscode);

    int updateSPMIntegralGoodsBaseSalesState(SpmIntegralgoodsBaseEntity spmIntegralgoodsBaseEntity);

    int UpdateSpmGoodsOrder();

    List<Map<String, Object>> selectSpmGoodsOrderByStoreids(Long storeids);

    int insertApmInteralGoodsOrder(@Param("userid") Long userid, @Param("goodscode")Long goodscode);

    Map<String,Object> selectSpmIntegralGoodsByGoodcode(@Param("goodscode")Long goodscode);

    SpmIntegralgoodsBaseEntity selectAllSpmIntegralGoodsByGoodcode(@Param("goodscode")String goodscode);

    int updateIntegralGoodsPurchased(String goodscode);
}
