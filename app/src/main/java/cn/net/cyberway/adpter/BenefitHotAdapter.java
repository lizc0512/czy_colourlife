package cn.net.cyberway.adpter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.BenefitHotEntity;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 彩惠人生-推荐
 * Created by hxg on 2019/07/15.
 */
public class BenefitHotAdapter extends BeeBaseAdapter {

    public BenefitHotAdapter(Context context, List<BenefitHotEntity.ContentBean.ListBean> list) {
        super(context, list);
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        BenefitHotAdapter.ViewHolder holder = new BenefitHotAdapter.ViewHolder();
        holder.tv_title = cellView.findViewById(R.id.tv_title);
        holder.tv_content = cellView.findViewById(R.id.tv_content);
        holder.iv_logo = cellView.findViewById(R.id.iv_logo);
        holder.ll_item = cellView.findViewById(R.id.ll_item);
        return holder;
    }

    @SuppressLint("SetTextI18n")
    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        ViewHolder holder = (ViewHolder) h;
        final BenefitHotEntity.ContentBean.ListBean item = (BenefitHotEntity.ContentBean.ListBean) dataList.get(position);

        holder.tv_title.setText(item.getTitle());
        holder.tv_content.setText(item.getDesc());
        GlideImageLoader.loadImageDefaultDisplay(mContext, item.getImage(), holder.iv_logo, R.drawable.icon_default, R.drawable.icon_default);

        holder.ll_item.setOnClickListener(v -> LinkParseUtil.parse(mContext, item.getUrl(), ""));

        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.adapter_benefit_hot, null);
    }

    public class ViewHolder extends BeeCellHolder {
        private TextView tv_title;
        private TextView tv_content;
        private ImageView iv_logo;
        private LinearLayout ll_item;
    }

}
