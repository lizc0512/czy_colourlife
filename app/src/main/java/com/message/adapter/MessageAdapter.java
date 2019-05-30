package com.message.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.Utils.DateUtils;
import com.BeeFramework.adapter.BeeBaseAdapter;

import com.message.model.SHOPMSG;
import com.message.model.SYSTEMMSG;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 系统通知/商家消息列表adapter
 * Created by chenql on 16/1/13.
 */
public class MessageAdapter extends BeeBaseAdapter {

    /**
     * 系统通知
     */
    public static final String SYSTEM_MSG = "SYSTEM_MSG";

    /**
     * 商家消息
     */
    public static final String SHOP_MSG = "SHOP_MSG";

    private String messageType = SYSTEM_MSG;

    public MessageAdapter(Context context, List data, String messageType) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        dataList = data;
        this.messageType = messageType;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder holder = new ViewHolder();

        holder.tv_content = (TextView) cellView.findViewById(R.id.tv_content);
        holder.tv_type = (TextView) cellView.findViewById(R.id.tv_type);
        holder.tv_time = (TextView) cellView.findViewById(R.id.tv_time);

        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {

        String type = "", content = "", time = "", isRead = "";

        if (messageType.equals(SYSTEM_MSG)) {
            // 系统通知
            SYSTEMMSG systemMsg = (SYSTEMMSG) dataList.get(position);
            type = systemMsg.title;
            content = systemMsg.content;
            time = DateUtils.phpToString(systemMsg.create_time, DateUtils.DATE_FORMAT_DAY);
            isRead = systemMsg.is_read;

        } else if (messageType.equals(SHOP_MSG)) {
            // 商家消息
            SHOPMSG shopMsg = (SHOPMSG) dataList.get(position);
            type = shopMsg.name;
            content = shopMsg.content;
            time = DateUtils.phpToString(shopMsg.creationtime, DateUtils.DATE_FORMAT_DAY);
            isRead = shopMsg.isread;
        }

        ViewHolder holder = (ViewHolder) h;

        holder.tv_content.setText(content);
        holder.tv_time.setText(time);
        holder.tv_type.setText(type);

        if ("0".equals(isRead)) {
            // 未读
            holder.tv_content.setTextColor(mContext.getResources().getColor(R.color.black_text_color));
        } else {
            // 已读
            holder.tv_content.setTextColor(mContext.getResources().getColor(R.color.gray_text_color));
        }

        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.list_item_message, null);
    }

    private class ViewHolder extends BeeCellHolder {
        private TextView tv_type;
        private TextView tv_time;
        private TextView tv_content;
    }
}
