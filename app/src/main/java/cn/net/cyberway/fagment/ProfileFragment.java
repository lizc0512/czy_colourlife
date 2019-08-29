package cn.net.cyberway.fagment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.model.Constants;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.activity.CustomerInfoActivity;
import com.customerInfo.activity.CustomerMakeZXingActivity;
import com.dashuview.library.keep.ListenerUtils;
import com.dashuview.library.keep.MyListener;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.XListView;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import com.user.UserAppConst;
import com.user.UserMessageConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.csh.colourful.life.view.recycleview.WrapHeightLinearLayoutManager;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import cn.net.cyberway.home.adapter.MyPageItemListAdapter;
import cn.net.cyberway.home.adapter.MyPageMenuAdapter;
import cn.net.cyberway.home.entity.HomeBottomAdviseEntity;
import cn.net.cyberway.home.protocol.MyOptionsGetResponse;
import cn.net.cyberway.home.protocol.OPTIONSCONTENT;
import cn.net.cyberway.home.protocol.OPTIONSDATA;
import cn.net.cyberway.home.view.CircleImageView;
import cn.net.cyberway.model.MyListModel;
import cn.net.cyberway.utils.LinkParseUtil;

import static com.user.UserAppConst.MYPAGESUBMENU;


/**
 * 个人中心
 */

