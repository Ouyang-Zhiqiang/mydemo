package com.example.backstage.crs.service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.example.backstage.crs.entity.Testcost;
import com.example.backstage.crs.mapper.CurriculumAnalysisMapper;
import com.example.backstage.crs.util.Param;
import com.example.backstage.crs.util.Send;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    public void bindingvenue(Param param){
        curriculumAnalysisMapper.bindingvenue(param.getStoreid(),param.getUserid());
    }

    // 购卡（续卡）BuyCard
    public String BuyCard(Param param)throws Exception{
        String result= null;
            if(param.getUserid().equals("")||param.getCardid().equals("")){
                result= "{\"state\":0,\"remarks\":\"参数为空\"}";
                return (new ObjectMapper()).writeValueAsString(result);
            }
            Long userid = Long.parseLong(param.getUserid());
            Long cardid = Long.parseLong(param.getCardid());
            Long tid = Long.parseLong(param.getTid());
            Double fee = Double.parseDouble(param.getFee());
            String buytype = "F";
            String cardno = param.getCardno();
            String qy1 = "select isopenedbyfirst from crd_membershipcardcategory_base where cardid=?";
            Map<String,Object> map = curriculumAnalysisMapper.qy1(cardid.toString());
            Integer a2=0,a3=0,a4=0,a5=0,a6;
            result = "";
            String qytype = "select cardtype,state from crd_membershipcardcategory_base where cardid=?";
            Map<String,Object> type = curriculumAnalysisMapper.qytype(cardid.toString());
            String cardtype = type.get("cardtype").toString();
            String state = type.get("state").toString();
            if (state.equals("0")){
                result = "{\"state\":0,\"remarks\":\"此卡种已被禁用\"}";
                return (new ObjectMapper()).writeValueAsString(result);
            }
            if (map !=null && map.size()>0){
                if (map.get("isopenedbyfirst")!=null){
                    String isf = map.get("isopenedbyfirst").toString();
                    //System.out.println("【31.2】isopenedbyfirst:"+isf);
                    //System.out.println("【31.3】cardtype:"+cardtype);
                    String  str = "0";
                    Map<String, Object> store = curriculumAnalysisMapper.selectstorename(userid.toString());
                    if (cardtype.equalsIgnoreCase("S")){
                        str = "trunc(b.fee/b.times,2)";
                    }
                    SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
                    String newid = sdf2.format(new Date())+(System.currentTimeMillis()+"").substring(8, 13);
                    if ("false".equalsIgnoreCase(isf)){

                        a2 = curriculumAnalysisMapper.qy2(cardno,userid.toString(),str,tid.toString(),cardid.toString());
                        a3 = curriculumAnalysisMapper.qy3(newid,cardno,buytype,fee.toString(),store.get("storeid").toString(),store.get("storename").toString(),userid.toString(),tid.toString(),cardid.toString());
                    }else {
                        a4 = curriculumAnalysisMapper.qy4(cardno,userid.toString(),str,tid.toString(),cardid.toString());
                        a5 = curriculumAnalysisMapper.qy5(newid,cardno,buytype,fee.toString(),store.get("storeid").toString(),store.get("storename").toString(),userid.toString(),tid.toString(),cardid.toString());
                    }
                    Integer a7 = curriculumAnalysisMapper.qy7(cardno,userid.toString());
                    Map<String,Object> map1 = curriculumAnalysisMapper.points(tid.toString());
                    // 添加积分变动日志
                    logPoints(userid,map1.get("points").toString(),"会员购卡:"+cardid+","+map1.get("points"),"+","购卡奖励","");
                    //System.out.println("【31.9】logPoints");

                    String qy6 = "update user_base set points=points+(select points from crd_membershipcardcategory_typecard where tid=?),lastedon=now(),lastedby=?,lastedname=name,lastedip='::1' where userid=?";
                    a6 = curriculumAnalysisMapper.qy6(tid.toString(),userid.toString());

                    //System.out.println("【31.10】加积分points=points+|a6:"+a6);

                    if (("false".equalsIgnoreCase(isf) && a2>0 && a3>0 && a6>0)|| (a4>0 && a5>0 && a6>0)){
                        result = "{\"state\":1,\"remarks\":\"\"}";
                    }else {
                        result = "{\"state\":0,\"remarks\":\"开卡失败\"}";
                    }
                }else {
                    result = "{\"state\":0,\"remarks\":\"此卡不存在\"}";
                }
            }else {
                result = "{\"state\":0,\"remarks\":\"此卡不存在\"}";
            }
            System.out.println("【31.12】result:"+result);

        return (new ObjectMapper()).writeValueAsString(result);
    }
    // 操作积分日志
    public boolean logPoints(Long uid,String optpts,String rks,String opt,String optrks,String str)throws Exception{
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
        String newid = sdf2.format(new Date())+(System.currentTimeMillis()+"").substring(8, 13);
        Long logid = Long.parseLong(newid);
        Long userid = uid;
        Map<String,Object> map = curriculumAnalysisMapper.qysalespoint(userid.toString());
        Double points = Double.parseDouble(optpts);
        String remarks = rks;
        Date createdon = new Date();
        String createdby = uid+"";
        String createdname;
        if (str.length() > 0){
            createdname = str;
        }else {
            createdname = curriculumAnalysisMapper.storeidOrTel(userid.toString()).get("name").toString();
        }
        String createdip = "::1";
        String actionstate = opt;
        String changeaction = optrks;
        Double currentpoints;
        Double surpluspoints;
        if (map!=null&& map.size()>0 && map.get("points")!=null) {
            currentpoints = Double.parseDouble(map.get("points").toString());
        }else {
            currentpoints = 0d;
        }
        if ("+".equals(opt)){
            surpluspoints = currentpoints + points;
        }else {
            if (currentpoints>points){
                surpluspoints = currentpoints - points;
            }else {
                surpluspoints = 0d;
            }

        }
        //System.out.println("logid|0|userid|1|currentpoints|2|points|3|surpluspoints|4|remarks|5|createdon|6|createdby|7|createdname|8|createdip|9|actionstate|10|changeaction|11|");
        //System.out.println(logid+",|0|"+userid+",|1|"+currentpoints+",|2|"+points+",|3|"+surpluspoints+",|4|"+remarks+",|5|"+createdon+",|6|"+createdby+",|7|"+createdname+",|8|"+createdip+",|9|"+actionstate+",|10|"+changeaction+"|11|");
        Integer a1 = curriculumAnalysisMapper.logqy(logid.toString(),userid.toString(),currentpoints.toString(),points.toString(),
                surpluspoints.toString(),remarks,
                createdon.toString(),createdby,createdname,createdip,actionstate,changeaction);
        if (a1>0){
            return true;
        }else {
            return false;
        }
    }
    public String selectstorename(Param param){
        Map<String, Object> selectstorename = curriculumAnalysisMapper.selectstorename(param.getUserid());
        return JSON.toJSONString(selectstorename);

    }

}
