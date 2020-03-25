package com.cashier.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.icu.math.BigDecimal;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.Gravity;
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
import com.cashier.protocolchang.PayEntity;
import com.cashier.protocolchang.PayResultEntity;
import com.cashier.protocolchang.PayStatusEntity;
import com.chinaums.pppay.unify.UnifyPayListener;
import com.chinaums.pppay.unify.UnifyPayPlugin;
import com.chinaums.pppay.unify.UnifyPayRequest;
import com.external.eventbus.EventBus;
import com.jdpaysdk.author.JDPayAuthor;
import com.lhqpay.ewallet.keepIntact.Listener;
import com.lhqpay.ewallet.keepIntact.MyListener;
import com.lhqpay.ewallet.keepIntact.PayListenerUtils;
import com.lhqpay.ewallet.keepIntact.PayUtil;
import com.lhqpay.ewallet.keepIntact.WXPayEntryActivity;
import com.nohttp.entity.BaseContentEntity;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.RequestEncryptionUtils;
import com.point.activity.ChangePawdTwoStepActivity;
import com.point.activity.PointChangeDeviceDialog;
import com.point.entity.PointTransactionTokenEntity;
import com.point.model.PointModel;
import com.point.password.PopEnterPassword;
import com.point.password.PopInputCodeView;
import com.popupScreen.PopupScUtils;
import com.realaudit.activity.RealCommonSubmitActivity;
import com.setting.activity.CertificateResultDialog;
import com.setting.activity.EditDialog;
import com.setting.activity.HtmlPayDialog;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.model.NewUserModel;
import com.user.model.RequestFailModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.BroadcastReceiverActivity;
import cn.net.cyberway.home.entity.PushNotificationEntity;
import cn.net.cyberway.utils.LinkParseUtil;

import static com.user.UserMessageConstant.POINT_GET_CODE;
import static com.user.UserMessageConstant.POINT_INPUT_CODE;
import static com.user.UserMessageConstant.POINT_INPUT_PAYPAWD;
import static com.user.UserMessageConstant.POINT_SHOW_CODE;
import static com.user.UserMessageConstant.REAL_FAIL_STATE;
import static com.user.UserMessageConstant.WEIXIN_PAY_MSG;


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

public class NewOrderPayActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse, Listener, MyListener, UnifyPayListener {

    public static final String ORDER_SN = "ORDER_SN";
    public static final String PAY_CHANNEL = "PAY_CHANNEL";
    private NewOrderPayModel newOrderPayModel;
    private FrameLayout czy_title_layout;
    private ImageView user_top_view_back;
    private TextView user_top_view_title;

    private RelativeLayout order_pay_layout;//订单支付布局
    private TextView tv_business_amount;  //人民币价格
    private TextView tv_ticket_amount;//饭票价格

    private RelativeLayout bottom_pay_layout;
    private String dialogTitle = "";


    private LinearLayout ticket_payment_layout;  //动态布局添加各种支付方式

    private Button btn_sure_pay; //确认支付
    private TextView tv_pay_money; //支付的
    private ImageView iv_pay_meal; //饭票支付的类型

