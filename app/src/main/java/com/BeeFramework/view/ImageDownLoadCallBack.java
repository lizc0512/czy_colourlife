package com.BeeFramework.view;

import android.graphics.Bitmap;

import java.io.File;

/**
 * 创建时间 : 2017/4/6.
 * 编写人 :  ${yuansk}
 * 功能描述:
 * 版本:
 */

public interface ImageDownLoadCallBack
{
    void onDownLoadSuccess(File file);
    void onDownLoadSuccess(Bitmap bitmap);

    void onDownLoadFailed();
}
