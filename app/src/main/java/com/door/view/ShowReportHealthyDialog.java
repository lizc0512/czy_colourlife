package com.door.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.net.cyberway.R;


/**
 * 开门结果展示Dialog
 * 2018/08/12
 */
public class ShowReportHealthyDialog extends Dialog {
    private Context context;
    public ImageView iv_report_healthy;
    private ImageView close_dialog;



    public ShowReportHealthyDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.show_upload_report_dialog);
        iv_report_healthy=findViewById(R.id.iv_report_healthy);
        close_dialog=findViewById(R.id.close_dialog);
        close_dialog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        Window window = getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        int density = getWidthPixels(context);
        params.width = density - 40;
        params.gravity = Gravity.CENTER;
        this.setCanceledOnTouchOutside(false);
    }

    private int getWidthPixels(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
}