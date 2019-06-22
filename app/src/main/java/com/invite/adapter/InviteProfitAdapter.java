package com.invite.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invite.protocol.InviteDetailListEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/**
 * Created by hxg on 2019/6/5 09:47
 */

public class InviteProfitAdapter extends RecyclerView.Adapter<InviteProfitAdapter.ViewHolder> {

    private List<InviteDetailListEntity.ContentBean.ListBean> mList;
    private Context mContext;
    private OnItemClickListener onClickListener;
    private int status;

    public InviteProfitAdapter(Context mContext, List<InviteDetailListEntity.ContentBean.ListBean> mList, int status) {
        this.mList = mList;
        this.mContext = mContext;
        this.status = status;
    }

    @Override
    public InviteProfitAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_profit_invite, parent, false);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(InviteProfitAdapter.ViewHolder holder, int position) {
        InviteDetailListEntity.ContentBean.ListBean item = mList.get(position);
        try {
            if (3 == status) {
                if (View.VISIBLE == holder.tv_add_red.getVisibility()) {
                    holder.tv_add_red.setVisibility(View.GONE);
                }
                if (View.GONE == holder.tv_add_red3.getVisibility()) {
                    holder.tv_add_red3.setVisibility(View.VISIBLE);
                }
                if (2 == item.getType()) {
                    holder.tv_add_red3.setText("-" + item.getAmount());
                } else {
                    holder.tv_add_red3.setText("+" + item.getAmount());
                }
            } else {
                if (View.VISIBLE == holder.tv_add_red3.getVisibility()) {
                    holder.tv_add_red3.setVisibility(View.GONE);
                }
                if (View.GONE == holder.tv_add_red.getVisibility()) {
                    holder.tv_add_red.setVisibility(View.VISIBLE);
                }
                if (2 == item.getType()) {
                    holder.tv_add_red.setText("-" + item.getAmount());
                } else {
                    holder.tv_add_red.setText("+" + item.getAmount());
                }
            }
            holder.tv_time.setText(item.getCreate_time());

            String[] strings = item.getDescribe().split("-");
            if (2 <= strings.length) {
                holder.tv_invite.setText(strings[0]);
                holder.tv_phone.setText(strings[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_phone;
        private TextView tv_time;
        private TextView tv_add_red;
        private TextView tv_add_red3;
        private TextView tv_invite;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_phone = itemView.findViewById(R.id.tv_phone);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_add_red = itemView.findViewById(R.id.tv_add_red);
            tv_add_red3 = itemView.findViewById(R.id.tv_add_red3);
            tv_invite = itemView.findViewById(R.id.tv_invite);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
