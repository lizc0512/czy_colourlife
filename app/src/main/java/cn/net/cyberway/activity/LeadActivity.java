package cn.net.cyberway.activity;


import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.external.viewpagerindicator.CirclePageIndicator;
import com.permission.AndPermission;
import com.tendcloud.tenddata.TCAgent;
import com.user.UserAppConst;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.adpter.LeadAdapter;

/**
 * 引导页
 */

public class LeadActivity extends BaseActivity implements View.OnClickListener {
    private ViewPager leadViewPager;
    private CirclePageIndicator indicator;
    private int[] imageBg = {R.drawable.zero, R.drawable.one, R.drawable.two, R.drawable.three};
    private TextView cancel;
    private int page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lead);
        //不清除缓存
        editor.putString(UserAppConst.APKNAME, "");
        editor.apply();
        SharedPreferences walletShare = getSharedPreferences("cache", 0);
        walletShare.edit().clear().apply();
        //清除缓存
//        String mobilePhone = shared.getString(UserAppConst.Colour_login_mobile, "");
//        editor.clear().apply();
//        editor.putString(UserAppConst.Colour_login_mobile, mobilePhone).apply();
        cancel = (TextView) findViewById(R.id.cancel);
        cancel.setOnClickListener(this);
        leadViewPager = (ViewPager) findViewById(R.id.lead_viewPager);
        indicator = (CirclePageIndicator) findViewById(R.id.indicator);
        leadViewPager.setAdapter(new LeadAdapter(this, imageBg));
        indicator.setViewPager(leadViewPager, 0);
        leadViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int arg0) {
                page = arg0;
                indicator.setCurrentItem(arg0);
                if (arg0 == imageBg.length - 1) {
                    indicator.setVisibility(View.GONE);
                    cancel.setVisibility(View.GONE);
                    cancel.setText(getResources().getString(R.string.lead_go_open));
                    cancel.setTextColor(getResources().getColor(R.color.blue));
                } else {
                    indicator.setVisibility(View.VISIBLE);
                    cancel.setVisibility(View.VISIBLE);
                    cancel.setText(getResources().getString(R.string.lead_jump));
                    cancel.setTextColor(getResources().getColor(R.color.gray));
                }
                if (page > 0 && page < imageBg.length - 1) {
                    TCAgent.onPageStart(getApplicationContext(), "引导页第" + page + "页");
                    TCAgent.onPageEnd(getApplicationContext(), "引导页第" + (page - 1) + "页");
                }
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
            }
        });
        int currentSDK_INT=Build.VERSION.SDK_INT;
        if (currentSDK_INT>= Build.VERSION_CODES.M) {
            List<String> permissionList = new ArrayList<>();
            if (currentSDK_INT<29){
                permissionList.add(Manifest.permission.READ_PHONE_STATE);
            }
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            permissionList.add(Manifest.permission.CAMERA);
            permissionList.add(Manifest.permission.RECORD_AUDIO);
            if (AndPermission.hasPermission(LeadActivity.this, permissionList)) {

            } else {
                if (currentSDK_INT<29){  //android10.0以下
                    AndPermission.with(this)
                            .permission(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.READ_PHONE_STATE,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                            .start();
                }else{
                    AndPermission.with(this)
                            .permission(Manifest.permission.ACCESS_FINE_LOCATION,
                                    Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.RECORD_AUDIO)
                            .start();
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        TCAgent.onPageStart(getApplicationContext(), "引导页第" + page + "页");
    }

    @Override
    protected void onPause() {
        super.onPause();
        TCAgent.onPageStart(getApplicationContext(), "引导页第" + page + "页");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(0, 0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.cancel: //直接跳转到主页面
                Intent intent = new Intent(this, MainActivity.class);
                if (page == imageBg.length - 1) {
                    intent.putExtra(MainActivity.CALLSETTTING, true);
                } else {
                    intent.putExtra(MainActivity.CALLSETTTING, false);
                }
                editor.putBoolean(UserAppConst.IS_CHECK_UPDATE, false);
                editor.commit();
                startActivity(intent);
                overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                finish();
                break;
        }
    }
}
