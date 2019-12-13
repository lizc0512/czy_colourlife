package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.external.eventbus.EventBus;
import com.external.gridpasswordview.GridPasswordView;
import com.external.gridpasswordview.PasswordType;
import com.nohttp.utils.GsonUtils;
import com.point.entity.PayPwdCheckEntity;
import com.point.model.PayPasswordModel;
import com.user.UserMessageConstant;

import cn.net.cyberway.R;

import static com.point.activity.ChangePawdThreeStepActivity.PAWDTOEKN;
import static com.point.activity.ChangePawdThreeStepActivity.PAWDTYPE;

/***
 * 修改密码第一步
 */
public class ChangePawdOneStepActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private ImageView mBack;
    private TextView mTitle;
    private GridPasswordView gridPasswordView_cqb;
    private PayPasswordModel payPasswordModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setContentView(R.layout.activity_password_change_layout);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        gridPasswordView_cqb = findViewById(R.id.grid_pawd_view);
        gridPasswordView_cqb.setPasswordType(PasswordType.NUMBER);
        gridPasswordView_cqb.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                payPasswordModel.checkPayPassword(0, psw, ChangePawdOneStepActivity.this);
            }
        });
        mBack.setOnClickListener(this);
        mTitle.setText("修改支付密码");
        if (!EventBus.getDefault().isregister(ChangePawdOneStepActivity.this)) {
            EventBus.getDefault().register(ChangePawdOneStepActivity.this);
        }
        payPasswordModel = new PayPasswordModel(this);
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.POINT_CHANGE_PAYPAWD:
                int tokenInvalid = message.arg1;
                if (tokenInvalid == 0) {
                    finish();
                }
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(ChangePawdOneStepActivity.this)) {
            EventBus.getDefault().unregister(ChangePawdOneStepActivity.this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }

    }

    @Override
    public void OnHttpResponse(int what, String result) {
        if (what == 0) {
            try {
                PayPwdCheckEntity payPwdCheckEntity = GsonUtils.gsonToBean(result, PayPwdCheckEntity.class);
                PayPwdCheckEntity.ContentBean contentBean = payPwdCheckEntity.getContent();
                if ("1".equals(contentBean.getIs_pwd())) { //已设置支付密码
                    if ("1".equals(contentBean.getRight_pwd())) {//原支付密码校验正确
                        Intent intent = new Intent(ChangePawdOneStepActivity.this, ChangePawdTwoStepActivity.class);
                        intent.putExtra(PAWDTYPE, 1);
                        intent.putExtra(PAWDTOEKN, contentBean.getToken());
                        startActivity(intent);
                    } else {
                        int remain = contentBean.getRemain();
                        if (remain == 0) {
                            ToastUtil.toastShow(ChangePawdOneStepActivity.this, "您已输入5次错误密码，账户被锁定，请明日再进行操作");
                        } else {
                            ToastUtil.toastShow(ChangePawdOneStepActivity.this, "支付密码不正确，您还可以输入" + remain + "次");
                        }
                    }
                } else {
                    //没有支付密码 前去设置支付密码
                    Intent intent = new Intent(ChangePawdOneStepActivity.this, ChangePawdTwoStepActivity.class);
                    startActivity(intent);
                }
            } catch (Exception e) {

            }
        }
    }
}
