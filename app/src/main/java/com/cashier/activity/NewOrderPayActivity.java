package com.cashier.activity;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.NetworkUtil;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.Constants;
import com.BeeFramework.model.NewHttpResponse;
import com.cashier.modelnew.NewOrderPayModel;
import com.cashier.protocolchang.OrderChekEntity;
import com.cashier.protocolchang.PayEntity;
import com.cashier.protocolchang.PayResultEntity;
import com.cashier.protocolchang.PayStatusEntity;
import com.customerInfo.protocol.RealNameTokenEntity;
import com.external.eventbus.EventBus;
import com.lhqpay.ewallet.keepIntact.Listener;
import com.lhqpay.ewallet.keepIntact.MyListener;
import com.lhqpay.ewallet.keepIntact.PayListenerUtils;
import com.lhqpay.ewallet.keepIntact.PayUtil;
import com.lhqpay.ewallet.keepIntact.WXPayEntryActivity;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.setting.activity.CertificateResultDialog;
import com.setting.activity.EditDialog;
import com.setting.activity.HtmlPayDialog;
import com.tencent.authsdk.AuthConfig;
import com.tencent.authsdk.AuthSDKApi;
import com.tencent.authsdk.IDCardInfo;
import com.tencent.authsdk.callback.IdentityCallback;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.model.NewUserModel;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.BroadcastReceiverActivity;
import cn.net.cyberway.utils.LinkParseUtil;


/**
 * @name ${yuansk}
 * @class name：com.cashier.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/7 10:35
 * @change
 * @chang time
 * @class describe   新的订单支付页面
 */

public class NewOrderPayActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, Listener, MyListener {

    public static final String ORDER_SN = "ORDER_SN";
    public static final String PAY_CHANNEL = "pay_channel";
    private NewOrderPayModel newOrderPayModel;
    private FrameLayout czy_title_layout;
    private ImageView user_top_view_back;
    private TextView user_top_view_title;

    private RelativeLayout order_pay_layout;//订单支付布局
    private TextView tv_business_amount;  //人民币价格
    private TextView tv_ticket_amount;//饭票价格

    private RelativeLayout bottom_pay_layout;


    private LinearLayout ticket_payment_layout;  //动态布局添加各种支付方式

    private Button btn_sure_pay; //确认支付
    private TextView tv_pay_money; //支付的
    private ImageView iv_pay_meal; //饭票支付的类型

