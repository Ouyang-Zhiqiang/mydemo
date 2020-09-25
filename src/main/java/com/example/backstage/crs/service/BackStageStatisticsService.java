package com.example.backstage.crs.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.example.backstage.crs.common.Constant;
import com.example.backstage.crs.entity.CrdMembershipcardUserEntity;
import com.example.backstage.crs.entity.CrdMembershipcardcategoryBase;
import com.example.backstage.crs.entity.CrdMembershipcardcategoryTeamcourseEntity;
import com.example.backstage.crs.entity.CrdMembershipcardcategoryTypecardEntity;
import com.example.backstage.crs.mapper.*;
import com.example.backstage.crs.util.DataUtil;
import com.example.backstage.crs.util.Utils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
public class BackStageStatisticsService  {
//    @Resource(name = "fbsDao")
//    protected FbsDao fbsDao;
    @Autowired
    protected OrdOrdercourseMapper ordOrdercourseMapper;
    @Autowired
    protected UserBaseMappers userBaseMappers;
    @Autowired
    protected CrdMembershipCardBaseMapper crdMembershipCardBaseMapper;
    @Autowired
    protected CrdMembershipCardUserMapper crdMembershipCardUserMapper;
    @Autowired
    protected CrdMembershipcardcategoryBaseMapper crdMembershipcardcategoryBaseMapper;
    @Autowired
    protected CrdMembershipcardPurchaseMapper crdMembershipcardPurchaseMapper;
    protected Utils utils;


    /*接口1:根据场馆以及时间段查询会员卡收入*/
    public String GetTotalStatisticsByNameAndDate(String name,String datebegin,String dateend) throws Exception{
        List<Map<String,Object>> list = crdMembershipCardBaseMapper.selectMemberShipCardByNameAndCreatedOn(name,datebegin,dateend);
        return JSONArray.toJSONString(list);
    }

    /*接口2 根据运动馆、销售人员、时间段查询会员卡总收入*/
    public String selectMemberCardByDateAndcreatednameAndStoreName(String storeid,String salerid,String datebegin,String dateend) throws Exception{

        BigDecimal TotalRevenue=new BigDecimal("0");

        /*查询会员购卡记录表：根据条件查询会员绑卡时的实收金额（或会员前台自主购卡时的金额）+续卡操作的实收金额*/
            Map<String, Object> list = crdMembershipcardPurchaseMapper.selectSellingfeeSumByStoreidAndSaleridAndDate(storeid,salerid,datebegin,dateend);
            Object a=list.get("sellingfeesum");
            if(list.size()>0&&list!=null&&a!=null&&a!=""){
                TotalRevenue=TotalRevenue.add(DataUtil.getBigDecimal(list.get("sellingfeesum")));
            }
            /*查询会员停卡表：根据条件查询停卡的退费和收款（退费表示负数，收款表示正数）*/
            List<Map<String, Object>> list1=crdMembershipcardPurchaseMapper.selectMembershipcardStop(storeid,salerid,datebegin,dateend);
            /*查询会员转卡表：根据条件查询转卡操作的手续费*/
            Map<String, Object> list2=crdMembershipcardPurchaseMapper.selectMembershipcardTransfer(storeid,salerid,datebegin,dateend);
            /*查询会员平账表：根据条件查询平账金额（增加时为正数，减少时为负数）*/
            List<Map<String, Object>> list3=crdMembershipcardPurchaseMapper.selectMembershipcardReconciliation(storeid,salerid,datebegin,dateend);


            for (Map<String, Object> map1:list1){
                if(list1!=null&&list1.size()>0&&map1.get("stoptype")!=null&&map1.get("stoptype")!=""){
                    if(map1.get("stoptype").equals(Constant.STOPTYP_TEMPORARY)){
                        TotalRevenue=TotalRevenue.add(DataUtil.getBigDecimal(map1.get("fee")));
                    }else{
                        TotalRevenue=TotalRevenue.subtract(DataUtil.getBigDecimal(map1.get("fee")));
                    }
                }
            }

            if(list2.size()>0&&list2!=null&&list2.get("fee")!=null&&list2.get("fee")!=""){
                TotalRevenue=TotalRevenue.add(DataUtil.getBigDecimal(list2.get("fee")));
            }

            for (Map<String, Object> map3:list3){
                if(list3!=null&&list3.size()>0&&map3.get("rectype")!=null&&map3.get("rectype")!=""){
                    if(map3.get("rectype").equals(Constant.RECTYPE_ADD)){
                        TotalRevenue=TotalRevenue.add(DataUtil.getBigDecimal(map3.get("recamount")));
                    }else{
                        TotalRevenue=TotalRevenue.add(DataUtil.getBigDecimal(map3.get("recamount")));
                    }
                }
            }

        return "{\"TotalRevenue\":"+TotalRevenue.toString()+"}";
    }

