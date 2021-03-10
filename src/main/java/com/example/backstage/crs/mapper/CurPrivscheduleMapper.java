package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.CurPrivscheduleEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CurPrivscheduleMapper {
    int updateReservedNumberPrivate(long scheduleid,@Param("traineenum") int traineenum);
}
