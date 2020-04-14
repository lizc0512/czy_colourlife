package cn.net.cyberway.home.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.user.Utils.TokenUtils;

import cn.net.cyberway.R;

/**
 * 首页banner视频播放页面
 * <p>
 * Created by hxg on 2019/3/20 16:35
 */
public class BannerVideoActivity extends Activity implements View.OnClickListener {

    public static final String NUM = "num";
    public static final String URI = "uri";

    private ImageView user_top_view_back;
    private RelativeLayout rl_play;
    private LinearLayout ll_cover;
    private TextView tv_alert;
    private ImageView iv_full;
    private ImageView exo_play;
    private FrameLayout fl_player;
    private PlayerView video_player;
    private ProgressBar pb_loading;
    private SimpleExoPlayer player = null;
    private String uri;
    private boolean mIsFullScreen = false;
    private int initWidth;
    private int initHeight;
    private long resumePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_video);
        //创建play实例
        player = ExoPlayerFactory.newSimpleInstance(this);
        initView();
        initData();
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        video_player = findViewById(R.id.video_player);
        pb_loading = findViewById(R.id.pb_loading);
        iv_full = findViewById(R.id.iv_full);
        ll_cover = findViewById(R.id.ll_cover);
        tv_alert = findViewById(R.id.tv_alert);
        rl_play = findViewById(R.id.rl_play);
        fl_player = findViewById(R.id.fl_player);
        exo_play = findViewById(R.id.exo_play);
        exo_play.setVisibility(View.VISIBLE);
        user_top_view_back.setOnClickListener(this);
        rl_play.setOnClickListener(this);
        iv_full.setOnClickListener(this);
    }

    @SuppressLint("SetTextI18n")
    private void initData() {
        initWidth = fl_player.getLayoutParams().width;
        initHeight = fl_player.getLayoutParams().height;
        String num = getIntent().getStringExtra(NUM);
        uri = getIntent().getStringExtra(URI);

        if ("WIFI".equals(TokenUtils.getNetworkType(this))) {//有wifi情况
            ll_cover.setVisibility(View.GONE);
            initPlayer(uri);
        } else {
            tv_alert.setText("您正在使用非wifi网络，播放将消耗" + num + "m流量");
        }
    }

    private void initPlayer(String uri) {
        //用工厂设置资源
        Uri videoUri = Uri.parse(uri);
        DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "cn.net.cyberway"));//这里Util也是ExoPlayer自带的，应用名字随便写的
        ExtractorMediaSource mediaSource = new ExtractorMediaSource.Factory(dataSourceFactory).createMediaSource(videoUri);

        //将播放器Player和View关联起来
        video_player.setPlayer(player);

        //准备加载播放资源
        player.prepare(mediaSource);

        //还可以设置加载好了就播放
        player.setPlayWhenReady(true);

        //监听缓冲状态设置loading  ExoPlayer.STATE_BUFFERING = 2 缓冲中
        player.addListener(new Player.EventListener() {
            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                if (playWhenReady) {
                    if (ExoPlayer.STATE_BUFFERING == playbackState) {//缓冲状态
                        pb_loading.setVisibility(View.VISIBLE);
                    } else {
                        pb_loading.setVisibility(View.GONE);
                    }
                }
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (player != null && player.getCurrentPosition() > 0) {
            player.setPlayWhenReady(true);
            player.seekTo(resumePosition);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (player != null && player.getPlayWhenReady()) {
            resumePosition = Math.max(0, player.getContentPosition());
            player.setPlayWhenReady(false);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.rl_play:
                ll_cover.setVisibility(View.GONE);
                initPlayer(uri);
                break;
            case R.id.iv_full:
                if (!mIsFullScreen) {//点击全屏
                    mIsFullScreen = true;
                    iv_full.setImageDrawable(getResources().getDrawable(R.drawable.icon_full_exit));//针对src
                    setupFullScreen();
                } else {//点击退出全屏
                    mIsFullScreen = false;
                    iv_full.setImageDrawable(getResources().getDrawable(R.drawable.icon_full));
                    setupUnFullScreen();
                }
                break;
        }
    }

    /**
     * 设置全屏
     */
    public void setupFullScreen() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        attrs.flags |= WindowManager.LayoutParams.FLAG_FULLSCREEN;
        getWindow().setAttributes(attrs);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    /**
     * 不全屏
     */
    private void setupUnFullScreen() {
        WindowManager.LayoutParams attrs = getWindow().getAttributes();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        attrs.flags &= (~WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setAttributes(attrs);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            fl_player.getLayoutParams().width = initWidth;
            fl_player.getLayoutParams().height = initHeight;
        } else {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            fl_player.getLayoutParams().width = FrameLayout.LayoutParams.MATCH_PARENT;
            fl_player.getLayoutParams().height = FrameLayout.LayoutParams.MATCH_PARENT;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != player) {
            player.release();
        }
    }
}
