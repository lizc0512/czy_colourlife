package com.notification.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.notification.adapter.ENotificationAdapter;
import com.notification.adapter.ENotificationPagerAdapter;
import com.notification.model.OldNotificationModel;
import com.notification.protocol.NOTICE;
import com.notification.protocol.NOTICECATEGORY;
import com.notification.protocol.NotifyGetApi;
import com.notification.protocol.NotifyGetResponse;
import com.notification.protocol.NotifycategoryGetApi;
import com.notification.protocol.NotifycategoryGetResponse;
import com.user.UserAppConst;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.net.cyberway.R;

/**
 * E通知
 * Created by chenql on 15/12/17.
 */
public class ENotificationActivity extends BaseActivity implements HttpApiResponse {
    public SimpleDateFormat sdf;
    /**
     * 放置四个XListView的ViewPager
     */
    private ViewPager mPager;

    /**
     * 用来表示顶部的Tab的按钮
     */
    private Button[] tabButton;

    /**
     * 四个列表
     */
    private XListView[] xListView;

    /**
     * 搜索
     */
    private EditText edtSearch;

    /**
     * 包含四个列表的list
     */
    private List<XListView> viewsList;

    /**
     * 四个列表通知的数据
     */
    private ArrayList<NOTICE> noticesList = new ArrayList<>();//通知内容
    private ArrayList<NOTICE> noticesList0 = new ArrayList<>();//全部内容
    private ArrayList<NOTICE> noticesList1 = new ArrayList<>();//政府公告
    private ArrayList<NOTICE> noticesList2 = new ArrayList<>();//小区通知
    private ArrayList<NOTICE> noticesList3 = new ArrayList<>();//资讯
    private boolean isfresh;
    /**
     * 刷新传递的页码
     */
    int[] pageSize = {1, 1, 1, 1, 1};
    /**
     * 搜索的时候，保存当前page下不符合搜索条件的notifications的临时数据
     */
    private ArrayList<NOTICE> tmpNotifications;
    /**
     * 每个列表数据的页
     */
    private int[] listPage;

    /**
     * ViewPager当前的页
     */
    private int currentPager = 0;
    /**
     * 通知分类
     */
    private ArrayList<NOTICECATEGORY> arrayList = null;//通知类型
    /**
     * 四个通知列表的Adapter
     */
    private ENotificationAdapter[] adapters = new ENotificationAdapter[4];

    private OldNotificationModel model;

