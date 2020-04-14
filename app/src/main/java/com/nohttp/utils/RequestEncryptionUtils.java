package com.nohttp.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.BeeFramework.model.Constants;
import com.update.activity.UpdateVerSion;
import com.user.UserAppConst;
import com.user.Utils.TokenUtils;

import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import cn.net.cyberway.utils.ChangeLanguageHelper;


/**
 * 创建时间 : 2017/7/10.
 * 编写人 :  ${yuansk}
 * 功能描述: 请求数据的加密
 * 版本:
 */

public class RequestEncryptionUtils {
    /**
     * 请求成功
     ***/
    public static final int responseSuccess = 200;
    /***重新**/
    public static final int responseRequest = 2000;

    /***请求ICE的post加密**/
    public static String postICEMD5(String urlString) {
        String appID = "";
        String ts = System.currentTimeMillis() / 1000 + "";
        String token = "";
        String sign = appID + ts + token + "false";
        String finalUrlString = urlString + "?sign=" + setMD5(sign) + "&ts=" + ts + "&appID=" + appID;
        return finalUrlString;
    }

    /***请求ICE的post加密  通过type支持多域名**/
    public static String postICEMD5(int type, String urlString) {
        String secertUrl = postICEMD5(urlString);
        return secertUrl;
    }

    /***请求ICE的get加密**/
    public static String getICEMD5(String urlString, Map<String, Object> paramsMap) {
        // 添加url参数
        urlString += getMapToString(paramsMap);
        return postICEMD5(urlString);
    }

    /***请求ICE的get加密 通过type支持多域名**/
    public static String getICEMD5(int type, String urlString, Map<String, Object> paramsMap) {
        String secertUrl = getICEMD5(urlString, paramsMap);
        return secertUrl;
    }

    /***请求Combile的get加密***/
    private static String getCombileMD5(Context context, String urlString, Map<String, Object> paramsMap) {
        String version = "1.0.0";
        SharedPreferences shared = context.getSharedPreferences("user_info", 0);
        String sign = null;
        String keyValue = shared.getString(UserAppConst.Colour_login_key, "33034808");
        String secretValue = shared.getString(UserAppConst.Colour_login_secret, "vEe41l1VTv401sGJ");
        long diff = shared.getLong(UserAppConst.Colour_Diff, 0);
        String ts = System.currentTimeMillis() / 1000 + diff + "";
        String valueStr;
        if (paramsMap == null) {
            sign = urlString + "?" + "key=" + keyValue + "&ts=" + ts + "&ve=" + version + "&secret=" + secretValue;
            valueStr = urlString + "?key=" + keyValue;
        } else {//有参数时
            sign = urlString + getMapToString(paramsMap) + "&key=" + keyValue + "&ts=" + ts + "&ve=" + version + "&secret=" + secretValue;
            valueStr = urlString + getMapToString(paramsMap) + "&key=" + keyValue;
        }
        valueStr = valueStr + "&ts=" + (System.currentTimeMillis() / 1000 + diff)
                + "&ve=" + version + "&sign=" + setMD5(sign);
        valueStr = valueStr.replaceAll(" ", "\\+");
        return valueStr;
    }

