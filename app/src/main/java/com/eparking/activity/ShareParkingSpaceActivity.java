package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.helper.ConstantKey;
import com.eparking.helper.OptionsPickerInterface;
import com.eparking.helper.OptionsPickerViewUtils;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingHomeModel;
import com.eparking.model.ParkingOperationModel;
import com.eparking.protocol.ParkingLockEntity;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.utils.KeyBoardUtils;
import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/11 17:00
 * @change
 * @chang time
 * @class describe  共享车位
 */
public class ShareParkingSpaceActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, OptionsPickerInterface {

    public static final String LOCKCODE = "lockcode";
    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private TextView user_top_view_right;
    private LinearLayout parking_address_layout;
    private TextView tv_parking_address;
    private ImageView iv_parking_arrow;
    private EditText ed_parking_name;
    private LinearLayout share_enter_layout;
    private TextView tv_share_starttime;
    private LinearLayout share_end_layout;
    private TextView tv_share_endtime;
    private Button btn_once_share;
    private List<ParkingLockEntity.ContentBean> parkingLockEntityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_parking);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_share_parking));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setText(getResources().getString(R.string.share_record));
        user_top_view_right.setTextColor(getResources().getColor(R.color.textcolor_27a2f0));
        parking_address_layout = findViewById(R.id.parking_address_layout);
        tv_parking_address = findViewById(R.id.tv_parking_address);
        iv_parking_arrow = findViewById(R.id.iv_parking_arrow);
        ed_parking_name = findViewById(R.id.ed_parking_name);
        share_enter_layout = findViewById(R.id.share_enter_layout);
        tv_share_starttime = findViewById(R.id.tv_share_starttime);
        share_end_layout = findViewById(R.id.share_end_layout);
        tv_share_endtime = findViewById(R.id.tv_share_endtime);
        btn_once_share = findViewById(R.id.btn_once_share);
        imageView_back.setOnClickListener(this);
        parking_address_layout.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        share_enter_layout.setOnClickListener(this);
        share_end_layout.setOnClickListener(this);
        btn_once_share.setOnClickListener(this);
        Intent intent = getIntent();
        String lockCode = intent.getStringExtra(LOCKCODE);
        parkingLockEntityList = new ArrayList<>();
        String lockListCache = shared.getString(ConstantKey.EPARKINGCARHISTORYLOCK, "");
        if (!TextUtils.isEmpty(lockListCache)) {
            getLockList(lockListCache);
        } else {
            ParkingHomeModel parkingHomeModel = new ParkingHomeModel(this);
            parkingHomeModel.getHomeLockList(0, this);
        }
        tv_parking_address.setText(lockCode);
        ParkingActivityUtils.getInstance().addActivity(this);
        ed_parking_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 setBtnClick();
            }
        });
    }

    @Override
    public void onClick(View v) {
        KeyBoardUtils.hideSoftKeyboard(ShareParkingSpaceActivity.this);
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                Intent intent = new Intent(ShareParkingSpaceActivity.this, ShareParkingSpaceRecoActivity.class);
                startActivity(intent);
                break;
            case R.id.parking_address_layout:
                OptionsPickerViewUtils.showPickerView(ShareParkingSpaceActivity.this, R.string.eparking_place, 2, null, this);
                break;
            case R.id.share_enter_layout:
                OptionsPickerViewUtils.showTimeDialog(ShareParkingSpaceActivity.this, R.string.share_start_time, 3, this);
                break;
            case R.id.share_end_layout:
                OptionsPickerViewUtils.showTimeDialog(ShareParkingSpaceActivity.this, R.string.share_end_time, 4, this);
                break;
            case R.id.btn_once_share:
                ParkingOperationModel parkingOperationModel = new ParkingOperationModel(this);
                String parkingName = tv_parking_address.getText().toString().trim();
                String carNumber = ed_parking_name.getText().toString().trim();
                parkingOperationModel.lockShareOperation(0, parkingName, carNumber, startSecond, endSecond, ShareParkingSpaceActivity.this);
                break;
        }
    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                ToastUtil.toastShow(ShareParkingSpaceActivity.this, getResources().getString(R.string.lock_share_success));
                ParkingActivityUtils.getInstance().exit();
                break;
            case 1:
                getLockList(result);
                break;
        }
    }

    private void getLockList(String result) {
        try {
            parkingLockEntityList.clear();
            ParkingLockEntity parkingLockEntity = GsonUtils.gsonToBean(result, ParkingLockEntity.class);
            parkingLockEntityList.addAll(parkingLockEntity.getContent());
        } catch (Exception e) {

        }
        if (parkingLockEntityList.size() > 1) {
            parking_address_layout.setEnabled(true);
        } else {
            iv_parking_arrow.setVisibility(View.INVISIBLE);
            parking_address_layout.setEnabled(false);
        }

    }

    private long startSecond;
    private long endSecond;

    @Override
    public void choicePickResult(int type, String choiceText, String choiceId) {
        switch (type) {
            case 2:
                tv_parking_address.setText(choiceText);
                break;
            case 3:
                tv_share_starttime.setText(choiceText);
                startSecond = Long.valueOf(choiceId);
                setBtnClick();
                break;
            case 4:
                tv_share_endtime.setText(choiceText);
                endSecond = Long.valueOf(choiceId);
                setBtnClick();
                break;
        }
    }

    private void setBtnClick() {
        String carNumber = ed_parking_name.getText().toString().trim();
        if (startSecond == 0 || endSecond == 0 || TextUtils.isEmpty(carNumber) || carNumber.length() < 7) {
            btn_once_share.setBackgroundResource(R.drawable.shape_openticket_default);
            btn_once_share.setEnabled(false);
        } else {
            btn_once_share.setBackgroundResource(R.drawable.shape_authorizeation_btn);
            btn_once_share.setEnabled(true);
        }
    }
}
