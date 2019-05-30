package com.eparking.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/18 15:25
 * @change
 * @chang time
 * @class describe
 */
public class PopFilterAdapter extends BaseAdapter {

    private List<String> dataSource;
    private int sourceType = 0;
    private Context mContext;
    private int selectPos = -1;

    public PopFilterAdapter(Context mContext, List<String> dataSource, int sourceType) {
        this.sourceType = sourceType;
        this.dataSource = dataSource;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return dataSource == null ? 0 : dataSource.size();
    }

    public void setSelectPos(int position) {
        this.selectPos = position;
        notifyDataSetChanged();
    }

    @Override
    public Object getItem(int position) {
        return dataSource.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (null == convertView) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.pop_filter_item, null);
        }
        TextView tv_filter_condition = convertView.findViewById(R.id.tv_filter_condition);
        String showContent = dataSource.get(position);
        tv_filter_condition.setText(showContent);
        if (sourceType == 1) {
            if (selectPos == position) {
                tv_filter_condition.setBackgroundResource(R.drawable.shape_history_filterchecked);
                tv_filter_condition.setTextColor(mContext.getResources().getColor(R.color.white));
            } else {
                tv_filter_condition.setBackgroundResource(R.drawable.shape_history_filterdefault);
                tv_filter_condition.setTextColor(mContext.getResources().getColor(R.color.color_333b46));
            }
        }
        return convertView;
    }
}
