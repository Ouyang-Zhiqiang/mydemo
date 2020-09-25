package com.example.backstage.crs.service;

import com.alibaba.fastjson.JSON;
import com.example.backstage.crs.common.Constant;
import com.example.backstage.crs.entity.*;
import com.example.backstage.crs.mapper.*;
import com.example.mydemo.mapper.UserBaseMapper;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class MarketingSeckillBaseSerice {
    @Autowired
    protected MarketingSeckillBaseMapper marketingSeckillBaseMapper;
    @Autowired
    protected MarketingSeckillBaseRecordMapper marketingSeckillBaseRecordMapper;
    @Autowired
    protected CrdMembershipcardcategoryTypecardMapper crdMembershipcardcategoryTypecardMapper;
    @Autowired
    protected CrdMembershipcardcategoryBaseMapper crdMembershipcardcategoryBaseMapper;
    @Autowired
    protected CrdMembershipCardBaseMapper crdMembershipCardBaseMapper;
    @Autowired
    protected CrdMembershipCardUserMapper crdMembershipCardUserMapper;
    @Autowired
    protected UserBaseMappers userBaseMappers;

    /*新增*/
    public String addMarketingSeckillBase(MarketingSeckillBaseEntity marketingSeckillBaseEntity) {
        String rs="false";
        int result=marketingSeckillBaseMapper.insertMarketingSeckillBase(marketingSeckillBaseEntity);
        if(result>0){
            rs="true";
        }
        return rs;
    }

    /*支付成功秒杀绑卡*/
    public String paySuccess(MarketingSeckillBaseRecordEntity marketingSeckillBaseRecordEntity, BigDecimal sfprice, String openid) throws Exception{
            String recordid= UUID.randomUUID().toString();
            //替换uuid中的"-"
            recordid=recordid.replace("-", "");
            /*记录表新增*/
            marketingSeckillBaseRecordEntity.setRecordid(recordid);
            marketingSeckillBaseRecordEntity.setPaymentstatus(Constant.UNPAID);//未支付
            marketingSeckillBaseRecordEntity.setIsremoved(0);//未删除
            marketingSeckillBaseRecordEntity.setState(1);//使用
            marketingSeckillBaseRecordEntity.setCardstatus(1);//未绑卡
            marketingSeckillBaseRecordEntity.setCreatedon(new Date());
            marketingSeckillBaseRecordEntity.setLastedon(new Date());
            marketingSeckillBaseRecordEntity.setUnionid(openid);
            marketingSeckillBaseRecordEntity.setPrice(sfprice);
            int success=marketingSeckillBaseRecordMapper.insertMarketingSeckillBaseRecord(marketingSeckillBaseRecordEntity);
            StringBuilder result = new StringBuilder();
            if(success>0){
                String uri="http://localhost:8081/wxPay?id="+recordid+"&&sfprice="+sfprice+"&&openid="+openid+"";
                URL url = new URL(uri);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("POST");
                BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
                String line = null;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                connection.disconnect();
                Map<String,String> map = JSON.parseObject(result.toString(), HashMap.class);
                map.put("recordid",recordid);
                map.put("groupid",marketingSeckillBaseRecordEntity.getGroupid().toString());
                JSONObject jsonObject= JSONObject.fromObject(map);
                result=new StringBuilder(jsonObject.toString());
            }else{
                return "false";
            }
            //result转map在map中添加其他对象
            return result.toString();
    }


    public int updateStatus(Map<String, Object> param) {
      return  marketingSeckillBaseRecordMapper.updateStatus(param);
    }

    public String updateMarketingSeckillBase(MarketingSeckillBaseEntity marketingSeckillBaseEntity) {
        int result=marketingSeckillBaseMapper.updateMarketingSeckillBase(marketingSeckillBaseEntity);
        boolean rs=false;
        if(result>0){
            rs=true;
        }
        return rs+"";
    }

    public List<Map<String,String>> selectMarketingSeckillBaseList(Integer limit, Integer page) {
        Integer pages=(page-1)*limit;
        return marketingSeckillBaseMapper.selectMarketingSeckillBaseList(limit,pages);
    }

    public int selectMarketingSeckillBaseCount() {
        return marketingSeckillBaseMapper.selectMarketingSeckillBaseCount();
    }

    public List<MarketingSeckillBaseRecordEntity> selectSeckillBaseRecord(Integer limit, Integer page, Integer groupid) {
        Integer pages=(page-1)*limit;
        return marketingSeckillBaseRecordMapper.selectSeckillBaseRecord(limit,pages,groupid);
    }

    public int selectSeckillBaseTotal() {
        return marketingSeckillBaseRecordMapper.selectSeckillBaseTotal();
    }

    public CrdMembershipcardcategoryTypecardEntity selectMembershipCardCategoryByCardId(Long cardid) {
        return crdMembershipcardcategoryTypecardMapper.selectMembershipCardCategoryByCardId(cardid);
    }

    public CrdMembershipcardcategoryBase selectCrdMembershipcardcategoryBaseByCardId(Long cardid) {
        return crdMembershipcardcategoryBaseMapper.selectCrdMembershipcardcategoryBaseByCardId(cardid);
    }

    public int insertCrdMembershipcardBase(CrdMembershipcardBaseEntity crdMembershipcardBaseEntity) {
        return crdMembershipCardBaseMapper.insertCrdMembershipcardBase(crdMembershipcardBaseEntity);
    }

    public String selectUserIdByPhone(String tel) {
        return userBaseMappers.selectUserIdByPhone(tel);
    }

    public int insertMembershipCardUser(CrdMembershipcardUserEntity crdMembershipcardUserEntity) {
        return crdMembershipCardUserMapper.insertMembershipCardUser(crdMembershipcardUserEntity);
    }

    public MarketingSeckillBaseEntity selectMarketingSeckillBaseBySeckillid(int groupid) {
        return marketingSeckillBaseMapper.selectMarketingSeckillBaseBySeckillid( groupid);
    }
}
