package com.eparking.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.eparking.helper.InputViewInterface;
import com.eparking.helper.MapNaviUtils;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingOperationModel;
import com.eparking.view.keyboard.PopupHelper;
import com.eparking.view.keyboard.view.InputView;
import com.user.UserAppConst;

import cn.csh.colourful.life.utils.KeyBoardUtils;
import cn.net.cyberway.R;
import cn.net.cyberway.home.view.LeadGestureDialog;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/11 16:57
 * @change
 * @chang time
 * @class describe  一键挪车
 */
public class FastmovingCarActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, InputViewInterface {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private InputView input_view;
    private ClearEditText ed_phone;
    private ClearEditText ed_remark;
    private Button btn_once_submit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fastmoving_car);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_fast_moving));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        input_view = findViewById(R.id.input_view);
        ed_phone = findViewById(R.id.ed_phone);
        ed_remark = findViewById(R.id.ed_remark);
        btn_once_submit = findViewById(R.id.btn_once_submit);
        btn_once_submit.setOnClickListener(this);
        btn_once_submit.setEnabled(false);
        final Button tv_change_cartype = findViewById(R.id.tv_change_cartype);
        MapNaviUtils.initInputView(FastmovingCarActivity.this, input_view, tv_change_cartype, this);
        MapNaviUtils.setInputViewData(FastmovingCarActivity.this, input_view, true);
        ParkingActivityUtils.getInstance().addActivity(this);
        ed_phone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                phone = s.toString().trim();
                setBtnStatus();
            }
        });
        ed_remark.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                remark = s.toString().trim();
                setBtnStatus();
            }
        });
        String phone = shared.getString(UserAppConst.Colour_login_mobile, "");
        ed_phone.setText(phone);
        ed_phone.setSelection(phone.length());
        ed_phone.setCursorVisible(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_once_submit:
                KeyBoardUtils.hideSoftKeyboard(FastmovingCarActivity.this);
                ParkingOperationModel parkingOperationModel = new ParkingOperationModel(this);
                String carNumber = input_view.getNumber();
                parkingOperationModel.carMoveOperation(0, carNumber, phone, remark, this);
                break;
        }
    }

    private String phone;
    private String remark;

    private void showNotice() {
        final LeadGestureDialog dialog = new LeadGestureDialog(FastmovingCarActivity.this, R.style.custom_dialog_theme);
        dialog.show();
        dialog.setTitle(getResources().getString(R.string.sweet_notice));
        dialog.dialog_content.setText(getResources().getString(R.string.moving_car_notice));
        dialog.dialog_content.setTextColor(getResources().getColor(R.color.color_333b46));
        dialog.btn_cancel.setVisibility(View.GONE);
        dialog.btn_open.setText(getResources().getString(R.string.home_know));
        dialog.btn_open.setTextColor(getResources().getColor(R.color.color_007fff));
        dialog.btn_open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                ParkingActivityUtils.getInstance().exit();
            }
        });
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        showNotice();
    }

    @Override
    public void inputViewCallBack(boolean isNewEnergyType) {
        isNewEnergy = isNewEnergyType;
        setBtnStatus();
    }

    private boolean isNewEnergy = false;

    private void setBtnStatus() {
        int length = input_view.getNumber().length();
        boolean isClick = isNewEnergy ? (length == 8 ? true : false) : (length == 7 ? true : false);
        if (isClick && !TextUtils.isEmpty(phone) && phone.length() == 11 && !TextUtils.isEmpty(remark)) {
            btn_once_submit.setBackgroundResource(R.drawable.shape_authorizeation_btn);
            btn_once_submit.setEnabled(true);
        } else {
            btn_once_submit.setBackgroundResource(R.drawable.shape_openticket_default);
            btn_once_submit.setEnabled(false);
        }
        if (isClick) {
            ed_remark.setCursorVisible(true);
            ed_phone.setCursorVisible(true);
        } else {
            ed_remark.setCursorVisible(false);
            ed_phone.setCursorVisible(false);
        }
    }
}
