package com.customerInfo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.customerInfo.protocol.AddressListEntity;

import java.util.List;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/2 16:58
 * @change
 * @chang time
 * @class describe  新改版地址中心
 */

public class SwitchAddressAdapter extends RecyclerView.Adapter<SwitchAddressAdapter.AddressViewHolder> {

    private List<AddressListEntity.ContentBean.DataBean> communityBeanList;
    private Context mContext;
    private String defaultId;

    public SwitchAddressAdapter(Context mContext, List<AddressListEntity.ContentBean.DataBean> communityBeanList) {
        this.communityBeanList = communityBeanList;
        this.mContext = mContext;
    }

    @Override
    public SwitchAddressAdapter.AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_customer_address, parent, false);
        return new AddressViewHolder(view);
    }

    public void setDefaultAddressId(String defaultAddressId) {
        defaultId = defaultAddressId;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(SwitchAddressAdapter.AddressViewHolder holder, int position) {
        AddressListEntity.ContentBean.DataBean communityBean = communityBeanList.get(position);
        holder.tvCommunity.setText(communityBean.getCommunity_name());
        holder.tvBuild.setText(communityBean.getBuild_name());
        holder.build_room.setText(communityBean.getUnit_name() + communityBean.getRoom_name());
        holder.tvAddress.setText(communityBean.getAddress());
        final String communityId = communityBean.getId();
        if (defaultId.equals(communityId)) {
            holder.imgSel.setImageResource(R.drawable.f3_sel);
        } else {
            holder.imgSel.setImageResource(R.drawable.f3_sel_circle);
        }
        holder.imgSel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                defaultId = communityId;
                notifyDataSetChanged();
            }
        });
    }

    public String getDefaultId() {
        return defaultId;
    }

    @Override
    public int getItemCount() {
        return communityBeanList == null ? 0 : communityBeanList.size();
    }


    class AddressViewHolder extends RecyclerView.ViewHolder {
        public TextView tvCommunity;
        public TextView tvBuild;
        public TextView tvAddress;
        public TextView build_room;
        public ImageView imgSel;

        public AddressViewHolder(View itemView) {
            super(itemView);
            tvCommunity = (TextView) itemView.findViewById(R.id.community_tv);
            tvBuild = (TextView) itemView.findViewById(R.id.build_tv);
            build_room = (TextView) itemView.findViewById(R.id.build_room);
            tvAddress = (TextView) itemView.findViewById(R.id.address_tv);
            imgSel = (ImageView) itemView.findViewById(R.id.sel_img);
        }
    }


}
