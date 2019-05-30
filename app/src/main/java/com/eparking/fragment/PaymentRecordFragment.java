package com.eparking.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseFragment;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.activity.ParkingOpenTicketDetailsActivity;
import com.eparking.activity.PaymentTempDetailsActivity;
import com.eparking.adapter.PaymentRecordAdapter;
import com.eparking.model.ParkingOrderModel;
import com.eparking.protocol.PaymentRecordEntity;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.cashier.activity.NewOrderPayActivity.ORDER_SN;
import static com.eparking.activity.CarsLicenseUploadActivity.FROMSOURCE;
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
 * @class describe  历史记录 ---缴费记录
 */
public class PaymentRecordFragment extends BaseFragment implements NewHttpResponse {
    private XRecyclerView rv_record;
    private LinearLayout no_data_layout;
    private TextView tv_nodata_title;
    private PaymentRecordAdapter paymentRecordAdapter;
    private List<PaymentRecordEntity.ContentBean.ListsBean> paymentRecordEntityList = new ArrayList<>();
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
                getPaymentRecord();
            }

            @Override
            public void onLoadMore() {
                if (total >= paymentRecordEntityList.size()) {
                    rv_record.loadMoreComplete();
                    ToastUtil.toastShow(getActivity(), getActivity().getResources().getString(R.string.parking_no_morepayment));
                } else {
                    page++;
                    getPaymentRecord();
                }
            }
        });

        paymentRecordAdapter = new PaymentRecordAdapter(getActivity(), paymentRecordEntityList);
        rv_record.setAdapter(paymentRecordAdapter);

        paymentRecordAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                PaymentRecordEntity.ContentBean.ListsBean listsBean = paymentRecordEntityList.get(i - 1);
                String openTicketStatus = listsBean.getInvoice();
                Intent intent = null;
                if ("Y".equals(openTicketStatus)) {
                    intent = new Intent(getActivity(), ParkingOpenTicketDetailsActivity.class);
                    intent.putExtra(ORDER_SN, listsBean.getTnum());
                    intent.putExtra(ParkingOpenTicketDetailsActivity.STATIONNAME,listsBean.getStation_name());
                    intent.putExtra(ParkingOpenTicketDetailsActivity.PLATE,listsBean.getPlate());
                    intent.putExtra(ParkingOpenTicketDetailsActivity.KPAMOUNT,listsBean.getAmount());
                    intent.putExtra(FROMSOURCE, 1);
                } else {
                    intent = new Intent(getActivity(), PaymentTempDetailsActivity.class);
                    intent.putExtra(PAYMENTINFOR, listsBean);
                }
                getActivity().startActivity(intent);
            }
        });
        parkingOrderModel = new ParkingOrderModel(getActivity());
        data = System.currentTimeMillis() / 1000;
        carId = getArguments().getString(CARID, "");
    }


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            rv_record.refresh();
        }
    }

    private String carId = "";
    private String stationId = "";
    private String carType = "";
    private long data;

    public void refreshFilterList(String carId, String stationId, int type) {
        this.carId = carId;
        this.stationId = stationId;
        if (type == 0) {
            carType = "MONTH";
        } else if (type == 1) {
            carType = "TEMP";
        } else {
            carType = "";
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

    private void getPaymentRecord() {
        parkingOrderModel.getHistoryOrderList(0, carId, stationId, carType, data, page, pageSize, PaymentRecordFragment.this);
    }

    private void setEmptyView() {
        if (isAdded()) {
            no_data_layout.setVisibility(View.VISIBLE);
            tv_nodata_title.setText(getResources().getString(R.string.parking_no_paymentrecord));
        }
    }

    private int total;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                isFirst = true;
                if (page == 1) {
                    paymentRecordEntityList.clear();
                }
                try {
                    PaymentRecordEntity paymentRecordEntity = GsonUtils.gsonToBean(result, PaymentRecordEntity.class);
                    PaymentRecordEntity.ContentBean contentBean = paymentRecordEntity.getContent();
                    total = contentBean.getTotal();
                    paymentRecordEntityList.addAll(contentBean.getLists());
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
                paymentRecordAdapter.notifyDataSetChanged();
                break;
        }
    }
}
