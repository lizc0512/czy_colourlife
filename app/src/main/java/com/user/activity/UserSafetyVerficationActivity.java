package com.user.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.Constants;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.MyProgressDialog;
import com.geetest.deepknow.DPAPI;
import com.geetest.deepknow.bean.DPJudgementBean;
import com.geetest.onepass.BaseGOPListener;
import com.geetest.onepass.GOPGeetestUtils;
import com.geetest.sensebot.SEAPI;
import com.geetest.sensebot.listener.BaseSEListener;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.RequestEncryptionUtils;
import com.user.Utils.TokenUtils;
import com.user.entity.CheckGatewayEntity;
import com.user.entity.LoginVerifyEntity;
import com.user.model.TokenModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import cn.net.cyberway.R;

import static com.BeeFramework.model.Constants.GOP_VERIFYURL;

/**
 * @name ${yuansk}
 * @class name：com.user.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/28 10:13
 * @change
 * @chang time
 * @class describe   极验验证的安全验证页面
 */

public class UserSafetyVerficationActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public final String activityName = "UserSafetyVerficationActivity";
    /**
     * onepass的工具类
     */
    private GOPGeetestUtils gopGeetestUtils;
    /**
     * onepass的监听类
     */
    private BaseGOPListener baseGOPListener;


    /**
     * 配置的customid
     */
    public static final String CUSTOM_ID = "243fafe04cb8b398811f59a3f7bff737";
    /**
     * 进度条
     */
    private MyProgressDialog progressDialog;

    private Button btn_start;

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_safety_notice;

    private String mobile;
    private String password;
    private int isWhite = 0;
    private int isRegister = 0;
    private TokenModel mTokenModel;
    private SEAPI seapi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //拿到这个权限可以更方便的进行网关验证
        setContentView(R.layout.activity_safty_verfity);
        initView();
        mTokenModel = new TokenModel(this);
        Intent intent = getIntent();
        mobile = intent.getStringExtra(UserRegisterAndLoginActivity.MOBILE);
        password = intent.getStringExtra(UserRegisterAndLoginActivity.PASSWORD);
        isWhite = intent.getIntExtra(UserRegisterAndLoginActivity.ISWHITE, 0);
        isRegister = intent.getIntExtra(UserRegisterAndLoginActivity.ISREGISTER, 0);
        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        }
        initGT3();
        initGop();
        if (isRegister == 0) {
            tv_safety_notice.setText(getResources().getString(R.string.user_register_verifysafty));
        } else {
            tv_safety_notice.setText(getResources().getString(R.string.user_login_verifysafty));
        }
    }

    private void initView() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_safety_notice = (TextView) findViewById(R.id.tv_safety_notice);
        btn_start = (Button) findViewById(R.id.btn_start);
        user_top_view_title.setText(getResources().getString(R.string.title_safty_verify));
        user_top_view_back.setOnClickListener(this);
        btn_start.setOnClickListener(this);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, user_top_view_back, user_top_view_title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_start:
                if (isRegister == 1) {
                    //登录需要极验验证
                    if (isWhite == 2) { //3个月未登录
                        openOnePass(null);
                    } else {  //更换设备  更换设备以及三个月未登录
                        showGeetest(2);
                    }
                } else {  //注册不是白名单用户
                    showGeetest(1);
                }
                break;
        }
    }

    private void showGeetest(int type) {
        seapi.onVerify(new DPJudgementBean(Constants.DEEPKNOWID, type, new HashMap<String, Object>()), new BaseSEListener() {
            /**
             * SDK内部show loading dialog
             */
            @Override
            public void onShowDialog() {

            }

            @Override
            public void onError(String errorCode, String error) {
                showVerficationError(error);
            }

            /**
             * 验证码Dialog关闭
             * 1：webview的叉按钮关闭
             * 2：点击屏幕外关闭
             * 3：点击回退键关闭
             *
             * @param num
             */
            @Override
            public void onCloseDialog(int num) {

            }

            /**
             * show 验证码webview
             */
            @Override
            public void onDialogReady() {

            }

            /**
             * 验证成功
             * @param challenge
             */
            @Override
            public void onResult(String challenge) {
                mTokenModel.checkDeepKnow(4, mobile, challenge, UserSafetyVerficationActivity.this);
            }
        });
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 4: //登录极验验证
                try {
                    LoginVerifyEntity loginVerifyEntity = GsonUtils.gsonToBean(result, LoginVerifyEntity.class);
                    LoginVerifyEntity.ContentBean contentBean = loginVerifyEntity.getContent();
                    if ("1".equals(contentBean.getStatus())) {
                        if (isRegister == 1) {
                            if (isWhite == 3) {
                                Intent intent = new Intent(UserSafetyVerficationActivity.this, UserSmsCodeActivity.class);
                                intent.putExtra(UserRegisterAndLoginActivity.MOBILE, mobile);
                                intent.putExtra(UserRegisterAndLoginActivity.PASSWORD, password);
                                intent.putExtra(UserRegisterAndLoginActivity.SMSTYPE, 2);
                                startActivityForResult(intent, 1000);
                            } else {
                                //从哪里来回到哪里去进行登录
                                setResult(500);
                                finish();
                            }
                        } else {
                            goUserRegisterPage();
                        }
                    } else {
                        String showError = contentBean.getUser_error() + contentBean.getError_code();
                        if (TextUtils.isEmpty(showError)) {
                            showVerficationError(showError);
                        } else {
                            showVerficationError(getResources().getString(R.string.user_verifysafty_error));
                        }

                    }
                } catch (Exception e) {
                    showVerficationError(getResources().getString(R.string.user_verifysafty_error));
                }
                break;
        }
    }

    private void showVerficationError(String errorMessage) {
        try {
            if (isRegister == 1) {
                ToastUtil.toastShow(UserSafetyVerficationActivity.this, errorMessage);
            } else {
                goUserRegisterPage();
            }
        } catch (Exception e) {

        }
    }


    private void goSmsCodePage() {
        Intent intent = new Intent(UserSafetyVerficationActivity.this, UserSmsCodeActivity.class);
        intent.putExtra(UserRegisterAndLoginActivity.MOBILE, mobile);
        intent.putExtra(UserRegisterAndLoginActivity.PASSWORD, password);
        intent.putExtra(UserRegisterAndLoginActivity.SMSTYPE, 2);
        startActivityForResult(intent, 1000);
    }

    private void goUserRegisterPage() {
        Intent intent = new Intent(UserSafetyVerficationActivity.this, UserRegisterActivity.class);
        intent.putExtra(UserRegisterAndLoginActivity.MOBILE, mobile);
        intent.putExtra(UserRegisterAndLoginActivity.PASSWORD, password);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (gopGeetestUtils != null) {
            gopGeetestUtils.cancelUtils();
        }
    }


    private void initGT3() {
        seapi = new SEAPI(this);
        DPAPI.getInstance(getApplicationContext()).ignoreDPView(btn_start, activityName);
    }


    /**
     * onepass的方法，执行onepass只需拿到验证码的validate，兼容所有公版验证码
     *
     * @param validate
     */
    private void openOnePass(String validate) {
        /**
         *    第一参数为填写的手机号
         *    第二个参数为验证后的validate
         *    第三个参数为customid
         *    第四个参数为回调
         */
        gopGeetestUtils.getOnePass(mobile, validate, CUSTOM_ID, baseGOPListener);
        progressDialog = new MyProgressDialog(UserSafetyVerficationActivity.this, "");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();
    }

    /**
     * 初始化onepass
     */
    private void initGop() {
        gopGeetestUtils = GOPGeetestUtils.getInstance(UserSafetyVerficationActivity.this);
        /**
         * 初始化onepass监听类(必须实现的有四个接口，处理流程中实现的问题)
         */
        baseGOPListener = new BaseGOPListener() {
            @Override
            public void gopOnError(String s) {
                /**
                 * 过程中的错误
                 */
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                goSmsCodePage();
            }

            @Override
            public void gopOnResult(String result) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                String code = "";
                if (!TextUtils.isEmpty(result)) {
                    try {
                        BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                        if (baseContentEntity.getCode() == 0) {
                            CheckGatewayEntity checkGatewayEntity = GsonUtils.gsonToBean(result, CheckGatewayEntity.class);
                            CheckGatewayEntity.ContentBean contentBean = checkGatewayEntity.getContent();
                            if (contentBean.getCheck_result() == 1) { /**
                             * 验证成功的回调  进行注册 登录和获取用户信息
                             */
                                code = contentBean.getSms_token();
                            }
                        }
                    } catch (Exception e) {

                    }
                }
                if (!TextUtils.isEmpty(code)) {
                    Intent intent = new Intent();
                    intent.putExtra(UserRegisterAndLoginActivity.SMSCODE, code);
                    setResult(200, intent);
                    finish();
                } else {
                    goSmsCodePage();
                }
            }

            @Override
            public int gopOnAnalysisVerifyUrl(JSONObject jsonObject) {
                /**
                 * 返回VerifyUrl的请求结果，并拿到result值回传给sdk
                 * 默认为：
                 *  try {
                 return var1.getInt("result");
                 }    catch (JSONException var3) {
                 var3.printStackTrace();
                 return 0;
                 }
                 */
                return super.gopOnAnalysisVerifyUrl(jsonObject);
            }

            @Override
            public String gopOnVerifyUrl() {
                /**
                 * 回传给sdk内部使用的VerifyUrl(必填)
                 */
                return GOP_VERIFYURL;
            }

            @Override
            public boolean gopOnDefaultSwitch() {
                return false;
            }

            public Map<String, String> gopOnVerifyUrlBody() {
                Map<String, Object> objectMap = new HashMap<String, Object>();
                objectMap.put("device_uuid", TokenUtils.getUUID(UserSafetyVerficationActivity.this));
                Map<String, String> stringMap = RequestEncryptionUtils.getStringMap(RequestEncryptionUtils.getNewSaftyMap(UserSafetyVerficationActivity.this, objectMap));
                return stringMap;
            }

            @Override
            public void gopOnSendMsg(boolean b, Map<String, String> map, JSONObject
                    jsonObject) {
                /**
                 * 发短信原因（JSON形式）
                 *
                 * 数据格式为json
                 * error_code与error
                 */
                /**
                 * 短信分发接口
                 */
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
                if (!b) {
                    goSmsCodePage();
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            String code = data.getStringExtra(UserRegisterAndLoginActivity.SMSCODE);
            Intent intent = new Intent();
            intent.putExtra(UserRegisterAndLoginActivity.SMSCODE, code);
            setResult(200, intent);
            finish();
        }
    }
}
