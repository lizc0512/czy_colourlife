package com.community.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.community.entity.CommunityDynamicRemindEntity;
import com.community.model.CommunityDynamicsModel;
import com.im.activity.IMAddFriendActivity;
import com.im.activity.IMApplyFriendRecordActivity;
import com.im.activity.IMFriendAndGroupActivity;
import com.im.activity.IMGroupListActivity;
import com.im.adapter.InStantMessageAdapter;
import com.im.helper.CacheApplyRecorderHelper;
import com.im.view.DeleteMsgDialog;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.chatgroup.IMGroupActivity;
import com.youmai.hxsdk.chatsingle.IMConnectionActivity;
import com.youmai.hxsdk.data.ExCacheMsgBean;
import com.youmai.hxsdk.db.bean.CacheMsgBean;
import com.youmai.hxsdk.im.IMMsgCallback;
import com.youmai.hxsdk.utils.AppUtils;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import q.rorbin.badgeview.QBadgeView;

import static com.user.UserAppConst.COLOUR_DYNAMICS_NOTICE_NUMBER;

/*
 *  社区消息列表的
 *
 * */
public class CommunityMessageListActivity extends BaseActivity implements View.OnClickListener, IMMsgCallback, NewHttpResponse {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ImageView img_right;
    private LinearLayout no_data_layout;
    private SwipeMenuRecyclerView message_rv;
    private InStantMessageAdapter mMessageAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_message);
        initView();
        addTopView();
        HuxinSdkManager.instance().chatMsgFromCache(CommunityMessageListActivity.this,
                data -> {
                    mMessageAdapter.changeMessageList(data);
                });
        getNoticeUnReadCount();

    }

    private void getNoticeUnReadCount() {
        CommunityDynamicsModel communityDynamicsModel = new CommunityDynamicsModel(CommunityMessageListActivity.this);
        communityDynamicsModel.getDynamicRemindCount(0, CommunityMessageListActivity.this);
    }


    private void updateMsgList(CacheMsgBean cacheMsgBean) {
        if (cacheMsgBean != null) {
            ExCacheMsgBean bean = new ExCacheMsgBean(cacheMsgBean);
            String targetId = bean.getTargetUuid();
            boolean isTop = AppUtils.getBooleanSharedPreferences(CommunityMessageListActivity.this, "top" + targetId, false);
            if (isTop) {
                bean.setTop(true);
            }
            bean.setDisplayName(cacheMsgBean.getTargetName());
            mMessageAdapter.addTop(bean);
        }
    }


    private void showUnReadCount() {
        int newFriendApplyCount = CacheApplyRecorderHelper.instance().toQueryApplyRecordSize(CommunityMessageListActivity.this, "0");
        if (shared.getBoolean(UserAppConst.IM_APPLY_FRIEND, false)) {
            newApplyNoticeBg.setBadgeNumber(newFriendApplyCount);
        } else {
            newApplyNoticeBg.setBadgeNumber(0);
        }
        int unReadNoticeCount = shared.getInt(COLOUR_DYNAMICS_NOTICE_NUMBER, 0);
        unReadNoticeBg.setBadgeNumber(unReadNoticeCount);
    }

    @Override
    protected void onResume() {
        super.onResume();
        showUnReadCount();
        HuxinSdkManager.instance().setImMsgCallback(this);
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        message_rv = findViewById(R.id.message_rv);
        no_data_layout = findViewById(R.id.no_data_layout);
        img_right = findViewById(R.id.img_right);
        mMessageAdapter = new InStantMessageAdapter(CommunityMessageListActivity.this);
        message_rv.setLayoutManager(new LinearLayoutManager(CommunityMessageListActivity.this));// 布局管理器。
        message_rv.setSwipeMenuCreator(swipeMenuCreator);
        message_rv.setSwipeMenuItemClickListener(mMenuItemClickListener);
        message_rv.setAdapter(mMessageAdapter);
        user_top_view_back.setOnClickListener(this::onClick);
        img_right.setOnClickListener(this::onClick);
        img_right.setVisibility(View.VISIBLE);
        img_right.setImageResource(R.drawable.im_icon_nav_friends);
        user_top_view_title.setText(getResources().getString(R.string.community_message_list));
        mMessageAdapter.setOnItemClickListener(new InStantMessageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(ExCacheMsgBean bean, int position) {
                if (bean.getUiType() == InStantMessageAdapter.ADAPTER_TYPE_SINGLE) {
                    Intent intent = new Intent(CommunityMessageListActivity.this, IMConnectionActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    intent.putExtra(IMConnectionActivity.DST_UUID, bean.getTargetUuid());
                    intent.putExtra(IMConnectionActivity.DST_NAME, bean.getDisplayName());
                    intent.putExtra(IMConnectionActivity.DST_USERNAME, bean.getTargetUserName());
                    intent.putExtra(IMConnectionActivity.DST_AVATAR, bean.getTargetAvatar());
                    startActivity(intent);
                } else if (bean.getUiType() == InStantMessageAdapter.ADAPTER_TYPE_GROUP) {
                    Intent intent = new Intent(CommunityMessageListActivity.this, IMGroupActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    int groupId = bean.getGroupId();
                    intent.putExtra(IMGroupActivity.DST_UUID, groupId);
                    intent.putExtra(IMGroupActivity.GROUP_TYPE, bean.getGroupType());
                    intent.putExtra(IMGroupActivity.DST_NAME, bean.getDisplayName());
                    startActivity(intent);
                }
            }
        });
    }

    private RelativeLayout new_friend_layout;
    private ImageView iv_new_friend;
    private RelativeLayout community_group_layout;
    private RelativeLayout dynamics_notice_layout;
    private ImageView iv_dynamic_notice;
    private QBadgeView unReadNoticeBg;
    private QBadgeView newApplyNoticeBg;

    private void addTopView() {
        View headView = LayoutInflater.from(CommunityMessageListActivity.this).inflate(R.layout.headview_message_list, null);
        message_rv.addHeaderView(headView);
        new_friend_layout = headView.findViewById(R.id.new_friend_layout);
        community_group_layout = headView.findViewById(R.id.community_group_layout);
        dynamics_notice_layout = headView.findViewById(R.id.dynamics_notice_layout);
        iv_new_friend = headView.findViewById(R.id.iv_new_friend);
        iv_dynamic_notice = headView.findViewById(R.id.iv_dynamic_notice);
        new_friend_layout.setOnClickListener(this::onClick);
        community_group_layout.setOnClickListener(this::onClick);
        dynamics_notice_layout.setOnClickListener(this::onClick);
        unReadNoticeBg = new QBadgeView(CommunityMessageListActivity.this);
        unReadNoticeBg.bindTarget(iv_dynamic_notice);
        unReadNoticeBg.setBadgeGravity(Gravity.END | Gravity.TOP);
        unReadNoticeBg.setGravityOffset(-2, -2, true);
        unReadNoticeBg.setBadgeTextSize(8f, true);
        unReadNoticeBg.setBadgeBackgroundColor(ContextCompat.getColor(CommunityMessageListActivity.this, R.color.hx_color_red_tag));
        unReadNoticeBg.setShowShadow(false);

        newApplyNoticeBg = new QBadgeView(CommunityMessageListActivity.this);
        newApplyNoticeBg.bindTarget(iv_new_friend);
        newApplyNoticeBg.setGravityOffset(-2, -2, true);
        newApplyNoticeBg.setBadgeGravity(Gravity.END | Gravity.TOP);
        newApplyNoticeBg.setBadgeTextSize(8f, true);
        newApplyNoticeBg.setBadgeBackgroundColor(ContextCompat.getColor(CommunityMessageListActivity.this, R.color.hx_color_red_tag));
        newApplyNoticeBg.setShowShadow(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.img_right:
                Intent friendGroupIntent = new Intent(CommunityMessageListActivity.this, IMAddFriendActivity.class);
                startActivity(friendGroupIntent);
                break;
            case R.id.new_friend_layout:
                Intent newFriendIntent = new Intent(CommunityMessageListActivity.this, IMFriendAndGroupActivity.class);
                startActivity(newFriendIntent);
                break;
            case R.id.community_group_layout:
                Intent communtiyIntent = new Intent(CommunityMessageListActivity.this, IMGroupListActivity.class);
                communtiyIntent.putExtra(IMGroupListActivity.GROUPTYPE, 1);
                startActivity(communtiyIntent);
                break;
            case R.id.dynamics_notice_layout:
                Intent dynamicsIntent = new Intent(CommunityMessageListActivity.this, DynamicNoticeActivity.class);
                startActivity(dynamicsIntent);
                break;
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

    @Override
    public void onStop() {
        super.onStop();
        HuxinSdkManager.instance().removeImMsgCallback(this);
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
                content = getResources().getString(R.string.im_cancle_stick);
            } else {
                content = getResources().getString(R.string.im_stick);
            }
            SwipeMenuItem topItem = new SwipeMenuItem(CommunityMessageListActivity.this)
                    .setBackground(new ColorDrawable(Color.rgb(0xc7, 0xc7, 0xc7)))
                    .setText(content) // 文字。
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(18) // 文字大小。
                    .setWidth(width)
                    .setHeight(height);
            SwipeMenuItem deleteItem = new SwipeMenuItem(CommunityMessageListActivity.this)
                    .setBackground(new ColorDrawable(Color.rgb(0xff, 0x00, 0x00)))
                    .setText(getResources().getString(R.string.im_delete)) // 文字。
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
                    boolean isTop = AppUtils.getBooleanSharedPreferences(CommunityMessageListActivity.this, "top" + bean.getTargetUuid(), false);
                    if (!isTop) {
                        bean.setTop(true);
                        AppUtils.setBooleanSharedPreferences(CommunityMessageListActivity.this, "top" + bean.getTargetUuid(), true);
                        mMessageAdapter.addTop(bean);
                        ToastUtil.toastShow(CommunityMessageListActivity.this, getResources().getString(R.string.stick_top));
                    } else {
                        bean.setTop(false);
                        AppUtils.setBooleanSharedPreferences(CommunityMessageListActivity.this, "top" + bean.getTargetUuid(), false);
                        mMessageAdapter.cancelTop(bean);
                        ToastUtil.toastShow(CommunityMessageListActivity.this, getResources().getString(R.string.stick_cancel));
                    }
                } else {
                    showDelNotice(adapterPosition);
                }
            }
        }
    };


    private void showDelNotice(final int adapterPosition) {
        final DeleteMsgDialog deleteMsgDialog = new DeleteMsgDialog(CommunityMessageListActivity.this, R.style.custom_dialog_theme);
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
            }
        });
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        if (what == 0) {
            try {
                CommunityDynamicRemindEntity communityDynamicRemindEntity = GsonUtils.gsonToBean(result, CommunityDynamicRemindEntity.class);
                int unReadNoticeCount = communityDynamicRemindEntity.getContent().getCount();
                editor.putInt(COLOUR_DYNAMICS_NOTICE_NUMBER, unReadNoticeCount).apply();
                unReadNoticeBg.setBadgeNumber(unReadNoticeCount);
            } catch (Exception e) {

            }
        }

    }
}
