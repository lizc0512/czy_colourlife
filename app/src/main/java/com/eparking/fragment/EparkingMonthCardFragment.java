package com.eparking.fragment;

import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseFragment;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.activity.EparkingMonthCardDetailsActivity;
import com.eparking.activity.MonthCardApplyActivity;
import com.eparking.adapter.EparkingMonthCardAdapter;
import com.eparking.model.ParkingHomeModel;
import com.eparking.protocol.MonthCardInforEntity;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.SpaceItemDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.eparking.activity.EparkingMonthCardDetailsActivity.MONTHCARDINFOR;

/**
 * @name ${yuansk}
 * @class name：com.eparking.fragment
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/11 15:47
 * @change
 * @chang time
 * @class describe  我的卡包  月卡
 */
public class EparkingMonthCardFragment extends BaseFragment implements NewHttpResponse {

    private SwipeMenuRecyclerView month_card_rv;
    private LinearLayout no_data_layout;
    private TextView tv_nodata_title;
    private Button btn_nodata;
    private int page = 0;
    private EparkingMonthCardAdapter eparkingMonthCardAdapter;
    private List<MonthCardInforEntity.ContentBean> monthCardInforEntityList;
    private ParkingHomeModel parkingHomeModel;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_month_card;
    }

    @Override
    protected void initView(View rootView) {
        month_card_rv = rootView.findViewById(R.id.month_card_rv);
        no_data_layout = rootView.findViewById(R.id.no_data_layout);
        tv_nodata_title = rootView.findViewById(R.id.tv_nodata_title);
        btn_nodata = rootView.findViewById(R.id.btn_nodata);
        month_card_rv.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器。
        month_card_rv.addItemDecoration(new SpaceItemDecoration(Utils.dip2px(getActivity(), 10)));
        month_card_rv.setItemAnimator(new DefaultItemAnimator());
        month_card_rv.useDefaultLoadMore();
        month_card_rv.setLoadMoreListener(new SwipeMenuRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                page++;
            }
        });
        monthCardInforEntityList = new ArrayList<>();
        eparkingMonthCardAdapter = new EparkingMonthCardAdapter(getActivity(), monthCardInforEntityList);
        month_card_rv.setAdapter(eparkingMonthCardAdapter);
        eparkingMonthCardAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Intent intent = new Intent(getActivity(), EparkingMonthCardDetailsActivity.class);
                intent.putExtra(MONTHCARDINFOR, monthCardInforEntityList.get(i));
                startActivity(intent);
            }
        });
        parkingHomeModel = new ParkingHomeModel(getActivity());
    }


    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            parkingHomeModel.getHomeContractList(0, this);
        }
    }


    private void setEmptyView() {
        if (isAdded()) {
            if (monthCardInforEntityList.size() == 0) {
                no_data_layout.setVisibility(View.VISIBLE);
                tv_nodata_title.setText(getResources().getString(R.string.parking_no_monthcard));
                btn_nodata.setText(getResources().getString(R.string.parking_apply_monthcard));
                btn_nodata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MonthCardApplyActivity.class);
                        getActivity().startActivity(intent);
                    }
                });
            } else {
                no_data_layout.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                isFirst = true;
                monthCardInforEntityList.clear();
                try {
                    MonthCardInforEntity monthCardInforEntity = GsonUtils.gsonToBean(result, MonthCardInforEntity.class);
                    monthCardInforEntityList.addAll(monthCardInforEntity.getContent());
                } catch (Exception e) {

                }
                if (monthCardInforEntityList.size() == 0) {
                    setEmptyView();
                }
                eparkingMonthCardAdapter.notifyDataSetChanged();
                break;
            case 1:


                break;
        }
    }
}
