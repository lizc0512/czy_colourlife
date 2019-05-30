
package com.gesturepwd.protocol;
import com.BeeFramework.model.HttpApi;

public class CustomerOpengestureGetApi extends HttpApi
{
  public CustomerOpengestureGetRequest request;
  public CustomerOpengestureGetResponse response;
  public static String apiURI="/1.0/customer/openGesture";
  public CustomerOpengestureGetApi()
  {
     request=new CustomerOpengestureGetRequest();
     response=new CustomerOpengestureGetResponse();
  }
}