    /*根据条件查询列表*/
    public String selectMemberCardList(String storeid,String salerid,String datebegin,String dateend,String page,String limit) throws Exception{
        List<Map<String, Object>> list=new ArrayList<>();
        Integer limits=Integer.parseInt(limit);
        Integer pages=(Integer.parseInt(page)-1)*limits;
        Integer total=0;
        if(!salerid.equalsIgnoreCase("")&&salerid!=null&&salerid.length()>0){
            total=crdMembershipcardPurchaseMapper.selectStaticListBySaleridCount(storeid,salerid,datebegin,dateend);
            list=crdMembershipcardPurchaseMapper.selectStaticListBySalerid(storeid,salerid,datebegin,dateend,limits,pages);
        }else{
            total=crdMembershipcardPurchaseMapper.selectStaticListCount(storeid,salerid,datebegin,dateend);
            list=crdMembershipcardPurchaseMapper.selectStaticList(storeid,salerid,datebegin,dateend,limits,pages);
        }
        Map<String,Object> pageTotal=new HashMap<String, Object>();
        if(total>0){
            for(Map<String, Object> map:list){
                map.put("total",total);
            }
        }else{
                pageTotal.put("total",0);
                list.add(pageTotal);
        }
        String str= JSON.toJSONStringWithDateFormat(list,"yyyy-MM-dd HH:mm").toString();
        return  str;
    }

    /*用户画像（单）*/
    public  String selectUserPortraitSingle(String CourseDatestart,String CourseDateend,String storeid,String n,String totalAmount)throws Exception{
        /*团课消课频次数:上团课数大于等于n节课的判断筛选后用户数量*/
        Map<String,Object> leagueNumber=ordOrdercourseMapper.selectLeagueNubmerByN(CourseDatestart,CourseDateend,storeid,n);
        /*私教消课频次数:上私教数大于等于n节课的判断筛选后用户数量*/
        Map<String,Object> privateNumber=ordOrdercourseMapper.selectPrivateNubmerByN(CourseDatestart,CourseDateend,storeid,n);
        /*总消费金额:总消费金额大于n的判断筛选后用户数量*/
        Map<String,Object> totalMoney=ordOrdercourseMapper.slectTotalMoneyByMoney(CourseDatestart,CourseDateend,storeid,totalAmount);
        return "["+ JSON.toJSONString(leagueNumber).toString()+","+ JSON.toJSONString(privateNumber).toString()+","+ JSON.toJSONString(totalMoney).toString()+"]";
    }

    /*用户画像（群）:男性数量、女性数量*/
    public String selectUserPortraitGroup(String CourseDatestart,String CourseDateend,String storeid){
        List<Map<String,Object>> result= userBaseMappers.selectNumberBySex(CourseDatestart,CourseDateend,storeid);
        return JSON.toJSONString(result);
    }

    /*活跃管理*/
    public String selectActiveManagement(String CourseDatestart,String CourseDateend,String storeid){
        List<Map<String,Object>> result=ordOrdercourseMapper.selectCourseNumber(CourseDatestart,CourseDateend,storeid);
        return JSON.toJSONString(result);
    }