    private List<ImageView> imageViewList = new ArrayList<ImageView>();
    private double meal_total = 0; //饭票的
    private double total_fee = 0; //现金的
    private String sn = "";
    private String token;
    private String state;
    private String encrypt;
    private String loginMobile;
    private PopInputCodeView popInputCodeView;
    private EditDialog noticeDialog = null;
    private NewUserModel newUserModel;
    private String payListResult;


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
        loginMobile = shared.getString(UserAppConst.Colour_login_mobile, "");
        newOrderPayModel = new NewOrderPayModel(NewOrderPayActivity.this);
        newUserModel = new NewUserModel(NewOrderPayActivity.this);
        newOrderPayModel.getPayOrderDetails(0, sn, false, this);
        newOrderPayModel.getPayPopupDate(6, this);
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
            String payment_name = payListBean.getPayment_name();
            if (normalDiscount > 0 && normalDiscount != 100) {
                tv_paystyle_title.setText(payment_name + " (" + normalDiscount + "折)");
            } else {
                tv_paystyle_title.setText(payment_name);
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
                            dialogTitle = payment_name;
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
                    select_ticket_pay.setTag(otherPaymentUUid + "," + listBean.getPay_channel());
                    secondTicketShow.put(mealTypeId, true);
                    secondTicketMap.put(mealTypeId, dataViewList);
                    imageViewList.add(select_ticket_pay);
                    ticket_pay_layout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if (otherMealAmount >= meal_total * mealDiscount / 100.0f) {
                                payment = mealpaymentType;
                                discount = mealDiscount;
                                dialogTitle = payment_name;
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
                    select_pay_type.setTag(paymentUUid + "," + payListBean.getPay_channel());
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
            String payment_name = payListBean.getPayment_name();
            if (balanceDiscount > 0 && balanceDiscount != 100) {
                tv_paystyle_title.setText(payment_name + " (" + balanceDiscount + "折)");
            } else {
                tv_paystyle_title.setText(payment_name);
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
                            dialogTitle = payment_name;
                            payChannelId = select_pay_type.getTag().toString().trim();
                            changePayChannelStatus();
                        }
                    } else {
                        discount = balanceDiscount;
                        payment = balancePaymentType;
                        is_native = balanceIsNative;
                        pay_url = balancePaymentUrl;
                        dialogTitle = payment_name;
                        payChannelId = select_pay_type.getTag().toString().trim();
                        changePayChannelStatus();
                    }
                }
            });
            select_pay_type.setTag(payListBean.getPayment_uuid() + "," + payListBean.getPay_channel());
            imageViewList.add(select_pay_type);
            if (i == size - 1) {
                tv_paystyle_view.setVisibility(View.GONE);
            } else {
                tv_paystyle_view.setVisibility(View.VISIBLE);
            }
            if (TextUtils.isEmpty(balancePayAmount)) {
                tv_pay_balance.setVisibility(View.GONE);
                showStatus(select_pay_type, balanceDiscount, balancePaymentType, balanceIsNative, balancePaymentUrl, payment_name);
            } else {
                tv_pay_balance.setVisibility(View.VISIBLE);
                setBalanceColor(getFormatMoney(balancePayAmount), tv_pay_balance, select_pay_type, balanceDiscount, balancePaymentType, balanceIsNative, balancePaymentUrl, payment_name);
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
            String payment_name = payListBean.getPayment_name();
            if (otherDiscount > 0 && otherDiscount != 100) {
                tv_paystyle_title.setText(payment_name + " (" + otherDiscount + "折)");
            } else {
                tv_paystyle_title.setText(payment_name);
            }
            tv_pay_balance.setVisibility(View.GONE);
            pay_type_layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    payment = otherPayment;
                    discount = otherDiscount;
                    is_native = otherNative;
                    pay_url = otherPayUrl;
                    dialogTitle = payment_name;
                    payChannelId = select_pay_type.getTag().toString().trim();
                    changePayChannelStatus();
                }
            });
            select_pay_type.setTag(payListBean.getPayment_uuid() + "," + payListBean.getPay_channel());
            imageViewList.add(select_pay_type);
            if (i == size - 1) {
                tv_paystyle_view.setVisibility(View.GONE);
            } else {
                tv_paystyle_view.setVisibility(View.VISIBLE);
            }
            showStatus(select_pay_type, otherDiscount, otherPayment, otherNative, otherPayUrl, payment_name);
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
            showStatus(selectImage, discount, payment, is_native, pay_url, "");
        } else {
            tv_pay_balance.setTextColor(Color.parseColor("#fe6262"));
            selectImage.setVisibility(View.GONE);
        }
    }

    /***花样年余额够不够支付订单现金支付的判断***/
    private void setBalanceColor(double balanceAmout, TextView tv_pay_balance, ImageView selectImage, int discount, int payment, int is_native, String pay_url, String payment_name) {
        if (balanceAmout >= total_fee * discount / 100.0f) {  //余额可以选中
            tv_pay_balance.setTextColor(Color.parseColor("#a3aaae"));
            selectImage.setVisibility(View.VISIBLE);
            showStatus(selectImage, discount, payment, is_native, pay_url, payment_name);
        } else {
            tv_pay_balance.setTextColor(Color.parseColor("#fe6262"));
            selectImage.setVisibility(View.GONE);
        }
    }

    /***显示默认的支付方式***/
    private void showStatus(ImageView selectImage, int discount, int payment, int is_native, String pay_url, String payment_name) {
        if (TextUtils.isEmpty(payChannelId)) {
            payChannelId = selectImage.getTag().toString();
            this.discount = discount;
            this.payment = payment;
            this.is_native = is_native;
            this.pay_url = pay_url;
            dialogTitle = payment_name;
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
                createCzyOrder();
                break;
        }
    }


    private void createCzyOrder() {
        if (TextUtils.isEmpty(payChannelId)) { //未返回支付方式用户点击上传日记
            ToastUtil.toastShow(NewOrderPayActivity.this, "请选择一种支付方式");
            RequestFailModel failModel = new RequestFailModel(NewOrderPayActivity.this);
            Map<String, Object> params = new HashMap<>();
            params.put("sn", sn);
            params.put("response", payListResult);
            failModel.uploadRequestFailLogs(11, RequestEncryptionUtils.getMapToString(params));
        } else {
            if (is_native == 1) {
                int index = payChannelId.indexOf(",");
                newOrderPayModel.goOrderPay(1, sn, payChannelId.substring(0, index), this);
            } else {
                LinkParseUtil.parse(NewOrderPayActivity.this, pay_url, "");
            }
        }
    }


    private String errorNotice;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {//彩钱包里面支付包,双乾支付的回调
            if (10010 == resultCode) {
                // 邻花钱插件返回
                if (data != null) {
                    int code = data.getIntExtra("code", -1);
                    if (code == 200) {
                        payResultQuery();
                    } else if (code == 1000) {
                        //交易取消
                    } else if (code == 1020) {
                        //交易失败
                        payResultQuery();
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
        } else if (requestCode == 10000) {  //彩之云接入支付宝支付的回调
            if (Activity.RESULT_OK == resultCode) {
                String result = data.getStringExtra("pay_result");
                if ("success".equals(result)) {
                    payResultQuery();
                } else if ("cancel".equals(result)) {
                    ToastUtil.toastShow(NewOrderPayActivity.this, "用户取消支付");
                } else {
                    ToastUtil.toastShow(NewOrderPayActivity.this, "支付失败");
                }
            }

        } else if (com.jdpaysdk.author.Constants.PAY_RESPONSE_CODE == resultCode) {//京东支付返回信息的回调
            String result = data.getStringExtra(JDPayAuthor.JDPAY_RESULT);
            try {
                JSONObject jsonObject = new JSONObject(result);
                String errorCode = jsonObject.optString("errorCode");
                if (!jsonObject.isNull("payStatus")) {
                    String payStatus = jsonObject.optString("payStatus");
                    if ("JDP_PAY_SUCCESS".equals(payStatus)) {
                        payResultQuery();
                    } else if ("JDP_PAY_FAIL".equals(payStatus)) {
                        ToastUtil.toastShow(NewOrderPayActivity.this, "支付失败[" + errorCode + "]");
                    } else if ("JDP_PAY_CANCEL".equals(payStatus)) {
                        ToastUtil.toastShow(NewOrderPayActivity.this, "用户取消支付");
                    } else {
                        ToastUtil.toastShow(NewOrderPayActivity.this, errorCode);
                    }
                } else {
                    ToastUtil.toastShow(NewOrderPayActivity.this, errorCode);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    public void payResultQuery() {
        int index = payChannelId.indexOf(",");
        Intent intent = new Intent(this, OrderWaitResultActivity.class);
        intent.putExtra(ORDER_SN, sn);
        if (index > 0) {
            intent.putExtra(PAY_CHANNEL, payChannelId.substring(0, index));
        } else {
            intent.putExtra(PAY_CHANNEL, payChannelId);
        }
        startActivityForResult(intent, 2000);
    }


    @Override
    public void onPayResult(int error_code, String message) {  //双乾微信支付回调
        if (error_code == 200) {
            //支付成功
//            payResult();
            payResultQuery();
        } else if (error_code == 1000) {
            //交易取消

        } else if (error_code == 1020) {
            //交易失败  code==1020
            errorNotice = message;
            if (message.contains("安装微信")) {
                ToastUtil.toastShow(NewOrderPayActivity.this, message);
            } else {
                payResultQuery();
            }
        }
    }


    private BroadcastReceiverActivity broadcast;
    private int showPayResultDialog = 0;
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

        if (showPayResultDialog == 1) {
            showH5PayResultDialog();
            showPayResultDialog = 0;
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
        switch (message.what) {
            case UserMessageConstant.GUANGCAI_PAY_MSG:
                showH5PayResultDialog();
                break;
            case UserMessageConstant.NET_CONN_CHANGE:
                if (NetworkUtil.isConnect(getApplicationContext())) {
                    againGetPayList();
                }
                break;
            case WEIXIN_PAY_MSG: //微信支付回调
                int payCode = message.arg1;
                if (payCode == 0) {
                    payResultQuery();
                } else if (payCode == -1) {
                    ToastUtil.toastShow(NewOrderPayActivity.this, message.obj.toString());
                } else if (payCode == -2) {
                    ToastUtil.toastShow(NewOrderPayActivity.this, "用户取消支付");
                }
                break;
            case POINT_INPUT_PAYPAWD://积分支付 设置密码成功后的回调
                String password = (String) message.obj;
                newOrderPayModel.goOrderPayByPoint(8, encrypt, password, token, NewOrderPayActivity.this);
                break;
            case POINT_SHOW_CODE:
                popInputCodeView = new PopInputCodeView(NewOrderPayActivity.this);
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
                PointModel pointModel = new PointModel(NewOrderPayActivity.this);
                pointModel.pointCheckCode(10, loginMobile, code, NewOrderPayActivity.this);
                break;
            case UserMessageConstant.REAL_SUCCESS_STATE:
                if ("3".equals(state)) {
                    state = "2";
                    Intent pawd_intent = new Intent(NewOrderPayActivity.this, ChangePawdTwoStepActivity.class);
                    startActivity(pawd_intent);
                } else {
                    showPayDialog();
                }
                break;
            case REAL_FAIL_STATE:
                showCertificateFail();
                break;
        }
    }

    private void getChangeDeviceCode() {
        newUserModel.getSmsCode(9, loginMobile, 7, 1, NewOrderPayActivity.this);
    }


    private void againGetPayList() {
        if (TextUtils.isEmpty(payChannelId)) {
            newOrderPayModel.getPayOrderDetails(0, sn, true, this);
        }
    }

    /***光彩支付的结果**/
    private void showH5PayResultDialog() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    final HtmlPayDialog htmlPayDialog = new HtmlPayDialog(NewOrderPayActivity.this);
                    htmlPayDialog.show();
                    if (!TextUtils.isEmpty(dialogTitle)) {
                        htmlPayDialog.setContent("请确认" + dialogTitle + "支付是否完成");
                    }
                    htmlPayDialog.tv_again_pay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            htmlPayDialog.dismiss();
                            newOrderPayModel.getPayOrderStatus(3, sn, NewOrderPayActivity.this);
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
                } catch (Exception e) {

                }
            }
        }, 500);
    }


    //彩之云微信支付
    private void wexinPayOrder(Map<String, String> resultMap) {
        if (resultMap.containsKey("wxPayInfo")) {
            String wxPayInfo = resultMap.get("wxPayInfo");
            JSONObject jsonObject = null;
            try {
                jsonObject = new JSONObject(wxPayInfo);
                String appId = jsonObject.optString("appId");
                String partnerId = jsonObject.optString("partnerId");
                String prepayId = jsonObject.optString("prepayId");
                String nonceStr = jsonObject.optString("nonceStr");
                String timeStamp = jsonObject.optString("timeStamp");
                String packageValue = jsonObject.optString("packageValue");
                String sign = jsonObject.optString("sign");
                IWXAPI mWeixinAPI = WXAPIFactory.createWXAPI(this, appId);
                // 将该app注册到微信
                mWeixinAPI.registerApp(appId);
                PayReq req = new PayReq();
                req.appId = appId;
                req.partnerId = partnerId;
                req.prepayId = prepayId;
                req.nonceStr = nonceStr;
                req.timeStamp = timeStamp;
                req.packageValue = packageValue;//"Sign=" + packageValue;
                req.sign = sign;
                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                mWeixinAPI.sendReq(req);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.toastShow(NewOrderPayActivity.this, resultMap.get("respMsg") + "[" + resultMap.get("respCode") + "]");
        }
    }


    //京东支付
    private void jindongPayOrder(Map<String, String> resultMap) {
        JDPayAuthor jdPayAuthor = new JDPayAuthor();
        String orderId = resultMap.get("order_id");
        String merchant = resultMap.get("merchant");
        String appId = "4c36f573025fd6997396e8f387c745d9";
        String signData = resultMap.get("sign_data");
        String extraInfo = "";
        jdPayAuthor.author(NewOrderPayActivity.this, orderId, merchant, appId, signData, extraInfo);
    }

    //银联聚合支付
    private void unionPayOrder(Map<String, String> resultMap,String  payChannel) {
        UnifyPayRequest msg = new UnifyPayRequest();
        msg.payChannel = payChannel;
        if (!resultMap.containsKey("appPayRequest")) {
            ToastUtil.toastShow(NewOrderPayActivity.this, "服务器返回数据格式有问题，缺少“appPayRequest”字段");
            return;
        } else {
            showPayResultDialog = 1;
            msg.payData = resultMap.get("appPayRequest");
            UnifyPayPlugin unifyPayPlugin = UnifyPayPlugin.getInstance(this);
            unifyPayPlugin.setListener(this);
            unifyPayPlugin.sendPayRequest(msg);
        }
    }


    private void pointPayOrder() { //判断用户是否实名设置支付密码
        PointModel pointModel = new PointModel(NewOrderPayActivity.this);
        pointModel.getTransactionToken(7, NewOrderPayActivity.this);
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        payListResult = result;
                        addPayTypeView(result);
                    } catch (Exception e) {
                        BaseContentEntity baseEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                        ToastUtil.toastShow(NewOrderPayActivity.this, baseEntity.getMessage());
                    }
                } else {
                    againGetPayList();
                }
                break;
            case 1: //支付渠道返回各个第三方的对接参数
                BaseContentEntity baseContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                int code = baseContentEntity.getCode();
                try {
                    if (code == 0) {
                        PayResultEntity payResultEntity = GsonUtils.gsonToBean(result, PayResultEntity.class);
                        String content = payResultEntity.getContent();
                        if (TextUtils.isEmpty(content)) { //处理后台content内容为jsonobject对象时
                            JSONObject jsonObject = new JSONObject(result);
                            if (!jsonObject.isNull("content")) {
                                content = jsonObject.optJSONObject("content").toString();
                            }
                        }
                        if (!TextUtils.isEmpty(content)) {
                            Map<String, String> resultMap = GsonUtils.gsonObjectToMaps(content);
                            if (payChannelId.endsWith("6")) {
                                //京东支付
                                jindongPayOrder(resultMap);
                            } else if (payChannelId.endsWith("7")) {
                                //工行微信
                                wexinPayOrder(resultMap);
                            } else if (payChannelId.endsWith("2")) { //彩之云积分支付
                                if (resultMap != null && resultMap.containsKey("encrypt")) {
                                    encrypt = resultMap.get("encrypt");
                                }
                                pointPayOrder();
                            } else if (payChannelId.endsWith("9")) {
                                //银联支付宝
                                unionPayOrder(resultMap,UnifyPayRequest.CHANNEL_ALIPAY);
                            } else if (payChannelId.endsWith("12")){
                                //银联微信
                                unionPayOrder(resultMap,UnifyPayRequest.CHANNEL_WEIXIN);
                            }else {
                                //彩钱包支付
                                LinkedHashMap<String, Object> publicParams = new LinkedHashMap<String, Object>();
                                publicParams.putAll(resultMap);
                                PayUtil.getInstance(NewOrderPayActivity.this).createPay(publicParams, Constants.CAIWALLET_ENVIRONMENT);
                            }
                        } else {
                            ToastUtil.toastShow(NewOrderPayActivity.this, "数据格式异常,请稍后重试");
                        }
                    } else if (code == 305) {
                        showNoticeDialog(baseContentEntity.getMessage());
                    } else {
                        ToastUtil.toastShow(NewOrderPayActivity.this, baseContentEntity.getMessage());
                    }
                } catch (Exception e) {
                    ToastUtil.toastShow(NewOrderPayActivity.this, baseContentEntity.getMessage());
                }
                break;
            case 3://查询订单状态
                try {
                    BaseContentEntity queryContentEntity = GsonUtils.gsonToBean(result, BaseContentEntity.class);
                    if (queryContentEntity.getCode() == 0) {
                        PayStatusEntity payStatusEntity = GsonUtils.gsonToBean(result, PayStatusEntity.class);
                        int orderStaus = payStatusEntity.getContent().getPay_success();
                        if (orderStaus == 2) {
                            createCzyOrder();
                        } else {
                            payResultQuery();
                        }
                    }
                } catch (Exception e) {
                    createCzyOrder();
                }
                break;
            case 6:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        PushNotificationEntity pushNotificationEntity = GsonUtils.gsonToBean(result, PushNotificationEntity.class);
                        List<PushNotificationEntity.ContentBean> newPopList = pushNotificationEntity.getContent();
                        int newSize = newPopList.size();
                        ArrayList<String> imageList = new ArrayList<>();
                        ArrayList<String> urlList = new ArrayList<>();
                        ArrayList<String> descList = new ArrayList<>();
                        for (int i = 0; i < newSize; i++) {
                            PushNotificationEntity.ContentBean contentBean = newPopList.get(i);
                            imageList.add(contentBean.getImg_url());
                            urlList.add(contentBean.getLink_url());
                            descList.add(contentBean.getMsg_title());
                        }
                        PopupScUtils.getInstance().jump(this, urlList, imageList, descList);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 7://判断用户支付密码和实名的状态
                try {
                    PointTransactionTokenEntity pointTransactionTokenEntity = GsonUtils.gsonToBean(result, PointTransactionTokenEntity.class);
                    PointTransactionTokenEntity.ContentBean contentBean = pointTransactionTokenEntity.getContent();
                    token = contentBean.getToken();
                    state = contentBean.getState();
                    String dev_change = contentBean.getDev_change();
                    switch (state) {
                        case "2"://已实名未设置支付密码
                            Intent intent = new Intent(NewOrderPayActivity.this, ChangePawdTwoStepActivity.class);
                            startActivity(intent);
                            break;
                        case "3"://未实名未设置支付密码
                        case "4"://未实名已设置支付密码
                            Intent realIntent = new Intent(NewOrderPayActivity.this, RealCommonSubmitActivity.class);
                            realIntent.putExtra(RealCommonSubmitActivity.SHOWFAILDIALOG, 1);
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
            case 8://彩之云积分支付成功
                payResultQuery();
                break;
            case 9: //短信验证码发送成功
                if (null != popInputCodeView) {
                    popInputCodeView.getCodeSuccess();
                }
                break;
            case 10://短信验证码验证通过
                showPayDialog();
                break;
        }
    }

    /**
     * 弹出更换设备支付的选择框
     **/
    private void showCodeDialog() {
        PointChangeDeviceDialog pointChangeDeviceDialog = new PointChangeDeviceDialog(NewOrderPayActivity.this);
        pointChangeDeviceDialog.show();
    }

    /***支付密码的弹窗**/
    private void showPayDialog() {
        PopEnterPassword popEnterPassword = new PopEnterPassword(this);
        popEnterPassword.show();
    }

    /***实名认证的弹窗**/
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


    private void showNoticeDialog(String notice) {
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
                Intent intent = new Intent(NewOrderPayActivity.this, RealCommonSubmitActivity.class);
                intent.putExtra(RealCommonSubmitActivity.SHOWFAILDIALOG, 1);
                startActivity(intent);
            }
        });
    }

    /*
     * 银联支付回调
     * */
    @Override
    public void onResult(String s, String s1) {
        if (s.equals("0000")) {
            payResultQuery();
        } else {
            //其他
            ToastUtil.toastShow(NewOrderPayActivity.this, s1);
        }
    }
}
