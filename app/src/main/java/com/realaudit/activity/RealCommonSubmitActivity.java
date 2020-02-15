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
import com.user.model.NewUserModel;

import org.json.JSONObject;

import cn.csh.colourful.life.utils.GsonUtils;
import cn.net.cyberway.R;

import static com.user.UserMessageConstant.REAL_CHANGE_STATE;

/**
 * 文件名:通用的实名信息提交页面
 * 创建者:yuansongkai
 * 邮箱:827194927@qq.com
 * 创建日期:
 * 描述:
 **/
public class RealCommonSubmitActivity extends BaseActivity implements NewHttpResponse {

    private String  realToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        NewUserModel newUserModel = new NewUserModel(RealCommonSubmitActivity.this);
        newUserModel.getRealNameToken(1, this, true);
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
                NewUserModel newUserModel = new NewUserModel(RealCommonSubmitActivity.this);
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
