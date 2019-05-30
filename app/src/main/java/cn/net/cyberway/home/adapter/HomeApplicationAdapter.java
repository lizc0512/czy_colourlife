package cn.net.cyberway.home.adapter;

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
import cn.net.cyberway.home.entity.HomeFuncEntity;

/**
 *
 */
public class HomeApplicationAdapter extends RecyclerView.Adapter<HomeApplicationAdapter.DefaultViewHolder> {
    private Context mContext;
    private ArrayList<HomeFuncEntity.ContentBean> applicationDataBeanList;
    private OnItemClickListener onClickListener;

    public HomeApplicationAdapter(Context context, ArrayList<HomeFuncEntity.ContentBean> applicationDataBeanList) {
        this.mContext = context;
        this.applicationDataBeanList = applicationDataBeanList;
    }

    @Override
    public HomeApplicationAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeApplicationAdapter.DefaultViewHolder viewHolder = new HomeApplicationAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_application_item, parent, false));
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }


    @Override
    public void onBindViewHolder(final HomeApplicationAdapter.DefaultViewHolder holder, int position) {
        final HomeFuncEntity.ContentBean dataBean = applicationDataBeanList.get(position);
        String image = dataBean.getImg();
        GlideImageLoader.loadImageDisplay(mContext, image, holder.iv_application);
        holder.tv_application_name.setText(dataBean.getName());
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return applicationDataBeanList == null ? 0 : applicationDataBeanList.size();
    }


    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_application;
        TextView tv_application_name;
        OnItemClickListener onClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            iv_application = itemView.findViewById(R.id.iv_application);
            tv_application_name = itemView.findViewById(R.id.tv_application_name);
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
