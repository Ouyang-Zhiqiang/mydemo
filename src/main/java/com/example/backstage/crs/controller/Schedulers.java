package com.example.backstage.crs.controller;

import com.example.backstage.crs.entity.UserBaseEntity;
import com.example.backstage.crs.service.OrderCourseService;
import com.example.backstage.crs.service.UserBasesService;
import com.sun.org.apache.xpath.internal.operations.Or;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling
public class Schedulers {
    @Autowired
    protected UserBasesService userBasesService;
    @Autowired
    protected OrderCourseService orderCourseService;
    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");

//    //每隔2秒执行一次
//    @Scheduled(fixedRate = 2000)
//    public void testTasks() {
//        System.out.println("定时任务执行时间：" + dateFormat.format(new Date()));
//    }

    //每天09:05执行
//    @SneakyThrows
//    @Scheduled(cron = "0 * 10 * * ?")
//     public void testTasks() {
////        System.out.println("打印");
//        List<MarketingMakeupgroupRecordEntity> marketingMakeupgroupBaseEntityList = marketingMakeupgroupBaseService.selectMakeupgroupBaseRecordByEffectiveenddate();
//        System.out.println("list-----"+marketingMakeupgroupBaseEntityList.toString());
//        StringBuilder result = new StringBuilder();
//        for (MarketingMakeupgroupRecordEntity m : marketingMakeupgroupBaseEntityList) {
//            //调用退款接口
//            String uri = "http://localhost:8081/refund?orderId=" + m.getRecordid() + "&&price=" +11.6+ "";
//            URL url = new URL(uri);
//            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
//            connection.setRequestMethod("POST");
//            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
//            String line = null;
//            while ((line = br.readLine()) != null) {
//                result.append(line);
//
//            }
//            connection.disconnect();
//            if(line!=null&&!line.equals("")){
//                MarketingMakeupgroupRecordEntity marketingMakeupgroupRecordEntity=new MarketingMakeupgroupRecordEntity();
//                marketingMakeupgroupRecordEntity.setRecordid(m.getRecordid());
//                marketingMakeupgroupRecordEntity.setPaymentstatus(Constant.PAYSTAMENT_REFUNDSUCCESSFUL);
//                int success = marketingMakeupgroupBaseService.updateStatusByRecordid(marketingMakeupgroupRecordEntity);
//            }
//        }
//    }

    //每天09:05执行
//    @SneakyThrows
//    @Scheduled(cron = "* 0 10 * * ?")//0 0 0 1 1/1 ?
//     public void testTasksOnMonth() {
//        System.out.println("打印");
//         SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
//        Calendar date1 = Calendar.getInstance();
//        date1.add(Calendar.MONTH,-1);
//        //当月日期
//        String dateStr=format.format(date1.getTime());
//        Calendar lastOneC1 = Calendar.getInstance();
//        lastOneC1.add(Calendar.MONTH,-2);
//        //前一个月日期
//        String lastOneMonth=format.format(lastOneC1.getTime());
//        Calendar lastTwoC1 = Calendar.getInstance();
//        lastTwoC1.add(Calendar.MONTH,-3);
//        //前两个月日期
//        String lastTwoMonth=format.format(lastTwoC1.getTime());
        /*查询出每个会员本月上课次数*/
//        List<UserBaseEntity> list= userBasesService.selectClassNumberOfMonth(dateStr);
//        if(list.size()>0&&list!=null){
//            for (UserBaseEntity userBaseEntity : list) {
//                Integer mer =userBaseEntity.getMemgradeone();
//                if (mer < 3 && userBaseEntity.getClassnumber() == 8) {
//                    int result = userBasesService.updateClassNumber(userBaseEntity.getUserid());
//                }
//                else if(userBaseEntity.getClassnumber() == 6 && mer == 3){
//                    int result = userBasesService.updateClassNumberNotClassnumber(userBaseEntity.getUserid());
//                }
//                else {
//                    int result = userBasesService.updateClassNumberNotMemgrade(userBaseEntity.getUserid());
//                }
//            }
//        }

        /*查询出前三个月未上过课的会员：会员等级降一级*/
//        List<Long> list1=orderCourseService.selectOnCourseThreeMonth(dateStr,lastOneMonth,lastTwoMonth);
//        //初始化需要得到的数组
//        Long[] useridArray = new Long[list1.size()];
//
//        //使用for循环得到数组
//        for(int i = 0; i < list1.size();i++){
//            useridArray[i] = list1.get(i);
//        }
//        int success=orderCourseService.updateReduce(useridArray);
//        System.out.println("成功！");
//    }
}
