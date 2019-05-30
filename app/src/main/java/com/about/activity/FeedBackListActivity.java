package com.about.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.about.adapter.FeedBackListAdapter;
import com.about.model.FeedbackModel;
import com.about.protocol.FeedBackListEntity;
import com.external.maxwin.view.XListView;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;


public class FeedBackListActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private FrameLayout czy_title_layout;
    private TextView tv_title;   //标题
    private ImageView imageView_back;//返回
    private FeedbackModel feedbackModel;
    private XListView listView;
    private FeedBackListAdapter adapter;
    private RelativeLayout rl_main;
    private LinearLayout ll_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedbacklist_layout);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        listView = (XListView) findViewById(R.id.listview);
        rl_main = findViewById(R.id.rl_main);
        ll_empty = findViewById(R.id.ll_empty);
        tv_title.setText(getResources().getString(R.string.feedback_history));
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, imageView_back, tv_title);
        imageView_back.setOnClickListener(this);
        feedbackModel = new FeedbackModel(this);
        feedbackModel.getFeedBackList(0, this);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //跳转到反馈详情页面
                FeedBackListEntity.ContentBean contentBean = feedbackList.get(position - 1);
                String linkUrl = contentBean.getRedirect_uri();
                if (TextUtils.isEmpty(linkUrl)) {
                    Intent intent = new Intent(FeedBackListActivity.this, FeedBackDetailsActivity.class);
                    intent.putExtra("model", contentBean);
                    startActivity(intent);
                } else {
                    LinkParseUtil.parse(FeedBackListActivity.this, linkUrl, "");
                }
                adapter.setBg(position - 1);
            }
        });
        listView.setPullRefreshEnable(false);
        listView.setPullLoadEnable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    private List<FeedBackListEntity.ContentBean> feedbackList = new ArrayList<>();

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (TextUtils.isEmpty(result)) {
                    rl_main.setVisibility(View.GONE);
                    ll_empty.setVisibility(View.VISIBLE);
                } else {
                    try {
                        FeedBackListEntity feedBackListEntity = GsonUtils.gsonToBean(result, FeedBackListEntity.class);
                        feedbackList.clear();
                        feedbackList.addAll(feedBackListEntity.getContent());
                        if (null == adapter) {
                            adapter = new FeedBackListAdapter(this, feedbackList);
                            listView.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        rl_main.setVisibility(View.GONE);
                        ll_empty.setVisibility(View.VISIBLE);
                    }
                }
                break;
        }
    }
}
