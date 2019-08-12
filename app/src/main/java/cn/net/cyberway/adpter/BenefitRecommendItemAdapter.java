package cn.net.cyberway.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.Utils;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.BenefitChannlEntity;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 彩惠人生-推荐
 * Created by hxg on 2019/07/15.
 */

public class BenefitRecommendItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<BenefitChannlEntity.ContentBean.RecommendBean.DataBean.RecommendGoodsBean> mList;
    private LinearLayout.LayoutParams layoutParamsImg;

    public BenefitRecommendItemAdapter(Context mContext, List<BenefitChannlEntity.ContentBean.RecommendBean.DataBean.RecommendGoodsBean> mList, LinearLayout.LayoutParams layoutParamsImg) {
        this.mContext = mContext;
        this.mList = mList;
        this.layoutParamsImg = layoutParamsImg;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.adapter_benefit_recommend_item, viewGroup, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        final BenefitChannlEntity.ContentBean.RecommendBean.DataBean.RecommendGoodsBean item = mList.get(position);
        ViewHolder holder = (ViewHolder) viewHolder;
        try {
            holder.iv_shop.setLayoutParams(layoutParamsImg);
            GlideImageLoader.loadImageDefaultDisplay(mContext, item.getImage(), holder.iv_shop, Utils.dip2px(mContext, 4), R.drawable.default_image, R.drawable.default_image);
            holder.tv_title.setText(item.getName());
            String price = item.getPrice();
            if (!TextUtils.isEmpty(price)) {
                holder.tv_price_left.setText("￥" + price);
            } else {
                holder.tv_price_left.setText("");
            }
            String song = item.getService_amount();
            if (!TextUtils.isEmpty(song)) {
                holder.tv_price_right.setText("送" + song);
            } else {
                holder.tv_price_right.setText("");
            }
            holder.ll_item.setOnClickListener(v -> LinkParseUtil.parse(mContext, item.getUrl(), ""));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    private static class ViewHolder extends RecyclerView.ViewHolder {

        private LinearLayout ll_item;
        private ImageView iv_shop;
        private TextView tv_title;
        private TextView tv_price_left;
        private TextView tv_price_right;

        ViewHolder(View itemView) {
            super(itemView);
            ll_item = itemView.findViewById(R.id.ll_item);
            iv_shop = itemView.findViewById(R.id.iv_shop);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_price_left = itemView.findViewById(R.id.tv_price_left);
            tv_price_right = itemView.findViewById(R.id.tv_price_right);
        }
    }

}
