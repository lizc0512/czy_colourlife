package cn.net.cyberway.fagment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.activity.CustomerInfoActivity;
import com.dashuview.library.keep.ListenerUtils;
import com.dashuview.library.keep.MyListener;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.IXListViewListener;
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

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
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

public class ProfileFragment extends Fragment implements View.OnClickListener, IXListViewListener, MyListener, NewHttpResponse {
    private View mView;
    private CircleImageView mHeadImg;
    private TextView mUsername;
    private TextView mCommunity;
    private ListView lv_myprofile_info;
    private RecyclerView rv_property_menu;
    private MyPageItemListAdapter myItemListAdapter;
    private MyListModel myListModel;
    private ArrayList<OPTIONSDATA> list = new ArrayList();
    private SharedPreferences mShared;
    public SharedPreferences.Editor mEditor;
    private XListView xListView;
    private View myView;
    private RelativeLayout rl_profile_info;
    private Activity activity;

    private int customer_id = 0;
    private boolean beanPoint = false;//是否有彩豆小红点

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_personal_listview, container, false);
        mShared = getActivity().getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        activity = getActivity();
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
        String listCache = mShared.getString(UserAppConst.MYPAGELIST, "");
        String subMenuCache = mShared.getString(MYPAGESUBMENU, "");
        if (!TextUtils.isEmpty(listCache)) {
            showProfileData(listCache);
            myListModel.getmypageList(0, false, ProfileFragment.this);
        } else {
            xListView.startHeaderRefresh();
        }
        if (!TextUtils.isEmpty(subMenuCache)) {
            showSubMenuData(subMenuCache);
            myListModel.getMySubMenuList(1, ProfileFragment.this);
        } else {
            xListView.startHeaderRefresh();
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
        if (null != activity) {
            if (myItemListAdapter == null) {
                myItemListAdapter = new MyPageItemListAdapter(activity, list);
                lv_myprofile_info.setAdapter(myItemListAdapter);
            } else {
                myItemListAdapter.setData(list);
            }
        }
    }


    private void prepareView() {
        myView = LayoutInflater.from(getActivity()).inflate(R.layout.activity_personal_center, null);
        xListView = (XListView) mView.findViewById(R.id.mypage_home_list);
        xListView.setAdapter(null);
        xListView.setPullRefreshEnable(true);
        xListView.loadMoreHide();
        xListView.setPullLoadEnable(false);
        xListView.addHeaderView(myView);
        xListView.setXListViewListener(this, 0);
        lv_myprofile_info = (ListView) myView.findViewById(R.id.lv_myprofile_info);
        rv_property_menu = myView.findViewById(R.id.rv_property_menu);
        mHeadImg = (CircleImageView) myView.findViewById(R.id.profile_head_img);
        mUsername = (TextView) myView.findViewById(R.id.profile_username);
        mCommunity = (TextView) myView.findViewById(R.id.profile_community);
        rl_profile_info = (RelativeLayout) myView.findViewById(R.id.rl_profile_info);
        rl_profile_info.setOnClickListener(this);
        TCAgent.onEvent(getActivity(), "203001");
        ListenerUtils.setCallBack(this);
        lv_myprofile_info.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0) {
                    OPTIONSDATA optionsdata = list.get(position);
                    LinkParseUtil.parse(getActivity(), optionsdata.url, optionsdata.name);
                }
            }
        });
    }

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
        }
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        if (message.what == UserMessageConstant.SIGN_IN_SUCCESS) {
        } else if (message.what == UserMessageConstant.CHANGE_DIFF_LANG) {
            xListView.startHeaderRefresh();
        } else if (message.what == UserMessageConstant.CHANGE_COMMUNITY) {
            xListView.startHeaderRefresh();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 6:
                if (resultCode == 1) {
                    this.onResume();
                }
                break;
        }
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("我的");
        TCAgent.onPageEnd(getActivity(), "我的");
    }

    @Override
    public void onRefresh(int id) {
        myListModel.getmypageList(0, false, ProfileFragment.this);
        myListModel.getMySubMenuList(1, ProfileFragment.this);
    }

    @Override
    public void onLoadMore(int id) {
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
                mEditor.putString(UserAppConst.MYPAGELIST, result).apply();
                showProfileData(result);
                break;
            case 1:
                questNum++;
                mEditor.putString(MYPAGESUBMENU, result).apply();
                showSubMenuData(result);
                break;
        }
        if (questNum == 2) {
            xListView.stopRefresh();
            questNum = 0;
        }
    }

}
