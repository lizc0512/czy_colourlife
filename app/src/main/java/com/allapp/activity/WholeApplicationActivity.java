package com.allapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.allapp.adapter.WholeAppAdapter;
import com.allapp.entity.HomeAllLifeEntity;
import com.allapp.entity.WholeAppSectionEntity;
import com.allapp.model.AllAppModel;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.external.eventbus.EventBus;
import com.nohttp.utils.GridSpacingItemDecoration;
import com.nohttp.utils.GsonUtils;
import com.realaudit.activity.RealCommonSubmitActivity;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.model.NewUserModel;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.home.view.AuthDialog;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/1/14 14:23
 * @change
 * @chang time
 * @class describe  全部应用的页面
 */
public class WholeApplicationActivity extends BaseActivity implements NewHttpResponse, View.OnClickListener {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private HorizontalScrollView horizonLScrollView;
    private RadioGroup rg_tab;
    private RecyclerView all_rv;
    private WholeAppAdapter wholeAdapter;
    private List<WholeAppSectionEntity> wholeAppList = new ArrayList<>();
    private AllAppModel allAppModel;
    private int customer_id;//用户的id

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_whole_application);
        initView();
        initData();
        if (!EventBus.getDefault().isregister(WholeApplicationActivity.this)) {
            EventBus.getDefault().register(WholeApplicationActivity.this);
        }
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        horizonLScrollView = findViewById(R.id.horizonLScrollView);
        rg_tab = findViewById(R.id.rg_tab);
        all_rv = findViewById(R.id.all_rv);
        user_top_view_title.setText("全部应用");
        user_top_view_back.setOnClickListener(this);
    }

    private void initData() {
        allAppModel = new AllAppModel(WholeApplicationActivity.this);
        customer_id = shared.getInt(UserAppConst.Colour_User_id, 0);
        allAppModel.getAllApplicationData(0, this);
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    if (!TextUtils.isEmpty(result)) {
                        HomeAllLifeEntity homeLifeEntity = GsonUtils.gsonToBean(result, HomeAllLifeEntity.class);
                        HomeAllLifeEntity.ContentBean contentBean = homeLifeEntity.getContent();
                        List<HomeAllLifeEntity.ContentBean.DataBean> dataBeanList = contentBean.getData();
                        wholeAppList.clear();
                        for (HomeAllLifeEntity.ContentBean.DataBean dataBean : dataBeanList) {
                            List<HomeAllLifeEntity.ContentBean.DataBean.ListBean> listBeanList = dataBean.getList();
                            int size = listBeanList == null ? 0 : listBeanList.size();
                            if (size > 0) {
                                initRadioGroup(dataBean, wholeAppList.size());
                                String headerContent = dataBean.getDesc();
                                WholeAppSectionEntity headerSectionEntity = new WholeAppSectionEntity(true, headerContent);
                                wholeAppList.add(headerSectionEntity);
                                for (int j = 0; j < size; j++) {
                                    WholeAppSectionEntity childSectionEntity = new WholeAppSectionEntity(listBeanList.get(j));
                                    wholeAppList.add(childSectionEntity);
                                }
                            }
                        }
                        setAllAppAdapter();
                    }
                } catch (Exception e) {

                }
                break;
            case 1:
                Message msg = new Message();
                msg.what = UserMessageConstant.UPDATE_APP_CLICK;
                EventBus.getDefault().post(msg);
                break;
        }
    }

    private void initRadioGroup(HomeAllLifeEntity.ContentBean.DataBean dataBean, int tagPosition) {
        String desc = dataBean.getDesc();
        scrollTab.add(desc);
        RadioButton rb = new RadioButton(this);
        rb.setPadding(20, 0, 20, 0);
        rb.setButtonDrawable(null);
        rb.setText(desc.trim());
        rb.setGravity(Gravity.CENTER);
        rb.setTag(tagPosition);
        rb.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
        try {
            if (scrollTab.size() == 1) {
                oldTab = desc;
                rb.setTextColor(getResources().getColorStateList(R.color.color_2ba0ea));
                rb.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.bg_underline_tab));
            } else {
                rb.setTextColor(getResources().getColorStateList(R.color.color_333333));
                rb.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.bg_white_underline_tab));
            }
            rb.setOnCheckedChangeListener(onCheckedChangeListener);
            rg_tab.addView(rb);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void moveToPosition(int position) {
        int first = gridLayoutManager.findFirstVisibleItemPosition();
        int end = gridLayoutManager.findLastVisibleItemPosition();
        if (first == -1 || end == -1) return;
        if (position <= first) {
            gridLayoutManager.scrollToPosition(position);
        } else if (position >= end) {
            isMove = true;
            scrollPosition = position;
            gridLayoutManager.smoothScrollToPosition(all_rv, null, position);
        } else {
            //中间部分
            int n = position - gridLayoutManager.findFirstVisibleItemPosition();
            if (n > 0 && n < wholeAppList.size()) {
                int top = gridLayoutManager.findViewByPosition(position).getTop();
                all_rv.scrollBy(0, top);
            }
        }
    }

    private boolean isMove = false;
    private int scrollPosition = 0;

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            try {
                if (isMove && newState == RecyclerView.SCROLL_STATE_IDLE) {
                    isMove = false;
                    View view = gridLayoutManager.findViewByPosition(scrollPosition);
                    if (view != null) {
                        int top = (int) view.getTop();
                        recyclerView.scrollBy(0, top);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int position = gridLayoutManager.findFirstVisibleItemPosition();
            String currentTab = "";
            if (position >= 0) {
                for (int i = 0; i < position + 1; i++) {
                    if (wholeAppList.get(i).isHeader) {
                        currentTab = wholeAppList.get(i).header;
                    }
                }
                resetStatus();
                scrollTab(currentTab);
            }
        }
    };

    private String oldTab;

    private void scrollTab(String newTab) {
        try {
            int position = scrollTab.indexOf(oldTab);
            int targetPosition = scrollTab.indexOf(newTab);
            if (targetPosition != -1) {
                oldTab = newTab;
                final int x = (targetPosition - position) * getTabWidth();
                RadioButton radioButton = ((RadioButton) rg_tab.getChildAt(targetPosition));
                radioButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.bg_underline_tab));
                radioButton.setTextColor(getResources().getColorStateList(R.color.color_2ba0ea));
                horizonLScrollView.scrollBy(x, 0);
            }
        } catch (Exception e) {
            e.printStackTrace();
            ToastUtil.toastShow(WholeApplicationActivity.this, e.getMessage());
        }
    }

    private void resetStatus() {
        for (int j = 0; j < rg_tab.getChildCount(); j++) {
            RadioButton radioButton = ((RadioButton) rg_tab.getChildAt(j));
            radioButton.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.bg_white_underline_tab));
            radioButton.setTextColor(getResources().getColorStateList(R.color.color_333333));
        }
    }

    private List<String> scrollTab = new ArrayList<>();
    private int tabWidth = 0;//标签宽度

    private int getTabWidth() {
        if (tabWidth == 0) {
            if (rg_tab != null && rg_tab.getChildCount() != 0) {
                tabWidth = rg_tab.getWidth() / rg_tab.getChildCount();
            }
        }
        return tabWidth;
    }


    RadioButton.OnCheckedChangeListener onCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            try {
                int position = (int) buttonView.getTag();
                String text = buttonView.getText().toString();
                if (!oldTab.equals(text) && isChecked) {
                    oldTab = text;
                    moveToPosition(position);
                }
                if (isChecked) {
                    resetStatus();
                    buttonView.setCompoundDrawablesWithIntrinsicBounds(null, null, null, getResources().getDrawable(R.drawable.bg_underline_tab));
                    buttonView.setTextColor(getResources().getColorStateList(R.color.textcolor_27a2f0));
                }
            } catch (Exception e) {
                e.printStackTrace();
                ToastUtil.toastShow(WholeApplicationActivity.this, e.getMessage());
            }
        }
    };
    private GridLayoutManager gridLayoutManager;
    private HomeAllLifeEntity.ContentBean.DataBean.ListBean listBean;

    private void setAllAppAdapter() {
        if (null == wholeAdapter) {
            wholeAdapter = new WholeAppAdapter(WholeApplicationActivity.this, R.layout.adapter_child_itemapp, R.layout.adapter_header_itemapp, wholeAppList);
            gridLayoutManager = new GridLayoutManager(WholeApplicationActivity.this, 4);
            all_rv.addItemDecoration(new GridSpacingItemDecoration(4, Utils.dip2px(WholeApplicationActivity.this, 5), false));
            all_rv.setLayoutManager(gridLayoutManager);
            wholeAdapter.isFirstOnly(true);
            wholeAdapter.openLoadAnimation(BaseQuickAdapter.ALPHAIN);
            all_rv.setAdapter(wholeAdapter);
        } else {
            wholeAdapter.notifyDataSetChanged();
        }

        wholeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                try {
                    listBean = wholeAppList.get(position).t;
                    String realName = shared.getString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "");
                    boolean isRealName = !TextUtils.isEmpty(realName);
                    if ("1".equals(listBean.getIs_auth()) && !isRealName) {//是否需要认证 1：需要实名，2：不需要实名
                        needAuth();
                    } else {
                        authItemClick();
                    }
                } catch (Exception e) {

                }
            }
        });
        all_rv.addOnScrollListener(onScrollListener);
        View emptyFooterView = LayoutInflater.from(WholeApplicationActivity.this).inflate(R.layout.adapter_child_footerview, null);
        wholeAdapter.addFooterView(emptyFooterView);
    }

    private AuthDialog authDialog;

    /**
     * 需求实名认证
     */
    private void needAuth() {
        if (authDialog == null) {
            authDialog = new AuthDialog(this);
        }
        authDialog.show();
        authDialog.iv_close.setOnClickListener(v1 -> {
            if (authDialog != null) {
                authDialog.dismiss();
            }
        });
        authDialog.tv_pass.setOnClickListener(v1 -> {
            if (authDialog != null) {
                authDialog.dismiss();
            }
            editor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "dismiss").commit();//出现过一次
            authItemClick();
        });
        authDialog.iv_goto.setOnClickListener(v2 -> {
            if (authDialog != null) {
                authDialog.dismiss();
            }
            editor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "dismiss").commit();//出现过一次
            Intent intent = new Intent(WholeApplicationActivity.this, RealCommonSubmitActivity.class);
            startActivity(intent);
        });
    }

    /**
     * 需要实名认证的item
     */
    private void authItemClick() {
        String linkUrl;
        if (null != listBean) {
            linkUrl = listBean.getUrl();
            if (!TextUtils.isEmpty(linkUrl)) {
                LinkParseUtil.parse(WholeApplicationActivity.this, linkUrl, "");
                allAppModel.uploadAppClick(1, listBean.getId(), WholeApplicationActivity.this);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }


    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.REAL_SUCCESS_STATE:
                authItemClick();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(WholeApplicationActivity.this)) {
            EventBus.getDefault().unregister(WholeApplicationActivity.this);
        }
    }
}
