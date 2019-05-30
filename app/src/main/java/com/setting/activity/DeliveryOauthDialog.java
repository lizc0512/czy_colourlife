package com.setting.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import cn.net.cyberway.R;

/**
 * Created by liusw on 2016/1/17.
 */
public class DeliveryOauthDialog extends Dialog {

    public Dialog mDialog;
    public ImageView iv_app_logo;
    public TextView tv_app_name;
    public TextView tv_cancel;
    public TextView tv_define;
    private Context context;

    public DeliveryOauthDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_address_oauth);
        iv_app_logo = (ImageView) findViewById(R.id.iv_app_logo);
        tv_app_name = (TextView) findViewById(R.id.tv_app_name);
        tv_cancel = (TextView) findViewById(R.id.tv_cancel);
        tv_define = (TextView) findViewById(R.id.tv_define);
        tv_cancel.setText(getContext().getResources().getString(R.string.message_cancel));
        tv_define.setText(getContext().getResources().getString(R.string.message_define));
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        int density = getWidthPixels(context);
        params.width = density - 40;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        this.setCanceledOnTouchOutside(false);
    }

    private int getWidthPixels(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
}