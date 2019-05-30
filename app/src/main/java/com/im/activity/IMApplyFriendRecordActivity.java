package com.im.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.im.adapter.ApplyRecordAdapter;
import com.im.entity.ApplyRecordEntity;
import com.im.entity.FriendInforEntity;
import com.im.entity.MobileBookEntity;
import com.im.helper.CacheApplyRecorderHelper;
import com.im.helper.CacheFriendInforHelper;
import com.im.model.IMUploadPhoneModel;
import com.im.utils.BaseUtil;
import com.im.utils.CharacterParser;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.SpaceItemDecoration;
import com.user.UserAppConst;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.ProtoCallback;
import com.youmai.hxsdk.proto.YouMaiBuddy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

import static com.im.activity.IMApplyFriendInforActivity.POSITION;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/25 11:08
 * @change
 * @chang time
 * @class describe  申请好友记录的页面
 */

public class IMApplyFriendRecordActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private ImageView iv_no_record;
    private TextView tv_no_record;
    private SwipeMenuRecyclerView rv_apply_record;
    private ApplyRecordAdapter applyRecordAdapter;
    private List<String> uuidList;
    public List<ApplyRecordEntity> applyRecordEntityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_record);
        if (!shared.getBoolean(UserAppConst.IS_LOGIN, false)) {
            LinkParseUtil.parse(IMApplyFriendRecordActivity.this, "", "");
            finish();
        }
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        iv_no_record = findViewById(R.id.iv_no_record);
        tv_no_record = findViewById(R.id.tv_no_record);
        rv_apply_record = findViewById(R.id.apply_record_rv);
        user_top_view_back.setOnClickListener(this);
        user_top_view_title.setText(getResources().getString(R.string.instant_frind_apply));
        rv_apply_record.setLayoutManager(new LinearLayoutManager(IMApplyFriendRecordActivity.this));// 布局管理器。
        rv_apply_record.setSwipeMenuCreator(swipeMenuCreator);
        // 设置操作监听。
        String requestUUid = CacheApplyRecorderHelper.instance().toApplyRecordUuidList(IMApplyFriendRecordActivity.this);
        if (!TextUtils.isEmpty(requestUUid)) {
            IMUploadPhoneModel imUploadPhoneModel = new IMUploadPhoneModel(IMApplyFriendRecordActivity.this);
            imUploadPhoneModel.getUserInforByUuid(0, requestUUid, true, this);
        }
        editor.putBoolean(UserAppConst.IM_APPLY_FRIEND, false).commit();
        uuidList = CacheFriendInforHelper.instance().toQueryFriendUUIdList(IMApplyFriendRecordActivity.this);
        applyRecordEntityList.addAll(CacheApplyRecorderHelper.instance().toQueryApplyRecordList(IMApplyFriendRecordActivity.this));
        for (ApplyRecordEntity applyRecordEntity : applyRecordEntityList) {
            if (uuidList.contains(applyRecordEntity.getUuid())) {
                applyRecordEntity.setState("1");
            } else {
                continue;
            }
        }
        Collections.reverse(applyRecordEntityList);
        rv_apply_record.setSwipeMenuItemClickListener(mMenuItemClickListener);
        rv_apply_record.addItemDecoration(new SpaceItemDecoration(1));
        applyRecordAdapter = new ApplyRecordAdapter(applyRecordEntityList);
        rv_apply_record.setAdapter(applyRecordAdapter);
        applyRecordAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                ApplyRecordEntity applyRecordEntity = applyRecordEntityList.get(i);
                String state = applyRecordEntity.getState();
                Intent intent;
                if ("0".equals(state)) {
                    intent = new Intent(IMApplyFriendRecordActivity.this, IMApplyFriendInforActivity.class);
                    intent.putExtra(IMFriendInforActivity.USERUUID, applyRecordEntity.getUuid());
                    intent.putExtra(IMApplyFriendInforActivity.USERREMARK, applyRecordEntity.getComment());
                    intent.putExtra(IMApplyFriendInforActivity.POSITION, i);
                    intent.putExtra(IMInviteRegisterActivity.USERGENDER, applyRecordEntity.getGender());
                    intent.putExtra(IMInviteRegisterActivity.USERNAME, applyRecordEntity.getName());
                    intent.putExtra(IMInviteRegisterActivity.USERNICKNAME, applyRecordEntity.getNickName());
                    intent.putExtra(IMInviteRegisterActivity.USECOMMUNITYNAME, applyRecordEntity.getCommunityName());
                    intent.putExtra(IMInviteRegisterActivity.USERPORTRAIT, applyRecordEntity.getPortrait());
                } else {
                    intent = new Intent(IMApplyFriendRecordActivity.this, IMFriendInforActivity.class);
                    intent.putExtra(IMFriendInforActivity.USERUUID, applyRecordEntity.getUuid());
                }
                startActivityForResult(intent, 2000);
            }
        });
        setEmptyView();
    }


    private void setEmptyView() {
        if (applyRecordEntityList.size() == 0) {
            iv_no_record.setVisibility(View.VISIBLE);
            tv_no_record.setVisibility(View.VISIBLE);
        } else {
            iv_no_record.setVisibility(View.GONE);
            tv_no_record.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 2000 && resultCode == 200) {
            int position = data.getIntExtra(POSITION, 0);
            ApplyRecordEntity applyRecordEntity = applyRecordEntityList.get(position);
            applyRecordEntity.setState("1");
            if (null != applyRecordAdapter) {
                applyRecordAdapter.notifyDataSetChanged();
            }
            CacheApplyRecorderHelper.instance().insertOrUpdate(getApplicationContext(), applyRecordEntity);
            addFriendList(applyRecordEntity);
        }
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
            SwipeMenuItem deleteItem = new SwipeMenuItem(IMApplyFriendRecordActivity.this)
                    .setBackground(new ColorDrawable(Color.rgb(0xff, 0x00, 0x00)))
                    .setText(getResources().getString(R.string.im_delete)) // 文字。
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(18) // 文字大小。
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。.
        }
    };

    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                CacheApplyRecorderHelper.instance().delApplyRecord(getApplicationContext(), applyRecordEntityList.get(adapterPosition).getUuid());
                applyRecordEntityList.remove(adapterPosition);
                if (null != applyRecordAdapter) {
                    applyRecordAdapter.notifyDataSetChanged();
                }
                setEmptyView();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {

            }
        }
    };

    private void addFriendList(ApplyRecordEntity applyRecordEntity) {
        String userName = applyRecordEntity.getName();
        String nickName = applyRecordEntity.getNickName();
        String realName = applyRecordEntity.getReal_name();
        FriendInforEntity friendInforEntity = new FriendInforEntity();
        friendInforEntity.setUuid(applyRecordEntity.getUuid());
        friendInforEntity.setPortrait(applyRecordEntity.getPortrait());
        friendInforEntity.setGender(applyRecordEntity.getGender());
        friendInforEntity.setNickname(nickName);
        friendInforEntity.setRealName(realName);
        friendInforEntity.setUsername(userName);
        friendInforEntity.setCommunityName(applyRecordEntity.getCommunityName());
        friendInforEntity.setMobile(applyRecordEntity.getMobile());
        String sortStr = "";
        if (TextUtils.isEmpty(sortStr)) {
            sortStr = BaseUtil.formatString(realName);
        }
        if (TextUtils.isEmpty(sortStr)) {
            sortStr = BaseUtil.formatString(userName);
        }
        if (TextUtils.isEmpty(sortStr)) {
            sortStr = BaseUtil.formatString(nickName);
        }
        if (!TextUtils.isEmpty(sortStr)) {
            CharacterParser characterParser = CharacterParser.getInstance();
            String pinyin = characterParser.getSelling(sortStr);
            String sortString = pinyin.substring(0, 1).toUpperCase();
            // 正则表达式，判断首字母是否是英文字母
            if (sortString.matches("[A-Z]")) {
                friendInforEntity.setSortLetters(sortString.toUpperCase());
            } else {
                friendInforEntity.setSortLetters("#");
            }
        } else {
            friendInforEntity.setSortLetters("#");
        }
        CacheFriendInforHelper.instance().insertOrUpdate(getApplicationContext(), friendInforEntity);
    }

    public void agreeFriendApply(int position) {
        final ApplyRecordEntity applyRecordEntity = applyRecordEntityList.get(position);
        ProtoCallback.AddFriendListener listener = new ProtoCallback.AddFriendListener() {
            @Override
            public void result(YouMaiBuddy.IMOptBuddyRsp ack) {
                if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_OK) {
                    ToastUtil.toastShow(getApplicationContext(), "好友验证成功");
                    applyRecordEntity.setState("1");
                    CacheApplyRecorderHelper.instance().insertOrUpdate(getApplicationContext(), applyRecordEntity);
                    if (null != applyRecordAdapter) {
                        applyRecordAdapter.notifyDataSetChanged();
                    }
                    addFriendList(applyRecordEntity);
                } else if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_BUDDY_REQUESTING) {
                    ToastUtil.toastShow(getApplicationContext(), "重复添加好友请求成功");
                } else if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_BUDDY_BUILT) {
                    applyRecordEntity.setState("1");
                    CacheApplyRecorderHelper.instance().insertOrUpdate(getApplicationContext(), applyRecordEntity);
                    if (null != applyRecordAdapter) {
                        applyRecordAdapter.notifyDataSetChanged();
                    }
                    addFriendList(applyRecordEntity);
                } else if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_BLACKLIST) {
                    ToastUtil.toastShow(getApplicationContext(), "黑名单");
                } else if (ack.getResult() == YouMaiBuddy.ResultCode.CODE_BUDDY_READD) {

                } else {
                    ToastUtil.toastShow(getApplicationContext(), "出现错误");
                }
            }
        };
        HuxinSdkManager.instance().addFriend(applyRecordEntity.getUuid(),
                YouMaiBuddy.BuddyOptType.BUDDY_OPT_ADD_AGREE,
                applyRecordEntity.getComment(), listener);
    }

    @Override
    public void OnHttpResponse(int what, String result) {

        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        MobileBookEntity mobileBookEntity = GsonUtils.gsonToBean(result, MobileBookEntity.class);
                        if (mobileBookEntity.getCode() == 0) {
                            List<MobileBookEntity.ContentBean> contentBeanList = mobileBookEntity.getContent();
                            for (MobileBookEntity.ContentBean contentBean : contentBeanList) {
                                if (uuidList.contains(contentBean.getUuid())) {
                                    contentBean.setState("1");
                                }
                                ApplyRecordEntity applyRecordEntity = CacheApplyRecorderHelper.instance().toQueryApplyRecordById(IMApplyFriendRecordActivity.this, contentBean.getUuid());
                                applyRecordEntity.setPortrait(contentBean.getPortrait());
                                applyRecordEntity.setName(contentBean.getName());
                                applyRecordEntity.setMobile(contentBean.getMobile());
                                applyRecordEntity.setGender(contentBean.getGender());
                                applyRecordEntity.setNickName(contentBean.getNick_name());
                                applyRecordEntity.setCommunityName(contentBean.getCommunity_name());
                                applyRecordEntity.setReal_name(contentBean.getReal_name());
                                CacheApplyRecorderHelper.instance().insertOrUpdate(IMApplyFriendRecordActivity.this, applyRecordEntity);
                            }
                            applyRecordEntityList.clear();
                            applyRecordEntityList.addAll(CacheApplyRecorderHelper.instance().toQueryApplyRecordList(IMApplyFriendRecordActivity.this));
                            Collections.reverse(applyRecordEntityList);
                            applyRecordAdapter.notifyDataSetChanged();
                        }
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }
}
