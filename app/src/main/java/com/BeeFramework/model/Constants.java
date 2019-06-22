package com.BeeFramework.model;

/**
 * 常量
 * Created by chenql on 16/1/5.
 */
public class Constants {

    //---------------------------生产环境-----------------------------
//    public static final String SERVER_ADDRESS = "https://cmobile.colourlife.com";//以后用cmobile接口
//    public static final String TOKEN_ADDRESS = "https://oauth2czy.colourlife.com";
//    public static final String NEWAPP_ADDRESS = "https://single.colourlife.com/app/"; //新接口的域名
//    public static final String OAUTH_ADDRESS = "http://oauth2czy.colourlife.com/oauth2/";
//    public static final String HOMEAPP_ADDRESS = "https://colourhome.colourlife.com/";
//    public static final String IMAPP_ADDRESS = "https://imapi-czy.colourlife.com";//IM的新域名
//    public static final String BUSINESS_ADDRESS = "https://business.colourlife.com/app/";
//    public static final String USERINFO_ADDRESS = "https://user-czy.colourlife.com/app/";
//    public static final String DELIVERY_ADDRESS = "https://kdaddr-ice.colourlife.com/app/delivery";
//    public static final String PROPERTY_ADDRESS = "https://property.colourlife.com";
//    public static final String VERIFY_ADDRESS = "https://verify.colourlife.com";
//    public static final String LINLI_ADDRESS = "https://linli.colourlife.com/";
//    public static final String BEAN_ADDRESS = "https://userbackend-czy.colourlife.com/";//彩豆积分
//    public static final String EPARKING_ADDRESS = "https://ep.colourlife.com";
//    public static final String FEEDBACK_ADDRESS = "https://service-czy.colourlife.com";
//    public static final String QRCODE_ADDRESS = "https://qrcode.colourlife.com/";
//    public static final String BEHAVIOR_ADDRESS = "https://probe-czy.colourlife.com/";
//    public static final String DOOR_ADDRESS = "https://bluetooth-door.colourlife.com/";
//    public static final String AUTH_APP_ADDRESS = "https://oauth-czy.colourlife.com/";//授权应用
//    public static final String PAY_WALLET_APPID = "327494513335603200";  //双乾彩钱包和支付
//    public static final String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCTFnAR7ORLx0jGzf9Ux1We7yHvRi+kQXKSRmtgBjDCXQzakGm2mrb6EupCkDbUcj4BUs7S7zm/rICQuVNC9fujeJGj"
//            + "cNWRg0XWVtm90XpbTqfKiXzGDHI9W8aULYZ3of/JJ9lyCyjqjigyCdLBPtQ27gOu"
//            + "boDzQuieR2ywPHawzQIDAQAB";
//    public static final boolean CAIWALLET_ENVIRONMENT = true;  //true为彩钱包的正式环境
//    public static int SAVENOHTTPRECORD = 0;  // 正式打包为0
//    public static final String BUTTAGSKEY = "09cbfdabb736305ef0ecb6696650dfdc"; //bugtags的key


    // ---------------------------测试版-----------------------------
    public static final String SERVER_ADDRESS = "https://cmobile-czytest.colourlife.com";
    public static final String TOKEN_ADDRESS = "http://oauth2-czytest.colourlife.com";
    public static final String NEWAPP_ADDRESS = "http://single-czytest.colourlife.com/app/";
    public static final String OAUTH_ADDRESS = "http://oauth2-czytest.colourlife.com/oauth2/";
    public static final String HOMEAPP_ADDRESS = "http://colourhome-czytest.colourlife.com/";
    public static final String IMAPP_ADDRESS = "https://imapi-czytest.colourlife.com";//IM的新域名
    public static final String BUSINESS_ADDRESS = "http://business-czytest.colourlife.com/app/";
    public static final String USERINFO_ADDRESS = "http://user.czytest.colourlife.com/app/";
    public static final String PROPERTY_ADDRESS = "http://property-czytest.colourlife.com";
    public static final String VERIFY_ADDRESS = "http://verify-czytest.colourlife.com";
    public static final String EPARKING_ADDRESS = "https://ep-test.colourlife.com";
    public static final String FEEDBACK_ADDRESS = "https://service-czytest.colourlife.com";
    public static final String QRCODE_ADDRESS = "http://qrcode-czytest.colourlife.com/";
    public static final String BEHAVIOR_ADDRESS = "https://probe-czytest.colourlife.com/";
    public static final String BEAN_ADDRESS = "https://userbackend-czytest.colourlife.com/";//彩豆积分
    public static final String DELIVERY_ADDRESS = "https://kdaddr-ice-test.colourlife.com/app/delivery";
    public static final String LINLI_ADDRESS = "http://linli-czytest.colourlife.com/";
    public static final String DOOR_ADDRESS = "https://bluetoothtest-door.colourlife.com/";//乐开门禁
    public static final String AUTH_APP_ADDRESS = "https://oauth-czytest.colourlife.com/";//授权应用
    public static final String PAY_WALLET_APPID = "323521861252157440";  //双乾彩钱包和支付
    public static final String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDZDqnWph9LxtD0zgtGAYT" +
            "Tf2jYeV+ni5F1o0w3Fag4OOD1YHCRUCXIsFy+iJYmuPf5vMkZrkoiJmKBfkaIzNlrJZzHzq+LsPQNCF86p1nLsuHbkWNvy" +
            "jOEPn/CUryP2Kxme4S+eEqLIeNwp70VOaMuPmRoEZxMDAgvc6Z0DWsVdQIDAQAB";
    /*****关于nohttp请求日记的保存*****/
    public static final boolean CAIWALLET_ENVIRONMENT = false;  //false为彩钱包的测试环境
    public static int SAVENOHTTPRECORD = 1;  // 测试为1，正式打包为0


