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
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.BenefitChannlEntity;

/**
 * 彩惠人生-推荐
 * Created by hxg on 2019/07/15.
 */

public class BenefitRecommendItemAdapter extends BeeBaseAdapter {

    private int imgSize;

    public BenefitRecommendItemAdapter(Context context, List<BenefitChannlEntity.ContentBean.RecommendBean.DataBean.RecommendGoodsBean> list, int imgSize) {
        super(context, list);
        this.imgSize = imgSize;
    }

    public class ViewHolder extends BeeCellHolder {
        private ImageView iv_shop;
        private TextView tv_title;
        private TextView tv_price_left;
        private TextView tv_price_right;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        BenefitRecommendItemAdapter.ViewHolder holder = new BenefitRecommendItemAdapter.ViewHolder();
        holder.iv_shop = cellView.findViewById(R.id.iv_shop);
        holder.tv_title = cellView.findViewById(R.id.tv_title);
        holder.tv_price_left = cellView.findViewById(R.id.tv_price_left);
        holder.tv_price_right = cellView.findViewById(R.id.tv_price_right);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        final BenefitChannlEntity.ContentBean.RecommendBean.DataBean.RecommendGoodsBean item = (BenefitChannlEntity.ContentBean.RecommendBean.DataBean.RecommendGoodsBean) dataList.get(position);
        ViewHolder holder = (ViewHolder) h;
        try {
            LinearLayout.LayoutParams layoutParamsImg = new LinearLayout.LayoutParams(imgSize, imgSize);
            holder.iv_shop.setLayoutParams(layoutParamsImg);

            GlideImageLoader.loadImageDefaultDisplay(mContext, item.getImage(), holder.iv_shop, Utils.dip2px(mContext, 4), R.drawable.default_image, R.drawable.default_image);
            holder.tv_title.setText(item.getName());
            holder.tv_price_left.setText("￥" + item.getPrice());
            holder.tv_price_right.setText(" 送" + item.getService_amount());

//            for (int i = 0; i < item.getRecommend_goods().size(); i++) {
//                final BenefitChannlEntity.ContentBean.RecommendBean.DataBean.RecommendGoodsBean goodsBean = item.getRecommend_goods().get(i);
//                LinearLayout ll_shop = new LinearLayout(mContext);
//                ll_shop.setOrientation(LinearLayout.VERTICAL);
//
//                LinearLayout.LayoutParams layoutParamsImg = new LinearLayout.LayoutParams(imgSize, imgSize);
//                layoutParamsImg.setMargins(0, 0, Utils.dip2px(mContext, 8), 0);
//                ImageView imageView = new ImageView(mContext);
//
//                GlideImageLoader.loadImageDefaultDisplay(mContext, goodsBean.getImage(), imageView, Utils.dip2px(mContext, 4), R.drawable.default_image, R.drawable.default_image);
//                ll_shop.addView(imageView, layoutParamsImg);
//
//                TextView titleText = new TextView(mContext);
//                String title = goodsBean.getName();
//                if (title.length() > 7) {
//                    title = title.substring(0, 7);
//                }
//                titleText.setText(title);
//                titleText.setTextSize(12);
//                titleText.setTextColor(mContext.getResources().getColor(R.color.black_text_color));
//                ll_shop.addView(titleText, titleLayoutParams);
//
//                TextView priceText = new TextView(mContext);
//                priceText.setTextSize(14);
//                priceText.setTextColor(mContext.getResources().getColor(R.color.color_fd4600));
//
//                String price = "￥" + goodsBean.getPrice() + " 送" + goodsBean.getService_amount();
//                if (price.length() > 14) {
//                    price = price.substring(0, 14);
//                }
//                SpannableString spannableString = new SpannableString(price);
//                ForegroundColorSpan colorSpan = new ForegroundColorSpan(Color.parseColor("#FEBA42"));
//                //设置颜色
//                spannableString.setSpan(colorSpan, price.indexOf("送"), spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                AbsoluteSizeSpan absoluteSizeSpan = new AbsoluteSizeSpan(12, true);
//                //设置大小
//                spannableString.setSpan(absoluteSizeSpan, price.indexOf("送"), spannableString.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//                priceText.setText(spannableString);
//
//                ll_shop.addView(priceText, priceLayoutParams);
//                ll_shop.setOnClickListener(v -> LinkParseUtil.parse(mContext, item.getUrl(), ""));
//
//                holder.ll_content.addView(ll_shop);
//
//                ll_shop = null;
//                imageView = null;
//                titleText = null;
//                priceText = null;
//                spannableString = null;
//                spannableString = null;
//                colorSpan = null;
//                absoluteSizeSpan = null;
//            }
//
//            layoutParams = null;
//            titleLayoutParams = null;
//            priceLayoutParams = null;
//            leftText = null;
//            rightText = null;
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.adapter_benefit_recommend_item, null);
    }

}
