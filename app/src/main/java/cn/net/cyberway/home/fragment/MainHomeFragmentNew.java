package cn.net.cyberway.home.fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkRequest;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.Constants;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.Util;
import com.ScreenManager;
import com.alibaba.android.vlayout.DelegateAdapter;
import com.alibaba.android.vlayout.VirtualLayoutManager;
import com.alibaba.android.vlayout.layout.GridLayoutHelper;
import com.alibaba.android.vlayout.layout.OnePlusNLayoutHelper;
import com.allapp.model.AllAppModel;
import com.customerInfo.protocol.RealNameTokenEntity;
import com.dashuview.library.keep.ListenerUtils;
import com.dashuview.library.keep.MyListener;
import com.door.entity.SingleCommunityEntity;
import com.door.model.NewDoorModel;
import com.eparking.helper.PermissionUtils;
import com.external.eventbus.EventBus;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GridSpacingItemDecoration;
import com.nohttp.utils.GsonUtils;
import com.tencent.authsdk.AuthConfig;
import com.tencent.authsdk.AuthSDKApi;
import com.tencent.authsdk.IDCardInfo;
import com.tencent.authsdk.callback.IdentityCallback;
import com.tendcloud.tenddata.TCAgent;
import com.tmall.ultraviewpager.UltraViewPager;
import com.umeng.analytics.MobclickAgent;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.model.NewUserModel;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.ServiceInfo;
import com.youmai.hxsdk.db.bean.CacheMsgBean;
import com.youmai.hxsdk.im.IMMsgCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.csh.colourful.life.view.recycleview.WrapHeightLinearLayoutManager;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import cn.net.cyberway.home.activity.BannerVideoActivity;
import cn.net.cyberway.home.adapter.GridLayoutAdapter;
import cn.net.cyberway.home.adapter.HomeApplicationAdapter;
import cn.net.cyberway.home.adapter.HomeCommunityMsgAdapter;
import cn.net.cyberway.home.adapter.HomeDoorAdapter;
import cn.net.cyberway.home.adapter.HomeFunctionAdapter;
import cn.net.cyberway.home.adapter.OnePlusNLayoutAdapter;
import cn.net.cyberway.home.entity.HomeBottomAdviseEntity;
import cn.net.cyberway.home.entity.HomeCommunityMsgEntity;
import cn.net.cyberway.home.entity.HomeFuncEntity;
import cn.net.cyberway.home.entity.HomeHeaderEntity;
import cn.net.cyberway.home.entity.HomeLayoutEntity;
import cn.net.cyberway.home.entity.HomeManagerEntity;
import cn.net.cyberway.home.entity.HomeNotifyEntity;
import cn.net.cyberway.home.entity.HomeRecycleAdapter;
import cn.net.cyberway.home.entity.HomeResourceEntity;
import cn.net.cyberway.home.model.NewHomeModel;
import cn.net.cyberway.home.view.AuthDialog;
import cn.net.cyberway.home.view.TransparentDrawable;
import cn.net.cyberway.utils.BuryingPointUtils;
import cn.net.cyberway.utils.CityCustomConst;
import cn.net.cyberway.utils.CityManager;
import cn.net.cyberway.utils.LekaiHelper;
import cn.net.cyberway.utils.LinkParseUtil;
import q.rorbin.badgeview.QBadgeView;

import static cn.net.cyberway.home.view.HomeViewUtils.addCommmonDoorList;
import static cn.net.cyberway.home.view.HomeViewUtils.getScollYDistance;
import static cn.net.cyberway.home.view.HomeViewUtils.setBadgeCommonPro;
import static cn.net.cyberway.home.view.HomeViewUtils.smoothScrollTop;
import static cn.net.cyberway.utils.TableLayoutUtils.jumpLoginPage;
import static com.user.UserAppConst.COLOR_HOME_USEDOOR;
import static com.youmai.hxsdk.utils.DisplayUtil.getStatusBarHeight;

/**
 * 2017/10/18
 * 新版彩之云5.0新首页
 */
public class MainHomeFragmentNew extends Fragment implements NewHttpResponse, MyListener, View.OnClickListener, IMMsgCallback {

    private View mView;
    private SwipeRefreshLayout refresh_layout;
    private SwipeMenuRecyclerView home_rv;
    private LinearLayout alpha_title_layout;
    private ImageView iv_local_up;
    private TextView alpha_community;
    private ImageView iv_enter_chat;
    private SharedPreferences mShared;
    private SharedPreferences.Editor editor;
    private NewHomeModel newHomeModel;
    private NewDoorModel newDoorModel;
    private NewUserModel newUserModel;
    private String community_name = "";  //小区的名称
    private int customer_id;//用户的id
    private String community_uuid;//当前小区的uuid
    private String tabColor;//背景颜色
    private ConnectivityManager.NetworkCallback networkCallback;  //网络改变的监听
    private ConnectivityManager connectivityManager;
    private int height = 120;
    private String realName = "";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShared = getActivity().getSharedPreferences(UserAppConst.USERINFO, 0);
        editor = mShared.edit();
        newHomeModel = new NewHomeModel(getActivity());
        newDoorModel = new NewDoorModel(getActivity());
        newUserModel = new NewUserModel(getActivity());
        initNetWorkListener();

