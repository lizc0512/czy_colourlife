package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.Utils;
import com.nohttp.utils.SpaceItemDecoration;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.home.entity.PersonalRecommendedEntity;
import cn.net.cyberway.utils.LinkParseUtil;
import cn.net.cyberway.utils.WrapLinearLayoutManager;

/**
 * Created by lizc 2017/10/07.
 */
public class TicketMallAdapter extends RecyclerView.Adapter<TicketMallAdapter.DefaultViewHolder> {
    private Context mContext;
    private List<PersonalRecommendedEntity.ContentBean> recommendContentBeanList;
    private OnItemClickListener onClickListener;

    public TicketMallAdapter(Context context, List<PersonalRecommendedEntity.ContentBean> recommendContentBeanList) {
        this.mContext = context;
        this.recommendContentBeanList = recommendContentBeanList;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        TicketMallAdapter.DefaultViewHolder viewHolder = new TicketMallAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.mhome_ticket_mall, parent, false));
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(DefaultViewHolder holder, int position) {
        final PersonalRecommendedEntity.ContentBean contentBean = recommendContentBeanList.get(position);
        List<PersonalRecommendedEntity.ContentBean.ItemsBean> itemsBeanList = contentBean.getItems();
        if (null != itemsBeanList && itemsBeanList.size() > 0) {
            holder.ticket_mall_layout.setVisibility(View.VISIBLE);
            holder.ticket_layout.setVisibility(View.VISIBLE);
            holder.tv_time.setText(TimeUtil.formatHomeTime(contentBean.getSend_time()));
            holder.tv_title.setText(contentBean.getApp_name());
        } else {
            holder.ticket_mall_layout.setVisibility(View.GONE);
            holder.ticket_layout.setVisibility(View.GONE);
        }
        final String msgId = contentBean.getMsg_id();
        ShowShopPicAdapter showShopPicAdapter = new ShowShopPicAdapter(mContext, itemsBeanList, msgId);
        holder.rv_ticket_mall.setLayoutManager(new WrapLinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false));
        holder.rv_ticket_mall.addItemDecoration(new SpaceItemDecoration(Utils.dip2px(mContext, 0.5f)));
        holder.rv_ticket_mall.setNestedScrollingEnabled(false);
        holder.rv_ticket_mall.setAdapter(showShopPicAdapter);
        holder.ticket_mall_layout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LinkParseUtil.parse(mContext, contentBean.getApp_link_url(), contentBean.getApp_name());
            }
        });
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return recommendContentBeanList == null ? 0 : recommendContentBeanList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        RecyclerView rv_ticket_mall;
        LinearLayout ticket_layout;
        LinearLayout ticket_mall_layout;
        TextView tv_time;
        TextView tv_title;
        OnItemClickListener onClickListener;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_title = (TextView) itemView.findViewById(R.id.tv_title);
            ticket_mall_layout = (LinearLayout) itemView.findViewById(R.id.ticket_mall_layout);
            ticket_layout = (LinearLayout) itemView.findViewById(R.id.ticket_layout);
            tv_time = (TextView) itemView.findViewById(R.id.tv_time);
            rv_ticket_mall = (RecyclerView) itemView.findViewById(R.id.rv_ticket_mall);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
