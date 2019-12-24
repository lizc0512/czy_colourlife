package com.setting.activity;

import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.external.eventbus.EventBus;
import com.user.UserAppConst;
import com.user.UserMessageConstant;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.ChangeLanguageHelper;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  多语言选择的修改
 */
public class ChoiceLanguageActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private RelativeLayout search_layout;//返回
    private RecyclerView rv_community;
    private TextView tv_near_parking;
    private List<String> dataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_eparking);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.choice_language));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        TextView user_top_view_right = findViewById(R.id.user_top_view_right);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setText(getResources().getString(R.string.customer_save_address));
        user_top_view_right.setTextColor(getResources().getColor(R.color.textcolor_27a2f0));
        search_layout = findViewById(R.id.search_layout);
        search_layout.setVisibility(View.GONE);
        tv_near_parking = (TextView) findViewById(R.id.tv_near_parking);
        rv_community = (RecyclerView) findViewById(R.id.rv_community);
        imageView_back.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        dataSource = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChoiceLanguageActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_community.setLayoutManager(layoutManager);
        String[] languageArr = getResources().getStringArray(R.array.language_list);
        for (int j = 0; j < languageArr.length; j++) {
            dataSource.add(languageArr[j]);
        }
    }

    private int choicePos = 0;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:
                editor.putInt(UserAppConst.CURRENTLANGUAGE, choicePos);
                editor.commit();
                recreate();
                ChangeLanguageHelper.changeLanguage(ChoiceLanguageActivity.this, choicePos);
                Message msg = Message.obtain();
                msg.what = UserMessageConstant.CHANGE_DIFF_LANG;
                EventBus.getDefault().post(msg);
                setResult(200);
                finish();
                break;
        }
    }
}
