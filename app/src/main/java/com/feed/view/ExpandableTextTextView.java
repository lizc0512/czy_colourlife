/*
 * Copyright (C) 2011 The Android Open Source Project
 * Copyright 2014 Manabu Shimobe
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.feed.view;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Build;
import android.text.Layout;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.text.style.URLSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.WebViewActivity;

import cn.net.cyberway.R;


/**
 * 文字超过一定行数之后，展开收起
 * @author gzc
 * git hub原始地址
 * https://github.com/Manabu-GT/ExpandableTextView
 */
public class ExpandableTextTextView extends LinearLayout implements View.OnClickListener {

	private static final String TAG = "ExpandableTextView";

    // The default number of lines;
    private static final int MAX_COLLAPSED_LINES = 8;

    // The default animation duration
    private static final int DEFAULT_ANIM_DURATION = 300;

    // The default alpha value when the animation starts
    private static final float DEFAULT_ANIM_ALPHA_START = 0.7f;

    protected TextView mTv;

    protected ImageButton mButton; // Button to expand/collapse
    protected TextView mButtonText; // text to expand/collapse

    private View mExpandFootView;

    private boolean mRelayout;

    private boolean mCollapsed = true; // Show short version as default.

    private int mCollapsedHeight;

    private int mMaxTextHeight;

    private int mMaxCollapsedLines;

    private int mMarginBetweenTxtAndBottom;

//    private Drawable mExpandDrawable;
//
//    private Drawable mCollapseDrawable;

    private String mExpandText;
    private String mCollapseText;

    private int mAnimationDuration;

    @SuppressWarnings("unused")
	private float mAnimAlphaStart;

    public static final int ClickAll = 0;
    public static final int ClickFooter = 1;

    // when in listview , use this map to save collapsed status
    private SparseBooleanArray mConvertTextCollapsedStatus = new SparseBooleanArray();
    //
//    private CellListener    cellListener;
    private int mPosition;

    private int mClickType;
    private Context mContext;
    public ExpandableTextTextView(Context context) {
        super(context);
    }

    public ExpandableTextTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
        mContext=context;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ExpandableTextTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
        mContext=context;
    }

    @Override
    public void onClick(View view) {
        if (mExpandFootView.getVisibility() != View.VISIBLE) {
            return;
        }

        mCollapsed = !mCollapsed;
        if (mConvertTextCollapsedStatus != null) {
        	mConvertTextCollapsedStatus.put(mPosition, mCollapsed);
//            cellListener.callback(mPosition, mCollapsed);
        }
        Log.i(TAG, " put postion " + mPosition + " " + mCollapsed + " this " + this);

//        mButton.setImageDrawable(mCollapsed ? mExpandDrawable : mCollapseDrawable);
        mButtonText.setText(mCollapsed?mExpandText:mCollapseText);

        Animation animation;
        
        Log.i(TAG, "click on position " + mPosition + " collapsed " + mCollapsed);

        if (mCollapsed) {//折叠
            animation = new ExpandCollapseAnimation(this, getHeight(), mCollapsedHeight);
        } else {
            animation = new ExpandCollapseAnimation(this, getHeight(), getHeight() +
                    mMaxTextHeight - mTv.getHeight());
        }

        animation.setFillAfter(true);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }
            @Override
            public void onAnimationEnd(Animation animation) {
            	clearAnimation();
            }
            @Override
            public void onAnimationRepeat(Animation animation) { }
        });

        clearAnimation();
        startAnimation(animation);
    }

    @SuppressLint("DrawAllocation") @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
    	
    	Log.d(TAG, " onMeasure ");
        // If no change, measure and return
        if (!mRelayout || getVisibility() == View.GONE) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        mRelayout = false;

        mExpandFootView.setVisibility(View.GONE);
        mTv.setMaxLines(Integer.MAX_VALUE);

        // Measure
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        // If the text fits in collapsed mode, we are done.
        if (mTv.getLineCount() <= mMaxCollapsedLines) {
            return;
        }

        // Saves the text height w/ max lines
        mMaxTextHeight = getTextViewRealHeight(mTv);
        Log.i(TAG, " mMaxTextHeight" + mMaxTextHeight);

        // Doesn't fit in collapsed mode. Collapse text view as needed. Show
        // button.
        if (mCollapsed) {
            mTv.setMaxLines(mMaxCollapsedLines);
        }
