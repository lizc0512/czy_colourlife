package com.door.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.door.entity.DoorBlueToothStatusEntity;
import com.door.entity.DoorSupportTypeEntity;
import com.door.model.NewDoorAuthorModel;
import com.eparking.helper.PermissionUtils;
import com.nohttp.utils.GsonUtils;

import cn.net.cyberway.R;

import static com.customerInfo.activity.CustomerAddPropertyActivity.BUILD_NAME;
import static com.customerInfo.activity.CustomerAddPropertyActivity.BUILD_UUID;
import static com.customerInfo.activity.CustomerAddPropertyActivity.COMMUNITY_NAME;
import static com.customerInfo.activity.CustomerAddPropertyActivity.COMMUNITY_UUID;
import static com.customerInfo.activity.CustomerAddPropertyActivity.IDENTITY_ID;
import static com.customerInfo.activity.CustomerAddPropertyActivity.IDENTITY_NAME;
import static com.customerInfo.activity.CustomerAddPropertyActivity.ROOM_NAME;
import static com.customerInfo.activity.CustomerAddPropertyActivity.ROOM_UUID;
import static com.customerInfo.activity.CustomerAddPropertyActivity.UNIT_NAME;
import static com.customerInfo.activity.CustomerAddPropertyActivity.UNIT_UUID;
/*
 * 申请远程门禁和蓝牙门禁
 *
 * */

public class NewDoorApplyActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, TextWatcher {
    public static final String DOORSUPPORTTYPE = "doorsupporttype";
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ClearEditText ed_real_name;
    private TextView tv_apply_room;
    private TextView tv_apply_identify;
    private RelativeLayout layout_register_phone;
    private TextView tv_register_phone;
    private RelativeLayout layout_validate_phone;
    private ClearEditText ed_validate_phone;
    private RelativeLayout layout_authorize_phone;
    private ClearEditText ed_authorize_phone;
    private TextView tv_apply_notice;
    private Button btn_submit_infor;
    private NewDoorAuthorModel newDoorAuthorModel;
    private String doorType = "";
    private String tgStatus = "";
    private String bid = "";
    private String community_uuid = "";
    private String community_name = "";
    private String build_uuid = "";
    private String buid_name = "";
    private String unit_uuid = "";
    private String unit_name = "";
    private String room_uuid = "";
    private String room_name = "";
    private String identity_id = "";
    private String identity_name = "";
    private String auth_name = ""; //用户的姓名
    private String auth_mobile = "";//远程门禁授权人的手机号

