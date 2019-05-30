package com.eparking.helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.widget.Button;

import com.BeeFramework.AppConst;
import com.amap.api.maps2d.model.LatLng;
import com.eparking.protocol.CityInforEntity;
import com.eparking.view.keyboard.KeyboardInputController;
import com.eparking.view.keyboard.OnInputChangedListener;
import com.eparking.view.keyboard.PopupHelper;
import com.eparking.view.keyboard.PopupKeyboard;
import com.eparking.view.keyboard.view.InputView;
import com.nohttp.utils.GsonUtils;

import java.io.File;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.CityCustomConst;

import static com.eparking.helper.ConstantKey.EPARKINGCITYINFOR;

/**
 * @name ${yuansk}
 * @class name：com.eparking.helper
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/31 14:09
 * @change
 * @chang time
 * @class describe
 */
public class MapNaviUtils {

    public static final String PN_GAODE_MAP = "com.autonavi.minimap"; // 高德地图包名
    public static final String PN_BAIDU_MAP = "com.baidu.BaiduMap"; // 百度地图包名
    public static final String DOWNLOAD_GAODE_MAP = "http://www.autonavi.com/"; // 高德地图下载地址

    /**
     * 检查应用是否安装
     *
     * @return
     */
    public static boolean isGdMapInstalled() {
        return isInstallPackage(PN_GAODE_MAP);
    }

    public static boolean isBaiduMapInstalled() {
        return isInstallPackage(PN_BAIDU_MAP);
    }

    private static boolean isInstallPackage(String packageName) {
        return new File("/data/data/" + packageName).exists();
    }

    /**
     * 跳转到高德地图
     ***/
    public static void openGaodeMapToGuide(Context context) {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        Uri uri = Uri.parse(DOWNLOAD_GAODE_MAP);
        //将功能Scheme以URI的方式传入data
        intent.setData(uri);
        //启动该页面即可
        context.startActivity(intent);
    }

