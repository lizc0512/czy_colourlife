package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/26 14:13
 * @change
 * @chang time
 * @class describe  用户个人信息
 */

public class UserInformationEntity extends BaseContentEntity {

    /**
     * content : {"id":2224370,"name":"180测试号","nick_name":"向日葵","community_uuid":"bcfe0f35-37b0-49cf-a73d-ca96914a46a5","community_name":"七星商业广场","portrait_url":"https://cimg-czytest.colourlife.com/images/2017/10/31/22/301269921.jpg","gender":2,"mobile":"18076627255","uuid":"6317642b-5fd9-43ab-8f0d-b4166efb301c","real_name":"张柠柠","source":1,"permit_position":1,"build_uuid":"","build_name":"","unit_uuid":"","unit_name":"","room_uuid":"","room_name":""}
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
         * id : 2224370
         * name : 180测试号
         * nick_name : 向日葵
         * community_uuid : bcfe0f35-37b0-49cf-a73d-ca96914a46a5
         * community_name : 七星商业广场
         * portrait_url : https://cimg-czytest.colourlife.com/images/2017/10/31/22/301269921.jpg
         * gender : 2
         * mobile : 18076627255
         * uuid : 6317642b-5fd9-43ab-8f0d-b4166efb301c
         * real_name : 张柠柠
         * source : 1
         * permit_position : 1
         * build_uuid :
         * build_name :
         * unit_uuid :
         * unit_name :
         * room_uuid :
         * room_name :
         */

        private int id;
        private String name;
        private String nick_name;
        private String community_uuid;
        private String community_name;
        private String portrait_url;



        private String email;
        private int gender;
        private String mobile;
        private String uuid;
        private String real_name;
        private int source;
        private String permit_position;
        private String build_uuid;
        private String build_name;
        private String unit_uuid;
        private String unit_name;
        private String room_uuid;
        private String room_name;
        private String password;
        private String authentication;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNick_name() {
            return nick_name;
        }

        public void setNick_name(String nick_name) {
            this.nick_name = nick_name;
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
        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }
        public String getPortrait_url() {
            return portrait_url;
        }

        public void setPortrait_url(String portrait_url) {
            this.portrait_url = portrait_url;
        }

        public int getGender() {
            return gender;
        }

        public void setGender(int gender) {
            this.gender = gender;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getUuid() {
            return uuid;
        }

        public void setUuid(String uuid) {
            this.uuid = uuid;
        }

        public String getReal_name() {
            return real_name;
        }

        public void setReal_name(String real_name) {
            this.real_name = real_name;
        }

        public int getSource() {
            return source;
        }

        public void setSource(int source) {
            this.source = source;
        }

        public String getPermit_position() {
            return permit_position;
        }

        public void setPermit_position(String permit_position) {
            this.permit_position = permit_position;
        }

        public String getBuild_uuid() {
            return build_uuid;
        }

        public void setBuild_uuid(String build_uuid) {
            this.build_uuid = build_uuid;
        }

        public String getBuild_name() {
            return build_name;
        }

        public void setBuild_name(String build_name) {
            this.build_name = build_name;
        }

        public String getUnit_uuid() {
            return unit_uuid;
        }

        public void setUnit_uuid(String unit_uuid) {
            this.unit_uuid = unit_uuid;
        }

        public String getUnit_name() {
            return unit_name;
        }

        public void setUnit_name(String unit_name) {
            this.unit_name = unit_name;
        }

        public String getRoom_uuid() {
            return room_uuid;
        }

        public void setRoom_uuid(String room_uuid) {
            this.room_uuid = room_uuid;
        }

        public String getRoom_name() {
            return room_name;
        }

        public void setRoom_name(String room_name) {
            this.room_name = room_name;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getAuthentication() {
            return authentication;
        }

        public void setAuthentication(String authentication) {
            this.authentication = authentication;
        }

    }
}
