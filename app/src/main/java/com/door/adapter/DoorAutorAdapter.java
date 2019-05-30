package com.door.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.adapter.BeeBaseAdapter;
import com.door.entity.DoorAuthorListEntity;


import java.util.List;

import cn.net.cyberway.R;


/**
 * Created by chengyun on 2016/1/5.
 */
public class DoorAutorAdapter extends BeeBaseAdapter {
    LayoutInflater mInflater = null;

    public DoorAutorAdapter(Context context, List<DoorAuthorListEntity.ContentBean.ListBean> list) {
        this.mContext = context;
        this.dataList = list;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        Holder holder = new Holder();
        holder.txt_mobile = (TextView) cellView.findViewById(R.id.txt_mobile);
        holder.txt_status = (TextView) cellView.findViewById(R.id.txt_status);
        holder.txt_date = (TextView) cellView.findViewById(R.id.txt_date);
        holder.txt_name = (TextView) cellView.findViewById(R.id.txt_name);
        holder.rl_name = (RelativeLayout) cellView.findViewById(R.id.rl_name);
        return (BeeCellHolder) holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        DoorAuthorListEntity.ContentBean.ListBean authorizationListResp = (DoorAuthorListEntity.ContentBean.ListBean) dataList.get(position);
        Holder holder = (Holder) h;

        String name = authorizationListResp.getToname();
        String statuString = "";
        String type = authorizationListResp.getType();
        String isdelete = authorizationListResp.getIsdeleted();
        if ("1".equals(type)) {

            // 默认 未批复
            if ("1".equals(isdelete)) {
                holder.txt_status.setTextColor(mContext.getResources()
                        .getColor(R.color.edit_text_color));
                statuString =mContext.getResources().getString(R.string.door_apply_refuse);
            } else {
                // 未批复
                holder.txt_status.setTextColor(mContext.getResources()
                        .getColor(R.color.lightgred_color));
                statuString =mContext.getResources().getString(R.string.door_apply_wait);
            }
        } else if ("2".equals(type)) {

            if ("1".equals(isdelete)) {
                // 已失效
                holder.txt_status.setTextColor(mContext.getResources()
                        .getColor(R.color.edit_text_color));
                statuString =mContext.getResources().getString(R.string.door_apply_timeout);
            } else if ("2".equals(type)) {
                // 已批复
                String auType = authorizationListResp.getUsertype();
                // 绿色
                holder.txt_status.setTextColor(mContext.getResources()
                        .getColor(R.color.lightggray_color));
                statuString =mContext.getResources().getString(R.string.door_apply_pass);

            }
        }
        String date = authorizationListResp.getCreationtime() + "000";
        holder.txt_mobile.setText(name);
        holder.txt_status.setText(statuString);
        holder.txt_date.setText(TimeUtil.getDateToString(authorizationListResp.getCreationtime()));
        holder.txt_name.setText(authorizationListResp.getName());
        if (authorizationListResp.getName() == null) {
            holder.rl_name.setVisibility(View.GONE);
        } else {
            holder.rl_name.setVisibility(View.VISIBLE);
        }

        return cellView;
    }


    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.door_author_item, null);
    }

    public class Holder extends BeeCellHolder {
        public TextView txt_mobile;//电话号码
        public TextView txt_status;//状态
        public TextView txt_date;//时间
        private TextView txt_name;//小区
        private RelativeLayout rl_name;
    }
}
