package com.customerInfo.activity;

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
 * 彩豆弹窗
 * Created by hxg on 2019/4/16.
 */
public class BeanExplainDialog extends Dialog {
    Context context;

    public ImageView iv_close;
    public TextView tv_bean_all;
    public TextView tv_bean_can;
    public TextView tv_bean_reserve;

    public BeanExplainDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_bean_explain);
        iv_close = findViewById(R.id.iv_close);
        tv_bean_all = findViewById(R.id.tv_bean_all);
        tv_bean_can = findViewById(R.id.tv_bean_can);
        tv_bean_reserve = findViewById(R.id.tv_bean_reserve);
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