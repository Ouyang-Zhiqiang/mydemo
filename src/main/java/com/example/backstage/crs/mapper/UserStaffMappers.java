package com.example.backstage.crs.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Mapper
@Repository
public interface UserStaffMappers {
    List<Map<String,Object>> isStaff(String userid);
}
