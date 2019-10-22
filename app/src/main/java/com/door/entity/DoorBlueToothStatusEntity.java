package com.door.entity;

import com.nohttp.entity.BaseContentEntity;

public class DoorBlueToothStatusEntity extends BaseContentEntity {
    /**
     * content : {"id":"1","communityUuid":"bcfe0f35-37b0-49cf-a73d-ca96914a46a5","tgStatus":1,"keyDate":0,"createTime":"2019-08-30 21:48:05","updateTime":"2019-09-02 14:31:27","phone":""}
     */

    private ContentBean content;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        public String getTgStatus() {
            return tgStatus;
        }

        public void setTgStatus(String tgStatus) {
            this.tgStatus = tgStatus;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        /**
         * id : 1
         * communityUuid : bcfe0f35-37b0-49cf-a73d-ca96914a46a5
         * tgStatus : 1   //是否需要小区管理审核 0否 1是
         * keyDate : 0
         * createTime : 2019-08-30 21:48:05
         * updateTime : 2019-09-02 14:31:27
         * phone :
         */


        private String tgStatus;
        private String name;
        private String mobile;
    }


}
