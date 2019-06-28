package com.myproperty.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.customerInfo.model.NewCustomerInfoModel;
import com.myproperty.protocol.IdentityEntity;
import com.myproperty.view.PropertyRealNameDialog;
import com.user.UserAppConst;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 房产实名认证 - 新增房产
 * Created by hxg on 2019/6/26.
 */
public class PropertyRealNameActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public final static String COMMUNITY_UUID = "community_uuid";
    public final static String BUILD_NAME = "build_name";
    public final static String UNIT_NAME = "unit_name";
    public final static String ROOM_NAME = "room_name";

    private String community_uuid = "";
    private String build_name = "";
    private String unit_name = "";
    private String room_name = "";

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

        Intent intent = getIntent();
        community_uuid = intent.getStringExtra(COMMUNITY_UUID) == null ? "" : intent.getStringExtra(COMMUNITY_UUID);
        build_name = intent.getStringExtra(BUILD_NAME) == null ? "" : intent.getStringExtra(BUILD_NAME);
        unit_name = intent.getStringExtra(UNIT_NAME) == null ? "" : intent.getStringExtra(UNIT_NAME);
        room_name = intent.getStringExtra(ROOM_NAME) == null ? "" : intent.getStringExtra(ROOM_NAME);
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
                newCustomerInfoModel.isWhiteName(0, name, idCard, community_uuid, build_name, unit_name, room_name, this);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String content = jsonObject.getString("content");
                        JSONObject data = new JSONObject(content);
                        int is_verify = data.getInt("is_white");
                        if (1 == is_verify) {//1：白名单 2：不是白名单
                            Intent intent = new Intent(this, PropertyChangeActivity.class);
                            startActivityForResult(intent, 1);
                        } else {
                            PropertyRealNameDialog dialog = new PropertyRealNameDialog(this, "您不在白名单范围内");
                            dialog.btn_yes.setOnClickListener(v -> dialog.dismiss());
                            dialog.show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    String identity = data.getStringExtra(PropertyChangeActivity.IDENTITY_TYPE);

                    Intent intent = new Intent();
                    intent.putExtra(PropertyChangeActivity.IDENTITY_TYPE, identity);
                    setResult(1, intent);
                    finish();
                }
                break;
        }
    }
}