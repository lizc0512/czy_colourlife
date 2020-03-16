package com.community.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.community.entity.CommunityRemindListEntity;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/**
 * @ProjectName:
 * @Package: com.community.adapter
 * @ClassName: CommunityDynamicsAdapter
 * @Description: 社区动态消息提醒列表
 * @Author: yuansk
 * @CreateDate: 2020/2/25 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/25 9:47
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public class CommunityRemindAdapter extends RecyclerView.Adapter<CommunityRemindAdapter.DefaultViewHolder> {

    private List<CommunityRemindListEntity.ContentBean> contentBeanList;
    private Context mContext;
    private OnItemClickListener onClickListener;
    public CommunityRemindAdapter(Context context, List<CommunityRemindListEntity.ContentBean> contentBeanList) {
        this.mContext = context;
        this.contentBeanList = contentBeanList;
    }
    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }
    @Override
    public CommunityRemindAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommunityRemindAdapter.DefaultViewHolder viewHolder = new CommunityRemindAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dynamics_notice, parent, false));
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommunityRemindAdapter.DefaultViewHolder holder, int position) {
        CommunityRemindListEntity.ContentBean contentBean = contentBeanList.get(position);
        holder.tv_dynamics_title.setText(contentBean.getContent());
        holder.tv_dynamics_time.setText(TimeUtil.noticeTime(contentBean.getCreated_at()));
        if ("1".equals(contentBean.getIs_read())){
            holder.tv_dynamics_title.setTextColor(Color.parseColor("#A9AFB8"));
        }else{
            holder.tv_dynamics_title.setTextColor(Color.parseColor("#25282E"));
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return contentBeanList == null ? 0 : contentBeanList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tv_dynamics_title;
        TextView tv_dynamics_time;
        OnItemClickListener onClickListener;
        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_dynamics_title = itemView.findViewById(R.id.tv_dynamics_title);
            tv_dynamics_time = itemView.findViewById(R.id.tv_dynamics_time);
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
