package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/23 16:23
 * @change
 * @chang time
 * @class describe
 */
public class ContractCostEntity extends BaseContentEntity {
    /**
     * content : {"total":100,"begin":"2018-10-01 00:00:00","end":"2018-10-31 23:59:59"}
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
         * total : 100
         * begin : 2018-10-01 00:00:00
         * end : 2018-10-31 23:59:59
         */

        private String total;
        private String begin;
        private String end;

        public String getTotal() {
            return total;
        }

        public void setTotal(String total) {
            this.total = total;
        }

        public String getBegin() {
            return begin;
        }

        public void setBegin(String begin) {
            this.begin = begin;
        }

        public String getEnd() {
            return end;
        }

        public void setEnd(String end) {
            this.end = end;
        }
    }
}
