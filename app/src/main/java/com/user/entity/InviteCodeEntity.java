package com.user.entity;

import com.google.gson.annotations.SerializedName;
import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.user.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/2/26 16:44
 * @change
 * @chang time
 * @class describe  邀请码的
 */

public class InviteCodeEntity extends BaseContentEntity {
    /**
     * content : {"invite_code":1234,"message":"我正在使用彩之云APP，物业费，停车费轻松缴，E理财高收益无风险，10%年化收益。赶紧下载彩之云APPhttp://dwz.cn/8YPIv，我的邀请码是:MVF0G，注册时一定要填写喔!","title":"我正在使用彩之云APP！","content":"物业费用一键缴纳，家庭维修随叫随到，彩富人生让你变身物业VIP~","redirect_url":"http://m.colourlife.com/home/register?customer_id=3321069","icon":"https://cc.colourlife.com/common/v30/logo/app_logo_v30.png"}
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
         * invite_code : 1234
         * message : 我正在使用彩之云APP，物业费，停车费轻松缴，E理财高收益无风险，10%年化收益。赶紧下载彩之云APPhttp://dwz.cn/8YPIv，我的邀请码是:MVF0G，注册时一定要填写喔!
         * title : 我正在使用彩之云APP！
         * content : 物业费用一键缴纳，家庭维修随叫随到，彩富人生让你变身物业VIP~
         * redirect_url : http://m.colourlife.com/home/register?customer_id=3321069
         * icon : https://cc.colourlife.com/common/v30/logo/app_logo_v30.png
         */

        private int invite_code;
        @SerializedName("message")
        private String messageX;
        private String title;
        private String content;
        private String redirect_url;
        private String icon;

        public int getInvite_code() {
            return invite_code;
        }

        public void setInvite_code(int invite_code) {
            this.invite_code = invite_code;
        }

        public String getMessageX() {
            return messageX;
        }

        public void setMessageX(String messageX) {
            this.messageX = messageX;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getRedirect_url() {
            return redirect_url;
        }

        public void setRedirect_url(String redirect_url) {
            this.redirect_url = redirect_url;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }
    }
}