    private String validate_phone = "";//蓝牙门禁授权人手机号


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_applyaccess_one);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        ed_real_name = findViewById(R.id.ed_real_name);
        tv_apply_room = findViewById(R.id.tv_apply_room);
        tv_apply_identify = findViewById(R.id.tv_apply_identify);
        layout_register_phone = findViewById(R.id.layout_register_phone);
        tv_register_phone = findViewById(R.id.tv_register_phone);
        layout_validate_phone = findViewById(R.id.layout_validate_phone);
        ed_validate_phone = findViewById(R.id.ed_validate_phone);
        layout_authorize_phone = findViewById(R.id.layout_authorize_phone);
        ed_authorize_phone = findViewById(R.id.ed_authorize_phone);
        tv_apply_notice = findViewById(R.id.tv_apply_notice);
        btn_submit_infor = findViewById(R.id.btn_submit_infor);
        btn_submit_infor.setOnClickListener(this::onClick);
        user_top_view_title.setText("申请开门");
        user_top_view_back.setOnClickListener(this::onClick);
        ed_real_name.addTextChangedListener(this);
        ed_validate_phone.addTextChangedListener(this);
        ed_authorize_phone.addTextChangedListener(this);
        newDoorAuthorModel = new NewDoorAuthorModel(NewDoorApplyActivity.this);
        Intent intent = getIntent();
        DoorSupportTypeEntity.ContentBean contentBean = (DoorSupportTypeEntity.ContentBean) intent.getSerializableExtra(DOORSUPPORTTYPE);
        String remote_door = contentBean.getRemote_door();
        String bluetooth_door = contentBean.getBluetooth_door();
        bid = contentBean.getBid();
        identity_id = intent.getStringExtra(IDENTITY_ID);
        identity_name = intent.getStringExtra(IDENTITY_NAME);
        community_uuid = intent.getStringExtra(COMMUNITY_UUID);
        community_name = intent.getStringExtra(COMMUNITY_NAME);
        build_uuid = intent.getStringExtra(BUILD_UUID);
        buid_name = intent.getStringExtra(BUILD_NAME);
        unit_uuid = intent.getStringExtra(UNIT_UUID);
        unit_name = intent.getStringExtra(UNIT_NAME);
        room_uuid = intent.getStringExtra(ROOM_UUID);
        room_name = intent.getStringExtra(ROOM_NAME);
        if ("1".equals(remote_door)) {
            if ("1".equals(bluetooth_door)) {
                doorType = "3";
            } else {
                doorType = "1";
            }
        } else {
            if ("1".equals(bluetooth_door)) {
                doorType = "2";
            } else {
                ToastUtil.toastShow(NewDoorApplyActivity.this, "此小区目前没有彩之云门禁，无法申请");
                finish();
            }
        }
        layout_register_phone.setVisibility(View.GONE);
        layout_validate_phone.setVisibility(View.GONE);
        if ("1".equals(identity_id)) { //业主
            switch (doorType) {
                case "1"://只有远程门禁
                    ed_authorize_phone.setHint("请输入授权人的手机号码");
                    tv_apply_notice.setText("注：授权人通过您的申请后您即可获得开门权限，授权人账号可咨询小区物业管理处");
                    break;
                case "2"://只有蓝牙门禁
                    layout_authorize_phone.setVisibility(View.GONE);
                    newDoorAuthorModel.bluetoothDoorVerify(0, community_uuid, unit_uuid, build_uuid, room_uuid, NewDoorApplyActivity.this);
                    break;
                default://远程门禁和蓝牙门禁都存在
                    setNoticeSpannString(1);
                    newDoorAuthorModel.bluetoothDoorVerify(0, community_uuid, unit_uuid, build_uuid, room_uuid, NewDoorApplyActivity.this);
                    break;
            }
        } else { //家属和租户
            switch (doorType) {
                case "1"://只有远程门禁
                    ed_authorize_phone.setHint("请输入授权人的手机号码");
                    tv_apply_notice.setText("注：授权人通过您的申请后您即可获得开门权限，授权人账号可咨询小区物业管理处");
                    break;
                case "2"://只有蓝牙门禁
                    layout_authorize_phone.setVisibility(View.GONE);
                    tv_apply_notice.setText("注：业主或小区物业管理处工作人员通过您的申请后，您即可获得开门权限");
                    newDoorAuthorModel.bluetoothDoorVerify(0, community_uuid, unit_uuid, build_uuid, room_uuid, NewDoorApplyActivity.this);
                    break;
                default://远程门禁和蓝牙门禁都存在
                    setNoticeSpannString(1);
                    newDoorAuthorModel.bluetoothDoorVerify(0, community_uuid, unit_uuid, build_uuid, room_uuid, NewDoorApplyActivity.this);
                    break;
            }
        }
        tv_apply_room.setText(community_name + buid_name + unit_name + room_name);
        tv_apply_identify.setText(identity_name);
    }

    private void setNoticeSpannString(int type) {
        StringBuffer stringBuffer = new StringBuffer();
        String door_notice_one = getResources().getString(R.string.door_notice_one);
        String door_notice_hotLine = getResources().getString(R.string.door_notice_hotline);
        stringBuffer.append(door_notice_one);
        stringBuffer.append(door_notice_hotLine);
        if (type == 1) {
            String door_notice_two = getResources().getString(R.string.door_notice_two);
            stringBuffer.append(door_notice_two);
        }
        SpannableString spannableString = new SpannableString(stringBuffer.toString());
        int middleLength = door_notice_one.length() + door_notice_hotLine.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#a9afb8")), 0, door_notice_one.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                PermissionUtils.showPhonePermission(NewDoorApplyActivity.this);
            }

            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#0567FA"));
                ds.setUnderlineText(false);
            }
        }, door_notice_one.length(), middleLength, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        if (type == 1) {
            spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#a9afb8")), middleLength, stringBuffer.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        }
        tv_apply_notice.setMovementMethod(LinkMovementMethod.getInstance());
        tv_apply_notice.setText(spannableString);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_submit_infor:
                if (TextUtils.isEmpty(auth_mobile)) {
                    doorType = "2";
                }
                newDoorAuthorModel.applyRemoteDoor(1, community_uuid, community_name, build_uuid, buid_name,
                        unit_uuid, unit_name, room_uuid, room_name, identity_id, auth_name, auth_mobile, validate_phone, doorType, bid, tgStatus, NewDoorApplyActivity.this);

                break;
        }
    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    DoorBlueToothStatusEntity doorBlueToothStatusEntity = GsonUtils.gsonToBean(result, DoorBlueToothStatusEntity.class);
                    DoorBlueToothStatusEntity.ContentBean contentBean = doorBlueToothStatusEntity.getContent();
                    tgStatus = contentBean.getTgStatus();
                    if ("1".equals(identity_id)) {  //身份为业主
                        if ("2".equals(doorType)) { //只有蓝牙门禁
                            if ("0".equals(tgStatus)) { //开启了RMS认证 不进行物业审核
                                setNoticeSpannString(0);
                                layout_register_phone.setVisibility(View.VISIBLE);
                                layout_validate_phone.setVisibility(View.VISIBLE);
                                tv_register_phone.setText(contentBean.getMobile());
                            } else {  //物业审核
                                tv_apply_notice.setText("注：小区物业管理处工作人员通过您的申请后，您即可获得开门权限");
                            }
                        } else if ("3".equals(doorType)) {//远程和蓝牙门禁都有
                            if ("0".equals(tgStatus)) {  //RMS认证
                                layout_register_phone.setVisibility(View.VISIBLE);
                                layout_validate_phone.setVisibility(View.VISIBLE);
                                tv_register_phone.setText(contentBean.getMobile());
                            }
                        }
                    }
                } catch (Exception e) {

                }
                break;
            case 1:
                if ("2".equals(doorType)) {
                    if ("0".equals(tgStatus)) {
                        ToastUtil.toastShow(NewDoorApplyActivity.this, "审核通过，自动发放钥匙");
                    } else {
                        ToastUtil.toastShow(NewDoorApplyActivity.this, "申请已提交，请等待审核通过");
                    }
                } else {
                    ToastUtil.toastShow(NewDoorApplyActivity.this, "申请已提交，请等待审核通过");
                }
                setResult(200);
                finish();
                break;
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        auth_mobile = ed_authorize_phone.getText().toString().trim();
        auth_name = ed_real_name.getText().toString().trim();
        validate_phone = ed_validate_phone.getText().toString().trim();
        setSubmitBtn();
    }

    private void setRomoteStatus() {  //远程开门
        if (TextUtils.isEmpty(auth_mobile) || TextUtils.isEmpty(auth_name) || 11 != auth_mobile.length()) {
            btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_default_bg);
            btn_submit_infor.setEnabled(false);
        } else {
            btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_bg);
            btn_submit_infor.setEnabled(true);
        }
    }

    private void setBothStatus() { //蓝牙和远程都存在
        if (TextUtils.isEmpty(auth_name)) {
            btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_default_bg);
            btn_submit_infor.setEnabled(false);
        } else {
            btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_bg);
            btn_submit_infor.setEnabled(true);
        }
    }


    private void setSubmitBtn() {
        if ("0".equals(identity_id)) { //业主
            switch (doorType) {
                case "1"://只有远程门禁
                    setRomoteStatus();
                    break;
                case "2"://只有蓝牙门禁
                    if ("0".equals(tgStatus)) {
                        if (TextUtils.isEmpty(validate_phone) || 11 != validate_phone.length()) {
                            btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_default_bg);
                            btn_submit_infor.setEnabled(false);
                        } else {
                            btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_bg);
                            btn_submit_infor.setEnabled(true);
                        }
                    } else {
                        setBothStatus();
                    }
                    break;
                default://远程门禁和蓝牙门禁都存在
                    if ("0".equals(tgStatus)) {
                        if (TextUtils.isEmpty(validate_phone) || 11 != validate_phone.length() || TextUtils.isEmpty(auth_name)) {
                            btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_default_bg);
                            btn_submit_infor.setEnabled(false);
                        } else {
                            btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_bg);
                            btn_submit_infor.setEnabled(true);
                        }
                    } else {
                        setBothStatus();
                    }
                    break;
            }
        } else {
            switch (doorType) {
                case "1"://只有远程门禁
                    setRomoteStatus();
                    break;
                default://远程门禁和蓝牙门禁都存在
                    setBothStatus();
                    break;
            }
        }
    }
}
