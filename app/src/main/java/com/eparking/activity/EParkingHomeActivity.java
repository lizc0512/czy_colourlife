package com.eparking.activity;

import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.adapter.EparkingHomeAdapter;
import com.eparking.adapter.EparkingServiceAdapter;
import com.eparking.adapter.ParkingMenuAdapter;
import com.eparking.helper.ConstantKey;
import com.eparking.model.ParkingHomeModel;
import com.eparking.model.ParkingOperationModel;
import com.eparking.protocol.EparkingServiceEntity;
import com.eparking.protocol.HomeFunctionListEntity;
import com.eparking.protocol.ParkingHomeEntity;
import com.eparking.view.keyboard.PopupHelper;
import com.feed.view.NoScrollListView;
import com.nohttp.utils.GsonUtils;
import com.tmall.ultraviewpager.UltraViewPager;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.csh.colourful.life.view.recycleview.WrapHeightGridLayoutManager;
import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/9 16:44
 * @change
 * @chang time
 * @class describe   e停首页
 */
public class EParkingHomeActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private ImageView img_right;
    private RecyclerView rv_service;
    private UltraViewPager ultra_viewpager;
    private TextView tv_home_service;
    private List<ParkingHomeEntity.ContentBean> homeParkingList = new ArrayList<>();
    private List<HomeFunctionListEntity.ContentBean> funcMenuList = new ArrayList<>();
    private List<EparkingServiceEntity.ContentBean> serviceEntityList = new ArrayList<>();
    private ParkingHomeModel parkingHomeModel;
    private ParkingOperationModel parkingOperationModel;
    private EparkingServiceAdapter serviceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eparking_home);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_parking));
        img_right = (ImageView) findViewById(R.id.img_right);
        img_right.setVisibility(View.VISIBLE);
        img_right.setImageResource(R.drawable.img_home_more);
        img_right.setOnClickListener(this);
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        imageView_back.setOnClickListener(this);
        ultra_viewpager = findViewById(R.id.ultra_viewpager);
        rv_service = findViewById(R.id.rv_service);
        tv_home_service = findViewById(R.id.tv_home_service);
        ultra_viewpager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
        String homeDataCache = shared.getString(ConstantKey.EPARKINGHOMEDATA, "");
        boolean homeLoading = true;
        if (!TextUtils.isEmpty(homeDataCache)) {
            try {
                homeLoading = false;
                ParkingHomeEntity parkingHomeEntity = GsonUtils.gsonToBean(homeDataCache, ParkingHomeEntity.class);
                homeParkingList.clear();
                homeParkingList.addAll(parkingHomeEntity.getContent());
            } catch (Exception e) {

            }
        }
        updateHomeData();
        String homeService = shared.getString(ConstantKey.EPARKINGHOMESERVICE, "");
        if (!TextUtils.isEmpty(homeService)) {
            try {
                tv_home_service.setVisibility(View.VISIBLE);
                EparkingServiceEntity eparkingServiceEntity = GsonUtils.gsonToBean(homeService, EparkingServiceEntity.class);
                serviceEntityList.addAll(eparkingServiceEntity.getContent());
            } catch (Exception e) {

            }
        }

        GridLayoutManager gridLayoutManager = new WrapHeightGridLayoutManager(EParkingHomeActivity.this, 2);
        //设置布局管理器
        rv_service.setLayoutManager(gridLayoutManager);
        serviceAdapter = new EparkingServiceAdapter(serviceEntityList);
        rv_service.setAdapter(serviceAdapter);
        String homeMenuCache = shared.getString(ConstantKey.EPARKINGHOMEMENU, "");
        if (!TextUtils.isEmpty(homeMenuCache)) {
            try {
                HomeFunctionListEntity homeFunctionListEntity = GsonUtils.gsonToBean(homeMenuCache, HomeFunctionListEntity.class);
                funcMenuList.clear();
                funcMenuList.addAll(homeFunctionListEntity.getContent());
            } catch (Exception e) {

            }
        }
        serviceAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                if (i >= 0) {
                    String linkUrl = serviceEntityList.get(i).getJump_url();
                    if (!TextUtils.isEmpty(linkUrl)) {
                        LinkParseUtil.parse(EParkingHomeActivity.this, linkUrl, "");
                    }
                }
            }
        });
        parkingHomeModel = new ParkingHomeModel(EParkingHomeActivity.this);
        parkingOperationModel = new ParkingOperationModel(EParkingHomeActivity.this);
        parkingHomeModel.getHomeParkingInfor(0, homeLoading, this);
        parkingHomeModel.getHomeFunctionList(2, this);
        parkingHomeModel.getHistoryCarNumber(3, this);
    }

    private void updateHomeData() {
        PagerAdapter adapter = new EparkingHomeAdapter(EParkingHomeActivity.this, true, homeParkingList);
        if (null != ultra_viewpager.getAdapter()) {
            ultra_viewpager.refresh();
        }
        ultra_viewpager.setAdapter(adapter);
        ultra_viewpager.setMultiScreen(0.9f);
        ultra_viewpager.refresh();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.img_right:
                if (funcMenuList.size() > 0) {
                    initPopup();
                }
                break;
        }
    }

    private PopupWindow popupWindow;

    private void initPopup() {
        PopupHelper.dismissFromActivity(EParkingHomeActivity.this);
        if (popupWindow != null) {
            popupWindow.dismiss();
        }
        View contentview = LayoutInflater.from(EParkingHomeActivity.this).inflate(R.layout.pop_eparking_home, null);
        popupWindow = new PopupWindow(contentview,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT,
                true);
        setBackgroundAlpha(0.8f);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.showAsDropDown(img_right, 0, 0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        NoScrollListView popLv = contentview.findViewById(R.id.pop_lv);
        popLv.setAdapter(new ParkingMenuAdapter(EParkingHomeActivity.this, funcMenuList));
        popLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                LinkParseUtil.parse(EParkingHomeActivity.this, funcMenuList.get(position).getRedirect(), "");
            }
        });
    }

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }


    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                try {
                    ParkingHomeEntity parkingHomeEntity = GsonUtils.gsonToBean(result, ParkingHomeEntity.class);
                    homeParkingList.clear();
                    homeParkingList.addAll(parkingHomeEntity.getContent());
                    updateHomeData();
                } catch (Exception e) {
                    editor.putString(ConstantKey.EPARKINGHOMEDATA, "").apply();
                    ToastUtil.toastShow(EParkingHomeActivity.this, e.getMessage());
                }
                parkingOperationModel.getCarAndStationList(7, false, this);
                break;
            case 1:
                try {
                    serviceEntityList.clear();
                    EparkingServiceEntity eparkingServiceEntity = GsonUtils.gsonToBean(result, EparkingServiceEntity.class);
                    serviceEntityList.addAll(eparkingServiceEntity.getContent());
                } catch (Exception e) {

                }
                tv_home_service.setVisibility(View.VISIBLE);
                serviceAdapter.notifyDataSetChanged();
                break;
            case 2:
                try {
                    HomeFunctionListEntity homeFunctionListEntity = GsonUtils.gsonToBean(result, HomeFunctionListEntity.class);
                    funcMenuList.clear();
                    funcMenuList.addAll(homeFunctionListEntity.getContent());
                } catch (Exception e) {

                }
                parkingHomeModel.getHomeAvertData(1, this);
                parkingHomeModel.getHomeServicePhone(5, EParkingHomeActivity.this);
                break;
            case 5:
                if ("close".equalsIgnoreCase(operateStatus)) {
                    operationContentBean.setStatus("close");
                    clickTextView.setText(getResources().getString(R.string.lock_down));
                } else {
                    operationContentBean.setStatus("open");
                    clickTextView.setText(getResources().getString(R.string.lock_up));
                }
                ultra_viewpager.getAdapter().notifyDataSetChanged();
                break;
            case 6:
                if (!TextUtils.isEmpty(result)) {
                    if ("lock".equalsIgnoreCase(operateStatus)) {
                        operationContentBean.setStatus("close");
                        clickImageView.setImageResource(R.drawable.eparking_img_lockcar);
                    } else {
                        operationContentBean.setStatus("open");
                        clickImageView.setImageResource(R.drawable.eparking_img_unlockcar);
                    }
                    ultra_viewpager.getAdapter().notifyDataSetChanged();
                }
                break;
        }
    }

    private TextView clickTextView;
    private ParkingHomeEntity.ContentBean operationContentBean;
    private ImageView clickImageView;
    private String operateStatus;

    public void openateCarLock(TextView textView, ParkingHomeEntity.ContentBean contentBean) {
        clickTextView = textView;
        operationContentBean = contentBean;
        String lockCode = contentBean.getEtcode();
        if (!"close".equalsIgnoreCase(contentBean.getStatus())) {
            operateStatus = "close";
        } else {
            operateStatus = "open";
        }
        ParkingOperationModel parkingOperationModel = new ParkingOperationModel(EParkingHomeActivity.this);
        parkingOperationModel.carLockOpenation(5, lockCode, operateStatus, this);
    }

    public void carLockController(ImageView iv_monthcard_sign, ParkingHomeEntity.ContentBean contentBean) {
        clickImageView = iv_monthcard_sign;
        operationContentBean = contentBean;
        if (!"close".equalsIgnoreCase(contentBean.getStatus())) {
            operateStatus = "lock";
        } else {
            operateStatus = "unlock";
        }
        ParkingOperationModel parkingOperationModel = new ParkingOperationModel(EParkingHomeActivity.this);
        parkingOperationModel.carLockHandle(6, contentBean.getPlate(), operateStatus, this);
    }
}
