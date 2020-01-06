package com.user.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.appsafekb.safekeyboard.NKeyBoardTextField;
import com.appsafekb.safekeyboard.values.ValueFactory;
import com.user.UserAppConst;
import com.user.model.NewUserModel;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.ConfigUtils;

/**
 * @name ${yuansk}
 * @class name：com.user.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/27 14:15
 * @change
 * @chang time
 * @class describe   用户忘记密码,输入密码进行重置密码
 */

public class UserForgetPasswordActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_forget_number;
    private TextView tv_contact_service;
    private NKeyBoardTextField ed_new_pawd;
    private Button btn_finish;
    private NewUserModel newUserModel;
    private String mobile;
    private String password;
    private String code;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgetpawd_layout);
        initView();
        Intent intent = getIntent();
        mobile = intent.getStringExtra(UserRegisterAndLoginActivity.MOBILE);
        code = intent.getStringExtra(UserRegisterAndLoginActivity.SMSCODE);
        tv_forget_number.setText(mobile);
        tv_forget_number.setVisibility(View.GONE);
        newUserModel = new NewUserModel(this);
    }

    private void initView() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_forget_number = (TextView) findViewById(R.id.tv_forget_number);
        ed_new_pawd =  findViewById(R.id.ed_new_pawd);
        tv_contact_service = findViewById(R.id.tv_contact_service);
        btn_finish = (Button) findViewById(R.id.btn_finish);
        findViewById(R.id.line).setVisibility(View.GONE);
        btn_finish.setEnabled(false);
        user_top_view_back.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        tv_contact_service.setOnClickListener(this);

        ed_new_pawd.setNkeyboardType(0);
        ed_new_pawd.setNKeyboardRandom(ValueFactory.buildAllTrue());
        ed_new_pawd.setEditClearIcon(true);
        ed_new_pawd.setNKeyboardKeyEncryption(false);
        ed_new_pawd.clearNkeyboard();
        ed_new_pawd.showNKeyboard();

        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, user_top_view_back, user_top_view_title);
        ed_new_pawd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                password = ed_new_pawd.getNKeyboardText();
                if (TextUtils.isEmpty(password)) {
                    btn_finish.setEnabled(false);
                    btn_finish.setBackgroundResource(R.drawable.onekey_login_default_bg);
                } else {
                    btn_finish.setEnabled(true);
                    btn_finish.setBackgroundResource(R.drawable.onekey_login_bg);
                }
            }
        });

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_finish:
                if (fastClick()) {
                    if (TextUtils.isEmpty(password)) {
                        password = ed_new_pawd.getNKeyboardText();
                    }
                    newUserModel.resetUserPassword(0, mobile, code, password, this);
                    ed_new_pawd.hideNKeyboard();
                }
                break;
            case R.id.tv_contact_service:
                ConfigUtils.jumpContactService(UserForgetPasswordActivity.this);
                break;
        }
    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0://密码成功跳转到登录页面
                ToastUtil.toastShow(this, getResources().getString(R.string.user_pswd_resetsuccess));
                Intent intent = new Intent(UserForgetPasswordActivity.this, UserRegisterAndLoginActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
