package com.cardcoupons.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseFragmentActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.cardcoupons.adapter.FragmentAdapter;
import com.cardcoupons.fragment.CouponsFragment;
import com.cardcoupons.model.CouponsModel;
import com.cardcoupons.protocol.coupons.CouponGettApi;
import com.tendcloud.tenddata.TCAgent;

import java.util.ArrayList;

import cn.net.cyberway.R;

/**
 *
 * Created by junier_li on 2016/1/5.
 */
public class CardCouponsActivity extends BaseFragmentActivity implements View.OnClickListener,
        ViewPager.OnPageChangeListener, HttpApiResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;
    private ImageView iv_back;

    // 四个textview
    private TextView tab1Tv, tab2Tv;

    private CouponsFragment couponsFragment;
    private CouponsFragment billOfLadingFragment;
    // 指示器
    private ImageView cursorImg;
    // viewpager
    private ViewPager viewPager;
    // fragment对象集合
    private ArrayList<Fragment> fragmentsList;
    // 记录当前选中的tab的index
    private int currentIndex = 0;
    // 指示器的偏移量
    private int offset = 0;
    // 左margin
    private int leftMargin = 0;
    // 屏幕宽度
    private int screenWidth = 0;
    // 屏幕宽度的四分之一
    private int screen1_4;
    //
    private LinearLayout.LayoutParams lp;

    private CouponsModel couponsModel;

    private int num;
    private int size;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);

        setContentView(R.layout.cardcoupons_activity);
        initDate();
        init();

        prepareData();

    }

    private void initDate() {
        num = getIntent().getIntExtra("num", 0);
    }

    /**
     * 初始化操作
     */
    private void init() {

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        screen1_4 = screenWidth / 2;

        cursorImg = (ImageView) findViewById(R.id.cursor);
        lp = (LinearLayout.LayoutParams) cursorImg.getLayoutParams();
        leftMargin = lp.leftMargin;
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        iv_back = (ImageView) findViewById(R.id.user_top_view_back);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.my_coupon));
        tab1Tv = (TextView) findViewById(R.id.tab1_tv);
        tab2Tv = (TextView) findViewById(R.id.tab2_tv);
        tab1Tv.setSelected(true);
        //
        iv_back.setOnClickListener(this);
        tab1Tv.setOnClickListener(this);
        tab2Tv.setOnClickListener(this);
        initViewPager();
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, iv_back, tv_title);
    }

    private void prepareData() {
        couponsModel = new CouponsModel(this);
        couponsModel.getCountAvailable(this);
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.cardcoupons_viewpager);
        fragmentsList = new ArrayList<Fragment>();
        couponsFragment = new CouponsFragment();
        fragmentsList.add(couponsFragment);
        billOfLadingFragment = new CouponsFragment();
        fragmentsList.add(billOfLadingFragment);
        viewPager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),
                fragmentsList));
        size = fragmentsList.size();
        viewPager.addOnPageChangeListener(this);
        if (num != 0) {
            viewPager.setCurrentItem(num);
            tab1Tv.setSelected(false);
            tab2Tv.setSelected(false);
        } else {
            viewPager.setCurrentItem(0);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tab1_tv:
                viewPager.setCurrentItem(0);
                tab1Tv.setSelected(true);
                tab2Tv.setSelected(false);
                break;
            case R.id.tab2_tv:
                viewPager.setCurrentItem(1);
                tab1Tv.setSelected(false);
                tab2Tv.setSelected(true);
                break;
            case R.id.user_top_view_back:
                if (tab1Tv.isSelected()) {
                    couponsFragment.goBack();
                } else if (tab2Tv.isSelected()) {
                    billOfLadingFragment.goBack();
                } else {
                    finish();
                }
                break;

        }
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset,
                               int positionOffsetPixels) {
        offset = (screen1_4 - cursorImg.getLayoutParams().width) / 2;
        if (position == 0) {// 0<->1
            lp.leftMargin = positionOffsetPixels / 2 + offset;
            tab1Tv.setSelected(true);
            tab2Tv.setSelected(false);
        } else if (position == 1) {// 1<->2
            lp.leftMargin = positionOffsetPixels / 2 + screen1_4 + offset;
            tab1Tv.setSelected(false);
            tab2Tv.setSelected(true);
        }
        cursorImg.setLayoutParams(lp);
        currentIndex = position;
    }

    @Override
    public void onPageSelected(int arg0) {
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(CouponGettApi.class)) {


        }
    }


    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }

    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
    }


    // 退出操作
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (tab1Tv.isSelected()) {
                couponsFragment.goBack();
            } else if (tab2Tv.isSelected()) {
                billOfLadingFragment.goBack();
            }
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        TCAgent.onPageEnd(getApplicationContext(), "我的卡券");
    }

    @Override
    protected void onResume() {
        super.onResume();
        TCAgent.onPageStart(getApplicationContext(), "我的卡券");
    }
}
