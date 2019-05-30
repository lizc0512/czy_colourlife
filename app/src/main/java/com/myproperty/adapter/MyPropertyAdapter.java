package com.myproperty.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.customerInfo.protocol.AddressListEntity;
import com.myproperty.activity.MyPropertyActivity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

public class MyPropertyAdapter extends RecyclerView.Adapter<MyPropertyAdapter.DefaultViewHolder> {

    private List<AddressListEntity.ContentBean.DataBean> communityBeanList;
    public Context mContext;
    private String defaultId;
    public OnItemClickListener onClickListener;

    public MyPropertyAdapter(Context mContext, List<AddressListEntity.ContentBean.DataBean> communityBeanList) {
        this.communityBeanList = communityBeanList;
        this.mContext = mContext;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public MyPropertyAdapter.DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_property, parent, false);
        MyPropertyAdapter.DefaultViewHolder viewHolder = new MyPropertyAdapter.DefaultViewHolder(view);
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyPropertyAdapter.DefaultViewHolder holder, int position) {
        final Context mContext = holder.itemView.getContext();
        AddressListEntity.ContentBean.DataBean communityBean = communityBeanList.get(position);
        holder.tv_community_name.setText(communityBean.getCommunity_name());
        holder.tv_detail_name.setText(communityBean.getAddress());
        final String communityId = communityBean.getId();
        if (defaultId.equals(communityId)) {
            holder.iv_current.setVisibility(View.VISIBLE);
        } else {
            holder.iv_current.setVisibility(View.GONE);
        }
        holder.tv_worker.setVisibility("1".equals(communityBean.getEmployee()) ? View.VISIBLE : View.GONE);//员工 1：是，2：否
        holder.tv_real.setVisibility("1".equals(communityBean.getAuthentication()) ? View.VISIBLE : View.GONE);// 已认证 1：是，2：否
        holder.cv_item.setOnClickListener(v -> ((MyPropertyActivity) mContext).select(position));

        if (!TextUtils.isEmpty(communityBean.getIdentity_name())) {
//            if ("待审核".equals(communityBean.getIdentity_state_name())) {
//                holder.tv_id.setVisibility(View.GONE);
//                holder.tv_identity.setText(communityBean.getIdentity_name());//业主 白背景
//                holder.tv_identity.setVisibility(View.VISIBLE);
//            } else {
//                holder.tv_identity.setVisibility(View.GONE);
//                holder.tv_id.setText(communityBean.getIdentity_name());//黄背景
//                holder.tv_id.setVisibility(View.VISIBLE);
//            }
            holder.tv_id.setText(communityBean.getIdentity_name());//黄背景
            holder.tv_id.setVisibility(View.VISIBLE);
        } else {
            holder.tv_id.setVisibility(View.GONE);
//            holder.tv_identity.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return communityBeanList == null ? 0 : communityBeanList.size();
    }

    public static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_community_name;
        TextView tv_detail_name;
        ImageView iv_select;
        TextView tv_delete;
        TextView tv_edit;
        TextView tv_worker;
        TextView tv_real;
        //        TextView tv_identity;
        TextView tv_id;
        CardView cv_item;
        ImageView iv_current;

        OnItemClickListener onClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_community_name = itemView.findViewById(R.id.tv_community_name);
            tv_detail_name = itemView.findViewById(R.id.tv_detail_name);
            iv_select = itemView.findViewById(R.id.iv_select);
            tv_edit = itemView.findViewById(R.id.tv_edit);
            tv_delete = itemView.findViewById(R.id.tv_delete);
            tv_worker = itemView.findViewById(R.id.tv_worker);
            tv_real = itemView.findViewById(R.id.tv_real);
//            tv_identity = itemView.findViewById(R.id.tv_identity);
            tv_id = itemView.findViewById(R.id.tv_id);
            cv_item = itemView.findViewById(R.id.cv_item);
            iv_current = itemView.findViewById(R.id.iv_current);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }

    public void setDefaultAddressId(String defaultAddressId) {
        defaultId = defaultAddressId;
        notifyDataSetChanged();
    }

}