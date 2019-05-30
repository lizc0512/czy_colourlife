package com.invite.model;

import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.telephony.TelephonyManager;

/**
 * DeviceId
 * Created by chenql on 16/1/11.
 */
public class DeviceId extends android.content.ContextWrapper {

    public DeviceId(Context base) {
        super(base);

        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        this.deviceId = telephonyManager.getDeviceId();

        if (deviceId == null) {
            this.idType = "MAC";
            WifiManager wifiMan = (WifiManager) this
                    .getSystemService(Context.WIFI_SERVICE);
            WifiInfo wifiInf = wifiMan.getConnectionInfo();

            this.deviceId = wifiInf.getMacAddress().replace(":", "");
            this.deviceId = this.deviceId.replace(".", "");

        } else {
            this.idType = "IMEI";
        }
    }

    private String deviceId;
    private String idType;

    public String getDeviceId() {
        return this.deviceId;
    }

    public String getIdType() {
        return this.idType;
    }
}