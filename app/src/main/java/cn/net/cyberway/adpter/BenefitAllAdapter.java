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
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 彩惠人生-全部
 * Created by hxg on 2019/07/17.
 */
public class BenefitAllAdapter extends BeeBaseAdapter {

    public BenefitAllAdapter(Context context, List<BenefitChannlEntity.ContentBean.AllBean.DataBeanX> list) {
        super(context, list);
    }

    public class ViewHolder extends BeeCellHolder {
        private ImageView iv_shop;
        private TextView tv_title;
        private LinearLayout ll_activity;
        private TextView tv_more;
        public TextView tv_content;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        BenefitAllAdapter.ViewHolder holder = new BenefitAllAdapter.ViewHolder();
        holder.iv_shop = cellView.findViewById(R.id.iv_shop);
        holder.tv_title = cellView.findViewById(R.id.tv_title);
        holder.ll_activity = cellView.findViewById(R.id.ll_activity);
        holder.tv_more = cellView.findViewById(R.id.tv_more);
        holder.tv_content = cellView.findViewById(R.id.tv_content);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        try {
            ViewHolder holder = (ViewHolder) h;
            final BenefitChannlEntity.ContentBean.AllBean.DataBeanX item = (BenefitChannlEntity.ContentBean.AllBean.DataBeanX) dataList.get(position);
            holder.ll_activity.removeAllViews();

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

            TextView leftText = new TextView(mContext);
            leftText.setText("单均送" + item.getStatistics().getAvg_service_amount() + "元");
            leftText.setTextSize(12);
            leftText.setTextColor(mContext.getResources().getColor(R.color.color_fd4600));
            leftText.setBackgroundResource(R.drawable.shape_benefit_text_red);
            holder.ll_activity.addView(leftText, layoutParams);

            TextView rightText = new TextView(mContext);
            rightText.setText(item.getStatistics().getVisit_num() + "用过");
            rightText.setTextSize(12);
            rightText.setTextColor(mContext.getResources().getColor(R.color.color_329dfa));
            rightText.setBackgroundResource(R.drawable.shape_benefit_text_blue);
            holder.ll_activity.addView(rightText, layoutParams);

            holder.tv_content.setText(item.getDesc());
            holder.tv_more.setOnClickListener(v -> LinkParseUtil.parse(mContext, item.getUrl(), ""));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.adapter_benefit_all, null);
    }

}
