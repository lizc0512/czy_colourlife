
package com.myproperty.protocol;

import org.json.JSONException;
import org.json.JSONObject;


public class ImageuploadPostResponse {
    public int code;

    public String message;

    public CTMYHOUSEPHOTOCONTENTDATA content;

    public void fromJson(JSONObject jsonObject) throws JSONException {
        if (null == jsonObject) {
            return;
        }
        this.code = jsonObject.optInt("code");
        this.message = jsonObject.optString("message");
        CTMYHOUSEPHOTOCONTENTDATA content = new CTMYHOUSEPHOTOCONTENTDATA();
        content.fromJson(jsonObject.optJSONObject("content"));
        this.content = content;
        return;
    }

}
