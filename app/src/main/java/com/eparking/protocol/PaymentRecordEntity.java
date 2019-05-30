package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/19 9:03
 * @change
 * @chang time
 * @class describe  历史记录---缴费记录的
 */
public class PaymentRecordEntity extends BaseContentEntity {


    /**
     * content : {"total":15,"lists":[{"station_name":"国际星城","plate":"粤B006EE","rule_name":"地下月卡70","tnum":"1028221719409856512002306689","amount":420,"paytime":"2018-08-11 18:09:12","station":230,"arrival":"2018-07-01 00:00:00","departure":"2018-12-31 23:59:59","total":6,"type":"MONTH","status":"paid","car":1195415,"duration":15897599,"source":"CZY","rule":1339,"invoice":"N","discount_type":"","cost_price":"","discount_amount":0},{"station_name":"国际星城","plate":"闽CC305Y","rule_name":"地下月卡2","tnum":"1011988398556188672002302591","amount":210,"paytime":"","station":230,"arrival":"2018-10-01 00:00:00","departure":"2018-12-31 23:59:59","total":3,"type":"MONTH","status":"created","car":7806807,"duration":7948799,"source":"EPWECHAT","rule":2922,"invoice":"N","discount_type":"","cost_price":"","discount_amount":0}]}
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
         * total : 15
         * lists : [{"station_name":"国际星城","plate":"粤B006EE","rule_name":"地下月卡70","tnum":"1028221719409856512002306689","amount":420,"paytime":"2018-08-11 18:09:12","station":230,"arrival":"2018-07-01 00:00:00","departure":"2018-12-31 23:59:59","total":6,"type":"MONTH","status":"paid","car":1195415,"duration":15897599,"source":"CZY","rule":1339,"invoice":"N","discount_type":"","cost_price":"","discount_amount":0},{"station_name":"国际星城","plate":"闽CC305Y","rule_name":"地下月卡2","tnum":"1011988398556188672002302591","amount":210,"paytime":"","station":230,"arrival":"2018-10-01 00:00:00","departure":"2018-12-31 23:59:59","total":3,"type":"MONTH","status":"created","car":7806807,"duration":7948799,"source":"EPWECHAT","rule":2922,"invoice":"N","discount_type":"","cost_price":"","discount_amount":0}]
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
             * station_name : 国际星城
             * plate : 粤B006EE
             * rule_name : 地下月卡70
             * tnum : 1028221719409856512002306689
             * amount : 420
             * paytime : 2018-08-11 18:09:12
             * station : 230
             * arrival : 2018-07-01 00:00:00
             * departure : 2018-12-31 23:59:59
             * total : 6
             * type : MONTH
             * status : paid
             * car : 1195415
             * duration : 15897599
             * source : CZY
             * rule : 1339
             * invoice : N
             * discount_type :
             * cost_price :
             * discount_amount : 0
             */

            private String station_name;
            private String plate;
            private String rule_name;
            private String tnum;
            private String amount;
            private String paytime;
            private String station;
            private String arrival;
            private String departure;
            private String total;
            private String type;
            private String status;
            private String car;
            private long duration;
            private String source;
            private String rule;
            private String invoice;
            private String discount_type;
            private String cost_price;
            private int discount_amount;

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

            public String getRule_name() {
                return rule_name;
            }

            public void setRule_name(String rule_name) {
                this.rule_name = rule_name;
            }

            public String getTnum() {
                return tnum;
            }

            public void setTnum(String tnum) {
                this.tnum = tnum;
            }

            public String getAmount() {
                return amount;
            }

            public void setAmount(String amount) {
                this.amount = amount;
            }

            public String getPaytime() {
                return paytime;
            }

            public void setPaytime(String paytime) {
                this.paytime = paytime;
            }

            public String getStation() {
                return station;
            }

            public void setStation(String station) {
                this.station = station;
            }

            public String getArrival() {
                return arrival;
            }

            public void setArrival(String arrival) {
                this.arrival = arrival;
            }

            public String getDeparture() {
                return departure;
            }

            public void setDeparture(String departure) {
                this.departure = departure;
            }

            public String getTotal() {
                return total;
            }

            public void setTotal(String total) {
                this.total = total;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getStatus() {
                return status;
            }

            public void setStatus(String status) {
                this.status = status;
            }

            public String getCar() {
                return car;
            }

            public void setCar(String car) {
                this.car = car;
            }

            public long getDuration() {
                return duration;
            }

            public void setDuration(long duration) {
                this.duration = duration;
            }

            public String getSource() {
                return source;
            }

            public void setSource(String source) {
                this.source = source;
            }

            public String getRule() {
                return rule;
            }

            public void setRule(String rule) {
                this.rule = rule;
            }

            public String getInvoice() {
                return invoice;
            }

            public void setInvoice(String invoice) {
                this.invoice = invoice;
            }

            public String getDiscount_type() {
                return discount_type;
            }

            public void setDiscount_type(String discount_type) {
                this.discount_type = discount_type;
            }

            public String getCost_price() {
                return cost_price;
            }

            public void setCost_price(String cost_price) {
                this.cost_price = cost_price;
            }

            public int getDiscount_amount() {
                return discount_amount;
            }

            public void setDiscount_amount(int discount_amount) {
                this.discount_amount = discount_amount;
            }
        }
    }
}
