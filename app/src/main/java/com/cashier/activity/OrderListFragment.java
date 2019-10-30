package com.cashier.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseFragment;
import com.BeeFramework.model.NewHttpResponse;
import com.cashier.adapter.OrderListAdapter;
import com.cashier.modelnew.NewOrderPayModel;
import com.cashier.protocolchang.OrderListEntity;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.net.cyberway.R;

import static com.cashier.activity.NewOrderPayActivity.ORDER_SN;

/**
 * @name ${yuansk}
 * @class name：com.cashier.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/22 10:27
 * @change
 * @chang time
 * @class describe
 */

public class OrderListFragment extends BaseFragment implements NewHttpResponse {

    public static final String ORDERSTATUS = "orderstatus";
    public static final String STARTTIME = "startTime";
    private XListView rv_orderlist;
    private LinearLayout empty_layout;
    private String orderStatus;//订单状态
    private String startTime;//当前的月份
    private int page = 1;
    private int pagesize = 10;
    private NewOrderPayModel newOrderPayModel;
    private List<OrderListEntity.ContentBean> orderList = new ArrayList<>();
    private OrderListAdapter orderListAdapter;

    public static OrderListFragment newInstance(String orderStatus, String startTime) {
        Bundle args = new Bundle();
        args.putString(ORDERSTATUS, orderStatus);
        args.putString(STARTTIME, startTime);
        OrderListFragment orderListFragment = new OrderListFragment();
        orderListFragment.setArguments(args);
        return orderListFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        orderStatus = bundle.getString(ORDERSTATUS);
        startTime = bundle.getString(STARTTIME);
        newOrderPayModel = new NewOrderPayModel(getActivity());
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_orderlist;
    }

    @Override
    protected void initView(View view) {
        rv_orderlist = (XListView) view.findViewById(R.id.lv_orderlist);
        empty_layout = (LinearLayout) view.findViewById(R.id.empty_layout);
        rv_orderlist.setPullRefreshEnable(true);
        rv_orderlist.setPullLoadEnable(true);
        rv_orderlist.loadMoreHide();
        rv_orderlist.setAdapter(null);
        rv_orderlist.setXListViewListener(new IXListViewListener() {
            @Override
            public void onRefresh(int id) {
                //刷新
                page = 1;
                newOrderPayModel.getPayOrderList(0, orderStatus, startTime, page, pagesize, OrderListFragment.this);
            }

            @Override
            public void onLoadMore(int id) {
                //加载更多
                page++;
                newOrderPayModel.getPayOrderList(0, orderStatus, startTime, page, pagesize, OrderListFragment.this);
            }
        }, 0);
        rv_orderlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 1) {
                    Intent intent = new Intent(getActivity(), OrderResultAndDetailActivity.class);
                    intent.putExtra(ORDER_SN, orderList.get(position - 1).getColour_sn());
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            rv_orderlist.startHeaderRefresh();
        }
    }

    //重新筛选月份后当前的fragment直接刷新
    public void resetMonthRefreshData(String startTime) {
        this.startTime = startTime;
        if (null != rv_orderlist) {
            rv_orderlist.startHeaderRefresh();
        }
    }

    /***筛选时间其他的fragment再可见时在刷新**/
    public void setNeedRefresh(String startTime, boolean needRefresh) {
        this.startTime = startTime;
        this.isFirst = needRefresh;
    }

    /***取消订单其他的fragment再可见时在刷新**/
    public void setNeedRefresh(boolean needRefresh) {
        this.isFirst = needRefresh;
    }


    /**
     * 取消订单当前的立即刷新
     **/
    public void cancelRefreshList() {
        rv_orderlist.startHeaderRefresh();
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                isFirst = true;
                if (!TextUtils.isEmpty(result)) {
                    BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                    if (baseContentEntity.getCode() == 0) {
                        if (page == 1) {
                            //清空数据源
                            orderList.clear();
                            rv_orderlist.stopRefresh();
                        } else {
                            rv_orderlist.stopLoadMore();
                        }
                        try {
                            OrderListEntity orderListEntity = GsonUtils.gsonToBean(result, OrderListEntity.class);
                            List<OrderListEntity.ContentBean> contentBeanList = orderListEntity.getContent();
                            if (page == 1 && contentBeanList.isEmpty()) {
                                ToastUtil.toastShow(getActivity(), "暂无更多订单记录");
                            }
                            orderList.addAll(contentBeanList);
                            if (orderListAdapter == null) {
                                orderListAdapter = new OrderListAdapter(getActivity(), orderList);
                                rv_orderlist.setAdapter(orderListAdapter);
                            } else {
                                orderListAdapter.setData(orderList);
                            }
                        } catch (Exception e) {

                        }
                    } else {
                        ToastUtil.toastShow(getActivity(), baseContentEntity.getMessage());
                    }
                } else {
                    if (page == 1) {
                        rv_orderlist.stopRefresh();
                    } else {
                        rv_orderlist.stopLoadMore();
                    }
                }
                if (orderList.size() == 0) {  //显示暂无订单列表
                    empty_layout.setVisibility(View.VISIBLE);
                } else {
                    empty_layout.setVisibility(View.GONE);
                }
                break;
        }
    }

}
