package com.door.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

import cn.csh.colourful.life.view.pickview.model.IPickerViewData;

/**
 * @name ${yuansk}
 * @class name：com.door.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/4/1 9:59
 * @change
 * @chang time
 * @class describe
 */
public class DoorCommunityEntity extends BaseContentEntity {
    /**
     * content : {"communitylist":[{"bid":10003685,"name":"彩生活大厦","colorid":2,"uuid":"82550031-75e7-4a8e-ab8f-074b285ab0a8"}]}
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
        private List<CommunitylistBean> communitylist;

        public List<CommunitylistBean> getCommunitylist() {
            return communitylist;
        }

        public void setCommunitylist(List<CommunitylistBean> communitylist) {
            this.communitylist = communitylist;
        }

        public static class CommunitylistBean implements IPickerViewData {
            /**
             * bid : 10003685
             * name : 彩生活大厦
             * colorid : 2
             * uuid : 82550031-75e7-4a8e-ab8f-074b285ab0a8
             */

            private String bid;
            private String name;
            private String colorid;
            private String uuid;

            public String getBid() {
                return bid;
            }

            public void setBid(String bid) {
                this.bid = bid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getColorid() {
                return colorid;
            }

            public void setColorid(String colorid) {
                this.colorid = colorid;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            @Override
            public String getPickerViewText() {
                return name;
            }
        }
    }
}
