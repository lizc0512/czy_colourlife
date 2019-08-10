package com.door.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.door.adapter.IntelligenceDoorRecordAdapter;
import com.door.entity.DoorRecordEntity;
import com.door.model.NewDoorModel;
import com.nohttp.utils.GsonUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultLoadMoreView;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 智能门禁
 * hxg 2019.8.8
 */
public class IntelligenceDoorRecordActivity extends BaseActivity implements NewHttpResponse, View.OnClickListener {
    public static final String DEVICE = "device";
    public static final String RESULT = "result";

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_pwd_num;
    private TextView tv_pwd_time;
    private SwipeMenuRecyclerView rv_record;
    private NewDoorModel newDoorModel;

    private int page = 1;
    private String deviceId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intelligent_door_record);
        newDoorModel = new NewDoorModel(this);
        initView();
        initData();
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        tv_pwd_num = findViewById(R.id.tv_pwd_num);
        tv_pwd_time = findViewById(R.id.tv_pwd_time);
        rv_record = findViewById(R.id.rv_record);

        user_top_view_back.setOnClickListener(this);
        rv_record.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));

        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(this);
        rv_record.setLoadMoreView(defaultLoadMoreView);
        rv_record.setLoadMoreListener(() -> {
            page++;
            newDoorModel.devicePasswordLog(1, deviceId, page, this);
        });
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        user_top_view_title.setText("密码记录");

        String result = getIntent().getStringExtra(RESULT);
        DoorRecordEntity doorRecordEntity = GsonUtils.gsonToBean(result, DoorRecordEntity.class);
        DoorRecordEntity.ContentBean.PsdDataBean psdDataBean = doorRecordEntity.getContent().getPsd_data();
        tv_pwd_num.setText(psdDataBean.getRemain() + "");
        tv_pwd_time.setText(psdDataBean.getTime());

        List<DoorRecordEntity.ContentBean.DataBean> dataBean = doorRecordEntity.getContent().getData();
        if (null == mAdapter) {
            mAdapter = new IntelligenceDoorRecordAdapter(this, dataBean);
            rv_record.setAdapter(mAdapter);
        }

        deviceId = getIntent().getStringExtra(DEVICE);
        if (!TextUtils.isEmpty(deviceId)) {
            newDoorModel.devicePasswordLog(1, deviceId, page, this);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.user_top_view_back) {
            finish();
        }
    }

    private IntelligenceDoorRecordAdapter mAdapter;

    @SuppressLint("SetTextI18n")
    @Override
    public void OnHttpResponse(int what, String result) {
        if (1 == what) {
            if (!TextUtils.isEmpty(result)) {
                if (page == 1) {
                    mAdapter.mList.clear();
                }
                DoorRecordEntity doorRecordEntity = GsonUtils.gsonToBean(result, DoorRecordEntity.class);
                DoorRecordEntity.ContentBean contentBean = doorRecordEntity.getContent();
                DoorRecordEntity.ContentBean.PsdDataBean psdDataBean = contentBean.getPsd_data();
                tv_pwd_num.setText(psdDataBean.getRemain() + "");
                tv_pwd_time.setText(psdDataBean.getTime());

                List<DoorRecordEntity.ContentBean.DataBean> dataBean = doorRecordEntity.getContent().getData();
                mAdapter.setData(dataBean);

                boolean dataEmpty = null == mAdapter.mList || mAdapter.mList.size() == 0;
                boolean hasMore = contentBean.getTotal() > mAdapter.mList.size();
                rv_record.loadMoreFinish(dataEmpty, hasMore);
            }
        }

    }
}
