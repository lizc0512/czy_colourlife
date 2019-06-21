package cn.net.cyberway.fagment;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.myproperty.activity.MyPropertyActivity;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GsonUtils;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import com.user.UserAppConst;
import com.user.UserMessageConstant;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import cn.net.cyberway.home.adapter.MyPageItemListAdapter;
import cn.net.cyberway.home.adapter.MyPageMenuAdapter;
import cn.net.cyberway.home.entity.HomeBottomAdviseEntity;
import cn.net.cyberway.home.protocol.MyOptionsGetResponse;
import cn.net.cyberway.home.protocol.OPTIONSCONTENT;
import cn.net.cyberway.home.protocol.OPTIONSDATA;
import cn.net.cyberway.home.view.CircleImageView;
import cn.net.cyberway.home.view.GuideView;
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
    private ImageView icon_my_background;
    private XListView xListView;
    private View myView;
    private RelativeLayout rl_profile_info;
    private Activity activity;
    private GuideView guideView;
    private RelativeLayout rl_home;
    private TextView tv_enter;

    private int num = 0;
    private int customer_id = 0;
    private boolean beanPoint = false;//是否有彩豆小红点
    private boolean isShow = false;//是否显示过
    private boolean hasData = false;//是否显示过
    private int millis = 500;

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
        beanPoint = mShared.getBoolean(UserAppConst.COLOUR_BEAN_SIGN_POINT + customer_id, false);
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
        try {
            String guide = mShared.getString(UserAppConst.COLOR_HOME_GUIDE_STEP + customer_id, "my");
            if ("my".equals(guide)) {//有首页引导
                icon_my_background = mView.findViewById(R.id.icon_my_background);
                for (int i = 0; i < list.size(); i++) {
                    if ("房产".equals(list.get(i).name)) {
                        num = i;
                        break;
                    }
                }
                guideTimer(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void prepareView() {
        millis = 500;
        isShow = false;
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
            case R.id.tv_enter:
                rl_home.setVisibility(View.GONE);
                guideView.hide();
                guideView = null;
                intent = new Intent(activity, MyPropertyActivity.class);
                startActivityForResult(intent, 0);
                break;
        }
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        if (message.what == UserMessageConstant.SIGN_IN_SUCCESS) {
            millis = 500;
            isShow = false;
            hasData = false;
        } else if (message.what == UserMessageConstant.CHANGE_DIFF_LANG) {
            millis = 500;
            isShow = false;
            xListView.startHeaderRefresh();
        } else if (message.what == UserMessageConstant.CHANGE_COMMUNITY) {
            millis = 500;
            isShow = false;
            xListView.startHeaderRefresh();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 0:
                if (resultCode == 1) {
                    ((MainActivity) Objects.requireNonNull(getActivity())).onTabSelected(MainActivity.FLAG_TAB_ONE);//返回首页
                    ((MainActivity) activity).delayIntoPoup(false);
                    ((MainActivity) activity).laterIntoPopup();
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

    /**
     * 遮罩引导
     */
    private void guideView() {
        rl_home = myView.findViewById(R.id.rl_home);
        rl_home.setVisibility(View.VISIBLE);
        mHandler.sendEmptyMessageDelayed(3, 400);
    }

    /**
     * 遮罩引导
     */
    private void showView() {
        RelativeLayout.LayoutParams layoutParam = (RelativeLayout.LayoutParams) rl_home.getLayoutParams();
        layoutParam.topMargin = icon_my_background.getHeight() + ((icon_my_background.getHeight() / 174) * 44) * num - 20;//图片高度 加 每个item高度
        rl_home.setLayoutParams(layoutParam);
        rl_home.post(this::guide);
    }

    /**
     * 遮罩引导
     */
    private void guide() {
        View inflate = View.inflate(getActivity(), R.layout.view_my_guide, null);
        tv_enter = inflate.findViewById(R.id.tv_enter);
        tv_enter.setOnClickListener(this);
        if (null == guideView) {
            guideView = new GuideView.Builder(getActivity())
                    .setTargetView(rl_home)
                    .setHintView(inflate)
                    .setHintViewDirection(GuideView.Direction.BOTTOM)
                    .setmForm(GuideView.Form.ELLIPSE)
                    .create();
        }
        if (!guideView.show()) {
            rl_home.setVisibility(View.GONE);
        }
        millis = 400;
    }

    private InterHandler mHandler = new InterHandler(this);

    /**
     * @param type 1 我的进入 2 延迟刷新
     */
    public void guideTimer(int type) {
        if (!isShow) {//没有显示过
            if (1 == type) {
                isShow = true;
                mHandler.sendEmptyMessageDelayed(1, millis);
            } else if (2 == type) {
                hasData = false;
                mHandler.sendEmptyMessageDelayed(2, 400);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mHandler.removeCallbacksAndMessages(null);
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

    private static class InterHandler extends Handler {
        private WeakReference<ProfileFragment> mActivity;

        InterHandler(ProfileFragment activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            ProfileFragment activity = mActivity.get();
            if (activity != null) {
                switch (msg.what) {
                    case 1:
                        activity.guideView();
                        break;
                    case 2:
                        activity.initData();
                        break;
                    case 3:
                        activity.showView();
                }
            } else {
                super.handleMessage(msg);
            }
        }
    }
}
