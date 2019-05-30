package com.eparking.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.adapter.MonthCardApplyRecordAdapter;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingOrderModel;
import com.eparking.protocol.MonthCardApplyEntity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/11 15:55
 * @change
 * @chang time
 * @class describe   月卡申请记录
 */
public class MonthCardApplyRecordActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private XRecyclerView rv_record;
    private LinearLayout empty_layout;
    private TextView empty_title;
    private MonthCardApplyRecordAdapter monthCardApplyRecordAdapter;
    private List<MonthCardApplyEntity.ContentBean> monthCardApplyEntityList = new ArrayList<>();

    private ParkingOrderModel parkingOrderModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_cardrecord);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_monthapply_record));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        rv_record = findViewById(R.id.rv_record);
        empty_layout = findViewById(R.id.empty_layout);
        empty_title = findViewById(R.id.empty_title);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MonthCardApplyRecordActivity.this);
        rv_record.setLayoutManager(linearLayoutManager);
        rv_record.addItemDecoration(new SpaceItemDecoration(Utils.dip2px(MonthCardApplyRecordActivity.this, 10)));
        rv_record.setLoadingMoreEnabled(false);
        rv_record.setPullRefreshEnabled(false);
        monthCardApplyRecordAdapter = new MonthCardApplyRecordAdapter(MonthCardApplyRecordActivity.this, monthCardApplyEntityList);
        rv_record.setAdapter(monthCardApplyRecordAdapter);
        parkingOrderModel = new ParkingOrderModel(MonthCardApplyRecordActivity.this);
        parkingOrderModel.getContractApplyList(0, this);
        ParkingActivityUtils.getInstance().addActivity(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    private void setEmptyView() {
        if (monthCardApplyEntityList.size() == 0) {
            empty_layout.setVisibility(View.VISIBLE);
            empty_title.setText(getResources().getString(R.string.parking_no_applyrecord));
        } else {
            empty_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    monthCardApplyEntityList.clear();
                    MonthCardApplyEntity monthCardApplyEntity = GsonUtils.gsonToBean(result, MonthCardApplyEntity.class);
                    monthCardApplyEntityList.addAll(monthCardApplyEntity.getContent());
                } catch (Exception e) {

                }
                if (monthCardApplyEntityList.size() == 0) {
                    setEmptyView();
                }
                monthCardApplyRecordAdapter.notifyDataSetChanged();
                break;
        }
    }
}
