package com.customerInfo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.adapter.BeanDetailAdapter;
import com.customerInfo.protocol.BeanDetailListEntity;
import com.nohttp.utils.GsonUtils;
import com.user.model.NewUserModel;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cn.csh.colourful.life.view.pickview.TimePickerView;
import cn.net.cyberway.R;

/**
 * 彩豆的签到规则,兑换饭票，明细页面
 * Created by hxg on 2019/4/16.
 */
public class CustomerBeanMoreActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    public static final String TYPE = "type";
    public static final String TOTAL_NUM = "total_num";
    public static final String USEFUL_NUM = "useful_num";

    private ImageView iv_back;
    private TextView tv_title;
    private TextView tv_right;
    private LinearLayout ll_rule;
    private SwipeMenuRecyclerView rv_detail;
    private LinearLayout ll_change;
    private BeanDetailAdapter mAdapter;

    private int type;
    private int totalNum = 0;// 总积分
    private int usefulNum = 0;// 可用积分
    private NewUserModel newUserModel;
    private List<BeanDetailListEntity.ContentBean.DataBean> detailList = new ArrayList<>();
    private int page = 1;
    private String month;//月份
    private int beanNum = 0;
    private boolean dialog = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_bean_more);
        getWindow().setBackgroundDrawable(null);//减少GPU绘制 布局要设为match_parent
        newUserModel = new NewUserModel(this);
        initView();
        initData();
    }

    private void initView() {
        iv_back = findViewById(R.id.iv_back);
        tv_title = findViewById(R.id.tv_title);
        tv_right = findViewById(R.id.tv_right);
        ll_change = findViewById(R.id.ll_change);
        rv_detail = findViewById(R.id.rv_detail);
        ll_rule = findViewById(R.id.ll_rule);
        iv_back.setOnClickListener(this);
        tv_right.setOnClickListener(this);
        tintManager.setStatusBarTintResource(R.color.color_f1a348);//设置状态栏
    }

    private void initData() {
        //type: 1:明细页面,2:兑换饭票,3:签到规则
        Intent intent = getIntent();
        type = intent.getIntExtra(TYPE, 0);
        if (1 == type) {
            tv_right.setVisibility(View.VISIBLE);
            long currentMills = System.currentTimeMillis();
            month = TimeUtil.getTime(currentMills, "yyyy-MM");
            tv_right.setText(TimeUtil.getYearAndMonthFormat(currentMills));

            tv_title.setText(getResources().getString(R.string.customer_detail));
            rv_detail.setVisibility(View.VISIBLE);
            rv_detail.setLayoutManager(new LinearLayoutManager(this));
            rv_detail.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
            mAdapter = new BeanDetailAdapter(this, detailList);
            rv_detail.setAdapter(mAdapter);
            rv_detail.useDefaultLoadMore();
            rv_detail.setLoadMoreListener(() -> {
                page++;
                getDetailList(false);
            });
            getDetailList(true);
        } else if (2 == type) {
            tv_title.setText(getResources().getString(R.string.customer_change_tickets));
            ll_change.setVisibility(View.VISIBLE);
            totalNum = intent.getIntExtra(TOTAL_NUM, 0);
            usefulNum = intent.getIntExtra(USEFUL_NUM, 0);
            initViewChange();
            getBean();
        } else {
            tv_title.setText(getResources().getString(R.string.customer_sign_rule));
            ll_rule.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 获取彩豆详情
     */
    private void getDetailList(boolean isLoading) {
        newUserModel.getBeanDetailList(0, page, month, isLoading, this);
    }

    private TextView tv_can_change;
    private TextView tv_most;
    private ImageView iv_question;
    private EditText et_change_num;
    private TextView tv_change;

    @SuppressLint("SetTextI18n")
    private void initViewChange() {
        tv_can_change = findViewById(R.id.tv_can_change);
        iv_question = findViewById(R.id.iv_question);
        et_change_num = findViewById(R.id.et_change_num);
        tv_most = findViewById(R.id.tv_most);
        tv_change = findViewById(R.id.tv_change);
        iv_question.setOnClickListener(this);
        tv_can_change.setText(getResources().getString(R.string.customer_can_use_bean) + totalNum);
        tv_most.setOnClickListener(this);

        if (0 == usefulNum) {
            tv_change.setBackgroundResource(R.drawable.text_change);
        } else {
            tv_change.setBackgroundResource(R.drawable.text_change_deep);
            tv_change.setOnClickListener(this);
        }
    }

    @SuppressLint("SetTextI18n")
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
                    getDetailList(false);
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
            case R.id.iv_question://疑问按钮
                dialog = true;
                getBean();
                break;
            case R.id.tv_change://兑换
                String num = et_change_num.getText().toString().trim();
                try {
                    beanNum = Integer.parseInt(num);
                    if (beanNum > 0) {
                        if (beanNum > usefulNum) {
                            ToastUtil.toastShow(this, getResources().getString(R.string.customer_cant_outnumber));
                        } else {
                            change(beanNum);
                        }
                    } else {
                        ToastUtil.toastShow(this, getResources().getString(R.string.customer_input_error));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.toastShow(this, getResources().getString(R.string.customer_input_error));
                }
                break;
            case R.id.tv_most://最多
                if (totalNum > 0) {
                    et_change_num.setText(totalNum + "");
                } else {
                    ToastUtil.toastShow(this, getResources().getString(R.string.customer_no_bean));
                }
                break;
        }
    }

    /**
     * 兑换饭票
     *
     * @param beanNum beanNum必须为正整数
     */
    private void change(int beanNum) {
        newUserModel.changeBean(1, beanNum + "", this);
    }

    /**
     * 兑换饭票
     */
    private void getBean() {
        newUserModel.getBeanMsg(2, this);//获取彩豆积分
    }

    /**
     * 疑问
     */
    @SuppressLint("SetTextI18n")
    private void showDialog() {
        final BeanExplainDialog payActivityDialog = new BeanExplainDialog(this, R.style.dialog);
        payActivityDialog.show();
        payActivityDialog.iv_close.setOnClickListener(v -> payActivityDialog.dismiss());

        payActivityDialog.tv_bean_all.setText(totalNum + "");
        payActivityDialog.tv_bean_can.setText(usefulNum + "");
        if (totalNum - usefulNum > 0) {
            payActivityDialog.tv_bean_reserve.setText(totalNum - usefulNum);
        } else {
            payActivityDialog.tv_bean_reserve.setText(0 + "");
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        BeanDetailListEntity beanDetailListEntity = GsonUtils.gsonToBean(result, BeanDetailListEntity.class);
                        BeanDetailListEntity.ContentBean contentBean = beanDetailListEntity.getContent();
                        if (page == 1) {
                            detailList.clear();
                        }
                        List<BeanDetailListEntity.ContentBean.DataBean> list = contentBean.getData();
                        detailList.addAll(list);
                        boolean dataEmpty = list.size() == 0;
                        int totalRecord = contentBean.getTotal();
                        boolean hasMore = totalRecord > detailList.size();
                        rv_detail.loadMoreFinish(dataEmpty, hasMore);
                        mAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        String message = jsonObject.getString("message");
                        if ("0".equals(code)) {
                            ToastUtil.toastShow(this, getResources().getString(R.string.customer_change_success));
                            getBean();
                        } else {
                            ToastUtil.toastShow(this, message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String content = jsonObject.getString("content");
                        JSONObject data = new JSONObject(content);
                        totalNum = data.getInt("total_num");
                        usefulNum = data.getInt("useful_num");
                        tv_can_change.setText(getResources().getString(R.string.customer_can_use_bean) + totalNum);
                        if (dialog) {
                            dialog = false;
                            showDialog();
                        }
                        if (0 == usefulNum) {
                            tv_change.setBackgroundResource(R.drawable.text_change);
                            tv_change.setOnClickListener(null);
                        } else {
                            tv_change.setBackgroundResource(R.drawable.text_change_deep);
                            tv_change.setOnClickListener(this);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}