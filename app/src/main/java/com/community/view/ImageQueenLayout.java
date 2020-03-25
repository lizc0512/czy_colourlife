package com.community.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.view.CircleImageView;
import com.BeeFramework.view.Util;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;

/**
 * author:yuansk
 * cretetime:2020/3/18
 * desc:
 **/
public class ImageQueenLayout extends RelativeLayout {
    private Context mContext;

    public ImageQueenLayout(Context context) {
        super(context);
    }

    public ImageQueenLayout(Context context, AttributeSet attrs, int defStyleAttr, Context mContext) {
        super(context, attrs, defStyleAttr);
        this.mContext = mContext;
    }

    public ImageQueenLayout(Context context, AttributeSet attrs, Context mContext) {
        super(context, attrs);
        this.mContext = mContext;
    }

    public void setDataSource(List<String> dataImageSource, int personNumber) {
        int width = Util.DensityUtil.dip2px(mContext, 18);
        int distance = width / 2;
        for (int j = 0; j < dataImageSource.size(); j++) {
            if (j <= 2) {
                RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(distance, distance);
                int marginLeft = (j - 1) * distance;
                layoutParams.setMargins(marginLeft, 0, 0, 0);
                layoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
                CircleImageView circleImageView = new CircleImageView(mContext);
                circleImageView.setLayoutParams(layoutParams);
                GlideImageLoader.loadImageDisplay(mContext, dataImageSource.get(j), circleImageView);
                addView(circleImageView);
            } else {
                break;
            }
        }
        int  textMarginLeft=0;
        String textContent="";
        if (personNumber==0){
            textMarginLeft=16;
            textContent= "快来参与活动吧";
        }else{
            textMarginLeft=60;
            textContent= personNumber + "个邻居参与";
        }
        RelativeLayout.LayoutParams textLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        textLayoutParams.addRule(RelativeLayout.CENTER_VERTICAL);
        textLayoutParams.setMargins(Util.DensityUtil.dip2px(mContext, textMarginLeft), 0, 0, 0);
        TextView textView = new TextView(mContext);
        textView.setTextColor(mContext.getResources().getColor(R.color.color_666666));
        textView.setTextSize(11);
        textView.setText(textContent);
        addView(textView);
    }
}
