package com.example.backstage.crs.controller;
import com.example.backstage.crs.mapper.NewMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
@Configuration      //1.主要用于标记配置类，兼备Component的效果。
@EnableScheduling   // 2.开启定时任务
public class TimedTaskController {
    @Autowired
    private NewMapper newMapper;
    @Scheduled(fixedRate=3600000)
    private void configureTasks() {
        Date d =new Date();
        SimpleDateFormat date = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
        List<Map<Object, Object>> tkmaps = newMapper.TimedTasktk(date.format(d), time.format(d));
        for (Map<Object, Object> map : tkmaps) {
            Date dd =new Date();
            newMapper.signed1(map.get("ordid").toString());
            newMapper.signed2(map.get("traineenum").toString(),map.get("scheduleid").toString());
            newMapper.setlog(String.valueOf(dd.getTime()),"OrdId:"+map.get("ordid").toString()+" 签到成功");
        }
        List<Map<Object, Object>> sjmaps = newMapper.TimedTasksj(date.format(d), time.format(d));
        for (Map<Object, Object> map : sjmaps) {
            Date dd =new Date();
            newMapper.signed1(map.get("ordid").toString());
            newMapper.signedsj(map.get("scheduleid").toString());
            newMapper.setlog(String.valueOf(dd.getTime()),"OrdId:"+map.get("ordid").toString()+" 签到成功");
        }
    }
}
