package com.eparking.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.activity.BaseFragmentActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.NoScrollGridView;
import com.cashier.adapter.ViewPagerAdapter;
import com.eparking.adapter.PopFilterAdapter;
import com.eparking.fragment.ParkingRecordFragment;
import com.eparking.fragment.PaymentRecordFragment;
import com.eparking.fragment.TicketOpenRecordFragment;
import com.eparking.helper.ConstantKey;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingHomeModel;
import com.eparking.protocol.HistoryCarBrand;
import com.eparking.protocol.HistoryStationEntity;
import com.eparking.protocol.ParkingAddressEntity;
import com.eparking.view.NoViewPager;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.csh.colourful.life.view.pickview.TimePickerView;
import cn.net.cyberway.R;
import cn.net.cyberway.utils.TableLayoutUtils;

import static com.eparking.activity.FindParkingSpaceActivity.FROMOTHER;
import static com.eparking.activity.SearchParkingActivity.CHOICEADDRESS;
import static com.eparking.activity.SearchParkingActivity.CHOICELAT;
import static com.eparking.activity.SearchParkingActivity.CHOICELON;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/10 16:48
 * @change
 * @chang time
 * @class describe  历史记录
 */
public class EParkingHistoryRecordActivity extends BaseFragmentActivity implements View.OnClickListener, NewHttpResponse {

