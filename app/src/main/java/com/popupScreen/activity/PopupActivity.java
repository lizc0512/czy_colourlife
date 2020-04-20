package com.popupScreen.activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.BeeFramework.activity.BaseActivity;
import com.external.viewpagerindicator.CirclePageIndicator;
import com.popupScreen.adapter.ImagePagerAdapter;
import com.user.UserAppConst;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 作者: zhudewei
 * 时间： 2016/12/18 09:16
 * God bless the program never bug
 */

public class PopupActivity extends BaseActivity {

    private ViewPager viewpager;
    private ImageView btn1;
    private LinearLayout llRoot;
    private FrameLayout fl_indicator;
    private CirclePageIndicator indicator;


    private List<String> imageList;
    private List<String> urlList;
    private List<String> nameList;
    private boolean isShowpop = false;
    private SharedPreferences mShared;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popup);
        mShared = getSharedPreferences(UserAppConst.USERINFO, 0);
        viewpager = findViewById(R.id.viewpager);
        btn1 = findViewById(R.id.btn1);
        llRoot = findViewById(R.id.ll_all_container);
        fl_indicator = findViewById(R.id.fl_indicator);
        indicator = findViewById(R.id.indicator);
        getIntentData();
        iniData();
        initListener();
    }

    private void getIntentData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        urlList = bundle.getStringArrayList("urldata");
        imageList = bundle.getStringArrayList("imagedata");
        nameList = bundle.getStringArrayList("namedata");

    }

    private void iniData() {
        if (null != urlList && urlList.size() > 1) {
            fl_indicator.setVisibility(View.VISIBLE);
        } else {
            fl_indicator.setVisibility(View.INVISIBLE);
        }

        ImagePagerAdapter adapter = new ImagePagerAdapter(this, imageList, urlList, nameList);
        viewpager.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        adapter.setOnImgClickListener((pos, url, name) -> {
            if (url != null) {
                if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                    LinkParseUtil.parse(PopupActivity.this, url, name);
                    PopupActivity.this.finish();
                } else {
                    LinkParseUtil.parse(PopupActivity.this, "", "");
                }
            }
        });
    }

    private void initListener() {
        btn1.setOnClickListener(v -> initExitAnim());

        indicator.setViewPager(viewpager, 0);
        indicator.requestLayout();
        viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                indicator.setCurrentItem(i);
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        llRoot.setOnTouchListener((v, event) -> {
            initExitAnim();
            return false;
        });
    }


    private void initExitAnim() {
        if (isShowpop) {
            finishThis();
        }
    }

    private void finishThis() {
        ObjectAnimator animator = ObjectAnimator
                .ofFloat(llRoot, "translationY", 0f, 2000f);
        animator.setInterpolator(new AnticipateOvershootInterpolator());
        animator.setDuration(300).start();
        animator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                finish();
                overridePendingTransition(0, -1);
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            initExitAnim();
            return false;
        } else {
            return super.onKeyDown(keyCode, event);
        }
    }
}
