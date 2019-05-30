package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import cn.net.cyberway.home.entity.HomeNotifyEntity;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 展示表格形式的多张图片
 */
public class SecondEmployPicAdapter extends RecyclerView.Adapter<SecondEmployPicAdapter.DefaultViewHolder> {
    private Context mContext;
    private List<HomeNotifyEntity.ContentBean.ItemsBean> imgArrBeanList;
    private OnItemClickListener onClickListener;
    private String msgId;

    public SecondEmployPicAdapter(Context context, List<HomeNotifyEntity.ContentBean.ItemsBean> imgArrBeanList, String msgId) {
        this.mContext = context;
        this.imgArrBeanList = imgArrBeanList;
        this.msgId = msgId;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        SecondEmployPicAdapter.DefaultViewHolder viewHolder = new SecondEmployPicAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_ticket_mall, null, false));
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        final HomeNotifyEntity.ContentBean.ItemsBean dataBean = imgArrBeanList.get(position);
        GlideImageLoader.loadImageDefaultDisplay(mContext, dataBean.getImg_url(), holder.image_ticket_mall, R.drawable.icon_style_four, R.drawable.icon_style_four);
        holder.image_ticket_mall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkParseUtil.parse(mContext, dataBean.getLink_url(), dataBean.getImg_title());
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return imgArrBeanList == null ? 0 : imgArrBeanList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image_ticket_mall;
        OnItemClickListener onClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            image_ticket_mall = (ImageView) itemView.findViewById(R.id.image_ticket_mall);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
