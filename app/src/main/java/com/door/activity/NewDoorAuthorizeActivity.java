package com.door.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseFragmentActivity;
import com.cashier.adapter.ViewPagerAdapter;
import com.door.fragment.AuthorizeRecordFragment;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

public class NewDoorAuthorizeActivity extends BaseFragmentActivity implements View.OnClickListener {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private AppBarLayout appbar;
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
        record_tabs = findViewById(R.id.record_tabs);
        record_viewpager = findViewById(R.id.record_viewpager);
        user_top_view_back.setOnClickListener(this);
        user_top_view_title.setText("授权");
        tabTitleArray = getResources().getStringArray(R.array.feedback_menu);
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
        }
    }
}
