package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.external.eventbus.EventBus;
import com.nohttp.utils.GsonUtils;
import com.point.entity.IndentityInforEntity;
import com.point.entity.PayPwdCheckEntity;
import com.point.model.PayPasswordModel;
import com.point.password.VirtualKeyboardView;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.activity.UserSmsCodeActivity;
import com.user.entity.SendCodeEntity;
import com.user.model.NewUserModel;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Map;

import cn.csh.colourful.life.utils.KeyBoardUtils;
import cn.net.cyberway.R;

import static cn.net.cyberway.utils.ConfigUtils.jumpContactService;
import static com.point.activity.ChangePawdThreeStepActivity.PAWDTOEKN;

/***
 * 忘记密码填写资料
 */
public class ForgetPayPawdActivity extends BaseActivity implements View.OnClickListener, TextWatcher, NewHttpResponse {
    private ImageView mBack;
    private TextView mTitle;
    private TextView tv_user_phone;
    private ClearEditText input_pawd_code;
    private TextView tv_get_code;
    private TextView tv_user_realname;
    private ClearEditText input_pawd_idcard;
    private Button btn_define;
    private TextView tv_contact_service;
    private MyTimeCount myTimeCount = null;
    private String idCardNumber;
    private String mobile;
    private String smsCode;
    private NewUserModel newUserModel;
    private PayPasswordModel payPasswordModel;
    private VirtualKeyboardView virtualKeyboardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_forget_layout);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        tv_user_phone = findViewById(R.id.tv_user_phone);
        input_pawd_code = findViewById(R.id.input_pawd_code);
        tv_get_code = findViewById(R.id.tv_get_code);
        tv_user_realname = findViewById(R.id.tv_user_realname);
        input_pawd_idcard = findViewById(R.id.input_pawd_idcard);
        btn_define = findViewById(R.id.btn_define);
        btn_define.setEnabled(false);
        tv_contact_service = findViewById(R.id.tv_contact_service);
        virtualKeyboardView = findViewById(R.id.virtualKeyboardView);
        mBack.setOnClickListener(this::onClick);
        tv_get_code.setOnClickListener(this::onClick);
        btn_define.setOnClickListener(this::onClick);
        tv_contact_service.setOnClickListener(this::onClick);
        mTitle.setText("支付密码");
        input_pawd_code.addTextChangedListener(this);
        input_pawd_idcard.addTextChangedListener(this);
        newUserModel = new NewUserModel(ForgetPayPawdActivity.this);
        payPasswordModel = new PayPasswordModel(ForgetPayPawdActivity.this);
        if (!EventBus.getDefault().isregister(ForgetPayPawdActivity.this)) {
            EventBus.getDefault().register(ForgetPayPawdActivity.this);
        }
        payPasswordModel.getIdentityInfor(0, ForgetPayPawdActivity.this);
        initKeyBoard();
    }


    private Animation enterAnim;
    private Animation exitAnim;
    private GridView gridView;
    private ArrayList<Map<String, String>> valueList;

    private void initKeyBoard() {
        enterAnim = AnimationUtils.loadAnimation(this, R.anim.push_buttom_in);
        exitAnim = AnimationUtils.loadAnimation(this, R.anim.push_buttom_out);
        valueList = virtualKeyboardView.getValueList();
        if (android.os.Build.VERSION.SDK_INT <= 10) {
            input_pawd_idcard.setInputType(InputType.TYPE_NULL);
        } else {
            this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            try {
                Class<EditText> cls = EditText.class;
                Method setShowSoftInputOnFocus;
                setShowSoftInputOnFocus = cls.getMethod("setShowSoftInputOnFocus",
                        boolean.class);
                setShowSoftInputOnFocus.setAccessible(true);
                setShowSoftInputOnFocus.invoke(input_pawd_idcard, false);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        virtualKeyboardView.getLayoutBack().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                virtualKeyboardView.startAnimation(exitAnim);
                virtualKeyboardView.setVisibility(View.GONE);
            }
        });
        gridView = virtualKeyboardView.getGridView();
        gridView.setOnItemClickListener(onItemClickListener);
        input_pawd_idcard.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    showCardKeyBoard();
                } else {
                    hideCardKeyBoard();
                }
            }
        });
        input_pawd_idcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (virtualKeyboardView.getVisibility() == View.GONE) {
                    showCardKeyBoard();
                }
            }
        });
        input_pawd_code.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    hideCardKeyBoard();
                }
            }
        });
    }

    private void hideCardKeyBoard() {
        virtualKeyboardView.startAnimation(exitAnim);
        virtualKeyboardView.setVisibility(View.GONE);
    }

    private void showCardKeyBoard() {
        KeyBoardUtils.closeKeybord(input_pawd_idcard, ForgetPayPawdActivity.this);
        virtualKeyboardView.setFocusable(true);
        virtualKeyboardView.setFocusableInTouchMode(true);
        virtualKeyboardView.startAnimation(enterAnim);
        virtualKeyboardView.setVisibility(View.VISIBLE);
    }

    private AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
            if (position < 11) {    //点击0~9按钮
                String amount = input_pawd_idcard.getText().toString().trim();
                amount = amount + valueList.get(position).get("name");
                input_pawd_idcard.setText(amount);
                Editable ea = input_pawd_idcard.getText();
                input_pawd_idcard.setSelection(ea.length());
            } else {
                if (position == 11) {      //点击退格键
                    String amount = input_pawd_idcard.getText().toString().trim();
                    if (amount.length() > 0) {
                        amount = amount.substring(0, amount.length() - 1);
                        input_pawd_idcard.setText(amount);
                        Editable ea = input_pawd_idcard.getText();
                        input_pawd_idcard.setSelection(ea.length());
                    }
                }
            }
        }
    };

    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.POINT_CHANGE_PAYPAWD:
                int tokenInvalid = message.arg1;
                if (tokenInvalid == 1) {
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(ForgetPayPawdActivity.this)) {
            EventBus.getDefault().unregister(ForgetPayPawdActivity.this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_get_code:
                if (fastClick()) {
                    newUserModel.getSmsCode(1, mobile, 7, 1, ForgetPayPawdActivity.this);
                }
                break;
            case R.id.btn_define:
                if (fastClick()) {
                    payPasswordModel.validIdentityInfor(2, mobile, smsCode, idCardNumber, ForgetPayPawdActivity.this);
                }
                break;
            case R.id.tv_contact_service:
                jumpContactService(ForgetPayPawdActivity.this);
                break;
        }

    }


    /***初始化计数器**/
    private void initTimeCount() {
        cancelTimeCount();
        tv_get_code.setClickable(false);
        tv_get_code.setTextColor(getResources().getColor(R.color.color_8d9299));
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
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        smsCode = input_pawd_code.getText().toString().trim();
        idCardNumber = input_pawd_idcard.getText().toString().trim();
        if (TextUtils.isEmpty(smsCode) || TextUtils.isEmpty(idCardNumber)) {
            btn_define.setEnabled(false);
            btn_define.setBackgroundResource(R.drawable.point_password_default_bg);
        } else {
            btn_define.setEnabled(true);
            btn_define.setBackgroundResource(R.drawable.point_password_click_bg);
        }

    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    IndentityInforEntity indentityInforEntity = GsonUtils.gsonToBean(result, IndentityInforEntity.class);
                    IndentityInforEntity.ContentBean contentBean = indentityInforEntity.getContent();
                    mobile = contentBean.getMobile();
                    tv_user_phone.setText(Utils.getHandlePhone(mobile));
                    String identity_name = contentBean.getIdentity_name();
                    int length = identity_name.length();
                    StringBuffer stringBuffer = new StringBuffer();
                    for (int j = 0; j < length - 1; j++) {
                        stringBuffer.append("*");
                    }
                    stringBuffer.append(identity_name.substring(length - 1, length));
                    tv_user_realname.setText(stringBuffer.toString());
                } catch (Exception e) {

                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        initTimeCount();
                        SendCodeEntity sendCodeEntity = GsonUtils.gsonToBean(result, SendCodeEntity.class);
                        ToastUtil.toastShow(ForgetPayPawdActivity.this, sendCodeEntity.getContent().getNotice());
                    } catch (Exception e) {
                        ToastUtil.toastShow(ForgetPayPawdActivity.this, getResources().getString(R.string.user_code_send));
                    }
                }
                break;
            case 2:
                try {
                    PayPwdCheckEntity payPwdCheckEntity = GsonUtils.gsonToBean(result, PayPwdCheckEntity.class);
                    PayPwdCheckEntity.ContentBean contentBean = payPwdCheckEntity.getContent();
                    Intent intent = new Intent(ForgetPayPawdActivity.this, ChangePawdTwoStepActivity.class);
                    intent.putExtra(ChangePawdThreeStepActivity.PAWDTYPE, 2);
                    intent.putExtra(PAWDTOEKN, contentBean.getToken());
                    startActivity(intent);
                } catch (Exception e) {

                }
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
            tv_get_code.setText(getResources().getString(R.string.user_again_getcode));
            tv_get_code.setTextColor(getResources().getColor(R.color.color_329dfa));
            tv_get_code.setClickable(true);
            tv_get_code.requestFocus();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            long currentSecond = millisUntilFinished / UserAppConst.INTERVAL;
            tv_get_code.setText(getResources().getString(R.string.user_already_send) + "(" + currentSecond + "S)");
        }
    }
}
