package com.message.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.DateUtils;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.message.model.MessageModel;
import com.message.model.SYSTEMMSG;
import com.message.model.SYSTEMMSG_INFO;
import com.message.protocol.PushmessageViewGetApi;
import com.message.protocol.PushmessageViewGetResponse;

import cn.net.cyberway.R;

/**
 * 系统通知详情
 * Created by chenql on 16/1/13.
 */
public class SystemMsgDetailActivity extends BaseActivity implements HttpApiResponse {

    private TextView tv_type;
    private TextView tv_time;
    private TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_system_msg_detail);

        initTopView();

        initView();

        initData();
    }

    private void initView() {
        tv_type = (TextView) findViewById(R.id.tv_type);
        tv_time = (TextView) findViewById(R.id.tv_time);
        tv_content = (TextView) findViewById(R.id.tv_content);
    }

    private void initData() {
        Bundle bundle = getIntent().getExtras();
        Intent intent = getIntent();
        SYSTEMMSG systemMsg = (SYSTEMMSG) bundle.getSerializable("systemMsg");

        MessageModel messageModel = new MessageModel(SystemMsgDetailActivity.this);
        String m_id = intent.getStringExtra("m_id");
        if (!TextUtils.isEmpty(m_id)){
            messageModel.readSystemMsg(SystemMsgDetailActivity.this,m_id);
        }else {
            if (systemMsg!=null&&!TextUtils.isEmpty(systemMsg.id)){
                messageModel.readSystemMsg(SystemMsgDetailActivity.this, systemMsg.id);
            }
        }


    }

    private void initTopView() {
        FrameLayout  czyTitleLayout            = (FrameLayout) findViewById(R.id.czy_title_layout);
        TextView tvTitle = (TextView) findViewById(R.id.user_top_view_title);
        tvTitle.setText(getString(R.string.details));

        ImageView imgBack = (ImageView) findViewById(R.id.user_top_view_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czyTitleLayout, imgBack, tvTitle);
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        PushmessageViewGetResponse response = ((PushmessageViewGetApi) api).response;
        SYSTEMMSG_INFO info = response.info;

        tv_type.setText(info.title);

        String time = DateUtils.phpToString(info.create_time, DateUtils.DATE_FORMAT_DAY);
        tv_time.setText(time);
        tv_content.setText(info.content);
    }
}
