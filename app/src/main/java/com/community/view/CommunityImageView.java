package com.community.view;

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
import com.BeeFramework.model.NewHttpResponse;
import com.about.model.FeedbackModel;
import com.about.protocol.FeedBackImageEntity;
import com.nohttp.utils.GsonUtils;

import cn.net.cyberway.R;

@SuppressLint("AppCompatCustomView")
public class CommunityImageView extends ImageView implements NewHttpResponse {
    private Context mContext;
    private AnimationDrawable mAnimationDrawable = null;
    private FeedbackModel feedbackModel;
    public String mUploadPhoto = null;
    public String mUploadPhotoId = null;

    public CommunityImageView(Context context) {
        super(context);
        mContext = context;
    }

    public CommunityImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public CommunityImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @SuppressLint("NewApi")
    public void setImageWithFilePath(String filePath) {
        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        setImageBitmap(bitmap);
        feedbackModel = new FeedbackModel(mContext);
        feedbackModel.uploadFeedBackImage(0, filePath, this);
        mAnimationDrawable = (AnimationDrawable) mContext.getResources().getDrawable(R.drawable.img_loading_animation);
        mAnimationDrawable.setOneShot(false);
        mAnimationDrawable.start();
        startAnimation();

        this.addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                removeOnLayoutChangeListener(this);
                Rect drawRect = new Rect();
                CommunityImageView.this.getDrawingRect(drawRect);
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
    public void OnHttpResponse(int what, String result) {
        try {
            stopAnimation();
            mAnimationDrawable = null;
            invalidate();
            FeedBackImageEntity feedBackImageEntity = GsonUtils.gsonToBean(result, FeedBackImageEntity.class);
            FeedBackImageEntity.ContentBean contentBean = feedBackImageEntity.getContent();
            mUploadPhoto = contentBean.getUrl();
            mUploadPhotoId = contentBean.getImg();
        } catch (Exception e) {

        }
    }
}
