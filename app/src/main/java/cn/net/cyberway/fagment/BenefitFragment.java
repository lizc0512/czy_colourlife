/*
package cn.net.cyberway.fagment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextPaint;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.dashuview.library.keep.MyListener;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.IXListViewListener;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultLoadMoreView;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.net.cyberway.R;
import cn.net.cyberway.adpter.BenefitAllAdapter;
import cn.net.cyberway.adpter.BenefitFindAdapter;
import cn.net.cyberway.adpter.BenefitRecommendAdapter;
import cn.net.cyberway.home.entity.HomeFuncEntity;
import cn.net.cyberway.utils.LinkParseUtil;

import static com.youmai.hxsdk.utils.DisplayUtil.getStatusBarHeight;

*/
/**
 * 生活-彩惠人生
 * hxg 2019.06.11
 *//*

public class BenefitFragment extends Fragment implements View.OnClickListener, HttpApiResponse, IXListViewListener, MyListener {
    private View mView;
    private View v_content;

    private ImageView iv_order;
    private ImageView iv_mine;
    private TextView tv_user_name;
    private RelativeLayout rl_sign;
    private LinearLayout ll_owe_address;
    private TextView tv_owe_address;
    private LinearLayout ll_owe_money;
    private TextView tv_owe_money;
    private LinearLayout ll_deduct_money;
    private TextView tv_deduct_money;
    private ImageView iv_pay;
    private TextView tv_hot;
    private TextView tv_hot_content;
    private RelativeLayout rl_recommend;
    private TextView tv_recommend;
    private View v_recommend;
    private RelativeLayout rl_all;
    private TextView tv_all;
    private View v_all;

    private ListView lv_recommend;
    private ListView lv_all;
    private SwipeMenuRecyclerView rv_find;
    private BenefitRecommendAdapter recommendAdapter;
    private BenefitAllAdapter allAdapter;
    private BenefitFindAdapter findAdapter;

    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    private SwipeRefreshLayout refresh_layout;

    private BGABanner bga_banner;
    private ArrayList<HomeFuncEntity.ContentBean> bannerDataBeanList = new ArrayList<HomeFuncEntity.ContentBean>();
    private List<String> bannerUrlList = new ArrayList<>();

    private int imgSize;//图片宽高
    private Activity mActivity;
    private int type = 0;
    private int page = 1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_benefit, container, false);
        mActivity = getActivity();
        mActivity.getWindow().setBackgroundDrawable(null);
        mShared = mActivity.getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        initView();
        initCatchData();
        initData();
        initListener();
        return mView;
    }

    private void initCatchData() {
        String homeBannerCache = mShared.getString(UserAppConst.COLOR_HOME_BANNER, "");
        if (!TextUtils.isEmpty(homeBannerCache)) {
            showBanner(homeBannerCache);
        }
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        if (message.what == UserMessageConstant.CHANGE_COMMUNITY) {//首页切换小区，及时更新
//            newHomeModel.getlifeInfo(0, this);
        } else if (message.what == UserMessageConstant.CHANGE_DIFF_LANG) {//切换语言
//            newHomeModel.getlifeInfo(0, this);
        } else if (message.what == UserMessageConstant.SIGN_IN_SUCCESS) {//登录成功刷新数据
//            lifeList.clear();
//            lifeIdList.clear();
            type = 0;
            initData();
        }
    }

    private void initView() {
        refresh_layout = mView.findViewById(R.id.refresh_layout);
        refresh_layout.setColorSchemeColors(Color.parseColor("#3290FF"), Color.parseColor("#6ABDF9"));
        rv_find = mView.findViewById(R.id.rv_find);

        v_content = LayoutInflater.from(getActivity()).inflate(R.layout.benefit_content_view, null);

        iv_order = v_content.findViewById(R.id.iv_order);
        iv_mine = v_content.findViewById(R.id.iv_mine);
        tv_user_name = v_content.findViewById(R.id.tv_user_name);
        rl_sign = v_content.findViewById(R.id.rl_sign);
        ll_owe_address = v_content.findViewById(R.id.ll_owe_address);
        tv_owe_address = v_content.findViewById(R.id.tv_owe_address);
        ll_owe_money = v_content.findViewById(R.id.ll_owe_money);
        tv_owe_money = v_content.findViewById(R.id.tv_owe_money);
        ll_deduct_money = v_content.findViewById(R.id.ll_deduct_money);
        tv_deduct_money = v_content.findViewById(R.id.tv_deduct_money);
        iv_pay = v_content.findViewById(R.id.iv_pay);
        bga_banner = v_content.findViewById(R.id.bga_benefit_banner);
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

        rv_find.addHeaderView(v_content);

        TextPaint paintRecommend = tv_recommend.getPaint();
        paintRecommend.setFakeBoldText(true);

        //推荐item图片宽高
        imgSize = (Utils.getDeviceWith(mActivity) - Utils.dip2px(mActivity, (16 + 12 + 8) * 2)) / 3;

        recommendAdapter = new BenefitRecommendAdapter(mActivity, bannerUrlList, imgSize);
        lv_recommend.setAdapter(recommendAdapter);

        allAdapter = new BenefitAllAdapter(mActivity, bannerUrlList);
        lv_all.setAdapter(allAdapter);

        rv_find.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        findAdapter = new BenefitFindAdapter(mActivity, bannerUrlList, 16);
        rv_find.setAdapter(findAdapter);
        DefaultLoadMoreView defaultLoadMoreView = new DefaultLoadMoreView(getContext());
        rv_find.setLoadMoreView(defaultLoadMoreView);
        rv_find.setLoadMoreListener(() -> {
            page++;
            bannerUrlList.addAll(bannerUrlList);
            findAdapter.notifyDataSetChanged();
            rv_find.loadMoreFinish(false, true);
        });
        rv_find.loadMoreFinish(false, true);
        findAdapter.setOnItemClickListener(position -> {
            ToastUtil.toastShow(mActivity, "点击了:" + position);
        });

        iv_order.setOnClickListener(this);
        iv_mine.setOnClickListener(this);
        rl_sign.setOnClickListener(this);
        ll_owe_address.setOnClickListener(this);
        ll_owe_money.setOnClickListener(this);
        ll_deduct_money.setOnClickListener(this);
        rl_recommend.setOnClickListener(this);
        rl_all.setOnClickListener(this);

        View v_benefit_status_bar = v_content.findViewById(R.id.v_benefit_status_bar);
        v_benefit_status_bar.setBackgroundColor(Color.parseColor("#FF898B"));
        setStatusBarHeight(v_benefit_status_bar);
    }

    private void setStatusBarHeight(View tabBarView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(mActivity));
        tabBarView.setLayoutParams(layoutParams);
    }

    private void initListener() {
        refresh_layout.setOnRefreshListener(this::initData);
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        tv_user_name.setText(mShared.getString(UserAppConst.Colour_NIACKNAME, "") + "您好~");
        refresh_layout.setRefreshing(false);
        selectTitle();
        findAdapter.notifyDataSetChanged();
    }

    */
