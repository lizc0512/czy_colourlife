package com.im.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.im.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/26 18:21
 * @change
 * @chang time
 * @class describe  IM用户的相关信息
 */

public class SameComunityPeopleEntity extends BaseContentEntity {

    /**
     * content : {"list":[{"id":2507637,"uuid":"","mobile":"13000000020","is_deleted":1,"state":0,"email":"","portrait":"http://cimg-czytest.colourlife.com/images/2017/12/31/22/460920526.jpg","gender":0,"nick_name":"半妖嗯嗯","name":"访客","community_uuid":"","community_name":"","real_name":"","comment":"mknvekn"},{"id":1002646939,"uuid":"a0127ebf-6179-4276-b65e-50c329e18c41","mobile":"18617194368","is_deleted":1,"state":0,"email":"","portrait":"http://cimg-czytest.colourlife.com/images/2018/06/26/09/443713650.jpg","gender":1,"nick_name":"zhangxi","name":"张锡旺","community_uuid":"82550031-75e7-4a8e-ab8f-074b285ab0a8","community_name":"彩科大厦","real_name":"张锡旺","comment":"vvv"}],"page_size":10,"total_page":1,"current_page":"1","total_record":2}
     */

    private ContentBean content;

    public ContentBean getContent() {
        return content;
    }

    public void setContent(ContentBean content) {
        this.content = content;
    }

    public static class ContentBean {
        /**
         * list : [{"id":2507637,"uuid":"","mobile":"13000000020","is_deleted":1,"state":0,"email":"","portrait":"http://cimg-czytest.colourlife.com/images/2017/12/31/22/460920526.jpg","gender":0,"nick_name":"半妖嗯嗯","name":"访客","community_uuid":"","community_name":"","real_name":"","comment":"mknvekn"},{"id":1002646939,"uuid":"a0127ebf-6179-4276-b65e-50c329e18c41","mobile":"18617194368","is_deleted":1,"state":0,"email":"","portrait":"http://cimg-czytest.colourlife.com/images/2018/06/26/09/443713650.jpg","gender":1,"nick_name":"zhangxi","name":"张锡旺","community_uuid":"82550031-75e7-4a8e-ab8f-074b285ab0a8","community_name":"彩科大厦","real_name":"张锡旺","comment":"vvv"}]
         * page_size : 10
         * total_page : 1
         * current_page : 1
         * total_record : 2
         */

        private int page_size;
        private int total_page;
        private String current_page;
        private int total_record;
        private List<ListBean> list;

        public int getPage_size() {
            return page_size;
        }

        public void setPage_size(int page_size) {
            this.page_size = page_size;
        }

        public int getTotal_page() {
            return total_page;
        }

        public void setTotal_page(int total_page) {
            this.total_page = total_page;
        }

        public String getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(String current_page) {
            this.current_page = current_page;
        }

        public int getTotal_record() {
            return total_record;
        }

        public void setTotal_record(int total_record) {
            this.total_record = total_record;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 2507637
             * uuid :
             * mobile : 13000000020
             * is_deleted : 1
             * state : 0
             * email :
             * portrait : http://cimg-czytest.colourlife.com/images/2017/12/31/22/460920526.jpg
             * gender : 0
             * nick_name : 半妖嗯嗯
             * name : 访客
             * community_uuid :
             * community_name :
             * real_name :
             * comment : mknvekn
             */

            private String id;
            private String uuid;
            private String mobile;
            private String is_deleted;
            private String state; //状态，0：正常，1：禁用,2,未注册  3.好友
            private String email;
            private String portrait;
            private String gender;
            private String nick_name;
            private String name;
            private String community_uuid;
            private String community_name;
            private String real_name;
            private String comment;

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            private String distance;
            private String sortLetters;  //显示数据拼音的首字母


            public String getSortLetters() {
                return sortLetters;
            }

            public void setSortLetters(String sortLetters) {
                this.sortLetters = sortLetters;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getIs_deleted() {
                return is_deleted;
            }

            public void setIs_deleted(String is_deleted) {
                this.is_deleted = is_deleted;
            }

            public String getState() {
                return state;
            }

            public void setState(String state) {
                this.state = state;
            }

            public String getEmail() {
                return email;
            }

            public void setEmail(String email) {
                this.email = email;
            }

            public String getPortrait() {
                return portrait;
            }

            public void setPortrait(String portrait) {
                this.portrait = portrait;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getNick_name() {
                return nick_name;
            }

            public void setNick_name(String nick_name) {
                this.nick_name = nick_name;
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

            public String getCommunity_name() {
                return community_name;
            }

            public void setCommunity_name(String community_name) {
                this.community_name = community_name;
            }

            public String getReal_name() {
                return real_name;
            }

            public void setReal_name(String real_name) {
                this.real_name = real_name;
            }

            public String getComment() {
                return comment;
            }

            public void setComment(String comment) {
                this.comment = comment;
            }
        }
    }
}
