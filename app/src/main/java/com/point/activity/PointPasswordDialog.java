package com.point.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;

import com.external.eventbus.EventBus;
import com.external.gridpasswordview.GridPasswordView;

import cn.csh.colourful.life.utils.KeyBoardUtils;
import cn.net.cyberway.R;

import static com.user.UserMessageConstant.POINT_INPUT_PAYPAWD;

/**
 * 积分或饭票的描述
 */
public class PointPasswordDialog {

    public Dialog mDialog;
    private GridPasswordView grid_pay_pawd;
    private ImageView iv_close_dialog;


    public PointPasswordDialog(Activity activity) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_point_password, null);
        mDialog = new Dialog(activity, R.style.custom_dialog_theme);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        grid_pay_pawd = view.findViewById(R.id.grid_pay_pawd);
        iv_close_dialog = view.findViewById(R.id.iv_close_dialog);
        grid_pay_pawd.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {

            }

            @Override
            public void onInputFinish(String psw) {
                Message message = Message.obtain();
                message.what = POINT_INPUT_PAYPAWD;
                message.obj = psw;
                EventBus.getDefault().post(message);
                dismiss();
            }
        });
        iv_close_dialog.setOnClickListener(v -> dismiss());
        mDialog.setOnDismissListener(dialog -> KeyBoardUtils.hideSoftKeyboard(activity));
    }

    public void show() {
        mDialog.show();
        new Handler().postDelayed(() -> {
            grid_pay_pawd.performClick();
        }, 300);

    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }

}