package com.example.mydemo.controller;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class Scheduler{
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    //每隔2秒执行一次
//    @Scheduled(fixedRate = 2000)
//    public void testTasks() {
//        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
//    }

    //每天3:05执行
    @Scheduled(cron = "0 20 17 * * ?")
     public void testTasks() {
//        List<MarketingMakeupgroupRecordEntity> marketingMakeupgroupBaseEntityList=marketingMakeupgroupBaseService.selectMakeupgroupBaseRecordByEffectiveenddate();
//        StringBuilder result = new StringBuilder();
//        for(MarketingMakeupgroupRecordEntity m:marketingMakeupgroupBaseEntityList){
//            //调用退款接口
//            String uri="http://localhost:8081/refund?orderId="+m.getRecordid()+"&&price="+m.getPrice()+"";
//            URL url = new URL(uri);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                result.append(line);
//            }
//            connection.disconnect();
//
//            MarketingMakeupgroupRecordEntity marketingMakeupgroupRecordEntity=new MarketingMakeupgroupRecordEntity();
//            marketingMakeupgroupRecordEntity.setRecordid(m.getRecordid());
//            marketingMakeupgroupRecordEntity.setPaymentstatus(Constant.PAYSTAMENT_REFUNDSUCCESSFUL);
//            int  success=marketingMakeupgroupBaseService.updateStatusByRecordid(marketingMakeupgroupRecordEntity);
    }


}
