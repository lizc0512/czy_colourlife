package com.community.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;

import cn.net.cyberway.R;
/**
 * @name ${yuansk}
 * @class name：com.cashier.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/7 10:35
 * @change
 * @chang time
 * @class describe   发送动态
 */
public class PublishDynamicsActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView user_top_view_right;
    private EditText create_dynamics_content;
    private GridLayout create_dynamics_photo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_dynamics);
        initView();
    }

    private void initView() {
        user_top_view_back=   findViewById(R.id.user_top_view_back);
        user_top_view_title=findViewById(R.id.user_top_view_title);
        user_top_view_right= findViewById(R.id.user_top_view_right);
        create_dynamics_content= findViewById(R.id.create_dynamics_content);
        create_dynamics_photo= findViewById(R.id.create_dynamics_photo);
        user_top_view_back.setOnClickListener(this::onClick);
        user_top_view_right.setOnClickListener(this::onClick);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setText(getResources().getString(R.string.community_publish));
        user_top_view_title.setText(getResources().getString(R.string.community_dynamics));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:


                break;
        }

    }

    @Override
    public void OnHttpResponse(int what, String result) {

    }
}

