package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/26 15:37
 * @change
 * @chang time
 * @class describe
 */
public class OrderInvoiceInforEntity extends BaseContentEntity {
    private List<ContentBean> content;

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * spmc : XX费
         * spbm : 3
         * je : 5000
         * slv : 5
         */

        private String spmc;
        private int spbm;
        private int je;
        private int slv;

        public String getSpmc() {
            return spmc;
        }

        public void setSpmc(String spmc) {
            this.spmc = spmc;
        }

        public int getSpbm() {
            return spbm;
        }

        public void setSpbm(int spbm) {
            this.spbm = spbm;
        }

        public int getJe() {
            return je;
        }

        public void setJe(int je) {
            this.je = je;
        }

        public int getSlv() {
            return slv;
        }

        public void setSlv(int slv) {
            this.slv = slv;
        }
    }
}
