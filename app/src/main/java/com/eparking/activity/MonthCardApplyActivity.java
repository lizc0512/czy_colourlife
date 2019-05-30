package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.eparking.helper.InputViewInterface;
import com.eparking.helper.MapNaviUtils;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.view.keyboard.view.InputView;

import cn.net.cyberway.R;

import static com.eparking.activity.CarsLicenseUploadActivity.CARNUMBER;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/11 15:55
 * @change
 * @chang time
 * @class describe   申请月卡
 */
public class MonthCardApplyActivity extends BaseActivity implements View.OnClickListener, InputViewInterface {
    public static final String CARSTATUS = "carstatus";//车辆是否认证
    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private TextView user_top_view_right;
    private ImageView imageView_back;//返回
    private InputView input_view;
    private Button btn_next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month_cardapply);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_apply_monthcard));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setText(getResources().getString(R.string.apply_record));
        user_top_view_right.setTextColor(getResources().getColor(R.color.textcolor_27a2f0));
        input_view = findViewById(R.id.input_view);
        btn_next = findViewById(R.id.btn_next);
        btn_next.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        final Button tv_change_cartype = findViewById(R.id.tv_change_cartype);
        MapNaviUtils.initInputView(MonthCardApplyActivity.this, input_view, tv_change_cartype, this);
        MapNaviUtils.setInputViewData(MonthCardApplyActivity.this, input_view, true);
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private void setBtnStatus(boolean isNewEnergyType) {
        int length = input_view.getNumber().length();
        boolean isClick = isNewEnergyType ? (length == 8 ? true : false) : (length == 7 ? true : false);
        if (isClick) {
            btn_next.setBackgroundResource(R.drawable.shape_authorizeation_btn);
        } else {
            btn_next.setBackgroundResource(R.drawable.shape_openticket_default);
        }
        btn_next.setEnabled(isClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                Intent intent = new Intent(MonthCardApplyActivity.this, MonthCardApplyRecordActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_next:
                Intent parkingIntent = new Intent(MonthCardApplyActivity.this, ChoiceParkingCommunityActivity.class);
                parkingIntent.putExtra(CARNUMBER, input_view.getNumber());
                startActivity(parkingIntent);
                break;
        }
    }

    @Override
    public void inputViewCallBack(boolean isNewEnergyType) {
        setBtnStatus(isNewEnergyType);
    }
}
