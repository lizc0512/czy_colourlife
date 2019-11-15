package com.point.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.net.cyberway.R;

public class PointReturnPlanAdapter extends RecyclerView.Adapter<PointReturnPlanAdapter.PointGivenHistoryViewHolder> {
    @NonNull
    @Override
    public PointReturnPlanAdapter.PointGivenHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_return_point_plan, viewGroup, false);
        PointReturnPlanAdapter.PointGivenHistoryViewHolder pointListViewHolder = new PointReturnPlanAdapter.PointGivenHistoryViewHolder(view);
        return pointListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PointReturnPlanAdapter.PointGivenHistoryViewHolder viewHolder, int i) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }


    class PointGivenHistoryViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_return_date;
        private TextView tv_return_amount;


        public PointGivenHistoryViewHolder(View itemView) {
            super(itemView);
            tv_return_date = itemView.findViewById(R.id.tv_return_date);
            tv_return_amount = itemView.findViewById(R.id.tv_return_amount);
        }
    }
}
