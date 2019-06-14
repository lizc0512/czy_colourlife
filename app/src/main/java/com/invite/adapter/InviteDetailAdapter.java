package com.invite.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.invite.protocol.InviteDetailListEntity;

import java.util.List;

import cn.net.cyberway.R;

/**
 * Created by hxg on 2019/5/11 09:47
 */
public class InviteDetailAdapter extends RecyclerView.Adapter<InviteDetailAdapter.DefaultViewHolder> {

    public Context mContext;
    private List<InviteDetailListEntity.ContentBean.ListBean> mList;

    public InviteDetailAdapter(Context context, List<InviteDetailListEntity.ContentBean.ListBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_invite_detail, parent, false);
        return new DefaultViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull DefaultViewHolder holder, int position) {
        final InviteDetailListEntity.ContentBean.ListBean item = mList.get(position);

        holder.tv_detail.setText(item.getDescribe());
        holder.tv_time.setText(item.getCreate_time());
        //item.getType() 1：收入 2：支出
        int num = 1;
        try {
            num = Integer.parseInt(item.getAmount());
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //0不处理
        if (0 == num) {
            holder.tv_money.setText(item.getAmount());
        } else {
            holder.tv_money.setText(2 == item.getType() ? "-" + item.getAmount() : "+" + item.getAmount());
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class DefaultViewHolder extends RecyclerView.ViewHolder {
        TextView tv_detail;
        TextView tv_time;
        TextView tv_money;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_detail = itemView.findViewById(R.id.tv_detail);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_money = itemView.findViewById(R.id.tv_money);
        }
    }
}
