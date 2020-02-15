package com.door.view;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.BeeFramework.view.Util;
import com.door.entity.OpenDoorResultEntity;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;


/**
 * @name ${yuansk}
 * @class name：com.door.view
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/17 15:37
 * @change
 * @chang time
 * @class describe
 */

public class AdvisementBannerAdapter extends PagerAdapter {

    private boolean isMultiScr;
    private List<OpenDoorResultEntity.ContentBean.AdBean> adBeanList;
    private Context mContext;

    public AdvisementBannerAdapter(Context mContext, boolean isMultiScr, List<OpenDoorResultEntity.ContentBean.AdBean> adBeanList) {
        this.isMultiScr = isMultiScr;
        this.mContext = mContext;
        this.adBeanList = adBeanList;
    }

    @Override
    public int getCount() {
        return adBeanList == null ? 0 : adBeanList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View view = LayoutInflater.from(container.getContext()).inflate(R.layout.nologinimg_item, null);
        ImageView image = (ImageView) view.findViewById(R.id.no_loimg);
        final OpenDoorResultEntity.ContentBean.AdBean adBean = adBeanList.get(position);
        GlideImageLoader.loadImageDefaultDisplay(container.getContext(), adBean.getImg(), image, R.drawable.icon_style_three, R.drawable.icon_style_three);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) image.getLayoutParams();
        layoutParams.width = LinearLayout.LayoutParams.MATCH_PARENT;
        layoutParams.height = Util.DensityUtil.dip2px(mContext, 120);
        image.setLayoutParams(layoutParams);
        image.setScaleType(ImageView.ScaleType.FIT_XY);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkParseUtil.parse(mContext, adBean.getUri(), "");
            }
        });
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
