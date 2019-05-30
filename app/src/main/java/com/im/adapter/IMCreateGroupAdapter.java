package com.im.adapter;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.im.entity.SameComunityPeopleEntity;

import java.util.List;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/28 19:23
 * @change
 * @chang time
 * @class describe  创建社区选择好友的adapter
 */
public class IMCreateGroupAdapter extends BaseAdapter implements SectionIndexer {
    private List<SameComunityPeopleEntity.ContentBean.ListBean> list = null;
    private Context mContext;

    public IMCreateGroupAdapter(Context mContext, List<SameComunityPeopleEntity.ContentBean.ListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     */
    public void updateListView(List<SameComunityPeopleEntity.ContentBean.ListBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public int getCount() {
        return list != null ? this.list.size() : 0;
    }

    public Object getItem(int position) {
        return list.get(position);
    }

    public long getItemId(int position) {
        return position;
    }

    public View getView(final int position, View view, ViewGroup arg2) {
        IMCreateGroupAdapter.ViewHolder viewHolder = null;
        final SameComunityPeopleEntity.ContentBean.ListBean mContent = list.get(position);
        if (view == null) {
            viewHolder = new IMCreateGroupAdapter.ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_create_groupchat, null);
            viewHolder.tv_catalog = view.findViewById(R.id.tv_catalog);
            viewHolder.iv_select_friend = view.findViewById(R.id.iv_select_friend);
            viewHolder.user_photo = view.findViewById(R.id.user_photo);
            viewHolder.user_nickname = view.findViewById(R.id.user_nickname);
            viewHolder.user_community = view.findViewById(R.id.user_community);
            view.setTag(viewHolder);
        } else {
            viewHolder = (IMCreateGroupAdapter.ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tv_catalog.setVisibility(View.VISIBLE);
            viewHolder.tv_catalog.setText(mContent.getSortLetters());
        } else {
            viewHolder.tv_catalog.setVisibility(View.GONE);
        }
        return view;

    }


    final static class ViewHolder {
        TextView tv_catalog;
        ImageView iv_select_friend;
        ImageView user_photo;
        TextView user_nickname;
        TextView user_community;
    }


    /**
     * 根据ListView的当前位置获取分类的首字母的Char ascii值
     */
    public int getSectionForPosition(int position) {
        return list.get(position).getSortLetters().charAt(0);
    }

    /**
     * 根据分类的首字母的Char ascii值获取其第一次出现该首字母的位置
     */
    public int getPositionForSection(int section) {
        for (int i = 0; i < getCount(); i++) {
            String sortStr = list.get(i).getSortLetters();
            char firstChar = sortStr.toUpperCase().charAt(0);
            if (firstChar == section) {
                return i;
            }
        }

        return -1;
    }

    /**
     * 提取英文的首字母，非英文字母用#代替。
     *
     * @param str
     * @return
     */
    private String getAlpha(String str) {
        String sortStr = str.trim().substring(0, 1).toUpperCase();
        // 正则表达式，判断首字母是否是英文字母
        if (sortStr.matches("[A-Z]")) {
            return sortStr;
        } else {
            return "#";
        }
    }

    @Override
    public Object[] getSections() {
        return null;
    }
}
