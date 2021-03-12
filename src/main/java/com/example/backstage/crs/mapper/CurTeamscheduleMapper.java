package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.CurTeamscheduleEntity;
import com.example.backstage.crs.entity.OrdOrdercourseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CurTeamscheduleMapper {
    int updateReservedNumber(long scheduleid,@Param("traineenum") int traineenum);

    List<CurTeamscheduleEntity> getAllTeam(com.example.backstage.crs.util.Param param);

    int getAllTeamCounts(com.example.backstage.crs.util.Param param);

    int getAllPrivateCounts(com.example.backstage.crs.util.Param param);

    List<CurTeamscheduleEntity> getAllPrivate(com.example.backstage.crs.util.Param param);

    int cancelTeam(@Param("scheduleid") String scheduleid, @Param("traineenum")String traineenum);
}
