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
import com.eparking.adapter.ParkingShareRecordAdapter;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingOrderModel;
import com.eparking.protocol.ParkingShareRecordEntity;
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
 * @time 2018/10/11 17:00
 * @change
 * @chang time
 * @class describe  共享车位记录
 */
public class ShareParkingSpaceRecoActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private LinearLayout empty_layout;
    private TextView empty_title;
    private XRecyclerView rv_record;
    private ParkingShareRecordAdapter parkingShareRecordAdapter;
    private List<ParkingShareRecordEntity.ContentBean.ListsBean> parkingShareRecordEntityList = new ArrayList<>();
    private ParkingOrderModel parkingOrderModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_cardrecord);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_shareparking_record));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        rv_record = findViewById(R.id.rv_record);
        empty_layout = findViewById(R.id.empty_layout);
        empty_title = findViewById(R.id.empty_title);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ShareParkingSpaceRecoActivity.this);
        rv_record.setLayoutManager(linearLayoutManager);
        rv_record.addItemDecoration(new SpaceItemDecoration(Utils.dip2px(ShareParkingSpaceRecoActivity.this, 10)));
        rv_record.setLoadingMoreEnabled(true);
        rv_record.setPullRefreshEnabled(true);
        parkingShareRecordAdapter = new ParkingShareRecordAdapter(ShareParkingSpaceRecoActivity.this, parkingShareRecordEntityList);
        rv_record.setAdapter(parkingShareRecordAdapter);
        rv_record.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                page = 1;
                parkingOrderModel.getLockOrderList(0, page, pageSize, ShareParkingSpaceRecoActivity.this);
            }

            @Override
            public void onLoadMore() {
                if (parkingShareRecordEntityList.size() < total) {
                    page++;
                    parkingOrderModel.getLockOrderList(0, page, pageSize, ShareParkingSpaceRecoActivity.this);
                } else {
                    rv_record.loadMoreComplete();
                }
            }
        });
        parkingOrderModel = new ParkingOrderModel(ShareParkingSpaceRecoActivity.this);
        rv_record.refresh();
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
        if (parkingShareRecordEntityList.size() == 0) {
            empty_layout.setVisibility(View.VISIBLE);
            empty_title.setText(getResources().getString(R.string.parking_no_sharerecord));
        } else {
            empty_layout.setVisibility(View.GONE);
        }
    }

    private int page = 1;
    private int pageSize = 20;
    private int total;

    @Override
    public void OnHttpResponse(int what, String result) {

        switch (what) {
            case 0:
                try {
                    if (page == 1) {
                        parkingShareRecordEntityList.clear();
                    }
                    ParkingShareRecordEntity shareRecordEntity = GsonUtils.gsonToBean(result, ParkingShareRecordEntity.class);
                    ParkingShareRecordEntity.ContentBean contentBean = shareRecordEntity.getContent();
                    parkingShareRecordEntityList.addAll(contentBean.getLists());
                    total = contentBean.getTotal();
                } catch (Exception e) {

                }
                if (parkingShareRecordEntityList.size() == 0) {
                    setEmptyView();
                }
                if (page == 1) {
                    rv_record.refreshComplete();
                } else {
                    rv_record.loadMoreComplete();
                }
                parkingShareRecordAdapter.notifyDataSetChanged();
                break;
        }

    }
}
