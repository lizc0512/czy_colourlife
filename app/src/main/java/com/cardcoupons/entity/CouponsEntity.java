package com.cardcoupons.entity;

/**
 * Created by junier_li on 2016/1/5.
 */
public class CouponsEntity {

    /**
     *
     */
    private static final long serialVersionUID = -7457677835050567105L;

    String url;
    String title;
    String count;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }
}
