package com.door.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.door.entity.DoorRecordEntity;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 智能门禁
 * Created by hxg on 2019/8/7 09:47
 */
public class IntelligenceDoorRecordAdapter extends RecyclerView.Adapter<IntelligenceDoorRecordAdapter.ViewHolder> {

    public List<DoorRecordEntity.ContentBean.DataBean> mList;
    private Context mContext;

    public IntelligenceDoorRecordAdapter(Context mContext, List<DoorRecordEntity.ContentBean.DataBean> mList) {
        this.mList = mList;
        this.mContext = mContext;
    }

    public void setData(List<DoorRecordEntity.ContentBean.DataBean> mList) {
        this.mList.addAll(mList);
        notifyDataSetChanged();
    }

    @Override
    public IntelligenceDoorRecordAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_intelligence_door_record, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(IntelligenceDoorRecordAdapter.ViewHolder holder, int position) {
        DoorRecordEntity.ContentBean.DataBean item = mList.get(position);
        try {
            holder.tv_pwd.setText(item.getPassword());
            holder.tv_time.setText(item.getCreated_at());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView tv_pwd;
        private TextView tv_time;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_pwd = itemView.findViewById(R.id.tv_pwd);
            tv_time = itemView.findViewById(R.id.tv_time);
        }
    }

}
