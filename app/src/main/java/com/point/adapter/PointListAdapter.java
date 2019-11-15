package com.point.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.point.activity.GivenPointMobileActivity;
import com.point.activity.PointTransactionListActivity;
import com.point.activity.ReturnPointPlanActivity;
import com.point.entity.PointAccountListEntity;

import java.util.List;

import cn.net.cyberway.R;

import static com.point.activity.PointTransactionListActivity.POINTTITLE;
import static com.point.activity.PointTransactionListActivity.POINTTPANO;

public class PointListAdapter extends RecyclerView.Adapter<PointListAdapter.PointListViewHolder> {
    private List<PointAccountListEntity.ContentBean.ListBean> listBeanList;


    public PointListAdapter(List<PointAccountListEntity.ContentBean.ListBean> listBeanList) {
        this.listBeanList = listBeanList;
    }

    @NonNull
    @Override
    public PointListAdapter.PointListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_point_type, viewGroup, false);
        PointListAdapter.PointListViewHolder pointListViewHolder = new PointListAdapter.PointListViewHolder(view);
        return pointListViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull PointListAdapter.PointListViewHolder viewHolder, int i) {
        PointAccountListEntity.ContentBean.ListBean listBean = listBeanList.get(i);
        if ("1".equals(listBean.getPano_type())) {
            viewHolder.tv_point_return.setVisibility(View.GONE);
            viewHolder.layout_point_bg.setBackgroundResource(R.drawable.point_one_balance_bg);
        } else {
            viewHolder.tv_point_return.setVisibility(View.VISIBLE);
            viewHolder.layout_point_bg.setBackgroundResource(R.drawable.point_two_balance_bg);
        }
        String pointTitle = listBean.getName();
        viewHolder.tv_point_title.setText(pointTitle);
        viewHolder.tv_point_total.setText(String.valueOf(listBean.getBalance() / 100.0f));
        viewHolder.tv_point_details.setOnClickListener(v -> {
            Context mContext = viewHolder.itemView.getContext();
            Intent intent = new Intent(mContext, PointTransactionListActivity.class);
            intent.putExtra(POINTTITLE, pointTitle);
            intent.putExtra(POINTTPANO, listBean.getPano());
            mContext.startActivity(intent);

        });
        viewHolder.tv_point_given.setOnClickListener(v -> {
            Context mContext = viewHolder.itemView.getContext();
            Intent intent = new Intent(mContext, GivenPointMobileActivity.class);
            mContext.startActivity(intent);
        });
        viewHolder.tv_point_return.setOnClickListener(v -> {
            Context mContext = viewHolder.itemView.getContext();
            Intent intent = new Intent(mContext, ReturnPointPlanActivity.class);
            intent.putExtra(POINTTPANO, listBean.getPano());
            mContext.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listBeanList == null ? 0 : listBeanList.size();
    }


    class PointListViewHolder extends RecyclerView.ViewHolder {
        private RelativeLayout layout_point_bg;
        private TextView tv_point_title;
        private TextView tv_point_total;
        private TextView tv_point_details;
        private TextView tv_point_return;
        private TextView tv_point_given;


        public PointListViewHolder(View itemView) {
            super(itemView);
            layout_point_bg = itemView.findViewById(R.id.layout_point_bg);
            tv_point_title = itemView.findViewById(R.id.tv_point_title);
            tv_point_total = itemView.findViewById(R.id.tv_point_total);
            tv_point_details = itemView.findViewById(R.id.tv_point_details);
            tv_point_return = itemView.findViewById(R.id.tv_point_return);
            tv_point_given = itemView.findViewById(R.id.tv_point_given);
        }
    }
}
