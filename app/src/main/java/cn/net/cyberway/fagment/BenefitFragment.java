package cn.net.cyberway.fagment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.NoScrollGridView;
import com.external.eventbus.EventBus;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultLoadMoreView;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.net.cyberway.R;
import cn.net.cyberway.adpter.BenefitAllAdapter;
import cn.net.cyberway.adpter.BenefitFindAdapter;
import cn.net.cyberway.adpter.BenefitHotAdapter;
import cn.net.cyberway.adpter.BenefitRecommendAdapter;
import cn.net.cyberway.model.BenefitModel;
import cn.net.cyberway.protocol.BenefitBannerEntity;
import cn.net.cyberway.protocol.BenefitChannlEntity;
import cn.net.cyberway.protocol.BenefitFindEntity;
import cn.net.cyberway.protocol.BenefitHotEntity;
import cn.net.cyberway.protocol.BenefitProfileEntity;
import cn.net.cyberway.utils.BenefitDefaultDataConst;
import cn.net.cyberway.utils.LinkParseUtil;

import static com.youmai.hxsdk.utils.DisplayUtil.getStatusBarHeight;

/**
 * 生活-彩惠人生
 * hxg 2019.06.11
 */
public class BenefitFragment extends Fragment implements View.OnClickListener, NewHttpResponse {
    private View mView;
    private View v_content;

    private ImageView iv_order;
    private ImageView iv_mine;
    private TextView tv_user_name;
    private ImageView iv_sign;
    private LinearLayout ll_owe_address;
    private TextView tv_owe_address;
    private LinearLayout ll_owe_money;
    private TextView tv_owe_money;
    private LinearLayout ll_deduct_money;
    private TextView tv_deduct_money;
    private ImageView iv_pay;
    private NoScrollGridView gv_hot;
    private TextView tv_hot;
    private TextView tv_hot_content;
    private RelativeLayout rl_recommend;
    private TextView tv_recommend;
    private View v_recommend;
    private RelativeLayout rl_all;
    private TextView tv_all;
    private View v_all;
    private TextView tv_find;
    private TextView tv_owe_address_name;
    private TextView tv_owe_money_name;
    private TextView tv_deduct_money_name;

    private RecyclerView lv_recommend;
    private RecyclerView lv_all;
    private SwipeMenuRecyclerView rv_find;
    private BenefitHotAdapter hotAdapter;
    private BenefitRecommendAdapter recommendAdapter;
    private BenefitAllAdapter allAdapter;
    private BenefitFindAdapter findAdapter;

    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    private SwipeRefreshLayout refresh_layout;
    private LinearLayout ll_alpha;
    private TextView tv_alpha_title;
    private View v_alpha_benefit_status_bar;
    private ImageView iv_alpha_order;
    private ImageView iv_alpha_mine;

    private BGABanner bga_banner;
    private ArrayList<BenefitBannerEntity.ContentBean> bannerDataBeanList = new ArrayList<>();
    private ArrayList<BenefitHotEntity.ContentBean.ListBean> hotList = new ArrayList<>();
    private List<String> bannerUrlList = new ArrayList<>();
    private List<BenefitChannlEntity.ContentBean.RecommendBean.DataBean> recommendList = new ArrayList<>();
    private List<BenefitChannlEntity.ContentBean.AllBean.DataBeanX> allList = new ArrayList<>();
    private List<BenefitFindEntity.ContentBean.ListBean> findList = new ArrayList<>();

    private Activity mActivity;
    private int type = 0;
    private int page = 1;
    private BenefitModel benefitModel;

    private String orderUrl = "";
    private String mineUrl = "";
    private String propertyAddressUrl = "";
    private String arrearsUrl = "";
    private String propertyDeductionUrl = "";
    private String payUrl = "";
    private String signUrl = "";
    private boolean isFinish = false;
    private boolean isRefresh = false;
    private boolean selectAll = false;
    private boolean selectRecoment = true;
    private InterHandler mHandler = new InterHandler(this);

