package com.door.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseFragmentActivity;
import com.BeeFramework.view.ClearEditText;
import com.cashier.adapter.ViewPagerAdapter;
import com.door.fragment.AuthorizeRecordFragment;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
/*
 * 新的授权和授权记录
 *
 * */

public class NewDoorAuthorizeActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private AppBarLayout appbar;
    private LinearLayout choice_room_layout;
    private TextView tv_room_name;
    private RecyclerView rv_authorize_time;
    private ClearEditText ed_real_name;
    private ClearEditText ed_authorize_phone;
    private LinearLayout choice_identify_layout;
    private Button btn_define_authorize;
    private TabLayout record_tabs;
    private ViewPager record_viewpager;
    private String[] tabTitleArray = null;
    private List<Fragment> fragmentList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_authorization_list);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        appbar = findViewById(R.id.appbar);
        choice_room_layout = findViewById(R.id.choice_room_layout);
        record_tabs = findViewById(R.id.record_tabs);
        tv_room_name = findViewById(R.id.tv_room_name);
        rv_authorize_time = findViewById(R.id.rv_authorize_time);
        ed_real_name = findViewById(R.id.ed_real_name);
        ed_authorize_phone = findViewById(R.id.ed_authorize_phone);
        choice_identify_layout = findViewById(R.id.choice_identify_layout);
        btn_define_authorize = findViewById(R.id.btn_define_authorize);
        record_viewpager = findViewById(R.id.record_viewpager);
        user_top_view_back.setOnClickListener(this);
        choice_room_layout.setOnClickListener(this);
        choice_identify_layout.setOnClickListener(this);
        btn_define_authorize.setOnClickListener(this);
        user_top_view_title.setText("授权");
        tabTitleArray = getResources().getStringArray(R.array.door_authorize_menu);
        for (int i = 0; i < tabTitleArray.length; i++) {
            record_tabs.addTab(record_tabs.newTab().setText(tabTitleArray[i]));
        }
        AuthorizeRecordFragment feedBackFragment = new AuthorizeRecordFragment();
        fragmentList.add(feedBackFragment);
        AuthorizeRecordFragment couponsFragment = new AuthorizeRecordFragment();
        fragmentList.add(couponsFragment);
        record_tabs.setTabMode(TabLayout.MODE_FIXED);
        record_tabs.setSelectedTabIndicatorHeight(4);
        record_tabs.setSelectedTabIndicatorColor(Color.parseColor("#3385FF"));
        record_tabs.setTabTextColors(Color.parseColor("#25282E"), Color.parseColor("#3385FF"));
        record_tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(), this, fragmentList, tabTitleArray);
        record_viewpager.setAdapter(adapter);
        record_viewpager.setOffscreenPageLimit(fragmentList.size());
        record_tabs.setupWithViewPager(record_viewpager);
        appbar.post(new Runnable() {
            @Override
            public void run() {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) appbar.getLayoutParams();
                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
                behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                    @Override
                    public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                        return true;
                    }
                });
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.choice_identify_layout:

                break;
            case R.id.btn_define_authorize:

                break;
            case R.id.choice_room_layout:

                break;

        }
    }
}
