package com.im.activity;

import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseFragmentActivity;
import com.im.adapter.IMPopAdapter;
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
    private ImageView img_right;
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
        search_layout.setVisibility(View.GONE);
        group_lv.setVisibility(View.GONE);
        img_right = findViewById(R.id.img_right);
        user_top_view_back.setOnClickListener(this);
        img_right.setOnClickListener(this);
        img_right.setVisibility(View.VISIBLE);
        img_right.setImageResource(R.drawable.im_icon_add);
        groupType = getIntent().getIntExtra(GROUPTYPE, 0);
        user_top_view_title.setText("群聊列表");

        fragmentManager = getSupportFragmentManager();
        transaction = fragmentManager.beginTransaction();
        if (groupListFragment == null) {
            groupListFragment = GroupListFragment.newInstance(YouMaiBasic.GroupType.GROUP_TYPE_MULTICHAT);
            transaction.add(R.id.fragment_layout, groupListFragment);
        } else {
            transaction.show(groupListFragment);
        }
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.img_right:
                initPopup();
                break;
        }
    }

    private PopupWindow popupWindow;

    private void initPopup() {
        View contentview = LayoutInflater.from(IMGroupListActivity.this).inflate(R.layout.pop_listview_layout, null);
        popupWindow = new PopupWindow(contentview,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        setBackgroundAlpha(0.8f);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(img_right, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        ListView popLv = contentview.findViewById(R.id.pop_lv);
        popLv.setAdapter(new IMPopAdapter(IMGroupListActivity.this));
        popLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                Intent intent = new Intent(IMGroupListActivity.this, classArr[position]);
                startActivity(intent);
            }
        });
    }

    private Class[] classArr = {IMCreateGroupChatActivity.class, IMCreateCommunityActivity.class,IMCommunityListActivity.class};

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }
}
