package com.cashier;

import android.app.Activity;
import android.os.Message;

/**
 * 收银台的抽象类，提供收银台支付的通用接口和结果回调
 */
public class Cashier {

    public interface CashierListener {
        public void OnPaySucceed();

        public void OnPayFailed();

        public void OnPayCanceled();
    }

    public static final int BASE = 1600;
    public static final int CASH_SUCCESS = BASE + 1;
    public static final int CASH_FAIL = BASE + 2;
    public static final int CASH_CANCEL = BASE + 2;

    private Activity mActivity;
    private CashierListener mCashierListener;

    public Cashier(android.app.Activity activity) {
        mActivity = activity;
        CashierEventBus.getDefault().register(this);
    }

    /**
     * @param orderSn  订单号
     * @param listener 支付结果回调
     */
    public void pay(String orderSn, CashierListener listener) {
        mCashierListener = listener;

    }

    public void changePassword() {

    }

    public void forgetPassword() {

    }

    public void bindId() {

    }

    public void finish() {

    }

    public void onEvent(Object event) {
        Message message = (Message) event;
        if (message.what == CASH_SUCCESS) {
            mCashierListener.OnPaySucceed();

        } else if (message.what == CASH_FAIL) {
            mCashierListener.OnPayFailed();
        } else if (message.what == CASH_CANCEL) {
            mCashierListener.OnPayCanceled();
        }

        if (CashierEventBus.getDefault().isregister(this)) {
            CashierEventBus.getDefault().unregister(this);
        }


    }
}
