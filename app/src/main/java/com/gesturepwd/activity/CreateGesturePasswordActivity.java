package com.gesturepwd.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.external.eventbus.EventBus;
import com.gesturepwd.utils.LockPatternUtils;
import com.gesturepwd.view.LockPatternView;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.entity.VerifyResultEntity;
import com.user.model.NewUserModel;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.BroadcastReceiverActivity;

import static com.BeeFramework.model.Constants.GESTURE_PWD_SET_FIVE_ERROR;
import static com.gesturepwd.view.LockPatternView.Cell;
import static com.gesturepwd.view.LockPatternView.DisplayMode;
import static com.gesturepwd.view.LockPatternView.INVISIBLE;
import static com.gesturepwd.view.LockPatternView.OnPatternListener;
import static com.gesturepwd.view.LockPatternView.VISIBLE;

public class CreateGesturePasswordActivity extends BaseActivity implements
        OnClickListener, NewHttpResponse {
    private static final int ID_EMPTY_MESSAGE = -1;
    private static final String KEY_UI_STAGE = "uiStage";
    private static final String KEY_PATTERN_CHOICE = "chosenPattern";
    private LockPatternView mLockPatternView;
    private FrameLayout czy_title_layout;
    private Button mFooterRightButton;
    private Button mFooterLeftButton;
    protected TextView mHeaderText;
    protected List<Cell> mChosenPattern = null;
    private CreateGesturePasswordActivity.Stage mUiStage = CreateGesturePasswordActivity.Stage.Introduction;
    private View mPreviewViews[][] = new View[3][3];
    private boolean isChangePwd = false;//判断是首次设置密码还是修改密码进入
    private NewUserModel newUserModel;
    private TextView tv_title;      //标题
    private ImageView back;
    private TextView function;      //重置
    private int num = 5;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    ToastUtil.toastShow(CreateGesturePasswordActivity.this, getResources().getString(R.string.lockpattern_set_success));
                    finish();
                } else {
                    mLockPatternView.clearPattern();
                    updateStage(CreateGesturePasswordActivity.Stage.FirstChoiceValid);
                    ToastUtil.toastShow(CreateGesturePasswordActivity.this, getResources().getString(R.string.lockpattern_set_fail));
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        VerifyResultEntity verifyResultEntity = GsonUtils.gsonToBean(result, VerifyResultEntity.class);
                        if ("1".equals(verifyResultEntity.getContent().getVerify_result())) {
                            updateStage(CreateGesturePasswordActivity.Stage.Introduction);
                            ToastUtil.toastShow(this, getResources().getString(R.string.lockpattern_old_verify));
                        } else {
                            updateStage(CreateGesturePasswordActivity.Stage.ChangePwdConfirmWrong);
                            mLockPatternView.clearPattern();
                        }
                    } catch (Exception e) {
                        updateStage(CreateGesturePasswordActivity.Stage.ChangePwdConfirmWrong);
                        mLockPatternView.clearPattern();
                    }
                } else {
                    updateStage(CreateGesturePasswordActivity.Stage.ChangePwdConfirmWrong);
                    mLockPatternView.clearPattern();

                }
                break;
        }
    }

    /**
     * The states of the left footer button.
     */
    enum LeftButtonMode {
        Cancel(android.R.string.cancel, true), CancelDisabled(
                android.R.string.cancel, false), Retry(
                R.string.lockpattern_retry_button_text, true), RetryDisabled(
                R.string.lockpattern_retry_button_text, false), Gone(
                ID_EMPTY_MESSAGE, false);

        /**
         * @param text    The displayed text for this mode.
         * @param enabled Whether the button should be enabled.
         */
        LeftButtonMode(int text, boolean enabled) {
            this.text = text;
            this.enabled = enabled;
        }

        final int text;
        final boolean enabled;
    }

    /**
     * The states of the right button.
     */
    enum RightButtonMode {
        Continue(R.string.lockpattern_continue_button_text, true), ContinueDisabled(
                R.string.lockpattern_continue_button_text, false), Confirm(
                R.string.lockpattern_confirm_button_text, true), ConfirmDisabled(
                R.string.lockpattern_confirm_button_text, false), Ok(
                android.R.string.ok, true);

        /**
         * @param text    The displayed text for this mode.
         * @param enabled Whether the button should be enabled.
         */
        RightButtonMode(int text, boolean enabled) {
            this.text = text;
            this.enabled = enabled;
        }

        final int text;
        final boolean enabled;
    }

    /**
     * Keep track internally of where the user is in choosing a pattern.
     */
    protected enum Stage {

        Introduction(R.string.lockpattern_recording_intro_header,
                CreateGesturePasswordActivity.LeftButtonMode.Cancel, CreateGesturePasswordActivity.RightButtonMode.ContinueDisabled,
                ID_EMPTY_MESSAGE, true),
        HelpScreen(
                R.string.lockpattern_settings_help_how_to_record,
                CreateGesturePasswordActivity.LeftButtonMode.Gone, CreateGesturePasswordActivity.RightButtonMode.Ok, ID_EMPTY_MESSAGE,
                false),
        ChoiceTooShort(
                R.string.lockpattern_recording_incorrect_too_short,
                CreateGesturePasswordActivity.LeftButtonMode.Retry, CreateGesturePasswordActivity.RightButtonMode.ContinueDisabled,
                ID_EMPTY_MESSAGE, true),
        FirstChoiceValid(
                R.string.lockpattern_pattern_entered_header,
                CreateGesturePasswordActivity.LeftButtonMode.Retry, CreateGesturePasswordActivity.RightButtonMode.Continue,
                ID_EMPTY_MESSAGE, false),
        NeedToConfirm(
                R.string.lockpattern_need_to_confirm, CreateGesturePasswordActivity.LeftButtonMode.Cancel,
                CreateGesturePasswordActivity.RightButtonMode.ConfirmDisabled, ID_EMPTY_MESSAGE, true),
        ConfirmWrong(
                R.string.lockpattern_need_to_unlock_wrong,
                CreateGesturePasswordActivity.LeftButtonMode.Cancel, CreateGesturePasswordActivity.RightButtonMode.ConfirmDisabled,
                ID_EMPTY_MESSAGE, true),
        ChoiceConfirmed(
                R.string.lockpattern_pattern_confirmed_header,
                CreateGesturePasswordActivity.LeftButtonMode.Cancel, CreateGesturePasswordActivity.RightButtonMode.Confirm,
                ID_EMPTY_MESSAGE, false),
        ChangePwdIntroduction(
                R.string.lockpattern_changepwd,
                CreateGesturePasswordActivity.LeftButtonMode.Cancel, CreateGesturePasswordActivity.RightButtonMode.ContinueDisabled,
                ID_EMPTY_MESSAGE, true),
        ChangePwdChoiceTooShort(
                R.string.lockpattern_recording_incorrect_too_short,
                CreateGesturePasswordActivity.LeftButtonMode.Retry, CreateGesturePasswordActivity.RightButtonMode.ContinueDisabled,
                ID_EMPTY_MESSAGE, true),
        ChangePwdConfirmWrong(
                R.string.lockpattern_changepwderror,
                CreateGesturePasswordActivity.LeftButtonMode.Cancel, CreateGesturePasswordActivity.RightButtonMode.ConfirmDisabled,
                ID_EMPTY_MESSAGE, true);


        /**
         * @param headerMessage  The message displayed at the top.
         * @param leftMode       The mode of the left button.
         * @param rightMode      The mode of the right button.
         * @param footerMessage  The footer message.
         * @param patternEnabled Whether the pattern widget is enabled.
         */
        Stage(int headerMessage, CreateGesturePasswordActivity.LeftButtonMode leftMode,
              CreateGesturePasswordActivity.RightButtonMode rightMode, int footerMessage,
              boolean patternEnabled) {
            this.headerMessage = headerMessage;
            this.leftMode = leftMode;
            this.rightMode = rightMode;
            this.footerMessage = footerMessage;
            this.patternEnabled = patternEnabled;
        }

        final int headerMessage;
        final CreateGesturePasswordActivity.LeftButtonMode leftMode;
        final CreateGesturePasswordActivity.RightButtonMode rightMode;
        final int footerMessage;
        final boolean patternEnabled;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gesturepassword_create);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        back = (ImageView) findViewById(R.id.user_top_view_back);
        back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        function = (TextView) findViewById(R.id.user_top_view_right);
        function.setVisibility(View.INVISIBLE);
        function.setText(getResources().getString(R.string.lockpattern_reset));
        function.setOnClickListener(this);
        isChangePwd = getIntent().getBooleanExtra("isChangePwd", false);//判断是否是修改密码进入
        if (isChangePwd) {
            tv_title.setText(getResources().getString(R.string.title_change_gesturepawd));
        } else {
            tv_title.setText(getResources().getString(R.string.title_set_gesturepawd));
        }
        newUserModel = new NewUserModel(this);
        mLockPatternView = (LockPatternView) this
                .findViewById(R.id.gesturepwd_create_lockview);
        mHeaderText = (TextView) findViewById(R.id.gesturepwd_create_text);
        mLockPatternView.setOnPatternListener(mChooseNewLockPatternListener);
        mLockPatternView.setTactileFeedbackEnabled(true);

        mFooterRightButton = (Button) this.findViewById(R.id.right_btn);
        mFooterLeftButton = (Button) this.findViewById(R.id.reset_btn);
        mFooterRightButton.setOnClickListener(this);
        mFooterLeftButton.setOnClickListener(this);
        int height = Utils.getDeviceHeight(this);
        if (height < 850) {//适配超低分辨率屏幕
            ViewGroup.LayoutParams layoutParams = (ViewGroup.LayoutParams) mLockPatternView.getLayoutParams();
            layoutParams.height = Utils.dip2px(this, 230);
            mLockPatternView.setLayoutParams(layoutParams);
        }
        initPreviewViews();
        if (isChangePwd) {//修改密码
            updateStage(CreateGesturePasswordActivity.Stage.ChangePwdIntroduction);
        } else {
            updateStage(CreateGesturePasswordActivity.Stage.Introduction);
        }
        ThemeStyleHelper.rightTexteFrameLayout(getApplicationContext(), czy_title_layout, back, tv_title, function);
    }

    private void initPreviewViews() {
        mPreviewViews = new View[3][3];
        mPreviewViews[0][0] = findViewById(R.id.gesturepwd_setting_preview_0);
        mPreviewViews[0][0].setBackgroundResource(R.drawable.gesture_corners2);
        mPreviewViews[0][1] = findViewById(R.id.gesturepwd_setting_preview_1);
        mPreviewViews[0][2] = findViewById(R.id.gesturepwd_setting_preview_2);
        mPreviewViews[1][0] = findViewById(R.id.gesturepwd_setting_preview_3);
        mPreviewViews[1][1] = findViewById(R.id.gesturepwd_setting_preview_4);
        mPreviewViews[1][2] = findViewById(R.id.gesturepwd_setting_preview_5);
        mPreviewViews[2][0] = findViewById(R.id.gesturepwd_setting_preview_6);
        mPreviewViews[2][1] = findViewById(R.id.gesturepwd_setting_preview_7);
        mPreviewViews[2][2] = findViewById(R.id.gesturepwd_setting_preview_8);
    }

    private void updatePreviewViews() {
        if (mChosenPattern == null)
            return;
        for (Cell cell : mChosenPattern) {
            mPreviewViews[cell.getRow()][cell.getColumn()]
                    .setBackgroundResource(R.drawable.gesture_corners);

        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(KEY_UI_STAGE, mUiStage.ordinal());
        if (mChosenPattern != null) {
            outState.putString(KEY_PATTERN_CHOICE,
                    LockPatternUtils.patternToString(mChosenPattern));
        }
    }


    private Runnable mClearPatternRunnable = new Runnable() {
        public void run() {
            mLockPatternView.clearPattern();
        }
    };

    protected OnPatternListener mChooseNewLockPatternListener = new OnPatternListener() {

        public void onPatternStart() {
            mHeaderText.setTextColor(getResources().getColor(R.color.black_text_color));
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
            patternInProgress();
        }

        public void onPatternCleared() {
            mHeaderText.setTextColor(getResources().getColor(R.color.black_text_color));
            mLockPatternView.removeCallbacks(mClearPatternRunnable);
        }


        public void onPatternDetected(List<Cell> pattern) {
            mHeaderText.setTextColor(getResources().getColor(R.color.black_text_color));
            if (pattern == null)
                return;
            // Log.i("way", "result = " + pattern.toString());
            if (mUiStage == CreateGesturePasswordActivity.Stage.NeedToConfirm
                    || mUiStage == CreateGesturePasswordActivity.Stage.ConfirmWrong) {
                if (mChosenPattern == null)
                    throw new IllegalStateException(
                            "null chosen pattern in stage 'need to confirm");
                if (mChosenPattern.equals(pattern)) {
                    updateStage(CreateGesturePasswordActivity.Stage.ChoiceConfirmed);
                } else {
                    updateStage(CreateGesturePasswordActivity.Stage.ConfirmWrong);
                    function.setVisibility(VISIBLE);
                }
            } else if (mUiStage == CreateGesturePasswordActivity.Stage.Introduction
                    || mUiStage == CreateGesturePasswordActivity.Stage.ChoiceTooShort) {
                if (pattern.size() < LockPatternUtils.MIN_LOCK_PATTERN_SIZE) {
                    updateStage(CreateGesturePasswordActivity.Stage.ChoiceTooShort);
                } else {
                    mChosenPattern = new ArrayList<Cell>(
                            pattern);
                    updateStage(CreateGesturePasswordActivity.Stage.FirstChoiceValid);
                }
            } else if (mUiStage == CreateGesturePasswordActivity.Stage.ChangePwdIntroduction) {
                if (pattern.size() >= LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {
                    newUserModel.verifyGesture(1, LockPatternUtils.convertToString(pattern), CreateGesturePasswordActivity.this);
                } else {
                    updateStage(CreateGesturePasswordActivity.Stage.ChangePwdConfirmWrong);
                }
                // 如果是修改密码，保存输入的手势，并与服务器比对，如果匹配成功，更新界面，输入新的密码，否则提示错误
            } else if (mUiStage == CreateGesturePasswordActivity.Stage.ChangePwdConfirmWrong) {
                if (pattern.size() >= LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {
                    newUserModel.verifyGesture(1, LockPatternUtils.convertToString(pattern), CreateGesturePasswordActivity.this);
                } else {
                    updateStage(CreateGesturePasswordActivity.Stage.ChangePwdConfirmWrong);
                }
            } else if (mUiStage == CreateGesturePasswordActivity.Stage.ChangePwdChoiceTooShort) {
                if (pattern.size() >= LockPatternUtils.MIN_PATTERN_REGISTER_FAIL) {
                    newUserModel.verifyGesture(1, LockPatternUtils.convertToString(pattern), CreateGesturePasswordActivity.this);
                } else {
                    updateStage(CreateGesturePasswordActivity.Stage.ChangePwdConfirmWrong);
                }
            } else {
                throw new IllegalStateException("Unexpected stage " + mUiStage
                        + " when " + "entering the pattern.");
            }
        }

        public void onPatternCellAdded(List<Cell> pattern) {

        }

        //绘制手势密码中
        private void patternInProgress() {
            mHeaderText.setText(R.string.lockpattern_recording_inprogress);
            mHeaderText.setTextColor(getResources().getColor(R.color.black_text_color));
            mFooterLeftButton.setEnabled(false);
            mFooterRightButton.setEnabled(false);
        }
    };

    //更新绘制状态
    private void updateStage(CreateGesturePasswordActivity.Stage stage) {
        mUiStage = stage;
        if (stage == CreateGesturePasswordActivity.Stage.ChoiceTooShort) {
            mHeaderText.setText(getResources().getString(stage.headerMessage,
                    LockPatternUtils.MIN_LOCK_PATTERN_SIZE));
        } else {
            mHeaderText.setText(stage.headerMessage);
        }

        // same for whether the patten is enabled
        if (stage.patternEnabled) {
            mLockPatternView.enableInput();
        } else {
            mLockPatternView.disableInput();
        }

        mLockPatternView.setDisplayMode(DisplayMode.Correct);

        switch (mUiStage) {
            case Introduction:
                mHeaderText.setTextColor(getResources().getColor(R.color.black_text_color));
                mLockPatternView.clearPattern();
                break;
            case HelpScreen:
//			mLockPatternView.setPattern(DisplayMode.Animate, mAnimatePattern);
                break;
            case ChoiceTooShort:
                mHeaderText.setTextColor(Color.RED);
                mLockPatternView.setDisplayMode(DisplayMode.Wrong);
                postClearPatternRunnable();
                break;
            case FirstChoiceValid:
                mHeaderText.setTextColor(getResources().getColor(R.color.black_text_color));
                updateStage(CreateGesturePasswordActivity.Stage.NeedToConfirm);
                break;
            case NeedToConfirm:
                mHeaderText.setTextColor(getResources().getColor(R.color.black_text_color));
                mLockPatternView.clearPattern();
                updatePreviewViews();
                break;
            case ConfirmWrong:
                mHeaderText.setTextColor(Color.RED);
                mLockPatternView.setDisplayMode(DisplayMode.Wrong);
                postClearPatternRunnable();
                break;
            case ChoiceConfirmed:
                mHeaderText.setTextColor(getResources().getColor(R.color.black_text_color));
                saveChosenPatternAndFinish();
                break;
            case ChangePwdIntroduction:
                mHeaderText.setTextColor(getResources().getColor(R.color.black_text_color));
                mLockPatternView.clearPattern();
                break;
            case ChangePwdConfirmWrong:
                mHeaderText.setTextColor(Color.RED);
                mLockPatternView.setDisplayMode(DisplayMode.Wrong);
                postClearPatternRunnable();
                num--;
                mHeaderText.setText(getResources().getString(R.string.lockpattern_error_input) + num + getResources().getString(R.string.lockpattern_remain_number));
                if (num == 0) {//验证手势密码错误超过5次，强制退出登录
                    editor.putBoolean(UserAppConst.IS_LOGIN, false);
                    editor.putString(UserAppConst.Colour_login_mobile + UserAppConst.GESTURE_OPENED, GESTURE_PWD_SET_FIVE_ERROR);
                    editor.apply();
                    sendBroadCast();
                    Message msg = new Message();
                    msg.what = UserMessageConstant.LOGOUT;//退出登录之后，
                    EventBus.getDefault().post(msg);
                    finish();
                }
                break;
            case ChangePwdChoiceTooShort:
                mHeaderText.setTextColor(Color.RED);
                mLockPatternView.setDisplayMode(DisplayMode.Wrong);
                postClearPatternRunnable();
                break;
        }


    }

    // clear the wrong pattern unless they have started a new one
    // already
    private void postClearPatternRunnable() {
        mLockPatternView.removeCallbacks(mClearPatternRunnable);
        mLockPatternView.postDelayed(mClearPatternRunnable, 2000);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                mChosenPattern = null;
                updateStage(CreateGesturePasswordActivity.Stage.Introduction);
                function.setVisibility(INVISIBLE);
                mPreviewViews[0][0].setBackgroundResource(R.drawable.gesture_corners2);
                mPreviewViews[0][1].setBackgroundResource(R.drawable.gesture_corners2);
                mPreviewViews[0][2].setBackgroundResource(R.drawable.gesture_corners2);
                mPreviewViews[1][0].setBackgroundResource(R.drawable.gesture_corners2);
                mPreviewViews[1][1].setBackgroundResource(R.drawable.gesture_corners2);
                mPreviewViews[1][2].setBackgroundResource(R.drawable.gesture_corners2);
                mPreviewViews[2][0].setBackgroundResource(R.drawable.gesture_corners2);
                mPreviewViews[2][1].setBackgroundResource(R.drawable.gesture_corners2);
                mPreviewViews[2][2].setBackgroundResource(R.drawable.gesture_corners2);
                break;
        }
    }

    /**
     * 发送广播关闭之前页面
     */
    public void sendBroadCast() {
        Intent intent = new Intent();
        intent.setAction(BroadcastReceiverActivity.GESTURE);
        sendBroadcast(intent);
    }

    private void saveChosenPatternAndFinish() {
        String pwd = LockPatternUtils.convertToString(mChosenPattern);//转换成字符密码
        newUserModel.setGesturePassword(0, pwd, this);
    }
}
