package com.example.backstage.crs.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import com.example.backstage.crs.entity.CrdMembershipcardBaseEntity;
import com.example.backstage.crs.entity.CrdMembershipcardPurchaseEntity;
import com.example.backstage.crs.entity.IncomeJsonData;
import com.example.backstage.crs.mapper.CrdMembershipcardPurchaseMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import springfox.documentation.spring.web.json.Json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class RevenueAnalysisService {
    @Autowired
    protected CrdMembershipcardPurchaseMapper crdMembershipcardPurchaseMapper;
    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    public String DrawChart1(String StoreId,String StartDate,String EndDate,Integer days) throws ParseException {
        List<CrdMembershipcardPurchaseEntity> list = crdMembershipcardPurchaseMapper.GetAllListSpecialChar(StoreId,
                StartDate
                ,EndDate);
        Map<String,String> jsonList =new HashMap<>();
        for (CrdMembershipcardPurchaseEntity item:list) {
            jsonList.put(item.getCreatedname(),item.getSellingfee());
        }
        List<Double> amountList = AddStatData1(jsonList, StartDate, EndDate,days);
        return amountList.toString();
    }


    private List<Double> AddStatData1(Map<String,String> map, String start, String end,Integer days) throws ParseException {
        Date currdate = format.parse(start);
        List<Double> amountList=new ArrayList<>();
        for (int i = 0; i < days; i++)
        {
            Calendar cl = Calendar.getInstance();
            cl.setTime(currdate);
            cl.add(Calendar.DATE, i);
            String d=format.format(cl.getTime());
            if(map.containsKey(d)){
                amountList.add(Double.parseDouble(map.get(d)));
            }else{
                amountList.add(new Double(0));
            }
        }
        return amountList;
    }

    public String DrawChart2(String storeId, String startDate, String endDate, Integer days) {
        List<IncomeJsonData> jsonList = new ArrayList<>();
        try
        {

            List<CrdMembershipcardPurchaseEntity> countList=crdMembershipcardPurchaseMapper.GetAllListByPayWay(storeId,startDate, endDate);

            if (countList != null && countList.size() > 0)
            {
                for (CrdMembershipcardPurchaseEntity item:countList)
                {

                            IncomeJsonData incomejsondata=new IncomeJsonData();
                            incomejsondata.setDateTime(item.getDateTime());
                            incomejsondata.setAmount(item.getSellingfee());
                            jsonList.add(incomejsondata);
            }


            }
        }
        catch (Exception ex) {
            System.out.println("错误");
        }
        return jsonList.toString();
    }
}
