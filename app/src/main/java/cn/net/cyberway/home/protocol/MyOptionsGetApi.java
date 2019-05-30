
package cn.net.cyberway.home.protocol;
import com.BeeFramework.model.HttpApi;

public class MyOptionsGetApi extends HttpApi
{
  public MyOptionsGetRequest request;
  public MyOptionsGetResponse response;
  public static String apiURI="app/home/my/options";
  public MyOptionsGetApi()
  {
     request=new MyOptionsGetRequest();
     response=new MyOptionsGetResponse();
  }
}
