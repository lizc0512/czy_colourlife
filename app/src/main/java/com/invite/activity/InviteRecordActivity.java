package com.invite.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.Constants;
import com.BeeFramework.model.NewHttpResponse;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.invite.adapter.InviteRecordAdapter;
import com.nohttp.utils.GsonUtils;
import com.user.entity.InviteRecordEntity;
import com.user.model.NewUserModel;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * 邀请记录 & 邀请战绩
 * Created by chenql on 16/1/8.
 */
public class InviteRecordActivity extends BaseActivity implements NewHttpResponse {

    private XListView xlv_invite_record;      // 邀请记录/邀请战绩列表
    private TextView tvTitle;                   // 标题
    private Boolean inviteSuccess = false;      // true:邀请战绩，false:邀请记录
    private List<InviteRecordEntity.ContentBean> inviterecords = new ArrayList<>();
    private InviteRecordAdapter inviteRecordAdapter;
    private NewUserModel newUserModel;
    private int page = 1;
    private LinearLayout ll_top_recordtips;
    private int inviteStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_record);
        initTopView();
        initView();
        initData();
    }

    private void initData() {
        inviteSuccess = getIntent().getBooleanExtra(Constants.INVITE_SUCCESS, false);
        newUserModel = new NewUserModel(this);
        if (inviteSuccess) {
            tvTitle.setText(getString(R.string.invite_success_record));
            ((TextView) findViewById(R.id.tv_status_title)).setText(getResources().getString(R.string.register_time));
            inviteStatus = 1;
        } else {
            tvTitle.setText(getString(R.string.invite_record));
            ((TextView) findViewById(R.id.tv_status_title)).setText(getResources().getString(R.string.register_status));
            inviteStatus = 0;
        }
        newUserModel.inviteRecord(0, inviteStatus, page, this);
    }

    /**
     * @param context       context
     * @param inviteSuccess true:邀请战绩，false:邀请记录
     */
    public static void actionStart(Context context, Boolean inviteSuccess) {
        Intent intent = new Intent(context, InviteRecordActivity.class);
        intent.putExtra(Constants.INVITE_SUCCESS, inviteSuccess);
        context.startActivity(intent);
    }

    private void initView() {
        ll_top_recordtips = (LinearLayout) findViewById(R.id.ll_top_recordtips);
        xlv_invite_record = (XListView) findViewById(R.id.xlv_invite_record);

        xlv_invite_record.setXListViewListener(new IXListViewListener() {
            @Override
            public void onRefresh(int id) {
                page = 1;
                newUserModel.inviteRecord(0, inviteStatus, page, InviteRecordActivity.this);
            }

            @Override
            public void onLoadMore(int id) {
                page++;
                newUserModel.inviteRecord(0, inviteStatus, page, InviteRecordActivity.this);

            }
        }, 0);
        xlv_invite_record.setPullLoadEnable(true);
        xlv_invite_record.setPullRefreshEnable(false);
    }

    /**
     * 隐藏列表，显示没有数据的视图
     */
    private void showNoData() {
        ll_top_recordtips.setVisibility(View.GONE);
        ImageView img_empty = (ImageView) findViewById(R.id.img_empty);
        TextView tv_tips = (TextView) findViewById(R.id.tv_tips);
        TextView tv_description = (TextView) findViewById(R.id.tv_description);
        img_empty.setVisibility(View.VISIBLE);
        tv_tips.setVisibility(View.VISIBLE);
        tv_description.setVisibility(View.VISIBLE);
        img_empty.setImageResource(R.drawable.invite_emtpy);
        tv_tips.setText(getResources().getString(R.string.invite_no_friend));
        tv_description.setText(getResources().getString(R.string.invite_friend_award));
    }

    private void initTopView() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tvTitle = (TextView) findViewById(R.id.user_top_view_title);
        ImageView imgBack = (ImageView) findViewById(R.id.user_top_view_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, imgBack, tvTitle);
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    InviteRecordEntity inviteRecordEntity = GsonUtils.gsonToBean(result, InviteRecordEntity.class);
                    List<InviteRecordEntity.ContentBean> contentBeanList = inviteRecordEntity.getContent();
                    if (0 == contentBeanList.size()) {
                        xlv_invite_record.setPullLoadEnable(false);
                    } else {
                        xlv_invite_record.setPullLoadEnable(true);
                    }
                    inviterecords.addAll(contentBeanList);
                    if (inviterecords.size() == 0) {
                        showNoData();
                    } else {
                        if (inviteRecordAdapter == null) {
                            inviteRecordAdapter = new InviteRecordAdapter(InviteRecordActivity.this, contentBeanList, inviteSuccess);
                            xlv_invite_record.setAdapter(inviteRecordAdapter);
                        } else {
                            inviteRecordAdapter.notifyDataSetChanged();
                        }
                    }
                    xlv_invite_record.loadMoreHide();
                } catch (Exception e) {
                    if (inviterecords.size() == 0) {
                        showNoData();
                    }
                }
                break;
        }
    }
}
