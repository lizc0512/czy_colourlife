package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.InputViewInterface;
import com.eparking.helper.MapNaviUtils;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingOperationModel;
import com.eparking.view.keyboard.view.InputView;

import cn.net.cyberway.R;

import static com.eparking.activity.CarsLicenseUploadActivity.CARLOGO;
import static com.eparking.activity.CarsLicenseUploadActivity.CARNUMBER;
import static com.eparking.activity.CarsLicenseUploadActivity.FROMSOURCE;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  车辆绑定的
 */
public class CarsBindActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, InputViewInterface {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private InputView input_view;
    private Button btn_once_submit;
    private ParkingOperationModel parkingOperationModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bind_car);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_bind_car));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        input_view = findViewById(R.id.input_view);
        btn_once_submit = findViewById(R.id.btn_once_submit);
        btn_once_submit.setOnClickListener(this);
        final Button tv_change_cartype = findViewById(R.id.tv_change_cartype);
        MapNaviUtils.initInputView(CarsBindActivity.this, input_view, tv_change_cartype, this);
        MapNaviUtils.setInputViewData(CarsBindActivity.this, input_view, true);
        parkingOperationModel = new ParkingOperationModel(this);
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private void setBtnStatus(boolean isNewEnergyType) {
        int length = input_view.getNumber().length();
        boolean isClick = isNewEnergyType ? (length == 8 ? true : false) : (length == 7 ? true : false);
        if (isClick) {
            btn_once_submit.setBackgroundResource(R.drawable.shape_authorizeation_btn);
        } else {
            btn_once_submit.setBackgroundResource(R.drawable.shape_openticket_default);
        }
        btn_once_submit.setEnabled(isClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_once_submit:
                //同步到我的车辆列表和车牌号选择
                parkingOperationModel.carBindOpenation(0, input_view.getNumber(), this);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        Intent intent = new Intent(CarsBindActivity.this, CarsLicenseUploadActivity.class);
        intent.putExtra(FROMSOURCE, 1);
        intent.putExtra(CARNUMBER, input_view.getNumber());
        intent.putExtra(CARLOGO, "");
        startActivity(intent);
    }

    @Override
    public void inputViewCallBack(boolean isNewEnergyType) {
        setBtnStatus(isNewEnergyType);
    }
}
