package com.Neighborhood.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.BeeFrameworkApp;
import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.Neighborhood.Adapter.ActiveAdapter;
import com.Neighborhood.model.ActiveListModel;
import com.Neighborhood.protocol.FeedActivityfeedlistApi;
import com.Neighborhood.protocol.FeedActivityfeedlistRequest;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.feed.FeedConstant;
import com.feed.activity.ReleaseActivity;
import com.feed.protocol.ACTIVITY_CATEGORY;
import com.feed.protocol.FEED;
import com.feed.protocol.FeedCommentDeleteApi;
import com.feed.protocol.FeedDeleteApi;
import com.feed.protocol.REPLY;
import com.feed.protocol.VerFeedCommentApi;
import com.feed.protocol.VerFeedLikeApi;
import com.feed.protocol.VerFeedUnlikeApi;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.protocol.USER;

import java.util.ArrayList;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 邻里活动列表页面
 * Created by HX_CHEN on 2016/4/14.
 */
public class ActiveDirectoryActivity extends BaseActivity implements HttpApiResponse, View.OnClickListener, IXListViewListener {
    private LinearLayout ll_select;//小区选择
    private XListView xListView;//活动列表
    private ImageView imageView_head;//活动类型照片
    private ImageView back;//返回/箭头
    private TextView tv_title_select;//小区
    private ActiveAdapter mFeedAdapter;
    private SharedPreferences mShared;
    private String mCommunityName;
    private String mCommunityId = "03b98def-b5bd-400b-995f-a9af82be01da";
    private int mPosition;
    private String mFeedId;//动态id
    private String mCommentId;//评论id
    private String mToUserId;
    private USER mToUser;
    private String mCityRegionId;//当前城市Id
    private String mProvinceId;//当前省份ID
    private String mDISTRICTID;//当前行政区ID
    private FEED mFeed;

    private EditText mEditText;
    private LinearLayout mInputView;
    private TextView mSend;
    private ActiveListModel mActiveModel;
    // 区域城市数据库查询工具
    private int mActiveType;//
    private View view;
    private View mEmptyView;
    private LinearLayout mEmptyLayout;
    private ImageView mEmptyImg;
    private TextView mEmptyText;
    private TextView mEmptyFeed;
    private TextView mEmptyActivity;

