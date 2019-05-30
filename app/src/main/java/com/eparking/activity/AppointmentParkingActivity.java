package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.OptionsPickerInterface;
import com.eparking.helper.OptionsPickerViewUtils;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingOperationModel;
import com.eparking.protocol.CarStationListEntity;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.net.cyberway.R;

import static com.eparking.helper.ConstantKey.EPARKINGCARSTATIONLIST;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  预约停车
 */
public class AppointmentParkingActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, OptionsPickerInterface {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private TextView user_top_view_right;
    private LinearLayout car_model_layout;
    private TextView tv_car_model;
    private ImageView iv_car_arrow;
    private LinearLayout car_parking_layout;
    private TextView tv_parking_address;
    private ImageView iv_address_arrow;
    private LinearLayout appointment_enter_layout;
    private TextView tv_appointment_entertime;
    private LinearLayout appointment_leave_layout;
    private TextView tv_appointment_leave;
    private Button btn_once_appointment;

    private ParkingOperationModel parkingOperationModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment_parking);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_appoint_eparking));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setText(getResources().getString(R.string.title_appoint_record));
        user_top_view_right.setTextColor(getResources().getColor(R.color.textcolor_27a2f0));
        car_model_layout = findViewById(R.id.car_model_layout);
        tv_car_model = (TextView) findViewById(R.id.tv_car_model);
        iv_car_arrow = findViewById(R.id.iv_car_arrow);
        car_parking_layout = findViewById(R.id.car_parking_layout);
        tv_parking_address = (TextView) findViewById(R.id.tv_parking_address);
        iv_address_arrow = findViewById(R.id.iv_address_arrow);
        appointment_enter_layout = findViewById(R.id.appointment_enter_layout);
        tv_appointment_entertime = findViewById(R.id.tv_appointment_entertime);
        appointment_leave_layout = findViewById(R.id.appointment_leave_layout);
        tv_appointment_leave = findViewById(R.id.tv_appointment_leave);
        btn_once_appointment = findViewById(R.id.btn_once_appointment);
        imageView_back.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        car_model_layout.setOnClickListener(this);
        car_parking_layout.setOnClickListener(this);
        appointment_enter_layout.setOnClickListener(this);
        appointment_leave_layout.setOnClickListener(this);
        btn_once_appointment.setOnClickListener(this);
        String carStationCache = shared.getString(EPARKINGCARSTATIONLIST, "");
        parkingOperationModel = new ParkingOperationModel(this);
        if (!TextUtils.isEmpty(carStationCache)) {
            showCarStationList(carStationCache);
            parkingOperationModel.getCarAndStationList(0, false, this);
        } else {
            parkingOperationModel.getCarAndStationList(0, true, this);
        }
        ParkingActivityUtils.getInstance().addActivity(this);
        setBtnClick();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                Intent intent = new Intent(AppointmentParkingActivity.this, AppointmentRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.car_model_layout:
                OptionsPickerViewUtils.showPickerView(AppointmentParkingActivity.this, R.string.car_brand, 0, carStationList, this);
                break;
            case R.id.car_parking_layout:
                OptionsPickerViewUtils.showPickerView(AppointmentParkingActivity.this, R.string.eparking_name, 1, stationListBeanList, this);
                break;
            case R.id.appointment_enter_layout:
                OptionsPickerViewUtils.showTimeDialog(AppointmentParkingActivity.this, R.string.enter_time, 2, this);
                break;
            case R.id.appointment_leave_layout:
                OptionsPickerViewUtils.showTimeDialog(AppointmentParkingActivity.this, R.string.parking_leave_date, 3, this);
                break;
            case R.id.btn_once_appointment:
                long currentMillions = System.currentTimeMillis() / 1000;
                if (currentMillions >= startSecond) {
                    ToastUtil.toastShow(AppointmentParkingActivity.this, "预约的入场时间不能小于当前时间");
                    return;
                }
                if (startSecond >= endSecond) {
                    ToastUtil.toastShow(AppointmentParkingActivity.this, "预约的离场时间应大于入场时间");
                    return;
                }
                parkingOperationModel.appointStopOpenation(1, plate, stationId, startSecond, endSecond, AppointmentParkingActivity.this);
                break;
        }
    }

    private String plate;
    private String stationId;
    private List<CarStationListEntity.ContentBean.StationListBean> stationListBeanList;
    private List<CarStationListEntity.ContentBean> carStationList = new ArrayList<>();
    private Map<String, List<CarStationListEntity.ContentBean.StationListBean>> mapStationList = new HashMap<>();

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                showCarStationList(result);
                break;
            case 1:
                ToastUtil.toastShow(AppointmentParkingActivity.this, getResources().getString(R.string.appoint_parking_success));
                ParkingActivityUtils.getInstance().exit();
                break;
        }
    }

    private void showCarStationList(String result) {
        try {
            carStationList.clear();
            CarStationListEntity carStationListEntity = GsonUtils.gsonToBean(result, CarStationListEntity.class);
            carStationList.addAll(carStationListEntity.getContent());
            for (CarStationListEntity.ContentBean contentBean : carStationList) {
                mapStationList.put(contentBean.getCar_id(), contentBean.getStation_list());
            }
            if (carStationList.size() > 0) {
                CarStationListEntity.ContentBean contentBean = carStationList.get(0);
                tv_car_model.setText(contentBean.getPlate());
                plate = contentBean.getPlate();
                stationListBeanList = mapStationList.get(contentBean.getCar_id());
                if (stationListBeanList != null && stationListBeanList.size() >= 1) {
                    car_parking_layout.setEnabled(false);
                    CarStationListEntity.ContentBean.StationListBean stationListBean = stationListBeanList.get(0);
                    tv_parking_address.setText(stationListBean.getStation_name());
                    stationId = stationListBean.getStation_id();
                }
            }
        } catch (Exception e) {

        }
        if (carStationList.size() <= 1) {
            iv_car_arrow.setVisibility(View.INVISIBLE);
            car_model_layout.setEnabled(false);
        } else {
            car_model_layout.setEnabled(true);
            iv_car_arrow.setVisibility(View.VISIBLE);
        }
        if (stationListBeanList == null || stationListBeanList.size() <= 1) {
            car_parking_layout.setEnabled(false);
            iv_address_arrow.setVisibility(View.INVISIBLE);
        } else {
            car_parking_layout.setEnabled(true);
            iv_address_arrow.setVisibility(View.VISIBLE);
        }
    }

    private long endSecond = 0;
    private long startSecond = 0;

    @Override
    public void choicePickResult(int type, String choiceText, String choiceId) {
        switch (type) {
            case 0:
                plate = choiceText;
                stationListBeanList = mapStationList.get(choiceId);
                tv_car_model.setText(plate);
                break;
            case 1:
                stationId = choiceId;
                tv_parking_address.setText(choiceText);
                break;
            case 2:
                tv_appointment_entertime.setText(choiceText);
                startSecond = Long.valueOf(choiceId);
                break;
            case 3:
                tv_appointment_leave.setText(choiceText);
                endSecond = Long.valueOf(choiceId);
                break;
        }
        setBtnClick();
    }

    private void setBtnClick() {
        if (startSecond == 0 || endSecond == 0 || TextUtils.isEmpty(plate) || TextUtils.isEmpty(stationId)) {
            btn_once_appointment.setBackgroundResource(R.drawable.shape_openticket_default);
            btn_once_appointment.setEnabled(false);
        } else {
            btn_once_appointment.setBackgroundResource(R.drawable.shape_authorizeation_btn);
            btn_once_appointment.setEnabled(true);
        }
    }
}
