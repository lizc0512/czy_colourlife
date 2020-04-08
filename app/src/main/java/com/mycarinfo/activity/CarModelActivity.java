package com.mycarinfo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.im.view.SideBar;
import com.mycarinfo.adapter.AllCarAdapter;
import com.mycarinfo.adapter.CarColurAdapter;
import com.mycarinfo.adapter.CarModelAdapter;
import com.mycarinfo.adapter.HotCarModelAdapter;
import com.mycarinfo.model.MyCarInfoModel;
import com.mycarinfo.protocol.COLOURTICKETCARBRANDINFO;
import com.mycarinfo.protocol.COLOURTICKETCARBRANDINFOLIST;
import com.mycarinfo.protocol.COLOURTICKETCARCOLOURINFOLIST;
import com.mycarinfo.protocol.COLOURTICKETCARMODELINFOLIST;
import com.mycarinfo.protocol.COLOURTICKETHOTCARINFOLIST;
import com.mycarinfo.protocol.VehicleGetcarbrandGetApi;
import com.mycarinfo.protocol.VehicleGetcarmodelGetApi;
import com.mycarinfo.protocol.VehicleGetcolourGetApi;
import com.mycarinfo.protocol.VehicleGethotcarGetApi;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * 选择车型
 */
public class CarModelActivity extends BaseActivity implements View.OnClickListener, HttpApiResponse {
    private ImageView mBack;
    private TextView mTitle;
    private TextView mRightText;

