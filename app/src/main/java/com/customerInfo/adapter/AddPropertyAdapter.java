package com.customerInfo.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.customerInfo.activity.CustomerAddPropertyActivity;
import com.customerInfo.protocol.AddListEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

public class AddPropertyAdapter extends RecyclerView.Adapter<AddPropertyAdapter.DefaultViewHolder> {

    private List<AddListEntity.ContentBean.DataBean> addBeanList;
    public Context mContext;
    public OnItemClickListener onClickListener;

    public AddPropertyAdapter(Context mContext, List<AddListEntity.ContentBean.DataBean> communityBeanList) {
        this.addBeanList = communityBeanList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public AddPropertyAdapter.DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_property, parent, false);
        return new DefaultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddPropertyAdapter.DefaultViewHolder holder, int position) {
        AddListEntity.ContentBean.DataBean addBean = addBeanList.get(position);
        holder.tv_community_name.setText(addBean.getName());
        holder.tv_community_name.setOnClickListener(v -> ((CustomerAddPropertyActivity) mContext).select(position));
    }

    @Override
    public int getItemCount() {
        return addBeanList == null ? 0 : addBeanList.size();
    }

    public static class DefaultViewHolder extends RecyclerView.ViewHolder {
        TextView tv_community_name;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_community_name = itemView.findViewById(R.id.tv_community_name);
        }
    }

}
