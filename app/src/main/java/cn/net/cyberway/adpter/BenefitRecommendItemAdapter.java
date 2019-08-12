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

public class BenefitRecommendItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>/* extends BeeBaseAdapter */ {

    private Context mContext;
    private List<BenefitChannlEntity.ContentBean.RecommendBean.DataBean.RecommendGoodsBean> mList;

    public BenefitRecommendItemAdapter(Context mContext, List<BenefitChannlEntity.ContentBean.RecommendBean.DataBean.RecommendGoodsBean> mList) {
        this.mContext = mContext;
        this.mList = mList;
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


//    private LayoutInflater mInflater;
//    public ArrayList<FANPIAOCONTENTDATA> content = new ArrayList<FANPIAOCONTENTDATA>();
//
//    public FindPropertyBottomRVAdapter(Context context, ArrayList<FANPIAOCONTENTDATA> contentBanner) {
//        mInflater = LayoutInflater.from(context);
//        content = contentBanner;
//    }

//    public void setData(ArrayList<FANPIAOCONTENTDATA> content) {
//        this.content = content;
//        notifyDataSetChanged();
//    }

//    @Override
//    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sv_findpro_bottomitem, parent, false);
//        FindPropertyBottomRVAdapter.MyTicketBottomViewHolder myTicketBottomViewHolder = new FindPropertyBottomRVAdapter.MyTicketBottomViewHolder(view);
//        return myTicketBottomViewHolder;
//    }

   /* @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((FindPropertyBottomRVAdapter.MyTicketBottomViewHolder) holder).name.setText(content.get(position).name);
        ImageLoader.getInstance().displayImage(content.get(position).img, ((FindPropertyBottomRVAdapter.MyTicketBottomViewHolder) holder).img, BeeFrameworkApp.optionsImage);
        ((FindPropertyBottomRVAdapter.MyTicketBottomViewHolder) holder).rl_findpro_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mShared = mInflater.getContext().getSharedPreferences(UserAppConst.USERINFO, 0);
                if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                    String linkUrl = content.get(position).url;
                    LinkParseUtil.parse(mInflater.getContext(), linkUrl, content.get(position).name);
                } else {
                    LinkParseUtil.parse(mInflater.getContext(), "", "");
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return content == null ? 0 : content.size();

    }

    private static class MyTicketBottomViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView name;
        private RelativeLayout rl_findpro_bottom;

        public MyTicketBottomViewHolder(View itemView) {
            super(itemView);
            rl_findpro_bottom = (RelativeLayout) itemView.findViewById(R.id.rl_findpro_bottom);
            img = (ImageView) itemView.findViewById(R.id.iv_findpro_bottom);
            name = (TextView) itemView.findViewById(R.id.tv_findpro_bottom);
        }
    }

*/

/*

    //    private int imgSize;
    private LinearLayout.LayoutParams layoutParamsImg;

    public BenefitRecommendItemAdapter(Context context, List<BenefitChannlEntity.ContentBean.RecommendBean.DataBean.RecommendGoodsBean> list*/
    /*, int imgSize*//*
) {
        super(context, list);
//        this.imgSize = imgSize;
    }

    public class ViewHolder extends BeeCellHolder {
        private LinearLayout ll_item;
        private ImageView iv_shop;
        private TextView tv_title;
        private TextView tv_price_left;
        private TextView tv_price_right;
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        BenefitRecommendItemAdapter.ViewHolder holder = new BenefitRecommendItemAdapter.ViewHolder();
        holder.ll_item = cellView.findViewById(R.id.ll_item);
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
//            layoutParamsImg = new LinearLayout.LayoutParams(imgSize, imgSize);
//            holder.iv_shop.setLayoutParams(layoutParamsImg);

            GlideImageLoader.loadImageDefaultDisplay(mContext, item.getImage(), holder.iv_shop, Utils.dip2px(mContext, 4), R.drawable.default_image, R.drawable.default_image);
            holder.tv_title.setText(item.getName());
            holder.tv_price_left.setText("￥" + item.getPrice());
            holder.tv_price_right.setText("送" + item.getService_amount());
            holder.ll_item.setOnClickListener(v -> LinkParseUtil.parse(mContext, item.getUrl(), ""));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.adapter_benefit_recommend_item, null);
    }
*/

}
