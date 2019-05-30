package com.eparking.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.adapter.AppointmentRecordAdapter;
import com.eparking.helper.OptionsPickerInterface;
import com.eparking.helper.OptionsPickerViewUtils;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingOrderModel;
import com.eparking.protocol.AppointmentRecordEntity;
import com.eparking.protocol.CarStationListEntity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

import static com.eparking.helper.ConstantKey.EPARKINGCARSTATIONLIST;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  预约记录
 */
public class AppointmentRecordActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private TextView user_top_view_right;//筛选
    private XRecyclerView rv_record;
    private LinearLayout empty_layout;
    private TextView empty_title;
    private AppointmentRecordAdapter appointmentRecordAdapter;
    private List<AppointmentRecordEntity.ContentBean.ListsBean> appointmentRecordEntityList = new ArrayList<>();
    private ParkingOrderModel parkingOrderModel = null;
    private List<CarStationListEntity.ContentBean> carStationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_cardrecord);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_appoint_record));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setTextColor(Color.parseColor("#27a2f0"));
        user_top_view_right.setText(getResources().getString(R.string.parking_filter));
        user_top_view_right.setOnClickListener(this);
        rv_record = findViewById(R.id.rv_record);
        empty_layout = findViewById(R.id.empty_layout);
        empty_title = findViewById(R.id.empty_title);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(AppointmentRecordActivity.this);
        rv_record.setLayoutManager(linearLayoutManager);
        rv_record.addItemDecoration(new SpaceItemDecoration(Utils.dip2px(AppointmentRecordActivity.this, 10)));
        rv_record.setLoadingMoreEnabled(true);
        rv_record.setPullRefreshEnabled(true);
        appointmentRecordAdapter = new AppointmentRecordAdapter(AppointmentRecordActivity.this, appointmentRecordEntityList);
        rv_record.setAdapter(appointmentRecordAdapter);
        rv_record.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                page = 1;
                getCarAppointmentRecord();
            }

            @Override
            public void onLoadMore() {
                if (total > appointmentRecordEntityList.size()) {
                    page++;
                    getCarAppointmentRecord();
                } else {
                    rv_record.loadMoreComplete();
                }
            }
        });
        String carStationCache = shared.getString(EPARKINGCARSTATIONLIST, "");
        if (!TextUtils.isEmpty(carStationCache)) {
            try {
                carStationList.clear();
                CarStationListEntity carStationListEntity = GsonUtils.gsonToBean(carStationCache, CarStationListEntity.class);
                carStationList.addAll(carStationListEntity.getContent());
            } catch (Exception e) {

            }
        }
        parkingOrderModel = new ParkingOrderModel(AppointmentRecordActivity.this);
        if (carStationList.size() == 0) {
            setEmptyView();
            user_top_view_right.setVisibility(View.GONE);
        } else {
            plate = carStationList.get(0).getPlate();
            rv_record.refresh();
        }
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private void getCarAppointmentRecord() {
        parkingOrderModel.getReservedApplyList(0, plate, page, pageSize, this);
    }


    private String plate;
    private int page = 1;
    private int pageSize = 20;
    private int total;

    private void setEmptyView() {
        if (appointmentRecordEntityList.size() == 0) {
            empty_layout.setVisibility(View.VISIBLE);
            empty_title.setText(getResources().getString(R.string.parking_no_appoinmentrecord));
        } else {
            empty_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                OptionsPickerViewUtils.showPickerView(AppointmentRecordActivity.this, R.string.car_brand, 0, carStationList, new OptionsPickerInterface() {
                    @Override
                    public void choicePickResult(int type, String choiceText, String choiceId) {
                        plate = choiceText;
                        empty_layout.setVisibility(View.GONE);
                        rv_record.refresh();
                    }
                });
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    if (page == 1) {
                        appointmentRecordEntityList.clear();
                    }
                    AppointmentRecordEntity appointmentRecordEntity = GsonUtils.gsonToBean(result, AppointmentRecordEntity.class);
                    AppointmentRecordEntity.ContentBean contentBean = appointmentRecordEntity.getContent();
                    appointmentRecordEntityList.addAll(contentBean.getLists());
                    total = contentBean.getTotal();
                } catch (Exception e) {

                }
                if (appointmentRecordEntityList.size() == 0) {
                    setEmptyView();
                }
                appointmentRecordAdapter.notifyDataSetChanged();
                if (page == 1) {
                    rv_record.refreshComplete();
                } else {
                    rv_record.loadMoreComplete();
                }
                break;
        }
    }
}
