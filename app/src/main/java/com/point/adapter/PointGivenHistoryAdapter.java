package com.point.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.point.entity.PointTransferListEntity;

import java.util.List;

import cn.net.cyberway.R;

public class PointGivenHistoryAdapter extends RecyclerView.Adapter<PointGivenHistoryAdapter.PointGivenHistoryViewHolder> {

    public PointGivenHistoryAdapter(List<PointTransferListEntity.ContentBean.ListBean> totalContentBeanList) {
        this.totalContentBeanList = totalContentBeanList;
    }

    private List<PointTransferListEntity.ContentBean.ListBean> totalContentBeanList;

    @NonNull
    @Override
    public PointGivenHistoryAdapter.PointGivenHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_given_point_history, viewGroup, false);
        PointGivenHistoryAdapter.PointGivenHistoryViewHolder pointListViewHolder = new PointGivenHistoryAdapter.PointGivenHistoryViewHolder(view);
        return pointListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PointGivenHistoryAdapter.PointGivenHistoryViewHolder viewHolder, int i) {
        PointTransferListEntity.ContentBean.ListBean  listBean=totalContentBeanList.get(i);
        viewHolder.tv_given_username.setText(listBean.getDest_client());
        viewHolder.tv_given_date.setText(listBean.getCreate_time());
        viewHolder.tv_given_amount.setText(String.valueOf(listBean.getDest_money()*1.0f/100));
    }

    @Override
    public int getItemCount() {
        return totalContentBeanList == null ? 0 : totalContentBeanList.size();
    }


    class PointGivenHistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_given_username;
        private TextView tv_given_date;
        private TextView tv_given_amount;


        public PointGivenHistoryViewHolder(View itemView) {
            super(itemView);
            tv_given_username = itemView.findViewById(R.id.tv_given_username);
            tv_given_date = itemView.findViewById(R.id.tv_given_date);
            tv_given_amount = itemView.findViewById(R.id.tv_given_amount);
        }
    }
}
