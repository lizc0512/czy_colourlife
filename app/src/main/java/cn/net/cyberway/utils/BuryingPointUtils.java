package cn.net.cyberway.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.home.entity.BehaviorEntity;
import cn.net.cyberway.model.UserBehaviorModel;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.utils
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/12/29 16:38
 * @change
 * @chang time
 * @class describe   彩之云首页埋点上传数据的工具类
 */
public class BuryingPointUtils {
    public static final String UPLOAD_DETAILS = "upload_details";//上传的内容
    public static final String ENTER_TIME = "enter_time";//页面进入时间
    public static final String LEAVE_TIME = "leave_time";//页面离开时间
    public static final String homePageName = "10000";
    public static final String channel = "yingyongbao";
    public static final String divisionSign = "divisionSign";
    public static final String homeTopCode = "10100";
    public static final String homeFuncCode = "10200";
    public static final String homeRecentlyCode = "10300";
    public static final String homeDoorCode = "10400";
    public static final String homeNotificationCode = "10500";
    public static final String homeManagerCode = "10600";
    public static final String homeBannnerCode = "10700";
    public static final String homeActivityCode = "10800";

    public static void uploadPageStayTime(Context context, String pageName, String page_code, String app_code, String app_name, long enterTime, long exitTime) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        final SharedPreferences.Editor editor = sharedPreferences.edit();
        final List<BehaviorEntity> behaviorEntityList = new ArrayList<>();
        if (!TextUtils.isEmpty(app_code) && !app_code.equalsIgnoreCase("null")) {
            BehaviorEntity behaviorEntity = new BehaviorEntity();
            behaviorEntity.setApp_code(app_code);
            behaviorEntity.setEntry_time(enterTime);
            behaviorEntity.setDeparture_time(exitTime);
            behaviorEntity.setRefer_module(page_code);
            behaviorEntity.setApp_name(app_name);
            behaviorEntity.setRefer_page(pageName);
            behaviorEntity.setExit_app(2); // 退出该页面是是否离开app , 1退出，2不退出
            behaviorEntityList.add(behaviorEntity);
        }
        String recordCache = sharedPreferences.getString(UserAppConst.BEHAVIOR_RECORD_LIST, "");
        if (!TextUtils.isEmpty(recordCache)) {
            List<BehaviorEntity> cacheEntityList = GsonUtils.gsonToList(recordCache, BehaviorEntity.class);
            behaviorEntityList.addAll(cacheEntityList);
        }
        if (behaviorEntityList.size() >= 10) {
            String uploadRecord = GsonUtils.gsonString(behaviorEntityList);
            UserBehaviorModel userBehaviorModel = new UserBehaviorModel(context);
            userBehaviorModel.uploadUserBehavior(0, uploadRecord, new NewHttpResponse() {
                @Override
                public void OnHttpResponse(int what, String result) {
                    if (TextUtils.isEmpty(result)) {
                        String recordList = GsonUtils.gsonString(behaviorEntityList);
                        editor.putString(UserAppConst.BEHAVIOR_RECORD_LIST, recordList).apply();
                    } else {
                        editor.putString(UserAppConst.BEHAVIOR_RECORD_LIST, "").apply();
                    }
                }
            });
        } else {
            String recordList = GsonUtils.gsonString(behaviorEntityList);
            editor.putString(UserAppConst.BEHAVIOR_RECORD_LIST, recordList).apply();
        }
    }

    public static void uploadClickMethod(Context context, String pageName, String homeSectionCode, String functionName, String functionCode) {
        uploadPageStayTime(context, pageName, homeSectionCode, functionCode, functionName, 0, 1);
    }
}
