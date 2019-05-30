package cn.net.cyberway.home.view;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.view
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/1/10 11:50
 * @change
 * @chang time
 * @class describe
 */
public class TransparentDrawable extends Drawable {
    @Override
    public void draw(@NonNull Canvas canvas) {
        canvas.drawColor(Color.parseColor("#ffffff"));
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.UNKNOWN;
    }
}
