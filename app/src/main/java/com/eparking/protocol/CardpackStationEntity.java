package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

import cn.csh.colourful.life.view.pickview.model.IPickerViewData;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/1/18 10:51
 * @change
 * @chang time
 * @class describe
 */
public class CardpackStationEntity extends BaseContentEntity {
    /**
     * content : {"total":1,"lists":[{"station_id":230,"station_name":"国际星城"}]}
     */

    private CardpackStationEntity.ContentBean content;

    public CardpackStationEntity.ContentBean getContent() {
        return content;
    }

    public void setContent(CardpackStationEntity.ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * total : 1
         * lists : [{"station_id":230,"station_name":"国际星城"}]
         */

        private int total;
        private List<CardpackStationEntity.ContentBean.ListsBean> lists;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public List<CardpackStationEntity.ContentBean.ListsBean> getLists() {
            return lists;
        }

        public void setLists(List<CardpackStationEntity.ContentBean.ListsBean> lists) {
            this.lists = lists;
        }

        public static class ListsBean implements IPickerViewData {
            /**
             * id : 230
             * name : 国际星城
             */


            private String id;
            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public String getPickerViewText() {
                return name;
            }
        }
    }
}
