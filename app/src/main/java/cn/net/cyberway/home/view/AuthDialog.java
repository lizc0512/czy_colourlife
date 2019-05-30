package cn.net.cyberway.home.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import cn.net.cyberway.R;

/**
 * Created by hxg on 2019/5/15.
 */
public class AuthDialog {

    public Dialog mDialog;

    public ImageView iv_close;
    public ImageView iv_goto;

    public AuthDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_auth, null);

        mDialog = new Dialog(context, R.style.custom_dialog_theme);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);

        iv_close = view.findViewById(R.id.iv_close);
        iv_goto = view.findViewById(R.id.iv_goto);
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}