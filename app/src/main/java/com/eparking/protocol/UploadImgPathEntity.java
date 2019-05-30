package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/14 15:30
 * @change
 * @chang time
 * @class describe
 */
public class UploadImgPathEntity extends BaseContentEntity {
    /**
     * content : http://eparking.b0.upaiyun.com/driver/licence/2018/11/02/059c900d4d2e292241a3b9c0e4ab17f4.jpeg
     */

    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
