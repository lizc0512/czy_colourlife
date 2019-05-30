package com.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.external.eventbus.EventBus;
import com.im.adapter.IMCommunityListAdapter;
import com.im.entity.CommunityProgressEntity;
import com.im.model.IMCommunityModel;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.youmai.hxsdk.chatgroup.IMGroupActivity;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/7/4 20:18
 * @change
 * @chang time
 * @class describe
 */
public class IMCommunityListActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private RelativeLayout search_layout;
    private ListView group_lv;
    private FrameLayout fragment_layout;

    private IMCommunityModel imCommunityModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_group_list);
        EventBus.getDefault().register(this);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        search_layout = findViewById(R.id.search_layout);
        group_lv = findViewById(R.id.group_lv);
        fragment_layout = findViewById(R.id.fragment_layout);
        search_layout.setVisibility(View.GONE);
        fragment_layout.setVisibility(View.GONE);
        user_top_view_back.setOnClickListener(this);
        user_top_view_title.setText("社群管理助手");
        imCommunityModel = new IMCommunityModel(IMCommunityListActivity.this);
        imCommunityModel.getUserCommunityInfor(0, true, this);
        group_lv.setDividerHeight(0);
        group_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CommunityProgressEntity.ContentBean contentBean = contentBeanList.get(position);
                int state = contentBean.getChange_state();
                if (2 == state) {
                    String imId = contentBean.getIm_id();
                    String groupName = contentBean.getGroup_name();
                    if (TextUtils.isEmpty(imId)) {
                        Intent intent = new Intent(IMCommunityListActivity.this, IMCreateGroupChatActivity.class);
                        intent.putExtra(IMCreateCommunityActivity.COMMUNITYID, contentBean.getId());
                        intent.putExtra(IMCreateGroupChatActivity.GROUPTYPE, 1);
                        intent.putExtra(IMCreateCommunityActivity.GROUPNAME, contentBean.getGroup_name());
                        startActivity(intent);
                        finish();
                    } else {
                        Intent intent = new Intent(IMCommunityListActivity.this, IMGroupActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                        intent.putExtra(IMGroupActivity.GROUP_TYPE, 2);
                        intent.putExtra(IMGroupActivity.DST_UUID, Integer.valueOf(contentBean.getIm_id()));
                        intent.putExtra(IMGroupActivity.DST_NAME, groupName);
                        startActivity(intent);
                    }
                } else {
                    Intent intent = new Intent(IMCommunityListActivity.this, IMApplyProgressActivity.class);
                    intent.putExtra(IMApplyProgressActivity.COMMMUNITYID, contentBean.getId());
                    startActivityForResult(intent, 1000);
                }
            }
        });
        String userUUid = shared.getString(UserAppConst.Colour_User_uuid, "");
        editor.putBoolean(UserAppConst.IM_CMANGAG_ERHELPER + userUUid, false).commit();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        imCommunityModel.getUserCommunityInfor(0, true, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            finish();
        }
    }

    private List<CommunityProgressEntity.ContentBean> contentBeanList = new ArrayList<>();

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    private IMCommunityListAdapter imCommunityListAdapter;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        CommunityProgressEntity communityProgressEntity = GsonUtils.gsonToBean(result, CommunityProgressEntity.class);
                        contentBeanList.clear();
                        contentBeanList.addAll(communityProgressEntity.getContent());
                        if (null == imCommunityListAdapter) {
                            imCommunityListAdapter = new IMCommunityListAdapter(IMCommunityListActivity.this, contentBeanList);
                            group_lv.setAdapter(imCommunityListAdapter);
                            group_lv.setSelection(contentBeanList.size() - 1);
                        } else {
                            imCommunityListAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.COMMUNITY_MANAGER_NOTICE:
                if (null == imCommunityModel) {
                    imCommunityModel = new IMCommunityModel(IMCommunityListActivity.this);
                }
                imCommunityModel.getUserCommunityInfor(0, false, this);
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(IMCommunityListActivity.this)) {
            EventBus.getDefault().unregister(this);
        }
    }
}
