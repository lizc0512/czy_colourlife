package com.feed.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.Neighborhood.protocol.FeedPublishShareApi;
import com.Neighborhood.protocol.FeedPublishShareRequest;
import com.Neighborhood.protocol.FeedPublishShareResponse;
import com.external.eventbus.EventBus;
import com.feed.model.ShareFeedModel;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.user.UserAppConst;
import com.user.UserMessageConstant;

import cn.net.cyberway.R;

/**
 * 邻里圈分享
 */
public class ShareFeedActivity extends BaseActivity implements View.OnClickListener, HttpApiResponse {
    private ImageView mBack;
    private TextView mBackText;
    private TextView mTitle;
    private TextView mRightText;
    private EditText mEditText;
    private String mImgUrl;
    private String mText;
    private String mUrl;
    private String mtitle;
    private ImageView mImg;
    private TextView mTextview;
    private ShareFeedModel shareFeedModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_feed);
        init();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * 初始化控件
     */
    private void init() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mBackText = (TextView) findViewById(R.id.user_top_view_tvback);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        mRightText = (TextView) findViewById(R.id.user_top_view_right);
        mEditText = (EditText) findViewById(R.id.create_feed_content);
        mImg = (ImageView) findViewById(R.id.img_share);
        mTextview = (TextView) findViewById(R.id.tv_text);
        mBackText.setText(getResources().getString(R.string.message_cancel));
        mTitle.setText(getResources().getString(R.string.feed_publish_activity));
        mRightText.setText(getResources().getString(R.string.feed_publish));
        mBackText.setVisibility(View.VISIBLE);
        mBack.setVisibility(View.GONE);
        mRightText.setVisibility(View.VISIBLE);
        mBackText.setOnClickListener(this);
        mRightText.setOnClickListener(this);

        mImgUrl = getIntent().getStringExtra("ImgUrl");
        mText = getIntent().getStringExtra("Content");
        mEditText.setText(mText);
        mtitle = getIntent().getStringExtra("Title");
        mUrl = getIntent().getStringExtra("Url");

        ImageLoader.getInstance().displayImage(mImgUrl, mImg, BeeFrameworkApp.optionsImage);
        mTextview.setText(mtitle);
        shareFeedModel = new ShareFeedModel(this);
        ThemeStyleHelper.backTexteFrameLayout(getApplicationContext(), czy_title_layout, mBackText, mTitle, mRightText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_tvback:
                finish();
                overridePendingTransition(0, 0);
                ToastUtil.toastShow(this, "取消分享");
                break;
            case R.id.user_top_view_right:
                FeedPublishShareRequest feedPublishShareRequest = new FeedPublishShareRequest();
                mText = mEditText.getText().toString();
                feedPublishShareRequest.title = mtitle;
                feedPublishShareRequest.content = mText;
                feedPublishShareRequest.url = mUrl;
                feedPublishShareRequest.image = mImgUrl;
                shareFeedModel.shareFeedPublish(this, feedPublishShareRequest);
                break;
        }
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(FeedPublishShareApi.class)) {
            FeedPublishShareResponse response = ((FeedPublishShareApi) api).response;
            if (response.ok == 1) {
                ToastUtil.toastShow(this, "发布成功");
                Message msg = new Message();
                msg.what = UserMessageConstant.CREATE_FEED;
                EventBus.getDefault().post(msg);
                Intent intent = new Intent();
                setResult(RESULT_OK, intent);
                finish();
            } else {
                ToastUtil.toastShow(this, response.message);
            }
        }
    }
}
