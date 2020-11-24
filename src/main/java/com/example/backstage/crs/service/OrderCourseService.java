package com.example.backstage.crs.service;

import com.example.backstage.crs.mapper.OrdOrdercourseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class OrderCourseService {

    @Autowired
    private OrdOrdercourseMapper ordOrdercourseMapper;

    public List<Long> selectOnCourseThreeMonth(String dateStr, String lastOneMonth, String lastTwoMonth) {
        return ordOrdercourseMapper.selectOnCourseThreeMonth(dateStr,lastOneMonth,lastTwoMonth);
    }
}
