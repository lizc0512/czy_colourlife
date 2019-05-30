package com.door.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Administrator on 2018/9/10.
 *
 * @Description
 */

public class DoorCommunityListEntity {

    /**
     * code : 0
     * message :
     * content : [{"community_uuid":"d4eb5113-b2f1-44b0-89d7-5a8b52277d59","community_name":"东华明珠园","door_list":[{"qr_code":"CSH000018","connection_type":"0","door_type":"0","door_name":"东华明珠园","position":0,"community_type":1,"door_id":60,"door_img":1},{"qr_code":"CSH000017","connection_type":"0","door_type":"0","door_name":"东华明珠园","position":1,"community_type":1,"door_id":61,"door_img":1},{"qr_code":"CSH000016","connection_type":"0","door_type":"0","door_name":"东华明珠园","position":2,"community_type":1,"door_id":62,"door_img":1}]},{"community_uuid":"37919352-32b0-4636-8fd9-4bde386d8747","community_name":"丹枫雅苑","door_list":[{"qr_code":"CSH000021","connection_type":"0","door_type":"0","door_name":"丹枫雅苑","position":3,"community_type":1,"door_id":56,"door_img":1}]},{"community_uuid":"e73d826a-b529-46db-aac4-7d63b993bf33","community_name":"南国丽园","door_list":[{"qr_code":"CSH000019","connection_type":"0","door_type":"0","door_name":"南国丽园","position":4,"community_type":1,"door_id":752,"door_img":1},{"qr_code":"CSH000020","connection_type":"0","door_type":"0","door_name":"南国丽园","position":5,"community_type":1,"door_id":753,"door_img":1}]}]
     * contentEncrypt :
     */

    private int code;
    private String message;
    private String contentEncrypt;
    private List<ContentBean> content;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

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

    public static class ContentBean implements Serializable {
        /**
         * community_uuid : d4eb5113-b2f1-44b0-89d7-5a8b52277d59
         * community_name : 东华明珠园
         * door_list : [{"qr_code":"CSH000018","connection_type":"0","door_type":"0","door_name":"东华明珠园","position":0,"community_type":1,"door_id":60,"door_img":1},{"qr_code":"CSH000017","connection_type":"0","door_type":"0","door_name":"东华明珠园","position":1,"community_type":1,"door_id":61,"door_img":1},{"qr_code":"CSH000016","connection_type":"0","door_type":"0","door_name":"东华明珠园","position":2,"community_type":1,"door_id":62,"door_img":1}]
         */

        private String community_uuid;
        private String community_name;
        private List<DoorListBean> door_list;

        public String getCommunity_uuid() {
            return community_uuid;
        }

        public void setCommunity_uuid(String community_uuid) {
            this.community_uuid = community_uuid;
        }

        public String getCommunity_name() {
            return community_name;
        }

        public void setCommunity_name(String community_name) {
            this.community_name = community_name;
        }

        public List<DoorListBean> getDoor_list() {
            return door_list;
        }

        public void setDoor_list(List<DoorListBean> door_list) {
            this.door_list = door_list;
        }

        public static class DoorListBean {
            /**
             * qr_code : CSH000018
             * connection_type : 0
             * door_type : 0
             * door_name : 东华明珠园
             * position : 0
             * community_type : 1
             * door_id : 60
             * door_img : 1
             */

            private String qr_code;
            private String connection_type;
            private String door_type;
            private String door_name;
            private String position;
            private int community_type;
            private String door_id;
            private String door_img;

            public String getQr_code() {
                return qr_code;
            }

            public void setQr_code(String qr_code) {
                this.qr_code = qr_code;
            }

            public String getConnection_type() {
                return connection_type;
            }

            public void setConnection_type(String connection_type) {
                this.connection_type = connection_type;
            }

            public String getDoor_type() {
                return door_type;
            }

            public void setDoor_type(String door_type) {
                this.door_type = door_type;
            }

            public String getDoor_name() {
                return door_name;
            }

            public void setDoor_name(String door_name) {
                this.door_name = door_name;
            }

            public String getPosition() {
                return position;
            }

            public void setPosition(String position) {
                this.position = position;
            }

            public int getCommunity_type() {
                return community_type;
            }

            public void setCommunity_type(int community_type) {
                this.community_type = community_type;
            }

            public String getDoor_id() {
                return door_id;
            }

            public void setDoor_id(String door_id) {
                this.door_id = door_id;
            }

            public String getDoor_img() {
                return door_img;
            }

            public void setDoor_img(String door_img) {
                this.door_img = door_img;
            }
        }
    }
}
