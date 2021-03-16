package com.example.backstage.crs.controller;

import com.alibaba.fastjson.JSON;
import com.example.backstage.crs.entity.*;
import com.example.backstage.crs.mapper.*;
import com.example.backstage.crs.util.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
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
    @Autowired
    private SPMIntegralGoodsBaseMapper spmIntegralGoodsBaseMapper;

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
    public int  updateReservedNumber(long scheduleid, @RequestParam("traineenum") int traineenum){
        int a=curTeamscheduleMapper.updateReservedNumber(scheduleid,traineenum);
        return a;
    }

    @RequestMapping("/updateReservedNumberPrivate")
    @ResponseBody
    public int  updateReservedNumberPrivate(long scheduleid, @RequestParam("traineenum") int traineenum){
        int a=curPrivscheduleMapper.updateReservedNumberPrivate(scheduleid,traineenum);
        return a;
    }

    @Transactional(rollbackFor= { Exception.class })
    @RequestMapping("/appointmentCourse")
    @ResponseBody
    public int  appointmentCourse(OrdOrdercourseEntity ordOrdercourseEntity){
        int a=0;
        try{
        int result=ordOrdercourseMapper.updateCurtimes(ordOrdercourseEntity.getUsabletimes(),ordOrdercourseEntity.getCardno());
        a=ordOrdercourseMapper.appointmentCourse(ordOrdercourseEntity);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//如果a抛了异常, //result是会回滚的
        }
        return a;
    }

    @RequestMapping("/selectStoreByUserid")
    @ResponseBody
    public Map<String,Object>  selectStoreByUserid(String userid){
        Map<String,Object> store=ordOrdercourseMapper.selectStoreByUserid(userid);
        return store;
    }

    @RequestMapping("/getAllTeam")
    @ResponseBody
    public List<CurTeamscheduleEntity>  getAllTeam(Param param){
        int counts=curTeamscheduleMapper.getAllTeamCounts(param);
        int pages=Integer.parseInt(param.getLimit())*Integer.parseInt(param.getPage());
        param.setPage(String.valueOf(pages));
        List<CurTeamscheduleEntity> store=curTeamscheduleMapper.getAllTeam(param);
        store.get(0).setCounts(counts);
        return store;
    }


    @RequestMapping("/getAllPrivate")
    @ResponseBody
    public List<CurTeamscheduleEntity>  getAllPrivate(Param param){
        int counts=curTeamscheduleMapper.getAllPrivateCounts(param);
        int pages=Integer.parseInt(param.getLimit())*Integer.parseInt(param.getPage());
        param.setPage(String.valueOf(pages));
        List<CurTeamscheduleEntity> store=curTeamscheduleMapper.getAllPrivate(param);
        store.get(0).setCounts(counts);
        return store;
    }


    /*取消时修改课程的已预约人数*/
    @RequestMapping("/cancelTeam")
    @ResponseBody
    public int  cancelTeam(Param param){
        int counts=curTeamscheduleMapper.cancelTeam(param.getScheduleid(),param.getTraineenum());
        return counts;
    }

    @RequestMapping("/cancelPrivate")
    @ResponseBody
    public int  cancelPrivate(Param param){
        int counts=curPrivscheduleMapper.cancelPrivate(param.getScheduleid(),param.getTraineenum());
        return counts;
    }

    /*取消时修改卡的次数*/
    @RequestMapping("/cancelUpdateCardCurtimes")
    @ResponseBody
    public int  cancelUpdateCardCurtimes(Param param){
        int a=ordOrdercourseMapper.updateOrdstate(param);
        int counts=ordOrdercourseMapper.cancelUpdateCardCurtimes(param.getCardno(),param.getTraineenum());
        return counts;
    }

    /*积分限购时修改已购数量*/
    @RequestMapping("/updateIntegralGoodsPurchased")
    @ResponseBody
    public int  updateIntegralGoodsPurchased(String goodscode){
        int counts=spmIntegralGoodsBaseMapper.updateIntegralGoodsPurchased(goodscode);
        return counts;
    }
}
