package com.community.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.Util;
import com.community.entity.CommunityFileEntity;
import com.community.model.CommunityDynamicsModel;
import com.nohttp.utils.GsonUtils;

import cn.net.cyberway.R;

public class CommunityImageView extends RelativeLayout implements NewHttpResponse {
    private Context mContext;
    private AnimationDrawable mAnimationDrawable = null;
    private CommunityDynamicsModel communityDynamicsModel;
    public String mUploadPhotoUrl = null;
    public String mUploadPhotoId = null;
    private String uploadFilePath;
    public ImageView iv_upload_image;
    private ImageView anim_view;
    public ImageView del_upload_image;

    public CommunityImageView(Context context) {
        super(context);
        mContext = context;
        initView();
    }

    public CommunityImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        initView();
    }

    public CommunityImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        initView();
    }


    private void initView() {
        LayoutInflater.from(mContext).inflate(R.layout.dynamic_upload_layout, this);
        iv_upload_image = findViewById(R.id.iv_upload_image);
        anim_view = findViewById(R.id.anim_view);
        del_upload_image = findViewById(R.id.del_upload_image);
    }


    public void setImageWithFilePath(String filePath, Bitmap bitmap) {
        iv_upload_image.setImageBitmap(bitmap);
        anim_view.setVisibility(View.VISIBLE);
        RelativeLayout.LayoutParams centerLayoutParams = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        centerLayoutParams.addRule(RelativeLayout.CENTER_IN_PARENT);
        anim_view.setLayoutParams(centerLayoutParams);
        anim_view.setImageResource(R.drawable.img_loading_animation);
        mAnimationDrawable = (AnimationDrawable) anim_view.getDrawable();
        mAnimationDrawable.setOneShot(false);
        mAnimationDrawable.start();

        RelativeLayout.LayoutParams topLayoutParmas = new RelativeLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        topLayoutParmas.addRule(RelativeLayout.ALIGN_PARENT_TOP);
        topLayoutParmas.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        topLayoutParmas.setMargins(0, Util.DensityUtil.dip2px(mContext, 5), Util.DensityUtil.dip2px(mContext, 5), 0);
        del_upload_image.setLayoutParams(topLayoutParmas);
        del_upload_image.setImageResource(R.drawable.community_dynamics_delpics);
        uploadFilePath = filePath;
        uploadDynamicFile(filePath);
    }

    private void uploadDynamicFile(String filePath) {
        communityDynamicsModel = new CommunityDynamicsModel(mContext);
        communityDynamicsModel.uploadDynamicFile(0, false, filePath, this);
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        if (TextUtils.isEmpty(result)) { //上传失败重新静默上传一次
            uploadDynamicFile(uploadFilePath);
        } else {
            try {
                if (null != mAnimationDrawable) {
                    mAnimationDrawable.stop();
                }
                anim_view.setVisibility(View.GONE);
                mAnimationDrawable = null;
                CommunityFileEntity communityFileEntity = GsonUtils.gsonToBean(result, CommunityFileEntity.class);
                CommunityFileEntity.ContentBean contentBean = communityFileEntity.getContent();
                mUploadPhotoUrl = contentBean.getUrl();
                mUploadPhotoId = contentBean.getObj();
            } catch (Exception e) {

            }
        }
    }
}
