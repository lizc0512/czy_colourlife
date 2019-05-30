package com.customerInfo.protocol;

import com.nohttp.entity.BaseContentEntity;

import java.util.List;

import cn.csh.colourful.life.view.pickview.model.IPickerViewData;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/3 16:56
 * @change
 * @chang time
 * @class describe   小区  楼栋 单元 房间号
 */

public class ComBuildUnitRoomEntity extends BaseContentEntity {

    /**
     * content : {"paging":{"page_size":2,"total_page":3,"current_page":1,"total_record":6},"data":[{"uuid":"0f18e998-4ce4-43b3-a7c7-e32b63e670ef","name":"RMS测试添加楼栋1"},{"uuid":"1c8d3478-4999-45e9-9ec9-bdf2f7aac839","name":"RMS测试添加楼栋-2"}]}
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
         * paging : {"page_size":2,"total_page":3,"current_page":1,"total_record":6}
         * data : [{"uuid":"0f18e998-4ce4-43b3-a7c7-e32b63e670ef","name":"RMS测试添加楼栋1"},{"uuid":"1c8d3478-4999-45e9-9ec9-bdf2f7aac839","name":"RMS测试添加楼栋-2"}]
         */

        private PagingBean paging;
        private List<DataBean> data;

        public PagingBean getPaging() {
            return paging;
        }

        public void setPaging(PagingBean paging) {
            this.paging = paging;
        }

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        public static class PagingBean {
            /**
             * page_size : 2
             * total_page : 3
             * current_page : 1
             * total_record : 6
             */

            private int page_size;
            private int total_page;
            private int current_page;
            private int total_record;

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

            public int getCurrent_page() {
                return current_page;
            }

            public void setCurrent_page(int current_page) {
                this.current_page = current_page;
            }

            public int getTotal_record() {
                return total_record;
            }

            public void setTotal_record(int total_record) {
                this.total_record = total_record;
            }
        }

        public static class DataBean implements IPickerViewData {
            /**
             * uuid : 0f18e998-4ce4-43b3-a7c7-e32b63e670ef
             * name : RMS测试添加楼栋1
             */

            private String uuid;
            private String name;

            public String getUuid() {
                return uuid;
            }

            public void setUuid(String uuid) {
                this.uuid = uuid;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            @Override
            public String getPickerViewText() {
                return name;
            }
        }
    }
}
