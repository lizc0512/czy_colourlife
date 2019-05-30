package com.feed.activity;

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


public class SelectTypeActivity extends BaseActivity implements IXListViewListener, AdapterView.OnItemClickListener, View.OnClickListener, HttpApiResponse {

    private XListView mListView;

    private ActivityTypeAdapter mAdapter;
    private ActivityModel mActivityModel;
    private ImageView mBack;
    private TextView mTitle;
    private TextView mTitle1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_type);

        mListView = (XListView) findViewById(R.id.selecttype_listview);
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mTitle1 = (TextView) findViewById(R.id.user_top_view_title);
        mTitle1.setText(getResources().getString(R.string.title_activity_type));
        mListView.setPullLoadEnable(true);
        mListView.setXListViewListener(this, 0);

        //给XListView设置条目点击事件
        mListView.setOnItemClickListener(this);
        mBack.setOnClickListener(this);
        mActivityModel = new ActivityModel(this);
        mActivityModel.getCategory(this, shared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da"));
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, mBack, mTitle1);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mActivityModel.activity_categories.size() - mListView.getHeaderViewsCount() <= mActivityModel.activity_categories.size()) {
            ACTIVITY_CATEGORY category = mActivityModel.activity_categories.get(position - mListView.getHeaderViewsCount());
            Intent intent = new Intent();
            intent.putExtra(ReleaseActivity.ACTIVITY_TYPE, category);
            setResult(ReleaseActivity.REQUEST_CODE, intent);
            finish();
        }

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
    public void onRefresh(int id) {
        mActivityModel.getCategory(this, shared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da"));
    }

    @Override
    public void onLoadMore(int id) {
        mActivityModel.getCategory(this, shared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da"));
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(VerFeedActivitycategoryApi.class)) {
            mListView.stopLoadMore();
            mListView.stopRefresh();
            mListView.setPullLoadEnable(mActivityModel.isMore);
            if (mAdapter == null) {
                mAdapter = new ActivityTypeAdapter(SelectTypeActivity.this, mActivityModel.activity_categories);
                mListView.setAdapter(mAdapter);
            } else {
                mAdapter.mList = mActivityModel.activity_categories;
                mAdapter.notifyDataSetChanged();
            }
        }
    }
}
