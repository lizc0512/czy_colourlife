package com.invite.activity;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.protocol.BeanDetailListEntity;
import com.invite.adapter.InviteDetailAdapter;
import com.invite.protocol.InviteDetailListEntity;
import com.nohttp.utils.GsonUtils;
import com.user.model.NewUserModel;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * 活动规则，明细
 * Created by hxg on 19/5/10.
 */
public class InviteActivityActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private TextView tvTitle;
    private ImageView imgBack;
    private LinearLayout ll_activity;
    private RelativeLayout rl_null;

    private SwipeMenuRecyclerView rv_detail;
    private InviteDetailAdapter mAdapter;
    private List<InviteDetailListEntity.ContentBean.DataBean> detailList = new ArrayList<>();
    private int page = 1;
    private NewUserModel newUserModel;

    public static final String ENTER_TYPE = "enter_type";
    public String type = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_activity);
        getWindow().setBackgroundDrawable(null);
        initView();
        initData();
    }

    private void initView() {
        tvTitle = findViewById(R.id.user_top_view_title);
        imgBack = findViewById(R.id.user_top_view_back);
        ll_activity = findViewById(R.id.ll_activity);
        rv_detail = findViewById(R.id.rv_detail);
        rl_null = findViewById(R.id.rl_null);
        imgBack.setOnClickListener(this);
    }

    private void initData() {
        newUserModel = new NewUserModel(this);

        type = getIntent().getStringExtra(ENTER_TYPE);
        if ("act".equals(type)) {
            tvTitle.setText(getString(R.string.invite_activity));
            ll_activity.setVisibility(View.VISIBLE);
            rv_detail.setVisibility(View.GONE);
            rl_null.setVisibility(View.GONE);
        } else if ("detail".equals(type)) {
            tvTitle.setText(getString(R.string.invite_detail));
            rv_detail.setVisibility(View.VISIBLE);
            ll_activity.setVisibility(View.GONE);
            rl_null.setVisibility(View.GONE);
            rv_detail.setLayoutManager(new LinearLayoutManager(this));
            rv_detail.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            mAdapter = new InviteDetailAdapter(this, detailList);
            rv_detail.setAdapter(mAdapter);
            rv_detail.useDefaultLoadMore();
            rv_detail.setLoadMoreListener(() -> {
                page++;
                getDetailList(false);
            });
            getDetailList(true);
        }
    }

    /**
     * 获取详情
     */
    private void getDetailList(boolean isLoading) {
//        rl_null.setVisibility(View.VISIBLE); //空


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        InviteDetailListEntity inviteDetailListEntity = GsonUtils.gsonToBean(result, InviteDetailListEntity.class);
                        InviteDetailListEntity.ContentBean contentBean = inviteDetailListEntity.getContent();
                        if (page == 1) {
                            detailList.clear();
                        }
                        List<InviteDetailListEntity.ContentBean.DataBean> list = contentBean.getData();
                        detailList.addAll(list);
                        boolean dataEmpty = list.size() == 0;
                        int totalRecord = contentBean.getTotal();
                        boolean hasMore = totalRecord > detailList.size();
                        rv_detail.loadMoreFinish(dataEmpty, hasMore);
                        mAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
