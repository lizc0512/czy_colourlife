package com.im.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * @name ${yuansk}
 * @class name：com.im.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/7/10 17:03
 * @change
 * @chang time
 * @class describe
 */
@Entity
public class MobileInforEntity {


    private String user_id;
    private String uuid;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getNick_name() {
        return nick_name;
    }

    public void setNick_name(String nick_name) {
        this.nick_name = nick_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommunity_uuid() {
        return community_uuid;
    }

    public void setCommunity_uuid(String community_uuid) {
        this.community_uuid = community_uuid;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getSortLetters() {
        return sortLetters;
    }

    public void setSortLetters(String sortLetters) {
        this.sortLetters = sortLetters;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String mobile;
    private String state; //状态，0：正常，1：禁用,2,未注册  3.好友
    private String portrait;
    private String gender;
    private String nick_name;
    private String name;
    private String community_uuid;
    private String community_name;
    private String comment;
    private String sortLetters;  //显示数据拼音的首字母
    @Id
    private Long id;  //主键ID

    @Generated(hash = 1058111722)
    public MobileInforEntity(String user_id, String uuid, String mobile,
            String state, String portrait, String gender, String nick_name,
            String name, String community_uuid, String community_name,
            String comment, String sortLetters, Long id) {
        this.user_id = user_id;
        this.uuid = uuid;
        this.mobile = mobile;
        this.state = state;
        this.portrait = portrait;
        this.gender = gender;
        this.nick_name = nick_name;
        this.name = name;
        this.community_uuid = community_uuid;
        this.community_name = community_name;
        this.comment = comment;
        this.sortLetters = sortLetters;
        this.id = id;
    }

    @Generated(hash = 409027127)
    public MobileInforEntity() {
    }
}
