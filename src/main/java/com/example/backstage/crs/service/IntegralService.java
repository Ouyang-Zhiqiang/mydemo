package com.example.backstage.crs.service;


import com.alibaba.fastjson.JSONArray;
import com.example.backstage.crs.entity.SpmIntegralgoodsBaseEntity;
import com.example.backstage.crs.mapper.SPMIntegralGoodsBaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class IntegralService {
    @Autowired
    protected SPMIntegralGoodsBaseMapper spmIntegralGoodsBaseMapper;

    /*新增商品*/
    public String insertSPMIntegralGoodsBase(SpmIntegralgoodsBaseEntity spmIntegralgoodsBaseEntity) {
        Boolean result=false;
        List<Map<String, String>> list = (List<Map<String, String>>) JSONArray.parse(spmIntegralgoodsBaseEntity.getStore());
        for (Map<String, String> map:list){
            spmIntegralgoodsBaseEntity.setStoreid(Long.valueOf(map.get("storeid")));
            spmIntegralgoodsBaseEntity.setStorename(map.get("storename"));
            int  a=spmIntegralGoodsBaseMapper.insertSPMIntegralGoodsBase(spmIntegralgoodsBaseEntity);
            if(a>0){
                result=true;
            }
        }
        return  result.toString();
    }

    /*修改商品*/
    public String updateSPMIntegralGoodsBase(SpmIntegralgoodsBaseEntity spmIntegralgoodsBaseEntity) {
        Boolean result=false;
        int  a=spmIntegralGoodsBaseMapper.updateSPMIntegralGoodsBase(spmIntegralgoodsBaseEntity);
        if(a>0){
            result=true;
        }
        return result.toString();
    }


    public String deleteSPMIntegralGoodsBase(Integer goodscode) {
        Boolean result=false;
        int a=spmIntegralGoodsBaseMapper.deleteSPMIntegralGoodsBase(goodscode);
        if(a>0){
            result=true;
        }
        return result.toString();
    }

    public String updateSPMIntegralGoodsBaseSalesState(SpmIntegralgoodsBaseEntity spmIntegralgoodsBaseEntity) {
        Boolean result=false;
        int a=spmIntegralGoodsBaseMapper.updateSPMIntegralGoodsBaseSalesState(spmIntegralgoodsBaseEntity);
        if(a>0){
            result=true;
        }
        return result.toString();
    }
}
