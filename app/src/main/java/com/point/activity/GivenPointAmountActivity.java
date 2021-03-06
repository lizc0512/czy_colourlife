package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.ClearEditText;
import com.external.eventbus.EventBus;
import com.nohttp.utils.CashierInputFilter;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.point.entity.PointBalanceEntity;
import com.point.entity.PointTransactionTokenEntity;
import com.point.model.PointModel;
import com.point.password.PopEnterPassword;
import com.point.password.PopInputCodeView;
import com.realaudit.activity.RealCommonSubmitActivity;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.model.NewUserModel;

import cn.net.cyberway.R;

import static com.user.UserMessageConstant.POINT_GET_CODE;
import static com.user.UserMessageConstant.POINT_INPUT_CODE;
import static com.user.UserMessageConstant.POINT_INPUT_PAYPAWD;
import static com.user.UserMessageConstant.POINT_SHOW_CODE;

/***
 * 赠送积分输入金额
 */
public class GivenPointAmountActivity extends BaseActivity implements View.OnClickListener, TextWatcher, NewHttpResponse {

    public static final String GIVENMOBILE = "givenmobile";
    public static final String GIVENAMOUNT = "givenamount";
    public static final String LASTTIME = "lasttime";
    public static final String LASTAMOUNT = "lastamount";
    public static final String USERPORTRAIT = "userportrait";
    public static final String USERNAME = "username";
    public static final String USERID = "userid";
    private ImageView mBack;
    private TextView mTitle;
    private ImageView iv_given_photo;
    private TextView tv_given_username;
    private ClearEditText ed_given_amount;
    private TextView tv_hint_notice;
    private ClearEditText ed_given_remark;
    private TextView tv_remain_amount;
    private TextView tv_remain_notice;
    private Button btn_given;
    private String giveAmount;//赠送的金额
    private String givenMobile;//赠送的手机号
    private PointModel pointModel;
    private NewUserModel newUserModel;
    private String keyword_sign;//是积分还是饭票的标识
    private String pano;//饭票类型
    private String token;//交易时的传参
    private String state;//支付密码的状态
    private String order_no;//支付订单号
    private String dest_account;//目标用户的id
    private int last_time; //剩余次数
    private float last_amount;//剩余金额
    private float balanceAmount = 0.01f;//账户余额
    private int giveBalance;//赠送的金额(单位分)
    private String loginMobile;
    private PopInputCodeView popInputCodeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setContentView(R.layout.activity_point_given_amount);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        iv_given_photo = findViewById(R.id.iv_given_photo);
        tv_given_username = findViewById(R.id.tv_given_username);
        ed_given_amount = findViewById(R.id.ed_given_amount);
        tv_hint_notice = findViewById(R.id.tv_hint_notice);
        ed_given_remark = findViewById(R.id.ed_given_remark);
        tv_remain_amount = findViewById(R.id.tv_remain_amount);
        tv_remain_notice = findViewById(R.id.tv_remain_notice);
        btn_given = findViewById(R.id.btn_given);
        btn_given.setEnabled(false);
        mBack.setOnClickListener(this);
        btn_given.setOnClickListener(this);
        loginMobile = shared.getString(UserAppConst.Colour_login_mobile, "");
        keyword_sign = shared.getString(UserAppConst.COLOUR_WALLET_KEYWORD_SIGN, "积分");
        mTitle.setText(keyword_sign + "赠送");
        ed_given_amount.addTextChangedListener(this);
        Intent intent = getIntent();
        givenMobile = intent.getStringExtra(GIVENMOBILE);
        last_time = intent.getIntExtra(LASTTIME, 0);
        last_amount = intent.getIntExtra(LASTAMOUNT, 0);
        dest_account = intent.getStringExtra(USERID);
        last_amount = last_amount * 1.0f / 100;
        pano = intent.getStringExtra(PointTransactionListActivity.POINTTPANO);
        pointModel = new PointModel(GivenPointAmountActivity.this);
        newUserModel = new NewUserModel(GivenPointAmountActivity.this);
        pointModel.getAccountBalance(1, pano, GivenPointAmountActivity.this);
        if (last_time!=-1&&last_amount!=-1){ //积分白名单
            tv_remain_notice.setText("今日可赠送" + last_time + "次，剩余额度" + last_amount + keyword_sign);
        }
        if (!EventBus.getDefault().isregister(GivenPointAmountActivity.this)) {
            EventBus.getDefault().register(GivenPointAmountActivity.this);
        }
        CashierInputFilter cashierInputFilter = new CashierInputFilter(GivenPointAmountActivity.this, 1, 5000);
        ed_given_amount.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10), cashierInputFilter});
        String user_name = intent.getStringExtra(USERNAME);
        String user_portrait = intent.getStringExtra(USERPORTRAIT);
        tv_given_username.setText("正在向" + user_name + "\n" + "赠送" + keyword_sign);
        GlideImageLoader.loadImageDisplay(GivenPointAmountActivity.this, user_portrait, iv_given_photo);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_given:
                if (fastClick()) {
                    giveAmount = ed_given_amount.getText().toString().trim();
                    int length = giveAmount.length();
                    if (giveAmount.endsWith(".")) {
                        giveAmount = giveAmount.substring(0, length - 1);
                    } else if (giveAmount.startsWith("0") && !giveAmount.contains(".")) {
                        int pos = giveAmount.lastIndexOf('0');
                        giveAmount = giveAmount.substring(pos + 1, length);
                    }
                    float give_Amount = Float.valueOf(giveAmount);
                    if (give_Amount > balanceAmount) {
                        ToastUtil.toastShow(GivenPointAmountActivity.this, "赠送金额不能超过可用余额");
                        return;
                    }
                    if (last_amount!=-1){
                        if (give_Amount > last_amount) {
                            ToastUtil.toastShow(GivenPointAmountActivity.this, "赠送金额不能超过剩余额度");
                            return;
                        }
                    }
                    giveBalance = (int) (give_Amount * 100);
                    pointModel.getTransactionToken(3, GivenPointAmountActivity.this);
                }
                break;
        }
    }


    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.POINT_SUCCESS_RETURN:
            case UserMessageConstant.POINT_CONTINUE_GIVEN:
                finish();
                break;
            case POINT_INPUT_PAYPAWD://密码框输入密码
                String password = message.obj.toString();
                pointModel.transferTransaction(4, giveBalance, password, token, order_no, dest_account, pano,
                        ed_given_remark.getText().toString().trim(), GivenPointAmountActivity.this);
                break;
            case POINT_SHOW_CODE:
                popInputCodeView = new PopInputCodeView(GivenPointAmountActivity.this);
                // 显示窗口
                popInputCodeView.showAtLocation(findViewById(R.id.layoutContent),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0); // 设置layout在PopupWindow中显示的位置
                break;
            case POINT_GET_CODE: //获取验证码的接口
                getChangeDeviceCode();
                break;
            case POINT_INPUT_CODE: //验证码输入完成的接口校验验证码是否正确
                String code = (String) message.obj;
                if (null != popInputCodeView) {
                    popInputCodeView.dismiss();
                }
                pointModel.pointCheckCode(8, loginMobile, code, GivenPointAmountActivity.this);
                break;
            case UserMessageConstant.REAL_SUCCESS_STATE:
                if ("3".equals(state)) {
                    state = "2";
                    Intent pawd_intent = new Intent(GivenPointAmountActivity.this, ChangePawdTwoStepActivity.class);
                    startActivity(pawd_intent);
                } else {
                    showPayDialog();
                }
                break;
        }
    }


    private void getChangeDeviceCode() {
        newUserModel.getSmsCode(7, loginMobile, 7, 1, GivenPointAmountActivity.this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(GivenPointAmountActivity.this)) {
            EventBus.getDefault().unregister(GivenPointAmountActivity.this);
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
        giveAmount = s.toString().trim();
        if (TextUtils.isEmpty(giveAmount)) {
            tv_hint_notice.setVisibility(View.VISIBLE);
        } else {
            tv_hint_notice.setVisibility(View.GONE);
        }
        if (TextUtils.isEmpty(giveAmount) || giveAmount.equals("0") || giveAmount.equals("0.")
                || giveAmount.equals("0.0") || giveAmount.equals("0.00")) {
            btn_given.setEnabled(false);
            btn_given.setBackgroundResource(R.drawable.point_password_default_bg);
        } else {
            btn_given.setEnabled(true);
            btn_given.setBackgroundResource(R.drawable.point_password_click_bg);
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 1:
                try {
                    PointBalanceEntity pointBalanceEntity = GsonUtils.gsonToBean(result, PointBalanceEntity.class);
                    PointBalanceEntity.ContentBean contentBean = pointBalanceEntity.getContent();
                    balanceAmount = contentBean.getBalance() * 1.0f / 100;
                    CashierInputFilter cashierInputFilter;
                    if (balanceAmount <= last_amount) {
                        cashierInputFilter = new CashierInputFilter(GivenPointAmountActivity.this, 0, balanceAmount);
                    } else {
                        cashierInputFilter = new CashierInputFilter(GivenPointAmountActivity.this, 1, last_amount);
                    }
                    ed_given_amount.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10), cashierInputFilter});
                    tv_remain_amount.setText("可用余额:" + balanceAmount);
                } catch (Exception e) {

                }
                break;
            case 3:
                try {
                    PointTransactionTokenEntity pointTransactionTokenEntity = GsonUtils.gsonToBean(result, PointTransactionTokenEntity.class);
                    PointTransactionTokenEntity.ContentBean contentBean = pointTransactionTokenEntity.getContent();
                    token = contentBean.getToken();
                    state = contentBean.getState();
                    order_no = contentBean.getOrder_no();
                    String dev_change = contentBean.getDev_change();
                    switch (state) {
                        case "2"://已实名未设置支付密码
                            Intent intent = new Intent(GivenPointAmountActivity.this, ChangePawdTwoStepActivity.class);
                            startActivity(intent);
                            break;
                        case "3"://未实名未设置支付密码
                        case "4"://未实名已设置支付密码
                            Intent realIntent=new Intent(GivenPointAmountActivity.this, RealCommonSubmitActivity.class);
                            startActivity(realIntent);
                            break;
                        default://1已实名已设置支付密码
                            if ("1".equals(dev_change)) {
                                showCodeDialog();
                            } else {
                                showPayDialog();
                            }
                            break;
                    }
                } catch (Exception e) {

                }
                break;
            case 4://赠送成功  刷新首页的余额
                Message message = new Message();
                message.what = UserMessageConstant.SUREBTNCHECKET;
                EventBus.getDefault().post(message);
                Intent intent = new Intent(GivenPointAmountActivity.this, GivenPointResultActivity.class);
                intent.putExtra(GIVENMOBILE, givenMobile);
                intent.putExtra(GIVENAMOUNT, giveAmount);
                startActivityForResult(intent, 2000);
                break;
            case 7://短信验证码发送成功
                if (null != popInputCodeView) {
                    popInputCodeView.getCodeSuccess();
                }
                break;
            case 8://短信验证码验证成功
                showPayDialog();
                break;
        }
    }


    /**
     * 弹出更换设备支付的选择框
     **/
    private void showCodeDialog() {
        PointChangeDeviceDialog pointChangeDeviceDialog = new PointChangeDeviceDialog(GivenPointAmountActivity.this);
        pointChangeDeviceDialog.show();
    }

    /**
     * 输入短信验证码
     **/
    private void showPayDialog() {
        PopEnterPassword popEnterPassword = new PopEnterPassword(this);
        // 显示窗口
        popEnterPassword.show();
    }
}
