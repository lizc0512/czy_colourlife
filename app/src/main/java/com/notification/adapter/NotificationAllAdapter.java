package com.notification.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.notification.activity.NotificationDetailsActivity;
import com.notification.protocol.NotificationAllEntity;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * Created by lizc on 2018/4/17.
 * 项目详情列表适配器
 *
 * @Description
 */

public class NotificationAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<NotificationAllEntity.ContentBean> mlist;
    private NotificationAllInfoAdapter notificationAllInfoAdapter;
    private NotificationAllInfoTwoAdapter notificationAllInfoTwoAdapter;

    public NotificationAllAdapter(Context context, List<NotificationAllEntity.ContentBean> list) {
        this.mContext = context;
        this.mlist = list;
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        String templateType = mlist.get(position).getTemplate_type();
        if ("4100".equals(templateType)) {//金额
            type = 0;
        } else if ("4101".equals(templateType)) {//内容
            type = 1;
        }
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 0) { //4100  提现
            view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_notification_all, viewGroup, false);
            viewHolder = new ViewHolder(view);
        } else if (viewType == 1) { //4100 充值
            view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_notification_alltwo, viewGroup, false);
            viewHolder = new ViewHolderTwo(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        if (viewHolder instanceof ViewHolder) {//金额
            NotificationAllEntity.ContentBean contentBean = mlist.get(i);
            ViewHolder holder = (ViewHolder) viewHolder;
            final String title = contentBean.getMsg_title();
            holder.tv_notice_all_time.setText(TimeUtil.noticeTime(contentBean.getSend_time()));
            holder.tv_notice_all_name.setText(title);
            holder.tv_notice_all_content.setText(contentBean.getOrder_status());
            if ("2".equals(contentBean.getOrder_type())) {
                holder.tv_notice_all_fuhao.setVisibility(View.GONE);
                holder.tv_notice_all_money.setText(contentBean.getMsg_intro());
                holder.tv_notice_all_money.setTextSize(14.0f);
            } else {
                String order_amount = contentBean.getOrder_amount();
                if (TextUtils.isEmpty(order_amount)) {
                    holder.tv_notice_all_fuhao.setVisibility(View.INVISIBLE);
                } else {
                    holder.tv_notice_all_fuhao.setVisibility(View.VISIBLE);
                }
                holder.tv_notice_all_money.setText(order_amount);
                holder.tv_notice_all_money.setTextSize(30.0f);
            }
            if ("0".equals(contentBean.getOrder_amount_type())) {//0饭票
                holder.tv_notice_all_fuhao.setImageResource(R.drawable.message_icon_fanpiao);
            } else {
                holder.tv_notice_all_fuhao.setImageResource(R.drawable.message_icon_rmb);
            }

            final String msg_id = contentBean.getMsg_id();
            final String msgUrl = contentBean.getDetail_url();
            holder.rl_notice_all_details_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(msgUrl)) {
                        if (msgUrl.startsWith("http") || msgUrl.endsWith("EntranceGuard") || msgUrl.endsWith("apply")) {
                            LinkParseUtil.parse(mContext, msgUrl, "");
                        } else {
                            Intent intent = new Intent(mContext, NotificationDetailsActivity.class);
                            intent.putExtra("msg_id", msg_id);
                            intent.putExtra("title", title);
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
            List<NotificationAllEntity.ContentBean.ItemsBean> mitemlist = contentBean.getItems();
            notificationAllInfoAdapter = new NotificationAllInfoAdapter(mContext, mitemlist);
            holder.rv_notice_all.setAdapter(notificationAllInfoAdapter);
            notificationAllInfoAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    if (!TextUtils.isEmpty(msgUrl)) {
                        if (msgUrl.startsWith("http") || msgUrl.endsWith("EntranceGuard") || msgUrl.endsWith("apply")) {
                            LinkParseUtil.parse(mContext, msgUrl, "");
                        } else {
                            Intent intent = new Intent(mContext, NotificationDetailsActivity.class);
                            intent.putExtra("msg_id", msg_id);
                            intent.putExtra("title", title);
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
        } else if (viewHolder instanceof ViewHolderTwo) {//内容\
            NotificationAllEntity.ContentBean contentBean = mlist.get(i);
            ViewHolderTwo holderTwo = (ViewHolderTwo) viewHolder;
            final String title = contentBean.getMsg_title();
            holderTwo.tv_notice_all_time.setText(TimeUtil.noticeTime(contentBean.getSend_time()));
            holderTwo.tv_notice_all_name.setText(title);
            holderTwo.tv_notice_all_content.setText(contentBean.getOrder_status());
            final String msg_id = contentBean.getMsg_id();
            final String msgUrl = contentBean.getMsg_url();
            holderTwo.rl_notice_all_details_two_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(msgUrl)) {
                        if (msgUrl.startsWith("http") || msgUrl.endsWith("EntranceGuard") || msgUrl.endsWith("apply")) {
                            LinkParseUtil.parse(mContext, msgUrl, "");
                        } else {
                            Intent intent = new Intent(mContext, NotificationDetailsActivity.class);
                            intent.putExtra("msg_id", msg_id);
                            intent.putExtra("title", title);
                            mContext.startActivity(intent);
                        }
                    }
                }
            });
            List<NotificationAllEntity.ContentBean.ItemsBean> mitemlist2 = mlist.get(i).getItems();
            notificationAllInfoTwoAdapter = new NotificationAllInfoTwoAdapter(mContext, mitemlist2);
            holderTwo.rv_notice_all_two.setAdapter(notificationAllInfoTwoAdapter);
        }
    }

    @Override
    public int getItemCount() {
        return mlist == null ? 0 : mlist.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_notice_all_time;
        TextView tv_notice_all_name;
        TextView tv_notice_all_content;
        TextView tv_notice_all_money;
        ImageView tv_notice_all_fuhao;
        RelativeLayout rl_notice_all_details_all;
        RecyclerView rv_notice_all;
        RelativeLayout rl_notice_all_see;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_notice_all_time = (TextView) itemView.findViewById(R.id.tv_notice_all_time);
            tv_notice_all_name = (TextView) itemView.findViewById(R.id.tv_notice_all_name);
            tv_notice_all_content = (TextView) itemView.findViewById(R.id.tv_notice_all_content);
            tv_notice_all_money = (TextView) itemView.findViewById(R.id.tv_notice_all_money);
            tv_notice_all_fuhao = (ImageView) itemView.findViewById(R.id.tv_notice_all_fuhao);
            rl_notice_all_details_all = (RelativeLayout) itemView.findViewById(R.id.rl_notice_all_details_all);
            rl_notice_all_see = (RelativeLayout) itemView.findViewById(R.id.rl_notice_all_see);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
            rv_notice_all = (RecyclerView) itemView.findViewById(R.id.rv_notice_all);
            rv_notice_all.setLayoutManager(linearLayoutManager);
        }
    }

    class ViewHolderTwo extends RecyclerView.ViewHolder {
        TextView tv_notice_all_time;
        TextView tv_notice_all_name;
        TextView tv_notice_all_content;
        RelativeLayout rl_notice_all_details_two_all;
        RecyclerView rv_notice_all_two;

        public ViewHolderTwo(View itemView) {
            super(itemView);
            tv_notice_all_time = (TextView) itemView.findViewById(R.id.tv_notice_all_time_two);
            tv_notice_all_name = (TextView) itemView.findViewById(R.id.tv_notice_all_name_two);
            tv_notice_all_content = (TextView) itemView.findViewById(R.id.tv_notice_all_content_two);
            rl_notice_all_details_two_all = (RelativeLayout) itemView.findViewById(R.id.rl_notice_all_details_two_all);
            LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(mContext);
            rv_notice_all_two = (RecyclerView) itemView.findViewById(R.id.rv_notice_all_two);
            rv_notice_all_two.setLayoutManager(linearLayoutManager2);
        }
    }
}
