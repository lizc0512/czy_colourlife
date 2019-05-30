package com.setting.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import cn.net.cyberway.R;

/**
 * Created by liusw on 2016/1/17.
 */
public class HtmlPayDialog {

    public Dialog   mDialog;
    public TextView tv_content;
    public TextView   tv_finish_pay;
    public TextView   tv_again_pay;
    public TextView   tv_cancel_pay;

    public HtmlPayDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_h5pay_result, null);

        mDialog = new Dialog(context, R.style.custom_dialog_theme);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);

        tv_content              = (TextView) view.findViewById(R.id.tv_content);
        tv_finish_pay     = (TextView) view.findViewById(R.id.tv_finish_pay);
        tv_again_pay    = (TextView) view.findViewById(R.id.tv_again_pay);
        tv_cancel_pay    = (TextView) view.findViewById(R.id.tv_cancel_pay);

    }

    public void setContent(String content){
        tv_content.setText(content);
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}