//        mButton.setVisibility(View.VISIBLE);
        mExpandFootView.setVisibility(View.VISIBLE);

        // Re-measure with new setup
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        if (mCollapsed) {
            // Gets the margin between the TextView's bottom and the ViewGroup's bottom
            mTv.post(new Runnable() {
                @Override
                public void run() {
                    mMarginBetweenTxtAndBottom = getHeight() - mTv.getHeight();
                }
            });
            // Saves the collapsed height of this ViewGroup
            mCollapsedHeight = getMeasuredHeight();
        }
        
    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ExpandableTextTextView);
        mMaxCollapsedLines = typedArray.getInt(R.styleable.ExpandableTextTextView_maxCollapsedLines, MAX_COLLAPSED_LINES);
        mAnimationDuration = typedArray.getInt(R.styleable.ExpandableTextTextView_animDuration, DEFAULT_ANIM_DURATION);
        mAnimAlphaStart = typedArray.getFloat(R.styleable.ExpandableTextTextView_animAlphaStart, DEFAULT_ANIM_ALPHA_START);
//        mExpandDrawable = typedArray.getDrawable(R.styleable.ExpandableTextTextView_expandDrawable);
//        mCollapseDrawable = typedArray.getDrawable(R.styleable.ExpandableTextTextView_collapseDrawable);
        mExpandText = typedArray.getString(R.styleable.ExpandableTextTextView_expandText);
        mCollapseText = typedArray.getString(R.styleable.ExpandableTextTextView_collapseText);
        mClickType = typedArray.getInt(R.styleable.ExpandableTextTextView_clickListenerType, ClickAll);

