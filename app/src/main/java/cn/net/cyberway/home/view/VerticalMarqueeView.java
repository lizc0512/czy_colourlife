package cn.net.cyberway.home.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ViewFlipper;

import java.util.List;

import cn.csh.colourful.life.utils.ColourLifeRes;
import cn.csh.colourful.life.view.UPMarqueeView;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.view.layoutmanager
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/4/8 16:14
 * @change
 * @chang time
 * @class describe  垂直的跑马灯
 */

public class VerticalMarqueeView extends ViewFlipper {

    private Context mContext;
    private boolean isSetAnimDuration = true;
    private int interval = 5000;
    /**
     * 动画时间
     */
    private int animDuration = 500;

    public VerticalMarqueeView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        this.mContext = context;
        setFlipInterval(interval);
        Animation animIn = AnimationUtils.loadAnimation(mContext, ColourLifeRes.anim("anim_marquee_in"));
        if (isSetAnimDuration) animIn.setDuration(animDuration);
        setInAnimation(animIn);
        Animation animOut = AnimationUtils.loadAnimation(mContext, ColourLifeRes.anim("anim_marquee_out"));
        if (isSetAnimDuration) animOut.setDuration(animDuration);
        setOutAnimation(animOut);
    }


    /**
     * 设置循环滚动的View数组
     *
     * @param views
     */
    public void setViews(final List<View> views) {
        if (views == null || views.size() == 0) return;
        removeAllViews();
        for ( int i = 0; i < views.size(); i++) {
            final int position=i;
            //设置监听回调
            views.get(i).setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onItemClickListener != null) {
                        if (views.size()>0){
                            onItemClickListener.onItemClick(position, views.get(position));
                        }
                    }
                }
            });
            addView(views.get(i));
        }
        if (views.size() > 1) {
            startFlipping();
        }
    }

    /**
     * 点击
     */
    private UPMarqueeView.OnItemClickListener onItemClickListener;

    /**
     * 设置监听接口
     * @param onItemClickListener
     */
    public void setOnItemClickListener(UPMarqueeView.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    /**
     * item_view的接口
     */
    public interface OnItemClickListener {
        void onItemClick(int position, View view);
    }
}