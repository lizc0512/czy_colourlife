package com.eparking.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseFragment;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.activity.ParkingTempDetailsActivity;
import com.eparking.adapter.ParkingRecordAdapter;
import com.eparking.helper.ConstantKey;
import com.eparking.model.ParkingOrderModel;
import com.eparking.protocol.HistoryCarBrand;
import com.eparking.protocol.StopStationRecordEntity;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.SpaceItemDecoration;
import com.user.UserAppConst;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.eparking.activity.EParkingHistoryRecordActivity.CARID;
import static com.eparking.activity.PaymentTempDetailsActivity.PAYMENTINFOR;


/**
 * @name ${yuansk}
 * @class name：com.eparking.fragment
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 17:33
 * @change
 * @chang time
 * @class describe  历史记录--停车记录
 */
public class ParkingRecordFragment extends BaseFragment implements NewHttpResponse {
    private XRecyclerView rv_record;
    private LinearLayout no_data_layout;
    private TextView tv_nodata_title;
    private ParkingRecordAdapter parkingRecordAdapter;
    private List<StopStationRecordEntity.ContentBean> paymentRecordEntityList = new ArrayList<>();
    private ParkingOrderModel parkingOrderModel;
    private int page = 1;
    private int pageSize = 20;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment__allkinds_historry;
    }

    @Override
    protected void initView(View rootView) {
        no_data_layout = rootView.findViewById(R.id.no_data_layout);
        tv_nodata_title = rootView.findViewById(R.id.tv_nodata_title);
        rv_record = rootView.findViewById(R.id.rv_record);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rv_record.setLayoutManager(linearLayoutManager);
        rv_record.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rv_record.addItemDecoration(new SpaceItemDecoration(Utils.dip2px(getActivity(), 10)));
        rv_record.setLoadingMoreEnabled(true);
        rv_record.setPullRefreshEnabled(true);
        rv_record.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                page = 1;
                getStopCarRecord();
            }

            @Override
            public void onLoadMore() {
                page++;
                getStopCarRecord();
            }
        });
        parkingRecordAdapter = new ParkingRecordAdapter(getActivity(), paymentRecordEntityList);
        rv_record.setAdapter(parkingRecordAdapter);
        parkingRecordAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                StopStationRecordEntity.ContentBean contentBean = paymentRecordEntityList.get(i - 1);
                Intent intent = new Intent(getActivity(), ParkingTempDetailsActivity.class);
                intent.putExtra(PAYMENTINFOR, contentBean);
                getActivity().startActivity(intent);
            }
        });
        SharedPreferences shared = getActivity().getSharedPreferences(UserAppConst.USERINFO, 0);
        String historyCar = shared.getString(ConstantKey.EPARKINGCARHISTORYBRAND, "");
        carId = getArguments().getString(CARID, "");
        if (TextUtils.isEmpty(carId)) {
            if (!TextUtils.isEmpty(historyCar)) {
                try {
                    HistoryCarBrand historyCarBrand = GsonUtils.gsonToBean(historyCar, HistoryCarBrand.class);
                    List<HistoryCarBrand.ContentBean> carBrandList = historyCarBrand.getContent();
                    if (carBrandList.size() > 0) {
                        carId = carBrandList.get(0).getCar();
                    }
                } catch (Exception e) {

                }
            }
        }
        parkingOrderModel = new ParkingOrderModel(getActivity());
        data = System.currentTimeMillis() / 1000;
    }


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            rv_record.refresh();
        }
    }

    private String carId = "";
    private String stationId = "";
    private String isMonth = "";
    private long data;

    public void refreshFilterList(String carId, String stationId, int type) {
        this.carId = carId;
        this.stationId = stationId;
        if (type == 0) {
            isMonth = "Y";
        } else if (type == 1) {
            isMonth = "N";
        } else {
            isMonth = "";
        }
        if (!TextUtils.isEmpty(carId)) {

        }
        rv_record.refresh();
    }

    public void refreshFilterList(boolean isRefresh) {
        isFirst = isRefresh;
    }

    public void refreshDataList(long data) {
        this.data = data;
        rv_record.refresh();
    }

    private void getStopCarRecord() {
        parkingOrderModel.getHistoryStationList(0, carId, stationId, isMonth, data, page, pageSize, ParkingRecordFragment.this);
    }

    private void setEmptyView() {
        if (isAdded()) {
            no_data_layout.setVisibility(View.VISIBLE);
            tv_nodata_title.setText(getResources().getString(R.string.parking_no_stoprecord));
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                isFirst = true;
                if (page == 1) {
                    paymentRecordEntityList.clear();
                }
                try {
                    StopStationRecordEntity stopStationRecordEntity = GsonUtils.gsonToBean(result, StopStationRecordEntity.class);
                    paymentRecordEntityList.addAll(stopStationRecordEntity.getContent());
                } catch (Exception e) {

                }
                if (paymentRecordEntityList.size() == 0) {
                    setEmptyView();
                } else {
                    no_data_layout.setVisibility(View.GONE);
                }
                if (page == 1) {
                    rv_record.refreshComplete();
                } else {
                    rv_record.loadMoreComplete();
                }
                parkingRecordAdapter.notifyDataSetChanged();
                break;
        }
    }
}
