package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

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
public class SingleOpenTicketEntity extends BaseContentEntity {


    /**
     * content : {"id":6,"invguid":"2ddb7ff4-7e6f-11e8-8dfc-fa0b91478fbe","fpdm":"152000186357","fphm":"152000186357","nsrsbh":"15000119780514458045","kprq":"2018-07-03","xfsh":"15000119780514458045","xfmc":"51盒子开发二十七","gfsh":"","gfmc":"张三","jehj":94.34,"sehj":5.66,"url":"15200018635710390215.pdf","tnum":"1011173402108760064011424533","status":2}
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
         * id : 6
         * invguid : 2ddb7ff4-7e6f-11e8-8dfc-fa0b91478fbe
         * fpdm : 152000186357
         * fphm : 152000186357
         * nsrsbh : 15000119780514458045
         * kprq : 2018-07-03
         * xfsh : 15000119780514458045
         * xfmc : 51盒子开发二十七
         * gfsh :
         * gfmc : 张三
         * jehj : 94.34
         * sehj : 5.66
         * url : 15200018635710390215.pdf
         * tnum : 1011173402108760064011424533
         * status : 2
         */

        private int id;
        private String invguid;
        private String fpdm;
        private String fphm;
        private String nsrsbh;
        private String kprq;
        private String xfsh;
        private String xfmc;
        private String gfsh;
        private String gfmc;
        private double jehj;
        private double sehj;
        private String url;
        private String tnum;
        private int status;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getInvguid() {
            return invguid;
        }

        public void setInvguid(String invguid) {
            this.invguid = invguid;
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

        public String getNsrsbh() {
            return nsrsbh;
        }

        public void setNsrsbh(String nsrsbh) {
            this.nsrsbh = nsrsbh;
        }

        public String getKprq() {
            return kprq;
        }

        public void setKprq(String kprq) {
            this.kprq = kprq;
        }

        public String getXfsh() {
            return xfsh;
        }

        public void setXfsh(String xfsh) {
            this.xfsh = xfsh;
        }

        public String getXfmc() {
            return xfmc;
        }

        public void setXfmc(String xfmc) {
            this.xfmc = xfmc;
        }

        public String getGfsh() {
            return gfsh;
        }

        public void setGfsh(String gfsh) {
            this.gfsh = gfsh;
        }

        public String getGfmc() {
            return gfmc;
        }

        public void setGfmc(String gfmc) {
            this.gfmc = gfmc;
        }

        public double getJehj() {
            return jehj;
        }

        public void setJehj(double jehj) {
            this.jehj = jehj;
        }

        public double getSehj() {
            return sehj;
        }

        public void setSehj(double sehj) {
            this.sehj = sehj;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getTnum() {
            return tnum;
        }

        public void setTnum(String tnum) {
            this.tnum = tnum;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
