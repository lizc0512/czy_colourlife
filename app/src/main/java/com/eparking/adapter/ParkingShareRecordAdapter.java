package com.eparking.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eparking.protocol.ParkingShareRecordEntity;

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
 * @class describe  车位共享记录的
 */
public class ParkingShareRecordAdapter extends RecyclerView.Adapter<ParkingShareRecordAdapter.PaymentViewHolder> {

    private List<ParkingShareRecordEntity.ContentBean.ListsBean> parkingShareRecordEntityList;
    private OnItemClickListener onClickListener;
    private Context mContext;

    public ParkingShareRecordAdapter(Context mContext, List<ParkingShareRecordEntity.ContentBean.ListsBean> parkingShareRecordEntityList) {
        this.parkingShareRecordEntityList = parkingShareRecordEntityList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ParkingShareRecordAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eparking_sharerecord_item, parent, false);
        PaymentViewHolder paymentViewHolder = new PaymentViewHolder(v);
        paymentViewHolder.onClickListener = onClickListener;
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ParkingShareRecordAdapter.PaymentViewHolder holder, int position) {
        ParkingShareRecordEntity.ContentBean.ListsBean listsBean = parkingShareRecordEntityList.get(position);
        holder.tv_parking_name.setText(listsBean.getStation_name());
        holder.tv_eparking_address.setText(listsBean.getLock_name());
        holder.tv_share_number.setText(listsBean.getPlate());
        holder.tv_lock_number.setText(listsBean.getEtcode());
        holder.tv_share_time.setText(listsBean.getTime_begin());
        holder.tv_leave_time.setText(listsBean.getTime_end());
        String status = listsBean.getStatus();
        if ("Y".equalsIgnoreCase(status)) {
            holder.tv_parking_status.setVisibility(View.VISIBLE);
            holder.parking_user_layout.setVisibility(View.VISIBLE);
            holder.tv_parking_user.setText(listsBean.getLock_user_name());
            holder.tv_parking_status.setText(mContext.getResources().getString(R.string.parking_shar_now));
        } else {
            holder.tv_parking_status.setVisibility(View.INVISIBLE);
            holder.parking_user_layout.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return parkingShareRecordEntityList == null ? 0 : parkingShareRecordEntityList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_parking_name;
        TextView tv_eparking_address;
        TextView tv_parking_status;
        LinearLayout parking_user_layout;
        TextView tv_parking_user;
        TextView tv_share_number;
        TextView tv_lock_number;
        TextView tv_share_time;
        TextView tv_leave_time;
        OnItemClickListener onClickListener;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_parking_name = (TextView) itemView.findViewById(R.id.tv_parking_name);
            tv_eparking_address = (TextView) itemView.findViewById(R.id.tv_eparking_address);
            tv_parking_status = (TextView) itemView.findViewById(R.id.tv_parking_status);
            tv_parking_user = (TextView) itemView.findViewById(R.id.tv_parking_user);
            parking_user_layout = (LinearLayout) itemView.findViewById(R.id.parking_user_layout);
            tv_share_number = (TextView) itemView.findViewById(R.id.tv_share_number);
            tv_lock_number = (TextView) itemView.findViewById(R.id.tv_lock_number);
            tv_share_time = (TextView) itemView.findViewById(R.id.tv_share_time);
            tv_leave_time = (TextView) itemView.findViewById(R.id.tv_leave_time);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
