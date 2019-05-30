
package cn.net.cyberway.home.protocol;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MODULELISTCONTENT
{
  public int sort; //排序字段

  public String desc; //  默认文字展示

  public int resource_id; //  对应资源ID

  public String name; //  名称

  public String img; //  展示图片

  public String url; //  跳转地址

  public String superscript; //  角图片标地址

  public void fromJson(JSONObject jsonObject) throws JSONException
  {
    if( null == jsonObject ) {
      return ;
    }

    JSONArray subItemArray = new JSONArray();

    this.sort = jsonObject.optInt("sort");
    this.desc = jsonObject.optString("desc");
    this.resource_id = jsonObject.optInt("resource_id");
    this.name = jsonObject.optString("name");
    this.img = jsonObject.optString("img");
    this.url = jsonObject.optString("url");
    this.superscript = jsonObject.optString("superscript");

    return;
  }

  public JSONObject toJson() throws JSONException 
  {
    JSONObject localItemObject = new JSONObject();
    JSONArray itemJSONArray = new JSONArray();
    localItemObject.put("sort", sort);
    localItemObject.put("desc", desc);
    localItemObject.put("resource_id", resource_id);
    localItemObject.put("name", name);
    localItemObject.put("img", img);
    localItemObject.put("url", url);
    localItemObject.put("superscript", superscript);
    return localItemObject;
  }
}
