package com.customerInfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.adapter.DeliveryAddressListAdapter;
import com.customerInfo.model.DeliveryAddressModel;
import com.customerInfo.protocol.DeliveryAddressListEnity;
import com.nohttp.utils.GsonUtils;
import com.setting.activity.DeleteAddressDialog;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/7 16:27
 * @change
 * @chang time
 * @class describe   收货地址列表
 */

public class DeliveryAddressListActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String DELIVERYID = "deliveryid";
    public static final String DELIVERYINFOR = "deliveryinfor";
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private Button btn_increase_address;
    private RecyclerView rv_address_list;
    private DeliveryAddressModel deliveryAddressModel;
    private DeliveryAddressListAdapter deliveryAddressListAdapter;
    private List<DeliveryAddressListEnity.ContentBean> deliveryAddressList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_address_list);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        btn_increase_address = (Button) findViewById(R.id.btn_increase_address);
        rv_address_list = (RecyclerView) findViewById(R.id.rv_address_list);
        btn_increase_address.setOnClickListener(this);
        user_top_view_back.setOnClickListener(this);
        user_top_view_title.setText(getResources().getString(R.string.title_manager_deliveryAddress));
        btn_increase_address.setOnClickListener(this);
        deliveryAddressModel = new DeliveryAddressModel(this);
        rv_address_list.setLayoutManager(new LinearLayoutManager(DeliveryAddressListActivity.this));// 布局管理器。
        deliveryAddressListAdapter = new DeliveryAddressListAdapter(this, deliveryAddressList);
        rv_address_list.setAdapter(deliveryAddressListAdapter);
        getDeliveryList();
    }

    private void getDeliveryList() {
        deliveryAddressModel.getDeliveryAddressList(0, this);
    }

    public void getSingleDeliveryAddressInfor(String addressId) {
        deliveryAddressModel.getDeliverySingleAddress(1, addressId, this);
    }

    private String delAddressId = "";

    public void delSingleDeliveryAddress(final String addressId) {
        final DeleteAddressDialog deleteAddressDialog = new DeleteAddressDialog(DeliveryAddressListActivity.this, R.style.dialog);
        deleteAddressDialog.show();
        deleteAddressDialog.btn_define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAddressDialog.dismiss();
                delAddressId = addressId;
                deliveryAddressModel.deleteDeliveryAddress(2, addressId, DeliveryAddressListActivity.this);
            }
        });
        deleteAddressDialog.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteAddressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            getDeliveryList();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_increase_address:
                Intent intent = new Intent(DeliveryAddressListActivity.this, DeliveryAddressIncreaseActivity.class);
                startActivityForResult(intent, 1000);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        DeliveryAddressListEnity deliveryAddressListEnity = GsonUtils.gsonToBean(result, DeliveryAddressListEnity.class);
                        deliveryAddressList.clear();
                        deliveryAddressList.addAll(deliveryAddressListEnity.getContent());
                        deliveryAddressListAdapter.notifyDataSetChanged();
                    } catch (Exception e) {

                    }
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String deliveryInfor = jsonObject.optString("content");
                        Intent intent = new Intent();
                        intent.putExtra(DELIVERYINFOR, deliveryInfor);
                        setResult(200, intent);
                        finish();
                    } catch (Exception e) {

                    }
                }
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        for (DeliveryAddressListEnity.ContentBean contentBean : deliveryAddressList) {
                            if (delAddressId.equals(contentBean.getAddress_uuid())) {
                                deliveryAddressList.remove(contentBean);
                                break;
                            }
                        }
                        ToastUtil.toastShow(DeliveryAddressListActivity.this, "地址删除成功!");
                        deliveryAddressListAdapter.notifyDataSetChanged();
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }
}
