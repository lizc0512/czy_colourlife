package com.door.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.door.entity.ApplyAuthorizeRecordEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/*
别人对你的申请记录
 */
public class NewDoorApplyRecordAdapter extends RecyclerView.Adapter<NewDoorApplyRecordAdapter.NewDoorAuthorHolder> {

    public List<ApplyAuthorizeRecordEntity.ContentBean.ApplyListBean> applyListBeanList;
    private OnItemClickListener onClickListener;

    public NewDoorApplyRecordAdapter(List<ApplyAuthorizeRecordEntity.ContentBean.ApplyListBean> applyListBeanList) {
        this.applyListBeanList = applyListBeanList;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @Override
    public NewDoorApplyRecordAdapter.NewDoorAuthorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_door_authorizerecord, parent, false);
        NewDoorApplyRecordAdapter.NewDoorAuthorHolder doorOfernViewHolder = new NewDoorApplyRecordAdapter.NewDoorAuthorHolder(view);
        doorOfernViewHolder.onClickListener = onClickListener;
        return doorOfernViewHolder;
    }

    @Override
    public void onBindViewHolder(NewDoorApplyRecordAdapter.NewDoorAuthorHolder holder, final int position) {
        ApplyAuthorizeRecordEntity.ContentBean.ApplyListBean applyListBean = applyListBeanList.get(position);
        holder.tv_authorize_name.setText(applyListBean.getToname());
        holder.tv_authorize_time.setText("申请时间:"+TimeUtil.getDateToString(applyListBean.getCreationtime()));
        String community_name = applyListBean.getName();
        String identify_name;
        switch (applyListBean.getUsertype()) {
            case "1":
                identify_name = "业主  ";
                break;
            case "3":
                identify_name = "租客  ";
                break;
            case "4":
                identify_name = "访客  ";
                break;
            default:
                identify_name = "家属  ";
                break;
        }
        String type = applyListBean.getType();
        String isdelete = applyListBean.getIsdeleted();
        String statuString = null;
        Context mContext = holder.itemView.getContext();
        if ("1".equals(type)) {
            if ("1".equals(isdelete)) {
                //拒绝
                holder.tv_authorize_duration.setTextColor(Color.parseColor("#DE4A47"));
                statuString = mContext.getResources().getString(R.string.door_apply_refuse);
            } else {
                // 未批复
                holder.tv_authorize_duration.setTextColor(Color.parseColor("#3385FF"));
                statuString = mContext.getResources().getString(R.string.door_apply_wait);
            }
        } else if ("2".equals(type)) {
            if ("1".equals(isdelete)) {
                // 已失效
                holder.tv_authorize_duration.setTextColor(Color.parseColor("#A9AFB8"));
                statuString = mContext.getResources().getString(R.string.door_apply_timeout);
            } else {
                // 已批复
                holder.tv_authorize_duration.setTextColor(Color.parseColor("#13CE66"));
                statuString = mContext.getResources().getString(R.string.door_apply_pass);

            }
        }
        holder.tv_authorize_duration.setText(statuString);
        holder.tv_authorize_address.setText(identify_name + community_name);
    }

    @Override
    public int getItemCount() {
        return applyListBeanList == null ? 0 : applyListBeanList.size();

    }

    public static class NewDoorAuthorHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tv_authorize_name;
        private TextView tv_authorize_address;
        private TextView tv_authorize_time;
        private TextView tv_authorize_duration;
        OnItemClickListener onClickListener;


        public NewDoorAuthorHolder(View itemView) {
            super(itemView);
            tv_authorize_name = itemView.findViewById(R.id.tv_authorize_name);
            tv_authorize_address = itemView.findViewById(R.id.tv_authorize_address);
            tv_authorize_time = itemView.findViewById(R.id.tv_authorize_time);
            tv_authorize_duration = itemView.findViewById(R.id.tv_authorize_duration);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onClickListener.onItemClick(getAdapterPosition());
        }
    }

}
