package com.notification.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.notification.protocol.NOTICE;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.net.cyberway.R;

/**
 * Created by chenql on 15/12/17.
 */
public class ENotificationAdapter extends BeeBaseAdapter {

    public SimpleDateFormat sdf;

    public ENotificationAdapter(Context context, List<NOTICE> data) {
        sdf = new SimpleDateFormat("MM-dd HH:mm");
        mInflater = LayoutInflater.from(context);
        mContext = context;
        dataList = data;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder holder = new ViewHolder();

        holder.tvType = (TextView) cellView.findViewById(R.id.tv_type);
        holder.tvTime = (TextView) cellView.findViewById(R.id.tv_time);
        holder.tvContent = (TextView) cellView.findViewById(R.id.tv_content);
        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        NOTICE notice = (NOTICE) dataList.get(position);
        ViewHolder holder = (ViewHolder) h;

        String type = notice.categoryName.trim();
        long time = Long.parseLong(notice.create_time)*1000;
        String content = notice.contact;

        holder.tvType.setText(type);
        holder.tvTime.setText(sdf.format(new Date(time)));
        holder.tvContent.setText(content);
        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.list_item_notification, null);
    }

    public class ViewHolder extends BeeCellHolder {
        public TextView tvType;
        public TextView tvTime;
        public TextView tvContent;
    }
}
