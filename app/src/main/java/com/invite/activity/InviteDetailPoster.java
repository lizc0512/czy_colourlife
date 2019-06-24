package com.invite.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.google.zxing.WriterException;
import com.invite.adapter.InviteDetailAdapter;
import com.invite.model.NewInviteModel;
import com.invite.protocol.InviteDetailListEntity;
import com.invite.utils.ScreenShotUtils;
import com.nohttp.utils.GsonUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.youmai.hxsdk.utils.ZXingUtil;

import java.io.File;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.csh.colourful.life.view.pickview.TimePickerView;
import cn.net.cyberway.R;

/**
 * 明细，保存海报
 * Created by hxg on 19/5/10.
 */
public class InviteDetailPoster extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String ENTER_TYPE = "enter_type";
    public static final String POSTER_URL = "poster_url";
    public static final String INVITE_URL = "invite_url";

    private TextView tv_title;
    private ImageView iv_back;
    private TextView tv_right;
    private RelativeLayout rl_null;
    private RelativeLayout rl_poster;
    private ImageView iv_code;

    private SwipeMenuRecyclerView rv_detail;
    private InviteDetailAdapter mAdapter;
    private List<InviteDetailListEntity.ContentBean.ListBean> detailList = new ArrayList<>();
    private int page = 1;
    private int pageSize = 10;
    private NewInviteModel newInviteModel;

    public String type = "";
    private String month;//月份

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invite_detail_poster);
        getWindow().setBackgroundDrawable(null);
        initView();
        initData();
    }

    private void initView() {
        tv_title = findViewById(R.id.tv_title);
        iv_back = findViewById(R.id.iv_back);
        tv_right = findViewById(R.id.tv_right);
        rv_detail = findViewById(R.id.rv_detail);
        rl_null = findViewById(R.id.rl_null);
        rl_poster = findViewById(R.id.rl_poster);
        iv_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
    }

    private void initData() {
        newInviteModel = new NewInviteModel(this);

        type = getIntent().getStringExtra(ENTER_TYPE);
        if ("detail".equals(type)) {
            tv_right.setVisibility(View.VISIBLE);
            long currentMills = System.currentTimeMillis();
            month = TimeUtil.getTime(currentMills, "yyyy-MM");
            tv_right.setText(TimeUtil.getYearAndMonthFormat(currentMills));

            tv_title.setText(getString(R.string.invite_detail));
            rv_detail.setVisibility(View.VISIBLE);
            rl_poster.setVisibility(View.GONE);
            rl_null.setVisibility(View.GONE);
            rv_detail.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            rv_detail.useDefaultLoadMore();
            rv_detail.setLoadMoreListener(() -> {
                page++;
                getDetailList(false);
            });
            getDetailList(true);
        } else if ("poster".equals(type)) {
            iv_code = findViewById(R.id.iv_code);

            tv_title.setText(getString(R.string.invite_title));
            rl_poster.setVisibility(View.VISIBLE);
            rv_detail.setVisibility(View.GONE);
            rl_null.setVisibility(View.GONE);

            String inviteUrl = getIntent().getStringExtra(INVITE_URL);

            Bitmap encodeBitmap = null;
            try {
                if (!TextUtils.isEmpty(inviteUrl)) {
                    encodeBitmap = ZXingUtil.encode(inviteUrl, 400, 400);
                }
            } catch (WriterException e) {
                e.printStackTrace();
            }
            iv_code.setImageBitmap(encodeBitmap);
            mHandler.sendEmptyMessageDelayed(0, 500);
        }
    }

    /**
     * 获取详情
     */
    private void getDetailList(boolean isLoading) {
        newInviteModel.inviteDetail(0, page, pageSize, month, this, isLoading);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_right:
                Calendar selectedDate = Calendar.getInstance();
                TimePickerView pvTime = new TimePickerView
                        .Builder(this, (date, v1) -> {//选中事件回调
                    page = 1;
                    long choiceTime = date.getTime();
                    month = TimeUtil.getTime(choiceTime, "yyyy-MM");
                    getDetailList(true);
                    tv_right.setText(TimeUtil.getYearAndMonthFormat(choiceTime));
                })
                        .setType(new boolean[]{true, true, false, false, false, false})
                        .setCancelText(getResources().getString(R.string.ssdk_oks_cancel))//取消按钮文字
                        .setSubmitText(getResources().getString(R.string.user_define))//确认按钮文字
                        .setContentSize(18)//滚轮文字大小
                        .setTitleSize(20)//标题文字大小
                        .setTitleText(getResources().getString(R.string.customer_check_year_month))//标题文字
                        .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                        .isCyclic(true)//是否循环滚动
                        .setTitleColor(Color.parseColor("#333b46"))//标题文字颜色
                        .setSubmitColor(Color.parseColor("#f28146"))//确定按钮文字颜色
                        .setCancelColor(Color.parseColor("#f28146"))//取消按钮文字颜色
                        .setTitleBgColor(Color.parseColor("#f5f5f5"))//标题背景颜色 Night mode
                        .setBgColor(Color.parseColor("#ffffff"))//滚轮背景颜色 Night mode
                        .setDate(selectedDate)// 默认是系统时间*/
                        .setRange(selectedDate.get(Calendar.YEAR) - 10, selectedDate.get(Calendar.YEAR) + 10)//默认是1900-2100年
                        .setLabel(getResources().getString(R.string.hx_wheel_year), getResources().getString(R.string.hx_wheel_month), "", "", "", "")
                        .build();
                pvTime.show();
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        InviteDetailListEntity inviteDetailListEntity = GsonUtils.gsonToBean(result, InviteDetailListEntity.class);
                        InviteDetailListEntity.ContentBean contentBean = inviteDetailListEntity.getContent();
                        pageSize = contentBean.getPage_size();
                        if (page == 1) {
                            detailList.clear();
                        }
                        List<InviteDetailListEntity.ContentBean.ListBean> list = contentBean.getList();
                        detailList.addAll(list);

                        if (0 != detailList.size()) {
                            boolean dataEmpty = list.size() == 0;
                            int totalRecord = contentBean.getTotal();
                            boolean hasMore = totalRecord > detailList.size();
                            rv_detail.loadMoreFinish(dataEmpty, hasMore);
                        }

                        rl_null.setVisibility(0 == detailList.size() ? View.VISIBLE : View.GONE);
                        rv_detail.setVisibility(0 == detailList.size() ? View.GONE : View.VISIBLE);
                        if (null == mAdapter) {
                            mAdapter = new InviteDetailAdapter(this, detailList);
                            rv_detail.setAdapter(mAdapter);
                        } else {
                            mAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    /**
     * 保存海报内容
     */
    private void saveScreenShot() {
        ScreenShotUtils s = new ScreenShotUtils(rl_poster);
        File file = s.apply();
        //通知刷新列表
        sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.fromFile(file)));
        ToastUtil.toastShow(this, "已保存至：" + file.getPath());
    }

    private InterHandler mHandler = new InterHandler(this);

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    private static class InterHandler extends Handler {
        private WeakReference<InviteDetailPoster> mActivity;

        InterHandler(InviteDetailPoster activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            InviteDetailPoster activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        activity.saveScreenShot();
                        break;
                }
            } else {
                super.handleMessage(msg);
            }
        }
    }
}
