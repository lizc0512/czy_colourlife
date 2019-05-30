package com.customerInfo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.customerInfo.activity.CustomerAddPropertyActivity;
import com.customerInfo.protocol.AreaListEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

public class AreaPropertyAdapter extends RecyclerView.Adapter<AreaPropertyAdapter.DefaultViewHolder> {

    private List<AreaListEntity.ContentBean.DataBean> areaBeanList;
    public Context mContext;
    public OnItemClickListener onClickListener;

    public AreaPropertyAdapter(Context mContext, List<AreaListEntity.ContentBean.DataBean> areaBeanList) {
        this.areaBeanList = areaBeanList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public AreaPropertyAdapter.DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_address, parent, false);
        return new DefaultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AreaPropertyAdapter.DefaultViewHolder holder, int position) {
        AreaListEntity.ContentBean.DataBean communityBean = areaBeanList.get(position);
        holder.tv_community_name.setText(communityBean.getName());
        holder.tv_address.setText(communityBean.getAddress());
        holder.ll_search.setOnClickListener(v -> ((CustomerAddPropertyActivity) mContext).searchSelect(position));
    }

    @Override
    public int getItemCount() {
        return areaBeanList == null ? 0 : areaBeanList.size();
    }

    public static class DefaultViewHolder extends RecyclerView.ViewHolder {
        RelativeLayout ll_search;
        TextView tv_community_name;
        TextView tv_address;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ll_search = itemView.findViewById(R.id.ll_search);
            tv_community_name = itemView.findViewById(R.id.tv_community_name);
            tv_address = itemView.findViewById(R.id.tv_address);
        }
    }

}
