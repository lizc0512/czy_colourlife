package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/15 17:58
 * @change
 * @chang time
 * @class describe  e停订单支付的模型
 */
public class EparkingPayResultEntity extends BaseContentEntity {


    /**
     * content : {"out_trade_no":"15475095","prepay_id":"15475095","qr_code":"https://colourlife.com","redirect":"2017-01-01 00:00:00"}
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
         * out_trade_no : 15475095
         * prepay_id : 15475095
         * qr_code : https://colourlife.com
         * redirect : 2017-01-01 00:00:00
         */

        private String out_trade_no;
        private String prepay_id;
        private String qr_code;
        private String redirect;

        public String getOut_trade_no() {
            return out_trade_no;
        }

        public void setOut_trade_no(String out_trade_no) {
            this.out_trade_no = out_trade_no;
        }

        public String getPrepay_id() {
            return prepay_id;
        }

        public void setPrepay_id(String prepay_id) {
            this.prepay_id = prepay_id;
        }

        public String getQr_code() {
            return qr_code;
        }

        public void setQr_code(String qr_code) {
            this.qr_code = qr_code;
        }

        public String getRedirect() {
            return redirect;
        }

        public void setRedirect(String redirect) {
            this.redirect = redirect;
        }
    }
}
