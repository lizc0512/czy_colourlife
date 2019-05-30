package com.setting.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.Constants;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.activity.CustomerCheckCodeActivity;
import com.gesturepwd.activity.CreateGesturePasswordActivity;
import com.nohttp.utils.GsonUtils;
import com.setting.switchButton.SwitchButton;
import com.user.UserAppConst;
import com.user.entity.IsGestureEntity;
import com.user.entity.SwitchGestureEntity;
import com.user.model.NewUserModel;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.BroadcastReceiverActivity;

import static com.setting.activity.VerifyLoginPwdActivity.PAWDTYPE;

/**
 * 手势密码主界面
 * Created by chenql on 16/3/2.
 */
public class GesturePwdMainActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private SwitchButton switch_gesture;

    /**
     * 手势密码三种状态
     * See {@link com.BeeFramework.model.Constants#GESTURE_PWD_UNSET}
     */
    private String gesturePwdStatus = "";// 服务器返回手势密码状态
    private NewUserModel newUserModel;
    private BroadcastReceiverActivity broadcast;
    private int switchStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gesture_pwd_main);

        initTopView();

        initView();

        initData();
    }

    private void initData() {
        newUserModel = new NewUserModel(this);
    }

    private void initView() {
        setupSwitchGesture();

        findViewById(R.id.tv_change_gesture_pwd).setOnClickListener(this);
        findViewById(R.id.tv_forget_gesture_pwd).setOnClickListener(this);

        hideGesturePwdOptions();// 默认隐藏 修改手势密码、忘记手势密码选项
    }

    private void setupSwitchGesture() {
        switch_gesture = (SwitchButton) findViewById(R.id.switch_gesture);

        switch_gesture.setOnStateChangedListener(new SwitchButton.OnStateChangedListener() {
            @Override
            public void toggleToOn(View view) {
                if (Constants.GESTURE_PWD_UNSET.equals(gesturePwdStatus)) {
                    // 未设置手势密码
                    jumpToSetGesturePwd();
                } else {
                    // 由关改开，调用打开手势密码接口
                    switch_gesture.setOpened(true);
                    showGesturePwdOptions();
                    switchStatus = 1;
                    newUserModel.switchGesture(0, switchStatus, GesturePwdMainActivity.this);
                }
            }

            @Override
            public void toggleToOff(View view) {
                switch_gesture.setOpened(false);
                hideGesturePwdOptions();
                switchStatus = 2;
                newUserModel.switchGesture(0, switchStatus, GesturePwdMainActivity.this);
            }
        });
    }

    private void initGestureStatus() {

        if (Constants.GESTURE_PWD_SET_AND_ENABLED.equals(gesturePwdStatus)) {
            // 已设置手势密码且开启才开启
            switch_gesture.setOpened(true);
            showGesturePwdOptions();
        } else {
            // 其他情况默认关闭
            switch_gesture.setOpened(false);
            hideGesturePwdOptions();
        }
    }

    @Override
    protected void onResume() {
        //注册广播
        broadcast = new BroadcastReceiverActivity(this);
        IntentFilter intentFilter = new IntentFilter(BroadcastReceiverActivity.GESTURE);
        registerReceiver(broadcast, intentFilter);
        initGestureStatus();
        newUserModel.isSetGesture(1, shared.getString(UserAppConst.Colour_login_mobile, ""), GesturePwdMainActivity.this);
        super.onResume();
    }

    /**
     * 跳转去设置手势密码
     */
    private void jumpToSetGesturePwd() {
        Intent intent = new Intent(GesturePwdMainActivity.this, CreateGesturePasswordActivity.class);
        intent.putExtra("isChangePwd", false);
        startActivity(intent);
    }

    /**
     * 隐藏 修改手势密码、忘记手势密码选项
     */
    private void hideGesturePwdOptions() {
        findViewById(R.id.ll_change_gesture_pwd).setVisibility(View.GONE);
        findViewById(R.id.ll_forget_gesture_pwd).setVisibility(View.GONE);
    }

    /**
     * 显示 修改手势密码、忘记手势密码选项
     */
    private void showGesturePwdOptions() {
        findViewById(R.id.ll_change_gesture_pwd).setVisibility(View.VISIBLE);
        findViewById(R.id.ll_forget_gesture_pwd).setVisibility(View.VISIBLE);
    }

    /**
     * 保存手势密码开关状态
     *
     * @param gesturePwdStatus String
     */
    private void saveGesturePwdStatus(String gesturePwdStatus) {
        this.gesturePwdStatus = gesturePwdStatus;
        editor.putString(UserAppConst.Colour_login_mobile + UserAppConst.GESTURE_OPENED, gesturePwdStatus);
        editor.commit();
    }

    private void initTopView() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        TextView user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        user_top_view_title.setText(R.string.gesture_pwd);
        ImageView user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, user_top_view_back, user_top_view_title);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.tv_change_gesture_pwd:
                // 修改手势密码
                intent.setClass(GesturePwdMainActivity.this, CreateGesturePasswordActivity.class);
                intent.putExtra("isChangePwd", true);// 是修改手势密码
                startActivity(intent);
                break;

            case R.id.tv_forget_gesture_pwd:
                // 忘记手势密码  1设置密码去验证登录密码  2未设置登录密码 去短信验证
                String setPawd = shared.getString(UserAppConst.Colour_set_password, "1");
                if ("1".equals(setPawd)) {
                    intent.setClass(GesturePwdMainActivity.this, VerifyLoginPwdActivity.class);
                    intent.putExtra(PAWDTYPE, 1);
                    startActivity(intent);
                } else {
                    intent.setClass(GesturePwdMainActivity.this, CustomerCheckCodeActivity.class);
                    startActivity(intent);
                }
                break;
            default:
                break;
        }
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
                try {
                    SwitchGestureEntity switchGestureEntity = GsonUtils.gsonToBean(result, SwitchGestureEntity.class);
                    SwitchGestureEntity.ContentBean contentBean = switchGestureEntity.getContent();
                    if ("1".equals(contentBean.getSwitch_on())) {
                        if (switchStatus == 1) {
                            saveGesturePwdStatus(Constants.GESTURE_PWD_SET_AND_ENABLED);
                        } else {
                            saveGesturePwdStatus(Constants.GESTURE_PWD_SET_AND_DISABLED);
                        }
                    }
                } catch (Exception e) {

                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        IsGestureEntity checkRegisterEntity = GsonUtils.gsonToBean(result, IsGestureEntity.class);
                        IsGestureEntity.ContentBean contentBean = checkRegisterEntity.getContent();
                        gesturePwdStatus = contentBean.getGesture();
                    } catch (Exception e) {

                    }
                }
                editor.putString(UserAppConst.Colour_login_mobile + UserAppConst.GESTURE_OPENED, gesturePwdStatus);
                editor.commit();
                initGestureStatus();
                break;
        }
    }
}
