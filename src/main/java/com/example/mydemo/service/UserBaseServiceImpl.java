package com.example.mydemo.service;

import com.example.mydemo.mapper.UserBaseMapper;
import com.example.mydemo.pojo.UserBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserBaseServiceImpl implements UserBaseService {

    @Resource
    UserBaseMapper userBaseMapper;

    @Override
    public UserBase findById(Long id) {
        return userBaseMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<UserBase> findAll() {
        return userBaseMapper.selectUsersAll();
    }
}
