package com.door.entity;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

public class DoorApplyRecordEntity extends BaseContentEntity {
    /**
     * content : {"list":[{"id":75263,"fromid":11274044,"toid":11273575,"creationtime":1570782789,"modifiedtime":1570782789,"cid":0,"fromname":"访客180****7255","mobile":"18076627255","colorid":1002851464,"type":1,"autype":0,"starttime":0,"stoptime":0,"times":0,"memo":"222","isdeleted":0,"granttype":0,"bid":0,"usertype":-1,"applyid":0,"toname":"","name":""}]}
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

        public static class ListBean {
            /**
             * id : 75263
             * fromid : 11274044
             * toid : 11273575
             * creationtime : 1570782789
             * modifiedtime : 1570782789
             * cid : 0
             * fromname : 访客180****7255
             * mobile : 18076627255
             * colorid : 1002851464
             * type : 1
             * autype : 0
             * starttime : 0
             * stoptime : 0
             * times : 0
             * memo : 222
             * isdeleted : 0
             * granttype : 0
             * bid : 0
             * usertype : -1
             * applyid : 0
             * toname :
             * name :
             */

            private String id;
            private String fromid;
            private String toid;
            private int creationtime;
            private int modifiedtime;
            private int cid;
            private String fromname;
            private String mobile;
            private String colorid;
            private String type;
            private String autype;
            private int starttime;
            private int stoptime;
            private int times;
            private String memo;
            private String isdeleted;
            private String granttype;
            private String bid;
            private String usertype;
            private String applyid;
            private String toname;
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

            public int getCid() {
                return cid;
            }

            public void setCid(int cid) {
                this.cid = cid;
            }

            public String getFromname() {
                return fromname;
            }

            public void setFromname(String fromname) {
                this.fromname = fromname;
            }

            public String getMobile() {
                return mobile;
            }

            public void setMobile(String mobile) {
                this.mobile = mobile;
            }

            public String getColorid() {
                return colorid;
            }

            public void setColorid(String colorid) {
                this.colorid = colorid;
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

            public String getGranttype() {
                return granttype;
            }

            public void setGranttype(String granttype) {
                this.granttype = granttype;
            }

            public String getBid() {
                return bid;
            }

            public void setBid(String bid) {
                this.bid = bid;
            }

            public String getUsertype() {
                return usertype;
            }

            public void setUsertype(String usertype) {
                this.usertype = usertype;
            }

            public String getApplyid() {
                return applyid;
            }

            public void setApplyid(String applyid) {
                this.applyid = applyid;
            }

            public String getToname() {
                return toname;
            }

            public void setToname(String toname) {
                this.toname = toname;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }
        }
    }
}
