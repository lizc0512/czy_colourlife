package com.door.activity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.door.adapter.DoorNewOpenRecordAdapter;

import cn.net.cyberway.R;

/*
 * 新的开门记录
 *
 *
 *
 *
 * */
public class NewDoorOpenRecordActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private RecyclerView rv_open_record;
    private DoorNewOpenRecordAdapter doorNewOpenRecordAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_openrecord);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        rv_open_record = findViewById(R.id.rv_open_record);
        user_top_view_back.setOnClickListener(this::onClick);
        user_top_view_title.setText("开门记录");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    private void loadingMoreData() {
        doorNewOpenRecordAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {

            }
        }, rv_open_record);
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:


                break;
        }

    }
}
