package cn.net.cyberway.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.BenefitChannlEntity;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 彩惠人生-推荐
 * Created by hxg on 2019/07/15.
 */

public class BenefitRecommendAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>/*extends BeeBaseAdapter */ {

    private LayoutInflater mInflater;
    private List<BenefitChannlEntity.ContentBean.RecommendBean.DataBean> mList;
    private BenefitRecommendItemAdapter adapter;
    private LinearLayout.LayoutParams layoutParamsImg;

    public BenefitRecommendAdapter(Context mContext, List<BenefitChannlEntity.ContentBean.RecommendBean.DataBean> mList, LinearLayout.LayoutParams layoutParamsImg) {
        this.mInflater = LayoutInflater.from(mContext);
        this.mList = mList;
        this.layoutParamsImg = layoutParamsImg;
    }

    public void setData(List<BenefitChannlEntity.ContentBean.RecommendBean.DataBean> mList) {
        this.mList = mList;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_benefit_recommend, viewGroup, false);
        BenefitRecommendAdapter.ViewHolder viewHolder = new BenefitRecommendAdapter.ViewHolder(view);
        return viewHolder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final BenefitChannlEntity.ContentBean.RecommendBean.DataBean item = mList.get(position);
        BenefitRecommendAdapter.ViewHolder holder = (BenefitRecommendAdapter.ViewHolder) viewHolder;
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

            GridLayoutManager gridBotLayoutManager = new GridLayoutManager(mInflater.getContext(), 3);
            holder.gv_content.setLayoutManager(gridBotLayoutManager);
            holder.gv_content.setNestedScrollingEnabled(false);
            adapter = new BenefitRecommendItemAdapter(mInflater.getContext(), item.getRecommend_goods(), layoutParamsImg);
            holder.gv_content.setAdapter(adapter);

            holder.ll_item.setOnClickListener(v -> LinkParseUtil.parse(mInflater.getContext(), item.getUrl(), ""));
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
        private TextView tv_dan;
        private TextView tv_ren;
        private LinearLayout ll_item;
        private RecyclerView gv_content;

        ViewHolder(View itemView) {
            super(itemView);
            iv_shop = itemView.findViewById(R.id.iv_shop);
            tv_dan = itemView.findViewById(R.id.tv_dan);
            tv_title = itemView.findViewById(R.id.tv_title);
            iv_ticket = itemView.findViewById(R.id.iv_ticket);
            tv_ren = itemView.findViewById(R.id.tv_ren);
            ll_item = itemView.findViewById(R.id.ll_item);
            gv_content = itemView.findViewById(R.id.gv_content);
        }
    }

}