package com.door.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.door.adapter.NewDoorOwnerApplyAdapter;
import com.door.entity.DoorApplyRecordEntity;
import com.door.model.NewDoorAuthorModel;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/*
 * 门禁(我的主动申请记录)
 *
 * */
public class NewDoorApplyRecordActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private RecyclerView rv_open_record;
    private TextView tv_empty_record;
    private List<DoorApplyRecordEntity.ContentBean.ListBean> listBeanList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_openrecord);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        rv_open_record = findViewById(R.id.rv_open_record);
        tv_empty_record = findViewById(R.id.tv_empty_record);
        user_top_view_back.setOnClickListener(this::onClick);
        user_top_view_title.setText("我的申请");
        NewDoorAuthorModel newDoorAuthorModel = new NewDoorAuthorModel(NewDoorApplyRecordActivity.this);
        newDoorAuthorModel.getUserApplyList(0, NewDoorApplyRecordActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    DoorApplyRecordEntity doorApplyRecordEntity = GsonUtils.gsonToBean(result, DoorApplyRecordEntity.class);
                    DoorApplyRecordEntity.ContentBean contentBean = doorApplyRecordEntity.getContent();
                    listBeanList.clear();
                    listBeanList.addAll(contentBean.getList());
                    if (listBeanList.size() == 0) {
                        rv_open_record.setVisibility(View.GONE);
                        tv_empty_record.setVisibility(View.VISIBLE);
                    } else {
                        NewDoorOwnerApplyAdapter newDoorOwnerApplyAdapter = new NewDoorOwnerApplyAdapter(listBeanList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewDoorApplyRecordActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_open_record.setLayoutManager(linearLayoutManager);
                        rv_open_record.setAdapter(newDoorOwnerApplyAdapter);
                    }
                } catch (Exception e) {

                }
                break;
        }
    }
}
