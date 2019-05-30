package com.eparking.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.ConstantKey;
import com.eparking.helper.ParkingActivityUtils;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  e停 商家、管家板块
 */
public class EparkingBusinessSectionActivity extends BaseActivity implements View.OnClickListener,NewHttpResponse{

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private ListView lv_section;
    private TextView  tv_hotline;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business_section);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_business_section));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        tv_hotline= findViewById(R.id.tv_hotline);
        lv_section=findViewById(R.id.lv_section);
        tv_hotline.setText(getResources().getString(R.string.eparking_hotline)+":"+shared.getString(ConstantKey.EPARKINGSERVICEPHONE,"10101778"));
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        
    }
}
