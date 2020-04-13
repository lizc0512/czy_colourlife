package com.nohttp.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.TransformationUtils;
import com.bumptech.glide.util.Util;

import java.nio.ByteBuffer;
import java.security.MessageDigest;

/**
 * 文件名:
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:
 * 描述:加载自定义圆角大小的图片  解决刷新闪烁问题
 **/
public class RoundedCornersTransform extends BitmapTransformation {
    private static final String ID = "com.bumptech.glide.load.resource.bitmap.FillSpace";
    private static final byte[] ID_BYTES = ID.getBytes(CHARSET);

    private float radius;

    private boolean isLeftTop, isRightTop, isLeftBottom, isRightBotoom;

    /**
     * 需要设置圆角的部分
     *
     * @param leftTop     左上角
     * @param rightTop    右上角
     * @param leftBottom  左下角
     * @param rightBottom 右下角
     */
    public void setNeedCorner(boolean leftTop, boolean rightTop, boolean leftBottom, boolean rightBottom) {
        isLeftTop = leftTop;
        isRightTop = rightTop;
        isLeftBottom = leftBottom;
        isRightBotoom = rightBottom;
    }

    /**
     * @param radius 圆角幅度
     */
    public RoundedCornersTransform(float radius) {
        this.radius = radius;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap bitmap = TransformationUtils.centerInside(pool, toTransform, outWidth, outHeight);
        return roundCrop(pool, bitmap);
    }

    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {

        if (source == null) {
            return null;
        }

        int width = source.getWidth();
        int height = source.getHeight();
        //左上角圆角
        Bitmap result = pool.get(width, height, Bitmap.Config.ARGB_8888);
        if (result == null) {
            result = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, this.radius, this.radius, paint);
        if (!isLeftTop) {
            canvas.drawRect(0, 0, radius, radius, paint);
        }
        //右上角圆角
        if (!isRightTop) {
            canvas.drawRect(canvas.getWidth() - radius, 0, canvas.getWidth(), radius, paint);
        }
        //左下角圆角
        if (!isLeftBottom) {
            canvas.drawRect(0, canvas.getHeight() - radius, radius, canvas.getHeight(), paint);
        }
        //右下角圆角
        if (!isRightBotoom) {
            canvas.drawRect(canvas.getWidth() - radius, canvas.getHeight() - radius, canvas.getWidth(), canvas.getHeight(), paint);
        }
        return result;
    }


    @Override
    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
        messageDigest.update(ID_BYTES);

        byte[] radiusData = ByteBuffer.allocate(4).putFloat(radius).array();
        messageDigest.update(radiusData);

    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof RoundedCornersTransform) {
            RoundedCornersTransform other = (RoundedCornersTransform) o;
            return radius == other.radius;
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Util.hashCode(ID.hashCode(),
                Util.hashCode(radius));
    }

}
