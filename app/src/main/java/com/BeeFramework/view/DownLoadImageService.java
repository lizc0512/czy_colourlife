package com.BeeFramework.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.widget.ImageView;

import com.BeeFramework.AppConst;
import com.BeeFramework.Utils.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ImageViewTarget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 创建时间 : 2017/4/6.
 * 编写人 :  ${yuansk}
 * 功能描述:
 * 版本:
 */

public class DownLoadImageService {
    private String url;
    private Context context;
    private ImageDownLoadCallBack callBack;
    private File currentFile;

    public DownLoadImageService(Context context, String url, ImageDownLoadCallBack callBack) {
        this.url = url;
        this.callBack = callBack;
        this.context = context;
    }

    public void savaPhoto(TouchImageView touchImageView) {
        try {
            Glide.with(context.getApplicationContext())
                    .load(url)
                    .into(new ImageViewTarget<Drawable>(touchImageView) {
                        @Override
                        protected void setResource(@Nullable Drawable resource) {
                            BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;
                            Bitmap bitmap = bitmapDrawable.getBitmap();
                            if (bitmap != null) {
                                saveImageToGallery(context, bitmap);
                                if (bitmap != null && currentFile.exists()) {
                                    callBack.onDownLoadSuccess(bitmap);
                                } else {
                                    callBack.onDownLoadFailed();
                                }
                            } else {
                                ToastUtil.toastShow(context, "图片下载失败");
                            }
                        }
                    });
        }catch (Exception e){

        }
    }

    public void saveImageToGallery(Context context, Bitmap bmp) {
        // 首先保存图片
        File file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).getAbsoluteFile();//注意小米手机必须这样获得public绝对路径
        String fileName = "/" + AppConst.CASH + "/download/img";
        File appDir = new File(file, fileName);
        if (!appDir.exists()) {
            appDir.mkdirs();
        }
        fileName = System.currentTimeMillis() + ".jpg";
        currentFile = new File(appDir, fileName);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(currentFile);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (fos != null) {
                    fos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        // 最后通知图库更新
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        Uri notifyURI = null;
        File notifyFile = new File(currentFile.getPath());
        if (Build.VERSION.SDK_INT >= 24) {
            notifyURI = FileProvider.getUriForFile(context, "cn.net.cyberway" + ".fileprovider", notifyFile);
        } else {
            notifyURI = Uri.fromFile(notifyFile);
        }
        intent.setData(notifyURI);
        context.sendBroadcast(intent);
    }
}
