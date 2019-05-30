package cn.net.cyberway.activity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewStub;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.external.eventbus.EventBus;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.SpaceItemDecoration;
import com.permission.AndPermission;
import com.setting.activity.EditDialog;
import com.setting.activity.PermissionDialog;
import com.user.UserAppConst;
import com.user.UserMessageConstant;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.adpter.HomeNearbyCommunityAdapter;
import cn.net.cyberway.adpter.SearchLocalCommunityAdapter;
import cn.net.cyberway.model.DataTree;
import cn.net.cyberway.model.HomeCommunityModel;
import cn.net.cyberway.protocol.HomeNearByEntity;
import cn.net.cyberway.protocol.HomeSearchCommunityEntity;
import cn.net.cyberway.utils.CityCustomConst;
import cn.net.cyberway.utils.CityManager;
import cn.net.cyberway.utils.WrapLinearLayoutManager;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/10/18 8:55
 * @change
 * @chang time
 * @class describe   加入社区的页面
 */

public class JoinCommunityActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private ImageView user_top_view_back; //返回按钮
    private TextView user_top_view_title;//标题栏
    private EditText ed_community_name; //搜索的社区名称
    private TextView tv_hint;
    private ImageView iv_delete_text;
    private RelativeLayout location_layout;//定位布局
    private TextView btn_location;
    private RecyclerView rv_community;//已加入小区


    private LinearLayout fail_location_layout;
    private ImageView iv_default_community;
    private TextView tv_default_communityname;

    //搜索小区后的布局
    private View researchlayout;
    private RecyclerView rv_searchcommunity;
    private LinearLayout no_community_layout;

    private int locationpermission = 1;

    private HomeCommunityModel homeCommunityModel;
    private SharedPreferences mShared;
    private boolean isPermissionOpen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_community);
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        ed_community_name = (EditText) findViewById(R.id.ed_community_name);
        tv_hint = (TextView) findViewById(R.id.tv_hint);
        iv_delete_text = (ImageView) findViewById(R.id.iv_delete_text);
        location_layout = (RelativeLayout) findViewById(R.id.location_layout);
        btn_location = (TextView) findViewById(R.id.btn_location);

        fail_location_layout = (LinearLayout) findViewById(R.id.fail_location_layout);
        iv_default_community = (ImageView) findViewById(R.id.iv_default_community);
        tv_default_communityname = (TextView) findViewById(R.id.tv_default_communityname);

        rv_community = (RecyclerView) findViewById(R.id.rv_community);
        researchlayout = ((ViewStub) findViewById(R.id.reseaech_communtiy_layout)).inflate();
        rv_searchcommunity = (RecyclerView) findViewById(R.id.rv_searchcommunity);
        no_community_layout = (LinearLayout) findViewById(R.id.no_community_layout);
        WrapLinearLayoutManager communityLayoutMangager = new WrapLinearLayoutManager(JoinCommunityActivity.this);
        rv_community.setNestedScrollingEnabled(true);
        //设置布局管理器
        rv_community.setLayoutManager(communityLayoutMangager);
        WrapLinearLayoutManager wrapLayoutManager = new WrapLinearLayoutManager(JoinCommunityActivity.this);
        rv_searchcommunity.setNestedScrollingEnabled(true);
        rv_searchcommunity.setLayoutManager(wrapLayoutManager);
        user_top_view_title.setText(getResources().getString(R.string.join_community));
        initTextWatcher();
        user_top_view_back.setOnClickListener(this);
        iv_delete_text.setOnClickListener(this);
        btn_location.setOnClickListener(this);
        homeCommunityModel = new HomeCommunityModel(this);
        mShared = getSharedPreferences(UserAppConst.USERINFO, 0);
        ThemeStyleHelper.onlyFrameTitileBar(JoinCommunityActivity.this, czy_title_layout, user_top_view_back, user_top_view_title);
        initData();
    }

    private void initData() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            isPermissionOpen = mShared.getBoolean(UserAppConst.Colour_location_permission, false);
            if (AndPermission.hasPermission(JoinCommunityActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) || isPermissionOpen) {
                if (!AndPermission.hasPermission(JoinCommunityActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                    mShared.edit().putString(CityCustomConst.LOCATION_LATITUDE, "").apply();
                    mShared.edit().putString(CityCustomConst.LOCATION_LONGITUDE, "").apply();
                    getNearCommmuntiyData();
                } else {
                    locationSuccessData();
                    mShared.edit().putBoolean(UserAppConst.Colour_location_permission, true).apply();
                }
            } else {
                //提示开启申请定位权限的提示框
                showPermissionDialog();
                locationpermission = 0;
                btn_location.setText("重新定位");
            }
        } else {
            locationSuccessData();
        }
        String communityName = shared.getString(UserAppConst.Colour_login_community_name, "互联网社区(非业主版首页)");
        iv_default_community.setImageResource(R.drawable.home_inter_on);
        tv_default_communityname.setText(communityName);
    }

    private void locationSuccessData() {
        locationpermission = 1;
        btn_location.setText("正在定位...");
        CityManager.getInstance(getApplicationContext()).initLocation();
        getNearCommmuntiyData();
    }

    private void getNearCommmuntiyData() {
        String latitude = mShared.getString(CityCustomConst.LOCATION_LATITUDE, "");
        String longitude = mShared.getString(CityCustomConst.LOCATION_LONGITUDE, "");
        initShowCacheData();
        homeCommunityModel.getHomeNearCommunity(0, longitude, latitude, this);
    }

    /**
     * 数据缓存
     */
    private void initShowCacheData() {
        String result = mShared.getString(UserAppConst.JOINCOMMUNITY, "");
        fail_location_layout.setVisibility(View.VISIBLE);
        if (!TextUtils.isEmpty(result)) {
            try {
                HomeNearByEntity homeNearByEntity = GsonUtils.gsonToBean(result, HomeNearByEntity.class);
                List<HomeNearByEntity.ContentBean> contentBeanList = homeNearByEntity.getContent();
                dataTrees.clear();
                for (int i = 0; i < contentBeanList.size(); i++) {
                    if (contentBeanList.get(i).getCommunity_type().equals("2")) {
                        HomeNearByEntity.ContentBean contentBean = contentBeanList.get(i);
                        List<HomeNearByEntity.ContentBean.ListBean> listBeanList = contentBean.getList();
                        if (listBeanList != null && listBeanList.size() > 0) {
                            dataTrees.add(new DataTree<HomeNearByEntity.ContentBean, HomeNearByEntity.ContentBean.ListBean>(contentBean, listBeanList));
                        }
                    }
                }
                initDataAdapter(dataTrees);
            } catch (Exception e) {
            }
        }
    }

    /**
     * 数据适配器
     *
     * @param dataTrees
     */
    private void initDataAdapter(List<DataTree<HomeNearByEntity.ContentBean, HomeNearByEntity.ContentBean.ListBean>> dataTrees) {
        if (homeNearbyCommunityAdapter == null) {
            homeNearbyCommunityAdapter = new HomeNearbyCommunityAdapter();
            rv_community.setAdapter(homeNearbyCommunityAdapter);
        }
        homeNearbyCommunityAdapter.setDataTrees(dataTrees);
    }

    private PermissionDialog permissionDialog;

    private void showPermissionDialog() {
        if (permissionDialog == null) {
            permissionDialog = new PermissionDialog(JoinCommunityActivity.this);
            permissionDialog.setContent("允许彩之云开启定位权限,请去彩之云的权限设置里面将定位权限打开");
        }
        permissionDialog.left_button.setText("取消");
        permissionDialog.left_button.setTextColor(Color.parseColor("#7caff5"));
        permissionDialog.right_button.setText("设置");
        permissionDialog.right_button.setTextColor(Color.parseColor("#7caff5"));
        permissionDialog.show();
        permissionDialog.left_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionDialog.dismiss();
                finish();
            }
        });
        permissionDialog.right_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                permissionDialog.dismiss();
                Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivityForResult(intent, 3000);
            }
        });
    }

    private void initTextWatcher() {
        ed_community_name.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String edCommunityName = ed_community_name.getText().toString().trim();
                    if (TextUtils.isEmpty(edCommunityName)) {
                        ToastUtil.toastShow(JoinCommunityActivity.this, "请输入要搜索的内容");
                    } else {
                        tv_hint.setVisibility(View.GONE);
                        rv_community.setVisibility(View.GONE);
                        researchlayout.setVisibility(View.VISIBLE);
                        //调用搜索小区的接口  隐藏定位成功的布局
                        homeCommunityModel.getHomeLocalSearchCommunity(1, edCommunityName, JoinCommunityActivity.this);
                    }
                }
                return false;
            }
        });

        ed_community_name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String edCommunityName = s.toString().trim();
                if (TextUtils.isEmpty(edCommunityName)) {
                    tv_hint.setVisibility(View.VISIBLE);
                    researchlayout.setVisibility(View.GONE);
                    rv_community.setVisibility(View.VISIBLE);
                    iv_delete_text.setVisibility(View.GONE);
                } else {
                    tv_hint.setVisibility(View.GONE);
                    iv_delete_text.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_location:
                if (locationpermission == 0) {
                    showPermissionDialog();
                }
                break;
            case R.id.iv_delete_text:
                ed_community_name.setText("");
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3000) {
            try {
                initData();
            } catch (Exception e) {
                ToastUtil.toastShow(JoinCommunityActivity.this, "请退出当前页面,重新进行定位！");
            }
        }
    }


    private List<HomeSearchCommunityEntity.ContentBean> searchBeanList = new ArrayList<>();
    private SearchLocalCommunityAdapter searchLocalCommunityAdapter;
    private HomeNearbyCommunityAdapter homeNearbyCommunityAdapter;
    private List<DataTree<HomeNearByEntity.ContentBean, HomeNearByEntity.ContentBean.ListBean>> dataTrees = new ArrayList<>();

    @Override
    public void OnHttpResponse(int what, String result) {

        switch (what) {
            case 0: //附近小区数据
                location_layout.setVisibility(View.GONE);
                if (TextUtils.isEmpty(result)) {
                    fail_location_layout.setVisibility(View.VISIBLE);
                } else {
                    try {
                        fail_location_layout.setVisibility(View.GONE);
                        HomeNearByEntity homeNearByEntity = GsonUtils.gsonToBean(result, HomeNearByEntity.class);
                        List<HomeNearByEntity.ContentBean> contentBeanList = homeNearByEntity.getContent();
                        dataTrees.clear();
                        int size = contentBeanList.size();
                        for (int i = 0; i < size; i++) {
                            HomeNearByEntity.ContentBean contentBean = contentBeanList.get(i);
                            List<HomeNearByEntity.ContentBean.ListBean> listBeanList = contentBean.getList();
                            if (listBeanList != null && listBeanList.size() > 0) {
                                if ("1".equals(contentBean.getCommunity_type())) {
                                    String currentCommunityuuid = mShared.getString(UserAppConst.Colour_login_community_uuid, "");
                                    HomeNearByEntity.ContentBean.ListBean currentListBean = listBeanList.get(0);
                                    selectCommunityuuid = currentListBean.getCommunity_uuid();
                                    if (!currentCommunityuuid.equals(selectCommunityuuid)) {  //当前没有默认小区
                                        selectCommunityId = currentListBean.getCzy_id();
                                        selectCommunityName = currentListBean.getName();
                                        selectCommunityAddress = currentListBean.getAddress();
                                        saveSelectCommunityData();
                                    }
                                }
                                dataTrees.add(new DataTree<HomeNearByEntity.ContentBean, HomeNearByEntity.ContentBean.ListBean>(contentBean, listBeanList));
                            }
                        }
                        initDataAdapter(dataTrees);
                    } catch (Exception e) {
                        fail_location_layout.setVisibility(View.VISIBLE);
                    }
                }
                break;
            case 1: //搜索小区
                try {
                    if (!TextUtils.isEmpty(result)) {
                        HomeSearchCommunityEntity homeSearchCommunityEntity = GsonUtils.gsonToBean(result, HomeSearchCommunityEntity.class);
                        searchBeanList.clear();
                        searchBeanList.addAll(homeSearchCommunityEntity.getContent());
                        if (searchBeanList.size() > 0) {
                            no_community_layout.setVisibility(View.GONE);
                            if (searchLocalCommunityAdapter == null) {
                                searchLocalCommunityAdapter = new SearchLocalCommunityAdapter(searchBeanList);
                                rv_searchcommunity.addItemDecoration(new SpaceItemDecoration(30));
                                rv_searchcommunity.setAdapter(searchLocalCommunityAdapter);
                            } else {
                                searchLocalCommunityAdapter.notifyDataSetChanged();
                            }
                        } else {
                            no_community_layout.setVisibility(View.VISIBLE);
                        }
                    } else {
                        no_community_layout.setVisibility(View.VISIBLE);
                    }
                    setRecycleViewListener();
                } catch (Exception e) {
                    no_community_layout.setVisibility(View.VISIBLE);
                }
                break;
            case 2:  //加入  缓存个人中心地址列表
                getSelectCommunityData();
                break;
        }
    }

    private String joinType = "2";

    private void setRecycleViewListener() {
        if (searchLocalCommunityAdapter != null) {
            searchLocalCommunityAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    HomeSearchCommunityEntity.ContentBean searchContentBean = searchBeanList.get(i);
                    selectCommunityName = searchContentBean.getName();
                    selectCommunityAddress = searchContentBean.getAddress();
                    selectCommunityId = searchContentBean.getCommunity_uuid();
                    joinType = searchContentBean.getType();
                    joinCommunity(selectCommunityName, selectCommunityId, "", "", searchContentBean.getCzy_id(), searchContentBean.getAddress(), joinType);

                }
            });
        }
    }

    private String selectCommunityName;
    private String selectCommunityAddress;
    private String selectCommunityId;
    private String selectCommunityuuid;
    private String selectBuildName;
    private String selectRoomName;
    private EditDialog noticeDialog;

    public void joinCommunity(final String communityName, final String communityuuId, final String buildName, String roomName, final String czyId, final String address, final String type) {
        joinType = type;
        if ("2".equals(type)) {
            selectCommunityName = communityName;
            selectCommunityId = czyId;
            selectCommunityuuid = communityuuId;
            selectBuildName = buildName;
            selectRoomName = roomName;
            int customerId = mShared.getInt(UserAppConst.Colour_User_id, 0);
            homeCommunityModel.joinHomeCommunity(2, customerId + "", communityuuId, communityName, address, type, this);
        } else {
            if (noticeDialog == null) {
                noticeDialog = new EditDialog(JoinCommunityActivity.this);
            }
            noticeDialog.setContent("确定加入" + communityName);
            noticeDialog.show();
            noticeDialog.left_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (noticeDialog != null) {
                        noticeDialog.dismiss();
                    }
                }
            });
            noticeDialog.right_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (noticeDialog != null) {
                        noticeDialog.dismiss();
                    }
                    selectCommunityName = communityName;
                    selectCommunityId = czyId;
                    selectCommunityuuid = communityuuId;
                    int customerId = mShared.getInt(UserAppConst.Colour_User_id, 0);
                    homeCommunityModel.joinHomeCommunity(2, customerId + "", communityuuId, communityName, address, type, JoinCommunityActivity.this);
                }
            });
        }
    }

    private void getSelectCommunityData() {
        saveSelectCommunityData();
        finish();
    }

    private void saveSelectCommunityData() {
        editor.putString(UserAppConst.Colour_login_community_uuid, selectCommunityuuid);
        editor.putString(UserAppConst.Colour_login_community_name, selectCommunityName);
        editor.putString(UserAppConst.Colour_login_community_address, selectCommunityAddress);
        editor.putString(UserAppConst.Colour_Build_name, selectBuildName);
        editor.putString(UserAppConst.Colour_Unit_name, "");
        editor.putString(UserAppConst.Colour_Room_name, selectRoomName);
        editor.commit();
        Message msg = new Message();
        msg.what = UserMessageConstant.CHANGE_COMMUNITY;
        EventBus.getDefault().post(msg);
    }
}
