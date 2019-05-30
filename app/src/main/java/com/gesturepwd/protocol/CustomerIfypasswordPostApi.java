
package com.gesturepwd.protocol;
import com.BeeFramework.model.HttpApi;

public class CustomerIfypasswordPostApi extends HttpApi
{
  public CustomerIfypasswordPostRequest request;
  public CustomerIfypasswordPostResponse response;
  public static String apiURI="/1.0/customer/verifyPassword";
  public CustomerIfypasswordPostApi()
  {
     request=new CustomerIfypasswordPostRequest();
     response=new CustomerIfypasswordPostResponse();
  }
}
