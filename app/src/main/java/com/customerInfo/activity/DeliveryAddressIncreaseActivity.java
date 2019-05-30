package com.customerInfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.model.DeliveryAddressModel;
import com.customerInfo.view.DeliveryAddressDialog;
import com.customerInfo.view.OnAddressSelectedListener;
import com.invite.activity.ContactsActivity;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/7 16:28
 * @change
 * @chang time
 * @class describe
 */

public class DeliveryAddressIncreaseActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, OnAddressSelectedListener {
    public static final String PROVINCEUUID = "provinceuuid";
    public static final String CITYUUID = "cityuuid";
    public static final String COUNTYUUID = "countyuuid";
    public static final String TOWNUUID = "townuuid";
    public static final String COMMUNITYUUID = "communityuuid";
    public static final String DELIVERYREGION = "deliveryregion";
    public static final String DELIVERYADDRESS = "deliveryaddress";
    public static final String DELIVERYNAME = "deliveryname";
    public static final String DELIVERYPHONE = "deliveryphone";
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private LinearLayout choice_phone_layout;
    private LinearLayout delivery_area_layout;
    private EditText ed_delivery_name;
    private EditText ed_delivery_phone;
    private TextView tv_delivery_address;
    private EditText ed_delivery_street;
    private Button btn_save;
    private String addressId = "";
    private String deliveryName;
    private String deliveryPhone;
    private String deliveryAddress;
    private String deliveryStreet;
    private String provinceId;
    private String cityId;
    private String countyId;
    private String streetId = "";
    private String communityId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_delivery_address);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        choice_phone_layout = (LinearLayout) findViewById(R.id.choice_phone_layout);
        delivery_area_layout = (LinearLayout) findViewById(R.id.delivery_area_layout);
        ed_delivery_name = (EditText) findViewById(R.id.ed_delivery_name);
        ed_delivery_phone = (EditText) findViewById(R.id.ed_delivery_phone);
        tv_delivery_address = (TextView) findViewById(R.id.tv_delivery_address);
        ed_delivery_street = (EditText) findViewById(R.id.ed_delivery_street);
        btn_save = (Button) findViewById(R.id.btn_save);
        user_top_view_back.setOnClickListener(this);
        choice_phone_layout.setOnClickListener(this);
        delivery_area_layout.setOnClickListener(this);
        btn_save.setOnClickListener(this);
        Intent intent = getIntent();
        addressId = intent.getStringExtra(DeliveryAddressListActivity.DELIVERYID);
        if (TextUtils.isEmpty(addressId)) {
            user_top_view_title.setText(getResources().getString(R.string.title_add_deliveryAddress));
        } else {
            user_top_view_title.setText(getResources().getString(R.string.title_edit_deliveryAddress));
            deliveryName = intent.getStringExtra(DeliveryAddressIncreaseActivity.DELIVERYNAME);
            deliveryPhone = intent.getStringExtra(DeliveryAddressIncreaseActivity.DELIVERYPHONE);
            provinceId = intent.getStringExtra(DeliveryAddressIncreaseActivity.PROVINCEUUID);
            cityId = intent.getStringExtra(DeliveryAddressIncreaseActivity.CITYUUID);
            countyId = intent.getStringExtra(DeliveryAddressIncreaseActivity.COUNTYUUID);
            streetId = intent.getStringExtra(DeliveryAddressIncreaseActivity.TOWNUUID);
            communityId = intent.getStringExtra(DeliveryAddressIncreaseActivity.COMMUNITYUUID);
            deliveryAddress = intent.getStringExtra(DeliveryAddressIncreaseActivity.DELIVERYREGION);
            deliveryStreet = intent.getStringExtra(DeliveryAddressIncreaseActivity.DELIVERYADDRESS);
            ed_delivery_name.setText(deliveryName);
            ed_delivery_phone.setText(deliveryPhone);
            tv_delivery_address.setText(deliveryAddress);
            ed_delivery_street.setText(deliveryStreet);
        }
    }

    private DeliveryAddressDialog deliveryAddressDialog;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.choice_phone_layout:
                Intent intent = new Intent(DeliveryAddressIncreaseActivity.this, ContactsActivity.class);
                startActivityForResult(intent, 1000);
                break;
            case R.id.delivery_area_layout:
                if (null == deliveryAddressDialog) {
                    deliveryAddressDialog = new DeliveryAddressDialog(DeliveryAddressIncreaseActivity.this, this);
                }
                deliveryAddressDialog.show();
                break;
            case R.id.btn_save:
                if (fastClick()) {
                    deliveryName = ed_delivery_name.getText().toString().trim();
                    deliveryPhone = ed_delivery_phone.getText().toString().trim();
                    deliveryAddress = tv_delivery_address.getText().toString().trim();
                    deliveryStreet = ed_delivery_street.getText().toString().trim();
                    if (TextUtils.isEmpty(deliveryName)) {
                        ToastUtil.toastShow(DeliveryAddressIncreaseActivity.this, "收货人的姓名不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(deliveryPhone)) {
                        ToastUtil.toastShow(DeliveryAddressIncreaseActivity.this, "收货人的电话号码不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(deliveryAddress)) {
                        ToastUtil.toastShow(DeliveryAddressIncreaseActivity.this, "收货人的地区不能为空");
                        return;
                    }
                    if (TextUtils.isEmpty(deliveryStreet)) {
                        ToastUtil.toastShow(DeliveryAddressIncreaseActivity.this, "收货人的详细地址不能为空");
                        return;
                    }
                    DeliveryAddressModel deliveryAddressModel = new DeliveryAddressModel(DeliveryAddressIncreaseActivity.this);
                    if (TextUtils.isEmpty(addressId)) {
                        deliveryAddressModel.postDeliveryAddress(0, provinceId, cityId, countyId, streetId, communityId, deliveryStreet, deliveryName, deliveryPhone, "", this);
                    } else {
                        deliveryAddressModel.updateDeliveryAddress(0, addressId, provinceId, cityId, countyId, streetId, communityId, deliveryStreet, deliveryName, deliveryPhone, "", this);
                    }
                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 1002) {
            ed_delivery_name.setText(data.getStringExtra("name"));
            ed_delivery_phone.setText(Utils.trimTelNum(data.getStringExtra("mobile")));
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        if (TextUtils.isEmpty(addressId)) {
                            ToastUtil.toastShow(DeliveryAddressIncreaseActivity.this, "新增地址成功");
                        } else {
                            ToastUtil.toastShow(DeliveryAddressIncreaseActivity.this, "编辑地址成功");
                        }
                        setResult(200);
                        finish();
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }


    @Override
    public void onAddressSelected(String province, String city, String county, String street, String community) {
        provinceId = province.split(",")[1];
        cityId = city.split(",")[1];
        countyId = "";
        if (!TextUtils.isEmpty(county)) {
            countyId = county.split(",")[1];
        }
        streetId = "";
        communityId = "";
        String provinceName = province.split(",")[0];
        String cityName = city.split(",")[0];
        String countyName = county.split(",")[0];
        if (!TextUtils.isEmpty(community)) {
            streetId = street.split(",")[1];
            communityId = community.split(",")[1];
            String streetName = street.split(",")[0];
            String communityName = community.split(",")[0];
            tv_delivery_address.setText(provinceName + cityName + countyName + streetName + communityName);
        } else if (!TextUtils.isEmpty(street)) {
            streetId = street.split(",")[1];
            String streetName = street.split(",")[0];
            tv_delivery_address.setText(provinceName + cityName + countyName + streetName);
        } else {
            tv_delivery_address.setText(provinceName + cityName + countyName);
        }
    }
}
