package com.eparking.helper;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;

/**
 * @name ${yuansk}
 * @class name：com.eparking.helper
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/11/15 14:44
 * @change
 * @chang time
 * @class describe
 */
public class MyRecyclerViewScrollListener extends RecyclerView.OnScrollListener {
    private Button btn_increase;
    private int distance;

    private boolean visible = true;


    public MyRecyclerViewScrollListener(Button btn_increase) {
        this.btn_increase = btn_increase;
    }

    @Override
    public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
        super.onScrollStateChanged(recyclerView, newState);


    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);
        if (distance < -ViewConfiguration.getTouchSlop() && !visible) { //显示fab
            btn_increase.setVisibility(View.VISIBLE);
            showFABAnimation(btn_increase);
            distance = 0;
            visible = true;
        } else if (distance > ViewConfiguration.getTouchSlop() && visible) { //隐藏
            btn_increase.setVisibility(View.GONE);
            hideFABAnimation(btn_increase);
            distance = 0;
            visible = false;
        }
        if ((dy > 0 && visible) || (dy < 0 && !visible))//向下滑并且可见  或者  向上滑并且不可见
            distance += dy;
    }


    public void showFABAnimation(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 1f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 1f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 1f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(400).start();
    }

    /**
     * by moos on 2017.8.21
     * func:隐藏fab的动画
     */
    public void hideFABAnimation(View view) {
        PropertyValuesHolder pvhX = PropertyValuesHolder.ofFloat("alpha", 0f);
        PropertyValuesHolder pvhY = PropertyValuesHolder.ofFloat("scaleX", 0f);
        PropertyValuesHolder pvhZ = PropertyValuesHolder.ofFloat("scaleY", 0f);
        ObjectAnimator.ofPropertyValuesHolder(view, pvhX, pvhY, pvhZ).setDuration(400).start();
    }
}
