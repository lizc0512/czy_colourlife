package com.im.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.SectionIndexer;
import android.widget.TextView;

import com.im.activity.IMApplyFriendActivity;
import com.im.activity.IMFriendInforActivity;
import com.im.activity.IMInviteRegisterActivity;
import com.im.activity.IMMobileBookActivity;
import com.im.entity.MobileBookEntity;
import com.im.entity.MobileInforEntity;
import com.im.entity.SameComunityPeopleEntity;
import com.im.entity.SortModel;

import java.util.List;

import cn.net.cyberway.R;

public class SortAdapter extends BaseAdapter implements SectionIndexer {
    private List<MobileInforEntity> list = null;
    private Context mContext;

    public SortAdapter(Context mContext, List<MobileInforEntity> list) {
        this.mContext = mContext;
        this.list = list;
    }

    /**
     * 当ListView数据发生变化时,调用此方法来更新ListView
     */
    public void updateListView(List<MobileInforEntity> list) {
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
        ViewHolder viewHolder = null;
        final MobileInforEntity mContent = list.get(position);
        if (view == null) {
            viewHolder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_mobile_book, null);
            viewHolder.tvTitle = (TextView) view.findViewById(R.id.catalog);
            viewHolder.user_nickname = (TextView) view.findViewById(R.id.user_nickname);
            viewHolder.user_mobile = (TextView) view.findViewById(R.id.user_mobile);
            viewHolder.user_status = (TextView) view.findViewById(R.id.user_status);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        //根据position获取分类的首字母的Char ascii值
        int section = getSectionForPosition(position);

        //如果当前位置等于该分类首字母的Char的位置 ，则认为是第一次出现
        if (position == getPositionForSection(section)) {
            viewHolder.tvTitle.setVisibility(View.VISIBLE);
            viewHolder.tvTitle.setText(mContent.getSortLetters());
        } else {
            viewHolder.tvTitle.setVisibility(View.GONE);
        }
        final String mobile = mContent.getMobile();
        viewHolder.user_mobile.setText(mobile);
        viewHolder.user_nickname.setText(mContent.getComment());
        int state = 2;
        if (!TextUtils.isEmpty(mContent.getState())) {
            state = Integer.valueOf(mContent.getState());
        }
        switch (state) {
            case 0:
                viewHolder.user_status.setText("添加");
                viewHolder.user_status.setTextColor(Color.parseColor("#ffffff"));
                viewHolder.user_status.setBackgroundResource(R.drawable.rect_round_blue);
                break;
            case 1:
                viewHolder.user_status.setText("已禁用");
                viewHolder.user_status.setTextColor(Color.parseColor("#333b46"));
                viewHolder.user_status.setBackgroundResource(R.drawable.shape_white_border);
                break;
            case 2:
                viewHolder.user_status.setText("邀请");
                viewHolder.user_status.setTextColor(Color.parseColor("#333b46"));
                viewHolder.user_status.setBackgroundResource(R.drawable.shape_white_border);
                break;
            case 3:
                viewHolder.user_status.setText("已添加");
                viewHolder.user_status.setTextColor(Color.parseColor("#D4D9DC"));
                viewHolder.user_status.setBackgroundResource(R.color.white);
                break;

        }
        final int finalState = state;
        viewHolder.user_status.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (finalState == 0) {
                    Intent intent = new Intent(mContext, IMApplyFriendActivity.class);
                    intent.putExtra(IMInviteRegisterActivity.USERPHONE, mContent.getMobile());
                    intent.putExtra(IMInviteRegisterActivity.USERNAME, mContent.getName());
                    intent.putExtra(IMInviteRegisterActivity.USERNICKNAME, mContent.getNick_name());
                    intent.putExtra(IMInviteRegisterActivity.USERGENDER, mContent.getGender());
                    intent.putExtra(IMInviteRegisterActivity.USERPORTRAIT, mContent.getPortrait());
                    intent.putExtra(IMFriendInforActivity.USERUUID, mContent.getUuid());
                    intent.putExtra(IMInviteRegisterActivity.USECOMMUNITYNAME, mContent.getCommunity_name());
                    mContext.startActivity(intent);
                } else if (finalState == 2) {
                    ((IMMobileBookActivity) mContext).inviteRegister(mobile);
                }
            }
        });
        return view;

    }


    final static class ViewHolder {
        TextView tvTitle;
        TextView user_nickname;
        TextView user_mobile;
        TextView user_status;
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