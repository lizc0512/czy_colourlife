package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/1 9:59
 * @change
 * @chang time
 * @class describe  车位共享记录的
 */
public class ParkingShareRecordEntity extends BaseContentEntity {


    /**
     * content : {"total":3,"lists":[{"id":15,"time_begin":"2018-04-28","time_end":"2018-04-30","status":"N","station_name":"彩科彩悦大厦","lock_name":"测试车位锁","etcode":"ET1101","plate":"粤B12345"},{"id":5,"time_begin":"2018-04-19","time_end":"2018-04-20","status":"N","station_name":"彩科彩悦大厦","lock_name":"测试车位锁","etcode":"ET1101","plate":"粤B12345"},{"id":4,"time_begin":"2018-04-19","time_end":"2018-04-20","status":"N","station_name":"彩科彩悦大厦","lock_name":"测试车位锁","etcode":"ET1101","plate":"粤B12345"}]}
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
         * total : 3
         * lists : [{"id":15,"time_begin":"2018-04-28","time_end":"2018-04-30","status":"N","station_name":"彩科彩悦大厦","lock_name":"测试车位锁","etcode":"ET1101","plate":"粤B12345"},{"id":5,"time_begin":"2018-04-19","time_end":"2018-04-20","status":"N","station_name":"彩科彩悦大厦","lock_name":"测试车位锁","etcode":"ET1101","plate":"粤B12345"},{"id":4,"time_begin":"2018-04-19","time_end":"2018-04-20","status":"N","station_name":"彩科彩悦大厦","lock_name":"测试车位锁","etcode":"ET1101","plate":"粤B12345"}]
         */

        private int total;
        private List<ListsBean> lists;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean {
            /**
             * id : 15
             * time_begin : 2018-04-28
             * time_end : 2018-04-30
             * status : N
             * station_name : 彩科彩悦大厦
             * lock_name : 测试车位锁
             * etcode : ET1101
             * plate : 粤B12345
             */

            private String id;
            private String time_begin;
            private String time_end;
            private String status;
            private String station_name;
            private String lock_name;
            private String etcode;
            private String plate;

            public String getLock_user_name() {
                return lock_user_name;
            }

            public void setLock_user_name(String lock_user_name) {
                this.lock_user_name = lock_user_name;
            }

            private String lock_user_name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTime_begin() {
                return time_begin;
            }

            public void setTime_begin(String time_begin) {
                this.time_begin = time_begin;
            }

            public String getTime_end() {
                return time_end;
            }

            public void setTime_end(String time_end) {
                this.time_end = time_end;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getStation_name() {
                return station_name;
            }

            public void setStation_name(String station_name) {
                this.station_name = station_name;
            }

            public String getLock_name() {
                return lock_name;
            }

            public void setLock_name(String lock_name) {
                this.lock_name = lock_name;
            }

            public String getEtcode() {
                return etcode;
            }

            public void setEtcode(String etcode) {
                this.etcode = etcode;
            }

            public String getPlate() {
                return plate;
            }

            public void setPlate(String plate) {
                this.plate = plate;
            }
        }
    }
}
