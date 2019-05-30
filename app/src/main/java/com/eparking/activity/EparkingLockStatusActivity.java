package com.eparking.activity;

import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.CustomDialog;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingOperationModel;
import com.eparking.protocol.ParkingLockDetailsEntity;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 16:29
 * @change
 * @chang time
 * @class describe  扫描车位锁的二维码获取---车位的状态
 */
public class EparkingLockStatusActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public static final String ETCODE = "etcode";
    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private ImageView iv_lock_status;
    private TextView tv_parking_lock;
    private TextView tv_parking_address;
    private TextView tv_lock_status;
    private Button btn_lock_operation;

    private ParkingOperationModel parkingOperationModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lockstate_infor);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_parking_infor));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        iv_lock_status = findViewById(R.id.iv_lock_status);
        tv_parking_lock = findViewById(R.id.tv_parking_lock);
        tv_parking_address = findViewById(R.id.tv_parking_address);
        tv_lock_status = findViewById(R.id.tv_lock_status);
        btn_lock_operation = findViewById(R.id.btn_lock_operation);
        imageView_back.setOnClickListener(this);
        btn_lock_operation.setOnClickListener(this);
        etCode = getIntent().getStringExtra(ETCODE);
        parkingOperationModel = new ParkingOperationModel(this);
        parkingOperationModel.getLockStatus(0, etCode, this);
        ParkingActivityUtils.getInstance().addActivity(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_lock_operation:
                if ("close".equalsIgnoreCase(etStatus)) {
                    showDialog();
                } else {
                    parkingOperationModel.carLockOpenation(0, etCode, "close", this);
                }
                break;
        }
    }

    private void showDialog() {
        final CustomDialog customDialog = new CustomDialog(EparkingLockStatusActivity.this, R.style.custom_dialog_theme);
        customDialog.show();
        customDialog.setCancelable(false);
        customDialog.dialog_content.setText(getResources().getString(R.string.parking_use_notice));
        customDialog.dialog_button_ok.setGravity(Gravity.CENTER_HORIZONTAL);
        customDialog.dialog_button_cancel.setVisibility(View.GONE);
        customDialog.dialog_button_ok.setText(getResources().getString(R.string.message_define));
        customDialog.dialog_button_ok.setTextColor(getResources().getColor(R.color.color_007fff));
        customDialog.dialog_button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }

    private String etCode = "";
    private String etStatus = "";

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    ParkingLockDetailsEntity parkingLockDetailsEntity = GsonUtils.gsonToBean(result, ParkingLockDetailsEntity.class);
                    ParkingLockDetailsEntity.ContentBean contentBean = parkingLockDetailsEntity.getContent();
                    etStatus = contentBean.getStatus();
                    etCode = contentBean.getEtcode();
                    tv_parking_lock.setText(contentBean.getName());
                    tv_parking_address.setText(contentBean.getStation_name());
                    if ("close".equalsIgnoreCase(etStatus)) {
                        iv_lock_status.setImageResource(R.drawable.eparking_img_nouse);
                        tv_lock_status.setText(getResources().getString(R.string.lock_down));
                    } else {
                        iv_lock_status.setImageResource(R.drawable.eparking_img_canuse);
                        tv_lock_status.setText(getResources().getString(R.string.lock_up));
                    }
                } catch (Exception e) {

                }
                break;
            case 1:
                BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                ToastUtil.toastShow(EparkingLockStatusActivity.this, baseContentEntity.getMessage());
                break;
        }
    }
}
