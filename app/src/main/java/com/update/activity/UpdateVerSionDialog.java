package com.update.activity;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

import cn.net.cyberway.R;

/**
 * Created by HX_CHEN on 2016/1/18.
 */
public class UpdateVerSionDialog {
    public Dialog mDialog;
    public Button ok;
    public ImageView cancel;
    public ListView listView;

    public UpdateVerSionDialog(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_update, null);
        mDialog = new Dialog(context, R.style.custom_dialog_theme);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(false);
        ok = (Button) view.findViewById(R.id.tv_ok);
        cancel = (ImageView) view.findViewById(R.id.iv_close);
        listView = (ListView) view.findViewById(R.id.list_update);

    }

    public void show() {
        try {
            mDialog.show();
        } catch (Exception e) {

        }

    }

    public void dismiss() {
        try {
            mDialog.dismiss();
        } catch (Exception e) {

        }

    }

}
