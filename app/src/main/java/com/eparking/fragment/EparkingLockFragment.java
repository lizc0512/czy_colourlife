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
import com.eparking.activity.EparkingLockDetailsActivity;
import com.eparking.adapter.EparkingLockAdapter;
import com.eparking.helper.CustomDialog;
import com.eparking.model.ParkingHomeModel;
import com.eparking.model.ParkingOperationModel;
import com.eparking.protocol.ParkingLockEntity;
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

import static com.eparking.activity.EparkingLockDetailsActivity.LOCKINFOR;

/**
 * @name ${yuansk}
 * @class name：com.eparking.fragment
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/11 15:49
 * @change
 * @chang time
 * @class describe    我的卡包 车位锁
 */
public class EparkingLockFragment extends BaseFragment implements NewHttpResponse {

    private SwipeMenuRecyclerView month_card_rv;
    private LinearLayout no_data_layout;
    private TextView tv_nodata_title;
    private Button btn_nodata;
    private EparkingLockAdapter eparkingLockAdapter;
    private List<ParkingLockEntity.ContentBean> parkingLockEntityList;

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
        month_card_rv.setAutoLoadMore(false);
        parkingLockEntityList = new ArrayList<>();
        eparkingLockAdapter = new EparkingLockAdapter(getActivity(), parkingLockEntityList);
        month_card_rv.setAdapter(eparkingLockAdapter);
        eparkingLockAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Intent intent = new Intent(getActivity(), EparkingLockDetailsActivity.class);
                intent.putExtra(LOCKINFOR, parkingLockEntityList.get(i));
                removePos = i;
                startActivityForResult(intent, 2000);
            }
        });
        parkingHomeModel = new ParkingHomeModel(getActivity());
        parkingOperationModel = new ParkingOperationModel(getActivity());
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            parkingHomeModel.getHomeLockList(0, this);
        }
    }

    private String operateStatus;
    private ParkingLockEntity.ContentBean parkingLockContentBean;

    public void operateLockStatus(ParkingLockEntity.ContentBean parkingLockContentBean) {
        this.parkingLockContentBean = parkingLockContentBean;
        String lockCode = parkingLockContentBean.getEtcode();
        String lockStatus = parkingLockContentBean.getStatus();
        if (!"close".equalsIgnoreCase(lockStatus)) {
            operateStatus = "close";
        } else {
            operateStatus = "open";
        }
        if (null != getActivity()) {
            ParkingOperationModel parkingOperationModel = new ParkingOperationModel(getActivity());
            parkingOperationModel.carLockOpenation(2, lockCode, operateStatus, this);
        }
    }

    private void setEmptyView() {
        if (isAdded()) {
            if (parkingLockEntityList.size() == 0) {
                no_data_layout.setVisibility(View.VISIBLE);
                tv_nodata_title.setText(getResources().getString(R.string.parking_no_lock));
                btn_nodata.setVisibility(View.GONE);
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
            swipeRightMenu.addMenuItem(deleteItem);

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
                    ParkingLockEntity.ContentBean contentBean = parkingLockEntityList.get(adapterPosition);
                    showDialog(contentBean.getLock_name(), contentBean.getLock_id(), adapterPosition);
                    break;
            }
        }
    };

    private void showDialog(String lockName, final String lockId, final int removePos) {
        final CustomDialog customDialog = new CustomDialog(getActivity(), R.style.custom_dialog_theme);
        customDialog.show();
        customDialog.setCancelable(false);
        customDialog.dialog_content.setText(getResources().getString(R.string.notice_unbind_start) + lockName + getResources().getString(R.string.notice_lock_msg) + getResources().getString(R.string.notice_unbind_end));
        customDialog.dialog_button_cancel.setText(getResources().getString(R.string.message_unbind));
        customDialog.dialog_button_ok.setText(getResources().getString(R.string.message_cancel));
        customDialog.dialog_line.setVisibility(View.VISIBLE);
        customDialog.dialog_button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
                parkingOperationModel.lockExtraUpdate(1, lockId, "N", EparkingLockFragment.this);
                parkingLockEntityList.remove(removePos);
                eparkingLockAdapter.notifyDataSetChanged();
            }
        });
        customDialog.dialog_button_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customDialog.dismiss();
            }
        });
    }

    private int removePos;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                isFirst = true;
                parkingLockEntityList.clear();
                try {
                    ParkingLockEntity parkingLockEntity = GsonUtils.gsonToBean(result, ParkingLockEntity.class);
                    parkingLockEntityList.addAll(parkingLockEntity.getContent());
                } catch (Exception e) {

                }
                if (parkingLockEntityList.size() == 0) {
                    setEmptyView();
                }
                eparkingLockAdapter.notifyDataSetChanged();
                break;
            case 1:


                break;
            case 2:
                if ("close".equalsIgnoreCase(operateStatus)) {
                    parkingLockContentBean.setStatus("open");
                } else {
                    parkingLockContentBean.setStatus("close");
                }
                eparkingLockAdapter.notifyDataSetChanged();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1000 && resultCode == 200) {
            parkingOperationModel.lockExtraUpdate(1, parkingLockEntityList.get(removePos).getLock_id(), "N", EparkingLockFragment.this);
            parkingLockEntityList.remove(removePos);
            eparkingLockAdapter.notifyDataSetChanged();
        }
    }
}
