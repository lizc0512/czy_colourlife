package cn.net.cyberway.fagment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

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

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.net.cyberway.R;
import cn.net.cyberway.home.entity.HomeFuncEntity;
import cn.net.cyberway.utils.LinkParseUtil;

import static com.youmai.hxsdk.utils.DisplayUtil.getStatusBarHeight;

/**
 * 生活-彩惠人生
 * hxg 2019.06.11
 */
public class BenefitFragment extends Fragment implements View.OnClickListener, HttpApiResponse, IXListViewListener, MyListener {
    private View mView;

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
    private TextView tv_hot;
    private TextView tv_hot_content;
    private RelativeLayout rl_recommend;
    private TextView tv_recommend;
    private View v_recommend;
    private RelativeLayout rl_all;
    private TextView tv_all;
    private View v_all;


    private LinearLayout ll_text;
    private View tv_text;


    private ImageView iv1;
    private ImageView iv2;
    private ImageView iv3;

    private SharedPreferences mShared;
    private SharedPreferences.Editor mEditor;
    private SwipeRefreshLayout refresh_layout;

    private BGABanner bga_banner;
    private ArrayList<HomeFuncEntity.ContentBean> bannerDataBeanList = new ArrayList<HomeFuncEntity.ContentBean>();
    private List<String> bannerUrlList = new ArrayList<>();

    private int itemSize;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_benefit, container, false);
        getActivity().getWindow().setBackgroundDrawable(null);
        mShared = getActivity().getSharedPreferences(UserAppConst.USERINFO, 0);
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
            initData();
        }
    }

    private void initView() {
        refresh_layout = mView.findViewById(R.id.refresh_layout);
        refresh_layout.setColorSchemeColors(Color.parseColor("#3290FF"), Color.parseColor("#6ABDF9"));

        iv_order = mView.findViewById(R.id.iv_order);
        iv_mine = mView.findViewById(R.id.iv_mine);
        tv_user_name = mView.findViewById(R.id.tv_user_name);
        iv_sign = mView.findViewById(R.id.iv_sign);
        ll_owe_address = mView.findViewById(R.id.ll_owe_address);
        tv_owe_address = mView.findViewById(R.id.tv_owe_address);
        ll_owe_money = mView.findViewById(R.id.ll_owe_money);
        tv_owe_money = mView.findViewById(R.id.tv_owe_money);
        ll_deduct_money = mView.findViewById(R.id.ll_deduct_money);
        tv_deduct_money = mView.findViewById(R.id.tv_deduct_money);
        iv_pay = mView.findViewById(R.id.iv_pay);
        bga_banner = mView.findViewById(R.id.bga_benefit_banner);
        tv_hot = mView.findViewById(R.id.tv_hot);
        tv_hot_content = mView.findViewById(R.id.tv_hot_content);
        rl_recommend = mView.findViewById(R.id.rl_recommend);
        tv_recommend = mView.findViewById(R.id.tv_recommend);
        v_recommend = mView.findViewById(R.id.v_recommend);
        rl_all = mView.findViewById(R.id.rl_all);
        tv_all = mView.findViewById(R.id.tv_all);
        v_all = mView.findViewById(R.id.v_all);
        iv1 = mView.findViewById(R.id.iv1);
        iv2 = mView.findViewById(R.id.iv2);
        iv3 = mView.findViewById(R.id.iv3);

        ll_text = mView.findViewById(R.id.ll_text);
        tv_text = LayoutInflater.from(getActivity()).inflate(R.layout.view_benefit_text, null);

        TextView a= tv_text.findViewById(R.id.iv3);
//        ll_text.removeAllViews();
//        tvt = new TextView(getActivity());
//        TextView tvt = tv_text.findViewById(R.id.tv_text);
//        ll_text.addView(tvt);


        TextView name = new TextView(getActivity());
        name.setText("adfadsf");
        name.setTextSize(15);
        name.setTextColor(getResources().getColor(R.color.color_f28146));
        ll_text.addView(name);

        TextView count = new TextView(getActivity());
        count.setText("333333333333");
        count.setTextSize(12);
        name.setTextColor(getResources().getColor(R.color.color_f28146));
        ll_text.addView(count);


        iv_order.setOnClickListener(this);
        iv_mine.setOnClickListener(this);
        iv_sign.setOnClickListener(this);
        ll_owe_address.setOnClickListener(this);
        ll_owe_money.setOnClickListener(this);
        ll_deduct_money.setOnClickListener(this);
        rl_recommend.setOnClickListener(this);
        rl_all.setOnClickListener(this);

        View v_benefit_status_bar = mView.findViewById(R.id.v_benefit_status_bar);
        v_benefit_status_bar.setBackgroundColor(Color.parseColor("#FF898B"));
        setStatusBarHeight(v_benefit_status_bar);

        //推荐item图片宽高
        itemSize = (Utils.getDeviceWith(getActivity()) - Utils.dip2px(getActivity(), (16 + 12 + 8) * 2)) / 3;

        LinearLayout.LayoutParams linearLayout = new LinearLayout.LayoutParams(itemSize, itemSize);
        iv1.setLayoutParams(linearLayout);
        iv2.setLayoutParams(linearLayout);
        iv3.setLayoutParams(linearLayout);
    }

    private void setStatusBarHeight(View tabBarView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(getActivity()));
        tabBarView.setLayoutParams(layoutParams);
    }

    private void initListener() {
        refresh_layout.setOnRefreshListener(() -> {
            refresh_layout.setRefreshing(false);
        });
    }

    @SuppressLint("SetTextI18n")
    public void initData() {
        tv_user_name.setText(mShared.getString(UserAppConst.Colour_NIACKNAME, "") + "您好~");
//        refresh_layout.setRefreshing(false);
    }

    /**
     * banner
     */
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
                GlideImageLoader.loadImageDefaultDisplay(getActivity(), model, itemView, R.drawable.icon_style_one, R.drawable.icon_style_one);
            }
        });
        bga_banner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                if (position >= 0 && position < bannerUrlList.size()) {
                    HomeFuncEntity.ContentBean contentBean = bannerDataBeanList.get(position);
                    LinkParseUtil.parse(getActivity(), contentBean.getRedirect_uri(), "");
                }
            }
        });
        bga_banner.setData(bannerUrlList, null);
        bga_banner.setAutoPlayAble(bannerUrlList.size() > 1);
        bga_banner.setData(bannerUrlList, null);
        bga_banner.startAutoPlay();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_order:
                break;

            case R.id.iv_mine:
                break;

            case R.id.iv_sign://签到
                break;

            case R.id.ll_owe_address://欠费地址
                break;

            case R.id.ll_owe_money://欠费
                break;

            case R.id.ll_deduct_money://缴费抵扣金
                break;

            case R.id.rl_recommend:
                tv_all.setTextColor(getActivity().getResources().getColor(R.color.gray_text_color));
                v_all.setVisibility(View.GONE);
                tv_recommend.setTextColor(getActivity().getResources().getColor(R.color.black_text_color));
                v_recommend.setVisibility(View.VISIBLE);

                break;

            case R.id.rl_all:
                tv_all.setTextColor(getActivity().getResources().getColor(R.color.black_text_color));
                v_all.setVisibility(View.VISIBLE);
                tv_recommend.setTextColor(getActivity().getResources().getColor(R.color.gray_text_color));
                v_recommend.setVisibility(View.GONE);

                break;

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

}
