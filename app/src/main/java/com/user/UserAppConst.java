package com.user;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.text.TextUtils;

import com.user.protocol.USER;


public class UserAppConst {

    public static String FILEPATH = Environment.getExternalStorageDirectory() + "/.YiChao/.cache/";

    public static final String USERINFO = "user_info";
    public static final String UPDATE = "update";
    public static final String APKNAME = "apkname";
    public static final String DOWNLOADERVERSION = "downloaderversion";
    public static final String IS_LOGIN = "isLogin";
    public static final String USER = "user";
    public static final String ADDRESSLISTCACHE = "addresslistcache";//改版地址的缓存
    public static final String MYPAGELIST = "mypagelist";
    public static final String MYPAGESUBMENU = "mypagesubmenu";
    public static final String NEWHOMEMANAGER = "newhomwmanager";
    public static final String SYSTEMMESSAGE = "systemmessage";
    public static final String SHOPMESSAGE = "shopmessage";
    /***爱加密授权的key***/
    public static final String IJIAMINLICENSEKEY = "456015162582B74A1B2522704A7C173920A87109723C099CC5BDE6C32F43AEADF8DB43946ADC6740AF2886C29E66F800E95C6AD68E6E401F7AFA017DEA940B09BE3DED408E7111C50C57C70132A34F09FFA057B897D71EEA8CF5F96FDD450573E6DA";


    public static final int INTERVAL = 1000;


    public static final String PHOTO_PATH = "photo_path";

    public static final String Colour_Diff = "Colour_Diff";
    /****auth2.0的参数*** **/
    public static final String Colour_access_token = "colorLifeAccessToken";
    public static final String Colour_refresh_token = "colorLifeRefreshToken";
    public static final String Colour_token_type = "colorLifeTokenType";
    public static final String Colour_expires_in = "colorLifeExpiresIn";
    public static final String Colour_get_time = "lastTime";
    public static final String COLOUR_ONEKEY_SHOW = "colour_onekey_show";

    /****auth2.0的参数*****/
    public static final String Colour_mDeviceID = "Colour_mDeviceID";   //极光推送唯一码
    public static final String Colour_token = "Colour_token";   //检验登录的唯一性时用到的token
    public static final String Colour_login_info = "Colour_login_info";
    public static final String Colour_login_mobile = "Colour_login_mobile";
    public static final String Colour_set_password = "Colour_set_password";//是否设置登录密码
    public static final String Colour_login_password = "Colour_login_password";
    public static final String Colour_login_gesture_password = "Colour_login_password";//手势密码
//    public static final String COLOUR_ACTIVITY_DIALOG = "colour_is_dialog";//首页是否有弹窗
//    public static final String COLOUR_BEAN_SIGN_POINT = "colour_bean_sign_point";//我的任务（彩豆）是否有小红点  UserAppConst.COLOUR_BEAN_SIGN_POINT + customer_id

    public static final String Colour_login_key = "Colour_login_key";
    public static final String Colour_user_login = "Colour_user_login";//判断是不是用户操作登录(手动登录不是静默登录)
    public static final String Colour_login_secret = "Colour_login_secret";
    public static final String Colour_login_community_uuid = "Colour_login_community_uuid";
    public static final String Colour_login_community_name = "Colour_login_community_name";
    public static final String Colour_login_community_address = "Colour_login_community_address";
    public static final String Colour_location_permission = "Colour_location_permission";
    public static final String Colour_NAME = "Colour_NAME";
    public static final String Colour_NIACKNAME = "Colour_NIACKNAME";
    public static final String Colour_GENDER = "Colour_GENDER";
    public static final String COLOUR_EMAIL = "colour_email";
    public static final String Colour_CHOOSE_CITY_NAME = "colour_city_name";
    public static final String Colour_HOME_CACHE_NEW = "colour_home_cache_new";
    public static final String Colour_SPLASH_CACHE = "colour_splash_cache";
    public static final String COLOUR_LIFEUSERECORD = "lifeUseRecord"; //生活页面最近使用

    public static final String Colour_REGION_ID = "Colour_REGION_ID";

