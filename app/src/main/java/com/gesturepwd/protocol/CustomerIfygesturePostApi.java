
package com.gesturepwd.protocol;
import com.BeeFramework.model.HttpApi;

public class CustomerIfygesturePostApi extends HttpApi
{
  public CustomerIfygesturePostRequest request;
  public CustomerIfygesturePostResponse response;
  public static String apiURI="/1.0/customer/verifyGesture";
  public CustomerIfygesturePostApi()
  {
     request=new CustomerIfygesturePostRequest();
     response=new CustomerIfygesturePostResponse();
  }
}
