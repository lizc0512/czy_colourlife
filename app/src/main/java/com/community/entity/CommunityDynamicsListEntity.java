package com.community.entity;

import com.nohttp.entity.BaseContentEntity;

import java.io.Serializable;
import java.util.List;

/**
 * author:yuansk
 * cretetime:2020/2/24
 * desc:社区动态列表的
 **/
public class CommunityDynamicsListEntity extends BaseContentEntity {
    /**
     * content : {"current_page":1,"data":[{"id":1,"user_id":1002848636,"user_uuid":"697d287e-7374-4028-a542-99c67b04a1b6","content":"测试动态了为","extra":["jkk.png","egeg.png"],"extra_type":1,"status":1,"created_at":"1582251574","updated_at":"1582251574","community_uuid":"c0400d56-f925-4f51-a333-58338f345cc2","community_name":"彩网测试小区","nickname":"0011","avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg","gender":"1","mobile":"15400000011","comment":[{"id":1,"source_id":1,"from_id":1002848636,"to_id":"\u201c\u201d","content":"咋此测试啊","status":1,"created_at":"1582251625","updated_at":"1582251625","from_nickname":"0011","from_avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg","from_gender":"1","from_mobile":"15400000011","to_nickname":"\u201c\u201d","to_avatar":"\u201c\u201d","to_gender":"\u201c\u201d","to_mobile":"\u201c\u201d"},{"id":2,"source_id":1,"from_id":1002646939,"to_id":1002848636,"content":"咋此测试啊","status":1,"created_at":"1582255148","updated_at":"1582255148","from_nickname":"zhangxi","from_avatar":"https://cimg.colourlife.com/images//2018/06/26/09/443713650.jpg","from_gender":"1","from_mobile":"18617194368","to_nickname":"0011","to_avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg","to_gender":"1","to_mobile":"15400000011"}],"comment_count":2,"zan":[{"id":1,"source_id":1,"from_id":1002848636,"status":1,"created_at":"1582251768","updated_at":"1582251768","from_nickname":"0011","from_avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg","from_gender":"1","from_mobile":"15400000011"}],"zan_count":1},{"id":2,"user_id":1002848636,"user_uuid":"697d287e-7374-4028-a542-99c67b04a1b6","content":"咋此测试啊","extra":null,"extra_type":null,"status":2,"created_at":"1582251596","updated_at":"1582260594","community_uuid":"c0400d56-f925-4f51-a333-58338f345cc2","community_name":"彩网测试小区","nickname":"0011","avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg","gender":"1","mobile":"15400000011","comment_count":2,"zan_count":1}],"from":1,"last_page":1,"next_page_url":"\u201c\u201d","path":"http://www.neighbour.com/neighbour/getDynamic","per_page":10,"prev_page_url":"\u201c\u201d","to":2,"total":2,"year":""}
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
         * current_page : 1
         * data : [{"id":1,"user_id":1002848636,"user_uuid":"697d287e-7374-4028-a542-99c67b04a1b6","content":"测试动态了为","extra":["jkk.png","egeg.png"],"extra_type":1,"status":1,"created_at":"1582251574","updated_at":"1582251574","community_uuid":"c0400d56-f925-4f51-a333-58338f345cc2","community_name":"彩网测试小区","nickname":"0011","avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg","gender":"1","mobile":"15400000011","comment":[{"id":1,"source_id":1,"from_id":1002848636,"to_id":"\u201c\u201d","content":"咋此测试啊","status":1,"created_at":"1582251625","updated_at":"1582251625","from_nickname":"0011","from_avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg","from_gender":"1","from_mobile":"15400000011","to_nickname":"\u201c\u201d","to_avatar":"\u201c\u201d","to_gender":"\u201c\u201d","to_mobile":"\u201c\u201d"},{"id":2,"source_id":1,"from_id":1002646939,"to_id":1002848636,"content":"咋此测试啊","status":1,"created_at":"1582255148","updated_at":"1582255148","from_nickname":"zhangxi","from_avatar":"https://cimg.colourlife.com/images//2018/06/26/09/443713650.jpg","from_gender":"1","from_mobile":"18617194368","to_nickname":"0011","to_avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg","to_gender":"1","to_mobile":"15400000011"}],"comment_count":2,"zan":[{"id":1,"source_id":1,"from_id":1002848636,"status":1,"created_at":"1582251768","updated_at":"1582251768","from_nickname":"0011","from_avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg","from_gender":"1","from_mobile":"15400000011"}],"zan_count":1},{"id":2,"user_id":1002848636,"user_uuid":"697d287e-7374-4028-a542-99c67b04a1b6","content":"咋此测试啊","extra":null,"extra_type":null,"status":2,"created_at":"1582251596","updated_at":"1582260594","community_uuid":"c0400d56-f925-4f51-a333-58338f345cc2","community_name":"彩网测试小区","nickname":"0011","avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg","gender":"1","mobile":"15400000011","comment_count":2,"zan_count":1}]
         * from : 1
         * last_page : 1
         * next_page_url : “”
         * path : http://www.neighbour.com/neighbour/getDynamic
         * per_page : 10
         * prev_page_url : “”
         * to : 2
         * total : 2
         * year :
         */

