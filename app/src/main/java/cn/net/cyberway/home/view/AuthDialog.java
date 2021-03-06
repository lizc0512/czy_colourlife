package cn.net.cyberway.home.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import cn.net.cyberway.R;

/**
 * 权限弹框
 * Created by hxg on 2019/5/15.
 */
public class AuthDialog {

    public Dialog mDialog;

    public ImageView iv_close;
    public ImageView iv_goto;
    public TextView tv_pass;

    public AuthDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_auth, null);

        mDialog = new Dialog(context, R.style.custom_dialog_theme);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);

        iv_close = view.findViewById(R.id.iv_close);
        iv_goto = view.findViewById(R.id.iv_goto);
        tv_pass = view.findViewById(R.id.tv_pass);
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}