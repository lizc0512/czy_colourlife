package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import cn.net.cyberway.R;

import static com.point.activity.PointTransactionListActivity.POINTTPANO;

/***
 * 饭票返回计划
 */

public class ReturnPointPlanActivity extends BaseActivity implements View.OnClickListener{


    private ImageView mBack;
    private TextView mTitle;
    private SwipeMenuRecyclerView rv_given_history;
    private String pano;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_given_history);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        rv_given_history = findViewById(R.id.rv_given_history);
        mBack.setOnClickListener(this);
        Intent intent=getIntent();
        pano=intent.getStringExtra(POINTTPANO);
        mTitle.setText("返回计划");
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
