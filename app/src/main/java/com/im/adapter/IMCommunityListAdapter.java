package com.im.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.im.entity.CommunityProgressEntity;
import com.im.entity.SameComunityPeopleEntity;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;


public class IMCommunityListAdapter<T> extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context mContext;
    public List<T> mList;


    public IMCommunityListAdapter(Context context, List<T> list) {
        mContext = context;
        mList = list;
        inflater = LayoutInflater.from(mContext);
    }


    @Override
    public int getCount() {
        return mList != null ? mList.size() : 0;
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_community_manager, null);
            holder.tv_community_time = convertView.findViewById(R.id.tv_community_time);
            holder.iv_community_manager = convertView.findViewById(R.id.iv_community_manager);
            holder.tv_community_name = convertView.findViewById(R.id.tv_community_name);
            holder.tv_group_name = convertView.findViewById(R.id.tv_group_name);
            holder.tv_group_status = convertView.findViewById(R.id.tv_group_status);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CommunityProgressEntity.ContentBean contentBean = (CommunityProgressEntity.ContentBean) mList.get(position);
        holder.tv_group_name.setText(contentBean.getTitle());
        holder.tv_group_status.setText(contentBean.getContent());
        holder.tv_community_time.setText(TimeUtil.getYearTime(contentBean.getUpdated_at()*1000,"yyyy年MM月dd日 HH:mm"));
        return convertView;
    }

    class ViewHolder {
        TextView tv_community_time;
        ImageView iv_community_manager;
        TextView tv_community_name;
        TextView tv_group_name;
        TextView tv_group_status;
    }
}