    private SideBar sideBar;
    private AllCarAdapter adapter;
    private CarModelAdapter carmodeladapter;//品牌N个车型信息
    private CarColurAdapter carcoluradapter;//汽车颜色
    private ListView sortListView;
    private GridView gvCarModel;
    private List<COLOURTICKETHOTCARINFOLIST> list = new ArrayList<>();//Gridview数据
    private List<COLOURTICKETCARBRANDINFO> carbrandlist = new ArrayList<>();//ABCD类型下的具体车
    private List<COLOURTICKETCARBRANDINFOLIST> brandlist = new ArrayList<>();//ABCDEDG
    private List<COLOURTICKETCARMODELINFOLIST> carmodellist = new ArrayList<>();//品牌N个车型信息
    private List<COLOURTICKETCARCOLOURINFOLIST> colourllist = new ArrayList<>();//汽车颜色
    private HotCarModelAdapter hotCarModelAdapter;
    private MyCarInfoModel myCarInfoModel;
    private ListView carmodelList;//品牌N个车型信息Listview
    private ListView carcolourList;//汽车颜色Listview
    private LinearLayout ll_addcar_carmodel;
    private LinearLayout ll_addcar_carcolour;
    private String car, carmodel, carcolour;
    private String brand_id, model_id, colour_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_model);
        initPublic();
        initView();
        initData();
    }

    private void initPublic() {
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        mRightText = (TextView) findViewById(R.id.user_top_view_right);
        mTitle.setText(getResources().getString(R.string.title_choice_cartype));
        mBack.setOnClickListener(this);
        ThemeStyleHelper.rightTexteFrameLayout(getApplicationContext(), czy_title_layout, mBack, mTitle, mRightText);
    }

    private View vHead;

    private void initView() {
        ll_addcar_carmodel = (LinearLayout) findViewById(R.id.ll_addcar_carmodel);
        ll_addcar_carcolour = (LinearLayout) findViewById(R.id.ll_carmodel_carcolour);
        carcolourList = (ListView) findViewById(R.id.lv_carmodel_carcolour);
        carmodelList = (ListView) findViewById(R.id.lv_carmodel_carmodel);
        sortListView = (ListView) this.findViewById(R.id.lv_carmodel);
        sideBar = (SideBar) this.findViewById(R.id.addcar_side_bar);
        vHead = View.inflate(this, R.layout.gridview_hotcar, null);
        gvCarModel = (GridView) vHead.findViewById(R.id.gv_carmodel2);
        gvCarModel.setSelector(new ColorDrawable(Color.TRANSPARENT));
        myCarInfoModel = new MyCarInfoModel(this);
        myCarInfoModel.getHotCar(this);
        myCarInfoModel.getCarBrand(this);
    }

    private void initData() {
        gvCarModel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                myCarInfoModel.getCarModel(CarModelActivity.this, String.valueOf(list.get(position).id));
                car = String.valueOf(list.get(position).name);//奥迪等品牌
                brand_id = String.valueOf(list.get(position).id);//奥迪等品牌ID
            }
        });
        // 设置右侧触摸监听
        sideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {

            @Override
            public void onTouchingLetterChanged(String s) {
                // 该字母首次出现的位置
                if (null != adapter) {
                    int position = adapter.getPositionForSection(s.charAt(0));
                    if (position != -1) {
                        sortListView.setSelection(position);
                    }
                }

            }
        });
        sortListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (ll_addcar_carcolour.getVisibility() == View.VISIBLE || ll_addcar_carmodel.getVisibility() == View.VISIBLE) {
                    ll_addcar_carcolour.setVisibility(View.GONE);
                    ll_addcar_carmodel.setVisibility(View.GONE);
                } else {
                    if (position >= 1) {
                        myCarInfoModel.getCarModel(CarModelActivity.this, String.valueOf(carbrandlist.get(position - 1).id));
                        car = String.valueOf(carbrandlist.get(position - 1).name);//奥迪等品牌
                        brand_id = String.valueOf(carbrandlist.get(position - 1).id);//奥迪等品牌ID
                    }
                }

            }
        });
        carmodelList.setOnItemClickListener(new AdapterView.OnItemClickListener() {//汽车子品牌
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!colourllist.isEmpty() || colourllist.size() != 0) {

                } else {

                }
                if (ll_addcar_carcolour.getVisibility() == View.VISIBLE) {
                    ll_addcar_carcolour.setVisibility(View.GONE);
                } else {

                }
                carmodel = carmodellist.get(position).name;//A4,A8车型
                model_id = String.valueOf(carmodellist.get(position).id);//A4,A8车型ID
                myCarInfoModel.getColour(CarModelActivity.this);
                ll_addcar_carmodel.setVisibility(View.VISIBLE);
            }
        });
        carcolourList.setOnItemClickListener(new AdapterView.OnItemClickListener() {//汽车颜色
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {//颜色
                ll_addcar_carcolour.setVisibility(View.GONE);
                ll_addcar_carmodel.setVisibility(View.GONE);
                carcolour = colourllist.get(position).colour;
                colour_id = String.valueOf(colourllist.get(position).id);
                carcoluradapter.setCheckId(colourllist.get(position).id);
                Intent intent = new Intent();
                intent.putExtra("car", car);
                intent.putExtra("carid", brand_id);
                intent.putExtra("carmodel", carmodel);
                intent.putExtra("carmodelid", model_id);
                intent.putExtra("carcolour", carcolour);
                intent.putExtra("carcolourid", colour_id);
                setResult(1, intent);
                CarModelActivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                CarModelActivity.this.finish();
                break;
        }
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(VehicleGethotcarGetApi.class)) {
            VehicleGethotcarGetApi vehicleGethotcarGetApi = (VehicleGethotcarGetApi) api;
            if (!(vehicleGethotcarGetApi.response.data).isEmpty()) {//热门车型
                list = vehicleGethotcarGetApi.response.data;
                if (!list.isEmpty() || list.size() != 0) {
                    sortListView.addHeaderView(vHead);
                }
                if (hotCarModelAdapter == null) {
                    hotCarModelAdapter = new HotCarModelAdapter(this, list);
                    gvCarModel.setAdapter(hotCarModelAdapter);
                } else {
                    hotCarModelAdapter.notifyDataSetChanged();
                }
            }

        } else if (api.getClass().equals(VehicleGetcarmodelGetApi.class)) {
            VehicleGetcarmodelGetApi vehicleGetcarmodelGetApi = (VehicleGetcarmodelGetApi) api;
            carmodellist.clear();
            carmodellist.addAll(vehicleGetcarmodelGetApi.response.data);
            if (carbrandlist.size() > 0) {
                ll_addcar_carmodel.setVisibility(View.VISIBLE);
            } else {
                ll_addcar_carmodel.setVisibility(View.GONE);
            }
            if (carmodeladapter == null) {
                carmodeladapter = new CarModelAdapter(this, carmodellist);
                carmodelList.setAdapter(carmodeladapter);
            } else {
                carmodeladapter.notifyDataSetChanged();
            }
        } else if (api.getClass().equals(VehicleGetcolourGetApi.class)) {
            VehicleGetcolourGetApi vehicleGetcolourGetApi = (VehicleGetcolourGetApi) api;
            colourllist.clear();
            colourllist .addAll( vehicleGetcolourGetApi.response.data);
            if (carbrandlist.size() > 0) {
                ll_addcar_carcolour.setVisibility(View.VISIBLE);
            } else {
                ll_addcar_carcolour.setVisibility(View.GONE);
            }
            if (carcoluradapter == null) {
                carcoluradapter = new CarColurAdapter(this, colourllist);
                carcolourList.setAdapter(carcoluradapter);
            } else {
                carcoluradapter.notifyDataSetChanged();
            }
        } else if (api.getClass().equals(VehicleGetcarbrandGetApi.class)) {
            VehicleGetcarbrandGetApi vehicleGetcarbrandGetApi = (VehicleGetcarbrandGetApi) api;
            brandlist.clear();
            brandlist.addAll(vehicleGetcarbrandGetApi.response.data) ;
            for (int i = 0; i < brandlist.size(); i++) {
                carbrandlist.addAll(brandlist.get(i).list);
            }
            if (adapter == null) {
                adapter = new AllCarAdapter(this, carbrandlist);
                sortListView.setAdapter(adapter);
            } else {
                adapter.notifyDataSetChanged();
            }
        }
    }
}
