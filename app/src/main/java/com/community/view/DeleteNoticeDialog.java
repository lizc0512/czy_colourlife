package com.community.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import cn.net.cyberway.R;

/**
 * 创建时间 : 2017/3/29.
 * 编写人 :  ${yuansk}
 * 功能描述:删除图片提示的dialog
 * 版本:
 */

public class DeleteNoticeDialog extends Dialog {
    Context context;

    public Button btn_define;
    public Button btn_cancel;

    public DeleteNoticeDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.delete_msg_notice);
        btn_define = (Button) findViewById(R.id.btn_define);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_cancel.setTextColor(Color.parseColor("#999999"));
        btn_define.setTextColor(Color.parseColor("#DE4A47"));
        TextView textView = findViewById(R.id.tv_desc);
        textView.setText("确认删除吗？");
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        int density = getWidthPixels(context);
        params.width = density - 40;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        this.setCanceledOnTouchOutside(false);
        btn_cancel.setOnClickListener(v -> dismiss());
    }

    private int getWidthPixels(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
}