    private FeedActivityfeedlistRequest request = new FeedActivityfeedlistRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_active_layout);
        mActiveModel = new ActiveListModel(this);
        mShared = getSharedPreferences(UserAppConst.USERINFO, 0);
        mFeed = (FEED) getIntent().getSerializableExtra("feed");
        mActiveType = Integer.parseInt(mFeed.activity_feed_content.activity_categoty.id);

        mCityRegionId = mFeed.region.city.id;
        mProvinceId = mFeed.region.province.id;
        mDISTRICTID = mFeed.region.district.id;
        mCommunityId = mFeed.community.id;
        mCommunityName = mFeed.community.name;
        request.community_id = mCommunityId;
        request.province_id = mProvinceId;
        request.activity_type_id = mActiveType;
        request.district_id = mDISTRICTID;
        request.city_id = mCityRegionId;

        ll_select = (LinearLayout) findViewById(R.id.ll_select);
        FrameLayout czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        back = (ImageView) findViewById(R.id.user_top_view_back);
        xListView = (XListView) findViewById(R.id.xlist_view);
        mEditText = (EditText) findViewById(R.id.feed_comment_edittext);
        mInputView = (LinearLayout) findViewById(R.id.feed_comment_view);
        mSend = (TextView) findViewById(R.id.feed_comment_submit);
        mSend.setOnClickListener(this);
        tv_title_select = (TextView) findViewById(R.id.user_top_view_title);
        ll_select.setOnClickListener(this);
        back.setOnClickListener(this);
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, back, tv_title_select);
        view = LayoutInflater.from(this).inflate(R.layout.activelist_item, null);
        imageView_head = (ImageView) view.findViewById(R.id.image);
        if (!TextUtils.isEmpty(mFeed.activity_feed_content.activity_categoty.photo)) {
            ImageLoader.getInstance().displayImage(mFeed.activity_feed_content.activity_categoty.photo, imageView_head, BeeFrameworkApp.optionsImage);
        }
        imageView_head.setOnClickListener(this);
        xListView.addHeaderView(view);
        initEmptyView();
        tv_title_select.setText(mCommunityName);
        xListView.setAdapter(null);
        xListView.startHeaderRefresh();
        xListView.setPullRefreshEnable(true);
        xListView.setXListViewListener(this, 0);
        xListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    closeKeyBoard();
                }
                return false;
            }
        });
        mActiveModel.getActiveListPro(this, request);
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().register(this);
        }
    }

    private ArrayList<FEED> feedinfo = new ArrayList<FEED>();
    ;

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(FeedActivityfeedlistApi.class)) {
            FeedActivityfeedlistApi feedActivityfeedlistApi = (FeedActivityfeedlistApi) api;
            xListView.stopLoadMore();
            xListView.stopRefresh();
            feedinfo.clear();
            feedinfo.addAll(feedActivityfeedlistApi.response.feeds);
            if (null != feedinfo && feedinfo.size() > 0) {
                mEmptyLayout.setVisibility(View.GONE);
                xListView.loadMoreShow();
                if (mFeedAdapter == null) {
                    mFeedAdapter = new ActiveAdapter(this, feedinfo);
                    xListView.setAdapter(mFeedAdapter);
                } else {
                    mFeedAdapter.setData(feedinfo);
                    mFeedAdapter.notifyDataSetChanged();
                }
                if (mActiveModel.isMore) {
                    xListView.setPullLoadEnable(true);
                } else {
                    xListView.setPullLoadEnable(false);
                }
            } else {
                xListView.loadMoreHide();
                mEmptyLayout.setVisibility(View.VISIBLE);
                closeKeyBoard();
            }
        } else if (api.getClass().equals(VerFeedLikeApi.class)) {
            mActiveModel.feedList.get(mPosition).like_status = 1;
            mActiveModel.feedList.get(mPosition).like_total += 1;
            USER user = UserAppConst.getUser(this);
            mActiveModel.feedList.get(mPosition).like.add(user);
            mFeedAdapter.setData(mActiveModel.feedList);
        } else if (api.getClass().equals(VerFeedUnlikeApi.class)) {
            mActiveModel.feedList.get(mPosition).like_status = 0;
            mActiveModel.feedList.get(mPosition).like_total -= 1;
            int user_id = mShared.getInt(UserAppConst.Colour_User_id, 0);
            for (int i = 0; i < mActiveModel.feedList.get(mPosition).like.size(); i++) {
                if (mActiveModel.feedList.get(mPosition).like.get(i).id == user_id) {
                    mActiveModel.feedList.get(mPosition).like.remove(i);
                    break;
                }
            }
            mFeedAdapter.setData(mActiveModel.feedList);
        } else if (api.getClass().equals(VerFeedCommentApi.class)) {
            for (int i = 0; i < mActiveModel.feedList.size(); i++) {
                if (mActiveModel.feedList.get(i).id.equals(mFeedId)) {
                    REPLY reply = new REPLY();
                    reply.from_user = UserAppConst.getUser(this);
                    reply.content = mEditText.getText().toString();
                    reply.id = mActiveModel.commentId;
                    if (mToUser != null) {
                        reply.to_user = mToUser;
                    } else {
                        reply.to_user = new USER();
                    }
                    mActiveModel.feedList.get(i).replys.add(reply);
                    mActiveModel.feedList.get(i).reply_total += 1;
                    mFeedAdapter.setData(mActiveModel.feedList);
                    mFeedAdapter.notifyDataSetChanged();
                    break;
                }
            }
            mFeedId = null;
            mToUserId = null;
            mToUser = null;
            mEditText.setHint("");
            mEditText.setText("");
        } else if (api.getClass().equals(FeedDeleteApi.class)) {
            for (int i = 0; i < mActiveModel.feedList.size(); i++) {
                if (mActiveModel.feedList.get(i).id.equals(mFeedId)) {
                    mActiveModel.feedList.remove(i);
                    mFeedAdapter.setData(mActiveModel.feedList);
                    ToastUtil.toastShow(this, "删除成功");
                    break;
                }
            }
            mFeedId = null;
            Message msg = new Message();
            msg.what = UserMessageConstant.DELETE_FEED;
            EventBus.getDefault().post(msg);
        } else if (api.getClass().equals(FeedCommentDeleteApi.class)) {
            for (int i = 0; i < mActiveModel.feedList.size(); i++) {
                if (mActiveModel.feedList.get(i).id.equals(mFeedId)) {
                    ArrayList<REPLY> replys = mActiveModel.feedList.get(i).replys;
                    for (int j = 0; j < replys.size(); j++) {
                        if (replys.get(j).id.equals(mCommentId)) {
                            replys.remove(j);
                            mFeedAdapter.setData(mActiveModel.feedList);
                            break;
                        }
                    }
                }
            }
            Message msg = new Message();
            msg.what = UserMessageConstant.DELETE_COMMENT;
            EventBus.getDefault().post(msg);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.image:
                if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                    Intent intent = new Intent(this, ActiveListActivity.class);
                    startActivityForResult(intent, 5);
                    overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                } else {
                    LinkParseUtil.parse(getApplicationContext(), "", "");
                }
                break;
            case R.id.feed_comment_submit:
                String content = mEditText.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    closeKeyBoard();
                    mActiveModel.reply(this, mFeedId, content, mToUserId);
                } else {
                    ToastUtil.toastShow(this, "请输入内容");
                }
                break;
        }
    }


    @Override
    public void onRefresh(int id) {
        mActiveModel.getActiveListPro(this, request);
    }

    @Override
    public void onLoadMore(int id) {
        mActiveModel.getActiveListNext(this, request);
    }

    // 关闭键盘
    public void closeKeyBoard() {
        mInputView.setVisibility(View.GONE);
        mEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        Message msg = new Message();
        msg.what = FeedConstant.ACTIVE_FEED_CLOSE_INPUTD;
        EventBus.getDefault().post(msg);
    }

    private void showInputView() {
        mInputView.setVisibility(View.VISIBLE);
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        Message msg = new Message();
        msg.what = FeedConstant.ACTIVE_FEED_SHOW_INPUTD;
        EventBus.getDefault().post(msg);
    }

    private void Login() {
        ToastUtil.toastShow(this, "请先登录");
        LinkParseUtil.parse(getApplicationContext(), "", "");
    }

    /**
     * 初始化空白页面
     */

    private void initEmptyView() {
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        mEmptyLayout = (LinearLayout) mEmptyView.findViewById(R.id.empty_layout);
        mEmptyActivity = (TextView) mEmptyView.findViewById(R.id.empty_activity);
        mEmptyFeed = (TextView) mEmptyView.findViewById(R.id.empty_feed);
        mEmptyImg = (ImageView) mEmptyView.findViewById(R.id.empty_img);
        mEmptyText = (TextView) mEmptyView.findViewById(R.id.empty_text);
        mEmptyView.setVisibility(View.VISIBLE);
        mEmptyLayout.setVisibility(View.GONE);
        mEmptyText.setText("最近大家都比较沉默~");
        mEmptyImg.setImageResource(R.drawable.c0_chat_empty);
        mEmptyFeed.setVisibility(View.INVISIBLE);
        mEmptyActivity.setVisibility(View.VISIBLE);
        mEmptyActivity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                    intent = new Intent(ActiveDirectoryActivity.this, ReleaseActivity.class);
                    startActivity(intent);
                } else {
                    LinkParseUtil.parse(getApplicationContext(), "", "");
                }
            }
        });
        xListView.addHeaderView(mEmptyView);
    }

    public void onEvent(Object event) {
        Message message = (Message) event;
        if (message.what == FeedConstant.ACTIVE_FEED_REPLYD) {
            if (!mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                mToUserId = null;
                mToUser = null;
                mEditText.setHint("");
                int position = message.arg1;
                mFeedId = mActiveModel.feedList.get(position).id;
                showInputView();

            }
        } else if (message.what == FeedConstant.ACTIVE_FEED_LIKED) {
            if (!mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                mPosition = message.arg1;
                mActiveModel.like(this, mActiveModel.feedList.get(mPosition).id);
            }
        } else if (message.what == FeedConstant.ACTIVE_FEED_UNLIKED) {
            if (!mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                mPosition = message.arg1;
                mActiveModel.unlike(this, mActiveModel.feedList.get(mPosition).id);
            }
        } else if (message.what == FeedConstant.ACTIVE_COMMENT_REPLY_USERD) {
            if (!mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                Bundle bundle = message.getData();
                mToUserId = String.valueOf(bundle.getInt("userId"));
                mFeedId = bundle.getString("mFeedId");
                String userName = bundle.getString("userName");
                mToUser = (USER) bundle.getSerializable("user");
                mEditText.setHint("回复" + userName + ":");

                showInputView();
            }
        } else if (message.what == UserMessageConstant.CREATE_FEED) {
            xListView.startHeaderRefresh();
        } else if (message.what == FeedConstant.ACTIVE_FEED_DELETED) {
            if (!mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                mPosition = message.arg1;
                mFeedId = mActiveModel.feedList.get(mPosition).id;
                mActiveModel.deleteFeed(this, mActiveModel.feedList.get(mPosition).id);
            }
        } else if (message.what == FeedConstant.ACTIVE_FEED_DELETE_COMMENTD) {
            if (!mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                mFeedId = (String) message.obj;
                mCommentId = String.valueOf(message.arg1);
                mActiveModel.deleteComment(this, mCommentId);
            }
        }
    }

    @Override
    protected void onDestroy() {
        if (EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().unregister(this);
        }
        super.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 5) {
            if (resultCode == 6) {
                ACTIVITY_CATEGORY activity_category = (ACTIVITY_CATEGORY) data.getSerializableExtra("data");
                if (!TextUtils.isEmpty(activity_category.photo)) {
                    ImageLoader.getInstance().displayImage(activity_category.photo, imageView_head, BeeFrameworkApp.optionsImage);
                }
                mActiveType = Integer.parseInt(activity_category.id);
                request.activity_type_id = mActiveType;
                xListView.startHeaderRefresh();
            }
        }
    }
}
