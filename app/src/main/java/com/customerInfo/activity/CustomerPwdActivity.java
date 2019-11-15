package com.customerInfo.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseFragmentActivity;
import com.cashier.adapter.ViewPagerAdapter;
import com.customerInfo.fragment.ChangePawdByCoderagment;
import com.customerInfo.fragment.ChangePawdByOldFragment;
import com.eparking.view.NoViewPager;
import com.lhqpay.ewallet.passwordview_cqb.GridPasswordView_cqb;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * Created by liusw on 2016/1/14.
 */
public class CustomerPwdActivity extends BaseFragmentActivity implements View.OnClickListener {

    private FrameLayout czyTitleLayout;
    private ImageView mBack;
    private TextView mTitle;
    private android.support.design.widget.TabLayout cardholder_tablayout;
    private NoViewPager cardholder_viewpager;
    private String[] tabTitleArray = {"原密码验证", "手机验证码验证"};
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_pawd_layout);
        initPublic();
        initView();
    }


    private void initPublic() {
        czyTitleLayout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        mBack.setOnClickListener(this);
        mTitle.setText("修改登录密码");
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czyTitleLayout, mBack, mTitle);
    }

    private void initView() {
        cardholder_tablayout = findViewById(R.id.cardholder_tablayout);
        cardholder_viewpager = findViewById(R.id.cardholder_viewpager);
        for (int i = 0; i < tabTitleArray.length; i++) {
            cardholder_tablayout.addTab(cardholder_tablayout.newTab().setText(tabTitleArray[i]));
        }
        LinearLayout linearLayout = (LinearLayout) cardholder_tablayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.tablayout_line));
        fragmentList.add(new ChangePawdByOldFragment());
        fragmentList.add(new ChangePawdByCoderagment());
        cardholder_tablayout.setTabMode(TabLayout.MODE_FIXED);
        cardholder_tablayout.setSelectedTabIndicatorHeight(4);
        cardholder_tablayout.setSelectedTabIndicatorColor(Color.parseColor("#27a2f0"));
        cardholder_tablayout.setTabTextColors(Color.parseColor("#333b46"), Color.parseColor("#27a2f0"));
        cardholder_tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        cardholder_viewpager.setNeedScroll(true);
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(), this, fragmentList, tabTitleArray);
        cardholder_viewpager.setAdapter(adapter);
        cardholder_viewpager.setOffscreenPageLimit(fragmentList.size());
        cardholder_tablayout.setupWithViewPager(cardholder_viewpager);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }
}
