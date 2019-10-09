package com.user.Utils;

import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.user.UserAppConst;
import com.youmai.hxsdk.HuxinSdkManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.Reader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Enumeration;
import java.util.UUID;

import cn.jpush.android.api.JPushInterface;
import cn.net.cyberway.BuildConfig;
import cn.net.cyberway.utils.CityCustomConst;


public class TokenUtils {
    public static String getDeviceUUID(Context context) {
        String blackBox = null;
        blackBox = getUUID(context);
        return blackBox;
    }


    public static void clearUserCache(Context context) {
        SharedPreferences mshared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        SharedPreferences.Editor mEditor = mshared.edit();
        if (HuxinSdkManager.instance().isLogin()) {
            HuxinSdkManager.instance().loginOut();
        }
        mEditor.putBoolean(UserAppConst.IS_LOGIN, false);
        int userId = mshared.getInt(UserAppConst.Colour_User_id, 0);
        String communityid = mshared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
        mEditor.putString(UserAppConst.COLOR_HOME_LAYOUT, "");
        mEditor.putString(UserAppConst.COLOR_HOME_HEADER, "");
        mEditor.putString(UserAppConst.COLOR_HOME_RESOURCE, "");
        mEditor.putString(UserAppConst.COLOR_HOME_FUNCTION, "");
        mEditor.putString(UserAppConst.COLOR_HOME_APPLICATION, "");
        mEditor.putString(UserAppConst.COLOR_HOME_MANAGER, "");
        mEditor.putString(UserAppConst.COLOR_HOME_USEDOOR, "");
        mEditor.putString(UserAppConst.COLOR_HOME_NOTIFICATION, "");
        mEditor.putString(UserAppConst.COLOR_HOME_BANNER, "");
        mEditor.putString(UserAppConst.COLOR_HOME_ACTIVITY, "");
        mEditor.putString(UserAppConst.MYPAGELIST, "");
        mEditor.putString(UserAppConst.MYPAGESUBMENU, "");
        mEditor.putString(UserAppConst.ADDRESSLISTCACHE, "");
        mEditor.putString(UserAppConst.JOINCOMMUNITY, "");
        mEditor.putInt(UserAppConst.Colour_User_id, 0);
        mEditor.putString(UserAppConst.Colour_NAME, "");
        mEditor.putString(UserAppConst.Colour_NIACKNAME, "");
        mEditor.putString(UserAppConst.Colour_login_info, "");
        mEditor.putString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
        mEditor.putString(UserAppConst.Colour_login_community_name, "互联网社区");
        mEditor.putString(UserAppConst.SHOPMESSAGE, "");
        mEditor.putString(UserAppConst.SYSTEMMESSAGE, "");
        mEditor.putString(UserAppConst.OPEN_CODE, "");
        mEditor.putString(UserAppConst.SOURCE, "");
        mEditor.putString(UserAppConst.Colour_access_token, "");
        mEditor.putString(UserAppConst.COLOUR_LIFEUSERECORD, "");
        mEditor.putLong(UserAppConst.Colour_expires_in, 0);
        mEditor.putLong(UserAppConst.Colour_get_time, 0);
        mEditor.putString(UserAppConst.Colour_login_gesture_password, "");
        mEditor.putString(UserAppConst.NEWHOMEMANAGER, "");
        mEditor.putString(UserAppConst.HOMEDOOROFTEN, "");
        mEditor.putBoolean(UserAppConst.HAVADOORGRANTED, false);
        mEditor.putBoolean(UserAppConst.Colour_user_login, false);
        mEditor.putString(UserAppConst.ZXINGCODE + userId, "");
        mEditor.putString(UserAppConst.LIFECATEGORY + communityid + userId, "");
        mEditor.putString(UserAppConst.Colour_HOME_CACHE_NEW + userId + communityid, "");
        mEditor.putString(UserAppConst.COMMUNITYFRAGMENTLIST + userId + communityid, "");
        mEditor.putString(UserAppConst.COLOUR_BENEFIT_PROFILE, "");
        mEditor.commit();
    }

