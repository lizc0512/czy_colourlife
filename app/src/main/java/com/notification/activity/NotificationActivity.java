package com.notification.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.external.eventbus.EventBus;
import com.nohttp.utils.GsonUtils;
import com.notification.adapter.NotificationAdapter;
import com.notification.adapter.NotificationTwoAdapter;
import com.notification.model.NotificationModel;
import com.notification.protocol.NotificationListEntity;
import com.user.UserMessageConstant;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.WrapLinearLayoutManager;

import static cn.net.cyberway.utils.BuryingPointUtils.ENTER_TIME;
import static cn.net.cyberway.utils.BuryingPointUtils.LEAVE_TIME;
import static cn.net.cyberway.utils.BuryingPointUtils.UPLOAD_DETAILS;


/**
 * 2018/04/16
 * 消息通知列表页面
 * lizc
 */
public class NotificationActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private TextView user_top_view_title;
    private LinearLayout empty_msg_layout;
    private ImageView user_top_view_back;
    private RecyclerView recyclerView;
    private RecyclerView rv_notification_notice_two;
    private NotificationModel notificationModel;
    private NotificationAdapter notificationAdapter;
    private NotificationTwoAdapter notificationTwoAdapter;
    private List<NotificationListEntity.ContentBean.ReadBean> readList = new ArrayList<>();
    private List<NotificationListEntity.ContentBean.UnReadBean> unreadList = new ArrayList<>();
    private RelativeLayout rl_notification_linemid;
    private RelativeLayout rl_notification_linebottom;
    private LinearLayout content_layout;
    private String appSectionCode = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        initView();
        initData();
        appSectionCode = getIntent().getStringExtra(UPLOAD_DETAILS);
    }

    private void initData() {
        notificationModel.getMsgList(0, true, this);
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.rv_notification_notice);
        rv_notification_notice_two = (RecyclerView) findViewById(R.id.rv_notification_notice_two);
        rl_notification_linemid = (RelativeLayout) findViewById(R.id.rl_notification_linemid);
        rl_notification_linebottom = (RelativeLayout) findViewById(R.id.rl_notification_linebottom);
        recyclerView.setLayoutManager(new WrapLinearLayoutManager(NotificationActivity.this));
        recyclerView.setNestedScrollingEnabled(false);
        rv_notification_notice_two.setLayoutManager(new WrapLinearLayoutManager(NotificationActivity.this));
        rv_notification_notice_two.setNestedScrollingEnabled(false);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        empty_msg_layout = (LinearLayout) findViewById(R.id.empty_msg_layout);
        content_layout = (LinearLayout) findViewById(R.id.content_layout);
        user_top_view_back.setOnClickListener(this);
        user_top_view_title.setText(getResources().getString(R.string.title_message_notification));
        notificationModel = new NotificationModel(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                NotificationActivity.this.finish();
                break;

        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0://未读消息
                if (!TextUtils.isEmpty(result)) {
                    try {
                        NotificationListEntity notificationListEntity = GsonUtils.gsonToBean(result, NotificationListEntity.class);
                        readList.addAll(notificationListEntity.getContent().getRead());
                        unreadList.addAll(notificationListEntity.getContent().getUnRead());
                        if (null == notificationAdapter) {
                            notificationAdapter = new NotificationAdapter(this, unreadList);
                            recyclerView.setAdapter(notificationAdapter);
                        } else {
                            notificationAdapter.notifyDataSetChanged();
                        }
                        int unReadSize = unreadList.size();
                        int readSize = readList.size();
                        if (null != readList && readSize > 0) {
                            if (null == notificationTwoAdapter) {
                                notificationTwoAdapter = new NotificationTwoAdapter(this, readList);
                                rv_notification_notice_two.setAdapter(notificationTwoAdapter);
                            } else {
                                notificationTwoAdapter.notifyDataSetChanged();
                            }
                        }
                        if (unReadSize == 0) {
                            rl_notification_linemid.setVisibility(View.GONE);
                        } else {
                            if (readSize > 0) {
                                rl_notification_linemid.setVisibility(View.VISIBLE);
                            } else {
                                rl_notification_linemid.setVisibility(View.GONE);
                            }
                        }
                        if (unReadSize == 0 && readSize == 0) {
                            rl_notification_linebottom.setVisibility(View.GONE);
                            content_layout.setBackgroundColor(Color.parseColor("#ffffff"));
                            empty_msg_layout.setVisibility(View.VISIBLE);
                        } else {
                            rl_notification_linebottom.setVisibility(View.VISIBLE);
                            empty_msg_layout.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        content_layout.setBackgroundColor(Color.parseColor("#ffffff"));
                        empty_msg_layout.setVisibility(View.VISIBLE);
                    }
                } else {
                    content_layout.setBackgroundColor(Color.parseColor("#ffffff"));
                    empty_msg_layout.setVisibility(View.VISIBLE);
                }
                break;
        }
    }


    private long showMillions = 0;//可见时的秒数

    @Override
    protected void onResume() {
        super.onResume();
        showMillions = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (!TextUtils.isEmpty(appSectionCode)) {
            Message message = Message.obtain();
            message.what = UserMessageConstant.UPLOAD_PAGE_TIME;
            Bundle bundle = new Bundle();
            bundle.putLong(ENTER_TIME, showMillions);
            bundle.putLong(LEAVE_TIME, System.currentTimeMillis());
            bundle.putString(UPLOAD_DETAILS, appSectionCode);
            message.setData(bundle);
            EventBus.getDefault().post(message);
        }
    }
}
