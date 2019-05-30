package com.scanCode.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseActivity;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * @author liqingjun
 */
public class ScanResultActivity extends BaseActivity implements OnClickListener {
    private TextView tv_title;
    private ImageView iv_back;
    private TextView tv_result;

    private String result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scan_result_activity);
        init();
        prepareView();
        prepareData();
    }

    private void init() {
        Intent intent = getIntent();

        result = intent.getStringExtra("result");
    }

    private void prepareView() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        iv_back = (ImageView) findViewById(R.id.user_top_view_back);
        iv_back.setOnClickListener(this);

        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_scanner_result));

        tv_result = (TextView) findViewById(R.id.tv_result);
        tv_result.setOnClickListener(this);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, iv_back, tv_title);
    }

    private void prepareData() {

        showResult(result);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.tv_result:
                LinkParseUtil.parse(ScanResultActivity.this, result, "");
                break;
            default:
                break;
        }
    }

    // 更新信息
    public void showResult(String result) {
        tv_result.setText(result);
    }
}
