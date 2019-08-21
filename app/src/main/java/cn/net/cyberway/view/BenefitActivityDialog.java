package cn.net.cyberway.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import cn.net.cyberway.R;

/**
 * 彩惠活动弹窗
 * Created by hxg on 2019/8/20.
 */
public class BenefitActivityDialog {

    public Dialog mDialog;

    public ImageView iv_close;
    public ImageView iv_activity;

    public BenefitActivityDialog(Context context, int screenWidth) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.view_benefit_activity, null);

        mDialog = new Dialog(context, R.style.benefit_dialog_theme);
        mDialog.setContentView(view);
        mDialog.setCanceledOnTouchOutside(true);

        iv_close = view.findViewById(R.id.iv_close);
        iv_activity = view.findViewById(R.id.iv_activity);

        Window dialogWindow = mDialog.getWindow();
        WindowManager.LayoutParams lp = dialogWindow.getAttributes();
        lp.width = screenWidth;

        dialogWindow.setAttributes(lp);
    }

    public void show() {
        mDialog.show();
    }

    public void dismiss() {
        mDialog.dismiss();
    }

}