    /*留存管理*/
    public String selectRetentionManagement(String CourseDatestart,String CourseDateend,String storeid){
        /*查询出只有一张卡的所有用户*/
        List<String> cardnumber=crdMembershipCardBaseMapper.selectCardnumberOnlyOne();
        String[] numberStr = cardnumber.toArray(new String[cardnumber.size()]);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String today=formatter.format(date);
        /*应续费用户数量*/
        List<String> usernumber=crdMembershipCardBaseMapper.selectUserNumberByToDay(CourseDatestart,CourseDateend,storeid,numberStr,today);
        String[] userArray = usernumber.toArray(new String[usernumber.size()]);
        /*应续费用户中已续费的用户数量*/
        List<String> renewednumber=crdMembershipCardBaseMapper.selectRenewed(CourseDatestart,CourseDateend,storeid,userArray);
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);

        //应续费用户数量中，已续费用户数量的比例
        String RenewalRatio =(usernumber.size()>0? numberFormat.format((float)  renewednumber.size()/ (float)usernumber.size()* 100):0.00)+"%";

        /*已续费用户数量*/
        List<String> userrenewed=crdMembershipCardBaseMapper.selectRenewed(CourseDatestart,CourseDateend,storeid,new String[0]);
        String RenewalRatioByDate =(userrenewed.size()>0? numberFormat.format((float)  usernumber.size()/ (float)userrenewed.size()* 100):0.00)+"%";
        /*正式绑卡的用户*/
        List<String> bindingCardUsers=crdMembershipCardBaseMapper.selectRenewal(CourseDatestart,CourseDateend,storeid, null,  "F",new String[0]);
        String[] bindingCardUsersArray = bindingCardUsers.toArray(new String[bindingCardUsers.size()]);
        /*续卡两次以上的用户*/
        List<String> twice=crdMembershipCardBaseMapper.selectRenewal(CourseDatestart,CourseDateend,storeid, 2,  "C",bindingCardUsersArray);
        /*续卡三次以上的用户*/
        List<String> three=crdMembershipCardBaseMapper.selectRenewal(CourseDatestart,CourseDateend,storeid, 3,  "C",bindingCardUsersArray);
        /*续卡四次以上的用户*/
        List<String> four=crdMembershipCardBaseMapper.selectRenewal(CourseDatestart,CourseDateend,storeid, 4, "C",bindingCardUsersArray);

