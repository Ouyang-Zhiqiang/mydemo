package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.CrdMembershipcardcategoryBase;
import com.example.backstage.crs.entity.CrdMembershipcardcategoryTeamcourseEntity;
import com.example.backstage.crs.entity.CrdMembershipcardcategoryTypecardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface CrdMembershipcardcategoryBaseMapper {
    int insertCrdMembershipcardcategoryBase(CrdMembershipcardcategoryBase crdMembershipcardcategoryBase);

    int insertCrdMembershipCardCategoryTypeCard(List<CrdMembershipcardcategoryTypecardEntity> list);

    int insertCrdMembershipCardCategoryTeamCourse(List<CrdMembershipcardcategoryTeamcourseEntity> list1);

    CrdMembershipcardcategoryBase selectCrdMembershipcardcategoryBaseByCardId(Long cardid);
}