    private String profileCache = "";
    private String bannerCache = "";
    private String hotCache = "";
    private String channelCache = "";
    private String findCache = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_benefit, container, false);
        mActivity = getActivity();
        mActivity.getWindow().setBackgroundDrawable(null);
        mShared = mActivity.getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        benefitModel = new BenefitModel(mActivity);
        if (!EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().register(this);
        }
        initView();
        initListener();
        initCacheData();
        selectTitle();
        initData();
        return mView;
    }

    private void initCacheData() {
        profileCache = mShared.getString(UserAppConst.COLOUR_BENEFIT_PROFILE, "");
        if (TextUtils.isEmpty(profileCache)) {
            refresh_layout.setRefreshing(true);
        }
        if (!TextUtils.isEmpty(profileCache)) {
            result(1, profileCache);
        }
        bannerCache = mShared.getString(UserAppConst.COLOUR_BENEFIT_BANNER, "");
        if (!TextUtils.isEmpty(profileCache)) {
            showBanner(bannerCache);
        }

        hotCache = mShared.getString(UserAppConst.COLOUR_BENEFIT_HOT, "");
        if (!TextUtils.isEmpty(hotCache)) {
            result(3, hotCache);
        } else {
            result(3, BenefitDefaultDataConst.DEFAULT_HOT);
        }

        channelCache = mShared.getString(UserAppConst.COLOUR_BENEFIT_CHANNEL, "");
        if (!TextUtils.isEmpty(channelCache)) {
            result(4, channelCache);
        } else {
            result(4, BenefitDefaultDataConst.DEFAULT_RECOMMEND);
        }

        findCache = mShared.getString(UserAppConst.COLOUR_BENEFIT_FIND, "");
        if (!TextUtils.isEmpty(findCache)) {
            mHandler.sendEmptyMessageDelayed(6, 600);
        }
    }

    public void onEvent(Object event) {
        try {
            final Message message = (Message) event;
            if (message.what == UserMessageConstant.CHANGE_COMMUNITY) {//首页切换小区，及时更新
                type = 0;
                page = 1;
                isFinish = false;
                selectAll = false;
                selectRecoment = true;
                selectTitle();
                initData();
            } else if (message.what == UserMessageConstant.CHANGE_DIFF_LANG) {//切换语言
                type = 0;
                page = 1;
                isFinish = false;
                selectAll = false;
                selectRecoment = true;
                selectTitle();
                initData();
            } else if (message.what == UserMessageConstant.SIGN_IN_SUCCESS) {//登录成功刷新数据
                type = 0;
                page = 1;
                isFinish = false;
                selectAll = false;
                selectRecoment = true;
                selectTitle();
                initData();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initView() {
        refresh_layout = mView.findViewById(R.id.refresh_layout);
        ll_alpha = mView.findViewById(R.id.ll_alpha);
        tv_alpha_title = mView.findViewById(R.id.tv_alpha_title);
        v_alpha_benefit_status_bar = mView.findViewById(R.id.v_alpha_benefit_status_bar);
        iv_alpha_order = mView.findViewById(R.id.iv_alpha_order);
        iv_alpha_mine = mView.findViewById(R.id.iv_alpha_mine);

        refresh_layout.setColorSchemeColors(Color.parseColor("#3290FF"), Color.parseColor("#6ABDF9"));
        refresh_layout.setOnRefreshListener(() -> {
            page = 1;
            isRefresh = true;
            isFinish = false;
            selectAll = false;
            selectRecoment = false;
            findList.clear();
            findAdapter.notifyDataSetChanged();
            initData();
        });

        rv_find = mView.findViewById(R.id.rv_find);
        v_content = LayoutInflater.from(mActivity).inflate(R.layout.benefit_content_view, null);

        iv_order = v_content.findViewById(R.id.iv_order);
        iv_mine = v_content.findViewById(R.id.iv_mine);
        tv_user_name = v_content.findViewById(R.id.tv_user_name);
        iv_sign = v_content.findViewById(R.id.iv_sign);
        ll_owe_address = v_content.findViewById(R.id.ll_owe_address);
        tv_owe_address = v_content.findViewById(R.id.tv_owe_address);
        ll_owe_money = v_content.findViewById(R.id.ll_owe_money);
        tv_owe_money = v_content.findViewById(R.id.tv_owe_money);
        ll_deduct_money = v_content.findViewById(R.id.ll_deduct_money);
        tv_deduct_money = v_content.findViewById(R.id.tv_deduct_money);
        iv_pay = v_content.findViewById(R.id.iv_pay);
        bga_banner = v_content.findViewById(R.id.bga_benefit_banner);
        gv_hot = v_content.findViewById(R.id.gv_hot);
        tv_hot = v_content.findViewById(R.id.tv_hot);
        tv_hot_content = v_content.findViewById(R.id.tv_hot_content);
        rl_recommend = v_content.findViewById(R.id.rl_recommend);
        tv_recommend = v_content.findViewById(R.id.tv_recommend);
        v_recommend = v_content.findViewById(R.id.v_recommend);
        rl_all = v_content.findViewById(R.id.rl_all);
        tv_all = v_content.findViewById(R.id.tv_all);
        v_all = v_content.findViewById(R.id.v_all);
        lv_recommend = v_content.findViewById(R.id.lv_recommend);
        lv_all = v_content.findViewById(R.id.lv_all);
        tv_find = v_content.findViewById(R.id.tv_find);
        tv_owe_address_name = v_content.findViewById(R.id.tv_owe_address_name);
        tv_owe_money_name = v_content.findViewById(R.id.tv_owe_money_name);
        tv_deduct_money_name = v_content.findViewById(R.id.tv_deduct_money_name);

        rv_find.addHeaderView(v_content);

        TextPaint paintRecommend = tv_recommend.getPaint();
        paintRecommend.setFakeBoldText(true);

        hotAdapter = new BenefitHotAdapter(mActivity, hotList);
        gv_hot.setAdapter(hotAdapter);
        gv_hot.setOnItemClickListener((parent, view, position, id) -> {
            LinkParseUtil.parse(mActivity, hotList.get(position).getUrl(), "");
        });

        lv_recommend.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        lv_recommend.setNestedScrollingEnabled(false);
        recommendAdapter = new BenefitRecommendAdapter(mActivity, recommendList/*, imgSize*/);
        lv_recommend.setAdapter(recommendAdapter);

        lv_all.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        lv_all.setNestedScrollingEnabled(false);
        allAdapter = new BenefitAllAdapter(mActivity, allList);
        lv_all.setAdapter(allAdapter);

        rv_find.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayout.VERTICAL, false));
        findAdapter = new BenefitFindAdapter(mActivity, findList);
        rv_find.setAdapter(findAdapter);
        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(getContext());
        rv_find.setLoadMoreView(defaultLoadMoreView);
        rv_find.setLoadMoreListener(() -> {
            if (!isFinish && !isRefresh) {
                page++;
                benefitModel.getArticle(5, page, this);
            }
        });
        rv_find.loadMoreFinish(false, true);
        findAdapter.setOnItemClickListener(position -> {
            LinkParseUtil.parse(mActivity, findList.get(position - 1).getUrl(), "");
        });

        iv_order.setOnClickListener(this);
        iv_alpha_order.setOnClickListener(this);
        iv_mine.setOnClickListener(this);
        iv_alpha_mine.setOnClickListener(this);
        iv_sign.setOnClickListener(this);
        ll_owe_address.setOnClickListener(this);
        ll_owe_money.setOnClickListener(this);
        ll_deduct_money.setOnClickListener(this);
        rl_recommend.setOnClickListener(this);
        rl_all.setOnClickListener(this);
        iv_pay.setOnClickListener(this);

        View v_benefit_status_bar = v_content.findViewById(R.id.v_benefit_status_bar);
        setStatusBarHeight(v_benefit_status_bar);
        setStatusBarHeight(v_alpha_benefit_status_bar);
    }

    private void setStatusBarHeight(View tabBarView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(mActivity));
        tabBarView.setLayoutParams(layoutParams);
    }

    private void initData() {
        isRefresh = true;
        mHandler.sendEmptyMessageDelayed(1, 50);
        mHandler.sendEmptyMessageDelayed(2, 300);
        mHandler.sendEmptyMessageDelayed(3, 600);
        mHandler.sendEmptyMessageDelayed(4, 1000);
        mHandler.sendEmptyMessageDelayed(5, 1200);
    }

    @Override
    public void onResume() {
        super.onResume();
        mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    /**
     * 选择推荐或全部
     */
    private void selectTitle() {
        if (0 == type) {
            tv_all.setTextColor(mActivity.getResources().getColor(R.color.gray_text_color));
            TextPaint paintAll = tv_all.getPaint();
            paintAll.setFakeBoldText(false);
            v_all.setVisibility(View.GONE);
            lv_all.setVisibility(View.GONE);

            tv_recommend.setTextColor(mActivity.getResources().getColor(R.color.black_text_color));
            TextPaint paintRecommend = tv_recommend.getPaint();
            paintRecommend.setFakeBoldText(true);
            v_recommend.setVisibility(View.VISIBLE);
            lv_recommend.setVisibility(View.VISIBLE);
        } else {
            tv_recommend.setTextColor(mActivity.getResources().getColor(R.color.gray_text_color));
            TextPaint recommend = tv_recommend.getPaint();
            recommend.setFakeBoldText(false);
            v_recommend.setVisibility(View.GONE);
            lv_recommend.setVisibility(View.GONE);

            tv_all.setTextColor(mActivity.getResources().getColor(R.color.black_text_color));
            TextPaint all = tv_all.getPaint();
            all.setFakeBoldText(true);
            v_all.setVisibility(View.VISIBLE);
            lv_all.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_order:
            case R.id.iv_alpha_order:
                LinkParseUtil.parse(mActivity, orderUrl, "");
                break;

            case R.id.iv_mine:
            case R.id.iv_alpha_mine:
                LinkParseUtil.parse(mActivity, mineUrl, "");
                break;

            case R.id.iv_sign://签到
                LinkParseUtil.parse(mActivity, signUrl, "");
                break;

            case R.id.ll_owe_address://欠费地址
                LinkParseUtil.parse(mActivity, propertyAddressUrl, "");
                break;

            case R.id.ll_owe_money://欠费
                LinkParseUtil.parse(mActivity, arrearsUrl, "");
                break;

            case R.id.ll_deduct_money://缴费抵扣金
                LinkParseUtil.parse(mActivity, propertyDeductionUrl, "");
                break;

            case R.id.iv_pay://缴费抵扣金
                LinkParseUtil.parse(mActivity, payUrl, "");
                break;

            case R.id.rl_recommend:
                type = 0;
                selectTitle();
                if (!selectRecoment) {
                    selectRecoment = true;
                    recommendAdapter.setData(recommendList);
                }
                break;

            case R.id.rl_all:
                type = 1;
                selectTitle();
                if (!selectAll) {
                    selectAll = true;
                    allAdapter.setData(allList);
                }
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        refresh_layout.setRefreshing(false);
        switch (what) {
            case 1:
                if (!profileCache.equals(result)) {
                    mEditor.putString(UserAppConst.COLOUR_BENEFIT_PROFILE, result).apply();
                    result(what, result);
                } else {
                    refresh_layout.setRefreshing(false);
                }
                break;
            case 2:
                if (!bannerCache.equals(result)) {
                    mEditor.putString(UserAppConst.COLOUR_BENEFIT_BANNER, result).apply();
                    result(what, result);
                }
                break;
            case 3:
                if (!hotCache.equals(result)) {
                    mEditor.putString(UserAppConst.COLOUR_BENEFIT_HOT, result).apply();
                    result(what, result);
                }
                break;
            case 4:
                if (!channelCache.equals(result)) {
                    mEditor.putString(UserAppConst.COLOUR_BENEFIT_CHANNEL, result).apply();
                    result(what, result);
                }
                break;
            case 5:
                result(what, result);
                break;
        }
    }

    @SuppressLint("SetTextI18n")
    private void result(int what, String result) {
        switch (what) {
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        BenefitProfileEntity entity = GsonUtils.gsonToBean(result, BenefitProfileEntity.class);
                        BenefitProfileEntity.ContentBean bean = entity.getContent();

                        orderUrl = bean.getOrder().getUrl();
                        mineUrl = bean.getPersonal_center().getUrl();

                        tv_user_name.setText(bean.getNickname() + "您好~");
                        if (1 == bean.getSign_in().getIs_show()) {
                            iv_sign.setVisibility(View.VISIBLE);
                            GlideImageLoader.loadImageDefaultDisplay(getActivity(), bean.getSign_in().getImage(), iv_sign, R.drawable.ic_benefit_sign, R.drawable.ic_benefit_sign);
                            signUrl = bean.getSign_in().getUrl();
                        } else {
                            iv_sign.setVisibility(View.GONE);
                        }
                        BenefitProfileEntity.ContentBean.PropertyAddressBean propertyAddressBean = entity.getContent().getProperty_address();
                        tv_owe_address_name.setText(propertyAddressBean.getTitle());
                        tv_owe_address.setText(propertyAddressBean.getNum() + "");
                        propertyAddressUrl = propertyAddressBean.getUrl();

                        BenefitProfileEntity.ContentBean.ArrearsBean arrearsBean = entity.getContent().getArrears();
                        arrearsUrl = arrearsBean.getUrl();
                        tv_owe_money_name.setText(arrearsBean.getTitle());
                        tv_owe_money.setText(arrearsBean.getAmount() + "");

                        BenefitProfileEntity.ContentBean.PropertyDeductionBean propertyDeductionBean = entity.getContent().getProperty_deduction();
                        propertyDeductionUrl = propertyDeductionBean.getUrl();
                        tv_deduct_money_name.setText(propertyDeductionBean.getTitle());
                        tv_deduct_money.setText(propertyDeductionBean.getBalance() + "");

                        BenefitProfileEntity.ContentBean.PropertyButtonBean propertyButtonBean = entity.getContent().getProperty_button();
                        if (1 == propertyButtonBean.getIs_show()) {
                            iv_pay.setVisibility(View.VISIBLE);
                            GlideImageLoader.loadImageDefaultDisplay(getActivity(), propertyButtonBean.getImage(), iv_pay, R.drawable.ic_benefit_pay, R.drawable.ic_benefit_pay);
                            payUrl = propertyButtonBean.getUrl();
                        } else {
                            iv_pay.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    showBanner(result);
                }
                break;
            case 3:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        BenefitHotEntity entity = GsonUtils.gsonToBean(result, BenefitHotEntity.class);
                        BenefitHotEntity.ContentBean bean = entity.getContent();
                        tv_hot.setText(bean.getTitle());
                        tv_hot_content.setText(bean.getDesc());
                        hotList.clear();
                        hotList.addAll(bean.getList());
                        hotAdapter.notifyDataSetChanged();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 4:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        BenefitChannlEntity entity = GsonUtils.gsonToBean(result, BenefitChannlEntity.class);
                        BenefitChannlEntity.ContentBean bean = entity.getContent();
                        tv_recommend.setText(bean.getRecommend().getTitle());
                        tv_all.setText(bean.getAll().getTitle());
                        recommendList.clear();
                        allList.clear();
                        recommendList.addAll(bean.getRecommend().getData());
                        allList.addAll(bean.getAll().getData());
                        if (0 == type) {
                            selectRecoment = true;
                            selectAll = false;
                            recommendAdapter.setData(recommendList);
                        } else {
                            selectRecoment = false;
                            selectAll = true;
                            allAdapter.setData(allList);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 5:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        BenefitFindEntity entity = GsonUtils.gsonToBean(result, BenefitFindEntity.class);
                        BenefitFindEntity.ContentBean bean = entity.getContent();
                        tv_find.setText(bean.getTitle());
                        if (1 == page) {
                            mEditor.putString(UserAppConst.COLOUR_BENEFIT_FIND, result).apply();
                        }
                        if (1 == page || (isRefresh && findList.size() != 0)) {
                            findList.clear();
                        }
                        findList.addAll(bean.getList());
                        findAdapter.total = bean.getTotal();
                        isFinish = findList.size() >= bean.getTotal();
                        findAdapter.notifyDataSetChanged();
                        rv_find.loadMoreFinish(false, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                isRefresh = false;
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (null != mHandler) {
            mHandler.removeCallbacksAndMessages(null);
        }
        if (EventBus.getDefault().isregister(getActivity())) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    /**
     * banner
     */
    private void showBanner(String result) {
        try {
            BenefitBannerEntity benefitBannerEntity = GsonUtils.gsonToBean(result, BenefitBannerEntity.class);
            bannerDataBeanList.clear();
            bannerDataBeanList.addAll(benefitBannerEntity.getContent());
            bannerUrlList.clear();
            for (BenefitBannerEntity.ContentBean dataBean : bannerDataBeanList) {
                bannerUrlList.add(dataBean.getImg());
            }
            bga_banner.setAdapter((BGABanner.Adapter<ImageView, String>) (banner, itemView, model, position) -> {
                GlideImageLoader.loadImageDefaultDisplay(mActivity, model, itemView, R.drawable.icon_style_one, R.drawable.icon_style_one);
            });
            bga_banner.setDelegate((BGABanner.Delegate<ImageView, String>) (banner, itemView, model, position) -> {
                if (position >= 0 && position < bannerUrlList.size()) {
                    BenefitBannerEntity.ContentBean contentBean = bannerDataBeanList.get(position);
                    LinkParseUtil.parse(mActivity, contentBean.getUrl(), "");
                }
            });
            bga_banner.setAutoPlayAble(bannerUrlList.size() > 1);
            bga_banner.setData(bannerUrlList, null);
            bga_banner.startAutoPlay();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 标题
     */
    private void initListener() {
        rv_find.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                refresh_layout.setEnabled(rv_find.getScrollY() == 0);
            }
        });
    }

    private static class InterHandler extends Handler {
        private WeakReference<BenefitFragment> handlerActivity;

        InterHandler(BenefitFragment fragment) {
            handlerActivity = new WeakReference<>(fragment);
        }

        @Override
        public void handleMessage(Message msg) {
            BenefitFragment fragment = handlerActivity.get();
            if (null != fragment) {
                switch (msg.what) {
                    case 1:
                        fragment.benefitModel.getProfile(1, fragment);
                        break;
                    case 2:
                        fragment.benefitModel.getBanner(2, fragment);
                        break;
                    case 3:
                        fragment.benefitModel.getHot(3, fragment);
                        break;
                    case 4:
                        fragment.benefitModel.getChannel(4, fragment);
                        break;
                    case 5:
                        fragment.benefitModel.getArticle(5, fragment.page, fragment);
                        break;
                    case 6:
                        fragment.result(5, fragment.findCache);
                        break;
                }
            } else {
                super.handleMessage(msg);
            }
        }
    }
}
