package com.message.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseFragmentActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.cardcoupons.adapter.FragmentAdapter;
import com.gem.GemConstant;
import com.gem.util.GemDialogUtil;
import com.message.adapter.MessageAdapter;
import com.message.fragment.MessageFragment;
import com.message.model.MessageModel;
import com.message.protocol.PushmessageSetreadGetApi;
import com.message.protocol.PushmessageSetreadGetResponse;
import com.message.protocol.WetownMessagecentersetallreadGetApi;
import com.message.protocol.WetownMessagecentersetallreadGetResponse;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

import cn.net.cyberway.R;

/**
 * 消息中心，包括系统通知、商家消息
 * Created by chenql on 16/1/13.
 */
public class MessageCenterActivity extends BaseFragmentActivity implements HttpApiResponse, View.OnClickListener, ViewPager.OnPageChangeListener {
    // 系统通知、商家消息fragment
    private MessageFragment systemMessageFragment, shopMessageFragment;
    // 指示器
    private ImageView img_cursor0, img_cursor1;
    // tab textView
    private TextView tv_tab1, tv_tab2;
    // viewPager
    private ViewPager message_viewpager;
    private TextView user_top_view_right; // 全部标记已读
    private int currentPager = 0; // 当前pager
    // 接受到通知，用户点击通知栏，是否应从main进入系统通知

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_center);
        initTopView();
        initView();
    }

    private void initView() {
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        img_cursor0 = (ImageView) findViewById(R.id.img_cursor0);
        img_cursor1 = (ImageView) findViewById(R.id.img_cursor1);
        tv_tab1 = (TextView) findViewById(R.id.tv_tab1);
        tv_tab2 = (TextView) findViewById(R.id.tv_tab2);
        tv_tab1.setSelected(true);
        tv_tab1.setOnClickListener(this);
        tv_tab2.setOnClickListener(this);
        initViewPager();
        ImageView ivGem = (ImageView) findViewById(R.id.iv_gem);
        GemDialogUtil.showGemDialog(ivGem, this, GemConstant.noticeIndex, "");
    }

    /**
     * 初始化viewpager
     */
    private void initViewPager() {
        message_viewpager = (ViewPager) findViewById(R.id.message_viewpager);
        // fragment对象集合
        ArrayList<Fragment> fragmentsList = new ArrayList<>();

        // 系统通知fragment
        systemMessageFragment = new MessageFragment();
        Bundle bundle = new Bundle();
        bundle.putString("msgType", MessageAdapter.SYSTEM_MSG);
        systemMessageFragment.setArguments(bundle);
        fragmentsList.add(systemMessageFragment);

        // 商家消息fragment
        shopMessageFragment = new MessageFragment();
        bundle = new Bundle();
        bundle.putString("msgType", MessageAdapter.SHOP_MSG);
        shopMessageFragment.setArguments(bundle);
        fragmentsList.add(shopMessageFragment);

        message_viewpager.setAdapter(new FragmentAdapter(getSupportFragmentManager(),
                fragmentsList));
        message_viewpager.setCurrentItem(0);
        message_viewpager.setOnPageChangeListener(this);
    }

    private void initTopView() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        TextView tvTitle = (TextView) findViewById(R.id.user_top_view_title);
        tvTitle.setText(getString(R.string.notice));

        ImageView imgBack = (ImageView) findViewById(R.id.user_top_view_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        user_top_view_right.setText(getResources().getString(R.string.message_all_read));
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setOnClickListener(this);

        ThemeStyleHelper.rightTexteFrameLayout(getApplicationContext(), czy_title_layout, imgBack, tvTitle, user_top_view_right);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_tab1:
                // 系统通知
                message_viewpager.setCurrentItem(0);
                currentPager = 0;
                tv_tab1.setSelected(true);
                tv_tab2.setSelected(false);
                break;

            case R.id.tv_tab2:
                // 商家消息
                message_viewpager.setCurrentItem(1);
                currentPager = 1;
                tv_tab1.setSelected(false);
                tv_tab2.setSelected(true);
                break;

            case R.id.user_top_view_right:
                // 标记已读
                readAll();
                break;

            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    private void readAll() {
        MessageModel messageModel = new MessageModel(MessageCenterActivity.this);
        switch (currentPager) {
            case 0:
                // 设置系统通知全部已读
                messageModel.readAllSystemMsg(MessageCenterActivity.this);
                break;

            case 1:
                // 设置商家消息全部已读
                messageModel.readAllShopMsg(MessageCenterActivity.this);
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (position == 0) {// 0<->1
            img_cursor0.setVisibility(View.VISIBLE);
            img_cursor1.setVisibility(View.INVISIBLE);
            currentPager = 0;
            tv_tab1.setSelected(true);
            tv_tab2.setSelected(false);

        } else if (position == 1) {// 1<->2
            img_cursor0.setVisibility(View.INVISIBLE);
            img_cursor1.setVisibility(View.VISIBLE);
            currentPager = 1;
            tv_tab1.setSelected(false);
            tv_tab2.setSelected(true);
        }
    }

    @Override
    public void onPageSelected(int i) {

    }

    @Override
    public void onPageScrollStateChanged(int i) {

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

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.push_left_in, R.anim.push_left_out);
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(PushmessageSetreadGetApi.class)) {
            PushmessageSetreadGetResponse response = ((PushmessageSetreadGetApi) api).response;

            if (response.status == 1) {
                systemMessageFragment.setAllRead(MessageAdapter.SYSTEM_MSG);
                ToastUtil.toastShow(MessageCenterActivity.this, getResources().getString(R.string.message_mark_success));
            } else {
                systemMessageFragment.setAllRead(MessageAdapter.SYSTEM_MSG);
                ToastUtil.toastShow(MessageCenterActivity.this, getResources().getString(R.string.message_all_read));
            }
        } else if (api.getClass().equals(WetownMessagecentersetallreadGetApi.class)) {
            WetownMessagecentersetallreadGetResponse response = ((WetownMessagecentersetallreadGetApi) api).response;
            if ("0".equals(response.result)) {
                systemMessageFragment.setAllRead(MessageAdapter.SHOP_MSG);
                ToastUtil.toastShow(MessageCenterActivity.this, getResources().getString(R.string.message_mark_success));
            } else {
                ToastUtil.toastShow(MessageCenterActivity.this, response.reason);
            }
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);       //统计时长
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
