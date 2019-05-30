package com.Neighborhood.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Message;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.AttributeSet;
import android.util.SparseBooleanArray;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.Utils;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.BeeFramework.view.MyDialog;
import com.external.eventbus.EventBus;
import com.feed.FeedConstant;
import com.feed.protocol.ENUM_FEED_TYPE;
import com.feed.protocol.FEED;
import com.feed.protocol.FeedDeleteApi;
import com.feed.view.ExpandableTextTextView;
import com.feed.view.FeedImageGridView;
import com.feed.view.FeedPopup;
import com.feed.view.FeedReplyView;
import com.nohttp.utils.GlideImageLoader;
import com.user.UserAppConst;
import com.user.protocol.USER;

import java.util.regex.Pattern;

import cn.net.cyberway.R;

/**
 * Created by HX_CHEN on 2016/4/15.
 */
public class ActiveView extends LinearLayout implements FeedPopup.OnItemOnClickListener, HttpApiResponse {
    public Context 			mContext;
    private ImageView mAvatar;
    protected TextView mName;
    private LinearLayout		mNameLayout;
    private TextView 			mTime;
    private TextView			mDelete;
    private TextView			mActivity;
    private ExpandableTextTextView mContent;

    private ImageView 			mOperateBtn;

    private LinearLayout			mFeedLikeGridLayout;
    private TextView				mFeedLikeName;
    private FeedReplyView mFeedReplyView;
    private LinearLayout		mFeedActivity;
    private TextView			mFeedActivityName;
    private TextView			mFeedActivityTime;
    private TextView			mFeedActivitySite;
    private FeedImageGridView mFeedImgGridview;
    private LinearLayout		mCommentLayout;
    private View mCommentLine;
    private FEED mFeed;
    private int 				mPosition;
    private FeedPopup 			mFeedPopup;
    private SparseBooleanArray mConvertTextCollapsedStatus = new SparseBooleanArray();

    public ActiveView(Context context) {
        super(context);
        mContext = context;
    }

    public ActiveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public ActiveView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        mContext = context;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    protected void init() {

        if(mAvatar == null) {
            mAvatar = (ImageView) findViewById(R.id.feed_avatar);
        }
        if(mNameLayout ==null){
            mNameLayout = (LinearLayout) findViewById(R.id.feed_name_layout);
        }
        if(mName == null) {
            mName = (TextView) findViewById(R.id.feed_name);
        }
        if(mTime == null) {
            mTime = (TextView) findViewById(R.id.feed_time);
        }
        if(mDelete == null){
            mDelete = (TextView) findViewById(R.id.feed_delete);
        }
        if(mContent == null) {
            mContent = (ExpandableTextTextView) findViewById(R.id.feed_content);
        }

        if(mOperateBtn == null) {
            mOperateBtn = (ImageView) findViewById(R.id.feed_operate_btn);
        }
        if(mFeedLikeGridLayout == null) {
            mFeedLikeGridLayout = (LinearLayout) findViewById(R.id.feed_like_layout);
        }
        if(mFeedLikeName == null){
            mFeedLikeName = (TextView) findViewById(R.id.feed_like_name);
        }

        if(mFeedReplyView == null) {
            mFeedReplyView = (FeedReplyView) findViewById(R.id.feed_reply_view);
        }

        if(mFeedActivity == null){
            mFeedActivity = (LinearLayout) findViewById(R.id.feed_item_activity);
        }
        if(mCommentLayout == null){
            mCommentLayout = (LinearLayout) findViewById(R.id.feed_comment_layout);
        }
        if(mCommentLine == null){
            mCommentLine =  findViewById(R.id.feed_comment_line);
        }
        if(mFeedActivityName == null){
            mFeedActivityName = (TextView) findViewById(R.id.feed_item_activity_name);
        }
        if(mFeedActivityTime == null){
            mFeedActivityTime = (TextView) findViewById(R.id.feed_item_activity_time);
        }
        if(mFeedActivitySite == null){
            mFeedActivitySite = (TextView) findViewById(R.id.feed_item_activity_site);
        }
        if(mActivity == null){
            mActivity = (TextView) findViewById(R.id.feed_activity);
        }
        if(mFeedImgGridview == null){
            mFeedImgGridview = (FeedImageGridView) findViewById(R.id.feed_image_grid);
        }

        mFeedPopup = new FeedPopup(mContext, Utils.dip2px(mContext, 140), Utils.dip2px(mContext, 35));
        mFeedPopup.setItemOnClickListener(this);
    }


