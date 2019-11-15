package com.point.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import cn.net.cyberway.R;

/***
 * 饭票赠送历史记录
 */

public class GivenPointHistoryActivity extends BaseActivity implements View.OnClickListener{


    private ImageView mBack;
    private TextView mTitle;
    private SwipeMenuRecyclerView rv_given_history;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_given_history);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        rv_given_history = findViewById(R.id.rv_given_history);
        mBack.setOnClickListener(this);
        mTitle.setText("历史记录");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }
}
