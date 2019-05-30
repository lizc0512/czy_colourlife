package com.door.entity;

import com.nohttp.entity.BaseContentEntity;
import com.setting.activity.SettingActivity;

import java.io.Serializable;
import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.door.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/4/1 10:06
 * @change
 * @chang time
 * @class describe
 */
public class DoorApplyListEntity extends BaseContentEntity {
    /**
     * content : {"list":[{"id":1947003,"usertype":1,"autype":2,"fromid":10052165,"toid":11650153,"starttime":0,"stoptime":0,"times":1,"creationtime":1527325737,"modifiedtime":1527325737,"granttype":1,"memo":"z","cid":10344662,"isdeleted":0,"applyid":0,"bid":10003685,"adminid":0,"cancelcid":0,"toname":"访客180****5107","fromname":"小芈侣","name":"彩生活大厦","mobile":"15320348027","type":2}]}
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
        private List<ListBean> list;

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean implements Serializable {
            /**
             * id : 1947003
             * usertype : 1
             * autype : 2
             * fromid : 10052165
             * toid : 11650153
             * starttime : 0
             * stoptime : 0
             * times : 1
             * creationtime : 1527325737
             * modifiedtime : 1527325737
             * granttype : 1
             * memo : z
             * cid : 10344662
             * isdeleted : 0
             * applyid : 0
             * bid : 10003685
             * adminid : 0
             * cancelcid : 0
             * toname : 访客180****5107
             * fromname : 小芈侣
             * name : 彩生活大厦
             * mobile : 15320348027
             * type : 2
             */

            private String id;
            private String usertype;
            private int autype;
            private String fromid;
            private int toid;
            private long starttime;
            private long stoptime;
            private long times;
            private long creationtime;
            private long modifiedtime;
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

            public int getAutype() {
                return autype;
            }

            public void setAutype(int autype) {
                this.autype = autype;
            }

            public String getFromid() {
                return fromid;
            }

            public void setFromid(String fromid) {
                this.fromid = fromid;
            }

            public int getToid() {
                return toid;
            }

            public void setToid(int toid) {
                this.toid = toid;
            }

            public long getStarttime() {
                return starttime;
            }

            public void setStarttime(long starttime) {
                this.starttime = starttime;
            }

            public long getStoptime() {
                return stoptime;
            }

            public void setStoptime(long stoptime) {
                this.stoptime = stoptime;
            }

            public long getTimes() {
                return times;
            }

            public void setTimes(long times) {
                this.times = times;
            }

            public long getCreationtime() {
                return creationtime;
            }

            public void setCreationtime(long creationtime) {
                this.creationtime = creationtime;
            }

            public long getModifiedtime() {
                return modifiedtime;
            }

            public void setModifiedtime(long modifiedtime) {
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
