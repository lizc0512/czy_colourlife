package com.im.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.im.entity.SameComunityPeopleEntity;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;


public class IMCommunityAdapter<T> extends BaseAdapter {

    private final LayoutInflater inflater;
    private Context mContext;
    public List<T> mList;
    public int type;

    public IMCommunityAdapter(Context context, List<T> list, int type) {
        mContext = context;
        mList = list;
        this.type = type;
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
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = inflater.inflate(R.layout.adapter_near_community, null);
            holder.user_photo = convertView.findViewById(R.id.user_photo);
            holder.user_nickname = convertView.findViewById(R.id.user_nickname);
            holder.user_community_distance = convertView.findViewById(R.id.user_community_distance);
            holder.user_sex = convertView.findViewById(R.id.user_sex);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        SameComunityPeopleEntity.ContentBean.ListBean listBean = (SameComunityPeopleEntity.ContentBean.ListBean) mList.get(position);
        GlideImageLoader.loadImageDefaultDisplay(mContext, listBean.getPortrait(), holder.user_photo, R.drawable.im_icon_default_head, R.drawable.im_icon_default_head);
        holder.user_nickname.setText(listBean.getName());
        if (1 == type) {
            holder.user_community_distance.setText(listBean.getCommunity_name().trim());
        } else {
            holder.user_community_distance.setText(listBean.getDistance());
        }
        String gender = listBean.getGender();
        if ("1".equals(gender)) {
            holder.user_sex.setVisibility(View.VISIBLE);
            holder.user_sex.setImageResource(R.drawable.im_icon_man);
        } else if ("2".equals(gender)) {
            holder.user_sex.setVisibility(View.VISIBLE);
            holder.user_sex.setImageResource(R.drawable.im_icon_woman);
        } else {
            holder.user_sex.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        ImageView user_photo;
        TextView user_nickname;
        TextView user_community_distance;
        ImageView user_sex;
    }
}
