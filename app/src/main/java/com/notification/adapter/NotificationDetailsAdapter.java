package com.notification.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.notification.protocol.NotificationDetailsEntity;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * Created by Administrator on 2018/4/16.
 * 消息详情页适配器
 * lizc
 *
 * @Description
 */

public class NotificationDetailsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<NotificationDetailsEntity.ContentBean.ItemsBean> list = new ArrayList<>();

    public NotificationDetailsAdapter(Context context, List<NotificationDetailsEntity.ContentBean.ItemsBean> mlist) {
        this.mContext = context;
        this.list = mlist;
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        int itemType = list.get(position).getKeyword_type();
        if (itemType== 1) {
            type = 0;
        } else if (itemType == 2) {
            type = 1;
        } else if (itemType == 3) {
            type = 2;
        }
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 0) {//纯文本
            view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_notification_details_one, viewGroup, false);
            viewHolder = new ViewHolder(view);
        } else if (viewType == 1) {//连接
            view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_notification_details_two, viewGroup, false);
            viewHolder = new ViewHolderTwo(view);
        } else if (viewType == 2) {//拨打电话
            view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_notification_details_two, viewGroup, false);
            viewHolder = new ViewHolderThree(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            holder.key.setText(list.get(position).getKeyword_name());
            holder.value.setText(list.get(position).getKeyword_value());
        } else if (viewHolder instanceof ViewHolderTwo) {//跳转链接
            ViewHolderTwo viewHolderTwo = (ViewHolderTwo) viewHolder;
            viewHolderTwo.key.setText(list.get(position).getKeyword_name());
            viewHolderTwo.value.setText(list.get(position).getKeyword_value());
            viewHolderTwo.rl_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String url = list.get(position).getKeyword_note();
                    if (!TextUtils.isEmpty(url)) {
                        LinkParseUtil.parse(mContext, url, "");
                    }
                }
            });

        } else if (viewHolder instanceof ViewHolderThree) {//拨打电话
            ViewHolderThree viewHolderThree = (ViewHolderThree) viewHolder;
            viewHolderThree.key.setText(list.get(position).getKeyword_name());
            viewHolderThree.value.setText(list.get(position).getKeyword_value());
            viewHolderThree.rl_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String phone = list.get(position).getKeyword_note();
                    if (!TextUtils.isEmpty(phone)){
                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phone));
                        mContext.startActivity(intent);
                    }
                }
            });

        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView key;
        TextView value;

        public ViewHolder(View itemView) {
            super(itemView);
            key = (TextView) itemView.findViewById(R.id.tv_details_key);
            value = (TextView) itemView.findViewById(R.id.tv_details_value);

        }
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder {
        RelativeLayout rl_next;
        TextView key;
        TextView value;

        public ViewHolderTwo(View itemView) {
            super(itemView);
            rl_next = (RelativeLayout) itemView.findViewById(R.id.rl_notice_details_two);
            key = (TextView) itemView.findViewById(R.id.tv_details_keytwo);
            value = (TextView) itemView.findViewById(R.id.tv_details_valuetwo);
        }
    }

    public class ViewHolderThree extends RecyclerView.ViewHolder {
        RelativeLayout rl_next;
        TextView key;
        TextView value;

        public ViewHolderThree(View itemView) {
            super(itemView);
            rl_next = (RelativeLayout) itemView.findViewById(R.id.rl_notice_details_two);
            key = (TextView) itemView.findViewById(R.id.tv_details_keytwo);
            value = (TextView) itemView.findViewById(R.id.tv_details_valuetwo);
        }
    }
}
