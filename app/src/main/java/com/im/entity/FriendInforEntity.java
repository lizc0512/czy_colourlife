package com.im.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @name ${yuansk}
 * @class name：com.im.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/7/4 10:28
 * @change
 * @chang time
 * @class describe  好友的信息
 */
@Entity
public class FriendInforEntity {
    private String uuid;
    private String username;
    private String nickname;
    private String mobile;
    private String portrait;
    private String gender;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    private int status;

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    private String realName;

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    private String communityName;

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    private String sortLetters;
    @Id
    private Long id;  //主键ID
    @Generated(hash = 1256767401)
    public FriendInforEntity(String uuid, String username, String nickname,
            String mobile, String portrait, String gender, int status,
            String realName, String communityName, String sortLetters, Long id) {
        this.uuid = uuid;
        this.username = username;
        this.nickname = nickname;
        this.mobile = mobile;
        this.portrait = portrait;
        this.gender = gender;
        this.status = status;
        this.realName = realName;
        this.communityName = communityName;
        this.sortLetters = sortLetters;
        this.id = id;
    }

    @Generated(hash = 668296903)
    public FriendInforEntity() {
    }
    public String getUuid() {
        return this.uuid;
    }
    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getNickname() {
        return this.nickname;
    }
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    public String getMobile() {
        return this.mobile;
    }
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
    public String getPortrait() {
        return this.portrait;
    }
    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }
    public String getGender() {
        return this.gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
}
