package cn.net.cyberway.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/10/18 18:53
 * @change
 * @chang time
 * @class describe   搜索小区的
 */

public class HomeSearchCommunityEntity extends BaseContentEntity {

    private List<ContentBean> content;

    public List<ContentBean> getContent() {
        return content;
    }

    public void setContent(List<ContentBean> content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * type : 1
         * img :
         * name : 彩科大厦
         * community_uuid : 82550031-75e7-4a8e-ab8f-074b285ab0a8
         * address : 深圳留仙大道34号
         */

        private String type;
        private String img;
        private String name;
        private String community_uuid;
        private String address;

        public String getCzy_id() {
            return czy_id;
        }

        public void setCzy_id(String czy_id) {
            this.czy_id = czy_id;
        }

        private String czy_id;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCommunity_uuid() {
            return community_uuid;
        }

        public void setCommunity_uuid(String community_uuid) {
            this.community_uuid = community_uuid;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
