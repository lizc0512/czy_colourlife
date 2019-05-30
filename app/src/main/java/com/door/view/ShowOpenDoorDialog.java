package com.door.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tmall.ultraviewpager.UltraViewPager;

import cn.net.cyberway.R;


/**
 * 开门结果展示Dialog
 * 2018/08/12
 */
public class ShowOpenDoorDialog extends Dialog {
    Context context;
    public RelativeLayout rl_opendoor;
    public ImageView iv_opendoor_close_ok;
    public ImageView iv_opendoor_fp_ok;
    public ImageView iv_opendoor_img;
    public TextView tv_opendoor_money_ok;
    public TextView tv_opendoor_message_ok;
    public TextView tv_opendoor_cqb_ok;
    public UltraViewPager ad_opendoor_banner;


    public ShowOpenDoorDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.show_opendoor_result);
        rl_opendoor = (RelativeLayout) findViewById(R.id.rl_opendoor);
        iv_opendoor_close_ok = (ImageView) findViewById(R.id.iv_opendoor_close_ok);
        iv_opendoor_fp_ok = (ImageView) findViewById(R.id.iv_opendoor_fp_ok);
        iv_opendoor_img = (ImageView) findViewById(R.id.iv_opendoor_img);
        tv_opendoor_money_ok = (TextView) findViewById(R.id.tv_opendoor_money_ok);
        tv_opendoor_message_ok = (TextView) findViewById(R.id.tv_opendoor_message_ok);
        tv_opendoor_cqb_ok = (TextView) findViewById(R.id.tv_opendoor_cqb_ok);
        ad_opendoor_banner = (UltraViewPager) findViewById(R.id.ad_opendoor_banner);
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        int density = getWidthPixels(context);
        params.width = density - 40;
        params.gravity = Gravity.CENTER;
//        window.setAttributes(params);
        this.setCanceledOnTouchOutside(false);
    }

    private int getWidthPixels(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
}