package cn.net.cyberway.fagment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Message;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.BeeFramework.Utils.NetworkUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.external.eventbus.EventBus;
import com.feed.activity.LinLiActivity;
import com.im.activity.IMAddFriendActivity;
import com.im.activity.IMCommunityListActivity;
import com.im.activity.IMCreateCommunityActivity;
import com.im.activity.IMCreateGroupChatActivity;
import com.im.activity.IMFriendAndGroupActivity;
import com.im.activity.IMSearchAllActivity;
import com.im.adapter.IMPopAdapter;
import com.im.adapter.InStantMessageAdapter;
import com.im.helper.CacheApplyRecorderHelper;
import com.im.view.DeleteMsgDialog;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.ProtoCallback;
import com.youmai.hxsdk.chatgroup.IMGroupActivity;
import com.youmai.hxsdk.chatsingle.IMConnectionActivity;
import com.youmai.hxsdk.data.ExCacheMsgBean;
import com.youmai.hxsdk.db.bean.CacheMsgBean;
import com.youmai.hxsdk.im.IMMsgCallback;
import com.youmai.hxsdk.im.IMMsgManager;
import com.youmai.hxsdk.utils.AppUtils;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import q.rorbin.badgeview.QBadgeView;

import static cn.net.cyberway.utils.TableLayoutUtils.jumpLoginPage;
import static com.youmai.hxsdk.utils.DisplayUtil.getStatusBarHeight;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.fagment
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/13 14:26
 * @change
 * @chang time
 * @class describe  即时通讯消息页面
 */

public class InstantMessageFragment extends Fragment implements View.OnClickListener, IMMsgCallback {

    public static final int INTENT_REQUEST_FOR_UPDATE_UI = 101;
    private SharedPreferences mShared;

