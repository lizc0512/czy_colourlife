package com.door.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;

import cn.net.cyberway.R;

/**
 * Created by hxg on 2019/10/9.
 */
public class DoorOverdueDialog extends Dialog {

    public Dialog mDialog;
    public TextView tv_cancel;
    public TextView tv_apply;

    public DoorOverdueDialog(Context context) {
        super(context, R.style.benefit_dialog_theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setContentView(R.layout.view_door_overdue);
        tv_cancel = findViewById(R.id.tv_cancel);
        tv_apply = findViewById(R.id.tv_apply);
    }

}