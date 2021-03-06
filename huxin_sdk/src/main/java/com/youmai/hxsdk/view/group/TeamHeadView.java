package com.youmai.hxsdk.view.group;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;


import com.youmai.hxsdk.R;

import java.util.List;

/**
 * 合成图片的view
 * Created by yangjianxin on 17/7/14.
 */

public class TeamHeadView extends AppCompatImageView {
    /**
     * 群聊头像合成器
     */
    private TeamHeadSynthesizer teamHeadSynthesizer;
    private int imageSize = 100;
    private int synthesizedBg = Color.LTGRAY;
    private int defaultImageResId = R.drawable.color_default_header;
    private int imageGap = 2;

    public TeamHeadView(Context context) {
        super(context);
        init(context);
    }

    public TeamHeadView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        init(context);
    }

    public TeamHeadView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttrs(attrs);
        init(context);
    }

    private void initAttrs(AttributeSet attributeSet) {
        TypedArray ta = getContext().obtainStyledAttributes(attributeSet, R.styleable.TeamHeadView);
        if (null != ta) {
            synthesizedBg = ta.getColor(R.styleable.TeamHeadView_synthesized_image_bg, synthesizedBg);
            defaultImageResId = ta.getResourceId(R.styleable.TeamHeadView_synthesized_default_image, defaultImageResId);
            imageSize = ta.getDimensionPixelSize(R.styleable.TeamHeadView_synthesized_image_size, imageSize);
            imageGap = ta.getDimensionPixelSize(R.styleable.TeamHeadView_synthesized_image_gap, imageGap);
            ta.recycle();
        }
    }

    private void init(Context context) {
        teamHeadSynthesizer = new TeamHeadSynthesizer(context, this);
        teamHeadSynthesizer.setMaxWidthHeight(imageSize, imageSize);
        teamHeadSynthesizer.setDefaultImage(defaultImageResId);
        teamHeadSynthesizer.setBgColor(synthesizedBg);
        teamHeadSynthesizer.setGap(imageGap);
    }

    public TeamHeadView displayImage(List<String> imageUrls) {
        teamHeadSynthesizer.getMultiImageData().setImageUrls(imageUrls);
        return this;
    }

    public TeamHeadView defaultImage(int defaultImage) {
        teamHeadSynthesizer.setDefaultImage(defaultImage);
        return this;
    }

    public TeamHeadView synthesizedWidthHeight(int maxWidth, int maxHeight) {
        teamHeadSynthesizer.setMaxWidthHeight(maxWidth, maxHeight);
        return this;
    }

    public void load() {
        teamHeadSynthesizer.load();
    }

}
