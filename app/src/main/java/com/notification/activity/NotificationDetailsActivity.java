package com.notification.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GsonUtils;
import com.notification.adapter.NotificationDetailsAdapter;
import com.notification.model.NotificationModel;
import com.notification.protocol.NotificationDetailsEntity;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.WrapLinearLayoutManager;

/**
 * 2018/04/16
 * 单个消息通知详情页面
 * lizc
 */
public class NotificationDetailsActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private TextView user_top_view_title;
    private ImageView user_top_view_back;
    private String msg_id;
    private RecyclerView rv_notice_details;
    private NotificationModel notificationModel;
    private NotificationDetailsEntity notificationDetailsEntity;
    private NotificationDetailsAdapter notificationDetailsAdapter;
    private List<NotificationDetailsEntity.ContentBean.ItemsBean> listDetails = new ArrayList<>();
    private LinearLayout content_layout;
    private TextView tv_details_name;
    private TextView tv_details_money;
    private TextView tv_details_content;
    private ImageView iv_notice_details;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_details);
        initView();
        initData();
    }

    private void initData() {
        msg_id = this.getIntent().getStringExtra("msg_id");
        notificationModel.getMsgDetail(0, msg_id, true, this);
    }

    private void initView() {
        content_layout = (LinearLayout) findViewById(R.id.content_layout);
        tv_details_name = (TextView) findViewById(R.id.tv_details_name);
        tv_details_money = (TextView) findViewById(R.id.tv_details_money);
        tv_details_content = (TextView) findViewById(R.id.tv_details_content);
        iv_notice_details = (ImageView) findViewById(R.id.iv_notice_details);
        rv_notice_details = (RecyclerView) findViewById(R.id.rv_notice_details);
        rv_notice_details.setLayoutManager(new WrapLinearLayoutManager(NotificationDetailsActivity.this, LinearLayoutManager.VERTICAL, false));
        rv_notice_details.setNestedScrollingEnabled(false);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        user_top_view_back.setOnClickListener(this);
        notificationModel = new NotificationModel(this);
        user_top_view_title.setText(getResources().getString(R.string.title_message_details));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                NotificationDetailsActivity.this.finish();
                break;

        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        notificationDetailsEntity = GsonUtils.gsonToBean(result, NotificationDetailsEntity.class);
                        NotificationDetailsEntity.ContentBean notificationContentBean = notificationDetailsEntity.getContent();
                        content_layout.setVisibility(View.VISIBLE);
                        String detailName = notificationContentBean.getDetail_title();
                        if (!TextUtils.isEmpty(detailName)) {
                            tv_details_name.setText(detailName);
                        }
                        //1为金额订单使用order_amount和order_amount_type，2为文本订单，使用msg_title
                        String detailType = notificationContentBean.getDetail_type();
                        String amountType = notificationContentBean.getOrder_amount_type();
                        if ("2".equals(detailType)) {
                            tv_details_money.setText(notificationContentBean.getMsg_sub_title());
                            tv_details_money.setTextSize(14f);
                            iv_notice_details.setVisibility(View.GONE);
                        } else {
                            if ("0".equals(amountType)) {//0饭票
                                iv_notice_details.setImageResource(R.drawable.message_icon_fanpiao);
                            } else {
                                iv_notice_details.setImageResource(R.drawable.message_icon_rmb);
                            }
                            iv_notice_details.setVisibility(View.VISIBLE);
                            tv_details_money.setText(notificationContentBean.getOrder_amount());
                        }
                        tv_details_content.setText(notificationContentBean.getOrder_status());
                        listDetails.addAll(notificationContentBean.getItems());
                        if (null == notificationDetailsAdapter) {
                            notificationDetailsAdapter = new NotificationDetailsAdapter(this, listDetails);
                            rv_notice_details.setAdapter(notificationDetailsAdapter);
                        } else {
                            notificationDetailsAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }
}
