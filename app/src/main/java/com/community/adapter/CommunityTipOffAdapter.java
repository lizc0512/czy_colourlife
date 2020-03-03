package com.community.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.community.entity.CommunityTipOffEntity;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/**
 * @ProjectName:
 * @Package: com.community.adapter
 * @ClassName: CommunityTipOffAdapter
 * @Description: 举报类型的adapter
 * @Author: yuansk
 * @CreateDate: 2020/2/25 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/25 9:47
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public class CommunityTipOffAdapter extends RecyclerView.Adapter<CommunityTipOffAdapter.DefaultViewHolder> {

    private List<CommunityTipOffEntity.ContentBean> tipOffTypeList;
    private Context mContext;
    private OnItemClickListener onClickListener;

    public CommunityTipOffAdapter(Context context, List<CommunityTipOffEntity.ContentBean> tipOffTypeList) {
        this.mContext = context;
        this.tipOffTypeList = tipOffTypeList;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public CommunityTipOffAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommunityTipOffAdapter.DefaultViewHolder viewHolder = new CommunityTipOffAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dynamics_tipsoff_item, parent, false));
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommunityTipOffAdapter.DefaultViewHolder holder, int position) {
        CommunityTipOffEntity.ContentBean contentBean = tipOffTypeList.get(position);
        holder.tv_dynamics_tipoffs.setText(contentBean.getReport_type());
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return tipOffTypeList == null ? 0 : tipOffTypeList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView tv_dynamics_tipoffs;

        OnItemClickListener onClickListener;


        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_dynamics_tipoffs = itemView.findViewById(R.id.tv_dynamics_tipoffs);
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
