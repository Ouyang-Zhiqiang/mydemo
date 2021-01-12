package com.example.backstage.crs.service;

import cn.hutool.core.date.DateTime;
import com.example.backstage.crs.entity.SpmIntegralgoodsOrderEntity;
import com.example.backstage.crs.entity.SpmIntegralgoodsOrderExchangelogEntity;
import com.example.backstage.crs.mapper.SPMIntegralGoodsBaseMapper;
import com.example.backstage.crs.mapper.SpmIntegralgoodsOrderExchangelogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SpmGoodService {

    @Autowired
    protected SpmIntegralgoodsOrderExchangelogMapper spmIntegralgoodsOrderExchangelogMapper;

    public String spmGoodChange(Long orderId,String createdby,String createdname,String ip) {

        SpmIntegralgoodsOrderEntity detail = spmIntegralgoodsOrderExchangelogMapper.selectSpmIntegralgoodsOrderByOrderid(orderId);
        SpmIntegralgoodsOrderExchangelogEntity slmodel = new SpmIntegralgoodsOrderExchangelogEntity();
        slmodel.setOrderid(detail.getOrderid());
        slmodel.setUserid(detail.getUserid());
        slmodel.setUsername(detail.getUsername()) ;
        slmodel.setUserphone(detail.getUserphone());
        slmodel.setGoodscode(detail.getGoodscode());
        slmodel.setName(detail.getName());
        slmodel.setImgid(detail.getImgid());
        slmodel.setImgurl(detail.getImgurl());
        slmodel.setSalespoint(detail.getSalespoint());
        slmodel.setStoreid(detail.getStoreid()) ;
        slmodel.setStorename(detail.getStorename()) ;
        slmodel.setUsedvaliditybegin(detail.getUsedvaliditybegin()) ;
        slmodel.setUsedvalidityend(detail.getUsedvalidityend()) ;
        slmodel.setSalesstate(detail.getSalesstate());
        slmodel.setSeqnum(detail.getSeqnum());
        slmodel.setOpttype(1);
        slmodel.setState(detail.getState());
        slmodel.setLogstate(1);
        slmodel.setIsremoved("false");
        slmodel.setCreatedby(createdby);
        slmodel.setCreatedname(createdname); ;
        slmodel.setCreatedip(ip);
        slmodel.setCreatedon(new Date());
        int success=spmIntegralgoodsOrderExchangelogMapper.Insert(slmodel);

        detail.setOrderid(orderId);
        detail.setState(1);
        detail.setLastedby(createdby);
        detail.setLastedname(createdname);
        detail.setLastedip(ip);
        detail.setLastedon(new Date());
        int result= spmIntegralgoodsOrderExchangelogMapper.Update(detail);
        if(success>0&&result>0){
            return   "操作成功！";
        }
       return   "操作失败！";
    }
}
