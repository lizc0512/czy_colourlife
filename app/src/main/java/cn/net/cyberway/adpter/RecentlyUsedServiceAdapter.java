package cn.net.cyberway.adpter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nohttp.utils.GlideImageLoader;

import java.util.ArrayList;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.protocol.HomeLifeEntity;

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

public class RecentlyUsedServiceAdapter extends RecyclerView.Adapter<RecentlyUsedServiceAdapter.DefaultViewHolder> {

    private OnItemClickListener onClickListener;
    private ArrayList<HomeLifeEntity.ContentBean.ListBean> lifeList;
    private Context mContext;

    public RecentlyUsedServiceAdapter(Context context, ArrayList<HomeLifeEntity.ContentBean.ListBean> lifeList) {
        this.lifeList = lifeList;
        this.mContext = context;
    }

    @Override
    public RecentlyUsedServiceAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecentlyUsedServiceAdapter.DefaultViewHolder viewHolder = new RecentlyUsedServiceAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.home_more_history_item, parent, false));
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @Override
    public void onBindViewHolder(RecentlyUsedServiceAdapter.DefaultViewHolder holder, int position) {
        HomeLifeEntity.ContentBean.ListBean attr = lifeList.get(position);
        holder.name.setText(attr.getName());
        GlideImageLoader.loadImageDefaultDisplay(mContext,attr.getImg(),holder.icon,R.drawable.default_image,R.drawable.default_image);
    }

    @Override
    public int getItemCount() {
        return lifeList == null ? 0 : lifeList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView icon;
        TextView name;

        OnItemClickListener onClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            name = (TextView) itemView.findViewById(R.id.name);
            icon = (ImageView) itemView.findViewById(R.id.icon);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
