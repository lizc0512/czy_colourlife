package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/1 10:13
 * @change
 * @chang time
 * @class describe  授权记录
 */
public class AuthorizeRecordEntity extends BaseContentEntity {

    /**
     * content : {"total":1,"lists":[{"plate":"粤B12345","user_from_phone":"13412345678","user_from_name":"张三","user_to_name":"李四","user_to_phone":"13412345678"}]}
     */

    private ContentBean content;


    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * total : 1
         * lists : [{"plate":"粤B12345","user_from_phone":"13412345678","user_from_name":"张三","user_to_name":"李四","user_to_phone":"13412345678"}]
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
             * plate : 粤B12345
             * user_from_phone : 13412345678
             * user_from_name : 张三
             * user_to_name : 李四
             * user_to_phone : 13412345678
             */

            private String plate;
            private String user_from_phone;
            private String user_from_name;
            private String user_to_name;
            private String user_to_phone;
            private String station_name;

            public String getStation_name() {
                return station_name;
            }

            public void setStation_name(String station_name) {
                this.station_name = station_name;
            }


            public String getExpiretime() {
                return expiretime;
            }

            public void setExpiretime(String expiretime) {
                this.expiretime = expiretime;
            }

            private String expiretime;

            public String getPlate() {
                return plate;
            }

            public void setPlate(String plate) {
                this.plate = plate;
            }

            public String getUser_from_phone() {
                return user_from_phone;
            }

            public void setUser_from_phone(String user_from_phone) {
                this.user_from_phone = user_from_phone;
            }

            public String getUser_from_name() {
                return user_from_name;
            }

            public void setUser_from_name(String user_from_name) {
                this.user_from_name = user_from_name;
            }

            public String getUser_to_name() {
                return user_to_name;
            }

            public void setUser_to_name(String user_to_name) {
                this.user_to_name = user_to_name;
            }

            public String getUser_to_phone() {
                return user_to_phone;
            }

            public void setUser_to_phone(String user_to_phone) {
                this.user_to_phone = user_to_phone;
            }
        }
    }
}
