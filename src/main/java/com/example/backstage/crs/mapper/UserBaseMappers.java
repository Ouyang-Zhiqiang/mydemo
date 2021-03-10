package com.example.backstage.crs.mapper;


import com.example.backstage.crs.entity.UserBaseEntity;
import com.example.mydemo.pojo.UserBase;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserBaseMappers {
    List<Map<String,Object>> selectNumberBySex(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid);

    List<String> selectOnylyIsfreePeopleNumber(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid);

    List<Map<String, Object>> selectIsfreeTrue(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid, @Param("idArray") String[] idArray);

    List<String> selectMoreThanOneClassNumber(@Param("CourseDatestart") String courseDatestart, @Param("CourseDateend") String courseDateend, @Param("storeid") String storeid);

    List<String> selectMoreThanTwice(@Param("CourseDatestart") String courseDatestart, @Param("CourseDateend") String courseDateend, @Param("storeid") String storeid);

    List<Map<String, Object>> selectTotalClass(@Param("CourseDatestart") String courseDatestart, @Param("CourseDateend") String courseDateend, @Param("storeid") String storeid);

    List<String> selectBuyCardNumber(@Param("CourseDatestart") String courseDatestart, @Param("CourseDateend") String courseDateend, @Param("storeid") String storeid, @Param("numberStr") String[] numberStr);

    List<String> selectsumNumber(@Param("CourseDatestart") String courseDatestart, @Param("CourseDateend") String courseDateend, @Param("storeid") String storeid);

    List<String> selectNaturalVisitNumber(@Param("CourseDatestart") String courseDatestart, @Param("CourseDateend") String courseDateend, @Param("storeid") String storeid, @Param("sourcetype") Integer sourcetype);

    List<String> selectvisitNumber(@Param("CourseDatestart") String courseDatestart, @Param("CourseDateend") String courseDateend, @Param("storeid") String storeid);

    Integer selectNaturalVisitNumberCount(@Param("CourseDatestart") String courseDatestart, @Param("CourseDateend") String courseDateend, @Param("storeid") String storeid, @Param("sourcetype") Integer sourcetype);

    List<String> selectvisitNumberNotDate(@Param("storeid") String storeid);

    List<Map<String, Object>> selectIsfreeTrueNotDate(@Param("storeid") String storeid, @Param("numberStr") String[] numberStr);

    List<Map<String, Object>> selectIsfreeTrueByCardNumber(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid, @Param("idArray") String[] idArray);

    String selectUserIdByPhone(String tel);


    List<UserBaseEntity> selectClassNumberOfMonth(String dateStr);

    int updateClassNumber(Long userid);

    List<UserBaseEntity> selectUserByInteger(Long[] longs);

    int updateClassNumberNotMemgrade(Long userid);

    int updateClassNumberNotClassnumber(long userid);

    List<Map<String, Object>> userByNameAndTel(@Param("name") String name, @Param("tel") String tel);
}
