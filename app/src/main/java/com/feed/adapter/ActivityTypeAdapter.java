package com.feed.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.BeeFrameworkApp;
import com.feed.protocol.ACTIVITY_CATEGORY;
import com.nohttp.utils.GlideImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 活动类型的adapter
 */
public class ActivityTypeAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    public List<ACTIVITY_CATEGORY> mList;
    private Context mContext;

    public ActivityTypeAdapter(Context context , List<ACTIVITY_CATEGORY> list){
        mContext = context;
        mList = list;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHoder holder;
        if(convertView == null){
            holder = new ViewHoder();
            convertView = inflater.inflate(R.layout.item_select_activitytype, null);
            holder.imageView = (ImageView) convertView.findViewById(R.id.item_select_type_imageview);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHoder) convertView.getTag();
        }
        ACTIVITY_CATEGORY category = mList.get(position);
        ImageLoader.getInstance().displayImage(category.photo,holder.imageView, GlideImageLoader.optionsImage );
        holder.name.setText(category.name);
        return convertView;
    }

    class ViewHoder{
        ImageView imageView;
        TextView name;
    }

}
