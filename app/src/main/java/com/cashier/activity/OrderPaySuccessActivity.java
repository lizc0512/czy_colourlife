package com.cashier.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.text.method.ScrollingMovementMethod;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.activity.WebViewActivity;
import com.BeeFramework.model.Constants;
import com.BeeFramework.model.NewHttpResponse;
import com.cashier.modelnew.NewOrderPayModel;
import com.cashier.protocolchang.OrderDetailsEntity;
import com.cashier.protocolchang.PaySuccessBannerEntity;
import com.external.eventbus.EventBus;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.setting.activity.PayActivityDialog;
import com.tmall.ultraviewpager.UltraViewPager;
import com.user.UserAppConst;
import com.user.UserMessageConstant;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.utils.GlideUtils;
import cn.csh.colourful.life.utils.ToastUtils;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.BroadcastReceiverActivity;
import cn.net.cyberway.home.adapter.UltraPagerAdapter;
import cn.net.cyberway.utils.LinkParseUtil;

import static com.tendcloud.tenddata.ab.mContext;

/**
 * @name ${yuansk}
 * @class name：com.cashier.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/22 11:14
 * @change
 * @chang time
 * @class describe  订单的详情和结果页面
 */

public class OrderPaySuccessActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, SensorEventListener {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView user_top_view_right;
    private ImageView iv_pay_status;
    private TextView tv_pay_result;


    private LinearLayout pay_infor_layout;
    private RelativeLayout preferential_layout;
    private ImageView iv_preferential_type;
    private TextView tv_preferential_desc;
    private TextView tv_preferential_amout;
    private TextView tv_receive_business;
    private EditText tv_trade_no;
    private RelativeLayout shake_layout;
    private ImageView iv_shake_enter;
    private ImageView iv_shake_gif;

    private TextView tv_pay_type;
    private ImageView iv_payment;
    private TextView tv_pay_amount;
    private TextView tv_original_amount;
    private UltraViewPager advisement_banner;
    private NewOrderPayModel newOrderPayModel;
    private String colorSn;
    private String payChannedId;

