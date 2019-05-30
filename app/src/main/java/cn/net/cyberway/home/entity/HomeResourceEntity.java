package cn.net.cyberway.home.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/2/22 11:03
 * @change
 * @chang time
 * @class describe
 */
public class HomeResourceEntity extends BaseContentEntity {


    /**
     * content : {"identity_type":2,"fp_balance_icon":"https://pics-czy-cdn.colourlife.com/dev-5c36abfd6e460596840.png","lq_balance_icon":"https://pics-czy-cdn.colourlife.com/dev-5c36ac0088fde770440.png","return_total_icon":"https://pics-czy-cdn.colourlife.com/dev-5c36ac03e93a4129833.png","return_stage_icon":"https://pics-czy-cdn.colourlife.com/dev-5c36ac082b960111835.png","font_color":"#ffffff","is_holiday":null,"background_img":"https://pics-czy-cdn.colourlife.com/dev-5c35e5df9c932859336.png","background_img_2":"https://pics-czy-cdn.colourlife.com/dev-5c36a6e38173b990324.png","background_img_3":"https://pics-czy-cdn.colourlife.com/dev-5c36a6e38173b990324.png","tab_color":"#ff0000","fp_icon":"https://pics-czy-cdn.colourlife.com/dev-5c45b59346bf9116358.png","more_icon":"https://pics-czy-cdn.colourlife.com/dev-5c45b58fcb31d743728.png","protect":0}
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
         * identity_type : 2
         * fp_balance_icon : https://pics-czy-cdn.colourlife.com/dev-5c36abfd6e460596840.png
         * lq_balance_icon : https://pics-czy-cdn.colourlife.com/dev-5c36ac0088fde770440.png
         * return_total_icon : https://pics-czy-cdn.colourlife.com/dev-5c36ac03e93a4129833.png
         * return_stage_icon : https://pics-czy-cdn.colourlife.com/dev-5c36ac082b960111835.png
         * font_color : #ffffff
         * is_holiday : null
         * background_img : https://pics-czy-cdn.colourlife.com/dev-5c35e5df9c932859336.png
         * background_img_2 : https://pics-czy-cdn.colourlife.com/dev-5c36a6e38173b990324.png
         * background_img_3 : https://pics-czy-cdn.colourlife.com/dev-5c36a6e38173b990324.png
         * tab_color : #ff0000
         * fp_icon : https://pics-czy-cdn.colourlife.com/dev-5c45b59346bf9116358.png
         * more_icon : https://pics-czy-cdn.colourlife.com/dev-5c45b58fcb31d743728.png
         * protect : 0
         */

        private int identity_type;
        private String fp_balance_icon;
        private String lq_balance_icon;
        private String return_total_icon;
        private String return_stage_icon;
        private String font_color;
        private int is_holiday;
        private String background_img;
        private String background_img_2;
        private String background_img_3;
        private String tab_color;
        private String fp_icon;
        private String msg_img;
        private String more_icon;
        private String position_img;
        private int protect;

        public String getMsg_img() {
            return msg_img;
        }

        public void setMsg_img(String msg_img) {
            this.msg_img = msg_img;
        }

        public int getIdentity_type() {
            return identity_type;
        }

        public void setIdentity_type(int identity_type) {
            this.identity_type = identity_type;
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

        public int getIs_holiday() {
            return is_holiday;
        }

        public void setIs_holiday(int is_holiday) {
            this.is_holiday = is_holiday;
        }

        public String getBackground_img() {
            return background_img;
        }

        public void setBackground_img(String background_img) {
            this.background_img = background_img;
        }

        public String getBackground_img_2() {
            return background_img_2;
        }

        public void setBackground_img_2(String background_img_2) {
            this.background_img_2 = background_img_2;
        }

        public String getBackground_img_3() {
            return background_img_3;
        }

        public void setBackground_img_3(String background_img_3) {
            this.background_img_3 = background_img_3;
        }

        public String getTab_color() {
            return tab_color;
        }

        public void setTab_color(String tab_color) {
            this.tab_color = tab_color;
        }

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

        public int getProtect() {
            return protect;
        }

        public void setProtect(int protect) {
            this.protect = protect;
        }

        public String getPosition_img() {
            return position_img;
        }

        public void setPosition_img(String position_img) {
            this.position_img = position_img;
        }
    }
}
