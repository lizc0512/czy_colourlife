package com.point.password;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.CountDownTimer;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.external.eventbus.EventBus;
import com.user.UserAppConst;

import cn.net.cyberway.R;

import static com.user.UserMessageConstant.POINT_GET_CODE;


/**
 * 输入支付密码
 *
 * @author lining
 */
public class PopInputCodeView extends PopupWindow {
    private String loginMobile;
    private InputCodeView codeView;

    private View mMenuView;

    private Activity mContext;
    private TextView codeViewText;
    private TextView send_code_notice;

    public PopInputCodeView(final Activity context) {
        super(context);
        this.mContext = context;
        SharedPreferences shared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        loginMobile = shared.getString(UserAppConst.Colour_login_mobile, "");
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mMenuView = inflater.inflate(R.layout.pop_enter_inputcode, null);
        codeView = mMenuView.findViewById(R.id.code_view);
        // 监听键盘上方的返回
        codeViewText = codeView.getCodeViewText();
        send_code_notice = codeView.getCodeNoticeText();
        codeView.getVirtualKeyboardView().getLayoutBack().setVisibility(View.GONE);
        codeViewText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.what = POINT_GET_CODE;
                EventBus.getDefault().post(message);
            }
        });
        codeView.getCloseImage().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        // 设置SelectPicPopupWindow的View
        this.setContentView(mMenuView);
        // 设置SelectPicPopupWindow弹出窗体的宽
        this.setWidth(LayoutParams.MATCH_PARENT);
        // 设置SelectPicPopupWindow弹出窗体的高
        this.setHeight(LayoutParams.WRAP_CONTENT);
        // 设置SelectPicPopupWindow弹出窗体可点击
        this.setFocusable(true);
        // 设置SelectPicPopupWindow弹出窗体动画效果
        this.setAnimationStyle(R.style.pop_add_ainm);
        // 实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x66000000);
        // 设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                if (null != myTimeCount) {
                    myTimeCount.cancel();
                    myTimeCount = null;
                }
            }
        });
        codeViewText.performClick();
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
            codeViewText.setText("重新发送");
            codeViewText.setClickable(true);
            codeViewText.requestFocus();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            long currentSecond = millisUntilFinished / UserAppConst.INTERVAL;
            codeViewText.setText(mContext.getResources().getString(R.string.user_already_send) + "(" + currentSecond + "S)");
            if (currentSecond <= 57) {
                send_code_notice.setVisibility(View.INVISIBLE);
            }
        }
    }

    private MyTimeCount myTimeCount;

    public void getCodeSuccess() {
        if (myTimeCount != null) {
            myTimeCount.cancel();
            myTimeCount = null;
        }
        codeViewText.setClickable(false);
        myTimeCount = new MyTimeCount(60000, 1000);
        myTimeCount.start();
        send_code_notice.setVisibility(View.VISIBLE);
        send_code_notice.setText("验证码已发送至手机号" + loginMobile);
    }
}
