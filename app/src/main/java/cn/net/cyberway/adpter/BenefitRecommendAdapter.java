package cn.net.cyberway.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.adapter.BeeBaseAdapter;
import com.BeeFramework.view.NoScrollGridView;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.BenefitChannlEntity;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 彩惠人生-推荐
 * Created by hxg on 2019/07/15.
 */

public class BenefitRecommendAdapter extends BeeBaseAdapter {

    private int imgSize;

    public BenefitRecommendAdapter(Context context, List<BenefitChannlEntity.ContentBean.RecommendBean.DataBean> list, int imgSize) {
        super(context, list);
        this.imgSize = imgSize;
    }

    public class ViewHolder extends BeeBaseAdapter.BeeCellHolder {
        private ImageView iv_shop;
        private TextView tv_title;
        private LinearLayout ll_activity;
        private LinearLayout ll_item;
        private NoScrollGridView gv_content;
    }

    @Override
    protected BenefitRecommendAdapter.BeeCellHolder createCellHolder(View cellView) {
        BenefitRecommendAdapter.ViewHolder holder = new BenefitRecommendAdapter.ViewHolder();
        holder.iv_shop = cellView.findViewById(R.id.iv_shop);
        holder.tv_title = cellView.findViewById(R.id.tv_title);
        holder.ll_activity = cellView.findViewById(R.id.ll_activity);
        holder.ll_item = cellView.findViewById(R.id.ll_item);
        holder.gv_content = cellView.findViewById(R.id.gv_content);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeBaseAdapter.BeeCellHolder h) {
        final BenefitChannlEntity.ContentBean.RecommendBean.DataBean item = (BenefitChannlEntity.ContentBean.RecommendBean.DataBean) dataList.get(position);
        ViewHolder holder = (ViewHolder) h;
        try {
            holder.ll_activity.removeAllViews();
            item.getUrl();

            GlideImageLoader.loadImageDefaultDisplay(mContext, item.getImage(), holder.iv_shop, R.drawable.icon_default, R.drawable.icon_default);
            holder.tv_title.setText(item.getTitle());
            if (1 == item.getIs_support_meal_ticket_pay()) {
                holder.tv_title.setCompoundDrawables(null, null, mContext.getResources().getDrawable(R.drawable.ic_benefit_ticket), null);
            } else {
                holder.tv_title.setCompoundDrawables(null, null, null, null);
            }

            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0, 0, Utils.dip2px(mContext, 8), 0);

            LinearLayout.LayoutParams titleLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            titleLayoutParams.setMargins(0, Utils.dip2px(mContext, 8), 0, Utils.dip2px(mContext, 4));

            LinearLayout.LayoutParams priceLayoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            priceLayoutParams.setMargins(0, 0, 0, Utils.dip2px(mContext, 8));

            TextView leftText = new TextView(mContext);
            leftText.setText("单均送" + item.getStatistics().getAvg_service_amount() + "元");
            leftText.setTextSize(12);
            leftText.setTextColor(mContext.getResources().getColor(R.color.color_fd4600));
            leftText.setBackgroundResource(R.drawable.shape_benefit_text_red);
            holder.ll_activity.addView(leftText, layoutParams);

            TextView rightText = new TextView(mContext);
            rightText.setText(item.getStatistics().getVisit_num() + "人用过");
            rightText.setTextSize(12);
            rightText.setTextColor(mContext.getResources().getColor(R.color.color_329dfa));
            rightText.setBackgroundResource(R.drawable.shape_benefit_text_blue);
            holder.ll_activity.addView(rightText, layoutParams);

            BenefitRecommendItemAdapter adapter = new BenefitRecommendItemAdapter(mContext, item.getRecommend_goods(), imgSize);
            holder.gv_content.setAdapter(adapter);
            holder.gv_content.setOnItemClickListener((parent1, view, position1, id) -> {
                LinkParseUtil.parse(mContext, item.getUrl(), "");
            });

            holder.ll_item.setOnClickListener(v -> LinkParseUtil.parse(mContext, item.getUrl(), ""));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.adapter_benefit_recommend, null);
    }

}
