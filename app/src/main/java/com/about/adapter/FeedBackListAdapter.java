package com.about.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.about.protocol.FeedBackListEntity;

import java.text.SimpleDateFormat;
import java.util.List;

import cn.net.cyberway.R;

public class FeedBackListAdapter extends BeeBaseAdapter {

    public SimpleDateFormat sdf;

    public FeedBackListAdapter(Context context, List<FeedBackListEntity.ContentBean> data) {
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
        holder.imageView = (ImageView) cellView.findViewById(R.id.img_circle);
        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        FeedBackListEntity.ContentBean feedback = (FeedBackListEntity.ContentBean) dataList.get(position);
        ViewHolder holder = (ViewHolder) h;
        String type = feedback.getName().trim();
        String content = feedback.getContent();
        holder.tvType.setText(type);
        holder.tvTime.setText(feedback.getCreate_at());
        holder.tvContent.setText(content);
        int isRead = feedback.getIs_read();
        if (1 == isRead) {//已读
            holder.tvContent.setTextColor(mContext.getResources().getColor(R.color.grayred_text_color));
            holder.imageView.setBackgroundResource(R.drawable.bg_circle);
        } else {//未读
            holder.tvContent.setTextColor(mContext.getResources().getColor(R.color.black_text_color));
            holder.imageView.setBackgroundResource(R.drawable.about_corners);
        }
        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.list_item_feedback, null);
    }

    public class ViewHolder extends BeeCellHolder {
        public TextView tvType;
        public TextView tvTime;
        public TextView tvContent;
        public ImageView imageView;
    }

    public void setBg(int positon) {
        FeedBackListEntity.ContentBean contentBean = (FeedBackListEntity.ContentBean) dataList.get(positon);
        contentBean.setIs_read(1);
        notifyDataSetChanged();
    }
}
