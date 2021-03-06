package com.nohttp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.view.Util;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.nohttp.utils
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/11 14:43
 * @change
 * @chang time
 * @class describe
 */

public class GlideImageLoader {


    //加载view的背景图
    public static void loadWrapHeightLayouBg(final Context mContext, String path, final LinearLayout layout) {
        try {
            Glide.with(mContext).asBitmap().load(path).into(new ViewTarget<View, Bitmap>(layout) {

                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    int height = resource.getHeight();
                    int width = resource.getWidth();
                    float scale = (float) height / (float) width;
                    if (scale < 1) {
                        ViewGroup.LayoutParams groupParams = view.getLayoutParams();
                        groupParams.height = (int) (Utils.getDeviceWith(mContext) * scale);
                        layout.setLayoutParams(groupParams);
                    }
                    view.setBackground(new BitmapDrawable(mContext.getResources(), resource));
                }
            });
        } catch (Exception e) {

        }
    }

    public static void loadStableHeightLayouBg(final Context mContext, String path, LinearLayout layout) {
        try {
            Glide.with(mContext).asBitmap().load(path).into(new ViewTarget<View, Bitmap>(layout) {

                @Override
                public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                    view.setBackground(new BitmapDrawable(mContext.getResources(), resource));
                }
            });
        } catch (Exception e) {

        }
    }


    public static void setTintBarColour(Bitmap resource) {
//        Palette.from(resource).generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                Palette.Swatch swatch = palette.getMutedSwatch();
//                int color;
//                if (swatch != null) {
//                    color = swatch.getRgb(); //获取样本颜色
//                   } else {
//                    color = primaryColor; //提取样本失败则使用默认颜色
//                   }
//                   setThemeColor(color); //设置状态栏颜色，下文中会有具体实现 } });
//                }
//            }
    }


    //默认加载
    public static void loadImageDisplay(Context mContext, String path, ImageView mImageView) {
        try {
            Glide.with(mContext).load(path).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)).into(mImageView);
        } catch (Exception e) {

        }
    }


    //设置加载中以及加载失败图片
    public static void loadImageDefaultDisplay(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView) {
        try {
            Glide.with(mContext).load(path).apply(new RequestOptions().placeholder(lodingImage).error(errorImageView).diskCacheStrategy(DiskCacheStrategy.ALL)).into(mImageView);
        } catch (Exception e) {

        }
    }

    //圆角图片 设置加载中以及加载失败图片
    public static void loadImageDefaultDisplay(Context mContext, String path, ImageView mImageView, int radiusNum, int lodingImage, int errorImageView) {
        try {
            RoundedCorners roundedCorners = new RoundedCorners(radiusNum);
            Glide.with(mContext).load(path).apply(new RequestOptions().bitmapTransform(roundedCorners).placeholder(lodingImage).error(errorImageView).diskCacheStrategy(DiskCacheStrategy.ALL)).into(mImageView);
        } catch (Exception e) {

        }
    }


    //设置加载中图片
    public static void loadActiveImageDisplay(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView) {
        try {
            Glide.with(mContext).load(path).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).error(errorImageView)
                    .placeholder(lodingImage).skipMemoryCache(false).dontAnimate()).into(mImageView);
        } catch (Exception e) {

        }
    }

    public static void loadCenterCropImageDisplay(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView) {
        try {
            Glide.with(mContext).load(path).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).error(errorImageView)
                    .placeholder(lodingImage).skipMemoryCache(false).dontAnimate()).into(mImageView);
        } catch (Exception e) {

        }
    }


    public static void loadTopRightCornerImageView(Context mContext, String path, ImageView mImageView) {
        RoundedCornersTransform transform = new RoundedCornersTransform(Util.DensityUtil.dip2px(mContext, 3));
        transform.setNeedCorner(true, true, false, false);
        RequestOptions options = new RequestOptions().placeholder(R.drawable.icon_style_four).transform(transform).diskCacheStrategy(DiskCacheStrategy.ALL).skipMemoryCache(false).dontAnimate();
        Glide.with(mContext).load(path).apply(options).into(mImageView);
    }
}
