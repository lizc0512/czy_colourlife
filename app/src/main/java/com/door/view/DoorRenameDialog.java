package com.door.view;

import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import cn.net.cyberway.R;


/**
 * 门禁重命名
 * 2018/08/12
 */
public class DoorRenameDialog extends Dialog {
    Context context;

    public EditText dialog_content;
    public TextView dialog_title;


    public Button btn_open;
    public Button btn_cancel;

    public DoorRenameDialog(Context context, int theme) {
        super(context, theme);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.rename_door);
        dialog_title = (TextView) findViewById(R.id.dialog_title);
        btn_open = (Button) findViewById(R.id.btn_open);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        dialog_content = (EditText) findViewById(R.id.dialog_content);
        Window window = getWindow();
        window.setBackgroundDrawableResource(R.color.transparent);
        WindowManager.LayoutParams params = window.getAttributes();
        int density = getWidthPixels(context);
        params.width = density - 40;
        params.gravity = Gravity.CENTER;
        window.setAttributes(params);
        this.setCanceledOnTouchOutside(false);
    }

    private int getWidthPixels(Context context) {
        Resources resources = context.getResources();
        DisplayMetrics dm = resources.getDisplayMetrics();
        return dm.widthPixels;
    }
}