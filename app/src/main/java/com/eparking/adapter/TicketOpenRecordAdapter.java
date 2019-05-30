package com.eparking.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eparking.protocol.OpenTicketEntity;
import com.eparking.protocol.PaymentRecordEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/15 17:47
 * @change
 * @chang time
 * @class describe
 * 历史记录 --- 开票记录
 */
public class TicketOpenRecordAdapter extends RecyclerView.Adapter<TicketOpenRecordAdapter.PaymentViewHolder> {

    private List<OpenTicketEntity.ContentBean.ListsBean> paymentRecordEntityList;
    private OnItemClickListener onClickListener;
    private Context mContext;

    public TicketOpenRecordAdapter(Context mContext, List<OpenTicketEntity.ContentBean.ListsBean> paymentRecordEntityList) {
        this.paymentRecordEntityList = paymentRecordEntityList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public TicketOpenRecordAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_openticket_item, parent, false);
        PaymentViewHolder paymentViewHolder = new PaymentViewHolder(v);
        paymentViewHolder.onClickListener = onClickListener;
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final TicketOpenRecordAdapter.PaymentViewHolder holder, int position) {
        OpenTicketEntity.ContentBean.ListsBean paymentRecordEntity = paymentRecordEntityList.get(position);
        holder.tv_openticket_address.setText(paymentRecordEntity.getStation_name());
        holder.tv_openticket_amount.setText("￥" + paymentRecordEntity.getKp_amount());
        holder.tv_car_brand.setText(paymentRecordEntity.getPlate());
        holder.tv_openticket_date.setText(paymentRecordEntity.getKprq());
    }

    @Override
    public int getItemCount() {
        return paymentRecordEntityList == null ? 0 : paymentRecordEntityList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_openticket_address;
        TextView tv_openticket_amount;
        TextView tv_car_brand;
        TextView tv_openticket_date;

        OnItemClickListener onClickListener;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_openticket_address = (TextView) itemView.findViewById(R.id.tv_openticket_address);
            tv_openticket_amount = (TextView) itemView.findViewById(R.id.tv_openticket_amount);
            tv_car_brand = (TextView) itemView.findViewById(R.id.tv_car_brand);
            tv_openticket_date = (TextView) itemView.findViewById(R.id.tv_openticket_date);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
