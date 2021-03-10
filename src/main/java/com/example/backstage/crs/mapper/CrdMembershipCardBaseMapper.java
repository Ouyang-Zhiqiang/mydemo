package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.CrdMembershipcardBaseEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface CrdMembershipCardBaseMapper {

    List<String> selectCardnumberOnlyOne();

    List<String> selectUserNumberByToDay(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid, @Param("numberStr") String[] numberStr, @Param("today") String today);

    List<String> selectRenewed(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid, @Param("userArray") String[] userArray);

    List<String> selectRenewal(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid, @Param("times") Integer times
            , @Param("buytype") String buytype, @Param("bindingCardUsersArray") String[] bindingCardUsersArray);

    List<String> selectLeagueAndPrivateCourseProportion(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid, @Param("coursetype") String coursetype, @Param("leagueAndPrivateArray") String[] leagueAndPrivateArray);

    CrdMembershipcardBaseEntity selectCrdMembershipcardBaseByCardNo(String CardNo);

    int insertCrdMembershipcardBase(CrdMembershipcardBaseEntity crdMembershipcardBaseEntity);


    List<Map<String, Object>> selectMemberShipCardByNameAndCreatedOn(@Param("name") String name,@Param("datebegin")  String datebegin,@Param("dateend")  String dateend);

    Map<String, Object> selectCardByCardNo(String cardno);

    List<Map<String, Object>> selectCardListByStr(@Param("userid")String userid, @Param("coursetype")String coursetype,@Param("cid") String cid);

    int activateCard(CrdMembershipcardBaseEntity crdMembershipcardBaseEntity);
}
