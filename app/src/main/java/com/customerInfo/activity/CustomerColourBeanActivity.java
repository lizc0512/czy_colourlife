package com.customerInfo.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.NestedScrollView;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.NoScrollGridView;
import com.customerInfo.adapter.BeanDutyAdapter;
import com.customerInfo.adapter.BeanSignAdapter;
import com.customerInfo.protocol.DutyListEntity;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.model.NewUserModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.home.entity.HomeColourBeanEntity;
import cn.net.cyberway.home.entity.HomeColourBeanFormatEntity;
import cn.net.cyberway.home.model.NewHomeModel;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 彩豆
 * Created by hxg on 2019/4/16.
 */
public class CustomerColourBeanActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private NestedScrollView nsv;
    private ImageView user_top_view_back;
    private ImageView user_top_view_back_hide;
    private ImageView iv_record;
    private ImageView iv_record_hide;
    private ImageView iv_sign;
    private TextView tv_bean_num;
    private TextView tv_change;
    private TextView tv_sign;
    private TextView tv_sign_rule;
    private NoScrollGridView gv_sign;
    private TextView tv_forever;
    private TextView tv_month;
    private TextView tv_title;
    private ListView lv_duty;
    private TextView tv_empty;

    private SharedPreferences mShared;
    public SharedPreferences.Editor mEditor;
    private NewUserModel newUserModel;
    private NewHomeModel newHomeModel;
    private BeanSignAdapter signAdapter;
    private BeanDutyAdapter dutyAdapter;
    private RelativeLayout alpha_title_layout_hide;
    private FrameLayout fl_bg;

    private List<HomeColourBeanFormatEntity.signBean> signList = new ArrayList<>();
    private int current;
    private List<DutyListEntity.ContentBean.OnceBean> dutyList = new ArrayList<>();
    private List<DutyListEntity.ContentBean.OnceBean> dutyForeverList = new ArrayList<>();
    private List<DutyListEntity.ContentBean.MonthBean> dutyMonthList = new ArrayList<>();

    private boolean refresh = false;
    private boolean clickForever = true;//是否点击永久
    private int totalNum = 0;// 总积分
    private int usefulNum = 0;// 可用积分
    private int customer_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_colour_bean);
        getWindow().setBackgroundDrawable(null);//减少GPU绘制 布局要设为match_parent
        initView();
        initCache();
        initData();
    }

    private void initView() {
        tintManager.setStatusBarTintResource(R.color.color_f1a348);//设置状态栏
        nsv = findViewById(R.id.nsv);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        iv_record = findViewById(R.id.iv_record);

        fl_bg = findViewById(R.id.fl_bg);
        tv_title = findViewById(R.id.tv_title);
        alpha_title_layout_hide = findViewById(R.id.alpha_title_layout_hide);
        user_top_view_back_hide = findViewById(R.id.user_top_view_back_hide);
        iv_record_hide = findViewById(R.id.iv_record_hide);
        iv_sign = findViewById(R.id.iv_sign);

        tv_bean_num = findViewById(R.id.tv_bean_num);
        tv_change = findViewById(R.id.tv_change);
        tv_sign = findViewById(R.id.tv_sign);
        tv_sign_rule = findViewById(R.id.tv_sign_rule);
        gv_sign = findViewById(R.id.gv_sign);
        tv_forever = findViewById(R.id.tv_forever);
        tv_month = findViewById(R.id.tv_month);
        lv_duty = findViewById(R.id.lv_duty);
        tv_empty = findViewById(R.id.tv_empty);

        user_top_view_back.setOnClickListener(this);
        user_top_view_back_hide.setOnClickListener(this);
        iv_record.setOnClickListener(this);
        iv_record_hide.setOnClickListener(this);
        iv_sign.setOnClickListener(this);
        tv_change.setOnClickListener(this);
        tv_sign_rule.setOnClickListener(this);
        tv_forever.setOnClickListener(this);
        tv_month.setOnClickListener(this);

        signAdapter = new BeanSignAdapter(this, signList);
        gv_sign.setAdapter(signAdapter);
        dutyAdapter = new BeanDutyAdapter(this, dutyList);
        lv_duty.setAdapter(dutyAdapter);

        initTopView();
    }

    private void initCache() {
        mShared = this.getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        customer_id = mShared.getInt(UserAppConst.Colour_User_id, 0);
        newUserModel = new NewUserModel(this);
        newHomeModel = new NewHomeModel(this);

        String beanCache = shared.getString(UserAppConst.BEAN_NUMBER, "");
        String signCache = shared.getString(UserAppConst.BEAN_SIGN, "");
        String dutyCache = shared.getString(UserAppConst.BEAN_DUTY, "");
        if (!TextUtils.isEmpty(beanCache)) {
            setResponse(1, beanCache);
        }
        if (!TextUtils.isEmpty(signCache)) {
            setResponse(2, signCache);
        }
        if (!TextUtils.isEmpty(dutyCache)) {
            setResponse(3, dutyCache);
        }
    }

    @SuppressLint("CommitPrefEdits")
    private void initData() {
        mHandler.sendEmptyMessageDelayed(0, 0);
        mHandler.sendEmptyMessageDelayed(1, 100);
        mHandler.sendEmptyMessageDelayed(2, 150);
        mHandler.sendEmptyMessageDelayed(3, 600);//防止后台处理彩豆任务慢
    }

    @SuppressLint("SetTextI18n")
    private void setResponse(int what, String result) {
        switch (what) {
            case 0://是否已经签到
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String content = jsonObject.getString("content");
                        JSONObject data = new JSONObject(content);
                        int show = data.getInt("show");
                        boolean hasPoint = mShared.getBoolean(UserAppConst.COLOUR_BEAN_SIGN_POINT + customer_id, false);
                        if (1 == show) {
                            iv_sign.setVisibility(View.VISIBLE);
                            if (!hasPoint) {
                                editor.putBoolean(UserAppConst.COLOUR_BEAN_SIGN_POINT + customer_id, true);//我的任务 有小红点
                                editor.commit();
                            }
                        } else {
                            iv_sign.setVisibility(View.GONE);
                            if (hasPoint) {
                                editor.putBoolean(UserAppConst.COLOUR_BEAN_SIGN_POINT + customer_id, false);
                                editor.commit();
                            }
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 1://获取彩豆积分
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String content = jsonObject.getString("content");
                        JSONObject data = new JSONObject(content);
                        totalNum = data.getInt("total_num");
                        usefulNum = data.getInt("useful_num");
                        tv_bean_num.setText(totalNum + "");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2://获取用户签到信息
                if (!TextUtils.isEmpty(result)) {
                    try {
                        HomeColourBeanEntity homeResourceEntity = GsonUtils.gsonToBean(result, HomeColourBeanEntity.class);
                        HomeColourBeanEntity.ContentBean contentBean = homeResourceEntity.getContent();
                        HomeColourBeanFormatEntity.signBean bean;
                        signList.clear();
                        for (int i = 0; i < contentBean.getIntegral().size(); i++) {
                            bean = new HomeColourBeanFormatEntity.signBean();
                            bean.setIntegral(contentBean.getIntegral().get(i));
                            bean.setCurrent(contentBean.getCurrent());
                            signList.add(bean);
                        }
                        current = contentBean.getCurrent();
                        tv_sign.setText(current + "");
                        signAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 3://获取用户彩豆任务列表信息
                if (!TextUtils.isEmpty(result)) {
                    try {
                        dutyList.clear();
                        DutyListEntity entity = GsonUtils.gsonToBean(result, DutyListEntity.class);
                        DutyListEntity.ContentBean contentBean = entity.getContent();
                        dutyForeverList = contentBean.getOnce();
                        dutyMonthList = contentBean.getMonth();
                        if (clickForever) {
                            dutyList.addAll(dutyForeverList);
                        } else {
                            clickMonth(dutyMonthList);
                        }
                        tv_empty.setVisibility(0 == dutyList.size() ? View.VISIBLE : View.GONE);
                        dutyAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 4://签到
                if (!TextUtils.isEmpty(result)) {
                    ToastUtil.toastShow(this, getResources().getString(R.string.customer_sign_success));
                    initData();
                }
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void OnHttpResponse(int what, String result) {
        setResponse(what, result);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        int type;
        boolean record = false;
        boolean change = false;
        switch (v.getId()) {
            case R.id.user_top_view_back:
            case R.id.user_top_view_back_hide:
                finish();
                break;
            case R.id.iv_record://点击记录
            case R.id.iv_record_hide://点击记录
                record = true;
            case R.id.tv_change:
                change = true;
            case R.id.tv_sign_rule://1:明细页面,2:兑换饭票,3:签到规则
                type = record ? 1 : change ? 2 : 3;
                intent = new Intent(this, CustomerBeanMoreActivity.class);
                intent.putExtra(CustomerBeanMoreActivity.TYPE, type);
                intent.putExtra(CustomerBeanMoreActivity.TOTAL_NUM, totalNum);
                intent.putExtra(CustomerBeanMoreActivity.USEFUL_NUM, usefulNum);
                startActivity(intent);
                break;
            case R.id.iv_sign://签到
                mHandler.sendEmptyMessageDelayed(4, 50);
                break;
            case R.id.tv_forever://永久
                clickForever = true;
                tv_forever.setTextColor(getResources().getColor(R.color.color_f28146));
                tv_month.setTextColor(getResources().getColor(R.color.grayred_text_color));
                dutyList.clear();
                dutyList.addAll(dutyForeverList);
                dutyAdapter.notifyDataSetChanged();
                tv_empty.setVisibility(0 == dutyList.size() ? View.VISIBLE : View.GONE);
                break;
            case R.id.tv_month://本月
                clickForever = false;
                tv_forever.setTextColor(getResources().getColor(R.color.grayred_text_color));
                tv_month.setTextColor(getResources().getColor(R.color.color_f28146));
                clickMonth(dutyMonthList);
                dutyAdapter.notifyDataSetChanged();
                tv_empty.setVisibility(0 == dutyList.size() ? View.VISIBLE : View.GONE);
                break;
        }
    }

    /**
     * 点击本月
     */
    private void clickMonth(List<DutyListEntity.ContentBean.MonthBean> dutyMonthList) {
        DutyListEntity.ContentBean.OnceBean bean;
        List<DutyListEntity.ContentBean.OnceBean> list = new ArrayList<>();
        for (DutyListEntity.ContentBean.MonthBean b : dutyMonthList) {
            bean = new DutyListEntity.ContentBean.OnceBean();
            bean.setImg(b.getImg());
            bean.setIs_finish(b.getIs_finish());
            bean.setName(b.getName());
            bean.setQuantity(b.getQuantity());
            bean.setUrl(b.getUrl());
            list.add(bean);
        }
        dutyList.clear();
        dutyList.addAll(list);
    }

    /**
     * 点击去完成
     *
     * @param url colourlife://proto?Information
     */
    public void todo(String url) {
        String title = "";
        if (url.contains("Setting")) {
            title = "FromColourBean";
        }
        LinkParseUtil.parse(this, url, title);
    }

    /**
     * 利用生命周期 处理数据刷新 和 确保显示隐藏小红点
     */
    @Override
    protected void onPause() {
        super.onPause();
        refresh = true;
        boolean hasPoint = mShared.getBoolean(UserAppConst.COLOUR_BEAN_SIGN_POINT + customer_id, false);
        if (View.GONE == iv_sign.getVisibility() && hasPoint) {//再次确保已签到情况隐藏小红点
            editor.putBoolean(UserAppConst.COLOUR_BEAN_SIGN_POINT + customer_id, false);
            editor.commit();
        } else if (View.VISIBLE == iv_sign.getVisibility() && !hasPoint) {
            editor.putBoolean(UserAppConst.COLOUR_BEAN_SIGN_POINT + customer_id, true);
            editor.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refresh) {
            refresh = false;
            initData();
        }
    }

    private void initTopView() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            nsv.setOnScrollChangeListener((View.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                int height = fl_bg.getMeasuredHeight();
                if (scrollY <= 0) {
                    alpha_title_layout_hide.setVisibility(View.GONE);
                    alpha_title_layout_hide.setAlpha(0);
                    tv_title.setAlpha(0);
                } else if (scrollY <= height) {
                    float scale = (float) scrollY / height;
                    alpha_title_layout_hide.setVisibility(View.VISIBLE);
                    alpha_title_layout_hide.setAlpha(scale);
                    user_top_view_back_hide.setAlpha(scale);
                    tv_title.setAlpha(scale);
                    iv_record_hide.setAlpha(scale);
                } else {
                    user_top_view_back_hide.setAlpha(1.0f);
                    tv_title.setAlpha(1.0f);
                    iv_record_hide.setAlpha(1.0f);
                    alpha_title_layout_hide.setAlpha(1.0f);
                    alpha_title_layout_hide.setVisibility(View.VISIBLE);
                }
            });
        }
    }

    private InterHandler mHandler = new InterHandler(this);

    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

    /**
     * 弱引用 防止handler内存泄露
     */
    private static class InterHandler extends Handler {
        private WeakReference<CustomerColourBeanActivity> mActivity;

        InterHandler(CustomerColourBeanActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            CustomerColourBeanActivity activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 0:
                        activity.newHomeModel.setHomeModelSignInBean(0, activity);//获取是否签到
                        break;
                    case 1:
                        activity.newUserModel.getBeanMsg(1, activity);//获取彩豆积分
                        break;
                    case 2:
                        activity.newHomeModel.getHomeModelColourBean(2, activity);//获取用户签到信息
                        break;
                    case 3:
                        activity.newUserModel.getDutyList(3, activity);//获取用户彩豆任务列表信息
                        break;
                    case 4:
                        activity.newUserModel.beanSignIn(4, activity);//签到
                        break;
                }
            } else {
                super.handleMessage(msg);
            }
        }
    }
}
