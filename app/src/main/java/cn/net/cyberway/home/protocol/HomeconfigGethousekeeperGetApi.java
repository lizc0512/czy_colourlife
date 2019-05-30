
package cn.net.cyberway.home.protocol;

import com.BeeFramework.model.HttpApi;

public class HomeconfigGethousekeeperGetApi extends HttpApi {
    public HomeconfigGethousekeeperGetRequest request;
    public HomeconfigGethousekeeperGetResponse response;
    public static String apiURI = "/1.0/homeConfig/getHousekeeper";

    public HomeconfigGethousekeeperGetApi() {
        request = new HomeconfigGethousekeeperGetRequest();
        response = new HomeconfigGethousekeeperGetResponse();
    }
}