    // ---------------------------预发版-----------------------------
//    public static final String SERVER_ADDRESS = "https://cmobile-czybeta.colourlife.com";
//    public static final String TOKEN_ADDRESS = "https://oauth2czy-czybeta.colourlife.com";
//    public static final String NEWAPP_ADDRESS = "https://single.colourlife.com/app/"; //新接口的域名
//    public static final String OAUTH_ADDRESS = "http://oauth2czy.colourlife.com/oauth2/";
//    public static final String HOMEAPP_ADDRESS = "https://colourhome-czybeta.colourlife.com/";
//    public static final String BUSINESS_ADDRESS = "https://business-czybeta.colourlife.com/app/";
//    public static final String IMAPP_ADDRESS = "https://imapi-czybeta.colourlife.com";
//    public static final String USERINFO_ADDRESS = "https://user-czybeta.colourlife.com/app/";
//    public static final String PROPERTY_ADDRESS = "http://property-czybeta.colourlife.com";
//    public static final String VERIFY_ADDRESS = "http://verify-czybeta.colourlife.com";
//    public static final String EPARKING_ADDRESS = "https://ep-beat.colourlife.com";
//    public static final String FEEDBACK_ADDRESS = "https://service-czybeta.colourlife.com";
//    public static final String QRCODE_ADDRESS = "https://qrcode.colourlife.com/";
//    public static final String BEHAVIOR_ADDRESS = "https://probe-czybeta.colourlife.com/";
//    public static final String DELIVERY_ADDRESS = "https://kdaddr-ice-beta.colourlife.com/app/delivery";
//    public static final String LINLI_ADDRESS = "http://linli-czybeta.colourlife.com/";
//    public static final String BEAN_ADDRESS = "https://userbackend-czybeta.colourlife.com/";//彩豆积分
//    public static final String DOOR_ADDRESS = "https://bluetoothbeta-door.colourlife.com/";
//    public static final String AUTH_APP_ADDRESS = "https://oauth-czybeta.colourlife.com/";//授权应用
//    public static final String PAY_WALLET_APPID = "323521861252157440";  //双乾彩钱包和支付
//    /*****关于nohttp请求日记的保存*****/
//    public static final String publicKeyString = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCTFnAR7ORLx0jGzf9Ux1We7yHvRi+kQXKSRmtgBjDCXQzakGm2mrb6EupCkDbUcj4BUs7S7zm/rICQuVNC9fujeJGj"
//            + "cNWRg0XWVtm90XpbTqfKiXzGDHI9W8aULYZ3of/JJ9lyCyjqjigyCdLBPtQ27gOu"
//            + "boDzQuieR2ywPHawzQIDAQAB";
//    /*****关于nohttp请求日记的保存*****/
//    public static final boolean CAIWALLET_ENVIRONMENT = false;  //false为彩钱包的测试环境
//    public static int SAVENOHTTPRECORD = 1;  // 测试为1，正式打包为0


    /**
     * true: 获取邀请战绩
     * false: 获取邀请记录
     */
    public static final String INVITE_SUCCESS = "INVITE_SUCCESS";

    /**
     * h5界面调用原生支付回调链接
     */
    public static String payResultUrl = "";

    /**
     * 是否由h5界面进入原生支付流程，默认为false
     */
    public static boolean isFromHtml = false;

    /**
     * 未设置手势密码
     */
    public static final String GESTURE_PWD_UNSET = "0";

    /**
     * 已设置手势密码且手势密码开启
     */
    public static final String GESTURE_PWD_SET_AND_ENABLED = "1";

