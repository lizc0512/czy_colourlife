package com.point.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.point.entity.PointTransferListEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

public class PointGivenHistoryAdapter extends RecyclerView.Adapter<PointGivenHistoryAdapter.PointGivenHistoryViewHolder> {

    private List<PointTransferListEntity.ContentBean.ListBean> totalContentBeanList;
    public OnItemClickListener onClickListener;

    public PointGivenHistoryAdapter(List<PointTransferListEntity.ContentBean.ListBean> totalContentBeanList) {
        this.totalContentBeanList = totalContentBeanList;
    }
    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    @NonNull
    @Override
    public PointGivenHistoryAdapter.PointGivenHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_given_point_history, viewGroup, false);
        PointGivenHistoryAdapter.PointGivenHistoryViewHolder pointListViewHolder = new PointGivenHistoryAdapter.PointGivenHistoryViewHolder(view);
        pointListViewHolder.onClickListener = onClickListener;
        return pointListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PointGivenHistoryAdapter.PointGivenHistoryViewHolder viewHolder, int i) {
        PointTransferListEntity.ContentBean.ListBean  listBean=totalContentBeanList.get(i);
        viewHolder.tv_given_username.setText(listBean.getDest_client()+" "+listBean.getMobile());
        viewHolder.tv_given_date.setText(listBean.getCreate_time());
        if ("1".equals(listBean.getType())){
            viewHolder.tv_given_amount.setText("+"+listBean.getDest_money()*1.0f/100);
            viewHolder.tv_given_amount.setTextColor(Color.parseColor("#F24724"));
        }else{
            viewHolder.tv_given_amount.setTextColor(Color.parseColor("#25282E"));
            viewHolder.tv_given_amount.setText("-"+listBean.getDest_money()*1.0f/100);
        }
    }

    @Override
    public int getItemCount() {
        return totalContentBeanList == null ? 0 : totalContentBeanList.size();
    }


    class PointGivenHistoryViewHolder extends RecyclerView.ViewHolder  implements View.OnClickListener {
        private TextView tv_given_username;
        private TextView tv_given_date;
        private TextView tv_given_amount;
        private OnItemClickListener onClickListener;

        public PointGivenHistoryViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this::onClick);
            tv_given_username = itemView.findViewById(R.id.tv_given_username);
            tv_given_date = itemView.findViewById(R.id.tv_given_date);
            tv_given_amount = itemView.findViewById(R.id.tv_given_amount);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
