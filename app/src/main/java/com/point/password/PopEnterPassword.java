package com.point.password;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.PopupWindow;

import com.external.eventbus.EventBus;
import com.point.activity.ForgetPayPawdActivity;

import cn.net.cyberway.R;

import static com.user.UserMessageConstant.POINT_INPUT_PAYPAWD;


/**
 * 输入支付密码
 *
 * @author lining
 */
public class PopEnterPassword extends PopupWindow {

    private PasswordView pwdView;

    private View mMenuView;

    private Activity mContext;

    public PopEnterPassword(final Activity context) {

        super(context);

        this.mContext = context;

        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        mMenuView = inflater.inflate(R.layout.pop_enter_password, null);

        pwdView = (PasswordView) mMenuView.findViewById(R.id.pwd_view);

        //添加密码输入完成的响应
        pwdView.setOnFinishInput(new OnPasswordInputFinish() {
            @Override
            public void inputFinish(final String password) {
                new Thread(new Runnable() {

                    @Override
                    public void run() {
                        // 模拟耗时的操作。
                        try {
                            Thread.sleep(100);

                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        mContext.runOnUiThread(new Runnable() {

                            @Override
                            public void run() {
                                Message message = Message.obtain();
                                message.what = POINT_INPUT_PAYPAWD;
                                message.obj = password;
                                EventBus.getDefault().post(message);
                                dismiss();
                            }
                        });
                    }
                }).start();
            }
        });

        // 监听X关闭按钮
        pwdView.getImgCancel().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        // 监听键盘上方的返回
        pwdView.getVirtualKeyboardView().getLayoutBack().setVisibility(View.GONE);
        pwdView.getForgetText().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent forget_intent = new Intent(mContext, ForgetPayPawdActivity.class);
                mContext.startActivity(forget_intent);
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

    }
}