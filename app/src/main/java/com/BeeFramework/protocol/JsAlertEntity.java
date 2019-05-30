package com.BeeFramework.protocol;

import java.util.List;

/**
 * Created by Administrator on 2018/2/26.
 *
 * @Description
 */

public class JsAlertEntity {
    /**
     * title : 标题
     * content : 这是一个弹窗
     * buttons : [{"name":"取消","url":""},{"name":"确定","url":"www.baidu.com"}]
     */

    private String title;
    private String content;
    private List<ButtonsBean> buttons;

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

    public List<ButtonsBean> getButtons() {
        return buttons;
    }

    public void setButtons(List<ButtonsBean> buttons) {
        this.buttons = buttons;
    }

    public static class ButtonsBean {
        /**
         * name : 取消
         * url :
         */

        private String name;
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