        String twiceRenewal=(bindingCardUsers.size()>0?numberFormat.format((float)  twice.size()/ (float)bindingCardUsers.size()* 100):0.00)+"%";
        String threeRenewal=(bindingCardUsers.size()>0?numberFormat.format((float)  three.size()/ (float)bindingCardUsers.size()* 100):0.00)+"%";
        String fourRenewal=(bindingCardUsers.size()>0?numberFormat.format((float)  four.size()/ (float)bindingCardUsers.size()* 100):0.00)+"%";
        /*绑定团课的用户数量*/
        List<String> leagueAndPrivateList=crdMembershipCardBaseMapper.selectLeagueAndPrivateCourseProportion(CourseDatestart,CourseDateend,storeid, Constant.CARDTYPE_LEAGUE,new String[0]);
        String[] leagueArray = bindingCardUsers.toArray(new String[bindingCardUsers.size()]);
        /*绑定团课的用户中绑定私课*/
        List<String> privateNumber=crdMembershipCardBaseMapper.selectLeagueAndPrivateCourseProportion(CourseDatestart,CourseDateend,storeid, Constant.CARDTYPE_PRIVATE,leagueArray);
        Integer onlyLeagueCount=leagueAndPrivateList.size()-privateNumber.size();
        String leagueAndPrivateProportion=(onlyLeagueCount>0?numberFormat.format((float)  privateNumber.size()/ (float)onlyLeagueCount* 100):0.00)+"%";
        List<Map<String,Object>> result=new ArrayList<Map<String, Object>>();
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("总续费率",RenewalRatio);
        map.put("计算续费率",RenewalRatioByDate);
        map.put("一次续费率",twiceRenewal);
        map.put("二次续费率",threeRenewal);
        map.put("三次及以上续费率",fourRenewal);
        map.put("团课转私教率",leagueAndPrivateProportion);
        result.add(map);
        return JSON.toJSONString(result);
    }

    public String selectBasicModule(String courseDatestart, String courseDateend, String storeid) {
        /*绑定体验卡的用户数量*/
        List<String> isfreeNumber= userBaseMappers.selectOnylyIsfreePeopleNumber(courseDatestart,courseDateend,storeid);
        /*只绑定体验卡的用户数量*/
        Integer onlyIsFreenumber=0;
        if(isfreeNumber.size()>0&&isfreeNumber!=null){
            String[] idArray = isfreeNumber.toArray(new String[isfreeNumber.size()]);

            List<Map<String,Object>> noIsfreeNumber= userBaseMappers.selectIsfreeTrue(courseDatestart,courseDateend,storeid,idArray);
            onlyIsFreenumber=isfreeNumber.size()-noIsfreeNumber.size();
        }


        /*绑定正式卡的用户*/
        List<Map<String,Object>> officialCard= userBaseMappers.selectIsfreeTrue(courseDatestart,courseDateend,storeid,new String[0]);

        /*签到超过1节课程，但未绑定正式卡产品的用户*/
        List<String>  moreThanOne= userBaseMappers.selectMoreThanOneClassNumber(courseDatestart,courseDateend,storeid);

        /*总课时小于等于1，或所有卡中有效期最多小于等于7天的用户*/
        List<Map<String,Object>> totalClass= userBaseMappers.selectTotalClass(courseDatestart,courseDateend,storeid);

        /*有二次正式卡付费行为的用户*/
        List<String>  MoreThanTwice= userBaseMappers.selectMoreThanTwice(courseDatestart,courseDateend,storeid);
        String result="[{\"onlyIsFreenumber\":"+onlyIsFreenumber+"},{\"officialCard\":"+officialCard.size()+"},{\"moreThanOne\":"+moreThanOne.size()+"},{\"totalClass\":"+totalClass.size()+"},{\"MoreThanTwice\":"+MoreThanTwice.size()+"}]";
        return result;
    }

    public String selectDocumentaryManagement(String courseDatestart, String courseDateend, String storeid) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date=new Date();
        String today=formatter.format(date);
        //已付过费但已失效，无有效期内卡和团课私教课时为0的会员数量
        Integer number=0;
        List<CrdMembershipcardUserEntity> list1=crdMembershipCardUserMapper.selectExpiredCardNumberEveryOne(courseDatestart,courseDateend,storeid,today);
        Map<Long,Integer> map=new HashMap<Long, Integer>();
        if(list1.size()>0||list1!=null){
            List<CrdMembershipcardUserEntity> list=crdMembershipCardUserMapper.selectCardNumberEveryOne(courseDatestart,courseDateend,storeid);
            for (CrdMembershipcardUserEntity cmue:list){
                map.put(cmue.getUserid(),cmue.getNumber());
            }

            for(CrdMembershipcardUserEntity cmue:list1){
                if(map.containsKey(cmue.getUserid())){
                    Integer n=map.get(cmue.getUserid());
                    if(n==cmue.getNumber()){
                        number++;
                    }
                }
            }
        }
        /*有会员信息，但是无绑卡*/
        Integer potentialCustomersNumber=0;

        List<String> sumNumber= userBaseMappers.selectsumNumber(courseDatestart,courseDateend,storeid);//总会员人数
        if(sumNumber.size()>0&&sumNumber!=null){
            String[] numberStr = sumNumber.toArray(new String[sumNumber.size()]);
            List<String> buyCardNumberList= userBaseMappers.selectBuyCardNumber(courseDatestart,courseDateend,storeid,numberStr);//绑过卡的人数
            potentialCustomersNumber=sumNumber.size()-buyCardNumberList.size();
        }

        /*会员只绑定了体验卡，无其他卡*/
        Integer onlyIsFreenumber=0;
        /*绑定体验卡的用户数量*/
        List<String> isfreeNumber= userBaseMappers.selectOnylyIsfreePeopleNumber(courseDatestart,courseDateend,storeid);
        /*只绑定体验卡的用户数量*/
        if(isfreeNumber.size()>0&&isfreeNumber!=null){
            String[] idArray = isfreeNumber.toArray(new String[isfreeNumber.size()]);

            List<Map<String,Object>> noIsfreeNumber= userBaseMappers.selectIsfreeTrue(courseDatestart,courseDateend,storeid,idArray);
            onlyIsFreenumber=isfreeNumber.size()-noIsfreeNumber.size();
        }
        return "[{\"number\":"+number+"},{\"potentialCustomersNumber\":"+potentialCustomersNumber+"},{\"onlyIsFreenumber\":"+onlyIsFreenumber+"}]";
    }

    public String selectTransformationManagement(String courseDatestart, String courseDateend, String storeid) {
        // 创建一个数值格式化对象
        NumberFormat numberFormat = NumberFormat.getInstance();
        // 设置精确到小数点后2位
        numberFormat.setMaximumFractionDigits(2);
        /*自然到访的流量用户中正式用户的数量占比*/
        List<String> list= userBaseMappers.selectNaturalVisitNumber(courseDatestart,courseDateend,storeid, Constant.SOURCETYPE_NATRUALVISIT);
        String naturalVisitNumber="0.00%";
        if(list!=null&&list.size()>0){
            String[] numberStr = list.toArray(new String[list.size()]);
            /*自然到访的流量用户中正式卡用户数量*/
            List<Map<String,Object>> officialCard= userBaseMappers.selectIsfreeTrueByCardNumber(courseDatestart,courseDateend,storeid,numberStr);
            naturalVisitNumber=(officialCard.size()>0?numberFormat.format((float)  officialCard.size()/ (float)list.size()* 100):0.00)+"%";
        }

        /*线上流量用户中正式用户的数量占比*/
        List<String> list1= userBaseMappers.selectNaturalVisitNumber(courseDatestart,courseDateend,storeid, Constant.SOURCETYPE_COMMENTS);
        String onlineNumber="0.00%";
        if(list!=null&&list.size()>0){
            String[] numberStr = list.toArray(new String[list.size()]);
            /*线上流量用户中正式卡用户数量*/
            List<Map<String,Object>> officialCard= userBaseMappers.selectIsfreeTrue(courseDatestart,courseDateend,storeid,numberStr);
            onlineNumber=(officialCard.size()>0?numberFormat.format((float)  officialCard.size()/ (float)list1.size()* 100):0.00)+"%";
        }

        /*老带新流量用户中正式用户的数量占比*/
        List<String> list2= userBaseMappers.selectNaturalVisitNumber(courseDatestart,courseDateend,storeid, Constant.SOURCETYPE_OLDBRINGSNEW);
        String oldBringsNew="0.00%";
        if(list!=null&&list.size()>0){
            String[] numberStr = list2.toArray(new String[list2.size()]);
            /*老带新流量用户中正式卡用户数量*/
            List<Map<String,Object>> officialCard= userBaseMappers.selectIsfreeTrue(courseDatestart,courseDateend,storeid,numberStr);
            oldBringsNew=(officialCard.size()>0?numberFormat.format((float)  officialCard.size()/ (float)list2.size()* 100):0.00)+"%";
        }

        /*拉访流量用户中正式用户的数量占比*/
        List<String> list3= userBaseMappers.selectNaturalVisitNumber(courseDatestart,courseDateend,storeid, Constant.SOURCETYPE_CANVASSING);
        String canvassingNumber="0.00%";
        if(list!=null&&list.size()>0){
            String[] numberStr = list3.toArray(new String[list3.size()]);
            /*拉访流量用户中正式卡用户数量*/
            List<Map<String,Object>> officialCard= userBaseMappers.selectIsfreeTrue(courseDatestart,courseDateend,storeid,numberStr);
            canvassingNumber=(officialCard.size()>0?numberFormat.format((float)  officialCard.size()/ (float)list3.size()* 100):0.00)+"%";
        }


        /*所有到访流量中正式用户的数量占比*/
        List<String> list7= userBaseMappers.selectvisitNumberNotDate(storeid);
        String visitNumberAll="0.00%";
        if(list!=null&&list.size()>0){
            String[] numberStr = list7.toArray(new String[list7.size()]);
            /*自所有到访流量中正式卡用户数量*/
            List<Map<String,Object>> officialCard= userBaseMappers.selectIsfreeTrueNotDate(storeid,numberStr);
            visitNumberAll=(officialCard.size()>0?numberFormat.format((float)  officialCard.size()/ (float)list7.size()* 100):0.00)+"%";
        }

        /*时间段内新绑卡正式用户数量/时间段内总到访流量(未做)*/
        List<String> list4= userBaseMappers.selectvisitNumber(courseDatestart,courseDateend,storeid);
        String visitNumber="0.00%";
        if(list!=null&&list.size()>0){
            String[] numberStr = list4.toArray(new String[list4.size()]);
            /*自所有到访流量中正式卡用户数量*/
            List<Map<String,Object>> officialCard= userBaseMappers.selectIsfreeTrue(courseDatestart,courseDateend,storeid,numberStr);
            visitNumber=(officialCard.size()>0?numberFormat.format((float)  officialCard.size()/ (float)list4.size()* 100):0.00)+"%";
        }


        /*所有流量中，正式用户数量占比*/
        List<String> list5= userBaseMappers.selectsumNumber(courseDatestart,courseDateend,storeid);//总会员人数
        String amount="0.00%";
        if(list!=null&&list.size()>0){
            String[] numberStr = list5.toArray(new String[list5.size()]);
            /*总流量中正式卡用户数量*/
            List<Map<String,Object>> officialCard= userBaseMappers.selectIsfreeTrue(courseDatestart,courseDateend,storeid,numberStr);
            amount=(officialCard.size()>0?numberFormat.format((float)  officialCard.size()/ (float)list5.size()* 100):0.00)+"%";
        }
        List<Map<String,String>> result=new ArrayList<Map<String, String>>();
        Map<String,String> map=new HashMap<String, String>();
        map.put("自然流量转化率",naturalVisitNumber);
        map.put("线上流量转化率",onlineNumber);
        map.put("老带新转化率",oldBringsNew);
        map.put("拉访转化率",canvassingNumber);
        map.put("到访转化率",visitNumberAll);
        map.put("计算转化率",visitNumber);
        map.put("总转化率",amount);
        result.add(map);
        return JSON.toJSONString(result);
    }


    public String selectInnovationManagement(String courseDatestart, String courseDateend, String storeid) {
        /*所有流量*/
        List<String> list5= userBaseMappers.selectsumNumber(courseDatestart,courseDateend,storeid);//总会员人数
        /*到访流量*/
        List<String> list4= userBaseMappers.selectvisitNumber(courseDatestart,courseDateend,storeid);
        /*拉访流量*/
        Integer list3= userBaseMappers.selectNaturalVisitNumberCount(courseDatestart,courseDateend,storeid, Constant.SOURCETYPE_CANVASSING);
        /*线上流量*/
        Integer list1= userBaseMappers.selectNaturalVisitNumberCount(courseDatestart,courseDateend,storeid, Constant.SOURCETYPE_COMMENTS);
        /*老带新流量*/
        Integer list2= userBaseMappers.selectNaturalVisitNumberCount(courseDatestart,courseDateend,storeid, Constant.SOURCETYPE_OLDBRINGSNEW);
        /*自然流量*/
        Integer list= userBaseMappers.selectNaturalVisitNumberCount(courseDatestart,courseDateend,storeid, Constant.SOURCETYPE_NATRUALVISIT);
        /*其它流量*/
        Integer list6= userBaseMappers.selectNaturalVisitNumberCount(courseDatestart,courseDateend,storeid, Constant.SOURCETYPE_OTHER);
        List<Map<String,Integer>> result=new ArrayList<Map<String, Integer>>();
        Map<String,Integer> map=new HashMap<String, Integer>();
        map.put("总流量",list5.size());
        map.put("到访流量",list4.size());
        map.put("拉访流量",list3);
        map.put("线上流量",list1);
        map.put("老带新流量",list2);
        map.put("自然流量",list);
        map.put("其它流量",list6);
        result.add(map);
        return JSON.toJSONString(result);
    }

    public Boolean insertMemberCard(CrdMembershipcardcategoryBase crdMembershipcardcategoryBase){
        crdMembershipcardcategoryBase.setCreatedon(new Date());
        crdMembershipcardcategoryBase.setLastedon(new Date());
        Boolean result=false;
        int insert1=crdMembershipcardcategoryBaseMapper.insertCrdMembershipcardcategoryBase(crdMembershipcardcategoryBase);

//        if(insert1>0){
            List<CrdMembershipcardcategoryTeamcourseEntity> list1 = JSONObject.parseArray(crdMembershipcardcategoryBase.getFaqjson(),CrdMembershipcardcategoryTeamcourseEntity.class);
            List<CrdMembershipcardcategoryTypecardEntity> list = JSONObject.parseArray(crdMembershipcardcategoryBase.getFaqjson(),CrdMembershipcardcategoryTypecardEntity.class);
            for (CrdMembershipcardcategoryTypecardEntity crdMembershipcardcategoryTypecardEntity:list){
                crdMembershipcardcategoryTypecardEntity.setCardid(crdMembershipcardcategoryBase.getCardid());
                crdMembershipcardcategoryTypecardEntity.setCreatedon(crdMembershipcardcategoryBase.getCreatedon());
                crdMembershipcardcategoryTypecardEntity.setCreatedname(crdMembershipcardcategoryBase.getCreatedname());
                crdMembershipcardcategoryTypecardEntity.setCreatedip(crdMembershipcardcategoryBase.getCreatedip());
                crdMembershipcardcategoryTypecardEntity.setCardtype(crdMembershipcardcategoryBase.getCardtype());
                crdMembershipcardcategoryTypecardEntity.setCreatedby(crdMembershipcardcategoryBase.getCreatedby());
                crdMembershipcardcategoryTypecardEntity.setLastedby(crdMembershipcardcategoryBase.getLastedby());
                crdMembershipcardcategoryTypecardEntity.setLastedip(crdMembershipcardcategoryBase.getLastedip());
                crdMembershipcardcategoryTypecardEntity.setLastedname(crdMembershipcardcategoryBase.getLastedname());
                crdMembershipcardcategoryTypecardEntity.setLastedon(crdMembershipcardcategoryBase.getLastedon());
            }
            for(CrdMembershipcardcategoryTeamcourseEntity crdMembershipcardcategoryTeamcourseEntity:list1){
                crdMembershipcardcategoryTeamcourseEntity.setCardid(crdMembershipcardcategoryBase.getCardid());
                crdMembershipcardcategoryTeamcourseEntity.setCreatedon(crdMembershipcardcategoryBase.getCreatedon());
                crdMembershipcardcategoryTeamcourseEntity.setCreatedname(crdMembershipcardcategoryBase.getCreatedname());
                crdMembershipcardcategoryTeamcourseEntity.setCreatedip(crdMembershipcardcategoryBase.getCreatedip());
                crdMembershipcardcategoryTeamcourseEntity.setCreatedby(crdMembershipcardcategoryBase.getCreatedby());
                crdMembershipcardcategoryTeamcourseEntity.setLastedby(crdMembershipcardcategoryBase.getLastedby());
                crdMembershipcardcategoryTeamcourseEntity.setLastedip(crdMembershipcardcategoryBase.getLastedip());
                crdMembershipcardcategoryTeamcourseEntity.setLastedname(crdMembershipcardcategoryBase.getLastedname());
                crdMembershipcardcategoryTeamcourseEntity.setLastedon(crdMembershipcardcategoryBase.getLastedon());
            }
            int insert2=crdMembershipcardcategoryBaseMapper.insertCrdMembershipCardCategoryTypeCard(list);
            int insert3=crdMembershipcardcategoryBaseMapper.insertCrdMembershipCardCategoryTeamCourse(list1);
            if(insert2>0&&insert3>0){
                result=true;
            }
//        }
        return result;
    }

}
