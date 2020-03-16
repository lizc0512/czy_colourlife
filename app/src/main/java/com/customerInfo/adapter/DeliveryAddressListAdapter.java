package com.customerInfo.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.StringUtil;
import com.customerInfo.activity.DeliveryAddressIncreaseActivity;
import com.customerInfo.activity.DeliveryAddressListActivity;
import com.customerInfo.protocol.DeliveryAddressListEnity;

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

public class DeliveryAddressListAdapter extends RecyclerView.Adapter<DeliveryAddressListAdapter.AddressViewHolder> {

    private List<DeliveryAddressListEnity.ContentBean> deliveryAddressList;
    private Context mContext;


    public DeliveryAddressListAdapter(Context mContext, List<DeliveryAddressListEnity.ContentBean> deliveryAddressList) {
        this.deliveryAddressList = deliveryAddressList;
        this.mContext = mContext;
    }

    @Override
    public DeliveryAddressListAdapter.AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_delivery_list, parent, false);
        return new AddressViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DeliveryAddressListAdapter.AddressViewHolder holder, int position) {
        final DeliveryAddressListEnity.ContentBean contentBean = deliveryAddressList.get(position);
        holder.tv_delivery_name.setText(contentBean.getName());
        holder.tv_delivery_phone.setText(StringUtil.getRecordPhone(contentBean.getMobile()));
        holder.tv_delivery_address.setText(contentBean.getProvince() + contentBean.getCity() + contentBean.getCounty()
                + contentBean.getTown() + contentBean.getCommunity() + contentBean.getAddress());
        holder.delivery_address_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DeliveryAddressListActivity) mContext).getSingleDeliveryAddressInfor(contentBean.getAddress_uuid());
            }
        });
        holder.tv_del_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((DeliveryAddressListActivity) mContext).delSingleDeliveryAddress(contentBean.getAddress_uuid());
            }
        });
        holder.tv_edit_address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, DeliveryAddressIncreaseActivity.class);
                intent.putExtra(DeliveryAddressIncreaseActivity.DELIVERYNAME, contentBean.getName());
                intent.putExtra(DeliveryAddressIncreaseActivity.DELIVERYPHONE, contentBean.getMobile());
                intent.putExtra(DeliveryAddressIncreaseActivity.PROVINCEUUID, contentBean.getProvince_uuid());
                intent.putExtra(DeliveryAddressIncreaseActivity.CITYUUID, contentBean.getCity_uuid());
                intent.putExtra(DeliveryAddressIncreaseActivity.COUNTYUUID, contentBean.getCounty_uuid());
                intent.putExtra(DeliveryAddressIncreaseActivity.TOWNUUID, contentBean.getTown_uuid());
                intent.putExtra(DeliveryAddressIncreaseActivity.COMMUNITYUUID, contentBean.getCommunity_uuid());
                intent.putExtra(DeliveryAddressIncreaseActivity.DELIVERYREGION, contentBean.getProvince() + contentBean.getCity()
                        + contentBean.getCounty() + contentBean.getTown() + contentBean.getCommunity());
                intent.putExtra(DeliveryAddressIncreaseActivity.DELIVERYADDRESS, contentBean.getAddress());
                intent.putExtra(DeliveryAddressListActivity.DELIVERYID, contentBean.getAddress_uuid());
                ((DeliveryAddressListActivity)mContext).startActivityForResult(intent, 1000);
            }
        });
    }


    @Override
    public int getItemCount() {
        return deliveryAddressList == null ? 0 : deliveryAddressList.size();
    }


    class AddressViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout delivery_address_layout;
        public TextView tv_delivery_name;
        public TextView tv_delivery_phone;
        public TextView tv_delivery_address;
        public TextView tv_del_address;
        public TextView tv_edit_address;

        public AddressViewHolder(View itemView) {
            super(itemView);
            delivery_address_layout = (LinearLayout) itemView.findViewById(R.id.delivery_address_layout);
            tv_delivery_name = (TextView) itemView.findViewById(R.id.tv_delivery_name);
            tv_delivery_phone = (TextView) itemView.findViewById(R.id.tv_delivery_phone);
            tv_delivery_address = (TextView) itemView.findViewById(R.id.tv_delivery_address);
            tv_del_address = (TextView) itemView.findViewById(R.id.tv_del_address);
            tv_edit_address = (TextView) itemView.findViewById(R.id.tv_edit_address);
        }
    }
}
