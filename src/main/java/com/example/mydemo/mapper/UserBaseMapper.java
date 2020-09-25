package com.example.mydemo.mapper;

import com.example.mydemo.pojo.UserBase;
import jdk.internal.dynalink.linker.LinkerServices;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserBaseMapper {
    int deleteByPrimaryKey(Long userid);

    int insert(UserBase record);

    int insertSelective(UserBase record);

    UserBase selectByPrimaryKey(Long userid);

    int updateByPrimaryKeySelective(UserBase record);

    int updateByPrimaryKey(UserBase record);

    List<UserBase> selectUsersAll();
}