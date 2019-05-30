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
import com.cashier.activity.NewOrderPayActivity;
import com.eparking.helper.OptionsPickerInterface;
import com.eparking.helper.OptionsPickerViewUtils;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingHomeModel;
import com.eparking.model.ParkingOrderModel;
import com.eparking.protocol.CardpackStationEntity;
import com.eparking.protocol.ContractCostEntity;
import com.eparking.protocol.ContractMsgEntity;
import com.eparking.protocol.EparkingPayResultEntity;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

import static com.eparking.activity.CarsLicenseUploadActivity.CARNUMBER;
import static com.eparking.activity.InputOpenCodeActivity.STATIONID;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 15:20
 * @change
 * @chang time
 * @class describe  月卡续费
 */
public class MonthCardRenewalActivity extends BaseActivity implements View.OnClickListener, OptionsPickerInterface, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private List<String> monthList;
    private List<CardpackStationEntity.ContentBean.ListsBean> listsBeanList = new ArrayList<>();
    private ParkingHomeModel parkingHomeModel;
    private String plate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monthcard_pay);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_monthcard_renewal));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        initView();
        monthList = new ArrayList<>();
        Intent intent = getIntent();
        plate = intent.getStringExtra(CARNUMBER);
        station_id = intent.getStringExtra(STATIONID);
        parkingHomeModel = new ParkingHomeModel(MonthCardRenewalActivity.this);
        boolean isNotice = false;
        if (TextUtils.isEmpty(station_id)) {
            isNotice = true;
        }
        parkingHomeModel.getCardpackInfor(1, plate, "", isNotice, this);
        parkingHomeModel.addHistoryCarNumber(4, plate, this);
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private TextView tv_payment_amount;
    private TextView tv_car_brand;
    private LinearLayout choice_station_layout;
    private TextView tv_eparking_name;
    private TextView tv_fee_rule;
    private LinearLayout continue_paymonth_layout;
    private ImageView iv_station_arrow;
    private TextView tv_continue_paymonth;
    private TextView tv_outof_date;
    private TextView tv_continue_datetime;
    private Button btn_payment;

    private void initView() {
        tv_payment_amount = findViewById(R.id.tv_payment_amount);
        tv_car_brand = findViewById(R.id.tv_car_brand);
        choice_station_layout = findViewById(R.id.choice_station_layout);
        tv_eparking_name = findViewById(R.id.tv_eparking_name);
        tv_fee_rule = findViewById(R.id.tv_fee_rule);
        continue_paymonth_layout = findViewById(R.id.continue_paymonth_layout);
        iv_station_arrow = findViewById(R.id.iv_station_arrow);
        tv_continue_paymonth = findViewById(R.id.tv_continue_paymonth);
        tv_outof_date = findViewById(R.id.tv_outof_date);
        tv_continue_datetime = findViewById(R.id.tv_continue_datetime);
        btn_payment = findViewById(R.id.btn_payment);
        choice_station_layout.setOnClickListener(this);
        continue_paymonth_layout.setOnClickListener(this);
        btn_payment.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.choice_station_layout:
                OptionsPickerViewUtils.showPickerView(MonthCardRenewalActivity.this, R.string.eparking_name, 0, listsBeanList, this);
                break;
            case R.id.continue_paymonth_layout:
                OptionsPickerViewUtils.showPickerView(MonthCardRenewalActivity.this, R.string.continue_pay_month, 1, monthList, this);
                break;
            case R.id.btn_payment: //创建订单
                ParkingOrderModel parkingOrderModel = new ParkingOrderModel(MonthCardRenewalActivity.this);
                parkingOrderModel.submitMonthPayOrder(3, monthNumber, car_id, station_id, plate, rule_id, begin_time, monthContinueTime, payTotal, MonthCardRenewalActivity.this);
                break;
        }
    }

    @Override
    public void choicePickResult(int type, String choiceText, String choiceId) {
        switch (type) {
            case 0:
                tv_eparking_name.setText(choiceText);
                tv_fee_rule.setText("");
                tv_outof_date.setText("");
                tv_continue_paymonth.setText("");
                tv_payment_amount.setText("");
                tv_continue_datetime.setText("");
                parkingHomeModel.getContractMsg(0, plate, choiceId, this);
                break;
            case 1:
                tv_continue_paymonth.setText(choiceText + "个月");
                monthNumber = choiceText;
                parkingHomeModel.getContractCost(2, end_time, choiceText, price, MonthCardRenewalActivity.this);
                break;
        }
    }

    private String price;
    private String payTotal;
    private String monthNumber = "1";
    private String car_id;
    private String station_id;
    private String begin_time;
    private String end_time;
    private String monthContinueTime = "";
    private String rule_id;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    ContractMsgEntity contractMsgEntity = GsonUtils.gsonToBean(result, ContractMsgEntity.class);
                    ContractMsgEntity.ContentBean contentBean = contractMsgEntity.getContent();
                    tv_car_brand.setText(plate);
                    car_id = contentBean.getCar_id();
                    station_id = contentBean.getStation_id();
                    rule_id = contentBean.getRule_id();
                    tv_eparking_name.setText(contentBean.getStation_name());
                    end_time = contentBean.getEnd_time();
                    begin_time = contentBean.getBegin_time();
                    price = contentBean.getCost();
                    tv_fee_rule.setText("¥" + price + "/月");
                    tv_outof_date.setText(end_time);
                    int rule_paymin = contentBean.getRule_paymin();
                    int rule_paymax = contentBean.getRule_paymax();
                    tv_continue_paymonth.setText(rule_paymin + "个月");
                    payTotal = String.valueOf(rule_paymin * Double.valueOf(price));
                    tv_payment_amount.setText(payTotal);
                    monthList.clear();
                    for (int i = rule_paymin; i <= rule_paymax; i++) {
                        monthList.add(String.valueOf(i));
                    }
                    monthNumber = String.valueOf(rule_paymin);
                    parkingHomeModel.getContractCost(2, end_time, monthNumber, price, MonthCardRenewalActivity.this);
                } catch (Exception e) {

                }
                break;
            case 1:
                try {
                    CardpackStationEntity cardpackStationEntity = GsonUtils.gsonToBean(result, CardpackStationEntity.class);
                    CardpackStationEntity.ContentBean contentBean = cardpackStationEntity.getContent();
                    listsBeanList.clear();
                    listsBeanList.addAll(contentBean.getLists());
                    if (TextUtils.isEmpty(station_id) && listsBeanList.size() > 0) {
                        station_id = listsBeanList.get(0).getId();
                    }
                    parkingHomeModel.getContractMsg(0, plate, station_id, this);
                } catch (Exception e) {
                    if (!TextUtils.isEmpty(station_id)) {
                        parkingHomeModel.getContractMsg(0, plate, station_id, this);
                    } else {
                        finish();
                    }
                }
                if (listsBeanList.size() > 1) {
                    choice_station_layout.setEnabled(true);
                    iv_station_arrow.setVisibility(View.VISIBLE);
                } else {
                    choice_station_layout.setEnabled(false);
                }
                break;
            case 2:
                try {
                    ContractCostEntity contractCostEntity = GsonUtils.gsonToBean(result, ContractCostEntity.class);
                    ContractCostEntity.ContentBean contentBean = contractCostEntity.getContent();
                    begin_time = contentBean.getBegin();
                    payTotal = contentBean.getTotal();
                    tv_payment_amount.setText(payTotal);
                    monthContinueTime = contentBean.getEnd();
                    tv_continue_datetime.setText(monthContinueTime);
                } catch (Exception e) {

                }
                break;
            case 3:
                BaseContentEntity basePayEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                try {
                    EparkingPayResultEntity payResultEntity = GsonUtils.gsonToBean(result, EparkingPayResultEntity.class);
                    EparkingPayResultEntity.ContentBean contentBean = payResultEntity.getContent();
                    Intent intent = new Intent(MonthCardRenewalActivity.this, NewOrderPayActivity.class);
                    intent.putExtra(NewOrderPayActivity.ORDER_SN, contentBean.getPrepay_id());
                    startActivity(intent);
                } catch (Exception e) {
                    ToastUtil.toastShow(MonthCardRenewalActivity.this, basePayEntity.getMessage());
                }
                break;
        }
    }
}
