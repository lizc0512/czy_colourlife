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
 * 累计收益
 * Created by hxg on 19/5/16.
 */
public class InviteProfitActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView imgBack;
    private TextView tvTitle;

    private TextView tv_already;
    private TextView tv_todo;
    private TextView tv_invalid;

    private SwipeMenuRecyclerView rv_list;
    private InviteProfitAdapter mAdapter;
    private List<InviteDetailListEntity.ContentBean.DataBean> detailList = new ArrayList<>();
    private int page = 1;
    private int type = 1;//1 已发放、2待发放、3已失效

    private NewUserModel newUserModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_profit);
        getWindow().setBackgroundDrawable(null);
        initView();
        initData();
    }

    private void initView() {
        imgBack = findViewById(R.id.user_top_view_back);
        tvTitle = findViewById(R.id.user_top_view_title);
        tv_already = findViewById(R.id.tv_already);
        tv_todo = findViewById(R.id.tv_todo);
        tv_invalid = findViewById(R.id.tv_invalid);
        rv_list = findViewById(R.id.rv_list);

        imgBack.setOnClickListener(this);
        tv_already.setOnClickListener(this);
        tv_todo.setOnClickListener(this);
        tv_invalid.setOnClickListener(this);

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
        tvTitle.setText("累计收益");
        newUserModel = new NewUserModel(this);
    }

    /**
     * 获取已发放列表
     * 1 已发放、2待发放、3已失效
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
            case R.id.tv_already:
                page = 1;
                type = 1;
                setBg();
                getList();
                break;
            case R.id.tv_todo:
                page = 1;
                type = 2;
                setBg();
                getList();
                break;
            case R.id.tv_invalid:
                page = 1;
                type = 3;
                setBg();
                getList();
                break;
        }
    }

    private void setBg() {
        switch (type) {
            case 1:
                tv_already.setTextColor(getResources().getColor(R.color.color_ff6a19));
                tv_already.setBackgroundResource(R.drawable.text_invite_select);
                tv_todo.setTextColor(getResources().getColor(R.color.black_text_color));
                tv_todo.setBackgroundColor(Color.WHITE);
                tv_invalid.setTextColor(getResources().getColor(R.color.black_text_color));
                tv_invalid.setBackgroundColor(Color.WHITE);
                break;
            case 2:
                tv_already.setTextColor(getResources().getColor(R.color.black_text_color));
                tv_already.setBackgroundColor(Color.WHITE);
                tv_todo.setTextColor(getResources().getColor(R.color.color_ff6a19));
                tv_todo.setBackgroundResource(R.drawable.text_invite_select);
                tv_invalid.setTextColor(getResources().getColor(R.color.black_text_color));
                tv_invalid.setBackgroundColor(Color.WHITE);
                break;
            case 3:
                tv_already.setTextColor(getResources().getColor(R.color.black_text_color));
                tv_already.setBackgroundColor(Color.WHITE);
                tv_todo.setTextColor(getResources().getColor(R.color.black_text_color));
                tv_todo.setBackgroundColor(Color.WHITE);
                tv_invalid.setTextColor(getResources().getColor(R.color.color_ff6a19));
                tv_invalid.setBackgroundResource(R.drawable.text_invite_select);
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