        private int current_page;
        private int from;
        private int last_page;
        private String next_page_url;
        private String path;
        private int per_page;
        private String prev_page_url;
        private int to;
        private int total;
        private String year;
        private List<DataBean> data;

        public int getCurrent_page() {
            return current_page;
        }

        public void setCurrent_page(int current_page) {
            this.current_page = current_page;
        }

        public int getFrom() {
            return from;
        }

        public void setFrom(int from) {
            this.from = from;
        }

        public int getLast_page() {
            return last_page;
        }

        public void setLast_page(int last_page) {
            this.last_page = last_page;
        }

        public String getNext_page_url() {
            return next_page_url;
        }

        public void setNext_page_url(String next_page_url) {
            this.next_page_url = next_page_url;
        }

        public String getPath() {
            return path;
        }

        public void setPath(String path) {
            this.path = path;
        }

        public int getPer_page() {
            return per_page;
        }

        public void setPer_page(int per_page) {
            this.per_page = per_page;
        }

        public String getPrev_page_url() {
            return prev_page_url;
        }

        public void setPrev_page_url(String prev_page_url) {
            this.prev_page_url = prev_page_url;
        }

        public int getTo() {
            return to;
        }

        public void setTo(int to) {
            this.to = to;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class DataBean implements Serializable {
            /**
             * id : 1
             * user_id : 1002848636
             * user_uuid : 697d287e-7374-4028-a542-99c67b04a1b6
             * content : 测试动态了为
             * extra : ["jkk.png","egeg.png"]
             * extra_type : 1
             * status : 1
             * created_at : 1582251574
             * updated_at : 1582251574
             * community_uuid : c0400d56-f925-4f51-a333-58338f345cc2
             * community_name : 彩网测试小区
             * nickname : 0011
             * avatar : https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg
             * gender : 1
             * mobile : 15400000011
             * comment : [{"id":1,"source_id":1,"from_id":1002848636,"to_id":"\u201c\u201d","content":"咋此测试啊","status":1,"created_at":"1582251625","updated_at":"1582251625","from_nickname":"0011","from_avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg","from_gender":"1","from_mobile":"15400000011","to_nickname":"\u201c\u201d","to_avatar":"\u201c\u201d","to_gender":"\u201c\u201d","to_mobile":"\u201c\u201d"},{"id":2,"source_id":1,"from_id":1002646939,"to_id":1002848636,"content":"咋此测试啊","status":1,"created_at":"1582255148","updated_at":"1582255148","from_nickname":"zhangxi","from_avatar":"https://cimg.colourlife.com/images//2018/06/26/09/443713650.jpg","from_gender":"1","from_mobile":"18617194368","to_nickname":"0011","to_avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg","to_gender":"1","to_mobile":"15400000011"}]
             * comment_count : 2
             * zan : [{"id":1,"source_id":1,"from_id":1002848636,"status":1,"created_at":"1582251768","updated_at":"1582251768","from_nickname":"0011","from_avatar":"https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg","from_gender":"1","from_mobile":"15400000011"}]
             * zan_count : 1
             */

            private int id;
            private int user_id;
            private String user_uuid;

            public String getSource_id() {
                return source_id;
            }

            public void setSource_id(String source_id) {
                this.source_id = source_id;
            }

            private String source_id;
            private String content;
            private int extra_type;
            private int status;
            private long created_at;
            private String updated_at;
            private String community_uuid;
            private String community_name;
            private String nickname;
            private String avatar;
            private String gender;
            private String mobile;

            public String getIs_zan() {
                return is_zan;
            }

            public void setIs_zan(String is_zan) {
                this.is_zan = is_zan;
            }

            private String is_zan ;
            private int comment_count;
            private int zan_count=0;
            private List<String> extra;
            private List<CommentBean> comment;
            private List<ZanBean> zan;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public int getUser_id() {
                return user_id;
            }

            public void setUser_id(int user_id) {
                this.user_id = user_id;
            }

            public String getUser_uuid() {
                return user_uuid;
            }

            public void setUser_uuid(String user_uuid) {
                this.user_uuid = user_uuid;
            }

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public int getExtra_type() {
                return extra_type;
            }

            public void setExtra_type(int extra_type) {
                this.extra_type = extra_type;
            }

            public int getStatus() {
                return status;
            }

            public void setStatus(int status) {
                this.status = status;
            }

            public long getCreated_at() {
                return created_at;
            }

            public void setCreated_at(long created_at) {
                this.created_at = created_at;
            }

            public String getUpdated_at() {
                return updated_at;
            }

            public void setUpdated_at(String updated_at) {
                this.updated_at = updated_at;
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

            public String getNickname() {
                return nickname;
            }

            public void setNickname(String nickname) {
                this.nickname = nickname;
            }

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getGender() {
                return gender;
            }

            public void setGender(String gender) {
                this.gender = gender;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public int getComment_count() {
                return comment_count;
            }

            public void setComment_count(int comment_count) {
                this.comment_count = comment_count;
            }

            public int getZan_count() {
                return zan_count;
            }

            public void setZan_count(int zan_count) {
                this.zan_count = zan_count;
            }

            public List<String> getExtra() {
                return extra;
            }

            public void setExtra(List<String> extra) {
                this.extra = extra;
            }

            public List<CommentBean> getComment() {
                return comment;
            }

            public void setComment(List<CommentBean> comment) {
                this.comment = comment;
            }

            public List<ZanBean> getZan() {
                return zan;
            }

            public void setZan(List<ZanBean> zan) {
                this.zan = zan;
            }

            public static class CommentBean implements Serializable{
                /**
                 * id : 1
                 * source_id : 1
                 * from_id : 1002848636
                 * to_id : “”
                 * content : 咋此测试啊
                 * status : 1
                 * created_at : 1582251625
                 * updated_at : 1582251625
                 * from_nickname : 0011
                 * from_avatar : https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg
                 * from_gender : 1
                 * from_mobile : 15400000011
                 * to_nickname : “”
                 * to_avatar : “”
                 * to_gender : “”
                 * to_mobile : “”
                 */

                private String id;
                private String source_id;
                private String from_id;
                private String to_id;
                private String content;
                private int status;
                private long created_at;
                private String updated_at;
                private String from_nickname;
                private String from_avatar;
                private String from_gender;
                private String from_mobile;
                private String to_nickname;
                private String to_avatar;
                private String to_gender;
                private String to_mobile;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public String getSource_id() {
                    return source_id;
                }

                public void setSource_id(String source_id) {
                    this.source_id = source_id;
                }

                public String getFrom_id() {
                    return from_id;
                }

                public void setFrom_id(String from_id) {
                    this.from_id = from_id;
                }

                public String getTo_id() {
                    return to_id;
                }

                public void setTo_id(String to_id) {
                    this.to_id = to_id;
                }

                public String getContent() {
                    return content;
                }

                public void setContent(String content) {
                    this.content = content;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public long getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(long created_at) {
                    this.created_at = created_at;
                }

                public String getUpdated_at() {
                    return updated_at;
                }

                public void setUpdated_at(String updated_at) {
                    this.updated_at = updated_at;
                }

                public String getFrom_nickname() {
                    return from_nickname;
                }

                public void setFrom_nickname(String from_nickname) {
                    this.from_nickname = from_nickname;
                }

                public String getFrom_avatar() {
                    return from_avatar;
                }

                public void setFrom_avatar(String from_avatar) {
                    this.from_avatar = from_avatar;
                }

                public String getFrom_gender() {
                    return from_gender;
                }

                public void setFrom_gender(String from_gender) {
                    this.from_gender = from_gender;
                }

                public String getFrom_mobile() {
                    return from_mobile;
                }

                public void setFrom_mobile(String from_mobile) {
                    this.from_mobile = from_mobile;
                }

                public String getTo_nickname() {
                    return to_nickname;
                }

                public void setTo_nickname(String to_nickname) {
                    this.to_nickname = to_nickname;
                }

                public String getTo_avatar() {
                    return to_avatar;
                }

                public void setTo_avatar(String to_avatar) {
                    this.to_avatar = to_avatar;
                }

                public String getTo_gender() {
                    return to_gender;
                }

                public void setTo_gender(String to_gender) {
                    this.to_gender = to_gender;
                }

                public String getTo_mobile() {
                    return to_mobile;
                }

                public void setTo_mobile(String to_mobile) {
                    this.to_mobile = to_mobile;
                }
            }

            public static class ZanBean implements Serializable{
                /**
                 * id : 1
                 * source_id : 1
                 * from_id : 1002848636
                 * status : 1
                 * created_at : 1582251768
                 * updated_at : 1582251768
                 * from_nickname : 0011
                 * from_avatar : https://nczy-user-avatar.oss-cn-shenzhen.aliyuncs.com/czy-Portrait/dev-5cd1667986d15659098.jpg
                 * from_gender : 1
                 * from_mobile : 15400000011
                 */

                private String id;
                private int source_id;
                private String from_id;
                private int status;
                private String created_at;
                private String updated_at;
                private String from_nickname;
                private String from_avatar;
                private String from_gender;
                private String from_mobile;

                public String getId() {
                    return id;
                }

                public void setId(String id) {
                    this.id = id;
                }

                public int getSource_id() {
                    return source_id;
                }

                public void setSource_id(int source_id) {
                    this.source_id = source_id;
                }

                public String getFrom_id() {
                    return from_id;
                }

                public void setFrom_id(String from_id) {
                    this.from_id = from_id;
                }

                public int getStatus() {
                    return status;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public String getCreated_at() {
                    return created_at;
                }

                public void setCreated_at(String created_at) {
                    this.created_at = created_at;
                }

                public String getUpdated_at() {
                    return updated_at;
                }

                public void setUpdated_at(String updated_at) {
                    this.updated_at = updated_at;
                }

                public String getFrom_nickname() {
                    return from_nickname;
                }

                public void setFrom_nickname(String from_nickname) {
                    this.from_nickname = from_nickname;
                }

                public String getFrom_avatar() {
                    return from_avatar;
                }

                public void setFrom_avatar(String from_avatar) {
                    this.from_avatar = from_avatar;
                }

                public String getFrom_gender() {
                    return from_gender;
                }

                public void setFrom_gender(String from_gender) {
                    this.from_gender = from_gender;
                }

                public String getFrom_mobile() {
                    return from_mobile;
                }

                public void setFrom_mobile(String from_mobile) {
                    this.from_mobile = from_mobile;
                }
            }
        }
    }
}
