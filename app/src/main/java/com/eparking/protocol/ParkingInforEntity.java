package com.eparking.protocol;

import com.nohttp.entity.BaseContentEntity;

/**
 * @name ${yuansk}
 * @class name：com.eparking.protocol
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/19 15:13
 * @change
 * @chang time
 * @class describe 找车位中 车场信息
 */
public class ParkingInforEntity extends BaseContentEntity {

    private String parkingAddrss;
    private int parkingSign;
    private double distance;
    private String parkingStatus;
    private double latitude;
    private double longitude;


    public String getParkingAddrss() {
        return parkingAddrss;
    }

    public void setParkingAddrss(String parkingAddrss) {
        this.parkingAddrss = parkingAddrss;
    }

    public int getParkingSign() {
        return parkingSign;
    }

    public void setParkingSign(int parkingSign) {
        this.parkingSign = parkingSign;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public String getParkingStatus() {
        return parkingStatus;
    }

    public void setParkingStatus(String parkingStatus) {
        this.parkingStatus = parkingStatus;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