    private ImageView img_clear_input; // 搜索框清除输入文本的x按钮
    private boolean fromMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_e_notification);
        initView();
        initPublic();
    }

    private void initDatas() {
        setCategory();
        listPage = new int[arrayList.size()];
        tmpNotifications = new ArrayList<>();

        String mCommunityId = shared.getString(UserAppConst.Colour_login_community_uuid, "1");

        model.getNotification(this, mCommunityId, arrayList.get(currentPager).id.toString(), pageSize[currentPager], false);
    }

    /**
     * 设置通知分类
     */
    private void setCategory() {
        for (int i = 0; i < arrayList.size(); i++) {
            tabButton[i].setText((arrayList.get(i).name).replace(" ", ""));
        }
        (findViewById(R.id.ll_btns)).setVisibility(View.VISIBLE);
        if (fromMain) {
            currentPager = 2;
            fromMain = false;
        }
        changeButton(currentPager);
    }

    /**
     * 根据viewpager控制顶部button的显示
     *
     * @param currentPager 指示当前的pager
     */
    @SuppressWarnings("deprecation")
    private void changeButton(int currentPager) {
        // selector不起作用，所以采用这种方式控制显示
        switch (currentPager) {
            case 0:
                tabButton[0].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_selected));
                tabButton[1].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_normal));
                tabButton[2].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_normal));
                tabButton[3].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_normal));

                tabButton[0].setTextColor(getResources().getColor(R.color.blue_text_color));
                tabButton[1].setTextColor(getResources().getColor(R.color.black_text_color));
                tabButton[2].setTextColor(getResources().getColor(R.color.black_text_color));
                tabButton[3].setTextColor(getResources().getColor(R.color.black_text_color));
                break;
            case 1:
                tabButton[0].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_normal));
                tabButton[1].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_selected));
                tabButton[2].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_normal));
                tabButton[3].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_normal));

                tabButton[0].setTextColor(getResources().getColor(R.color.black_text_color));
                tabButton[1].setTextColor(getResources().getColor(R.color.blue_text_color));
                tabButton[2].setTextColor(getResources().getColor(R.color.black_text_color));
                tabButton[3].setTextColor(getResources().getColor(R.color.black_text_color));
                break;
            case 2:
                tabButton[0].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_normal));
                tabButton[1].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_normal));
                tabButton[2].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_selected));
                tabButton[3].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_normal));

                tabButton[0].setTextColor(getResources().getColor(R.color.black_text_color));
                tabButton[1].setTextColor(getResources().getColor(R.color.black_text_color));
                tabButton[2].setTextColor(getResources().getColor(R.color.blue_text_color));
                tabButton[3].setTextColor(getResources().getColor(R.color.black_text_color));
                break;
            case 3:
                tabButton[0].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_normal));
                tabButton[1].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_normal));
                tabButton[2].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_normal));
                tabButton[3].setBackgroundDrawable(getResources().getDrawable(R.drawable.bg_tab_selected));

                tabButton[0].setTextColor(getResources().getColor(R.color.black_text_color));
                tabButton[1].setTextColor(getResources().getColor(R.color.black_text_color));
                tabButton[2].setTextColor(getResources().getColor(R.color.black_text_color));
                tabButton[3].setTextColor(getResources().getColor(R.color.blue_text_color));
                break;
        }
    }

    @SuppressLint("InflateParams")
    private void initView() {

        img_clear_input = (ImageView) findViewById(R.id.img_clear_input);
        img_clear_input.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSearch.setText("");
            }
        });

        initTabButtons();

        initXListViews();

        initSearchEditText();

        initViewPage();
    }

    private void initViewPage() {
        mPager = (ViewPager) findViewById(R.id.notice_viewpager);
        mPager.setAdapter(new ENotificationPagerAdapter(viewsList));
        mPager.setCurrentItem(currentPager);
        mPager.setOnPageChangeListener(new PagerOnClickListener());
    }

    /**
     * 初始化搜索框
     */
    private void initSearchEditText() {
        edtSearch = (EditText) findViewById(R.id.edt_search);
        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String input = edtSearch.getText().toString();
                // 有输入内容，则隐藏搜索提示控件
                if (!TextUtils.isEmpty(input)) {
                    findViewById(R.id.rl_hint).setVisibility(View.INVISIBLE);
                    img_clear_input.setVisibility(View.VISIBLE);
                } else {
                    findViewById(R.id.rl_hint).setVisibility(View.VISIBLE);
                    if (null != adapters[currentPager]) {
                        adapters[currentPager].dataList.addAll(tmpNotifications);
                    }
                    viewsList.get(currentPager).setAdapter(adapters[currentPager]);
                    if (null != adapters[currentPager]) {
                        adapters[currentPager].notifyDataSetChanged();
                    }
                    viewsList.get(currentPager).setPullLoadEnable(true);
                    img_clear_input.setVisibility(View.INVISIBLE);
                }
            }
        });
        edtSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if ((actionId == EditorInfo.IME_ACTION_DONE
                        || actionId == EditorInfo.IME_ACTION_SEARCH)
                        && !TextUtils.isEmpty(edtSearch.getText().toString())) {
                    searchFor(edtSearch.getText().toString());
                    hideKeyBoard();
                }
                return false;
            }
        });
    }

    /**
     * 初始化四个XListView
     */
    private void initXListViews() {
        viewsList = new ArrayList<>();
        xListView = new XListView[4];
        LayoutInflater inflater = LayoutInflater.from(ENotificationActivity.this);
        xListView[0] = (XListView) inflater.inflate(R.layout.pager_item_notification, null);
        xListView[1] = (XListView) inflater.inflate(R.layout.pager_item_notification, null);
        xListView[2] = (XListView) inflater.inflate(R.layout.pager_item_notification, null);
        xListView[3] = (XListView) inflater.inflate(R.layout.pager_item_notification, null);

        for (int i = 0; i < 4; i++) {
            xListView[i].setXListViewListener(new IXListViewListener() {
                @Override
                public void onRefresh(int id) {
                }

                @Override
                public void onLoadMore(int id) {
                    pageSize[currentPager] = pageSize[currentPager] + 1;
                    isfresh = true;
                    getNotificationData(currentPager);
                }

            }, i);

            // 屏蔽下拉，激活上拉
            xListView[i].setPullLoadEnable(true);
            xListView[i].setPullRefreshEnable(false);
        }

        viewsList.add(xListView[0]);
        viewsList.add(xListView[1]);
        viewsList.add(xListView[2]);
        viewsList.add(xListView[3]);
    }

    /**
     * 初始化顶部表示Tab的按钮
     */
    private void initTabButtons() {
        tabButton = new Button[4];
        tabButton[0] = (Button) findViewById(R.id.btn_notice0);
        tabButton[1] = (Button) findViewById(R.id.btn_notice1);
        tabButton[2] = (Button) findViewById(R.id.btn_notice2);
        tabButton[3] = (Button) findViewById(R.id.btn_notice3);

        for (int i = 0; i < 4; i++) {
            final int finalI = i;
            tabButton[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    currentPager = finalI;
                    if (adapters[currentPager] == null) {
                        isfresh = false;
                        getNotificationData(currentPager);
                    } else {
                        mPager.setCurrentItem(finalI);
                    }
                }
            });
            tabButton[i].setBackgroundColor(getResources().getColor(R.color.bg_color));
        }
    }

    /**
     * 在类型、时间、通知内容中搜索
     *
     * @param s 待搜索的关键字
     */
    private void searchFor(String s) {
        if (s != null) {
            tmpNotifications.clear();
            int a = currentPager;
            if (adapters[currentPager] != null) {
                ArrayList<NOTICE> nos = (ArrayList<NOTICE>) adapters[currentPager].dataList;
                for (int i = 0; i < nos.size(); i++) {
                    if (!((nos.get(i).contact.contains(s))
                            || (nos.get(i).categoryName.contains(s))
                            || ((sdf.format(new Date(Long.parseLong(nos.get(i).create_time) * 1000))).contains(s)))) {
                        tmpNotifications.add(nos.get(i));// 保存不符合搜索条件的数据
                        adapters[currentPager].dataList.remove(i);
                        i--;
                    }
                }
                adapters[currentPager].notifyDataSetChanged();
                xListView[currentPager].stopRefresh();
                xListView[currentPager].stopLoadMore();
                viewsList.get(currentPager).loadMoreHide();
            } else {
                //ToastUtil.toastShow(this,"暂无数据");
            }
        } else {
            viewsList.get(currentPager).setPullLoadEnable(true);
        }
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        /**
         * 获取到通知类型
         */
        if (api.getClass().equals(NotifycategoryGetApi.class)) {
            NotifycategoryGetApi api1 = (NotifycategoryGetApi) api;
            NotifycategoryGetResponse response = api1.response;
            NOTICECATEGORY noticecategory = new NOTICECATEGORY();
            noticecategory.id = "0";
            noticecategory.name = "全部";
            arrayList = new ArrayList<>();
            arrayList.add(noticecategory);
            ArrayList<NOTICECATEGORY> list = response.categorys;
            arrayList.addAll(list);
            initDatas();
        } else if (api.getClass().equals(NotifyGetApi.class)) {
            /**
             * 获取通知内容
             */
            NotifyGetApi api1 = (NotifyGetApi) api;
            NotifyGetResponse response = api1.response;
            noticesList = response.notifies;
            if (response.notifies.size() > 0) {
                if (currentPager == 0) {
                    noticesList0.addAll(noticesList);
                    adapters[currentPager] = new ENotificationAdapter(ENotificationActivity.this, noticesList0);
                } else if (currentPager == 1) {
                    noticesList1.addAll(noticesList);
                    adapters[currentPager] = new ENotificationAdapter(ENotificationActivity.this, noticesList1);

                } else if (currentPager == 2) {
                    noticesList2.addAll(noticesList);
                    adapters[currentPager] = new ENotificationAdapter(ENotificationActivity.this, noticesList2);

                } else if (currentPager == 3) {
                    noticesList3.addAll(noticesList);
                    adapters[currentPager] = new ENotificationAdapter(ENotificationActivity.this, noticesList3);
                }
                //是否是刷新获取通知内容
                if (isfresh) {
                    adapters[currentPager].notifyDataSetChanged();
                } else {
                    viewsList.get(currentPager).setAdapter(adapters[currentPager]);
                }
                mPager.setCurrentItem(currentPager);
            } else {
                ToastUtil.toastShow(this, "没有更多数据");
            }
            xListView[currentPager].stopRefresh();
            xListView[currentPager].stopLoadMore();
        }
    }


    public class PagerOnClickListener implements ViewPager.OnPageChangeListener {
        @Override
        public void onPageScrollStateChanged(int arg0) {
            // 滑动的时候隐藏左边那条线
            // 可以优化，随手势滑动
            if (ViewPager.SCROLL_STATE_SETTLING == arg0
                    || ViewPager.SCROLL_STATE_IDLE == arg0) {
                findViewById(R.id.view_line).setVisibility(View.VISIBLE);
            } else {
                viewsList.get(currentPager).setPullLoadEnable(true);
                findViewById(R.id.view_line).setVisibility(View.GONE);
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
            edtSearch.setText("");
            currentPager = arg0;
            changeButton(currentPager);
            if (adapters[currentPager] == null) {
                isfresh = false;
                getNotificationData(currentPager);
            } else {
                ((ArrayList<NOTICE>) adapters[currentPager].dataList).addAll(tmpNotifications);
                adapters[currentPager].notifyDataSetChanged();
                mPager.setCurrentItem(currentPager);
            }
        }
    }

    /**
     * 隐藏软键盘，这个方法应该写到公共父类中
     */
    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService
                (Context.INPUT_METHOD_SERVICE);
        if (imm.isActive()) {
            if (ENotificationActivity.this.getCurrentFocus() != null) {
                imm.hideSoftInputFromWindow(ENotificationActivity.this.getCurrentFocus().getWindowToken()
                        , InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    /**
     * 获取当前pager里列表数据第listPage页的通知
     *
     * @param currentPage 当前的pager
     */
    private void getNotificationData(int currentPage) {
        currentPager = currentPage;
        // 获取通知内容
        try {
            model.getNotification(this, "1", arrayList.get(currentPager).id, pageSize[currentPager], isfresh);
        } catch (Exception e) {

        }
        listPage[currentPager]++;
    }

    /**
     * 初始化TopView里的控件
     */
    private void initPublic() {
        sdf = new SimpleDateFormat("MM-dd HH:mm");
        model = new OldNotificationModel(this);
        model.getCategory(this);
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        TextView tvTitle = (TextView) findViewById(R.id.user_top_view_title);
        tvTitle.setText(getString(R.string.e_notification));
        ImageView imgBack = (ImageView) findViewById(R.id.user_top_view_back);
        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        fromMain = getIntent().getBooleanExtra("fromMain", false);

        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, imgBack, tvTitle);
    }

}
