package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.external.eventbus.EventBus;
import com.user.UserAppConst;

import cn.net.cyberway.R;

import static com.user.UserMessageConstant.POINT_CONTINUE_GIVEN;
import static com.user.UserMessageConstant.POINT_SUCCESS_RETURN;

/***
 * 积分赠送的结果
 */
public class GivenPointResultActivity extends BaseActivity implements View.OnClickListener {


    private ImageView mBack;
    private TextView mTitle;
    private TextView tv_given_amount;//显示赠送的金额
    private TextView tv_given_username;//显示目标账号的
    private TextView tv_continue_given;//继续赠送
    private TextView tv_return; //返回积分首页

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_given_result);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        tv_given_amount = findViewById(R.id.tv_given_amount);
        tv_given_username = findViewById(R.id.tv_given_username);
        tv_continue_given = findViewById(R.id.tv_continue_given);
        tv_return = findViewById(R.id.tv_return);
        mBack.setOnClickListener(this);
        tv_continue_given.setOnClickListener(this);
        tv_return.setOnClickListener(this);
        mTitle.setText(shared.getString(UserAppConst.COLOUR_WALLET_KEYWORD_SIGN, "积分") + "赠送");
        Intent intent = getIntent();
        tv_given_amount.setText(intent.getStringExtra(GivenPointAmountActivity.GIVENAMOUNT));
        tv_given_username.setText(intent.getStringExtra(GivenPointAmountActivity.GIVENMOBILE));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
            case R.id.tv_return:
                returnGivenPoint();
                break;
            case R.id.tv_continue_given:
                Message message = Message.obtain();
                message.what = POINT_CONTINUE_GIVEN;
                EventBus.getDefault().post(message);
                finish();
                break;
        }
    }

    private void returnGivenPoint(){
        EventBus eventBus = EventBus.getDefault();
        Message message1 = Message.obtain();
        message1.what = POINT_SUCCESS_RETURN;
        eventBus.post(message1);
        finish();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        returnGivenPoint();
    }
}
