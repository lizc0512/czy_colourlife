package com.community.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
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
 * 功能描述:举报和删除评论的dialog
 * 版本:
 */

public class TipoffsCommentDialog extends Dialog {
    Context context;

    public TextView tv_dynamics_comment;
    public TextView tv_dynamics_tipoffs;
    public TextView tv_dynamics_cancel;

    public TipoffsCommentDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.dialog_dynamics_tipsoff);
        tv_dynamics_comment = findViewById(R.id.tv_dynamics_comment);
        tv_dynamics_tipoffs = findViewById(R.id.tv_dynamics_tipoffs);
        tv_dynamics_cancel = findViewById(R.id.tv_dynamics_cancel);
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        int density = getWidthPixels(context);
        params.width = density - 40;
        params.gravity = Gravity.BOTTOM;
        window.setAttributes(params);
        this.setCanceledOnTouchOutside(false);
        tv_dynamics_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
    }

    private int getWidthPixels(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
}