package com.community.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * author:yuansk
 * cretetime:2020/2/26
 * desc:举报类型的
 **/
public class CommunityTipOffEntity extends BaseContentEntity {
    /**
     * content : [{"id":1,"report_type":"营销广告"},{"id":2,"report_type":"政治相关"},{"id":3,"report_type":"色情低俗"},{"id":4,"report_type":"辱骂攻击"},{"id":5,"report_type":"垃圾内容"}]
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
         * id : 1
         * report_type : 营销广告
         */

        private String id;
        private String report_type;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getReport_type() {
            return report_type;
        }

        public void setReport_type(String report_type) {
            this.report_type = report_type;
        }
    }
}