    /**
     * 百度坐标系 (BD-09) 与 火星坐标系 (GCJ-02)的转换
     * 即 百度 转 谷歌、高德
     *
     * @param latLng
     * @returns
     */
    public static LatLng BD09ToGCJ02(LatLng latLng) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double x = latLng.longitude - 0.0065;
        double y = latLng.latitude - 0.006;
        double z = Math.sqrt(x * x + y * y) - 0.00002 * Math.sin(y * x_pi);
        double theta = Math.atan2(y, x) - 0.000003 * Math.cos(x * x_pi);
        double gg_lat = z * Math.sin(theta);
        double gg_lng = z * Math.cos(theta);
        return new LatLng(gg_lat, gg_lng);
    }

    /**
     * 火星坐标系 (GCJ-02) 与百度坐标系 (BD-09) 的转换
     * 即谷歌、高德 转 百度
     *
     * @param latLng
     * @returns
     */
    public static LatLng GCJ02ToBD09(LatLng latLng) {
        double x_pi = 3.14159265358979324 * 3000.0 / 180.0;
        double z = Math.sqrt(latLng.longitude * latLng.longitude + latLng.latitude * latLng.latitude) + 0.00002 * Math.sin(latLng.latitude * x_pi);
        double theta = Math.atan2(latLng.latitude, latLng.longitude) + 0.000003 * Math.cos(latLng.longitude * x_pi);
        double bd_lat = z * Math.sin(theta) + 0.006;
        double bd_lng = z * Math.cos(theta) + 0.0065;
        return new LatLng(bd_lat, bd_lng);
    }

    /**
     * 打开高德地图导航功能
     *
     * @param context
     * @param slat    起点纬度
     * @param slon    起点经度
     * @param sname   起点名称 可不填（0,0，null）
     * @param dlat    终点纬度
     * @param dlon    终点经度
     * @param dname   终点名称 必填
     */
    public static void openGaoDeNavi(Context context, double slat, double slon, String sname, double dlat, double dlon, String dname) {
        String uriString = null;
        StringBuilder builder = new StringBuilder("amapuri://route/plan?sourceApplication=maxuslife");
        if (0 == slat) {
//            //如果不传起点（注释下面这段），默认就是现在我的位置（手机当前定位）
//            AMapLocation location = LocationService.getInstance().getAMapLocation();
//            if (LocationService.isSuccess(location)) {
//                builder.append("&sname=我的位置")
//                        .append("&slat=").append(location.getLatitude())
//                        .append("&slon=").append(location.getLongitude());
//            }
        } else {
            builder.append("&sname=").append(sname)
                    .append("&slat=").append(slat)
                    .append("&slon=").append(slon);
        }
        builder.append("&dlat=").append(dlat)
                .append("&dlon=").append(dlon)
                .append("&dname=").append(dname)
                .append("&dev=0")
                .append("&t=0");
        uriString = builder.toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage(PN_GAODE_MAP);
        intent.setData(Uri.parse(uriString));
        context.startActivity(intent);
    }

    /**
     * 打开百度地图导航功能(默认坐标点是高德地图，需要转换)
     *
     * @param context
     * @param slat    起点纬度
     * @param slon    起点经度
     * @param sname   起点名称 可不填（0,0，null）
     * @param dlat    终点纬度
     * @param dlon    终点经度
     * @param dname   终点名称 必填
     */
    public static void openBaiDuNavi(Context context, double slat, double slon, String sname, double dlat, double dlon, String dname) {
        String uriString = null;
        //终点坐标转换
        LatLng destination = new LatLng(dlat, dlon);
        LatLng destinationLatLng = GCJ02ToBD09(destination);
        dlat = destinationLatLng.latitude;
        dlon = destinationLatLng.longitude;

        StringBuilder builder = new StringBuilder("baidumap://map/direction?mode=driving&");
        if (slat != 0) {
            //起点坐标转换
            LatLng origin = new LatLng(slat, slon);
            LatLng originLatLng = GCJ02ToBD09(origin);
            slat = originLatLng.latitude;
            slon = originLatLng.longitude;

            builder.append("origin=latlng:")
                    .append(slat)
                    .append(",")
                    .append(slon)
                    .append("|name:")
                    .append(sname);
        }
        builder.append("&destination=latlng:")
                .append(dlat)
                .append(",")
                .append(dlon)
                .append("|name:")
                .append(dname);
        uriString = builder.toString();
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setPackage(PN_BAIDU_MAP);
        intent.setData(Uri.parse(uriString));
        context.startActivity(intent);
    }

    public static void setInputViewData(Context context, InputView inputView, boolean isShow) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(AppConst.USERINFO, 0);
        String cityInfor = sharedPreferences.getString(EPARKINGCITYINFOR, "");
        String fristLetter = "粤";
        String secondLetter = "B";
        try {
            CityInforEntity cityInforEntity = GsonUtils.gsonToBean(cityInfor, CityInforEntity.class);
            CityInforEntity.ContentBean contentBean = cityInforEntity.getContent();
            fristLetter = contentBean.getAbbreviation();
            secondLetter = contentBean.getLetter();
        } catch (Exception e) {

        }
        inputView.updateNumber(fristLetter + secondLetter);
        if (isShow) {
            inputView.performPosFieldView(2);
        }
    }

    public static void initInputView(final Activity activity, InputView input_view, final Button tv_change_cartype, final InputViewInterface  inputViewInterface) {
        // 创建弹出键盘
        input_view.setItemBorderSelectedColor(activity.getResources().getColor(R.color.color_64cdff));
        input_view.setItemBorderTextColor(activity.getResources().getColor(R.color.color_333b46));
        final PopupKeyboard mPopupKeyboard = new PopupKeyboard(activity);
        SharedPreferences shared = activity.getSharedPreferences(AppConst.USERINFO, 0);
        mPopupKeyboard.getKeyboardEngine().setLocalProvinceName(shared.getString(CityCustomConst.LOCATION_PROVINCE, ""));
        mPopupKeyboard.attach(input_view, activity);
        mPopupKeyboard.getController()
                .setDebugEnabled(true)
                .bindLockTypeProxy(new KeyboardInputController.ButtonProxyImpl(tv_change_cartype) {
                    @Override
                    public void onNumberTypeChanged(boolean isNewEnergyType) {
                        super.onNumberTypeChanged(isNewEnergyType);
                        if (isNewEnergyType) {
                            tv_change_cartype.setText(activity.getResources().getString(R.string.switch_normal_car));
                        } else {
                            tv_change_cartype.setText(activity.getResources().getString(R.string.switch_new_energy));
                        }
                        inputViewInterface.inputViewCallBack(isNewEnergyType);
                    }
                }).addOnInputChangedListener(new OnInputChangedListener() {
            @Override
            public void onChanged(String number, boolean isCompleted) {

            }

            @Override
            public void onCompleted(String number, boolean isAutoCompleted) {
                if (isAutoCompleted) {
                    PopupHelper.dismissFromActivity(activity);
                }
            }
        });
    }
}
