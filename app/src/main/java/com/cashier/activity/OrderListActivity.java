package com.cashier.activity;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseFragmentActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.cashier.adapter.ViewPagerAdapter;
import com.cashier.modelnew.NewOrderPayModel;
import com.cashier.protocolchang.PayResultEntity;
import com.external.eventbus.EventBus;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;
import com.user.UserMessageConstant;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.csh.colourful.life.view.pickview.TimePickerView;
import cn.net.cyberway.R;

import static cn.net.cyberway.utils.TableLayoutUtils.reflex;

/**
 * @name ${yuansk}
 * @class name：com.cashier.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/22 10:15
 * @change
 * @chang time
 * @class describe  订单列表的界面
 */

public class OrderListActivity extends BaseFragmentActivity implements View.OnClickListener, NewHttpResponse {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView user_top_view_right;
    private android.support.design.widget.TabLayout orderlist_tablayout;
    private ViewPager orderlist_viewpager;
    private String[] tabTitleArray = null;
    private String[] statusArray = {"0", "1", "2", "3", "4"};
    private List<Fragment> fragmentList = new ArrayList<>();
    private int selectPos;//当前的fragment

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderlist);
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        orderlist_tablayout = (TabLayout) findViewById(R.id.orderlist_tablayout);
        orderlist_viewpager = (ViewPager) findViewById(R.id.orderlist_viewpager);
        tabTitleArray = getResources().getStringArray(R.array.orderlist_second_menu);
        user_top_view_title.setText(getResources().getString(R.string.order_list));
        long currentMills = System.currentTimeMillis();
        user_top_view_right.setText(TimeUtil.getYearAndMonth(currentMills));
        user_top_view_right.setTextColor(Color.parseColor("#27a2f0"));
        for (int i = 0; i < tabTitleArray.length; i++) {
            orderlist_tablayout.addTab(orderlist_tablayout.newTab().setText(tabTitleArray[i]));
            fragmentList.add(OrderListFragment.newInstance(statusArray[i], currentMills / 1000 + ""));
        }
        orderlist_tablayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        orderlist_tablayout.setSelectedTabIndicatorHeight(4);
        orderlist_tablayout.setSelectedTabIndicatorColor(Color.parseColor("#27a2f0"));
        orderlist_tablayout.setTabTextColors(Color.parseColor("#333b46"), Color.parseColor("#27a2f0"));
        orderlist_tablayout.setTabGravity(TabLayout.GRAVITY_CENTER);
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(), this, fragmentList, tabTitleArray);
        orderlist_viewpager.setAdapter(adapter);
        orderlist_viewpager.setOffscreenPageLimit(fragmentList.size());
        orderlist_tablayout.setupWithViewPager(orderlist_viewpager);
        reflex(orderlist_tablayout);
        initClick();
        if (!EventBus.getDefault().isregister(OrderListActivity.this)) {
            EventBus.getDefault().register(OrderListActivity.this);
        }
        ThemeStyleHelper.rightTexteFrameLayout(getApplicationContext(), czy_title_layout, user_top_view_back, user_top_view_title, user_top_view_right);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(OrderListActivity.this)) {
            EventBus.getDefault().unregister(this);
        }
    }


    public void onEvent(Object event) {
        final Message message = (Message) event;
        if (message.what == UserMessageConstant.SUREBTNCHECKET) {
            refreshFragmentData();
        }
    }

    /**
     * 筛选日期刷新
     ***/
    private void refreshFragmentData(long times) {
        for (int k = 0; k < fragmentList.size(); k++) {
            if (k == selectPos) {
                ((OrderListFragment) fragmentList.get(selectPos)).resetMonthRefreshData(times / 1000 + "");
            } else {
                ((OrderListFragment) fragmentList.get(k)).setNeedRefresh(times / 1000 + "", false);
            }
        }
    }


    /**
     * 取消订单或订单支付成功或失败后刷新
     ***/
    private void refreshFragmentData() {
        for (int k = 0; k < fragmentList.size(); k++) {
            if (k == selectPos) {
                ((OrderListFragment) fragmentList.get(selectPos)).cancelRefreshList();
            } else {
                ((OrderListFragment) fragmentList.get(k)).setNeedRefresh(false);
            }
        }
    }

    private void initClick() {
        user_top_view_back.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        orderlist_tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                selectPos = tab.getPosition();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private TimePickerView pvTime;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                Calendar selectedDate = Calendar.getInstance();
                pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
                    @Override
                    public void onTimeSelect(Date date, View v) {//选中事件回调
                        long choiceTime = date.getTime();
                        refreshFragmentData(choiceTime);
                        user_top_view_right.setText(TimeUtil.getYearAndMonth(choiceTime));
                    }
                })
                        .setType(new boolean[]{true, true, false, false, false, false})
                        .setCancelText("取消")//取消按钮文字
                        .setSubmitText("确定")//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleText("选择年月")//标题文字
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTitleColor(Color.parseColor("#333b46"))//标题文字颜色
                        .setSubmitColor(Color.parseColor("#27a2f0"))//确定按钮文字颜色
                        .setCancelColor(Color.parseColor("#27a2f0"))//取消按钮文字颜色
                        .setTitleBgColor(Color.parseColor("#f5f5f5"))//标题背景颜色 Night mode
                        .setBgColor(Color.parseColor("#ffffff"))//滚轮背景颜色 Night mode
                        .setDate(selectedDate)// 默认是系统时间*/
                        .setRange(selectedDate.get(Calendar.YEAR) - 10, selectedDate.get(Calendar.YEAR) + 10)//默认是1900-2100年
                        .setLabel("年", "月", "", "", "", "")
                        .build();
                pvTime.show();
                break;
        }
    }


    public void cancelOrder(String colourSn) {
        NewOrderPayModel newOrderPayModel = new NewOrderPayModel(this);
        newOrderPayModel.cancelSingleOrder(1, colourSn, this);
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                    String msg = baseContentEntity.getMessage();
                    if (baseContentEntity.getCode() == 0) {
                        PayResultEntity payResultEntity = GsonUtils.gsonToBean(result, PayResultEntity.class);
                        String content = payResultEntity.getContent();
                        if ("1".equals(content)) {
                            ToastUtil.toastShow(OrderListActivity.this, "订单取消成功");
                            refreshFragmentData();
                        } else {
                            ToastUtil.toastShow(OrderListActivity.this, "订单取消失败");
                        }
                    } else {
                        ToastUtil.toastShow(OrderListActivity.this, msg);
                    }
                }
                break;
        }
    }
}
