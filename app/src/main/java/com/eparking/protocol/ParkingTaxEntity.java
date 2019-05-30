package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

import cn.csh.colourful.life.view.pickview.model.IPickerViewData;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/20 14:59
 * @change
 * @chang time
 * @class describe   根据公司名称 获取纳税人识别号
 */
public class ParkingTaxEntity extends BaseContentEntity {
    /**
     * content : {"total":6,"lists":[{"gfmc":"北京中税信息网络有限公司","gfnsrsbh":"91110102633082712Q"},{"gfmc":"北京中税信息网络有限公司沈阳公司","gfnsrsbh":""},{"gfmc":"北京中税信息网络有限公司山东服务中心","gfnsrsbh":""},{"gfmc":"北京中税信息网络有限公司黑龙江服务中心","gfnsrsbh":""},{"gfmc":"北京中税信息网络有限公司河南服务中心","gfnsrsbh":""},{"gfmc":"北京中税信息网络有限公司四川服务中心","gfnsrsbh":""}]}
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
         * total : 6
         * lists : [{"gfmc":"北京中税信息网络有限公司","gfnsrsbh":"91110102633082712Q"},{"gfmc":"北京中税信息网络有限公司沈阳公司","gfnsrsbh":""},{"gfmc":"北京中税信息网络有限公司山东服务中心","gfnsrsbh":""},{"gfmc":"北京中税信息网络有限公司黑龙江服务中心","gfnsrsbh":""},{"gfmc":"北京中税信息网络有限公司河南服务中心","gfnsrsbh":""},{"gfmc":"北京中税信息网络有限公司四川服务中心","gfnsrsbh":""}]
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

        public static class ListsBean  implements IPickerViewData {
            /**
             * gfmc : 北京中税信息网络有限公司
             * gfnsrsbh : 91110102633082712Q
             */

            private String gfmc;
            private String gfnsrsbh;

            public String getGfmc() {
                return gfmc;
            }

            public void setGfmc(String gfmc) {
                this.gfmc = gfmc;
            }

            public String getGfnsrsbh() {
                return gfnsrsbh;
            }

            public void setGfnsrsbh(String gfnsrsbh) {
                this.gfnsrsbh = gfnsrsbh;
            }

            @Override
            public String getPickerViewText() {
                return gfmc;
            }
        }
    }
}
