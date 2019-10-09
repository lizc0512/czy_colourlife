package com.door.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;

import cn.net.cyberway.R;

/**
 * 申请开门
 * hxg 2019.9.26
 */
public class DoorApplyOpenActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;

    private com.BeeFramework.view.ClearEditText et_name;
    private TextView tv_community;
    private TextView tv_identity;
    private com.BeeFramework.view.ClearEditText et_reason;
    private TextView tv_apply_one;
    private TextView tv_apply_forever;

    private boolean canSubmit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_apply_open);
        initView();
        initData();
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);

        et_name = findViewById(R.id.et_name);
        tv_community = findViewById(R.id.tv_community);
        tv_identity = findViewById(R.id.tv_identity);
        et_reason = findViewById(R.id.et_reason);
        tv_apply_one = findViewById(R.id.tv_apply_one);
        tv_apply_forever = findViewById(R.id.tv_apply_forever);

        user_top_view_back.setOnClickListener(this);
        tv_community.setOnClickListener(this);
        tv_identity.setOnClickListener(this);
        tv_apply_one.setOnClickListener(this);
        tv_apply_forever.setOnClickListener(this);
    }

    private void initData() {
        user_top_view_title.setText("申请开门");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_community:
                break;
            case R.id.tv_identity:
                break;
            case R.id.tv_apply_one:
                if (canSubmit) {

                } else {
                    ToastUtil.toastShow(this, "请完善信息");
                }
                break;
            case R.id.tv_apply_forever:
                break;
        }
    }

    private void canSubmit() {
        if (!TextUtils.isEmpty(et_name.getText().toString().trim())
                && !TextUtils.isEmpty(et_name.getText().toString().trim())
                && !TextUtils.isEmpty(et_name.getText().toString().trim())
                && !TextUtils.isEmpty(et_name.getText().toString().trim())) {
            canSubmit = true;
            tv_apply_one.setBackgroundResource(R.drawable.shape_radius24_color_0567fa);
        } else {
            canSubmit = false;
            tv_apply_one.setBackgroundResource(R.drawable.shape_radius24_color_cdcfd1);
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 1:
                if (!TextUtils.isEmpty(result)) {

                }
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {

                }
                break;
        }
    }
}
