package com.gem.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.BeeFramework.model.Constants;
import com.gem.GemConstant;
import com.gem.IsTimeData;
import com.gem.model.GemModel;
import com.user.UserAppConst;

/**
 * Created by zhu29 on 2016/11/10.
 * 宝石埋点工具类
 */

public class GemDialogUtil {



    public static void showGemDialog(ImageView view, final Context context, String section_code, String url) {
        if (null != context) {
            SharedPreferences mShared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
            if (mShared.getBoolean(UserAppConst.IS_LOGIN, false) && GemConstant.isShow) {
                int customer_id = mShared.getInt(UserAppConst.Colour_User_id, 0);
                String version = "1";//版本号 当前接口为1（2016-12-21）
                if (Constants.ISSHOWGEM == 1) {
                    getData(view, context, section_code, url, customer_id, version);
                } else if (Constants.ISSHOWGEM == 0) {
                    if (!TextUtils.isEmpty(IsTimeData.getInstance().getLevel())) {
                        if (IsTimeData.getInstance().getLevel().equals("0")) {
                            return;
                        } else if (IsTimeData.getInstance().getLevel().equals("1")) {
                            getData(view, context, section_code, url, customer_id, version);
                        }
                    }
                }
            } else {
                if (view!=null){
                    view.setVisibility(View.GONE);
                }
            }
        }
    }


    public static void getData(@NonNull final ImageView view, final Context mContext, String section_code, String url
            , int customer_id, String version) {
        GemModel gemModel=new GemModel(mContext);
        gemModel.getGemData(section_code,url,customer_id,version,view);

    }
}
