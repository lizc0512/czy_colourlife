package cn.net.cyberway.home.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/12/14 11:24
 * @change
 * @chang time
 * @class describe
 */
public class HomeHeaderEntity extends BaseContentEntity {
    /**
     * content : {"background_img":"http://www.colourlife.com/image","identity_type":1,"icon_name_01":"彩钱包","icon_redirect_01":"http://www.colourlife.com/redirect","icon_name_02":"彩住宅","icon_redirect_02":"http://www.colourlife.com/redirect","icon_name_03":"彩车位","icon_redirect_03":"http://www.colourlife.com/redirect","fp_balance":11,"lq_balance":22,"return_total":22,"return_stage":"12/24"}
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
         * background_img : http://www.colourlife.com/image
         * identity_type : 1
         * icon_name_01 : 彩钱包
         * icon_redirect_01 : http://www.colourlife.com/redirect
         * icon_name_02 : 彩住宅
         * icon_redirect_02 : http://www.colourlife.com/redirect
         * icon_name_03 : 彩车位
         * icon_redirect_03 : http://www.colourlife.com/redirect
         * fp_balance : 11
         * lq_balance : 22
         * return_total : 22
         * return_stage : 12/24
         */

        private String background_img;
        private int identity_type;

        private String icon_redirect_01;

        private String icon_redirect_02;
        private String fp_balance;
        private String lq_balance;
        private String return_total;
        private String return_stage;
        private String fp_icon;
        private String more_icon;
        private String fp_balance_icon;
        private String lq_balance_icon;
        private String return_total_icon;
        private String return_stage_icon;
        private String font_color;
        private String background_img_2;
        private String background_img_3;
        private int is_holiday;
        private String identity_name;
        private String tab_color;
        private String return_name;

        public String getReturn_name() {
            return return_name;
        }

        public void setReturn_name(String return_name) {
            this.return_name = return_name;
        }

        public String getBalance_name() {
            return balance_name;
        }

        public void setBalance_name(String balance_name) {
            this.balance_name = balance_name;
        }

        private String balance_name;


        public String getFp_icon() {
            return fp_icon;
        }

        public void setFp_icon(String fp_icon) {
            this.fp_icon = fp_icon;
        }

        public String getMore_icon() {
            return more_icon;
        }

        public void setMore_icon(String more_icon) {
            this.more_icon = more_icon;
        }



        public String getFp_balance_icon() {
            return fp_balance_icon;
        }

        public void setFp_balance_icon(String fp_balance_icon) {
            this.fp_balance_icon = fp_balance_icon;
        }

        public String getLq_balance_icon() {
            return lq_balance_icon;
        }

        public void setLq_balance_icon(String lq_balance_icon) {
            this.lq_balance_icon = lq_balance_icon;
        }

        public String getReturn_total_icon() {
            return return_total_icon;
        }

        public void setReturn_total_icon(String return_total_icon) {
            this.return_total_icon = return_total_icon;
        }

        public String getReturn_stage_icon() {
            return return_stage_icon;
        }

        public void setReturn_stage_icon(String return_stage_icon) {
            this.return_stage_icon = return_stage_icon;
        }

        public String getFont_color() {
            return font_color;
        }

        public void setFont_color(String font_color) {
            this.font_color = font_color;
        }

        public String getBackground_img_2() {
            return background_img_2;
        }

        public void setBackground_img_2(String background_img_2) {
            this.background_img_2 = background_img_2;
        }

        public int getIs_holiday() {
            return is_holiday;
        }

        public void setIs_holiday(int is_holiday) {
            this.is_holiday = is_holiday;
        }



        public String getBackground_img_3() {
            return background_img_3;
        }

        public void setBackground_img_3(String background_img_3) {
            this.background_img_3 = background_img_3;
        }


        public String getIdentity_name() {
            return identity_name;
        }

        public void setIdentity_name(String identity_name) {
            this.identity_name = identity_name;
        }




        public String getTab_color() {
            return tab_color;
        }

        public void setTab_color(String tab_color) {
            this.tab_color = tab_color;
        }


        public String getBackground_img() {
            return background_img;
        }

        public void setBackground_img(String background_img) {
            this.background_img = background_img;
        }

        public int getIdentity_type() {
            return identity_type;
        }

        public void setIdentity_type(int identity_type) {
            this.identity_type = identity_type;
        }


        public String getIcon_redirect_01() {
            return icon_redirect_01;
        }

        public void setIcon_redirect_01(String icon_redirect_01) {
            this.icon_redirect_01 = icon_redirect_01;
        }


        public String getIcon_redirect_02() {
            return icon_redirect_02;
        }

        public void setIcon_redirect_02(String icon_redirect_02) {
            this.icon_redirect_02 = icon_redirect_02;
        }

        public String getFp_balance() {
            return fp_balance;
        }

        public void setFp_balance(String fp_balance) {
            this.fp_balance = fp_balance;
        }

        public String getLq_balance() {
            return lq_balance;
        }

        public void setLq_balance(String lq_balance) {
            this.lq_balance = lq_balance;
        }

        public String getReturn_total() {
            return return_total;
        }

        public void setReturn_total(String return_total) {
            this.return_total = return_total;
        }

        public String getReturn_stage() {
            return return_stage;
        }

        public void setReturn_stage(String return_stage) {
            this.return_stage = return_stage;
        }
    }
}
