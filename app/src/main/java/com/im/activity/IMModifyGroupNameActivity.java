package com.im.activity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/15 9:57
 * @change
 * @chang time
 * @class describe  修改或设置备注名
 */

public class IMModifyGroupNameActivity extends BaseActivity implements View.OnClickListener {
    public static final String REMARKNAME = "remarkname";
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView user_top_view_right;
    private EditText ed_remark_name;
    private String remarkName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_group_name);
        remarkName = getIntent().getStringExtra(REMARKNAME);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_top_view_right = findViewById(R.id.user_top_view_right);
        ed_remark_name = findViewById(R.id.ed_remark_name);
        user_top_view_title.setText("备注姓名");
        user_top_view_right.setText("保存");
        user_top_view_back.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        ed_remark_name.setText(remarkName);
        ed_remark_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                remarkName = s.toString().trim();
                if (TextUtils.isEmpty(remarkName)) {
                    user_top_view_right.setEnabled(false);
                    user_top_view_right.setTextColor(getResources().getColor(R.color.color_bbc0cb));
                } else {
                    user_top_view_right.setEnabled(true);
                    user_top_view_right.setTextColor(getResources().getColor(R.color.color_45adff));
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right: //保存用户的备注名

                break;
        }
    }
}
