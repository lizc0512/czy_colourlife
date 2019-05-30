package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.protocol.MonthCardInforEntity;
import com.nohttp.utils.GlideImageLoader;

import cn.net.cyberway.R;

import static com.eparking.activity.CarsLicenseUploadActivity.CARNUMBER;
import static com.eparking.activity.InputOpenCodeActivity.STATIONID;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 16:29
 * @change
 * @chang time
 * @class describe  我的卡包---月卡信息
 */
public class EparkingMonthCardDetailsActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public static final String MONTHCARDINFOR = "monthCardInfor";
    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private String plate;
    private String stationId;
    private String carId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthcard_infor);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_monthcard_infor));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        initView();
        MonthCardInforEntity.ContentBean contentBean = (MonthCardInforEntity.ContentBean) getIntent().getSerializableExtra(MONTHCARDINFOR);
        GlideImageLoader.loadImageDefaultDisplay(EparkingMonthCardDetailsActivity.this, contentBean.getLogo(), iv_car_logo, R.drawable.eparking_img_carmodel, R.drawable.eparking_img_carmodel);
        tv_parking_address.setText(contentBean.getStation_name());
        plate = contentBean.getPlate();
        carId = contentBean.getCar_id();
        stationId = contentBean.getStation_id();
        tv_car_number.setText(plate);
        tv_outof_date.setText(contentBean.getEnd_time());
        tv_month_amount.setText(contentBean.getAmount());
        tv_remark.setText(contentBean.getRule_name());
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private ImageView iv_car_logo;
    private TextView tv_parking_address;
    private TextView tv_car_number;
    private TextView tv_outof_date;
    private TextView tv_month_amount;
    private TextView tv_remark;
    private RelativeLayout payment_record_layout;
    private Button btn_monthcard_renewal;

    private void initView() {
        iv_car_logo = findViewById(R.id.iv_car_logo);
        tv_parking_address = findViewById(R.id.tv_parking_address);
        tv_car_number = findViewById(R.id.tv_car_number);
        tv_outof_date = findViewById(R.id.tv_outof_date);
        tv_month_amount = findViewById(R.id.tv_month_amount);
        tv_remark = findViewById(R.id.tv_remark);
        payment_record_layout = findViewById(R.id.payment_record_layout);
        btn_monthcard_renewal = findViewById(R.id.btn_monthcard_renewal);
        payment_record_layout.setOnClickListener(this);
        btn_monthcard_renewal.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.payment_record_layout:
                Intent chargeIntent = new Intent(EparkingMonthCardDetailsActivity.this, EParkingHistoryRecordActivity.class);
                chargeIntent.putExtra(EParkingHistoryRecordActivity.CHOICEINDEX, 0);
                chargeIntent.putExtra(EParkingHistoryRecordActivity.CARID, carId);
                startActivity(chargeIntent);
                break;
            case R.id.btn_monthcard_renewal:
                Intent intent = new Intent(EparkingMonthCardDetailsActivity.this, MonthCardRenewalActivity.class);
                intent.putExtra(CARNUMBER, plate);
                intent.putExtra(STATIONID, stationId);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {

    }
}
