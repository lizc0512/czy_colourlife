package com.im.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/19 11:10
 * @change
 * @chang time
 * @class describe  搜索所有的(社群，群聊，好友)
 */

public class IMSearchAllActivity extends BaseActivity implements View.OnClickListener {

    private EditText ed_search_content;
    private TextView tv_hint;
    private ImageView iv_delete_text;
    private TextView tv_cancel;
    private TextView tv_search_community;
    private TextView tv_search_group;
    private TextView tv_search_friend;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_condition);
        ed_search_content = findViewById(R.id.ed_search_content);
        tv_hint = findViewById(R.id.tv_hint);
        iv_delete_text = findViewById(R.id.iv_delete_text);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_search_community = findViewById(R.id.tv_search_community);
        tv_search_group = findViewById(R.id.tv_search_group);
        tv_search_friend = findViewById(R.id.tv_search_friend);
        tv_cancel.setOnClickListener(this);
        tv_search_community.setOnClickListener(this);
        tv_search_group.setOnClickListener(this);
        tv_search_friend.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                break;
            case R.id.tv_search_community:

                break;
            case R.id.tv_search_group:

                break;
            case R.id.tv_search_friend:

                break;
        }
    }
}
