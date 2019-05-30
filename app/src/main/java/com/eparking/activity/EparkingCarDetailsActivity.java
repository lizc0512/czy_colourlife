package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.CustomDialog;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingHomeModel;
import com.eparking.model.ParkingOperationModel;
import com.eparking.protocol.CarDetailsEntity;
import com.eparking.protocol.CarInforEntity;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.setting.switchButton.SwitchButton;

import cn.net.cyberway.R;

import static com.eparking.activity.CarsLicenseUploadActivity.CARLOGO;
import static com.eparking.activity.CarsLicenseUploadActivity.CARNUMBER;
import static com.eparking.activity.CarsLicenseUploadActivity.FROMSOURCE;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 16:29
 * @change
 * @chang time
 * @class describe  我的卡包---车辆信息
 */
public class EparkingCarDetailsActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public static final String CARINFOR = "carinfor";

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private TextView user_top_view_right;

    private ImageView iv_car_logo;
    private TextView tv_car_brand;
    private TextView tv_car_type;
    private LinearLayout car_model_layout;
    private TextView tv_car_model;
    private RelativeLayout eparking_type_layout;
    private TextView tv_eparking_type;
    private RelativeLayout month_parking_layout;
    private TextView tv_monthcard_place;
    private RelativeLayout nosecret_payment_layout;
    private TextView tv_payment_staus;
    private RelativeLayout auto_lock_layout;
    private SwitchButton sb_auto_lock;
    private RelativeLayout message_notify_layout;
    private SwitchButton sb_message_notify;
    private RelativeLayout stopcar_record_layout;
    private RelativeLayout charge_record_layout;
    private RelativeLayout authorization_record_layout;
    private Button btn_car_authorization;
    private ParkingOperationModel parkingOperationModel = null;
    private String carId;
    private String carLogo;
    private String plate;
    private String isAccred;
    private int contract_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eparking_carinfor);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_car_infor));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setText(getResources().getString(R.string.parking_car_unbind));
        user_top_view_right.setTextColor(getResources().getColor(R.color.textcolor_27a2f0));
        user_top_view_right.setOnClickListener(this);
        parkingOperationModel = new ParkingOperationModel(this);
        initView();
        initData();
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private void initData() {
        Intent intent = getIntent();
        CarInforEntity.ContentBean contentBean = (CarInforEntity.ContentBean) intent.getSerializableExtra(CARINFOR);
        carLogo = contentBean.getLogo();
        plate = contentBean.getPlate();
        tv_car_brand.setText(plate);
        carId = contentBean.getCar_id();
        isAccred = contentBean.getIs_accred();
        contract_state = contentBean.getContract_state();
        GlideImageLoader.loadImageDefaultDisplay(EparkingCarDetailsActivity.this, carLogo, iv_car_logo, R.drawable.eparking_img_carmodel, R.drawable.eparking_img_carmodel);
        switch (contract_state) {
            case -1:
                if ("Y".equalsIgnoreCase(isAccred)) {  //临停认证
                    message_notify_layout.setVisibility(View.VISIBLE);
                    btn_car_authorization.setText(getResources().getString(R.string.monthcard_apply));
                } else {
                    btn_car_authorization.setText(getResources().getString(R.string.car_authentication));
                }
                break;
            case 2:
                btn_car_authorization.setText(getResources().getString(R.string.car_authentication));
                break;
            case 0:
                btn_car_authorization.setEnabled(false);
                btn_car_authorization.setBackgroundResource(R.drawable.shape_openticket_default);
                btn_car_authorization.setText(getResources().getString(R.string.contract_apply_waiting));
                break;
            case 1:
                eparking_type_layout.setVisibility(View.VISIBLE);
                month_parking_layout.setVisibility(View.VISIBLE);
                auto_lock_layout.setVisibility(View.VISIBLE);
                message_notify_layout.setVisibility(View.VISIBLE);
                authorization_record_layout.setVisibility(View.VISIBLE);
                tv_eparking_type.setText(getResources().getString(R.string.monthcard));
                btn_car_authorization.setVisibility(View.VISIBLE);
                tv_monthcard_place.setText(contentBean.getStation_name());
                break;
        }
        if (8 == plate.trim().length()) {
            tv_car_type.setText(getResources().getString(R.string.energy_car_model));
        } else {
            tv_car_type.setText(getResources().getString(R.string.normal_car_model));
        }
        ParkingHomeModel parkingHomeModel = new ParkingHomeModel(EparkingCarDetailsActivity.this);
        parkingHomeModel.getHomeCarDetails(0, plate, this);
    }


    private void initView() {
        iv_car_logo = findViewById(R.id.iv_car_logo);
        tv_car_brand = findViewById(R.id.tv_car_brand);
        tv_car_type = findViewById(R.id.tv_car_type);
        car_model_layout = findViewById(R.id.car_model_layout);
        tv_car_model = findViewById(R.id.tv_car_model);
        eparking_type_layout = findViewById(R.id.eparking_type_layout);
        tv_eparking_type = findViewById(R.id.tv_eparking_type);
        month_parking_layout = findViewById(R.id.month_parking_layout);
        tv_monthcard_place = findViewById(R.id.tv_monthcard_place);
        nosecret_payment_layout = findViewById(R.id.nosecret_payment_layout);
        tv_payment_staus = findViewById(R.id.tv_payment_staus);
        auto_lock_layout = findViewById(R.id.auto_lock_layout);
        sb_auto_lock = findViewById(R.id.sb_auto_lock);
        message_notify_layout = findViewById(R.id.message_notify_layout);
        sb_message_notify = findViewById(R.id.sb_message_notify);
        stopcar_record_layout = findViewById(R.id.stopcar_record_layout);
        charge_record_layout = findViewById(R.id.charge_record_layout);
        authorization_record_layout = findViewById(R.id.authorization_record_layout);
        btn_car_authorization = findViewById(R.id.btn_car_authorization);
        car_model_layout.setOnClickListener(this);
        nosecret_payment_layout.setOnClickListener(this);
        stopcar_record_layout.setOnClickListener(this);
        charge_record_layout.setOnClickListener(this);
        authorization_record_layout.setOnClickListener(this);
        btn_car_authorization.setOnClickListener(this);
        initLockListener();
    }

    private void initLockListener() {
        sb_auto_lock.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                sb_auto_lock.setOpened(true);
                parkingOperationModel.carLockHandle(1, plate, "lock", EparkingCarDetailsActivity.this);
            }

            @Override
            public void toggleToOff(View view) {
                sb_auto_lock.setOpened(false);
                parkingOperationModel.carLockHandle(2, plate, "unlock", EparkingCarDetailsActivity.this);
            }
        });

        sb_message_notify.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                sb_message_notify.setOpened(true);
                parkingOperationModel.pushCarMsgOpenation(3, carId, "Y", EparkingCarDetailsActivity.this);
            }

            @Override
            public void toggleToOff(View view) {
                sb_message_notify.setOpened(false);
                parkingOperationModel.pushCarMsgOpenation(4, carId, "N", EparkingCarDetailsActivity.this);
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right: //解绑
                //DeleteAddressDialog
                showDialog();
                break;
            case R.id.car_model_layout:

                break;
            case R.id.nosecret_payment_layout:

                break;
            case R.id.stopcar_record_layout:
                Intent stopcarIntent = new Intent(EparkingCarDetailsActivity.this, EParkingHistoryRecordActivity.class);
                stopcarIntent.putExtra(EParkingHistoryRecordActivity.CHOICEINDEX, 2);
                stopcarIntent.putExtra(EParkingHistoryRecordActivity.CARID, carId);
                startActivity(stopcarIntent);
                break;
            case R.id.charge_record_layout:
                Intent chargeIntent = new Intent(EparkingCarDetailsActivity.this, EParkingHistoryRecordActivity.class);
                chargeIntent.putExtra(EParkingHistoryRecordActivity.CHOICEINDEX, 0);
                chargeIntent.putExtra(EParkingHistoryRecordActivity.CARID, carId);
                startActivity(chargeIntent);
                break;
            case R.id.authorization_record_layout:
                Intent authIntent = new Intent(EparkingCarDetailsActivity.this, CarsAuthorizeRecordActivity.class);
                authIntent.putExtra(CARNUMBER, plate);
                startActivity(authIntent);
                break;
            case R.id.btn_car_authorization:
                Intent intent = null;
                switch (contract_state) {
                    case -1:
                        if ("Y".equalsIgnoreCase(isAccred)) {  //临停认证
                            intent = new Intent(EparkingCarDetailsActivity.this, ChoiceParkingCommunityActivity.class);
                        } else {
                            intent = new Intent(EparkingCarDetailsActivity.this, CarsLicenseUploadActivity.class);
                            intent.putExtra(FROMSOURCE, 1);
                            intent.putExtra(CARLOGO, carLogo);
                        }
                        break;
                    case 2:
                        intent = new Intent(EparkingCarDetailsActivity.this, CarsLicenseUploadActivity.class);
                        intent.putExtra(FROMSOURCE, 1);
                        intent.putExtra(CARLOGO, carLogo);
                        break;
                    case 0:
                        btn_car_authorization.setEnabled(false);
                        btn_car_authorization.setBackgroundResource(R.drawable.shape_openticket_default);
                        btn_car_authorization.setText(getResources().getString(R.string.contract_apply_waiting));
                        break;
                    case 1:
                        intent = new Intent(EparkingCarDetailsActivity.this, CarsAuthorizationActivity.class);
                        break;
                }
                intent.putExtra(CARNUMBER, plate);
                startActivity(intent);
                break;
        }
    }

    private void showDialog() {
        final CustomDialog customDialog = new CustomDialog(EparkingCarDetailsActivity.this, R.style.custom_dialog_theme);
        customDialog.show();
        customDialog.setCancelable(false);
        customDialog.dialog_content.setText(getResources().getString(R.string.notice_unbind_start) + plate + getResources().getString(R.string.notice_stopcar_msg) + getResources().getString(R.string.notice_unbind_end));
        customDialog.dialog_button_ok.setText(getResources().getString(R.string.message_cancel));
        customDialog.dialog_button_cancel.setText(getResources().getString(R.string.message_unbind));
        customDialog.dialog_line.setVisibility(View.VISIBLE);
        customDialog.dialog_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                setResult(200);
                finish();

            }
        });
        customDialog.dialog_button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    CarDetailsEntity carDetailsEntity = GsonUtils.gsonToBean(result, CarDetailsEntity.class);
                    CarDetailsEntity.ContentBean contentBean = carDetailsEntity.getContent();
                    String autoLock = contentBean.getAutolock();
                    String pushStatus = contentBean.getPush();
                    if ("Y".equalsIgnoreCase(autoLock)) {
                        sb_auto_lock.setOpened(true);
                    } else {
                        sb_auto_lock.setOpened(false);
                    }
                    if ("Y".equalsIgnoreCase(pushStatus)) {
                        sb_message_notify.setOpened(true);
                    } else {
                        sb_message_notify.setOpened(false);
                    }
                } catch (Exception e) {

                }
                break;
            case 1:
                if (TextUtils.isEmpty(result)) {
                    sb_auto_lock.setOpened(false);
                }
                break;
            case 2:
                if (TextUtils.isEmpty(result)) {
                    sb_auto_lock.setOpened(true);
                }
                break;
            case 3:
                if (TextUtils.isEmpty(result)) {
                    sb_message_notify.setOpened(false);
                }
                break;
            case 4:
                if (TextUtils.isEmpty(result)) {
                    sb_message_notify.setOpened(true);
                }
                break;

        }
    }
}
