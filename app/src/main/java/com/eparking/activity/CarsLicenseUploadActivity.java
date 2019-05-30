package com.eparking.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.Util;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.helper.PermissionUtils;
import com.eparking.model.ParkingApplyModel;
import com.eparking.protocol.UploadImgPathEntity;
import com.eparking.view.cardcamera.camera.CameraActivity;
import com.feed.utils.CompressHelper;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.youmai.hxsdk.config.FileConfig;

import java.io.File;
import java.util.ArrayList;

import cn.csh.colourful.life.view.imagepicker.ImagePicker;
import cn.csh.colourful.life.view.imagepicker.bean.ImageItem;
import cn.csh.colourful.life.view.imagepicker.ui.ImageGridActivity;
import cn.net.cyberway.R;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;

import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGBUILDNAME;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGCOMMUNITYID;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGROLE;
import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGROOMNAME;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  认证车辆和月卡申请绑定车辆  上传证件信息
 */
public class CarsLicenseUploadActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String FROMSOURCE = "fromsource";
    public static final String APPLAYNAME = "applayname"; //申请人姓名
    public static final String APPLAYPHONE = "applayphone";//申请人手机号
    public static final String CARLOGO = "carlogo"; //车的logo
    public static final String CARNUMBER = "carnumber";//车牌号

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private TextView user_top_view_right;
    private RelativeLayout cert_car_layout; //绑定车辆的layout
    private ImageView iv_car_logo; //车辆的logo
    private TextView tv_car_brand; //车牌号
    private TextView tv_car_type;//颜色 型号
    private View cert_car_view;

    private TextView tv_user_phone;
    private ImageView iv_vehiclelicens;
    private ImageView iv_driverlicense;
    private Button btn_check_finish;
    private LinearLayout custom_service_layout;

    private ParkingApplyModel parkingApplyModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_certified_vehicle);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        user_top_view_right.setText(getResources().getString(R.string.apply_record));
        user_top_view_right.setTextColor(getResources().getColor(R.color.textcolor_27a2f0));
        tv_user_phone = findViewById(R.id.tv_user_phone);
        cert_car_layout = findViewById(R.id.cert_car_layout);
        iv_car_logo = findViewById(R.id.iv_car_logo);
        tv_car_brand = findViewById(R.id.tv_car_brand);
        tv_car_type = findViewById(R.id.tv_car_type);
        cert_car_view = findViewById(R.id.cert_car_view);
        iv_vehiclelicens = findViewById(R.id.iv_vehiclelicens);
        iv_driverlicense = findViewById(R.id.iv_driverlicense);
        btn_check_finish = findViewById(R.id.btn_check_finish);
        custom_service_layout = findViewById(R.id.custom_service_layout);
        imageView_back.setOnClickListener(this);
        iv_vehiclelicens.setOnClickListener(this);
        iv_driverlicense.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        btn_check_finish.setOnClickListener(this);
        custom_service_layout.setOnClickListener(this);
        parkingApplyModel = new ParkingApplyModel(this);
        initData();
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private int source = 0;
    private String phone;
    private String name;
    private String parkingId;
    private String plate;
    private String buildName;
    private String roomName;
    private String roleName;

    private void initData() {
        Intent intent = getIntent();
        source = intent.getIntExtra(FROMSOURCE, 0);
        if (source == 1) {
            tv_title.setText(getResources().getString(R.string.title_indentify_car));
            user_top_view_right.setVisibility(View.GONE);
            cert_car_layout.setVisibility(View.VISIBLE);
            cert_car_view.setVisibility(View.VISIBLE);
            tv_user_phone.setVisibility(View.GONE);
            String carLogo = intent.getStringExtra(CARLOGO);
            plate = intent.getStringExtra(CARNUMBER);
            GlideImageLoader.loadImageDefaultDisplay(CarsLicenseUploadActivity.this, carLogo, iv_car_logo, R.drawable.eparking_img_carmodel, R.drawable.eparking_img_carmodel);
            tv_car_brand.setText(plate);
        } else {
            tv_title.setText(getResources().getString(R.string.title_apply_monthcard));
            user_top_view_right.setVisibility(View.VISIBLE);
            cert_car_layout.setVisibility(View.GONE);
            cert_car_view.setVisibility(View.GONE);
            tv_user_phone.setVisibility(View.VISIBLE);
            phone = intent.getStringExtra(APPLAYPHONE);
            name = intent.getStringExtra(APPLAYPHONE);
            plate = intent.getStringExtra(CARNUMBER);
            parkingId = intent.getStringExtra(PARKINGCOMMUNITYID);
            buildName = intent.getStringExtra(PARKINGBUILDNAME);
            roomName = intent.getStringExtra(PARKINGROOMNAME);
            roleName = intent.getStringExtra(PARKINGROLE);
            tv_user_phone.setText("请上传用户" + phone + "的有效行驶证");
        }
        initImagePicker();
        setBtnStatus(false);
    }

    private void setBtnStatus(boolean isClick) {
        if (isClick) {
            btn_check_finish.setBackgroundResource(R.drawable.shape_authorizeation_btn);
        } else {
            btn_check_finish.setBackgroundResource(R.drawable.shape_openticket_default);
        }
        btn_check_finish.setEnabled(isClick);
    }

    private void initImagePicker() {
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new cn.csh.colourful.life.utils.GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(false);  //显示拍照按钮
        imagePicker.setCrop(false);
        imagePicker.setMultiMode(false);
        imagePicker.setSelectLimit(1);    //选中数量限制
        imagePicker.setOutPutX(Util.DensityUtil.dip2px(CarsLicenseUploadActivity.this, 325));//保存文件的宽度。单位像素
        imagePicker.setOutPutY(Util.DensityUtil.dip2px(CarsLicenseUploadActivity.this, 200));//保存文件的高度。单位像素
    }

    private static final int AVATAR_ALBUM = 2;

    /**
     * 用户选择图片
     */
    private void choosePicture() {
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
                dialog.dismiss();
                Intent intent = new Intent(CarsLicenseUploadActivity.this, ImageGridActivity.class);
                startActivityForResult(intent, AVATAR_ALBUM);
            }
        });

        //拍照上传
        photograph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                CameraActivity.toCameraActivity(CarsLicenseUploadActivity.this, CameraActivity.TYPE_CARD_OTHER);
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
            case R.id.user_top_view_right:
                Intent intent = new Intent(CarsLicenseUploadActivity.this, MonthCardApplyRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.iv_vehiclelicens:
                choiceType = 0;
                choosePicture();
                break;
            case R.id.iv_driverlicense:
                choiceType = 1;
                choosePicture();
                break;
            case R.id.btn_check_finish: //同步到我的车辆列表和车牌号选择
                if (source == 1) {
                    //绑定车辆
                    parkingApplyModel.contractCertAdd(2, plate, uploadVehiclePath, uploadDrvierPath, this);
                } else {
                    //申请月卡
                    parkingApplyModel.contractApplyOperation(3, phone, name, plate, uploadVehiclePath, uploadDrvierPath, parkingId, buildName, roomName, roleName, this);
                }
                break;
            case R.id.custom_service_layout: //联系客服
                PermissionUtils.showPhonePermission(CarsLicenseUploadActivity.this);
                break;
        }
    }

    private int choiceType = 0;


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case CameraActivity.RESULT_CODE:
            case ImagePicker.RESULT_CODE_ITEMS:
                String imagePath = "";
                if (resultCode == CameraActivity.RESULT_CODE) {
                    imagePath = CameraActivity.getImagePath(data);
                } else {
                    ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                    if (images.size() > 0) {
                        imagePath = images.get(0).path;
                    }
                }
                if (choiceType == 0) {
                    iv_vehiclelicens.setImageBitmap(BitmapFactory.decodeFile(imagePath));
                } else {
                    iv_driverlicense.setImageBitmap(BitmapFactory.decodeFile(imagePath));
                }

                final String finalImagePath = imagePath;
                Luban.with(CarsLicenseUploadActivity.this)
                        .load(imagePath)
                        .ignoreBy(500)
                        .setTargetDir(FileConfig.getThumbImagePaths())
                        .setCompressListener(new OnCompressListener() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSuccess(File file) {
                                parkingApplyModel.contractUploadOperation(choiceType, file.getPath(), CarsLicenseUploadActivity.this);
                            }

                            @Override
                            public void onError(Throwable e) {
                                if (!TextUtils.isEmpty(finalImagePath)) {
                                    CompressHelper compressHelper = null;
                                    CompressHelper.Builder builder = new CompressHelper.Builder(CarsLicenseUploadActivity.this);
                                    builder.setQuality(60);
                                    builder.setMaxWidth(650);
                                    builder.setMaxHeight(400);
                                    compressHelper = builder.build();
                                    File compressFile = compressHelper.compressToFile(new File(finalImagePath));
                                    parkingApplyModel.contractUploadOperation(choiceType, compressFile.getPath(), CarsLicenseUploadActivity.this);
                                }
                            }
                        }).launch();

                break;
        }
    }

    private String uploadVehiclePath;
    private String uploadDrvierPath;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
            case 1:
                try {
                    UploadImgPathEntity uploadImgPathEntity = GsonUtils.gsonToBean(result, UploadImgPathEntity.class);
                    if (what == 0) {
                        uploadVehiclePath = uploadImgPathEntity.getContent();
                    } else {
                        uploadDrvierPath = uploadImgPathEntity.getContent();
                    }
                    if (!TextUtils.isEmpty(uploadVehiclePath) && !TextUtils.isEmpty(uploadDrvierPath)) {
                        setBtnStatus(true);
                    } else {
                        setBtnStatus(false);
                    }
                } catch (Exception e) {

                }
                break;
            case 2:
                BaseContentEntity successEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                ToastUtil.toastShow(CarsLicenseUploadActivity.this, successEntity.getMessage());
                ParkingActivityUtils.getInstance().exit();
                break;
            case 3:
                BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                ToastUtil.toastShow(CarsLicenseUploadActivity.this, baseContentEntity.getMessage());
                ParkingActivityUtils.getInstance().exit();
                break;
        }
    }
}
