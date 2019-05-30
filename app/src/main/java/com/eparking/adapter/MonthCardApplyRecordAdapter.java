package com.eparking.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eparking.protocol.MonthCardApplyEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/22 15:34
 * @change
 * @chang time
 * @class describe  月卡申请记录
 */
public class MonthCardApplyRecordAdapter extends RecyclerView.Adapter<MonthCardApplyRecordAdapter.PaymentViewHolder> {

    private List<MonthCardApplyEntity.ContentBean> monthCardApplyEntityList;
    private OnItemClickListener onClickListener;
    private Context mContext;

    public MonthCardApplyRecordAdapter(Context mContext, List<MonthCardApplyEntity.ContentBean> monthCardApplyEntityList) {
        this.monthCardApplyEntityList = monthCardApplyEntityList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MonthCardApplyRecordAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eparking_mocardapply_item, parent, false);
        MonthCardApplyRecordAdapter.PaymentViewHolder paymentViewHolder = new MonthCardApplyRecordAdapter.PaymentViewHolder(v);
        paymentViewHolder.onClickListener = onClickListener;
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final MonthCardApplyRecordAdapter.PaymentViewHolder holder, int position) {
        MonthCardApplyEntity.ContentBean monthCardApplyEntity = monthCardApplyEntityList.get(position);
        holder.tv_car_brand.setText(monthCardApplyEntity.getApply_car_plate());
        holder.tv_eparking_place.setText(monthCardApplyEntity.getApply_station_name());
        holder.tv_apply_time.setText(monthCardApplyEntity.getApply_time());
        int status = monthCardApplyEntity.getApply_state();
        if (0 == status) {
            holder.tv_apply_status.setBackgroundResource(R.drawable.shape_applyrecord_model);
            holder.tv_apply_status.setText(mContext.getResources().getString(R.string.monthcard_apply_waiting));
        } else if (1 == status) {
            holder.tv_apply_status.setBackgroundResource(R.drawable.shape_bindstatus_model);
            holder.tv_apply_status.setText(mContext.getResources().getString(R.string.monthcard_apply_pass));
        } else {
            holder.tv_apply_status.setBackgroundResource(R.drawable.shape_stopcar_model);
            holder.tv_apply_status.setText(mContext.getResources().getString(R.string.monthcard_apply_refuse));
        }
    }


    @Override
    public int getItemCount() {
        return monthCardApplyEntityList == null ? 0 : monthCardApplyEntityList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_eparking_place;
        TextView tv_car_brand;
        TextView tv_apply_time;
        TextView tv_apply_status;

        OnItemClickListener onClickListener;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_car_brand = (TextView) itemView.findViewById(R.id.tv_car_brand);
            tv_eparking_place = (TextView) itemView.findViewById(R.id.tv_eparking_place);
            tv_apply_time = (TextView) itemView.findViewById(R.id.tv_apply_time);
            tv_apply_status = (TextView) itemView.findViewById(R.id.tv_apply_status);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}