package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.SpmIntegralgoodsOrderEntity;
import com.example.backstage.crs.entity.SpmIntegralgoodsOrderExchangelogEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface SpmIntegralgoodsOrderExchangelogMapper {
    SpmIntegralgoodsOrderEntity selectSpmIntegralgoodsOrderByOrderid(Long orderId);

    int Insert(SpmIntegralgoodsOrderExchangelogEntity slmodel);

    int Update(SpmIntegralgoodsOrderEntity detail);
}
