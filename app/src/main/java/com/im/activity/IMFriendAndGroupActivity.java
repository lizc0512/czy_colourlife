package com.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.im.adapter.FriendInforAdapter;
import com.im.entity.FriendInforEntity;
import com.im.helper.CacheApplyRecorderHelper;
import com.im.helper.CacheFriendInforHelper;
import com.im.utils.FriendInforComparator;
import com.im.view.SideBar;
import com.user.UserAppConst;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.net.cyberway.R;
import q.rorbin.badgeview.QBadgeView;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/22 15:10
 * @change
 * @chang time
 * @class describe  我的好友和群组列表页面
 */

public class IMFriendAndGroupActivity extends BaseActivity implements View.OnClickListener {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ImageView img_right;
    private ListView sortListView;
    private SideBar sideBar;
    private TextView dialog;
    private FriendInforAdapter friendInforAdapter;
    private List<FriendInforEntity> friendInforEntityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_group);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        img_right = findViewById(R.id.img_right);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sortListView = (ListView) findViewById(R.id.phone_lv);
        user_top_view_title.setText("通讯录");
        img_right.setVisibility(View.VISIBLE);
        img_right.setImageResource(R.drawable.im_icon_nav_friends);
        sortListView = (ListView) findViewById(R.id.phone_lv);
        user_top_view_back.setOnClickListener(this);
        img_right.setOnClickListener(this);
        sideBar.setTextView(dialog);
        //设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = friendInforAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }

            }
        });
        initData();
        initHeadView();
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (position >= 1) {
                    FriendInforEntity friendInforEntity = friendInforEntityList.get(position - 1);
                    String uuid = friendInforEntity.getUuid();
                    Intent intent = null;
                    if (shared.getString(UserAppConst.Colour_User_uuid, "").equals(uuid)) {
                        intent = new Intent(IMFriendAndGroupActivity.this, IMUserSelfInforActivity.class);
                    } else {
                        intent = new Intent(IMFriendAndGroupActivity.this, IMFriendInforActivity.class);
                    }
                    intent.putExtra(IMFriendInforActivity.USERUUID, uuid);
                    startActivity(intent);
                }
            }
        });

    }


    private void initData() {
        friendInforEntityList.clear();
        friendInforEntityList.addAll(CacheFriendInforHelper.instance().toQueryFriendList(IMFriendAndGroupActivity.this));
        FriendInforComparator friendInforComparator = new FriendInforComparator();
        Collections.sort(friendInforEntityList, friendInforComparator);
        List<String> uuidList = CacheFriendInforHelper.instance().toQueryFriendUUIdList(IMFriendAndGroupActivity.this);
        Map<String, Boolean> checkMap = new HashMap<>();
        for (String str : uuidList) {
            checkMap.put(str, false);
        }
        if (null == friendInforAdapter) {
            friendInforAdapter = new FriendInforAdapter(this, friendInforEntityList, 0);
            friendInforAdapter.setCheckMap(checkMap);
            sortListView.setAdapter(friendInforAdapter);
        } else {
            friendInforAdapter.setCheckMap(checkMap);
            friendInforAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (null != badgeView) {
            int size = CacheApplyRecorderHelper.instance().toQueryApplyRecordSize(getApplicationContext(), "0");
            if (shared.getBoolean(UserAppConst.IM_APPLY_FRIEND, false)) {
                badgeView.setBadgeNumber(size);
            } else {
                badgeView.setBadgeNumber(0);
            }
        }
        initData();
    }

    private QBadgeView badgeView;

    private void initHeadView() {
        View headView = LayoutInflater.from(IMFriendAndGroupActivity.this).inflate(R.layout.headview_group_chat, null);
        headView.findViewById(R.id.community_group_layout).setOnClickListener(this);
        headView.findViewById(R.id.new_friend_layout).setOnClickListener(this);
        headView.findViewById(R.id.normal_group_layout).setOnClickListener(this);
        ImageView iv_new_friend = headView.findViewById(R.id.iv_new_friend);
        sortListView.addHeaderView(headView);
        badgeView = new QBadgeView(IMFriendAndGroupActivity.this);
        badgeView.bindTarget(iv_new_friend);
        badgeView.setBadgeGravity(Gravity.TOP | Gravity.END);
        badgeView.setBadgeTextSize(10f, true);
        badgeView.setBadgeBackgroundColor(ContextCompat.getColor(IMFriendAndGroupActivity.this, R.color.hx_color_red_tag));
        badgeView.setShowShadow(false);
        int size = CacheApplyRecorderHelper.instance().toQueryApplyRecordSize(getApplicationContext(), "0");
        badgeView.setBadgeNumber(size);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.new_friend_layout://添加好友页面
                Intent newFriendIntent = new Intent(IMFriendAndGroupActivity.this, IMApplyFriendRecordActivity.class);
                startActivity(newFriendIntent);
                break;
            case R.id.img_right://添加好友页面
                Intent intent = new Intent(IMFriendAndGroupActivity.this, IMAddFriendActivity.class);
                startActivity(intent);
                break;
            case R.id.community_group_layout: //去社群的列表
                Intent communtiyIntent = new Intent(IMFriendAndGroupActivity.this, IMGroupListActivity.class);
                communtiyIntent.putExtra(IMGroupListActivity.GROUPTYPE, 1);
                startActivity(communtiyIntent);
                break;
            case R.id.normal_group_layout://去普通群组的列表
                Intent groupIntent = new Intent(IMFriendAndGroupActivity.this, IMGroupListActivity.class);
                groupIntent.putExtra(IMGroupListActivity.GROUPTYPE, 0);
                startActivity(groupIntent);
                break;
        }
    }
}
