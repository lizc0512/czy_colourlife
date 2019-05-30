package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

import cn.csh.colourful.life.view.pickview.model.IPickerViewData;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/21 15:25
 * @change
 * @chang time
 * @class describe  历史停车场的记录
 */
public class HistoryStationEntity extends BaseContentEntity {
    /**
     * content : {"total":1,"lists":[{"station_id":230,"station_name":"国际星城"}]}
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
         * lists : [{"station_id":230,"station_name":"国际星城"}]
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

        public static class ListsBean implements IPickerViewData {
            /**
             * station_id : 230
             * station_name : 国际星城
             */

            private String station_id;
            private String station_name;

            public String getStation_id() {
                return station_id;
            }

            public void setStation_id(String station_id) {
                this.station_id = station_id;
            }

            public String getStation_name() {
                return station_name;
            }

            public void setStation_name(String station_name) {
                this.station_name = station_name;
            }

            @Override
            public String getPickerViewText() {

                return station_name;
            }
        }
    }
}
