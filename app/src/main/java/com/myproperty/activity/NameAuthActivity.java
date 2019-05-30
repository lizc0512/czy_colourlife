package com.myproperty.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.feed.utils.CompressHelper;
import com.myproperty.model.PropertyInfoModel;
import com.myproperty.protocol.HouseaddressPropretyifyPostApi;
import com.myproperty.protocol.ImageuploadPostApi;
import com.permission.AndPermission;

import java.io.File;
import java.util.ArrayList;

import cn.csh.colourful.life.utils.GlideImageLoader;
import cn.csh.colourful.life.view.imagepicker.ImagePicker;
import cn.csh.colourful.life.view.imagepicker.bean.ImageItem;
import cn.csh.colourful.life.view.imagepicker.ui.ImageGridActivity;
import cn.net.cyberway.R;
import cn.net.cyberway.utils.FileUtils;


public class NameAuthActivity extends BaseActivity implements View.OnClickListener, HttpApiResponse {
    private ImageView mBack;
    private TextView mTitle;
    private TextView mRightText;
    private Button button;//提交按钮
    private ImageView iv_nameauth_idfront2;
    private ImageView iv_nameauth_idfront3;
    private ImageView iv_nameauth_idback2;
    private ImageView iv_nameauth_idback3;
    private ImageView iv_nameauth_propertyfront2;
    private ImageView iv_nameauth_propertyfront3;
    private ImageView iv_nameauth_propertyback2;
    private ImageView iv_nameauth_propertyback3;
    private PropertyInfoModel propertyInfoModel;
    private EditText etfamilyName;
    private EditText etName;
    private EditText etPhone;
    private EditText etId;

