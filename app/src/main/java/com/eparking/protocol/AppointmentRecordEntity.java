package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/1 9:53
 * @change
 * @chang time
 * @class describe  预约记录的
 */
public class AppointmentRecordEntity extends BaseContentEntity {

    /**
     * content : {"total":1,"lists":[{"id":6,"from":20886,"plate":"苏BL9J36","carpark":93,"state":0,"deleted":"n","starttime":"2017-01-21 21:18:48","stoptime":"2017-01-21 23:59:59","creationtime":1481110573,"modifiedtime":1485004728,"station_name":"无锡花郡","username":"潘慧玲","phone":"18762633696"}]}
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
         * lists : [{"id":6,"from":20886,"plate":"苏BL9J36","carpark":93,"state":0,"deleted":"n","starttime":"2017-01-21 21:18:48","stoptime":"2017-01-21 23:59:59","creationtime":1481110573,"modifiedtime":1485004728,"station_name":"无锡花郡","username":"潘慧玲","phone":"18762633696"}]
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
             * id : 6
             * from : 20886
             * plate : 苏BL9J36
             * carpark : 93
             * state : 0
             * deleted : n
             * starttime : 2017-01-21 21:18:48
             * stoptime : 2017-01-21 23:59:59
             * creationtime : 1481110573
             * modifiedtime : 1485004728
             * station_name : 无锡花郡
             * username : 潘慧玲
             * phone : 18762633696
             */

            private int id;
            private int from;
            private String plate;
            private int carpark;
            private int state;
            private String deleted;
            private String starttime;
            private String stoptime;
            private int creationtime;
            private int modifiedtime;
            private String station_name;

            public String getStation_addr() {
                return station_addr;
            }

            public void setStation_addr(String station_addr) {
                this.station_addr = station_addr;
            }

            private String station_addr;
            private String username;
            private String phone;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getFrom() {
                return from;
            }

            public void setFrom(int from) {
                this.from = from;
            }

            public String getPlate() {
                return plate;
            }

            public void setPlate(String plate) {
                this.plate = plate;
            }

            public int getCarpark() {
                return carpark;
            }

            public void setCarpark(int carpark) {
                this.carpark = carpark;
            }

            public int getState() {
                return state;
            }

            public void setState(int state) {
                this.state = state;
            }

            public String getDeleted() {
                return deleted;
            }

            public void setDeleted(String deleted) {
                this.deleted = deleted;
            }

            public String getStarttime() {
                return starttime;
            }

            public void setStarttime(String starttime) {
                this.starttime = starttime;
            }

            public String getStoptime() {
                return stoptime;
            }

            public void setStoptime(String stoptime) {
                this.stoptime = stoptime;
            }

            public int getCreationtime() {
                return creationtime;
            }

            public void setCreationtime(int creationtime) {
                this.creationtime = creationtime;
            }

            public int getModifiedtime() {
                return modifiedtime;
            }

            public void setModifiedtime(int modifiedtime) {
                this.modifiedtime = modifiedtime;
            }

            public String getStation_name() {
                return station_name;
            }

            public void setStation_name(String station_name) {
                this.station_name = station_name;
            }

            public String getUsername() {
                return username;
            }

            public void setUsername(String username) {
                this.username = username;
            }

            public String getPhone() {
                return phone;
            }

            public void setPhone(String phone) {
                this.phone = phone;
            }
        }
    }
}
