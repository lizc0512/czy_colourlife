package com.myproperty.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import cn.net.cyberway.R;

public class PropertyRealNameDialog {

    public Context mContext;
    public Dialog mDialog;
    private TextView tv_msg;
    public Button btn_yes;

    public PropertyRealNameDialog(final Context context, String message) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_property_real_name, null);
        mContext = context;
        mDialog = new Dialog(context, R.style.dialog);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);

        tv_msg = view.findViewById(R.id.tv_msg);
        btn_yes = view.findViewById(R.id.btn_yes);
        tv_msg.setText(message);
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}
