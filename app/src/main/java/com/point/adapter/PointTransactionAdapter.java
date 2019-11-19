package com.point.adapter;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nohttp.utils.GlideImageLoader;
import com.point.entity.PointTransactionRecordEntity;

import java.util.List;


import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

public class PointTransactionAdapter extends RecyclerView.Adapter<PointTransactionAdapter.PointTransactionViewHolder> {

    private List<PointTransactionRecordEntity.ContentBean.ListBean> totalListBean;
    public OnItemClickListener onClickListener;

    public PointTransactionAdapter(List<PointTransactionRecordEntity.ContentBean.ListBean> totalListBean) {
        this.totalListBean = totalListBean;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public PointTransactionAdapter.PointTransactionViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_point_transaction, viewGroup, false);
        PointTransactionAdapter.PointTransactionViewHolder pointTransactionViewHolder = new PointTransactionAdapter.PointTransactionViewHolder(view);
        pointTransactionViewHolder.onClickListener = onClickListener;
        return pointTransactionViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PointTransactionAdapter.PointTransactionViewHolder viewHolder, int i) {
        PointTransactionRecordEntity.ContentBean.ListBean listBean = totalListBean.get(i);
        viewHolder.tv_transaction_name.setText(listBean.getTrans_name());
        viewHolder.tv_transaction_date.setText(listBean.getCreate_time());
        int org_money=listBean.getOrg_money();
        String type=listBean.getType();
        if ("1".equals(type)){
            viewHolder.tv_transaction_amout.setText("+"+org_money*1.0f/100);
            viewHolder.tv_transaction_amout.setTextColor(Color.parseColor("#F24724"));
        }else{
            viewHolder.tv_transaction_amout.setTextColor(Color.parseColor("#25282E"));
            viewHolder.tv_transaction_amout.setText("-"+org_money*1.0f/100);
        }

        GlideImageLoader.loadImageDisplay(viewHolder.itemView.getContext(), listBean.getLogo(), viewHolder.iv_transaction_type);
    }

    @Override
    public int getItemCount() {
        return totalListBean == null ? 0 : totalListBean.size();
    }


    class PointTransactionViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iv_transaction_type;
        private TextView tv_transaction_name;
        private TextView tv_transaction_date;
        private TextView tv_transaction_amout;
        private OnItemClickListener onClickListener;

        public PointTransactionViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this::onClick);
            iv_transaction_type = itemView.findViewById(R.id.iv_transaction_type);
            tv_transaction_name = itemView.findViewById(R.id.tv_transaction_name);
            tv_transaction_date = itemView.findViewById(R.id.tv_transaction_date);
            tv_transaction_amout = itemView.findViewById(R.id.tv_transaction_amout);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
