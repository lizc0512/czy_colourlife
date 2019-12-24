package com.eparking.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.View;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.csh.colourful.life.view.pickview.OptionsPickerView;
import cn.csh.colourful.life.view.pickview.TimePickerView;
import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.helper
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/14 11:08
 * @change
 * @chang time
 * @class describe
 */
public class OptionsPickerViewUtils {

    public static OptionsPickerView showPickerView(Context mContext, int titleId, final int type, List lists, final OptionsPickerInterface optionsPickerInterface) {// 弹出选择器
        if (lists == null) {
            lists = new ArrayList();
            SharedPreferences shared = mContext.getSharedPreferences(UserAppConst.USERINFO, 0);
            String historyCache = "";
            switch (type) {
                case 0:  //车牌
                    historyCache = shared.getString(ConstantKey.EPARKINGCARHISTORYBRAND, "");

                    break;
                case 1: //停车场
                    historyCache = shared.getString(ConstantKey.EPARKINGCARHISTORYSTATION, "");

                    break;
                case 2:  //车位
                    historyCache = shared.getString(ConstantKey.EPARKINGCARHISTORYLOCK, "");

                    break;
            }
        }
        final List finalLists = lists;
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(mContext, new OptionsPickerView.OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                Object object = finalLists.get(options1);

            }
        })
                .setTitleText(mContext.getResources().getString(titleId))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setCancelColor(Color.parseColor("#629ef0"))
                .setSubmitColor(Color.parseColor("#629ef0"))
                .setCancelText(mContext.getResources().getString(R.string.message_cancel))
                .setSubmitText(mContext.getResources().getString(R.string.message_define))
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(lists);//一级选择器
        pvOptions.show();
        if (lists == null || lists.size() == 0) {
            ToastUtil.toastShow(mContext, mContext.getResources().getString(R.string.no_filter_message));
            pvOptions.dismiss();
        }
        return pvOptions;
    }

    //展示选择时间的dialog的方法
    public static void showTimeDialog(Context contxt, int titleId, final int type, final OptionsPickerInterface optionsPickerInterface) {
        Calendar selectedDate = Calendar.getInstance();
        TimePickerView pvTime = new TimePickerView.Builder(contxt, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                optionsPickerInterface.choicePickResult(type, TimeUtil.getDateToString(date), String.valueOf(date.getTime() / 1000));
            }
        })
                .setType(new boolean[]{true, true, true, true, true, false})
                .setCancelText(contxt.getResources().getString(R.string.message_cancel))//取消按钮文字
                .setSubmitText(contxt.getResources().getString(R.string.message_define))//确认按钮文字
                .setContentSize(16)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText(contxt.getResources().getString(titleId))//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.parseColor("#333b46"))//标题文字颜色
                .setSubmitColor(Color.parseColor("#27a2f0"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#27a2f0"))//取消按钮文字颜色
                .setTitleBgColor(Color.parseColor("#f5f5f5"))//标题背景颜色 Night mode
                .setBgColor(Color.parseColor("#ffffff"))//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 默认是系统时间*/
                .setRange(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.YEAR) + 1)//默认是1900-2100年
                .setLabel("年", "月", "日", "时", "分", "")
                .build();
        pvTime.show();
    }
}
