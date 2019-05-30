package com.invite.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.user.entity.InviteRecordEntity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import cn.net.cyberway.R;

/**
 * 邀请记录 & 邀请战绩adapter
 * Created by chenql on 16/1/8.
 */
public class InviteRecordAdapter extends BeeBaseAdapter {

    private Boolean isSuccess = false;// 默认为邀请记录

    public InviteRecordAdapter(Context context, List list, Boolean isSuccess) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        dataList = list;
        this.isSuccess = isSuccess;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder holder = new ViewHolder();

        holder.tv_invite_time = (TextView) cellView.findViewById(R.id.tv_invite_time);
        holder.tv_mobile = (TextView) cellView.findViewById(R.id.tv_mobile);
        holder.tv_register_status = (TextView) cellView.findViewById(R.id.tv_register_status);

        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        ViewHolder holder = (ViewHolder) h;
        if (isSuccess) {
            if (null != dataList && dataList.size() > 0) {
                InviteRecordEntity.ContentBean contentBean = (InviteRecordEntity.ContentBean) dataList.get(position);
                holder.tv_invite_time.setText(parseDate(contentBean.getTime_create()));
                holder.tv_mobile.setText(contentBean.getMobile());
            }
        } else {
            if (null != dataList && dataList.size() > 0) {
                InviteRecordEntity.ContentBean contentBean = (InviteRecordEntity.ContentBean) dataList.get(position);
                holder.tv_invite_time.setText(parseDate(contentBean.getTime_create()));
                holder.tv_mobile.setText(contentBean.getMobile());
                int status = contentBean.getInvite_state();
                switch (status) {
                    case 1:
                        holder.tv_register_status.setText("已注册");
                        break;

                    case 2:
                        holder.tv_register_status.setText("注册中");
                        break;

                    case 3:
                        holder.tv_register_status.setText("已失效");
                        break;

                    case 4:
                        holder.tv_register_status.setText("邀请无效");
                        break;
                    default:
                        holder.tv_register_status.setText("注册中");
                        break;
                }
            }
        }
        return cellView;
    }

    private String parseDate(String date) {

        long time = Long.parseLong(date) * 1000;
        if (time != 0) {
            @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd\n HH:mm");
            return df.format(new Date(time));

        }
        return "";
    }

    @SuppressLint("InflateParams")
    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.list_item_invite_record, null);
    }

    private class ViewHolder extends BeeCellHolder {
        private TextView tv_invite_time;
        private TextView tv_mobile;
        private TextView tv_register_status;
    }
}