    private SensorManager mSensorManager;
    private Sensor mAccelerometerSensor;
    private Vibrator mVibrator;//手机震动
    private SoundPool mSoundPool;//摇一摇音效
    private int mAudio;
    private static final int START_SHAKE = 0x1;
    private boolean isShake = false;
    private String shareAwardUrl;
    private int swingShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_pay_success);
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        iv_pay_status = findViewById(R.id.iv_pay_status);
        tv_pay_result = findViewById(R.id.tv_pay_result);
        pay_infor_layout = findViewById(R.id.pay_infor_layout);
        tv_pay_type = findViewById(R.id.tv_pay_type);
        iv_payment = findViewById(R.id.iv_payment);
        tv_pay_amount = findViewById(R.id.tv_pay_amount);
        tv_original_amount = findViewById(R.id.tv_original_amount);
        preferential_layout = findViewById(R.id.preferential_layout);
        iv_preferential_type = findViewById(R.id.iv_preferential_type);
        tv_preferential_desc = findViewById(R.id.tv_preferential_desc);
        tv_preferential_amout = findViewById(R.id.tv_preferential_amout);
        tv_receive_business = findViewById(R.id.tv_receive_business);
        tv_trade_no = findViewById(R.id.tv_trade_no);
        advisement_banner = findViewById(R.id.advisement_banner);
        shake_layout = findViewById(R.id.shake_layout);
        iv_shake_enter = findViewById(R.id.iv_shake_enter);
        iv_shake_gif = findViewById(R.id.iv_shake_gif);
        user_top_view_title.setText(getResources().getString(R.string.order_payresult));
        user_top_view_right.setText(getResources().getString(R.string.cashier_finish));
        user_top_view_right.setTextColor(getResources().getColor(R.color.textcolor_27a2f0));
        ThemeStyleHelper.rightTexteFrameLayout(getApplicationContext(), czy_title_layout, user_top_view_back, user_top_view_title, user_top_view_right);
        Intent intent = getIntent();
        colorSn = intent.getStringExtra(NewOrderPayActivity.ORDER_SN);
        payChannedId = intent.getStringExtra(NewOrderPayActivity.PAY_CHANNEL);
        newOrderPayModel = new NewOrderPayModel(this);
        newOrderPayModel.getAdvertiseBanner(1, payChannedId, this);
        newOrderPayModel.getSingleOrderInfor(0, colorSn, this);
        user_top_view_back.setVisibility(View.GONE);
        user_top_view_back.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        iv_shake_enter.setOnClickListener(this);
        //初始化SoundPool
        mSoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
        mAudio = mSoundPool.load(this, R.raw.shake_audio, 1);

        //获取Vibrator震动服务
        mVibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        tv_trade_no.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                // 创建普通字符型ClipData
                ClipData mClipData = ClipData.newPlainText("Label", colorSn);
                // 将ClipData内容放到系统剪贴板里。
                cm.setPrimaryClip(mClipData);
                ToastUtils.showMessage(mContext, "订单编号已复制到剪贴板");
                return false;
            }
        });
        editor.putString(UserAppConst.COLOURLIFE_TRADE_NO, colorSn).apply();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //获取 SensorManager 负责管理传感器
        mSensorManager = ((SensorManager) getSystemService(SENSOR_SERVICE));
        if (mSensorManager != null) {
            //获取加速度传感器
            mAccelerometerSensor = mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
            if (mAccelerometerSensor != null) {
                mSensorManager.registerListener(this, mAccelerometerSensor, SensorManager.SENSOR_DELAY_UI);
            }
        }
    }

    @Override
    protected void onPause() {
        // 务必要在pause中注销 mSensorManager
        // 否则会造成界面退出后摇一摇依旧生效的bug
        if (mSensorManager != null) {
            mSensorManager.unregisterListener(this);
        }
        super.onPause();
    }

    private void adversizeBannerShow() {
        int size = contentBeanList.size();
        PagerAdapter adapter = new UltraPagerAdapter(OrderPaySuccessActivity.this, true, contentBeanList);
        advisement_banner.setAdapter(adapter);
        if (size > 1) {
            advisement_banner.initIndicator();
            advisement_banner.getIndicator()
                    .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
                    .setFocusColor(Color.parseColor("#1cAef4"))
                    .setNormalColor(Color.parseColor("#bbc0cb"))
                    .setMargin(0, 0, 0, 3)
                    .setRadius((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 3, getResources().getDisplayMetrics
                            ()));
            advisement_banner.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
            advisement_banner.getIndicator().build();
            advisement_banner.setInfiniteLoop(size > 1);
            advisement_banner.setAutoScroll(3000);
        } else {
            advisement_banner.setInfiniteLoop(false);
            advisement_banner.disableAutoScroll();
            advisement_banner.disableIndicator();
        }
        advisement_banner.refresh();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
            case R.id.user_top_view_right:
                jumpOrderDetails();
                break;
            case R.id.iv_shake_enter:
                startShakeAnimation();
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                        if (baseContentEntity.getCode() == 0) {
                            OrderDetailsEntity orderDetailsEntity = GsonUtils.gsonToBean(result, OrderDetailsEntity.class);
                            OrderDetailsEntity.ContentBean contentBean = orderDetailsEntity.getContent();
                            if (null != contentBean) {
                                String totalFee = contentBean.getTotal_fee();
                                String actual_fee = contentBean.getActual_fee();
                                tv_pay_amount.setText(contentBean.getActual_fee());
                                tv_pay_type.setText(contentBean.getPayment_name());
                                int tradeState = contentBean.getTrade_state();
                                tv_pay_result.setVisibility(View.VISIBLE);
                                iv_pay_status.setVisibility(View.VISIBLE);
                                iv_payment.setVisibility(View.VISIBLE);
                                pay_infor_layout.setVisibility(View.VISIBLE);
                                if (tradeState != 2) {
                                    tv_pay_result.setText(contentBean.getTrade_state_name());
                                    iv_pay_status.setImageResource(R.drawable.icon_pay_fail);
                                }
                                if (totalFee.equalsIgnoreCase(actual_fee)) {
                                    tv_original_amount.setVisibility(View.GONE);
                                    preferential_layout.setVisibility(View.GONE);
                                } else {
                                    tv_original_amount.setVisibility(View.VISIBLE);
                                    preferential_layout.setVisibility(View.VISIBLE);
                                }
                                int isMeal = contentBean.getIs_fanpiao();
                                int drawId = 0;
                                if (isMeal == 1) {
                                    drawId = R.drawable.icon_orgin_meal;
                                    iv_payment.setImageResource(R.drawable.icon_list_meal);
                                    iv_preferential_type.setImageResource(R.drawable.icon_preferential_meal);
                                } else {
                                    drawId = R.drawable.icon_orgin_cashier;
                                    iv_payment.setImageResource(R.drawable.icon_list_cashier);
                                    iv_preferential_type.setImageResource(R.drawable.icon_preferential_caehier);
                                }
                                Drawable dra = getResources().getDrawable(drawId);
                                dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
                                tv_original_amount.setCompoundDrawables(dra, null, null, null);
                                tv_original_amount.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                                tv_original_amount.setText(totalFee);
                                tv_preferential_desc.setText(contentBean.getDiscount_content());
                                tv_preferential_amout.setText(contentBean.getDiscount_amount());
                                tv_receive_business.setText(contentBean.getBussiness_name());
                                tv_trade_no.setText(contentBean.getColour_sn());
                                tv_trade_no.setMovementMethod(ScrollingMovementMethod.getInstance());
                                String popImg = contentBean.getPop_img();
                                String popRedirect = contentBean.getPop_redirect();
                                if (!TextUtils.isEmpty(popImg)) {
                                    //弹出彩钱包的活动窗口
                                    showOauthDialog(popImg, popRedirect);
                                }
                            }
                        }
                    } catch (Exception e) {
                        ToastUtil.toastShow(OrderPaySuccessActivity.this, e.getMessage());
                    }
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        PaySuccessBannerEntity paySuccessBannerEntity = GsonUtils.gsonToBean(result, PaySuccessBannerEntity.class);
                        contentBeanList.clear();
                        PaySuccessBannerEntity.ContentBean contentBean = paySuccessBannerEntity.getContent();
                        contentBeanList.addAll(contentBean.getBanner_arr());
                        int bannerShow = contentBean.getShow();
                        swingShow = contentBean.getSwing_show();
                        shareAwardUrl = contentBean.getSwing_url();
                        if (bannerShow == 1) {
                            adversizeBannerShow();
                        }
                        if (swingShow == 1) {
                            shake_layout.setVisibility(View.VISIBLE);
                            GlideImageLoader.loadImageDefaultDisplay(OrderPaySuccessActivity.this, contentBean.getSwing_img(), iv_shake_enter, R.drawable.icon_success_shakebg, R.drawable.icon_success_shakebg);
                            GlideImageLoader.loadImageDefaultDisplay(OrderPaySuccessActivity.this, contentBean.getSwing_gif(), iv_shake_gif, R.drawable.icon_success_shake, R.drawable.icon_success_shake);
                        }
                    } catch (Exception e) {

                    }
                }
                break;

        }
    }

    private void showOauthDialog(String imageUrl, final String imageLink) {
        final PayActivityDialog payActivityDialog = new PayActivityDialog(OrderPaySuccessActivity.this, R.style.dialog);
        payActivityDialog.show();
        payActivityDialog.iv_wallet_activity.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                LinkParseUtil.parse(OrderPaySuccessActivity.this, imageLink, "");
                payActivityDialog.dismiss();
            }
        });
        GlideUtils.loadImageView(OrderPaySuccessActivity.this, imageUrl, payActivityDialog.iv_wallet_activity);
        payActivityDialog.iv_wallet_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                payActivityDialog.dismiss();
            }
        });
    }

    private List<PaySuccessBannerEntity.ContentBean.BannerArrBean> contentBeanList = new ArrayList<>();

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

    /**
     * 发送广播关闭之前页面
     */
    public void sendBroadCast() {
        Intent intent = new Intent();
        intent.setAction(BroadcastReceiverActivity.FINISH_PAYMENT_ACTIVYTY);
        sendBroadcast(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        jumpOrderDetails();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();

        if (type == Sensor.TYPE_ACCELEROMETER) {
            //获取三个方向值
            float[] values = event.values;
            float x = values[0];
            float y = values[1];
            float z = values[2];

            if ((Math.abs(x) > 15 || Math.abs(y) > 15 || Math
                    .abs(z) > 15) && !isShake) {
                startShakeAnimation();
            }
        }
    }

    private void startShakeAnimation() {
        isShake = true;
        // TODO: 2016/10/19 实现摇动逻辑, 摇动后进行震动
        Thread thread = new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    //开始震动 发出提示音 展示动画效果
                    handler.obtainMessage(START_SHAKE).sendToTarget();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        thread.start();
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case START_SHAKE:
                    //This method requires the caller to hold the permission VIBRATE.
                    //当前的url不为空。
                    if (!TextUtils.isEmpty(shareAwardUrl) && swingShow == 1) {
                        mVibrator.vibrate(500);
                        mSoundPool.play(mAudio, 1, 1, 0, 0, 1);
                        startAnim();
                    }
                    break;
            }
        }
    };

    //定义摇一摇动画动画
    private void startAnim() {
        AnimationSet animup = new AnimationSet(true);
        TranslateAnimation animation0 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0f,
                Animation.RELATIVE_TO_SELF, -0.3f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, -0.1f);
        animation0.setDuration(500);
        TranslateAnimation animation1 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, -0.3f,
                Animation.RELATIVE_TO_SELF, 0.3f, Animation.RELATIVE_TO_SELF, -0.1f, Animation.RELATIVE_TO_SELF, 0.1f);
        animation1.setDuration(500);
        animation1.setStartOffset(500);
        TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0.3f,
                Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0.1f, Animation.RELATIVE_TO_SELF, 0f);
        animation2.setDuration(500);
        animation2.setStartOffset(1000);
        animup.addAnimation(animation0);
        animup.addAnimation(animation1);
        animup.addAnimation(animation2);
        animup.setRepeatCount(Animation.RESTART);
        iv_shake_gif.startAnimation(animup);
        iv_shake_gif.getAnimation().setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isShake = false;
                if (!TextUtils.isEmpty(shareAwardUrl) && swingShow == 1) {
                    if (shareAwardUrl.startsWith("http")) {
                        Intent intent = new Intent(OrderPaySuccessActivity.this, WebViewActivity.class);
                        intent.putExtra(WebViewActivity.WEBURL, shareAwardUrl);
                        intent.putExtra(WebViewActivity.WEBTITLE, "");
                        intent.putExtra(WebViewActivity.THRIDSOURCE, false);
                        startActivityForResult(intent, 2000);
                    } else {
                        LinkParseUtil.parse(OrderPaySuccessActivity.this, shareAwardUrl, "");
                    }
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000 && resultCode == 200) {
            String state = data.getStringExtra("state");
            if (!TextUtils.isEmpty(state)) {
                shake_layout.setVisibility(View.GONE);
                isShake = true;
            }
        }
    }
}
