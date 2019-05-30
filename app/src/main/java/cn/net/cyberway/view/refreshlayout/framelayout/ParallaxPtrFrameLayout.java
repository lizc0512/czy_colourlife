/*
 * AUTHOR：Yan Zhenjie
 *
 * DESCRIPTION：create the File, and add the content.
 *
 * Copyright © ZhiMore. All Rights Reserved
 *
 */
package cn.net.cyberway.view.refreshlayout.framelayout;

import android.content.Context;
import android.util.AttributeSet;

import cn.net.cyberway.view.refreshlayout.PtrClassicDefaultFooter;
import cn.net.cyberway.view.refreshlayout.PtrFrameLayout;

/**
 * Created by Yan Zhenjie on 2016/11/21.
 */
public class ParallaxPtrFrameLayout extends PtrFrameLayout
{

    private ParallaxHeader mParallaxHeader;
    private PtrClassicDefaultFooter footer =null;




    public ParallaxPtrFrameLayout(Context context) {
        super(context);
        initViews();
    }

    public ParallaxPtrFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initViews();
    }

    public ParallaxPtrFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initViews();
    }

    private void initViews() {
        mParallaxHeader = new ParallaxHeader(getContext());
        footer=new  PtrClassicDefaultFooter(getContext());
        setHeaderView(mParallaxHeader);
        setFooterView(footer);
        addPtrUIHandler(mParallaxHeader);
        addPtrUIHandler(footer);
    }

    public ParallaxHeader getHeader() {
        return mParallaxHeader;
    }


}
