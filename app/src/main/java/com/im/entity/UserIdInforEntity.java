package com.im.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.im.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/9/5 16:21
 * @change
 * @chang time
 * @class describe
 */
public class UserIdInforEntity extends BaseContentEntity {
    /**
     * content : {"id":1511998,"uuid":"6ee2d688-b050-43c8-8fa9-2ba1981b118e","mobile":"15320348027","email":"","state":0,"is_deleted":1,"nick_name":"米哈","name":"小芈侣","portrait":"2018/04/14/00/044089932.jpg","gender":1,"real_name":"刘强","community_uuid":"82550031-75e7-4a8e-ab8f-074b285ab0a8","community_name":"彩悦大厦"}
     * contentEncrypt :
     */

    private ContentBean content;
    private String contentEncrypt;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public String getContentEncrypt() {
        return contentEncrypt;
    }

    public void setContentEncrypt(String contentEncrypt) {
        this.contentEncrypt = contentEncrypt;
    }

    public static class ContentBean {
        /**
         * id : 1511998
         * uuid : 6ee2d688-b050-43c8-8fa9-2ba1981b118e
         * mobile : 15320348027
         * email :
         * state : 0
         * is_deleted : 1
         * nick_name : 米哈
         * name : 小芈侣
         * portrait : 2018/04/14/00/044089932.jpg
         * gender : 1
         * real_name : 刘强
         * community_uuid : 82550031-75e7-4a8e-ab8f-074b285ab0a8
         * community_name : 彩悦大厦
         */

        private int id;
        private String uuid;
        private String mobile;
        private String email;
        private int state;
        private int is_deleted;
        private String nick_name;
        private String name;
        private String portrait;
        private int gender;
        private String real_name;
        private String community_uuid;
        private String community_name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public int getIs_deleted() {
            return is_deleted;
        }

        public void setIs_deleted(int is_deleted) {
            this.is_deleted = is_deleted;
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

        public String getPortrait() {
            return portrait;
        }

        public void setPortrait(String portrait) {
            this.portrait = portrait;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
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
    }
}
