package com.example.backstage.crs.mapper;


import com.example.backstage.crs.entity.SpmIntegralgoodsBaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SPMIntegralGoodsBaseMapper {

    int insertSPMIntegralGoodsBase(SpmIntegralgoodsBaseEntity spmIntegralgoodsBaseEntity);

    int updateSPMIntegralGoodsBase(SpmIntegralgoodsBaseEntity spmIntegralgoodsBaseEntity);

    int deleteSPMIntegralGoodsBase(Integer goodscode);

    int updateSPMIntegralGoodsBaseSalesState(SpmIntegralgoodsBaseEntity spmIntegralgoodsBaseEntity);
}
