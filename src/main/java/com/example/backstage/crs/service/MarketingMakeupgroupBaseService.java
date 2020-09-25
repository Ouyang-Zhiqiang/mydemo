package com.example.backstage.crs.service;

import com.alibaba.fastjson.JSON;
import com.example.backstage.crs.common.Constant;
import com.example.backstage.crs.entity.CrdMembershipcardBaseEntity;
import com.example.backstage.crs.entity.MarketingMakeupgroupBaseEntity;
import com.example.backstage.crs.entity.MarketingMakeupgroupRecordEntity;
import com.example.backstage.crs.mapper.CrdMembershipCardBaseMapper;
import com.example.backstage.crs.mapper.MarketingMakeupgroupBaseMapper;
import com.example.backstage.crs.mapper.MarketingMakeupgroupRecordMapper;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Service
public class MarketingMakeupgroupBaseService {
    @Autowired
    protected MarketingMakeupgroupBaseMapper marketingMakeupgroupBaseMapper;
    @Autowired
    protected MarketingMakeupgroupRecordMapper marketingMakeupgroupRecordMapper;
    @Autowired
    protected CrdMembershipCardBaseMapper crdMembershipCardBaseMapper;

    public String insertMarketingMakeupgroupBase(MarketingMakeupgroupBaseEntity marketingMakeupgroupBaseEntity) {
        String rs="false";
        int result=marketingMakeupgroupBaseMapper.insertMarketingMakeupgroupBase(marketingMakeupgroupBaseEntity);
        if(result>0){
            rs="true";
        }
        return rs;
    }

    public String updateMarketingMakeupgroupBase(MarketingMakeupgroupBaseEntity marketingMakeupgroupBaseEntity) {
        String rs="false";
        int result=marketingMakeupgroupBaseMapper.updateMarketingMakeupgroupBase(marketingMakeupgroupBaseEntity);
        if(result>0){
            rs="true";
        }
        return rs;
    }

    public List<Map<String,String>> marketingMakeupgroupBaseMapperList(Integer limit, Integer page) {
        Integer pages=(page-1)*limit;
        return marketingMakeupgroupBaseMapper.marketingMakeupgroupBaseMapperList(limit,pages);
    }

