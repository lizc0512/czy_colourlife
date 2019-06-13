package com.invite.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * Created by hxg on 2019/5/27 16:55
 */
public class InviteInviteRecodeEntity extends BaseContentEntity {

    /**
     * content : {"now_page":1,"total":2,"page_size":10,"list":[{"id":21,"mobile":"185*****563","level":1,"award_amount":"0.00","create_time":"2019.04.02 16:17:41"},{"id":12,"mobile":"185*****533","level":1,"award_amount":"0.00","create_time":"2019.04.02 16:10:53"}]}
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
         * now_page : 1
         * total : 2
         * page_size : 10
         * list : [{"id":21,"mobile":"185*****563","level":1,"award_amount":"0.00","create_time":"2019.04.02 16:17:41"},{"id":12,"mobile":"185*****533","level":1,"award_amount":"0.00","create_time":"2019.04.02 16:10:53"}]
         */
        private int now_page;
        private int total;
        private int page_size;
        private List<ListBean> list;

        public int getNow_page() {
            return now_page;
        }

        public void setNow_page(int now_page) {
            this.now_page = now_page;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 21
             * mobile : 185*****563
             * level : 1
             * award_amount : 0.00
             * create_time : 2019.04.02 16:17:41
             *
             *
             *
             "id": 56,
             "level": 2,
             "amount": "0.30",
             "describe": "相关收益-177*****425",
             "type": 1,
             "create_time": "2019.04.19 09:42"
             */

            private int id;
            private String mobile;
            private int level;
            private String award_amount;
            private String create_time;

//            private String amount;
//            private String describe;
//            private String type;

//            public String getAmount() {
//                return amount;
//            }
//
//            public void setAmount(String amount) {
//                this.amount = amount;
//            }
//
//            public String getDescribe() {
//                return describe;
//            }
//
//            public void setDescribe(String describe) {
//                this.describe = describe;
//            }
//
//            public String getType() {
//                return type;
//            }
//
//            public void setType(String type) {
//                this.type = type;
//            }

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getLevel() {
                return level;
            }

            public void setLevel(int level) {
                this.level = level;
            }

            public String getAward_amount() {
                return award_amount;
            }

            public void setAward_amount(String award_amount) {
                this.award_amount = award_amount;
            }

            public String getCreate_time() {
                return create_time;
            }

            public void setCreate_time(String create_time) {
                this.create_time = create_time;
            }
        }
    }
}