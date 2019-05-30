package com.eparking.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.helper.PermissionUtils;
import com.eparking.model.ParkingHomeModel;
import com.eparking.protocol.ParkingAddressEntity;
import com.eparking.protocol.PaymentRecordEntity;
import com.nohttp.utils.GsonUtils;

import java.util.List;

import cn.csh.colourful.life.utils.ToastUtils;
import cn.net.cyberway.R;

import static com.cashier.activity.NewOrderPayActivity.ORDER_SN;
import static com.tendcloud.tenddata.ab.mContext;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  临停或月卡的缴费详情
 */
public class PaymentTempDetailsActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String PAYMENTINFOR = "paymentinfor";
    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private ScrollView scroll_view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temppark_paymentdetails);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_payment_details));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        scroll_view = findViewById(R.id.scroll_view);
        initView();
        initData();
        imageView_back.setOnClickListener(this);
        btn_open_ticket.setOnClickListener(this);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                scroll_view.fullScroll(ScrollView.FOCUS_DOWN);
            }
        }, 300);
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private String tNum;

    private void initData() {
        Intent intent = getIntent();
        PaymentRecordEntity.ContentBean.ListsBean listsBean = (PaymentRecordEntity.ContentBean.ListsBean) intent.getSerializableExtra(PAYMENTINFOR);
        String type = listsBean.getType();
        tv_parking_name.setText(listsBean.getStation_name());
        tv_car_number.setText(listsBean.getPlate());
        String stationId = listsBean.getStation();//通过id去获取地址
        if (!"MONTH".equals(type)) {
            parking_time_layout.setVisibility(View.VISIBLE);
            leave_date_layout.setVisibility(View.VISIBLE);
            enter_date_layout.setVisibility(View.VISIBLE);
            tv_enter_date.setText(listsBean.getArrival());
            tv_leave_date.setText(listsBean.getDeparture());
            tv_parking_time.setText(TimeUtil.dateDiff(listsBean.getDuration()));
            tv_payment_type.setText(getResources().getString(R.string.temporary_stop_fee));
            tv_discount_amount.setText(listsBean.getDiscount_amount() + "");
        } else {
            tv_discount_title.setText(getResources().getString(R.string.parking_payment_timeinterval));
            tv_payment_type.setText(getResources().getString(R.string.monthcard));
            tv_discount_amount.setText(TimeUtil.formatTime(listsBean.getArrival(), "yyyy.MM.dd") + "-" + TimeUtil.formatTime(listsBean.getDeparture(), "yyyy.MM.dd"));
        }
        tv_payment_amount.setText(listsBean.getAmount());
        tv_payment_time.setText(listsBean.getPaytime());
        tNum = listsBean.getTnum();
        if (!TextUtils.isEmpty(tNum)) {
            tv_payment_number.setText(tNum);
            tv_payment_number.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                    // 创建普通字符型ClipData
                    ClipData mClipData = ClipData.newPlainText("Label", tNum);
                    // 将ClipData内容放到系统剪贴板里。
                    cm.setPrimaryClip(mClipData);
                    ToastUtils.showMessage(mContext, "缴费编号已复制到剪贴板");
                    return false;
                }
            });
        }
        ParkingHomeModel parkingHomeModel = new ParkingHomeModel(PaymentTempDetailsActivity.this);
        parkingHomeModel.getParkingAddressInfor(0, stationId, this);
    }


    private TextView tv_parking_name;
    private TextView tv_car_number;
    private TextView tv_parking_address;
    private TextView tv_enter_date;
    private TextView tv_leave_date;
    private TextView tv_parking_time;
    private TextView tv_payment_type;
    private TextView tv_discount_title;
    private TextView tv_discount_amount;
    private TextView tv_payment_amount;
    private TextView tv_payment_time;
    private TextView tv_payment_number;
    private Button btn_open_ticket;
    private RelativeLayout parking_time_layout;
    private RelativeLayout leave_date_layout;
    private RelativeLayout enter_date_layout;
    private LinearLayout bottom_layout;

    private void initView() {
        tv_parking_name = findViewById(R.id.tv_parking_name);
        tv_car_number = findViewById(R.id.tv_car_number);
        tv_parking_address = findViewById(R.id.tv_parking_address);
        tv_enter_date = findViewById(R.id.tv_enter_date);
        tv_leave_date = findViewById(R.id.tv_leave_date);
        tv_parking_time = findViewById(R.id.tv_parking_time);
        tv_payment_type = findViewById(R.id.tv_payment_type);
        tv_discount_amount = findViewById(R.id.tv_discount_amount);
        tv_discount_title = findViewById(R.id.tv_discount_title);
        tv_payment_amount = findViewById(R.id.tv_payment_amount);
        tv_payment_time = findViewById(R.id.tv_payment_time);
        tv_payment_number = findViewById(R.id.tv_payment_number);
        btn_open_ticket = findViewById(R.id.btn_open_ticket);
        parking_time_layout = findViewById(R.id.parking_time_layout);
        leave_date_layout = findViewById(R.id.leave_date_layout);
        enter_date_layout = findViewById(R.id.enter_date_layout);
        bottom_layout = findViewById(R.id.bottom_layout);
        bottom_layout.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_open_ticket:
                Intent intent = new Intent(PaymentTempDetailsActivity.this, ApplyOpenTicketActivity.class);
                intent.putExtra(ORDER_SN, tNum);
                startActivity(intent);
                break;
            case R.id.bottom_layout:
                PermissionUtils.showPhonePermission(PaymentTempDetailsActivity.this);
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
