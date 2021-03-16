package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.CardBaseEntity;
import com.example.backstage.crs.entity.GetUsersEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface NewMapper {
    List<Map<Object,Object>> getStoreIdAll();
    List<Map<Object,Object>> login(@Param("tel")String tel);
    List<Map<Object,Object>> getCoachAll();
    List<Map<Object,Object>> getFunction();
    List<Map<Object,Object>> getCourseAll();
    List<Map<Object,Object>> getTeamschedule(@Param("storeid")String storeid,@Param("coachid")String coachid,
                                             @Param("day1")String day1,@Param("day2")String day2
    );
    List<Map<Object,Object>> getCurTeamStrength(@Param("cid")String cid,@Param("strenth")String strenth);
    void setCurTeamCourseBase(com.example.backstage.crs.util.Param param);
    void setCurTeamSchedule(com.example.backstage.crs.util.Param param);
    void upreservablenumber(@Param("reservablenumber") String reservablenumber,@Param("scheduleid")String scheduleid);
    Map<Object,Object> getReservablenumber(@Param("scheduleid")String scheduleid);
    Map<Object,Object> getRoleid(@Param("tel")String tel);
    Map<Object,Object> setPartsjson(@Param("scheduleid")String scheduleid);
    List<Map<Object,Object>> getXiaoshou();
    void deletetk(@Param("scheduleid")String scheduleid);
    void quxiaoyuyue1(@Param("traineenum")String traineenum,@Param("cardno")String cardno);
    void quxiaoyuyue2(@Param("ordid")String ordid);
    void quxiaoyuyue3(@Param("traineenum")String traineenum,@Param("scheduleid")String scheduleid);
    void quxiaoyuyuesj(@Param("traineenum")String traineenum,@Param("scheduleid")String scheduleid);
    void signed1(@Param("ordid")String ordid);
    void signed2(@Param("traineenum")String traineenum,@Param("scheduleid")String scheduleid);
    void signedsj(@Param("scheduleid")String scheduleid);
    List<Map<Object,Object>> setUser(@Param("name")String name);
    List<Map<Object,Object>> getAllcourse();
    List<Map<Object,Object>> getStrenth(@Param("cid")String cid,@Param("strengthgrade")String strengthgrade,
    @Param("limit")String limit,@Param("page")String page);
    List<Map<Object,Object>> getUnits();
    List<Map<Object,Object>> getCategory();
    List<Map<Object,Object>> getStrength();
    List<Map<Object,Object>> getAims();
    List<Map<Object,Object>> getParts();
    List<Map<Object,Object>> getactionlibrary(@Param("category")String category,@Param("strength")String strength);
    void setStrength(com.example.backstage.crs.util.Param param);
    void DeleteStrength(@Param("sid")String sid);
    void UpdateStrength(com.example.backstage.crs.util.Param param);
    List<Map<Object,Object>> getNumberofreservations(@Param("storeid")String storeid,
                                                     @Param("day1")String day1,@Param("day2")String day2);
    List<Map<Object,Object>> getPreCourse();
    void setCurprivschedule(com.example.backstage.crs.util.Param param);
    List<Map<Object,Object>> getPivateuser(@Param("scheduleid")String scheduleid);
    void deletesj(@Param("scheduleid")String scheduleid);
    List<Map<Object,Object>> getAllhyk();
    List<Map<Object,Object>> getUsers(GetUsersEntity getUsersEntity);
    List<Map<Object,Object>> TimedTasktk(@Param("date")String date,@Param("time")String time);
    List<Map<Object,Object>> TimedTasksj(@Param("date")String date,@Param("time")String time);
    void setlog(@Param("logid")String logid,@Param("message")String message);
    void updateUser(com.example.backstage.crs.util.Param param);
    void DisableUser(com.example.backstage.crs.util.Param param);
    void setLogUserPoints(com.example.backstage.crs.util.Param param);
    void updatePoints(com.example.backstage.crs.util.Param param);
    void updateMemgrade(com.example.backstage.crs.util.Param param);
    Map<Object,Object> getImage(@Param("userid")String userid);
    Map<Object,Object> getAmount(@Param("userid")String userid);
    Map<Object,Object> getCourseAmount(@Param("userid")String userid);
    List<Map<Object,Object>> getCardByUserid(@Param("userid")String userid);
    List<Map<Object,Object>> getCardByUseridsx(@Param("userid")String userid);
    void setCrdMembershipcardPurchase(com.example.backstage.crs.util.Param param);
    void xuka(com.example.backstage.crs.util.Param param);
    void setCrdMembershipcardStop(com.example.backstage.crs.util.Param param);
    void tingka(com.example.backstage.crs.util.Param param);
    void huifu(String cardno);
    void setCrdMembershipCardReduce(com.example.backstage.crs.util.Param param);
    void kouci(com.example.backstage.crs.util.Param param);
    void setCrdMembershipCardChangePeriod(com.example.backstage.crs.util.Param param);
    void qixianbiangeng(com.example.backstage.crs.util.Param param);
    void setCrdMembershipCardReconciliation(com.example.backstage.crs.util.Param param);
    void pingzhang1(com.example.backstage.crs.util.Param param);
    void pingzhang2(com.example.backstage.crs.util.Param param);
    void bufenzhuanka(com.example.backstage.crs.util.Param param);
    void quanbuzhuanka(com.example.backstage.crs.util.Param param);
    void zhuankakouci(com.example.backstage.crs.util.Param param);
    void bangka1(com.example.backstage.crs.util.Param param);
    void bangka2(com.example.backstage.crs.util.Param param);
    void bangka3(com.example.backstage.crs.util.Param param);
    void setCrdMembershipCardTransfer(com.example.backstage.crs.entity.SetCrdMembershipCardTransferEntity s);
    List<Map<Object,Object>> getUserSalesFollowup(@Param("userid") String userid);
    List<Map<Object,Object>> getOrdOrdercourse(@Param("userid") String userid,@Param("limit") String limit
    ,@Param("page") String page);
    List<Map<Object,Object>> getCaozuojilu(@Param("userid") String userid,@Param("limit") String limit
            ,@Param("page") String page);
    void setUserSalesFollowup(@Param("userid") String userid,@Param("remarks") String remarks);
    List<Map<Object,Object>> getCrdMembershipCardCategoryTypeCard(@Param("cardid")String cardid);
    List<Map<Object,Object>> getAllCards(com.example.backstage.crs.util.Param param);
    List<Map<Object,Object>> getCrdMembershipCardCategoryTeamCourse(@Param("cardid")String cardid);
    void setImage(@Param("resid") String resid,@Param("resurl") String resurl);
    void xiugaihuiyuanka(CardBaseEntity cardBaseEntity);
    void xiugaikecheng(@Param("cardid")String cardid);
    void setCrdMembershipCardCategoryTeamCourse(@Param("tcid")String tcid,@Param("cardid")String cardid,
                                                @Param("courseid")String courseid,@Param("coursename")String coursename);
    void setCrdMembershipCardCategoryTypeCard(@Param("cardid")String cardid,@Param("cardtype")String cardtype,
                                                @Param("times")String times,@Param("fee")String fee,@Param("periodvalidity")String periodvalidity);
    void setCrdmembershipcardcategoryBase(CardBaseEntity cardBaseEntity);
    void xiugaihykzt(CardBaseEntity cardBaseEntity);
}
