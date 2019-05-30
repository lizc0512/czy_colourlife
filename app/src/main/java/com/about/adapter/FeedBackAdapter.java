package com.about.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.about.protocol.FeedBackTypeEntity;

import java.util.ArrayList;

import cn.net.cyberway.R;

public class FeedBackAdapter extends BeeBaseAdapter {
    private int select = -1;

    public FeedBackAdapter(Context c, ArrayList<FeedBackTypeEntity.ContentBean> typeBeanList) {
        mContext = c;
        dataList = typeBeanList;
        mInflater = LayoutInflater.from(c);
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder viewHolder = new ViewHolder();
        viewHolder.tv_type = (TextView) cellView.findViewById(R.id.tv_money);
        viewHolder.ll_type = (LinearLayout) cellView.findViewById(R.id.ll_money);
        return viewHolder;
    }

    @Override
    protected View bindData(final int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        ViewHolder holder = (ViewHolder) h;
        FeedBackTypeEntity.ContentBean contentBean = (FeedBackTypeEntity.ContentBean) dataList.get(position);
        holder.tv_type.setText(contentBean.getName());
        if (select == position) {
            holder.ll_type.setBackgroundResource(R.drawable.about_corners7);
            holder.tv_type.setBackgroundResource(R.drawable.about_corners7);
            holder.tv_type.setTextColor(mContext.getResources().getColor(R.color.white));
        } else {
            holder.ll_type.setBackgroundResource(R.drawable.about_corners4);
            holder.tv_type.setTextColor(mContext.getResources().getColor(R.color.black_text_color));
            holder.tv_type.setBackgroundResource(R.drawable.about_corners4);
        }
        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.feedback_gv_item, null);
    }

    public class ViewHolder extends BeeCellHolder {
        public TextView tv_type;
        public LinearLayout ll_type;
    }

    public void setSelect(int position) {
        select = position;
        notifyDataSetChanged();
    }

}
