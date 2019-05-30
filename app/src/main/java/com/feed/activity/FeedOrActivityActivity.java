package com.feed.activity;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;

import cn.net.cyberway.R;


public class FeedOrActivityActivity extends BaseActivity implements View.OnClickListener {
    public static final String CREATENORMALFEED = "CREATENORMALFEED";
    private LinearLayout mFeedLayout;
    private LinearLayout mActivityLayout;
    private ImageView mClose;
    private boolean isFeed;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_or_activity);
        layout = (RelativeLayout) findViewById(R.id.layout);
        mFeedLayout = (LinearLayout) findViewById(R.id.feed_ac_feed);
        mActivityLayout = (LinearLayout) findViewById(R.id.feed_ac_activity);
        mClose = (ImageView) findViewById(R.id.feed_ac_close);
        mFeedLayout.setOnClickListener(this);
        mActivityLayout.setOnClickListener(this);
        mClose.setOnClickListener(this);
        startAnimate();
        isFeed = getIntent().getBooleanExtra(CREATENORMALFEED, false);
        if (isFeed) {
            finish();
        }
    }

    /**
     * 开始动画
     */
    private void startAnimate() {
        layout.animate().alpha(0.65f).setDuration(getAnamationTime(6)).start();
        mClose.animate().alpha(1f).setDuration(getAnamationTime(5)).setStartDelay(getAnamationTime(8)).setStartDelay(getAnamationTime(8)).start();
        float translationY = (float) (Utils.dip2px(this, 140) + BeeFrameworkApp.getDeviceHeight(this) * 0.3);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            mActivityLayout.animate().alpha(1f).translationY(translationY).setDuration(getAnamationTime(6)).start();
            mFeedLayout.animate().alpha(1f).translationY(translationY).setDuration(getAnamationTime(6)).start();
        } else {
            mFeedLayout.animate().alpha(1f).translationY(translationY).setDuration(getAnamationTime(6)).setStartDelay(getAnamationTime(3)).setInterpolator(new AccelerateInterpolator()).start();
            mActivityLayout.animate().alpha(1f).translationY(translationY).setDuration(getAnamationTime(6)).setStartDelay(getAnamationTime(5)).setInterpolator(new AccelerateInterpolator()).start();
        }
    }

    @Override
    public void onClick(View v) {
        Intent intent = null;
        switch (v.getId()) {
            case R.id.feed_ac_close:
                finishAnimate();
                break;
            case R.id.feed_ac_feed:
                intent = new Intent(this, CreateNormalFeedActivity.class);
                startActivity(intent);
                finishAnimate();
                break;
            case R.id.feed_ac_activity:
                intent = new Intent(this, ReleaseActivity.class);
                startActivity(intent);
                finishAnimate();
                break;
        }
    }


    private long getAnamationTime(int time) {
        return 33 * time;

    }

    @Override
    public void finish() {
        super.finish();
    }

    private void finishAnimate() {
        float translationY = (float) (Utils.dip2px(this, 140) + BeeFrameworkApp.getDeviceHeight(this) * 0.7);
        mFeedLayout.animate().setInterpolator(new AccelerateInterpolator()).translationYBy(translationY).setDuration(getAnamationTime(6)).start();
        mActivityLayout.animate().setInterpolator(new AccelerateInterpolator()).translationYBy(translationY).setDuration(getAnamationTime(6)).setStartDelay(getAnamationTime(2)).start();
        layout.animate().alpha(0f).setDuration(getAnamationTime(4)).setInterpolator(new AccelerateInterpolator()).setStartDelay(getAnamationTime(6)).setListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                finish();
                overridePendingTransition(0, 0);

            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        }).start();
        mClose.animate().alpha(0f).setInterpolator(new AccelerateInterpolator()).setDuration(getAnamationTime(4)).start();

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finishAnimate();
            return true;
        }
        return super.onKeyDown(keyCode, event);

    }
}
