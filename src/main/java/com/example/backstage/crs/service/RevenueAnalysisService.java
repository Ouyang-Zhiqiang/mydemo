package com.example.backstage.crs.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateTime;
import com.example.backstage.crs.entity.CrdMemberShipcardPurchaseResult;
import com.example.backstage.crs.entity.CrdMembershipcardBaseEntity;
import com.example.backstage.crs.entity.CrdMembershipcardPurchaseEntity;
import com.example.backstage.crs.entity.IncomeJsonData;
import com.example.backstage.crs.mapper.CrdMembershipcardPurchaseMapper;
import net.sf.json.JSONArray;
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
                            incomejsondata.setDateTime(String.valueOf(item.getPayment()));
                            incomejsondata.setAmount(item.getSellingfee());
                            jsonList.add(incomejsondata);
            }


            }
        }
        catch (Exception ex) {
            System.out.println("错误");
        }
        String json = JSONArray.fromObject(jsonList).toString();
        return json;
    }

    public String BarChar2(String storeId, String startDate, String endDate, Integer days, String salerId, String buyType) {
        List<CrdMemberShipcardPurchaseResult> list = crdMembershipcardPurchaseMapper.GetCharBar2(startDate,
                endDate, storeId, salerId, buyType);

//        List<CrdMemberShipcardPurchaseResult> resultList = new ArrayList<>();
//        if (list != null && list.size() > 0)
//        {
//
//            for (CrdMemberShipcardPurchaseResult crdMemberShipcardPurchaseResult : list) {
//                CrdMemberShipcardPurchaseResult crdMemberShipcardPurchaseResult1=new CrdMemberShipcardPurchaseResult();
//                crdMemberShipcardPurchaseResult1.setCardname(crdMemberShipcardPurchaseResult.get);
//                resultList.add();
//            }
//
//            foreach (var item in list)
//            {
//                var model = new CharBarJsonResult
//                {
//                    CardName = item.Cardname,
//                            CardAmount = item.TotalAmount.ToString("F"),
//                            CardCount = item.CardCount.ToString()
//                };
//                resultList.Add(model);


//            }
//        }
        return  JSONArray.fromObject(list).toString();
    }
}
