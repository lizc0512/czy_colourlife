package com.community.activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.CircleImageView;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.cashier.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/7 10:35
 * @change
 * @chang time
 * @class describe   动态详情
 */

public class DynamicsDetailsActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private AppBarLayout dynamics_appbar;
    private TabLayout dynamics_tabs;
    private ViewPager dynamics_viewpager;

    private CircleImageView iv_dynamics_user_pics;
    private TextView tv_dynamics_user_name;
    private TextView tv_dynamics_user_community;
    private ImageView iv_dynamics_user_operate;
    private TextView tv_dynamics_text_content;
    private RecyclerView rv_dynamics_images;
    private TextView tv_dynamics_publish_time;
    private TextView tv_dynamics_comment;
    private TextView tv_dynamics_like;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamics_details);
        initView();
    }

    private void initView() {
        user_top_view_back=   findViewById(R.id.user_top_view_back);
        user_top_view_title=findViewById(R.id.user_top_view_title);
        dynamics_appbar= findViewById(R.id.dynamics_appbar);
        dynamics_tabs= findViewById(R.id.dynamics_tabs);
        dynamics_viewpager=findViewById(R.id.dynamics_viewpager);
        iv_dynamics_user_pics= findViewById(R.id.iv_dynamics_user_pics);
        tv_dynamics_user_name=findViewById(R.id.tv_dynamics_user_name);
        tv_dynamics_user_community=  findViewById(R.id.tv_dynamics_user_community);
        iv_dynamics_user_operate=findViewById(R.id.iv_dynamics_user_operate);
        rv_dynamics_images=  findViewById(R.id.rv_dynamics_images);
        tv_dynamics_publish_time=findViewById(R.id.tv_dynamics_publish_time);
        tv_dynamics_comment=findViewById(R.id.tv_dynamics_comment);
        tv_dynamics_like=  findViewById(R.id.tv_dynamics_like);
        user_top_view_back.setOnClickListener(this::onClick);
        dynamics_appbar.setOnClickListener(this::onClick);
        iv_dynamics_user_pics.setOnClickListener(this::onClick);
        iv_dynamics_user_operate.setOnClickListener(this::onClick);
        tv_dynamics_comment.setOnClickListener(this::onClick);
        tv_dynamics_like.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {


    }

    @Override
    public void OnHttpResponse(int what, String result) {

    }
}
