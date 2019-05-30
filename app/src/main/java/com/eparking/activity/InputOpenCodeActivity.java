package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingOperationModel;
import com.eparking.view.PasswordInputView;

import cn.csh.colourful.life.utils.KeyBoardUtils;
import cn.net.cyberway.R;

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
public class InputOpenCodeActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public static final String PLATE = "plate";
    public static final String STATIONID = "stationid";

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private PasswordInputView pwd_inputview;
    private Button btn_once_opencode;

    public String plateNumber;
    public String stationId;
    private ParkingOperationModel parkingOperationModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_opencode);
        czy_title_layout = findViewById(R.id.czy_title_layout);
        tv_title = findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_emergency_sluice));
        imageView_back = findViewById(R.id.user_top_view_back);
        pwd_inputview = findViewById(R.id.pwd_inputview);
        btn_once_opencode = findViewById(R.id.btn_once_opencode);
        imageView_back.setOnClickListener(this);
        btn_once_opencode.setOnClickListener(this);
        pwd_inputview.setPasswordPaintSize();
        pwd_inputview.setBtnClick(btn_once_opencode);
        Intent intent = getIntent();
        plateNumber = intent.getStringExtra(PLATE);
        stationId = intent.getStringExtra(STATIONID);
        parkingOperationModel = new ParkingOperationModel(this);
        parkingOperationModel.getOpenCode(0, plateNumber, stationId, this);
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_once_opencode:
                String inputCode = pwd_inputview.getText().toString().trim();
                if (!TextUtils.isEmpty(inputCode)) {
                    parkingOperationModel.carOpenOperation(1, plateNumber, stationId, inputCode, this);
                } else {
                    ToastUtil.toastShow(InputOpenCodeActivity.this, getResources().getString(R.string.input_open_code));
                }
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                KeyBoardUtils.openKeybord(pwd_inputview, InputOpenCodeActivity.this);
                ToastUtil.toastShow(InputOpenCodeActivity.this, getResources().getString(R.string.opencode_send_success));
                break;
            case 1:
                ToastUtil.toastShow(InputOpenCodeActivity.this, getResources().getString(R.string.emergency_open_success));
                ParkingActivityUtils.getInstance().exit();
                break;
        }

    }
}
