package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/16 17:00
 * @change
 * @chang time
 * @class describe  开票记录的
 */
public class OpenTicketEntity extends BaseContentEntity {

    /**
     * content : {"total":1,"lists":[{"kp_amount":100,"amount":0.05,"fp_status":"成功","fpdm":"152000186357","fphm":"10390215","tnum":"968878472548192256506100263","url":"15200018635710390215.pdf","xfmc":"51盒子开发二十七","sehj":5.66,"gfmc":"张三","station_name":"彩科彩悦大厦","plate":"粤B12341","kprq":"2018-07-03"}]}
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
         * total : 1
         * lists : [{"kp_amount":100,"amount":0.05,"fp_status":"成功","fpdm":"152000186357","fphm":"10390215","tnum":"968878472548192256506100263","url":"15200018635710390215.pdf","xfmc":"51盒子开发二十七","sehj":5.66,"gfmc":"张三","station_name":"彩科彩悦大厦","plate":"粤B12341","kprq":"2018-07-03"}]
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

        public static class ListsBean implements Serializable {
            /**
             * kp_amount : 100
             * amount : 0.05
             * fp_status : 成功
             * fpdm : 152000186357
             * fphm : 10390215
             * tnum : 968878472548192256506100263
             * url : 15200018635710390215.pdf
             * xfmc : 51盒子开发二十七
             * sehj : 5.66
             * gfmc : 张三
             * station_name : 彩科彩悦大厦
             * plate : 粤B12341
             * kprq : 2018-07-03
             */

            private int kp_amount;
            private double amount;
            private String fp_status;
            private String fpdm;
            private String fphm;
            private String tnum;
            private String url;
            private String xfmc;
            private double sehj;
            private String gfmc;
            private String station_name;
            private String plate;
            private String kprq;

            public int getKp_amount() {
                return kp_amount;
            }

            public void setKp_amount(int kp_amount) {
                this.kp_amount = kp_amount;
            }

            public double getAmount() {
                return amount;
            }

            public void setAmount(double amount) {
                this.amount = amount;
            }

            public String getFp_status() {
                return fp_status;
            }

            public void setFp_status(String fp_status) {
                this.fp_status = fp_status;
            }

            public String getFpdm() {
                return fpdm;
            }

            public void setFpdm(String fpdm) {
                this.fpdm = fpdm;
            }

            public String getFphm() {
                return fphm;
            }

            public void setFphm(String fphm) {
                this.fphm = fphm;
            }

            public String getTnum() {
                return tnum;
            }

            public void setTnum(String tnum) {
                this.tnum = tnum;
            }

            public String getUrl() {
                return url;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getXfmc() {
                return xfmc;
            }

            public void setXfmc(String xfmc) {
                this.xfmc = xfmc;
            }

            public double getSehj() {
                return sehj;
            }

            public void setSehj(double sehj) {
                this.sehj = sehj;
            }

            public String getGfmc() {
                return gfmc;
            }

            public void setGfmc(String gfmc) {
                this.gfmc = gfmc;
            }

            public String getStation_name() {
                return station_name;
            }

            public void setStation_name(String station_name) {
                this.station_name = station_name;
            }

            public String getPlate() {
                return plate;
            }

            public void setPlate(String plate) {
                this.plate = plate;
            }

            public String getKprq() {
                return kprq;
            }

            public void setKprq(String kprq) {
                this.kprq = kprq;
            }
        }
    }
}
