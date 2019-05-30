package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import cn.net.cyberway.home.entity.PersonalRecommendedEntity;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * Created by lizc 2017/10/07.
 */
public class SecondShopPicAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    private List<PersonalRecommendedEntity.ContentBean.ItemsBean.DataBean> dataBeanListataBean;
    private String msgId;

    public SecondShopPicAdapter(Context context, List<PersonalRecommendedEntity.ContentBean.ItemsBean.DataBean> dataBeanListataBean, String msgId) {
        this.mContext = context;
        this.msgId = msgId;
        this.dataBeanListataBean = dataBeanListataBean;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        switch (viewType) {
            case onlyImage:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_guesslike_layout_only, parent, false);
                return new OnlyImageViewHolder(view);
            case leftImage:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_guesslike_layout_left, parent, false);
                return new LeftImageViewHolder(view);
            case rightImage:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_guesslike_layout_right, parent, false);
                return new RightImageViewHolder(view);
            case topImage:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_guesslike_layout_top, parent, false);
                return new TopImageViewHolder(view);
            case bottomImage:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_guesslike_layout_bottom, parent, false);
                return new BottomImageViewHolder(view);
            default:
                view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_guesslike_layout_only, parent, false);
                return new OnlyImageViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final PersonalRecommendedEntity.ContentBean.ItemsBean.DataBean dataBean = dataBeanListataBean.get(position);
        if (holder instanceof OnlyImageViewHolder) {
            OnlyImageViewHolder onlyImageViewHolder = (OnlyImageViewHolder) holder;
            GlideImageLoader.loadImageDefaultDisplay(mContext,dataBean.getImg_url(),onlyImageViewHolder.image_ticket_mall,R.drawable.icon_style_one,R.drawable.icon_style_one);
            onlyImageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinkParseUtil.parse(mContext, dataBean.getLink_url(), dataBean.getImg_title());
                }
            });
        } else if (holder instanceof LeftImageViewHolder) {
            LeftImageViewHolder leftImageViewHolder = (LeftImageViewHolder) holder;
            GlideImageLoader.loadImageDefaultDisplay(mContext,dataBean.getImg_url(),leftImageViewHolder.left_shop_logo,R.drawable.icon_style_three,R.drawable.icon_style_three);
            leftImageViewHolder.left_shop_name.setText(dataBean.getImg_title());
            String subTitle = dataBean.getImg_sub_title();
            if (subTitle.startsWith("f") || subTitle.startsWith("F")) {
                leftImageViewHolder.iv_ticket_logo.setVisibility(View.VISIBLE);
                leftImageViewHolder.left_shop_introduce.setText(subTitle.substring(1, subTitle.length()));
            } else {
                leftImageViewHolder.iv_ticket_logo.setVisibility(View.GONE);
                leftImageViewHolder.left_shop_introduce.setText(subTitle);
            }
            leftImageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinkParseUtil.parse(mContext, dataBean.getLink_url(), dataBean.getImg_title());
                }
            });
        } else if (holder instanceof RightImageViewHolder) {
            RightImageViewHolder rightImageViewHolder = (RightImageViewHolder) holder;
            GlideImageLoader.loadImageDefaultDisplay(mContext,dataBean.getImg_url(),rightImageViewHolder.right_shop_logo,R.drawable.icon_style_three,R.drawable.icon_style_three);
            rightImageViewHolder.right_shop_name.setText(dataBean.getImg_title());
            String subTitle = dataBean.getImg_sub_title();
            if (subTitle.startsWith("f") || subTitle.startsWith("F")) {
                rightImageViewHolder.iv_ticket_logo.setVisibility(View.VISIBLE);
                rightImageViewHolder.right_shop_introduce.setText(subTitle.substring(1, subTitle.length()));
            } else {
                rightImageViewHolder.iv_ticket_logo.setVisibility(View.GONE);
                rightImageViewHolder.right_shop_introduce.setText(subTitle);
            }
            rightImageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinkParseUtil.parse(mContext, dataBean.getLink_url(), dataBean.getImg_title());
                }
            });
        } else if (holder instanceof TopImageViewHolder) {
            TopImageViewHolder topImageViewHolder = (TopImageViewHolder) holder;
            GlideImageLoader.loadImageDefaultDisplay(mContext,dataBean.getImg_url(),topImageViewHolder.top_shop_logo,R.drawable.icon_style_two,R.drawable.icon_style_two);
            topImageViewHolder.top_shop_name.setText(dataBean.getImg_title());
            String subTitle = dataBean.getImg_sub_title();
            if (subTitle.startsWith("f") || subTitle.startsWith("F")) {
                topImageViewHolder.iv_ticket_logo.setVisibility(View.VISIBLE);
                topImageViewHolder.top_shop_introduce.setText(subTitle.substring(1, subTitle.length()));
            } else {
                topImageViewHolder.iv_ticket_logo.setVisibility(View.GONE);
                topImageViewHolder.top_shop_introduce.setText(subTitle);
            }
            topImageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinkParseUtil.parse(mContext, dataBean.getLink_url(), dataBean.getImg_title());
                }
            });
        } else if (holder instanceof BottomImageViewHolder) {
            BottomImageViewHolder bottomImageViewHolder = (BottomImageViewHolder) holder;
            GlideImageLoader.loadImageDefaultDisplay(mContext,dataBean.getImg_url(),bottomImageViewHolder.bottom_shop_logo,R.drawable.icon_style_two,R.drawable.icon_style_two);
            bottomImageViewHolder.bottom_shop_name.setText(dataBean.getImg_title());
            String subTitle = dataBean.getImg_sub_title();
            if (subTitle.startsWith("f") || subTitle.startsWith("F")) {
                bottomImageViewHolder.iv_ticket_logo.setVisibility(View.VISIBLE);
                bottomImageViewHolder.bottom_shop_introduce.setText(subTitle.substring(1, subTitle.length()));
            } else {
                bottomImageViewHolder.iv_ticket_logo.setVisibility(View.GONE);
                bottomImageViewHolder.bottom_shop_introduce.setText(subTitle);
            }

            bottomImageViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    LinkParseUtil.parse(mContext, dataBean.getLink_url(), dataBean.getImg_title());
                }
            });
        }

    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dataBeanListataBean == null ? 0 : dataBeanListataBean.size();
    }

    private final int onlyImage = 1200;//只是展示一张图片的
    private final int leftImage = 1203;
    private final int rightImage = 1204;
    private final int topImage = 1201;
    private final int bottomImage = 1202;

    @Override
    public int getItemViewType(int position) {
        PersonalRecommendedEntity.ContentBean.ItemsBean.DataBean dataBean = dataBeanListataBean.get(position);
        String type = dataBean.getView_type();
        if (TextUtils.isEmpty(type)) {
            return 0;
        } else {
            return Integer.valueOf(type);
        }
    }


    static class OnlyImageViewHolder extends RecyclerView.ViewHolder {
        ImageView image_ticket_mall;

        public OnlyImageViewHolder(View itemView) {
            super(itemView);
            image_ticket_mall = (ImageView) itemView.findViewById(R.id.image_ticket_mall);
        }
    }


    static class LeftImageViewHolder extends RecyclerView.ViewHolder {
        ImageView left_shop_logo;
        ImageView iv_ticket_logo;
        TextView left_shop_name;
        TextView left_shop_introduce;


        public LeftImageViewHolder(View itemView) {
            super(itemView);
            left_shop_logo = (ImageView) itemView.findViewById(R.id.iv_shop_logo);
            iv_ticket_logo = (ImageView) itemView.findViewById(R.id.iv_ticket_logo);
            left_shop_name = (TextView) itemView.findViewById(R.id.tv_shop_name);
            left_shop_introduce = (TextView) itemView.findViewById(R.id.tv_shop_introduce);
        }
    }

    static class RightImageViewHolder extends RecyclerView.ViewHolder {
        ImageView right_shop_logo;
        ImageView iv_ticket_logo;
        TextView right_shop_name;
        TextView right_shop_introduce;

        public RightImageViewHolder(View itemView) {
            super(itemView);
            right_shop_logo = (ImageView) itemView.findViewById(R.id.iv_shop_logo);
            iv_ticket_logo = (ImageView) itemView.findViewById(R.id.iv_ticket_logo);
            right_shop_name = (TextView) itemView.findViewById(R.id.tv_shop_name);
            right_shop_introduce = (TextView) itemView.findViewById(R.id.tv_shop_introduce);
        }
    }

    static class TopImageViewHolder extends RecyclerView.ViewHolder {
        ImageView top_shop_logo;
        ImageView iv_ticket_logo;
        TextView top_shop_name;
        TextView top_shop_introduce;

        public TopImageViewHolder(View itemView) {
            super(itemView);
            top_shop_logo = (ImageView) itemView.findViewById(R.id.iv_shop_logo);
            iv_ticket_logo = (ImageView) itemView.findViewById(R.id.iv_ticket_logo);
            top_shop_name = (TextView) itemView.findViewById(R.id.tv_shop_name);
            top_shop_introduce = (TextView) itemView.findViewById(R.id.tv_shop_introduce);
        }

    }

    static class BottomImageViewHolder extends RecyclerView.ViewHolder {
        ImageView bottom_shop_logo;
        ImageView iv_ticket_logo;
        TextView bottom_shop_name;
        TextView bottom_shop_introduce;


        public BottomImageViewHolder(View itemView) {
            super(itemView);
            bottom_shop_logo = (ImageView) itemView.findViewById(R.id.iv_shop_logo);
            iv_ticket_logo = (ImageView) itemView.findViewById(R.id.iv_ticket_logo);
            bottom_shop_name = (TextView) itemView.findViewById(R.id.tv_shop_name);
            bottom_shop_introduce = (TextView) itemView.findViewById(R.id.tv_shop_introduce);
        }

    }

}
