package com.eparking.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.Utils;
import com.BeeFramework.activity.BaseFragment;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.activity.CarsBindActivity;
import com.eparking.activity.EparkingCarDetailsActivity;
import com.eparking.adapter.EparkingCarsAdapter;
import com.eparking.helper.CustomDialog;
import com.eparking.model.ParkingHomeModel;
import com.eparking.model.ParkingOperationModel;
import com.eparking.protocol.CarInforEntity;
import com.nohttp.utils.GsonUtils;
import com.nohttp.utils.SpaceItemDecoration;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.eparking.activity.EparkingCarDetailsActivity.CARINFOR;

/**
 * @name ${yuansk}
 * @class name：com.eparking.fragment
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/11 15:46
 * @change
 * @chang time
 * @class describe  我的卡包 车辆
 */
public class EparkingCarsFragment extends BaseFragment implements NewHttpResponse {
    private SwipeMenuRecyclerView month_card_rv;
    private LinearLayout no_data_layout;
    private TextView tv_nodata_title;
    private Button btn_nodata;
    private EparkingCarsAdapter eparkingCarsAdapter;
    private List<CarInforEntity.ContentBean> carInforEntityList;
    private ParkingHomeModel parkingHomeModel;
    private ParkingOperationModel parkingOperationModel;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_month_card;
    }

    @Override
    protected void initView(View rootView) {
        month_card_rv = rootView.findViewById(R.id.month_card_rv);
        no_data_layout = rootView.findViewById(R.id.no_data_layout);
        tv_nodata_title = rootView.findViewById(R.id.tv_nodata_title);
        btn_nodata = rootView.findViewById(R.id.btn_nodata);
        month_card_rv.setLayoutManager(new LinearLayoutManager(getActivity()));// 布局管理器。
        month_card_rv.setSwipeMenuCreator(swipeMenuCreator);
        month_card_rv.addItemDecoration(new SpaceItemDecoration(Utils.dip2px(getActivity(), 10)));
        month_card_rv.setSwipeMenuItemClickListener(mMenuItemClickListener);
        month_card_rv.useDefaultLoadMore();
        carInforEntityList = new ArrayList<>();
        eparkingCarsAdapter = new EparkingCarsAdapter(getActivity(), carInforEntityList);
        month_card_rv.setAdapter(eparkingCarsAdapter);
        eparkingCarsAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Intent intent = new Intent(getActivity(), EparkingCarDetailsActivity.class);
                removePos = i;
                intent.putExtra(CARINFOR, carInforEntityList.get(i));
                startActivityForResult(intent, 1000);
            }
        });
        parkingHomeModel = new ParkingHomeModel(getActivity());
        parkingOperationModel = new ParkingOperationModel(getActivity());
    }

    private int removePos;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 200) {
            parkingOperationModel.carUnBindOpenation(1, carInforEntityList.get(removePos).getCar_id(), EparkingCarsFragment.this);
            carInforEntityList.remove(removePos);
            eparkingCarsAdapter.notifyDataSetChanged();
        }
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            parkingHomeModel.getHomeCarList(0, this);
        }
    }

    private void setEmptyView() {
        if (isAdded()) {
            if (carInforEntityList.size() == 0) {
                no_data_layout.setVisibility(View.VISIBLE);
                tv_nodata_title.setText(getResources().getString(R.string.parking_no_car));
                btn_nodata.setText(getResources().getString(R.string.parking_bind_car));
                btn_nodata.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), CarsBindActivity.class);
                        getActivity().startActivity(intent);
                    }
                });
            } else {
                no_data_layout.setVisibility(View.GONE);
            }
        }


    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = (int) (50 * getResources().getDisplayMetrics().density + 0.5f);
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            SwipeMenuItem deleteItem = new SwipeMenuItem(getActivity())
                    .setBackground(R.drawable.shape_menu_unbind)
                    .setText(getResources().getString(R.string.parking_car_unbind)) // 文字。
                    .setTextColor(Color.WHITE) // 文字颜色。
                    .setTextSize(16) // 文字大小。
                    .setWidth(width)
                    .setHeight(height);
            swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
        }
    };

    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            // 任何操作必须先关闭菜单，否则可能出现Item菜单打开状态错乱。
            menuBridge.closeMenu();
            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。
            switch (menuPosition) {
                case 0:
                    CarInforEntity.ContentBean contentBean = carInforEntityList.get(adapterPosition);
                    showDialog(contentBean.getPlate(), contentBean.getCar_id(), adapterPosition);
                    break;
            }
        }
    };


    private void showDialog(String plate, final String carId, final int removePos) {
        final CustomDialog customDialog = new CustomDialog(getActivity(), R.style.custom_dialog_theme);
        customDialog.show();
        customDialog.setCancelable(false);
        customDialog.dialog_content.setText(getResources().getString(R.string.notice_unbind_start) + plate + getResources().getString(R.string.notice_stopcar_msg) + getResources().getString(R.string.notice_unbind_end));
        customDialog.dialog_button_cancel.setText(getResources().getString(R.string.message_unbind));
        customDialog.dialog_button_ok.setText(getResources().getString(R.string.message_cancel));
        customDialog.dialog_line.setVisibility(View.VISIBLE);
        customDialog.dialog_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                parkingOperationModel.carUnBindOpenation(1, carId, EparkingCarsFragment.this);
                carInforEntityList.remove(removePos);
                eparkingCarsAdapter.notifyDataSetChanged();
                customDialog.dismiss();
            }
        });
        customDialog.dialog_button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                isFirst = true;
                carInforEntityList.clear();
                try {
                    CarInforEntity carInforEntity = GsonUtils.gsonToBean(result, CarInforEntity.class);
                    carInforEntityList.addAll(carInforEntity.getContent());
                } catch (Exception e) {

                }
                if (carInforEntityList.size() == 0) {
                    setEmptyView();
                }
                eparkingCarsAdapter.notifyDataSetChanged();
                break;
            case 1:


                break;
        }
    }
}
