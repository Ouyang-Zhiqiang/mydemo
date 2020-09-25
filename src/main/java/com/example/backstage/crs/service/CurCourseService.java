package com.example.backstage.crs.service;

import com.example.backstage.crs.mapper.CurCourseMapper;
import com.example.backstage.crs.util.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class CurCourseService {

    @Resource
    private CurCourseMapper curCourseMapper;

    /*
     *团课总上座率
     * */
    public String getTotalAttendance(Param param) throws Exception {
        double totalAttendance = curCourseMapper.getTotalAttendance(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid());
        return "{" + "\"TotalAttendance\":\"" + String.format("%.2f", totalAttendance) + "%\"}";
    }

    /*
     *分类团课上座率
     * */
    public String getGroupClassAttendance(Param param) throws Exception {
        double TotalAttendance = curCourseMapper.getGroupClassAttendance(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid(),param.getCid());
        return "{"+"\"TotalAttendance\":\""+String.format("%.2f",TotalAttendance)+"%\"}";
    }

    /*
     *月时段上座率
     * */
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


    /*
     *团课体验课转化率
     * */
    public String getTConversionRateOfGroupLessons(Param param){
        String str="T";
        return getConversionRateOfGroupLessons(str,param);
    }

    /*
     *私教体验课转化率
     * */
    public String getPConversionRateOfGroupLessons(Param param){
        String str="P";
        return getConversionRateOfGroupLessons(str,param);
    }

    /*
     *团课消课节数
     * */
    public String getNumberOfLessonsGroupLessons(Param param) throws Exception {
        int number = curCourseMapper.getNumberOfLessonsGroupLessons(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid());
        return "{"+"\"Tnumber\":"+number+"}";
    }


    /*
     *私教消课节数
     * */
    public String getNumberOfPrivateLessons(Param param) throws Exception {
        int number = curCourseMapper.getNumberOfPrivateLessons(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid());
        return "{"+"\"Tnumber\":"+number+"}";
    }

    /*
     * 体验课转化率
     * */
    private String getConversionRateOfGroupLessons(String str,Param param){
        try {
            double ConversionRateOfGroupLessons = curCourseMapper.getConversionRateOfGroupLessons(str, param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid());
            return "{"+"\"ConversionRateOfGroupLessons\":\""+String.format("%.2f",ConversionRateOfGroupLessons)+"%\"}";
        }
        catch (Exception e){
            return "{"+"\"ConversionRateOfGroupLessons\":\"暂无参数\"}";
        }
    }

}