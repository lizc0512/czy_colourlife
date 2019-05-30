package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingApplyModel;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/11 16:27
 * @change
 * @chang time
 * @class describe 提交实名认证信息
 */
public class SubmitIDCardInforActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private EditText ed_idcard_number;
    private EditText ed_idcard_name;
    private EditText ed_idcard_sex;
    private EditText ed_idcard_nation;
    private EditText ed_born_date;
    private ClearEditText ed_idcard_address;
    private EditText ed_effective_date;
    private EditText ed_expiry_date;
    private Button btn_check_next;

    private ParkingApplyModel parkingApplyModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submit_idcard);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_identify_authorize));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        ed_idcard_number = findViewById(R.id.ed_idcard_number);
        ed_idcard_name = findViewById(R.id.ed_idcard_name);
        ed_idcard_sex = findViewById(R.id.ed_idcard_sex);
        ed_idcard_nation = findViewById(R.id.ed_idcard_nation);
        ed_born_date = findViewById(R.id.ed_born_date);
        ed_idcard_address = findViewById(R.id.ed_idcard_address);
        ed_effective_date = findViewById(R.id.ed_effective_date);
        ed_expiry_date = findViewById(R.id.ed_expiry_date);
        btn_check_next = findViewById(R.id.btn_check_next);
        imageView_back.setOnClickListener(this);
        btn_check_next.setOnClickListener(this);
        parkingApplyModel = new ParkingApplyModel(SubmitIDCardInforActivity.this);
        ParkingActivityUtils.getInstance().addActivity(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_check_next:
                String idcardNumber = ed_idcard_number.getText().toString().trim();
                String name = ed_idcard_name.getText().toString().trim();
                parkingApplyModel.userVerifyOperation(0, "", "", name, idcardNumber, this);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        Intent intent = new Intent(SubmitIDCardInforActivity.this, InputOpenCodeActivity.class);
        startActivity(intent);
    }
}
