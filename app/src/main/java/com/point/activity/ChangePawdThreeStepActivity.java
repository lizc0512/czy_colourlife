package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.external.gridpasswordview.GridPasswordView;
import com.external.gridpasswordview.PasswordType;

import cn.csh.colourful.life.utils.KeyBoardUtils;
import cn.net.cyberway.R;
/***
 * 修改密码第三步
 */
public class ChangePawdThreeStepActivity extends BaseActivity implements View.OnClickListener {
    public static final String OLDPAYPAWD = "oldpaypawd";
    public static final String NEWPAYPAWD = "newpaypawd";
    public static final String PAWDTYPE = "pawdtype";//支付密码的类型  0表示设置密码  1表示修改密码 2 表示忘记密码
    private ImageView mBack;
    private TextView mTitle;
    private TextView tv_tips_content;
    private Button btn_define;
    private GridPasswordView gridPasswordView_cqb;
    private String oldPayPawd;
    private String newPayPawd;
    private String definePayPawd;
    private int passwordType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
        setContentView(R.layout.activity_password_change_layout);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        tv_tips_content = findViewById(R.id.tv_tips_content);
        btn_define = findViewById(R.id.btn_define);
        gridPasswordView_cqb = findViewById(R.id.grid_pawd_view);
        gridPasswordView_cqb.setPasswordType(PasswordType.NUMBER);
        mBack.setOnClickListener(this);
        btn_define.setVisibility(View.VISIBLE);
        btn_define.setEnabled(false);
        btn_define.setOnClickListener(this);
        Intent intent=getIntent();
        oldPayPawd=intent.getStringExtra(OLDPAYPAWD);
        newPayPawd=intent.getStringExtra(NEWPAYPAWD);
        passwordType=intent.getIntExtra(PAWDTYPE,0);
        switch (passwordType){
            case 1:
                mTitle.setText("修改支付密码");
                tv_tips_content.setText("请确认支付密码");
                break;
            case 2:
                mTitle.setText("重置支付密码");
                tv_tips_content.setText("请再次确认支付密码");
                break;
            default:
                mTitle.setText("设置支付密码");
                tv_tips_content.setText("请确认支付密码");
                break;
        }
        gridPasswordView_cqb.setOnPasswordChangedListener(new GridPasswordView.OnPasswordChangedListener() {
            @Override
            public void onTextChanged(String psw) {
                btn_define.setEnabled(false);
                btn_define.setBackgroundResource(R.drawable.point_password_default_bg);
            }

            @Override
            public void onInputFinish(String psw) {
                btn_define.setEnabled(true);
                btn_define.setBackgroundResource(R.drawable.point_password_click_bg);
                definePayPawd=psw;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_define:
                if (passwordType==1){
                    //修改支付密码
                }else if (passwordType==2){
                    //忘记支付密码

                }else{ //设置支付密码

                }
                break;
        }

    }
}
