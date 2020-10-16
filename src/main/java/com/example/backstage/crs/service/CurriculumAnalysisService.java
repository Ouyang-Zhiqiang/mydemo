package com.example.backstage.crs.service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.backstage.crs.entity.Testcost;
import com.example.backstage.crs.mapper.CurriculumAnalysisMapper;
import com.example.backstage.crs.util.Param;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.lang.reflect.Array;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class CurriculumAnalysisService {
    @Resource
    private CurriculumAnalysisMapper curriculumAnalysisMapper;

    /*上课人数*/
    public String getCoursesNumber(Param param) throws Exception {
        String result = "[{";
        for (int i=1;i<=7;i++){
            List<Integer> maps;
            maps   = curriculumAnalysisMapper.sql6(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid(), param.getCoachid(), i);
            if(i>6){
            maps   = curriculumAnalysisMapper.sql7(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid(),param.getCoachid(),i);
            }
            result+="\"Classes"+i+"\":"+maps.size()+",";
        }
        result=result.substring(0,result.length()-1);
        result+="}]";
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
       List<Map> maps = curriculumAnalysisMapper.ctCoursereport(param.getCourseDatestart(), param.getCourseDateend(), param.getCoachid(), param.getStoreid(),limit, page);
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
        List<Map> maps = curriculumAnalysisMapper.cpCoursereport(param.getCourseDatestart(), param.getCourseDateend(), param.getCoachid(),param.getStoreid(), limit, page);
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

    /*查询预约人次，实到人次，上课总节数*/
    public String getPersontimesandClassnumber(Param param){
        Map map = new HashMap();
        //团课预约人次
        int tNumberofreservations = curriculumAnalysisMapper.getNumberofreservations(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid(), param.getCoachid(), "T");
        //私教预约人次
        int pNumberofreservations = curriculumAnalysisMapper.getNumberofreservations(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid(), param.getCoachid(), "P");
        //团课实到人次
        int tNumberofsignin = curriculumAnalysisMapper.getNumberofsignin(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid(), param.getCoachid(), "T");
        //私教签到人次
        int pNumberofsignin = curriculumAnalysisMapper.getNumberofsignin(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid(), param.getCoachid(), "P");
        //团课总节数
        int numberofgrouplessons = curriculumAnalysisMapper.getNumberofgrouplessons(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid(), param.getCoachid());
        //私教总节数
        int numberofprivatelessons = curriculumAnalysisMapper.getNumberofprivatelessons(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid(), param.getCoachid());
            map.put("tNumberofreservations",tNumberofreservations);
            map.put("pNumberofreservations",pNumberofreservations);
            map.put("tNumberofsignin",tNumberofsignin);
            map.put("pNumberofsignin",pNumberofsignin);
            map.put("numberofgrouplessons",numberofgrouplessons);
            map.put("numberofprivatelessons",numberofprivatelessons);
        return JSON.toJSONString(map);
    }

    public String getAmountoflessonssoldpercard(Param param){
        List<Map> list = curriculumAnalysisMapper.getAmountoflessonssoldpercard(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid(), param.getCoachid());
        return JSON.toJSONString(list);
    }

    public String selectcost(long fenbu){
        if(fenbu==1||fenbu==2){
            List<Map> selectcost = curriculumAnalysisMapper.selectcost(fenbu);
            return JSON.toJSONString(selectcost);
        }else {
            List<Map> selectcostall = curriculumAnalysisMapper.selectcostall();
            return JSON.toJSONString(selectcostall);
        }

        }

    public String updatecost(String jsonstr){
        List<Testcost> list = (List<Testcost>) JSONArray.parseArray(jsonstr, Testcost.class);
        for (Testcost testcost : list) {
            curriculumAnalysisMapper.updatecost(testcost);
        }
        long fenbu=list.get(0).getFenbu();
        return selectcost(fenbu);
    }

    public String selectrevenue(long fenbu){
        System.err.println(fenbu);
        List<Map> revenue = curriculumAnalysisMapper.selectrevenue();
        List<Map<Object,Object>> list= new ArrayList<>();
        if (fenbu==1){
            for (Map map : revenue) {
                Map<Object,Object> map1=new HashMap<>();
                map1.put("revenue",map.get("zbys"));
                list.add(map1);
            }
        }else if(fenbu==2){
            for (Map map : revenue) {
                Map<Object,Object> map1=new HashMap<>();
                map1.put("revenue",map.get("mdys"));
                list.add(map1);
            }
        }else{
            List<Map> selectrevenueall = curriculumAnalysisMapper.selectrevenueall();
            for (Map map : selectrevenueall) {
                Map<Object,Object> map1=new HashMap<>();
                map1.put("revenue",map.get("revenue"));
                list.add(map1);
            }
        }
        return JSON.toJSONString(list);
    }

    public String getNumberofreservation(String array,Param param) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd");
        array = array.substring(1);
        array = array.substring(0,array.length() - 1);
        array= array.replace("\"", "");
        String [] arr= array.split(",");
        List<Integer> list = new ArrayList<>();
        if(arr[0].length()>5) {
            for (String s : arr) {
                Date date = sdf.parse(s);
                Calendar cld = Calendar.getInstance();
                cld.setTime(date);
                cld.add(Calendar.DATE, 1);
                date = cld.getTime();
                String nextDay = sdf.format(date);
                list.add(curriculumAnalysisMapper.getNumberofreservation(s, nextDay, param.getStoreid(), param.getCoachid()));
            }
            return JSON.toJSONString(list);
        }
        return "";
    }

}
