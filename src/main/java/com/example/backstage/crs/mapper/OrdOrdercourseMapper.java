package com.example.backstage.crs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface OrdOrdercourseMapper {
    Map<String,Object> selectLeagueNubmerByN(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid, @Param("number") String n);

    Map<String,Object> selectPrivateNubmerByN(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid, @Param("number") String n);

    Map<String,Object> slectTotalMoneyByMoney(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid, @Param("money") String money);

    List<Map<String,Object>> selectCourseNumber(@Param("CourseDatestart") String CourseDatestart, @Param("CourseDateend") String CourseDateend, @Param("storeid") String storeid);

    Map<String, Object> selectOrderCourseByOrdid(@Param("ordid") Long ordid);

    List<Long> selectOnCourseThreeMonth(@Param("fristMonth")String dateStr, @Param("twoMonth")String lastOneMonth, @Param("threeMonth")String lastTwoMonth);

    int updateReduce(@Param("useridArray") Long[] useridArray);

}
