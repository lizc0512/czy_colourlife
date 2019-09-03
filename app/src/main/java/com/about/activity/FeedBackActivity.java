package com.about.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseFragmentActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.about.model.FeedbackModel;
import com.about.protocol.FeedBackRedirectEntity;
import com.cardcoupons.fragment.CouponsFragment;
import com.cashier.adapter.ViewPagerAdapter;
import com.nohttp.utils.GsonUtils;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * Created by HENXIAN_C on 2016/1/8.
 */
public class FeedBackActivity extends BaseFragmentActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;   //标题
    private ImageView imageView_back;//返回
    private TextView user_top_view_right;
    private TabLayout feedback_tablayout;
    private ViewPager feedback_viewpager;
    private List<Fragment> fragmentList = new ArrayList<>();
    private String[] tabTitleArray = null;
    private CouponsFragment couponsFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_main_layout);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        user_top_view_right.setOnClickListener(this);
        user_top_view_right.setText(getResources().getString(R.string.feedback_history));
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        feedback_tablayout = findViewById(R.id.feedback_tablayout);
        feedback_viewpager = findViewById(R.id.feedback_viewpager);
        imageView_back.setOnClickListener(this);
        tabTitleArray = getResources().getStringArray(R.array.feedback_menu);
        for (int i = 0; i < tabTitleArray.length; i++) {
            feedback_tablayout.addTab(feedback_tablayout.newTab().setText(tabTitleArray[i]));
        }
        LinearLayout linearLayout = (LinearLayout) feedback_tablayout.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.tablayout_line));
//        linearLayout.setDividerPadding(30);
        FeedBackFragment feedBackFragment = new FeedBackFragment();
        fragmentList.add(feedBackFragment);
        couponsFragment = new CouponsFragment();
        fragmentList.add(couponsFragment);


        feedback_tablayout.setTabMode(TabLayout.MODE_FIXED);
        feedback_tablayout.setSelectedTabIndicatorHeight(4);
        feedback_tablayout.setSelectedTabIndicatorColor(Color.parseColor("#27a2f0"));
        feedback_tablayout.setTabTextColors(Color.parseColor("#333b46"), Color.parseColor("#27a2f0"));
        feedback_tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(), this, fragmentList, tabTitleArray);
        feedback_viewpager.setAdapter(adapter);
        feedback_viewpager.setOffscreenPageLimit(fragmentList.size());
        feedback_tablayout.setupWithViewPager(feedback_viewpager);
        tv_title.setText(getResources().getString(R.string.adveice_feedback));
        ThemeStyleHelper.rightTexteFrameLayout(getApplicationContext(), czy_title_layout, imageView_back, tv_title, user_top_view_right);
        FeedbackModel feedbackModel = new FeedbackModel(FeedBackActivity.this);
        feedbackModel.getFeedBackUrl(0, this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                Intent intent = new Intent(this, FeedBackListActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TCAgent.onPageStart(getApplicationContext(), "意见反馈");
    }


    @Override
    protected void onPause() {
        super.onPause();
        TCAgent.onPageEnd(getApplicationContext(), "意见反馈");
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    FeedBackRedirectEntity feedBackRedirectEntity = GsonUtils.gsonToBean(result, FeedBackRedirectEntity.class);
                    couponsFragment.WEBURL = feedBackRedirectEntity.getContent().getUri();
                } catch (Exception e) {
                    couponsFragment.WEBURL = "https://service-czy.colourlife.com/redirect";
                }
                couponsFragment.prepareData();
                break;
        }
    }
}
