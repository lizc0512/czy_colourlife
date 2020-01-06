package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.external.eventbus.EventBus;
import com.external.gridpasswordview.GridPasswordView;
import com.external.gridpasswordview.PasswordType;
import com.point.model.PayPasswordModel;

import cn.net.cyberway.R;

import static com.user.UserMessageConstant.POINT_CHANGE_PAYPAWD;
import static com.user.UserMessageConstant.POINT_SET_PAYPAWD;

/***
 * 修改密码第三步
 */
public class ChangePawdThreeStepActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String NEWPAYPAWD = "newpaypawd";
    public static final String PAWDTOEKN = "pawdtoekn";
    public static final String PAWDTYPE = "pawdtype";//支付密码的类型  0表示设置密码  1表示修改密码 2 表示忘记密码
    private ImageView mBack;
    private TextView mTitle;
    private TextView tv_tips_content;
    private Button btn_define;
    private GridPasswordView gridPasswordView_cqb;
    private String newPayPawd;
    private String definePayPawd;
    private String passordToken;
    private int passwordType;
    private PayPasswordModel payPasswordModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setContentView(R.layout.activity_password_change_layout);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        tv_tips_content = findViewById(R.id.tv_tips_content);
        btn_define = findViewById(R.id.btn_define);
        gridPasswordView_cqb = findViewById(R.id.grid_pawd_view);
        mBack.setOnClickListener(this);
        btn_define.setVisibility(View.VISIBLE);
        btn_define.setEnabled(false);
        btn_define.setOnClickListener(this);
        Intent intent = getIntent();
        newPayPawd = intent.getStringExtra(NEWPAYPAWD);
        passordToken = intent.getStringExtra(PAWDTOEKN);
        passwordType = intent.getIntExtra(PAWDTYPE, 0);
        switch (passwordType) {
            case 1:
                mTitle.setText("修改支付密码");
                tv_tips_content.setText("请确认支付密码");
                break;
            case 2:
                mTitle.setText("重置支付密码");
                tv_tips_content.setText("请再次确认支付密码");
                break;
            default:
                mTitle.setText("设置支付密码");
                tv_tips_content.setText("请确认支付密码");
                break;
        }
        gridPasswordView_cqb.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                btn_define.setEnabled(false);
                btn_define.setBackgroundResource(R.drawable.point_password_default_bg);
            }

            @Override
            public void onInputFinish(String psw) {
                if (newPayPawd.equals(psw)) {
                    btn_define.setEnabled(true);
                    btn_define.setBackgroundResource(R.drawable.point_password_click_bg);
                    definePayPawd = psw;
                } else {
                    ToastUtil.toastShow(ChangePawdThreeStepActivity.this, "密码输入不一致，请重新输入");
                }
            }
        });
        payPasswordModel = new PayPasswordModel(ChangePawdThreeStepActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_define:
                if (passwordType == 1 || passwordType == 2) {
                    //修改支付密码    //忘记支付密码
                    payPasswordModel.setPayPassword(0, definePayPawd, passordToken, ChangePawdThreeStepActivity.this);
                } else { //设置支付密码
                    payPasswordModel.addPayPassword(0, definePayPawd, ChangePawdThreeStepActivity.this);
                }
                break;
        }

    }

    @Override
    public void OnHttpResponse(int what, String result) {
        if (what == 0) {
            switch (passwordType) {
                case 1:
                    if (!"1000".equals(result)) { //密码过于简单
                        Message msg = Message.obtain();
                        msg.what = POINT_CHANGE_PAYPAWD;
                        EventBus.getDefault().post(msg);
                        if (!"1001".equals(result)) {
                            msg.arg1=0;
                            ToastUtil.toastShow(ChangePawdThreeStepActivity.this, "支付密码修改成功");
                        }else{//token失效不关闭原验证码验证
                            msg.arg1=1;
                        }
                    }
                    finish();
                    break;
                case 2:
                    if (!"1000".equals(result)) { //密码过于简单
                        Message msg1 = Message.obtain();
                        msg1.what = POINT_CHANGE_PAYPAWD;
                        msg1.obj = definePayPawd;
                        EventBus.getDefault().post(msg1);
                        if (!"1001".equals(result)) {
                            msg1.arg1=0;
                            ToastUtil.toastShow(ChangePawdThreeStepActivity.this, "支付密码重置成功");
                        }else{//token失效不关闭填写资料页面
                            msg1.arg1=1;
                        }
                    }
                    finish();
                    break;
                default:
                    if (!"1000".equals(result)) { //密码过于简单
                        ToastUtil.toastShow(ChangePawdThreeStepActivity.this, "支付密码设置成功");
                        Message msg2 = Message.obtain();
                        msg2.what = POINT_SET_PAYPAWD;
                        msg2.obj = definePayPawd;
                        EventBus.getDefault().post(msg2);
                    }
                    finish();
                    break;
            }
        }
    }
}
