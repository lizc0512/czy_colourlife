package com.community.view;

import android.content.Context;
import android.graphics.Canvas;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.webkit.WebView;

/**
 * author:yuansk
 * cretetime:2020/3/19
 * desc:
 **/
public class WrapWebView extends WebView {
    private int lastContentHeight = 0;
    private static final int MSG_CONTENT_CHANGE = 1;
    private onContentChangeListener onContentChangeListener = null;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MSG_CONTENT_CHANGE:
                    if (onContentChangeListener != null) {
                        onContentChangeListener.onContentChange();
                    }
                    break;
            }
        }
    };


    public WrapWebView(Context context) {
        this(context, null);
    }

    public WrapWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (getContentHeight() != lastContentHeight) {
            handler.sendEmptyMessage(MSG_CONTENT_CHANGE);
            lastContentHeight = getContentHeight();
        }

    }

    public void setOnContentChangeListener(WrapWebView.onContentChangeListener onContentChangeListener) {
        this.onContentChangeListener = onContentChangeListener;
    }

    /**
     * 监听内容高度发生变化
     */
    public interface onContentChangeListener {
        void onContentChange();
    }
}
