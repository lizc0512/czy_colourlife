package cn.net.cyberway.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;

import com.amap.api.maps2d.model.LatLng;
import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.eparking.helper.ConstantKey;
import com.eparking.helper.MapNaviUtils;
import com.user.UserAppConst;

/**
 * 百度定位管理类
 * <p>
 * 【您当前所选择下载的开发包包含如下功能】
 * --------------------------------------------------------------------------------------------
 * 离线定位：在基础定位能力基础之上，提供离线定位能力，可在网络环境不佳时，进行精准定位；
 * 计算工具：包括距离计算、坐标转换、调起百度地图导航等功能；
 * 骑行导航（含基础地图）：包含骑行导航功能全部功能。
 * 支持骑行导航的基础地图：支持骑行导航功能的基础地图。
 * <p>
 * --------------------------------------------------------------------------------------------
 * 统一下载平台
 * 【产品说明】
 * 基础定位+离线定位+室内定位 = 全量定位 -> 原Android定位SDK，当前版本v7.6.0
 * 基础地图+检索功能+LBS云检索+计算工具+骑行导航 -> 原Android SDK（地图SDK），当前版本v5.3.0
 * 驾车导航（含TTS） ->  原导航SDK，当前版本v4.6.0.0 (如果和基础地图一起接入，需要删除libs目录下的galaxy_lite_lbs_v2.0.jar以及lbsCoreSDK.jar)
 * 全景图功能 -> 原全景图SDK，当前版本v2.6.2
 * AR地图功能 -> 当前版本v1.0.0
 */

public class CityManager implements BDLocationListener {

    private static CityManager self;
    private static SharedPreferences mShared;
    private static SharedPreferences.Editor mEditor;
    private static Context mContext;
    private LocationClient mLocationClient;
    private String mLocationCity = "";
    private static LocationListener mLocationListener;

    public static CityManager getInstance(Context context) {
        mContext = context;
        if (self == null) {
            return new CityManager();
        } else {
            return self;
        }

    }

    private CityManager() {
        mShared = mContext.getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
    }

    public void initLocation() {
        mLocationClient = new LocationClient(mContext.getApplicationContext());
        mLocationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Battery_Saving);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
        option.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，
        option.setScanSpan(10000);
        option.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
        option.setProdName(UserAppConst.BAIDU_MAP_PRODNAME);
        option.setOpenGps(false);
        option.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
        option.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死
        option.setEnableSimulateGps(false);//可选，默认false，设置是否需要过滤gps仿真结果，默认需要
        option.setIsNeedLocationDescribe(false);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
        option.setIsNeedLocationPoiList(false);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
        option.setNeedNewVersionRgc(true);//是否显示最新街道信息
        mLocationClient.setLocOption(option);
        mLocationClient.start();
    }

    public void registerLocationLister(LocationListener listener) {
        this.mLocationListener = listener;
    }


    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (bdLocation != null && !TextUtils.isEmpty(bdLocation.getCity())) {
            mLocationCity = bdLocation.getCity();
            if (mLocationClient.isStarted()) {
                mLocationClient.stop();
            }
            mShared.edit().putString(UserAppConst.Colour_CHOOSE_CITY_NAME, mLocationCity).commit();
            mEditor.putString(CityCustomConst.LOCATION_LATITUDE, String.valueOf(bdLocation.getLatitude()));
            mEditor.putString(CityCustomConst.LOCATION_LONGITUDE, String.valueOf(bdLocation.getLongitude()));
            mEditor.putString(CityCustomConst.LOCATION_PROVINCE, bdLocation.getProvince());
            mEditor.putString(CityCustomConst.LOCATION_CITY, bdLocation.getCity());
            mEditor.putString(CityCustomConst.LOCATION_DISTRICT, bdLocation.getDistrict());
            mEditor.putString(CityCustomConst.LOCATION_ADDRESS, bdLocation.getAddrStr());
            mEditor.putString(CityCustomConst.LOCATION_BUILDNAME, bdLocation.getBuildingName());
            mEditor.putString(CityCustomConst.LOCATION_FLOOR, bdLocation.getFloor());
            mEditor.putString(CityCustomConst.LOCATION_HOME, bdLocation.getDistrict() + bdLocation.getStreet());
            LatLng latLng = new LatLng(bdLocation.getLatitude(), bdLocation.getLongitude());
            latLng = MapNaviUtils.BD09ToGCJ02(latLng);
            mEditor.putString(ConstantKey.EPARKINGLON, String.valueOf(latLng.longitude));
            mEditor.putString(ConstantKey.EPARKINGLAT, String.valueOf(latLng.latitude));
            mEditor.putString(ConstantKey.EPARKINCITYID, bdLocation.getCityCode());
            mEditor.commit();
            //定位成功之后回调
            if (null != mLocationListener) {
                mLocationListener.onReceiveLocation();
            }
        } else {
            if (null != mLocationListener) {
                mLocationListener.onError();
            }
        }
    }

}
