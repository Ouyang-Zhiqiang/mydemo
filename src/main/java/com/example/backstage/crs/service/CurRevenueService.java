package com.example.backstage.crs.service;

import com.alibaba.fastjson.JSON;
import com.example.backstage.crs.mapper.CurRevenueServiceMapper;
import com.example.backstage.crs.util.Param;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;


@Service
public class CurRevenueService {
    @Resource
    private CurRevenueServiceMapper curRevenueServiceMapper;
    /*营收总金额*/
public String getRevenuetotal(Param param){
    Map<String, Object> map = curRevenueServiceMapper.getRevenuetotal(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid());
    return JSON.toJSONString(map);
}

    /*课程营收总金额*/
public String getCourseRevenuetotal(Param param){
    Map<String, Object> map = curRevenueServiceMapper.getCourseRevenuetotal(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid());
    return JSON.toJSONString(map);
}


    /*新单金额*/
   public String getCourseRevenueTotalFirst(Param param){
       Map<String, Object> courseRevenueTotalFirst = curRevenueServiceMapper.getCourseRevenueTotalFirst(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid());
       return JSON.toJSONString(courseRevenueTotalFirst);
   }

    /*续费金额*/
    public String getCourseRevenueTotalContinue(Param param){
        Map<String, Object> courseRevenueTotalContinue = curRevenueServiceMapper.getCourseRevenueTotalContinue(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid());
        return JSON.toJSONString(courseRevenueTotalContinue);
    }




    /*团课单节平均价（次卡）*/
    public String getAveragePriceSingleGroupLesson(Param param){
        String str="T";
        Map<String, Object> singleSectionAveragePrice = getSingleSectionAveragePrice(str, param);
        return "{"+"\"AveragePrice\":"+String.format("%.2f",getSingleSectionAveragePrice(str,param).get("averageprice"))+"}";
    }


    /*私教单节平均价（次卡）*/
    public String getPrivateEducationSingleSectionAveragePrice(Param param){
        String str="P";
        return "{"+"\"AveragePrice\":"+String.format("%.2f",getSingleSectionAveragePrice(str,param).get("averageprice"))+"}";
    }

    /*次卡消课总金额*/
    public String getSubcardMoney(Param param){
        String str="";
        return JSON.toJSONString(getSubcardMoneyall(param,str));
    }



    /*团课次卡消课金额*/
    public String getTeamSubcardMoney(Param param){
        String str="T";
        return JSON.toJSONString(getSubcardMoneyall(param,str));
    }


    /*私教次卡消课金额*/
    public String getPrivateSubcardMoney(Param param){
        String str="P";
        return JSON.toJSONString(getSubcardMoneyall(param,str));
    }

    /*平均客单价*/
    public String getAverageunit(Param param){
        double averageunit = curRevenueServiceMapper.getAverageunit(param.getCourseDatestart(), param.getCourseDateend(), param.getStoreid());
        return   "{"+"\"AveragePrice\":"+String.format("%.2f",averageunit)+"}";
    }

    /*团课平均销课单价*/
    public String getGroupAverageUnitPrice(Param param){
        String str="T";
        return "{"+"\"AveragePrice\":"+String.format("%.2f",getAverageUnitPrice(str,param))+"}";
    }


    /*私教平均销课单价*/
    public String getPrivateAverageUnitPrice(Param param){
        String str="P";
        return "{"+"\"AverageUnitPrice\":"+String.format("%.2f",getAverageUnitPrice(str,param))+"}";
    }


    /*团课单节平均价*/
    public String  getGroupAveragePriceUnit(Param param) throws Exception {
        String str="T";
        return "{"+"\"AverageUnitPrice\":"+String.format("%.2f",getAveragePriceUnit(str,param).get("averageprice"))+"}";
    }

    /*私教单节平均价*/
    public String  getPrivateAveragePriceUnit(Param param) throws Exception {
        String str="P";
        return "{"+"\"AverageUnitPrice\":"+String.format("%.2f",getAveragePriceUnit(str,param).get("averageprice"))+"}";
    }


    /*商品总金额*/
    public String getTotalAmountofGoods(Param param){
        return JSON.toJSONString(curRevenueServiceMapper.getTotalAmountofGoods(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid()));
    }

    /*客单价*/
    public String getCustomerPrice(Param param){
        double customerPrice=0;
        try {
            customerPrice=Double.valueOf(curRevenueServiceMapper.getCustomerPrice(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid()).get("customerprice").toString());
        }catch(Exception e){
            customerPrice=0;
        }
        return "{"+"\"CustomerPrice\":"+String.format("%.2f",customerPrice)+"}";
    }


    /*商品售出总数*/
    public String getGoodsnumber(Param param){
        return JSON.toJSONString(curRevenueServiceMapper.getGoodsnumber(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid()));
    }




    /*单商品金额*/
    public String getSingleProductAmount(Param param){
        double SingleProductAmount=0;
        try {
            SingleProductAmount=Double.valueOf(curRevenueServiceMapper.getSingleProductAmount(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid(),param.getSid(),param.getName()).get("singleproductamount").toString());
        }catch(Exception e){
            SingleProductAmount=0;
        }
        return "{"+"\"SingleProductAmount\":"+String.format("%.2f",SingleProductAmount)+"}";
    }


/*   * 课程营收金额
    * */


    /*团课单节平均价（次卡）、私教单节平均价（次卡）*/
    public Map<String, Object> getSingleSectionAveragePrice(String str,Param param){
        return curRevenueServiceMapper.getSingleSectionAveragePrice(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid(),str);
    }

    /*次卡消课总金额、团课次卡销课金额、私教次卡销课金额*/
    private Map<String, Object> getSubcardMoneyall(Param param,String cardtype) {
        return curRevenueServiceMapper.getSubcardMoneyall(param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid(),cardtype);
    }

    /*平均消课客单价*/
    public double getAverageUnitPrice(String str,Param param){
        return curRevenueServiceMapper.getAverageUnitPrice(str,param.getCourseDatestart(),param.getCourseDateend(),param.getStoreid());
    }

    /*单节平均价（期卡）*/
    public Map<String, Object> getAveragePriceUnit(String str,Param param) throws Exception {
        Map<String, Object> map = curRevenueServiceMapper.getAveragePriceUnit(str, param.getCourseDateend(), param.getStoreid());
        if(map.get("averageprice")==null||"".equals(map.get("averageprice"))){
            map.put("averageprice",0.00);
        }
        return map;
    }

}
