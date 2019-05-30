package com.eparking.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.eparking.activity.MonthCardRenewalActivity;
import com.eparking.protocol.MonthCardInforEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.BeeFramework.Utils.TimeUtil.getCurrentMonthLastDay;
import static com.eparking.activity.CarsLicenseUploadActivity.CARNUMBER;
import static com.eparking.activity.InputOpenCodeActivity.STATIONID;

/**
 * @name ${yuansk}
 * @class name：com.eparking.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/15 17:47
 * @change
 * @chang time
 * @class describe  我的卡包  月卡
 */
public class EparkingMonthCardAdapter extends RecyclerView.Adapter<EparkingMonthCardAdapter.PaymentViewHolder> {

    private List<MonthCardInforEntity.ContentBean> monthCardInforEntityList;
    private OnItemClickListener onClickListener;
    private Context mContext;

    public EparkingMonthCardAdapter(Context mContext, List<MonthCardInforEntity.ContentBean> monthCardInforEntityList) {
        this.monthCardInforEntityList = monthCardInforEntityList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public EparkingMonthCardAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eparking_monthcard_item, parent, false);
        PaymentViewHolder paymentViewHolder = new PaymentViewHolder(v);
        paymentViewHolder.onClickListener = onClickListener;
        return paymentViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final EparkingMonthCardAdapter.PaymentViewHolder holder, int position) {
        final MonthCardInforEntity.ContentBean monthCardBean = monthCardInforEntityList.get(position);
        holder.tv_eparking_place.setText(monthCardBean.getStation_name());
        holder.tv_out_time.setText(mContext.getResources().getString(R.string.out_of_date) + ":" + monthCardBean.getEnd_time());
        holder.tv_car_brand.setText(monthCardBean.getPlate());
        int remainDay = monthCardBean.getRemain_day();
        int useProgress = monthCardBean.getProgress();
        if (remainDay != 0) {
            if (useProgress == 0) {
                int monthDay = getCurrentMonthLastDay();
                holder.pb_remain_progress.setProgress((int) ((monthDay - remainDay) * 1.0f / monthDay * 100));
            } else {
                holder.pb_remain_progress.setProgress(useProgress);
            }
        } else {
            holder.pb_remain_progress.setProgress(useProgress);
        }
        if (useProgress == 100) {
            holder.tv_remain_days.setText(mContext.getResources().getString(R.string.parking_monthcard_outdate));
        } else {
            holder.tv_remain_days.setText(mContext.getResources().getString(R.string.parking_monthcard_remain) + remainDay + mContext.getResources().getString(R.string.parking_monthcard_day));
        }
        holder.tv_monthcard_amount.setText(monthCardBean.getRule_name());
        holder.tv_continue_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, MonthCardRenewalActivity.class);
                intent.putExtra(CARNUMBER, monthCardBean.getPlate());
                intent.putExtra(STATIONID, monthCardBean.getStation_id());
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return monthCardInforEntityList == null ? 0 : monthCardInforEntityList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_eparking_place;
        TextView tv_out_time;
        TextView tv_car_brand;
        TextView tv_remain_days;
        ProgressBar pb_remain_progress;
        TextView tv_monthcard_amount;
        TextView tv_continue_pay;


        OnItemClickListener onClickListener;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_eparking_place = (TextView) itemView.findViewById(R.id.tv_eparking_place);
            tv_out_time = (TextView) itemView.findViewById(R.id.tv_out_time);
            tv_car_brand = (TextView) itemView.findViewById(R.id.tv_car_brand);
            tv_remain_days = (TextView) itemView.findViewById(R.id.tv_remain_days);
            pb_remain_progress = itemView.findViewById(R.id.pb_remain_progress);
            tv_monthcard_amount = (TextView) itemView.findViewById(R.id.tv_monthcard_amount);
            tv_continue_pay = (TextView) itemView.findViewById(R.id.tv_continue_pay);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
