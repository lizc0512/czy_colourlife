package cn.net.cyberway.adpter;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.csh.colourful.life.utils.GlideUtils;
import cn.net.cyberway.R;
import cn.net.cyberway.protocol.HomeSearchCommunityEntity;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.adpter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/10/18 19:08
 * @change
 * @chang time
 * @class describe
 */

public class SearchLocalCommunityAdapter extends RecyclerView.Adapter<SearchLocalCommunityAdapter.DefaultViewHolder> {
    private List<HomeSearchCommunityEntity.ContentBean> contentBeanList;
    private OnItemClickListener onClickListener;

    public SearchLocalCommunityAdapter(List<HomeSearchCommunityEntity.ContentBean> contentBeanList) {
        this.contentBeanList = contentBeanList;
    }

    @Override
    public SearchLocalCommunityAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SearchLocalCommunityAdapter.DefaultViewHolder viewHolder = new SearchLocalCommunityAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.community_item_layout, parent, false));
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @Override
    public void onBindViewHolder(SearchLocalCommunityAdapter.DefaultViewHolder holder, int position) {
        String communtiyName = "";
        String provinceName = "";
        HomeSearchCommunityEntity.ContentBean contentBean = contentBeanList.get(position);
        communtiyName = contentBean.getName();
        provinceName = contentBean.getAddress();
        String type = contentBean.getType();
        if (type.equals("2")) {
            holder.tvJoin.setText(holder.itemView.getResources().getString(R.string.home_enter));
            holder.tvJoin.setVisibility(View.VISIBLE);
            holder.ivCommunity.setImageResource(R.drawable.home_shequ_off);
            holder.tvCommunityName.setTextColor(Color.parseColor("#333b46"));
            holder.tvProvincename.setTextColor(Color.parseColor("#a3aaae"));
        } else if (type.equals("1")) {
            holder.tvJoin.setVisibility(View.GONE);
            holder.ivCommunity.setImageResource(R.drawable.home_shequ_on);
            holder.tvCommunityName.setTextColor(Color.parseColor("#7caff5"));
            holder.tvProvincename.setTextColor(Color.parseColor("#aa7caff5"));
        } else {
            holder.tvJoin.setText(holder.itemView.getResources().getString(R.string.home_join));
            holder.tvJoin.setVisibility(View.VISIBLE);
            holder.tvCommunityName.setTextColor(Color.parseColor("#333b46"));
            holder.tvProvincename.setTextColor(Color.parseColor("#a3aaae"));
            holder.ivCommunity.setImageResource(R.drawable.home_shequ_off);
        }
        String imagUrl = contentBean.getImg();
        if (!TextUtils.isEmpty(imagUrl)) {
            GlideUtils.loadImageView(holder.itemView.getContext(), imagUrl, holder.ivCommunity);
        }
        holder.top_view_line.setVisibility(View.VISIBLE);
        holder.tvCommunityName.setText(communtiyName);
        holder.tvProvincename.setText(provinceName);
    }

    @Override
    public int getItemCount() {
        return contentBeanList == null ? 0 : contentBeanList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView ivCommunity;
        TextView tvCommunityName;
        TextView tvProvincename;
        TextView tvJoin;
        View top_view_line;
        OnItemClickListener onClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ivCommunity = (ImageView) itemView.findViewById(R.id.iv_community);
            tvCommunityName = (TextView) itemView.findViewById(R.id.tv_communityname);
            tvProvincename = (TextView) itemView.findViewById(R.id.tv_provincename);
            tvJoin = (TextView) itemView.findViewById(R.id.tv_join);
            top_view_line = itemView.findViewById(R.id.top_view_line);
            tvJoin.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null && v.getId() == R.id.tv_join) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
