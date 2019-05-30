package com.im.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.google.protobuf.InvalidProtocolBufferException;
import com.im.adapter.FriendInforAdapter;
import com.im.entity.FriendInforEntity;
import com.im.helper.CacheFriendInforHelper;
import com.im.model.IMCommunityModel;
import com.im.utils.FriendInforComparator;
import com.im.view.SideBar;
import com.user.UserAppConst;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.chatgroup.IMGroupActivity;
import com.youmai.hxsdk.db.bean.ContactBean;
import com.youmai.hxsdk.proto.YouMaiBasic;
import com.youmai.hxsdk.proto.YouMaiGroup;
import com.youmai.hxsdk.socket.PduBase;
import com.youmai.hxsdk.socket.ReceiveListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/25 10:40
 * @change
 * @chang time
 * @class describe  通过好友创建社群或群聊
 */

public class IMCreateGroupChatActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String GROUPTYPE = "grouptype";
    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView user_top_view_right;
    private List<FriendInforEntity> friendInforEntityList = new ArrayList<>();
    private SideBar sideBar;
    private TextView dialog;
    private ListView sortListView;
    private FriendInforAdapter friendInforAdapter;
    private FriendInforComparator friendInforComparator;
    private int communityId;
    private int communityType = 0;
    private String ownerUUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_groupchat);
        Intent intent = getIntent();
        communityType = intent.getIntExtra(GROUPTYPE, 0);
        communityId = intent.getIntExtra(IMCreateCommunityActivity.COMMUNITYID, 0);
        groupName = intent.getStringExtra(IMCreateCommunityActivity.GROUPNAME);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        user_top_view_right = findViewById(R.id.user_top_view_right);
        sideBar = (SideBar) findViewById(R.id.sidrbar);
        dialog = (TextView) findViewById(R.id.dialog);
        sortListView = (ListView) findViewById(R.id.phone_lv);
        if (1 == communityType) {
            user_top_view_title.setText("添加成员");
        } else {
            user_top_view_title.setText("发起群聊");
        }
        user_top_view_right.setText("确定");
        user_top_view_right.setTextColor(getResources().getColor(R.color.color_bbc0cb));
        sideBar.setTextView(dialog);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_back.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        try {
            friendInforEntityList.addAll(CacheFriendInforHelper.instance().toQueryFriendList(IMCreateGroupChatActivity.this));
            friendInforComparator = new FriendInforComparator();
            Collections.sort(friendInforEntityList, friendInforComparator);
            ownerUUid = shared.getString(UserAppConst.Colour_User_uuid, "");
            friendInforAdapter = new FriendInforAdapter(this, friendInforEntityList, 1);
            List<String> uuidList = CacheFriendInforHelper.instance().toQueryFriendUUIdList(IMCreateGroupChatActivity.this);
            Map<String, Boolean> checkMap = new HashMap<>();
            for (String str : uuidList) {
                if (ownerUUid.equals(str)) {
                    checkMap.put(str, true);
                } else {
                    checkMap.put(str, false);
                }
            }
            friendInforAdapter.setCheckMap(checkMap);
            friendInforAdapter.setUUId(ownerUUid);
            sortListView.setAdapter(friendInforAdapter);
        } catch (Exception e) {
            ToastUtil.toastShow(IMCreateGroupChatActivity.this, e.getMessage());
        }
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = friendInforAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    sortListView.setSelection(position);
                }
            }
        });
    }

    private List<ContactBean> contactBeanList = new ArrayList<>();
    private String groupName = "";

    public void setBtnStatus() {
        createGroup();
        if (contactBeanList.size() > 1) {
            user_top_view_right.setEnabled(true);
            user_top_view_right.setTextColor(getResources().getColor(R.color.color_45adff));
        } else {
            user_top_view_right.setEnabled(false);
            user_top_view_right.setTextColor(getResources().getColor(R.color.color_bbc0cb));
        }
    }

    private void createGroup() {
        contactBeanList.clear();
        Map<String, Boolean> checkUUidMap = friendInforAdapter.getCheckMap();
        Set<Map.Entry<String, Boolean>> entrySet = checkUUidMap.entrySet();
        Iterator<Map.Entry<String, Boolean>> iterator = entrySet.iterator();
        StringBuffer stringBuffer = new StringBuffer();
        String realName = shared.getString(UserAppConst.Colour_Real_name, "");
        String userName = shared.getString(UserAppConst.Colour_NAME, "");
        String nickName = shared.getString(UserAppConst.Colour_NIACKNAME, "");
        if (TextUtils.isEmpty(realName)) {
            if (TextUtils.isEmpty(userName)) {
                stringBuffer.append(nickName);
            } else {
                stringBuffer.append(userName);
            }
        } else {
            stringBuffer.append(realName);
        }
        List<String> nameList = new ArrayList<>();
        while (iterator.hasNext()) {
            Map.Entry<String, Boolean> entry = iterator.next();
            if (entry.getValue()) {
                String uuid = entry.getKey();
                FriendInforEntity friendInforEntity = CacheFriendInforHelper.instance().toQueryFriendnforById(IMCreateGroupChatActivity.this, uuid);
                String contactName = friendInforEntity.getUsername();
                String contactRealName = friendInforEntity.getRealName();
                String contactNickname = friendInforEntity.getNickname();
                ContactBean contactBean = new ContactBean();
                contactBean.setAvatar(friendInforEntity.getPortrait());
                contactBean.setUuid(uuid);
                contactBeanList.add(contactBean);
                if (nameList.size() < 2) {
                    if (!ownerUUid.equals(uuid)) {
                        if (TextUtils.isEmpty(contactRealName)) {
                            if (TextUtils.isEmpty(contactName)) {
                                nameList.add(contactNickname);
                            } else {
                                nameList.add(contactName);
                            }
                        } else {
                            nameList.add(contactRealName);
                        }
                    }
                }
            }
        }
        for (String name : nameList) {
            stringBuffer.append(name);
        }
        if (communityType == 0) {
            groupName = stringBuffer.toString();
            groupType = YouMaiBasic.GroupType.GROUP_TYPE_MULTICHAT;
        } else {
            groupType = YouMaiBasic.GroupType.GROUP_TYPE_COMMUNITY;
        }
    }


    private YouMaiBasic.GroupType groupType = YouMaiBasic.GroupType.GROUP_TYPE_MULTICHAT;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right: //确定
                if (fastClick()) {
                    HuxinSdkManager.instance().createGroupById(groupName, groupType, contactBeanList, new ReceiveListener() {
                        @Override
                        public void OnRec(PduBase pduBase) {
                            try {
                                YouMaiGroup.GroupCreateRsp ack = YouMaiGroup.GroupCreateRsp.parseFrom(pduBase.body);
                                if (ack.getResult() == YouMaiBasic.ResultCode.RESULT_CODE_SUCCESS) {
                                    int groupId = ack.getGroupId();
                                    if (communityType == 1) {
                                        IMCommunityModel imCommunityModel = new IMCommunityModel(IMCreateGroupChatActivity.this);
                                        imCommunityModel.repeatCommitApply(0, String.valueOf(communityId), "1", String.valueOf(groupId), "", "",
                                                "", "", "", false, IMCreateGroupChatActivity.this);
                                        ToastUtil.toastShow(IMCreateGroupChatActivity.this, "创建社群成功");
                                    } else {
                                        ToastUtil.toastShow(IMCreateGroupChatActivity.this, "创建群聊成功");
                                    }
                                    Intent intent = new Intent(IMCreateGroupChatActivity.this, IMGroupActivity.class);
                                    intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
                                    if (groupType == YouMaiBasic.GroupType.GROUP_TYPE_MULTICHAT) {
                                        intent.putExtra(IMGroupActivity.GROUP_TYPE, 1);
                                    } else {
                                        intent.putExtra(IMGroupActivity.GROUP_TYPE, 2);
                                    }
                                    intent.putExtra(IMGroupActivity.DST_UUID, groupId);
                                    intent.putExtra(IMGroupActivity.DST_NAME, groupName);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    if (communityType == 1) {
                                        ToastUtil.toastShow(IMCreateGroupChatActivity.this, "创建社群失败");
                                    } else {
                                        ToastUtil.toastShow(IMCreateGroupChatActivity.this, "创建群聊失败");
                                    }

                                }
                            } catch (InvalidProtocolBufferException e) {
                                e.printStackTrace();
                            } catch (Exception e) {
                                ToastUtil.toastShow(IMCreateGroupChatActivity.this, e.getMessage());
                            }
                        }
                    });
                }
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {

    }
}