    /***请求Combile的get加密 通过type支持多域名***/
    public static String getCombileMD5(Context context, int type, String urlString, Map<String, Object> paramsMap) {
        String finalUrl = "";
        switch (type) {
            case 0:
                String serviceUrl = getCombileMD5(context, urlString, paramsMap);
                finalUrl = Constants.SERVER_ADDRESS + serviceUrl;//旧版combile
                break;
            case 1:
                finalUrl = Constants.HOMEAPP_ADDRESS + urlString;//4.0的
                break;
            case 2:
                finalUrl = Constants.BUSINESS_ADDRESS + urlString; //收银台
                break;
            case 3:
                finalUrl = Constants.USERINFO_ADDRESS + urlString;//用户信息微服务
                break;
            case 4:
                finalUrl = Constants.IMAPP_ADDRESS + urlString;//IM通讯相关
                break;
            case 5:
                finalUrl = Constants.DELIVERY_ADDRESS + urlString; //收货地址的
                break;
            case 6:
                finalUrl = Constants.PROPERTY_ADDRESS + urlString;  //房产车辆修改的
                break;
            case 7:
                finalUrl = Constants.VERIFY_ADDRESS + urlString;  //房产车辆修改的
                break;
            case 8:
                finalUrl = Constants.EPARKING_ADDRESS + urlString;  //e停的
                break;
            case 9:
                finalUrl = Constants.FEEDBACK_ADDRESS + urlString;  //意见反馈的修改
                break;
            case 10:
                finalUrl = Constants.BEAN_ADDRESS + urlString;  //彩豆积分系统
                break;
            case 11:
                finalUrl = Constants.DOOR_ADDRESS + urlString;  //蓝牙门禁
                break;
            case 12:
                finalUrl = Constants.AUTH_APP_ADDRESS + urlString;  //授权应用
                break;
            case 13:
                finalUrl = Constants.CARHUI_ADDRESS + urlString;  //彩惠人生
                break;
            case 14:
                finalUrl = Constants.VERSION_ADDRESS + urlString;  //版本升级
                break;
            case 15:
                finalUrl=Constants.ACCOUNT_ADDRESS+urlString;//新彩钱包
                break;
            case 16:
                finalUrl=Constants.NEIGHBOUR_ADDRESS+urlString;//比邻社区
                break;
            default:
                finalUrl = urlString;
        }
        return finalUrl;
    }

    /***请求Combile的post加密***/
    private static String postCombileMD5(Context context, String urlString) {
        String version = "1.0.0";
        SharedPreferences shared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        String keyValue = shared.getString(UserAppConst.Colour_login_key, "33034808");
        String secretValue = shared.getString(UserAppConst.Colour_login_secret, "vEe41l1VTv401sGJ");
        long diff = shared.getLong(UserAppConst.Colour_Diff, 0);
        String ts = System.currentTimeMillis() / 1000 + diff + "";
        String sign = urlString + "?" + "key=" + keyValue + "&ts=" + ts + "&ve=" + version + "&secret=" + secretValue;
        String valueStr;
        valueStr = urlString + "?" + "key=" + keyValue;

        valueStr = valueStr + "&ts=" + (System.currentTimeMillis() / 1000 + diff)
                + "&ve=" + version + "&sign=" + setMD5(sign);
        return valueStr;
    }

    /***请求Combile的post加密 通过type支持多域名***/
    public static String postCombileMD5(Context context, int type, String urlString) {
        String finalUrl = "";
        switch (type) {
            case 0:
                String serviceUrl = postCombileMD5(context, urlString);
                finalUrl = Constants.SERVER_ADDRESS + serviceUrl; //combile
                break;
            case 1:
                finalUrl = Constants.TOKEN_ADDRESS + urlString; //获取accesstoken
                break;
            case 2:
                finalUrl = Constants.NEWAPP_ADDRESS + urlString;//单设备
                break;
            case 3:
                finalUrl = Constants.HOMEAPP_ADDRESS + urlString; //4.0的接口
                break;
            case 4:
                finalUrl = Constants.LINLI_ADDRESS + urlString;//邻里新接口
                break;
            case 5:
                finalUrl = Constants.BUSINESS_ADDRESS + urlString;//新订单支付接口
                break;
            case 6:
                finalUrl = Constants.USERINFO_ADDRESS + urlString;//新的用户相关的
                break;
            case 7:
                finalUrl = Constants.IMAPP_ADDRESS + urlString;//IM通讯相关
                break;
            case 8:
                finalUrl = Constants.DELIVERY_ADDRESS + urlString;  //收货地址
                break;
            case 9:
                finalUrl = Constants.PROPERTY_ADDRESS + urlString; //房产和车辆
                break;
            case 10:
                finalUrl = Constants.VERIFY_ADDRESS + urlString;  //房产车辆修改的
                break;
            case 11:
                finalUrl = Constants.EPARKING_ADDRESS + urlString;  //e停的
                break;
            case 12:
                finalUrl = Constants.QRCODE_ADDRESS + urlString;  //新的扫码接口
                break;
            case 13:
                finalUrl = Constants.BEHAVIOR_ADDRESS + urlString;  //用户埋点的
                break;
            case 14:
                finalUrl = Constants.FEEDBACK_ADDRESS + urlString;  //意见反馈
                break;
            case 15:
                finalUrl = Constants.DOOR_ADDRESS + urlString;  //蓝牙门禁
                break;
            case 16:
                finalUrl= Constants.ACCOUNT_ADDRESS + urlString;  //新彩钱包
                break;
            case 17:
                finalUrl=Constants.NEIGHBOUR_ADDRESS+urlString;//比邻社区
                break;
            default:
                finalUrl = urlString;
                break;
        }
        return finalUrl;
    }

