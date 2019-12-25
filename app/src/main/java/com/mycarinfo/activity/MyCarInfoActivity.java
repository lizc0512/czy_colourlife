package com.mycarinfo.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.BeeFramework.swipe.SwipeMenu;
import com.BeeFramework.swipe.SwipeMenuCreator;
import com.BeeFramework.swipe.SwipeMenuItem;
import com.BeeFramework.swipe.SwipeMenuListView;
import com.external.maxwin.view.IXListViewListener;
import com.mycarinfo.adapter.MyCarListAdapter;
import com.mycarinfo.model.MyCarInfoModel;
import com.mycarinfo.protocol.COLOURTICKETMYCARINFOLIST;
import com.mycarinfo.protocol.VehicleDeletevehiclePostApi;
import com.mycarinfo.protocol.VehicleGetmyvehicleGetApi;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

public class MyCarInfoActivity extends BaseActivity implements View.OnClickListener, HttpApiResponse {
    private ImageView mBack;
    private TextView mTitle;
    private TextView mRightText;
    private SwipeMenuListView xListView;
    private Button btnAddCar;
    private MyCarInfoModel myCarInfoModel;
    private List<COLOURTICKETMYCARINFOLIST> list=new ArrayList<>();
    private MyCarListAdapter adapter;
    private int deletePosition;
    private ImageView img_empty;
    private TextView tv_tips;
    private int bind_state;
    /**
     * Item左滑菜单
     */
    private SwipeMenuCreator creator = new SwipeMenuCreator() {
        @Override
        public void create(SwipeMenu menu) {
            //创建一个菜单条
            SwipeMenuItem deleteItem = new SwipeMenuItem(getApplicationContext());
            // 设置菜单的背景
            deleteItem.setBackground(new ColorDrawable(Color.rgb(0xff, 0x00, 0x00)));
            // 宽度  菜单的宽度是一定要有的，否则不显示菜单
            deleteItem.setWidth((int) (90 * getResources().getDisplayMetrics().density + 0.5f));
            // 菜单标题
            deleteItem.setTitle(getResources().getString(R.string.im_delete));
            // 标题大小
            deleteItem.setTitleSize(18);
            // 标题的颜色
            deleteItem.setTitleColor(Color.WHITE);
            // 添加到menu
            menu.addMenuItem(deleteItem);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_car_info);
        initView();
    }

    private void initView() {
        img_empty = (ImageView) findViewById(R.id.img_empty);
        tv_tips = (TextView) findViewById(R.id.tv_tips);
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        mRightText = (TextView) findViewById(R.id.user_top_view_right);
        mTitle.setText(getResources().getString(R.string.title_car_list));
        mBack.setOnClickListener(this);
        btnAddCar = (Button) findViewById(R.id.btn_addcar);
        btnAddCar.setOnClickListener(this);

        xListView = new SwipeMenuListView(this);
        xListView = (SwipeMenuListView) findViewById(R.id.mycar_list);
        xListView.setAdapter(null);
        ThemeStyleHelper.rightTexteFrameLayout(getApplicationContext(), czy_title_layout, mBack, mTitle, mRightText);
        xListView.setXListViewListener(new IXListViewListener() {
            @Override
            public void onRefresh(int id) {
                refreshData();
            }

            @Override
            public void onLoadMore(int id) {
            }
        }, 0);
        // 屏蔽下拉，激活上拉
        xListView.startHeaderRefresh();
        xListView.setPullLoadEnable(true);
        xListView.setPullRefreshEnable(true);
        xListView.loadMoreHide();
        xListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int a = position - 1;
                if (a >= 0) {
                    bind_state = list.get(a).bind_mealticket_state;
                    if (bind_state == 1) {//已绑定
                    } else if (bind_state == 0) {//未绑定
                        Intent intent = new Intent(MyCarInfoActivity.this, BindFPActivity.class);
                        intent.putExtra("vehicle_id", String.valueOf(list.get(a).id));
                        startActivityForResult(intent, 100);
                    }
                }
            }
        });
        xListView.setMenuCreator(creator);
        xListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClick(int position, SwipeMenu menu, int index) {
                if (position >= 0) {
                    //没有选择其他item时根据请求返回数据判断，选择过其他item时根据adapter保存的标记位判断
                    deletePosition = position;
                    myCarInfoModel.postdeleteVehicle(MyCarInfoActivity.this, String.valueOf(list.get(position).id), true);
                }
            }
        });
    }

    private void refreshData() {
        if (myCarInfoModel == null) {
            myCarInfoModel = new MyCarInfoModel(MyCarInfoActivity.this);
        }
        myCarInfoModel.getMyVehicleInfo(this, false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                MyCarInfoActivity.this.finish();
                break;
            case R.id.btn_addcar:
                Intent intent = new Intent();
                intent.setClass(MyCarInfoActivity.this, AddCarActivity.class);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case 1:
                if (resultCode == 120) {
                    xListView.startHeaderRefresh();
                }
                break;
            case 100://回调绑定车辆信息
                refreshData();
                break;
        }
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(VehicleGetmyvehicleGetApi.class)) {//我的车辆
            VehicleGetmyvehicleGetApi vehicleGetmyvehicleGetApi = (VehicleGetmyvehicleGetApi) api;
            if (vehicleGetmyvehicleGetApi.response.code == 0) {
                list.clear();
                list.addAll( vehicleGetmyvehicleGetApi.response.data);
                if (list.size() == 0) {
                    img_empty.setVisibility(View.VISIBLE);
                    tv_tips.setVisibility(View.VISIBLE);
                } else {
                    img_empty.setVisibility(View.GONE);
                    tv_tips.setVisibility(View.GONE);
                    if (adapter == null) {
                        adapter = new MyCarListAdapter(this, list);
                        xListView.setAdapter(adapter);
                    } else {
                        adapter.notifyDataSetChanged();
                    }
                }
            }
            xListView.stopLoadMore();
            xListView.stopRefresh();
        } else if (api.getClass().equals(VehicleDeletevehiclePostApi.class)) {
            VehicleDeletevehiclePostApi vehicleDeletevehiclePostApi = (VehicleDeletevehiclePostApi) api;
            if (vehicleDeletevehiclePostApi.response.code == 0) {
                list.remove(deletePosition);
                adapter.notifyDataSetChanged();
                ToastUtil.toastShow(MyCarInfoActivity.this, getResources().getString(R.string.car_del_success));
            } else {
                ToastUtil.toastShow(MyCarInfoActivity.this, "删除失败，请重试");
            }

        }
    }
}
