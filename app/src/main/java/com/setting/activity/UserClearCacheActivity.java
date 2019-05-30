package com.setting.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.youmai.hxsdk.FileCacheManager;
import com.youmai.hxsdk.HuxinSdkManager;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.setting.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/26 16:31
 * @change
 * @chang time
 * @class describe
 */

public class UserClearCacheActivity extends BaseActivity implements View.OnClickListener {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_cache_size;
    private Button btn_clear;
    private String cacheSize = "0.0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clear_cache);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        tv_cache_size = findViewById(R.id.tv_cache_size);
        btn_clear = findViewById(R.id.btn_clear);
        user_top_view_title.setText(getResources().getString(R.string.clear_cache));
        cacheSize = FileCacheManager.getCacheSize();
        if (!"0.0".equals(cacheSize)) {
            tv_cache_size.setText(cacheSize);
            btn_clear.setText(getResources().getString(R.string.once_clear));
            btn_clear.setTextColor(getResources().getColor(R.color.white));
            btn_clear.setBackgroundResource(R.drawable.rect_round_blue);
        } else {
            tv_cache_size.setText(cacheSize);
            btn_clear.setText(getResources().getString(R.string.notneed_clear));
            btn_clear.setTextColor(getResources().getColor(R.color.color_333b46));
            btn_clear.setBackgroundResource(R.drawable.shape_white_border);
        }
        user_top_view_back.setOnClickListener(this);
        btn_clear.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_clear:
                if (!"0.0".equals(cacheSize)) {
                    HuxinSdkManager.instance().clearCache(getApplicationContext());
                    HuxinSdkManager.instance().clearMsgBadge(getApplicationContext());
                    ToastUtil.toastShow(getApplicationContext(), getResources().getString(R.string.clear_cache_success));
                }
                finish();
                break;

        }
    }
}
