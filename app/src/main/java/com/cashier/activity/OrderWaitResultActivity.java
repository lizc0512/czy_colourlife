package com.cashier.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.Constants;
import com.BeeFramework.model.NewHttpResponse;
import com.cashier.modelnew.NewOrderPayModel;
import com.cashier.protocolchang.PayStatusEntity;
import com.external.eventbus.EventBus;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.UserMessageConstant;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.BroadcastReceiverActivity;

import static com.cashier.activity.NewOrderPayActivity.PAY_CHANNEL;

/**
 * @name ${yuansk}
 * @class name：com.cashier.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/3/27 12:45
 * @change
 * @chang time
 * @class describe
 */

public class OrderWaitResultActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private FrameLayout czy_title_layout;
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private RelativeLayout wait_result_layout;
    private TextView tv_wait_time;
    private ImageView iv_progress;

    private LinearLayout time_out_layout;
    private TextView btn_see_order;
    private TextView btn_refresh;

    private String colorSn = "";
    private String payChannelId = "";
    private NewOrderPayModel newOrderPayModel;
    private AnimationDrawable animationDrawable = null;
    private boolean countFinish = false; //定时器五秒倒计时是否结束
    private BroadcastReceiverActivity broadcast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_wait_result);
        initView();
        colorSn = getIntent().getStringExtra(NewOrderPayActivity.ORDER_SN);
        payChannelId = getIntent().getStringExtra(PAY_CHANNEL);
        newOrderPayModel = new NewOrderPayModel(this);
        countFinish = false;
        initCountTime();
        broadcast = new BroadcastReceiverActivity(this);
        IntentFilter intentFilter = new IntentFilter(BroadcastReceiverActivity.FINISH_PAYMENT_ACTIVYTY);
        registerReceiver(broadcast, intentFilter);
    }


    private MyTimeCount myTimeCount;

    private void initCountTime() {
        if (null != myTimeCount) {
            myTimeCount.cancel();
        }
        time_out_layout.setVisibility(View.GONE);
        wait_result_layout.setVisibility(View.VISIBLE);
        String text = "正在支付,请等待...5S";
        tv_wait_time.setText(text);
        iv_progress.setImageResource(R.drawable.loading_animation);
        myTimeCount = new MyTimeCount(5000, 1500);
        myTimeCount.start();
        newOrderPayModel.getPayOrderStatus(0, colorSn, this);
        animationDrawable = (AnimationDrawable) iv_progress.getDrawable();
        animationDrawable.setOneShot(false);
        animationDrawable.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != myTimeCount) {
            myTimeCount.cancel();
        }
        if (null != broadcast) {
            unregisterReceiver(broadcast);
            broadcast = null;
        }
    }

    /**
     * 定义一个倒计时的内部类
     */
    class MyTimeCount extends CountDownTimer {
        public MyTimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            countFinish = false;
            animationDrawable.stop();
            time_out_layout.setVisibility(View.VISIBLE);
            wait_result_layout.setVisibility(View.GONE);
            cancel();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            String text = "正在支付,请等待..." + millisUntilFinished / UserAppConst.INTERVAL + "S";
            tv_wait_time.setText(text);
            newOrderPayModel.getPayOrderStatus(0, colorSn, OrderWaitResultActivity.this);
        }
    }

    private void initView() {
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        wait_result_layout = (RelativeLayout) findViewById(R.id.wait_result_layout);
        tv_wait_time = (TextView) findViewById(R.id.tv_wait_time);
        iv_progress = (ImageView) findViewById(R.id.iv_progress);
        time_out_layout = (LinearLayout) findViewById(R.id.time_out_layout);
        btn_see_order = (TextView) findViewById(R.id.btn_see_order);
        btn_refresh = (TextView) findViewById(R.id.btn_refresh);
        user_top_view_back.setOnClickListener(this);
        btn_see_order.setOnClickListener(this);
        btn_refresh.setOnClickListener(this);
        user_top_view_title.setText(getResources().getString(R.string.order_payresult));
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, user_top_view_back, user_top_view_title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_see_order:
                jumpOrderDetails();
                break;
            case R.id.btn_refresh:
                countFinish = false;
                initCountTime();
                break;
        }
    }


    private void jumpOrderDetails() {
        Message message = new Message();
        message.what = UserMessageConstant.SUREBTNCHECKET;
        EventBus.getDefault().post(message);
        if (Constants.isFromHtml) {
            // 来自h5调用原生支付，返回webview，调用回调
            Intent intent = new Intent();
            setResult(200, intent);
            finish();
        } else {
            Intent intent = new Intent(this, OrderResultAndDetailActivity.class);
            intent.putExtra(OrderResultAndDetailActivity.FROMRESULT, true);
            intent.putExtra(NewOrderPayActivity.ORDER_SN, colorSn);
            startActivity(intent);
            sendBroadCast();
            finish();
        }
    }

    private void jumpPaySuccessPage() {
        if (null != myTimeCount) {
            myTimeCount.cancel();
        }
        Intent intent = new Intent(this, OrderPaySuccessActivity.class);
        intent.putExtra(NewOrderPayActivity.ORDER_SN, colorSn);
        intent.putExtra(NewOrderPayActivity.PAY_CHANNEL, payChannelId);
        startActivityForResult(intent, 2000);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000) {
            if (200 == resultCode) {
                Intent intent = new Intent();
                setResult(200, intent);
                finish();
            }
        }
    }

    /**
     * 发送广播关闭之前页面
     */
    public void sendBroadCast() {
        Intent intent = new Intent();
        intent.setAction(BroadcastReceiverActivity.FINISH_PAYMENT_ACTIVYTY);
        sendBroadcast(intent);
    }

    private int orderStaus = 0; //支付中的状态进行在轮询

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                        if (baseContentEntity.getCode() == 0) {
                            PayStatusEntity payStatusEntity = GsonUtils.gsonToBean(result, PayStatusEntity.class);
                            orderStaus = payStatusEntity.getContent().getPay_success();
                            if (!countFinish) {  //订单的状态不是订单支付中
                                countFinish = true;
                                if (orderStaus == 1) {
                                    jumpPaySuccessPage();
                                } else if (orderStaus == 2) {
                                    ToastUtil.toastShow(getApplicationContext(), "当前订单未支付,请重新进行支付");
                                    finish();
                                }
                            }
                        }
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }
}
