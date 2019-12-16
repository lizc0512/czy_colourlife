package com.point.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.external.eventbus.EventBus;
import com.external.gridpasswordview.GridPasswordView;

import cn.csh.colourful.life.utils.KeyBoardUtils;
import cn.net.cyberway.R;

import static com.user.UserMessageConstant.POINT_GET_CODE;
import static com.user.UserMessageConstant.POINT_INPUT_PAYPAWD;
import static com.user.UserMessageConstant.POINT_SHOW_CODE;

/**
 * 积分或饭票的描述
 */
public class PointChangeDeviceDialog {

    public Dialog mDialog;
    private TextView btn_cancel;
    public TextView btn_define;


    public PointChangeDeviceDialog(Activity activity) {
        LayoutInflater inflater = LayoutInflater.from(activity);
        View view = inflater.inflate(R.layout.dialog_change_device, null);
        mDialog = new Dialog(activity, R.style.custom_dialog_theme);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_define = view.findViewById(R.id.btn_define);

        btn_cancel.setOnClickListener(v -> dismiss());
        btn_define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Message message = Message.obtain();
                message.what = POINT_SHOW_CODE;
                EventBus.getDefault().post(message);
                dismiss();
            }
        });
        mDialog.setOnDismissListener(dialog -> KeyBoardUtils.hideSoftKeyboard(activity));
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        if (mDialog != null) {
            mDialog.dismiss();
        }
    }
}