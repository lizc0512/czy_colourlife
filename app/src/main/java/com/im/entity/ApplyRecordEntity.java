package com.im.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * @name ${yuansk}
 * @class name：com.im.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/7/2 17:30
 * @change
 * @chang time
 * @class describe
 */
@Entity
public class ApplyRecordEntity {


    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private String portrait;
    private String name;
    private String nickName;

    public String getNewComment() {
        return newComment;
    }

    public void setNewComment(String newComment) {
        this.newComment = newComment;
    }

    private String newComment;

    public String getCommunityName() {
        return communityName;
    }

    public void setCommunityName(String communityName) {
        this.communityName = communityName;
    }

    private String communityName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    private String gender;
    private String comment;
    private String state;
    private String uuid;

    public String getReal_name() {
        return real_name;
    }

    public void setReal_name(String real_name) {
        this.real_name = real_name;
    }

    private String real_name;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    private String mobile;
    @Id
    private Long id;  //主键ID

    @Generated(hash = 1429184001)
    public ApplyRecordEntity(String portrait, String name, String nickName,
            String newComment, String communityName, String gender, String comment,
            String state, String uuid, String real_name, String mobile, Long id) {
        this.portrait = portrait;
        this.name = name;
        this.nickName = nickName;
        this.newComment = newComment;
        this.communityName = communityName;
        this.gender = gender;
        this.comment = comment;
        this.state = state;
        this.uuid = uuid;
        this.real_name = real_name;
        this.mobile = mobile;
        this.id = id;
    }

    @Generated(hash = 1523851173)
    public ApplyRecordEntity() {
    }

}
