package com.example.backstage.crs.mapper;


import com.example.backstage.crs.entity.CrdMembershipcardUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CrdMembershipCardUserMapper {
    List<CrdMembershipcardUserEntity> selectCardNumberEveryOne(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid);
    List<CrdMembershipcardUserEntity> selectExpiredCardNumberEveryOne(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid, @Param("today") String today);

    int insertMembershipCardUser(CrdMembershipcardUserEntity crdMembershipcardUserEntity);
}
