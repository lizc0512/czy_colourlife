package com.community.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * author:yuansk
 * cretetime:2020/3/23
 * desc:活动详情
 **/
public class CommunityActivityDetailsEntity extends BaseContentEntity {


    /**
     * content : {"id":4,"ac_type":"普适性活动","ac_title":"一起来春游呀，哈哈哈","ac_category":"春节活动","ac_status":5,"operator":"","operator_id":1002646939,"operator_oa":"","ac_tag":"免费","ac_fee":0,"ac_address":"七星小区门口","limit_num":10,"stop_apply_time":1586016000,"begin_time":1586102400,"end_time":1586448000,"contact_user_id":21541,"picture_require":1,"picture_num":3,"picture_prompt":"","ac_banner":"https://pics-czy-cdn.colourlife.com/dev-5ccec537ddda7439048.jpg","ac_detail":"","created_at":"1585227490","updated_at":"1585227490","contact_user_name":"张锡旺","contact_user_mobile":"18617194368","contact_user_avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5e6736ae6eb6e384325.jpg","source_id":"2020_4","join_num":0,"join_user":[]}
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
         * id : 4
         * ac_type : 普适性活动
         * ac_title : 一起来春游呀，哈哈哈
         * ac_category : 春节活动
         * ac_status : 5
         * operator :
         * operator_id : 1002646939
         * operator_oa :
         * ac_tag : 免费
         * ac_fee : 0
         * ac_address : 七星小区门口
         * limit_num : 10
         * stop_apply_time : 1586016000
         * begin_time : 1586102400
         * end_time : 1586448000
         * contact_user_id : 21541
         * picture_require : 1
         * picture_num : 3
         * picture_prompt :
         * ac_banner : https://pics-czy-cdn.colourlife.com/dev-5ccec537ddda7439048.jpg
         * ac_detail :
         * created_at : 1585227490
         * updated_at : 1585227490
         * contact_user_name : 张锡旺
         * contact_user_mobile : 18617194368
         * contact_user_avatar : https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5e6736ae6eb6e384325.jpg
         * source_id : 2020_4
         * join_num : 0
         * join_user : []
         */

        private int id;
        private String ac_type;
        private String ac_title;
        private String ac_category;
        private String ac_status;
        private String operator;
        private String operator_id;
        private String operator_oa;
        private String ac_tag;
        private String ac_fee;
        private String ac_address;
        private int limit_num;
        private long stop_apply_time;
        private long begin_time;
        private long end_time;
        private String contact_user_id;
        private String picture_require;
        private int picture_num;
        private String picture_prompt;
        private String ac_banner;
        private String ac_detail;
        private String created_at;
        private String updated_at;
        private String contact_user_name;
        private String contact_user_mobile;
        private String contact_user_avatar;
        private String source_id;
        private int join_num;
        private List<String> join_user;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getAc_type() {
            return ac_type;
        }

        public void setAc_type(String ac_type) {
            this.ac_type = ac_type;
        }

        public String getAc_title() {
            return ac_title;
        }

        public void setAc_title(String ac_title) {
            this.ac_title = ac_title;
        }

        public String getAc_category() {
            return ac_category;
        }

        public void setAc_category(String ac_category) {
            this.ac_category = ac_category;
        }

        public String getAc_status() {
            return ac_status;
        }

        public void setAc_status(String ac_status) {
            this.ac_status = ac_status;
        }

        public String getOperator() {
            return operator;
        }

        public void setOperator(String operator) {
            this.operator = operator;
        }

        public String getOperator_id() {
            return operator_id;
        }

        public void setOperator_id(String operator_id) {
            this.operator_id = operator_id;
        }

        public String getOperator_oa() {
            return operator_oa;
        }

        public void setOperator_oa(String operator_oa) {
            this.operator_oa = operator_oa;
        }

        public String getAc_tag() {
            return ac_tag;
        }

        public void setAc_tag(String ac_tag) {
            this.ac_tag = ac_tag;
        }

        public String getAc_fee() {
            return ac_fee;
        }

        public void setAc_fee(String ac_fee) {
            this.ac_fee = ac_fee;
        }

        public String getAc_address() {
            return ac_address;
        }

        public void setAc_address(String ac_address) {
            this.ac_address = ac_address;
        }

        public int getLimit_num() {
            return limit_num;
        }

        public void setLimit_num(int limit_num) {
            this.limit_num = limit_num;
        }

        public long getStop_apply_time() {
            return stop_apply_time;
        }

        public void setStop_apply_time(long stop_apply_time) {
            this.stop_apply_time = stop_apply_time;
        }

        public long getBegin_time() {
            return begin_time;
        }

        public void setBegin_time(long begin_time) {
            this.begin_time = begin_time;
        }

        public long getEnd_time() {
            return end_time;
        }

        public void setEnd_time(long end_time) {
            this.end_time = end_time;
        }

        public String getContact_user_id() {
            return contact_user_id;
        }

        public void setContact_user_id(String contact_user_id) {
            this.contact_user_id = contact_user_id;
        }

        public String getPicture_require() {
            return picture_require;
        }

        public void setPicture_require(String picture_require) {
            this.picture_require = picture_require;
        }

        public int getPicture_num() {
            return picture_num;
        }

        public void setPicture_num(int picture_num) {
            this.picture_num = picture_num;
        }

        public String getPicture_prompt() {
            return picture_prompt;
        }

        public void setPicture_prompt(String picture_prompt) {
            this.picture_prompt = picture_prompt;
        }

        public String getAc_banner() {
            return ac_banner;
        }

        public void setAc_banner(String ac_banner) {
            this.ac_banner = ac_banner;
        }

        public String getAc_detail() {
            return ac_detail;
        }

        public void setAc_detail(String ac_detail) {
            this.ac_detail = ac_detail;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public String getContact_user_name() {
            return contact_user_name;
        }

        public void setContact_user_name(String contact_user_name) {
            this.contact_user_name = contact_user_name;
        }

        public String getContact_user_mobile() {
            return contact_user_mobile;
        }

        public void setContact_user_mobile(String contact_user_mobile) {
            this.contact_user_mobile = contact_user_mobile;
        }

        public String getContact_user_avatar() {
            return contact_user_avatar;
        }

        public void setContact_user_avatar(String contact_user_avatar) {
            this.contact_user_avatar = contact_user_avatar;
        }

        public String getSource_id() {
            return source_id;
        }

        public void setSource_id(String source_id) {
            this.source_id = source_id;
        }

        public int getJoin_num() {
            return join_num;
        }

        public void setJoin_num(int join_num) {
            this.join_num = join_num;
        }

        public List<String> getJoin_user() {
            return join_user;
        }

        public void setJoin_user(List<String> join_user) {
            this.join_user = join_user;
        }
    }
}
