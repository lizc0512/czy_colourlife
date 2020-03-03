package com.realaudit.activity;

import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.protocol.RealNameTokenEntity;
import com.external.eventbus.EventBus;
import com.tencent.authsdk.AuthConfig;
import com.tencent.authsdk.AuthSDKApi;
import com.tencent.authsdk.IDCardInfo;
import com.tencent.authsdk.callback.IdentityCallback;
import com.user.UserAppConst;
import com.user.model.NewUserModel;

import org.json.JSONObject;

import cn.csh.colourful.life.utils.GsonUtils;
import cn.net.cyberway.R;

import static com.user.UserMessageConstant.REAL_FAIL_STATE;
import static com.user.UserMessageConstant.REAL_SUCCESS_STATE;

/**
 * 文件名:通用的实名信息提交页面
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:
 * 描述:
 **/
public class RealCommonSubmitActivity extends BaseActivity implements NewHttpResponse {
    public static final String SHOWFAILDIALOG = "showfaildialog";

    private String realToken;
    private String realName;
    private int customer_id;//用户的id
    private NewUserModel newUserModel;
    private int showDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customer_id = shared.getInt(UserAppConst.Colour_User_id, 0);
        showDialog = getIntent().getIntExtra(SHOWFAILDIALOG, 0);
        newUserModel = new NewUserModel(RealCommonSubmitActivity.this);
        newUserModel.getRealNameToken(1, this, false);
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
                realName = idCardInfo.getName();//姓名
                newUserModel.submitRealName(2, realToken, "", "", this);//提交实名认证
            }
        } else {
            finish();
        }
    };

    private void authenticateFail(String errorMsg) {
        if (showDialog == 0) {
            ToastUtil.toastShow(this, errorMsg);
        } else {
            Message message = Message.obtain();
            message.what = REAL_FAIL_STATE;
            EventBus.getDefault().post(message);
        }
        finish();
    }

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
                        authenticateFail("");
                    }
                } else {
                    authenticateFail("");
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
                                editor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, realName);
                                editor.putString(UserAppConst.COLOUR_DYNAMICS_REAL_IDENTITY, "1");
                                editor.apply();
                                newUserModel.finishTask(3, "2", "task_native", this);//实名认证任务
                            }
                        } else {
                            if (showDialog == 0) {
                                String message = jsonObject.getString("message");
                                authenticateFail(message);
                            } else {
                                authenticateFail("");
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        authenticateFail("");
                    }
                } else {
                    authenticateFail("");
                }
                break;
            case 3:
                ToastUtil.toastShow(this, "认证成功");
                Message message = Message.obtain();
                message.what = REAL_SUCCESS_STATE;
                message.obj = realName;
                EventBus.getDefault().post(message);
                finish();
                break;
        }
    }
}
