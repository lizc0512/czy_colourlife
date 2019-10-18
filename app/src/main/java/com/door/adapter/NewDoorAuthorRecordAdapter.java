package com.door.adapter;

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


public class NewDoorAuthorRecordAdapter extends RecyclerView.Adapter<NewDoorAuthorRecordAdapter.NewDoorAuthorHolder> {

    public List<ApplyAuthorizeRecordEntity.ContentBean.AuthorizationListBean> authorizationList;
    private OnItemClickListener onClickListener;

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    public NewDoorAuthorRecordAdapter(List<ApplyAuthorizeRecordEntity.ContentBean.AuthorizationListBean> authorizationList) {
        this.authorizationList = authorizationList;
    }


    @Override
    public NewDoorAuthorRecordAdapter.NewDoorAuthorHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_door_authorizerecord, parent, false);
        NewDoorAuthorRecordAdapter.NewDoorAuthorHolder doorOfernViewHolder = new NewDoorAuthorRecordAdapter.NewDoorAuthorHolder(view);
        doorOfernViewHolder.onClickListener = onClickListener;
        return doorOfernViewHolder;
    }

    @Override
    public void onBindViewHolder(NewDoorAuthorRecordAdapter.NewDoorAuthorHolder holder, final int position) {
        ApplyAuthorizeRecordEntity.ContentBean.AuthorizationListBean authorizationListBean = authorizationList.get(position);
        holder.tv_authorize_name.setText(authorizationListBean.getToname());
        holder.tv_authorize_time.setText("授权时间:" + TimeUtil.getDateToString(authorizationListBean.getCreationtime()));
        String community_name = authorizationListBean.getName();
        String identify_name;
        switch (authorizationListBean.getUsertype()) {
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
        holder.tv_authorize_address.setText(identify_name + community_name);
        String is_delete = authorizationListBean.getIsdeleted();
        if ("1".equals(is_delete)) {
            holder.tv_authorize_duration.setTextColor(Color.parseColor("#A9AFB8"));
            holder.tv_authorize_duration.setText(holder.itemView.getResources().getString(R.string.door_apply_timeout));
        } else {
            holder.tv_authorize_duration.setTextColor(Color.parseColor("#13CE66"));
            int stopTime = authorizationListBean.getStoptime();
            if (stopTime == 0) {
                holder.tv_authorize_duration.setText("永久");
            } else {
                int distance = stopTime - authorizationListBean.getStarttime();
                if (distance < 3600 * 2) {
                    holder.tv_authorize_duration.setText("2小时");
                } else if (distance <= 3600 * 24) {
                    holder.tv_authorize_duration.setText("1天");
                } else if (distance <= 7 * 3600 * 24) {
                    holder.tv_authorize_duration.setText("7天");
                } else if (distance <= 30 * 3600 * 24) {
                    holder.tv_authorize_duration.setText("1个月");
                } else if (distance <= 30 * 3600 * 24 * 6) {
                    holder.tv_authorize_duration.setText("6个月");
                } else {
                    holder.tv_authorize_duration.setText("1年");
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        return authorizationList == null ? 0 : authorizationList.size();

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