    /**
     * 已设置手势密码且手势密码关闭
     */
    public static final String GESTURE_PWD_SET_AND_DISABLED = "2";

    /**
     * 修改手势密码 输入错误手势密码五次
     */
    public static final String GESTURE_PWD_SET_FIVE_ERROR = "3";

    /**
     * 控制是否显示埋点
     */
    public static int ISSHOWGEM = 1;

    /**
     * 控制通知栏开关
     */
    public static boolean NOTIFICATION_BTN = true;

    /***彩之云4.0加密的秘钥***/
    public static final String secertKey = "oy4x7fSh5RI4BNc78UoV4fN08eO5C4pj0daM0B8M";

    /**
     * onepass服务器配置的verifyUrl接口
     */
    public static final String GOP_VERIFYURL = Constants.USERINFO_ADDRESS + "geek/checkGateway";

    /***极验深知的ID**/
//    public static final String DEEPKNOWID = "37362fdffee9d0fd2689ff4677092a47";

    /***验证码验证**/
    public static final String DEEPKNOWID = "91233559683da2c4e1e7f36b2a146a78";

    /***bugly**/
    public static final String BUGLY_KEY = "1c98a8240d";

    /**
     * 首页默认的功能模块
     **/
    public static String defaultLayout = "{\n" +
            "    \"code\":0,\n" +
            "    \"message\":\"success\",\n" +
            "    \"content\":[\n" +
            "        {\n" +
            "            \"app_code\":1001,\n" +
            "            \"app_name\":\"recently_module\",\n" +
            "            \"is_show\":1\n" +
            "        },\n" +
            "        {\n" +
            "            \"app_code\":1002,\n" +
            "            \"app_name\":\"open_door\",\n" +
            "            \"is_show\":1\n" +
            "        },\n" +
            "        {\n" +
            "            \"app_code\":1003,\n" +
            "            \"app_name\":\"community_notify\",\n" +
            "            \"is_show\":1\n" +
            "        },\n" +
            "        {\n" +
            "            \"app_code\":1004,\n" +
            "            \"app_name\":\"mannger\",\n" +
            "            \"is_show\":1\n" +
            "        },\n" +
            "        {\n" +
            "            \"app_code\":1005,\n" +
            "            \"app_name\":\"banner\",\n" +
            "            \"is_show\":1\n" +
            "        },\n" +
            "        {\n" +
            "            \"app_code\":1006,\n" +
            "            \"app_name\":\"ad\",\n" +
            "            \"is_show\":1\n" +
            "        }\n" +
            "    ],\n" +
            "    \"contentEncrypt\":\"\"\n" +
            "}";
    public static String defaultHomeFunc = "{\n" +
            "    \"code\": 0,\n" +
            "    \"message\": \"success\",\n" +
            "    \"content\": [\n" +
            "        {\n" +
            "            \"resource_id\": 6,\n" +
            "            \"img\": \"https://pics-czy-cdn.colourlife.com/pro-5c20fecef4142578011.png\",\n" +
            "            \"name\": \"彩生活住宅\",\n" +
            "            \"redirect_uri\": \"http://czz.czy.colourlife.com/?_type=czy#/\",\n" +
            "            \"desc\": \"购房返饭票\",\n" +
            "            \"superscript\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"resource_id\": 8,\n" +
            "            \"img\": \"https://pics-czy-cdn.colourlife.com/pro-5c21a7d9bf929495718.png\",\n" +
            "            \"name\": \"找物业\",\n" +
            "            \"redirect_uri\": \"colourlife://proto?type=FindProperty\",\n" +
            "            \"desc\": \"社区管家样\",\n" +
            "            \"superscript\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"resource_id\": 131,\n" +
            "            \"img\": \"https://pics-czy-cdn.colourlife.com/pro-5c20ffacb6a91274805.png\",\n" +
            "            \"name\": \"饭票商城\",\n" +
            "            \"redirect_uri\": \"https://czyjd.xinfuli.net/api/isv/colourlife/login\",\n" +
            "            \"desc\": \"价低品质优\",\n" +
            "            \"superscript\": \"\"\n" +
            "        },\n" +
            "        {\n" +
            "            \"resource_id\": 4,\n" +
            "            \"img\": \"https://pics-czy-cdn.colourlife.com/pro-5c20ff0ab5dc2512783.png\",\n" +
            "            \"name\": \"停车\",\n" +
            "            \"redirect_uri\": \"https://c.aparcar.cn\",\n" +
            "            \"desc\": \"随时停靠自由派\",\n" +
            "            \"superscript\": \"\"\n" +
            "        }\n" +
            "    ],\n" +
            "    \"contentEncrypt\": \"\"\n" +
            "}";

}
