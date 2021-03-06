package com.myproperty.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.model.NewCustomerInfoModel;
import com.myproperty.protocol.IdentityEntity;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;

import java.util.List;

import cn.net.cyberway.R;

/**
 * 身份修改 身份选择
 * Created by hxg on 2019/5/14.
 */
public class PropertyChangeActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public final static String CANT_BACK = "cant_back";
    public final static String IDENTITY_TYPE = "identity_type";
    public final static String IDENTITY_NAME = "identity_name";
    public final static String FROM_DEFAULT = "from_default";
    public final static String FROM_DOOR = "from_door";

    private ImageView mBack;
    private TextView mTitle;
    private TextView user_top_view_right;

    private CardView cv_owner;
    private CardView cv_tenant;
    private CardView cv_family;
    private CardView cv_tourist;
    private Button bt_done;

    private TextView tv_choose;
    private TextView tv_yz;
    private TextView tv_zk;
    private TextView tv_js;
    private TextView tv_yk;

    private SharedPreferences mShared;
    public SharedPreferences.Editor mEditor;
    public int customer_id;
    public int type = 0;
    private boolean cantBack = false;
    private boolean fromDefault = false;//从设置默认进入
    private boolean fromDoor = false;//从门禁进入
    private NewCustomerInfoModel newCustomerInfoModel;
    private List<IdentityEntity.ContentBean> bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_change);
        getWindow().setBackgroundDrawable(null);
        mShared = this.getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        initView();
        initData();
    }

    private void initView() {
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);

        cv_owner = findViewById(R.id.cv_owner);
        cv_tenant = findViewById(R.id.cv_tenant);
        cv_family = findViewById(R.id.cv_family);
        cv_tourist = findViewById(R.id.cv_tourist);
        bt_done = findViewById(R.id.bt_done);

        tv_choose = findViewById(R.id.tv_choose);
        tv_yz = findViewById(R.id.tv_yz);
        tv_zk = findViewById(R.id.tv_zk);
        tv_js = findViewById(R.id.tv_js);
        tv_yk = findViewById(R.id.tv_yk);

        mBack.setOnClickListener(this);

        cv_owner.setOnClickListener(this);
        cv_tenant.setOnClickListener(this);
        cv_family.setOnClickListener(this);
        cv_tourist.setOnClickListener(this);
        bt_done.setOnClickListener(this);
    }

    private void initData() {
        cantBack = getIntent().getBooleanExtra(CANT_BACK, false);//不可返回
        fromDefault = getIntent().getBooleanExtra(FROM_DEFAULT, false);//从设置默认进入
        fromDoor = getIntent().getBooleanExtra(FROM_DOOR, false);//从门禁进入

        if (fromDefault) {
            mTitle.setText("身份选择");
            tv_choose.setText("该房产暂无身份哦，请选择");
        } else if (fromDoor) {
            mTitle.setText("申请权限");
            user_top_view_right = findViewById(R.id.user_top_view_right);
            user_top_view_right.setText("我的申请");
            user_top_view_right.setOnClickListener(this);
        } else {
            mTitle.setText("身份修改");
        }
        if (newCustomerInfoModel == null) {
            newCustomerInfoModel = new NewCustomerInfoModel(this);
        }
        getList();
    }

    private void getList() {
        newCustomerInfoModel.getIdentityList(0, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                if (cantBack) {
                    ToastUtil.toastShow(this, "请选择您的身份");
                } else {
                    finish();
                }
                break;
            case R.id.cv_owner:
                type = 1;
                setBg(1);
                break;
            case R.id.cv_tenant:
                type = 2;
                setBg(2);
                break;
            case R.id.cv_family:
                type = 3;
                setBg(3);
                break;
            case R.id.cv_tourist:
                type = 4;
                setBg(4);
                break;
            case R.id.bt_done:
                Intent intent = null;
                try {
                    int typeId = bean.get(type - 1).getId();
                    String name = bean.get(type - 1).getName();
                    intent = new Intent();
                    intent.putExtra(IDENTITY_TYPE, typeId + "");
                    intent.putExtra(IDENTITY_NAME, name);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                setResult(1, intent);
                finish();
                break;
            case R.id.user_top_view_right:

                break;
        }
    }

    /**
     * 设置选中背景
     */
    private void setBg(int num) {
        bt_done.setBackground(getResources().getDrawable(R.drawable.shape_text_property_select));
        switch (num) {
            case 1:
                cv_tenant.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv_family.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv_tourist.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv_owner.setCardBackgroundColor(getResources().getColor(R.color.color_cecfd1));
                break;
            case 2:
                cv_owner.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv_family.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv_tourist.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv_tenant.setCardBackgroundColor(getResources().getColor(R.color.color_cecfd1));
                break;
            case 3:
                cv_owner.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv_tenant.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv_tourist.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv_family.setCardBackgroundColor(getResources().getColor(R.color.color_cecfd1));
                break;
            case 4:
                cv_owner.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv_tenant.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv_family.setCardBackgroundColor(getResources().getColor(R.color.white));
                cv_tourist.setCardBackgroundColor(getResources().getColor(R.color.color_cecfd1));
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    IdentityEntity addressListEntity = GsonUtils.gsonToBean(result, IdentityEntity.class);
                    bean = addressListEntity.getContent();
                    for (int i = 0; i < bean.size(); i++) {
                        if (i == 0) {
                            tv_yz.setText(bean.get(i).getName());
                        } else if (i == 1) {
                            tv_zk.setText(bean.get(i).getName());
                        } else if (i == 2) {
                            tv_js.setText(bean.get(i).getName());
                        } else if (i == 3) {
                            tv_yk.setText(bean.get(i).getName());
                        }
                    }
                }
                break;
        }
    }

    @Override
    public void onBackPressed() {
        if (cantBack) {
            ToastUtil.toastShow(this, "请选择您的身份");
        } else {
            super.onBackPressed();
        }
    }
}