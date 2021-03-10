package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.CurTeamscheduleEntity;
import com.example.backstage.crs.entity.OrdOrdercourseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CurTeamscheduleMapper {
    int updateReservedNumber(long scheduleid,@Param("traineenum") int traineenum);
}
