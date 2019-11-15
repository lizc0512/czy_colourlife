package com.point.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.net.cyberway.R;

public class PointGivenHistoryAdapter extends RecyclerView.Adapter<PointGivenHistoryAdapter.PointGivenHistoryViewHolder> {
    @NonNull
    @Override
    public PointGivenHistoryAdapter.PointGivenHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_given_point_history, viewGroup, false);
        PointGivenHistoryAdapter.PointGivenHistoryViewHolder pointListViewHolder = new PointGivenHistoryAdapter.PointGivenHistoryViewHolder(view);
        return pointListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PointGivenHistoryAdapter.PointGivenHistoryViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
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
