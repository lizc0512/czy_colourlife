package com.realaudit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.protocol.RealNameTokenEntity;
import com.external.eventbus.EventBus;
import com.tencent.authsdk.AuthConfig;
import com.tencent.authsdk.AuthSDKApi;
import com.tencent.authsdk.IDCardInfo;
import com.tencent.authsdk.callback.IdentityCallback;
import com.user.model.NewUserModel;

import org.json.JSONObject;

import cn.csh.colourful.life.utils.GsonUtils;
import cn.net.cyberway.R;

import static cn.net.cyberway.utils.ConfigUtils.jumpContactService;
import static com.user.UserMessageConstant.REAL_CHANGE_STATE;

/**
 * 文件名:实名信息审核结果页面
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:
 * 描述:
 **/
public class RealCheckResultActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public static final String CHECKSTATE = "checkstate";
    public static final String CHECKREASON = "checkreason";

    private TextView tv_title;   //标题
    private ImageView imageView_back;//返回


    private ImageView iv_check_result;
    private TextView tv_contact_service;
    private Button btn_apply;
    private String checkState;
    private String realToken = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_realname_check_result);
        tv_title = findViewById(R.id.user_top_view_title);
        imageView_back = findViewById(R.id.user_top_view_back);
        iv_check_result = findViewById(R.id.iv_check_result);
        tv_contact_service = findViewById(R.id.tv_contact_service);
        btn_apply = findViewById(R.id.btn_apply);
        Intent intent = getIntent();
        checkState = intent.getStringExtra(CHECKSTATE);
        String checkReason = intent.getStringExtra(CHECKREASON);

        TextView tv_check_reason = findViewById(R.id.tv_check_reason);
        TextView tv_check_result = findViewById(R.id.tv_check_result);
        if ("2".equals(checkState)) {
            tv_check_reason.setText(getResources().getString(R.string.real_status_success));
        } else {
            btn_apply.setText(getResources().getString(R.string.real_upload_again));
            iv_check_result.setImageResource(R.drawable.real_check_fail);
            tv_check_result.setText(getResources().getString(R.string.real_status_fail));
            if (!TextUtils.isEmpty(checkReason)) {
                tv_check_reason.setText(checkReason);
            }else{
                tv_check_reason.setText("");
            }
        }

        imageView_back.setOnClickListener(this::onClick);
        tv_contact_service.setOnClickListener(this::onClick);
        btn_apply.setOnClickListener(this::onClick);
        tv_title.setText(getResources().getString(R.string.real_title_change_realname));

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_contact_service:
                jumpContactService(RealCheckResultActivity.this);
                break;
            case R.id.btn_apply:
                if ("2".equals(checkState)) {
                    NewUserModel newUserModel = new NewUserModel(RealCheckResultActivity.this);
                    newUserModel.getRealNameToken(1, this, true);
                } else {
                    Intent intent = new Intent(RealCheckResultActivity.this, RealOriginUploadActivity.class);
                    startActivity(intent);
                    finish();
                }
                break;
        }

    }

    /**
     * 实名认证
     */
    private void startAuthenticate(String realToken) {
        AuthConfig.Builder configBuilder = new AuthConfig.Builder(realToken, R.class.getPackage().getName());
        AuthSDKApi.startMainPage(this, configBuilder.build(), mListener);
    }

    /**
     * 监听实名认证返回
     */
    private IdentityCallback mListener = data -> {
        boolean identityStatus = data.getBooleanExtra(AuthSDKApi.EXTRA_IDENTITY_STATUS, false);
        if (identityStatus) {//identityStatus true 已实名
            IDCardInfo idCardInfo = data.getExtras().getParcelable(AuthSDKApi.EXTRA_IDCARD_INFO);
            if (idCardInfo != null) {//身份证信息   idCardInfo.getIDcard();//身份证号码
                NewUserModel newUserModel = new NewUserModel(RealCheckResultActivity.this);
                newUserModel.submitRealName(2, realToken, this);//提交实名认证
            }
        }else {
            finish();
        }
    };


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        RealNameTokenEntity entity = GsonUtils.gsonToBean(result, RealNameTokenEntity.class);
                        RealNameTokenEntity.ContentBean bean = entity.getContent();
                        realToken = bean.getBizToken();
                        startAuthenticate(realToken);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2://认证
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        if ("0".equals(code)) {
                            String content = jsonObject.getString("content");
                            if ("1".equals(content)) {
                                Message message=Message.obtain();
                                message.what=REAL_CHANGE_STATE;
                                EventBus.getDefault().post(message);
                                ToastUtil.toastShow(this, "认证成功");
                                finish();
                            }
                        } else {
                            String message = jsonObject.getString("message");
                            ToastUtil.toastShow(this, message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
