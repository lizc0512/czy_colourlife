package com.invite.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.invite.protocol.InviteDetailListEntity;

import java.util.List;

import cn.net.cyberway.R;

/**
 * Created by hxg on 2019/6/5 09:47
 */
public class InviteProfitAdapter extends BeeBaseAdapter {

    public Context mContext;
    private List<InviteDetailListEntity.ContentBean.ListBean> mList;
    private int status;

    public InviteProfitAdapter(Context context, List<InviteDetailListEntity.ContentBean.ListBean> list, int status) {
        super(context, list);
        this.mContext = context;
        this.mList = list;
        this.status = status;
    }

    public class ViewHolder extends BeeCellHolder {
        private TextView tv_phone;
        private TextView tv_time;
        private TextView tv_add_red;
        private TextView tv_add_red3;
        private TextView tv_invite;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.item_profit_invite, null);
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        ViewHolder holder = new ViewHolder();
        holder.tv_phone = cellView.findViewById(R.id.tv_phone);
        holder.tv_time = cellView.findViewById(R.id.tv_time);
        holder.tv_add_red = cellView.findViewById(R.id.tv_add_red);
        holder.tv_add_red3 = cellView.findViewById(R.id.tv_add_red3);
        holder.tv_invite = cellView.findViewById(R.id.tv_invite);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        ViewHolder holder = (ViewHolder) h;
        InviteDetailListEntity.ContentBean.ListBean item = mList.get(position);
        try {
            if (3 == status) {
                if (View.VISIBLE == holder.tv_add_red.getVisibility()) {
                    holder.tv_add_red.setVisibility(View.GONE);
                }
                if (View.GONE == holder.tv_add_red3.getVisibility()) {
                    holder.tv_add_red3.setVisibility(View.VISIBLE);
                }
                if (2 == item.getType()) {
                    holder.tv_add_red3.setText("-" + item.getAmount());
                } else {
                    holder.tv_add_red3.setText("+" + item.getAmount());
                }
            } else {
                if (View.VISIBLE == holder.tv_add_red3.getVisibility()) {
                    holder.tv_add_red3.setVisibility(View.GONE);
                }
                if (View.GONE == holder.tv_add_red.getVisibility()) {
                    holder.tv_add_red.setVisibility(View.VISIBLE);
                }
                if (2 == item.getType()) {
                    holder.tv_add_red.setText("-" + item.getAmount());
                } else {
                    holder.tv_add_red.setText("+" + item.getAmount());
                }
            }
            holder.tv_time.setText(item.getCreate_time());

            String[] strings = item.getDescribe().split("-");
            if (2 <= strings.length) {
                holder.tv_invite.setText(strings[0]);
                holder.tv_phone.setText(strings[1]);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return cellView;
    }
}
