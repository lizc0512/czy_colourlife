package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/15 10:17
 * @change
 * @chang time
 * @class describe  首页右上角菜单栏的模型
 */
public class HomeFunctionListEntity extends BaseContentEntity {
    /**
     * content : [{"icon":"https://cc.colourlife.com/common/v30/b0_icon_jf@3x.png","name":"申请月卡","redirect":"colourlife://redirect"},{"icon":"https://cc.colourlife.com/common/v30/b0_icon_jf@3x.png","name":"一键挪车","redirect":"colourlife://redirect"}]
     * contentEncrypt :
     */

    private String contentEncrypt;
    private List<ContentBean> content;

    public String getContentEncrypt() {
        return contentEncrypt;
    }

    public void setContentEncrypt(String contentEncrypt) {
        this.contentEncrypt = contentEncrypt;
    }

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * icon : https://cc.colourlife.com/common/v30/b0_icon_jf@3x.png
         * name : 申请月卡
         * redirect : colourlife://redirect
         */

        private String icon;
        private String name;
        private String redirect;

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRedirect() {
            return redirect;
        }

        public void setRedirect(String redirect) {
            this.redirect = redirect;
        }
    }
}