    private List<ImageView> imageViewList = new ArrayList<ImageView>();
    private double meal_total = 0; //饭票的
    private double total_fee = 0; //现金的
    private String sn = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_pay_order);
        initView();
        initData();
        PayListenerUtils.setCallBack(this);
    }

    private void initData() {
        sn = getIntent().getStringExtra(ORDER_SN);
        newOrderPayModel = new NewOrderPayModel(NewOrderPayActivity.this);
        newOrderPayModel.getPayOrderDetails(0, sn, false, this);
    }

    private void initView() {
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        order_pay_layout = (RelativeLayout) findViewById(R.id.order_pay_layout);
        tv_business_amount = (TextView) findViewById(R.id.tv_business_amount);
        tv_ticket_amount = (TextView) findViewById(R.id.tv_ticket_amount);
        ticket_payment_layout = (LinearLayout) findViewById(R.id.ticket_payment_layout);
        bottom_pay_layout = (RelativeLayout) findViewById(R.id.bottom_pay_layout);
        btn_sure_pay = (Button) findViewById(R.id.btn_sure_pay);
        tv_pay_money = (TextView) findViewById(R.id.tv_pay_money);
        iv_pay_meal = (ImageView) findViewById(R.id.iv_pay_meal);
        user_top_view_title.setText(getResources().getString(R.string.pay_order));
        user_top_view_back.setOnClickListener(this);
        btn_sure_pay.setOnClickListener(this);
        ThemeStyleHelper.onlyFrameTitileBar(NewOrderPayActivity.this, czy_title_layout, user_top_view_back, user_top_view_title);
    }


    /***显示的标题样式***/
    private void addHeadTitleView(String titleName) {
        View headTitleView = LayoutInflater.from(NewOrderPayActivity.this).inflate(R.layout.adapter_payment_title, null);
        TextView tv_pay_title = (TextView) headTitleView.findViewById(R.id.tv_pay_title);
        tv_pay_title.setText(titleName);
        ticket_payment_layout.addView(headTitleView);
    }


    /**
     * 饭票支付的样式
     ***/
    private void addMealPayView(int size, List<PayEntity.ContentBean.PaymentBean.MealPayBean.PayListBean> mealPayList) {
        for (int i = 0; i < size; i++) {
            final PayEntity.ContentBean.PaymentBean.MealPayBean.PayListBean payListBean = mealPayList.get(i);
            View payStyleView = LayoutInflater.from(NewOrderPayActivity.this).inflate(R.layout.adapter_payment_itemview, null);
            ticket_payment_layout.addView(payStyleView);
            LinearLayout pay_type_layout = (LinearLayout) payStyleView.findViewById(R.id.pay_type_layout);
            ImageView iv_pay_icon = (ImageView) payStyleView.findViewById(R.id.iv_pay_icon);
            TextView tv_paystyle_title = (TextView) payStyleView.findViewById(R.id.tv_paystyle_title);
            TextView tv_pay_balance = (TextView) payStyleView.findViewById(R.id.tv_pay_balance);
            final ImageView select_pay_type = (ImageView) payStyleView.findViewById(R.id.select_pay_type);
            final View tv_paystyle_view = payStyleView.findViewById(R.id.tv_paystyle_view);
            GlideImageLoader.loadImageDefaultDisplay(NewOrderPayActivity.this, payListBean.getPayment_logo(), iv_pay_icon, R.drawable.icon_default, R.drawable.icon_default);
            final double mealBalance = getFormatMoney(payListBean.getAmount());
            final int normalDiscount = payListBean.getDiscount();
            final int normalPayment = payListBean.getPayment_type();
            final int normalIsNative = payListBean.getIs_native();
            final String normalPayUrl = payListBean.getPay_url();
            if (normalDiscount > 0 && normalDiscount != 100) {
                tv_paystyle_title.setText(payListBean.getPayment_name() + " (" + normalDiscount + "折)");
            } else {
                tv_paystyle_title.setText(payListBean.getPayment_name());
            }
            tv_pay_balance.setText("可用: " + mealBalance);
            final String mealTypeId = String.valueOf(payListBean.getMeal_type());
            List<PayEntity.ContentBean.PaymentBean.MealPayBean.PayListBean.ListBean> secondListBeanList = payListBean.getList();
            final int secondListSize = secondListBeanList == null ? 0 : secondListBeanList.size();
            pay_type_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (secondListSize > 0) {  //这个显示的是显示或隐藏
                        changeTicketShowStatus(mealTypeId, select_pay_type);
                    } else {   //选中饭票
                        if (mealBalance >= meal_total * normalDiscount / 100.0f) {
                            payment = normalPayment;
                            discount = normalDiscount;
                            is_native = normalIsNative;
                            pay_url = normalPayUrl;
                            payChannelId = select_pay_type.getTag().toString().trim();
                            changePayChannelStatus();
                        }
                    }
                }
            });
            if (secondListSize > 0) {  //有子类的
                select_pay_type.setImageResource(R.drawable.icon_bottom_arrow);
                tv_paystyle_view.setVisibility(View.VISIBLE);
                List<View> dataViewList = new ArrayList<>();
                for (int k = 0; k < secondListSize; k++) {
                    final PayEntity.ContentBean.PaymentBean.MealPayBean.PayListBean.ListBean listBean = secondListBeanList.get(k);
                    View ticketStylePayView = LayoutInflater.from(NewOrderPayActivity.this).inflate(R.layout.ticket_payment_layout, null);
                    dataViewList.add(ticketStylePayView);
                    ticket_payment_layout.addView(ticketStylePayView);
                    LinearLayout ticket_pay_layout = (LinearLayout) ticketStylePayView.findViewById(R.id.ticket_pay_layout);
                    TextView tv_ticket_title = (TextView) ticketStylePayView.findViewById(R.id.tv_ticket_title);
                    TextView tv_ticket_balance = (TextView) ticketStylePayView.findViewById(R.id.tv_ticket_balance);
                    final ImageView select_ticket_pay = (ImageView) ticketStylePayView.findViewById(R.id.select_ticket_pay);
                    View ticket_bottom_line = ticketStylePayView.findViewById(R.id.ticket_bottom_line);
                    if (k == secondListSize - 1) {
                        ticket_bottom_line.setVisibility(View.GONE);
                    } else {
                        ticket_bottom_line.setVisibility(View.VISIBLE);
                    }
                    final double otherMealAmount = listBean.getAmount();
                    final int mealDiscount = listBean.getDiscount();
                    if (mealDiscount > 0 && mealDiscount != 100) {
                        tv_ticket_title.setText(listBean.getPayment_name() + " (" + mealDiscount + "折)");
                    } else {
                        tv_ticket_title.setText(listBean.getPayment_name());
                    }
                    tv_ticket_balance.setText("可用: " + otherMealAmount);
                    String otherPaymentUUid = listBean.getPayment_uuid();
                    final int mealpaymentType = listBean.getPayment_type();
                    final int mealNative = listBean.getIs_native();
                    final String mealPayUrl = listBean.getPay_url();
                    select_ticket_pay.setTag(otherPaymentUUid);
                    secondTicketShow.put(mealTypeId, true);
                    secondTicketMap.put(mealTypeId, dataViewList);
                    imageViewList.add(select_ticket_pay);
                    ticket_pay_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (otherMealAmount >= meal_total * mealDiscount / 100.0f) {
                                payment = mealpaymentType;
                                discount = mealDiscount;
                                is_native = mealNative;
                                pay_url = mealPayUrl;
                                payChannelId = select_ticket_pay.getTag().toString().trim();
                                changePayChannelStatus();
                            }
                        }
                    });
                    setMealTextColor(otherMealAmount, tv_ticket_balance, select_ticket_pay, mealDiscount, mealpaymentType, mealNative, mealPayUrl);
                }
            } else {
                String paymentUUid = payListBean.getPayment_uuid();
                if (TextUtils.isEmpty(paymentUUid)) {  //尊享饭票没子类不显示
                    pay_type_layout.setVisibility(View.GONE);
                } else {
                    pay_type_layout.setVisibility(View.VISIBLE);
                    select_pay_type.setTag(paymentUUid);
                    imageViewList.add(select_pay_type);
                    if (i == size - 1) {
                        tv_paystyle_view.setVisibility(View.GONE);
                    } else {
                        tv_paystyle_view.setVisibility(View.VISIBLE);
                    }
                    setMealTextColor(mealBalance, tv_pay_balance, select_pay_type, normalDiscount, normalPayment, normalIsNative, normalPayUrl);
                }
            }
        }
    }

    /**
     * 花样年钱包支付和余额支付的样式
     ****/
    private void addWalletPayView(int size, List<PayEntity.ContentBean.PaymentBean.WalletPayBean.PayListBeanX> wallPayList) {
        for (int i = 0; i < size; i++) {
            final PayEntity.ContentBean.PaymentBean.WalletPayBean.PayListBeanX payListBean = wallPayList.get(i);
            View payStyleView = LayoutInflater.from(NewOrderPayActivity.this).inflate(R.layout.adapter_payment_itemview, null);
            ticket_payment_layout.addView(payStyleView);
            LinearLayout pay_type_layout = (LinearLayout) payStyleView.findViewById(R.id.pay_type_layout);
            ImageView iv_pay_icon = (ImageView) payStyleView.findViewById(R.id.iv_pay_icon);
            TextView tv_paystyle_title = (TextView) payStyleView.findViewById(R.id.tv_paystyle_title);
            TextView tv_pay_balance = (TextView) payStyleView.findViewById(R.id.tv_pay_balance);
            final ImageView select_pay_type = (ImageView) payStyleView.findViewById(R.id.select_pay_type);
            final View tv_paystyle_view = payStyleView.findViewById(R.id.tv_paystyle_view);
            GlideImageLoader.loadImageDefaultDisplay(NewOrderPayActivity.this, payListBean.getPayment_logo(), iv_pay_icon, R.drawable.icon_default, R.drawable.icon_default);
            final int balanceDiscount = payListBean.getDiscount();
            final String balancePayAmount = payListBean.getAmount();
            final int balancePaymentType = payListBean.getPayment_type();
            final int balanceIsNative = payListBean.getIs_native();
            final String balancePaymentUrl = payListBean.getPay_url();
            if (balanceDiscount > 0 && balanceDiscount != 100) {
                tv_paystyle_title.setText(payListBean.getPayment_name() + " (" + balanceDiscount + "折)");
            } else {
                tv_paystyle_title.setText(payListBean.getPayment_name());
            }
            tv_pay_balance.setVisibility(View.GONE);
            pay_type_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (!TextUtils.isEmpty(balancePayAmount)) { //余额支付
                        if (getFormatMoney(balancePayAmount) >= total_fee * balanceDiscount / 100.0f) {
                            discount = balanceDiscount;
                            payment = balancePaymentType;
                            is_native = balanceIsNative;
                            pay_url = balancePaymentUrl;
                            payChannelId = select_pay_type.getTag().toString().trim();
                            changePayChannelStatus();
                        }
                    } else {
                        discount = balanceDiscount;
                        payment = balancePaymentType;
                        is_native = balanceIsNative;
                        pay_url = balancePaymentUrl;
                        payChannelId = select_pay_type.getTag().toString().trim();
                        changePayChannelStatus();
                    }
                }
            });
            select_pay_type.setTag(payListBean.getPayment_uuid());
            imageViewList.add(select_pay_type);
            if (i == size - 1) {
                tv_paystyle_view.setVisibility(View.GONE);
            } else {
                tv_paystyle_view.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(balancePayAmount)) {
                tv_pay_balance.setVisibility(View.GONE);
                showStatus(select_pay_type, balanceDiscount, balancePaymentType, balanceIsNative, balancePaymentUrl);
            } else {
                tv_pay_balance.setVisibility(View.VISIBLE);
                setBalanceColor(getFormatMoney(balancePayAmount), tv_pay_balance, select_pay_type, balanceDiscount, balancePaymentType, balanceIsNative, balancePaymentUrl);
            }
        }
    }

    /*****其他支付方式******/
    private void addOtherPayView(int size, List<PayEntity.ContentBean.PaymentBean.OtherPayBean.PayListBeanXX> wallPayList) {
        for (int i = 0; i < size; i++) {
            final PayEntity.ContentBean.PaymentBean.OtherPayBean.PayListBeanXX payListBean = wallPayList.get(i);
            View payStyleView = LayoutInflater.from(NewOrderPayActivity.this).inflate(R.layout.adapter_payment_itemview, null);
            ticket_payment_layout.addView(payStyleView);
            LinearLayout pay_type_layout = (LinearLayout) payStyleView.findViewById(R.id.pay_type_layout);
            ImageView iv_pay_icon = (ImageView) payStyleView.findViewById(R.id.iv_pay_icon);
            TextView tv_paystyle_title = (TextView) payStyleView.findViewById(R.id.tv_paystyle_title);
            TextView tv_pay_balance = (TextView) payStyleView.findViewById(R.id.tv_pay_balance);
            final ImageView select_pay_type = (ImageView) payStyleView.findViewById(R.id.select_pay_type);
            final View tv_paystyle_view = payStyleView.findViewById(R.id.tv_paystyle_view);
            GlideImageLoader.loadImageDefaultDisplay(NewOrderPayActivity.this, payListBean.getPayment_logo(), iv_pay_icon, R.drawable.icon_default, R.drawable.icon_default);
            final int otherDiscount = payListBean.getDiscount();
            final int otherPayment = payListBean.getPayment_type();
            final int otherNative = payListBean.getIs_native();
            final String otherPayUrl = payListBean.getPay_url();
            if (otherDiscount > 0 && otherDiscount != 100) {
                tv_paystyle_title.setText(payListBean.getPayment_name() + " (" + otherDiscount + "折)");
            } else {
                tv_paystyle_title.setText(payListBean.getPayment_name());
            }
            tv_pay_balance.setVisibility(View.GONE);
            pay_type_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payment = otherPayment;
                    discount = otherDiscount;
                    is_native = otherNative;
                    pay_url = otherPayUrl;
                    payChannelId = select_pay_type.getTag().toString().trim();
                    changePayChannelStatus();
                }
            });
            select_pay_type.setTag(payListBean.getPayment_uuid());
            imageViewList.add(select_pay_type);
            if (i == size - 1) {
                tv_paystyle_view.setVisibility(View.GONE);
            } else {
                tv_paystyle_view.setVisibility(View.VISIBLE);
            }
            showStatus(select_pay_type, otherDiscount, otherPayment, otherNative, otherPayUrl);
        }
    }

    /***格式化金额的显示**/
    public double getFormatMoney(String money) {
        double formatMoney = 0;
        if (TextUtils.isEmpty(money)) {
            return formatMoney;
        }
        try {
            java.math.BigDecimal bigDec = new java.math.BigDecimal(money.trim());
            formatMoney = bigDec.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
        } catch (NumberFormatException e) {
            formatMoney = Double.valueOf(money);
        } catch (Exception e) {
            formatMoney = 0;
        }
        return formatMoney;
    }

    //添加各种类型的支付列表
    private void addPayTypeView(String result) {
        final PayEntity payEntity = GsonUtils.gsonToBean(result, PayEntity.class);
        ticket_payment_layout.removeAllViews();
        PayEntity.ContentBean contentBean = payEntity.getContent();
        PayEntity.ContentBean.OrderBean orderBean = contentBean.getOrder();
        if (null != orderBean) {
            bottom_pay_layout.setVisibility(View.VISIBLE);
            order_pay_layout.setVisibility(View.VISIBLE);
            meal_total = getFormatMoney(orderBean.getMeal_total());
            total_fee = getFormatMoney(orderBean.getTotal_fee());
            if (meal_total == 0) {
                meal_total = total_fee;
            }
            if (total_fee == 0) {
                total_fee = meal_total;
            }
            sn = orderBean.getColour_sn();
            tv_business_amount.setText(total_fee + "");
            tv_ticket_amount.setText("(饭票价:" + meal_total + ")");
            tv_pay_money.setText(getFormatMoney(String.valueOf(meal_total)) + "");
            String msg = orderBean.getMsg();
            if (!TextUtils.isEmpty(msg)) {
                ToastUtil.toastTime(NewOrderPayActivity.this, msg, 3000);
            }
        }
        if (null != orderBean) {
            PayEntity.ContentBean.PaymentBean paymentBean = contentBean.getPayment();
            if (null != paymentBean) {
                PayEntity.ContentBean.PaymentBean.MealPayBean mealPayBean = paymentBean.getMeal_pay();
                PayEntity.ContentBean.PaymentBean.WalletPayBean walletPayBean = paymentBean.getWallet_pay();
                PayEntity.ContentBean.PaymentBean.OtherPayBean otherPayBean = paymentBean.getOther_pay();
                if (null != mealPayBean) {
                    List<PayEntity.ContentBean.PaymentBean.MealPayBean.PayListBean> mealPayList = mealPayBean.getPay_list();
                    int size = mealPayList == null ? 0 : mealPayList.size();
                    if (size > 0) {
                        addHeadTitleView(mealPayBean.getPay_name());
                        addMealPayView(size, mealPayList);
                    }
                }
                if (null != walletPayBean) {
                    List<PayEntity.ContentBean.PaymentBean.WalletPayBean.PayListBeanX> wallPayList = walletPayBean.getPay_list();
                    int size = wallPayList == null ? 0 : wallPayList.size();
                    if (size > 0) {
                        addHeadTitleView(walletPayBean.getPay_name());
                        addWalletPayView(size, wallPayList);
                    }
                }
                if (null != otherPayBean) {
                    List<PayEntity.ContentBean.PaymentBean.OtherPayBean.PayListBeanXX> otherPayList = otherPayBean.getPay_list();
                    int size = otherPayList == null ? 0 : otherPayList.size();
                    if (size > 0) {
                        addHeadTitleView(otherPayBean.getPay_name());
                        addOtherPayView(size, otherPayList);
                    }
                }
            }
        }
    }

    private String payChannelId = "";
    private int payment = 2;//1:表示 饭票  2:现金
    private int discount = 100; //折扣
    private int is_native = 0;//1 原生 2 h5
    private String pay_url;
    private Map<String, List<View>> secondTicketMap = new HashMap<>();
    private Map<String, Boolean> secondTicketShow = new HashMap<>();


    /***饭票余额够不够支付订单的饭票支付判断***/
    private void setMealTextColor(double mealAmount, TextView tv_pay_balance, ImageView selectImage, int discount, int payment, int is_native, String pay_url) {
        if (mealAmount >= meal_total * discount / 100.0f) {  //饭票可以选中
            tv_pay_balance.setTextColor(Color.parseColor("#a3aaae"));
            selectImage.setVisibility(View.VISIBLE);
            showStatus(selectImage, discount, payment, is_native, pay_url);
        } else {
            tv_pay_balance.setTextColor(Color.parseColor("#fe6262"));
            selectImage.setVisibility(View.GONE);
        }
    }

    /***花样年余额够不够支付订单现金支付的判断***/
    private void setBalanceColor(double balanceAmout, TextView tv_pay_balance, ImageView selectImage, int discount, int payment, int is_native, String pay_url) {
        if (balanceAmout >= total_fee * discount / 100.0f) {  //余额可以选中
            tv_pay_balance.setTextColor(Color.parseColor("#a3aaae"));
            selectImage.setVisibility(View.VISIBLE);
            showStatus(selectImage, discount, payment, is_native, pay_url);
        } else {
            tv_pay_balance.setTextColor(Color.parseColor("#fe6262"));
            selectImage.setVisibility(View.GONE);
        }
    }

    /***显示默认的支付方式***/
    private void showStatus(ImageView selectImage, int discount, int payment, int is_native, String pay_url) {
        if (TextUtils.isEmpty(payChannelId)) {
            payChannelId = selectImage.getTag().toString();
            this.discount = discount;
            this.payment = payment;
            this.is_native = is_native;
            this.pay_url = pay_url;
            selectImage.setImageResource(R.drawable.f3_sel);
            showPayType();
        } else {
            selectImage.setImageResource(R.drawable.f3_sel_circle);
        }
    }

    /***尊享饭票子类的显示和隐藏***/
    private void changeTicketShowStatus(String mealTypeId, ImageView select_pay_type) {
        boolean showHide = secondTicketShow.get(mealTypeId);
        List<View> dataListView = secondTicketMap.get(mealTypeId);
        for (int i = 0; i < dataListView.size(); i++) {
            View childView = dataListView.get(i);
            if (showHide) {  //可见
                childView.setVisibility(View.GONE);
                select_pay_type.setImageResource(R.drawable.icon_up_arrow);
            } else { //不可见
                childView.setVisibility(View.VISIBLE);
                select_pay_type.setImageResource(R.drawable.icon_bottom_arrow);
            }
            secondTicketShow.put(mealTypeId, !showHide);
        }
    }

    /***支付方式选中的样式***/
    private void changePayChannelStatus() {
        for (int i = 0; i < imageViewList.size(); i++) {
            ImageView imageView = imageViewList.get(i);
            if (imageView.getTag() != null) {
                String imageBindId = imageView.getTag().toString().trim();
                if (payChannelId.equals(imageBindId)) {
                    imageView.setImageResource(R.drawable.f3_sel);
                } else {
                    imageView.setImageResource(R.drawable.f3_sel_circle);
                }
            } else {
                imageView.setImageResource(R.drawable.f3_sel_circle);
            }
        }
        showPayType();
    }

    /*****显示底部是饭票还是现金支付*****/
    private void showPayType() {
        if (payment == 1) {
            tv_pay_money.setText(getFormatMoney(String.valueOf(meal_total * discount / 100.0f)) + "");
            iv_pay_meal.setImageResource(R.drawable.icon_meal);
        } else {
            tv_pay_money.setText(getFormatMoney(String.valueOf(total_fee * discount / 100.0f)) + "");
            iv_pay_meal.setImageResource(R.drawable.icon_cashier);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_sure_pay:  //确认支付订单
//                newOrderPayModel.getUserRealCertificate(3, sn, this);
                createCzyOrder();
                break;
        }
    }


    private void createCzyOrder() {
        if (is_native == 1) {
            if (TextUtils.isEmpty(payChannelId)) {
                ToastUtil.toastShow(NewOrderPayActivity.this, "请选择一种支付方式");
            } else {
                newOrderPayModel.goOrderPay(1, sn, payChannelId, this);
            }
        } else {
            LinkParseUtil.parse(NewOrderPayActivity.this, pay_url, "");
        }
    }


    private String errorNotice;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  //支付包,双乾支付
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (10010 == resultCode) {
                // 邻花钱插件返回
                if (data != null) {
                    int code = data.getIntExtra("code", -1);
//                    ToastUtil.toastShow(NewOrderPayActivity.this, data.getStringExtra("message"));
                    if (code == 200) {
                        //支付成功
//                        payResult();
                        payResultQuery();
                    } else if (code == 1000) {
                        //交易取消
                    } else if (code == 1020) {
                        //交易失败  code==1020
//                        payResult();
//                        errorNotice = data.getStringExtra("message");
//                        checkFailOrderStatus();
                        payResultQuery();
//                        ToastUtil.toastShow(NewOrderPayActivity.this, );
                    } else if (code == 88) {
                        //实名认证成功
                    } else if (code == 300) {//光彩支付的回调
                        payResultQuery();
                    } else if (code == 403) {//重复订单
                        ToastUtil.toastShow(NewOrderPayActivity.this, data.getStringExtra("message"));
                    }
                }
            }
        } else if (requestCode == 2000) {
            if (200 == resultCode) {
                Intent intent = new Intent();
                setResult(200, intent);
                finish();
            }
        }
    }

    public void payResultQuery() {
        Intent intent = new Intent(this, OrderWaitResultActivity.class);
        intent.putExtra(ORDER_SN, sn);
        intent.putExtra(PAY_CHANNEL, payChannelId);
        startActivityForResult(intent, 2000);
    }


    @Override
    public void onPayResult(int error_code, String message) {  //微信回调的接口
//        ToastUtil.toastShow(this, message);
        if (error_code == 200) {
            //支付成功
//            payResult();
            payResultQuery();
        } else if (error_code == 1000) {
            //交易取消

        } else if (error_code == 1020) {
            //交易失败  code==1020
//            payResult();
            errorNotice = message;
            if (message.contains("安装微信")) {
                ToastUtil.toastShow(NewOrderPayActivity.this, message);
            } else {
                payResultQuery();
            }
//            checkFailOrderStatus();
//            ToastUtil.toastShow(NewOrderPayActivity.this, message);
        }
    }

