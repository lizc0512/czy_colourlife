package com.customerInfo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.customerInfo.protocol.ComBuildUnitRoomEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
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

public class ComBuildUnitRoomAdapter extends RecyclerView.Adapter<ComBuildUnitRoomAdapter.AddressViewHolder> {

    private List<ComBuildUnitRoomEntity.ContentBean.DataBean> buildUnitList;
    private Context mContext;
    private OnItemClickListener onClickListener;

    public ComBuildUnitRoomAdapter(Context mContext, List<ComBuildUnitRoomEntity.ContentBean.DataBean> buildUnitList) {
        this.buildUnitList = buildUnitList;
        this.mContext = mContext;
    }

    @Override
    public ComBuildUnitRoomAdapter.AddressViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_area, parent, false);
        AddressViewHolder addressViewHolder = new AddressViewHolder(view);
        addressViewHolder.onClickListener = onClickListener;
        return addressViewHolder;
    }


    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public void onBindViewHolder(ComBuildUnitRoomAdapter.AddressViewHolder holder, int position) {
        ComBuildUnitRoomEntity.ContentBean.DataBean communityBean = buildUnitList.get(position);
        holder.textView.setText(communityBean.getName());
    }

    @Override
    public int getItemCount() {
        return buildUnitList == null ? 0 : buildUnitList.size();
    }


    class AddressViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public TextView textView;
        OnItemClickListener onClickListener;

        public AddressViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            textView = (TextView) itemView.findViewById(R.id.textView);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