//        if (mExpandDrawable == null) {
//            mExpandDrawable = getResources().getDrawable(R.drawable.pack_up);
//        }
//        if (mCollapseDrawable == null) {
//            mCollapseDrawable = getResources().getDrawable(R.drawable.unfold);
//        }
        mExpandText = "全文";
        mCollapseText = "收起";
        typedArray.recycle();
    }

    private static boolean isPostHoneycomb() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    private void findViews() {
        mTv = (TextView) findViewById(R.id.expandable_text);
//        mButton = (ImageButton) findViewById(R.id.expand_collapse);
//        mButton.setImageDrawable(mCollapsed ? mExpandDrawable : mCollapseDrawable);
        mButtonText = (TextView) findViewById(R.id.expand_collapse_text);
        mButtonText.setText(mCollapsed ? mExpandText:mCollapseText);
        mExpandFootView = findViewById(R.id.expand_footer);
        
        if (mClickType == ClickAll) {
//        	mButton.setOnClickListener(this);
            mButtonText.setOnClickListener(this);
//        	setOnClickListener(this);
//        	mTv.setOnClickListener(this);
        	mExpandFootView.setOnClickListener(this);
        } else if (mClickType == ClickFooter) {
//        	mButton.setOnClickListener(this);
            mButtonText.setOnClickListener(this);
//        	mTv.setClickable(false);
        	setClickable(false);
        	mExpandFootView.setOnClickListener(this);
        }
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static void applyAlphaAnimation(View view, float alpha) {
        if (isPostHoneycomb()) {
            view.setAlpha(alpha);
        } else {
            AlphaAnimation alphaAnimation = new AlphaAnimation(alpha, alpha);
            // make it instant
            alphaAnimation.setDuration(0);
            alphaAnimation.setFillAfter(true);
            view.startAnimation(alphaAnimation);
        }
    }

    public void setText(String text) {
        mRelayout = true;
        if (mTv == null) {
            findViews();
        }
        mTv.setText(text);
        CharSequence sequence = mTv.getText();
        if (sequence instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) mTv.getText();
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();// should clear old spans
            for (URLSpan url : urls) {
                MyURLSpan myURLSpan = new MyURLSpan(url.getURL());
                style.setSpan(myURLSpan, sp.getSpanStart(url), sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            mTv.setText(style);
        }
//
        setVisibility(text.length() == 0 ? View.GONE : View.VISIBLE);
    }
    
    
    public void setConvertText(SparseBooleanArray convertStatus,int position,String text/*,CellListener cellListener*/) {
    	mConvertTextCollapsedStatus = convertStatus;
//        this.cellListener = cellListener;
    	boolean isCollapsed = mConvertTextCollapsedStatus.get(position, true);
    	Log.i(TAG, "setConvertText is collapsed " + isCollapsed + " position" + position + " this " + this);
    	mPosition = position;
    	clearAnimation();
    	mCollapsed = isCollapsed;
//    	if (mButton != null) {
//    		mButton.setImageDrawable(mCollapsed ? mExpandDrawable : mCollapseDrawable);
//    	}
        if(mButtonText != null){
            mButtonText.setText(mCollapsed ? mExpandText : mCollapseText);
        }
    	clearAnimation();
    	if (mCollapsed) {
    		if (mTv!=null){
    			mTv.setMaxLines(mMaxCollapsedLines);
    		}
    	} else {
    		if (mTv!=null) {
    			mTv.setMaxLines(Integer.MAX_VALUE);
    		}
    	}
    	this.getLayoutParams().height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
    	setText(text);
    	requestLayout();
    }
    

    public CharSequence getText() {
        if (mTv == null) {
            return "";
        }
        return mTv.getText();
    }

    protected class ExpandCollapseAnimation extends Animation {
        private final View mTargetView;
        private final int mStartHeight;
        private final int mEndHeight;

        public ExpandCollapseAnimation(View view, int startHeight, int endHeight) {
            mTargetView = view;
            mStartHeight = startHeight;
            mEndHeight = endHeight;
            
            setDuration(mAnimationDuration);
        }

        //动画时的高度
        @Override
        protected void applyTransformation(float interpolatedTime, Transformation t) {
            final int newHeight = (int)((mEndHeight - mStartHeight) * interpolatedTime + mStartHeight);
            mTv.setMaxHeight(newHeight - mMarginBetweenTxtAndBottom);
//            applyAlphaAnimation(mTv, mAnimAlphaStart + interpolatedTime * (1.0f - mAnimAlphaStart));
            mTargetView.getLayoutParams().height = newHeight;
            mTargetView.requestLayout();
        }

        @Override
        public void initialize( int width, int height, int parentWidth, int parentHeight ) {
            super.initialize(width, height, parentWidth, parentHeight);
        }

        @Override
        public boolean willChangeBounds( ) {
            return true;
        }
    }
    
    private int getTextViewRealHeight(TextView pTextView) {
        Layout layout = pTextView.getLayout();
        int desired = layout.getLineTop(pTextView.getLineCount());
        int padding = pTextView.getCompoundPaddingTop() + pTextView.getCompoundPaddingBottom();
        return desired + padding;
    }


    private  class MyURLSpan extends ClickableSpan {

        private String mUrl;

        MyURLSpan(String url) {
            mUrl = url;
        }

        @Override
        public void onClick(View widget) {
            Intent intent = new Intent(mContext, WebViewActivity.class);
            intent.putExtra(WebViewActivity.WEBURL, mUrl);
            mContext.startActivity(intent);
            ((Activity) mContext).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
        }

        @Override
        public void updateDrawState(TextPaint ds) {
            super.updateDrawState(ds);
            ds.setUnderlineText(false);
        }
    }
}