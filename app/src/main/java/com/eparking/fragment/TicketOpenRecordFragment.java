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
import com.eparking.adapter.TicketOpenRecordAdapter;
import com.eparking.model.ParkingOrderModel;
import com.eparking.protocol.OpenTicketEntity;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.SpaceItemDecoration;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.eparking.activity.CarsLicenseUploadActivity.FROMSOURCE;

/**
 * @name ${yuansk}
 * @class name：com.eparking.fragment
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 17:33
 * @change
 * @chang time
 * @class describe  历史记录 ---开票记录
 */
public class TicketOpenRecordFragment extends BaseFragment implements NewHttpResponse {
    private XRecyclerView rv_record;
    private LinearLayout no_data_layout;
    private TextView tv_nodata_title;
    private TicketOpenRecordAdapter ticketOpenRecordAdapter;
    private int page = 1;
    private int pageSize = 20;
    private ParkingOrderModel parkingOrderModel;

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
        rv_record.addItemDecoration(new SpaceItemDecoration(Utils.dip2px(getActivity(), 10)));
        rv_record.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        rv_record.setLoadingMoreEnabled(true);
        rv_record.setPullRefreshEnabled(true);
        rv_record.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {
                page = 1;
                getOpenTicketData();
            }

            @Override
            public void onLoadMore() {
                if (listsBeanList.size() >= total) {
                    ToastUtil.toastShow(getActivity(), getActivity().getResources().getString(R.string.parking_no_moreticket));
                } else {
                    page++;
                    getOpenTicketData();
                }
            }
        });

        ticketOpenRecordAdapter = new TicketOpenRecordAdapter(getActivity(), listsBeanList);
        rv_record.setAdapter(ticketOpenRecordAdapter);

        ticketOpenRecordAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                OpenTicketEntity.ContentBean.ListsBean paymentRecordEntity = listsBeanList.get(i - 1);
                Intent intent = new Intent(getActivity(), ParkingOpenTicketDetailsActivity.class);
                intent.putExtra(ParkingOpenTicketDetailsActivity.OPENTICKETDETAIL, paymentRecordEntity);
                intent.putExtra(FROMSOURCE, 0);
                getActivity().startActivity(intent);
            }
        });
        parkingOrderModel = new ParkingOrderModel(getActivity());
        data = System.currentTimeMillis() / 1000;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            data = System.currentTimeMillis() / 1000;
            rv_record.refresh();
        }
    }

    public void refreshFilterList(boolean isRefresh) {
        isFirst = isRefresh;
    }

    public void refreshDataList(long data) {
        this.data = data;
        rv_record.refresh();
    }

    private long data;

    private void getOpenTicketData() {
        parkingOrderModel.getInvoiceList(0, data, page, pageSize, this);
    }

    private void setEmptyView() {
        if (isAdded()) {
            no_data_layout.setVisibility(View.VISIBLE);
            tv_nodata_title.setText(getResources().getString(R.string.parking_no_ticketrecord));
        }
    }

    private List<OpenTicketEntity.ContentBean.ListsBean> listsBeanList = new ArrayList<>();
    private int total = 0;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                isFirst = true;
                if (page == 1) {
                    listsBeanList.clear();
                }
                try {
                    OpenTicketEntity openTicketEntity = GsonUtils.gsonToBean(result, OpenTicketEntity.class);
                    OpenTicketEntity.ContentBean contentBean = openTicketEntity.getContent();
                    listsBeanList.addAll(contentBean.getLists());
                    total = contentBean.getTotal();
                } catch (Exception e) {

                }
                if (listsBeanList.size() == 0) {
                    setEmptyView();
                } else {
                    no_data_layout.setVisibility(View.GONE);
                }
                if (page == 1) {
                    rv_record.refreshComplete();
                } else {
                    rv_record.loadMoreComplete();
                }
                ticketOpenRecordAdapter.notifyDataSetChanged();
                break;
        }
    }
}
