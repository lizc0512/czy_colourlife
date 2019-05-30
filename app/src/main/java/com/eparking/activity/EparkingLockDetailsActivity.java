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
import com.eparking.helper.CustomDialog;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.protocol.ParkingLockEntity;

import cn.net.cyberway.R;

import static com.eparking.activity.ShareParkingSpaceActivity.LOCKCODE;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 16:29
 * @change
 * @chang time
 * @class describe  我的卡包---车位锁
 */
public class EparkingLockDetailsActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public static final String LOCKINFOR = "lockinfor";
    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private TextView user_top_view_right;
    private String lockName;
    private String lockCode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eparking_lockinfor);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_lock_infor));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setText(getResources().getString(R.string.parking_car_unbind));
        user_top_view_right.setTextColor(getResources().getColor(R.color.textcolor_27a2f0));
        user_top_view_right.setOnClickListener(this);
        initView();
        Intent intent = getIntent();
        ParkingLockEntity.ContentBean contentBean = (ParkingLockEntity.ContentBean) intent.getSerializableExtra(LOCKINFOR);
        tv_parking_lock.setText(contentBean.getLock_name());
        tv_car_type.setText(contentBean.getStation_name());
        lockCode = contentBean.getEtcode();
        tv_lock_number.setText(lockCode);
        lockName = contentBean.getLock_name();
        tv_eparking_address.setText(lockName);
        String status = contentBean.getStatus();
        if ("close".equalsIgnoreCase(status)) {
            iv_car_logo.setImageResource(R.drawable.eparking_img_nouse);
            tv_lock_status.setText(getResources().getString(R.string.lock_down));
        } else {
            iv_car_logo.setImageResource(R.drawable.eparking_img_canuse);
            tv_lock_status.setText(getResources().getString(R.string.lock_up));
        }
        tv_eparking_address.setText(contentBean.getStation_address());
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    private ImageView iv_car_logo;
    private TextView tv_parking_lock;
    private TextView tv_car_type;
    private TextView tv_lock_status;
    private TextView tv_lock_number;
    private TextView tv_eparking_address;
    private RelativeLayout share_record_layout;
    private Button btn_share_epark;

    private void initView() {
        iv_car_logo = findViewById(R.id.iv_car_logo);
        tv_parking_lock = findViewById(R.id.tv_parking_lock);
        tv_car_type = findViewById(R.id.tv_car_type);
        tv_lock_status = findViewById(R.id.tv_lock_status);
        tv_lock_number = findViewById(R.id.tv_lock_number);
        tv_eparking_address = findViewById(R.id.tv_eparking_address);
        share_record_layout = findViewById(R.id.share_record_layout);
        btn_share_epark = findViewById(R.id.btn_share_epark);
        share_record_layout.setOnClickListener(this);
        btn_share_epark.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                showDialog();
                break;
            case R.id.share_record_layout:
                Intent intent = new Intent(EparkingLockDetailsActivity.this, ShareParkingSpaceRecoActivity.class);
                startActivity(intent);
                break;
            case R.id.btn_share_epark:
                Intent shareIntent = new Intent(EparkingLockDetailsActivity.this, ShareParkingSpaceActivity.class);
                shareIntent.putExtra(LOCKCODE, lockCode);
                startActivity(shareIntent);
                break;
        }
    }

    private void showDialog() {
        final CustomDialog customDialog = new CustomDialog(EparkingLockDetailsActivity.this, R.style.custom_dialog_theme);
        customDialog.show();
        customDialog.setCancelable(false);
        customDialog.dialog_content.setText(getResources().getString(R.string.notice_unbind_start) + lockName + getResources().getString(R.string.notice_lock_msg) + getResources().getString(R.string.notice_unbind_end));
        customDialog.dialog_button_cancel.setText(getResources().getString(R.string.message_cancel));
        customDialog.dialog_button_ok.setText(getResources().getString(R.string.message_unbind));
        customDialog.dialog_line.setVisibility(View.VISIBLE);
        customDialog.dialog_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                setResult(200);
                finish();
            }
        });
        customDialog.dialog_button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }

    @Override
    public void OnHttpResponse(int what, String result) {

    }
}
