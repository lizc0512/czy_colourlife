package com.notification.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.notification.protocol.NotificationAllEntity;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/**
 * Created by Administrator on 2018/4/16.
 * 消息更多消息单个item内容适配器
 * lizc
 *
 * @Description
 */

public class NotificationAllInfoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<NotificationAllEntity.ContentBean.ItemsBean> list ;
    private OnItemClickListener onClickListener;
    public NotificationAllInfoAdapter(Context context, List<NotificationAllEntity.ContentBean.ItemsBean> mlist) {
        this.mContext = context;
        this.list = mlist;
    }
    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_notification_all_one, null);
        ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.key.setText(list.get(position).getKeyword_name()+":");
            holder.value.setText(list.get(position).getKeyword_value());
        }
    }

    @Override
    public int getItemCount() {
        return list == null ? 0 : list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView key;
        TextView value;
        OnItemClickListener onClickListener;
        public ViewHolder(View itemView) {
            super(itemView);
            key = (TextView) itemView.findViewById(R.id.tv_notice_all_type1);
            value = (TextView) itemView.findViewById(R.id.tv_notice_all_type_name1);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
