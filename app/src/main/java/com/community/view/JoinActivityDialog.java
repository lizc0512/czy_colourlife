package com.community.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.GridLayout;
import android.widget.TextView;

import cn.net.cyberway.R;

/**
 * author:yuansk
 * cretetime:2020/3/19
 * desc:分享活动的对话框
 **/
public class JoinActivityDialog extends Dialog {

    private Context context;
    public TextView tv_join_notice;
    public TextView tv_cancel_join;
    public TextView tv_define_join;
    public GridLayout join_activity_photo;

    public JoinActivityDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_join_activity);
        tv_join_notice = findViewById(R.id.tv_join_notice);
        tv_cancel_join = findViewById(R.id.tv_cancel_join);
        tv_define_join = findViewById(R.id.tv_define_join);
        join_activity_photo = findViewById(R.id.join_activity_photo);
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        int density = getWidthPixels(context);
        params.width = density;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        this.setCanceledOnTouchOutside(true);
    }


    private int getWidthPixels(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
}