//    private void checkFailOrderStatus() {
////        NewOrderPayModel newOrderPayModel = new NewOrderPayModel(this);
////        newOrderPayModel.getPayOrderStatus(2, sn, NewOrderPayActivity.this);
//        payResultQuery();
//    }

    private BroadcastReceiverActivity broadcast;

    @Override
    protected void onResume() {
        super.onResume();
        WXPayEntryActivity.sendValue(NewOrderPayActivity.this);
        broadcast = new BroadcastReceiverActivity(this);
        IntentFilter intentFilter = new IntentFilter(BroadcastReceiverActivity.FINISH_PAYMENT_ACTIVYTY);
        registerReceiver(broadcast, intentFilter);
        if (!EventBus.getDefault().isregister(NewOrderPayActivity.this)) {
            EventBus.getDefault().register(NewOrderPayActivity.this);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //销毁广播注册
        if (broadcast != null) {
            unregisterReceiver(broadcast);
            broadcast = null;
        }
        if (EventBus.getDefault().isregister(NewOrderPayActivity.this)) {
            EventBus.getDefault().unregister(NewOrderPayActivity.this);
        }
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        if (message.what == UserMessageConstant.GUANGCAI_PAY_MSG) {//登录成功刷新数据
            showH5PayResultDialog();
        } else if (message.what == UserMessageConstant.NET_CONN_CHANGE) {
            if (NetworkUtil.isConnect(getApplicationContext())) {
                againGetPayList();
            }
        }
    }

    private void againGetPayList() {
        if (TextUtils.isEmpty(payChannelId)) {
            newOrderPayModel.getPayOrderDetails(0, sn, true, this);
        }
    }

    /***光彩支付的结果**/
    private void showH5PayResultDialog() {
        final HtmlPayDialog htmlPayDialog = new HtmlPayDialog(NewOrderPayActivity.this);
        htmlPayDialog.show();
        htmlPayDialog.tv_again_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                htmlPayDialog.dismiss();
                newOrderPayModel.goOrderPay(1, sn, payChannelId, NewOrderPayActivity.this);
            }
        });
        htmlPayDialog.tv_finish_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                htmlPayDialog.dismiss();
                payResultQuery();
            }
        });
        htmlPayDialog.tv_cancel_pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                htmlPayDialog.dismiss();
            }
        });
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        addPayTypeView(result);
                    } catch (Exception e) {
                        BaseContentEntity baseEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                        ToastUtil.toastShow(NewOrderPayActivity.this, baseEntity.getMessage());
                    }
                } else {
                    againGetPayList();
                }
                break;
            case 1:
                BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                try {
                    if (baseContentEntity.getCode() == 0) {
                        PayResultEntity payResultEntity = GsonUtils.gsonToBean(result, PayResultEntity.class);
                        LinkedHashMap<String, String> publicParams = new LinkedHashMap<String, String>();
                        Map<String, String> resultMap = GsonUtils.gsonObjectToMaps(payResultEntity.getContent());
                        publicParams.putAll(resultMap);
                        PayUtil.getInstance(NewOrderPayActivity.this).createPay(publicParams, Constants.CAIWALLET_ENVIRONMENT);
                    } else {
                        ToastUtil.toastShow(NewOrderPayActivity.this, baseContentEntity.getMessage());
                    }
                } catch (Exception e) {
                    ToastUtil.toastShow(NewOrderPayActivity.this, baseContentEntity.getMessage());
                }
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        BaseContentEntity baseEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                        if (baseEntity.getCode() == 0) {
                            PayStatusEntity payStatusEntity = GsonUtils.gsonToBean(result, PayStatusEntity.class);
                            int orderStaus = payStatusEntity.getContent().getPay_success();
                            if (orderStaus == 2) {
                                if (TextUtils.isEmpty(errorNotice)) {
                                    errorNotice = "支付失败";
                                }
                                ToastUtil.toastShow(getApplicationContext(), errorNotice);
                                finish();
                            } else {
                                payResultQuery();
                            }
                        }
                    } catch (Exception e) {
                        payResultQuery();
                    }
                } else {
                    payResultQuery();
                }
                break;
            case 3:
                if (TextUtils.isEmpty(result)) {
                    createCzyOrder();
                } else {
                    try {
                        OrderChekEntity orderChekEntity = GsonUtils.gsonToBean(result, OrderChekEntity.class);
                        OrderChekEntity.ContentBean contentBean = orderChekEntity.getContent();
                        if ("1".equals(contentBean.getLimit())) {
                            if ("1".equals(contentBean.getIs_identity())) {
                                createCzyOrder();
                            } else {
                                shownoticeDialog(contentBean.getNote());
                            }
                        } else {
                            createCzyOrder();
                        }
                    } catch (Exception e) {
                        createCzyOrder();
                    }
                }
                break;
            case 4:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        RealNameTokenEntity entity = GsonUtils.gsonToBean(result, RealNameTokenEntity.class);
                        RealNameTokenEntity.ContentBean bean = entity.getContent();
                        AuthConfig.Builder configBuilder = new AuthConfig.Builder(bean.getBizToken(), R.class.getPackage().getName());
                        AuthSDKApi.startMainPage(this, configBuilder.build(), mListener);
                    } catch (Exception e) {
                        e.printStackTrace();
                        showCertificateFail();
                    }
                } else {
                    showCertificateFail();
                }
                break;
            case 5:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        if ("0".equals(code)) {
                            String content = jsonObject.getString("content");
                            if ("1".equals(content)) {
                                ToastUtil.toastShow(this, "认证成功");
                                editor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + shared.getInt(UserAppConst.Colour_User_id, 0), realName).commit();
                                createCzyOrder();
                            } else {
                                showCertificateFail();
                            }
                        } else {
                            showCertificateFail();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        showCertificateFail();
                    }
                } else {
                    showCertificateFail();
                }
                break;
        }
    }

    private void showCertificateFail() {
        CertificateResultDialog certificateResultDialog = new CertificateResultDialog(NewOrderPayActivity.this, R.style.dialog);
        certificateResultDialog.show();
    }


    @Override
    public void returnToCZY(String s, int i) {
        switch (i) {
            case 500: //支付成功
            case 502://支付失败
                payResultQuery();
                break;
            case 501: //主动取消支付
            case 503://开通彩白条回调
                if (!TextUtils.isEmpty(s)) {
                    ToastUtil.toastShow(NewOrderPayActivity.this, s);
                }
                break;
        }
    }


    private EditDialog noticeDialog = null;
    private NewUserModel newUserModel;
    private String realName;

    private void shownoticeDialog(String notice) {
        if (noticeDialog == null) {
            noticeDialog = new EditDialog(NewOrderPayActivity.this);
        }
        noticeDialog.setContent(notice);
        noticeDialog.show();
        noticeDialog.left_button.setText("取消");
        noticeDialog.left_button.setTextColor(Color.parseColor("#131719"));
        noticeDialog.left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noticeDialog != null) {
                    noticeDialog.dismiss();
                }
            }
        });
        noticeDialog.right_button.setText("前往认证");
        noticeDialog.right_button.setTextColor(Color.parseColor("#629ef0"));
        noticeDialog.right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (noticeDialog != null) {
                    noticeDialog.dismiss();
                }
                if (null == newUserModel) {
                    newUserModel = new NewUserModel(NewOrderPayActivity.this);
                }
                newUserModel.getRealNameToken(4, NewOrderPayActivity.this, false);
            }
        });
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
                if (null == newUserModel) {
                    newUserModel = new NewUserModel(NewOrderPayActivity.this);
                }
                newUserModel.submitRealName(5, idCardInfo.getIDcard(), realName, this);//提交实名认证
            }
        }
    };
}
