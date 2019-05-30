package com.eparking.adapter;

import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.eparking.protocol.EparkingServiceEntity;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import q.rorbin.badgeview.QBadgeView;

/**
 * @name ${yuansk}
 * @class name：com.eparking.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/15 17:47
 * @change
 * @chang time
 * @class describe  首页 服务
 */
public class EparkingServiceAdapter extends RecyclerView.Adapter<EparkingServiceAdapter.ServiceViewHolder> {

    private List<EparkingServiceEntity.ContentBean> serviceEntityList;
    private OnItemClickListener onClickListener;

    public EparkingServiceAdapter(List<EparkingServiceEntity.ContentBean> serviceEntityList) {
        this.serviceEntityList = serviceEntityList;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @NonNull
    @Override
    public EparkingServiceAdapter.ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.eparking_homeservice_item, parent, false);
        ServiceViewHolder serviceViewHolder = new ServiceViewHolder(v);
        serviceViewHolder.onClickListener = onClickListener;
        return serviceViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EparkingServiceAdapter.ServiceViewHolder holder, int position) {
        EparkingServiceEntity.ContentBean esContentBean = serviceEntityList.get(position);
        GlideImageLoader.loadImageDefaultDisplay(holder.itemView.getContext(), esContentBean.getLogo_url(), holder.iv_service_logo, R.drawable.eparking_img_gift, R.drawable.eparking_img_gift);
        holder.tv_service_name.setText(esContentBean.getTitle());
        holder.tv_service_desc.setText(esContentBean.getRemark());
        holder.service_sign.setVisibility(View.GONE);
    }

    @Override
    public int getItemCount() {
        return serviceEntityList==null?0:serviceEntityList.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_service_logo;
        TextView tv_service_name;
        TextView tv_service_desc;
        LinearLayout item_service_layout;
        QBadgeView service_sign;
        OnItemClickListener onClickListener;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            item_service_layout = (LinearLayout) itemView.findViewById(R.id.item_service_layout);
            iv_service_logo = (ImageView) itemView.findViewById(R.id.iv_service_logo);
            tv_service_name = (TextView) itemView.findViewById(R.id.tv_service_name);
            tv_service_desc = (TextView) itemView.findViewById(R.id.tv_service_desc);
            service_sign = new QBadgeView(itemView.getContext());
            service_sign.bindTarget(item_service_layout);
            service_sign.setBadgeGravity(Gravity.TOP | Gravity.END);
            service_sign.setBadgeTextSize(10f, true);
            service_sign.setGravityOffset(10, 3, true);
            service_sign.setBadgeBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.color_fe6262));
            service_sign.setShowShadow(false);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
