package com.invite.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.invite.protocol.InviteInviteRecodeEntity;

import java.util.List;

import cn.net.cyberway.R;

/**
 * Created by hxg on 2019/5/27 09:47
 */
public class InviteMyAdapter extends BeeBaseAdapter {

    public Context mContext;
    private List<InviteInviteRecodeEntity.ContentBean.ListBean> mList;

    public InviteMyAdapter(Context context, List<InviteInviteRecodeEntity.ContentBean.ListBean> list) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
    }

    public class ViewHolder extends BeeCellHolder {
        private TextView tv_invite_phone;
        private TextView tv_invite_time;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.item_my_invite, null);
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder holder = new ViewHolder();
        holder.tv_invite_phone = cellView.findViewById(R.id.tv_invite_phone);
        holder.tv_invite_time = cellView.findViewById(R.id.tv_invite_time);
        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        ViewHolder holder = (ViewHolder) h;
        InviteInviteRecodeEntity.ContentBean.ListBean item = mList.get(position);
        holder.tv_invite_phone.setText(item.getMobile());
        holder.tv_invite_time.setText(item.getCreate_time());

        return cellView;
    }
}
