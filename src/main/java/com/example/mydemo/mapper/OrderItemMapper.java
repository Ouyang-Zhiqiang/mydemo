package com.example.mydemo.mapper;

import com.example.mydemo.pojo.OrderItem;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface OrderItemMapper {
    int insert(List<OrderItem> orderItemList);

    void insertOne(OrderItem pojo);
}
