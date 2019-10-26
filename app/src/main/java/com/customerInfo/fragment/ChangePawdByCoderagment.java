package com.customerInfo.fragment;

import android.os.CountDownTimer;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseFragment;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GsonUtils;
import com.smileback.bankcommunicationsstyle.BCSIJMInputEditText;
import com.user.UserAppConst;
import com.user.entity.SendCodeEntity;
import com.user.model.NewUserModel;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.fragment
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/4/17 11:20
 * @change
 * @chang time
 * @class describe 通过短信验证码设置或忘记登录密码
 */
public class ChangePawdByCoderagment extends BaseFragment implements View.OnClickListener, NewHttpResponse {

    private TextView user_set_phone;
    private BCSIJMInputEditText ed_new_pawd;
    private EditText ed_sms;
    private TextView tv_get_sms;
    private TextView tv_voice_code;
    private Button btn_finish;
    private String password;
    private String code;
    private NewUserModel newUserModel;
    private String mobile;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_forget_pawd;
    }

    @Override
    protected void initView(View rootView) {
        user_set_phone = rootView.findViewById(R.id.user_set_phone);
        ed_new_pawd = rootView.findViewById(R.id.ed_new_pawd);
        ed_new_pawd.setNlicenseKey(UserAppConst.IJIAMINLICENSEKEY);
        ed_new_pawd.setKeyboardNoRandom(true);
        ed_new_pawd.setNKeyboardKeyBg(true);
        ed_new_pawd.clearKeyboard();
        ed_sms = rootView.findViewById(R.id.ed_sms);
        tv_get_sms = rootView.findViewById(R.id.tv_get_sms);
        tv_voice_code = rootView.findViewById(R.id.tv_voice_code);
        btn_finish = rootView.findViewById(R.id.btn_finish);
        tv_get_sms.setOnClickListener(this);
        tv_voice_code.setOnClickListener(this);
        btn_finish.setOnClickListener(this);
        ed_sms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                code = s.toString().trim();
                changeBtnStatus();
            }
        });
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
                changeBtnStatus();
            }
        });
        newUserModel = new NewUserModel(getActivity());
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        super.onFragmentVisibleChange(isVisible);
        if (isVisible) {
            mobile = shared.getString(UserAppConst.Colour_login_mobile, "");
            user_set_phone.setText(mobile);
        }
    }

    private void changeBtnStatus() {
        if (!TextUtils.isEmpty(code) && !TextUtils.isEmpty(password)) {
            btn_finish.setEnabled(true);
            btn_finish.setBackgroundResource(R.drawable.rect_round_blue);
        } else {
            btn_finish.setEnabled(false);
            btn_finish.setBackgroundResource(R.drawable.rect_round_gray);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_get_sms:
                newUserModel.getSmsCode(0, mobile, 1, 1, this);
                break;
            case R.id.tv_voice_code:
                newUserModel.getSmsCode(1, mobile, 1, 2, this);//找回密码获取语音验证码
                break;
            case R.id.btn_finish:
                if (TextUtils.isEmpty(password)) {
                    password = ed_new_pawd.getNKeyboardText();
                }
                newUserModel.resetUserPassword(2, mobile, code, password, this);
                ed_new_pawd.hideNKeyboard();
                break;
        }
    }

    private MyTimeCount myTimeCount = null;


    /***初始化计数器**/
    private void initTimeCount() {
        cancelTimeCount();
        tv_get_sms.setClickable(false);
        tv_get_sms.setTextColor(getResources().getColor(R.color.color_a3aaae));
        myTimeCount = new MyTimeCount(60000, 1000);
        myTimeCount.start();
    }

    private void cancelTimeCount() {
        if (myTimeCount != null) {
            myTimeCount.cancel();
            myTimeCount = null;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    //提示语音验证码获取成功
                    initTimeCount();
                    try {
                        SendCodeEntity sendCodeEntity = GsonUtils.gsonToBean(result, SendCodeEntity.class);
                        ToastUtil.toastShow(getActivity(), sendCodeEntity.getContent().getNotice());
                    } catch (Exception e) {
                        ToastUtil.toastShow(getActivity(), getResources().getString(R.string.user_hung_up));
                    }
                } else {
                    //显示发送短信到指定号码
                }
                break;
            case 2:
                cancelTimeCount();
                ToastUtil.toastShow(getActivity(), getResources().getString(R.string.user_pswd_resetsuccess));
                getActivity().finish();
                break;
        }

    }

    /**
     * 定义一个倒计时的内部类
     */
    class MyTimeCount extends CountDownTimer {
        public MyTimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            tv_get_sms.setText(getActivity().getResources().getString(R.string.user_again_getcode));
            if(getActivity()!=null){
                tv_get_sms.setTextColor(getActivity().getResources().getColor(R.color.tv_blue_bg));
            }
            tv_get_sms.setClickable(true);
            tv_get_sms.requestFocus();
        }





        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            long currentSecond = millisUntilFinished / UserAppConst.INTERVAL;
            if (getActivity()!=null){
                tv_get_sms.setText(getActivity().getResources().getString(R.string.user_already_send) + "(" + currentSecond + "S)");
                if (currentSecond <= 20) {
                    tv_voice_code.setVisibility(View.VISIBLE);
                } else {
                    tv_voice_code.setVisibility(View.GONE);
                }
            }
        }
    }
}
