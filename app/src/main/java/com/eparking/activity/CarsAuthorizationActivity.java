package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.ConstantKey;
import com.eparking.helper.CustomDialog;
import com.eparking.helper.OptionsPickerInterface;
import com.eparking.helper.OptionsPickerViewUtils;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingOperationModel;
import com.eparking.protocol.CarStationListEntity;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

import static com.eparking.activity.CarsLicenseUploadActivity.CARNUMBER;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  车辆授权
 */
public class CarsAuthorizationActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, OptionsPickerInterface {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private TextView user_top_view_right;
    private LinearLayout car_model_layout;
    private TextView tv_car_model;
    private ImageView iv_more_cars;
    private EditText ed_authorize_phone;
    private Button btn_authorization;
    private ParkingOperationModel parkingOperationModel = null;
    private List<CarStationListEntity.ContentBean> carStationList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eparking_carsauthorization);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_car_authorize));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        car_model_layout = findViewById(R.id.car_model_layout);
        tv_car_model = findViewById(R.id.tv_car_model);
        iv_more_cars = findViewById(R.id.iv_more_cars);
        ed_authorize_phone = findViewById(R.id.ed_authorize_phone);
        btn_authorization = findViewById(R.id.btn_authorization);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setText(getResources().getString(R.string.title_authorize_record));
        user_top_view_right.setTextColor(getResources().getColor(R.color.textcolor_27a2f0));
        imageView_back.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        car_model_layout.setOnClickListener(this);
        btn_authorization.setOnClickListener(this);
        initWatcher();
        String carStationCache = shared.getString(ConstantKey.EPARKINGCARSTATIONLIST, "");
        try {
            carStationList.clear();
            CarStationListEntity carStationListEntity = GsonUtils.gsonToBean(carStationCache, CarStationListEntity.class);
            carStationList.addAll(carStationListEntity.getContent());
            int size = carStationList.size();
            if (size > 1) {
                iv_more_cars.setVisibility(View.VISIBLE);
                car_model_layout.setEnabled(true);
            } else {
                iv_more_cars.setVisibility(View.GONE);
                car_model_layout.setEnabled(false);
            }
        } catch (Exception e) {
            iv_more_cars.setVisibility(View.GONE);
            car_model_layout.setEnabled(false);
        }
        carNumber = getIntent().getStringExtra(CARNUMBER);
        tv_car_model.setText(carNumber);
        parkingOperationModel = new ParkingOperationModel(this);
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private void initWatcher() {
        ed_authorize_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                userPhone = s.toString().trim();
                setButtonClick();
            }
        });
        setButtonClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                Intent intent = new Intent(CarsAuthorizationActivity.this, CarsAuthorizeRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.car_model_layout:
                OptionsPickerViewUtils.showPickerView(CarsAuthorizationActivity.this, R.string.car_brand, 0, carStationList, this);
                break;
            case R.id.btn_authorization:
                showDialog();
                break;
        }
    }

    private void showDialog() {
        final CustomDialog customDialog = new CustomDialog(CarsAuthorizationActivity.this, R.style.custom_dialog_theme);
        customDialog.show();
        customDialog.setCancelable(false);
        customDialog.dialog_content.setText(getResources().getString(R.string.car_authorize_notice));
        customDialog.dialog_button_cancel.setText(getResources().getString(R.string.message_define));
        customDialog.dialog_button_cancel.setTextColor(getResources().getColor(R.color.color_007fff));
        customDialog.dialog_button_ok.setText(getResources().getString(R.string.message_cancel));
        customDialog.dialog_line.setVisibility(View.VISIBLE);
        customDialog.dialog_button_ok.setTextColor(getResources().getColor(R.color.color_999faa));
        customDialog.dialog_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { //确认按钮
                customDialog.dismiss();
                parkingOperationModel.carAuthorizeOpenation(0, carNumber, userPhone, CarsAuthorizationActivity.this);
            }
        });
        customDialog.dialog_button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }

    private String userPhone;
    private String carNumber;

    private void setButtonClick() {
        if (TextUtils.isEmpty(userPhone) || TextUtils.isEmpty(carNumber) || userPhone.length() != 11) {
            btn_authorization.setEnabled(false);
            btn_authorization.setBackgroundResource(R.drawable.shape_openticket_default);
        } else {
            btn_authorization.setEnabled(true);
            btn_authorization.setBackgroundResource(R.drawable.shape_authorizeation_btn);
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                ToastUtil.toastShow(CarsAuthorizationActivity.this, getResources().getString(R.string.car_authorize_success));
                ParkingActivityUtils.getInstance().exit();
                break;
        }
    }

    @Override
    public void choicePickResult(int type, String choiceText, String choiceId) {
        carNumber = choiceText;
        tv_car_model.setText(carNumber);
        setButtonClick();
    }
}
