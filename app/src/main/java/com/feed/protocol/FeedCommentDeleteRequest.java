
package com.feed.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class FeedCommentDeleteRequest
{
  public String comment_id; // 评论id
  public String from_uid;//用户Id 未登录传0
  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();
    this.from_uid = jsonObject.getString("from_uid");
    this.comment_id = jsonObject.optString("comment_id");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("comment_id", comment_id);
    localItemObject.put("from_uid",from_uid);
    return localItemObject;
  }
}
