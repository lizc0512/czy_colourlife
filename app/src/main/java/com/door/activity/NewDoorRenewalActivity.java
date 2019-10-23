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

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.view.pickview.OptionsPickerView;
import cn.net.cyberway.R;

import static com.customerInfo.activity.CustomerAddPropertyActivity.COMMUNITY_NAME;
import static com.customerInfo.activity.CustomerAddPropertyActivity.COMMUNITY_UUID;
import static com.customerInfo.activity.CustomerAddPropertyActivity.IDENTITY_ID;
/*
 * 门禁续期
 *
 * */

public class NewDoorRenewalActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, TextWatcher {
    public static final String DOOR_TYPE = "door_type";
    public static final String UNIT_NAME_LIST = "unit_name_list";
    public static final String UNIT_UUID_LIST = "unit_uuid_list";
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ClearEditText ed_real_name;
    private RelativeLayout apply_room_layout;
    private TextView tv_apply_room;
    private ImageView iv_apply_arrow;
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
    private String tgStatus = "1";
    private String auth_mobile = "";
    private String validate_phone = "";
    private ArrayList<String> unitNameList;
    private ArrayList<String> unitIdList;
    private String unit_name;
    private String unit_uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_applyaccess_one);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        ed_real_name = findViewById(R.id.ed_real_name);
        apply_room_layout = findViewById(R.id.apply_room_layout);
        tv_apply_room = findViewById(R.id.tv_apply_room);
        tv_apply_identify = findViewById(R.id.tv_apply_identify);
        iv_apply_arrow = findViewById(R.id.iv_apply_arrow);
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
        apply_room_layout.setOnClickListener(this::onClick);
        ed_validate_phone.addTextChangedListener(this);
        ed_authorize_phone.addTextChangedListener(this);
        newDoorAuthorModel = new NewDoorAuthorModel(NewDoorRenewalActivity.this);
        Intent intent = getIntent();
        community_uuid = intent.getStringExtra(COMMUNITY_UUID);
        community_name = intent.getStringExtra(COMMUNITY_NAME);
        identify_id = intent.getStringExtra(IDENTITY_ID);
        door_type = intent.getStringExtra(DOOR_TYPE);
        String identify_name;
        switch (identify_id) {
            case "1":
                identify_name = "业主  ";
                break;
            case "3":
                identify_name = "租客  ";
                break;
            case "4":
                identify_name = "访客  ";
                break;
            default:
                identify_id = "2";
                identify_name = "家属  ";
                break;
        }
        tv_apply_identify.setText(identify_name);
        newDoorAuthorModel.getDoorExtensionMsg(0, community_uuid, identify_id, NewDoorRenewalActivity.this);
        if ("1".equals(door_type)) {
            layout_register_phone.setVisibility(View.GONE);
            layout_validate_phone.setVisibility(View.GONE);
            ed_real_name.setFocusable(false);
            apply_room_layout.setEnabled(false);
            tv_apply_notice.setText("注：授权人通过您的申请后您即可获得开门权限，授权人账号可咨询小区物业管理处");
        } else {  //蓝牙门禁 的续期需要关联到单元信息
            unitNameList = intent.getStringArrayListExtra(UNIT_NAME_LIST);
            unitIdList = intent.getStringArrayListExtra(UNIT_UUID_LIST);
            apply_room_layout.setEnabled(true);
            if (unitNameList.size() > 1) {
                iv_apply_arrow.setVisibility(View.VISIBLE);
            } else {
                iv_apply_arrow.setVisibility(View.INVISIBLE);
            }
            if (!"1".equals(identify_id)) { //租客和家属
                layout_register_phone.setVisibility(View.GONE);
                layout_validate_phone.setVisibility(View.GONE);
                layout_authorize_phone.setVisibility(View.GONE);
                btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_bg);
                btn_submit_infor.setEnabled(true);
                tv_apply_notice.setText("注：业主或小区物业管理处工作人员通过您的申请后，您即可获得开门权限");
            }
            newDoorAuthorModel.bluetoothDoorVerify(1, community_uuid, unit_uuid, "", "", NewDoorRenewalActivity.this);
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
                    newDoorAuthorModel.setRemoteDoorExtensionValid(2, community_uuid, community_name, identify_id, bid, auth_mobile, NewDoorRenewalActivity.this);
                } else { //蓝牙门禁的续期
                    newDoorAuthorModel.setBluetoothDoorExtensionValid(2, community_uuid, community_name, unit_name, unit_uuid, identify_id, auth_mobile, name, validate_phone, tgStatus, NewDoorRenewalActivity.this);
                }
                break;
            case R.id.apply_room_layout: //蓝牙门禁续期  选择单元
                if (roomUnitList.size() > 1) {
                    showPickerView(roomUnitList, "授权小区单元");
                }
                break;
        }
    }

    private List<String> roomUnitList = new ArrayList<>();


    private void showPickerView(List<String> list, String title) {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                unit_name = unitNameList.get(options1);
                unit_uuid = unitIdList.get(options1);
                tv_apply_room.setText(roomUnitList.get(options1));
            }
        })
                .setTitleText(title)
                .setTitleColor(Color.parseColor("#81868F"))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.parseColor("#333333")) //设置选中项文字颜色
                .setCancelColor(Color.parseColor("#81868F"))
                .setSubmitColor(Color.parseColor("#0567FA"))
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(list);//一级选择器
        pvOptions.show();
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
                    if (!TextUtils.isEmpty(contentBean.getCommunity_name())) {
                        community_name = contentBean.getCommunity_name();
                    }
                    if ("1".equals(door_type)) {//远程门禁
                        tv_apply_room.setText(community_name);
                    } else {
                        if (null != unitNameList && unitNameList.size() > 0) {  //蓝牙门禁拼接上单元名称
                            unit_name = unitNameList.get(0);
                            unit_uuid = unitIdList.get(0);
                            tv_apply_room.setText(community_name + unit_name);
                            roomUnitList.clear();
                            for (int j = 0; j < unitNameList.size(); j++) {
                                roomUnitList.add(community_name + unitNameList.get(j));
                            }
                        } else {
                            tv_apply_room.setText(community_name);
                        }
                    }
                    ed_real_name.setText(name);
                    ed_real_name.setFocusable(false);
                } catch (Exception e) {
                    ToastUtil.toastShow(NewDoorRenewalActivity.this, e.getMessage());
                }
                break;
            case 1:
                try {
                    DoorBlueToothStatusEntity doorBlueToothStatusEntity = GsonUtils.gsonToBean(result, DoorBlueToothStatusEntity.class);
                    DoorBlueToothStatusEntity.ContentBean contentBean = doorBlueToothStatusEntity.getContent();
                    tgStatus = contentBean.getTgStatus();
                    tv_register_phone.setText(contentBean.getMobile());
                    if ("1".equals(identify_id)) { //业主
                        if ("0".equals(tgStatus)) { //RMS认证
                            setNoticeSpannString();
                            layout_authorize_phone.setVisibility(View.GONE);
                        } else { //物业认证
                            tv_apply_notice.setText("注：业主或小区物业管理处工作人员通过您的申请后，您即可获得开门权限");
                            layout_register_phone.setVisibility(View.GONE);
                            layout_validate_phone.setVisibility(View.GONE);
                            layout_authorize_phone.setVisibility(View.GONE);
                            btn_submit_infor.setBackgroundResource(R.drawable.onekey_login_bg);
                            btn_submit_infor.setEnabled(true);
                        }
                    }
                } catch (Exception e) {

                }
                break;
            case 2:
                if ("0".equals(tgStatus)) {
                    ToastUtil.toastShow(NewDoorRenewalActivity.this, "审核通过，自动发放钥匙");
                } else {
                    ToastUtil.toastShow(NewDoorRenewalActivity.this, "续期申请已提交，请等待审核通过");
                }
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
            if ("1".equals(identify_id)&&"0".equals(tgStatus)) {
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
