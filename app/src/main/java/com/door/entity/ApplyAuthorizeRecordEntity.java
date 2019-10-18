package com.door.entity;

import com.nohttp.entity.BaseContentEntity;

import java.io.Serializable;
import java.util.List;

public class ApplyAuthorizeRecordEntity extends BaseContentEntity {
    /**
     * content : {"apply_list":[{"id":75264,"fromid":10075736,"toid":11273575,"creationtime":1571020443,"modifiedtime":1571020443,"cid":0,"toname":"张锡旺","type":1,"autype":0,"starttime":0,"stoptime":0,"times":0,"memo":"测试啊","isdeleted":0,"fromname":"","granttype":0}],"authorization_list":[{"id":1976587,"usertype":3,"autype":1,"fromid":10649214,"toid":11273575,"starttime":1570786712,"stoptime":1570873112,"times":1,"creationtime":1570786713,"modifiedtime":1570786713,"granttype":0,"memo":"Again","cid":10075736,"isdeleted":0,"applyid":0,"bid":10125444,"adminid":0,"cancelcid":0,"toname":"张锡旺","fromname":"","name":"万隆苑","mobile":"18617194368","type":2}]}
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
        private List<ApplyListBean> apply_list;
        private List<AuthorizationListBean> authorization_list;

        public List<ApplyListBean> getApply_list() {
            return apply_list;
        }

        public void setApply_list(List<ApplyListBean> apply_list) {
            this.apply_list = apply_list;
        }

        public List<AuthorizationListBean> getAuthorization_list() {
            return authorization_list;
        }

        public void setAuthorization_list(List<AuthorizationListBean> authorization_list) {
            this.authorization_list = authorization_list;
        }

        public static class ApplyListBean implements Serializable {
            /**
             * id : 75264
             * fromid : 10075736
             * toid : 11273575
             * creationtime : 1571020443
             * modifiedtime : 1571020443
             * cid : 0
             * toname : 张锡旺
             * type : 1
             * autype : 0
             * starttime : 0
             * stoptime : 0
             * times : 0
             * memo : 测试啊
             * isdeleted : 0
             * fromname :
             * granttype : 0
             */

            private String id;
            private String fromid;
            private String toid;
            private int creationtime;
            private int modifiedtime;
            private String cid;
            private String toname;
            private String type;
            private String autype;
            private int starttime;
            private int stoptime;
            private int times;
            private String memo;
            private String isdeleted;
            private String fromname;
            private String granttype;

            public String getBid() {
                return bid;
            }

            public void setBid(String bid) {
                this.bid = bid;
            }

            private String bid;

            public String getUsertype() {
                return usertype;
            }

            public void setUsertype(String usertype) {
                this.usertype = usertype;
            }

            private String usertype;

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            private String name;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getFromid() {
                return fromid;
            }

            public void setFromid(String fromid) {
                this.fromid = fromid;
            }

            public String getToid() {
                return toid;
            }

            public void setToid(String toid) {
                this.toid = toid;
            }

            public int getCreationtime() {
                return creationtime;
            }

            public void setCreationtime(int creationtime) {
                this.creationtime = creationtime;
            }

            public int getModifiedtime() {
                return modifiedtime;
            }

