package com.feed.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;

import com.BeeFramework.Utils.ImageUtil;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.feed.model.UploadImageModel;
import com.repair.protocol.PhotoPostApi;
import com.repair.protocol.PhotoPostResponse;

import cn.net.cyberway.R;

@SuppressLint("AppCompatCustomView")
public class UploadImageView extends ImageView implements HttpApiResponse {
    private Context mContext;
    private AnimationDrawable mAnimationDrawable = null;
    private UploadImageModel mUploadImageModel;
    public String mUploadPhoto = null;

    public UploadImageView(Context context) {
        super(context);
        mContext = context;
    }

    public UploadImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public UploadImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @SuppressLint("NewApi")
    public void setImageWithFilePath(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        setImageBitmap(bitmap);
        mUploadImageModel = new UploadImageModel(mContext);
        mUploadImageModel.upload(this, filePath);
        mAnimationDrawable = (AnimationDrawable) mContext.getResources().getDrawable(R.drawable.img_loading_animation);
        mAnimationDrawable.setOneShot(false);
        mAnimationDrawable.start();
        startAnimation();

        this.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                removeOnLayoutChangeListener(this);
                Rect drawRect = new Rect();
                UploadImageView.this.getDrawingRect(drawRect);
                int widthPx = ImageUtil.Dp2Px(mContext, 40);
                drawRect.right = widthPx;
                drawRect.bottom = widthPx;
                int offset = (right - left - widthPx) / 2;
                if (drawRect != null) {
                    drawRect.offset(offset, offset);
                    mAnimationDrawable.setBounds(drawRect);
                }
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (null != mAnimationDrawable) {
            mAnimationDrawable.run();
            mAnimationDrawable.getCurrent().draw(canvas);
        }
    }

    Handler mHandler = new Handler();
    Runnable mTick = new Runnable() {
        public void run() {
            invalidate();
            mHandler.postDelayed(this, 60); // 20ms == 60fps
        }
    };

    void startAnimation() {
        mHandler.removeCallbacks(mTick);
        mHandler.post(mTick);
    }

    void stopAnimation() {
        mHandler.removeCallbacks(mTick);
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        stopAnimation();
        mAnimationDrawable = null;
        invalidate();
        PhotoPostApi photoPostApi = (PhotoPostApi) api;
        PhotoPostResponse photoPostResponse = photoPostApi.response;
        mUploadPhoto = photoPostResponse.url;
    }
}
