package com.myproperty.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.myproperty.activity.MyPropertyActivity;
import com.myproperty.protocol.AddressAuthListEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

public class MyPropertyAuthAdapter extends RecyclerView.Adapter<MyPropertyAuthAdapter.DefaultViewHolder> {

    private List<AddressAuthListEntity.ContentBean> mnList;
    public Context mContext;
    public OnItemClickListener onClickListener;

    public MyPropertyAuthAdapter(Context mContext, List<AddressAuthListEntity.ContentBean> mnList) {
        this.mnList = mnList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyPropertyAuthAdapter.DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_property_auth, parent, false);
        return new DefaultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyPropertyAuthAdapter.DefaultViewHolder holder, int position) {
        final Context mContext = holder.itemView.getContext();
        AddressAuthListEntity.ContentBean communityBean = mnList.get(position);
        holder.tv_community_name.setText(communityBean.getCommunity_name());
        holder.tv_detail_name.setText(communityBean.getAddress());
        if (communityBean.getSelect() == 0) {
            holder.iv_select.setImageResource(R.drawable.icon_no_select);
        } else {
            holder.iv_select.setImageResource(R.drawable.icon_select);
        }
        holder.iv_select.setOnClickListener(v -> {
            int select = communityBean.getSelect() == 0 ? 1 : 0;
            ((MyPropertyActivity) mContext).choose(position, select);
        });
    }

    @Override
    public int getItemCount() {
        return mnList == null ? 0 : mnList.size();
    }

    public static class DefaultViewHolder extends RecyclerView.ViewHolder {
        TextView tv_community_name;
        TextView tv_detail_name;
        ImageView iv_select;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_community_name = itemView.findViewById(R.id.tv_community_name);
            tv_detail_name = itemView.findViewById(R.id.tv_detail_name);
            iv_select = itemView.findViewById(R.id.iv_select);
        }
    }

}
