package com.banner.viewpager;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.BeeFramework.BeeFrameworkApp;
import com.nohttp.utils.GlideImageLoader;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.user.UserAppConst;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.ATTR;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 横幅BannerView 适配器
 */
public class BanerAdapter extends PagerAdapter
{
    private List<ATTR> list;
    private LayoutInflater mInflater;
    private Context mContext;

    public BanerAdapter(Context context, List<ATTR> list) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((LoopViewPager) container).removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        final ViewHolder holder;

        holder = new ViewHolder();
        View imageLayout = mInflater.inflate(R.layout.new_banner_item, null);
        holder.image = (ImageView) imageLayout.findViewById(R.id.news_banner_image);
        final ATTR banner = list.get(position);
        final int currentPosition = position;

        ImageLoader.getInstance().displayImage(banner.img, holder.image, GlideImageLoader.optionsImage );
//        Glide.with(mContext)
//                .load(banner.img)
//                .centerCrop()
//                .placeholder(R.drawable.default_image)
//                .crossFade()
//                .into(holder.image);

        ((LoopViewPager) view).addView(imageLayout, 0);
        imageLayout.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                SharedPreferences mShared = mContext.getSharedPreferences(UserAppConst.USERINFO, 0);
                if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                    LinkParseUtil.parse(mContext, banner.url, banner.name);
                } else {
                    LinkParseUtil.parse(mContext, "", "");
                }

            }
        });

        return imageLayout;
    }

    class ViewHolder
    {
        public ImageView image;
    }
}
