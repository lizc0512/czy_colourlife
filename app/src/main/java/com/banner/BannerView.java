package com.banner;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Matrix;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.BeeFramework.view.NoScrollViewPager;
import com.banner.viewpager.BanerAdapter;
import com.banner.viewpager.BeeViewPager;
import com.external.viewpagerindicator.BeeCirclePageIndicator;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.ATTR;


/**
 * 横幅BannerView
 */
public class BannerView extends LinearLayout {
    private Context mContext;
    private NoScrollViewPager viewPager;
    private ImageView cursor;
    private BeeCirclePageIndicator bannerIndicator;
    private FrameLayout fl_indicator;
    private Timer bannerTimer;
    private BannerTimerTask bannerTimerTask;
    private Handler handler;
    private Long DALAY = 5000L;
    private Long PEROOD = 5000L;

    private int cursorWidth; //游标的宽度
    private int currIndex = 0;// 当前页卡编号

    private ArrayList<ATTR> list = new ArrayList<ATTR>();

    public BannerView(Context context) {
        super(context);
        mContext = context;
        bannerTimer = new Timer();
    }

    public BannerView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        bannerTimer = new Timer();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public BannerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
        bannerTimer = new Timer();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        if(viewPager == null) {
            viewPager = (NoScrollViewPager) findViewById(R.id.news_banner_pager);
        }
        if(cursor == null) {
            cursor = (ImageView) findViewById(R.id.news_banner_cursor);
        }

        if(bannerIndicator == null) {
            bannerIndicator = (BeeCirclePageIndicator)findViewById(R.id.indicator);
        }


        if(fl_indicator==null){
            fl_indicator=(FrameLayout)findViewById(R.id.fl_indicator);
        }
        viewPager.setAdapter(new BanerAdapter(mContext, list));

        if(list.size() ==1){
            fl_indicator.setVisibility(View.INVISIBLE);
        } else{
            fl_indicator.setVisibility(VISIBLE);
            bannerIndicator.setViewPager(viewPager,0);
            bannerIndicator.requestLayout();
        }

        viewPager.setOnPageChangeListener(new BeeViewPager.OnPageChangeListener() {
            @Override
            public void onPageSelected(int arg0) {
                // TODO Auto-generated method stub
                if (arg0 >= list.size()) {
                    int newPosition = arg0%list.size();
                    arg0 = newPosition;
                }
                Animation animation = null;
                if(currIndex > arg0) {
                    animation = new TranslateAnimation(cursorWidth * (arg0 + 1), cursorWidth * arg0, 0, 0);
                } else {
                    animation = new TranslateAnimation(cursorWidth * (arg0 - 1), cursorWidth * arg0, 0, 0);
                }

                animation.setFillAfter(true);// True:图片停在动画结束位置
                animation.setDuration(200);
                cursor.startAnimation(animation);
                currIndex = arg0;

                bannerIndicator.setCurrentItem(arg0);

            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
                if (arg0 == ViewPager.SCROLL_STATE_IDLE)
                {
                    bannerStartPlay();
                }
                else
                {
                    bannerStopPlay();
                }
            }
        });

        handler = new Handler() {
            public void handleMessage(Message msg) {
                viewPager.setCurrentItem(msg.what);
                super.handleMessage(msg);

            }

        };
        if(list.size()>0){
            bannerStartPlay();
        }else{
            bannerStopPlay();
        }
    }

    public int getCurrentPage()
    {
        return viewPager.getCurrentItem();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 初始化动画
     */
    private void InitImageView() {
        DisplayMetrics dm = getResources().getDisplayMetrics();
        cursorWidth = dm.widthPixels/list.size();

        android.view.ViewGroup.LayoutParams params = (android.view.ViewGroup.LayoutParams) cursor.getLayoutParams();
        params.width = cursorWidth;
        cursor.setLayoutParams(params);

        Animation animation = new TranslateAnimation(0, 0, 0, 0);
        animation.setFillAfter(true);// True:图片停在动画结束位置
        animation.setDuration(0);
        cursor.startAnimation(animation);

        Matrix matrix = new Matrix();
        matrix.postTranslate(0, 0);
        cursor.setImageMatrix(matrix);// 设置动画初始位置
        currIndex = 0;
    }

    //绑定数据
    public void bindData(ArrayList<ATTR> list) {
        this.list = list;
        if(list.size()>1){
           viewPager.setNoScroll(false);
        }else{
            viewPager.setNoScroll(true);
        }
        init();
    }

    //启动banner自动轮播
    public void bannerStartPlay() {
        if (bannerTimer != null) {
            if (bannerTimerTask != null)
                bannerTimerTask.cancel();
            bannerTimerTask = new BannerTimerTask();
            bannerTimer.schedule(bannerTimerTask, DALAY, PEROOD);//5秒后执行，每隔5秒执行一次
        }
    }

    //暂停banner自动轮播
    public void bannerStopPlay() {
        if (bannerTimerTask != null)
            bannerTimerTask.cancel();
        bannerTimerTask = null;
    }

    class BannerTimerTask extends TimerTask {
        @Override
        public void run() {
            Message msg = new Message();
            if (list.size() <= 1)
                return;
            msg.what = currIndex + 1;
            handler.sendMessage(msg);
        }

    }

}
