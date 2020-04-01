package com.community.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * author:yuansk
 * cretetime:2020/3/23
 * desc:首页活动信息
 **/
public class CommunityActivityEntity extends BaseContentEntity {


    /**
     * content : {"id":1,"source_id":"2020_1","list_type":2,"ac_banner":"https://pics-czy-cdn.colourlife.com/dev-5ccec537ddda7439048.jpg","ac_tag":"免费","ac_oproperty":"官方活动","ac_title":"最美宠物摄影图片活动","ac_address":"七星小区","ac_status":1,"stop_apply_time":1585932055,"join_num":10,"join_user":["https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/pro-5e719b79f3100478546.jpg","https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/pro-5e4737866af4e327676.jpg","https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/pro-5e5e3c3b1fb37332374.jpg"]}
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
         * id : 1
         * source_id : 2020_1
         * list_type : 2
         * ac_banner : https://pics-czy-cdn.colourlife.com/dev-5ccec537ddda7439048.jpg
         * ac_tag : 免费
         * ac_oproperty : 官方活动
         * ac_title : 最美宠物摄影图片活动
         * ac_address : 七星小区
         * ac_status : 1
         * stop_apply_time : 1585932055
         * join_num : 10
         * join_user : ["https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/pro-5e719b79f3100478546.jpg","https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/pro-5e4737866af4e327676.jpg","https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/pro-5e5e3c3b1fb37332374.jpg"]
         */

        private String id;
        private String source_id;
        private String list_type;
        private String ac_banner;
        private String ac_tag;
        private String ac_oproperty;
        private String ac_title;
        private String ac_address;
        private String ac_status;

        public String getIs_join() {
            return is_join;
        }

        public void setIs_join(String is_join) {
            this.is_join = is_join;
        }

        private String is_join;
        private long stop_apply_time;
        private int join_num;
        private List<String> join_user;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getSource_id() {
            return source_id;
        }

        public void setSource_id(String source_id) {
            this.source_id = source_id;
        }

        public String getList_type() {
            return list_type;
        }

        public void setList_type(String list_type) {
            this.list_type = list_type;
        }

        public String getAc_banner() {
            return ac_banner;
        }

        public void setAc_banner(String ac_banner) {
            this.ac_banner = ac_banner;
        }

        public String getAc_tag() {
            return ac_tag;
        }

        public void setAc_tag(String ac_tag) {
            this.ac_tag = ac_tag;
        }

        public String getAc_oproperty() {
            return ac_oproperty;
        }

        public void setAc_oproperty(String ac_oproperty) {
            this.ac_oproperty = ac_oproperty;
        }

        public String getAc_title() {
            return ac_title;
        }

        public void setAc_title(String ac_title) {
            this.ac_title = ac_title;
        }

        public String getAc_address() {
            return ac_address;
        }

        public void setAc_address(String ac_address) {
            this.ac_address = ac_address;
        }

        public String getAc_status() {
            return ac_status;
        }

        public void setAc_status(String ac_status) {
            this.ac_status = ac_status;
        }

        public long getStop_apply_time() {
            return stop_apply_time;
        }

        public void setStop_apply_time(long stop_apply_time) {
            this.stop_apply_time = stop_apply_time;
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
