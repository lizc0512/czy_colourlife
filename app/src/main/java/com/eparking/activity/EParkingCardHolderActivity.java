package com.eparking.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.activity.BaseFragmentActivity;
import com.cashier.adapter.ViewPagerAdapter;
import com.eparking.fragment.EparkingCarsFragment;
import com.eparking.fragment.EparkingLockFragment;
import com.eparking.fragment.EparkingMonthCardFragment;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.protocol.ParkingLockEntity;
import com.eparking.view.NoViewPager;
import com.eparking.view.keyboard.PopupHelper;
import com.feed.view.NoScrollListView;
import com.im.adapter.IMPopAdapter;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.TableLayoutUtils;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/10 16:48
 * @change
 * @chang time
 * @class describe   我的卡包
 */
public class EParkingCardHolderActivity extends BaseFragmentActivity implements View.OnClickListener {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private ImageView img_right;//更多
    private android.support.design.widget.TabLayout cardholder_tablayout;
    private NoViewPager cardholder_viewpager;
    private String[] tabTitleArray = null;
    private List<Fragment> fragmentList = new ArrayList<>();
    private EparkingLockFragment eparkingLockFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eparking_cardholder);
        czy_title_layout = findViewById(R.id.czy_title_layout);
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        tv_title = findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.title_my_cardwallet));
        tabTitleArray = getResources().getStringArray(R.array.parking_cardwallet_menu);
        img_right = (ImageView) findViewById(R.id.img_right);
        img_right.setVisibility(View.VISIBLE);
        img_right.setImageResource(R.drawable.img_home_more);
        cardholder_tablayout = findViewById(R.id.cardholder_tablayout);
        cardholder_viewpager = findViewById(R.id.cardholder_viewpager);
        imageView_back.setOnClickListener(this);
        img_right.setOnClickListener(this);
        for (int i = 0; i < tabTitleArray.length; i++) {
            cardholder_tablayout.addTab(cardholder_tablayout.newTab().setText(tabTitleArray[i]));
        }
        cardholder_tablayout.setTabMode(TabLayout.MODE_FIXED);
        cardholder_tablayout.setSelectedTabIndicatorHeight(4);
        cardholder_tablayout.setSelectedTabIndicatorColor(Color.parseColor("#27a2f0"));
        cardholder_tablayout.setTabTextColors(Color.parseColor("#333b46"), Color.parseColor("#27a2f0"));
        cardholder_tablayout.setTabGravity(TabLayout.GRAVITY_FILL);
        fragmentList.add(new EparkingCarsFragment());
        fragmentList.add(new EparkingMonthCardFragment());
        eparkingLockFragment = new EparkingLockFragment();
        fragmentList.add(eparkingLockFragment);
        cardholder_viewpager.setNeedScroll(true);
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(), this, fragmentList, tabTitleArray);
        cardholder_viewpager.setAdapter(adapter);
        cardholder_viewpager.setOffscreenPageLimit(fragmentList.size());
        cardholder_tablayout.setupWithViewPager(cardholder_viewpager);
        TableLayoutUtils.reflex(cardholder_tablayout);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, imageView_back, tv_title);
        ParkingActivityUtils.getInstance().addActivity(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.img_right:  //绑定车辆和申请月卡
                initPopup();
                break;
        }
    }

    private PopupWindow popupWindow;

    private void initPopup() {
        PopupHelper.dismissFromActivity(EParkingCardHolderActivity.this);
        View contentview = LayoutInflater.from(EParkingCardHolderActivity.this).inflate(R.layout.pop_eparking_home, null);
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
        contentArr = getResources().getStringArray(R.array.parking_cardbag_rightmenu);
        popLv.setAdapter(new IMPopAdapter(EParkingCardHolderActivity.this, contentArr, imageArr));
        popLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                popupWindow.dismiss();
                Intent intent = new Intent(EParkingCardHolderActivity.this, classArr[position]);
                startActivity(intent);
            }
        });
    }

    public void openateCarLock(ParkingLockEntity.ContentBean parkingLockContentBean) {
        if (null != eparkingLockFragment) {
            eparkingLockFragment.operateLockStatus(parkingLockContentBean);
        }
    }

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getWindow().setAttributes(lp);
    }

    private String[] contentArr = null;
    private int[] imageArr = {R.drawable.eparking_img_down_bindcar, R.drawable.eparking_img_down_yueka};

    private Class[] classArr = {CarsBindActivity.class, MonthCardApplyActivity.class};

}
