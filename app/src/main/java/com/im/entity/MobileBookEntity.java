package com.im.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.im.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/29 18:00
 * @change
 * @chang time
 * @class describe
 */
public class MobileBookEntity extends BaseContentEntity {
    /**
     * content : [{"user_id":2507637,"uuid":"","mobile":"13000000020","state":0,"portrait":"https://cimg-czytest.colourlife.com/images/2017/12/31/22/460920526.jpg","gender":0,"nick_name":"半妖嗯嗯","name":"访客","community_uuid":null,"community_name":null,"comment":"feg"},{"user_id":1002646939,"uuid":"a0127ebf-6179-4276-b65e-50c329e18c41","mobile":"18617194368","state":0,"portrait":"https://cimg-czytest.colourlife.com/images/2018/06/26/09/443713650.jpg","gender":1,"nick_name":"zhangxi","name":"张锡旺","community_uuid":"82550031-75e7-4a8e-ab8f-074b285ab0a8","community_name":"彩科大厦","comment":"张"},{"id":"","uuid":"","mobile":18617194365,"is_deleted":"","state":"2","email":"","portrait":"","gender":"","nick_name":"","name":"","community_uuid":"","community_name":"","real_name":"","comment":"替换"}]
     * contentEncrypt :
     */

    private String contentEncrypt;
    private List<ContentBean> content;

    public String getContentEncrypt() {
        return contentEncrypt;
    }

    public void setContentEncrypt(String contentEncrypt) {
        this.contentEncrypt = contentEncrypt;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * user_id : 2507637
         * uuid :
         * mobile : 13000000020
         * state : 0
         * portrait : https://cimg-czytest.colourlife.com/images/2017/12/31/22/460920526.jpg
         * gender : 0
         * nick_name : 半妖嗯嗯
         * name : 访客
         * community_uuid : null
         * community_name : null
         * comment : feg
         * id :
         * is_deleted :
         * email :
         * real_name :
         */

        private String user_id;
        private String uuid;
        private String mobile;
        private String state;
        private String portrait;
        private String gender;
        private String nick_name;
        private String name;
        private String community_uuid;
        private String community_name;
        private String comment;
        private String id;
        private String is_deleted;
        private String email;
        private String real_name;

        public String getSortLetters() {
            return sortLetters;
        }

        public void setSortLetters(String sortLetters) {
            this.sortLetters = sortLetters;
        }

        private String sortLetters;  //显示数据拼音的首字母
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getIs_deleted() {
            return is_deleted;
        }

        public void setIs_deleted(String is_deleted) {
            this.is_deleted = is_deleted;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }
    }
}
