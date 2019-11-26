package com.about.activity;

import android.app.DownloadManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.activity.WebViewActivity;
import com.update.activity.UpdateVerSion;
import com.update.service.UpdateService;
import com.user.UserAppConst;

import cn.net.cyberway.R;

import static com.update.activity.UpdateVerSion.HASNEWCODE;
import static com.update.activity.UpdateVerSion.SAVEVERSION;

/**
 * Created by HENXIAN_C on 2016/1/8.
 */
public class AboutActivity extends BaseActivity implements View.OnClickListener {
    private FrameLayout czy_title_layout;
    private RelativeLayout ll_agreement;//用户协议
    private RelativeLayout ll_terms;    //隐私条款
    private RelativeLayout ll_update;   //检查更新
    private TextView tv_czy_version;//彩之云版本号
    private TextView tv_version;  //有更新显示的红点
    private String versionName;  //软件版本名
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private String newversion;
    private SharedPreferences mShared;
    private UpdateVerSion updateVerSion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_layout);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.about_colourlife));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        ll_agreement = (RelativeLayout) findViewById(R.id.ll_agreement);
        ll_terms = (RelativeLayout) findViewById(R.id.ll_terms);
        ll_update = (RelativeLayout) findViewById(R.id.ll_update);
        tv_czy_version = (TextView) findViewById(R.id.tv_czy_version);
        tv_version = (TextView) findViewById(R.id.tv_version);

        ll_agreement.setOnClickListener(this);
        ll_terms.setOnClickListener(this);
        ll_update.setOnClickListener(this);
        imageView_back.setOnClickListener(this);
        mShared = getSharedPreferences(UserAppConst.USERINFO, 0);

        updateVerSion = new UpdateVerSion();
        //获取软件版本号
        versionName = updateVerSion.getVersionName(this);
        tv_czy_version.setText(getResources().getString(R.string.app_name) + updateVerSion.showVersionName(versionName));
        //检查更新
        newversion = mShared.getString(SAVEVERSION, versionName);
        boolean hasNewVersion = mShared.getBoolean(HASNEWCODE, false);
        if (hasNewVersion) {//表示有更新
            tv_version.setVisibility(View.VISIBLE);//显示红点，提示有版本更新
        } else {
            tv_version.setVisibility(View.INVISIBLE);
        }
        String downloadedVersion = mShared.getString(UserAppConst.DOWNLOADERVERSION, versionName);
        int checkResult = downloadedVersion.compareTo(newversion);
        if (checkResult < 0) {  //下载未安装的版本小于最新版本
            editor.putString(UserAppConst.APKNAME, "").apply();
        }
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, imageView_back, tv_title);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent();
        switch (v.getId()) {
            case R.id.ll_agreement:
                intent.setClass(this, WebViewActivity.class);
                String url = "https://m.colourlife.com/xieyiApp";
                intent.putExtra(WebViewActivity.WEBTITLE, "用户协议");
                intent.putExtra(WebViewActivity.WEBURL, url);
                startActivity(intent);
                break;
            case R.id.ll_terms:
                intent.setClass(this, WebViewActivity.class);
                String url2 = "https://m.colourlife.com/xieyiApp/protocol";
                intent.putExtra(WebViewActivity.WEBTITLE, "隐私条款");
                intent.putExtra(WebViewActivity.WEBURL, url2);
                startActivity(intent);
                break;
            case R.id.ll_update:
                long downId = shared.getLong(UserAppConst.UPDATE, -1L);
                DownloadManager manager = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
                int status = UpdateService.getDownloadStatus(manager, downId);
                if (downId != -1L && (status == DownloadManager.STATUS_RUNNING || status == DownloadManager.STATUS_PAUSED)) {
                    ToastUtil.toastShow(getApplicationContext(), UpdateService.getBytesAndStatus(manager, downId));
                } else {
                    if (status == DownloadManager.STATUS_SUCCESSFUL) {
                        String appName = shared.getString(UserAppConst.APKNAME, "");
                        if (TextUtils.isEmpty(appName)) {
//                            updateVerSion.getNewVerSion("2", true, AboutActivity.this);
                            updateVerSion.getNewVerSion(AboutActivity.this, false,false);
                        } else {
                            UpdateService.installCzyAPP(AboutActivity.this, appName);
                        }
                    } else {
//                        updateVerSion.getNewVerSion("2", true, AboutActivity.this);
                         updateVerSion.getNewVerSion(AboutActivity.this, false,false);
                    }
                }
                break;
            case R.id.user_top_view_back:
                AboutActivity.this.finish();
                break;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == 101) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            } else {
                Toast.makeText(this, "请到手机设置中允许修改系统权限", Toast.LENGTH_SHORT).show();
            }
        } else if (requestCode == 3000) {
            boolean hasInstallPermission = false;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (hasInstallPermission) {
                    String appName = shared.getString(UserAppConst.APKNAME, "");
                    UpdateService.installCzyAPP(AboutActivity.this, appName);
                } else {
                    ToastUtil.toastShow(AboutActivity.this, "未知应用程序安装权限未开启");
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 3000) {
            boolean hasInstallPermission = false;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                hasInstallPermission = getPackageManager().canRequestPackageInstalls();
                if (hasInstallPermission) {
                    String appName = shared.getString(UserAppConst.APKNAME, "");
                    UpdateService.installCzyAPP(AboutActivity.this, appName);
                } else {
                    ToastUtil.toastShow(AboutActivity.this, "未知应用程序安装权限未开启");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
