package com.eparking.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.eparking.activity.EParkingHistoryRecordActivity;
import com.eparking.activity.FindParkingSpaceActivity;
import com.eparking.protocol.StopStationRecordEntity;

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
 * @class describe  历史记录 --- 停车记录
 */
public class ParkingRecordAdapter extends RecyclerView.Adapter<ParkingRecordAdapter.PaymentViewHolder> {

    private List<StopStationRecordEntity.ContentBean> paymentRecordEntityList;
    private OnItemClickListener onClickListener;
    private Context mContext;

    public ParkingRecordAdapter(Context mContext, List<StopStationRecordEntity.ContentBean> paymentRecordEntityList) {
        this.paymentRecordEntityList = paymentRecordEntityList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public ParkingRecordAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.record_parking_item, parent, false);
        PaymentViewHolder paymentViewHolder = new PaymentViewHolder(v);
        paymentViewHolder.onClickListener = onClickListener;
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ParkingRecordAdapter.PaymentViewHolder holder, int position) {
        final StopStationRecordEntity.ContentBean contentBean = paymentRecordEntityList.get(position);
        holder.tv_parking_address.setText(contentBean.getStation_name());
        holder.tv_parking_date.setText(TimeUtil.formatTime(contentBean.getTime_in(), "yyyy.MM.dd") + "-" + TimeUtil.formatTime(contentBean.getTime_out(), "yyyy.MM.dd"));
        holder.tv_car_brand.setText(contentBean.getPlate());
        holder.tv_parking_type.setText(contentBean.getMonth());
        holder.tv_parking_time.setText(TimeUtil.dateDiff(contentBean.getTime_in(), contentBean.getTime_out()));
        holder.tv_go_here.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EParkingHistoryRecordActivity) mContext).getDestinationCoordinate(contentBean.getStation_id());
            }
        });
    }

    @Override
    public int getItemCount() {
        return paymentRecordEntityList == null ? 0 : paymentRecordEntityList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_parking_address;
        TextView tv_parking_type;
        TextView tv_car_brand;
        TextView tv_parking_date;
        RelativeLayout driving_navigation_layout;
        TextView tv_parking_time;
        TextView tv_go_here;
        OnItemClickListener onClickListener;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_parking_address = (TextView) itemView.findViewById(R.id.tv_parking_address);
            tv_parking_type = (TextView) itemView.findViewById(R.id.tv_parking_type);
            tv_car_brand = (TextView) itemView.findViewById(R.id.tv_car_brand);
            tv_parking_date = (TextView) itemView.findViewById(R.id.tv_parking_date);
            driving_navigation_layout = (RelativeLayout) itemView.findViewById(R.id.driving_navigation_layout);
            tv_parking_time = (TextView) itemView.findViewById(R.id.tv_parking_time);
            tv_go_here = (TextView) itemView.findViewById(R.id.tv_go_here);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
