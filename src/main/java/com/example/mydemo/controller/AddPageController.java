package com.example.mydemo.controller;
import ch.qos.logback.core.util.FileUtil;
import com.UpYun;
import com.alibaba.fastjson.JSONObject;
import com.example.mydemo.mapper.OrderMapper;
import com.example.mydemo.pojo.GhostUser;
import com.example.mydemo.pojo.Order;
import com.example.mydemo.pojo.OrderItem;
import com.example.mydemo.service.GhostUserService;
import com.example.mydemo.util.AESUtil;
import com.example.mydemo.util.AesCbcUtil;
import com.example.mydemo.util.HttpRequest;
import com.example.mydemo.util.PayUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.upyun.RestManager;
import com.upyun.UpYunUtils;
import com.upyun.*;
import net.sf.json.JSONArray;
import okhttp3.Response;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.example.mydemo.util.CheckUtils;


@RestController
public class AddPageController {

//    @RequestMapping("/test1")
//    public String getTest(){
//        return "111test";
//    }

    @RequestMapping(value = "/addPage",method = RequestMethod.POST)
    public String addPage(@RequestParam("activityid")String activityid,@RequestParam("name")String name,@RequestParam("detail")String detail,@RequestParam("fee")String fee,@RequestParam("signtime")String signtime,@RequestParam("starttime")String starttime){
        String acid=activityid;
        String acname=name;
        String acdetail=detail;
        String acfee=fee;
        String mypage2="var activityid="+acid+";var name='"+name+"';var detail='"+detail+"';var fee="+acfee+";var starttime='"+starttime+"';var signtime='"+signtime+"';";
        String pageName=UUID.randomUUID().toString();
        String mypage1="<!DOCTYPE html>\n" +
                "<html lang=\"zh\">\n" +
                "<head>\n" +
                "<meta charset=\"UTF-8\" />\n" +
                "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\" />\n" +
                "<meta http-equiv=\"X-UA-Compatible\" content=\"ie=edge\" />\n" +
                "<title>活动报名</title>\n" +
                "<link href=\"js/swiper-4.5.1/swiper-Swiper4/dist/css/swiper.min.css\"; rel=\"stylesheet\">\n" +
                "<link href=\"js/bootstrap-3.3.7-dist/css/bootstrap.min.css\"; rel=\"stylesheet\">\n" +
                "<script src=\"js/jquery.min.js\";></script>\n" +
                "<script src=\"js/swiper-4.5.1/swiper-Swiper4/dist/js/swiper.min.js\";></script>\n" +
                "<script src=\"js/bootstrap-3.3.7-dist/js/bootstrap.min.js\"></script>\n" +
                "<script src=\"http://res.wx.qq.com/open/js/jweixin-1.4.0.js\"></script>\n" +
                "<style type=\"text/css\">\n" +
                "  *{\n" +
                "    margin: 0;\n" +
                "    padding: 0;\n" +
                "    font-family: 微软雅黑!important;\n" +
                "  }\n" +
                "  html{\n" +
                "      width: 100%;\n" +
                "      background-repeat:no-repeat;\n" +
                "      touch-action:none;\n" +
                "      touch-action:pan-y;\n" +
                "      /* pointer-events: auto;\n" +
                "      touch-action: manipulation; */\n" +
                "     \n" +
                "  }\n" +
                "  body{\n" +
                "      background-color:#130E4E;\n" +
                "      /* background-size:100%; */\n" +
                "      color: white;\n" +
                "  }\n" +
                "  \n" +
                "</style>\n" +
                "</head>\n" +
                "<body style=\"background-image: url(images-14/slide2.jpg);background-repeat:no-repeat;background-size:100% 100%;-moz-background-size:100% 100%;\">\n" +
                "  <div class=\"row\" style=\"margin-top:20px\">\n" +
                "    <div class=\"col-lg-3 col-md-3 col-xs-0\" >\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"col-lg-6 col-md-6 col-xs-12\" style=\"padding:25px;margin-bottom: 30px;\">\n" +
                "        <div style=\"width: 73%;margin: 0 auto;\">\n" +
                "            <span style=\"font-size: 23px;\" id=\"mytitle\"></span><br>\n" +
                "            <span id=\"mydetail\">\n" +
                "                \n" +
                "            </span>\n" +
                "        </div>\n" +
                "        \n" +
                "        <form role=\"form\" class=\"form-horizontal\" style=\"margin-left:50px;margin-top: 20px;\">\n" +
                "          <div class=\"form-group form-group-xs\">\n" +
                "            <label  style=\"width: 100px;margin-left:15px;float: left;\">报名时间:</label>\n" +
                "            <div style=\"float: left;margin-left: -30px;\">\n" +
                "                <span id=\"signtime\"></span>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "          <div class=\"form-group form-group-xs\">\n" +
                "            <label  style=\"width: 100px;margin-left:15px;float: left;\">活动时间:</label>\n" +
                "            <div style=\"float: left;margin-left: -30px;\" >\n" +
                "                <span id=\"starttime\"></span>\n" +
                "            </div>\n" +
                "          </div>\n" +
                "            <div class=\"form-group form-group-xs\">\n" +
                "                <label for=\"hospitalName\" class=\"control-label \" style=\"width: 100px;margin-left:15px;\">姓名(必填):</label><br>\n" +
                "                <div class=\"col-xs-5\">\n" +
                "                    <input type=\"text\" class=\"form-control\" style=\"width: 240px;border:0px\" name=\"name\" id=\"name\" placeholder=\"\" required=\"required\" />\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"form-group form-group-xs\">\n" +
                "              <label for=\"mobileNum\" class=\"control-label \" style=\"width: 100px;display: block;float: left;margin-left: 15px;margin-top: 8px;\">性别(必填):</label>\n" +
                "              <div class=\"col-xs-5\" style=\"float: left;\">\n" +
                "                  <label class=\"radio-inline\">\n" +
                "                      <input type=\"radio\" name=\"optionsRadiosinline\" id=\"sex\" value=\"1\" checked> 男\n" +
                "                  </label>\n" +
                "                  &nbsp;&nbsp;&nbsp;\n" +
                "                  <label class=\"radio-inline\">\n" +
                "                      <input type=\"radio\" name=\"optionsRadiosinline\" id=\"sex\"  value=\"0\"> 女\n" +
                "                  </label>\n" +
                "              </div>\n" +
                "          </div>\n" +
                "            <div class=\"form-group form-group-xs\">\n" +
                "                <label for=\"username\" class=\"control-label \" style=\"width: 100px;margin-left:15px;\">手机号(必填):</label><br>\n" +
                "                <div class=\"col-xs-5\">\n" +
                "                    <input type=\"text\" class=\"form-control\" style=\"width: 240px;\" name=\"phone\" id=\"phone\" placeholder=\"\" required=\"required\"/>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            \n" +
                "            <div class=\"form-group form-group-xs\">\n" +
                "                <label for=\"mobileNum\" class=\"control-label \" style=\"width: 100px;margin-left:15px;\">人数(必填):</label><br>\n" +
                "                <div class=\"col-xs-5\">\n" +
                "                    <input type=\"text\" class=\"form-control\" style=\"width: 240px;\" name=\"city\" id=\"number\" placeholder=\"1\" required=\"required\" maxlength=\"11\"/>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"form-group form-group-xs\">\n" +
                "                <label for=\"mobileNum\" class=\"control-label\" style=\"width: 100px;margin-left:15px;\">备注:</label><br>\n" +
                "                <div class=\"col-xs-5\">\n" +
                "                    <input type=\"text\" class=\"form-control\" style=\"width: 240px;\" name=\"sqsm\" id=\"remarks\" placeholder=\"\" required=\"required\" maxlength=\"11\"/>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "            <div class=\"form-group form-group-xs\">\n" +
                "                <label  style=\"width: 100px;margin-left:15px;float: left;\">报名费:</label>\n" +
                "                <div style=\"float: left;margin-left: -30px;\" id=\"fee\">\n" +
                "                    <!-- <input type=\"text\" class=\"form-control\" style=\"width: 250px;\" name=\"sqsm\" id=\"sqsm\" placeholder=\"\" required=\"required\" maxlength=\"11\"/> -->\n" +
                "                    <span id=\"myfee\">30</span>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "\n" +
                "            <div class=\"form-group form-group-xs\">\n" +
                "                <div class=\"col-xs-2 col-xs-offset-2\">\n" +
                "                    <div id=\"tijiao\" name=\"tijiao\" class=\"btn btn-success btn-xs\" style=\"width: 80px;height: 30px;text-align: center;line-height: 25px;margin-left:35px;margin-top:20px;\">提交</div>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </form>\n" +
                "\n" +
                "    </div>\n" +
                "\n" +
                "    <div class=\"col-lg-3 col-md-3 col-xs-0\"  >\n" +
                "    </div>\n" +
                "</div>\n" +
                "\n" +
                "</body>\n" +
                "</html>\n" +
                "<script>\n" +
                "  jQuery(function($) {";
        String myPage3="document.getElementById(\"mytitle\").innerHTML=name\n" +
                "    document.getElementById(\"mydetail\").innerHTML=detail\n" +
                "    document.getElementById(\"myfee\").innerHTML=fee\n" +
                "    document.getElementById(\"starttime\").innerHTML=starttime\n" +
                "    document.getElementById(\"signtime\").innerHTML=signtime\n" +
                "    var signendtime=signtime.split('~')[1]\n" +
                "\n" +
                "    var url = location.search; //获取url中\"?\"符后的字串\n" +
                "    var openid=''\n" +
                "    var theRequest = new Object();\n" +
                "\n" +
                "    if (url.indexOf(\"?\") != -1) {\n" +
                "\n" +
                "        var str = url.substr(1);\n" +
                "\n" +
                "        strs = str.split(\"&\");\n" +
                "\n" +
                "        for(var i = 0; i < strs.length; i ++) {\n" +
                "\n" +
                "          theRequest[strs[i].split(\"=\")[0]]=(strs[i].split(\"=\")[1]);\n" +
                "\n" +
                "        }\n" +
                "\n" +
                "    }\n" +
                "    \n" +
                "    const local = window.location.href\n" +
                "\n" +
                "    if (theRequest.code == null || theRequest.code === '') {\n" +
                "        var time = (new Date()).valueOf()\n" +
                "        window.location.href = 'https://open.weixin.qq.com/connect/oauth2/authorize?appid=' + 'wx1b98935219078af2' +\n" +
                "            '&redirect_uri=' + encodeURIComponent(local)+\"?time=\"+time + '&response_type=code&scope=snsapi_userinfo&state=1#wechat_redirect'\n" +
                "    }\n" +
                "\n" +
                "    function isPhoneNum(str){\n" +
                "        var myreg=/^[1][3,4,5,6,7,8][0-9]{9}$/;\n" +
                "        if (!myreg.test(str)) {\n" +
                "            return false;\n" +
                "        }else{\n" +
                "            return true;\n" +
                "        }\n" +
                "    }\n" +
                "\n" +
                "    $('#tijiao').click(function(){\n" +
                "      var username,phone,sex,numbers=1,remarks,totalfee;\n" +
                "      var orderid=(new Date()).valueOf()+''+Math.ceil(Math.random()*10000) \n" +
                "      if(new Date(new Date(signendtime).getTime()+86400000)>=new Date()){\n" +
                "      if($(\"#name\").val()!=''&&$(\"#phone\").val()!=''&&$(\"#sex\").val()!=''){\n" +
                "        if(isPhoneNum($(\"#phone\").val())){\n" +
                "          username=$(\"#name\").val()\n" +
                "          phone=$(\"#phone\").val()\n" +
                "          sex=$(\"#sex\").val()\n" +
                "          if($(\"#number\").val()!=''){\n" +
                "            if(typeof (parseInt($(\"#number\").val()))=='number'){\n" +
                "              numbers=$(\"#number\").val()\n" +
                "              totalfee = fee*numbers\n" +
                "              remarks=$(\"#remarks\").val()\n" +
                "              sex=$(\"#sex\").val()\n" +
                "              if(fee==0){\n" +
                "                $.ajax({\n" +
                "                  type : \"POST\",\n" +
                "                  url : \"http://106.14.162.18:8080/hi/main?hi=24CQRLLN9PBA\",\n" +
                "                  dataType : \"text\",\n" +
                "                  data : {id:orderid,name:username,tel:phone,sex:sex,number:numbers,activityid:activityid},\n" +
                "                  success : function(data) {\n" +
                "                    alert('报名成功')\n" +
                "                    parent.WeixinJSBridge.call('closeWindow');\n" +
                "                    window.close();\n" +
                "                  }\n" +
                "                })\n" +
                "              }else{\n" +
                "                $.ajax({\n" +
                "                  type : \"POST\",\n" +
                "                  url : \"http://106.14.162.18:8080/hi/main?hi=24CQ2GUXV3CB\",\n" +
                "                  dataType : \"text\",\n" +
                "                  data : {id:orderid,name:username,tel:phone,sex:sex,number:numbers,activityid:activityid,totalfee:totalfee},\n" +
                "                  success : function(data) {\n" +
                "                    $.ajax({\n" +
                "                      type : \"POST\",\n" +
                "                      url : \"http://106.14.162.18:8081/getCode\",\n" +
                "                      dataType : \"text\",\n" +
                "                      data : {code:theRequest.code},\n" +
                "                      success : function(e) {\n" +
                "                        openid=e\n" +
                "                        $.ajax({\n" +
                "                          type : \"POST\",\n" +
                "                          url : \"http://106.14.162.18:8081/wxPay\",\n" +
                "                          dataType : \"text\",\n" +
                "                          data : {id:orderid,sfprice:totalfee,openid:openid},\n" +
                "                          success : function(e) {\n" +
                "                            var myresult=JSON.parse(e) \n" +
                "                            WeixinJSBridge.invoke(\n" +
                "                              'getBrandWCPayRequest', {\n" +
                "                                \"appId\":'wx1b98935219078af2',     //公众号名称，由商户传入     \n" +
                "                                \"timeStamp\":myresult.timeStamp,         //时间戳，自1970年以来的秒数     \n" +
                "                                \"nonceStr\":myresult.nonceStr, //随机串     \n" +
                "                                \"package\":myresult.package,     \n" +
                "                                \"signType\":\"MD5\",         //微信签名方式：     \n" +
                "                                \"paySign\":myresult.paySign //微信签名 \n" +
                "                              },\n" +
                "                              function(res){\n" +
                "                              if(res.err_msg == \"get_brand_wcpay_request:ok\" ){\n" +
                "                          \n" +
                "                                $.ajax({\n" +
                "                                  type : \"POST\",\n" +
                "                                  url : \"http://106.14.162.18:8080/hi/main?hi=24CQ2GUXV3Z0\",\n" +
                "                                  dataType : \"text\",\n" +
                "                                  data : {id:orderid},\n" +
                "                                  success : function(data) {\n" +
                "                                   \n" +
                "                                    alert('报名成功')\n" +
                "                                    parent.WeixinJSBridge.call('closeWindow');\n" +
                "                                    window.close();\n" +
                "                                  }\n" +
                "                                })  \n" +
                "                              } \n" +
                "                          });\n" +
                "                                \n" +
                "                          }\n" +
                "                        })\n" +
                "                      }\n" +
                "                    }) \n" +
                "                  \n" +
                "                  }\n" +
                "                })\n" +
                "              }\n" +
                "            }else{\n" +
                "              alert('人数请填数字')\n" +
                "            }\n" +
                "          }else{\n" +
                "            totalfee=numbers*fee\n" +
                "            if(fee==0){\n" +
                "                $.ajax({\n" +
                "                  type : \"POST\",\n" +
                "                  url : \"http://106.14.162.18:8080/hi/main?hi=24CQRLLN9PBA\",\n" +
                "                  dataType : \"text\",\n" +
                "                  data : {id:orderid,name:username,tel:phone,sex:sex,number:numbers,activityid:activityid},\n" +
                "                  success : function(data) {\n" +
                "                    alert('报名成功')\n" +
                "                    parent.WeixinJSBridge.call('closeWindow');\n" +
                "                    window.close();\n" +
                "                  }\n" +
                "                })\n" +
                "              }else{\n" +
                "                $.ajax({\n" +
                "                  type : \"POST\",\n" +
                "                  url : \"http://106.14.162.18:8080/hi/main?hi=24CQ2GUXV3CB\",\n" +
                "                  dataType : \"text\",\n" +
                "                  data : {id:orderid,name:username,tel:phone,sex:sex,number:numbers,activityid:activityid,totalfee:totalfee},\n" +
                "                  success : function(data) {\n" +
                "                    $.ajax({\n" +
                "                      type : \"POST\",\n" +
                "                      url : \"http://106.14.162.18:8081/getCode\",\n" +
                "                      dataType : \"text\",\n" +
                "                      data : {code:theRequest.code},\n" +
                "                      success : function(e) {\n" +
                "                        openid=e\n" +
                "                        $.ajax({\n" +
                "                          type : \"POST\",\n" +
                "                          url : \"http://106.14.162.18:8081/wxPay\",\n" +
                "                          dataType : \"text\",\n" +
                "                          data : {id:orderid,sfprice:totalfee,openid:openid},\n" +
                "                          success : function(e) {\n" +
                "                            var myresult=JSON.parse(e) \n" +
                "                            WeixinJSBridge.invoke(\n" +
                "                              'getBrandWCPayRequest', {\n" +
                "                                \"appId\":'wx1b98935219078af2',     //公众号名称，由商户传入     \n" +
                "                                \"timeStamp\":myresult.timeStamp,         //时间戳，自1970年以来的秒数     \n" +
                "                                \"nonceStr\":myresult.nonceStr, //随机串     \n" +
                "                                \"package\":myresult.package,     \n" +
                "                                \"signType\":\"MD5\",         //微信签名方式：     \n" +
                "                                \"paySign\":myresult.paySign //微信签名 \n" +
                "                              },\n" +
                "                              function(res){\n" +
                "                              if(res.err_msg == \"get_brand_wcpay_request:ok\" ){\n" +
                "                                $.ajax({\n" +
                "                                  type : \"POST\",\n" +
                "                                  url : \"http://106.14.162.18:8080/hi/main?hi=24CQ2GUXV3Z0\",\n" +
                "                                  dataType : \"text\",\n" +
                "                                  data : {id:orderid},\n" +
                "                                  success : function(data) {\n" +
                "                                    \n" +
                "                                    alert('报名成功')\n" +
                "                                    parent.WeixinJSBridge.call('closeWindow');\n" +
                "                                    window.close();\n" +
                "                                  }\n" +
                "                                })              \n" +
                "                              } \n" +
                "                          });\n" +
                "                                \n" +
                "                          }\n" +
                "                        })\n" +
                "                      }\n" +
                "                    }) \n" +
                "                  \n" +
                "                  }\n" +
                "                })\n" +
                "              }\n" +
                "          }\n" +
                "        }else{\n" +
                "          alert('请检查手机号是否有误')\n" +
                "        }\n" +
                "        \n" +
                "        \n" +
                "      }else{\n" +
                "        alert('检测到有未填信息')\n" +
                "      }\n" +
                "    }else{\n" +
                "      alert('报名已截止')\n" +
                "    }\n" +
                "      \n" +
                "    })\n" +
                "  })\n" +
                " \n" +
                "    \n" +
                "\n" +
                "</script>";
        String mypages=mypage1+mypage2+myPage3;

        try {
            byte[] sourceByte = mypages.getBytes("utf-8");
            File file=new File("C:\\facebodywebsite\\"+pageName+".html");
            FileOutputStream outStream = new FileOutputStream(file);
            outStream.write(sourceByte);
        }catch (Exception e){
        }
        return pageName;
    }
}
