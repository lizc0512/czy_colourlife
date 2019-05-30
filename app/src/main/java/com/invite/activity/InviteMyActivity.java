package com.invite.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.invite.adapter.InviteProfitAdapter;
import com.invite.protocol.InviteDetailListEntity;
import com.user.model.NewUserModel;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * 我的邀请
 * Created by hxg on 19/5/16.
 */
public class InviteMyActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView imgBack;
    private TextView tvTitle;

    private TextView tv_invite, tv_direct, tv_indirect;

    private SwipeMenuRecyclerView rv_list;
    private InviteProfitAdapter mAdapter;
    private List<InviteDetailListEntity.ContentBean.DataBean> detailList = new ArrayList<>();
    private int page = 1;
    private int type = 1;//1 已发放、2待发放、3已失效

    private NewUserModel newUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_my);
        getWindow().setBackgroundDrawable(null);
        initView();
        initData();
    }

    private void initView() {
        imgBack = findViewById(R.id.user_top_view_back);
        tvTitle = findViewById(R.id.user_top_view_title);

        tv_invite = findViewById(R.id.tv_invite);
        tv_direct = findViewById(R.id.tv_direct);
        tv_indirect = findViewById(R.id.tv_indirect);

        rv_list = findViewById(R.id.rv_list);

        imgBack.setOnClickListener(this);
        tv_direct.setOnClickListener(this);
        tv_indirect.setOnClickListener(this);

        rv_list.setLayoutManager(new LinearLayoutManager(this));
        rv_list.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        mAdapter = new InviteProfitAdapter(this, detailList);
        rv_list.setAdapter(mAdapter);
        rv_list.useDefaultLoadMore();
        rv_list.setLoadMoreListener(() -> {
            page++;
            getList();
        });
        getList();
    }

    private void initData() {
        type = 1;
        tvTitle.setText("我的邀请");
        newUserModel = new NewUserModel(this);
    }

    /**
     * 获取列表
     * 1 直接、2间接
     */
    private void getList() {
        if (1 == type) {

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_direct:
                page = 1;
                type = 1;
                setBg();
                getList();
                break;
            case R.id.tv_indirect:
                page = 1;
                type = 2;
                setBg();
                getList();
                break;
        }
    }

    private void setBg() {
        switch (type) {
            case 1:
                tv_direct.setTextColor(getResources().getColor(R.color.color_ff6a19));
                tv_direct.setBackgroundResource(R.drawable.text_invite_my_select);
                tv_indirect.setTextColor(getResources().getColor(R.color.black_text_color));
                tv_indirect.setBackgroundColor(Color.WHITE);
                break;
            case 2:
                tv_direct.setTextColor(getResources().getColor(R.color.black_text_color));
                tv_direct.setBackgroundColor(Color.WHITE);
                tv_indirect.setTextColor(getResources().getColor(R.color.color_ff6a19));
                tv_indirect.setBackgroundResource(R.drawable.text_invite_my_select);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:

                break;
        }
    }
}
