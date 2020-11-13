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

    public String xiaochengxukeyonghuiyanka(Param param) throws Exception {
        String result = "";
        List<Map<String, Object>> list = curCourseMapper.xiaochengxukeyonghuiyanka(param.getUserid(), param.getScheduleid());
        if(list!=null&&list.size()>0){
            if (list.get(0)!=null&&list.get(0).size()>0){
                result += courseAndCardJsonStr("", list.get(0));
            }else {
                result = "[]";
            }
        }else {
            result = "[]";
        }
        System.err.println(result);
        return result;
    }

    public String courseAndCardJsonStr(String result,Map<String,Object> map)throws Exception{

        String str = "[";
        if("".equals(map.get("cards")+"")||"null".equals(map.get("cards")+"")||"[]".equals(map.get("cards")+"")){
            str =  "[]";
        }else {
            String[] strings = map.get("cards").toString().replace("[","").replace("]","").split(",\\{");
            for (String strs:strings){
                if (strs.startsWith("{")){

                }else {
                    strs = "{" + strs;
                }
                JSONObject json = JSON.parseObject(strs);
                String f1 = json.getString("f1");
                String f2 = json.getString("f2");
                String f3 = json.getString("f3");
                String f4 = json.getString("f4");
                String f5 = json.getString("f5");
                String f6 = json.getString("f6");

                if (f6.equalsIgnoreCase("P")){

                    Map<String,Object> map1 = curCourseMapper.selectcardtime(f1);
                    Long _f4 = 0l;
                    if (map1.get("cardend")!=null && (map1.get("cardend")+"").length()>0){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date date = new Date();
                        String begindate = sdf.format(date);
                        _f4 = getDaySub(begindate,map1.get("cardend").toString()) ;
                        if (_f4<=0){
                            _f4 = 0l;
                        }
                        f4 = _f4 + "";
                    }else {
                        f4 = _f4 + "";
                    }
                }
                str += "{\"f1\":\""+f1+"\",\"f2\":\""+f2+"\",\"f3\":"+httpResurl(f3)+",\"f4\":"+f4+",\"f5\":"+f5+",\"f6\":\""+f6+"\"},";
            }
            str = str.substring(0, str.length() - 1) +"]";
        }
        String schedulebegin = "";
        String scheduleend = "";
        if (map.get("schedulebegin")!=null){
            schedulebegin = map.get("schedulebegin").toString().substring(0,map.get("schedulebegin").toString().length()-3);
            scheduleend = map.get("scheduleend").toString().substring(0,map.get("scheduleend").toString().length()-3);
        }
        result += "{\"scheduleid\":" + "\"" + map.get("scheduleid").toString() + "\""
                + ",\"courseid\":"  +  "\"" + map.get("courseid").toString() + "\""
                + ",\"coursename\":" +  "\"" + map.get("coursename").toString() + "\""
                + ",\"schedulebegin\":" + "\"" + schedulebegin + "\""
                + ",\"scheduleend\":"+ "\"" + scheduleend + "\""
                + ",\"resurl\":" + httpResurl(isMapKey(map,"resurl"))
                + ",\"labels\":" + "\"" + map.get("labels").toString() + "\""
                + ",\"coachid\":" + "\"" + map.get("coachid").toString() + "\""
                + ",\"coachname\":" + "\"" + map.get("coachname").toString() + "\""
                + ",\"cards\":"  + str
                + "}";
        return result;
    }

    public Long getDaySub(String beginDateStr,String endDateStr)throws Exception {
        Long day = 0l;
        java.text.SimpleDateFormat format = new java.text.SimpleDateFormat("yyyy-MM-dd");

        Date beginDate = format.parse(beginDateStr);
        Date endDate= format.parse(endDateStr);
        day = (endDate.getTime()-beginDate.getTime())/(24*60*60*1000l);
        return day + 1;
    }

    public static String httpResurl(String resurl){
        if (resurl==null){
            return "\"\"";
        }
        if (resurl.toLowerCase().indexOf("http://")==0||resurl.toLowerCase().indexOf("https://")==0){
            return "\""+resurl+"\"";
        }else {
            String http = "https://res.facebodyfitness.com/facebody";
            if (resurl != null) {
                if (resurl.length() > 0) {
                    return "\"" + http + resurl + "\"";
                } else {
                    return "\"\"";
                }
            } else {
                return "\"\"";
            }
        }
    }

    public static String isMapKey(Map<String,Object> map,String key){
        if (map.get(key)!=null){
            return map.get(key).toString();
        }else {
            return "";
        }
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

    public void inserttice(TiceEntity tice){
            curCourseMapper.inserttice(tice.getUserid(),"11","体脂率","%",tice.getTizhi(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"7","身高","cm",tice.getShengao(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"8","体重","kg",tice.getTizhong(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"10","基础代谢","cal",tice.getJichudaixie(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"7","大腿围L","cal",tice.getDatuiweil(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"20","大腿围R","cal",tice.getDatuiweir(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"5","小腿L","cal",tice.getXiaotuil(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"6","小腿R","cal",tice.getXiaotuir(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"8","手臂L","cal",tice.getShoubil(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"9","手臂R","cal",tice.getShoubir(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"14","胸围","cal",tice.getXiongwei(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"12","脂肪含量","cal",tice.getZhifang(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"13","骨骼肌含量","cal",tice.getGugeji(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"15","腰围","cal",tice.getYaowei(),tice.getCreatedby(),tice.getCreatedname());
            curCourseMapper.inserttice(tice.getUserid(),"16","臀围","cal",tice.getTunwei(),tice.getCreatedby(),tice.getCreatedname());
    }

    public void deletetice(String time,String userid){
        curCourseMapper.deletetice(time,userid);
    }

    public void updatetice(TiceEntity tice){
        
        List<Map> maps = curCourseMapper.selecttice(tice.getCreatedon(), tice.getUserid());
        for (Map map : maps) {
            if(map.get("testname").equals("体脂率")){
                curCourseMapper.updatetice(tice.getTizhi(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"体脂率");
            }
            if(map.get("testname").equals("身高")){
                curCourseMapper.updatetice(tice.getShengao(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"身高");
            }
            if(map.get("testname").equals("体重")){
                curCourseMapper.updatetice(tice.getTizhong(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"体重");
            }
            if(map.get("testname").equals("基础代谢")){
                curCourseMapper.updatetice(tice.getJichudaixie(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"基础代谢");
            }
            if(map.get("testname").equals("大腿围L")){
                curCourseMapper.updatetice(tice.getDatuiweil(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"大腿围L");
            }
            if(map.get("testname").equals("大腿围R")){
                curCourseMapper.updatetice(tice.getDatuiweir(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"大腿围R");
            }
            if(map.get("testname").equals("小腿L")){
                curCourseMapper.updatetice(tice.getXiaotuil(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"小腿L");
            }
            if(map.get("testname").equals("小腿R")){
                curCourseMapper.updatetice(tice.getXiaotuir(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"小腿R");
            }
            if(map.get("testname").equals("手臂L")){
                curCourseMapper.updatetice(tice.getShoubil(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"手臂L");
            }
            if(map.get("testname").equals("手臂R")){
                curCourseMapper.updatetice(tice.getShoubir(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"手臂R");
            }
            if(map.get("testname").equals("胸围")){
                curCourseMapper.updatetice(tice.getXiongwei(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"胸围");
            }
            if(map.get("testname").equals("脂肪含量")){
                curCourseMapper.updatetice(tice.getZhifang(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"脂肪含量");
            }
            if(map.get("testname").equals("骨骼肌含量")){
                curCourseMapper.updatetice(tice.getGugeji(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"骨骼肌含量");
            }
            if(map.get("testname").equals("腰围")){
                curCourseMapper.updatetice(tice.getYaowei(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"腰围");
            }
            if(map.get("testname").equals("臀围")){
                curCourseMapper.updatetice(tice.getTunwei(),tice.getCreatedby(),tice.getCreatedname(),tice.getUserid(),tice.getCreatedon(),"臀围");
            }
        }
    }


}