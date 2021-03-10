package com.example.backstage.crs.controller;

import com.alibaba.fastjson.JSON;
import com.example.backstage.crs.entity.*;
import com.example.backstage.crs.mapper.*;
import com.example.backstage.crs.util.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/web/Appointment")
public class AppointmentController {
    @Autowired
    private UserBaseMappers userBaseMappers;
    @Autowired
    private CrdMembershipCardBaseMapper crdMembershipCardBaseMapper;
    @Autowired
    private UserStaffMappers userStaffMappers;
    @Autowired
    private CurTeamscheduleMapper curTeamscheduleMapper;
    @Autowired
    private CurPrivscheduleMapper curPrivscheduleMapper;
    @Autowired
    private OrdOrdercourseMapper ordOrdercourseMapper;

    @RequestMapping("/userByNameAndTel")
    @ResponseBody
    public String userByNameAndTel(String value){
        String[] str=value.split(" ");
        String name=str[0];
        String tel=str[1];
        return JSON.toJSONString(userBaseMappers.userByNameAndTel(name,tel));
    }

    @RequestMapping("/selectCardListByStr")
    @ResponseBody
    public String selectCardListByStr(Param param){
        return JSON.toJSONString(crdMembershipCardBaseMapper.selectCardListByStr(param.getUserid(),param.getCoursetype(),param.getCid()));
    }

    @RequestMapping("/isStaff")
    @ResponseBody
    public List<Map<String,Object>> isStaff(Param param){
        return userStaffMappers.isStaff(param.getUserid());
    }

    @RequestMapping("/activateCard")
    @ResponseBody
    public String  activateCard(CrdMembershipcardBaseEntity crdMembershipcardBaseEntity){
        int a=crdMembershipCardBaseMapper.activateCard(crdMembershipcardBaseEntity);
        return "a";
    }

    @RequestMapping("/updateReservedNumber")
    @ResponseBody
    public String  updateReservedNumber(long scheduleid, @RequestParam("traineenum") int traineenum){
        int a=curTeamscheduleMapper.updateReservedNumber(scheduleid,traineenum);
        return "a";
    }

    @RequestMapping("/updateReservedNumberPrivate")
    @ResponseBody
    public String  updateReservedNumberPrivate(long scheduleid, @RequestParam("traineenum") int traineenum){
        int a=curPrivscheduleMapper.updateReservedNumberPrivate(scheduleid,traineenum);
        return "a";
    }

    @RequestMapping("/appointmentCourse")
    @ResponseBody
    public String  appointmentCourse(OrdOrdercourseEntity ordOrdercourseEntity){
        int result=ordOrdercourseMapper.updateCurtimes(ordOrdercourseEntity.getUsabletimes(),ordOrdercourseEntity.getCardno());
        int a=ordOrdercourseMapper.appointmentCourse(ordOrdercourseEntity);
        return "a";
    }

    @RequestMapping("/selectStoreByUserid")
    @ResponseBody
    public Map<String,Object>  selectStoreByUserid(String userid){
        Map<String,Object> store=ordOrdercourseMapper.selectStoreByUserid(userid);
        return store;
    }


}
