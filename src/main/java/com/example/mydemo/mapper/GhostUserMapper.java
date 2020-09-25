package com.example.mydemo.mapper;

import com.example.mydemo.pojo.GhostUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
public interface GhostUserMapper {
    int deleteByPrimaryKey(String uuid);

    int insert(GhostUser record);

    int insertSelective(GhostUser record);

    GhostUser selectByPrimaryKey(String uuid);

    int updateByPrimaryKeySelective(GhostUser record);

    int updateByPrimaryKey(GhostUser record);

}