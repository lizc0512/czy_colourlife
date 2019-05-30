package com.door.entity;

import com.nohttp.entity.BaseContentEntity;

import java.io.Serializable;
import java.util.List;

/**
 * @name ${yuansk}
 * @class name：com.door.entity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2019/4/1 10:17
 * @change
 * @chang time
 * @class describe
 */
public class DoorAuthorListEntity extends BaseContentEntity {
    /**
     * content : {"list":[{"id":1983411,"usertype":3,"autype":1,"fromid":10052165,"toid":91583881,"starttime":1536824406,"stoptime":1536910806,"times":1,"creationtime":1536824405,"modifiedtime":1536824405,"granttype":0,"memo":"测试","cid":11650153,"isdeleted":0,"applyid":0,"bid":10003685,"adminid":0,"cancelcid":0,"toname":"访客155****2105","fromname":"鄢格伦","name":"彩生活大厦","mobile":"15517552105","type":2},{"id":1947014,"usertype":5,"autype":1,"fromid":10052165,"toid":10145421,"starttime":1527326551,"stoptime":1558862551,"times":1,"creationtime":1527326553,"modifiedtime":1527326553,"granttype":0,"memo":"su -s /bin/bash -c \"php artisan queue:work\" application","cid":11650153,"isdeleted":0,"applyid":0,"bid":10003685,"adminid":0,"cancelcid":0,"toname":"啦啊","fromname":"鄢格伦","name":"彩生活大厦","mobile":"18138839680","type":2}]}
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
             * id : 1983411
             * usertype : 3
             * autype : 1
             * fromid : 10052165
             * toid : 91583881
             * starttime : 1536824406
             * stoptime : 1536910806
             * times : 1
             * creationtime : 1536824405
             * modifiedtime : 1536824405
             * granttype : 0
             * memo : 测试
             * cid : 11650153
             * isdeleted : 0
             * applyid : 0
             * bid : 10003685
             * adminid : 0
             * cancelcid : 0
             * toname : 访客155****2105
             * fromname : 鄢格伦
             * name : 彩生活大厦
             * mobile : 15517552105
             * type : 2
             */

            private String id;
            private String usertype;
            private int autype;
            private int fromid;
            private String toid;
            private int starttime;
            private int stoptime;
            private int times;
            private long creationtime;
            private long modifiedtime;
            private int granttype;
            private String memo;
            private int cid;
            private String isdeleted;
            private int applyid;
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

            public int getFromid() {
                return fromid;
            }

            public void setFromid(int fromid) {
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

            public int getGranttype() {
                return granttype;
            }

            public void setGranttype(int granttype) {
                this.granttype = granttype;
            }

            public String getMemo() {
                return memo;
            }

            public void setMemo(String memo) {
                this.memo = memo;
            }

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public String getIsdeleted() {
                return isdeleted;
            }

            public void setIsdeleted(String isdeleted) {
                this.isdeleted = isdeleted;
            }

            public int getApplyid() {
                return applyid;
            }

            public void setApplyid(int applyid) {
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