/**
     * 更多
     *//*

    public void clickMore() {
        ToastUtil.toastShow(mActivity, "");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_order:
                break;

            case R.id.iv_mine:
                break;

            case R.id.rl_sign://签到

                break;

            case R.id.ll_owe_address://欠费地址
                break;

            case R.id.ll_owe_money://欠费
                break;

            case R.id.ll_deduct_money://缴费抵扣金
                break;

            case R.id.rl_recommend:
                type = 0;
                selectTitle();
                break;

            case R.id.rl_all:
                type = 1;
                selectTitle();
                break;
        }

    }

    */
/**
     * 选择推荐或全部
     *//*

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

            recommendAdapter.notifyDataSetChanged();
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

            allAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void OnHttpResponse(HttpApi api) {

    }

    @Override
    public void authenticationFeedback(String s, int i) {

    }

    @Override
    public void toCFRS(String s) {

    }

    @Override
    public void onRefresh(int id) {
        refresh_layout.setRefreshing(false);

    }

    @Override
    public void onLoadMore(int id) {
        refresh_layout.setRefreshing(false);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    */
/**
     * banner
     *//*

    private void showBanner(String result) {
        HomeFuncEntity homeFuncEntity = GsonUtils.gsonToBean(result, HomeFuncEntity.class);
        bannerDataBeanList.clear();
        bannerDataBeanList.addAll(homeFuncEntity.getContent());
        bannerUrlList.clear();
        for (HomeFuncEntity.ContentBean dataBean : bannerDataBeanList) {
            bannerUrlList.add(dataBean.getImg());
        }
        bga_banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                GlideImageLoader.loadImageDefaultDisplay(mActivity, model, itemView, R.drawable.icon_style_one, R.drawable.icon_style_one);
            }
        });
        bga_banner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                if (position >= 0 && position < bannerUrlList.size()) {
                    HomeFuncEntity.ContentBean contentBean = bannerDataBeanList.get(position);
                    LinkParseUtil.parse(mActivity, contentBean.getRedirect_uri(), "");
                }
            }
        });
        bga_banner.setData(bannerUrlList, null);
        bga_banner.setAutoPlayAble(bannerUrlList.size() > 1);
        bga_banner.setData(bannerUrlList, null);
        bga_banner.startAutoPlay();
    }

}
*/
