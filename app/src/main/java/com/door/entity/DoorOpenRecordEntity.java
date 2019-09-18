package com.door.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

public class DoorOpenRecordEntity extends BaseContentEntity {
    /**
     * content : [{"date":"2019-09-17","data":[{"date":"2019-09-17","cipher_id":"B6F14E0CEAD7","status":1,"time":"04:05","door_name":"星商业广场-办公楼B座-4号电梯1"}]},{"date":"2019-09-15","data":[{"date":"2019-09-15","cipher_id":"B6F14E0CEAD7","status":2,"time":"21:32","door_name":"星商业广场-办公楼B座-4号电梯1"}]}]
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
         * date : 2019-09-17
         * data : [{"date":"2019-09-17","cipher_id":"B6F14E0CEAD7","status":1,"time":"04:05","door_name":"星商业广场-办公楼B座-4号电梯1"}]
         */

        private String date;
        private List<DataBean> data;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean {
            /**
             * date : 2019-09-17
             * cipher_id : B6F14E0CEAD7
             * status : 1
             * time : 04:05
             * door_name : 星商业广场-办公楼B座-4号电梯1
             */

            private String date;
            private String cipher_id;
            private String status;
            private String time;
            private String door_name;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getCipher_id() {
                return cipher_id;
            }

            public void setCipher_id(String cipher_id) {
                this.cipher_id = cipher_id;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }

            public String getDoor_name() {
                return door_name;
            }

            public void setDoor_name(String door_name) {
                this.door_name = door_name;
            }
        }
    }
}
