package com.example.backstage.crs.service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backstage.crs.entity.LogMobileMetaEntity;
import com.example.backstage.crs.entity.OrdOrdercourseEntity;
import com.example.backstage.crs.entity.Testcost;
import com.example.backstage.crs.mapper.CurriculumAnalysisMapper;
import com.example.backstage.crs.mapper.OrdOrdercourseMapper;
import com.example.backstage.crs.util.MsgParam;
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
    @Resource
    private OrdOrdercourseMapper ordOrdercourseMapper;

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

                    Map<String, Object> userCard = curriculumAnalysisMapper.userCard(cardno);
                    Map<String, Object> storeidOrTel = curriculumAnalysisMapper.storeidOrTel(userid.toString());
                    String varmsg = "FaceBody"+userCard.get("cardname")+"，"+userCard.get("originalfee")+"，总计："+userCard.get("originalfee")+"元，有效期至："+userCard.get("cardend")+"，";
                    String varmsg2 = storeidOrTel.get("name")+"购买了"+"FaceBody"+userCard.get("cardname")+"，金额："+userCard.get("originalfee")+"，";

                    // 买卡通知会员
                    if (isSendOrNot("hymktz")) {
                        sendNotice(storeidOrTel(userid, "tel"), "hymktz", varmsg, "");
                    }

                    // 买卡通知员工
                    if (isSendOrNot("ygmktz")) {
                        // noticeAllStaff/31购卡续卡
                        noticeAllStaff(userid,"", "ygmktz", varmsg2, "");
                    }

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

    public boolean isSendOrNot(String str){
        Map<String,Object> map = curriculumAnalysisMapper.isSendOrNot(str);
        if (map!=null&&map.size()>0){
            if (map.get("isopen")!=null){
                return Boolean.parseBoolean(map.get("isopen").toString());
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    // 多线程启动
    public void sendNotice(String phonenum,String tmpMsg,String varmsg,String name)throws Exception{
        name = "";
        MsgParam msgParam = new MsgParam();
        if ("".equals(tmpMsg)){
            msgParam.setMsg(varmsg);
        }else{
            Map map = curriculumAnalysisMapper.tmpNotic(tmpMsg);
            String msgs = name + map.get("prenoticcontents").toString()+varmsg+map.get("lstnoticecontents").toString();
            msgParam.setMsg(msgs);
        }
        msgParam.setMobilenum(phonenum);
        msgParam.setPlatform("");

        msgParam.setLocalip("");
        msgParam.setLongitude("");
        msgParam.setLatitude("");
        msgParam.setThridplatform("");
        sendOneMessageByYunTree(msgParam);
    }

    // 获取需要发送手机号
    public String storeidOrTel(Long userid,String storeidOrTel)throws Exception {
        Map<String, Object> map = curriculumAnalysisMapper.storeidOrTel(userid.toString());
        if (map.size() > 0 && map != null) {
            if (map.get(storeidOrTel) != null) {
                return map.get(storeidOrTel).toString();
            } else {
                return "";
            }
        }
        return "";
    }

    // 发送短信
    public void noticeAllStaff(Long userid,String coachid,String tmpMsg,String varmsg,String name)throws Exception{
        List<Map<String,Object>> list = userStaffPhone(userid,coachid,tmpMsg);

        if (list !=null){
            for (Map<String,Object> map:list){
                sendNotice(storeidOrTel(Long.parseLong(map.get("userid").toString()),"tel"),tmpMsg,varmsg,name);
            }
        }
    }

    // 获取员工手机号
    public List<Map<String,Object>> userStaffPhone(Long userid,String coachid,String tmpcode)throws Exception{
        //
        List<Map<String,Object>> newList = new ArrayList<Map<String,Object>>();
        if(isSendOrNotByRole(tmpcode,"教练")){
            if (coachid.length()>0){
                Map<String,Object> map = new HashMap<String, Object>();
                map.put("userid",coachid);
                newList.add(map);
            }else {
                // 获取教练对应的roleid
                Map<String,Object> map1 = curriculumAnalysisMapper.userStaffPhone1("教练");

                // 获取符合条件的员工账号
                List<Map<String,Object>> list1 = curriculumAnalysisMapper.userStaffPhone2(curriculumAnalysisMapper.storeidOrTel(userid.toString()).get("storeid").toString(),map1.get("roleid").toString());
                newList.addAll(list1);
            }
        }
        if (isSendOrNotByRole(tmpcode,"店长")){
            // 获取店长对应的roleid
            Map<String,Object> map2 =curriculumAnalysisMapper.userStaffPhone1("店长");

            // 获取符合条件的员工账号
            List<Map<String,Object>> list2 = curriculumAnalysisMapper.userStaffPhone2(curriculumAnalysisMapper.storeidOrTel(userid.toString()).get("storeid").toString(),map2.get("roleid").toString());
            newList.addAll(list2);
        }
        if (isSendOrNotByRole(tmpcode,"管理层")){
            // 获取管理层对应的roleid
            Map<String,Object> map3 = curriculumAnalysisMapper.userStaffPhone1("管理层");

            // 获取符合条件的员工账号
            List<Map<String,Object>> list3 = curriculumAnalysisMapper.userStaffPhone2(curriculumAnalysisMapper.storeidOrTel(userid.toString()).get("storeid").toString(),map3.get("roleid").toString());
            newList.addAll(list3);
        }
        return newList;
    }

    // 获取是否发送短信通知相关人员
    public boolean isSendOrNotByRole(String tmpcode,String str)throws Exception{
        Map<String,Object> map = curriculumAnalysisMapper.isSendOrNotByRole(tmpcode);
        if (map!=null&&map.size()>0){
            if (map.get("isnoticesupervisor")!=null){
                if (str.equals("店长")){
                    return Boolean.parseBoolean(map.get("isnoticesupervisor").toString());
                }else if(str.equals("教练")){
                    return Boolean.parseBoolean(map.get("isnoticecoach").toString());
                }else {
                    return Boolean.parseBoolean(map.get("isnoticemanager").toString());
                }
            }else {
                return false;
            }
        }else {
            return false;
        }
    }

    public String sendOneMessageByYunTree(MsgParam param) throws Exception {

        LogMobileMetaEntity logMobileMetaEntity = new LogMobileMetaEntity();
        logMobileMetaEntity.setMobilenum(param.getMobilenum());
        //System.out.println("------------5.接口25 插入短信日志：insertLogMobileMeta");
        Boolean inMeta = insertLogMobileMeta(logMobileMetaEntity,param);
        Send send = new Send();
        if (inMeta){
            //System.out.println("------------6.接口25 发送短信：send.sendysMsg");
            String json = send.sendNewysMsg(param);
            JSONObject job = JSON.parseObject(json);
            //System.out.println("------------8.接口25 接收短信返回信息：json "+json);
            Map<String,Object> map = new HashMap<String,Object>();
            map.put("result",job.getString("result"));
            param.setRecivedstate(job.getString("result"));
            param.setRecivedinfos(getYsResult(Integer.parseInt(job.getString("result"))));
            //System.out.println("------------9.接口25 插入短信返回日志：insertLogMobileReceived");
            insertLogMobileReceived(param);
            return (new ObjectMapper()).writeValueAsString(map);
        }else {
            return (new ObjectMapper()).writeValueAsString("Error");
        }
    }

    public Boolean insertLogMobileMeta(LogMobileMetaEntity logMobileMetaEntity, MsgParam param) throws Exception {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        String newid = sdf2.format(new Date())+(System.currentTimeMillis()+"").substring(2, 13);
        String platform = param.getLatitude()==null?"":param.getLatitude();
        String mobilenum = param.getMobilenum()==null?"":param.getMobilenum();
        String msg = param.getMsg()==null?"":param.getMsg();
        String localip = param.getLocalip()==null?"":param.getLocalip();
        String longitude = param.getLongitude()==null?"":param.getLongitude();
        String latitude = param.getLatitude()==null?"":param.getLatitude();
        Integer a1 = curriculumAnalysisMapper.insertLogMobileMeta (newid,platform,mobilenum,msg,localip,longitude,latitude);
        System.err.println(a1);
        Thread.sleep(50);
        if (a1>0){
            return true;
        }else {
            return false;
        }
    }

    public Boolean insertLogMobileReceived(MsgParam param) throws Exception {
        SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
        String newid = sdf2.format(new Date())+(System.currentTimeMillis()+"").substring(2, 13);
        String platform = param.getLatitude()==null?"":param.getLatitude();
        String mobilenum = param.getMobilenum()==null?"":param.getMobilenum();
        String msg = param.getMsg()==null?"":param.getMsg();
        String localip = param.getLocalip()==null?"":param.getLocalip();
        String longitude = param.getLongitude()==null?"":param.getLongitude();
        String latitude = param.getLatitude()==null?"":param.getLatitude();
        String thridplatform = param.getThridplatform()==null?"":param.getThridplatform();
        String recivedstate = param.getRecivedstate()==null?"":param.getRecivedstate();
        String recivedinfos = param.getRecivedinfos()==null?"":param.getRecivedinfos();
        Integer a1 = curriculumAnalysisMapper.insertLogMobileReceived(newid,platform,mobilenum,msg,localip,longitude,latitude,thridplatform,recivedstate,recivedinfos);
        Thread.sleep(50);
        if (a1>0){
            return true;
        }else {
            return false;
        }
    }

    private  String getYsResult(int state)
    {
        String ret;
        switch (state)
        {
            case 0:
                ret = "0发送成功";
                break;
            case 101:
                ret = "无此用户";
                break;
            case 102:
                ret = "密码错";
                break;
            case 103:
                ret = "提交过快（提交速度超过流速限制）";
                break;
            case 104:
                ret = "系统忙（因平台侧原因，暂时无法处理提交的短信）";
                break;
            case 105:
                ret = "敏感短信（短信内容包含敏感词）";
                break;
            case 106:
                ret = "消息长度错（>700或<=0）";
                break;
            case 107:
                ret = "包含错误的手机号码";
                break;
            case 108:
                ret = "手机号码个数错（>50000或<=0）";
                break;
            case 109:
                ret = "无发送额度（该用户可用短信条数为0）";
                break;
            case 110:
                ret = "不在发送时间内";
                break;
            case 111:
                ret = "超出该账户当月发送额度限制";
                break;
            case 112:
                ret = "无此产品，用户没有订购该产品";
                break;
            case 113:
                ret = "extno格式错（非数字或者长度不对）";
                break;
            case 115:
                ret = "自动审核驳回";
                break;
            case 116:
                ret = "签名不合法，未带签名（用户必须带签名的前提下）";
                break;
            case 117:
                ret = "IP地址认证错,请求调用的IP地址不是系统登记的IP地址";
                break;
            case 118:
                ret = "用户没有相应的发送权限";
                break;
            case 119:
                ret = "用户已过期";
                break;
            case 120:
                ret = "内容不在白名单模板中";
                break;
            default:
                ret = "不可知异常错误";
                break;
        }
        return ret;

    }


    /*预约课程成功后发送短信给教练和会员*/
    public void SendSMS( OrdOrdercourseEntity orderCourseEntity )throws Exception{
        Long userid=Long.valueOf(orderCourseEntity.getUserid());
        String varmsg = getYMD(orderCourseEntity.getCoursedate())+orderCourseEntity.getCoursetime().toString().substring(0,5)+"-"+orderCourseEntity.getCourseendtime().toString().substring(0,5)+orderCourseEntity.getCoursename();
        String varmsg2 = orderCourseEntity.getStorename()+getYMD(orderCourseEntity.getCoursedate())+orderCourseEntity.getCoursename();
        // 通知约课会员
        if (isSendOrNot("hyyktz")) {
            sendNotice(storeidOrTel(userid, "tel"), "hyyktz", varmsg, storeidOrTel(userid, "name"));
        }
        // 通知约课员工
        if (isSendOrNot("ygyktz")) {
            // noticeAllStaff/25团课约课
            noticeAllStaff(userid,String.valueOf(orderCourseEntity.getCoachid()), "ygyktz", varmsg2, storeidOrTel(userid, "name"));
        }

//        return "";
    }

    // 获取年月日
    public String getYMD(Date date)throws Exception{
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        return now.get(Calendar.YEAR)+"年"+(now.get(Calendar.MONTH) + 1)+"月"+now.get(Calendar.DAY_OF_MONTH)+"日";
    }

    public void CancelCourseOrdersByOrderIdAndUserId(Long userid,String coachid,Long ordid)throws Exception{
        String varmsg = getYMD(new SimpleDateFormat("yyyy-MM-dd").parse(orderCourse(ordid,"coursedate")))+orderCourse(ordid,"coursetime").substring(0,5)+orderCourse(ordid,"courseendtime").substring(0,5)+orderCourse(ordid,"coursename");
        // 取消约课通知会员
        if (isSendOrNot("hyqxtz1")) {
            sendNotice(storeidOrTel(userid, "tel"), "hyqxtz1", varmsg, "");
        }
        // 取消约课通知员工
        if (isSendOrNot("ygqxtz1")) {
            // noticeAllStaff/30取消订单约课
            noticeAllStaff(userid,coachid, "ygqxtz1", varmsg, storeidOrTel(userid, "name"));
        }
    }

    // 获取需要的课程信息
    public String orderCourse(Long orderid,String course)throws Exception{
        Map<String,Object> map = ordOrdercourseMapper.selectOrderCourseByOrdid(orderid);
        if (map.get(course)!=null){
            return map.get(course).toString();
        }else {
            return "";
        }
    }
}
