package com.example.mydemo.service;

import com.example.mydemo.pojo.UserBase;

import java.util.List;

public interface UserBaseService {

    public UserBase findById(Long id);

    public List<UserBase> findAll();

}
