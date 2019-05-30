package com.gesturepwd.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.CircleImageView;
import com.eparking.helper.CustomDialog;
import com.external.eventbus.EventBus;
import com.gesturepwd.utils.LockPatternUtils;
import com.gesturepwd.view.LockPatternView;
import com.jpush.Constant;
import com.nohttp.utils.GsonUtils;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.permission.AndPermission;
import com.permission.PermissionListener;
import com.tendcloud.tenddata.TCAgent;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.activity.UserRegisterAndLoginActivity;
import com.user.activity.UserSafetyVerficationActivity;
import com.user.activity.UserSmsCodeActivity;
import com.user.entity.CheckWhiteEntity;
import com.user.model.NewUserModel;
import com.user.model.TokenModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.net.cyberway.R;
import cn.net.cyberway.home.model.NewHomeModel;

import static cn.net.cyberway.utils.IMFriendDataUtils.userInitImData;
import static com.BeeFramework.model.Constants.GESTURE_PWD_SET_FIVE_ERROR;
import static com.user.activity.UserRegisterAndLoginActivity.REQUEST_CALLPHONE;

public class UnlockGesturePasswordActivity extends BaseActivity implements NewHttpResponse {
    private FrameLayout czy_title_layout;
    private LockPatternView mLockPatternView;
    private int mFailedPatternAttemptsSinceLastTimeout = 0;
    private CountDownTimer mCountdownTimer = null;
    private TextView mHeadTextView;
    private Animation mShakeAnim;
    private TextView resetpassword;
    private TokenModel mTokenModel;
    private NewUserModel mUserModel;
    private TextView tv_title;      //标题
    private TextView function;      //订单记录
    private ImageView back;
    private TextView tv_phone;
    private SharedPreferences mShared;
    protected List<LockPatternView.Cell> mChosenPattern = null;
    private String mobile;
    public static final int LOCLGESTURE_CODE = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesturepassword_unlock);
        mShared = getSharedPreferences(UserAppConst.USERINFO, 0);
        mTokenModel = new TokenModel(this);
        mUserModel = new NewUserModel(this);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        back = (ImageView) findViewById(R.id.user_top_view_back);
        back.setVisibility(View.INVISIBLE);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_user_login));
        function = (TextView) findViewById(R.id.user_top_view_right);
        function.setVisibility(View.VISIBLE);
        tv_phone = (TextView) findViewById(R.id.tv_phone);
        mobile = mShared.getString(UserAppConst.Colour_login_mobile, "");
        tv_phone.setText(mobile);
        tv_phone.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //切换账号登录
                Intent intent = new Intent(UnlockGesturePasswordActivity.this, UserRegisterAndLoginActivity.class);
                editor.putString(UserAppConst.Colour_login_mobile + UserAppConst.GESTURE_OPENED, "");
                editor.commit();
                startActivity(intent);
                finish();
            }
        });
        resetpassword = (TextView) findViewById(R.id.gesturepwd_unlock_forget);
        resetpassword.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UnlockGesturePasswordActivity.this, UserRegisterAndLoginActivity.class);
                editor.putString(UserAppConst.Colour_login_mobile + UserAppConst.GESTURE_OPENED, "");
                editor.commit();
                startActivity(intent);
                finish();
            }
        });

        mLockPatternView = (LockPatternView) this
                .findViewById(R.id.gesturepwd_unlock_lockview);
        int height = Utils.getDeviceHeight(this);
        if (height < 850) {//适配超低分辨率屏幕
            ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) mLockPatternView.getLayoutParams();
            layoutParams.height = Utils.dip2px(this, 230);
            mLockPatternView.setLayoutParams(layoutParams);
        }

        mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
        mLockPatternView.setTactileFeedbackEnabled(true);
        mHeadTextView = (TextView) findViewById(R.id.gesturepwd_unlock_text);
        mShakeAnim = AnimationUtils.loadAnimation(this, R.anim.shake_x);
        //设置头像
        CircleImageView img_head = (CircleImageView) findViewById(R.id.gesturepwd_unlock_face);
        String url = mShared.getString(UserAppConst.Colour_head_img, "");
        if (!TextUtils.isEmpty(url)) {
            DisplayImageOptions displayImageOptions = new DisplayImageOptions.Builder()
                    .showImageOnLoading(R.drawable.head_default)
                    .showImageForEmptyUri(R.drawable.head_default)    // 设置图片Uri为空或是错误的时候显示的图片
                    .showImageOnFail(R.drawable.head_default)        // 设置图片加载或解码过程中发生错误显示的图片
                    .cacheInMemory(true)                        // 设置下载的图片是否缓存在内存中
                    .cacheOnDisk(true)                            // 设置下载的图片是否缓存在SD卡中
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .build();
            ImageLoader.getInstance().displayImage(url, img_head, displayImageOptions);
        }
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, back, tv_title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mCountdownTimer != null) {
            mCountdownTimer.cancel();
        }
    }

    private Runnable mClearPatternRunnable = new Runnable() {
        public void run() {
            mLockPatternView.clearPattern();
        }
    };

    protected LockPatternView.OnPatternListener mChooseNewLockPatternListener = new LockPatternView.OnPatternListener() {

        public void onPatternStart() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
            patternInProgress();
        }

        public void onPatternCleared() {
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
        }

        public void onPatternDetected(List<LockPatternView.Cell> pattern) {
            if (pattern == null)
                return;
//			// TODO 在这里验证验证手势密码是否正确 登录成功跳转到首页
            if (pattern.size() >= LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {
                //如果输入的密码长度大于4请求接口
                mChosenPattern = pattern;
                mUserModel.getCheckWhite(0, mobile, 1, UnlockGesturePasswordActivity.this);
            } else {
                mLockPatternView
                        .setDisplayMode(LockPatternView.DisplayMode.Wrong);
                mFailedPatternAttemptsSinceLastTimeout++;
                int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
                        - mFailedPatternAttemptsSinceLastTimeout;
                if (retry >= 0) {
                    if (retry == 0) {
                        //密码超过五次 逻辑
                        Intent intent = new Intent(UnlockGesturePasswordActivity.this, UserRegisterAndLoginActivity.class);
                        editor.putString(UserAppConst.Colour_login_mobile + UserAppConst.GESTURE_OPENED, GESTURE_PWD_SET_FIVE_ERROR);
                        editor.commit();
                        startActivity(intent);
                        finish();
                    }
                    mHeadTextView.setText(getResources().getString(R.string.lockpattern_error_input) + retry + getResources().getString(R.string.lockpattern_remain_number));
                    mHeadTextView.setTextColor(Color.RED);
                    mHeadTextView.startAnimation(mShakeAnim);//文字抖动动画效果
                }
                if (mFailedPatternAttemptsSinceLastTimeout >= LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT) {
                    //超过五次密码错误，跳转到普通登录页面
                    mLockPatternView.clearPattern();
                    Intent intent = new Intent(UnlockGesturePasswordActivity.this, UserRegisterAndLoginActivity.class);
                    editor.putString(UserAppConst.Colour_login_mobile + UserAppConst.GESTURE_OPENED, GESTURE_PWD_SET_FIVE_ERROR);
                    editor.commit();
                    startActivity(intent);
                    finish();
                } else {
                    mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
                }
            }
        }

        public void onPatternCellAdded(List<LockPatternView.Cell> pattern) {

        }

        private void patternInProgress() {

        }
    };

    /**
     * Oauth2.0的Access_token获取
     */
    private void getOauth2() {
        //type:登录的类型 1=>传统登录方式，提交手机号和密码认证；2=>手势密码；3=>短信密码
        //username为手机号
        String username = mShared.getString(UserAppConst.Colour_login_mobile, "0");
        mUserModel.getAuthToken(4, username, LockPatternUtils.convertToString(mChosenPattern), "2", true, this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            getOauth2();
        } else if (resultCode == 500) {
            getOauth2();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 发送本地通知:登录成功
     */
    private void sendNotification() {

        LocalBroadcastManager mLocalBroadcastManager = LocalBroadcastManager
                .getInstance(this);
        Intent data = new Intent();
        data.setAction(Constant.ACTION_LOGIN_FINISH_COMPLETED);
        mLocalBroadcastManager.sendBroadcast(data);
    }

    private int isWhite = 0;
    private String hotLine = "4008893893";

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                String forbidNotice = "";
                if (!TextUtils.isEmpty(result)) {
                    try {
                        CheckWhiteEntity checkWhiteEntity = GsonUtils.gsonToBean(result, CheckWhiteEntity.class);
                        CheckWhiteEntity.ContentBean contentBean = checkWhiteEntity.getContent();
                        isWhite = contentBean.getIs_white();
                        forbidNotice = contentBean.getNotice();
                        hotLine = contentBean.getHotLine();
                        if (TextUtils.isEmpty(forbidNotice)) {
                            forbidNotice = getResources().getString(R.string.change_pswd_verify);
                        }
                        if (TextUtils.isEmpty(hotLine)) {
                            hotLine = "4008893893";
                        }
                    } catch (Exception e) {
                        hotLine = "4008893893";
                    }
                }
                if (isWhite == 1) {
                    getOauth2();
                } else if (isWhite == 5) {
                    showReviewDialog(forbidNotice);
                } else if (isWhite == 6) {
                    Intent intent = new Intent(UnlockGesturePasswordActivity.this, UserSmsCodeActivity.class);
                    intent.putExtra(UserRegisterAndLoginActivity.MOBILE, mobile);
                    intent.putExtra(UserRegisterAndLoginActivity.PASSWORD, LockPatternUtils.convertToString(mChosenPattern));
                    intent.putExtra(UserRegisterAndLoginActivity.SMSTYPE, 4);
                    startActivityForResult(intent, LOCLGESTURE_CODE);
                } else {
                    Intent intent = new Intent(UnlockGesturePasswordActivity.this, UserSafetyVerficationActivity.class);
                    intent.putExtra(UserRegisterAndLoginActivity.MOBILE, mobile);
                    intent.putExtra(UserRegisterAndLoginActivity.PASSWORD, LockPatternUtils.convertToString(mChosenPattern));
                    intent.putExtra(UserRegisterAndLoginActivity.ISREGISTER, 1);
                    intent.putExtra(UserRegisterAndLoginActivity.ISWHITE, isWhite);
                    startActivityForResult(intent, LOCLGESTURE_CODE);
                }
                break;
            case 4:
                if (!TextUtils.isEmpty(result)) {
                    mUserModel.getUserInformation(7, true, this);
                } else {
                    mLockPatternView.setDisplayMode(LockPatternView.DisplayMode.Wrong);
                    mFailedPatternAttemptsSinceLastTimeout++;
                    int retry = LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT
                            - mFailedPatternAttemptsSinceLastTimeout;
                    if (retry >= 0) {
                        if (retry == 0) {
                            //密码超过五次 逻辑
                            Intent intent = new Intent(this, UserRegisterAndLoginActivity.class);
                            editor.putString(UserAppConst.Colour_login_mobile + UserAppConst.GESTURE_OPENED, GESTURE_PWD_SET_FIVE_ERROR);
                            editor.commit();
                            startActivity(intent);
                            finish();
                        }
                        mHeadTextView.setText(getResources().getString(R.string.lockpattern_error_input) + retry + getResources().getString(R.string.lockpattern_remain_number));
                        mHeadTextView.setTextColor(Color.RED);
                        mHeadTextView.startAnimation(mShakeAnim);//文字抖动动画效果
                    }
                    if (mFailedPatternAttemptsSinceLastTimeout >= LockPatternUtils.FAILED_ATTEMPTS_BEFORE_TIMEOUT) {
                        //超过五次密码错误，跳转到普通登录页面
                        mLockPatternView.clearPattern();
                        Intent intent = new Intent(this, UserRegisterAndLoginActivity.class);
                        editor.putString(UserAppConst.Colour_login_mobile + UserAppConst.GESTURE_OPENED, GESTURE_PWD_SET_FIVE_ERROR);
                        editor.commit();
                        startActivity(intent);
                        finish();
                    } else {
                        mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
                    }
                    Map<String, String> loginMap = new HashMap<String, String>();
                    loginMap.put("mobile", mobile);
                    TCAgent.onEvent(getApplicationContext(), "201006", "", loginMap);
                }
                break;
            case 5:
                ToastUtil.toastShow(this, getResources().getString(R.string.user_login_success));
                editor.putBoolean(UserAppConst.Colour_user_login, true);
                editor.commit();
                Message msg = new Message();
                msg.what = UserMessageConstant.SIGN_IN_SUCCESS;
                EventBus.getDefault().post(msg);
                sendNotification();
                finish();
                overridePendingTransition(0, 0);
                break;
            case 7:
                if (!TextUtils.isEmpty(result)) {
                    userInitImData(UnlockGesturePasswordActivity.this, shared);
                    mTokenModel.getToken(5, 3, true, this);
                }
                break;
        }
    }


    /**
     * 用户更换手机号是审核中的
     *
     * @param tips String提示文字
     */
    private CustomDialog reviewDialog;

    private void showReviewDialog(String tips) {
        reviewDialog = new CustomDialog(UnlockGesturePasswordActivity.this, R.style.custom_dialog_theme);
        reviewDialog.show();
        reviewDialog.setCancelable(false);
        reviewDialog.dialog_content.setText(tips);
        reviewDialog.dialog_line.setVisibility(View.VISIBLE);
        reviewDialog.dialog_button_ok.setGravity(Gravity.CENTER_HORIZONTAL);
        reviewDialog.dialog_button_cancel.setText(getResources().getString(R.string.message_define));
        reviewDialog.dialog_button_ok.setText(getResources().getString(R.string.user_contact_service));
        reviewDialog.dialog_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reviewDialog.dismiss();
            }
        });
        reviewDialog.dialog_button_ok.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (AndPermission.hasPermission(UnlockGesturePasswordActivity.this, Manifest.permission.CALL_PHONE)) {
                        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hotLine));//跳转到拨号界面，同时传递电话号码
                        startActivity(dialIntent);
                    } else {
                        ArrayList<String> permission = new ArrayList<>();
                        permission.add(Manifest.permission.CALL_PHONE);
                        if (AndPermission.hasAlwaysDeniedPermission(UnlockGesturePasswordActivity.this, permission)) {
                            ToastUtil.toastShow(getApplicationContext(), getResources().getString(R.string.user_callpermission_notice));
                        } else {
                            AndPermission.with(UnlockGesturePasswordActivity.this)
                                    .requestCode(REQUEST_CALLPHONE)
                                    .permission(Manifest.permission.CALL_PHONE)
                                    .callback(permissionListener)
                                    .start();
                        }
                    }
                } else {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hotLine));//跳转到拨号界面，同时传递电话号码
                    startActivity(dialIntent);
                }
                reviewDialog.dismiss();
            }
        });
    }

    /**
     * 回调监听。
     */
    private PermissionListener permissionListener = new PermissionListener() {
        @Override
        public void onSucceed(int requestCode, List<String> grantPermissions) {
            switch (requestCode) {
                case REQUEST_CALLPHONE: {
                    Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + hotLine));//跳转到拨号界面，同时传递电话号码
                    startActivity(dialIntent);
                    break;
                }
            }
        }

        @Override
        public void onFailed(int requestCode, @NonNull List<String> deniedPermissions) {
            if (AndPermission.hasAlwaysDeniedPermission(UnlockGesturePasswordActivity.this, deniedPermissions)) {
                ToastUtil.toastShow(getApplicationContext(), "拨号权限被禁止，请去开启该权限");
            }
        }
    };
}
