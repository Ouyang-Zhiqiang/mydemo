package com.example.backstage.crs.mapper;


import com.example.backstage.crs.entity.CrdMembershipcardcategoryTypecardEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface CrdMembershipcardcategoryTypecardMapper {

    CrdMembershipcardcategoryTypecardEntity selectMembershipCardCategoryByCardId(Long cardid);
}
