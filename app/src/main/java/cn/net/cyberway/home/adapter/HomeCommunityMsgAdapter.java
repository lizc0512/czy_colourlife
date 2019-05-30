package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;

import java.util.ArrayList;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.home.entity.HomeCommunityMsgEntity;
import q.rorbin.badgeview.QBadgeView;

/**
 * 首页消息通知
 */
public class HomeCommunityMsgAdapter extends RecyclerView.Adapter<HomeCommunityMsgAdapter.DefaultViewHolder> {
    private Context mContext;
    private ArrayList<HomeCommunityMsgEntity.ContentBean.DataBean> dataBeanArrayList;
    private OnItemClickListener onClickListener;

    public HomeCommunityMsgAdapter(Context context, ArrayList<HomeCommunityMsgEntity.ContentBean.DataBean> dataBeanArrayList) {
        this.mContext = context;
        this.dataBeanArrayList = dataBeanArrayList;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeCommunityMsgAdapter.DefaultViewHolder viewHolder = new HomeCommunityMsgAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_notification_item, parent, false));
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        final HomeCommunityMsgEntity.ContentBean.DataBean dataBean = dataBeanArrayList.get(position);
        String appName = dataBean.getApp_name();
        holder.tv_message_type.setText(appName);
        if (appName.contains("投诉") || appName.contains("报修")) {
            holder.tv_message_title.setText(dataBean.getOrder_status());
        } else {
            holder.tv_message_title.setText(dataBean.getMsg_title());
        }
        holder.tv_message_body.setText(dataBean.getMsg_subject());
        if ("1".equals(dataBean.getIs_read())) {
            holder.message_status.setVisibility(View.GONE);
        } else {
            holder.message_status.setVisibility(View.VISIBLE);
        }
        if (position == dataBeanArrayList.size() - 1) {
            holder.bottom_view.setVisibility(View.GONE);
        } else {
            holder.bottom_view.setVisibility(View.VISIBLE);
        }
        long sendTime = dataBean.getSend_time();
        holder.tv_message_time.setText(TimeUtil.noticeTime(sendTime));
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataBeanArrayList == null ? 0 : dataBeanArrayList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_message_type;
        TextView tv_message_title;
        TextView tv_message_time;
        TextView tv_message_body;
        QBadgeView message_status;
        View bottom_view;
        OnItemClickListener onClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_message_type = itemView.findViewById(R.id.tv_message_type);
            tv_message_title = itemView.findViewById(R.id.tv_message_title);
            tv_message_time = itemView.findViewById(R.id.tv_message_time);
            tv_message_body = itemView.findViewById(R.id.tv_message_body);
            bottom_view = itemView.findViewById(R.id.bottom_view);
            message_status = new QBadgeView(itemView.getContext());
            message_status.bindTarget(tv_message_type);
            message_status.setBadgeGravity(Gravity.TOP | Gravity.END);
            message_status.setBadgeTextSize(4f, true);
            message_status.setGravityOffset(0.5f, 0.5f, true);
            message_status.setBadgePadding(0.5f, true);
            message_status.setBadgeBackgroundColor(ContextCompat.getColor(itemView.getContext(), R.color.hx_color_red_tag));
            message_status.setShowShadow(false);
            message_status.setBadgeText(" ");
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
