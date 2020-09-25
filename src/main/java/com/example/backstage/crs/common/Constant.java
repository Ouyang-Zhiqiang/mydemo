package com.example.backstage.crs.common;

public class Constant {
    /*P=永久；T=临时*/
    public static  final String STOPTYP_TEMPORARY = "T";
    public static  final String STOPTYP_FOREVER = "P";

    /*A=增加；R=减少*/
    public static final String RECTYPE_ADD="A";
    public static final String RECTYPE_SUBTRACT="R";

    /*课程类型：T团课、P私课*/
    public static final String CARDTYPE_LEAGUE="T";
    public static final String CARDTYPE_PRIVATE="P";

    /*性别：女1，男0*/
    public static final String SEX_WOMAN="1";
    public static final String SEX_MAN="0";

    /*约课是否预约：1已预约、2已取消*/
    public static final String COURSE_RESERVED="1";
    public static final String COURSE_CANCELLED="2";

    /*约课是否签到：1已签到、0未签到*/
    public static final String COURSE_SIGNIN="1";
    public static final String COURSE_NOSIGNIN="0";

    /*购卡类型：首次购卡F、续卡C*/
    public static final String FIRSTTIME="F";
    public static final String RENEWALCARD="C";

    /*用户来源:0自然到店  1老带新  2拉访  3大众点评  4活动  5其他*/
    public static final Integer SOURCETYPE_NATRUALVISIT=0;
    public static final Integer SOURCETYPE_OLDBRINGSNEW=1;
    public static final Integer SOURCETYPE_CANVASSING=2;
    public static final Integer SOURCETYPE_COMMENTS=3;
    public static final Integer SOURCETYPE_ACTIVITY=4;
    public static final Integer SOURCETYPE_OTHER=5;


    /*12正式卡、13员工卡、14赠卡*/
    public static final Integer CARDNUMBER_OFFICIAL_CARD=12;
    public static final Integer CARDNUMBER_EMPLOYEE_CARD=13;
    public static final Integer CARDNUMBER_FREE_CARD=14;

    /*支付状态：0未支付、1支付失败、2支付成功*/
    public static final Integer UNPAID=0;
    public static final Integer PAYMENTSUCCESSFUL=1;
    public static final Integer PAYMENTFAILD=2;

    /*支付方式：刷卡：1=POS；转账：2=TRAC；线上支付：3=ONLINE；现金：4=CASH*/
    public static final Integer PAYMENTMETHOD_POS=1;
    public static final Integer PAYMENTMETHOD_TRAC=2;
    public static final Integer PAYMENTMETHOD_ONLINE=3;
    public static final Integer PAYMENTMETHOD_CASH=4;


    /*支付状态（0未付款、1拼团中、2拼团成功、3拼团失败、4退款成功）*/
    public static final Integer PAYSTAMENT_UNPAID=0;
    public static final Integer PAYSTAMENT_INGROUNP=1;
    public static final Integer PAYSTAMENT_SUCCESS=2;
    public static final Integer PAYSTAMENT_FAILD=3;
    public static final Integer PAYSTAMENT_REFUNDSUCCESSFUL=4;


    /**/


}
