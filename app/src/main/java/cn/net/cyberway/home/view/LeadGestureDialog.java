package cn.net.cyberway.home.view;

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
 * 功能描述:
 * 版本:
 */

public class LeadGestureDialog extends Dialog
{
    Context context;

    public TextView dialog_content;
    public TextView dialog_title;

    public Button btn_open;
    public Button btn_cancel;

    public LeadGestureDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.lead_set_gesture);
        dialog_title = (TextView) findViewById(R.id.dialog_title);
        dialog_content = (TextView) findViewById(R.id.dialog_content);
        btn_open = (Button) findViewById(R.id.btn_open);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
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

    public void setContent(String content){
        dialog_content.setTextColor(Color.parseColor("#bfc7cc"));
        dialog_content.setText(content);
    }
    public void setTitle(String title){
        dialog_title.setText(title);
    }
}