    /*未支付时团购*/
    public String makeupgroupSuccess(MarketingMakeupgroupRecordEntity marketingMakeupgroupRecordEntity, BigDecimal sfprice, String openid) throws Exception{
        String recordid= UUID.randomUUID().toString();
        //替换uuid中的"-"
        recordid=recordid.replace("-", "");
        /*记录表新增*/
        marketingMakeupgroupRecordEntity.setRecordid(recordid);
        Long groupid=marketingMakeupgroupRecordEntity.getGroupid();
        StringBuilder result = new StringBuilder();
        /*查找拼团详情*/
        MarketingMakeupgroupBaseEntity marketingMakeupgroupBaseEntity=marketingMakeupgroupBaseMapper.selectMarketingMakeupgroupBaseEntityByGroupId(groupid);
        if(marketingMakeupgroupBaseEntity!=null&&!marketingMakeupgroupBaseEntity.equals("")){
            /*新增记录表*/
            MarketingMakeupgroupRecordEntity record=marketingMakeupgroupRecordMapper.selectGroupid(groupid);
            marketingMakeupgroupRecordEntity.setCardno(marketingMakeupgroupBaseEntity.getCardid());
            marketingMakeupgroupRecordEntity.setGroupid(groupid);
            marketingMakeupgroupRecordEntity.setCardstatus(1);
            marketingMakeupgroupRecordEntity.setPaymentstatus(Constant.PAYSTAMENT_UNPAID);
            marketingMakeupgroupRecordEntity.setPhonenumber("");//未获取
            marketingMakeupgroupRecordEntity.setWechatimgid("");//未获取
            marketingMakeupgroupRecordEntity.setWechatimgurl("");//未获取
            marketingMakeupgroupRecordEntity.setCreatedon(new Date());
            marketingMakeupgroupRecordEntity.setLastedon(new Date());
            if(record.getTotal()==0){
                marketingMakeupgroupRecordEntity.setGroupnumber(recordid);
                marketingMakeupgroupRecordEntity.setMemberStatus(0);//团长
            }else{
                marketingMakeupgroupRecordEntity.setGroupnumber(record.getGroupnumber());
                marketingMakeupgroupRecordEntity.setMemberStatus(1);
            }
            int  success=marketingMakeupgroupRecordMapper.insertMarketingMakeupgroupRecord(marketingMakeupgroupRecordEntity);

            //调用支付接口
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
                Integer count=(record.getTotal()+1);
                Map<String,String> map = JSON.parseObject(result.toString(), HashMap.class);
                map.put("recordid",recordid);
                map.put("groupCount",String.valueOf(marketingMakeupgroupBaseEntity.getGroupsize()));
                map.put("groupid",String.valueOf(record.getGroupid()));
                map.put("number",count.toString());
                JSONObject jsonObject= JSONObject.fromObject(map);
                result=new StringBuilder(jsonObject.toString());
                return result.toString();
            }else{
                return "false";
            }
        }else{
            return "活动已结束";
        }
    }


    public int updateStateByRecordId(String recordid, Integer paystamentIngrounp) {
        return marketingMakeupgroupRecordMapper.updateStateByRecordId(recordid,paystamentIngrounp);
    }

    public Integer selectTotalList() {
        return marketingMakeupgroupBaseMapper.selectTotalList();
    }

    public List<MarketingMakeupgroupRecordEntity> selectMakeupgroupBaseByGroupNumber(String groupnumber) {
        return marketingMakeupgroupRecordMapper.selectMakeupgroupBaseByGroupNumber(groupnumber);
    }

    public int updateStateByGroupNumber(String groupnumber, Integer paystamentIngrounp) {
        return marketingMakeupgroupRecordMapper.updateStateByGroupNumber(groupnumber,paystamentIngrounp);
    }

    public CrdMembershipcardBaseEntity selectCrdMembershipcardBaseByCardNo(String cardno) {
        return crdMembershipCardBaseMapper.selectCrdMembershipcardBaseByCardNo(cardno);
    }

    public List<MarketingMakeupgroupRecordEntity> selectMakeupgroupBaseRecord(Integer limit, Integer page, Integer groupid) {
        Integer pages=(page-1)*limit;
        return marketingMakeupgroupRecordMapper.selectMakeupgroupBaseRecord(limit,page,groupid);
    }

    public int selectMakeupgroupTotal(Integer groupid) {
        return marketingMakeupgroupRecordMapper.selectMakeupgroupTotal(groupid);
    }

    public List<MarketingMakeupgroupRecordEntity> selectMakeupgroupBaseRecordByEffectiveenddate() {
        return marketingMakeupgroupRecordMapper.selectMakeupgroupBaseRecordByEffectiveenddate();
    }


    public MarketingMakeupgroupBaseEntity selectmarketingMakeupgroupBaseById(int groupid) {
        return marketingMakeupgroupRecordMapper.selectmarketingMakeupgroupBaseById(groupid);
    }

    /**
     * 判断对象中属性值是否全为空
     *
     * @param object
     * @return
     */
    public static boolean checkObjAllFieldsIsNull(Object object) {
        if (null == object) {
            return true;
        }

        try {
            for (Field f : object.getClass().getDeclaredFields()) {
                f.setAccessible(true);

                System.out.print(f.getName() + ":");
                System.out.println(f.get(object));

                if (f.get(object) != null && StringUtils.isNotBlank(f.get(object).toString())) {
                    return false;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }


    public int updateStatusByRecordid(MarketingMakeupgroupRecordEntity marketingMakeupgroupRecordEntity) {
        return marketingMakeupgroupRecordMapper.updateStatusByRecordid(marketingMakeupgroupRecordEntity);
    }
}
