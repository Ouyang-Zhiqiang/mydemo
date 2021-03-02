package com.example.backstage.crs.controller;

import com.example.backstage.crs.entity.CrdMembershipcardPurchaseEntity;
import com.example.backstage.crs.entity.MarketingSeckillBaseEntity;
import com.example.backstage.crs.entity.OrdOrdercourseEntity;
import com.example.backstage.crs.service.CurCourseService;
import com.example.backstage.crs.service.CurriculumAnalysisService;
import com.example.backstage.crs.util.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/web/ordercourse/")
public class OrdOrderCourseController {
    @Resource
    protected CurriculumAnalysisService cAnalysisService;

    /*预约课程成功后对教练和会员发送短信通知*/
    @RequestMapping(value = "SendToMembersAndCoach",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public void SendToMembersAndCoach(OrdOrdercourseEntity orderCourseEntity)throws Exception{
        cAnalysisService.SendSMS(orderCourseEntity);
    }

    /*取消约课订单*/
    @RequestMapping(value = "CancelCourseOrdersByOrderIdAndUserId",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public void CancelCourseOrdersByOrderIdAndUserId(@RequestParam("userid") String userid,@RequestParam("coachid") String coachid,@RequestParam("ordid")String ordid)throws Exception{
        cAnalysisService.CancelCourseOrdersByOrderIdAndUserId(Long.valueOf(userid),coachid,Long.valueOf(ordid));
    }

    /*购卡通知（DT）*/
    @RequestMapping(value = "BuyCardSendToDT",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public void BuyCardSendToDT(@RequestParam("cardno") String cardno,@RequestParam("userid") String userid)throws Exception{
        cAnalysisService.BuyCardSendToDT(cardno,Long.valueOf(userid));
    }

}
