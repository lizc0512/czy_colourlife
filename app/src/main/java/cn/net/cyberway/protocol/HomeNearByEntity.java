package cn.net.cyberway.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/10/18 18:47
 * @change
 * @chang time
 * @class describe  附近小区的
 */

public class HomeNearByEntity extends BaseContentEntity {


    /**
     * content : [{"community_type":"1","desc":"当前小区","list":[{"address":"深圳市龙华区民治街道梅龙路七星商业广场","pid":"","name":"七星商业广场","community_uuid":"bcfe0f35-37b0-49cf-a73d-ca96914a46a5","czy_id":13,"type":1}]},{"community_type":"2","desc":"已加入的小区","list":[{"address":"重庆市渝北区金开大道106号","pid":"","name":"互联网产业园","community_uuid":"79d0ec9e-f900-4899-be13-84fcfb77de76","czy_id":1624,"type":2},{"address":"广东省汕头市潮南区","pid":"","name":"中通雅苑","community_uuid":"aa3de793-ef23-4947-8ad7-7e7e28d2c4a6","czy_id":4578,"type":2},{"address":"桂林市七星区骖鸾路4号","pid":"","name":"众鼎大厦","community_uuid":"6472342b-cbd9-42e8-bb96-af6d40e9a7ce","czy_id":263,"type":2},{"address":"广东省深圳市宝安区","pid":"","name":"金叶茗苑","community_uuid":"9ce172a3-640b-41fe-a171-5ba8df66d1ea","czy_id":1486,"type":2},{"address":"深圳市龙华区留仙大道彩悦大厦","pid":"","name":"彩悦大厦","community_uuid":"82550031-75e7-4a8e-ab8f-074b285ab0a8","czy_id":2,"type":2},{"address":"深圳市龙华区留仙大道彩悦大厦\n","pid":"","name":"彩悦之星","community_uuid":"cc27db5f-f471-4da0-8118-73c511a2b93e","czy_id":2881,"type":2},{"address":"广东省深圳市宝安区油松路89号","pid":"","name":"香缇雅苑","community_uuid":"e334515f-a264-4e47-8529-402a24e8d089","czy_id":69,"type":2}]},{"community_type":"3","desc":"附近小区","list":[{"community_uuid":"03b98def-b5bd-400b-995f-a9af82be01da","name":"互联网社区","address":"","img":"","type":3},{"community_uuid":"ce82c48d-2ad3-4231-9e97-07aec30f0dcc","name":"盛荟居","czy_id":2893,"address":"深圳市龙华区明治街道民塘路519号盛荟居","type":"3","img":""},{"community_uuid":"d4781aed-fad1-4897-aa3b-a6923adc694e","name":"汇龙苑","czy_id":73,"address":"广东省深圳市宝安区","type":"3","img":""},{"community_uuid":"daf42a86-62cc-439f-a654-77063bff68b6","name":"华美丽苑","czy_id":67,"address":"广东省深圳市龙华区","type":"3","img":""},{"community_uuid":"efb6a088-5832-468a-8bf4-d2f6964e7b87","name":"御龙华庭","czy_id":70,"address":"深圳市龙华新区民治街道布龙路与简上路交汇处","type":"3","img":""},{"community_uuid":"ff360acf-4853-4018-b578-7b92c0b47e90","name":"和成世纪名园","czy_id":211,"address":"广东省深圳市龙岗区坂田街道五和大道118号","type":"3","img":""}]}]
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
         * community_type : 1
         * desc : 当前小区
         * list : [{"address":"深圳市龙华区民治街道梅龙路七星商业广场","pid":"","name":"七星商业广场","community_uuid":"bcfe0f35-37b0-49cf-a73d-ca96914a46a5","czy_id":13,"type":1}]
         */

        private String community_type;
        private String desc;
        private List<ListBean> list;

        public String getCommunity_type() {
            return community_type;
        }

        public void setCommunity_type(String community_type) {
            this.community_type = community_type;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * address : 深圳市龙华区民治街道梅龙路七星商业广场
             * pid :
             * name : 七星商业广场
             * community_uuid : bcfe0f35-37b0-49cf-a73d-ca96914a46a5
             * czy_id : 13
             * type : 1
             */

            private String address;
            private String pid;
            private String name;
            private String community_uuid;
            private String czy_id;
            private String type;

            public String getBuild_name() {
                return build_name;
            }

            public void setBuild_name(String build_name) {
                this.build_name = build_name;
            }

            public String getRoom_name() {
                return room_name;
            }

            public void setRoom_name(String room_name) {
                this.room_name = room_name;
            }

            private String build_name;
            private String room_name;

            public String getImg() {
                return img;
            }

            public void setImg(String img) {
                this.img = img;
            }

            private String img;

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getPid() {
                return pid;
            }

            public void setPid(String pid) {
                this.pid = pid;
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

            public String getCzy_id() {
                return czy_id;
            }

            public void setCzy_id(String czy_id) {
                this.czy_id = czy_id;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }
        }
    }
}
