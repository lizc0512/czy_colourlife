package cn.net.cyberway.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.BeeFramework.model.Constants;
import com.im.entity.FriendInforEntity;
import com.im.entity.MobileBookEntity;
import com.im.greendao.IMGreenDaoManager;
import com.im.helper.CacheFriendInforHelper;
import com.im.utils.BaseUtil;
import com.im.utils.CharacterParser;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.youmai.hxsdk.HuxinSdkManager;
import com.youmai.hxsdk.ProtoCallback;
import com.youmai.hxsdk.UserInfo;
import com.youmai.hxsdk.db.bean.ContactBean;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.utils
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/1/7 11:47
 * @change
 * @chang time
 * @class describe
 */
public class IMFriendDataUtils {

    /***好友同意接收回调加入好友列表**/
    public static void saveFriendData(Context context, String result) {
        try {
            MobileBookEntity mobileBookEntity = GsonUtils.gsonToBean(result, MobileBookEntity.class);
            if (mobileBookEntity.getCode() == 0) {
                MobileBookEntity.ContentBean contentBean = mobileBookEntity.getContent().get(0);
                String useruuid = contentBean.getUuid();
                FriendInforEntity friendInforEntity = new FriendInforEntity();
                friendInforEntity.setUuid(useruuid);
                String realName = contentBean.getReal_name();
                String name = contentBean.getName();
                String nickName = contentBean.getNick_name();
                friendInforEntity.setNickname(nickName);
                friendInforEntity.setUsername(name);
                friendInforEntity.setRealName(realName);
                friendInforEntity.setCommunityName(contentBean.getCommunity_name());
                friendInforEntity.setPortrait(contentBean.getPortrait());
                friendInforEntity.setStatus(1);
                friendInforEntity.setMobile(contentBean.getMobile());
                friendInforEntity.setGender(contentBean.getGender());
                String sortsStr = "";
                if (!TextUtils.isEmpty(realName)) {
                    sortsStr = realName;
                } else {
                    sortsStr = name;
                }
                if (TextUtils.isEmpty(sortsStr)) {
                    sortsStr = nickName;
                }
                if (!TextUtils.isEmpty(sortsStr)) {
                    CharacterParser characterParser = CharacterParser.getInstance();
                    String pinyin = characterParser.getSelling(sortsStr);
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
                CacheFriendInforHelper.instance().insertOrUpdate(context, friendInforEntity);
            }
        } catch (Exception e) {

        }
    }

    /***用户登录IM**/
    public static void userInitImData(Context context, SharedPreferences mShared) {
        UserInfo userInfo = new UserInfo();
        String uuid = mShared.getString(UserAppConst.Colour_User_uuid, "");
        int userId = mShared.getInt(UserAppConst.Colour_User_id, 0);
        String name = mShared.getString(UserAppConst.Colour_NAME, "");
        String nickName = mShared.getString(UserAppConst.Colour_NIACKNAME, "");
        String avatar = mShared.getString(UserAppConst.Colour_head_img, "");
        String phoneNumber = mShared.getString(UserAppConst.Colour_login_mobile, "");
        String sex = mShared.getString(UserAppConst.Colour_GENDER, "");
        String communityName = mShared.getString(UserAppConst.Colour_login_community_name, "");
        String communityUUId = mShared.getString(UserAppConst.Colour_login_community_uuid, "");
        String realName = mShared.getString(UserAppConst.Colour_Real_name, "");
        if (TextUtils.isEmpty(uuid)) {
            uuid = "";
        }
        userInfo.setUuid(uuid);
        userInfo.setUserId(String.valueOf(userId));
        userInfo.setAvatar(avatar);
        userInfo.setUserName(name);
        userInfo.setNickName(nickName);
        userInfo.setPhoneNum(phoneNumber);
        userInfo.setSex(sex);
        userInfo.setOrgName(communityName);
        userInfo.setOrgId(communityUUId);
        if (!TextUtils.isEmpty(realName)) {
            userInfo.setRealName(realName);
        } else {
            userInfo.setRealName("");
        }
        IMGreenDaoManager.instance(context).init(uuid);
        HuxinSdkManager.instance().setUserInfo(userInfo);
    }

    /***获取好友列表**/
    public static void getFriendListData(final Context context) {
        HuxinSdkManager.instance().reqContactList(new ProtoCallback.ContactListener() {
            @Override
            public void result(List<ContactBean> list) {
                CharacterParser characterParser = CharacterParser.getInstance();
                int size = list.size();
                for (int j = 0; j < size; j++) {
                    ContactBean userInfo = list.get(j);
                    int status = userInfo.getStatus();
                    if (status != 0) {
                        FriendInforEntity friendInforEntity = new FriendInforEntity();
                        String userName = userInfo.getUserName();
                        String realName = userInfo.getRealName();
                        String nickName = userInfo.getNickName();
                        String showName = BaseUtil.formatString(userName);
                        if (!TextUtils.isEmpty(showName)) {
                            friendInforEntity.setUsername(userName);
                        } else {
                            if (TextUtils.isEmpty(nickName)) {
                                friendInforEntity.setUsername("");
                            } else {
                                userName = nickName;
                                friendInforEntity.setUsername(nickName);
                            }
                        }
                        if (TextUtils.isEmpty(nickName)) {
                            friendInforEntity.setNickname("");
                        } else {
                            friendInforEntity.setNickname(nickName);
                        }
                        friendInforEntity.setUuid(userInfo.getUuid());
                        String avatar = userInfo.getAvatar();
                        if (!avatar.startsWith("http")) {
                            if (Constants.SAVENOHTTPRECORD == 1) {
                                friendInforEntity.setPortrait("https://cimg-czytest.colourlife.com/images/" + avatar);
                            } else {
                                friendInforEntity.setPortrait("https://cimg-czy.colourlife.com/images/" + avatar);
                            }
                        } else {
                            friendInforEntity.setPortrait(avatar);
                        }
                        friendInforEntity.setGender(String.valueOf(userInfo.getSex()));
                        friendInforEntity.setStatus(status);
                        friendInforEntity.setMobile(userInfo.getMobile());
                        friendInforEntity.setCommunityName(userInfo.getOrgName());
                        String showReal = BaseUtil.formatString(realName);
                        if (!TextUtils.isEmpty(showReal)) {
                            userName = realName;
                            friendInforEntity.setRealName(realName);
                        }
                        if (!TextUtils.isEmpty(userName)) {
                            String pinyin = characterParser.getSelling(userName);
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
                        CacheFriendInforHelper.instance().insertOrUpdate(context, friendInforEntity);
                    } else {
                        continue;
                    }
                }
            }
        });
    }
}
