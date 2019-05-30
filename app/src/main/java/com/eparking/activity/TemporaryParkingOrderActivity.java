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

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.cashier.activity.NewOrderPayActivity;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.helper.PermissionUtils;
import com.eparking.model.ParkingHomeModel;
import com.eparking.model.ParkingOrderModel;
import com.eparking.protocol.EparkingPayResultEntity;
import com.eparking.protocol.TempMoneyEntity;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;

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
 * @class describe  临停缴费的订单
 */
public class TemporaryParkingOrderActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private String plate;
    private ParkingHomeModel parkingHomeModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eparking_order);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_parking_order));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        initView();
        plate = getIntent().getStringExtra(CARNUMBER);
        parkingHomeModel = new ParkingHomeModel(TemporaryParkingOrderActivity.this);
        parkingHomeModel.getTempCarMsg(0, plate, this);
        parkingHomeModel.addHistoryCarNumber(2, plate, this);
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private ImageView iv_car_model;
    private TextView tv_car_number;
    private TextView tv_parking_space;
    private TextView tv_parking_time;
    private TextView tv_chargeable_time;
    private LinearLayout choice_coupon_layout;
    private TextView tv_coupon_amount;
    private TextView tv_order_number;
    private TextView tv_remark;
    private TextView tv_pay_amount;
    private Button btn_payoff_the_count;
    private LinearLayout custom_service_layout;

    private void initView() {
        iv_car_model = findViewById(R.id.iv_car_model);
        tv_car_number = findViewById(R.id.tv_car_number);
        tv_parking_space = findViewById(R.id.tv_parking_space);
        tv_parking_time = findViewById(R.id.tv_parking_time);
        tv_chargeable_time = findViewById(R.id.tv_chargeable_time);
        choice_coupon_layout = findViewById(R.id.choice_coupon_layout);
        tv_coupon_amount = findViewById(R.id.tv_coupon_amount);
        tv_order_number = findViewById(R.id.tv_order_number);
        tv_remark = findViewById(R.id.tv_remark);
        tv_pay_amount = findViewById(R.id.tv_pay_amount);
        tv_pay_amount = findViewById(R.id.tv_pay_amount);
        btn_payoff_the_count = findViewById(R.id.btn_payoff_the_count);
        custom_service_layout = findViewById(R.id.custom_service_layout);
        choice_coupon_layout.setOnClickListener(this);
        btn_payoff_the_count.setOnClickListener(this);
        custom_service_layout.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_payoff_the_count:
                ParkingOrderModel parkingOrderModel = new ParkingOrderModel(TemporaryParkingOrderActivity.this);
                parkingOrderModel.submitTemporaryPayOrder(1, plate, "0", TemporaryParkingOrderActivity.this);
                break;
            case R.id.custom_service_layout:
                PermissionUtils.showPhonePermission(TemporaryParkingOrderActivity.this);
                break;
        }
    }

    private String money;
    private String beginTime;
    private String endTime;
    private String station_id;
    private String car_id;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (TextUtils.isEmpty(result)) {
                    finish();
                } else {
                    BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                    try {
                        TempMoneyEntity tempMoneyEntity = GsonUtils.gsonToBean(result, TempMoneyEntity.class);
                        TempMoneyEntity.ContentBean contentBean = tempMoneyEntity.getContent();
                        GlideImageLoader.loadImageDefaultDisplay(TemporaryParkingOrderActivity.this, contentBean.getLogo(), iv_car_model, R.drawable.eparking_img_carmodel, R.drawable.eparking_img_carmodel);
                        tv_car_number.setText(plate);
                        car_id = contentBean.getCar_id();
                        station_id = contentBean.getStation_id();
                        beginTime = contentBean.getBegin();
                        endTime = contentBean.getEnd();
                        tv_parking_space.setText(contentBean.getStation_name());
                        tv_parking_time.setText(TimeUtil.dateDiffTime(contentBean.getStay_time()));
                        tv_chargeable_time.setText(beginTime + "-\n" + endTime);
                        money = contentBean.getMoney();
                        tv_pay_amount.setText(money);
                        tv_remark.setText(contentBean.getComment());
                        tv_coupon_amount.setText(contentBean.getDiscounts());
                    } catch (Exception e) {
                        ToastUtil.toastShow(TemporaryParkingOrderActivity.this, baseContentEntity.getMessage());
                        finish();
                    }
                }
                break;
            case 1:
                BaseContentEntity basePayEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                try {
                    EparkingPayResultEntity payResultEntity = GsonUtils.gsonToBean(result, EparkingPayResultEntity.class);
                    EparkingPayResultEntity.ContentBean contentBean = payResultEntity.getContent();
                    Intent intent = new Intent(TemporaryParkingOrderActivity.this, NewOrderPayActivity.class);
                    intent.putExtra(NewOrderPayActivity.ORDER_SN, contentBean.getPrepay_id());
                    startActivity(intent);
                } catch (Exception e) {
                    ToastUtil.toastShow(TemporaryParkingOrderActivity.this, basePayEntity.getMessage());
                }
                break;
        }
    }
}
