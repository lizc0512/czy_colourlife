package cn.net.cyberway.protocol;

import com.nohttp.entity.BaseRetCodeEntity;

/**
 * 创建时间 : 2017/8/14.
 * 编写人 :  ${yuansk}
 * 功能描述:
 * 版本:
 */

public class ShareEntity extends BaseRetCodeEntity
{
    public String getShareText() {
        return shareText;
    }

    public void setShareText(String shareText) {
        this.shareText = shareText;
    }

    public int getShareDrawableId() {
        return shareDrawableId;
    }

    public void setShareDrawableId(int shareDrawableId) {
        this.shareDrawableId = shareDrawableId;
    }

    private  String  shareText;
    private  int  shareDrawableId;
}
