package com.eparking.helper;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.view.View;

import com.BeeFramework.Utils.ToastUtil;
import com.permission.AndPermission;
import com.setting.activity.EditDialog;
import com.user.UserAppConst;

import java.util.ArrayList;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.helper
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/23 8:59
 * @change
 * @chang time
 * @class describe   e停客服电话提示
 */
public class PermissionUtils {

    public static void showPhonePermission(Context context) {
        SharedPreferences shared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        String servicePhone = shared.getString(ConstantKey.EPARKINGSERVICEPHONE, "10101778");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermission(context, Manifest.permission.CALL_PHONE)) {
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + servicePhone));//跳转到拨号界面，同时传递电话号码
                context.startActivity(dialIntent);
            } else {
                ArrayList<String> permission = new ArrayList<>();
                permission.add(Manifest.permission.CALL_PHONE);
                if (AndPermission.hasAlwaysDeniedPermission(context, permission)) {
                    ToastUtil.toastShow(context, context.getResources().getString(R.string.user_callpermission_notice));
                } else {
                    AndPermission.with(context)
                            .permission(Manifest.permission.CALL_PHONE)
                            .start();
                }
            }
        } else {
            Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + servicePhone));//跳转到拨号界面，同时传递电话号码
            context.startActivity(dialIntent);
        }
    }

    public static void showPhonePermission(Context context, String phone) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermission(context, Manifest.permission.CALL_PHONE)) {
                showCallDialog(context, phone);
            } else {
                ArrayList<String> permission = new ArrayList<>();
                permission.add(Manifest.permission.CALL_PHONE);
                if (AndPermission.hasAlwaysDeniedPermission(context, permission)) {
                    ToastUtil.toastShow(context, context.getResources().getString(R.string.user_callpermission_notice));
                } else {
                    AndPermission.with(context)
                            .permission(Manifest.permission.CALL_PHONE)
                            .start();
                }
            }
        } else {
            showCallDialog(context, phone);
        }
    }

    private static void showCallDialog(final Context context, final String phone) {
        final EditDialog mEditDialog = new EditDialog(context);
        mEditDialog.show();
        mEditDialog.setContent(phone);
        mEditDialog.left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditDialog.dismiss();
            }
        });
        mEditDialog.right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditDialog.dismiss();
                Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));//跳转到拨号界面，同时传递电话号码
                context.startActivity(dialIntent);
            }
        });
    }
}