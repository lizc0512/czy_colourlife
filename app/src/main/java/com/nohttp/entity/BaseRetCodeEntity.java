package com.nohttp.entity;

import java.io.Serializable;

/**
 * 创建时间 : 2017/8/12.
 * 编写人 :  ${yuansk}
 * 功能描述:
 * 版本:
 */

public class BaseRetCodeEntity implements Serializable
{

    /**
     * retCode : 1
     * retMessage : 请求成功
     */

    private int retCode;

    public String getRetMsg() {
        return retMsg;
    }

    public void setRetMsg(String retMsg) {
        this.retMsg = retMsg;
    }

    private String retMsg;

    public void setRetCode(int retCode) {
        this.retCode = retCode;
    }



    public int getRetCode() {
        return retCode;
    }


}
