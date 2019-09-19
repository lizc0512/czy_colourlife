package com.customerInfo.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cn.net.cyberway.R;

/**
 * 个人弹窗
 * Created by hxg on 2019/9/18.
 */
public class CustomerInfoDialog {

    public Dialog mDialog;

    public TextView tv_title;
    public TextView tv_msg;
    public TextView tv_continue;
    public TextView tv_leave;

    public CustomerInfoDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_customer_info, null);

        mDialog = new Dialog(context, R.style.custom_dialog_theme);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);

        tv_title = view.findViewById(R.id.tv_title);
        tv_msg = view.findViewById(R.id.tv_msg);
        tv_continue = view.findViewById(R.id.tv_continue);
        tv_leave = view.findViewById(R.id.tv_leave);
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}