package com.customerInfo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;

import cn.net.cyberway.R;

/**
 * 修改名字\昵称
 * Created by liusw on 2016/1/14.
 */
public class CustomerNameActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout czyTitleLayout;
    private ImageView mBack;
    private TextView mTitle;
    private EditText name_et;
    private Button complete_btn;
    private String type;
    private String name;
    private ImageView iv_delete_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_name);
        initPublic();
        initView();
        initData();
    }

    private void initPublic() {
        czyTitleLayout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        mBack.setOnClickListener(this);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czyTitleLayout, mBack, mTitle);
    }

    private void initView() {
        name_et = (EditText) findViewById(R.id.name_et);
        complete_btn = (Button) findViewById(R.id.complete_btn);
        iv_delete_name = (ImageView) findViewById(R.id.iv_delete_name);
        complete_btn.setOnClickListener(this);
        iv_delete_name.setOnClickListener(this);
    }

    private void initData() {
        String name = getIntent().getStringExtra("name");
        name_et.setText(name);
        name_et.setSelection(name_et.getText().length());
        type = getIntent().getStringExtra("type");
        if (type.equals("name")) {
            mTitle.setText("名字");
            name_et.setHint("请输入名字");
        } else if (type.equals("nikeName")) {
            mTitle.setText("昵称");
            name_et.setHint("请输入昵称");
        }  else if (type.equals("email")) {
            mTitle.setText("邮箱");
            name_et.setHint("请输入邮箱");
        }
        name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    iv_delete_name.setVisibility(View.GONE);
                } else {
                    iv_delete_name.setVisibility(View.VISIBLE);
                }
            }
        });
        if (!TextUtils.isEmpty(name)) {
            iv_delete_name.setVisibility(View.VISIBLE);
        } else {
            iv_delete_name.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.complete_btn:
                name = name_et.getText().toString().trim();
                if ("".equals(name)) {
                    if (type.equals("name")) {
                        ToastUtil.toastShow(this, "名字不能为空");
                    } else if (type.equals("nikeName")) {
                        ToastUtil.toastShow(this, "昵称不能为空");
                    } else if (type.equals("email")) {
                        ToastUtil.toastShow(this, "邮箱不能为空");
                    }
                } else {
                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    if (type.equals("name")) {
                        setResult(2, intent);
                    } else if (type.equals("nikeName")) {
                        setResult(3, intent);
                    }else if (type.equals("email")) {
                        if (!Utils.checkEmail(name)) {
                            ToastUtil.toastShow(this, "邮箱的格式不正确,请检查后重新输入");
                            return;
                        } else {
                            setResult(5, intent);
                        }
                    }
                    finish();
                }
                break;
            case R.id.iv_delete_name:
                name_et.setText("");
                break;
        }
    }

    public static void startCustomerNameActivityForResult(Context context, String name, String type) {
        Intent intent = new Intent(context, CustomerNameActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("type", type);
        ((Activity) context).startActivityForResult(intent, 1);
    }
}
