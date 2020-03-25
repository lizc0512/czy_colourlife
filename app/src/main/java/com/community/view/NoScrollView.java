package com.community.view;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.ScrollView;

/**
 * author:yuansk
 * cretetime:2020/3/19
 * desc:
 **/
public class NoScrollView extends ScrollView {
    RecyclerView recyclerView;

    public NoScrollView(Context context) {
        this(context, null);
    }

    public NoScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }


    public void setRecyclerView(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
    }

    int downY;
    int moveY = 0;
    MotionEvent downEvent;

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //如果RecyclerView
        if (recyclerView != null) {
            switch (ev.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    downEvent = ev;
                    downY = (int) ev.getX();
                    moveY = 0;
                    break;
                case MotionEvent.ACTION_MOVE:
                    int moveY = (int) ev.getY() - downY;
                    if (Math.abs(moveY) > 20) {
                        //表示滑动
                        if (moveY > 0) {
                            //向下滑动,如果recyclerView还没有显示则拦截事件
                            if (getScrollY() < recyclerView.getTop()) {
                                return true;
                            }
                        } else {
                            //向上滑动
                            if (getScrollY() == recyclerView.getTop()) {
                                //如果第一个item已经显现才拦截事件
                                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();
                                if (layoutManager.findFirstVisibleItemPosition() == 0) {
                                    return true;
                                }
                            }
                        }
                    }
                    break;
            }
        }

        return super.onInterceptTouchEvent(ev);
    }
}

