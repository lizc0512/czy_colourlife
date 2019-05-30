package com.notification.activity;

import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nohttp.utils.GsonUtils;
import com.notification.adapter.NotificationAllAdapter;
import com.notification.model.NotificationModel;
import com.notification.protocol.NotificationAllEntity;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * 2018/04/16
 * 此消息的更多详情页面
 * lizc
 */
public class NotificationAllInfoActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private TextView user_top_view_title;
    private ImageView user_top_view_back;
    private String app_id;
    private String title;
    private NotificationAllAdapter notificationAllAdapter;
    private XRecyclerView rv_notice_all_activity;
    private List<NotificationAllEntity.ContentBean> mlist = new ArrayList<>();
    private NotificationModel notificationModel;
    private LinearLayoutManager linearLayoutManager;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_all_info);
        initView();
        initData();
    }

    private void initData() {
        app_id = this.getIntent().getStringExtra("app_id");
        title = this.getIntent().getStringExtra("title");
        user_top_view_title.setText(title);
        rv_notice_all_activity.refresh();
    }

    private void initView() {
        rv_notice_all_activity = (XRecyclerView) findViewById(R.id.rv_notice_all_activity);
        linearLayoutManager = new LinearLayoutManager(this);
        rv_notice_all_activity.setLayoutManager(linearLayoutManager);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        user_top_view_back.setOnClickListener(this);
        notificationModel = new NotificationModel(this);
        rv_notice_all_activity.setItemAnimator(new DefaultItemAnimator());
        rv_notice_all_activity.setLoadingMoreEnabled(true);
        rv_notice_all_activity.setPullRefreshEnabled(true);
        rv_notice_all_activity.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                page = 1;
                initLoadMoreData(false);
            }

            @Override
            public void onLoadMore() {
                page++;
                if (mlist.size() >= totalSize) {
                    rv_notice_all_activity.setNoMore(true);//判断没有更多
                    rv_notice_all_activity.loadMoreComplete();
                } else {
                    initLoadMoreData(false);
                }
            }
        });
        notificationAllAdapter = new NotificationAllAdapter(NotificationAllInfoActivity.this, mlist);
        rv_notice_all_activity.setAdapter(notificationAllAdapter);
    }

    private void initLoadMoreData(boolean isLoading) {
        notificationModel.getMsgMoreList(0, app_id, page, isLoading, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                NotificationAllInfoActivity.this.finish();
                break;


        }
    }

    private int totalSize;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        NotificationAllEntity notificationAllEntity = GsonUtils.gsonToBean(result, NotificationAllEntity.class);
                        totalSize = notificationAllEntity.getTotal();
                        if (page == 1) {
                            mlist.clear();
                        }
                        mlist.addAll(notificationAllEntity.getContent());
                        if (null != notificationAllAdapter) {
                            notificationAllAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {

                    }
                }
                if (page == 1) {
                    rv_notice_all_activity.refreshComplete();
                } else {
                    rv_notice_all_activity.loadMoreComplete();
                }
                break;
        }
    }
}
