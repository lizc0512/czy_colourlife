package com.Neighborhood.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.feed.adapter.ActivityTypeAdapter;
import com.feed.model.ActivityModel;
import com.feed.protocol.ACTIVITY_CATEGORY;
import com.feed.protocol.VerFeedActivitycategoryApi;
import com.user.UserAppConst;

import cn.net.cyberway.R;

/**
 * Created by HX_CHEN on 2016/4/18.
 */
public class ActiveListActivity extends BaseActivity implements HttpApiResponse {
    private XListView listView;
    private ActivityTypeAdapter mAdapter;
    private ActivityModel mActivityModel;
    private ImageView imgBack;
    private FrameLayout czy_title_layout;
    private TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activelist_layout);
        initView();
        initData();
    }
    private void initView() {
        listView = (XListView) findViewById(R.id.xlist_view);
        imgBack = (ImageView) findViewById(R.id.user_top_view_back);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tvTitle = (TextView) findViewById(R.id.user_top_view_title);
        tvTitle.setText(getResources().getString(R.string.title_activity_type));
        mActivityModel = new ActivityModel(this);
        mActivityModel.getCategory(this, shared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da"));
    }
    private void initData() {
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                ACTIVITY_CATEGORY activity_category = (ACTIVITY_CATEGORY) parent.getAdapter().getItem(position);
                intent.putExtra("data",activity_category);
                setResult(6,intent);
                finish();
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            }
        });
        listView.setXListViewListener(new IXListViewListener() {
            @Override
            public void onRefresh(int id) {
                mActivityModel.getCategory(ActiveListActivity.this, shared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da"));
            }

            @Override
            public void onLoadMore(int id) {
                mActivityModel.getCategory(ActiveListActivity.this, shared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da"));
            }
        }, 0);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(),czy_title_layout, imgBack,tvTitle);
    }



    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(VerFeedActivitycategoryApi.class)) {
            listView.stopLoadMore();
            listView.stopRefresh();
            listView.setPullLoadEnable(mActivityModel.isMore);
            if (mAdapter == null) {
                mAdapter = new ActivityTypeAdapter(ActiveListActivity.this, mActivityModel.activity_categories);
                listView.setAdapter(mAdapter);
            } else {
                mAdapter.mList = mActivityModel.activity_categories;
                mAdapter.notifyDataSetChanged();
            }
        }

    }
}