    //绑定数据
    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void bindData(final FEED feed, final int position) {
        mFeed = feed;
        mPosition = position;
        if(feed.user.id == UserAppConst.getUser(mContext).id){
            mDelete.setVisibility(VISIBLE);
        }else{
            mDelete.setVisibility(GONE);
        }
        if(feed.type == ENUM_FEED_TYPE.NORMAL.value()){
            //动态
            mFeedActivity.setVisibility(GONE);
            mActivity.setVisibility(GONE);
            if(!TextUtils.isEmpty(feed.normal_feed_content.content)){
                mContent.setConvertText(mConvertTextCollapsedStatus,position,feed.normal_feed_content.content);
            }else{
                mContent.setVisibility(GONE);
            }
            ViewGroup.LayoutParams params =mContent.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            mContent.setLayoutParams(params);
            //发表的图片
            if(feed.normal_feed_content.photos!= null && feed.normal_feed_content.photos.size() >0){
                mFeedImgGridview.setVisibility(VISIBLE);
                mFeedImgGridview.bindData(feed.normal_feed_content.photos,true);
            }else{
                mFeedImgGridview.setVisibility(GONE);
            }

        }else{
            //活动
            mFeedActivity.setVisibility(VISIBLE);
            mActivity.setVisibility(VISIBLE);
            mActivity.setText(feed.community.name);
            mFeedImgGridview.setVisibility(GONE);
            mFeedActivitySite.setText(feed.activity_feed_content.location);
            mFeedActivityTime.setText(feed.activity_feed_content.start_time);
            if(!TextUtils.isEmpty(feed.activity_feed_content.content)){
                mContent.setText(feed.activity_feed_content.content);
            }else{
                mContent.setVisibility(GONE);
            }
            mContent.requestLayout();

        }
        mTime.setText(TimeUtil.timeAgoInt(mFeed.creationtime));

        //赞
        if(feed.like != null && feed.like.size() > 0){
            mFeedLikeGridLayout.setVisibility(VISIBLE);
            initLikeLayout();
            mCommentLine.setVisibility(VISIBLE);
            mCommentLayout.setVisibility(VISIBLE);
        }else{
            mFeedLikeGridLayout.setVisibility(GONE);
            mCommentLine.setVisibility(GONE);
            mCommentLayout.setVisibility(GONE);
        }
        //回复
        if(feed.replys != null && feed.replys.size() > 0){
            FeedConstant.FEE_REPLYD_DELETED_FORM = 1;//用于删除回复的判断
            mFeedReplyView.setVisibility(VISIBLE);
            mFeedReplyView.bindData(feed.replys,feed.id);
            mCommentLayout.setVisibility(VISIBLE);
        }else{
            mFeedReplyView.setVisibility(GONE);
            mCommentLine.setVisibility(GONE);
        }

        //头像
        if(mFeed.user != null && mFeed.user.portrait != null) {
            GlideImageLoader.loadActiveImageDisplay(mContext,mFeed.user.portrait,mAvatar,R.drawable.d2_head_default,R.drawable.d2_head_default,10);
        }
        //点击进入个人页面
        mAvatar.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                intoOtherPeopelDetail(mFeed.user);
            }
        });
        //点击进入个人页面
        mName.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                intoOtherPeopelDetail(mFeed.user);
            }
        });
        if(mFeed.user != null) {
            mName.setText(mFeed.user.nickname);
            if(feed.type == ENUM_FEED_TYPE.ACTIVITY.value()){
                mName.getPaint().setFakeBoldText(true);
                int spec = MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED);
                mName.measure(spec, spec);
                mActivity.measure(spec, spec);
                WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
                int width1 = wm.getDefaultDisplay().getWidth();
                ViewGroup.LayoutParams layoutParams = mName.getLayoutParams();
                ViewGroup.LayoutParams layoutParamsT = mActivity.getLayoutParams();
                int width = mName.getMeasuredWidth();
                int widthT = mActivity.getMeasuredWidth();
                layoutParamsT.width = widthT;
                mActivity.setLayoutParams(layoutParamsT);
                if(width >= width1- widthT - Utils.dip2px(mContext, 85)){
                    layoutParams.width = width1- widthT - Utils.dip2px(mContext, 85);
                    mName.setLayoutParams(layoutParams);
                }

            }

        }

        mOperateBtn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                mFeedPopup.setAnimationStyle(R.style.cricleBottomAnimation);
                mFeedPopup.show(v, mPosition, mFeed.like_status);
            }
        });


        mDelete.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                final MyDialog dialog = new MyDialog(mContext,"确定删除吗？");
                dialog.positive.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Message msg = new Message();
                        msg.what = FeedConstant.ACTIVE_FEED_DELETED;
                        msg.arg1 = position;
                        EventBus.getDefault().post(msg);
                    }
                });
                dialog.negative.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });

    }

    //赞
    private void initLikeLayout() {

        StringBuffer buffer = new StringBuffer();
        for (int i=0;i<mFeed.like.size();i++){
            buffer.append(mFeed.like.get(i).nickname).append("，");
        }
        int lastIndex = buffer.lastIndexOf("，");
        String name = buffer.substring(0,lastIndex);
        SpannableStringBuilder ssb = new SpannableStringBuilder(name);
        int length =0;
        int start;
        int end;
        for (int i=0;i<mFeed.like.size();i++){
            USER user = mFeed.like.get(i);
            start = length;
            if(i != mFeed.like.size()-1){
                end = start + user.nickname.length()+1;
            }else{
                end = start + user.nickname.length();
            }

            final int finalI = i;
            ClickableSpan what1 = new ClickableSpan() {

                @Override
                public void onClick(View widget) {
                    intoOtherPeopelDetail(mFeed.like.get(finalI));
                }

                @Override
                public void updateDrawState(TextPaint ds) {
                    ds.setColor(mContext.getResources().getColor(R.color.blue_text_color_feed));
                    ds.setFakeBoldText(true);
                    ds.setUnderlineText(false); //去掉下划线
                }
            };
            ssb.setSpan(what1, start, end, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
            length = end;
        }
        mFeedLikeName.setText(ssb);
        mFeedLikeName.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onItemClick(int index, int position, int status) {
        if(index == 0) {//赞
            if(status == 1) {
                Message msg = new Message();
                msg.what = FeedConstant.ACTIVE_FEED_UNLIKED;
                msg.arg1 = position;
                EventBus.getDefault().post(msg);
            } else {
                Message msg = new Message();
                msg.what = FeedConstant.ACTIVE_FEED_LIKED;
                msg.arg1 = position;
                EventBus.getDefault().post(msg);
            }
        } else if(index == 1) {//回复
            Message msg = new Message();
            msg.what = FeedConstant.ACTIVE_FEED_REPLYD;
            msg.arg1 = position;
            EventBus.getDefault().post(msg);
        }
    }

    @Override
    public void onDismiss() {
        mOperateBtn.setImageResource(R.drawable.icon_neighborhood_pj);
    }

    private void intoOtherPeopelDetail(USER user){
        Message msg = Message.obtain();
        msg.what = FeedConstant.FEED_USER_INFOR;
        msg.obj=user.id;
        EventBus.getDefault().post(msg);
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if(api.getClass().equals(FeedDeleteApi.class)){

        }
    }
}
