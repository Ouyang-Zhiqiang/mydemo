package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.MarketingUserCouponEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MarketingUserCouponMapper {
    void setMarketingUserCoupon(MarketingUserCouponEntity marketingUserCoupon);
}
