package com.door.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.view.ClearEditText;
import com.eparking.helper.PermissionUtils;

import cn.net.cyberway.R;
/*
 * 申请远程门禁和蓝牙门禁
 *
 * */

public class NewDoorApplyActivity extends BaseActivity implements View.OnClickListener {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ClearEditText ed_real_name;
    private TextView tv_apply_room;
    private TextView tv_apply_identify;
    private TextView tv_register_phone;
    private ClearEditText ed_validate_phone;
    private ClearEditText ed_authorize_phone;
    private TextView tv_apply_notice;
    private Button btn_submit_infor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_applyaccess_one);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        ed_real_name = findViewById(R.id.ed_real_name);
        tv_apply_room = findViewById(R.id.tv_apply_room);
        tv_apply_identify = findViewById(R.id.tv_apply_identify);
        tv_register_phone = findViewById(R.id.tv_register_phone);
        ed_validate_phone = findViewById(R.id.ed_validate_phone);
        ed_authorize_phone = findViewById(R.id.ed_authorize_phone);
        tv_apply_notice = findViewById(R.id.tv_apply_notice);
        btn_submit_infor = findViewById(R.id.btn_submit_infor);
        btn_submit_infor.setOnClickListener(this::onClick);
        StringBuffer stringBuffer = new StringBuffer();
        String door_notice_one = getResources().getString(R.string.door_notice_one);
        String door_notice_hotLine = getResources().getString(R.string.door_notice_hotline);
        String door_notice_two = getResources().getString(R.string.door_notice_two);
        stringBuffer.append(door_notice_one);
        stringBuffer.append(door_notice_hotLine);
        stringBuffer.append(door_notice_two);
        SpannableString spannableString = new SpannableString(stringBuffer.toString());
        int middleLength = door_notice_one.length() + door_notice_hotLine.length();
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#a9afb8")), 0, door_notice_one.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#a9afb8")), middleLength, stringBuffer.toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
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
        tv_apply_notice.setMovementMethod(LinkMovementMethod.getInstance());
        tv_apply_notice.setText(spannableString);
        user_top_view_title.setText("申请开门");
        user_top_view_back.setOnClickListener(this::onClick);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_submit_infor:

                break;
        }
    }
}