    public static String getMapToString(Map<String, Object> paramsMap) {
        if (null == paramsMap) {
            return "";
        } else {
            Iterator<String> it = paramsMap.keySet().iterator();
            StringBuffer sb = null;
            while (it.hasNext()) {
                String key = it.next();
                String value = String.valueOf(paramsMap.get(key));
                if (sb == null) {
                    sb = new StringBuffer();
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                sb.append(key);
                sb.append("=");
                sb.append(value);
            }
            if (null == sb) {
                return "";
            } else {
                return sb.toString();
            }
        }
    }


    /***md5加密***/
    public static String setMD5(String string) {
        MessageDigest md5;
        StringBuilder sb = new StringBuilder();
        try {
            md5 = MessageDigest.getInstance("MD5");
            md5.update(string.getBytes("UTF-8"));
            byte[] b = md5.digest();
            for (byte aB : b) {
                int temp = 0xFF & aB;
                String s = Integer.toHexString(temp);
                if (temp <= 0x0F) {
                    s = "0" + s;
                }
                sb.append(s);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /***4.0新接口的安全加密以后的请求参数Map**/
    public static Map<String, Object> getNewSaftyMap(Context context, Map<String, Object> paramsMap) {
        paramsMap.put("nonce_str", getRandomNonceStr());
        paramsMap.put("native_type", 1);
        paramsMap.put("lang", ChangeLanguageHelper.getLanguageType(context));
        if (null!=paramsMap&&!paramsMap.containsKey("version")){
            paramsMap.put("version", UpdateVerSion.handleVersionName(UpdateVerSion.getVersionName(context)));
        }
        paramsMap.put("ip", TokenUtils.getIPAddress(context));
        paramsMap.put("dev_uuid", TokenUtils.getUUID(context));
        paramsMap.put("OsVersionCode", TokenUtils.getOsVersionCode());
        String buff = "";
        try {
            List<Map.Entry<String, Object>> infoIds = new ArrayList<>(paramsMap.entrySet());
            // 对所有传入参数按照字段名的 ASCII 码从小到大排序（字典序）
            Collections.sort(infoIds, (o1, o2) -> (o1.getKey()).compareTo((o2.getKey())));
            // 构造URL 键值对的格式
            StringBuilder buf = new StringBuilder();
            for (Map.Entry<String, Object> item : infoIds) {
                if (null != item && !TextUtils.isEmpty(item.getValue().toString())) {
                    String key = item.getKey();
                    String val = item.getValue().toString();
                    val = URLEncoder.encode(val, "utf-8");
                    val = val.replace("*", "%2A");
                    buf.append(key + "=" + val);
                    buf.append("&");
                }
            }
            buff = setMD5(buf.toString() + "secret=" + Constants.secertKey).toUpperCase();
            paramsMap.put("signature", buff);
        } catch (Exception e) {
            return paramsMap;
        }
        return paramsMap;
    }


    public static Map<String, String> getStringMap(Map<String, Object> paramsMap) {
        Iterator<String> it = paramsMap.keySet().iterator();
        Map<String, String> stringMap = new HashMap<>();
        while (it.hasNext()) {
            String key = it.next();
            String value = String.valueOf(paramsMap.get(key));
            stringMap.put(key, value);
        }
        return stringMap;
    }

    public static Map<String, Object> getTrimMap(Map<String, Object> paramsMap) {
        Map<String, Object> trimParamMap = new HashMap<>();
        if (null != paramsMap) {
            Iterator<String> it = paramsMap.keySet().iterator();
            while (it.hasNext()) {
                String key = it.next();
                String value = String.valueOf(paramsMap.get(key)).trim();
                trimParamMap.put(key, value);
            }
        }
        return trimParamMap;
    }

    /***4.0生成8位随机数算法*/
    private static String getRandomNonceStr() {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXZY";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 16; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }
}