    public static String getDeviceInfor(Context context) {
        SharedPreferences mShared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        String latitude = mShared.getString(CityCustomConst.LOCATION_LATITUDE, "");
        String longitude = mShared.getString(CityCustomConst.LOCATION_LONGITUDE, "");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("networkType", getNetworkType(context));//网络类型
            jsonObject.put("imeiId", getImeiId(context));//和手机卡相关信息
            jsonObject.put("simNum", getSimNum(context));//和手机卡相关信息
            jsonObject.put("ipAddress", getIPAddress(context));//ip地址
            jsonObject.put("OsVersionName", getOsVersionName());//系统的名称
            jsonObject.put("OsVersionCode", getOsVersionCode());//系统的版本
            jsonObject.put("macAddress", getMac()); //macd地址
            jsonObject.put("batteryLevel", getBatteryInfor(context));//和手机卡相关信息
            jsonObject.put("ProvidersName", getProvidersName(context));//网络运营商
            jsonObject.put("MANUFACTURER", getDeviceName().toLowerCase());//手机制造商
            jsonObject.put("longitude", longitude);
            jsonObject.put("latitude", latitude);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject.toString();
    }

    private static String getBatteryInfor(Context context) {
        int level = 0;
        try {
            Intent batteryInfoIntent = context.registerReceiver(null, new IntentFilter(Intent.ACTION_BATTERY_CHANGED));
            level = batteryInfoIntent.getIntExtra("level", 0);//电量（0-100）
        } catch (Exception e) {

        }
        return level + "%";
    }


    public static String getLocalIpAddress() {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (SocketException ex) {

        }
        return "";
    }

