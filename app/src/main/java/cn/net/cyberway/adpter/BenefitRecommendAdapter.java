package cn.net.cyberway.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.adapter.BeeBaseAdapter;
import com.customerInfo.protocol.DutyListEntity;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 彩惠人生-推荐
 * Created by hxg on 2019/07/15.
 */
public class BenefitRecommendAdapter extends BeeBaseAdapter {

    private int imgSize;

    public BenefitRecommendAdapter(Context context, List<String> list, int imgSize) {
        super(context, list);
        this.imgSize = imgSize;
    }

    public class ViewHolder extends BeeBaseAdapter.BeeCellHolder {
        private ImageView iv_shop;
        private TextView tv_title;
        private LinearLayout ll_activity;
        private TextView tv_more;
        private LinearLayout ll_content;
    }

    @Override
    protected BenefitRecommendAdapter.BeeCellHolder createCellHolder(View cellView) {
        BenefitRecommendAdapter.ViewHolder holder = new BenefitRecommendAdapter.ViewHolder();
        holder.iv_shop = cellView.findViewById(R.id.iv_shop);
        holder.tv_title = cellView.findViewById(R.id.tv_title);
        holder.ll_activity = cellView.findViewById(R.id.ll_activity);
        holder.tv_more = cellView.findViewById(R.id.tv_more);
        holder.ll_content = cellView.findViewById(R.id.ll_content);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeBaseAdapter.BeeCellHolder h) {
        ViewHolder holder = (ViewHolder) h;

        holder.ll_activity.removeAllViews();
        holder.ll_content.removeAllViews();

        final DutyListEntity.ContentBean.OnceBean item = (DutyListEntity.ContentBean.OnceBean) dataList.get(position);

        GlideImageLoader.loadImageDefaultDisplay(mContext, "", holder.iv_shop, R.drawable.icon_default, R.drawable.icon_default);
        holder.tv_title.setText("京东精选");
        holder.tv_title.setCompoundDrawables(null, null, mContext.getResources().getDrawable(R.drawable.ic_benefit_ticket), null);

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
        leftText.setText("单均送18元");
        leftText.setTextSize(12);
        leftText.setTextColor(mContext.getResources().getColor(R.color.color_fd4600));
        leftText.setBackgroundResource(R.drawable.shape_benefit_text_red);
        holder.ll_activity.addView(leftText, layoutParams);

        TextView rightText = new TextView(mContext);
        rightText.setText("3243人用过");
        rightText.setTextSize(12);
        rightText.setTextColor(mContext.getResources().getColor(R.color.color_329dfa));
        rightText.setBackgroundResource(R.drawable.shape_benefit_text_blue);
        holder.ll_activity.addView(rightText, layoutParams);

        for (int i = 0; i < 3; i++) {
            LinearLayout ll_shop = new LinearLayout(mContext);
            ll_shop.setOrientation(LinearLayout.VERTICAL);

            LinearLayout.LayoutParams layoutParamsImg = new LinearLayout.LayoutParams(imgSize, imgSize);
            layoutParamsImg.setMargins(0, 0, Utils.dip2px(mContext, 8), 0);
            ImageView imageView = new ImageView(mContext);
            GlideImageLoader.loadImageDefaultDisplay(mContext, "", imageView, R.drawable.default_image, R.drawable.default_image);
            ll_shop.addView(imageView, layoutParamsImg);

            TextView titleText = new TextView(mContext);
            titleText.setText("电饭锅");
            titleText.setTextSize(14);
            titleText.setTextColor(mContext.getResources().getColor(R.color.black_text_color));
            ll_shop.addView(titleText, titleLayoutParams);

            TextView priceText = new TextView(mContext);
            priceText.setTextSize(14);
            priceText.setTextColor(mContext.getResources().getColor(R.color.color_fd4600));

            int a = position + 97;
            String b = "￥" + a + " 送20元";

            SpannableString spannableString = new SpannableString(b);
            ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FEBA42"));
            spannableString.setSpan(colorSpan, b.indexOf("送"), spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
            priceText.setText(spannableString);

            ll_shop.addView(priceText, priceLayoutParams);

            holder.ll_content.addView(ll_shop);
        }

        holder.tv_more.setOnClickListener(v -> {

        });

        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.adapter_benefit_recommend, null);
    }

}
