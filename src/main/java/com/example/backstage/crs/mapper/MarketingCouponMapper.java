package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.MarketingCouponEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MarketingCouponMapper {
    void setMarketingCoupon(MarketingCouponEntity marketingCouponEntity);
    void upMarketingCoupon(MarketingCouponEntity marketingCouponEntity);
}