    private String room_uuid;
    private String property_id;
    private String familyName;
    private String name;
    private String phone;
    private String idCard;
    private String types;
    private String idfront = "";
    private String idback = "";
    private String profront = "";
    private String proback = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_name_auth);
        initView();
        initImagePicker();
    }

    private void initView() {
        Intent intent = this.getIntent();
        room_uuid = intent.getStringExtra("house_uuid");
        property_id = String.valueOf(intent.getIntExtra("property_id", 0));
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        mRightText = (TextView) findViewById(R.id.user_top_view_right);
        mTitle.setText(getResources().getString(R.string.title_realname_indenty));
        mBack.setOnClickListener(this);
        etfamilyName = (EditText) findViewById(R.id.et_nameauth_familyname);
        etName = (EditText) findViewById(R.id.et_nameauth_name);
        etPhone = (EditText) findViewById(R.id.et_nameauth_phone);
        etId = (EditText) findViewById(R.id.et_nameauth_idcard);
        button = (Button) findViewById(R.id.btn_nameauth_commit);
        iv_nameauth_idfront2 = (ImageView) findViewById(R.id.iv_nameauth_idfront2);
        iv_nameauth_idfront3 = (ImageView) findViewById(R.id.iv_nameauth_idfront3);
        iv_nameauth_idback2 = (ImageView) findViewById(R.id.iv_nameauth_idback2);
        iv_nameauth_idback3 = (ImageView) findViewById(R.id.iv_nameauth_idback3);
        iv_nameauth_propertyfront2 = (ImageView) findViewById(R.id.iv_nameauth_propertyfront2);
        iv_nameauth_propertyfront3 = (ImageView) findViewById(R.id.iv_nameauth_propertyfront3);
        iv_nameauth_propertyback2 = (ImageView) findViewById(R.id.iv_nameauth_propertyback2);
        iv_nameauth_propertyback3 = (ImageView) findViewById(R.id.iv_nameauth_propertyback3);
        button.setOnClickListener(this);
        iv_nameauth_idfront3.setOnClickListener(this);
        iv_nameauth_idback3.setOnClickListener(this);
        iv_nameauth_propertyfront3.setOnClickListener(this);
        iv_nameauth_propertyback3.setOnClickListener(this);
        etfamilyName.setSelection(etfamilyName.getText().length());
        propertyInfoModel = new PropertyInfoModel(this);
        ThemeStyleHelper.rightTexteFrameLayout(getApplicationContext(), czy_title_layout, mBack, mTitle, mRightText);
    }

    private int carcme = 0;//判断哪个图片位置

    @Override
    public void onClick(View v) {
        final Dialog dialog = new Dialog(this, R.style.dialog);
        View contentView = this.getLayoutInflater().inflate(R.layout.user_avatar_dialog, null);
        dialog.setContentView(contentView);
        android.view.ViewGroup.LayoutParams cursorParams = contentView.getLayoutParams();
        cursorParams.width = getResources().getDisplayMetrics().widthPixels;
        contentView.setLayoutParams(cursorParams);
        dialog.getWindow().setWindowAnimations(R.style.dialogWindowAnim); // 设置窗口弹出动画

        TextView album = (TextView) dialog.findViewById(R.id.avatar_album);
        TextView photograph = (TextView) dialog.findViewById(R.id.avatar_photograph);
        TextView cancel = (TextView) dialog.findViewById(R.id.avatar_cancel);
        carcme = 0;
        switch (v.getId()) {
            case R.id.user_top_view_back:
                NameAuthActivity.this.finish();
                break;
            case R.id.iv_nameauth_idfront3:
                dialog.show();
                //从相册选择上传
                album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        picture(1000);
                    }
                });
                //拍照上传
                photograph.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        openCarcme(1000);

                    }
                });
                //取消
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.iv_nameauth_idback3:
                dialog.show();
                //从相册选择上传
                album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picture(2000);
                        dialog.dismiss();
                    }
                });
                //拍照上传
                photograph.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        openCarcme(2000);

                    }
                });
                //取消
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.iv_nameauth_propertyfront3:
                dialog.show();
                //从相册选择上传
                album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        picture(3000);
                        dialog.dismiss();


                    }
                });
                //拍照上传
                photograph.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        openCarcme(3000);
                    }
                });
                //取消
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.iv_nameauth_propertyback3:
                dialog.show();
                //从相册选择上传
                album.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        picture(4000);
                    }
                });
                //拍照上传
                photograph.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        openCarcme(4000);
                    }
                });
                //取消
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                break;
            case R.id.btn_nameauth_commit:
                if (!(etfamilyName.getText().toString().trim()).isEmpty()) {
                    if (!(etName.getText().toString().trim()).isEmpty()) {
                        if (!(etPhone.getText().toString().trim()).isEmpty() &&
                                Utils.isMobile(etPhone.getText().toString().trim())) {
                            if (!(etId.getText().toString().trim()).isEmpty() &&
                                    etId.getText().toString().trim().length() == 18) {
                                commit();
                            } else {
                                ToastUtil.toastShow(NameAuthActivity.this, getResources().getString(R.string.property_idcard_error));
                            }
                        } else {
                            ToastUtil.toastShow(NameAuthActivity.this, getResources().getString(R.string.property_mobile_error));
                        }
                    } else {
                        ToastUtil.toastShow(NameAuthActivity.this, getResources().getString(R.string.property_name_notice));
                    }
                } else {
                    ToastUtil.toastShow(NameAuthActivity.this, getResources().getString(R.string.property_surname_notice));
                }
                break;
        }
    }

    /**
     * 提交实名认证数据
     */
    private void commit() {
        familyName = etfamilyName.getText().toString().trim();
        name = etName.getText().toString().trim();
        phone = etPhone.getText().toString().trim();
        idCard = etId.getText().toString().trim();
        if (TextUtils.isEmpty(idfront) || TextUtils.isEmpty(idback)) {
            ToastUtil.toastShow(NameAuthActivity.this, getResources().getString(R.string.property_upload_idcard));
        } else {
            propertyInfoModel.postpropretyVerify(this, property_id, room_uuid, familyName,
                    name, phone, idCard, idfront, idback, profront, proback);
        }
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(false);
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }


    /**
     * 本地相册选择图片
     */
    private void picture(int requestCode) {
        ImagePicker.getInstance().setSelectLimit(1);
        Intent intent = new Intent(NameAuthActivity.this, ImageGridActivity.class);
        startActivityForResult(intent, requestCode);
    }

    /**
     * 打开照相机
     */
    private void openCarcme(int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (AndPermission.hasPermission(getApplicationContext(), Manifest.permission.CAMERA)) {
                ImagePicker.getInstance().setSelectLimit(1);
                Intent intent = new Intent(NameAuthActivity.this, ImageGridActivity.class);
                intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
                startActivityForResult(intent, requestCode);
            } else {
                ToastUtil.toastShow(getApplicationContext(), getResources().getString(R.string.user_camerapermission_notice));
            }
        } else {
            ImagePicker.getInstance().setSelectLimit(1);
            Intent intent = new Intent(NameAuthActivity.this, ImageGridActivity.class);
            intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS, true); // 是否是直接打开相机
            startActivityForResult(intent, requestCode);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1000:
            case 2000:
            case 3000:
            case 4000:
                if (null != data) {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    if (null == images || images.size() == 0) {
                        return;
                    }
                    String path = images.get(0).path;
                    Bitmap newBp = FileUtils.decodeSampledBitmapFromFd(path, 100, 100);
                    if (requestCode == 1000) {
                        iv_nameauth_idfront2.setImageBitmap(newBp);
                        types = "idcard_front";
                    } else if (requestCode == 2000) {
                        iv_nameauth_idback2.setImageBitmap(newBp);
                        types = "idcard_back";
                    } else if (requestCode == 3000) {
                        iv_nameauth_propertyfront2.setImageBitmap(newBp);
                        types = "property_front";
                    } else if (requestCode == 4000) {
                        iv_nameauth_propertyback2.setImageBitmap(newBp);
                        types = "property_back";
                    }
                    File newFile = CompressHelper.getDefault(this).compressToFile(new File(path));
                    propertyInfoModel.postimageUpload(this, newFile.getPath(), types);
                }
                break;
        }
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(ImageuploadPostApi.class)) {//上传图片
            ImageuploadPostApi imageuploadPostApi = (ImageuploadPostApi) api;
            if (imageuploadPostApi.response.code == 0) {
                ToastUtil.toastShow(NameAuthActivity.this, getResources().getString(R.string.property_upload_success));
                if (types.equals("idcard_front")) {
                    idfront = imageuploadPostApi.response.content.image_id;
                } else if (types.equals("idcard_back")) {
                    idback = imageuploadPostApi.response.content.image_id;
                } else if (types.equals("property_front")) {
                    profront = imageuploadPostApi.response.content.image_id;
                } else if (types.equals("property_back")) {
                    proback = imageuploadPostApi.response.content.image_id;
                }
                types = "";
            } else {
                ToastUtil.toastShow(NameAuthActivity.this, getResources().getString(R.string.property_upload_fail));
            }
        }
        if (api.getClass().equals(HouseaddressPropretyifyPostApi.class)) {//房产认证
            ToastUtil.toastShow(NameAuthActivity.this, getResources().getString(R.string.property_verify_success));
            NameAuthActivity.this.finish();
        }
    }
}
