package com.notification.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.model.NewHttpResponse;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nohttp.utils.GlideImageLoader;
import com.notification.activity.NotificationAllInfoActivity;
import com.notification.activity.NotificationDetailsActivity;
import com.notification.model.NotificationModel;
import com.notification.protocol.NotificationListEntity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * Created by Administrator on 2018/4/16.
 * 消息通知列表适配器
 * lizc
 *
 * @Description
 */

public class NotificationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<NotificationListEntity.ContentBean.UnReadBean> list = new ArrayList<>();
    private NotificationModel notificationModel;
    private HashMap<String, Integer> showMap = new HashMap<>();

    public NotificationAdapter(Context context, List<NotificationListEntity.ContentBean.UnReadBean> mlist) {
        this.mContext = context;
        this.list = mlist;
        notificationModel = new NotificationModel(mContext);
        for (NotificationListEntity.ContentBean.UnReadBean unReadBean : mlist) {
            showMap.put(unReadBean.getMsg_id(), unReadBean.getShow_type());
        }
    }

    @Override
    public int getItemViewType(int position) {
        int type = 0;
        String template = list.get(position).getTemplate_type();
        if ("2102".equals(template)) {
            type = 0;
        } else if ("2101".equals(template)) {
            type = 1;
        } else if ("2100".equals(template)) {
            type = 2;
        } else {
            type = 3;
        }
        return type;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view;
        RecyclerView.ViewHolder viewHolder = null;
        if (viewType == 0) { //2102
            view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_notification_three, viewGroup, false);
            viewHolder = new ViewHolder(view);
        } else if (viewType == 1) {  //2101
            view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_notification_two, viewGroup, false);
            viewHolder = new ViewHolderTwo(view);
        } else if (viewType == 2) { //2100
            view = LayoutInflater.from(mContext).inflate(R.layout.rv_item_notification, viewGroup, false);
            viewHolder = new ViewHolderThree(view);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position) {
        final NotificationListEntity.ContentBean.UnReadBean unReadBean = list.get(position);
        if (viewHolder instanceof ViewHolder) {
            ViewHolder holder = (ViewHolder) viewHolder;
            GlideImageLoader.loadImageDefaultDisplay(mContext,unReadBean.getApp_logo(),holder.app_logo,R.drawable.default_image,R.drawable.default_image);
            holder.app_name.setText(unReadBean.getApp_name());
            holder.send_time.setText(TimeUtil.noticeTime(unReadBean.getSend_time()));
            holder.msg_status.setText(unReadBean.getOrder_status());
            holder.msg_title.setText(unReadBean.getMsg_sub_title());
            holder.msg_sub_title.setText(unReadBean.getMsg_intro());
            final String msg_id = unReadBean.getMsg_id();
            final String app_id = unReadBean.getApp_id();
            final String title = unReadBean.getApp_name();
            final String msgUrl = unReadBean.getDetail_url();
            holder.rl_notice_three.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(msgUrl)) {
                        if (msgUrl.startsWith("http")||msgUrl.endsWith("EntranceGuard")||msgUrl.endsWith("apply")) {
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
            holder.rl_item_notice_details_three.setOnClickListener(new View.OnClickListener()

            {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NotificationAllInfoActivity.class);
                    intent.putExtra("app_id", app_id);
                    intent.putExtra("title", title);
                    mContext.startActivity(intent);
                }
            });
            String showMore = unReadBean.getShow_more();
            if ("1".equals(showMore))
            {
                holder.rl_item_notice_details_three.setVisibility(View.VISIBLE);
            } else
            {
                holder.rl_item_notice_details_three.setVisibility(View.GONE);
            }
        } else if (viewHolder instanceof ViewHolderTwo) {
            final ViewHolderTwo viewHolderTwo = (ViewHolderTwo) viewHolder;
            GlideImageLoader.loadImageDefaultDisplay(mContext,unReadBean.getApp_logo(),viewHolderTwo.app_logo,R.drawable.default_image,R.drawable.default_image);
            viewHolderTwo.app_name.setText(unReadBean.getApp_name());
            viewHolderTwo.send_time.setText(TimeUtil.noticeTime(unReadBean.getSend_time()));
            viewHolderTwo.msg_status.setText(unReadBean.getMsg_sub_title());
            viewHolderTwo.msg_title.setText(unReadBean.getOrder_status());
            viewHolderTwo.order_amount.setText(unReadBean.getOrder_amount());
            String amountType = unReadBean.getOrder_amount_type();
            if ("0".equals(amountType)) {
                viewHolderTwo.order_amount_type.setImageResource(R.drawable.message_icon_fanpiao);
            } else {
                viewHolderTwo.order_amount_type.setImageResource(R.drawable.message_icon_rmb);
            }
            final String msg_id = unReadBean.getMsg_id();
            final String app_id = unReadBean.getApp_id();
            final String title = unReadBean.getApp_name();
            final String msgUrl = unReadBean.getDetail_url();
            final int showTypw = showMap.get(msg_id);
            final String orderAmount = unReadBean.getOrder_amount();
            if (showTypw == 0) {//0闭眼
                viewHolderTwo.order_amount.setText("******");
                viewHolderTwo.order_amount_type.setVisibility(View.INVISIBLE);
                viewHolderTwo.iv_item_notice_isshow_two.setImageResource(R.drawable.message_icon_not_obviously_password);
            } else {
                viewHolderTwo.order_amount.setText(orderAmount);
                viewHolderTwo.order_amount_type.setVisibility(View.VISIBLE);
                viewHolderTwo.iv_item_notice_isshow_two.setImageResource(R.drawable.message_icon_obviously_password);
            }
            viewHolderTwo.iv_item_notice_isshow_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showTypw == 0) {//如果选中，显示密码
                        showMap.put(msg_id, 1);
                        viewHolderTwo.order_amount.setText(orderAmount);
                        viewHolderTwo.order_amount_type.setVisibility(View.VISIBLE);
                        viewHolderTwo.iv_item_notice_isshow_two.setImageResource(R.drawable.message_icon_obviously_password);
                        notificationModel.getShowEye(0, msg_id, 1, false, (NewHttpResponse) mContext);
                    } else {   //否则隐藏密码
                        showMap.put(msg_id, 0);
                        viewHolderTwo.order_amount.setText("******");
                        viewHolderTwo.order_amount_type.setVisibility(View.INVISIBLE);
                        viewHolderTwo.iv_item_notice_isshow_two.setImageResource(R.drawable.message_icon_not_obviously_password);
                        notificationModel.getShowEye(0, msg_id, 0, false, (NewHttpResponse) mContext);
                    }
                    notifyDataSetChanged();
                }
            });
            viewHolderTwo.rl_notice_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(msgUrl)) {
                        if (msgUrl.startsWith("http")||msgUrl.endsWith("EntranceGuard")||msgUrl.endsWith("apply")) {
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
            viewHolderTwo.rl_item_notice_details_two.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NotificationAllInfoActivity.class);
                    intent.putExtra("app_id", app_id);
                    intent.putExtra("title", title);
                    mContext.startActivity(intent);
                }
            });
            String showMore = unReadBean.getShow_more();
            if ("1".equals(showMore)) {
                viewHolderTwo.rl_item_notice_details_two.setVisibility(View.VISIBLE);
            } else {
                viewHolderTwo.rl_item_notice_details_two.setVisibility(View.GONE);
            }
        } else if (viewHolder instanceof ViewHolderThree) {
            final ViewHolderThree viewHolderThree = (ViewHolderThree) viewHolder;
            GlideImageLoader.loadImageDefaultDisplay(mContext,unReadBean.getApp_logo(),viewHolderThree.app_logo,R.drawable.default_image,R.drawable.default_image);
         
            String amountType = unReadBean.getOrder_amount_type();
            final String msg_id = list.get(position).getMsg_id();
            final String app_id = list.get(position).getApp_id();
            final String title = list.get(position).getApp_name();
            final int showType = showMap.get(msg_id);
            final String orderAmount = unReadBean.getOrder_amount();
            final String orderDesc = unReadBean.getOrder_descript();
            final String orderName = unReadBean.getOrder_name();
            final String orderTotalTime = unReadBean.getOrder_total_time();
            viewHolderThree.app_name.setText(unReadBean.getApp_name());
            viewHolderThree.send_time.setText(TimeUtil.noticeTime(unReadBean.getSend_time()));
            viewHolderThree.order_status.setText(unReadBean.getOrder_status());
            viewHolderThree.order_name.setText(orderName);
            viewHolderThree.order_amount.setText(unReadBean.getOrder_amount());
            viewHolderThree.tv_item_notice_describeNum.setText(orderDesc);
            if (showType == 0) {//0闭眼
                viewHolderThree.order_amount.setText("******");
                viewHolderThree.iv_item_notice_fu.setVisibility(View.GONE);
                viewHolderThree.order_total_time.setText("******");
                viewHolderThree.iv_item_notice_isshow.setImageResource(R.drawable.message_icon_not_obviously_password);
            } else {
                viewHolderThree.order_amount.setText(orderAmount);
                viewHolderThree.iv_item_notice_fu.setVisibility(View.VISIBLE);
                viewHolderThree.order_total_time.setText(orderTotalTime);
                viewHolderThree.iv_item_notice_isshow.setImageResource(R.drawable.message_icon_obviously_password);
            }
            if ("0".equals(amountType)) {
                viewHolderThree.iv_item_notice_fu.setImageResource(R.drawable.message_icon_fanpiao);
            } else {
                viewHolderThree.iv_item_notice_fu.setImageResource(R.drawable.message_icon_rmb);
            }
            viewHolderThree.iv_item_notice_isshow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (showType == 0) {
                        //如果选中，显示密码
                        showMap.put(msg_id, 1);
                        viewHolderThree.order_amount.setText(orderAmount);
                        viewHolderThree.iv_item_notice_fu.setVisibility(View.VISIBLE);
                        viewHolderThree.order_total_time.setText(orderTotalTime);
                        viewHolderThree.iv_item_notice_isshow.setImageResource(R.drawable.message_icon_obviously_password);
                        notificationModel.getShowEye(0, msg_id, 1, false, (NewHttpResponse) mContext);
                    } else {
                        //否则隐藏密码
                        showMap.put(msg_id, 0);
                        viewHolderThree.order_amount.setText("******");
                        viewHolderThree.iv_item_notice_fu.setVisibility(View.GONE);
                        viewHolderThree.order_total_time.setText("******");
                        viewHolderThree.iv_item_notice_isshow.setImageResource(R.drawable.message_icon_not_obviously_password);
                        notificationModel.getShowEye(0, msg_id, 0, false, (NewHttpResponse) mContext);
                    }
                    notifyDataSetChanged();
                }
            });
            final String msgUrl = unReadBean.getDetail_url();
            viewHolderThree.rl_notice_one.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(msgUrl)) {
                        if (msgUrl.startsWith("http")||msgUrl.endsWith("EntranceGuard")||msgUrl.endsWith("apply")) {
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
            viewHolderThree.rl_item_notice_details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, NotificationAllInfoActivity.class);
                    intent.putExtra("app_id", app_id);
                    intent.putExtra("title", title);
                    mContext.startActivity(intent);
                }
            });
            String showMore = unReadBean.getShow_more();
            if ("1".equals(showMore)) {
                viewHolderThree.rl_item_notice_details.setVisibility(View.VISIBLE);
            } else {
                viewHolderThree.rl_item_notice_details.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView app_logo;
        TextView app_name;
        TextView msg_title;
        TextView msg_sub_title;
        TextView msg_status;
        TextView send_time;
        RelativeLayout rl_notice_three;
        RelativeLayout rl_item_notice_details_three;

        public ViewHolder(View itemView) {
            super(itemView);
            app_logo = (ImageView) itemView.findViewById(R.id.iv_item_notice_logo_three);
            app_name = (TextView) itemView.findViewById(R.id.tv_item_notice_name_three);
            msg_title = (TextView) itemView.findViewById(R.id.tv_item_notice_describe_three);
            msg_sub_title = (TextView) itemView.findViewById(R.id.tv_item_notice_explain_three);
            msg_status = (TextView) itemView.findViewById(R.id.tv_item_notice_content_three);
            send_time = (TextView) itemView.findViewById(R.id.tv_item_notice_date_three);
            rl_notice_three = (RelativeLayout) itemView.findViewById(R.id.rl_notice_three);
            rl_item_notice_details_three = (RelativeLayout) itemView.findViewById(R.id.rl_item_notice_details_three);

        }
    }

    public class ViewHolderTwo extends RecyclerView.ViewHolder {
        ImageView app_logo;
        TextView app_name;
        TextView msg_title;
        TextView msg_status;
        TextView send_time;
        TextView order_amount;
        ImageView order_amount_type;//饭票类型
        RelativeLayout rl_item_notice_details_two;
        RelativeLayout rl_notice_two;
        ImageView iv_item_notice_isshow_two;

        public ViewHolderTwo(View itemView) {
            super(itemView);
            app_logo = (ImageView) itemView.findViewById(R.id.iv_item_notice_logo_two);
            app_name = (TextView) itemView.findViewById(R.id.tv_item_notice_name_two);
            msg_title = (TextView) itemView.findViewById(R.id.tv_item_notice_describe_two);
            msg_status = (TextView) itemView.findViewById(R.id.tv_item_notice_explain_two);
            send_time = (TextView) itemView.findViewById(R.id.tv_item_notice_time_two);
            order_amount_type = (ImageView) itemView.findViewById(R.id.tv_item_notice_fuhao);
            order_amount = (TextView) itemView.findViewById(R.id.tv_item_notice_money_two);
            rl_item_notice_details_two = (RelativeLayout) itemView.findViewById(R.id.rl_item_notice_details_two);
            rl_notice_two = (RelativeLayout) itemView.findViewById(R.id.rl_notice_two);
            iv_item_notice_isshow_two = (ImageView) itemView.findViewById(R.id.iv_item_notice_isshow_two);
        }
    }

    public class ViewHolderThree extends RecyclerView.ViewHolder {
        ImageView app_logo;
        ImageView iv_item_notice_isshow;
        ImageView iv_item_notice_fu;
        TextView app_name;
        TextView order_name;
        TextView order_amount;
        TextView send_time;
        TextView order_total_time;
        TextView order_status;
        TextView tv_item_notice_describeNum;
        RelativeLayout rl_notice_one;
        RelativeLayout rl_item_notice_details;

        public ViewHolderThree(View itemView) {
            super(itemView);
            app_logo = (ImageView) itemView.findViewById(R.id.iv_item_notice_logo);
            iv_item_notice_fu = (ImageView) itemView.findViewById(R.id.iv_item_notice_fu);
            iv_item_notice_isshow = (ImageView) itemView.findViewById(R.id.iv_item_notice_isshow);
            app_name = (TextView) itemView.findViewById(R.id.tv_item_notice_name);
            order_name = (TextView) itemView.findViewById(R.id.tv_item_notice_content);
            order_amount = (TextView) itemView.findViewById(R.id.tv_item_notice_money);
            send_time = (TextView) itemView.findViewById(R.id.tv_item_notice_time);
            order_status = (TextView) itemView.findViewById(R.id.tv_item_notice_describe);
            order_total_time = (TextView) itemView.findViewById(R.id.tv_item_notice_periods);
            tv_item_notice_describeNum = (TextView) itemView.findViewById(R.id.tv_item_notice_describeNum);
            rl_notice_one = (RelativeLayout) itemView.findViewById(R.id.rl_notice_one);
            rl_item_notice_details = (RelativeLayout) itemView.findViewById(R.id.rl_item_notice_details);
        }
    }
}
