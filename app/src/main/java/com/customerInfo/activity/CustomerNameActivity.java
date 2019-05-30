package com.customerInfo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.tendcloud.tenddata.TCAgent;
import com.wheel.src.kankan.wheel.widget.OnWheelChangedListener;
import com.wheel.src.kankan.wheel.widget.WheelView;
import com.wheel.src.kankan.wheel.widget.adapters.AbstractWheelTextAdapter;

import java.util.ArrayList;

import cn.net.cyberway.R;

/**
 * 修改名字\昵称
 * Created by liusw on 2016/1/14.
 */
public class CustomerNameActivity extends BaseActivity implements View.OnClickListener {

    private FrameLayout czyTitleLayout;
    private ImageView   mBack;
    private TextView    mTitle;
    private EditText    name_et;
    private Button      complete_btn;

    private String      type;
    private String      name;
    private ArrayList<String> genderList;
    private WheelView    choose_v;
    private LinearLayout ll_gender;
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
        czyTitleLayout     = (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack           = (ImageView)   findViewById(R.id.user_top_view_back);
        mTitle          = (TextView)    findViewById(R.id.user_top_view_title);
        mBack.setOnClickListener(this);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(),czyTitleLayout,mBack,mTitle);
    }

    private void initView() {
        name_et      = (EditText) findViewById(R.id.name_et);
        complete_btn = (Button) findViewById(R.id.complete_btn);
        choose_v     = (WheelView)findViewById(R.id.choose_wv);
        ll_gender    = (LinearLayout) findViewById(R.id.ll_choose);
        iv_delete_name    = (ImageView) findViewById(R.id.iv_delete_name);
        genderList   = new ArrayList<>();
        genderList.add(getResources().getString(R.string.customer_man));
        genderList.add(getResources().getString(R.string.customer_femal));
        complete_btn.setOnClickListener(this);
        iv_delete_name.setOnClickListener(this);
    }

    private void initData() {
        String name=getIntent().getStringExtra("name");
        name_et.setText(name);
        name_et.setSelection(name_et.getText().length());
        type = getIntent().getStringExtra("type");
        if (type.equals("name")){
            mTitle.setText("名字");
            name_et.setHint("请输入名字");
            TCAgent.onEvent(getApplicationContext(), "203009");
        }else if(type.equals("nikeName")){
            mTitle.setText("昵称");
            name_et.setHint("请输入昵称");
            TCAgent.onEvent(getApplicationContext(), "203005");
        }else if (type.equals("gender")){
            TCAgent.onEvent(getApplicationContext(), "203013");
            name_et.setFocusable(false);
            mTitle.setText("性别");
            name_et.setHint("请选择性别");
            ll_gender.setVisibility(View.VISIBLE);
            choose_v.setViewAdapter(new genderAdapter(this, genderList));
            choose_v.setVisibleItems(5);
            choose_v.setCurrentItem(0);
            name_et.setText(genderList.get(0));
            choose_v.addChangingListener(new OnWheelChangedListener() {
                @Override
                public void onChanged(WheelView wheel, int oldValue, int newValue) {
                    TCAgent.onEvent(getApplicationContext(), "203014");
                    name_et.setText(genderList.get(newValue));
                }
            });
        }
        name_et.setOnFocusChangeListener(new View.OnFocusChangeListener()
        {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!TextUtils.isEmpty(name_et.getText().toString())){
                    if (type.equals("name")){
                        TCAgent.onEvent(getApplicationContext(), "203010");
                    }else if(type.equals("nikeName")){
                        TCAgent.onEvent(getApplicationContext(), "203006");
                    }else if (type.equals("gender")){

                    }
                }
            }
        });
        name_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                 if (TextUtils.isEmpty(s.toString().trim())){
                     iv_delete_name.setVisibility(View.GONE);
                 }else{
                     iv_delete_name.setVisibility(View.VISIBLE);
                 }
            }
        });
        if (!TextUtils.isEmpty(name)){
            iv_delete_name.setVisibility(View.VISIBLE);
        }else{
            iv_delete_name.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.complete_btn:
                name = name_et.getText().toString();
                if ("".equals(name)) {
                    if (type.equals("name")){
                        ToastUtil.toastShow(this, "名字不能为空");
                    }else if (type.equals("nikeName")){
                        ToastUtil.toastShow(this, "昵称不能为空");
                    }else if (type.equals("gender")){
                        ToastUtil.toastShow(this, "请选择性别");
                    }
                }else {
                    Intent intent = new Intent();
                    intent.putExtra("name", name);
                    if (type.equals("name")) {
                        TCAgent.onEvent(getApplicationContext(), "203011");
                        setResult(2, intent);
                    } else if (type.equals("nikeName")){
                        TCAgent.onEvent(getApplicationContext(), "203007");
                        setResult(3, intent);
                    }else if (type.equals("gender")){
                        TCAgent.onEvent(getApplicationContext(), "203015");
                        setResult(4, intent);
                    }
                    finish();
                }
                break;
            case R.id. iv_delete_name:
                name_et.setText("");
                break;
        }
    }

    public static void startCustomerNameActivityForResult(Context context, String name , String type){
        Intent intent = new Intent(context, CustomerNameActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("type", type);
        ((Activity)context).startActivityForResult(intent, 1);
    }
    /**
     * 滚动选择栏适配器
     */
    private class genderAdapter extends AbstractWheelTextAdapter {
        private ArrayList<String> list;
        protected genderAdapter(Context context, ArrayList<String> list ) {
            super(context, R.layout.country_layout, NO_RESOURCE);//设置
            this.list = list ;
            setItemTextResource(R.id.country_name);//设置Item的布局
        }
        @Override
        public View getItem(int index, View cachedView, ViewGroup parent) {
            return super.getItem(index, cachedView, parent);
        }
        @Override
        public int getItemsCount() {
            return list.size();
        }
        @Override
        protected CharSequence getItemText(int index) {
            return list.get(index);
        }
    }
}
