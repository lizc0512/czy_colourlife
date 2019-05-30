package com.im.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.im.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/7/4 20:46
 * @change
 * @chang time
 * @class describe
 */
public class CommunityProgressEntity extends BaseContentEntity {
    /**
     * content : [{"id":10,"im_id":null,"group_name":"测试2额","updated_at":1530587654,"state":1,"title":"你创建的社群【测试2额】正在审核中。","content":"审核通过后，即可添加成员，沟通协同更加高效"},{"id":10,"im_id":null,"group_name":"测试2额","updated_at":1530587654,"state":2,"title":"你创建的社群【测试2额】已审核通过。","content":"点击添加新成员，至少添加两位成员社群才能生效，社群人数越多，权益越多"}]
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
         * id : 10
         * im_id : null
         * group_name : 测试2额
         * updated_at : 1530587654
         * state : 1
         * title : 你创建的社群【测试2额】正在审核中。
         * content : 审核通过后，即可添加成员，沟通协同更加高效
         */

        private int id;
        private String im_id;
        private String group_name;
        private long updated_at;
        private int state;

        public int getChange_state() {
            return change_state;
        }

        public void setChange_state(int change_state) {
            this.change_state = change_state;
        }

        private int change_state;
        private String title;
        private String content;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getIm_id() {
            return im_id;
        }

        public void setIm_id(String im_id) {
            this.im_id = im_id;
        }

        public String getGroup_name() {
            return group_name;
        }

        public void setGroup_name(String group_name) {
            this.group_name = group_name;
        }

        public long getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(long updated_at) {
            this.updated_at = updated_at;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
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
    }
}
