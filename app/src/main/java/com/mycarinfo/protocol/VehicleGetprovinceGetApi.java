
package com.mycarinfo.protocol;

import com.BeeFramework.model.HttpApi;

public class VehicleGetprovinceGetApi extends HttpApi {
    public VehicleGetprovinceGetRequest request;
    public VehicleGetprovinceGetResponse response;
    public static String apiURI = "/app/vehicle/getProvince";

    public VehicleGetprovinceGetApi() {
        request = new VehicleGetprovinceGetRequest();
        response = new VehicleGetprovinceGetResponse();
    }
}
