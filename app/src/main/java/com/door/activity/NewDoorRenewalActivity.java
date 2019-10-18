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
import com.door.entity.DoorExtensionMsgEntity;
import com.door.model.NewDoorAuthorModel;
import com.eparking.helper.PermissionUtils;
import com.nohttp.utils.GsonUtils;

import cn.net.cyberway.R;

import static com.customerInfo.activity.CustomerAddPropertyActivity.COMMUNITY_UUID;
import static com.customerInfo.activity.CustomerAddPropertyActivity.IDENTITY_ID;
/*
 * 门禁续期
 *
 * */

public class NewDoorRenewalActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, TextWatcher {
    public static final String DOOR_TYPE = "door_type";
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
    private String community_uuid;
    private String community_name;
    private String identify_id;
    private String name;
    private String door_type;
    private String bid;
    private String tgStatus;
    private String auth_mobile = "";
    private String validate_phone = "";


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
        user_top_view_title.setText("申请续期");
        user_top_view_back.setOnClickListener(this::onClick);
        newDoorAuthorModel = new NewDoorAuthorModel(NewDoorRenewalActivity.this);
        Intent intent = getIntent();
        community_uuid = intent.getStringExtra(COMMUNITY_UUID);
        identify_id = intent.getStringExtra(IDENTITY_ID);
        door_type = intent.getStringExtra(DOOR_TYPE);
        newDoorAuthorModel.getDoorExtensionMsg(0, community_uuid, identify_id, NewDoorRenewalActivity.this);
        if ("1".equals(door_type)) {
            layout_register_phone.setVisibility(View.GONE);
            layout_validate_phone.setVisibility(View.GONE);
            ed_real_name.setFocusable(false);
            tv_apply_notice.setText("注：授权人通过您的申请后您即可获得开门权限，授权人账号可咨询小区物业管理处");
        } else {
            if ("1".equals(identify_id)) { //租客和家属
                layout_register_phone.setVisibility(View.GONE);
                layout_validate_phone.setVisibility(View.GONE);
                layout_authorize_phone.setVisibility(View.GONE);
                btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_bg);
                btn_submit_infor.setEnabled(true);
                tv_apply_notice.setText("注：业主或小区物业管理处工作人员通过您的申请后，您即可获得开门权限");
            } else {  //业主
                newDoorAuthorModel.bluetoothDoorVerify(1, community_uuid, NewDoorRenewalActivity.this);
            }
        }
    }

    private void setNoticeSpannString() {
        StringBuffer stringBuffer = new StringBuffer();
        String door_notice_one = getResources().getString(R.string.door_notice_one);
        String door_notice_hotLine = getResources().getString(R.string.door_notice_hotline);
        stringBuffer.append(door_notice_one);
        stringBuffer.append(door_notice_hotLine);
        SpannableString spannableString = new SpannableString(stringBuffer.toString());
        int middleLength = door_notice_one.length() + door_notice_hotLine.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#a9afb8")), 0, door_notice_one.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                PermissionUtils.showPhonePermission(NewDoorRenewalActivity.this);
            }

            public void updateDrawState(@NonNull TextPaint ds) {
                ds.setColor(Color.parseColor("#0567FA"));
                ds.setUnderlineText(false);
            }
        }, door_notice_one.length(), middleLength, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
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
                if ("1".equals(door_type)) {  //远程门禁的续期
                    newDoorAuthorModel.setDoorExtensionValid(2, community_uuid, community_name, identify_id, bid, auth_mobile, NewDoorRenewalActivity.this);
                } else {

                }
                break;
        }
    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    DoorExtensionMsgEntity doorExtensionMsgEntity = GsonUtils.gsonToBean(result, DoorExtensionMsgEntity.class);
                    DoorExtensionMsgEntity.ContentBean contentBean = doorExtensionMsgEntity.getContent();
                    name = contentBean.getName();
                    bid = contentBean.getBid();
                    community_name = contentBean.getCommunity_name();
                    tv_apply_room.setText(community_name);
                    ed_real_name.setText(name);
                    ed_real_name.setFocusable(false);
                } catch (Exception e) {

                }
                break;
            case 1:
                try {
                    DoorBlueToothStatusEntity doorBlueToothStatusEntity = GsonUtils.gsonToBean(result, DoorBlueToothStatusEntity.class);
                    DoorBlueToothStatusEntity.ContentBean contentBean = doorBlueToothStatusEntity.getContent();
                    tgStatus = contentBean.getTgStatus();
                    if ("1".equals(tgStatus)) { //开启认证
                        setNoticeSpannString();
                        layout_authorize_phone.setVisibility(View.GONE);
                    } else {
                        layout_register_phone.setVisibility(View.GONE);
                        layout_validate_phone.setVisibility(View.GONE);
                        layout_authorize_phone.setVisibility(View.GONE);
                    }
                } catch (Exception e) {

                }
                break;
            case 2:
                ToastUtil.toastShow(NewDoorRenewalActivity.this, "续期申请已提交，请等待审核通过");
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
        validate_phone = ed_validate_phone.getText().toString().trim();
        if ("1".equals(door_type)) { //远程门禁
            if (TextUtils.isEmpty(auth_mobile) || 11 != auth_mobile.length()) {
                btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_default_bg);
                btn_submit_infor.setEnabled(false);
            } else {
                btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_bg);
                btn_submit_infor.setEnabled(true);
            }
        } else {
            if (!"1".equals(identify_id)) {
                if (TextUtils.isEmpty(validate_phone) || 11 != validate_phone.length()) {
                    btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_default_bg);
                    btn_submit_infor.setEnabled(false);
                } else {
                    btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_bg);
                    btn_submit_infor.setEnabled(true);
                }
            }
        }
    }
}
