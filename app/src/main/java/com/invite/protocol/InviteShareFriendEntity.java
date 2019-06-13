package com.invite.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * Created by hxg on 2019/5/27 14:55
 */
public class InviteShareFriendEntity extends BaseContentEntity {

    /**
     * content : {"accumulate_income":1.24,"invite_url":"https://www.baidu.com","poster_url":"https://www.baidu.com/img/bd_logo1.png","activity_rule_url":"https://cn.bing.com/","share":{"title":"活动分享标题","describe":"活动分享描述","icon":"https://oa.colourlife.com/images/portal1/logo.png"}}
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
         * accumulate_income : 1.24
         * invite_url : https://www.baidu.com
         * poster_url : https://www.baidu.com/img/bd_logo1.png
         * activity_rule_url : https://cn.bing.com/
         * splitbill_url : 及时奖励地址  https:\/\/income-czytest.colourlife.com\/redirect
         * share : {"title":"活动分享标题","describe":"活动分享描述","icon":"https://oa.colourlife.com/images/portal1/logo.png"}
         */

        private String accumulated_income;
        private String invite_url;
        private String poster_url;
        private String activity_rule_url;
        private String splitbill_url;
        private ShareBean share;

        public String getSplitbill_url() {
            return splitbill_url;
        }

        public void setSplitbill_url(String splitbill_url) {
            this.splitbill_url = splitbill_url;
        }

        public String getAccumulated_income() {
            return accumulated_income;
        }

        public void setAccumulated_income(String accumulated_income) {
            this.accumulated_income = accumulated_income;
        }

        public String getInvite_url() {
            return invite_url;
        }

        public void setInvite_url(String invite_url) {
            this.invite_url = invite_url;
        }

        public String getPoster_url() {
            return poster_url;
        }

        public void setPoster_url(String poster_url) {
            this.poster_url = poster_url;
        }

        public String getActivity_rule_url() {
            return activity_rule_url;
        }

        public void setActivity_rule_url(String activity_rule_url) {
            this.activity_rule_url = activity_rule_url;
        }

        public ShareBean getShare() {
            return share;
        }

        public void setShare(ShareBean share) {
            this.share = share;
        }

        public static class ShareBean {
            /**
             * title : 活动分享标题
             * describe : 活动分享描述
             * icon : https://oa.colourlife.com/images/portal1/logo.png
             */

            private String title;
            private String describe;
            private String icon;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getDescribe() {
                return describe;
            }

            public void setDescribe(String describe) {
                this.describe = describe;
            }

            public String getIcon() {
                return icon;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }
        }
    }
}