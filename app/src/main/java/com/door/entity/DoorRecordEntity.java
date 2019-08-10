package com.door.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * hxg 2019 08.08
 */
public class DoorRecordEntity extends BaseContentEntity {

    /**
     * content : {"current_page":1,"data":[{"id":1,"password":"123456","device_id":"115","user_uuid":"a0127ebf-6179-4276-b65e-50c329e18c41","created_at":"2019-08-08 15:18:19"}],"from":1,"last_page":1,"next_page_url":null,"path":"http://bluetoothtest-door.colourlife.com:443/app/door/devicePasswordLog","per_page":10,"prev_page_url":null,"to":1,"total":1,"psd_data":{"remain":9,"time":"2019-08-08 15:19:19"}}
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
         * current_page : 1
         * data : [{"id":1,"password":"123456","device_id":"115","user_uuid":"a0127ebf-6179-4276-b65e-50c329e18c41","created_at":"2019-08-08 15:18:19"}]
         * from : 1
         * last_page : 1
         * next_page_url : null
         * path : http://bluetoothtest-door.colourlife.com:443/app/door/devicePasswordLog
         * per_page : 10
         * prev_page_url : null
         * to : 1
         * total : 1
         * psd_data : {"remain":9,"time":"2019-08-08 15:19:19"}
         */

        private int current_page;
        private int from;
        private int last_page;
        private String next_page_url;
        private String path;
        private int per_page;
        private String prev_page_url;
        private int to;
        private int total;
        private PsdDataBean psd_data;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }


        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public String getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(String next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(String prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public PsdDataBean getPsd_data() {
            return psd_data;
        }

        public void setPsd_data(PsdDataBean psd_data) {
            this.psd_data = psd_data;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class PsdDataBean {
            /**
             * remain : 9
             * time : 2019-08-08 15:19:19
             */

            private int remain;
            private String time;

            public int getRemain() {
                return remain;
            }

            public void setRemain(int remain) {
                this.remain = remain;
            }

            public String getTime() {
                return time;
            }

            public void setTime(String time) {
                this.time = time;
            }
        }

        public static class DataBean {
            /**
             * id : 1
             * password : 123456
             * device_id : 115
             * user_uuid : a0127ebf-6179-4276-b65e-50c329e18c41
             * created_at : 2019-08-08 15:18:19
             */

            private int id;
            private String password;
            private String device_id;
            private String user_uuid;
            private String created_at;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getPassword() {
                return password;
            }

            public void setPassword(String password) {
                this.password = password;
            }

            public String getDevice_id() {
                return device_id;
            }

            public void setDevice_id(String device_id) {
                this.device_id = device_id;
            }

            public String getUser_uuid() {
                return user_uuid;
            }

            public void setUser_uuid(String user_uuid) {
                this.user_uuid = user_uuid;
            }

            public String getCreated_at() {
                return created_at;
            }

            public void setCreated_at(String created_at) {
                this.created_at = created_at;
            }
        }
    }
}
