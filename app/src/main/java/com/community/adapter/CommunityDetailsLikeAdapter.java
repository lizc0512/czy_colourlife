package com.community.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.community.entity.CommunityDynamicsListEntity;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;

/**
 * @ProjectName:
 * @Package: com.community.adapter
 * @ClassName: CommunityDynamicsAdapter
 * @Description: 社区动态详情点赞列表
 * @Author: yuansk
 * @CreateDate: 2020/2/25 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/25 9:47
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public class CommunityDetailsLikeAdapter extends RecyclerView.Adapter<CommunityDetailsLikeAdapter.DefaultViewHolder> {

    private List<CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean> zanBeanList;
    private Context mContext;

    public CommunityDetailsLikeAdapter(Context context, List<CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean> zanBeanList) {
        this.mContext = context;
        this.zanBeanList = zanBeanList;
    }

    @Override
    public CommunityDetailsLikeAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommunityDetailsLikeAdapter.DefaultViewHolder viewHolder = new CommunityDetailsLikeAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dynamics_like, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommunityDetailsLikeAdapter.DefaultViewHolder holder, int position) {
        CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean zanBean = zanBeanList.get(position);
        GlideImageLoader.loadImageDisplay(mContext, zanBean.getFrom_avatar(), holder.iv_dynamics_like_pics);
        holder.iv_dynamics_like_names.setText(zanBean.getFrom_nickname());
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return zanBeanList == null ? 0 : zanBeanList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_dynamics_like_pics;
        TextView iv_dynamics_like_names;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            iv_dynamics_like_pics = itemView.findViewById(R.id.iv_dynamics_like_pics);
            iv_dynamics_like_names = itemView.findViewById(R.id.iv_dynamics_like_names);
        }
    }
}
