package com.about.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.about.model.FeedbackModel;
import com.about.protocol.FeedBackDetailsEntity;
import com.about.protocol.FeedBackListEntity;
import com.feed.adapter.FeedGirdViewImageAdapter;
import com.feed.view.NoScrollGridView;
import com.nohttp.utils.GsonUtils;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * Created by HX_CHEN on 2016/7/14.
 */
public class FeedBackDetailsActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private FrameLayout czy_title_layout;
    private LinearLayout ll_feedback;
    private LinearLayout ll_norespose;
    private RelativeLayout ll_back;
    private TextView tv_content;
    private TextView tv_type_back;
    private TextView tv_content_back;
    private TextView tv_type;
    private TextView tv_time;
    private TextView tv_time_back;
    private TextView tv_title;   //标题
    private ImageView imageView_back;//返回
    private NoScrollGridView feedImageGridView;
    private FeedbackModel feedbackModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_details_layout);
        ll_feedback = (LinearLayout) findViewById(R.id.ll_myfeedback);
        ll_norespose = (LinearLayout) findViewById(R.id.ll_norespose);
        ll_back = (RelativeLayout) findViewById(R.id.rl_cotent);
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_content = (TextView) findViewById(R.id.tv_content);
        tv_type_back = (TextView) findViewById(R.id.tv_type_back);
        tv_content_back = (TextView) findViewById(R.id.tv_content_back);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_time_back = (TextView) findViewById(R.id.tv_time_back);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        feedImageGridView = (NoScrollGridView) findViewById(R.id.feed_image_gridview);
        tv_title.setText(getResources().getString(R.string.feedback_details));
        imageView_back.setOnClickListener(this);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, imageView_back, tv_title);
        FeedBackListEntity.ContentBean feedbacklistdata = (FeedBackListEntity.ContentBean) getIntent().getSerializableExtra("model");
        tv_content.setText(feedbacklistdata.getContent());
        tv_time.setText(feedbacklistdata.getCreate_at());
        tv_type.setText(feedbacklistdata.getName());
        feedbackModel = new FeedbackModel(this);
        feedbackModel.getFeedBackDetails(0, feedbacklistdata.getId(), this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    private ArrayList<String> imageList = new ArrayList<>();

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    FeedBackDetailsEntity feedBackDetailsEntity = GsonUtils.gsonToBean(result, FeedBackDetailsEntity.class);
                    FeedBackDetailsEntity.ContentBean contentBean = feedBackDetailsEntity.getContent();
                    FeedBackDetailsEntity.ContentBean.FeedbackBean feedbackBean = contentBean.getFeedback();
                    List<FeedBackDetailsEntity.ContentBean.FeedbackBean.ImageArrBean> imageArrBeanList = feedbackBean.getImage_arr();
                    imageList.clear();
                    for (FeedBackDetailsEntity.ContentBean.FeedbackBean.ImageArrBean imageArrBean : imageArrBeanList) {
                        imageList.add(imageArrBean.getUrl());
                    }
                    if (imageList.size() > 0) {
                        feedImageGridView.setVisibility(View.VISIBLE);
                        FeedGirdViewImageAdapter feedGirdViewImageAdapter = new FeedGirdViewImageAdapter(this, imageList);
                        feedImageGridView.setAdapter(feedGirdViewImageAdapter);
                    } else {
                        feedImageGridView.setVisibility(View.GONE);
                    }
                    JSONObject jsonObject = new JSONObject(result).optJSONObject("content");
                    if (!jsonObject.isNull("reply")) {
                        ll_back.setVisibility(View.VISIBLE);
                        ll_norespose.setVisibility(View.GONE);
                        JSONObject replyJsonObject = jsonObject.optJSONObject("reply");
                        tv_type_back.setText(replyJsonObject.optString("name"));
                        tv_time_back.setText(replyJsonObject.optString("create_at"));
                        tv_content_back.setText(replyJsonObject.optString("content"));
                    } else {
                        ll_back.setVisibility(View.GONE);
                        ll_norespose.setVisibility(View.VISIBLE);
                    }
                } catch (Exception e) {
                    
                }
                break;
        }
    }
}
