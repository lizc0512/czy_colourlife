package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.home.entity.HomeNotifyEntity;

/**
 * 猜你喜欢，彩富人生，彩住宅
 */
public class HomeActivityAdapter extends RecyclerView.Adapter<HomeActivityAdapter.DefaultViewHolder> {

    private List<HomeNotifyEntity.ContentBean> notifyEntityList;
    private Context mContext;
    private OnItemClickListener onClickListener;

    public HomeActivityAdapter(Context context, List<HomeNotifyEntity.ContentBean> notifyEntityList) {
        this.mContext = context;
        this.notifyEntityList = notifyEntityList;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public HomeActivityAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        HomeActivityAdapter.DefaultViewHolder viewHolder = new HomeActivityAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_activity_item, parent, false));
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HomeActivityAdapter.DefaultViewHolder holder, int position) {
        final HomeNotifyEntity.ContentBean contentBean = notifyEntityList.get(position);
        List<HomeNotifyEntity.ContentBean.ItemsBean> itemsBeanList = contentBean.getItems();
        if (null != itemsBeanList && itemsBeanList.size() > 0) {
            HomeNotifyEntity.ContentBean.ItemsBean itemsBean = itemsBeanList.get(0);
            GlideImageLoader.loadImageDefaultDisplay(mContext, itemsBean.getImg_url(), holder.iv_activity, R.drawable.icon_style_four, R.drawable.icon_style_four);
            holder.tv_activity_name.setText(itemsBean.getImg_title());
            holder.tv_activity_desc.setText(itemsBean.getImg_sub_title());
        }

    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return notifyEntityList == null ? 0 : notifyEntityList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView iv_activity;
        TextView tv_activity_name;
        TextView tv_activity_desc;
        OnItemClickListener onClickListener;


        public DefaultViewHolder(View itemView) {
            super(itemView);
            iv_activity = (ImageView) itemView.findViewById(R.id.iv_activity);
            tv_activity_name = (TextView) itemView.findViewById(R.id.tv_activity_name);
            tv_activity_desc = (TextView) itemView.findViewById(R.id.tv_activity_desc);
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
