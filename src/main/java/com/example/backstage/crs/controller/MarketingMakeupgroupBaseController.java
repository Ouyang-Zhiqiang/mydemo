package com.example.backstage.crs.controller;

import com.alibaba.fastjson.JSON;
import com.example.backstage.crs.common.Constant;
import com.example.backstage.crs.entity.*;
import com.example.backstage.crs.mapper.CrdMembershipcardPurchaseMapper;
import com.example.backstage.crs.service.BackStageStatisticsService;
import com.example.backstage.crs.service.MarketingMakeupgroupBaseService;
import com.example.backstage.crs.service.MarketingSeckillBaseSerice;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

@Controller
@RequestMapping("/web/marketingMakeupgroupBase/")
public class MarketingMakeupgroupBaseController {

    @Autowired
    protected MarketingMakeupgroupBaseService marketingMakeupgroupBaseService;
    @Autowired
    protected CrdMembershipcardPurchaseMapper crdMembershipcardPurchaseMapper;
    @Autowired
    protected MarketingSeckillBaseSerice marketingSeckillBaseSerice;
//    @Autowired
//    protected FbsDao fbsDao;
    @Autowired
    protected BackStageStatisticsService backStageStatisticsService;

    /*新增团购活动*/
    @RequestMapping(value = "addMarketingMakeupgroupBase",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String addMarketingMakeupgroupBase(MarketingMakeupgroupBaseEntity marketingMakeupgroupBaseEntity){
        String result=marketingMakeupgroupBaseService.insertMarketingMakeupgroupBase(marketingMakeupgroupBaseEntity);
        return result;
    }

    /*修改团购活动*/
    @RequestMapping(value = "updateMarketingMakeupgroupBase",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String updateMarketingMakeupgroupBase(MarketingMakeupgroupBaseEntity marketingMakeupgroupBaseEntity){
        String result=marketingMakeupgroupBaseService.updateMarketingMakeupgroupBase(marketingMakeupgroupBaseEntity);
        return result;
    }


    /*团购活动列表*/
    @RequestMapping(value = "marketingMakeupgroupBaseMapperList",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String marketingMakeupgroupBaseMapperList(@RequestParam("limit")Integer limit , @RequestParam("page") Integer page ){
        List<Map<String,String>> result=marketingMakeupgroupBaseService.marketingMakeupgroupBaseMapperList(limit,page);
        int total=marketingMakeupgroupBaseService.selectTotalList();
        for (Map<String,String> maps:result){
            maps.put("total",String.valueOf(total));
        }
        return JSON.toJSONStringWithDateFormat(result,"yyyy-MM-dd").toString();
    }

    /*查询当前团购活动团购记录表*/
    @RequestMapping(value = "selectMakeupgroupBaseRecord",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String selectMakeupgroupBaseRecord(@RequestParam("limit")Integer limit , @RequestParam("page") Integer page , @RequestParam("groupid") Integer groupid){
        List<MarketingMakeupgroupRecordEntity> result=marketingMakeupgroupBaseService.selectMakeupgroupBaseRecord(limit,page,groupid);
        int count=marketingMakeupgroupBaseService.selectMakeupgroupTotal(groupid);
        for(MarketingMakeupgroupRecordEntity entity:result){
            entity.setTotal(count);
        }
        return JSON.toJSONStringWithDateFormat(result,"yyyy-MM-dd").toString();
    }

    /*支付后团购活动*/
    @RequestMapping(value = "makeupgroupBase",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public Object marketingMakeupgroupBase(MarketingMakeupgroupRecordEntity marketingMakeupgroupRecordEntity, @Param("sfprice") BigDecimal sfprice, @Param("openid") String openid, HttpServletResponse response){
        /*支付时新增*/
        String result= null;
        try {
            result = marketingMakeupgroupBaseService.makeupgroupSuccess(marketingMakeupgroupRecordEntity, sfprice, openid);
            if (!result.equals("false") && result != "false") {
                //sb为微信返回的xml
                String notityXml = result;
                String resXml = "";
                Gson gson = new Gson();
                Map<String, Object> map = new HashMap<String, Object>();
                map = gson.fromJson(notityXml, map.getClass());
                String returnCode = (String) map.get("return_code");
                System.out.println("回调函数启动:" + returnCode);
                //获取商户订单号
                String recordid = (String) map.get("recordid");
                /*活动规定成团人数*/
                Integer groupCount =Integer.parseInt(map.get("groupCount").toString());
                /*该团已有人数*/
                Integer number = Integer.parseInt( map.get("number").toString());
                /*团号*/
                String groupnumber = (String) map.get("groupnumber");
                if ("SUCCESS".equals(returnCode)) {
                    int groupid = Integer.parseInt(map.get("groupid").toString());
//                    MarketingMakeupgroupBaseEntity marketingMakeupgroupBaseEntity = marketingMakeupgroupBaseService.selectmarketingMakeupgroupBaseById(groupid);

                    /** 此处添加自己的业务逻辑代码start **/
                    if (groupCount == number) {

                        /*支付成功且满足活动条件人数，修改状态为拼团成功并且绑卡*/
                        int a = marketingMakeupgroupBaseService.updateStateByGroupNumber(groupnumber, Constant.PAYSTAMENT_INGROUNP);

                        String id = UUID.randomUUID().toString();
                        //替换uuid中的"-"
                        id = id.replace("-", "");
                        /*会员卡类型*/
                        Long cardid = Long.parseLong(map.get("cardid").toString());
                        String cardname = map.get("cardname").toString();
                        List<Map<String, Object>> useridlist = crdMembershipcardPurchaseMapper.selectThirdaccount(openid);
                        /*查询当前会员卡类型详情*/
                        CrdMembershipcardcategoryBase crdMembershipcardcategoryBase = marketingSeckillBaseSerice.selectCrdMembershipcardcategoryBaseByCardId(cardid);
                        /*查询梯度表中当前会员卡类型的详情*/
                        CrdMembershipcardcategoryTypecardEntity crdMembershipcardcategoryTypecardEntity = marketingSeckillBaseSerice.selectMembershipCardCategoryByCardId(cardid);
                        /*新增会员卡*/
                        CrdMembershipcardBaseEntity crdMembershipcardBaseEntity = new CrdMembershipcardBaseEntity();
                        crdMembershipcardBaseEntity.setCardno(id);
                        crdMembershipcardBaseEntity.setCardid(cardid.toString());
                        crdMembershipcardBaseEntity.setCardname(cardname);
                        crdMembershipcardBaseEntity.setCardbegin(new Date());
                        crdMembershipcardBaseEntity.setCardtype(crdMembershipcardcategoryBase.getCardtype());
                        crdMembershipcardBaseEntity.setIsfree(crdMembershipcardcategoryBase.getIsfree());
                        crdMembershipcardBaseEntity.setCurtimes(crdMembershipcardcategoryTypecardEntity.getTimes());
                        crdMembershipcardBaseEntity.setIsopen(false);
                        crdMembershipcardBaseEntity.setPeriodvalidity(crdMembershipcardcategoryTypecardEntity.getPeriodvalidity());
                        crdMembershipcardBaseEntity.setOriginalfee(crdMembershipcardcategoryTypecardEntity.getFee().toString());
                        crdMembershipcardBaseEntity.setTypeid(crdMembershipcardcategoryTypecardEntity.getTid());
                        crdMembershipcardBaseEntity.setPoints(crdMembershipcardcategoryTypecardEntity.getPoints());
                        crdMembershipcardBaseEntity.setTotalfee(crdMembershipcardcategoryTypecardEntity.getFee().toString());
                        crdMembershipcardBaseEntity.setTotaltimes(crdMembershipcardcategoryTypecardEntity.getTimes());
                        crdMembershipcardBaseEntity.setCreatedby(map.get("createdby").toString());
                        crdMembershipcardBaseEntity.setLastedby(map.get("lastedby").toString());
                        crdMembershipcardBaseEntity.setLastedname(map.get("lastedname").toString());
                        crdMembershipcardBaseEntity.setCreatedname(cardname);
                        int rs = marketingSeckillBaseSerice.insertCrdMembershipcardBase(crdMembershipcardBaseEntity);


                        /*新增会员关系表*/
                        CrdMembershipcardUserEntity crdMembershipcardUserEntity = new CrdMembershipcardUserEntity();
                        crdMembershipcardUserEntity.setCardno(crdMembershipcardBaseEntity.getCardno());
                        crdMembershipcardUserEntity.setIspoint(true);//默认为1
                        crdMembershipcardUserEntity.setUserid(Long.valueOf(useridlist.get(0).get("userid").toString()));
                        crdMembershipcardUserEntity.setCreatedby(map.get("createdby").toString());
                        crdMembershipcardUserEntity.setLastedby(map.get("lastedby").toString());
                        crdMembershipcardUserEntity.setLastedname(map.get("lastedname").toString());
                        crdMembershipcardUserEntity.setCreatedname(cardname);
                        crdMembershipcardUserEntity.setIsremoved(false);
                        int sc = marketingSeckillBaseSerice.insertMembershipCardUser(crdMembershipcardUserEntity);


                        List<MarketingMakeupgroupRecordEntity> list = marketingMakeupgroupBaseService.selectMakeupgroupBaseByGroupNumber(groupnumber);
                        for (MarketingMakeupgroupRecordEntity makeupgroupRecordEntity : list) {
                            CrdMembershipcardPurchaseEntity crdMembershipcardPurchaseEntity = new CrdMembershipcardPurchaseEntity();
//                          CrdMembershipcardBaseEntity crdMembershipcardBaseEntity=marketingMakeupgroupBaseService.selectCrdMembershipcardBaseByCardNo(id);
                            crdMembershipcardPurchaseEntity.setGroupid((int) makeupgroupRecordEntity.getGroupid());
                            crdMembershipcardPurchaseEntity.setPayment(Constant.PAYMENTMETHOD_ONLINE);//线上支付
                            crdMembershipcardPurchaseEntity.setCardid(Long.parseLong(map.get("cardid").toString()));
                            crdMembershipcardPurchaseEntity.setCardname(map.get("cardname").toString());
                            crdMembershipcardPurchaseEntity.setCardno(id);
                            crdMembershipcardPurchaseEntity.setStoreid(Long.parseLong(map.get("storeid").toString()));
                            crdMembershipcardPurchaseEntity.setStorename(map.get("storename").toString());
                            crdMembershipcardPurchaseEntity.setBuytimes(crdMembershipcardBaseEntity.getTimes());
                            crdMembershipcardPurchaseEntity.setPoints(crdMembershipcardBaseEntity.getPoints());
                            crdMembershipcardPurchaseEntity.setBuytype(Constant.FIRSTTIME);
                            crdMembershipcardPurchaseEntity.setSellingfee(map.get("sellingfee").toString());
                            crdMembershipcardPurchaseEntity.setSalerid(Long.parseLong(map.get("salerid").toString()));
                            crdMembershipcardPurchaseEntity.setSalername(map.get("salername").toString());
                            crdMembershipcardPurchaseEntity.setRemarks(map.get("remarks").toString());
                            crdMembershipcardPurchaseEntity.setCreatedon(new Date());
                            crdMembershipcardPurchaseEntity.setCreatedname(map.get("createdname").toString());
                            crdMembershipcardPurchaseEntity.setCreatedip(map.get("createdip").toString());
                            crdMembershipcardPurchaseEntity.setCreatedby(map.get("createdby").toString());
                            crdMembershipcardPurchaseEntity.setLastedby(map.get("lastedby").toString());
                            crdMembershipcardPurchaseEntity.setLastedip(map.get("lastedip").toString());
                            crdMembershipcardPurchaseEntity.setLastedname(map.get("lastedname").toString());
                            crdMembershipcardPurchaseEntity.setLastedon(new Date());
                            int success = crdMembershipcardPurchaseMapper.insertCrdMembershipCardPurchase(crdMembershipcardPurchaseEntity);
                        }
                    } else {
                        int success = marketingMakeupgroupBaseService.updateStateByRecordId(recordid, Constant.PAYSTAMENT_INGROUNP);
                    }


                    /** 此处添加自己的业务逻辑代码end **/
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

                } else {
                    //TODO 暂时不清楚会发生这种情况
                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                            + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
                }

                BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
                out.write(resXml.getBytes());
                out.flush();
                out.close();
                return resXml;
            } else{
                return false;
            }
            } catch(Exception e){
                e.printStackTrace();
            }

        return result;
    }

    /*团购支付后的回调方法*/
@RequestMapping(value = "/GroupPay/return",produces = {"application/json; ~charset=utf-8"}, method = RequestMethod.POST)
    public void wxPayNotify(HttpServletRequest request, HttpServletResponse response) throws Exception {
        System.out.println("回调函数启动");
        BufferedReader br = new BufferedReader(new InputStreamReader(request.getInputStream(), "UTF-8"));
        String line = null;
        StringBuilder sb = new StringBuilder();
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        //sb为微信返回的xml
        String notityXml = sb.toString();
        String resXml = "";
        Gson gson = new Gson();
        Map<String, Object> map = new HashMap<String, Object>();
        map = gson.fromJson(notityXml, map.getClass());
        String returnCode = (String) map.get("return_code");
        System.out.println("回调函数启动:"+returnCode);
        //获取活动号
        Integer recordid=(Integer)map.get("recordid");
        /*活动规定成团人数*/
        Integer groupCount =(Integer)map.get("groupCount");
        /*该团已有人数*/
        Integer number=(Integer)map.get("number");
        /*团号*/
        String groupnumber=(String)map.get("groupnumber");
        if ("SUCCESS".equals(returnCode) ) {
            /** 此处添加自己的业务逻辑代码start **/
            if(groupCount==number){
                /*支付成功且满足活动条件人数，修改状态为拼团成功并且绑卡*/
                int a=marketingMakeupgroupBaseService.updateStateByGroupNumber(groupnumber,Constant.PAYSTAMENT_INGROUNP);

                String id= UUID.randomUUID().toString();
                //替换uuid中的"-"
                id=id.replace("-", "");
                /*会员卡类型*/
                Long cardid=Long.parseLong(map.get("cardid").toString());
                String cardname=map.get("cardname").toString();
                String tel=map.get("tel").toString();
                /*根据手机号查询userid*/
                String userid=marketingSeckillBaseSerice.selectUserIdByPhone(tel);

                /*查询当前会员卡类型详情*/
                CrdMembershipcardcategoryBase crdMembershipcardcategoryBase=marketingSeckillBaseSerice.selectCrdMembershipcardcategoryBaseByCardId(cardid);
                /*查询梯度表中当前会员卡类型的详情*/
                CrdMembershipcardcategoryTypecardEntity crdMembershipcardcategoryTypecardEntity=marketingSeckillBaseSerice.selectMembershipCardCategoryByCardId(cardid);
                /*新增会员卡*/
                CrdMembershipcardBaseEntity crdMembershipcardBaseEntity=new CrdMembershipcardBaseEntity();
                crdMembershipcardBaseEntity.setCardno(id);
                crdMembershipcardBaseEntity.setCardid(cardid.toString());
                crdMembershipcardBaseEntity.setCardname(cardname);
                crdMembershipcardBaseEntity.setCardbegin(new Date());
                crdMembershipcardBaseEntity.setCardtype(crdMembershipcardcategoryBase.getCardtype());
                crdMembershipcardBaseEntity.setIsfree(crdMembershipcardcategoryBase.getIsfree());
                crdMembershipcardBaseEntity.setCurtimes(crdMembershipcardcategoryTypecardEntity.getTimes());
                crdMembershipcardBaseEntity.setIsopen(false);
                crdMembershipcardBaseEntity.setPeriodvalidity(crdMembershipcardcategoryTypecardEntity.getPeriodvalidity());
                crdMembershipcardBaseEntity.setOriginalfee(crdMembershipcardcategoryTypecardEntity.getFee().toString());
                crdMembershipcardBaseEntity.setTypeid(crdMembershipcardcategoryTypecardEntity.getTid());
                crdMembershipcardBaseEntity.setPoints(crdMembershipcardcategoryTypecardEntity.getPoints());
                crdMembershipcardBaseEntity.setTotalfee(crdMembershipcardcategoryTypecardEntity.getFee().toString());
                crdMembershipcardBaseEntity.setTotaltimes(crdMembershipcardcategoryTypecardEntity.getTimes());
                crdMembershipcardBaseEntity.setCreatedby(map.get("createdby").toString());
                crdMembershipcardBaseEntity.setLastedby(map.get("lastedby").toString());
                crdMembershipcardBaseEntity.setLastedname(map.get("lastedname").toString());
                crdMembershipcardBaseEntity.setCreatedname(cardname);
                int rs=marketingSeckillBaseSerice.insertCrdMembershipcardBase(crdMembershipcardBaseEntity);


                /*新增会员关系表*/
                CrdMembershipcardUserEntity crdMembershipcardUserEntity=new CrdMembershipcardUserEntity();
                crdMembershipcardUserEntity.setCardno(crdMembershipcardBaseEntity.getCardno());
                crdMembershipcardUserEntity.setIspoint(true);//默认为1
                crdMembershipcardUserEntity.setUserid(Long.valueOf(userid));
                crdMembershipcardUserEntity.setCreatedby(map.get("createdby").toString());
                crdMembershipcardUserEntity.setLastedby(map.get("lastedby").toString());
                crdMembershipcardUserEntity.setLastedname(map.get("lastedname").toString());
                crdMembershipcardUserEntity.setCreatedname(cardname);
                crdMembershipcardUserEntity.setIsremoved(false);
                int sc=marketingSeckillBaseSerice.insertMembershipCardUser(crdMembershipcardUserEntity);



                List<MarketingMakeupgroupRecordEntity> list= marketingMakeupgroupBaseService.selectMakeupgroupBaseByGroupNumber(groupnumber);
                for(MarketingMakeupgroupRecordEntity makeupgroupRecordEntity:list){
                    CrdMembershipcardPurchaseEntity crdMembershipcardPurchaseEntity=new CrdMembershipcardPurchaseEntity();
//                    CrdMembershipcardBaseEntity crdMembershipcardBaseEntity=marketingMakeupgroupBaseService.selectCrdMembershipcardBaseByCardNo(id);
                    crdMembershipcardPurchaseEntity.setGroupid((int) makeupgroupRecordEntity.getGroupid());
                    crdMembershipcardPurchaseEntity.setPayment(Constant.PAYMENTMETHOD_ONLINE);//线上支付
                    crdMembershipcardPurchaseEntity.setCardid(Long.parseLong(map.get("cardid").toString()));

                    crdMembershipcardPurchaseEntity.setCardname(map.get("cardname").toString());
                    crdMembershipcardPurchaseEntity.setCardno(id);
                    crdMembershipcardPurchaseEntity.setStoreid(Long.parseLong(map.get("storeid").toString()));
                    crdMembershipcardPurchaseEntity.setStorename(map.get("storename").toString());
                    crdMembershipcardPurchaseEntity.setBuytimes(crdMembershipcardBaseEntity.getTimes());
                    crdMembershipcardPurchaseEntity.setPoints(crdMembershipcardBaseEntity.getPoints());
                    crdMembershipcardPurchaseEntity.setBuytype(Constant.FIRSTTIME);
                    crdMembershipcardPurchaseEntity.setSellingfee(map.get("sellingfee").toString());
                    crdMembershipcardPurchaseEntity.setSalerid(Long.parseLong(map.get("salerid").toString()));
                    crdMembershipcardPurchaseEntity.setSalername(map.get("salername").toString());
                    crdMembershipcardPurchaseEntity.setRemarks(map.get("remarks").toString());
                    crdMembershipcardPurchaseEntity.setCreatedon(new Date());
                    crdMembershipcardPurchaseEntity.setCreatedname(map.get("createdname").toString());
                    crdMembershipcardPurchaseEntity.setCreatedip(map.get("createdip").toString());
                    crdMembershipcardPurchaseEntity.setCreatedby(map.get("createdby").toString());
                    crdMembershipcardPurchaseEntity.setLastedby(map.get("lastedby").toString());
                    crdMembershipcardPurchaseEntity.setLastedip(map.get("lastedip").toString());
                    crdMembershipcardPurchaseEntity.setLastedname(map.get("lastedname").toString());
                    crdMembershipcardPurchaseEntity.setLastedon(new Date());
                    int success=crdMembershipcardPurchaseMapper.insertCrdMembershipCardPurchase(crdMembershipcardPurchaseEntity);

                }
            }else{
                int  success=marketingMakeupgroupBaseService.updateStateByRecordId(recordid.toString(),Constant.PAYSTAMENT_INGROUNP);
            }


            /** 此处添加自己的业务逻辑代码end **/
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

        } else {
            //TODO 暂时不清楚会发生这种情况
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }

        BufferedOutputStream out = new BufferedOutputStream(response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }

    @SneakyThrows
    @RequestMapping(value = "TimedTasks",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String TimedTasks(){
        List<MarketingMakeupgroupRecordEntity> marketingMakeupgroupBaseEntityList=marketingMakeupgroupBaseService.selectMakeupgroupBaseRecordByEffectiveenddate();
        StringBuilder result = new StringBuilder();
        for(MarketingMakeupgroupRecordEntity m:marketingMakeupgroupBaseEntityList){
            //调用退款接口
            String uri="http://localhost:8081/refund?orderId="+m.getRecordid()+"&&price="+m.getPrice()+"";
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            connection.disconnect();

            MarketingMakeupgroupRecordEntity marketingMakeupgroupRecordEntity=new MarketingMakeupgroupRecordEntity();
            marketingMakeupgroupRecordEntity.setRecordid(m.getRecordid());
            marketingMakeupgroupRecordEntity.setPaymentstatus(Constant.PAYSTAMENT_REFUNDSUCCESSFUL);
          int  success=marketingMakeupgroupBaseService.updateStatusByRecordid(marketingMakeupgroupRecordEntity);
        }
        return result.toString();
    }


    /*新增会员卡*/

    @SneakyThrows
    @RequestMapping(value = "insertMemberCard",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String insertMemberCard(CrdMembershipcardcategoryBase crdMembershipcardcategoryBase){
        Boolean result=backStageStatisticsService.insertMemberCard(crdMembershipcardcategoryBase);
        return result.toString();
    }


}
