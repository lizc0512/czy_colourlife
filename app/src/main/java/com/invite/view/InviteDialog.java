package com.invite.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import cn.net.cyberway.R;

/**
 * Created by hxg on 2019/5/29.
 */
public class InviteDialog {

    public Dialog mDialog;

    public ImageView iv_close;
    public ImageView iv_code;

    public InviteDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_invite, null);

        mDialog = new Dialog(context, R.style.custom_dialog_theme);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);

        iv_close = view.findViewById(R.id.iv_close);
        iv_code = view.findViewById(R.id.iv_code);
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}