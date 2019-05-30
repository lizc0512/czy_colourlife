package com.im.adapter;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/22 10:39
 * @change
 * @chang time
 * @class describe    弹出的popwindow的adapter
 */

public class IMPopAdapter extends BaseAdapter {
    private String[] contentArr;
    private int[] imageArr = {R.drawable.im_icon_drop_groupchat, R.drawable.im_icon_drop_friends, R.drawable.im_icon_drop_community, R.drawable.im_icon_drop_neighborhood, R.drawable.im_icon_drop_community};
    private Context mContext;

    public IMPopAdapter(Context context) {
        super();
        mContext = context;
        contentArr = context.getResources().getStringArray(R.array.im_menu);
    }

    public IMPopAdapter(Context context, String[] contentArr, int[] imageArr) {
        super();
        mContext = context;
        this.contentArr = contentArr;
        this.imageArr = imageArr;
    }

    @Override
    public int getCount() {
        return contentArr.length;
    }

    @Override
    public Object getItem(int position) {
        return contentArr[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.adapter_pop_item, null);
        }
        ImageView iv_pop_logo = convertView.findViewById(R.id.iv_pop_logo);
        TextView tv_pop_name = convertView.findViewById(R.id.tv_pop_name);
        iv_pop_logo.setImageResource(imageArr[position]);
        tv_pop_name.setText(contentArr[position]);
        return convertView;
    }
}