        LekaiHelper.init(getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home_new_main, container, false);
        initBasicalData();
        initHeaderView();
        loadCacheData();
        EventBus.getDefault().register(this);
        ListenerUtils.setCallBack(this);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart("首页");
        TCAgent.onPageStart(getActivity(), "首页");
        if (isClick) {
            if (!TextUtils.isEmpty(linkUrl) && !linkUrl.startsWith("http")) {
                isClick = false;
                String uploadContent = appCode + BuryingPointUtils.divisionSign + appName + BuryingPointUtils.divisionSign + funCode;
                ((MainActivity) getActivity()).uploadPageStayTime(startTime / 1000, System.currentTimeMillis() / 1000, uploadContent);
            }
        }
        HuxinSdkManager.instance().setImMsgCallback(this);
        showUnReadMsg(HuxinSdkManager.instance().unreadServiceManagerMessage());
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("首页");
        TCAgent.onPageEnd(getActivity(), "首页");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(getActivity())) {
            EventBus.getDefault().unregister(this);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (null != connectivityManager && null != networkCallback) {
                connectivityManager.unregisterNetworkCallback(networkCallback);
            }
        }
        LekaiHelper.stop(getActivity());
    }

    private void initNetWorkListener() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { //5.0以后网络状态改变的监听
            networkCallback = new NetworkCallbackImpl();
            NetworkRequest.Builder builder = new NetworkRequest.Builder();
            NetworkRequest request = builder.build();
            connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            connectivityManager.registerNetworkCallback(request, networkCallback);
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            ((MainActivity) getActivity()).setHomeStyle(tabColor);
        }
    }

    private QBadgeView badgeView;
    private QBadgeView headbadgeView;

    public void showUnReadMsg(int totalUnRead) {
        if (null != iv_enter_chat) {
            if (null == badgeView) {
                badgeView = new QBadgeView(getActivity());
                badgeView.bindTarget(iv_enter_chat);
                badgeView.setBadgeGravity(Gravity.TOP | Gravity.END);
                badgeView.setBadgeTextSize(8f, true);
                badgeView.setBadgeBackgroundColor(ContextCompat.getColor(getActivity(), R.color.hx_color_red_tag));
                badgeView.setShowShadow(false);
            }
            badgeView.setBadgeNumber(totalUnRead);
        }
        if (null != head_enter_chat) {
            if (null == headbadgeView) {
                headbadgeView = new QBadgeView(getActivity());
                headbadgeView.bindTarget(head_enter_chat);
                headbadgeView.setBadgeGravity(Gravity.TOP | Gravity.END);
                headbadgeView.setBadgeTextSize(8f, true);
                headbadgeView.setBadgeBackgroundColor(ContextCompat.getColor(getActivity(), R.color.hx_color_red_tag));
                headbadgeView.setShowShadow(false);
            }
            headbadgeView.setBadgeNumber(totalUnRead);
        }
    }

    private void initBasicalData() {
        community_name = mShared.getString(UserAppConst.Colour_login_community_name, "");
        community_uuid = mShared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
        customer_id = mShared.getInt(UserAppConst.Colour_User_id, 0);
    }

    private View top_view = null;
    private View application_view = null;
    private View manager_view = null;
    private View opendoor_view = null;
    private View notification_view = null;
    private View bga_view = null;
    private ImageView iv_video = null;
    private View activity_view = null;
    private View footer_view = null;
    private HomeRecycleAdapter homeRecycleAdapter = null;

    private void initHeaderView() {
        refresh_layout = mView.findViewById(R.id.refresh_layout);
        refresh_layout.setColorSchemeColors(Color.parseColor("#3290FF"), Color.parseColor("#6ABDF9"));
        home_rv = mView.findViewById(R.id.home_rv);
        View alpha_tabbar_view = mView.findViewById(R.id.alpha_tabbar_view);
        setLinearTabViewHeight(alpha_tabbar_view);
        alpha_title_layout = mView.findViewById(R.id.alpha_title_layout);
        alpha_community = mView.findViewById(R.id.alpha_community);
        iv_enter_chat = mView.findViewById(R.id.iv_enter_chat);
        iv_local_up = mView.findViewById(R.id.iv_local_up);
        iv_enter_chat.setOnClickListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        home_rv.setLayoutManager(linearLayoutManager);// 布局管理器。
        home_rv.loadMoreFinish(true, false);
        homeRecycleAdapter = new HomeRecycleAdapter();
        home_rv.setAdapter(homeRecycleAdapter);
        top_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_home_new_header, null);
        home_rv.addHeaderView(top_view);
        initTopView();
        initHomeClick();
    }


    private RecyclerView rv_application;
    private LinearLayout bind_manager_layout;
    private ImageView iv_manager_photo;
    private TextView tv_manager_name;
    private ImageView iv_call_phone;
    private ImageView iv_chat_msg;
    private LinearLayout open_door_layout;
    private LinearLayout door_root_layout;
    private UltraViewPager viewpage_door;
    private ImageView iv_left_arrow;
    private ImageView iv_right_arrow;
    private RelativeLayout notification_layout;
    private TextView tv_new_message;
    private RecyclerView rv_notification;
    private TextView tv_no_message;
    private View bottom_empty_view;
    private BGABanner bga_banner;
    private RelativeLayout rl_banner;
    private RecyclerView rv_activity;
    private LinearLayout head_layout;
    private ImageView iv_local;
    private TextView tv_show_community;
    private ImageView head_enter_chat;
    private BGABanner head_banner;
    /*有彩住宅的布局*/
    private LinearLayout home_parking_layout;
    private LinearLayout home_period_layout;
    private LinearLayout home_wallet_layout;
    private ImageView iv_frist_tag;
    private ImageView iv_second_tag;
    private ImageView iv_thrid_tag;
    private ImageView iv_forth_tag;
    private ImageView iv_fp_logo;
    private TextView title_return_amount;
    private TextView tv_return_amount;
    private TextView title_return_periods;
    private TextView tv_return_periods;
    private TextView title_meal_amount;
    private TextView tv_meal_amount;
    private TextView title_account_amount;
    private TextView tv_account_amount;
    private View colour_wallet_view;
    private RecyclerView rv_fuction;
    private String colourWalletUrl = "";
    private String colourHomeUrl = "";

    private void setImageLogo(String path, ImageView imageView, int defaultId) {
        if (!TextUtils.isEmpty(path)) {
            GlideImageLoader.loadImageDisplay(getActivity(), path, imageView);
        } else {
            imageView.setImageResource(defaultId);
        }
    }

    private void setTextColor(String colorValue, TextView textView) {
        textView.setTextColor(Color.parseColor(colorValue));
    }

    private void setLinearTabViewHeight(View tabBarView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(getActivity()));
        tabBarView.setLayoutParams(layoutParams);
    }

    private void initTopView() {
        head_layout = top_view.findViewById(R.id.head_layout);
        View head_tabbar_view = top_view.findViewById(R.id.head_tabbar_view);
        setLinearTabViewHeight(head_tabbar_view);
        iv_local = top_view.findViewById(R.id.iv_local);
        tv_show_community = top_view.findViewById(R.id.tv_show_community);
        head_enter_chat = top_view.findViewById(R.id.head_enter_chat);
        head_banner = top_view.findViewById(R.id.head_banner);
        home_parking_layout = top_view.findViewById(R.id.home_parking_layout);
        home_period_layout = top_view.findViewById(R.id.home_period_layout);
        home_wallet_layout = top_view.findViewById(R.id.home_wallet_layout);
        iv_frist_tag = top_view.findViewById(R.id.iv_frist_tag);
        iv_second_tag = top_view.findViewById(R.id.iv_second_tag);
        iv_thrid_tag = top_view.findViewById(R.id.iv_thrid_tag);
        iv_forth_tag = top_view.findViewById(R.id.iv_forth_tag);
        iv_fp_logo = top_view.findViewById(R.id.iv_fp_logo);
        title_return_amount = top_view.findViewById(R.id.title_return_amount);
        title_return_periods = top_view.findViewById(R.id.title_return_periods);
        title_meal_amount = top_view.findViewById(R.id.title_meal_amount);
        title_account_amount = top_view.findViewById(R.id.title_account_amount);
        tv_return_amount = top_view.findViewById(R.id.tv_return_amount);
        tv_return_periods = top_view.findViewById(R.id.tv_return_periods);
        tv_meal_amount = top_view.findViewById(R.id.tv_meal_amount);
        tv_account_amount = top_view.findViewById(R.id.tv_account_amount);
        colour_wallet_view = top_view.findViewById(R.id.colour_wallet_view);
        rv_fuction = top_view.findViewById(R.id.rv_fuction);
        home_wallet_layout.setOnClickListener(this);
        home_period_layout.setOnClickListener(this);
        head_enter_chat.setOnClickListener(this);
    }

    private int themeSuccess = 0;
    private int identity;
    private String return_total_icon;
    private String return_stage_icon;
    private String fp_balance_icon;
    private String lq_balance_icon;
    private int is_holiday;
    private String title_bg;
    private String head_bg;
    private String textColor;
    private String fp_icon;
    private String msg_img;
    private String position_img;

    private void homeHeaderResourceShow(String result) {
        try {
            HomeResourceEntity homeResourceEntity = GsonUtils.gsonToBean(result, HomeResourceEntity.class);
            HomeResourceEntity.ContentBean contentBean = homeResourceEntity.getContent();
            showBuildAndRoom();
            return_total_icon = contentBean.getReturn_total_icon();
            return_stage_icon = contentBean.getReturn_stage_icon();
            fp_balance_icon = contentBean.getFp_balance_icon();
            lq_balance_icon = contentBean.getLq_balance_icon();
            textColor = contentBean.getFont_color();
            is_holiday = contentBean.getIs_holiday();
            title_bg = contentBean.getBackground_img();
            head_bg = contentBean.getBackground_img_3();
            identity = contentBean.getIdentity_type();
            tabColor = contentBean.getTab_color();
            fp_icon = contentBean.getFp_icon();
            msg_img = contentBean.getMsg_img();
            position_img = contentBean.getPosition_img();
            showHomeHeaderResource();
            themeSuccess = 1;
        } catch (Exception e) {
            themeSuccess = 0;
        }
    }

    private void showHomeHeaderResource() {
        setImageLogo(return_total_icon, iv_frist_tag, R.drawable.home_icon_ccw);
        setImageLogo(return_stage_icon, iv_second_tag, R.drawable.home_icon_czz);
        setImageLogo(fp_balance_icon, iv_thrid_tag, R.drawable.home_icon_fp);
        setImageLogo(lq_balance_icon, iv_forth_tag, R.drawable.home_icon_rmb);
        if (TextUtils.isEmpty(tabColor)) {
            if (is_holiday == 1) {
                tabColor = "#D94021";
            } else {
                tabColor = "#ffffff";
            }
        }
        if (TextUtils.isEmpty(textColor)) {
            if (is_holiday == 1) {
                textColor = "#ffffff";
            } else {
                textColor = "#131719";
            }
        }
        setTextColor(textColor, title_return_amount);
        setTextColor(textColor, tv_return_amount);
        setTextColor(textColor, title_return_periods);
        setTextColor(textColor, tv_return_periods);
        setTextColor(textColor, title_meal_amount);
        setTextColor(textColor, tv_meal_amount);
        setTextColor(textColor, title_account_amount);
        setTextColor(textColor, tv_account_amount);
        setTextColor(textColor, tv_show_community);
        setTextColor(textColor, alpha_community);
        if (identity == 3) {
            height = 40;
            LinearLayout.LayoutParams viewLayoutParams = (LinearLayout.LayoutParams) colour_wallet_view.getLayoutParams();
            viewLayoutParams.height = Util.DensityUtil.dip2px(getActivity(), 115.0f) + getStatusBarHeight(getActivity());
            colour_wallet_view.setLayoutParams(viewLayoutParams);
            home_parking_layout.setVisibility(View.GONE);
            head_banner.setVisibility(View.VISIBLE);
            if (null != rl_banner) {
                rl_banner.setVisibility(View.GONE);
            }
        } else {
            height = 110;
            LinearLayout.LayoutParams viewLayoutParams = (LinearLayout.LayoutParams) colour_wallet_view.getLayoutParams();
            viewLayoutParams.height = Util.DensityUtil.dip2px(getActivity(), 55.0f) + getStatusBarHeight(getActivity());
            colour_wallet_view.setLayoutParams(viewLayoutParams);
            home_parking_layout.setVisibility(View.VISIBLE);
            head_banner.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(title_bg)) {  //c12832
            alpha_title_layout.setAlpha(0);
//            ((MainActivity) getActivity()).setHomeStyle(tabColor);
            alpha_title_layout.setBackgroundColor(Color.parseColor("#00000000"));
            GlideImageLoader.loadStableHeightLayouBg(getActivity(), title_bg, alpha_title_layout);
        } else {
            alpha_title_layout.setBackground(new TransparentDrawable());
            alpha_title_layout.setBackgroundColor(Color.parseColor("#ffffff"));
//            ((MainActivity) getActivity()).setHomeStyle("#ffffff");
        }
        if (!TextUtils.isEmpty(head_bg)) {
            GlideImageLoader.loadWrapHeightLayouBg(getActivity(), head_bg, head_layout);
        } else {
            FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) head_layout.getLayoutParams();
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT;
            head_layout.setLayoutParams(layoutParams);
            head_layout.setBackgroundColor(Color.parseColor("#ffffff"));
        }
        GlideImageLoader.loadImageDefaultDisplay(getActivity(), fp_icon, iv_fp_logo, R.drawable.home_white_meal, R.drawable.home_white_meal);
        if (!TextUtils.isEmpty(msg_img)) {
            GlideImageLoader.loadImageDefaultDisplay(getActivity(), msg_img, head_enter_chat, R.drawable.home_msg_chat, R.drawable.home_msg_chat);
            GlideImageLoader.loadImageDefaultDisplay(getActivity(), msg_img, iv_enter_chat, R.drawable.home_msg_chat, R.drawable.home_msg_chat);
        }
        if (!TextUtils.isEmpty(position_img)) {
            GlideImageLoader.loadImageDefaultDisplay(getActivity(), position_img, iv_local, R.drawable.icon_local, R.drawable.icon_local);
            GlideImageLoader.loadImageDefaultDisplay(getActivity(), position_img, iv_local_up, R.drawable.icon_local, R.drawable.icon_local);
        }
    }

    private void homeHeaderOldResourceShow(String result) {
        HomeHeaderEntity homeHeaderEntity = GsonUtils.gsonToBean(result, HomeHeaderEntity.class);
        HomeHeaderEntity.ContentBean contentBean = homeHeaderEntity.getContent();
        return_total_icon = contentBean.getReturn_total_icon();
        return_stage_icon = contentBean.getReturn_stage_icon();
        fp_balance_icon = contentBean.getFp_balance_icon();
        lq_balance_icon = contentBean.getLq_balance_icon();
        textColor = contentBean.getFont_color();
        is_holiday = contentBean.getIs_holiday();
        title_bg = contentBean.getBackground_img();
        head_bg = contentBean.getBackground_img_3();
        identity = contentBean.getIdentity_type();
        tabColor = contentBean.getTab_color();
        fp_icon = contentBean.getFp_icon();
        if (themeSuccess == 0) {
            showHomeHeaderResource();
        }
    }

    //显示头部彩钱包金额和彩车位等信息
    private void homeHeaderShow(String result) {
        try {
            HomeHeaderEntity homeHeaderEntity = GsonUtils.gsonToBean(result, HomeHeaderEntity.class);
            HomeHeaderEntity.ContentBean contentBean = homeHeaderEntity.getContent();
            colourWalletUrl = contentBean.getIcon_redirect_01();
            colourHomeUrl = contentBean.getIcon_redirect_02();
            if (TextUtils.isEmpty(contentBean.getReturn_total())) {
                tv_return_amount.setText("0.00");
            } else {
                tv_return_amount.setText(contentBean.getReturn_total());
            }
            if (TextUtils.isEmpty(contentBean.getReturn_stage())) {
                tv_return_periods.setText("0/0");
            } else {
                tv_return_periods.setText(contentBean.getReturn_stage());
            }
            tv_meal_amount.setText(contentBean.getFp_balance());
            tv_account_amount.setText(contentBean.getLq_balance());
            showBuildAndRoom();
            homeHeaderOldResourceShow(result);
        } catch (Exception e) {
            editor.putString(UserAppConst.COLOR_HOME_HEADER, "").apply();
        }
    }

    private ArrayList<HomeFuncEntity.ContentBean> funcDataBeanList = new ArrayList<HomeFuncEntity.ContentBean>();
    private HomeFunctionAdapter homeFunctionAdapter = null;
    private HomeFuncEntity.ContentBean homeFuncBean;
    private boolean fromFunc = false;

    //中间的四宫格模块
    private void homeFuncShow(String result) {
        try {
            HomeFuncEntity homeFuncEntity = GsonUtils.gsonToBean(result, HomeFuncEntity.class);
            funcDataBeanList.clear();
            funcDataBeanList.addAll(homeFuncEntity.getContent());
            if (homeFunctionAdapter == null) {
                homeFunctionAdapter = new HomeFunctionAdapter(getActivity(), funcDataBeanList);
                rv_fuction.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
                rv_fuction.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dip2px(getActivity(), 2), false));
                rv_fuction.setNestedScrollingEnabled(false);
                rv_fuction.setAdapter(homeFunctionAdapter);
            } else {
                homeFunctionAdapter.notifyDataSetChanged();
            }
            homeFunctionAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    if (i >= 0) {
                        fromFunc = true;
                        homeFuncBean = funcDataBeanList.get(i);
                        String realName = mShared.getString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "");
                        boolean isRealName = !TextUtils.isEmpty(realName);
                        if ("1".equals(homeFuncBean.getIs_auth()) && !isRealName) {//是否需要认证 1：需要实名，2：不需要实名
                            needAuth();
                        } else {
                            authItemClick(fromFunc, homeFuncBean);
                        }
                    }
                }
            });
        } catch (Exception e) {
            editor.putString(UserAppConst.COLOR_HOME_FUNCTION, "").apply();
        }
    }

    /**
     * 需要实名认证的item
     * fromFunc true：从func进入 false：从app进入
     */
    private void authItemClick(boolean fromFunc, HomeFuncEntity.ContentBean dataBean) {
        if (fromFunc) {
            String redirect_uri = dataBean.getRedirect_uri();
            funCode = BuryingPointUtils.homeFuncCode;
            initUploadData(dataBean);
            if (TextUtils.isEmpty(appCode)) {
                LinkParseUtil.parse(getActivity(), redirect_uri, dataBean.getName());
            } else {
                LinkParseUtil.parse(getActivity(), redirect_uri, appCode + BuryingPointUtils.divisionSign + appName + BuryingPointUtils.divisionSign + funCode);
            }
            uploadAppClickLister(dataBean.getResource_id());
        } else {
            String redirect_uri = dataBean.getRedirect_uri();
            initUploadData(dataBean);
            funCode = BuryingPointUtils.homeRecentlyCode;
            if (TextUtils.isEmpty(appCode)) {
                LinkParseUtil.parse(getActivity(), redirect_uri, dataBean.getName());
            } else {
                LinkParseUtil.parse(getActivity(), redirect_uri, appCode + BuryingPointUtils.divisionSign + appName + BuryingPointUtils.divisionSign + funCode);
            }
            String resource_id = dataBean.getResource_id();
            if (!TextUtils.isEmpty(resource_id)) {
                uploadAppClickLister(resource_id);
                updateRecentApp();
            }
        }
    }

    private void initUploadData(Object object) {
        if (object instanceof HomeFuncEntity.ContentBean) {
            HomeFuncEntity.ContentBean dataBean = (HomeFuncEntity.ContentBean) object;
            appCode = dataBean.getApp_code();
            appName = dataBean.getName();
            linkUrl = dataBean.getRedirect_uri();
            startTime = System.currentTimeMillis();
            isClick = true;
        } else if (object instanceof HomeNotifyEntity.ContentBean) {
            HomeNotifyEntity.ContentBean contentBean = (HomeNotifyEntity.ContentBean) object;
            appCode = contentBean.getApp_code();
            appName = contentBean.getApp_name();
            linkUrl = contentBean.getMsg_url();
            startTime = System.currentTimeMillis();
            isClick = true;
        }
    }

    private String appCode;
    private String appName;
    private String funCode;
    private String linkUrl;
    private long startTime;
    private boolean isClick = false;

    private ArrayList<HomeFuncEntity.ContentBean> appDataBeanList = new ArrayList<HomeFuncEntity.ContentBean>();
    private HomeApplicationAdapter homeAppAdapter = null;
    private HomeFuncEntity.ContentBean appBean = null;

    //常用应用模块
    private void homeApplicationShow(String result) {
        try {
            HomeFuncEntity homeFuncEntity = GsonUtils.gsonToBean(result, HomeFuncEntity.class);
            appDataBeanList.clear();
            appDataBeanList.addAll(homeFuncEntity.getContent());
            if (null == homeAppAdapter) {
                homeAppAdapter = new HomeApplicationAdapter(getActivity(), appDataBeanList);
                rv_application.setLayoutManager(new LinearLayoutManager(getActivity(), GridLayoutManager.HORIZONTAL, false));
                rv_application.setAdapter(homeAppAdapter);
            } else {
                homeAppAdapter.notifyDataSetChanged();
            }
            homeAppAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    if (i >= 0) {
                        fromFunc = false;
                        appBean = appDataBeanList.get(i);
                        String realName = mShared.getString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "");
                        boolean isRealName = !TextUtils.isEmpty(realName);
                        if ("1".equals(appBean.getIs_auth()) && !isRealName) {//是否需要认证 1：需要实名，2：不需要实名
                            needAuth();
                        } else {
                            authItemClick(fromFunc, appBean);
                        }
                    }
                }
            });
        } catch (Exception e) {
            editor.putString(UserAppConst.COLOR_HOME_APPLICATION, "").apply();
        }
    }

    private void uploadAppClickLister(String appId) {
        AllAppModel allAppModel = new AllAppModel(getActivity());
        allAppModel.uploadAppClick(10, appId, this);
    }


    private ArrayList<HomeCommunityMsgEntity.ContentBean.DataBean> msgDataBeanList = new ArrayList<>();
    private int unRead = 0;
    private HomeCommunityMsgAdapter homeCommunityMsgAdapter;

    /***消息通知模块***/
    private void homeNotificationShow(String result) {
        try {
            HomeCommunityMsgEntity homeCommunityMsgEntity = GsonUtils.gsonToBean(result, HomeCommunityMsgEntity.class);
            HomeCommunityMsgEntity.ContentBean contentBean = homeCommunityMsgEntity.getContent();
            unRead = contentBean.getUnread();
            setUnReadShow();
            msgDataBeanList.clear();
            msgDataBeanList.addAll(contentBean.getData());
            if (msgDataBeanList.size() == 0) {
                notification_layout.setVisibility(View.GONE);
                bottom_empty_view.setVisibility(View.GONE);
            } else {
                notification_layout.setVisibility(View.VISIBLE);
                bottom_empty_view.setVisibility(View.VISIBLE);
            }
            if (null == homeCommunityMsgAdapter) {
                homeCommunityMsgAdapter = new HomeCommunityMsgAdapter(getActivity(), msgDataBeanList);
                rv_notification.setLayoutManager(new WrapHeightLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                rv_notification.setNestedScrollingEnabled(false);
                rv_notification.setAdapter(homeCommunityMsgAdapter);
            } else {
                homeCommunityMsgAdapter.notifyDataSetChanged();
            }
            homeCommunityMsgAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    if (i >= 0) {
                        HomeCommunityMsgEntity.ContentBean.DataBean dataBean = msgDataBeanList.get(i);
                        String linkUrl = dataBean.getLink_url();
                        if (TextUtils.isEmpty(linkUrl)) {
                            LinkParseUtil.parse(getActivity(), "colourlife://proto?type=notificationList", "");
                        } else {
                            LinkParseUtil.parse(getActivity(), linkUrl, dataBean.getMsg_title());
                        }
                        String is_read = dataBean.getIs_read();
                        if (!"1".equals(is_read)) {
                            dataBean.setIs_read("1");
                            unRead--;
                            setUnReadShow();
                            homeCommunityMsgAdapter.notifyDataSetChanged();
                            newHomeModel.setInstantMsgRead(8, dataBean.getMsg_id(), MainHomeFragmentNew.this);
                        }
                    }

                }
            });
        } catch (Exception e) {
            editor.putString(UserAppConst.COLOR_HOME_NOTIFICATION, "").apply();
        }
    }

    private QBadgeView number_bagdeView = null;

    private void setUnReadShow() {
        if (unRead > 0) {
            if (number_bagdeView == null) {
                number_bagdeView = new QBadgeView(getActivity());
                setBadgeCommonPro(getActivity(), number_bagdeView, tv_new_message);
            }
            number_bagdeView.setVisibility(View.VISIBLE);
            number_bagdeView.setBadgeNumber(unRead);
        } else {
            if (null != number_bagdeView) {
                number_bagdeView.setVisibility(View.GONE);
            }
        }
    }

    private void setMessageRead() {
        for (HomeCommunityMsgEntity.ContentBean.DataBean dataBean : msgDataBeanList) {
            dataBean.setIs_read("1");
        }
        unRead = 0;
        setUnReadShow();
        if (null != homeCommunityMsgAdapter) {
            homeCommunityMsgAdapter.notifyDataSetChanged();
        }
    }


    private void adversizeBannerShow() {
        head_banner.setAdapter(new BGABanner.Adapter<ImageView, String>() {
            @Override
            public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
                GlideImageLoader.loadImageDefaultDisplay(getActivity(), model, itemView, R.drawable.icon_style_one, R.drawable.icon_style_one);
            }
        });
        head_banner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
            @Override
            public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                if (position >= 0 && position < bannerUrlList.size()) {
                    HomeFuncEntity.ContentBean contentBean = bannerDataBeanList.get(position);
                    initUploadData(contentBean);
                    funCode = BuryingPointUtils.homeBannnerCode;
                    if (TextUtils.isEmpty(appCode)) {
                        LinkParseUtil.parse(getActivity(), contentBean.getRedirect_uri(), appName);
                    } else {
                        if ("2".equals(contentBean.getType())) {//type 2视频 1连接
                            try {
                                Intent intent = new Intent(getActivity(), BannerVideoActivity.class);
                                intent.putExtra(BannerVideoActivity.NUM, contentBean.getFilesize());
                                intent.putExtra(BannerVideoActivity.URI, contentBean.getRedirect_uri());
                                startActivity(intent);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        } else {
                            LinkParseUtil.parse(getActivity(), contentBean.getRedirect_uri(), appCode + BuryingPointUtils.divisionSign + appName + BuryingPointUtils.divisionSign + funCode);
                        }
                    }
                }
            }
        });
        head_banner.setData(bannerUrlList, null);
        head_banner.setAutoPlayInterval(3000);
        head_banner.setAutoPlayAble(bannerUrlList.size() > 1);
        head_banner.setData(bannerUrlList, null);
        head_banner.startAutoPlay();
        //显示banner视频播放logo
        if (bannerDataBeanList.size() > 0) {
            iv_video.setVisibility("2".equals(bannerDataBeanList.get(0).getType()) ? View.VISIBLE : View.GONE);
        }
        head_banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {//添加视频播放logo
                try {
                    if (bannerDataBeanList.size() > position) {
                        HomeFuncEntity.ContentBean bean;
                        bean = bannerDataBeanList.get(position);
                        if ("2".equals(bean.getType())) {
                            iv_video.setVisibility(View.VISIBLE);
                        } else {
                            iv_video.setVisibility(View.GONE);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    private ArrayList<HomeFuncEntity.ContentBean> bannerDataBeanList = new ArrayList<HomeFuncEntity.ContentBean>();
    private List<String> bannerUrlList = new ArrayList<>();

    /****广告banner****/
    private void homeBannerShow(String result) {
        try {
            HomeFuncEntity homeFuncEntity = GsonUtils.gsonToBean(result, HomeFuncEntity.class);
            bannerDataBeanList.clear();
            bannerDataBeanList.addAll(homeFuncEntity.getContent());
            bannerUrlList.clear();
            for (HomeFuncEntity.ContentBean dataBean : bannerDataBeanList) {
                bannerUrlList.add(dataBean.getImg());
            }
            if (identity == 3) {
                rl_banner.setVisibility(View.GONE);
                head_banner.setVisibility(View.VISIBLE);
                adversizeBannerShow();
            } else {
                rl_banner.setVisibility(View.VISIBLE);
                head_banner.setVisibility(View.GONE);
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
                            initUploadData(contentBean);
                            funCode = BuryingPointUtils.homeBannnerCode;
                            if (TextUtils.isEmpty(appCode)) {
                                LinkParseUtil.parse(getActivity(), contentBean.getRedirect_uri(), appName);
                            } else {
                                if ("2".equals(contentBean.getType())) {//type 2视频 1连接
                                    try {
                                        Intent intent = new Intent(getActivity(), BannerVideoActivity.class);
                                        intent.putExtra(BannerVideoActivity.NUM, contentBean.getFilesize());
                                        intent.putExtra(BannerVideoActivity.URI, contentBean.getRedirect_uri());
                                        startActivity(intent);
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                } else {
                                    LinkParseUtil.parse(getActivity(), contentBean.getRedirect_uri(), appCode + BuryingPointUtils.divisionSign + appName + BuryingPointUtils.divisionSign + funCode);
                                }
                            }
                        }
                    }
                });
                bga_banner.setData(bannerUrlList, null);
                bga_banner.setAutoPlayInterval(3000);
                bga_banner.setAutoPlayAble(bannerUrlList.size() > 1);
                bga_banner.setData(bannerUrlList, null);
                bga_banner.startAutoPlay();
                //显示banner视频播放logo
                if (bannerDataBeanList.size() > 0) {
                    iv_video.setVisibility("2".equals(bannerDataBeanList.get(0).getType()) ? View.VISIBLE : View.GONE);
                }
                bga_banner.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                    }

                    @Override
                    public void onPageSelected(int position) {//添加视频播放logo
                        try {
                            if (bannerDataBeanList.size() > position) {
                                HomeFuncEntity.ContentBean bean;
                                bean = bannerDataBeanList.get(position);
                                if ("2".equals(bean.getType())) {
                                    iv_video.setVisibility(View.VISIBLE);
                                } else {
                                    iv_video.setVisibility(View.GONE);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {
                    }
                });
            }
        } catch (Exception e) {

        }
    }

    private List<HomeBottomAdviseEntity.ContentBean> homeBottomList = new ArrayList<>();

    private DelegateAdapter delegateAdapter;
    final List<DelegateAdapter.Adapter> adapters = new LinkedList<>();

    /***底部彩住宅,彩惠***/
    private void homeActivityShow(String result) {
        try {
            HomeBottomAdviseEntity homeBottomAdviseEntity = GsonUtils.gsonToBean(result, HomeBottomAdviseEntity.class);
            homeBottomList.clear();
            adapters.clear();
            homeBottomList.addAll(homeBottomAdviseEntity.getContent());
            RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool();
            rv_activity.setRecycledViewPool(viewPool);
            viewPool.setMaxRecycledViews(0, 10);
            if (homeBottomList.size() == 3) {
                adapters.add(initOnePlusNLayout(getActivity()));
            } else {
                adapters.add(initLinerLayout(getActivity()));
            }
            VirtualLayoutManager manager = new VirtualLayoutManager(getActivity());
            rv_activity.setLayoutManager(manager);
            delegateAdapter = new DelegateAdapter(manager);
            delegateAdapter.setAdapters(adapters);
            rv_activity.setAdapter(delegateAdapter);
        } catch (Exception e) {
            editor.putString(UserAppConst.COLOR_HOME_ACTIVITY, "").apply();
        }
    }


    public GridLayoutAdapter initLinerLayout(Context context) {
        GridLayoutHelper gridLayoutHelper = new GridLayoutHelper(2);
        //设置间隔高度
        gridLayoutHelper.setAutoExpand(true);
        //设置布局底部与下个布局的间隔
        //设置间距
        gridLayoutHelper.setMargin(10, 10, 10, 10);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        GridLayoutAdapter delegateRecyclerAdapter = new GridLayoutAdapter(context, gridLayoutHelper, homeBottomList);
        return delegateRecyclerAdapter;
    }

    public OnePlusNLayoutAdapter initOnePlusNLayout(Context context) {
        OnePlusNLayoutHelper onePlusNLayoutHelper = new OnePlusNLayoutHelper();
        //设置布局底部与下个布局的间隔
        onePlusNLayoutHelper.setMargin(10, 10, 10, 10);// 设置LayoutHelper的子元素相对LayoutHelper边缘的距离
        OnePlusNLayoutAdapter onePlusNLayoutAdapter = new OnePlusNLayoutAdapter(context, onePlusNLayoutHelper, homeBottomList);
        return onePlusNLayoutAdapter;
    }

    private ArrayList<SingleCommunityEntity.ContentBean.CommonUseBean> commonUseBeanList = new ArrayList<>();
    private int useDoorSize = 0;


    /***快捷开门模块**/
    private void homeDoorShow(String result) {
        SingleCommunityEntity singleCommunityEntity = GsonUtils.gsonToBean(result, SingleCommunityEntity.class);
        try {
            commonUseBeanList.clear();
            SingleCommunityEntity.ContentBean contentBean = singleCommunityEntity.getContent();
            commonUseBeanList.addAll(contentBean.getCommon_use());
            useDoorSize = commonUseBeanList.size();
            if (useDoorSize == 0) {
                commonUseBeanList.addAll(addCommmonDoorList(contentBean));
            }
            useDoorSize = commonUseBeanList.size();
            if (useDoorSize == 0) {
                SingleCommunityEntity.ContentBean.CommonUseBean singleCommonUse = new SingleCommunityEntity.ContentBean.CommonUseBean();
                useDoorSize = commonUseBeanList.size();
                singleCommonUse.setDoor_name("申请门禁");
                commonUseBeanList.add(singleCommonUse);
            }
            updateDoorView();
        } catch (Exception e) {
            editor.putString(UserAppConst.COLOR_HOME_USEDOOR, "").apply();
            showExceptionDoorData();
        }
    }

    private void updateDoorView() {
        useDoorSize = commonUseBeanList.size();
        viewpage_door.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        if (viewpage_door.getAdapter() != null) {
            viewpage_door.refresh();
        }
        HomeDoorAdapter homeDoorAdapter = new HomeDoorAdapter(commonUseBeanList, false, true);
        viewpage_door.setAdapter(homeDoorAdapter);
        viewpage_door.refresh();
    }

    private int choiceIndex = 0;

    private String managerPhone;
    private String managerLink;
    private int bindStatus;

    /****客户经理模块***/
    private void homeManagerShow(String result) {
        try {
            HomeManagerEntity homeManagerEntity = GsonUtils.gsonToBean(result, HomeManagerEntity.class);
            if (null != homeManagerEntity) {
                HomeManagerEntity.ContentBean contentBean = homeManagerEntity.getContent();
                String avatar = contentBean.getAvatar();
                GlideImageLoader.loadImageDefaultDisplay(getActivity(), avatar, iv_manager_photo, R.drawable.home_guanjia, R.drawable.home_guanjia);
                bindStatus = contentBean.getBind_state();
                int isShow = contentBean.getIs_show();
                managerPhone = contentBean.getMobile();
                managerLink = contentBean.getLink();
                if (bindStatus == 1) {
                    bind_manager_layout.setVisibility(View.VISIBLE);
                    String username = contentBean.getUsername();
                    ServiceInfo serviceInfo = new ServiceInfo();
                    if (TextUtils.isEmpty(avatar)) {
                        serviceInfo.setAvatar(" https://cc.colourlife.com/common/v30/logo/app_logo_v30.png");
                    } else {
                        serviceInfo.setAvatar(avatar);
                    }
                    serviceInfo.setSex(contentBean.getSex());
                    serviceInfo.setUuid(contentBean.getUuid());
                    serviceInfo.setPhoneNum(managerPhone);
                    String realName = contentBean.getRealname();
                    String oa = contentBean.getOa();
                    if (TextUtils.isEmpty(realName)) {
                        serviceInfo.setRealName(username);
                        tv_manager_name.setText(username);
                    } else {
                        serviceInfo.setRealName(realName);
                        tv_manager_name.setText(realName);
                    }
                    serviceInfo.setNickName(contentBean.getNickname());
                    if (TextUtils.isEmpty(oa)) {
                        serviceInfo.setUserName(username);
                    } else {
                        serviceInfo.setUserName(oa);
                    }
                    HuxinSdkManager.instance().setServiceInfo(serviceInfo);
                } else {
                    if (isShow == 1) {
                        bind_manager_layout.setVisibility(View.VISIBLE);
                        tv_manager_name.setText("未绑定");
                    } else {
                        bind_manager_layout.setVisibility(View.GONE);
                    }
                }
            }
        } catch (Exception e) {
            editor.putString(UserAppConst.COLOR_HOME_MANAGER, "").apply();
        }
    }

    private void loadCacheData() {
        String homeLayoutCache = mShared.getString(UserAppConst.COLOR_HOME_LAYOUT, "");
        if (!TextUtils.isEmpty(homeLayoutCache)) {
            showHomeLayout(homeLayoutCache, true);
        }
        String homeResourceCache = mShared.getString(UserAppConst.COLOR_HOME_RESOURCE, "");
        String homeBalanceCache = mShared.getString(UserAppConst.COLOR_HOME_HEADER, "");
        if (!TextUtils.isEmpty(homeResourceCache)) {
            homeHeaderResourceShow(homeResourceCache);
            if (identity == 3) {
                String homeBannerCache = mShared.getString(UserAppConst.COLOR_HOME_BANNER, "");
                homeBannerShow(homeBannerCache);
            }
        } else {
            if (!TextUtils.isEmpty(homeBalanceCache)) {
                homeHeaderOldResourceShow(homeBalanceCache);
            }
        }
        if (!TextUtils.isEmpty(homeBalanceCache)) {
            homeHeaderShow(homeBalanceCache);
        } else {
            refresh_layout.setEnabled(true);
            refresh_layout.setRefreshing(true);
        }
        String homeFunctionCache = mShared.getString(UserAppConst.COLOR_HOME_FUNCTION, "");
        if (TextUtils.isEmpty(homeFunctionCache)) {
            homeFunctionCache = Constants.defaultHomeFunc;
        }
        homeFuncShow(homeFunctionCache);
        getFristData();
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    getLayoutAndFuncModel();
                    break;
                case 1:
                    getUserBalance();
                    break;
                case 2:
                    needRequestNumber++;
                    updateRecentApp();
                    break;
                case 3:
                    needRequestNumber++;
                    getCommonDoorData();
                    break;
                case 4:
                    needRequestNumber++;
                    getNotification();
                    break;
                case 5:
                    needRequestNumber++;
                    newHomeModel.getHomeModelManager(5, MainHomeFragmentNew.this);
                    break;
                case 6:
                    needRequestNumber++;
                    newHomeModel.getHomeModelBanner(6, MainHomeFragmentNew.this);
                    break;
                case 7:
                    needRequestNumber++;
                    newHomeModel.getHomeNewAdMsgActivity(7, MainHomeFragmentNew.this);
                    break;
                case 12:
                    newUserModel.getIsRealName(12, MainHomeFragmentNew.this);
                    break;
                case 14:
                    newUserModel.getLekaiDoor(14, MainHomeFragmentNew.this);
                    break;
            }
        }
    };

    /***获取layout模块和四宫格数据**/
    private void getLayoutAndFuncModel() {
        newHomeModel.getHomeLayout(-1, MainHomeFragmentNew.this);
        newHomeModel.getHomeModuleFunc(1, MainHomeFragmentNew.this);
    }

    private void getUserBalance() {
        newHomeModel.getHomeThemeLayout(8, MainHomeFragmentNew.this);
        newHomeModel.getHomeModuleHeader(0, MainHomeFragmentNew.this);
    }

    public void updateRecentApp() {
        newHomeModel.getHomeModelApp(2, MainHomeFragmentNew.this);
    }

    private void getCommonDoorData() {
        newDoorModel.getSingleCommunityList(3, community_uuid, false, MainHomeFragmentNew.this);
    }

    private void getNotification() {
        newHomeModel.getHomeModelNotification(4, MainHomeFragmentNew.this);
    }

    private void getFristData() {
        if (Util.isGps(Objects.requireNonNull(getActivity()))) {
            CityManager.getInstance(getActivity()).initLocation();
        } else if (TextUtils.isEmpty(community_name)) {
            ToastUtil.toastShow(getActivity(), "未能获取当前定位");
        }
        handler.sendEmptyMessageDelayed(0, 500);
        handler.sendEmptyMessageDelayed(1, 1000);
        handler.sendEmptyMessageDelayed(14, 100);
        String realName = mShared.getString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "");
        if (TextUtils.isEmpty(realName)) {
            handler.sendEmptyMessageDelayed(12, 5000);
        }
    }

    private void showBuildAndRoom() {
        if (Util.isGps(Objects.requireNonNull(getActivity()))) {
            community_name = mShared.getString(CityCustomConst.LOCATION_CITY, "");
            String address_street = mShared.getString(CityCustomConst.LOCATION_HOME, "");
            if (!TextUtils.isEmpty(address_street)) {
                tv_show_community.setText(address_street);//显示定位 如龙华区民治路
                alpha_community.setText(address_street);//显示定位
            } else if (!TextUtils.isEmpty(community_name)) {
                tv_show_community.setText(community_name);
                alpha_community.setText(community_name);
            } else {
                tv_show_community.setText("无法获取当前位置");
                alpha_community.setText("无法获取当前位置");
                CityManager.getInstance(getActivity()).initLocation();
            }
        } else {
            tv_show_community.setText("无法获取当前位置");
            alpha_community.setText("无法获取当前位置");
        }
    }

    private void showUserData() {
        initBasicalData();
        ((MainActivity) getActivity()).configPushMessage();
        showBuildAndRoom();
    }

    private void refreshData() {
        needRequestNumber = 4;
        questReturnNumber = 0;
        refresh_layout.setEnabled(true);
        refresh_layout.setRefreshing(true);
        getFristData();
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.SIGN_IN_SUCCESS:
                showUserData();
                if (null != home_rv) {
                    refreshData();
                }
                break;
            case UserMessageConstant.CHANGE_COMMUNITY:
                showUserData();
                refreshData();
                HuxinSdkManager.instance().reqModifyOrgName(community_name);
                ((MainActivity) getActivity()).getChangeCommunityTheme();
                break;
            case UserMessageConstant.LOGOUT:
                ((MainActivity) getActivity()).clearUserData("");
                smoothScrollTop(home_rv);
                jumpLoginPage(getActivity(), mShared, 1000);
                break;
            case UserMessageConstant.SQUEEZE_OUT:
            case UserMessageConstant.AUDIT_PASS_OUT:
            case UserMessageConstant.WEB_OUT:
                ScreenManager.getScreenManager().popAllActivityExceptOne(MainActivity.class);
                jumpLoginPage(getActivity(), mShared, 1000);
                String notice = message.obj.toString();
                if (TextUtils.isEmpty(notice)) {
                    notice = "审核已通过，请使用新手机号登录";
                }
                ToastUtil.toastTime(getActivity().getApplicationContext(), notice, 5000);
                smoothScrollTop(home_rv);
                ((MainActivity) getActivity()).clearUserData("");
                break;
            case UserMessageConstant.APP_INSTANCE_MSG:
                if (null != notification_view) {
                    getNotification();
                }
                break;
            case UserMessageConstant.SUREBTNCHECKET:
                getUserBalance();
                break;
            case UserMessageConstant.UPDATE_APP_CLICK:
                updateRecentApp();
                break;
        }
    }

    private int questReturnNumber = 0; //请求接口回调的计数器
    private int needRequestNumber = 4; //layout接口 余额接口  四宫格模块的接口  资源接口

    /**
     * 请求结束停止刷新
     **/
    private void stopRefresh() {
        if (questReturnNumber >= needRequestNumber) {
            refresh_layout.setRefreshing(false);
            needRequestNumber = 4;
            questReturnNumber = 0;
        }
        smoothScrollTop(home_rv);
    }

    private void showHomeLayout(String layoutResult, boolean loadCacheData) {
        try {
            HomeLayoutEntity homeLayoutEntity = GsonUtils.gsonToBean(layoutResult, HomeLayoutEntity.class);
            List<HomeLayoutEntity.ContentBean> contentBeanList = homeLayoutEntity.getContent();
            if (null != contentBeanList) {
                for (HomeLayoutEntity.ContentBean contentBean : contentBeanList) {
                    int app_code = contentBean.getApp_code();
                    int is_show = contentBean.getIs_show();
                    switch (app_code) {
                        case 1000: //头部饭票权限加四宫格

                            break;
                        case 1001: //常用应用模块
                            if (null == application_view) {
                                application_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_normalapp_view, null);
                                home_rv.addHeaderView(application_view);
                                rv_application = application_view.findViewById(R.id.rv_application);
                            }
                            if (is_show == 1) {
                                String homeAppCache = mShared.getString(UserAppConst.COLOR_HOME_APPLICATION, "");
                                if (!TextUtils.isEmpty(homeAppCache) && loadCacheData) {
                                    homeApplicationShow(homeAppCache);
                                }
                                handler.sendEmptyMessageDelayed(2, 2000);
                            } else {
                                if (null != homeAppAdapter) {
                                    appDataBeanList.clear();
                                    homeAppAdapter.notifyDataSetChanged();
                                }
                            }
                            break;
                        case 1002: //门禁模块
                            if (null == opendoor_view) {
                                opendoor_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_opendoor_view, null);
                                home_rv.addHeaderView(opendoor_view);
                                open_door_layout = opendoor_view.findViewById(R.id.open_door_layout);
                                door_root_layout = opendoor_view.findViewById(R.id.door_root_layout);
                                viewpage_door = opendoor_view.findViewById(R.id.viewpage_door);
                                iv_left_arrow = opendoor_view.findViewById(R.id.iv_left_arrow);
                                iv_right_arrow = opendoor_view.findViewById(R.id.iv_right_arrow);
                                iv_left_arrow.setOnClickListener(this);
                                iv_right_arrow.setOnClickListener(this);
                                open_door_layout.setOnClickListener(this);
                            }
                            if (is_show == 1) {
                                door_root_layout.setVisibility(View.VISIBLE);
                                String homeUseDoorCache = mShared.getString(COLOR_HOME_USEDOOR, "");
                                if (!TextUtils.isEmpty(homeUseDoorCache) && loadCacheData) {
                                    homeDoorShow(homeUseDoorCache);
                                }
                                handler.sendEmptyMessageDelayed(3, 2000);
                            } else {
                                door_root_layout.setVisibility(View.GONE);
                            }
                            break;
                        case 1003://消息通知模块
                            if (null == notification_view) {
                                notification_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_notification_view, null);
                                home_rv.addHeaderView(notification_view);
                                notification_layout = notification_view.findViewById(R.id.notification_layout);
                                tv_new_message = notification_view.findViewById(R.id.tv_new_message);
                                rv_notification = notification_view.findViewById(R.id.rv_notification);
                                tv_no_message = notification_view.findViewById(R.id.tv_no_message);
                                bottom_empty_view = notification_view.findViewById(R.id.bottom_empty_view);
                                notification_layout.setOnClickListener(this);
                            }
                            if (is_show == 1) {
                                String homeNotifiactionCache = mShared.getString(UserAppConst.COLOR_HOME_NOTIFICATION, "");
                                if (!TextUtils.isEmpty(homeNotifiactionCache) && loadCacheData) {
                                    homeNotificationShow(homeNotifiactionCache);
                                }
                                handler.sendEmptyMessageDelayed(4, 4000);
                            } else {
                                // 处理数据源;
                                notification_layout.setVisibility(View.GONE);
                                tv_no_message.setVisibility(View.GONE);
                                bottom_empty_view.setVisibility(View.GONE);
                                if (null != homeCommunityMsgAdapter) {
                                    msgDataBeanList.clear();
                                    homeCommunityMsgAdapter.notifyDataSetChanged();
                                }
                            }
                            break;
                        case 1004://客户经理模块
                            if (null == manager_view) {
                                manager_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_manager_view, null);
                                home_rv.addHeaderView(manager_view);
                                bind_manager_layout = manager_view.findViewById(R.id.bind_manager_layout);
                                iv_manager_photo = manager_view.findViewById(R.id.iv_manager_photo);
                                tv_manager_name = manager_view.findViewById(R.id.tv_manager_name);
                                tv_manager_name = manager_view.findViewById(R.id.tv_manager_name);
                                iv_chat_msg = manager_view.findViewById(R.id.iv_chat_msg);
                                iv_call_phone = manager_view.findViewById(R.id.iv_call_phone);
                                bind_manager_layout.setOnClickListener(this);
                                iv_call_phone.setOnClickListener(this);
                                iv_chat_msg.setOnClickListener(this);
                            }
                            if (is_show == 1) {
                                String homeManagerCache = mShared.getString(UserAppConst.COLOR_HOME_MANAGER, "");
                                if (!TextUtils.isEmpty(homeManagerCache) && loadCacheData) {
                                    homeManagerShow(homeManagerCache);
                                }
                                handler.sendEmptyMessageDelayed(5, 4000);
                            } else {
                                bind_manager_layout.setVisibility(View.GONE);
                            }
                            break;
                        case 1005://banner图模块
                            if (null == bga_view) {
                                bga_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_banner_view, null);
                                home_rv.addHeaderView(bga_view);
                                bga_banner = bga_view.findViewById(R.id.bga_banner);
                                rl_banner = bga_view.findViewById(R.id.rl_banner);
                                iv_video = bga_view.findViewById(R.id.iv_video);
                            }
                            if (is_show == 1) {
                                String homeBannerCache = mShared.getString(UserAppConst.COLOR_HOME_BANNER, "");
                                if (!TextUtils.isEmpty(homeBannerCache) && loadCacheData) {
                                    homeBannerShow(homeBannerCache);
                                }
                                handler.sendEmptyMessageDelayed(6, 7000);
                            } else {
                                rl_banner.setVisibility(View.GONE);
                            }
                            break;
                        case 1006://活动模块
                            if (null == activity_view) {
                                activity_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_activity_view, null);
                                home_rv.addHeaderView(activity_view);
                                rv_activity = activity_view.findViewById(R.id.rv_activity);
                            }
                            if (is_show == 1) {
                                rv_activity.setVisibility(View.VISIBLE);
                                String homeActivityCache = mShared.getString(UserAppConst.COLOR_HOME_ACTIVITY, "");
                                if (!TextUtils.isEmpty(homeActivityCache) && loadCacheData) {
                                    homeActivityShow(homeActivityCache);
                                }
                                handler.sendEmptyMessageDelayed(7, 7000);
                            } else {
                                rv_activity.setVisibility(View.GONE);
                            }
                            break;
                    }
                }
                if (footer_view == null) {
                    footer_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_footer_view, null);
                    home_rv.addHeaderView(footer_view);
                }
            }
        } catch (Exception e) {

        }
    }

    private void showExceptionDoorData() {
        commonUseBeanList.clear();
        SingleCommunityEntity.ContentBean.CommonUseBean singleCommonUse = new SingleCommunityEntity.ContentBean.CommonUseBean();
        singleCommonUse.setDoor_name("申请门禁");
        commonUseBeanList.add(singleCommonUse);
        updateDoorView();
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case -1:
                if (!TextUtils.isEmpty(result)) {
                    showHomeLayout(result, false);
                } else {
                    showHomeLayout(Constants.defaultLayout, false);
                }
                break;
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    homeHeaderShow(result);
                }
                break;
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    homeFuncShow(result);
                } else {
                    homeFuncShow(Constants.defaultHomeFunc);
                }
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    homeApplicationShow(result);
                }
                break;
            case 3:
                if (!TextUtils.isEmpty(result)) {
                    homeDoorShow(result);
                } else {
                    showExceptionDoorData();
                }
                break;
            case 4:
                if (!TextUtils.isEmpty(result)) {
                    homeNotificationShow(result);
                }
                break;
            case 5:
                if (!TextUtils.isEmpty(result)) {
                    homeManagerShow(result);
                }
                break;
            case 6:
                if (!TextUtils.isEmpty(result)) {
                    homeBannerShow(result);
                }
                break;
            case 7:
                if (!TextUtils.isEmpty(result)) {
                    homeActivityShow(result);
                }
                break;
            case 8:
                if (!TextUtils.isEmpty(result)) {
                    homeHeaderResourceShow(result);
                } else {
                    themeSuccess = 0;
                }
                break;
            case 10://获取实名token
                if (!TextUtils.isEmpty(result)) {
                    try {
                        RealNameTokenEntity entity = cn.csh.colourful.life.utils.GsonUtils.gsonToBean(result, RealNameTokenEntity.class);
                        RealNameTokenEntity.ContentBean bean = entity.getContent();
                        startAuthenticate(bean.getBizToken());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 11://提交认证
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        if ("0".equals(code)) {
                            String content = jsonObject.getString("content");
                            if ("1".equals(content)) {
                                ToastUtil.toastShow(getActivity(), "认证成功");
                                editor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, realName).commit();
                                authItemClick(fromFunc, fromFunc ? homeFuncBean : appBean); //跳转
                            } else {
                                editor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "").commit();
                            }
                        } else {
                            String message = jsonObject.getString("message");
                            ToastUtil.toastShow(getActivity(), message);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 12://获取认证
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        if ("0".equals(code)) {
                            String content = jsonObject.getString("content");
                            JSONObject data = new JSONObject(content);
                            int isIdentity = data.getInt("is_identity");
                            String real = data.getString("real_name");
                            if (1 == isIdentity) {
                                editor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, real).commit();
                            } else {
                                editor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "").commit();
                            }
                        } else {
                            String message = jsonObject.getString("message");
                            ToastUtil.toastShow(getActivity(), message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 14:
                LekaiHelper.startService(getActivity(), result);
                break;
        }
        questReturnNumber++;
        stopRefresh();
    }

    @Override
    public void authenticationFeedback(String s, int i) {
        switch (i) {
            case 14:
                ToastUtil.toastShow(getActivity(), "开通彩钱包成功");
                break;
            case 15:
                ToastUtil.toastShow(getActivity(), "取消彩钱包成功");
                break;
        }
    }

    @Override
    public void toCFRS(String s) {
        if (!TextUtils.isEmpty(s)) {
            LinkParseUtil.parse(getActivity(), s, "");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.home_wallet_layout:
                if (!TextUtils.isEmpty(colourWalletUrl)) {
                    BuryingPointUtils.uploadClickMethod(getActivity(), BuryingPointUtils.homePageName, BuryingPointUtils.homeTopCode, "彩钱包", "10103");
                    LinkParseUtil.parse(getActivity(), colourWalletUrl, "");
                }
                break;
            case R.id.home_period_layout:
                if (!TextUtils.isEmpty(colourHomeUrl)) {
                    String content = "10102" + BuryingPointUtils.divisionSign + "彩住宅" + BuryingPointUtils.divisionSign + BuryingPointUtils.homeTopCode;
                    LinkParseUtil.parse(getActivity(), colourHomeUrl, content);
                }
                break;
            case R.id.bind_manager_layout:
                if (!TextUtils.isEmpty(managerLink)) {
//                    LinkParseUtil.parse(getActivity(), managerLink, "");
                }
                break;
            case R.id.iv_call_phone:
                if (bindStatus == 1) {
                    BuryingPointUtils.uploadClickMethod(getActivity(), BuryingPointUtils.homePageName, BuryingPointUtils.homeManagerCode, "拨号", "10601");
                    PermissionUtils.showPhonePermission(getActivity(), managerPhone);
                } else {
                    if (!TextUtils.isEmpty(managerLink)) {
//                        LinkParseUtil.parse(getActivity(), managerLink, "");
                    }
                }
                break;
            case R.id.iv_enter_chat:
            case R.id.head_enter_chat:
                HuxinSdkManager.instance().entryServiceManager(getActivity());
                break;
            case R.id.iv_chat_msg:
                HuxinSdkManager.instance().entryChatService(getActivity());
                break;
            case R.id.iv_left_arrow:
                try {
                    if (choiceIndex > 0) {
                        choiceIndex--;
                        viewpage_door.setCurrentItem(choiceIndex);
                    }
                } catch (Exception e) {

                }
                break;
            case R.id.iv_right_arrow:
                try {
                    if (choiceIndex < useDoorSize - 1) {
                        choiceIndex++;
                        viewpage_door.setCurrentItem(choiceIndex);
                    }
                } catch (Exception e) {

                }
                break;
            case R.id.open_door_layout:
                BuryingPointUtils.uploadClickMethod(getActivity(), BuryingPointUtils.homePageName, BuryingPointUtils.homeDoorCode, "门禁", "10401");
                LinkParseUtil.parse(getActivity(), "colourlife://proto?type=EntranceGuard", "");
                break;
            case R.id.notification_layout:
                String content = "10502" + BuryingPointUtils.divisionSign + "消息通知" + BuryingPointUtils.divisionSign + BuryingPointUtils.homeNotificationCode;
                LinkParseUtil.parse(getActivity(), "colourlife://proto?type=notificationList", content);
                setMessageRead();
                break;
        }
    }

    private AuthDialog authDialog;

    /**
     * 需求实名认证
     */
    private void needAuth() {
        if (authDialog == null) {
            authDialog = new AuthDialog(getActivity());
        }
        authDialog.show();
        authDialog.iv_close.setOnClickListener(v1 -> {
            if (authDialog != null) {
                authDialog.dismiss();
            }
        });
        authDialog.tv_pass.setOnClickListener(v2 -> {
            if (authDialog != null) {
                authDialog.dismiss();
            }
            editor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "dismiss").commit();//出现过一次
            authItemClick(fromFunc, fromFunc ? homeFuncBean : appBean);//跳转
        });
        authDialog.iv_goto.setOnClickListener(v2 -> {
            if (authDialog != null) {
                authDialog.dismiss();
            }
            editor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "dismiss").commit();//出现过一次
            newUserModel.getRealNameToken(10, MainHomeFragmentNew.this, true);
        });
    }

    /**
     * 实名认证
     */
    private void startAuthenticate(String realToken) {
        AuthConfig.Builder configBuilder = new AuthConfig.Builder(realToken, R.class.getPackage().getName());
        AuthSDKApi.startMainPage(getActivity(), configBuilder.build(), mListener);
    }

    /**
     * 监听实名认证返回
     */
    private IdentityCallback mListener = data -> {
        boolean identityStatus = data.getBooleanExtra(AuthSDKApi.EXTRA_IDENTITY_STATUS, false);
        if (identityStatus) {//true 已实名
            IDCardInfo idCardInfo = data.getExtras().getParcelable(AuthSDKApi.EXTRA_IDCARD_INFO);
            if (idCardInfo != null) {//身份证信息   idCardInfo.getIDcard();//身份证号码
                realName = idCardInfo.getName();//姓名
                newUserModel.submitRealName(11, idCardInfo.getIDcard(), realName, this);//提交实名认证
            }
        }
    };

    private void initHomeClick() {
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getFristData();
            }
        });
        home_rv.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
                if (layoutManager instanceof LinearLayoutManager) {
                    LinearLayoutManager linearLayoutManager = (LinearLayoutManager) layoutManager;
                    int complePos = linearLayoutManager.findFirstCompletelyVisibleItemPosition();
                    if (complePos == 0) {
                        refresh_layout.setEnabled(true);
                    } else {
                        refresh_layout.setEnabled(false);
                    }
                    int position = linearLayoutManager.findFirstVisibleItemPosition();
                    if (position == 0) {
                        int moveDistance = getScollYDistance(linearLayoutManager);
                        int pixHeight = Util.DensityUtil.dip2px(getActivity(), height);
                        if (moveDistance <= 0) {
                            alpha_title_layout.setVisibility(View.GONE);
                            alpha_title_layout.setAlpha(0);
                            alpha_community.setAlpha(0);
                        } else if (moveDistance > 0 && moveDistance <= pixHeight) { //滑动距离小于banner图的高度时，设置背景和字体颜色颜色透明度渐变
                            float scale = (float) moveDistance / pixHeight;
                            alpha_title_layout.setVisibility(View.VISIBLE);
                            alpha_title_layout.setAlpha(scale);
                            alpha_community.setAlpha(scale);
                        } else {
                            alpha_title_layout.setAlpha(1.0f);
                            alpha_community.setAlpha(1.0f);
                            alpha_title_layout.setVisibility(View.VISIBLE);
                        }
                    }
                }
            }
        });
    }

    @Override
    public void onBuddyMsgCallback(CacheMsgBean cacheMsgBean) {
        showUnReadMsg(HuxinSdkManager.instance().unreadServiceManagerMessage());
    }

    @Override
    public void onCommunityMsgCallback(CacheMsgBean cacheMsgBean) {
        showUnReadMsg(HuxinSdkManager.instance().unreadServiceManagerMessage());
    }

    @Override
    public void onOwnerMsgCallback(CacheMsgBean cacheMsgBean) {
        showUnReadMsg(HuxinSdkManager.instance().unreadServiceManagerMessage());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 100) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    LekaiHelper.startOperationService(getActivity());
                }
            }
        }
    }
}
