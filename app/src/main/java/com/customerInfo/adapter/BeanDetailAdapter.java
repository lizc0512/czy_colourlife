package com.customerInfo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.customerInfo.protocol.BeanDetailListEntity;

import java.util.List;

import cn.net.cyberway.R;

/**
 * Created by hxg on 2019/4/16 09:47
 */
public class BeanDetailAdapter extends RecyclerView.Adapter<BeanDetailAdapter.DefaultViewHolder> {

    public Context mContext;
    private List<BeanDetailListEntity.ContentBean.DataBean> mList;

    public BeanDetailAdapter(Context context, List<BeanDetailListEntity.ContentBean.DataBean> list) {
        this.mContext = context;
        this.mList = list;
    }

    @NonNull
    @Override
    public BeanDetailAdapter.DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_bean_detail, parent, false);
        return new DefaultViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull BeanDetailAdapter.DefaultViewHolder holder, int position) {
        final BeanDetailListEntity.ContentBean.DataBean item = mList.get(position);
        holder.tv_title.setText(item.getComment());
        holder.tv_time.setText(item.getAdd_time());
        String number = "0";
        if (item.getQuantity() > 0) {
            number = "+" + item.getQuantity();
        } else if (item.getQuantity() < 0) {
            number = "" + item.getQuantity();
        }
        holder.tv_num.setText(number);
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public class DefaultViewHolder extends RecyclerView.ViewHolder {
        TextView tv_title;
        TextView tv_time;
        TextView tv_num;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_num = itemView.findViewById(R.id.tv_num);
        }
    }
}