            public void setModifiedtime(int modifiedtime) {
                this.modifiedtime = modifiedtime;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getToname() {
                return toname;
            }

            public void setToname(String toname) {
                this.toname = toname;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getAutype() {
                return autype;
            }

            public void setAutype(String autype) {
                this.autype = autype;
            }

            public int getStarttime() {
                return starttime;
            }

            public void setStarttime(int starttime) {
                this.starttime = starttime;
            }

            public int getStoptime() {
                return stoptime;
            }

            public void setStoptime(int stoptime) {
                this.stoptime = stoptime;
            }

            public int getTimes() {
                return times;
            }

            public void setTimes(int times) {
                this.times = times;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public String getIsdeleted() {
                return isdeleted;
            }

            public void setIsdeleted(String isdeleted) {
                this.isdeleted = isdeleted;
            }

            public String getFromname() {
                return fromname;
            }

            public void setFromname(String fromname) {
                this.fromname = fromname;
            }

            public String getGranttype() {
                return granttype;
            }

            public void setGranttype(String granttype) {
                this.granttype = granttype;
            }
        }

        public static class AuthorizationListBean  implements Serializable {
            /**
             * id : 1976587
             * usertype : 3
             * autype : 1
             * fromid : 10649214
             * toid : 11273575
             * starttime : 1570786712
             * stoptime : 1570873112
             * times : 1
             * creationtime : 1570786713
             * modifiedtime : 1570786713
             * granttype : 0
             * memo : Again
             * cid : 10075736
             * isdeleted : 0
             * applyid : 0
             * bid : 10125444
             * adminid : 0
             * cancelcid : 0
             * toname : 张锡旺
             * fromname :
             * name : 万隆苑
             * mobile : 18617194368
             * type : 2
             */

            private String id;
            private String usertype;
            private String autype;
            private String fromid;
            private String toid;
            private int starttime;
            private int stoptime;
            private int times;
            private int creationtime;
            private int modifiedtime;
            private String granttype;
            private String memo;
            private String cid;
            private String isdeleted;
            private String applyid;
            private String bid;
            private String adminid;
            private String cancelcid;
            private String toname;
            private String fromname;
            private String name;
            private String mobile;
            private String type;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getUsertype() {
                return usertype;
            }

            public void setUsertype(String usertype) {
                this.usertype = usertype;
            }

            public String getAutype() {
                return autype;
            }

            public void setAutype(String autype) {
                this.autype = autype;
            }

            public String getFromid() {
                return fromid;
            }

            public void setFromid(String fromid) {
                this.fromid = fromid;
            }

            public String getToid() {
                return toid;
            }

            public void setToid(String toid) {
                this.toid = toid;
            }

            public int getStarttime() {
                return starttime;
            }

            public void setStarttime(int starttime) {
                this.starttime = starttime;
            }

            public int getStoptime() {
                return stoptime;
            }

            public void setStoptime(int stoptime) {
                this.stoptime = stoptime;
            }

            public int getTimes() {
                return times;
            }

            public void setTimes(int times) {
                this.times = times;
            }

            public int getCreationtime() {
                return creationtime;
            }

            public void setCreationtime(int creationtime) {
                this.creationtime = creationtime;
            }

            public int getModifiedtime() {
                return modifiedtime;
            }

            public void setModifiedtime(int modifiedtime) {
                this.modifiedtime = modifiedtime;
            }

            public String getGranttype() {
                return granttype;
            }

            public void setGranttype(String granttype) {
                this.granttype = granttype;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public String getCid() {
                return cid;
            }

            public void setCid(String cid) {
                this.cid = cid;
            }

            public String getIsdeleted() {
                return isdeleted;
            }

            public void setIsdeleted(String isdeleted) {
                this.isdeleted = isdeleted;
            }

            public String getApplyid() {
                return applyid;
            }

            public void setApplyid(String applyid) {
                this.applyid = applyid;
            }

            public String getBid() {
                return bid;
            }

            public void setBid(String bid) {
                this.bid = bid;
            }

            public String getAdminid() {
                return adminid;
            }

            public void setAdminid(String adminid) {
                this.adminid = adminid;
            }

            public String getCancelcid() {
                return cancelcid;
            }

            public void setCancelcid(String cancelcid) {
                this.cancelcid = cancelcid;
            }

            public String getToname() {
                return toname;
            }

            public void setToname(String toname) {
                this.toname = toname;
            }

            public String getFromname() {
                return fromname;
            }

            public void setFromname(String fromname) {
                this.fromname = fromname;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
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
