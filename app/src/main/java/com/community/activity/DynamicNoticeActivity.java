package com.community.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.community.adapter.CommunityRemindAdapter;
import com.community.entity.CommunityDynamicsListEntity;
import com.community.entity.CommunityRemindListEntity;
import com.community.model.CommunityDynamicsModel;
import com.external.eventbus.EventBus;
import com.nohttp.utils.GsonUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.community.activity.DynamicsDetailsActivity.DYNAMICS_DETAILS;
import static com.community.fragment.CommunityDynamicsFragment.UPDATE_DYNAMIC_REMINDCOUNT;

/**
 * @name ${yuansk}
 * @class name：com.cashier.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/7 10:35
 * @change
 * @chang time
 * @class describe   动态提醒
 */

public class DynamicNoticeActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private SwipeMenuRecyclerView message_rv;
    private LinearLayout no_data_layout;
    private CommunityDynamicsModel communityDynamicsModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_message);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        message_rv = findViewById(R.id.message_rv);
        no_data_layout = findViewById(R.id.no_data_layout);
        user_top_view_back.setOnClickListener(this::onClick);
        user_top_view_title.setText(getResources().getString(R.string.community_dynamics_notice));
        message_rv.useDefaultLoadMore();
        communityDynamicsModel = new CommunityDynamicsModel(DynamicNoticeActivity.this);
        communityDynamicsModel.getDynamicRemindList(0, true, DynamicNoticeActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    private List<CommunityRemindListEntity.ContentBean> contentBeanList = new ArrayList<>();

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    CommunityRemindListEntity communityRemindListEntity = GsonUtils.gsonToBean(result, CommunityRemindListEntity.class);
                    contentBeanList.clear();
                    contentBeanList.addAll(communityRemindListEntity.getContent());
                    if (contentBeanList.size() == 0) {
                        no_data_layout.setVisibility(View.VISIBLE);
                    } else {
                        no_data_layout.setVisibility(View.GONE);
                        CommunityRemindAdapter communityRemindAdapter = new CommunityRemindAdapter(DynamicNoticeActivity.this, contentBeanList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DynamicNoticeActivity.this, LinearLayoutManager.VERTICAL, false);
                        message_rv.setLayoutManager(linearLayoutManager);
                        message_rv.setAdapter(communityRemindAdapter);
                        message_rv.loadMoreFinish(false, false);
                        communityRemindAdapter.setOnItemClickListener(new OnItemClickListener() {
                            @Override
                            public void onItemClick(int i) {
                                if (i >= 0) {
                                    getDynamicDetails(contentBeanList.get(i).getSource_id());
                                }
                            }
                        });
                    }
                } catch (Exception e) {


                }
                break;
            case 1:
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (!jsonObject.isNull("content")) {
                        JSONObject sonJsonObject = jsonObject.optJSONObject("content");
                        JSONObject newResultObject = new JSONObject();
                        JSONObject newContentObject = new JSONObject();
                        JSONArray newJsonArray = new JSONArray();
                        newJsonArray.put(sonJsonObject);
                        newContentObject.put("data", newJsonArray);
                        newResultObject.put("content", newContentObject);
                        String newResult = newResultObject
                                .toString();
                        CommunityDynamicsListEntity communityDynamicsListEntity = GsonUtils.gsonToBean(newResult, CommunityDynamicsListEntity.class);
                        List<CommunityDynamicsListEntity.ContentBean.DataBean> dataBeanList = communityDynamicsListEntity.getContent().getData();
                        if (null != dataBeanList && dataBeanList.size() > 0) {
                            Intent intent = new Intent(DynamicNoticeActivity.this, DynamicsDetailsActivity.class);
                            intent.putExtra(DYNAMICS_DETAILS, dataBeanList.get(0));
                            startActivity(intent);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                break;
            case 2://标记为已读更改未读数量
                Message message = Message.obtain();
                message.what = UPDATE_DYNAMIC_REMINDCOUNT;
                EventBus.getDefault().post(message);
                communityDynamicsModel.getDynamicRemindList(0, false, DynamicNoticeActivity.this);
                break;
        }
    }

    private void getDynamicDetails(String source_id) {
        communityDynamicsModel.setDynamicRemindRead(2, source_id, DynamicNoticeActivity.this);
        communityDynamicsModel.getDynamicsDetails(1, source_id, DynamicNoticeActivity.this);
    }
}
