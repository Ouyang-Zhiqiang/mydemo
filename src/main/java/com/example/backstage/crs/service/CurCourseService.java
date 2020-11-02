package com.example.backstage.crs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.backstage.crs.entity.LogMobileMetaEntity;
import com.example.backstage.crs.entity.TiceEntity;
import com.example.backstage.crs.mapper.CurCourseMapper;
import com.example.backstage.crs.util.MsgParam;
import com.example.backstage.crs.util.Param;
import com.example.backstage.crs.util.Send;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class CurCourseService {

    @Resource
    private CurCourseMapper curCourseMapper;

    /*团课总上座率*/
    public String getTotalAttendance(Param param) throws Exception {
        double totalAttendance = curCourseMapper.getTotalAttendance(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid());
        return "{" + "\"TotalAttendance\":\"" + String.format("%.2f", totalAttendance) + "%\"}";
    }

    /*分类团课上座率*/
    public String getGroupClassAttendance(Param param) throws Exception {
        double TotalAttendance = curCourseMapper.getGroupClassAttendance(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid(),param.getCid());
        return "{"+"\"TotalAttendance\":\""+String.format("%.2f",TotalAttendance)+"%\"}";
    }

    /*月时段上座率*/
    public String getMonthlyAttendance(Param param){
        String coursetime = param.getCoursetime();
        String courseendtime = param.getCourseendtime();
        if(coursetime==null||"".equals(coursetime)){
            coursetime="'00:00:00'";
        }
        if(courseendtime==null||"".equals(courseendtime)){
            courseendtime="'23:59:59'";
        }
        double monthlyAttendance = curCourseMapper.getMonthlyAttendance(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid(), coursetime, courseendtime);
        return "{"+"\"TotalAttendance\":\""+String.format("%.2f",monthlyAttendance)+"%\"}";
    }


    /*团课体验课转化率*/
    public String getTConversionRateOfGroupLessons(Param param){
        String str="T";
        return getConversionRateOfGroupLessons(str,param);
    }

    /*私教体验课转化率*/
    public String getPConversionRateOfGroupLessons(Param param){
        String str="P";
        return getConversionRateOfGroupLessons(str,param);
    }

    /*团课消课节数*/
    public String getNumberOfLessonsGroupLessons(Param param) throws Exception {
        int number = curCourseMapper.getNumberOfLessonsGroupLessons(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid());
        return "{"+"\"Tnumber\":"+number+"}";
    }


    /*私教消课节数*/
    public String getNumberOfPrivateLessons(Param param) throws Exception {
        int number = curCourseMapper.getNumberOfPrivateLessons(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid());
        return "{"+"\"Tnumber\":"+number+"}";
    }

    /*体验课转化率*/
    private String getConversionRateOfGroupLessons(String str,Param param){
        try {
            double ConversionRateOfGroupLessons = curCourseMapper.getConversionRateOfGroupLessons(str, param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid());
            return "{"+"\"ConversionRateOfGroupLessons\":\""+String.format("%.2f",ConversionRateOfGroupLessons)+"%\"}";
        }
        catch (Exception e){
            return "{"+"\"ConversionRateOfGroupLessons\":\"暂无参数\"}";
        }
    }

    // 接口42 手动注册绑定发送验证码 web/users/RegUserSendvcode.xhtml
    public String RegUserSendvcode(Param param)throws Exception{
        String result = null;
        try {
            String num = param.getPhonenum();
            String code = param.getCode();
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
            String newid = sdf2.format(new Date())+(System.currentTimeMillis()+"").substring(8, 13);
            result = "{\"phonenum\":\""+num+"\",\"code\":\""+code+"\"}";
        } catch (Exception e) {
            Send.sendError(e,"接口42 手动注册绑定发送验证码 web/users/RegUserSendvcode.xhtml");
        }
        return result;
    }

    public String sendOneMessageByYunTree(MsgParam param) throws Exception {
        //System.out.println("------------5.接口25 插入短信日志：insertLogMobileMeta");
            Send send = new Send();
            //System.out.println("------------6.接口25 发送短信：send.sendysMsg");
            String json = send.sendNewysMsg(param);
            //System.out.println("------------8.接口25 接收短信返回信息：json "+json);
            return "ok";

    }

    public void sendNotice(String phonenum,String varmsg)throws Exception{
        MsgParam msgParam = new MsgParam();
        msgParam.setMsg(varmsg);
        msgParam.setMobilenum(phonenum);
        sendOneMessageByYunTree(msgParam);
    }

    public String  getcourseinformation(Param param){
        System.err.println(param.getScheduleid());
        List<Map> maps = curCourseMapper.getcourseinformation(param.getScheduleid());
        return JSON.toJSONStringWithDateFormat(maps,"yyyy-MM-dd HH:mm");
    }

    public String privatelessonschedule(Param param){
        List<Map> maps = curCourseMapper.privatelessonschedule(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid(), param.getCoachid());
        for (Map map : maps) {
            map.put("reservednumber",curCourseMapper.sijiaoyuyuerenshu(map.get("scheduleid").toString()));
            map.put("scheduleid",map.get("scheduleid").toString());
        }
        return JSON.toJSONString(maps);
    }

    public String goukasongjifen(Param param) throws UnknownHostException {
        InetAddress address = InetAddress.getLocalHost();
        String createdip = address.getHostAddress();
        String remarks="购买"+param.getRemarks()+"赠积分";
        curCourseMapper.xiugaijifen(param.getPoints(),param.getUserid());
        curCourseMapper.goukasongjifen(param.getUserid(),param.getPoints(),remarks,param.getCreatedby(),param.getCreatedname(),createdip);
        return "ok";
    }

    public String ticeliebiao(Param param){
        List<Map> maps = curCourseMapper.ticeliebiao(param.getUserid());
        List<Map> list =new LinkedList<>();
        Map map=new HashMap();
        for (int i = 0; i < maps.size(); i++) {
            map.put(maps.get(i).get("testname"),maps.get(i).get("testvalue"));
            if(i==maps.size()-1){
                map.put("时间",maps.get(i).get("createdon"));
                list.add(map);
                break;
            }
            if(!maps.get(i).get("createdon").equals(maps.get(i+1).get("createdon"))){
                map.put("时间",maps.get(i).get("createdon"));
                list.add(map);
                map=new HashMap();
            }
        }
        System.err.println(list);
        return JSON.toJSONStringWithDateFormat(list,"yyyy-MM-dd HH:mm");
    }

    public void addtice(Param param,TiceEntity tice){
        System.err.println(tice);
        System.err.println(param.getUserid());
        System.err.println(param.getCreatedby());
        System.err.println(param.getCreatedname());
    }



}