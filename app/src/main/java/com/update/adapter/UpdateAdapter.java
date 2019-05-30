package com.update.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;

import java.util.List;

import cn.net.cyberway.R;

/**
 * Created by HX_CHEN on 2016/1/18.
 */
public class UpdateAdapter extends BeeBaseAdapter {
    public UpdateAdapter(Context c, List<String> date) {
        mContext = c;
        dataList = date;
        mInflater = LayoutInflater.from(c);
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder holder = new ViewHolder();
        holder.tv_desc  = (TextView) cellView.findViewById(R.id.tv_update_desc);
        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        ViewHolder holder = (ViewHolder) h;
        holder.tv_desc.setText(dataList.get(position).toString());
        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.updatelist_item, null);
    }
    public class ViewHolder extends BeeCellHolder{
        public TextView tv_desc;
    }
}
