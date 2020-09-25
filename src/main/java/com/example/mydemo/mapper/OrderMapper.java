package com.example.mydemo.mapper;

import com.example.mydemo.pojo.Order;
import org.apache.ibatis.annotations.Mapper;

import java.util.Map;

@Mapper
public interface OrderMapper {
    int insert(Order order);

    int updateStatus(Map<String, Object> param);
}
