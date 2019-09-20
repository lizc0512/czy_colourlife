package cn.net.cyberway.fagment;


import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.NoScrollGridView;
import com.allapp.model.AllAppModel;
import com.customerInfo.protocol.RealNameTokenEntity;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.XListView;
import com.nohttp.utils.GsonUtils;
import com.tencent.authsdk.AuthConfig;
import com.tencent.authsdk.AuthSDKApi;
import com.tencent.authsdk.IDCardInfo;
import com.tencent.authsdk.callback.IdentityCallback;
import com.tendcloud.tenddata.TCAgent;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.model.NewUserModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.adpter.HomeMoreCellAdapter;
import cn.net.cyberway.adpter.LifeHomeRVAdapter;
import cn.net.cyberway.adpter.RecentlyUsedServiceAdapter;
import cn.net.cyberway.home.adapter.GvLifeTwoAdapter;
import cn.net.cyberway.home.model.NewHomeModel;
import cn.net.cyberway.home.view.AuthDialog;
import cn.net.cyberway.protocol.HomeLifeEntity;
import cn.net.cyberway.utils.LinkParseUtil;

import static com.youmai.hxsdk.utils.DisplayUtil.getStatusBarHeight;

/**
 * 生活页面
 */

public class LifeHomeFragment extends Fragment implements NewHttpResponse {
    public SharedPreferences shared;
    public SharedPreferences.Editor editor;
    private View mView;
    private View myView;
    private XListView xListView;
    private SwipeRefreshLayout refresh_layout;
    private NewHomeModel newHomeModel;
    private NewUserModel newUserModel;
    private String homeMoreCathe;
    private NoScrollGridView sv_life_recommend;
    private GvLifeTwoAdapter gvLifeTwoAdapter;
    private LinearLayout layout_life;
    private RelativeLayout rl_life_neer;
    private RelativeLayout rl_life_tv;
    private RelativeLayout rl_life_recommend;
    private RecyclerView rv_usefulItem;
    private TextView tv_life_recommend;
    private EditText et_life_serach;
    private RecyclerView rv_life_home;
    private LifeHomeRVAdapter lifeHomeRVAdapter;
    private ArrayList<HomeLifeEntity.ContentBean> SerachDatas = new ArrayList<>();
    private ArrayList<HomeLifeEntity.ContentBean.ListBean> lifeHomeSerachlist = new ArrayList<>();
    private ArrayList<HomeLifeEntity.ContentBean.ListBean> lifeList = new ArrayList<HomeLifeEntity.ContentBean.ListBean>(4);
    private ArrayList<String> lifeIdList = new ArrayList<String>(4);
    private List<HomeLifeEntity.ContentBean.ListBean> listRecommend = new ArrayList<>();
    private List<HomeLifeEntity.ContentBean> list = new ArrayList<>();
    private HomeLifeEntity.ContentBean.ListBean listBean = null;
    private HomeLifeEntity.ContentBean.ListBean attrBean = null;
    private HomeLifeEntity.ContentBean.ListBean recommendListBean = null;
    private String realName = "";
    private int customer_id;//用户的id
    private String type = "";//用户的id

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_discover_listview, container, false);
        shared = getActivity().getSharedPreferences(UserAppConst.USERINFO, 0);
        editor = shared.edit();
        newHomeModel = new NewHomeModel(getActivity());
        newUserModel = new NewUserModel(getActivity());
        initView();
        getRecentlyData();
        initData();
        initListener();
        return mView;
    }

    private void initListener() {
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                newHomeModel.getlifeInfo(0, LifeHomeFragment.this);
            }
        });
    }

    /**
     * 最近使用
     */
    private void getRecentlyData() {
        String recentlyRecord = shared.getString(UserAppConst.COLOUR_LIFEUSERECORD, "");
        if (!TextUtils.isEmpty(recentlyRecord)) {
            JSONArray subItemArray = null;
            try {
                subItemArray = new JSONArray(recentlyRecord);
                if (null != subItemArray) {
                    for (int i = 0; i < subItemArray.length(); i++) {
                        JSONObject subItemObject = subItemArray.getJSONObject(i);
                        HomeLifeEntity.ContentBean.ListBean subItem = new HomeLifeEntity.ContentBean.ListBean();
                        subItem.fromJson(subItemObject);
                        lifeIdList.add(subItem.getId() + "");
                        lifeList.add(subItem);
                    }
                    showRecentlyUsedFunc();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else {
            rl_life_neer.setVisibility(View.GONE);
        }
    }

    private void setTabViewHeight(View tabBarView) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(getActivity()));
        tabBarView.setLayoutParams(layoutParams);
    }

    private void initView() {
        myView = LayoutInflater.from(getActivity()).inflate(R.layout.life_more, null);
        xListView = (XListView) mView.findViewById(R.id.life_home_list);
        refresh_layout = mView.findViewById(R.id.refresh_layout);
        refresh_layout.setColorSchemeColors(Color.parseColor("#3290FF"), Color.parseColor("#6ABDF9"));
        View life_tabbar_view = mView.findViewById(R.id.life_tabbar_view);
        life_tabbar_view.setBackgroundColor(Color.parseColor("#ffffff"));
        setTabViewHeight(life_tabbar_view);
        xListView.setAdapter(null);
        xListView.setPullRefreshEnable(false);
        xListView.setPullLoadEnable(false);
        xListView.loadMoreHide();
        xListView.addHeaderView(myView);
        refresh_layout.setRefreshing(true);
        sv_life_recommend = (NoScrollGridView) myView.findViewById(R.id.sv_life_recommend);
        rv_life_home = (RecyclerView) myView.findViewById(R.id.rv_life_home);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 4);
        rv_life_home.setLayoutManager(gridLayoutManager);
        rv_life_home.setNestedScrollingEnabled(false);
        layout_life = (LinearLayout) myView.findViewById(R.id.layout_life);
        et_life_serach = (EditText) mView.findViewById(R.id.et_life_serach);
        tv_life_recommend = (TextView) myView.findViewById(R.id.tv_life_recommend);
        rl_life_neer = (RelativeLayout) myView.findViewById(R.id.rl_life_neer);
        rl_life_recommend = (RelativeLayout) myView.findViewById(R.id.rl_life_recommend);
        rv_usefulItem = (RecyclerView) myView.findViewById(R.id.rv_usefulItem);
        rv_usefulItem.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rl_life_tv = (RelativeLayout) myView.findViewById(R.id.rl_life_tv);
        rl_life_tv.setVisibility(View.GONE);
    }

    /***将最新的数据同步更新最近使用的缓存中***/
    private void syncCacheRecentlyData() {
        List<String> tempIdList = new ArrayList<>();
        for (int k = 0; k < lifeIdList.size(); k++) {
            inner:
            for (int i = 1; i < list.size(); i++) {
                List<HomeLifeEntity.ContentBean.ListBean> listBeanList = list.get(i).getList();
                for (HomeLifeEntity.ContentBean.ListBean attr : listBeanList) {
                    String saveId = lifeIdList.get(k);
                    String everyId = String.valueOf(attr.getId());
                    if (saveId.equals(everyId) && !tempIdList.contains(saveId)) {  //如果有且不包含
                        tempIdList.add(saveId);
                        lifeList.add(attr);
                        break inner;
                    } else {
                        continue;
                    }
                }
            }
        }
        lifeIdList.clear();
        lifeIdList.addAll(tempIdList);
        showRecentlyUsedFunc();
    }

    private void showFuncView() {
        if (null != list && list.size() > 1) {
            layout_life.removeAllViews();
            for (int i = 1; i < list.size(); i++) {
                final HomeLifeEntity.ContentBean listMoress = list.get(i);
                try {
                    View moreItemView = LayoutInflater.from(getActivity().getApplicationContext()).inflate(R.layout.home_more_item, null);
                    TextView name = (TextView) moreItemView.findViewById(R.id.name);
                    NoScrollGridView gridView = (NoScrollGridView) moreItemView.findViewById(R.id.grid);
                    final List<HomeLifeEntity.ContentBean.ListBean> listBeanList = listMoress.getList();
                    HomeMoreCellAdapter homeMoreCellAdapter = new HomeMoreCellAdapter(getActivity(), listBeanList);
                    gridView.setAdapter(homeMoreCellAdapter);
                    name.setText(list.get(i).getDesc());
                    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            listBean = listBeanList.get(position);
                            String realName = shared.getString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "");
                            boolean isRealName = !TextUtils.isEmpty(realName);
                            type = "life";
                            if ("1".equals(listBean.getIs_auth()) && !isRealName) {//是否需要认证 1：需要实名，2：不需要实名
                                needAuth();
                            } else {
                                authItemClick();
                            }
                        }
                    });
                    layout_life.addView(moreItemView);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
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
            authItemClick();//跳转
        });
        authDialog.iv_goto.setOnClickListener(v2 -> {
            if (authDialog != null) {
                authDialog.dismiss();
            }
            editor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "dismiss").commit();//出现过一次
            newUserModel.getRealNameToken(2, LifeHomeFragment.this, true);
        });
    }

    private void uploadAppClickLister(String appId) {
        AllAppModel allAppModel = new AllAppModel(getActivity());
        allAppModel.uploadAppClick(1, appId, this);
    }

    private RecentlyUsedServiceAdapter recentlyUsedServiceAdapter = null;

    public void showRecentlyUsedFunc() {
        if (recentlyUsedServiceAdapter == null) {
            recentlyUsedServiceAdapter = new RecentlyUsedServiceAdapter(getActivity(), lifeList);
            rv_usefulItem.setAdapter(recentlyUsedServiceAdapter);
        } else {
            recentlyUsedServiceAdapter.notifyDataSetChanged();
        }
        if (lifeList.size() > 0) {
            rl_life_neer.setVisibility(View.VISIBLE);
        } else {
            rl_life_neer.setVisibility(View.GONE);
        }
        if (recentlyUsedServiceAdapter != null) {
            recentlyUsedServiceAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    if (i >= 0) {
                        attrBean = lifeList.get(i);
                        String realName = shared.getString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "");
                        boolean isRealName = !TextUtils.isEmpty(realName);
                        if (null != attrBean) {
                            if ("1".equals(attrBean.getIs_auth()) && !isRealName) {//是否需要认证 1：需要实名，2：不需要实名
                                type = "recently";
                                needAuth();
                            } else {
                                LinkParseUtil.parse(getActivity(), attrBean.getUrl(), attrBean.getName());
                            }
                        }
                    }
                }
            });
        }
    }

    private void initData() {
        customer_id = shared.getInt(UserAppConst.Colour_User_id, 0);
        homeMoreCathe = shared.getString(UserAppConst.LIFECATEGORY, "");
        if (!TextUtils.isEmpty(homeMoreCathe)) {//更多
            try {
                lifeDataAdapter(homeMoreCathe);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        newHomeModel.getlifeInfo(0, this);
        et_life_serach.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                rl_life_neer.setVisibility(View.GONE);
                rl_life_recommend.setVisibility(View.GONE);
                layout_life.setVisibility(View.GONE);
                rv_life_home.setVisibility(View.VISIBLE);
                rl_life_tv.setVisibility(View.VISIBLE);
                if (!list.isEmpty() && list.size() > 0) {
                    SerachDatas.clear();
                    SerachDatas.addAll(list);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() == 0 || et_life_serach.getText().toString().length() == 0) {
                    layout_life.setVisibility(View.VISIBLE);
                    rv_life_home.setVisibility(View.GONE);
                    rl_life_tv.setVisibility(View.GONE);
                    if (listRecommend.size() > 0) {
                        rl_life_recommend.setVisibility(View.VISIBLE);
                    }
                    if (lifeList.size() > 0) {
                        rl_life_neer.setVisibility(View.VISIBLE);
                    } else {
                        rl_life_neer.setVisibility(View.GONE);
                    }
                } else {//输入框有数据才筛选
                    lifeHomeSerachlist.clear();
                    String word = et_life_serach.getText().toString().trim();
                    for (int i = 0; i < SerachDatas.size(); i++) {
                        for (int y = 0; y < SerachDatas.get(i).getList().size(); y++) {
                            if (SerachDatas.get(i).getList().get(y).getName().toLowerCase().contains(word.toLowerCase())) {
                                lifeHomeSerachlist.add(SerachDatas.get(i).getList().get(y));
                            }
                        }
                    }
                    if (lifeHomeSerachlist.size() > 0) {
                        if (null == lifeHomeRVAdapter) {
                            lifeHomeRVAdapter = new LifeHomeRVAdapter(getActivity(), lifeHomeSerachlist);
                            rv_life_home.setAdapter(lifeHomeRVAdapter);
                        } else {
                            lifeHomeRVAdapter.setData(lifeHomeSerachlist);
                            lifeHomeRVAdapter.notifyDataSetChanged();
                        }
                    }
                }
            }
        });
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        if (message.what == UserMessageConstant.CHANGE_COMMUNITY) {//首页切换小区，及时更新
            newHomeModel.getlifeInfo(0, this);
        } else if (message.what == UserMessageConstant.CHANGE_DIFF_LANG) {//切换语言
            newHomeModel.getlifeInfo(0, this);
        } else if (message.what == UserMessageConstant.SIGN_IN_SUCCESS) {//登录成功刷新数据
            lifeList.clear();
            lifeIdList.clear();
            getRecentlyData();
            initData();
        }
    }


    /**
     * 最近推荐适配
     */
    private void setRecommendAdapter() {
        if (listRecommend.size() > 0) {
            rl_life_recommend.setVisibility(View.VISIBLE);
            tv_life_recommend.setText(list.get(0).getDesc());
        }
        if (null != getActivity()) {
            if (gvLifeTwoAdapter == null) {
                gvLifeTwoAdapter = new GvLifeTwoAdapter(getActivity(), listRecommend);
                sv_life_recommend.setAdapter(gvLifeTwoAdapter);
            } else {
                gvLifeTwoAdapter.setData(listRecommend);
                gvLifeTwoAdapter.notifyDataSetChanged();
            }
        }
        sv_life_recommend.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                recommendListBean = listRecommend.get(position);
                if (shared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                    String realName = shared.getString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, "");
                    boolean isRealName = !TextUtils.isEmpty(realName);
                    if ("1".equals(recommendListBean.getIs_auth()) && !isRealName) {//是否需要认证 1：需要实名，2：不需要实名
                        type = "recommend";
                        needAuth();
                    } else {
                        LinkParseUtil.parse(getActivity(), recommendListBean.getUrl(), recommendListBean.getName());
                        uploadAppClickLister(String.valueOf(recommendListBean.getId()));
                    }
                } else {
                    LinkParseUtil.parse(getActivity(), "", "");
                }
            }
        });
    }

    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().register(this);
        }
        TCAgent.onPageStart(getActivity(), "更多");
        String url = shared.getString("CurrentLinkUrl", "");
        if (url.endsWith("NewEReduceList")) {
            TCAgent.onPageEnd(getActivity(), "新彩富人生");
            editor.putString("CurrentLinkUrl", "").commit();
        }
