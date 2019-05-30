package com.eparking.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.adapter.CarsAuthorizeRecordAdapter;
import com.eparking.helper.OptionsPickerInterface;
import com.eparking.helper.OptionsPickerViewUtils;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingOperationModel;
import com.eparking.model.ParkingOrderModel;
import com.eparking.protocol.AuthorizeRecordEntity;
import com.eparking.protocol.CarStationListEntity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

import static com.eparking.activity.CarsLicenseUploadActivity.CARNUMBER;
import static com.eparking.helper.ConstantKey.EPARKINGCARSTATIONLIST;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  车辆授权
 */
public class CarsAuthorizeRecordActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private TextView user_top_view_right;//筛选
    private XRecyclerView rv_record;
    private LinearLayout empty_layout;
    private TextView empty_title;
    private CarsAuthorizeRecordAdapter carsAuthorizeRecordAdapter;
    private List<AuthorizeRecordEntity.ContentBean.ListsBean> authorizeRecordEntityList = new ArrayList<>();
    private ParkingOrderModel parkingOrderModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_cardrecord);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_authorize_record));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setTextColor(Color.parseColor("#27a2f0"));
        user_top_view_right.setText(getResources().getString(R.string.parking_filter));
        user_top_view_right.setOnClickListener(this);
        rv_record = findViewById(R.id.rv_record);
        empty_layout = findViewById(R.id.empty_layout);
        empty_title = findViewById(R.id.empty_title);
        imageView_back.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(CarsAuthorizeRecordActivity.this);
        rv_record.setLayoutManager(linearLayoutManager);
        rv_record.setItemAnimator(new DefaultItemAnimator());
        rv_record.addItemDecoration(new SpaceItemDecoration(Utils.dip2px(CarsAuthorizeRecordActivity.this, 10)));
        rv_record.setLoadingMoreEnabled(true);
        rv_record.setPullRefreshEnabled(true);
        carsAuthorizeRecordAdapter = new CarsAuthorizeRecordAdapter(CarsAuthorizeRecordActivity.this, authorizeRecordEntityList);
        rv_record.setAdapter(carsAuthorizeRecordAdapter);
        rv_record.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                page = 1;
                getCarAuthorizeRecord();
            }

            @Override
            public void onLoadMore() {

                if (total > authorizeRecordEntityList.size()) {
                    page++;
                    getCarAuthorizeRecord();
                } else {
                    rv_record.loadMoreComplete();
                }

            }
        });
        Intent intent = getIntent();
        plate = intent.getStringExtra(CARNUMBER);
        parkingOrderModel = new ParkingOrderModel(CarsAuthorizeRecordActivity.this);
        String carStationCache = shared.getString(EPARKINGCARSTATIONLIST, "");
        if (!TextUtils.isEmpty(carStationCache)) {
            try {
                carStationList.clear();
                CarStationListEntity carStationListEntity = GsonUtils.gsonToBean(carStationCache, CarStationListEntity.class);
                carStationList.addAll(carStationListEntity.getContent());
            } catch (Exception e) {

            }
        }
        if (TextUtils.isEmpty(plate)) {
            if (carStationList.size() == 0) {
                setEmptyView();
            } else {
                plate = carStationList.get(0).getPlate();
                rv_record.refresh();
            }
        } else {
            rv_record.refresh();
        }
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private void getCarAuthorizeRecord() {
        parkingOrderModel.getCarOrderList(0, plate, page, pagesize, this);
    }

    private List<CarStationListEntity.ContentBean> carStationList = new ArrayList<>();
    private String plate;
    private int page = 1;
    private int pagesize = 20;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                OptionsPickerViewUtils.showPickerView(CarsAuthorizeRecordActivity.this, R.string.car_brand, 0, carStationList, new OptionsPickerInterface() {
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

    private void setEmptyView() {
        if (authorizeRecordEntityList.size() == 0) {
            empty_layout.setVisibility(View.VISIBLE);
            empty_title.setText(getResources().getString(R.string.parking_no_authorizerecord));
        } else {
            empty_layout.setVisibility(View.GONE);
        }
    }

    public void setAuthorizeOperation(int positon) {
        ParkingOperationModel parkingOperationModel = new ParkingOperationModel(this);
        AuthorizeRecordEntity.ContentBean.ListsBean listsBean = authorizeRecordEntityList.get(positon);
        parkingOperationModel.carUnauthorizeOpenation(1, listsBean.getPlate(), listsBean.getUser_to_phone(), this);

    }

    private int total;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    if (page == 1) {
                        authorizeRecordEntityList.clear();
                    }
                    AuthorizeRecordEntity authorizeRecordEntity = GsonUtils.gsonToBean(result, AuthorizeRecordEntity.class);
                    AuthorizeRecordEntity.ContentBean contentBean = authorizeRecordEntity.getContent();
                    total = contentBean.getTotal();
                    authorizeRecordEntityList.addAll(contentBean.getLists());
                } catch (Exception e) {

                }
                if (authorizeRecordEntityList.size() == 0) {
                    setEmptyView();
                }
                if (page == 1) {
                    rv_record.refreshComplete();
                } else {
                    rv_record.loadMoreComplete();
                }
                carsAuthorizeRecordAdapter.notifyDataSetChanged();
                break;
            case 1:
                ToastUtil.toastShow(CarsAuthorizeRecordActivity.this, getResources().getString(R.string.cancel_authorize_success));
                getCarAuthorizeRecord();
                break;
        }
    }
}
