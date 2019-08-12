package cn.net.cyberway.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.BenefitChannlEntity;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 彩惠人生-全部
 * Created by hxg on 2019/07/17.
 */
public class BenefitAllAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private LayoutInflater mInflater;
    private List<BenefitChannlEntity.ContentBean.AllBean.DataBeanX> mList;

    public BenefitAllAdapter(Context mContext, List<BenefitChannlEntity.ContentBean.AllBean.DataBeanX> mList) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mList = mList;
    }

    public void setData(List<BenefitChannlEntity.ContentBean.AllBean.DataBeanX> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_benefit_all, viewGroup, false);
        return new BenefitAllAdapter.ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final BenefitChannlEntity.ContentBean.AllBean.DataBeanX item = mList.get(position);
        BenefitAllAdapter.ViewHolder holder = (BenefitAllAdapter.ViewHolder) viewHolder;
        try {
            GlideImageLoader.loadImageDefaultDisplay(mInflater.getContext(), item.getImage(), holder.iv_shop, R.drawable.icon_default, R.drawable.icon_default);
            holder.tv_title.setText(item.getTitle());
            if (1 == item.getIs_support_meal_ticket_pay()) {
                holder.iv_ticket.setVisibility(View.VISIBLE);
            } else {
                holder.iv_ticket.setVisibility(View.GONE);
            }

            holder.tv_dan.setText("单均送" + item.getStatistics().getAvg_service_amount() + "元");
            holder.tv_ren.setText(item.getStatistics().getVisit_num() + "人用过");

            holder.tv_content.setText(item.getDesc());
            holder.rl_item.setOnClickListener(v -> LinkParseUtil.parse(mInflater.getContext(), item.getUrl(), ""));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView iv_shop;
        private TextView tv_title;
        private ImageView iv_ticket;
        private TextView tv_content;
        private RelativeLayout rl_item;
        private TextView tv_dan;
        private TextView tv_ren;

        ViewHolder(View itemView) {
            super(itemView);
            iv_shop = itemView.findViewById(R.id.iv_shop);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_ticket = itemView.findViewById(R.id.iv_ticket);
            tv_content = itemView.findViewById(R.id.tv_content);
            rl_item = itemView.findViewById(R.id.rl_item);
            tv_dan = itemView.findViewById(R.id.tv_dan);
            tv_ren = itemView.findViewById(R.id.tv_ren);
        }
    }


//    public class ViewHolder extends BeeCellHolder {
//        private ImageView iv_shop;
//        private TextView tv_title;
//        private ImageView iv_ticket;
//        public TextView tv_content;
//        public RelativeLayout rl_item;
//        private TextView tv_dan;
//        private TextView tv_ren;
//    }
//
//    @Override
//    protected BeeCellHolder createCellHolder(View cellView) {
//        BenefitAllAdapter.ViewHolder holder = new BenefitAllAdapter.ViewHolder();
//        holder.iv_shop = cellView.findViewById(R.id.iv_shop);
//        holder.tv_title = cellView.findViewById(R.id.tv_title);
//        holder.iv_ticket = cellView.findViewById(R.id.iv_ticket);
//        holder.tv_content = cellView.findViewById(R.id.tv_content);
//        holder.rl_item = cellView.findViewById(R.id.rl_item);
//        holder.tv_dan = cellView.findViewById(R.id.tv_dan);
//        holder.tv_ren = cellView.findViewById(R.id.tv_ren);
//        return holder;
//    }


//
//    public BenefitAllAdapter(Context context, List<BenefitChannlEntity.ContentBean.AllBean.DataBeanX> list) {
//        super(context, list);
//    }
//
//    public class ViewHolder extends BeeCellHolder {
//        private ImageView iv_shop;
//        private TextView tv_title;
//        private ImageView iv_ticket;
//        public TextView tv_content;
//        public RelativeLayout rl_item;
//        private TextView tv_dan;
//        private TextView tv_ren;
//    }
//
//    @Override
//    protected BeeCellHolder createCellHolder(View cellView) {
//        BenefitAllAdapter.ViewHolder holder = new BenefitAllAdapter.ViewHolder();
//        holder.iv_shop = cellView.findViewById(R.id.iv_shop);
//        holder.tv_title = cellView.findViewById(R.id.tv_title);
//        holder.iv_ticket = cellView.findViewById(R.id.iv_ticket);
//        holder.tv_content = cellView.findViewById(R.id.tv_content);
//        holder.rl_item = cellView.findViewById(R.id.rl_item);
//        holder.tv_dan = cellView.findViewById(R.id.tv_dan);
//        holder.tv_ren = cellView.findViewById(R.id.tv_ren);
//        return holder;
//    }
//
//    @SuppressLint("SetTextI18n")
//    @Override
//    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
//        try {
//            ViewHolder holder = (ViewHolder) h;
//            final BenefitChannlEntity.ContentBean.AllBean.DataBeanX item = (BenefitChannlEntity.ContentBean.AllBean.DataBeanX) dataList.get(position);
//
//            GlideImageLoader.loadImageDefaultDisplay(mContext, item.getImage(), holder.iv_shop, R.drawable.icon_default, R.drawable.icon_default);
//            holder.tv_title.setText(item.getTitle());
//            if (1 == item.getIs_support_meal_ticket_pay()) {
//                holder.iv_ticket.setVisibility(View.VISIBLE);
//            } else {
//                holder.iv_ticket.setVisibility(View.GONE);
//            }
//
//            holder.tv_dan.setText("单均送" + item.getStatistics().getAvg_service_amount() + "元");
//            holder.tv_ren.setText(item.getStatistics().getVisit_num() + "人用过");
//
//            holder.tv_content.setText(item.getDesc());
//            holder.rl_item.setOnClickListener(v -> LinkParseUtil.parse(mContext, item.getUrl(), ""));
//        } catch (Resources.NotFoundException e) {
//            e.printStackTrace();
//        }
//        return cellView;
//    }
//
//    @Override
//    public View createCellView() {
//        return mInflater.inflate(R.layout.adapter_benefit_all, null);
//    }

}
