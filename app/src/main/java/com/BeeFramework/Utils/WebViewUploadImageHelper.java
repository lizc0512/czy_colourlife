package com.BeeFramework.Utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Build;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.community.utils.ImagePickerLoader;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.permission.AndPermission;

import cn.net.cyberway.R;

/**
 * 上传照片工具类
 * Created by chenql on 16/1/7.
 */
public class WebViewUploadImageHelper {


    /*
     * 选择文件result code
     */
    public static final int FILECHOOSER_RESULTCODE = 1;

    /**
     * 相机
     */
    public static final int REQ_CAMERA = FILECHOOSER_RESULTCODE + 1;

    /**
     * 相册选择
     */
    public static final int REQ_CHOOSE = REQ_CAMERA + 1;


    /**
     * 上传的图片类型
     */

    private Activity activity;


    public WebViewUploadImageHelper(Activity activity) {
        if (activity != null) {
            this.activity = activity;
        }
        initImagePicker();
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new ImagePickerLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(false);
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }


    public void selectImage(final String choiceMode) {
        if ("1".equals(choiceMode)) {
            openCarcme(REQ_CAMERA);
        } else if ("2".equals(choiceMode)) {
            chosePic(REQ_CHOOSE);
        } else {
            final Dialog dialog = new Dialog(activity, R.style.dialog);
            @SuppressLint("InflateParams") View contentView = LayoutInflater.from(activity).inflate(R.layout.user_avatar_dialog, null);
            dialog.setContentView(contentView);
            android.view.ViewGroup.LayoutParams cursorParams = contentView.getLayoutParams();
            cursorParams.width = activity.getResources().getDisplayMetrics().widthPixels;
            contentView.setLayoutParams(cursorParams);

            dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画
            dialog.show();

            TextView album = (TextView) dialog.findViewById(R.id.avatar_album);
            TextView photograph = (TextView) dialog.findViewById(R.id.avatar_photograph);
            TextView cancel = (TextView) dialog.findViewById(R.id.avatar_cancel);

            //从相册选择上传
            album.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    chosePic(REQ_CHOOSE);
                }
            });

            //拍照上传
            photograph.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    openCarcme(REQ_CAMERA);
                }
            });
            //取消
            cancel.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {

                    dialog.dismiss();
                }
            });
            contentView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                }
            });
        }
    }

    /**
     * 打开照相机
     */
    private void openCarcme(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermission(activity, Manifest.permission.CAMERA)) {
                Intent intent = new Intent(activity, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                activity.startActivityForResult(intent, requestCode);
            } else {
                ToastUtil.toastShow(activity, "相机权限未开启，请去开启该权限");
            }
        } else {
            Intent intent = new Intent(activity, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            activity.startActivityForResult(intent, requestCode);
        }
    }

    /**
     * 本地相册选择图片
     */
    private void chosePic(int requestCode) {
        Intent intent = new Intent(activity, ImageGridActivity.class);
        activity.startActivityForResult(intent, requestCode);
    }
}