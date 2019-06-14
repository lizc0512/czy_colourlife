package com.invite.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseFragmentActivity;
import com.cashier.adapter.ViewPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.TableLayoutUtils;

/**
 * 我的邀请，累计收益
 * Created by hxg on 19/5/16
 */
public class InviteListActivity extends BaseFragmentActivity implements View.OnClickListener {

    public static final String FROM_PROFIT = "profit";
    public static final String SUM = "sum";

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_invite_explain, tv_invite_num;
    private TabLayout tl_invite;
    private ViewPager vp_invite;

    private boolean isProfit;
    private String sum;
    private List<Fragment> fragmentList = new ArrayList<>();
    private int[] inviteStatusArray = {1, 2};
    private int[] profitStatusArray = {1, 2, 3};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_list);
        getWindow().setBackgroundDrawable(null);
        initTheme();
        initView();
        initData();
    }

    private void initTheme() {
        Intent intent = getIntent();
        isProfit = intent.getBooleanExtra(FROM_PROFIT, false);//是否是累计收益
        sum = intent.getStringExtra(SUM);
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        tv_invite_explain = findViewById(R.id.tv_invite_explain);
        tv_invite_num = findViewById(R.id.tv_invite_num);
        tl_invite = findViewById(R.id.tl_invite);
        vp_invite = findViewById(R.id.vp_invite);

        String[] tabTitleArray = getResources().getStringArray(isProfit ? R.array.invite_profit : R.array.invite_invite);
        for (int i = 0; i < tabTitleArray.length; i++) {
            tl_invite.addTab(tl_invite.newTab().setText(tabTitleArray[i]));
            fragmentList.add(InviteListFragment.newInstance(isProfit, isProfit ? profitStatusArray[i] : inviteStatusArray[i]));
        }
        //画竖线
        LinearLayout linearLayout = (LinearLayout) tl_invite.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(this, R.drawable.tablayout_vertical_line));
        int dp16 = Utils.dip2px(this, 16);
        linearLayout.setDividerPadding(dp16);

        tl_invite.setTabMode(TabLayout.MODE_FIXED);
        tl_invite.setTabTextColors(getResources().getColor(R.color.black_text_color), getResources().getColor(R.color.color_ff6a19));
        tl_invite.setSelectedTabIndicatorColor(getResources().getColor(R.color.color_ff6a19));
        tl_invite.setSelectedTabIndicatorHeight(8);
        tl_invite.setTabGravity(TabLayout.GRAVITY_FILL);

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager(), this, fragmentList, tabTitleArray);
        vp_invite.setAdapter(adapter);
        vp_invite.setOffscreenPageLimit(fragmentList.size());
        tl_invite.setupWithViewPager(vp_invite);
        TableLayoutUtils.reflexSameText(tl_invite);

        user_top_view_back.setOnClickListener(this);
    }

    private void initData() {
        user_top_view_title.setText(isProfit ? "累计收益" : "我的邀请");
        tv_invite_explain.setText(isProfit ? "累计收益（元）" : "我的邀请（人）");
        tv_invite_num.setText(sum);
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