    public static final String COLOUR_BEAN_SIGN_POINT = "colour_bean_sign_point";//我的任务（彩豆）是否有小红点
    public static final String Colour_head_img = "Colour_head_img";
    public static final String Colour_User_id = "Colour_User_id";
    public static final String Colour_User_uuid = "Colour_User_uuid";
    public static final String Colour_Real_name = "Colour_Real_name";
    public static final String Colour_Permit_position = "colour_permit_position";
    public static final String Colour_Build_id = "colour_build_id";
    public static final String Colour_Build_name = "Colour_Build_name";
    public static final String Colour_Unit_name = "Colour_Unit_name";
    public static final String Colour_Room_name = "Colour_Room_name";
    public static final String IS_CHECK_UPDATE = "is__check_update";
    public static final String Colour_authentication = "Colour_authentication";//是否认证房产 1：是，2：否
    //Baidu地图 ProdName
    public static final String BAIDU_MAP_PRODNAME = "gaibianjia";


    /**
     * 4.0新版首页常量名
     */

    public static final String LIFECATEGORY = "lifecategory";  //生活页面的
    public static final String FINDPROPERTY = "findproperty";
    public static final String FINDNOTIFY = "findnotify";
    public static final String LINLISHOWPOP = "linlishowpop";
    public static final String DOOREDITSHOWPOP = "dooreditshowpop";
    public static final String JOINCOMMUNITY = "joincommunity";
    public static final String THEME = "theme";
    public static final String THEMEUPDATETIME = "themeupdatetime";
    public static final String DELIVERYOAUTHCACHE = "deliveryoauthcache";
    public static final String HOMEDOOROFTEN = "homedooroften";
    public static final String SHOWSHORTCUTTIPS = "showshortcuttips";
    public static final String HAVADOORGRANTED = "havedoorgranted";
    public static final String EDITDOORCOMMUNITYUUID = "editdoorcommunityuuid";
    public static final String EDITDOORCOMMUNITYNAME = "editdoorcommunityname";
    public static final String CURRENTLANGUAGE = "currentlanguage";

    /**
     * 消息和二维码缓存
     */
    public static final String ZXINGCODE = "ZxingCode";


    /**
     * 邻里缓存
     */
    public static final String COMMUNITYFRAGMENTLIST = "communityFragmentList";


    /*
    5.0IM的缓存数据
     */
    public static final String IM_CMANGAG_ERHELPER = "im_cmangag_erhelper";//社群管理助手通知提醒

    public static final String IM_APPLY_FRIEND = "im_apply_friend";//添加好友申请人数

    public static final String COLOURLIFE_JUMP_EXCESSIVE = "/colourlife/currency/jump"; //彩之云对sdk内部跳转到彩之云的通用跳转路径
    public static final String COLOURLIFE_PHONE_ADDRESSBOOK = "/colourlife/phoneAddressBook"; //彩之云对sdk内部跳转到通讯录

    public static final String COLOURLIFE_TRADE_NO = "colourlife_trade_no";//彩之云交易订单号


    public static final String COLOR_HOME_LAYOUT = "color_home_layout";  //首页的布局模块
    public static final String COLOR_HOME_HEADER = "color_home_header";  //首页头部饭票
    public static final String COLOR_HOME_RESOURCE = "color_home_resource";  //首页资源内容
    public static final String COLOR_HOME_FUNCTION = "color_home_function";//首页四个功能模块
    public static final String COLOR_HOME_APPLICATION = "color_home_application";//首页的应用
    public static final String COLOR_HOME_MANAGER = "color_home_manager";//首页的客户经理
    public static final String COLOR_HOME_USEDOOR = "color_home_usedoor";//首页常用的门禁
    public static final String COLOR_HOME_NOTIFICATION = "color_home_notification";//首页的消息通知
    public static final String COLOR_HOME_BANNER = "color_home_banner";//首页的banner
    public static final String COLOR_HOME_ACTIVITY = "color_home_activity";//首页的彩住宅活动
    public static final String COLOR_HOME_GUIDE_STEP = "color_home_guide_step";//首页的引导遮罩步骤

