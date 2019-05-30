package com.cashier.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.adapter.BeeBaseAdapter;
import com.BeeFramework.model.Constants;
import com.cashier.activity.NewOrderPayActivity;
import com.cashier.activity.OrderListActivity;
import com.cashier.activity.OrderResultAndDetailActivity;
import com.cashier.protocolchang.OrderListEntity;
import com.nohttp.utils.GlideImageLoader;
import com.setting.activity.EditDialog;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.adpter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/23
 * @change
 * @chang time
 * @class describe  订单列表的dapter
 */
public class OrderListAdapter extends BeeBaseAdapter {

    private ArrayList<OrderListEntity.ContentBean> orderList;
    private LayoutInflater mInflater;


    public OrderListAdapter(Context c, List dataList) {
        super(c, dataList);
        mInflater = LayoutInflater.from(c);
        orderList = (ArrayList<OrderListEntity.ContentBean>) dataList;
    }

    public class ViewHolder extends BeeCellHolder {
        public ImageView iv_business_logo;//
        public ImageView iv_payment;//
        public TextView tv_business_name;//
        public TextView tv_order_staus;//
        public TextView order_amount;//
        public TextView tv_order_time;//
        public TextView tv_order_sn;//
        public TextView tv_go_pay;//
        public TextView tv_cancel_order;//
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.adapter_order_itemview, null);
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        OrderListAdapter.ViewHolder holder = new OrderListAdapter.ViewHolder();
        holder.iv_payment = (ImageView) cellView.findViewById(R.id.iv_payment);
        holder.iv_business_logo = (ImageView) cellView.findViewById(R.id.iv_business_logo);
        holder.tv_business_name = (TextView) cellView.findViewById(R.id.tv_business_name);
        holder.tv_order_staus = (TextView) cellView.findViewById(R.id.tv_order_staus);
        holder.order_amount = (TextView) cellView.findViewById(R.id.order_amount);
        holder.tv_order_time = (TextView) cellView.findViewById(R.id.tv_order_time);
        holder.tv_order_sn = cellView.findViewById(R.id.tv_order_sn);
        holder.tv_go_pay = (TextView) cellView.findViewById(R.id.tv_go_pay);
        holder.tv_cancel_order = (TextView) cellView.findViewById(R.id.tv_cancel_order);
        return holder;
    }

    public void setData(List dataList) {
        this.orderList = (ArrayList<OrderListEntity.ContentBean>) dataList;
        notifyDataSetChanged();
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        OrderListAdapter.ViewHolder holder = (OrderListAdapter.ViewHolder) h;
        OrderListEntity.ContentBean contentBean = orderList.get(position);
        holder.tv_business_name.setText(contentBean.getBusiness_name());
        holder.order_amount.setText(contentBean.getTotal_fee());
        final String colorSn = contentBean.getColour_sn();
        holder.tv_order_sn.setText(contentBean.getOrder_detail());
        holder.tv_order_time.setText(TimeUtil.getDateToString(contentBean.getTime_create()));
        int payment = contentBean.getPayment_type();//支付的方式
        final int orderStatus = contentBean.getTrade_state();
        if (payment == 1) {
            holder.iv_payment.setImageResource(R.drawable.icon_list_cashier);
        } else {
            holder.iv_payment.setImageResource(R.drawable.icon_list_meal);
        }
        String tradeName = contentBean.getTrade_state_name();
        holder.tv_order_staus.setText(tradeName);
        if (orderStatus == 1) {
            holder.tv_cancel_order.setText(cellView.getResources().getString(R.string.cashier_cancel_order));
            holder.tv_go_pay.setVisibility(View.VISIBLE);
            holder.tv_cancel_order.setVisibility(View.VISIBLE);
        } else if (orderStatus == 2) {
            holder.tv_cancel_order.setText(cellView.getResources().getString(R.string.notification_see_details));
            holder.tv_go_pay.setVisibility(View.GONE);
            holder.tv_cancel_order.setVisibility(View.VISIBLE);
        } else if (orderStatus == 3) {
            holder.tv_cancel_order.setText(cellView.getResources().getString(R.string.notification_see_details));
            holder.tv_go_pay.setVisibility(View.GONE);
            holder.tv_cancel_order.setVisibility(View.VISIBLE);
        } else if (orderStatus == 4) {
            holder.tv_go_pay.setVisibility(View.GONE);
            holder.tv_cancel_order.setText(cellView.getResources().getString(R.string.notification_see_details));
            holder.tv_cancel_order.setVisibility(View.VISIBLE);
        }
        holder.tv_go_pay.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, NewOrderPayActivity.class);
                Constants.isFromHtml = false;
                intent.putExtra("ORDER_SN", colorSn);
                mContext.startActivity(intent);
            }
        });
        holder.tv_cancel_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (orderStatus != 1) {
                    Intent intent = new Intent(mContext, OrderResultAndDetailActivity.class);
                    intent.putExtra("ORDER_SN", colorSn);
                    mContext.startActivity(intent);
                } else {
                    if (noticeDialog == null) {
                        noticeDialog = new EditDialog(mContext);
                    }
                    noticeDialog.setContent("你确定取消该订单吗?");
                    noticeDialog.show();
                    noticeDialog.left_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (noticeDialog != null) {
                                noticeDialog.dismiss();
                            }
                        }
                    });
                    noticeDialog.right_button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (noticeDialog != null) {
                                noticeDialog.dismiss();
                            }
                            ((OrderListActivity) mContext).cancelOrder(colorSn);
                        }
                    });
                }

            }
        });
        GlideImageLoader.loadImageDefaultDisplay(cellView.getContext(), contentBean.getBusiness_logo(), holder.iv_business_logo, R.drawable.icon_business_logo, R.drawable.icon_business_logo);
        return cellView;
    }

    private EditDialog noticeDialog;
}
