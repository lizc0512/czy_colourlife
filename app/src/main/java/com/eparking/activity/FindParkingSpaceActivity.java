package com.eparking.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.UiSettings;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.CameraPosition;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.Marker;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;
import com.amap.api.maps2d.overlay.DrivingRouteOverlay;
import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.core.PoiItem;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeResult;
import com.amap.api.services.poisearch.PoiResult;
import com.amap.api.services.poisearch.PoiSearch;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkRouteResult;
import com.eparking.adapter.ParkingInforAdapter;
import com.eparking.helper.MapNaviUtils;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingApplyModel;
import com.eparking.protocol.ParkingAddressEntity;
import com.eparking.protocol.ParkingInforEntity;
import com.eparking.view.NeverCarshXRecyclerView;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/11 17:00
 * @change
 * @chang time
 * @class describe  找车位  涉及热点的标注 以及查找
 */
public class FindParkingSpaceActivity extends BaseActivity implements View.OnClickListener, LocationSource, AMapLocationListener, AMap.OnCameraChangeListener,
        AMap.OnMarkerClickListener, GeocodeSearch.OnGeocodeSearchListener, PoiSearch.OnPoiSearchListener, RouteSearch.OnRouteSearchListener, NewHttpResponse {

    public static final String FROMOTHER = "fromother";//来自于停车记录的
    private EditText ed_search_content;      //标题
    private ImageView iv_back;//返回
    private MapView mapView;
    private AMap aMap;
    private GeocodeSearch geocoderSearch;
    private TextView tv_near_name;
    private TextView tv_distance;
    private TextView tv_eparking_number;
    private TextView tv_more_parking;
    private ImageView iv_clicklocation;
    private RelativeLayout bottom_layout;
    private Button btn_setout;
    private String cityCode;

    //实现AMapNaviView生命周期
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_parkingspace);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        ed_search_content = findViewById(R.id.ed_search_content);
        mapView = (MapView) findViewById(R.id.navi_view);
        tv_near_name = findViewById(R.id.tv_near_name);
        tv_distance = findViewById(R.id.tv_distance);
        iv_clicklocation = findViewById(R.id.iv_clicklocation);
        tv_eparking_number = findViewById(R.id.tv_eparking_number);
        tv_more_parking = findViewById(R.id.tv_more_parking);
        bottom_layout = findViewById(R.id.bottom_layout);
        btn_setout = findViewById(R.id.btn_setout);
        iv_back.setOnClickListener(this);
        ed_search_content.setOnClickListener(this);
        tv_more_parking.setOnClickListener(this);
        btn_setout.setOnClickListener(this);
        iv_clicklocation.setOnClickListener(this);
        mapView.onCreate(savedInstanceState);
        aMap = mapView.getMap();
        if (null != aMap) {
            setUpMap();
        } else {
            ToastUtil.toastShow(FindParkingSpaceActivity.this, "初始化失败");
        }
        addMostMarker();
        ParkingActivityUtils.getInstance().addActivity(this);
        Intent intent = getIntent();
        boolean fromOther = intent.getBooleanExtra(FROMOTHER, false);
        if (fromOther) {
            latLonPoint = new LatLonPoint(intent.getDoubleExtra(SearchParkingActivity.CHOICELAT, 0),
                    intent.getDoubleExtra(SearchParkingActivity.CHOICELON, 0));
            choiceAddress = intent.getStringExtra(SearchParkingActivity.CHOICEADDRESS);
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    drawLine();
                }
            }, 2000);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        activate(mListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        deactivate();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }

    private void addMostMarker() {
        // 定义 Marker 点击事件监听
        AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {
            // marker 对象被点击时回调的接口
            // 返回 true 则表示接口已响应事件，否则返回false
            @Override
            public boolean onMarkerClick(Marker marker) {
                LatLng latLng = marker.getPosition();
                latLonPoint = new LatLonPoint(latLng.latitude, latLng.longitude);
                choiceAddress = marker.getTitle();
                drawLine();
                return false;
            }
        };
        aMap.setOnMarkerClickListener(markerClickListener);
        // 定义 Marker拖拽的监听
        AMap.OnMarkerDragListener markerDragListener = new AMap.OnMarkerDragListener() {

            // 当marker开始被拖动时回调此方法, 这个marker的位置可以通过getPosition()方法返回。
            // 这个位置可能与拖动的之前的marker位置不一样。
            // marker 被拖动的marker对象。
            @Override
            public void onMarkerDragStart(Marker arg0) {
                // TODO Auto-generated method stub

            }

            // 在marker拖动完成后回调此方法, 这个marker的位置可以通过getPosition()方法返回。
            // 这个位置可能与拖动的之前的marker位置不一样。
            // marker 被拖动的marker对象。
            @Override
            public void onMarkerDragEnd(Marker arg0) {
                // TODO Auto-generated method stub

            }

            // 在marker拖动过程中回调此方法, 这个marker的位置可以通过getPosition()方法返回。
            // 这个位置可能与拖动的之前的marker位置不一样。
            // marker 被拖动的marker对象。
            @Override
            public void onMarkerDrag(Marker arg0) {
                // TODO Auto-generated method stub

            }
        };
        aMap.setOnMarkerDragListener(markerDragListener);

        AMap.OnInfoWindowClickListener listener = new AMap.OnInfoWindowClickListener() {

            @Override
            public void onInfoWindowClick(Marker arg0) {

                ToastUtil.toastShow(FindParkingSpaceActivity.this, arg0.getTitle());
                ;
            }
        };
//绑定信息窗点击事件
        aMap.setOnInfoWindowClickListener(listener);

    }

    /**
     * 设置amap属性
     */
    private void setUpMap() {
        aMap.moveCamera(CameraUpdateFactory.zoomTo(16f));
        aMap.setLocationSource(this);// 设置定位监听
        UiSettings uiSettings = aMap.getUiSettings();
        uiSettings.setZoomPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
        uiSettings.setScaleControlsEnabled(true);
        uiSettings.setZoomControlsEnabled(true);
        uiSettings.setZoomGesturesEnabled(true);
        uiSettings.setAllGesturesEnabled(true);
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        myLocationStyle.strokeColor(Color.argb(0, 0, 0, 0));// 设置圆形的边框颜色
        myLocationStyle.radiusFillColor(Color.argb(0, 0, 0, 0));// 设置圆形的填充颜色
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.fromResource(R.drawable.eparking_img_location));
        myLocationStyle.interval(10000);
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);//定位一次，且将视角移动到地图中心点。
        aMap.setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap.setOnMarkerClickListener(this);// 设置点击marker事件监听器
        geocoderSearch = new GeocodeSearch(FindParkingSpaceActivity.this);
        geocoderSearch.setOnGeocodeSearchListener(this);
    }

    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private OnLocationChangedListener mListener;
    private AMapLocation amapLocation;
    private LatLonPoint latLonPoint;

    /**
     * 激活定位
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(FindParkingSpaceActivity.this);
            mLocationOption = new AMapLocationClientOption();
            // 设置定位监听
            mlocationClient.setLocationListener(this);
            // 设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            // 设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }


    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        this.amapLocation = aMapLocation;
        LatLng curLatlng = new LatLng(amapLocation.getLatitude(), amapLocation.getLongitude());
        MarkerOptions markerOption = new MarkerOptions();
        markerOption.position(curLatlng);
        String title = amapLocation.getCity() + amapLocation.getAoiName();
        markerOption.title(title);
        markerOption.draggable(true);
        aMap.setOnCameraChangeListener(this);// 对amap添加移动地图事件监听器
        if (mListener != null) {
            mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
        }
        cityCode = amapLocation.getCityCode();
        if (!isExcute) {
            searchPoi("", 1000);
            isExcute = true;
            ParkingApplyModel parkingApplyModel = new ParkingApplyModel(FindParkingSpaceActivity.this);
            parkingApplyModel.nearStationList(0, amapLocation.getLatitude() + "", amapLocation.getLongitude() + "", 0.15, true, FindParkingSpaceActivity.this);
        }

    }

    private boolean isExcute;


    @Override
    public void deactivate() {
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }

    @Override
    public void onPoiSearched(PoiResult poiResult, int i) {
        ArrayList<PoiItem> poiItems = poiResult.getPois();
        int size = poiItems == null ? 0 : poiItems.size();
        if (size > 0) {
            PoiItem poiItem = null;
            poiItem = poiItems.get(0);
            setNearlyInfor(poiItem);
            for (PoiItem everyPoiItem : poiItems) {
                ParkingInforEntity parkingInforEntity = new ParkingInforEntity();
                parkingInforEntity.setDistance(everyPoiItem.getDistance());
                LatLonPoint everLatLonPoint = everyPoiItem.getLatLonPoint();
                parkingInforEntity.setLatitude(everLatLonPoint.getLatitude());
                parkingInforEntity.setLongitude(everLatLonPoint.getLongitude());
                parkingInforEntity.setParkingAddrss(everyPoiItem.getTitle());
                parkingInforEntity.setParkingStatus(everyPoiItem.getBusinessArea());
                parkingInforEntityList.add(parkingInforEntity);
            }
        } else {
            ToastUtil.toastShow(FindParkingSpaceActivity.this, "暂无更多车位信息");
        }
        if (null != parkingInforAdapter) {
            parkingInforAdapter.notifyDataSetChanged();
        }
        if (page > 1) {
            rv_eparking.loadMoreComplete();
        }
    }

    @Override
    public void onPoiItemSearched(PoiItem poiItem, int i) {

    }

    private Marker nearMarker;

    private void setNearlyInfor(PoiItem poiItem) {
        if (null == nearMarker) {
            tv_distance.setText("距我" + poiItem.getDistance() + "m");
            latLonPoint = poiItem.getLatLonPoint();
            tv_near_name.setText(poiItem.getTitle());
            tv_eparking_number.setText(poiItem.getBusinessArea());
            nearMarker = aMap.addMarker(new MarkerOptions()
                    .position(new LatLng(latLonPoint.getLatitude(), latLonPoint.getLongitude()))
                    .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                            .decodeResource(getResources(), R.drawable.eparking_img_nearly)))
                    .title(poiItem.getTitle())
                    .draggable(true));
            nearMarker.setObject("mostNear");
        }
    }

    /**
     * 搜索附近
     */
    private void searchPoi(String areaAddress, int distance) {
        LatLonPoint searchPoint = new LatLonPoint(amapLocation.getLatitude(), amapLocation.getLongitude());
        latLonPoint = searchPoint;
        String code = amapLocation.getCityCode();
        PoiSearch.Query query = new PoiSearch.Query(areaAddress, "停车场", code);
        query.setPageSize(pageSize);// 设置每页最多返回多少条poiitem
        query.setPageNum(page);//
        PoiSearch poiSearch = new PoiSearch(this, query);
        poiSearch.setBound(new PoiSearch.SearchBound(searchPoint, distance));//设置周边搜索的中心点以及区域
        poiSearch.setOnPoiSearchListener(this);//设置数据返回的监听器
        poiSearch.searchPOIAsyn();//开始搜索
    }

    private int page = 1;
    private int pageSize = 20;


    private NeverCarshXRecyclerView rv_eparking;
    private ParkingInforAdapter parkingInforAdapter;
    private String choiceAddress = "";


    public void showDialog() {
        bottomViewStatus(0);
        View contentView = LayoutInflater.from(this).inflate(R.layout.pop_parking_infor, null);
        rv_eparking = contentView.findViewById(R.id.rv_eparking);
        rv_eparking.setLoadingMoreEnabled(true);
        rv_eparking.setPullRefreshEnabled(false);
        rv_eparking.setLayoutManager(new LinearLayoutManager(FindParkingSpaceActivity.this, LinearLayoutManager.VERTICAL, false));// 布局管理器。
        parkingInforAdapter = new ParkingInforAdapter(this, parkingInforEntityList);
        rv_eparking.setAdapter(parkingInforAdapter);
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.setContentView(contentView);
        dialog.show();
        parkingInforAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                ParkingInforEntity parkingInforEntity = parkingInforEntityList.get(i);
                latLonPoint = new LatLonPoint(parkingInforEntity.getLatitude(), parkingInforEntity.getLongitude());
                choiceAddress = parkingInforEntity.getParkingAddrss();
                drawLine();
                dialog.dismiss();
            }
        });

        rv_eparking.setLoadingListener(new NeverCarshXRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                page++;
                searchPoi("", 1000);
            }
        });
        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                bottomViewStatus(1);
                parkingInforEntityList.clear();
                page = 1;
                searchPoi("", 1000);
            }
        });
    }

    private void bottomViewStatus(int status) {
        if (status == 1) {
            bottom_layout.setVisibility(View.VISIBLE);
            btn_setout.setVisibility(View.VISIBLE);
        } else {
            bottom_layout.setVisibility(View.GONE);
            btn_setout.setVisibility(View.GONE);
        }
    }

    private List<ParkingInforEntity> parkingInforEntityList = new ArrayList<>();


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                break;
            case R.id.tv_more_parking:
                showDialog();
                break;
            case R.id.btn_setout:
                if (TextUtils.isEmpty(choiceAddress)) {
                    ToastUtil.toastShow(FindParkingSpaceActivity.this, "请选择你要去的停车场");
                } else {
                    if (MapNaviUtils.isGdMapInstalled()) {
                        MapNaviUtils.openGaoDeNavi(FindParkingSpaceActivity.this, amapLocation.getLatitude(),
                                amapLocation.getLongitude(), amapLocation.getAddress(), latLonPoint.getLatitude(), latLonPoint.getLongitude(), choiceAddress);
                    } else if (MapNaviUtils.isBaiduMapInstalled()) {
                        MapNaviUtils.openBaiDuNavi(FindParkingSpaceActivity.this, amapLocation.getLatitude(),
                                amapLocation.getLongitude(), amapLocation.getAddress(), latLonPoint.getLatitude(), latLonPoint.getLongitude(), choiceAddress);
                    } else {
                        MapNaviUtils.openGaodeMapToGuide(FindParkingSpaceActivity.this);
                    }
                }
                break;
            case R.id.ed_search_content:
                Intent intent = new Intent(FindParkingSpaceActivity.this, SearchParkingActivity.class);
                intent.putExtra(SearchParkingActivity.CITYCODE, cityCode);
                startActivityForResult(intent, 2000);
                break;
            case R.id.iv_clicklocation:
                aMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(amapLocation.getLatitude(),
                        amapLocation.getLongitude()), 16f));
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            latLonPoint = new LatLonPoint(data.getDoubleExtra(SearchParkingActivity.CHOICELAT, 0),
                    data.getDoubleExtra(SearchParkingActivity.CHOICELON, 0));
            choiceAddress = data.getStringExtra(SearchParkingActivity.CHOICEADDRESS);
            drawLine();
        }
    }

    private void drawLine() {
        if (null != amapLocation) {
            double latitude = amapLocation.getLatitude();
            double longitude = amapLocation.getLongitude();
            RouteSearch routeSearch = new RouteSearch(this);
            routeSearch.setRouteSearchListener(this);
            RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(new LatLonPoint(latitude, longitude), latLonPoint);
            RouteSearch.DriveRouteQuery query = new RouteSearch.DriveRouteQuery(fromAndTo, RouteSearch.DRIVING_MULTI_CHOICE_HIGHWAY_AVOID_CONGESTION, null, null, "");
            routeSearch.calculateDriveRouteAsyn(query);
        } else {
            ToastUtil.toastShow(FindParkingSpaceActivity.this, "获取当前位置失败,请稍后再试");
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //在activity执行onSaveInstanceState时执行mMapView.onSaveInstanceState (outState)，保存地图当前的状态
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onCameraChange(CameraPosition cameraPosition) {
        LatLng latLng = cameraPosition.target;
        double longitude = latLng.longitude;
        double latitude = latLng.latitude;

        /**
         * 计算当前定位点和目标点的距离，并显示，单位为米
         * */
        LatLng latLng2 = new LatLng(lastLat, lastLon);
        float distance = AMapUtils.calculateLineDistance(latLng, latLng2);
        if ((lastLon == 0 && lastLat == 0) || distance >= 3000) {
            ParkingApplyModel parkingApplyModel = new ParkingApplyModel(FindParkingSpaceActivity.this);
            parkingApplyModel.nearStationList(0, latitude + "", longitude + "", 0.15, false, FindParkingSpaceActivity.this);
            lastLon = longitude;
            lastLat = latitude;
        }
    }

    private double lastLon;
    private double lastLat;

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }

    //公交
    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

    }

    private List<DrivingRouteOverlay> drivingRouteOverlays = new ArrayList<>();

    //驾车
    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int errorCode) {
        if (errorCode == 1000) {
            if (errorCode == AMapException.CODE_AMAP_SUCCESS) {
                if (driveRouteResult != null && driveRouteResult.getPaths() != null) {
                    if (driveRouteResult.getPaths().size() > 0) {
                        final DrivePath drivePath = driveRouteResult.getPaths()
                                .get(0);
                        DrivingRouteOverlay drivingRouteOverlay = new DrivingRouteOverlay(
                                this, aMap, drivePath,
                                driveRouteResult.getStartPos(),
                                driveRouteResult.getTargetPos(), null);
                        drivingRouteOverlay.setNodeIconVisibility(false);//设置节点marker是否显示
                        drivingRouteOverlay.setThroughPointIconVisibility(true);
                        drivingRouteOverlay.removeFromMap();
                        if (drivingRouteOverlays.size() > 0) {
                            drivingRouteOverlays.get(0).removeFromMap();
                        }
                        drivingRouteOverlay.addToMap();
                        drivingRouteOverlay.zoomToSpan();
                        drivingRouteOverlays.clear();
                        drivingRouteOverlays.add(drivingRouteOverlay);
                    }
                } else {
                    ToastUtil.toastShow(FindParkingSpaceActivity.this, "线路规划失败");
                }
            } else {
                ToastUtil.toastShow(FindParkingSpaceActivity.this, "线路规划失败");
            }
        } else {
            ToastUtil.toastShow(FindParkingSpaceActivity.this, "线路规划失败");
        }

    }

    //步行
    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

    }

    //骑车
    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {

    }

    private List<ParkingAddressEntity.ContentBean> parkingAddressList = new ArrayList<>();

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    parkingAddressList.clear();
                    clearMarkers();
                    ParkingAddressEntity parkingAddressEntity = GsonUtils.gsonToBean(result, ParkingAddressEntity.class);
                    parkingAddressList.addAll(parkingAddressEntity.getContent());
                    for (ParkingAddressEntity.ContentBean contentBean : parkingAddressList) {
                        Marker marker = aMap.addMarker(new MarkerOptions()
                                .position(new LatLng(contentBean.getLatitude(), contentBean.getLongitude()))
                                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                                        .decodeResource(getResources(), R.drawable.eparking_img_marker)))
                                .title(contentBean.getName())
                                .draggable(true));
                        marker.setObject("centernear");
                    }
                    aMap.invalidate();//刷新地图
                } catch (Exception e) {

                }
                break;
        }
    }

    /***删除除最近的搜有marker**/
    private void clearMarkers() {
        List<Marker> mapScreenMarkers = aMap.getMapScreenMarkers();
        for (int i = 0; i < mapScreenMarkers.size(); i++) {
            Marker marker = mapScreenMarkers.get(i);
            if (marker != null && null != marker.getObject()) {
                if (marker.getObject().toString().equalsIgnoreCase("centernear")) {
                    marker.remove();//移除当前Marker
                }
            }
        }
        aMap.invalidate();//刷新地图
    }
}
