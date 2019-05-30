package com.setting.activity;

import android.content.Intent;
import android.content.IntentFilter;
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
import com.gesturepwd.activity.CreateGesturePasswordActivity;
import com.smileback.bankcommunicationsstyle.BCSIJMInputEditText;
import com.user.UserAppConst;
import com.user.model.NewUserModel;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.BroadcastReceiverActivity;

/**
 * 忘记手势密码，验证登录密码界面
 * Created by chenql on 16/3/3.
 */
public class VerifyLoginPwdActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String PAWDTYPE = "pawdtype";
    private BCSIJMInputEditText edt_pwd;// 密码输入框
    private NewUserModel newUserModel;
    private Button btn_verify;
    private BroadcastReceiverActivity broadcast;
    private String pawd;
    private int passwordType = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_login_pwd);
        initTopView();
        initData();
    }

    private void initData() {
        newUserModel = new NewUserModel(this);
    }


    private void initTopView() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        ImageView mBack = (ImageView) findViewById(R.id.user_top_view_back);
        TextView mTitle = (TextView) findViewById(R.id.user_top_view_title);
        btn_verify = (Button) findViewById(R.id.btn_verify);
        edt_pwd = (BCSIJMInputEditText) findViewById(R.id.edt_pwd);
        edt_pwd.setKeyboardNoRandom(true);
        edt_pwd.setNKeyboardKeyBg(true);
        edt_pwd.setNkeyboardEject(true);
        edt_pwd.setNlicenseKey(UserAppConst.IJIAMINLICENSEKEY);
        passwordType = getIntent().getIntExtra(PAWDTYPE, 0);
        if (passwordType == 0) {
            edt_pwd.setHint(getResources().getString(R.string.user_set_pawd));
            mTitle.setText(getResources().getString(R.string.set_login_pwd));
            btn_verify.setText(getResources().getString(R.string.message_define));
        } else {
            mTitle.setText(getResources().getString(R.string.verify_login_pwd));
            btn_verify.setText(getResources().getString(R.string.verify));
        }
        mBack.setOnClickListener(this);
        btn_verify.setOnClickListener(this);
        edt_pwd.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                pawd = edt_pwd.getNKeyboardText();
                if (!TextUtils.isEmpty(pawd)) {
                    btn_verify.setEnabled(true);
                    btn_verify.setBackgroundResource(R.drawable.rect_round_blue);
                } else {
                    btn_verify.setEnabled(false);
                    btn_verify.setBackgroundResource(R.drawable.rect_round_gray);
                }
            }
        });
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, mBack, mTitle);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_verify:
                if (passwordType == 1) {
                    newUserModel.verifyPassword(0, pawd, this);
                } else {
                    //短信登录未设置登录密码去设置登录密码
                    newUserModel.setLoginPassword(1, pawd, this);
                }
                edt_pwd.hideNKeyboard();
                break;
        }
    }

    @Override
    protected void onResume() {
        //注册广播
        broadcast = new BroadcastReceiverActivity(this);
        IntentFilter intentFilter = new IntentFilter(BroadcastReceiverActivity.GESTURE);
        registerReceiver(broadcast, intentFilter);
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        //销毁广播注册
        if (broadcast != null) {
            unregisterReceiver(broadcast);
            broadcast = null;
        }
        super.onDestroy();
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                Intent intent = new Intent(VerifyLoginPwdActivity.this, CreateGesturePasswordActivity.class);
                intent.putExtra("isChangePwd", false);// 是否为修改手势密码
                startActivity(intent);
                finish();
                break;
            case 1:
                editor.putString(UserAppConst.Colour_set_password, "1");
                editor.apply();
                ToastUtil.toastShow(VerifyLoginPwdActivity.this, "登录密码设置成功");
                setResult(200);
                finish();
                break;
        }
    }
}
