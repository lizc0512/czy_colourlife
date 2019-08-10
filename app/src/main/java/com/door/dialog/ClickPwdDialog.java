package com.door.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.net.cyberway.R;

/**
 * Created by hxg on 2019/8/8.
 */
public class ClickPwdDialog {

    public Dialog mDialog;
    public ImageView iv_close;
    public TextView tv_pwd_num;
    public TextView tv_pwd_time;
    public TextView tv_pwd_record;
    public TextView tv_pwd_get;

    public ClickPwdDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_click_pwd, null);

        mDialog = new Dialog(context, R.style.custom_dialog_theme);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);

        iv_close = view.findViewById(R.id.iv_close);
        tv_pwd_num = view.findViewById(R.id.tv_pwd_num);
        tv_pwd_time = view.findViewById(R.id.tv_pwd_time);
        tv_pwd_record = view.findViewById(R.id.tv_pwd_record);
        tv_pwd_get = view.findViewById(R.id.tv_pwd_get);
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}