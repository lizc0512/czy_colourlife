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
import cn.net.cyberway.home.view.LeadGestureDialog;

import static com.eparking.helper.ConstantKey.EPARKINGCARSTATIONLIST;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/11 16:27
 * @change
 * @chang time
 * @class describe 紧急开闸
 */
public class EmergencySluiceActivity extends BaseActivity implements View.OnClickListener, OptionsPickerInterface, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private LinearLayout car_model_layout;
    private TextView tv_car_model;
    private ImageView iv_car_arrow;
    private LinearLayout car_parking_layout;
    private TextView tv_parking_address;
    private ImageView iv_address_arrow;
    private Button btn_once_apply;
    private ParkingOperationModel parkingOperationModel = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_sluice);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_emergency_sluice));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        car_model_layout = findViewById(R.id.car_model_layout);
        tv_car_model = (TextView) findViewById(R.id.tv_car_model);
        iv_car_arrow = findViewById(R.id.iv_car_arrow);
        car_parking_layout = findViewById(R.id.car_parking_layout);
        tv_parking_address = (TextView) findViewById(R.id.tv_parking_address);
        iv_address_arrow = findViewById(R.id.iv_address_arrow);
        btn_once_apply = (Button) findViewById(R.id.btn_once_apply);
        imageView_back.setOnClickListener(this);
        car_model_layout.setOnClickListener(this);
        car_parking_layout.setOnClickListener(this);
        btn_once_apply.setOnClickListener(this);
        setBtnClick();
        parkingOperationModel = new ParkingOperationModel(this);
        String carStationCache = shared.getString(EPARKINGCARSTATIONLIST, "");
        if (!TextUtils.isEmpty(carStationCache)) {
            showCarStationList(carStationCache);
            parkingOperationModel.getCarAndStationList(0, false, this);
        } else {
            parkingOperationModel.getCarAndStationList(0, true, this);
        }
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.car_model_layout:
                OptionsPickerViewUtils.showPickerView(EmergencySluiceActivity.this, R.string.car_brand, 0, carStationList, this);
                break;
            case R.id.car_parking_layout:
                OptionsPickerViewUtils.showPickerView(EmergencySluiceActivity.this, R.string.eparking_name, 1, stationListBeanList, this);
                break;
            case R.id.btn_once_apply:
                //LeadGestureDialog
                Intent intent = new Intent(EmergencySluiceActivity.this, InputOpenCodeActivity.class);
                intent.putExtra(InputOpenCodeActivity.PLATE, carNumber);
                intent.putExtra(InputOpenCodeActivity.STATIONID, stationId);
                startActivity(intent);
                break;
        }
    }

    private void showNotice() {
        final LeadGestureDialog dialog = new LeadGestureDialog(EmergencySluiceActivity.this, R.style.custom_dialog_theme);
        dialog.show();
        dialog.setTitle(getResources().getString(R.string.idcard_authorize));
        dialog.dialog_content.setText(getResources().getString(R.string.idcard_authorize_notice));
        dialog.dialog_content.setTextColor(getResources().getColor(R.color.color_333b46));
        dialog.btn_cancel.setText(getResources().getString(R.string.idcard_no_authorize));
        dialog.btn_cancel.setTextColor(getResources().getColor(R.color.color_999faa));
        dialog.btn_open.setText(getResources().getString(R.string.idcard_go_authorize));
        dialog.btn_open.setTextColor(getResources().getColor(R.color.color_007fff));
        dialog.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });
        dialog.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
//                Intent intent = new Intent(EmergencySluiceActivity.this, EmergencyRealNameActivity.class);
//                startActivity(intent);
            }
        });
    }


    private String carNumber;
    private String parkingAddress;
    private String carId;
    private String stationId;
    private List<CarStationListEntity.ContentBean.StationListBean> stationListBeanList;
    private List<CarStationListEntity.ContentBean> carStationList = new ArrayList<>();
    private Map<String, List<CarStationListEntity.ContentBean.StationListBean>> mapStationList = new HashMap<>();

    private void setBtnClick() {
        carNumber = tv_car_model.getText().toString().trim();
        parkingAddress = tv_parking_address.getText().toString().trim();
        if (TextUtils.isEmpty(carNumber) || TextUtils.isEmpty(parkingAddress)) {
            btn_once_apply.setEnabled(false);
            btn_once_apply.setBackgroundResource(R.drawable.shape_openticket_default);
        } else {
            btn_once_apply.setBackgroundResource(R.drawable.shape_authorizeation_btn);
            btn_once_apply.setEnabled(true);
        }
    }

    @Override
    public void choicePickResult(int type, String choiceText, String choiceId) {
        if (type == 0) {
            carId = choiceId;
            stationListBeanList = mapStationList.get(carId);
            tv_car_model.setText(choiceText);
        } else {
            stationId = choiceId;
            tv_parking_address.setText(choiceText);
        }
        setBtnClick();
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                showCarStationList(result);
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
                carId = contentBean.getCar_id();
                stationListBeanList = mapStationList.get(carId);
                if (null != stationListBeanList && stationListBeanList.size() >= 1) {
                    CarStationListEntity.ContentBean.StationListBean stationListBean = stationListBeanList.get(0);
                    tv_parking_address.setText(stationListBean.getStation_name().trim());
                    stationId = stationListBean.getStation_id();
                }
            }
        } catch (Exception e) {

        }
        if (carStationList.size() <= 1) {
            iv_car_arrow.setVisibility(View.INVISIBLE);
            car_model_layout.setEnabled(false);
        } else {
            iv_car_arrow.setVisibility(View.VISIBLE);
            car_model_layout.setEnabled(true);
        }
        if (stationListBeanList == null || stationListBeanList.size() <= 1) {
            car_parking_layout.setEnabled(false);
            iv_address_arrow.setVisibility(View.INVISIBLE);
        } else {
            car_parking_layout.setEnabled(true);
            iv_address_arrow.setVisibility(View.VISIBLE);
        }
        setBtnClick();
    }
}
