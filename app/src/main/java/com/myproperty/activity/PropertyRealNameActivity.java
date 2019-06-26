package com.myproperty.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.customerInfo.model.NewCustomerInfoModel;
import com.myproperty.protocol.IdentityEntity;
import com.user.UserAppConst;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 房产实名认证 - 新增房产
 * Created by hxg on 2019/6/26.
 */
public class PropertyRealNameActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView mBack;
    private TextView mTitle;

    private ClearEditText et_name;
    private TextView et_id_card;
    private TextView bt_done;

    private SharedPreferences mShared;
    public SharedPreferences.Editor mEditor;
    public int customer_id;
    private NewCustomerInfoModel newCustomerInfoModel;
    private List<IdentityEntity.ContentBean> bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_real_name);
        getWindow().setBackgroundDrawable(null);
        mShared = this.getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        initView();
        initData();
    }

    private void initView() {
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        et_name = findViewById(R.id.et_name);
        et_id_card = findViewById(R.id.et_id_card);
        bt_done = findViewById(R.id.bt_done);

        mBack.setOnClickListener(this);
        bt_done.setOnClickListener(this);
    }

    private void initData() {
        mTitle.setText("新增房产");
        if (newCustomerInfoModel == null) {
            newCustomerInfoModel = new NewCustomerInfoModel(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.bt_done:
                String name = et_name.getText().toString().trim();
                String idCard = et_id_card.getText().toString().trim();
                ToastUtil.toastShow(this, "完成：" + name + " -- " + idCard);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {


                }
                break;
        }
    }

}