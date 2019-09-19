package com.door.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.door.adapter.DoorNewOpenRecordAdapter;
import com.door.entity.DoorOpenRecordEntity;
import com.door.entity.DoorOpenRecordSectionEntity;
import com.door.model.NewDoorModel;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/*
 * 新的开门记录
 *
 * */
public class NewDoorOpenRecordActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private RecyclerView rv_open_record;
    private NewDoorModel newDoorModel;
    private DoorNewOpenRecordAdapter doorNewOpenRecordAdapter;
    private int page = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_openrecord);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        rv_open_record = findViewById(R.id.rv_open_record);
        user_top_view_back.setOnClickListener(this::onClick);
        user_top_view_title.setText("开门记录");
        newDoorModel = new NewDoorModel(NewDoorOpenRecordActivity.this);
        newDoorModel.getDoorOpenRecord(0, page, NewDoorOpenRecordActivity.this);
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
        if (null != doorNewOpenRecordAdapter) {
            doorNewOpenRecordAdapter.disableLoadMoreIfNotFullPage();
            doorNewOpenRecordAdapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
                @Override
                public void onLoadMoreRequested() {
                    if (requsetSize > 0) {
                        page++;
                        newDoorModel.getDoorOpenRecord(0, page, NewDoorOpenRecordActivity.this);
                    }
                }
            }, rv_open_record);
            doorNewOpenRecordAdapter.loadMoreComplete();
            if (requsetSize == 0) {
                doorNewOpenRecordAdapter.loadMoreEnd(true);
            }
        }
    }

    private List<DoorOpenRecordSectionEntity> doorRecordList = new ArrayList<>();
    private int requsetSize = 0;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    DoorOpenRecordEntity doorOpenRecordEntity = GsonUtils.gsonToBean(result, DoorOpenRecordEntity.class);
                    DoorOpenRecordEntity.ContentBean contentBean = doorOpenRecordEntity.getContent();
                    List<DoorOpenRecordEntity.ContentBean.DataBeanX> dataBeanXList = contentBean.getData();
                    requsetSize = dataBeanXList.size();
                    if (requsetSize > 0) {
                        for (DoorOpenRecordEntity.ContentBean.DataBeanX dataBeanX : dataBeanXList) {
                            String headerContent = dataBeanX.getDate();
                            DoorOpenRecordSectionEntity headerSectionEntity = new DoorOpenRecordSectionEntity(true, headerContent);
                            doorRecordList.add(headerSectionEntity);
                            List<DoorOpenRecordEntity.ContentBean.DataBeanX.DataBean> dataBeanList = dataBeanX.getData();
                            for (int j = 0; j < dataBeanList.size(); j++) {
                                DoorOpenRecordSectionEntity childSectionEntity = new DoorOpenRecordSectionEntity(dataBeanList.get(j));
                                doorRecordList.add(childSectionEntity);
                            }
                        }
                    }
                    if (null == doorNewOpenRecordAdapter) {
                        doorNewOpenRecordAdapter = new DoorNewOpenRecordAdapter(NewDoorOpenRecordActivity.this, R.layout.adapter_door_openrecord, R.layout.adapter_header_itemapp, doorRecordList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(NewDoorOpenRecordActivity.this, LinearLayoutManager.VERTICAL, false);
                        rv_open_record.setLayoutManager(linearLayoutManager);
                        doorNewOpenRecordAdapter.isFirstOnly(true);
                        doorNewOpenRecordAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
                        rv_open_record.setAdapter(doorNewOpenRecordAdapter);
                    } else {
                        doorNewOpenRecordAdapter.notifyDataSetChanged();
                    }
                    loadingMoreData();
                } catch (Exception e) {

                }
                break;
        }

    }
}
