package com.example.backstage.crs.service;

import com.example.backstage.crs.entity.MarketingSeckillBaseEntity;
import com.example.backstage.crs.entity.UserBaseEntity;
import com.example.backstage.crs.mapper.UserBaseMappers;
import com.example.mydemo.pojo.UserBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserBasesService {
    @Autowired
    private UserBaseMappers userBaseMappers;

    public List<UserBaseEntity> selectClassNumberOfMonth(String dateStr) {
        return userBaseMappers.selectClassNumberOfMonth(dateStr);
    }

    public int updateClassNumber(Long userid) {
        return userBaseMappers.updateClassNumber(userid);
    }

    public List<UserBaseEntity> selectUserByInteger(Long[] longs) {
        return userBaseMappers.selectUserByInteger(longs);
    }

    public int updateClassNumberNotMemgrade(Long userid) {
        return userBaseMappers.updateClassNumberNotMemgrade(userid);
    }

    public int updateClassNumberNotClassnumber(long userid) {
        return userBaseMappers.updateClassNumberNotClassnumber(userid);
    }


}
