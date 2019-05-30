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
import android.widget.TextView;

import com.eparking.activity.EParkingCardHolderActivity;
import com.eparking.activity.ShareParkingSpaceActivity;
import com.eparking.protocol.ParkingLockEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.eparking.activity.ShareParkingSpaceActivity.LOCKCODE;

/**
 * @name ${yuansk}
 * @class name：com.eparking.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/15 17:47
 * @change
 * @chang time
 * @class describe  我的卡包  车位锁
 */
public class EparkingLockAdapter extends RecyclerView.Adapter<EparkingLockAdapter.PaymentViewHolder> {

    private List<ParkingLockEntity.ContentBean> parkingLockEntityList;
    private OnItemClickListener onClickListener;
    private Context mContext;

    public EparkingLockAdapter(Context mContext, List<ParkingLockEntity.ContentBean> parkingLockEntityList) {
        this.parkingLockEntityList = parkingLockEntityList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public EparkingLockAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eparking_parkinglock_item, parent, false);
        PaymentViewHolder paymentViewHolder = new PaymentViewHolder(v);
        paymentViewHolder.onClickListener = onClickListener;
        return paymentViewHolder;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final EparkingLockAdapter.PaymentViewHolder holder, int position) {
        final ParkingLockEntity.ContentBean parkingLockContentBean = parkingLockEntityList.get(position);
        holder.tv_eparking_place.setText(parkingLockContentBean.getStation_name());
        holder.tv_lock_name.setText(parkingLockContentBean.getLock_name());
        final String lockStatus = parkingLockContentBean.getStatus();
        if (!"close".equalsIgnoreCase(lockStatus)) {
            holder.tv_carlock_status.setText(mContext.getResources().getString(R.string.lock_up));
        } else {
            holder.tv_carlock_status.setText(mContext.getResources().getString(R.string.lock_down));
        }
        final String etCode = parkingLockContentBean.getEtcode();
        holder.tv_carlock_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((EParkingCardHolderActivity) mContext).openateCarLock(parkingLockContentBean);
            }
        });
        holder.tv_share_lock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent shareIntent = new Intent(mContext, ShareParkingSpaceActivity.class);
                shareIntent.putExtra(LOCKCODE, etCode);
                mContext.startActivity(shareIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return parkingLockEntityList == null ? 0 : parkingLockEntityList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_eparking_place;
        TextView tv_carlock_status;
        TextView tv_lock_name;
        TextView tv_share_lock;

        OnItemClickListener onClickListener;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_eparking_place = (TextView) itemView.findViewById(R.id.tv_eparking_place);
            tv_carlock_status = (TextView) itemView.findViewById(R.id.tv_carlock_status);
            tv_lock_name = (TextView) itemView.findViewById(R.id.tv_lock_name);
            tv_share_lock = (TextView) itemView.findViewById(R.id.tv_share_lock);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
