package com.im.view;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import cn.net.cyberway.R;

/**
 * 创建时间 : 2017/3/29.
 * 编写人 :  ${yuansk}
 * 功能描述:
 * 版本:
 */

public class CommunityOperationDialog extends Dialog {

    Context context;

    public TextView tv_cancel;
    public TextView tv_again_submit;

    public View middle_line;
    public TextView tv_revoke;

    public CommunityOperationDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.apply_operation_dialog);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_again_submit = findViewById(R.id.tv_again_submit);
        middle_line = findViewById(R.id.middle_line);
        tv_revoke = findViewById(R.id.tv_revoke);
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        int density = getWidthPixels(context);
        params.width = density - 40;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        this.setCanceledOnTouchOutside(true);
    }

    private int getWidthPixels(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }

    public void setShowHide(int status) {
        switch (status) {
            case 3:
            case 4:
                middle_line.setVisibility(View.GONE);
                tv_revoke.setVisibility(View.GONE);
                break;
        }
    }

}