package com.example.backstage.crs.mapper;

import com.example.backstage.crs.entity.TjTestEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface TjTestMapper {
    List<Map<String, String>> selectAllTest();

    int updateAllTest(List<TjTestEntity> tjTestEntity);
}
