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
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.ViewTarget;
import com.bumptech.glide.request.transition.Transition;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;

import cn.net.cyberway.R;
import cn.net.cyberway.view.GlideRoundTransform;

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


    //加载指定大小
    public static void loadImageSizeDisplay(Context mContext, String path, int width, int height, ImageView mImageView) {
        try {
            Glide.with(mContext).load(path).apply(new RequestOptions().override(width, height)).into(mImageView);
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
    public static void loadImagePlaceHolderDisplay(Context mContext, String path, ImageView mImageView, int lodingImage) {
        try {
            Glide.with(mContext).load(path).apply(new RequestOptions().placeholder(lodingImage)).into(mImageView);
        } catch (Exception e) {

        }

    }

    //设置加载中图片
    public static void loadActiveImageDisplay(Context mContext, String path, ImageView mImageView, int lodingImage, int errorImageView,int radius ) {
        try {
            Glide.with(mContext).load(path).apply(new RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL).error(errorImageView)
                    .placeholder(lodingImage).transform(new GlideRoundTransform(mContext, radius))
            ).into(mImageView);
        } catch (Exception e) {

        }
    }

    //加载指定大小
    public static void loadImageSizeDisplay(Context mContext, String path, int width, int height, int radius,ImageView mImageView,int lodingImage, int errorImageView) {
        try {
            Glide.with(mContext).load(path).apply(new RequestOptions().override(width, height).diskCacheStrategy(DiskCacheStrategy.ALL).error(errorImageView)
                    .placeholder(lodingImage).transform(new GlideRoundTransform(mContext, radius))
            ).into(mImageView);
        } catch (Exception e) {

        }
    }

    public static DisplayImageOptions optionsImage;        // DisplayImageOptions是用于设置图片显示的类

    public static void initImageLoader(Context context) {
        if (!ImageLoader.getInstance().isInited()) {
            ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
                    .threadPriority(Thread.NORM_PRIORITY - 2)//设置线程的优先级
                    .threadPoolSize(3)
                    .denyCacheImageMultipleSizesInMemory()//当同一个Uri获取不同大小的图片，缓存到内存时，只缓存一个。默认会缓存多个不同的大小的相同图片
                    .diskCacheFileNameGenerator(new Md5FileNameGenerator())//设置缓存文件的名字
                    .tasksProcessingOrder(QueueProcessingType.FIFO)// 设置图片下载和显示的工作队列排序
                    .memoryCache(new WeakMemoryCache())
                    .build();
            ImageLoader.getInstance().init(config);
        }
        if (optionsImage == null) {
            optionsImage = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.default_image)            // 设置图片下载期间显示的图片
                    .showImageForEmptyUri(R.drawable.default_image)    // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.default_image)        // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中*
                    .cacheOnDisk(true)                            // 设置下载的图片是否缓存在SD卡中
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
        }
    }
}
