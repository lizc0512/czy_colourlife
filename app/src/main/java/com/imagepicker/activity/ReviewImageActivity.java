package com.imagepicker.activity;

import android.os.Bundle;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.view.TouchImageView;
import com.imagepicker.utils.SDCardImageLoader;
import com.imagepicker.utils.ScreenUtils;

import cn.net.cyberway.R;

public class ReviewImageActivity extends BaseActivity {
    public static final String IMAGE_PATH = "image_path";
    private TouchImageView mImage;
    private String              mPath;
    private SDCardImageLoader   mLoader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.review_image);
        mImage = (TouchImageView) findViewById(R.id.review_image);
        mPath = getIntent().getStringExtra(IMAGE_PATH);
        mImage.setTag(mPath);
        mImage.isClickBack(true);
        mLoader = new SDCardImageLoader(ScreenUtils.getScreenW(), ScreenUtils.getScreenH());
        mLoader.loadImage2(2, mPath, mImage);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.my_alpha_finish, R.anim.my_alpha_action);
    }
}
