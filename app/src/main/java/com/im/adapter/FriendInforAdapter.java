package com.im.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.im.activity.IMCreateGroupChatActivity;
import com.im.entity.FriendInforEntity;
import com.im.utils.BaseUtil;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;
import java.util.Map;

import cn.net.cyberway.R;

public class FriendInforAdapter extends BaseAdapter implements SectionIndexer {
    private List<FriendInforEntity> list = null;
    private Context mContext;
    private int showCheck;
    private Map<String, Boolean> checkMap;

    public FriendInforAdapter(Context mContext, List<FriendInforEntity> list, int showCheck) {
        this.mContext = mContext;
        this.list = list;
        this.showCheck = showCheck;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     */
    public void updateListView(List<FriendInforEntity> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    public void setCheckMap(Map<String, Boolean> checkMap) {
        this.checkMap = checkMap;
    }

    private String ownerUUid;

    public void setUUId(String uuId) {
        this.ownerUUid = uuId;
    }

    public Map<String, Boolean> getCheckMap() {
        return checkMap;
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
        ViewHolder viewHolder = null;
        final FriendInforEntity friendInforEntity = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_create_groupchat, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.tv_catalog);
            viewHolder.user_nickname = (TextView) view.findViewById(R.id.user_nickname);
            viewHolder.iv_select_friend = (ImageView) view.findViewById(R.id.iv_select_friend);
            viewHolder.user_photo = (ImageView) view.findViewById(R.id.user_photo);
            viewHolder.user_community = (TextView) view.findViewById(R.id.user_community);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
            viewHolder.tvTitle.setText(friendInforEntity.getSortLetters());
        } else {
            viewHolder.tvTitle.setVisibility(View.GONE);
        }
        if (showCheck == 0) {
            viewHolder.iv_select_friend.setVisibility(View.GONE);
        } else {
            viewHolder.iv_select_friend.setVisibility(View.VISIBLE);
        }
        final String uuid = friendInforEntity.getUuid();
        final boolean isSelected = checkMap.get(uuid);
        viewHolder.iv_select_friend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!ownerUUid.equals(uuid)) {
                    checkMap.put(uuid, !isSelected);
                    ((IMCreateGroupChatActivity) mContext).setBtnStatus();
                    notifyDataSetChanged();
                }
            }
        });
        if (checkMap.get(uuid)) {
            viewHolder.iv_select_friend.setImageResource(R.drawable.im_icon_groupchat_check);
        } else {
            viewHolder.iv_select_friend.setImageResource(R.drawable.im_icon_groupchat_uncheck);
        }
        GlideImageLoader.loadImageDefaultDisplay(mContext, friendInforEntity.getPortrait(),
                viewHolder.user_photo, R.drawable.im_icon_default_portrait, R.drawable.im_icon_default_portrait);
        String communityName = friendInforEntity.getCommunityName();
        if (TextUtils.isEmpty(communityName) || "null".equalsIgnoreCase(communityName)) {
            viewHolder.user_community.setText("");
        } else {
            viewHolder.user_community.setText(communityName);
        }
        String userName = friendInforEntity.getUsername();
        String realName = friendInforEntity.getRealName();
        if (TextUtils.isEmpty(BaseUtil.formatString(realName))) {
            if (TextUtils.isEmpty(BaseUtil.formatString(userName))) {
                viewHolder.user_nickname.setText(friendInforEntity.getNickname());
            } else {
                viewHolder.user_nickname.setText(userName);
            }
        } else {
            viewHolder.user_nickname.setText(realName);
        }
        return view;
    }

    final static class ViewHolder {
        TextView tvTitle;
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