//        ((MainActivity) getActivity()).changeStyle();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            ((MainActivity) getActivity()).changeStyle();
        } else {
            saveRecentlyCache();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        saveRecentlyCache();
    }

    private void saveRecentlyCache() {
        editor.putString(UserAppConst.COLOUR_LIFEUSERECORD, "").commit();
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < lifeList.size(); i++) {
            try {
                HomeLifeEntity.ContentBean.ListBean listBean1 = lifeList.get(i);
                JSONObject itemJSONObject = listBean1.toJson();
                jsonArray.put(itemJSONObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        editor.putString(UserAppConst.COLOUR_LIFEUSERECORD, jsonArray.toString()).commit();
    }

    public void onPause() {
        super.onPause();
        TCAgent.onPageEnd(getActivity(), "生活");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    lifeDataAdapter(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                refresh_layout.setRefreshing(false);
                break;
            case 1:
                Message msg = new Message();
                msg.what = UserMessageConstant.UPDATE_APP_CLICK;
                EventBus.getDefault().post(msg);
                break;
            case 2:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        RealNameTokenEntity entity = GsonUtils.gsonToBean(result, RealNameTokenEntity.class);
                        RealNameTokenEntity.ContentBean bean = entity.getContent();
                        startAuthenticate(bean.getBizToken());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
            case 3:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String code = jsonObject.getString("code");
                        if ("0".equals(code)) {
                            String content = jsonObject.getString("content");
                            if ("1".equals(content)) {
                                ToastUtil.toastShow(getActivity(), "认证成功");
                                newUserModel.finishTask(4, "2", this);
                                editor.putString(UserAppConst.COLOUR_AUTH_REAL_NAME + customer_id, realName).commit();
                                authItemClick();//跳转
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
            case 4://实名认证任务
                break;
        }
    }

    /**
     * 数据汇总适配
     *
     * @param result
     */
    private void lifeDataAdapter(String result) {
        list.clear();
        if (!TextUtils.isEmpty(result)) {
            HomeLifeEntity homeLifeEntity = GsonUtils.gsonToBean(result, HomeLifeEntity.class);
            list.addAll(homeLifeEntity.getContent());
            if (null != list) {
                if (list.size() > 0) {//最近推荐
                    listRecommend.clear();
                    listRecommend = list.get(0).getList();
                    setRecommendAdapter();
                }
                showFuncView();
                lifeList.clear();
                syncCacheRecentlyData();
            }
        }
    }

    /**
     * 需要实名认证的item
     */
    private void authItemClick() {
        switch (type) {
            case "life":
                type = "";
                String attrId = listBean.getId() + "";
                LinkParseUtil.parse(getActivity(), listBean.getUrl(), listBean.getName());
                if (lifeIdList.contains(attrId)) {
                    lifeIdList.remove(attrId);
                    for (int j = 0; j < lifeList.size(); j++) {
                        if (attrId.equals(lifeList.get(j).getId() + "")) {
                            lifeList.remove(j);
                            break;
                        }
                    }
                }
                lifeIdList.add(0, attrId);
                lifeList.add(0, listBean);
                if (lifeIdList.size() > 4) {
                    lifeList.remove(4);
                    lifeIdList.remove(4);
                }
                showRecentlyUsedFunc();
                uploadAppClickLister(attrId);
                break;
            case "recommend":
                type = "";
                LinkParseUtil.parse(getActivity(), recommendListBean.getUrl(), recommendListBean.getName());
                uploadAppClickLister(String.valueOf(recommendListBean.getId()));
                break;
            case "recently":
                type = "";
                LinkParseUtil.parse(getActivity(), attrBean.getUrl(), attrBean.getName());
                break;
        }
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
        if (identityStatus) {
            IDCardInfo idCardInfo = data.getExtras().getParcelable(AuthSDKApi.EXTRA_IDCARD_INFO);
            if (idCardInfo != null) {
                realName = idCardInfo.getName();
                newUserModel.submitRealName(3, idCardInfo.getIDcard(), realName, this);//提交实名认证
            }
        }
    };
}