    public static final String COLOR_NOLOGIN_FUNCTION = "color_nologin_function";//首页未登录功能模块
    public static final String COLOR_NOLOGIN_APPLICATION = "color_nologin_application";//首页未登录应用模块
    public static final String COLOR_NOLOGIN_BANNNER = "color_nologin_bannner";//首页未登录banner

    public static final String BEHAVIOR_RECORD_LIST = "behavior_record_list";//用户埋点的数据记录
    public static final String COLOUR_COMMUNITYLIST = "colour_communityList";//门禁小区的选择

    public static final String COLOUR_AUTH_REAL_NAME = "colour_auth_real_name";//腾讯实名认证
    public static final String COLOUR_INTELLIGENCE_DOOR = "colour_intelligence_door";//智能门禁
    public static final String COLOUR_BLUETOOTH_ADVISE = "COLOUR_BLUETOOTH_ADVISE";//蓝牙开门的广告banner
    public static final String COLOUR_DOOR_AUTHOUR_APPLY= "colour_door_authour_apply";//门禁授权和申请记录


   //新版彩钱包
    public static final String COLOUR_WALLET_KEYWORD_SIGN ="colour_wallet_keyword_sign";//彩钱包显示饭票还是积分的标识
    public static final String COLOUR_WALLET_ACCOUNT_LIST ="colour_wallet_account_list";//彩钱包的账户列表
    public static final String COLOUR_OLD_WALLET_DIALOG="colour_old_wallet_dialog";//旧版钱包的弹窗提示
    public static final String COLOUR_POINT_PASSWORD_DIALOG="colour_point_password_dialog";//积分密码的指引弹窗
    public static final String COLOUR_POINT_ACCOUNT_DIALOG="colour_point_account_dialog";//账户安全的指引


    //社区动态
    public static final String COLOUR_DYNAMICS_REAL_IDENTITY="colour_dynamics_real_identity";//用户是否实名的
    public static final String COLOUR_DYNAMICS_TIPOFF_LIST="colour_dynamics_tipoff_list";//举报列表
    public static final String COLOUR_DYNAMICS_NOTICE_NUMBER="colour_dynamics_notice_number";//消息提醒数量

    /**
     * 彩惠人生
     */
    public static final String COLOUR_BENEFIT_PROFILE = "color_benefit_profile";//彩惠的顶部
    public static final String COLOUR_BENEFIT_BANNER = "color_benefit_banner";//彩惠的banner
    public static final String COLOUR_BENEFIT_HOT = "color_benefit_hot";//彩惠的热点
    public static final String COLOUR_BENEFIT_CHANNEL = "color_benefit_channel";//彩惠的推荐全部
    public static final String COLOUR_BENEFIT_FIND = "color_benefit_find";//彩惠的发现

    /**
     * 手势密码是否开启
     * mobile + GESTURE_OPENED
     */
    public static final String GESTURE_OPENED = "GESTURE_OPENED";
    public static final String OPEN_CODE = "OPEN_CODE";//第三方登录路获取到的Uid
    public static final String SOURCE = "SOURCE_THIRD";//第三方登录（qq wechat）
    public static SharedPreferences shared;

    /**
     * 我的 彩豆
     */
    public static final String BEAN_NUMBER = "bean_number";//彩豆数量
    public static final String BEAN_SIGN = "bean_sign";//彩豆签到
    public static final String BEAN_DUTY = "bean_duty";//彩豆任务

    /**
     * 邀请好友
     */
    public static final String INVITE_FRIEND = "invite_friend";//邀请好友数据
    public static final String INVITE_INVITE = "invite_invite";//我的邀请

    public static com.user.protocol.USER getUser(Context context) {
        USER user = new USER();
        if (null != context) {
            shared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
            String name = shared.getString(Colour_NAME, "");
            String nickname = shared.getString(Colour_NIACKNAME, "");
            user.id = shared.getInt(Colour_User_id, 0);
            if (!TextUtils.isEmpty(name)) {
                user.nickname = name;
            } else if (!TextUtils.isEmpty(nickname)) {
                user.nickname = nickname;
            } else {
                user.nickname = "彩多多";
            }
        }
        return user;
    }

}
