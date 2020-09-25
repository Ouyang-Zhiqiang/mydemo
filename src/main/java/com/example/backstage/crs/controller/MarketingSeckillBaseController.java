package com.example.backstage.crs.controller;

import com.alibaba.fastjson.JSON;
import com.example.backstage.crs.common.Constant;
import com.example.backstage.crs.entity.*;
import com.example.backstage.crs.mapper.CrdMembershipCardBaseMapper;
import com.example.backstage.crs.mapper.CrdMembershipcardPurchaseMapper;
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
@RequestMapping("/web/activity/")
public class MarketingSeckillBaseController {

    @Autowired
    protected MarketingSeckillBaseSerice marketingSeckillBaseSerice;
    @Autowired
    protected CrdMembershipCardBaseMapper crdMembershipCardBaseMapper;
    @Autowired
    protected CrdMembershipcardPurchaseMapper crdMembershipcardPurchaseMapper;



    /*新增秒杀活动*/
    @RequestMapping(value = "addMarketingSeckillBase",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String addMarketingSeckillBase( MarketingSeckillBaseEntity marketingSeckillBaseEntity){
        String result=marketingSeckillBaseSerice.addMarketingSeckillBase(marketingSeckillBaseEntity);
        return result;
    }

    /*修改秒杀活动*/
    @RequestMapping(value = "updateMarketingSeckillBase",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String updateMarketingSeckillBase(@RequestParam MarketingSeckillBaseEntity marketingSeckillBaseEntity){
        System.err.println("fdsfsdfs----"+marketingSeckillBaseEntity);
        String result=marketingSeckillBaseSerice.updateMarketingSeckillBase(marketingSeckillBaseEntity);
        return result;
    }

    /*查询秒杀活动列表*/
    @RequestMapping(value = "marketingSeckillBaseList",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String selectMarketingSeckillBaseList(@RequestParam("limit")Integer limit , @RequestParam("page") Integer page ){
        List<Map<String,String>> result=marketingSeckillBaseSerice.selectMarketingSeckillBaseList(limit,page);
        int count=marketingSeckillBaseSerice.selectMarketingSeckillBaseCount();
        for(Map<String,String> maps:result){
            maps.put("total",String.valueOf(count));
        }
        return JSON.toJSONStringWithDateFormat(result,"yyyy-MM-dd").toString();
    }

    /*支付完成秒杀成功*/
    @SneakyThrows
    @RequestMapping(value = "SeckillPaySuccess",produces = {"text/json;charset=UTF-8"},method= RequestMethod.POST)
    @ResponseBody
    public Object paySuccess(MarketingSeckillBaseRecordEntity marketingSeckillBaseRecordEntity, @Param("sfprice") BigDecimal sfprice, @Param("openid") String openid
            , HttpServletRequest request, HttpServletResponse response){
        System.out.println("openid-------"+openid);
        String result= marketingSeckillBaseSerice.paySuccess(marketingSeckillBaseRecordEntity,sfprice,openid);
        if(!result.equals("false")&&result!="false"){
            String notityXml = result;
            String resXml = "";
            Gson gson = new Gson();
            Map<String, Object> map = new HashMap<String, Object>();
            map = gson.fromJson(notityXml, map.getClass());
            String returnCode = (String) map.get("return_code");
            System.out.println("回调函数启动:"+returnCode);

            //获取商户订单号
            String recordid = (String)map.get("recordid");
            if ("SUCCESS".equals(returnCode) ) {
                List<Map<String, Object>> list = crdMembershipcardPurchaseMapper.selectThirdaccount(openid);

//                if(list.size()>0&&list!=null){
                    /** 此处添加自己的业务逻辑代码start **/
                    Map<String, Object> param = new HashMap<String,Object>();
                    param.put("recordid",recordid);
                    //状态为2，已支付
                    param.put("paymentstatus",2);
                    param.put("cardstatus",2);//已绑卡
                    //这里写逻辑代码，比如更新记录状态为已支付
                    int result1 = marketingSeckillBaseSerice.updateStatus(param);
                    int groupid=Integer.parseInt(map.get("groupid").toString());
                    MarketingSeckillBaseEntity marketingSeckillBaseEntity=marketingSeckillBaseSerice.selectMarketingSeckillBaseBySeckillid(groupid);
                    String id= UUID.randomUUID().toString();
                    //替换uuid中的"-"
                    id=id.replace("-", "");


                    /*查询当前会员卡类型详情*/
                    CrdMembershipcardcategoryBase crdMembershipcardcategoryBase=marketingSeckillBaseSerice.selectCrdMembershipcardcategoryBaseByCardId(Long.valueOf(marketingSeckillBaseEntity.getCardid()));
                    /*查询梯度表中当前会员卡类型的详情*/
                    CrdMembershipcardcategoryTypecardEntity crdMembershipcardcategoryTypecardEntity=marketingSeckillBaseSerice.selectMembershipCardCategoryByCardId(Long.valueOf(marketingSeckillBaseEntity.getCardid()));
                    /*新增会员卡*/
                    CrdMembershipcardBaseEntity crdMembershipcardBaseEntity=new CrdMembershipcardBaseEntity();
                    crdMembershipcardBaseEntity.setCardno(id);
                    crdMembershipcardBaseEntity.setCardid(marketingSeckillBaseEntity.getCardid());
                    crdMembershipcardBaseEntity.setCardname(marketingSeckillBaseEntity.getCardname());
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
                    crdMembershipcardBaseEntity.setCreatedname(map.get("createdname").toString());
                    int rs=marketingSeckillBaseSerice.insertCrdMembershipcardBase(crdMembershipcardBaseEntity);

                    /*新增会员关系表*/
                    CrdMembershipcardUserEntity crdMembershipcardUserEntity=new CrdMembershipcardUserEntity();
                    crdMembershipcardUserEntity.setCardno(crdMembershipcardBaseEntity.getCardno());
                    crdMembershipcardUserEntity.setIspoint(true);//默认为1
                    crdMembershipcardUserEntity.setUserid(Long.valueOf(String.valueOf(list.get(0).get("userid"))).longValue());
                    crdMembershipcardUserEntity.setCreatedby(map.get("createdby").toString());
                    crdMembershipcardUserEntity.setLastedby(map.get("lastedby").toString());
                    crdMembershipcardUserEntity.setLastedname(map.get("lastedname").toString());
                    crdMembershipcardUserEntity.setCreatedname(map.get("createdname").toString());
                    crdMembershipcardUserEntity.setIsremoved(false);
                    int sc=marketingSeckillBaseSerice.insertMembershipCardUser(crdMembershipcardUserEntity);


                    /*绑定会员卡*/
                    CrdMembershipcardPurchaseEntity crdMembershipcardPurchaseEntity=new CrdMembershipcardPurchaseEntity();
                    crdMembershipcardPurchaseEntity.setCardno(id);
                    crdMembershipcardPurchaseEntity.setSeckillid(marketingSeckillBaseEntity.getSeckillid());
                    crdMembershipcardPurchaseEntity.setPayment(Constant.PAYMENTMETHOD_ONLINE);//线上支付
                    crdMembershipcardPurchaseEntity.setCardid(Long.valueOf(marketingSeckillBaseEntity.getCardid()));
                    crdMembershipcardPurchaseEntity.setCardname(marketingSeckillBaseEntity.getCardname());
                    crdMembershipcardPurchaseEntity.setStoreid(Long.parseLong(map.get("storeid").toString()));
                    crdMembershipcardPurchaseEntity.setStorename(map.get("storename").toString());
                    crdMembershipcardPurchaseEntity.setBuytimes(crdMembershipcardBaseEntity.getTimes());
                    crdMembershipcardPurchaseEntity.setPoints(crdMembershipcardBaseEntity.getPoints());
                    crdMembershipcardPurchaseEntity.setBuytype(Constant.FIRSTTIME);
                    crdMembershipcardPurchaseEntity.setSellingfee(sfprice.toString());
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




//            System.out.println(
//                    result>0?"支付成功":"支付失败"
//            );

                    /** 此处添加自己的业务逻辑代码end **/
                    resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                            + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

//                } else {
//                    //TODO 暂时不清楚会发生这种情况
//                    resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
//                            + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
//                }

                BufferedOutputStream out = new BufferedOutputStream(
                        response.getOutputStream());
                out.write(resXml.getBytes());
                out.flush();
                out.close();
                return resXml;
                }else{
                return false;
            }


        }else{
            return false;
        }


    }

    @SneakyThrows
    @RequestMapping(value = "connect",produces = {"text/json;charset=UTF-8"},method= RequestMethod.POST)
    @ResponseBody
    public Object connect(@Param("access_token") String access_token, @Param("openid") String openid, HttpServletRequest request){
        System.out.println(access_token+"----"+openid);
            StringBuilder result = new StringBuilder();
            String uri="https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&&openid="+openid+"";
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(),"utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                result.append(line);
            }
            connection.disconnect();
        return result;
    }

    //这里是支付成功后的回调
    @RequestMapping(value = "/SeckillPay/return",produces = {"application/json; charset=utf-8"}, method = RequestMethod.POST)
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

        //获取商户订单号
        String recordid = (String)map.get("recordid");
        if ("SUCCESS".equals(returnCode) ) {
            /** 此处添加自己的业务逻辑代码start **/
            Map<String, Object> param = new HashMap<String,Object>();
            param.put("recordid",recordid);
            //状态为2，已支付
            param.put("paymentstatus",2);
            //这里写逻辑代码，比如更新记录状态为已支付
            int result = marketingSeckillBaseSerice.updateStatus(param);
            int groupid=Integer.parseInt(map.get("groupid").toString());
            MarketingSeckillBaseEntity marketingSeckillBaseEntity=marketingSeckillBaseSerice.selectMarketingSeckillBaseBySeckillid(groupid);
            String id= UUID.randomUUID().toString();
            //替换uuid中的"-"
            id=id.replace("-", "");
            String tel=map.get("tel").toString();
            /*根据手机号查询userid*/
            String userid=marketingSeckillBaseSerice.selectUserIdByPhone(tel);

            /*查询当前会员卡类型详情*/
            CrdMembershipcardcategoryBase crdMembershipcardcategoryBase=marketingSeckillBaseSerice.selectCrdMembershipcardcategoryBaseByCardId(Long.valueOf(marketingSeckillBaseEntity.getCardid()));
            /*查询梯度表中当前会员卡类型的详情*/
            CrdMembershipcardcategoryTypecardEntity crdMembershipcardcategoryTypecardEntity=marketingSeckillBaseSerice.selectMembershipCardCategoryByCardId(Long.valueOf(marketingSeckillBaseEntity.getCardid()));
            /*新增会员卡*/
            CrdMembershipcardBaseEntity crdMembershipcardBaseEntity=new CrdMembershipcardBaseEntity();
            crdMembershipcardBaseEntity.setCardno(id);
            crdMembershipcardBaseEntity.setCardid(marketingSeckillBaseEntity.getCardid());
            crdMembershipcardBaseEntity.setCardname(marketingSeckillBaseEntity.getCardname());
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
            crdMembershipcardBaseEntity.setCreatedname(map.get("createdname").toString());
            int rs=marketingSeckillBaseSerice.insertCrdMembershipcardBase(crdMembershipcardBaseEntity);

            /*新增会员关系表*/
            CrdMembershipcardUserEntity crdMembershipcardUserEntity=new CrdMembershipcardUserEntity();
            crdMembershipcardUserEntity.setCardno(crdMembershipcardBaseEntity.getCardno());
            crdMembershipcardUserEntity.setIspoint(true);//默认为1
            crdMembershipcardUserEntity.setUserid(Long.valueOf(userid));
            crdMembershipcardUserEntity.setCreatedby(map.get("createdby").toString());
            crdMembershipcardUserEntity.setLastedby(map.get("lastedby").toString());
            crdMembershipcardUserEntity.setLastedname(map.get("lastedname").toString());
            crdMembershipcardUserEntity.setCreatedname(map.get("createdname").toString());
            crdMembershipcardUserEntity.setIsremoved(false);
            int sc=marketingSeckillBaseSerice.insertMembershipCardUser(crdMembershipcardUserEntity);



            /*绑定会员卡*/
            CrdMembershipcardPurchaseEntity crdMembershipcardPurchaseEntity=new CrdMembershipcardPurchaseEntity();
            crdMembershipcardPurchaseEntity.setCardno(id);
            crdMembershipcardPurchaseEntity.setSeckillid(marketingSeckillBaseEntity.getSeckillid());
            crdMembershipcardPurchaseEntity.setPayment(Constant.PAYMENTMETHOD_ONLINE);//线上支付
            crdMembershipcardPurchaseEntity.setCardid(Long.valueOf(marketingSeckillBaseEntity.getCardid()));
            crdMembershipcardPurchaseEntity.setCardname(marketingSeckillBaseEntity.getCardname());
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


            System.out.println(
                    result>0?"支付成功":"支付失败"
            );

            /** 此处添加自己的业务逻辑代码end **/
            resXml = "<xml>" + "<return_code><![CDATA[SUCCESS]]></return_code>"
                    + "<return_msg><![CDATA[OK]]></return_msg>" + "</xml> ";

        } else {
            //TODO 暂时不清楚会发生这种情况
            resXml = "<xml>" + "<return_code><![CDATA[FAIL]]></return_code>"
                    + "<return_msg><![CDATA[报文为空]]></return_msg>" + "</xml> ";
        }

        BufferedOutputStream out = new BufferedOutputStream(
                response.getOutputStream());
        out.write(resXml.getBytes());
        out.flush();
        out.close();
    }


    @RequestMapping(value = "seckillRecordBaseList",produces = {"text/json;charset=UTF-8"})
    @ResponseBody
    public String selectSeckillRecordBaseTotal(@RequestParam("limit")Integer limit , @RequestParam("page") Integer page , @RequestParam("groupid") Integer groupid){
        List<MarketingSeckillBaseRecordEntity> result=marketingSeckillBaseSerice.selectSeckillBaseRecord(limit,page,groupid);
        int count=marketingSeckillBaseSerice.selectSeckillBaseTotal();
        for(MarketingSeckillBaseRecordEntity maps:result){
            maps.setTotal(count);
        }
        return JSON.toJSONStringWithDateFormat(result,"yyyy-MM-dd").toString();
    }


}
