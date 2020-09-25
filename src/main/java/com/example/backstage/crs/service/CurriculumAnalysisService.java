package com.example.backstage.crs.service;
import com.example.backstage.crs.mapper.CurriculumAnalysisMapper;
import com.example.backstage.crs.util.Param;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class CurriculumAnalysisService {
    @Resource
    private CurriculumAnalysisMapper curriculumAnalysisMapper;

    /*查询上课人次、上课次数及次卡销课总金额*/

    public String getCoursesNumber(Param param) throws Exception {
        List<Map<String,Object>> list=new ArrayList<Map<String, Object>>();

        String result = "[{";
        list.add(curriculumAnalysisMapper.sql1(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid(),param.getCoachid()));
        list.add(curriculumAnalysisMapper.sql2(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid(),param.getCoachid()));
        list.add(curriculumAnalysisMapper.sql3(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid(),param.getCoachid()));
        list.add(curriculumAnalysisMapper.sql4(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid(),param.getCoachid()));
        list.add(curriculumAnalysisMapper.sql5(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid(),param.getCoachid()));

        for (int i=1;i<=7;i++){
            List<Integer> maps;
            maps   = curriculumAnalysisMapper.sql6(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid(), param.getCoachid(), i);
            if(i>6){
            maps   = curriculumAnalysisMapper.sql7(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid(),param.getCoachid(),i);
            }
            result+="\"Classes"+i+"\":"+maps.size()+",";
        }
        result=buyCardUsersJsonStr(result,list)+"]";
        return result;
    }

   /*查询团课排课列表*/
   public String  ctCoursereport(Param param) throws Exception {
       String page=param.getPage();
       String limit=param.getLimit();
       if (limit==null||"".equals(limit)){
           limit="20";
       }
       if (page==null||"".equals(page)){
           page="0";
       }else{
           page=String.valueOf(Integer.parseInt(page)-1);
           page=String.valueOf(Integer.parseInt(param.getLimit())*Integer.parseInt(page));
       }
       List<Map> maps = curriculumAnalysisMapper.ctCoursereport(param.getCourseDatestart(), param.getCourseDateend(), param.getCoachid(), limit, page);
       String result = "[";
       for (Map<String, Object> map : maps) {
           result+="{\"Date\":\""+map.get("日期")+"\","
                   +"\"Coach\":\""+map.get("教练")+"\","
                   +"\"Curriculumtype\":\""+map.get("课程类型")+"\","
                   +"\"Curriculumname\":\""+map.get("课程名称")+"\","
                   +"\"Curriculumtime\":\""+map.get("上课时间")+"\","
                   +"\"Makeanappointment\":"+map.get("预约人次")+","
                   +"\"Signin\":"+map.get("签到人次")+","
                   +"\"Cancel\":"+map.get("取消人次")+","
                   +"\"Appointmentavailable\":"+map.get("可约人次")+","
                   +"\"CurriculumPrice\":\""+map.get("课程价格")+"\","
                   +"\"Fullcapacityrate\":\""+map.get("满员率")+"%"+"\","
                   +"\"total\":"+map.get("total")+""
                   +"},";
       }
       result=result.substring(0,result.length()-1);
       result+="]";
       return result;
   }


    /*查询私教排课列表*/
    public String cpCoursereport(Param param) throws Exception {
        String page=param.getPage();
        String limit=param.getLimit();
        if (limit==null||"".equals(limit)){
            limit="20";
        }
        if (page==null||"".equals(page)){
            page="0";
        }else{
            page=String.valueOf(Integer.parseInt(page)-1);
            page=String.valueOf(Integer.parseInt(param.getLimit())*Integer.parseInt(page));
        }
        List<Map> maps = curriculumAnalysisMapper.cpCoursereport(param.getCourseDatestart(), param.getCourseDateend(), param.getCoachid(), limit, page);
        String result = "[";
        for (Map<String, Object> map : maps) {
            result+="{\"Date\":\""+map.get("日期")+"\","
                    +"\"Coach\":\""+map.get("教练")+"\","
                    +"\"Curriculumtype\":\""+map.get("课程类型")+"\","
                    +"\"Curriculumname\":\""+map.get("课程名称")+"\","
                    +"\"Curriculumtime\":\""+map.get("上课时间")+"\","
                    +"\"Makeanappointment\":"+map.get("预约人次")+","
                    +"\"Signin\":"+map.get("签到人次")+","
                    +"\"Cancel\":"+map.get("取消人次")+","
                    +"\"CurriculumPrice\":\""+map.get("课程价格")+"\","
                    +"\"total\":"+map.get("total")+""
                    +"},";
        }
        result=result.substring(0,result.length()-1);
        result+="]";
        return result;
    }




    public String buyCardUsersJsonStr(String result,List<Map<String,Object>> list)throws Exception{
        int sum=Integer.parseInt(list.get(0).get("count").toString())+
                Integer.parseInt(list.get(1).get("count").toString());
        result+="\"Courses\":"+sum+","+
                "\"League\":"+list.get(0).get("count")+","+
                "\"Private\":"+list.get(1).get("count")+","+
                "\"LeaguePersontimes\":"+list.get(2).get("sum")+","+
                "\"PrivatePersontimes\":"+list.get(3).get("sum")+","+
                "\"Subcardmoney\":"+list.get(4).get("sum")+"}";
        return result;
    }
}
