package com.eparking.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
 * @class describe  绑定月卡  选择 停车场  楼栋 房间号等
 */
public class ChoiceParkingAddressAdapter extends RecyclerView.Adapter<ChoiceParkingAddressAdapter.PaymentViewHolder> {

    private List<String> choiceData;
    private int choiceType;
    private OnItemClickListener onClickListener;
    private Context mContext;
    private int selectPos = -1;

    public ChoiceParkingAddressAdapter(Context mContext, List<String> choiceData, int choiceType) {
        this.choiceType = choiceType;
        this.choiceData = choiceData;
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
    public ChoiceParkingAddressAdapter.PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eparking_choiceaddress_item, parent, false);
        PaymentViewHolder paymentViewHolder = new PaymentViewHolder(v);
        paymentViewHolder.onClickListener = onClickListener;
        return paymentViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ChoiceParkingAddressAdapter.PaymentViewHolder holder, int position) {
        if (choiceType == 0) {
            holder.iv_choice_sign.setVisibility(View.GONE);
            holder.iv_choice_language.setVisibility(View.GONE);
            if (selectPos == position) {
                holder.tv_choice_address.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.color_108ee9));
            } else {
                holder.tv_choice_address.setTextColor(holder.itemView.getContext().getResources().getColor(R.color.color_333b46));
            }
        } else if (choiceType == 1) {
            holder.iv_choice_sign.setVisibility(View.VISIBLE);
            holder.iv_choice_language.setVisibility(View.GONE);
            if (selectPos == position) {
                holder.iv_choice_sign.setImageResource(R.drawable.eparking_openticket_select);
            } else {
                holder.iv_choice_sign.setImageResource(R.drawable.eparking_openticket_default);
            }
        } else if (choiceType == 2) {
            holder.iv_choice_sign.setVisibility(View.GONE);
            holder.iv_choice_language.setVisibility(View.VISIBLE);
            if (selectPos == position) {
                holder.iv_choice_language.setImageResource(R.drawable.eparking_openticket_select);
            } else {
                holder.iv_choice_language.setImageResource(R.drawable.eparking_openticket_default);
            }
        } else {
            holder.iv_choice_sign.setVisibility(View.GONE);
        }
        holder.tv_choice_address.setText(choiceData.get(position));
    }

    @Override
    public int getItemCount() {
        return choiceData == null ? 0 : choiceData.size();
    }

    public static class PaymentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_choice_sign;
        ImageView iv_choice_language;
        TextView tv_choice_address;

        OnItemClickListener onClickListener;

        public PaymentViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            iv_choice_sign = itemView.findViewById(R.id.iv_choice_sign);
            iv_choice_language = itemView.findViewById(R.id.iv_choice_language);
            tv_choice_address = itemView.findViewById(R.id.tv_choice_address);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
