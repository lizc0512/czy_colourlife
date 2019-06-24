package com.invite.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invite.protocol.InviteInviteRecodeEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/**
 * Created by hxg on 2019/5/27 09:47
 */

public class InviteMyAdapter extends RecyclerView.Adapter<InviteMyAdapter.ViewHolder> {

    private List<InviteInviteRecodeEntity.ContentBean.ListBean> mList;
    private Context mContext;
    private OnItemClickListener onClickListener;

    public InviteMyAdapter(Context mContext, List<InviteInviteRecodeEntity.ContentBean.ListBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    @Override
    public InviteMyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_my_invite, parent, false);
        return new ViewHolder(view);
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(InviteMyAdapter.ViewHolder holder, int position) {
        try {
            InviteInviteRecodeEntity.ContentBean.ListBean item = mList.get(position);
            holder.tv_invite_phone.setText(item.getMobile());
            holder.tv_invite_time.setText(item.getCreate_time());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tv_invite_phone;
        private TextView tv_invite_time;

        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            tv_invite_phone = itemView.findViewById(R.id.tv_invite_phone);
            tv_invite_time = itemView.findViewById(R.id.tv_invite_time);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
