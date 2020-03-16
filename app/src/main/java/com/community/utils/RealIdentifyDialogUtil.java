package com.community.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import com.realaudit.activity.RealCommonSubmitActivity;
import com.setting.activity.EditDialog;

/**
 * author:yuansk
 * cretetime:2020/2/26
 * desc:实名认证的弹窗
 **/
public class RealIdentifyDialogUtil {

    public static void showGoIdentifyDialog(Activity activity) {
        EditDialog noticeDialog = new EditDialog(activity);
        noticeDialog.setContent("此功能需通过实名认证后才能使用");
        noticeDialog.tv.setTextColor(Color.parseColor("#333333"));
        noticeDialog.show();
        noticeDialog.left_button.setText("取消");
        noticeDialog.left_button.setTextColor(Color.parseColor("#999999"));
        noticeDialog.left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noticeDialog != null) {
                    noticeDialog.dismiss();
                }
            }
        });
        noticeDialog.right_button.setText("去认证");
        noticeDialog.right_button.setTextColor(Color.parseColor("#108EE9"));
        noticeDialog.right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noticeDialog != null) {
                    noticeDialog.dismiss();
                }
                Intent intent = new Intent(activity, RealCommonSubmitActivity.class);
                activity.startActivity(intent);
            }
        });
    }
}
