package com.eparking.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.eparking.activity.ApplyOpenTicketActivity;
import com.eparking.activity.ParkingOpenTicketDetailsActivity;
import com.eparking.protocol.PaymentRecordEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.cashier.activity.NewOrderPayActivity.ORDER_SN;
import static com.eparking.activity.CarsLicenseUploadActivity.FROMSOURCE;
import static com.eparking.activity.ParkingOpenTicketDetailsActivity.OPENTICKETDETAIL;

/**
 * @name ${yuansk}
 * @class name：com.eparking.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/15 17:47
 * @change
 * @chang time
 * @class describe
 * 历史记录 --- 缴费记录
 */
public class PaymentRecordAdapter extends RecyclerView.Adapter<PaymentRecordAdapter.PaymentViewHolder> {

    private List<PaymentRecordEntity.ContentBean.ListsBean> paymentRecordEntityList;
    private OnItemClickListener onClickListener;
    private Context mContext;

    public PaymentRecordAdapter(Context mContext, List<PaymentRecordEntity.ContentBean.ListsBean> paymentRecordEntityList) {
        this.paymentRecordEntityList = paymentRecordEntityList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public PaymentRecordAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_payment_item, parent, false);
        PaymentViewHolder serviceViewHolder = new PaymentViewHolder(v);
        serviceViewHolder.onClickListener = onClickListener;
        return serviceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final PaymentRecordAdapter.PaymentViewHolder holder, int position) {
        final PaymentRecordEntity.ContentBean.ListsBean listsBean = paymentRecordEntityList.get(position);
        holder.tv_eparking_address.setText(listsBean.getStation_name());
        holder.tv_payment_amount.setText("￥" + listsBean.getAmount());
        holder.tv_car_brand.setText(listsBean.getPlate());
        String type = listsBean.getType();
        if ("MONTH".equals(type)) {
            holder.tv_payment_date.setText(TimeUtil.formatTime(listsBean.getArrival(), "yyyy.MM.dd") + "-" + TimeUtil.formatTime(listsBean.getDeparture(), "yyyy.MM.dd"));
            holder.tv_payment_type.setText(mContext.getResources().getString(R.string.monthcard));
        } else {
            holder.tv_payment_date.setText(TimeUtil.formatTime(listsBean.getPaytime(), "yyyy.MM.dd"));
            holder.tv_payment_type.setText(TimeUtil.dateDiff(listsBean.getDuration()));
        }
        final String openTicketStatus = listsBean.getInvoice();
        if ("Y".equals(openTicketStatus)) {
            holder.tv_ticket_status.setText(mContext.getResources().getString(R.string.payment_opened_ticket));
            holder.tv_ticket_status.setTextColor(mContext.getResources().getColor(R.color.color_626b77));
            holder.iv_right_arrow.setVisibility(View.INVISIBLE);
        } else {
            holder.tv_ticket_status.setText(mContext.getResources().getString(R.string.payment_opening_ticket));
            holder.tv_ticket_status.setTextColor(mContext.getResources().getColor(R.color.color_333b46));
            holder.iv_right_arrow.setVisibility(View.VISIBLE);
        }
        holder.open_ticket_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ("Y".equals(openTicketStatus)) {
                    //发票详情
                    Intent intent = new Intent(mContext, ParkingOpenTicketDetailsActivity.class);
                    intent.putExtra(ORDER_SN, listsBean.getTnum());
                    intent.putExtra(ParkingOpenTicketDetailsActivity.STATIONNAME,listsBean.getStation_name());
                    intent.putExtra(ParkingOpenTicketDetailsActivity.PLATE,listsBean.getPlate());
                    intent.putExtra(ParkingOpenTicketDetailsActivity.KPAMOUNT,listsBean.getAmount());
                    intent.putExtra(FROMSOURCE, 1);
                    mContext.startActivity(intent);
                } else {
                    //去开发票
                    Intent intent = new Intent(mContext, ApplyOpenTicketActivity.class);
                    intent.putExtra(ORDER_SN, listsBean.getTnum());
                    mContext.startActivity(intent);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return paymentRecordEntityList == null ? 0 : paymentRecordEntityList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_eparking_address;
        TextView tv_payment_amount;
        TextView tv_car_brand;
        TextView tv_payment_date;
        RelativeLayout open_ticket_layout;
        TextView tv_payment_type;
        TextView tv_ticket_status;
        ImageView iv_right_arrow;

        OnItemClickListener onClickListener;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_eparking_address = (TextView) itemView.findViewById(R.id.tv_eparking_address);
            tv_payment_amount = (TextView) itemView.findViewById(R.id.tv_payment_amount);
            tv_car_brand = (TextView) itemView.findViewById(R.id.tv_car_brand);
            tv_payment_date = (TextView) itemView.findViewById(R.id.tv_payment_date);
            open_ticket_layout = (RelativeLayout) itemView.findViewById(R.id.open_ticket_layout);
            tv_payment_type = (TextView) itemView.findViewById(R.id.tv_payment_type);
            tv_ticket_status = (TextView) itemView.findViewById(R.id.tv_ticket_status);
            iv_right_arrow = (ImageView) itemView.findViewById(R.id.iv_right_arrow);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
