package com.setting.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.net.cyberway.R;

/**
 * Created by liusw on 2016/1/17.
 */
public class PermissionDialog {

    public Dialog mDialog;
    public TextView tv;
    public Button left_button;
    public Button right_button;
    public View middle_view;

    public PermissionDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_permission_notice, null);

        mDialog = new Dialog(context, R.style.custom_dialog_theme);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);

        tv = (TextView) view.findViewById(R.id.tv);
        left_button = (Button) view.findViewById(R.id.left_button);
        right_button = (Button) view.findViewById(R.id.right_button);
        middle_view = view.findViewById(R.id.middle_view);

    }

    public void setContent(String content) {
        tv.setText(content);
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}