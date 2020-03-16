package com.cashier.adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;

import java.util.List;
import cn.net.cyberway.R;


/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.adpter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/23
 * @change
 * @chang time
 * @class describe  订单详情的adapter
 */

public class OrderDetailsAdapter extends RecyclerView.Adapter<OrderDetailsAdapter.DefaultViewHolder> {
    private List<String> titleList;
    private List<String> contentList;

    public OrderDetailsAdapter(List<String> titleList, List<String> contentList) {
        this.titleList = titleList;
        this.contentList = contentList;
    }

    @Override
    public OrderDetailsAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        OrderDetailsAdapter.DefaultViewHolder viewHolder = new OrderDetailsAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_order_detailview, parent, false));
        return viewHolder;
    }


    @Override
    public void onBindViewHolder(final OrderDetailsAdapter.DefaultViewHolder holder, int position) {
        final String title = titleList.get(position);
        final String content = contentList.get(position);
        holder.tv_order_title.setText(title);
        holder.tv_order_staus.setText(content);
        holder.tv_order_staus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if ("订单编号".equals(title)) {
                    ClipboardManager cm = (ClipboardManager) holder.itemView.getContext().getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", content);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtil.toastShow(holder.itemView.getContext(), "订单编号已复制到剪贴板");
                }
                return false;
            }
        });
    }

    @Override
    public int getItemCount() {
        return titleList == null ? 0 : titleList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {

        TextView tv_order_title;
        TextView tv_order_staus;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_order_title = (TextView) itemView.findViewById(R.id.tv_order_title);
            tv_order_staus = (TextView) itemView.findViewById(R.id.tv_order_staus);

        }
    }
}
