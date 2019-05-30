package com.im.entity;

import android.support.annotation.VisibleForTesting;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.im.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/7/4 20:48
 * @change
 * @chang time
 * @class describe
 */
public class CommunityInforEntity extends BaseContentEntity {
    /**
     * content : {"id":2,"im_id":"trjrjr15","group_name":"测试啊","total_num":120,"area":484.55,"owner":"ee","mobile":"18617194368","apply_num":"201807021157585b39a2c6f2af5699151","state":1,"created_at":1530503878,"updated_at":1530503878,"apply_pass_at":null}
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
         * id : 2
         * im_id : trjrjr15
         * group_name : 测试啊
         * total_num : 120
         * area : 484.55
         * owner : ee
         * mobile : 18617194368
         * apply_num : 201807021157585b39a2c6f2af5699151
         * state : 1
         * created_at : 1530503878
         * updated_at : 1530503878
         * apply_pass_at : null
         */

        private int id;
        private String im_id;
        private String group_name;
        private String total_num;
        private String area;
        private String owner;
        private String mobile;
        private String apply_num;
        private int state;
        private long created_at;
        private long updated_at;
        private long apply_pass_at;
        private long now_time;
        public long getNow_time() {
            return now_time;
        }

        public void setNow_time(long now_time) {
            this.now_time = now_time;
        }



        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIm_id() {
            return im_id;
        }

        public void setIm_id(String im_id) {
            this.im_id = im_id;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public String getTotal_num() {
            return total_num;
        }

        public void setTotal_num(String total_num) {
            this.total_num = total_num;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getApply_num() {
            return apply_num;
        }

        public void setApply_num(String apply_num) {
            this.apply_num = apply_num;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public long getCreated_at() {
            return created_at;
        }

        public void setCreated_at(long created_at) {
            this.created_at = created_at;
        }

        public long getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(long updated_at) {
            this.updated_at = updated_at;
        }

        public long getApply_pass_at() {
            return apply_pass_at;
        }

        public void setApply_pass_at(long apply_pass_at) {
            this.apply_pass_at = apply_pass_at;
        }
    }
}
