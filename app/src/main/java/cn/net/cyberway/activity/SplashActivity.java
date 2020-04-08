package cn.net.cyberway.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.BeeFramework.model.NewHttpResponse;
import com.nohttp.utils.GlideImageLoader;
import com.update.activity.UpdateVerSion;
import com.user.UserAppConst;

import org.json.JSONException;
import org.json.JSONObject;

import cn.net.cyberway.R;
import cn.net.cyberway.model.SplashModel;
import cn.net.cyberway.model.ThemeModel;


/**
 * 欢迎页
 */
public class SplashActivity extends Activity implements HttpApiResponse, NewHttpResponse {

    private SplashModel splashModel;
    private SharedPreferences shared;
    private ImageView ad;
    private TextView cancel;
    private boolean autoJump = true;
    private ThemeModel themeModel;
    private String linkUrl = "";
    private int delayTime = 1000;
    private TimeCount timeCount = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        shared = getSharedPreferences(UserAppConst.USERINFO, 0);
        splashModel = new SplashModel(this);
        themeModel = new ThemeModel(this);
        splashModel.getStartImage(1, this);
        splashModel.getOneKeyShow();
        themeModel.getTheme(0, this);
        ad = (ImageView) findViewById(R.id.ad);
        cancel = (TextView) findViewById(R.id.cancel);
        /**
         *读取本地启屏广告,如果有就展示,没有跳转到主界面或者引导页面
         */
        String splashCathe = shared.getString(UserAppConst.Colour_SPLASH_CACHE, "");
        if (!TextUtils.isEmpty(splashCathe)) {//有缓存
            try {
                JSONObject jsonObject = new JSONObject(splashCathe);
                String startImage = jsonObject.optString("img");
                int time_stop = jsonObject.optInt("time_stop");
                delayTime = time_stop * 1000;
                linkUrl = jsonObject.optString("url");
                if (!TextUtils.isEmpty(startImage)) {
                    cancel.setVisibility(View.VISIBLE);//显示加载的广告显示页面
                    GlideImageLoader.loadImageDisplay(getApplicationContext(), startImage, ad);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        ad.setOnClickListener(v -> {
            if (!TextUtils.isEmpty(linkUrl)) {
                autoJump = false;
                timeCount.cancel();
                redirectto(linkUrl);
            }
        });
        cancel.setOnClickListener(v -> {
            autoJump = false;
            timeCount.cancel();
            redirectto("");
        });
        timeCount = new TimeCount(delayTime, 1000);
        timeCount.start();

    }


    /**
     * 如果第一次运行或者及版本更新就跳转到引导页，否则跳转到主界面
     */
    private void redirectto(String jumpUrl) {
        if (UpdateVerSion.getVersionCode(this) > shared.getInt(UpdateVerSion.SAVEVERSIONCODE, 1)) {
            Intent intent = new Intent(this, LeadActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            finish();
        } else {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra(MainActivity.JUMPOTHERURL, jumpUrl);
            startActivity(intent);
            overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
            finish();
        }
        if (null != timeCount) {
            timeCount.cancel();
        }
    }

    /**
     * 屏蔽返回键
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_HOME:
            case KeyEvent.KEYCODE_BACK:
            case KeyEvent.KEYCODE_CALL:
            case KeyEvent.KEYCODE_SYM:
            case KeyEvent.KEYCODE_VOLUME_DOWN:
            case KeyEvent.KEYCODE_VOLUME_UP:
            case KeyEvent.KEYCODE_STAR:
                return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    @Override
    public void OnHttpResponse(HttpApi api) {

    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:

                break;
            case 1:
                if (TextUtils.isEmpty(result)) {
                    redirectto("");
                }

                break;
        }
    }

    /**
     * 定义一个倒计时的内部类
     */
    class TimeCount extends CountDownTimer {
        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            cancel.setText(getResources().getString(R.string.lead_jump) + "(" + 0 + "s)");
            if (autoJump) {
                redirectto("");
            }
            cancel();
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            cancel.setText(getResources().getString(R.string.lead_jump) + "(" + millisUntilFinished / 1000 + "s)");
        }
    }
}
