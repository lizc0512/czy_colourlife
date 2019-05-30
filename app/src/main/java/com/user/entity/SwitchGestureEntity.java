package com.user.entity;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/26 15:00
 * @change
 * @chang time
 * @class describe  开启/关闭手势密码接口
 */

public class SwitchGestureEntity extends BaseContentEntity {
    /**
     * content : {"switch_on":"1"}
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
         * switch_on : 1
         */

        private String switch_on;

        public String getSwitch_on() {
            return switch_on;
        }

        public void setSwitch_on(String switch_on) {
            this.switch_on = switch_on;
        }
    }
}