    public static String getIPAddress(Context context) {
        NetworkInfo info = ((ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE)).getActiveNetworkInfo();
        if (info != null && info.isConnected()) {
            if (info.getType() == ConnectivityManager.TYPE_MOBILE) {//当前使用2G/3G/4G网络
                try {
                    for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements(); ) {
                        NetworkInterface intf = en.nextElement();
                        for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements(); ) {
                            InetAddress inetAddress = enumIpAddr.nextElement();
                            if (!inetAddress.isLoopbackAddress() && inetAddress instanceof Inet4Address) {
                                return inetAddress.getHostAddress();
                            }
                        }
                    }
                } catch (SocketException e) {
                    e.printStackTrace();
                }

            } else if (info.getType() == ConnectivityManager.TYPE_WIFI) {//当前使用无线网络
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                String ipAddress = intIP2StringIP(wifiInfo.getIpAddress());//得到IPV4地址
                return ipAddress;
            }
        } else {
            //当前无网络连接,请在设置中打开网络
            return getLocalIpAddress();
        }
        return "";
    }

    /**
     * 将得到的int类型的IP转换为String类型
     *
     * @param ip
     * @return
     */
    public static String intIP2StringIP(int ip) {
        return (ip & 0xFF) + "." +
                ((ip >> 8) & 0xFF) + "." +
                ((ip >> 16) & 0xFF) + "." +
                (ip >> 24 & 0xFF);
    }

    /***获取当前的网络类型***/
    public static String getNetworkType(Context context) {
        String strNetworkType = "";
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connManager.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            if (networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                strNetworkType = "WIFI";
            } else if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                String _strSubTypeName = networkInfo.getSubtypeName();


                // TD-SCDMA   networkType is 17
                int networkType = networkInfo.getSubtype();
                switch (networkType) {
                    case TelephonyManager.NETWORK_TYPE_GPRS:
                    case TelephonyManager.NETWORK_TYPE_EDGE:
                    case TelephonyManager.NETWORK_TYPE_CDMA:
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN: //api<8 : replace by 11
                        strNetworkType = "2G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_EVDO_A:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B: //api<9 : replace by 14
                    case TelephonyManager.NETWORK_TYPE_EHRPD:  //api<11 : replace by 12
                    case TelephonyManager.NETWORK_TYPE_HSPAP:  //api<13 : replace by 15
                        strNetworkType = "3G";
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:    //api<11 : replace by 13
                        strNetworkType = "4G";
                        break;
                    default:
                        if (_strSubTypeName.equalsIgnoreCase("TD-SCDMA") || _strSubTypeName.equalsIgnoreCase("WCDMA") || _strSubTypeName.equalsIgnoreCase("CDMA2000")) {
                            strNetworkType = "3G";
                        } else {
                            strNetworkType = _strSubTypeName;
                        }
                        break;
                }
            }
        }
        return strNetworkType;
    }


    /****获取AndroidId***/
    public static String getAndroirdId(Context context) {
        String androidId = android.provider.Settings.Secure.getString(
                context.getContentResolver(),
                android.provider.Settings.Secure.ANDROID_ID);
        if (TextUtils.isEmpty(androidId)) {
            androidId = "";
        }
        return androidId;
    }


    public static String getImeiId(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String imei = "";
        try {
            if (tm != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        imei = "";
                    } else {
                        imei = tm.getDeviceId();
                    }
                } else {
                    imei = tm.getDeviceId();
                }
            }
        } catch (Exception e) {

        }

        return imei;
    }


    public static String getSimNum(Context context) {
        TelephonyManager tm = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String sn = "";
        try {
            if (tm != null) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        sn = "";
                    } else {
                        sn = tm.getSimSerialNumber();
                    }
                } else {
                    sn = tm.getSimSerialNumber();
                }
            }
        } catch (Exception e) {

        }

        return sn;
    }

    /**
     * 获取手机的MAC地址
     *
     * @return
     */
    public static String getMac() {
        String str = "";
        String macSerial = "";
        try {
            Process pp = Runtime.getRuntime().exec(
                    "cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for (; null != str; ) {
                str = input.readLine();
                if (str != null) {
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (TextUtils.isEmpty(macSerial)) {
            try {
                macSerial = loadFileAsString("/sys/class/net/eth0/address")
                        .toLowerCase().substring(0, 17);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(macSerial)) {
            try {
                Enumeration<NetworkInterface> enumeration = NetworkInterface.getNetworkInterfaces();
                if (enumeration == null) {
                    return "";
                }
                while (enumeration.hasMoreElements()) {
                    NetworkInterface netInterface = enumeration.nextElement();
                    if (netInterface.getName().equals("wlan0")) {
                        return new String(netInterface.getHardwareAddress(), "UTF-8");
                    }
                }
            } catch (Exception e) {

            }
        }
        return macSerial;
    }


    public static String loadFileAsString(String fileName) throws Exception {
        FileReader reader = new FileReader(fileName);
        String text = loadReaderAsString(reader);
        reader.close();
        if (TextUtils.isEmpty(text)) {
            text = "";
        }
        return text;
    }

    public static String loadReaderAsString(Reader reader) throws Exception {
        StringBuilder builder = new StringBuilder();
        char[] buffer = new char[4096];
        int readLength = reader.read(buffer);
        while (readLength >= 0) {
            builder.append(buffer, 0, readLength);
            readLength = reader.read(buffer);
        }
        return builder.toString();
    }

    /**
     * 获取唯一的UUID
     */
    public static String getUUID(Context context) {
        SharedPreferences shared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        SharedPreferences.Editor editor = shared.edit();
        String m_szUniqueID = "";
        if (JPushInterface.isPushStopped(context)) {
            JPushInterface.resumePush(context);
        }
        if (!shared.contains("UniqueID")) {
            m_szUniqueID = JPushInterface.getRegistrationID(context);
            if (TextUtils.isEmpty(m_szUniqueID)) {
                String androidId = getAndroirdId(context);
                String tmDevice = getImeiId(context);
                String tmSerial = getSimNum(context);
                String macAddress = getMac();
                String m_szLongID = androidId + tmDevice + tmSerial + macAddress;
                MessageDigest m = null;
                try {
                    m = MessageDigest.getInstance("MD5");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                m.update(m_szLongID.getBytes(), 0, m_szLongID.length());
                byte p_md5Data[] = m.digest();

                for (int i = 0; i < p_md5Data.length; i++) {
                    int b = (0xFF & p_md5Data[i]);
                    if (b <= 0xF)
                        m_szUniqueID += "0";
                    m_szUniqueID += Integer.toHexString(b);
                }
                m_szUniqueID = m_szUniqueID.toLowerCase();
                if (m_szUniqueID.contains("-")) {
                    m_szUniqueID = m_szUniqueID.replace("-", "");
                }
            }
            if (TextUtils.isEmpty(m_szUniqueID)) {
                m_szUniqueID = UUID.randomUUID().toString().replace("-", "");
            }
            editor.putString("UniqueID", m_szUniqueID);
            editor.commit();
        } else {
            m_szUniqueID = shared.getString("UniqueID", UUID.randomUUID().toString().replace("-", ""));
        }
        return m_szUniqueID;
    }

    /***移动设备信息**/
    public static String getPhoneInfo(Context context) {
        String phoneModel = android.os.Build.MODEL;
        String makeVendor = getDeviceName().toLowerCase();
        String providername = getProvidersName(context);
        JSONObject json = new JSONObject();
        try {
            json.put("phoneModel", phoneModel);
            json.put("makeVendor", makeVendor);
            json.put("providername", providername);
        } catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return json.toString();
    }

    /**
     * 系统的版本名称
     **/
    public static String getOsVersionName() {
        return android.os.Build.ID;
    }

    /**
     * 系统的版本号
     **/
    public static String getOsVersionCode() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 移动设备的名称
     **/
    public static String getDeviceName() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 移动设备的品牌
     **/
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 移动设备的型号
     **/
    public static String getDeviceType() {
        return android.os.Build.MODEL;
    }

    /****移动运营商****/
    public static String getProvidersName(Context context) {
        TelephonyManager telephonyManager = (TelephonyManager) context
                .getSystemService(Context.TELEPHONY_SERVICE);
        String ProvidersName = null;
        // 返回唯一的用户ID;就是这张卡的编号神马的
        String IMSI = "";
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    IMSI = "";
                } else {
                    IMSI = telephonyManager.getSubscriberId();
                }
            } else {
                IMSI = telephonyManager.getSubscriberId();
            }
        } catch (Exception e) {

        }
        // IMSI号前面3位460是国家，紧接着后面2位00 02是中国移动，01是中国联通，03是中国电信。
        if (TextUtils.isEmpty(IMSI)) {
            ProvidersName = "";
        } else {
            if (IMSI.startsWith("46000") || IMSI.startsWith("46002")) {
                ProvidersName = "中国移动";
            } else if (IMSI.startsWith("46001")) {
                ProvidersName = "中国联通";
            } else if (IMSI.startsWith("46003")) {
                ProvidersName = "中国电信";
            }
        }
        return ProvidersName;
    }

    /**
     * 跳转到各个手机的应用授权页面
     **/
    public static void jumpPermissionPage(Context context) {
        String deviceBrand = getDeviceBrand();
        Intent intent = null;
        if (deviceBrand.equalsIgnoreCase("huawei")) {
            intent = new Intent();
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            ComponentName comp = new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity");
            intent.setComponent(comp);
        } else if (deviceBrand.equalsIgnoreCase("meizu")) {
            intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
            intent.addCategory(Intent.CATEGORY_DEFAULT);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
        } else if (deviceBrand.equalsIgnoreCase("xiaomi")) {
            intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
            ComponentName componentName = new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity");
            intent.setComponent(componentName);
            intent.putExtra("extra_pkgname", BuildConfig.APPLICATION_ID);
        } else if (deviceBrand.equalsIgnoreCase("oppo")) {
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("packageName", BuildConfig.APPLICATION_ID);
            ComponentName comp = new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity");
            intent.setComponent(comp);
        } else {
            intent = new Intent();
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            if (Build.VERSION.SDK_INT >= 9) {
                intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.fromParts("package", context.getPackageName(), null));
            } else if (Build.VERSION.SDK_INT <= 8) {
                intent.setAction(Intent.ACTION_VIEW);
                intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
                intent.putExtra("com.android.settings.ApplicationPkgName", context.getPackageName());
            }
        }
        context.startActivity(intent);
    }
}
