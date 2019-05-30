package com.im.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseFragmentActivity;
import com.youmai.hxsdk.fragment.GroupListFragment;
import com.youmai.hxsdk.proto.YouMaiBasic;

import cn.net.cyberway.R;


/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/15 9:57
 * @change
 * @chang time
 * @class describe  社群和普通群的列表
 */

public class IMGroupListActivity extends BaseFragmentActivity implements View.OnClickListener {
    public static final String GROUPTYPE = "grouptype";
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private LinearLayout fragment_layout;
    private RelativeLayout search_layout;
    private ListView group_lv;
    private int groupType = 0; //0 群聊 1社群
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private GroupListFragment groupListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_group_list);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        search_layout = findViewById(R.id.search_layout);
        group_lv = findViewById(R.id.group_lv);
        user_top_view_back.setOnClickListener(this);
        search_layout.setOnClickListener(this);
        groupType = getIntent().getIntExtra(GROUPTYPE, 0);
        if (groupType == 0) {
            user_top_view_title.setText("群聊");
            search_layout.setVisibility(View.GONE);
            group_lv.setVisibility(View.GONE);
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            if (groupListFragment == null) {
                groupListFragment = GroupListFragment.newInstance(YouMaiBasic.GroupType.GROUP_TYPE_MULTICHAT);
                transaction.add(R.id.fragment_layout, groupListFragment);
            } else {
                transaction.show(groupListFragment);
            }
            transaction.commitAllowingStateLoss();
        } else {
            user_top_view_title.setText("社群");
//            findViewById(R.id.fragment_layout).setVisibility(View.GONE);
            search_layout.setVisibility(View.GONE);
            group_lv.setVisibility(View.GONE);
            fragmentManager = getSupportFragmentManager();
            transaction = fragmentManager.beginTransaction();
            if (groupListFragment == null) {
                groupListFragment = GroupListFragment.newInstance(YouMaiBasic.GroupType.GROUP_TYPE_COMMUNITY);
                transaction.add(R.id.fragment_layout, groupListFragment);
            } else {
                transaction.show(groupListFragment);
            }
            transaction.commitAllowingStateLoss();
        }
        group_lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.search_layout:

                break;
        }
    }
}
