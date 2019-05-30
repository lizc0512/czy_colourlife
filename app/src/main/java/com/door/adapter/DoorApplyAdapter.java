package com.door.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.adapter.BeeBaseAdapter;
import com.door.entity.DoorApplyListEntity;


import java.util.List;

import cn.net.cyberway.R;


/**
 * @name ${yuansk}
 * @class name：${PACKAGE_NAME}
 * @class describe
 * @anthor ${USER} QQ:827194927
 * @time ${DATE} ${TIME}
 * @change
 * @chang time
 * @class describe
 */
public class DoorApplyAdapter extends BeeBaseAdapter {
    LayoutInflater mInflater = null;

    public DoorApplyAdapter(Context context, List<DoorApplyListEntity.ContentBean.ListBean> list) {
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
        return (BeeCellHolder) holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        DoorApplyListEntity.ContentBean.ListBean authorizationListResp = (DoorApplyListEntity.ContentBean.ListBean) dataList.get(position);
        Holder holder = (Holder) h;
        String name = authorizationListResp.getFromname();
        String statuString = "";
        String type = authorizationListResp.getType();
        String isdelete = authorizationListResp.getIsdeleted();
        if ("1".equals(type)) {

            // 默认 未批复
            if ("1".equals(isdelete)) {
                holder.txt_status.setTextColor(mContext.getResources()
                        .getColor(R.color.edit_text_color));
                statuString = "拒绝";
            } else {
                // 未批复
                holder.txt_status.setTextColor(mContext.getResources()
                        .getColor(R.color.lightgred_color));
                statuString = "未批复";
            }
        } else if ("2".equals(type)) {

            if ("1".equals(isdelete)) {
                // 已失效
                holder.txt_status.setTextColor(mContext.getResources()
                        .getColor(R.color.edit_text_color));
                statuString = "已失效";
            } else if ("2".equals(type)) {
                // 已批复
                String auType = authorizationListResp.getUsertype();
                // 绿色
                holder.txt_status.setTextColor(mContext.getResources()
                        .getColor(R.color.lightggray_color));

                statuString = "通过";

            }
        }
        holder.txt_mobile.setText(name);
        holder.txt_status.setText(statuString);
        holder.txt_date.setText(TimeUtil.getDateToString(authorizationListResp.getCreationtime()));
        holder.txt_name.setText(authorizationListResp.getName());
        return cellView;
    }


    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.door_apply_item, null);
    }

    public class Holder extends BeeCellHolder {
        public TextView txt_mobile;//电话号码
        public TextView txt_status;//状态
        public TextView txt_date;//时间
        private TextView txt_name;//小区
    }
}