public class ProfileFragment extends Fragment implements View.OnClickListener, MyListener, NewHttpResponse {
    private View mView;
    private CircleImageView mHeadImg;
    private TextView mUsername;
    private TextView mCommunity;
    private RecyclerView lv_myprofile_info;
    private RecyclerView rv_property_menu;
    private MyPageItemListAdapter myItemListAdapter;
    private MyListModel myListModel;
    private ArrayList<OPTIONSDATA> list = new ArrayList();
    private SharedPreferences mShared;
    public SharedPreferences.Editor mEditor;
    private XListView xListView;
    private SwipeRefreshLayout refresh_layout;
    private View myView;
    private RelativeLayout rl_profile_info;
    private int customer_id = 0;
    private boolean beanPoint = false;//是否有彩豆小红点


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_personal_listview, container, false);
        mShared = getActivity().getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        if (!EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().register(this);
        }
        prepareView();
        initData();
        return mView;
    }

    public void initData() {
        if (null == myListModel) {
            myListModel = new MyListModel(getActivity());
        }
        customer_id = mShared.getInt(UserAppConst.Colour_User_id, 0);
        beanPoint = mShared.getBoolean(UserAppConst.COLOUR_BEAN_SIGN_POINT + customer_id, false);
        String listCache = mShared.getString(UserAppConst.MYPAGELIST, "");
        String subMenuCache = mShared.getString(MYPAGESUBMENU, "");
        if (!TextUtils.isEmpty(listCache)) {
            isLoading = false;
            showProfileData(listCache);
            myListModel.getmypageList(0, isLoading, ProfileFragment.this);
        }
        if (!TextUtils.isEmpty(subMenuCache)) {
            isLoading = false;
            showSubMenuData(subMenuCache);
            myListModel.getMySubMenuList(1, isLoading, ProfileFragment.this);
        }
        if (TextUtils.isEmpty(listCache) || TextUtils.isEmpty(subMenuCache)) {
            getData();
        }
    }


    private void showProfileData(String result) {
        JSONObject object = null;
        try {
            object = new JSONObject(result);
            MyOptionsGetResponse myOptionsGetResponse = new MyOptionsGetResponse();
            myOptionsGetResponse.fromJson(object);
            ArrayList<OPTIONSCONTENT> myitemlist = myOptionsGetResponse.content;
            list.clear();
            for (int i = 0; i < myitemlist.size(); i++) {
                list.addAll(myitemlist.get(i).data);
            }
            myInfoAdapter(list);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private MyPageMenuAdapter myPageMenuAdapter;
    private List<HomeBottomAdviseEntity.ContentBean> contentBeanList = new ArrayList<>();

    private void showSubMenuData(String result) {
        try {
            contentBeanList.clear();
            HomeBottomAdviseEntity homeBottomAdviseEntity = GsonUtils.gsonToBean(result, HomeBottomAdviseEntity.class);
            contentBeanList.addAll(homeBottomAdviseEntity.getContent());
            if (null == myPageMenuAdapter) {
                myPageMenuAdapter = new MyPageMenuAdapter(getActivity(), contentBeanList);
                rv_property_menu.setLayoutManager(new GridLayoutManager(getActivity(), 3));
                rv_property_menu.setAdapter(myPageMenuAdapter);
            } else {
                myPageMenuAdapter.notifyDataSetChanged();
            }
        } catch (Exception e) {

        }
        if (null != myPageMenuAdapter) {
            myPageMenuAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    if (i >= 0) {
                        HomeBottomAdviseEntity.ContentBean contentBean = contentBeanList.get(i);
                        LinkParseUtil.parse(getActivity(), contentBean.getUrl(), "");
                    }
                }
            });
        }
    }

    /**
     * 我的ITEM数据适配
     *
     * @param list
     */
    private void myInfoAdapter(ArrayList<OPTIONSDATA> list) {
        try {
            if (myItemListAdapter == null) {
                myItemListAdapter = new MyPageItemListAdapter(getActivity(), list);
                lv_myprofile_info.setLayoutManager(new WrapHeightLinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
                lv_myprofile_info.setNestedScrollingEnabled(false);
                lv_myprofile_info.setAdapter(myItemListAdapter);
            } else {
                myItemListAdapter.setData(list);
            }
        } catch (Exception e) {

        }
        if (myItemListAdapter != null) {
            myItemListAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    if (position >= 0) {
                        OPTIONSDATA optionsdata = list.get(position);
                        LinkParseUtil.parse(getActivity(), optionsdata.url, optionsdata.name);
                    }
                }
            });
        }
    }


    private void prepareView() {
        myView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_personal_center, null);
        xListView = mView.findViewById(R.id.mypage_home_list);
        refresh_layout = mView.findViewById(R.id.refresh_layout);
        refresh_layout.setColorSchemeColors(Color.parseColor("#3290FF"), Color.parseColor("#6ABDF9"));
        xListView.setAdapter(null);
        xListView.setPullRefreshEnable(false);
        xListView.setPullLoadEnable(false);
        xListView.stopRefresh();
        xListView.stopLoadMore();
        xListView.loadMoreHide();
        xListView.addHeaderView(myView);
        lv_myprofile_info = myView.findViewById(R.id.lv_myprofile_info);
        rv_property_menu = myView.findViewById(R.id.rv_property_menu);
        mHeadImg = myView.findViewById(R.id.profile_head_img);
        mUsername = myView.findViewById(R.id.profile_username);
        mCommunity = myView.findViewById(R.id.profile_community);
        ImageView iv_qr_code = myView.findViewById(R.id.iv_qr_code);
        rl_profile_info = myView.findViewById(R.id.rl_profile_info);
        rl_profile_info.setOnClickListener(this);
        iv_qr_code.setOnClickListener(this);
        TCAgent.onEvent(getActivity(), "203001");
        ListenerUtils.setCallBack(this);
        refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                myListModel.getmypageList(0, isLoading, ProfileFragment.this);
                myListModel.getMySubMenuList(1, isLoading, ProfileFragment.this);
            }
        });
    }

    private boolean isLoading;

    @Override
    public void onResume() {
        super.onResume();
        try {
            String nickname = mShared.getString(UserAppConst.Colour_NIACKNAME, "");
            String name = mShared.getString(UserAppConst.Colour_NAME, "");
            if (!TextUtils.isEmpty(name)) {
                mUsername.setText(name);
            } else if (!TextUtils.isEmpty(nickname)) {
                mUsername.setText(nickname);
            } else {
                mUsername.setText("彩多多");
            }
            mCommunity.setText(mShared.getString(UserAppConst.Colour_login_community_name, ""));
            String headImgUrl = mShared.getString(UserAppConst.Colour_head_img, "");
            GlideImageLoader.loadImageDefaultDisplay(getActivity(), headImgUrl, mHeadImg, R.drawable.icon_my_tx, R.drawable.icon_my_tx);
            MobclickAgent.onPageStart("我的");
            TCAgent.onPageStart(getActivity(), "我的");
            boolean isLogin = mShared.getBoolean(UserAppConst.IS_LOGIN, false);
            if (isLogin) {
//                ((MainActivity) getActivity()).changeStyle();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!isHidden()) {
            boolean isLogin = mShared.getBoolean(UserAppConst.IS_LOGIN, false);
            if (isLogin) {
//                ((MainActivity) getActivity()).changeStyle();
            }
        }
    }


    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.rl_profile_info:
                TCAgent.onEvent(getActivity(), "203002");
                intent = new Intent(getActivity(), CustomerInfoActivity.class);
                startActivityForResult(intent, 6);
                break;
            case R.id.iv_qr_code:
                intent = new Intent(getActivity(), CustomerInfoActivity.class);
                startActivity(intent);
                break;
        }
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        if (message.what == UserMessageConstant.CHANGE_DIFF_LANG) {
            getData();
        } else if (message.what == UserMessageConstant.CHANGE_COMMUNITY) {
            getData();
        }
    }

    private void getData() {
        questNum = 0;
        myListModel.getmypageList(0, true, ProfileFragment.this);
        myListModel.getMySubMenuList(1, true, ProfileFragment.this);
        refresh_layout.setRefreshing(true);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == 1) {
                    ((MainActivity) Objects.requireNonNull(getActivity())).onTabSelected(MainActivity.FLAG_TAB_ONE);//返回首页
                }
                break;
            case 1:
                boolean beanChange = mShared.getBoolean(UserAppConst.COLOUR_BEAN_SIGN_POINT + customer_id, false);
                if (beanChange != beanPoint && list.size() > 0) {//已签到，隐藏小红点
                    myInfoAdapter(list);
                }
                break;
            case 6:
                if (resultCode == 1) {
                    this.onResume();
                }
                break;
        }
    }

    public void onPause() {
        super.onPause();
        beanPoint = mShared.getBoolean(UserAppConst.COLOUR_BEAN_SIGN_POINT + customer_id, false);//防止切换用户没变化状态
        MobclickAgent.onPageEnd("我的");
        TCAgent.onPageEnd(getActivity(), "我的");
    }

    @Override
    public void authenticationFeedback(String s, int i) {
        switch (i) {
            case 14:
                ToastUtil.toastShow(getActivity(), getResources().getString(R.string.open_wallet_success));
                break;
            case 15:
                ToastUtil.toastShow(getActivity(), getResources().getString(R.string.cancel_open_wallet));
                break;
        }
    }

    @Override
    public void toCFRS(String s) {
        if (!TextUtils.isEmpty(s)) {
            LinkParseUtil.parse(getActivity(), s, "");
        }
    }


    private int questNum = 0;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                questNum++;
                if (!TextUtils.isEmpty(result)) {
                    mEditor.putString(UserAppConst.MYPAGELIST, result).apply();
                    showProfileData(result);
                    isLoading = false;
                } else {
                    String mySubMenu = mShared.getString(MYPAGESUBMENU, Constants.myPageMainList);
                    showProfileData(mySubMenu);
                    isLoading = true;
                }
                break;
            case 1:
                questNum++;
                if (!TextUtils.isEmpty(result)) {
                    mEditor.putString(MYPAGESUBMENU, result).apply();
                    showSubMenuData(result);
                    isLoading = false;
                } else {
                    String mySubMenu = mShared.getString(MYPAGESUBMENU, Constants.mySubMenuList);
                    showProfileData(mySubMenu);
                    isLoading = true;
                }
                break;
        }
        if (questNum == 2) {
            refresh_layout.setRefreshing(false);
            questNum = 0;
        }
    }
}
