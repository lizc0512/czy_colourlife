package com.eparking.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eparking.protocol.ParkingInforEntity;

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
 * @class describe  停车场的位置信息
 */
public class ParkingInforAdapter extends RecyclerView.Adapter<ParkingInforAdapter.PaymentViewHolder> {

    private List<ParkingInforEntity> parkingInforEntityList;
    private OnItemClickListener onClickListener;
    private Context mContext;

    public ParkingInforAdapter(Context mContext, List<ParkingInforEntity> parkingInforEntityList) {
        this.parkingInforEntityList = parkingInforEntityList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ParkingInforAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_parkinginfor_item, parent, false);
        PaymentViewHolder paymentViewHolder = new PaymentViewHolder(v);
        paymentViewHolder.onClickListener = onClickListener;
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ParkingInforAdapter.PaymentViewHolder holder, int position) {

        ParkingInforEntity parkingInforEntity = parkingInforEntityList.get(position);
        holder.tv_near_name.setText(parkingInforEntity.getParkingAddrss());
        holder.tv_distance.setText("距我" + parkingInforEntity.getDistance() + "m");
        holder.tv_eparking_status.setText(parkingInforEntity.getParkingStatus());
    }

    @Override
    public int getItemCount() {
        return parkingInforEntityList == null ? 0 : parkingInforEntityList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_near_name;
        TextView tv_distance;
        TextView tv_eparking_sign;
        TextView tv_eparking_status;
        LinearLayout go_here_layout;
        OnItemClickListener onClickListener;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            tv_near_name = itemView.findViewById(R.id.tv_near_name);
            tv_distance = itemView.findViewById(R.id.tv_distance);
            tv_eparking_sign = itemView.findViewById(R.id.tv_eparking_sign);
            tv_eparking_status = itemView.findViewById(R.id.tv_eparking_status);
            go_here_layout = itemView.findViewById(R.id.go_here_layout);
            go_here_layout.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
