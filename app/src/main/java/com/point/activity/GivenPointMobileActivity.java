package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
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
import com.im.entity.UserIdInforEntity;
import com.nohttp.utils.GsonUtils;
import com.point.entity.PointAccountLimitEntity;
import com.point.model.PointModel;
import com.user.UserAppConst;
import com.user.UserMessageConstant;

import cn.net.cyberway.R;

import static com.point.activity.GivenPointAmountActivity.GIVENMOBILE;
import static com.point.activity.PointTransactionListActivity.POINTTPANO;

/***
 * 赠送积分输入手机号
 */
public class GivenPointMobileActivity extends BaseActivity implements View.OnClickListener, TextWatcher, NewHttpResponse {


    private ImageView mBack;
    private TextView mTitle;
    private TextView user_top_view_right;
    private ClearEditText input_given_mobile;//赠送人的手机号
    private Button btn_next_step;
    private TextView tv_remain_notice;
    private String givePhone = "";
    private String pano;//积分或饭票的类型
    private String keyword_sign; //积分或饭票的标识
    private PointModel pointModel;
    private boolean canGiven = false;
    private int last_times;//剩余次数
    private int last_amount;//剩余金额


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setContentView(R.layout.activity_point_given_mobile);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        user_top_view_right = findViewById(R.id.user_top_view_right);
        input_given_mobile = findViewById(R.id.input_given_mobile);
        btn_next_step = findViewById(R.id.btn_next_step);
        tv_remain_notice = findViewById(R.id.tv_remain_notice);
        mBack.setOnClickListener(this);
        btn_next_step.setEnabled(false);
        btn_next_step.setOnClickListener(this);
        keyword_sign = shared.getString(UserAppConst.COLOUR_WALLET_KEYWORD_SIGN, "积分");
        mTitle.setText(keyword_sign + "赠送");
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setText("记录");
        user_top_view_right.setOnClickListener(this::onClick);
        input_given_mobile.addTextChangedListener(this);
        Intent intent = getIntent();
        pano = intent.getStringExtra(POINTTPANO);
        givePhone = intent.getStringExtra(GIVENMOBILE);
        pointModel = new PointModel(GivenPointMobileActivity.this);
        pointModel.getAccountLimit(0, pano, GivenPointMobileActivity.this);
        if (!EventBus.getDefault().isregister(GivenPointMobileActivity.this)) {
            EventBus.getDefault().register(GivenPointMobileActivity.this);
        }
        if (!TextUtils.isEmpty(givePhone)) {
            input_given_mobile.setText(givePhone);
            input_given_mobile.setSelection(givePhone.length());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                Intent intent = new Intent(GivenPointMobileActivity.this, GivenPointHistoryActivity.class);
                intent.putExtra(POINTTPANO, pano);
                startActivityForResult(intent, 200);
                break;
            case R.id.btn_next_step:
                if (shared.getString(UserAppConst.Colour_login_mobile, "").equals(givePhone)) {
                    ToastUtil.toastShow(GivenPointMobileActivity.this, "不能给自己赠送" + keyword_sign);
                } else {
                    pointModel.getUserInfor(1, givePhone, GivenPointMobileActivity.this);
                }
                break;
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.POINT_SUCCESS_RETURN:
                finish();
                break;
            case UserMessageConstant.POINT_CONTINUE_GIVEN:
                pointModel.getAccountLimit(0, pano, GivenPointMobileActivity.this);
                break;

        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(GivenPointMobileActivity.this)) {
            EventBus.getDefault().unregister(GivenPointMobileActivity.this);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        givePhone = s.toString().trim();
        setBtnClick();
    }


    private void setBtnClick() {
        if (TextUtils.isEmpty(givePhone) || 11 != givePhone.length() || !canGiven) {
            btn_next_step.setEnabled(false);
            btn_next_step.setBackgroundResource(R.drawable.point_password_default_bg);
        } else {
            btn_next_step.setEnabled(true);
            btn_next_step.setBackgroundResource(R.drawable.point_password_click_bg);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            givePhone = data.getStringExtra(GIVENMOBILE);
            input_given_mobile.setText(givePhone);
            input_given_mobile.setSelection(givePhone.length());
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    PointAccountLimitEntity pointAccountLimitEntity = GsonUtils.gsonToBean(result, PointAccountLimitEntity.class);
                    PointAccountLimitEntity.ContentBean contentBean = pointAccountLimitEntity.getContent();
                    last_times = contentBean.getLast_times();
                    last_amount = contentBean.getLast_amount();
                    if (last_amount > 0 && last_times > 0) {
                        canGiven = true;
                    } else {
                        canGiven = false;
                    }
                    setBtnClick();
                    tv_remain_notice.setText("今天可赠送" + last_times + "次，剩余额度" + last_amount * 1.0f / 100 + keyword_sign);
                } catch (Exception e) {

                }
                break;
            case 1:
                try {
                    UserIdInforEntity userIdInforEntity = GsonUtils.gsonToBean(result, UserIdInforEntity.class);
                    UserIdInforEntity.ContentBean contentBean = userIdInforEntity.getContent();
                    String real_name = contentBean.getReal_name();
                    String user_name = contentBean.getName();
                    String nick_name = contentBean.getNick_name();
                    String show_name;
                    if (!TextUtils.isEmpty(real_name)) {
                        show_name = real_name;
                    } else {
                        if (!TextUtils.isEmpty(user_name)) {
                            show_name = user_name;
                        } else {
                            show_name = nick_name;
                        }
                    }
                    Intent amount_Intent = new Intent(GivenPointMobileActivity.this, GivenPointAmountActivity.class);
                    amount_Intent.putExtra(POINTTPANO, pano);
                    amount_Intent.putExtra(GIVENMOBILE, givePhone);
                    amount_Intent.putExtra(GivenPointAmountActivity.USERPORTRAIT, contentBean.getPortrait());
                    amount_Intent.putExtra(GivenPointAmountActivity.USERID, contentBean.getId());
                    amount_Intent.putExtra(GivenPointAmountActivity.USERNAME, show_name);
                    amount_Intent.putExtra(GivenPointAmountActivity.LASTAMOUNT, last_amount);
                    amount_Intent.putExtra(GivenPointAmountActivity.LASTTIME, last_times);
                    startActivity(amount_Intent);
                } catch (Exception e) {

                }
                break;
        }
    }
}
