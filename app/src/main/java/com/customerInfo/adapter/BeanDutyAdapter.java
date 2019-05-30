package com.customerInfo.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.customerInfo.activity.CustomerColourBeanActivity;
import com.customerInfo.protocol.DutyListEntity;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 彩豆任务适配器
 * <p>
 * Created by hxg on 2019/4/16 09:47
 */
public class BeanDutyAdapter extends BeeBaseAdapter {

    public BeanDutyAdapter(Context c, List dataList) {
        super(c, dataList);
    }

    public class ViewHolder extends BeeCellHolder {
        LinearLayout iv_title;
        TextView tv_title;
        TextView tv_add_bean;
        ImageView iv_status;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder holder = new ViewHolder();
        holder.iv_title = cellView.findViewById(R.id.iv_title);
        holder.tv_title = cellView.findViewById(R.id.tv_title);
        holder.tv_add_bean = cellView.findViewById(R.id.tv_add_bean);
        holder.iv_status = cellView.findViewById(R.id.iv_status);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        ViewHolder holder = (ViewHolder) h;
        final DutyListEntity.ContentBean.OnceBean item = (DutyListEntity.ContentBean.OnceBean) dataList.get(position);
        holder.iv_title.setBackground(null);

        if (!TextUtils.isEmpty(item.getImg())) {
            GlideImageLoader.loadWrapHeightLayouBg(mContext, item.getImg(), holder.iv_title);
        } else {
            switch (item.getName()) {
                case "设置头像"://设置头像
                    holder.iv_title.setBackgroundResource(R.drawable.icon_bean_set_head);
                    break;
                case "设置昵称"://设置昵称
                    holder.iv_title.setBackgroundResource(R.drawable.icon_bean_set_nickname);
                    break;
                case "设置性别"://设置性别
                    holder.iv_title.setBackgroundResource(R.drawable.icon_bean_set_sex);
                    break;
                case "设置提醒"://消息提醒
                    holder.iv_title.setBackgroundResource(R.drawable.icon_bean_msg_remind);
                    break;
                case "消息推送"://推送提醒
                    holder.iv_title.setBackgroundResource(R.drawable.icon_bean_msg_push);
                    break;
                case "人脸采集"://人脸采集
                    holder.iv_title.setBackgroundResource(R.drawable.icon_bean_head_collection);
                    break;
                case "绑定地址"://绑定地址
                    holder.iv_title.setBackgroundResource(R.drawable.icon_bean_bind_address);
                    break;
                case "绑定车辆"://绑定车辆
                    holder.iv_title.setBackgroundResource(R.drawable.icon_bean_bind_car);
                    break;
                default:
                    holder.iv_title.setBackgroundResource(R.drawable.default_image);
                    break;
            }
        }
        holder.tv_title.setText(item.getName());

        String number = "0";
        if (item.getQuantity() > 0) {
            number = "+" + item.getQuantity();
        } else if (item.getQuantity() < 0) {
            number = "" + item.getQuantity();
        }
        holder.tv_add_bean.setText(number);

        if (item.getIs_finish() == 1) {//已完成
            holder.iv_status.setBackgroundResource(R.drawable.icon_bean_todo);
            holder.iv_status.setOnClickListener(null);
        } else {//去完成
            holder.iv_status.setBackgroundResource(R.drawable.icon_bean_done);
            holder.iv_status.setOnClickListener(v -> ((CustomerColourBeanActivity) mContext).todo(item.getUrl()));
        }

        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.item_bean_duty, null);
    }
}
