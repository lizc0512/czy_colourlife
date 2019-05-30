package cn.net.cyberway.home.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.NewHttpResponse;
import com.door.entity.SingleCommunityEntity;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GridSpacingItemDecoration;
import com.nohttp.utils.GsonUtils;
import com.tmall.ultraviewpager.UltraViewPager;
import com.user.UserAppConst;
import com.user.activity.UserRegisterAndLoginActivity;

import java.util.ArrayList;
import java.util.List;

import cn.bingoogolapple.bgabanner.BGABanner;
import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import cn.net.cyberway.home.adapter.HomeApplicationAdapter;
import cn.net.cyberway.home.adapter.HomeDoorAdapter;
import cn.net.cyberway.home.adapter.HomeFunctionAdapter;
import cn.net.cyberway.home.entity.HomeFuncEntity;
import cn.net.cyberway.home.model.NewHomeModel;


/**
 * 2017/10/18
 * 新版彩之云5.0新首页
 */
public class NologinHomeFragment extends Fragment implements NewHttpResponse, View.OnClickListener {

    private View mView;
    private XRecyclerView home_rv;
    private NewHomeModel newHomeModel;
    private SharedPreferences mShared;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newHomeModel = new NewHomeModel(getActivity());
        mShared = getActivity().getSharedPreferences(UserAppConst.USERINFO, 0);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_home_nologin, container, false);
        initHeaderView();
        return mView;
    }


    private View function_view;
    private View application_view;
    private View manager_view;
    private View opendoor_view;
    private View notification_view;
    private View banner_view;

    private TextView tv_register_login;

    private void initHeaderView() {
        home_rv = mView.findViewById(R.id.home_rv);
        tv_register_login = mView.findViewById(R.id.tv_register_login);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        home_rv.setLayoutManager(layoutManager);
        home_rv.setPullRefreshEnabled(true);
        home_rv.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        home_rv.setLoadingMoreEnabled(false);
        home_rv.setAdapter(homeAdapter);
        function_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_function_view, null);
        application_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_normalapp_view, null);
        manager_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_manager_view, null);
        opendoor_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_opendoor_view, null);
        notification_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_notification_view, null);
        banner_view = LayoutInflater.from(getActivity()).inflate(R.layout.mhome_banner_view, null);
        home_rv.addHeaderView(function_view);
        home_rv.addHeaderView(application_view);
        home_rv.addHeaderView(opendoor_view);
        home_rv.addHeaderView(notification_view);
        home_rv.addHeaderView(manager_view);
        home_rv.addHeaderView(banner_view);
        initChildView();
        initHomeClick();
        loadCacheData();
        tv_register_login.setOnClickListener(this);
    }

    private void loadCacheData() {
        String homeNologinFunc = mShared.getString(UserAppConst.COLOR_NOLOGIN_FUNCTION, "");
        String homeNologinApp = mShared.getString(UserAppConst.COLOR_NOLOGIN_APPLICATION, "");
        String homeNologinBanner = mShared.getString(UserAppConst.COLOR_NOLOGIN_BANNNER, "");
        if (!TextUtils.isEmpty(homeNologinFunc)) {
            homeFuncShow(homeNologinFunc);
            homeDoorShow();
        } else {
            home_rv.refresh();
        }
        if (!TextUtils.isEmpty(homeNologinApp)) {
            homeApplicationShow(homeNologinApp);
        }
        if (!TextUtils.isEmpty(homeNologinBanner)) {
            homeBannerShow(homeNologinBanner);
        }

    }


    private RecyclerView rv_fuction;
    private RecyclerView rv_application;

    private LinearLayout bind_manager_layout;
    private ImageView iv_manager_photo;
    private TextView tv_manager_name;
    private ImageView iv_call_phone;

    private LinearLayout open_door_layout;
    private UltraViewPager viewpage_door;
    private ImageView iv_left_arrow;
    private ImageView iv_right_arrow;
    private ImageView iv_open_door;
    private BGABanner advise_banner;

    private void initChildView() {
        rv_fuction = function_view.findViewById(R.id.rv_fuction);
        LinearLayout fuction_layout = function_view.findViewById(R.id.fuction_layout);
        rv_application = application_view.findViewById(R.id.rv_application);
        bind_manager_layout = manager_view.findViewById(R.id.bind_manager_layout);
        iv_manager_photo = manager_view.findViewById(R.id.iv_manager_photo);
        tv_manager_name = manager_view.findViewById(R.id.tv_manager_name);
        tv_manager_name.setText("未绑定");
        iv_call_phone = manager_view.findViewById(R.id.iv_call_phone);
        bind_manager_layout.setOnClickListener(this);
        RelativeLayout notification_layout = notification_view.findViewById(R.id.notification_layout);
        TextView tv_no_message = notification_view.findViewById(R.id.tv_no_message);
        View bottom_empty_view = notification_view.findViewById(R.id.bottom_empty_view);
        notification_layout.setVisibility(View.VISIBLE);
        tv_no_message.setVisibility(View.VISIBLE);
        bottom_empty_view.setVisibility(View.VISIBLE);
        open_door_layout = opendoor_view.findViewById(R.id.open_door_layout);
        viewpage_door = opendoor_view.findViewById(R.id.viewpage_door);
        iv_left_arrow = opendoor_view.findViewById(R.id.iv_left_arrow);
        iv_right_arrow = opendoor_view.findViewById(R.id.iv_right_arrow);
        iv_open_door = opendoor_view.findViewById(R.id.iv_open_door);
        iv_left_arrow.setOnClickListener(this);
        open_door_layout.setOnClickListener(this);
        iv_right_arrow.setOnClickListener(this);
        iv_open_door.setOnClickListener(this);
        tv_no_message.setOnClickListener(this);
        notification_layout.setOnClickListener(this);
        advise_banner = banner_view.findViewById(R.id.bga_banner);

    }


    private ArrayList<HomeFuncEntity.ContentBean> funcDataBeanList = new ArrayList<HomeFuncEntity.ContentBean>();
    private HomeFunctionAdapter homeFunctionAdapter = null;

    private void homeFuncShow(String result) {
        try {
            HomeFuncEntity homeFuncEntity = GsonUtils.gsonToBean(result, HomeFuncEntity.class);
            funcDataBeanList.clear();
            funcDataBeanList.addAll(homeFuncEntity.getContent());
            if (homeFunctionAdapter == null) {
                homeFunctionAdapter = new HomeFunctionAdapter(getActivity(), funcDataBeanList);
                rv_fuction.setLayoutManager(new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false));
                rv_fuction.addItemDecoration(new GridSpacingItemDecoration(2, Utils.dip2px(getActivity(), 5), false));
                rv_fuction.setAdapter(homeFunctionAdapter);
            } else {
                homeFunctionAdapter.notifyDataSetChanged();
            }
            homeFunctionAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    if (i >= 0) {
                        Intent intent = new Intent(getActivity(), UserRegisterAndLoginActivity.class);
                        getActivity().startActivity(intent);
                    }
                }
            });
        } catch (Exception e) {

        }
    }

    private ArrayList<HomeFuncEntity.ContentBean> appDataBeanList = new ArrayList<HomeFuncEntity.ContentBean>();
    private HomeApplicationAdapter homeAppAdapter = null;


    private void homeApplicationShow(String result) {
        try {
            HomeFuncEntity homeFuncEntity = GsonUtils.gsonToBean(result, HomeFuncEntity.class);
            appDataBeanList.clear();
            appDataBeanList.addAll(homeFuncEntity.getContent());
            if (appDataBeanList.size() > 0) {
                if (homeAppAdapter == null) {
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
                            Intent intent = new Intent(getActivity(), UserRegisterAndLoginActivity.class);
                            getActivity().startActivity(intent);
                        }
                    }
                });
            } else {

            }
        } catch (Exception e) {

        }
    }

    private BGABanner.Adapter<ImageView, String> bgaAdapter = new BGABanner.Adapter<ImageView, String>() {
        @Override
        public void fillBannerItem(BGABanner banner, ImageView itemView, String model, int position) {
            GlideImageLoader.loadImageDefaultDisplay(getActivity(), model, itemView, R.drawable.icon_style_one, R.drawable.icon_style_one);
        }
    };


    private ArrayList<HomeFuncEntity.ContentBean> bannerDataBeanList = new ArrayList<HomeFuncEntity.ContentBean>();

    /****广告banner****/
    private void homeBannerShow(String result) {
        try {
            HomeFuncEntity homeFuncEntity = GsonUtils.gsonToBean(result, HomeFuncEntity.class);
            bannerDataBeanList.clear();
            List<String> bannerUrlList = new ArrayList<>();
            bannerDataBeanList.addAll(homeFuncEntity.getContent());
            for (HomeFuncEntity.ContentBean dataBean : bannerDataBeanList) {
                bannerUrlList.add(dataBean.getImg());
            }
            advise_banner.setDelegate(new BGABanner.Delegate<ImageView, String>() {
                @Override
                public void onBannerItemClick(BGABanner banner, ImageView itemView, String model, int position) {
                    Intent intent = new Intent(getActivity(), UserRegisterAndLoginActivity.class);
                    getActivity().startActivity(intent);
                }
            });
            advise_banner.setData(bannerUrlList, null);
            advise_banner.setAdapter(bgaAdapter);
            advise_banner.setAutoPlayInterval(3000);
            advise_banner.setAutoPlayAble(bannerUrlList.size() > 1);
            advise_banner.startAutoPlay();
        } catch (Exception e) {

        }
    }


    private ArrayList<SingleCommunityEntity.ContentBean.CommonUseBean> commonUseBeanList = new ArrayList<>();

    private void homeDoorShow() {
        commonUseBeanList.clear();
        SingleCommunityEntity.ContentBean.CommonUseBean singleCommonUse = new SingleCommunityEntity.ContentBean.CommonUseBean();
        singleCommonUse.setDoor_name("申请门禁");
        commonUseBeanList.add(singleCommonUse);
        HomeDoorAdapter homeDoorAdapter = new HomeDoorAdapter(commonUseBeanList, false, false);
        viewpage_door.setAdapter(homeDoorAdapter);
        viewpage_door.refresh();
    }


    private void getFunctionData() {
        newHomeModel.getHomeModuleFunc(1, this);
    }

    private void getApplicationData() {
        newHomeModel.getHomeModelApp(2, this);
    }

    private void getBannerData() {
        newHomeModel.getHomeModelBanner(6, this);
    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 1:
                if (!TextUtils.isEmpty(result)) {
                    homeFuncShow(result);
                }
                getBannerData();
                homeDoorShow();
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    homeApplicationShow(result);
                }
                break;
            case 6:
                if (!TextUtils.isEmpty(result)) {
                    homeBannerShow(result);
                }
                home_rv.refreshComplete();
                break;
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bind_manager_layout:
            case R.id.iv_left_arrow:
            case R.id.iv_right_arrow:
            case R.id.iv_open_door:
            case R.id.notification_layout:
            case R.id.open_door_layout:
            case R.id.tv_no_message:
            case R.id.tv_register_login:
                Intent intent = new Intent(getActivity(), UserRegisterAndLoginActivity.class);
                getActivity().startActivity(intent);
                break;
        }
    }


    private RecyclerView.Adapter homeAdapter = new RecyclerView.Adapter() {
        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    };

    private void initHomeClick() {
        home_rv.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                getFunctionData();
                getApplicationData();
            }

            @Override
            public void onLoadMore() {

            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            ((MainActivity) getActivity()).setHomeStyle("#ffffff");
        }
    }


}
