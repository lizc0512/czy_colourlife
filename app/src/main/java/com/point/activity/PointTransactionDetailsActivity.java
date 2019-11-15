package com.point.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.nohttp.utils.GlideImageLoader;
import com.point.entity.PointTransactionRecordEntity;

import cn.net.cyberway.R;

/***
 * 积分交易的详情
 */
public class PointTransactionDetailsActivity extends BaseActivity implements View.OnClickListener {
    public static final String POINTTRANSACTIONDETAIL = "pointtransactiondetail";
    private ImageView mBack;
    private TextView mTitle;
    private ImageView iv_transaction_logo;
    private TextView tv_transaction_name;
    private TextView tv_transaction_amount;
    private TextView tv_transaction_title;
    private TextView tv_transaction_type;
    private TextView tv_transaction_date;
    private TextView tv_transaction_sn;
    private TextView tv_transaction_remark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point_transaction_details);
        mBack = findViewById(R.id.user_top_view_back);
        mTitle = findViewById(R.id.user_top_view_title);
        iv_transaction_logo = findViewById(R.id.iv_transaction_logo);
        tv_transaction_name = findViewById(R.id.tv_transaction_name);
        tv_transaction_amount = findViewById(R.id.tv_transaction_name);
        tv_transaction_title = findViewById(R.id.tv_transaction_title);
        tv_transaction_type = findViewById(R.id.tv_transaction_type);
        tv_transaction_date = findViewById(R.id.tv_transaction_date);
        tv_transaction_sn = findViewById(R.id.tv_transaction_sn);
        tv_transaction_remark = findViewById(R.id.tv_transaction_remark);
        mBack.setOnClickListener(this::onClick);
        mTitle.setText("交易详情");
        Intent intent = getIntent();
        PointTransactionRecordEntity.ContentBean.ListBean listBean = (PointTransactionRecordEntity.ContentBean.ListBean) intent.getSerializableExtra(POINTTRANSACTIONDETAIL);
        GlideImageLoader.loadImageDisplay(PointTransactionDetailsActivity.this, listBean.getLogo(), iv_transaction_logo);
        tv_transaction_name.setText(listBean.getTrans_name());
        String amount = listBean.getDest_money();
        if ("1".equals(listBean.getType())) {
            tv_transaction_amount.setText("+" + amount);
        } else {
            tv_transaction_amount.setText("-" + amount);
        }
        String trans_type = listBean.getTrans_type();
        tv_transaction_title.setText(trans_type + "金额");
        tv_transaction_type.setText(trans_type);
        tv_transaction_date.setText(listBean.getCreate_time());
        tv_transaction_sn.setText(listBean.getOrder_no());
        tv_transaction_remark.setText(listBean.getDetail());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }
}
