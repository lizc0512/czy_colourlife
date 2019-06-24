package com.myproperty.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.activity.CustomerAddPropertyActivity;
import com.customerInfo.model.NewCustomerInfoModel;
import com.external.eventbus.EventBus;
import com.setting.activity.EditDialog;
import com.user.UserAppConst;
import com.user.UserMessageConstant;

import cn.net.cyberway.R;

/**
 * 房产详情
 * Created by hxg on 2019/5/14.
 */
public class PropertyDetailActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public final static String ID = "id";
    public final static String COMMUNITY_UUID = "community_uuid";
    public final static String COMMUNITY_NAME = "community_name";
    public final static String BUILD_UUID = "build_uuid";
    public final static String BUILD_NAME = "build_name";
    public final static String UNIT_UUID = "unit_uuid";
    public final static String UNIT_NAME = "unit_name";
    public final static String ROOM_UUID = "room_uuid";
    public final static String ROOM_NAME = "room_name";
    public final static String CITY = "city";
    public final static String DEFAULT = "default";
    public final static String ADDRESS = "address";
    public final static String AUTHENTICATION = "authentication";
    public final static String IDENTITY_STATE_NAME = "identity_state_name";
    public final static String IDENTITY_NAME = "identity_name";
    public final static String EMPLOYEE = "employee";

    private String id = "";
    private String community_uuid = "";
    private String community_name = "";
    private String building_uuid = "";
    private String building_name = "";
    private String unit_uuid = "";
    private String unit_name = "";
    private String room_uuid = "";
    private String room_name = "";
    private String city = "";
    private boolean defaul = false;
    private String address = "";
    private String authentication = "";
    private String identityName = "";
    private String employee = "";

    private ImageView mBack;
    private TextView mTitle;
    private TextView tv_address;
    private TextView tv_community_name;
    private TextView tv_identity;
    private LinearLayout ll_has_default;
    private LinearLayout ll_no_default;
    private LinearLayout ll_emp;

    private TextView tv_set_default;
    private TextView tv_edit;
    private TextView tv_change;
    private TextView tv_delete;
    private TextView tv_edit_other;
    private TextView tv_change_other;
    private TextView tv_set_default_emp;

    private SharedPreferences mShared;
    public SharedPreferences.Editor mEditor;
    public int customer_id;
    private NewCustomerInfoModel newCustomerInfoModel;
    private EditDialog noticeDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_detail);
        getWindow().setBackgroundDrawable(null);
        newCustomerInfoModel = new NewCustomerInfoModel(this);
        mShared = this.getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        initView();
        initData();
    }

    private void initView() {
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        tv_address = findViewById(R.id.tv_address);
        tv_community_name = findViewById(R.id.tv_community_name);
        tv_identity = findViewById(R.id.tv_identity);
        ll_has_default = findViewById(R.id.ll_has_default);
        ll_no_default = findViewById(R.id.ll_no_default);
        ll_emp = findViewById(R.id.ll_emp);
        tv_set_default = findViewById(R.id.tv_set_default);
        tv_edit = findViewById(R.id.tv_edit);
        tv_change = findViewById(R.id.tv_change);
        tv_delete = findViewById(R.id.tv_delete);
        tv_edit_other = findViewById(R.id.tv_edit_other);
        tv_change_other = findViewById(R.id.tv_change_other);
        tv_set_default_emp = findViewById(R.id.tv_set_default_emp);

        mBack.setOnClickListener(this);
        tv_set_default.setOnClickListener(this);
        tv_edit.setOnClickListener(this);
        tv_change.setOnClickListener(this);
        tv_delete.setOnClickListener(this);
        tv_edit_other.setOnClickListener(this);
        tv_change_other.setOnClickListener(this);
        tv_set_default_emp.setOnClickListener(this);
    }

    private void initData() {
        mTitle.setText("房产详情");
        Intent intent = getIntent();
        community_name = intent.getStringExtra(COMMUNITY_NAME);
        address = intent.getStringExtra(ADDRESS);

        tv_address.setText(address);
        tv_community_name.setText(community_name);

        id = intent.getStringExtra(ID);
        community_uuid = intent.getStringExtra(COMMUNITY_UUID) == null ? "" : intent.getStringExtra(COMMUNITY_UUID);
        if (!TextUtils.isEmpty(intent.getStringExtra(COMMUNITY_NAME))) {
            community_name = intent.getStringExtra(COMMUNITY_NAME);
        }
        building_uuid = intent.getStringExtra(BUILD_UUID) == null ? "" : intent.getStringExtra(BUILD_UUID);
        if (!TextUtils.isEmpty(intent.getStringExtra(BUILD_NAME))) {
            building_name = intent.getStringExtra(BUILD_NAME);
        }
        unit_uuid = intent.getStringExtra(UNIT_UUID) == null ? "" : intent.getStringExtra(UNIT_UUID);
        if (!TextUtils.isEmpty(intent.getStringExtra(UNIT_NAME))) {
            unit_name = intent.getStringExtra(UNIT_NAME);
        }
        room_uuid = intent.getStringExtra(ROOM_UUID) == null ? "" : intent.getStringExtra(ROOM_UUID);
        room_name = intent.getStringExtra(ROOM_NAME) == null ? "" : intent.getStringExtra(ROOM_NAME);

        city = intent.getStringExtra(CITY);
        defaul = getIntent().getBooleanExtra(DEFAULT, false);
        authentication = intent.getStringExtra(AUTHENTICATION);
        employee = intent.getStringExtra(EMPLOYEE);

        if ("1".equals(employee)) {//员工 只有设置当前无操作
            ll_emp.setVisibility(defaul ? View.GONE : View.VISIBLE);
            tv_identity.setText("员工");
        } else {
            ll_has_default.setVisibility(defaul ? View.GONE : View.VISIBLE);
            ll_no_default.setVisibility(defaul ? View.VISIBLE : View.GONE);
            identityName = intent.getStringExtra(IDENTITY_NAME);
            tv_identity.setText(identityName);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_set_default_emp://员工设置默认
            case R.id.tv_set_default://设置默认
                if ("1".equals(employee) || !TextUtils.isEmpty(identityName)) {
                    newCustomerInfoModel.customerAddressChange(0, id, this);
                } else {
                    Intent intentSet = new Intent(this, PropertyChangeActivity.class);
                    intentSet.putExtra(PropertyChangeActivity.FROM_DEFAULT, true);
                    startActivityForResult(intentSet, 3);//修改房产身份
                }
                break;
            case R.id.tv_edit_other:
            case R.id.tv_edit://点击编辑
                edit();
                break;
            case R.id.tv_change_other:
            case R.id.tv_change://修改身份
                Intent intent = new Intent(this, PropertyChangeActivity.class);
                startActivityForResult(intent, 2);
                break;
            case R.id.tv_delete://删除
                if (noticeDialog == null) {
                    noticeDialog = new EditDialog(this);
                }
                noticeDialog.setContent("确定要删除改地址?");
                noticeDialog.show();
                noticeDialog.left_button.setOnClickListener(v1 -> {
                    if (noticeDialog != null) {
                        noticeDialog.dismiss();
                    }
                });
                noticeDialog.right_button.setOnClickListener(v2 -> {
                    if (noticeDialog != null) {
                        noticeDialog.dismiss();
                    }
                    newCustomerInfoModel.deleteCustomerAddress(1, id, PropertyDetailActivity.this);
                });
                break;
        }
    }

    /**
     * 点击编辑
     */
    public void edit() {
        Intent intent = new Intent();
        intent.setClass(this, CustomerAddPropertyActivity.class);

        intent.putExtra(CustomerAddPropertyActivity.ID, id);
        intent.putExtra(CustomerAddPropertyActivity.COMMUNITY_UUID, community_uuid);
        intent.putExtra(CustomerAddPropertyActivity.COMMUNITY_NAME, community_name);
        intent.putExtra(CustomerAddPropertyActivity.BUILD_UUID, building_uuid);
        intent.putExtra(CustomerAddPropertyActivity.BUILD_NAME, building_name);
        intent.putExtra(CustomerAddPropertyActivity.UNIT_UUID, unit_uuid);
        intent.putExtra(CustomerAddPropertyActivity.UNIT_NAME, unit_name);
        intent.putExtra(CustomerAddPropertyActivity.ROOM_UUID, room_uuid);
        intent.putExtra(CustomerAddPropertyActivity.ROOM_NAME, room_name);
        intent.putExtra(CustomerAddPropertyActivity.CITY, city);
        intent.putExtra(CustomerAddPropertyActivity.DEFAULT, defaul);
        intent.putExtra(CustomerAddPropertyActivity.IDENTITY, false);

        startActivityForResult(intent, 1);
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0://设置默认
                if (!TextUtils.isEmpty(result)) {
                    ToastUtil.toastShow(this, getResources().getString(R.string.property_set_default_success));
                    editor.putString(UserAppConst.Colour_login_community_uuid, community_uuid);
                    editor.putString(UserAppConst.Colour_login_community_name, community_name);
                    editor.putString(UserAppConst.Colour_Build_name, building_name);
                    editor.putString(UserAppConst.Colour_Room_name, room_name);
                    editor.putString(UserAppConst.Colour_Unit_name, unit_uuid);
                    editor.putString(UserAppConst.Colour_authentication, authentication);
                    editor.commit();
                    Message msg = new Message();
                    msg.what = UserMessageConstant.CHANGE_COMMUNITY;
                    msg.obj = id;
                    EventBus.getDefault().post(msg);
                    setResult(1);//回调刷新列表
                    finish();
                }
                break;
            case 1://删除
                ToastUtil.toastShow(this, getResources().getString(R.string.property_delect_success));
                setResult(1);//回调刷新列表
                finish();
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    ToastUtil.toastShow(this, "设置成功");
                    tv_identity.setText(identityName);
                }
                break;
            case 3:
                if (!TextUtils.isEmpty(result)) {
                    newCustomerInfoModel.customerAddressChange(0, id, this);
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 1) {
                    setResult(1);//回调刷新列表
                    finish();
                }
                break;
            case 2:
                if (resultCode == 1) {//修改身份
                    try {
                        String identity = data.getStringExtra(PropertyChangeActivity.IDENTITY_TYPE);
                        identityName = data.getStringExtra(PropertyChangeActivity.IDENTITY_NAME);
                        newCustomerInfoModel.changeIdentity(2, identity, id, this);
                        setResult(1);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 3:
                if (resultCode == 1) {//修改身份
                    try {
                        String identity = data.getStringExtra(PropertyChangeActivity.IDENTITY_TYPE);
                        newCustomerInfoModel.changeIdentity(3, identity, id, this);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}