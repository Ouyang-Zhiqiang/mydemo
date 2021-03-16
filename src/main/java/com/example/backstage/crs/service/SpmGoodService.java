package com.example.backstage.crs.service;

import com.alibaba.fastjson.JSON;
import com.example.backstage.crs.entity.SpmIntegralgoodsBaseEntity;
import com.example.backstage.crs.entity.SpmIntegralgoodsOrderEntity;
import com.example.backstage.crs.entity.SpmIntegralgoodsOrderExchangelogEntity;
import com.example.backstage.crs.mapper.SPMIntegralGoodsBaseMapper;
import com.example.backstage.crs.mapper.SpmIntegralgoodsOrderExchangelogMapper;
import com.example.backstage.crs.util.Send;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SpmGoodService {

    @Autowired
    protected SpmIntegralgoodsOrderExchangelogMapper spmIntegralgoodsOrderExchangelogMapper;
    @Autowired
    protected SPMIntegralGoodsBaseMapper spmIntegralGoodsBaseMapper;

    public String spmGoodChange(Long orderId,String createdby,String createdname,String ip) {

        SpmIntegralgoodsOrderEntity detail = spmIntegralgoodsOrderExchangelogMapper.selectSpmIntegralgoodsOrderByOrderid(orderId);
        SpmIntegralgoodsOrderExchangelogEntity slmodel = new SpmIntegralgoodsOrderExchangelogEntity();
        slmodel.setOrderid(detail.getOrderid());
        slmodel.setUserid(detail.getUserid());
        slmodel.setUsername(detail.getUsername()) ;
        slmodel.setUserphone(detail.getUserphone());
        slmodel.setGoodscode(detail.getGoodscode());
        slmodel.setName(detail.getName());
        slmodel.setImgid(detail.getImgid());
        slmodel.setImgurl(detail.getImgurl());
        slmodel.setSalespoint(detail.getSalespoint());
        slmodel.setStoreid(detail.getStoreid()) ;
        slmodel.setStorename(detail.getStorename()) ;
        slmodel.setUsedvaliditybegin(detail.getUsedvaliditybegin()) ;
        slmodel.setUsedvalidityend(detail.getUsedvalidityend()) ;
        slmodel.setSalesstate(detail.getSalesstate());
        slmodel.setSeqnum(detail.getSeqnum());
        slmodel.setOpttype(1);
        slmodel.setState(detail.getState());
        slmodel.setLogstate(1);
        slmodel.setIsremoved("false");
        slmodel.setCreatedby(createdby);
        slmodel.setCreatedname(createdname);
        slmodel.setCreatedip(ip);
        slmodel.setCreatedon(new Date());
        int success=spmIntegralgoodsOrderExchangelogMapper.Insert(slmodel);

        detail.setOrderid(orderId.toString());
        detail.setState(1);
        detail.setLastedby(createdby);
        detail.setLastedname(createdname);
        detail.setLastedip(ip);
        detail.setLastedon(new Date());
        int result= spmIntegralgoodsOrderExchangelogMapper.Update(detail);
        if(success>0&&result>0){
            return   "操作成功！";
        }
       return   "操作失败！";
    }

    // 接口37 积分换购商品的列表 web/spms/GetIntegralgoodsListByStoreId.xhtml
    public String GetIntegralgoodsListByStoreId(String storeids)throws Exception{
        String result = null;
        try {
            if(storeids.equals("")){
                return JSON.toJSONString("[]");
            }
            Long storeid = Long.parseLong(storeids);
            spmIntegralGoodsBaseMapper.UpdateSpmGoodsOrder();
            List<Map<String,Object>> list = spmIntegralGoodsBaseMapper.selectSpmGoodsOrderByStoreids(storeid);
            return JSON.toJSONString(list);
        } catch (Exception e) {
            Send.sendError(e,"接口37 积分换购商品的列表 web/spms/GetIntegralgoodsListByStoreId.xhtml");
        }
        return JSON.toJSONString(result);
    }

//   判断数量
    public String JudgeQuantity(String goodscode) {
        SpmIntegralgoodsBaseEntity integralgoodsBaseEntity=spmIntegralGoodsBaseMapper.selectAllSpmIntegralGoodsByGoodcode(goodscode);
        if(integralgoodsBaseEntity.getPurchased()>=integralgoodsBaseEntity.getStock()){
            return JSON.toJSONString("");
        }
        return JSON.toJSONString("");
    }

    public List<Map<String,Object>> getGoodsList(String storeid) {
        return spmIntegralGoodsBaseMapper.getGoodsList(storeid);
    }

    public List<SpmIntegralgoodsOrderEntity> selectGoodsOrderList(String storeid) {
        return spmIntegralGoodsBaseMapper.selectGoodsOrderList(storeid);
    }

    public List<Map<String, Object>> selectGoodsOrderExchangeList(String storeid) {
        return spmIntegralGoodsBaseMapper.selectGoodsOrderExchangeList(storeid);
    }

//    // 接口38 会员换购积分商品 web/spms/BuyIntegralgoods.xhtml
//    public String BuyIntegralgoods(Param param)throws Exception{
//        String result= null;
//        try {
//            if(param.getUserid().equals("")||param.getGoodscode().equals("")){
//                return JSON.toJSONString("[]");
//            }
//            Long userid = Long.parseLong(param.getUserid());
//            Long goodscode = Long.parseLong(param.getGoodscode());
//            int a= spmIntegralGoodsBaseMapper.UpdateSpmGoodsOrder();
//            result = "";
//            int b=spmIntegralGoodsBaseMapper.insertApmInteralGoodsOrder(userid,goodscode);
//            if (b>0){
//
//                Map<String,Object> map = spmIntegralGoodsBaseMapper.selectSpmIntegralGoodsByGoodcode(goodscode);
//                // 添加积分操作日志
//                logPoints(userid,map.get("salespoint").toString(),"会员换购积分商品:"+goodscode,"-","积分兑换","");
//
//                // 减少换购操作积分
//                String qy2 = "update user_base set points=points-COALESCE((select salespoint from spm_integralgoods_base where goodscode=?),0) where userid=?";
//                Integer a2 = fbsDao.updateBySql(qy2,new Object[]{goodscode,userid});
//                if (a2>0){
//                    result = "{\"state\":1,\"remarks\":\"\"}";
//                }else {
//                    result = "{\"state\":0,\"remarks\":\"接口38_BuyIntegralgoods更新失败\"}";
//                }
//            }else {
//                result = "{\"state\":0,\"remarks\":\"接口38_BuyIntegralgoods插入失败\"}";
//            }
//        } catch (Exception e) {
//            Send.sendError(e,"接口38 会员换购积分商品 web/spms/BuyIntegralgoods.xhtml");
//        }
//        return StringUtil.toJson(result);
//    }


}
