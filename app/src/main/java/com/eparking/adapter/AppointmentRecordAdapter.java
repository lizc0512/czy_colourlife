package com.eparking.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.eparking.protocol.AppointmentRecordEntity;

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
 * @class describe  预约记录的
 */
public class AppointmentRecordAdapter extends RecyclerView.Adapter<AppointmentRecordAdapter.PaymentViewHolder> {

    private List<AppointmentRecordEntity.ContentBean.ListsBean> appointmentRecordEntityList;
    private OnItemClickListener onClickListener;
    private Context mContext;

    public AppointmentRecordAdapter(Context mContext, List<AppointmentRecordEntity.ContentBean.ListsBean> appointmentRecordEntityList) {
        this.appointmentRecordEntityList = appointmentRecordEntityList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public AppointmentRecordAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eparking_appointment_item, parent, false);
        AppointmentRecordAdapter.PaymentViewHolder paymentViewHolder = new AppointmentRecordAdapter.PaymentViewHolder(v);
        paymentViewHolder.onClickListener = onClickListener;
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final AppointmentRecordAdapter.PaymentViewHolder holder, int position) {
        AppointmentRecordEntity.ContentBean.ListsBean listsBean = appointmentRecordEntityList.get(position);
        holder.tv_car_brand.setText(listsBean.getPlate());
        holder.tv_eparking_place.setText(listsBean.getStation_name());
        holder.tv_eparking_address.setText(listsBean.getStation_addr());
        holder.tv_authorization_entertime.setText(listsBean.getStarttime());
        holder.tv_authorization_leavetime.setText(listsBean.getStoptime());
    }


    @Override
    public int getItemCount() {
        return appointmentRecordEntityList == null ? 0 : appointmentRecordEntityList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_car_brand;
        TextView tv_eparking_place;
        TextView tv_eparking_address;
        TextView tv_authorization_entertime;
        TextView tv_authorization_leavetime;


        OnItemClickListener onClickListener;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            tv_car_brand = (TextView) itemView.findViewById(R.id.tv_car_brand);
            tv_eparking_place = (TextView) itemView.findViewById(R.id.tv_eparking_place);
            tv_eparking_address = (TextView) itemView.findViewById(R.id.tv_eparking_address);
            tv_authorization_entertime = (TextView) itemView.findViewById(R.id.tv_authorization_entertime);
            tv_authorization_leavetime = (TextView) itemView.findViewById(R.id.tv_authorization_leavetime);

        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}