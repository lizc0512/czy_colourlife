package com.realaudit.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.permission.AndPermission;

import java.util.ArrayList;

import cn.csh.colourful.life.utils.GlideImageLoader;
import cn.csh.colourful.life.view.imagepicker.ImagePicker;
import cn.csh.colourful.life.view.imagepicker.bean.ImageItem;
import cn.csh.colourful.life.view.imagepicker.ui.ImageGridActivity;
import cn.net.cyberway.R;

/**
 * 文件名:上传要绑定用户新的证件信息
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:
 * 描述:
 **/
public class RealNewUploadActivity extends BaseActivity implements View.OnClickListener {

    private TextView tv_title;   //标题
    private ImageView imageView_back;//返回


    private TextView tv_cardinfor_tips;

    private ImageView iv_idcard_back;
    private ImageView iv_del_back;

    private ImageView iv_idcard_front;
    private ImageView iv_del_front;


    private ImageView iv_idcard_hand;
    private ImageView iv_del_hand;


    private Button btn_define_upload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_orgin_idcard);
        tv_title = findViewById(R.id.user_top_view_title);
        imageView_back = findViewById(R.id.user_top_view_back);
        tv_cardinfor_tips = findViewById(R.id.tv_cardinfor_tips);
        iv_idcard_back = findViewById(R.id.iv_idcard_back);

        iv_del_back = findViewById(R.id.iv_del_back);
        iv_idcard_front = findViewById(R.id.iv_idcard_front);
        iv_del_front = findViewById(R.id.iv_del_front);
        iv_idcard_hand = findViewById(R.id.iv_idcard_hand);
        iv_del_hand = findViewById(R.id.iv_del_hand);
        btn_define_upload = findViewById(R.id.btn_define_upload);

        imageView_back.setOnClickListener(this::onClick);
        iv_del_back.setOnClickListener(this::onClick);
        iv_idcard_back.setOnClickListener(this::onClick);
        iv_idcard_front.setOnClickListener(this::onClick);
        iv_del_front.setOnClickListener(this::onClick);
        iv_idcard_hand.setOnClickListener(this::onClick);
        iv_del_hand.setOnClickListener(this::onClick);
        btn_define_upload.setOnClickListener(this::onClick);
        tv_title.setText(getResources().getString(R.string.real_title_change_realname));
        tv_cardinfor_tips.setText(getResources().getString(R.string.real_new_idcard));
        initImagePicker();
    }

    private void initImagePicker() {
        final ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(false);
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }


    private void choosePicture(int requestCode) {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View contentView = LayoutInflater.from(this).inflate(R.layout.user_avatar_dialog, null);
        dialog.setContentView(contentView);
        android.view.ViewGroup.LayoutParams cursorParams = contentView.getLayoutParams();
        cursorParams.width = getResources().getDisplayMetrics().widthPixels;
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
                Intent intent = new Intent(RealNewUploadActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, requestCode);
                dialog.dismiss();
            }
        });

        //拍照上传
        photograph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (AndPermission.hasPermission(getApplicationContext(), Manifest.permission.CAMERA)) {
                        Intent intent = new Intent(RealNewUploadActivity.this, ImageGridActivity.class);
                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                        startActivityForResult(intent, requestCode);
                    } else {
                        ToastUtil.toastShow(getApplicationContext(), "相机权限未开启，请去开启该权限");
                    }
                } else {
                    Intent intent = new Intent(RealNewUploadActivity.this, ImageGridActivity.class);
                    intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                    startActivityForResult(intent, requestCode);
                }
                dialog.dismiss();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.iv_idcard_back:
                choosePicture(1000);
                break;
            case R.id.iv_del_back:
                iv_idcard_back.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.real_idcard_back));
                break;
            case R.id.iv_idcard_front:
                choosePicture(2000);
                break;
            case R.id.iv_del_front:
                iv_idcard_front.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.real_idcard_front));
                break;
            case R.id.iv_idcard_hand:
                Intent hand_intent = new Intent(RealNewUploadActivity.this, ImageGridActivity.class);
                startActivityForResult(hand_intent, 3000);
                break;
            case R.id.iv_del_hand:
                iv_idcard_hand.setImageBitmap(BitmapFactory.decodeResource(getResources(),R.drawable.real_idcard_hand));
                break;
            case R.id.btn_define_upload:
                Intent intent = new Intent(RealNewUploadActivity.this, RealCheckWaitingActivity.class);
                startActivity(intent);
                break;
        }
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS && null != data) {
            final ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
            if (null == images || images.size() == 0) {
                return;
            }
            String filePath = images.get(0).path;
            switch (requestCode) {
                case 1000:
                    iv_idcard_back.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    break;
                case 2000:
                    iv_idcard_front.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    break;
                case 3000:
                    iv_idcard_hand.setImageBitmap(BitmapFactory.decodeFile(filePath));
                    break;

        }
    }
}
}