    private ImageView iv_phone_book;
    private ImageView iv_add_friend;
    private RelativeLayout search_layout;
    private LinearLayout net_error_layout;
    private LinearLayout empty_msg_layout;
    private SwipeMenuRecyclerView message_rv;
    private QBadgeView badgeView;
    private InStantMessageAdapter mMessageAdapter;
    private boolean isFrist = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShared = getActivity().getSharedPreferences(UserAppConst.USERINFO, 0);
        if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
            isFrist = true;
            initData();
        }
    }

    private void initData() {
        HuxinSdkManager.instance().chatMsgFromCache(getActivity(),
                new ProtoCallback.CacheMsgCallBack() {
                    @Override
                    public void result(List<ExCacheMsgBean> data) {
                        mMessageAdapter.changeMessageList(data);
                        showEmptyLayout();
                        initUnreadList();
                    }
                });
    }

    private void showEmptyLayout() {
        List<ExCacheMsgBean> data = mMessageAdapter.getMsgList();
        if (data.size() == 0) {
            empty_msg_layout.setVisibility(View.VISIBLE);
        } else {
            empty_msg_layout.setVisibility(View.GONE);
        }
    }

    private void setTabViewHeight(View tabBarView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(getActivity()));
        tabBarView.setLayoutParams(layoutParams);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        EventBus.getDefault().register(this);
        View rootView = inflater.inflate(R.layout.fragment_instant_message, container, false);
        View life_tabbar_view = rootView.findViewById(R.id.instant_tabbar_view);
        life_tabbar_view.setBackgroundColor(Color.parseColor("#ffffff"));
        setTabViewHeight(life_tabbar_view);
        iv_phone_book = rootView.findViewById(R.id.iv_phone_book);
        iv_add_friend = rootView.findViewById(R.id.iv_add_friend);
        search_layout = rootView.findViewById(R.id.search_layout);
        empty_msg_layout = rootView.findViewById(R.id.empty_msg_layout);
        net_error_layout = rootView.findViewById(R.id.net_error_layout);
        message_rv = rootView.findViewById(R.id.message_rv);
        iv_phone_book.setOnClickListener(this);
        iv_add_friend.setOnClickListener(this);
        search_layout.setOnClickListener(this);
        net_error_layout.setOnClickListener(this);
        badgeView = new QBadgeView(getActivity());
        badgeView.bindTarget(iv_phone_book);
        badgeView.setBadgeGravity(Gravity.END | Gravity.TOP);
        badgeView.setBadgeTextSize(8f, true);
        badgeView.setBadgeBackgroundColor(ContextCompat.getColor(getActivity(), R.color.hx_color_red_tag));
        badgeView.setShowShadow(false);
        int size = CacheApplyRecorderHelper.instance().toQueryApplyRecordSize(getActivity(), "0");
        if (mShared.getBoolean(UserAppConst.IM_APPLY_FRIEND, false)) {
            badgeView.setBadgeNumber(size);
        } else {
            badgeView.setBadgeNumber(0);
        }
        mMessageAdapter = new InStantMessageAdapter(getActivity());
        message_rv.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器。
        message_rv.setSwipeMenuCreator(swipeMenuCreator);
        message_rv.setSwipeMenuItemClickListener(mMenuItemClickListener);
        message_rv.setAdapter(mMessageAdapter);
        mMessageAdapter.setOnItemClickListener(new InStantMessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ExCacheMsgBean bean, int position) {
                if (bean.getUiType() == InStantMessageAdapter.ADAPTER_TYPE_SINGLE) {
                    Intent intent = new Intent(getActivity(), IMConnectionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(IMConnectionActivity.DST_UUID, bean.getTargetUuid());
                    intent.putExtra(IMConnectionActivity.DST_NAME, bean.getDisplayName());
                    intent.putExtra(IMConnectionActivity.DST_USERNAME, bean.getTargetUserName());
                    intent.putExtra(IMConnectionActivity.DST_AVATAR, bean.getTargetAvatar());
                    startActivityForResult(intent, INTENT_REQUEST_FOR_UPDATE_UI);
                } else if (bean.getUiType() == InStantMessageAdapter.ADAPTER_TYPE_GROUP) {
                    Intent intent = new Intent(getActivity(), IMGroupActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    int groupId = bean.getGroupId();
                    intent.putExtra(IMGroupActivity.DST_UUID, groupId);
                    intent.putExtra(IMGroupActivity.GROUP_TYPE, bean.getGroupType());
                    intent.putExtra(IMGroupActivity.DST_NAME, bean.getDisplayName());
                    startActivityForResult(intent, INTENT_REQUEST_FOR_UPDATE_UI);
                }
            }
        });
        setLoginStatus();
        return rootView;
    }

    private void setLoginStatus() {
        HuxinSdkManager.LoginStatusListener listener = new HuxinSdkManager.LoginStatusListener() {
            @Override
            public void onKickOut() {
                if (isVisible()) {
                    Message msg = new Message();
                    msg.what = UserMessageConstant.LOGOUT;//退出登录之后，
                    EventBus.getDefault().post(msg);
                    if (null != getActivity()) {
                        ToastUtil.toastShow(getActivity(),"你的账号在其他地方登录");
                        jumpLoginPage(getActivity(), mShared, 1000);
                    }
                }
            }

            @Override
            public void onReLoginSuccess() {

            }
        };
        HuxinSdkManager.instance().setLoginStatusListener(listener);
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = (int) (90 * getResources().getDisplayMetrics().density + 0.5f);
            // MATCH_PARENT 自适应高度，保持和内容一样高；也可以指定菜单具体高度，也可以用WRAP_CONTENT。
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            String content = "";
            String realType = String.valueOf(viewType);
            if (realType.startsWith("1")) {
                content = getActivity().getResources().getString(R.string.im_cancle_stick);
            } else {
                content = getActivity().getResources().getString(R.string.im_stick);
            }
            SwipeMenuItem topItem = new SwipeMenuItem(getActivity())
                    .setBackground(new ColorDrawable(Color.rgb(0xc7, 0xc7, 0xc7)))
                    .setText(content) // 文字。
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(18) // 文字大小。
                    .setWidth(width)
                    .setHeight(height);
            SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                    .setBackground(new ColorDrawable(Color.rgb(0xff, 0x00, 0x00)))
                    .setText(getActivity().getResources().getString(R.string.im_delete)) // 文字。
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(18) // 文字大小。
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(topItem);// 添加一个按钮到右侧侧菜单。.
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };

    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position
            int position = menuBridge.getPosition();
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                if (position == 0) {
                    List<ExCacheMsgBean> list = mMessageAdapter.getMsgList();
                    ExCacheMsgBean bean = list.get(adapterPosition);
                    boolean isTop = AppUtils.getBooleanSharedPreferences(getContext(), "top" + bean.getTargetUuid(), false);
                    if (!isTop) {
                        bean.setTop(true);
                        AppUtils.setBooleanSharedPreferences(getContext(), "top" + bean.getTargetUuid(), true);
                        mMessageAdapter.addTop(bean);
                        ToastUtil.toastShow(getActivity(), getActivity().getResources().getString(R.string.stick_top));
                    } else {
                        bean.setTop(false);
                        AppUtils.setBooleanSharedPreferences(getContext(), "top" + bean.getTargetUuid(), false);
                        mMessageAdapter.cancelTop(bean);
                        ToastUtil.toastShow(getActivity(), getActivity().getResources().getString(R.string.stick_cancel));
                    }
                } else {
                    showDelNotice(adapterPosition);
                }
            }
        }
    };


    private void showDelNotice(final int adapterPosition) {
        final DeleteMsgDialog deleteMsgDialog = new DeleteMsgDialog(getActivity(), R.style.custom_dialog_theme);
        deleteMsgDialog.show();
        deleteMsgDialog.btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteMsgDialog.dismiss();
            }
        });
        deleteMsgDialog.btn_define.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<ExCacheMsgBean> list = mMessageAdapter.getMsgList();
                String targetUuid = list.get(adapterPosition).getTargetUuid();
                mMessageAdapter.deleteMessage(targetUuid);
                HuxinSdkManager.instance().delMsgChat(targetUuid);
                deleteMsgDialog.dismiss();
                initUnreadList();
                showEmptyLayout();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_phone_book:
                Intent friendIntent = new Intent(getActivity(), IMFriendAndGroupActivity.class);
                startActivity(friendIntent);
                break;
            case R.id.iv_add_friend:
                initPopup();
                break;
            case R.id.search_layout:
                Intent searchIntent = new Intent(getActivity(), IMSearchAllActivity.class);
                startActivity(searchIntent);
                break;
            case R.id.net_error_layout:
                Intent intent = new Intent(Settings.ACTION_SETTINGS);
                startActivity(intent);
                break;
        }
    }

    private PopupWindow popupWindow;

    private void initPopup() {
        View contentview = LayoutInflater.from(getActivity()).inflate(R.layout.pop_listview_layout, null);
        popupWindow = new PopupWindow(contentview,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        setBackgroundAlpha(0.8f);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(iv_add_friend, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        ListView popLv = contentview.findViewById(R.id.pop_lv);
        popLv.setAdapter(new IMPopAdapter(getActivity()));
        popLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                Intent intent = new Intent(getActivity(), classArr[position]);
                startActivity(intent);
            }
        });
    }

    private Class[] classArr = {IMCreateGroupChatActivity.class, IMAddFriendActivity.class, IMCreateCommunityActivity.class, LinLiActivity.class, IMCommunityListActivity.class};

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    private void showApplyNumber() {
        if (null != badgeView) {
            int size = CacheApplyRecorderHelper.instance().toQueryApplyRecordSize(getActivity(), "0");
            if (mShared.getBoolean(UserAppConst.IM_APPLY_FRIEND, false)) {
                badgeView.setBadgeNumber(size);
            } else {
                badgeView.setBadgeNumber(0);
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        HuxinSdkManager.instance().setImMsgCallback(this);
//        ((MainActivity) getActivity()).changeStyle();
        showApplyNumber();
        initUnreadList();
        String userUUid = mShared.getString(UserAppConst.Colour_User_uuid, "");
        boolean cMHelperNotice = mShared.getBoolean(UserAppConst.IM_CMANGAG_ERHELPER + userUUid, false);
        showCommmunityManager(cMHelperNotice);
        if (NetworkUtil.isConnect(getActivity())) {
            setNetWorkStatusLayout(1);
        } else {
            setNetWorkStatusLayout(0);
        }
        if (HuxinSdkManager.instance().isKicked()) {
            if (null != getActivity()) {
                jumpLoginPage(getActivity(), mShared, 1000);
            }
        }
    }

    private void showCommmunityManager(boolean isShow) {
        if (isShow) {
            if (null == cMHelperBadgeView) {
                cMHelperBadgeView = new QBadgeView(getActivity());
            }
            cMHelperBadgeView.bindTarget(iv_add_friend);
            cMHelperBadgeView.setBadgeGravity(Gravity.TOP | Gravity.END);
            cMHelperBadgeView.setBadgeTextSize(6f, true);
            cMHelperBadgeView.setGravityOffset(0.5f, 0.5f, true);
            cMHelperBadgeView.setBadgePadding(0.5f, true);
            cMHelperBadgeView.setBadgeBackgroundColor(ContextCompat.getColor(getActivity(), R.color.hx_color_red_tag));
            cMHelperBadgeView.setShowShadow(false);
            cMHelperBadgeView.setBadgeText(" ");
            cMHelperBadgeView.setVisibility(View.VISIBLE);
        } else {
            if (null != cMHelperBadgeView) {
                cMHelperBadgeView.setBadgeText("");
                cMHelperBadgeView.setVisibility(View.GONE);
            }
        }
    }

    private QBadgeView cMHelperBadgeView = null;


    @Override
    public void onStop() {
        super.onStop();
        HuxinSdkManager.instance().removeImMsgCallback(this);
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
//            ((MainActivity) getActivity()).changeStyle();
        }
    }

    private void initUnreadList() {
        int totalUnRead = HuxinSdkManager.instance().unreadBuddyAndCommMessage();
        ((MainActivity) getActivity()).showUnReadMsg(totalUnRead);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(getActivity())) {
            EventBus.getDefault().unregister(this);
        }
    }

    public void onEvent(Object event) {
        final Message message = (Message) event;
        switch (message.what) {
            case UserMessageConstant.LOGOUT:
            case UserMessageConstant.SQUEEZE_OUT:
            case UserMessageConstant.WEB_OUT:
            case UserMessageConstant.AUDIT_PASS_OUT:
                if (HuxinSdkManager.instance().isLogin()) {
                    HuxinSdkManager.instance().loginOut();
                    IMMsgManager.instance().cancelPushMsg();
                }
                break;
            case UserMessageConstant.GET_APPLY_NUMBER:
                showApplyNumber();
                break;
            case UserMessageConstant.COMMUNITY_MANAGER_NOTICE:
                showCommmunityManager(true);
                break;
            case UserMessageConstant.NET_CONN_CHANGE:
                int netWorkType = message.arg1;
                if (netWorkType == 1) {
                    setNetWorkStatusLayout(1);
                } else {
                    if (NetworkUtil.isConnect(getActivity())) {
                        setNetWorkStatusLayout(1);
                    } else {
                        setNetWorkStatusLayout(0);
                    }
                }
            case UserMessageConstant.SIGN_IN_SUCCESS:
                if (isFrist) {
                    mMessageAdapter.clearAllMessage();
                    initData();
                }
                break;
        }
    }

    private void setNetWorkStatusLayout(int netStatus) {
        if (null != net_error_layout) {
            if (netStatus == 1) {
                net_error_layout.setVisibility(View.GONE);
            } else {
                net_error_layout.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onBuddyMsgCallback(CacheMsgBean cacheMsgBean) {
        updateMsgList(cacheMsgBean);
    }

    @Override
    public void onCommunityMsgCallback(CacheMsgBean cacheMsgBean) {
        updateMsgList(cacheMsgBean);
    }

    @Override
    public void onOwnerMsgCallback(CacheMsgBean cacheMsgBean) {

    }

    private void updateMsgList(CacheMsgBean cacheMsgBean) {
        if (cacheMsgBean != null) {
            ExCacheMsgBean bean = new ExCacheMsgBean(cacheMsgBean);
            String targetId = bean.getTargetUuid();
            boolean isTop = AppUtils.getBooleanSharedPreferences(getContext(), "top" + targetId, false);
            if (isTop) {
                bean.setTop(true);
            }
            bean.setDisplayName(cacheMsgBean.getTargetName());
            mMessageAdapter.addTop(bean);
            initUnreadList();
            showEmptyLayout();
        }
    }
}
