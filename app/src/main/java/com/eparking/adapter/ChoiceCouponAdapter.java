package com.eparking.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.eparking.protocol.CouponEntity;

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
 * @class describe  选择优惠券的
 */
public class ChoiceCouponAdapter extends RecyclerView.Adapter<ChoiceCouponAdapter.PaymentViewHolder> {

    private List<CouponEntity> couponEntityList;
    private OnItemClickListener onClickListener;
    private Context mContext;
    private int selectPos;

    public ChoiceCouponAdapter(Context mContext, List<CouponEntity> couponEntityList) {
        this.couponEntityList = couponEntityList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public void setSelectPos(int pos) {
        this.selectPos = pos;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChoiceCouponAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.pop_coupon_item, parent, false);
        PaymentViewHolder paymentViewHolder = new PaymentViewHolder(v);
        paymentViewHolder.onClickListener = onClickListener;
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChoiceCouponAdapter.PaymentViewHolder holder, int position) {
        CouponEntity couponEntity = couponEntityList.get(position);
        double couponAmount = couponEntity.getCouponAmount();
        if (couponAmount!=0){
            holder.tv_coupon_amount.setText("￥" + couponEntity.getCouponAmount());
            holder.tv_coupon_date.setText(TimeUtil.getTime(couponEntity.getCouponDate() * 1000, "yyyy:MM:dd ") + "到期");
        }
        holder.tv_coupon_condition.setText(couponEntity.getCouponDesc());
        if (selectPos == position) {
            holder.iv_coupon_select.setImageResource(R.drawable.eparking_coupon_select);
        } else {
            holder.iv_coupon_select.setImageResource(R.drawable.eparking_openticket_default);
        }
    }

    @Override
    public int getItemCount() {
        return couponEntityList==null?0:couponEntityList.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_coupon_select;
        TextView tv_coupon_amount;
        TextView tv_coupon_condition;
        TextView tv_coupon_date;

        OnItemClickListener onClickListener;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            iv_coupon_select = itemView.findViewById(R.id.iv_coupon_select);
            tv_coupon_amount = itemView.findViewById(R.id.tv_coupon_amount);
            tv_coupon_condition = itemView.findViewById(R.id.tv_coupon_condition);
            tv_coupon_date = itemView.findViewById(R.id.tv_coupon_date);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
