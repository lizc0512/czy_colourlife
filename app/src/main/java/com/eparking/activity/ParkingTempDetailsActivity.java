package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.helper.PermissionUtils;
import com.eparking.model.ParkingHomeModel;
import com.eparking.protocol.ParkingAddressEntity;
import com.eparking.protocol.StopStationRecordEntity;
import com.nohttp.utils.GsonUtils;

import java.util.List;

import cn.net.cyberway.R;

import static com.eparking.activity.PaymentTempDetailsActivity.PAYMENTINFOR;


/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  临停或月卡的停车详情
 */
public class ParkingTempDetailsActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temppark_stopdetails);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_parking_details));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        initView();
        initData();
        imageView_back.setOnClickListener(this);
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private void initData() {
        Intent intent = getIntent();
        StopStationRecordEntity.ContentBean contentBean = (StopStationRecordEntity.ContentBean) intent.getSerializableExtra(PAYMENTINFOR);
        String type = contentBean.getMonth();
        if (!getResources().getString(R.string.monthcard).equals(type)) {  //临停显示
//            discount_amount_layout.setVisibility(View.VISIBLE);
//            payment_time_layout.setVisibility(View.VISIBLE);
//            order_number_layout.setVisibility(View.VISIBLE);
        }
        tv_parking_name.setText(contentBean.getStation_name());
        tv_car_number.setText(contentBean.getPlate());
        String stationId = contentBean.getStation_id();
        tv_enter_date.setText(contentBean.getTime_in());
        tv_leave_date.setText(contentBean.getTime_out());
        tv_parking_time.setText(TimeUtil.dateDiff(contentBean.getTime_in(), contentBean.getTime_out()));
        ParkingHomeModel parkingHomeModel = new ParkingHomeModel(ParkingTempDetailsActivity.this);
        parkingHomeModel.getParkingAddressInfor(0, stationId, this);
    }

    private TextView tv_parking_name;
    private TextView tv_car_number;
    private TextView tv_parking_address;
    private TextView tv_enter_date;
    private TextView tv_leave_date;

    private TextView tv_parking_time;
    private RelativeLayout discount_amount_layout;
    private TextView tv_discount_amount;

    private TextView tv_payment_amount;
    private RelativeLayout payment_time_layout;
    private TextView tv_payment_time;
    private RelativeLayout order_number_layout;
    private TextView tv_order_number;
    private LinearLayout bottom_layout;

    private void initView() {
        tv_parking_name = findViewById(R.id.tv_parking_name);
        tv_car_number = findViewById(R.id.tv_car_number);
        tv_parking_address = findViewById(R.id.tv_parking_address);
        tv_enter_date = findViewById(R.id.tv_enter_date);
        tv_leave_date = findViewById(R.id.tv_leave_date);
        tv_parking_time = findViewById(R.id.tv_parking_time);
        discount_amount_layout = findViewById(R.id.discount_amount_layout);
        tv_discount_amount = findViewById(R.id.tv_discount_amount);
        tv_payment_amount = findViewById(R.id.tv_payment_amount);
        payment_time_layout = findViewById(R.id.payment_time_layout);
        tv_payment_time = findViewById(R.id.tv_payment_time);
        order_number_layout = findViewById(R.id.order_number_layout);
        tv_order_number = findViewById(R.id.tv_order_number);
        bottom_layout = findViewById(R.id.bottom_layout);
        bottom_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.bottom_layout:
                PermissionUtils.showPhonePermission(ParkingTempDetailsActivity.this);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    ParkingAddressEntity parkingAddressEntity = GsonUtils.gsonToBean(result, ParkingAddressEntity.class);
                    List<ParkingAddressEntity.ContentBean> contentBeanList = parkingAddressEntity.getContent();
                    if (contentBeanList.size() > 0) {
                        tv_parking_address.setText(contentBeanList.get(0).getAddress());
                    }
                } catch (Exception e) {

                }
                break;
        }
    }
}