    public static final String CHOICEINDEX = "choiceindex";
    public static final String CARID = "carid";
    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private TextView user_top_view_right;//筛选
    private LinearLayout choice_month_layout;
    private TextView tv_choice_month;
    private TextView tv_total_fee;
    private TabLayout cardholder_tablayout;
    private NoViewPager cardholder_viewpager;
    private String[] tabTitleArray = null;
    private List<Fragment> fragmentList = new ArrayList<>();
    private PaymentRecordFragment paymentRecordFragment;
    private TicketOpenRecordFragment ticketOpenRecordFragment;
    private ParkingRecordFragment parkingRecordFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eparking_cardholder);
        czy_title_layout = findViewById(R.id.czy_title_layout);
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        tv_title = findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_history_record));
        tabTitleArray = getResources().getStringArray(R.array.parking_record_menu);
        user_top_view_right = (TextView) findViewById(R.id.user_top_view_right);
        user_top_view_right.setVisibility(View.VISIBLE);
        user_top_view_right.setTextColor(Color.parseColor("#27a2f0"));
        user_top_view_right.setText(getResources().getString(R.string.parking_filter));
        cardholder_tablayout = findViewById(R.id.cardholder_tablayout);
        cardholder_viewpager = findViewById(R.id.cardholder_viewpager);
        choice_month_layout = findViewById(R.id.choice_month_layout);
        choice_month_layout.setVisibility(View.VISIBLE);
        tv_choice_month = findViewById(R.id.tv_choice_month);
        tv_total_fee = findViewById(R.id.tv_total_fee);
        tv_total_fee.setVisibility(View.INVISIBLE);
        imageView_back.setOnClickListener(this);
        user_top_view_right.setOnClickListener(this);
        tv_choice_month.setOnClickListener(this);
        String carId = getIntent().getStringExtra(CARID);
        for (int i = 0; i < tabTitleArray.length; i++) {
            cardholder_tablayout.addTab(cardholder_tablayout.newTab().setText(tabTitleArray[i]));
        }
        paymentRecordFragment = new PaymentRecordFragment();
        ticketOpenRecordFragment = new TicketOpenRecordFragment();
        parkingRecordFragment = new ParkingRecordFragment();
        Bundle bundle = new Bundle();
        bundle.putString(CARID, carId);
        paymentRecordFragment.setArguments(bundle);
        ticketOpenRecordFragment.setArguments(bundle);
        parkingRecordFragment.setArguments(bundle);
        fragmentList.add(paymentRecordFragment);
        fragmentList.add(ticketOpenRecordFragment);
        fragmentList.add(parkingRecordFragment);
        cardholder_tablayout.setTabMode(TabLayout.MODE_FIXED);
        cardholder_tablayout.setSelectedTabIndicatorHeight(4);
        cardholder_tablayout.setSelectedTabIndicatorColor(Color.parseColor("#27a2f0"));
        cardholder_tablayout.setTabTextColors(Color.parseColor("#333b46"), Color.parseColor("#27a2f0"));
        cardholder_tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(), this, fragmentList, tabTitleArray);
        cardholder_viewpager.setAdapter(adapter);
        cardholder_viewpager.setOffscreenPageLimit(fragmentList.size());
        cardholder_tablayout.setupWithViewPager(cardholder_viewpager);
        TableLayoutUtils.reflex(cardholder_tablayout);
        long currentMills = System.currentTimeMillis();
        tv_choice_month.setText(TimeUtil.getYearMonthToString(currentMills));
        tv_choice_month.setTextColor(Color.parseColor("#27a2f0"));
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, imageView_back, tv_title);
        int index = getIntent().getIntExtra(CHOICEINDEX, 0);
        cardholder_viewpager.setCurrentItem(index);
        cardholder_viewpager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 1) {
                    user_top_view_right.setVisibility(View.GONE);
                    choice_month_layout.setVisibility(View.GONE);
                } else {
                    choice_month_layout.setVisibility(View.VISIBLE);
                    user_top_view_right.setVisibility(View.VISIBLE);
                }
                currentPos = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        ParkingActivityUtils.getInstance().addActivity(this);
        ParkingHomeModel parkingHomeModel = new ParkingHomeModel(EParkingHistoryRecordActivity.this);
        parkingHomeModel.getHistoryStation(0, EParkingHistoryRecordActivity.this);
    }

    private int currentPos;


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.user_top_view_right:  //筛选
                showFilterPop();
                break;
            case R.id.tv_choice_month:  //筛选月份
                showDatePicker();
                break;
            case R.id.tv_cancel:  //取消
                popupWindow.dismiss();
                break;
            case R.id.tv_define:  //确定
                popupWindow.dismiss();
                refreshFragmentFilter();
                break;
            case R.id.tv_filter_all:  //确定
                resetAllFilterStatus(1);
                break;
        }
    }


    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    private void showFilterPop() {
        View popView = LayoutInflater.from(EParkingHistoryRecordActivity.this).inflate(R.layout.pop_filter_history, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, true);
        setBackgroundAlpha(0.8f);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
        popupWindow.showAtLocation(findViewById(R.id.root_history_layout), Gravity.BOTTOM, 0, 0);
        initPopView(popView);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
    }

    private PopupWindow popupWindow;
    private TextView tv_cancel;
    private TextView tv_define;
    private TextView tv_filter_all;
    private NoScrollGridView gv_parking_address;
    private NoScrollGridView gv_car_number;
    private NoScrollGridView gv_parking_type;

    private List<String> parkingAddressList = new ArrayList<>();
    private List<String> carNumberList = new ArrayList<>();
    private List<String> parkingTypeList = new ArrayList<>();
    private List<HistoryStationEntity.ContentBean.ListsBean> stationList = new ArrayList<>();
    private List<HistoryCarBrand.ContentBean> carBrandList = new ArrayList<>();

    private PopFilterAdapter parkingAddressAdpter;
    private PopFilterAdapter carNumberAdpter;
    private PopFilterAdapter parkingTypeAdpter;

    private void initPopView(View popView) {
        tv_cancel = popView.findViewById(R.id.tv_cancel);
        tv_define = popView.findViewById(R.id.tv_define);
        tv_filter_all = popView.findViewById(R.id.tv_filter_all);
        TextView tv_all_title = popView.findViewById(R.id.tv_all_title);
        gv_parking_address = popView.findViewById(R.id.gv_parking_address);
        gv_car_number = popView.findViewById(R.id.gv_car_number);
        gv_parking_type = popView.findViewById(R.id.gv_parking_type);
        tv_cancel.setOnClickListener(this);
        tv_define.setOnClickListener(this);
        tv_filter_all.setOnClickListener(this);
        parkingAddressList.clear();
        carNumberList.clear();
        parkingTypeList.clear();
        if (currentPos == 0) {
            tv_filter_all.setVisibility(View.VISIBLE);
            tv_all_title.setVisibility(View.VISIBLE);
        } else {
            tv_filter_all.setVisibility(View.GONE);
            tv_all_title.setVisibility(View.GONE);
        }
        String historyStation = shared.getString(ConstantKey.EPARKINGCARHISTORYSTATION, "");
        if (!TextUtils.isEmpty(historyStation)) {
            try {
                HistoryStationEntity historyStationEntity = GsonUtils.gsonToBean(historyStation, HistoryStationEntity.class);
                stationList.clear();
                stationList.addAll(historyStationEntity.getContent().getLists());
                for (HistoryStationEntity.ContentBean.ListsBean listsBean : stationList) {
                    parkingAddressList.add(listsBean.getStation_name());
                }
            } catch (Exception e) {

            }
        }
        String historyCar = shared.getString(ConstantKey.EPARKINGCARHISTORYBRAND, "");
        if (!TextUtils.isEmpty(historyCar)) {
            try {
                HistoryCarBrand historyCarBrand = GsonUtils.gsonToBean(historyCar, HistoryCarBrand.class);
                carBrandList.clear();
                carBrandList.addAll(historyCarBrand.getContent());
                for (HistoryCarBrand.ContentBean contentBean : carBrandList) {
                    carNumberList.add(contentBean.getPlate());
                }
            } catch (Exception e) {

            }
        }
        parkingTypeList.add(getResources().getString(R.string.monthcard));
        parkingTypeList.add(getResources().getString(R.string.temporary));
        parkingAddressAdpter = new PopFilterAdapter(EParkingHistoryRecordActivity.this, parkingAddressList, 1);
        gv_parking_address.setAdapter(parkingAddressAdpter);
        carNumberAdpter = new PopFilterAdapter(EParkingHistoryRecordActivity.this, carNumberList, 1);
        gv_car_number.setAdapter(carNumberAdpter);
        parkingTypeAdpter = new PopFilterAdapter(EParkingHistoryRecordActivity.this, parkingTypeList, 1);
        gv_parking_type.setAdapter(parkingTypeAdpter);
        gv_parking_address.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choiceStationId = stationList.get(position).getStation_id();
                parkingAddressAdpter.setSelectPos(position);
                resetAllFilterStatus(0);
            }
        });
        gv_car_number.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choiceCarId = carBrandList.get(position).getCar();
                carNumberAdpter.setSelectPos(position);
                resetAllFilterStatus(0);
            }
        });
        gv_parking_type.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                choiceType = position;
                parkingTypeAdpter.setSelectPos(position);
                resetAllFilterStatus(0);
            }
        });
    }

    private String choiceStationId = "";
    private String choiceCarId = "";
    private int choiceType = -1;

    private void resetAllFilterStatus(int status) {
        if (status == 1) {
            tv_filter_all.setBackgroundResource(R.drawable.shape_history_filterchecked);
            tv_filter_all.setTextColor(getResources().getColor(R.color.white));
            choiceStationId = "";
            choiceCarId = "";
            choiceType = -1;
            parkingAddressAdpter.setSelectPos(-1);
            carNumberAdpter.setSelectPos(-1);
            parkingTypeAdpter.setSelectPos(-1);
        } else {
            tv_filter_all.setBackgroundResource(R.drawable.shape_history_filterdefault);
            tv_filter_all.setTextColor(getResources().getColor(R.color.color_333b46));
        }
    }

    private TimePickerView pvTime;

    private void showDatePicker() {
        Calendar selectedDate = Calendar.getInstance();
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                long choiceTime = date.getTime();
                refreshFragmentData(choiceTime);
                tv_choice_month.setText(TimeUtil.getYearMonthToString(choiceTime));
            }
        })
                .setType(new boolean[]{true, true, false, false, false, false})
                .setCancelText(getResources().getString(R.string.message_cancel))//取消按钮文字
                .setSubmitText(getResources().getString(R.string.message_define))//确认按钮文字
                .setContentSize(18)//滚轮文字大小
                .setTitleSize(20)//标题文字大小
                .setTitleText("选择年月")//标题文字
                .setOutSideCancelable(false)//点击屏幕，点在控件外部范围时，是否取消显示
                .isCyclic(true)//是否循环滚动
                .setTitleColor(Color.parseColor("#333b46"))//标题文字颜色
                .setSubmitColor(Color.parseColor("#27a2f0"))//确定按钮文字颜色
                .setCancelColor(Color.parseColor("#27a2f0"))//取消按钮文字颜色
                .setTitleBgColor(Color.parseColor("#f5f5f5"))//标题背景颜色 Night mode
                .setBgColor(Color.parseColor("#ffffff"))//滚轮背景颜色 Night mode
                .setDate(selectedDate)// 默认是系统时间*/
                .setRange(selectedDate.get(Calendar.YEAR) - 10, selectedDate.get(Calendar.YEAR) + 10)//默认是1900-2100年
                .setLabel("年", "月", "", "", "", "")
                .build();
        pvTime.show();
    }

    /**
     * 筛选日期刷新
     ***/
    private void refreshFragmentData(long times) {
        switch (currentPos) {
            case 0:
                paymentRecordFragment.refreshDataList(times / 1000);
                parkingRecordFragment.refreshFilterList(false);
                ticketOpenRecordFragment.refreshFilterList(false);
                break;
            case 1:
                ticketOpenRecordFragment.refreshDataList(times / 1000);
                paymentRecordFragment.refreshFilterList(false);
                parkingRecordFragment.refreshFilterList(false);
                break;
            case 2:
                parkingRecordFragment.refreshDataList(times / 1000);
                paymentRecordFragment.refreshFilterList(false);
                ticketOpenRecordFragment.refreshFilterList(false);
                break;
        }
    }

    private void refreshFragmentFilter() {
        //各个Fragment刷新数据
        if (currentPos == 0) {
            paymentRecordFragment.refreshFilterList(choiceCarId, choiceStationId, choiceType);
            parkingRecordFragment.refreshFilterList(false);
        } else if (currentPos == 2) {
            parkingRecordFragment.refreshFilterList(choiceCarId, choiceStationId, choiceType);
            paymentRecordFragment.refreshFilterList(false);
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    ParkingAddressEntity parkingAddressEntity = GsonUtils.gsonToBean(result, ParkingAddressEntity.class);
                    List<ParkingAddressEntity.ContentBean> contentBeanList = parkingAddressEntity.getContent();
                    if (contentBeanList.size() > 0) {
                        ParkingAddressEntity.ContentBean contentBean = contentBeanList.get(0);
                        Intent intent = new Intent(EParkingHistoryRecordActivity.this, FindParkingSpaceActivity.class);
                        intent.putExtra(FROMOTHER, true);
                        intent.putExtra(CHOICELAT, contentBean.getLatitude());
                        intent.putExtra(CHOICELON, contentBean.getLongitude());
                        intent.putExtra(CHOICEADDRESS, contentBean.getName());
                        startActivity(intent);
                    }
                } catch (Exception e) {

                }

                break;
        }

    }

    public void getDestinationCoordinate(String stationId) {
        ParkingHomeModel parkingHomeModel = new ParkingHomeModel(EParkingHistoryRecordActivity.this);
        parkingHomeModel.getParkingAddressInfor(0, stationId, this);
    }
}
