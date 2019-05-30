
package cn.net.cyberway.home.protocol;
import com.BeeFramework.model.HttpApi;

public class AppLifeRecommendGetApi extends HttpApi
{
  public AppLifeRecommendGetResponse response;
  public static String apiURI="app/home/life/list";
  public AppLifeRecommendGetApi()
  {
     response=new AppLifeRecommendGetResponse();
  }
}
