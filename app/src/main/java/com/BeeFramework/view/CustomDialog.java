package com.BeeFramework.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import cn.net.cyberway.R;


/**
 * 弹出图片操作框
 */
public abstract class CustomDialog extends Dialog {

    private Context context;

    /**
     * 构造器
     *
     * @param context 上下文
     */
    public CustomDialog(Context context) {
        super(context, cn.csh.colourful.life.R.style.dialog);
        this.context = context;
        setContentView(R.layout.custom_qrcode_dialog);
        createDialog();
    }

    /**
     * 设置dialog
     */
    public void createDialog() {
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.width = WindowManager.LayoutParams.WRAP_CONTENT;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        initViews();
        if (!(context instanceof Activity)) {
            getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        }
    }

    public abstract void initViews();

    public CustomDialog closeDialog() {
        this.dismiss();
        return this;
    }

}
