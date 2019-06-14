package com.feed.activity;

import android.content.Context;
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

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.feed.FeedConstant;
import com.feed.adapter.FeedAdapter;
import com.feed.model.FeedCustomerModel;
import com.feed.model.FeedModel;
import com.feed.protocol.FeedCommentDeleteApi;
import com.feed.protocol.FeedDeleteApi;
import com.feed.protocol.REPLY;
import com.feed.protocol.VerFeedCommentApi;
import com.feed.protocol.VerFeedCustomerApi;
import com.feed.protocol.VerFeedLikeApi;
import com.feed.protocol.VerFeedUnlikeApi;
import com.im.activity.IMInviteRegisterActivity;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.protocol.USER;

import java.util.ArrayList;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 个人的朋友圈页面
 * Created by insthub on 2016/1/14.
 */
public class PersonalFeedActivity extends BaseActivity implements View.OnClickListener, IXListViewListener, HttpApiResponse {
    public static final String USERID = "userid";
    private FrameLayout czy_title_layout;
    private TextView mTitle;
    private ImageView mBack;
    private XListView mListView;
    private LinearLayout mInputView;
    private EditText mEditText;
    private TextView mSend;
    private View mEmptyView;
    private LinearLayout mEmptyLayout;
    private ImageView mEmptyImg;
    private TextView mEmptyText;
    private TextView mEmptyFeed;
    private TextView mEmptyActivity;
    private FeedAdapter mFeedAdapter;
    private FeedCustomerModel mCustomerModel;
    private FeedModel mFeedModel;
    private int mPosition;
    private String mFeedId;
    private String mToUserId;
    private USER mToUser;
    private String mCommentId;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_feed);
        initView();
    }

    /**
     * 初始化各种信息
     */
    private void initView() {
        userId = getIntent().getStringExtra(USERID);
        String userName = getIntent().getStringExtra(IMInviteRegisterActivity.USERNAME);
        mCustomerModel = new FeedCustomerModel(this);
        mFeedModel = new FeedModel(this);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        mTitle = (TextView) findViewById(R.id.user_top_view_title);
        mBack = (ImageView) findViewById(R.id.user_top_view_back);
        mListView = (XListView) findViewById(R.id.community_listview);
        mEditText = (EditText) findViewById(R.id.feed_comment_edittext);
        mInputView = (LinearLayout) findViewById(R.id.feed_comment_view);
        mSend = (TextView) findViewById(R.id.feed_comment_submit);
        mListView.setXListViewListener(this, 0);
        mListView.setAdapter(null);
        initEmptyView();
        mBack.setOnClickListener(this);
        mSend.setOnClickListener(this);
        mListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    closeKeyBoard();
                }
                return false;
            }
        });
        if (TextUtils.isEmpty(userName)) {
            mTitle.setText(getResources().getString(R.string.instant_linli_circle));
        } else {
            mTitle.setText(userName);
        }
        mListView.startHeaderRefresh();
        ThemeStyleHelper.onlyFrameTitileBar(getApplicationContext(), czy_title_layout, mBack, mTitle);
    }


    /**
     * 空页面
     */
    private void initEmptyView() {
        mEmptyView = LayoutInflater.from(this).inflate(R.layout.empty_layout, null);
        mEmptyLayout = (LinearLayout) mEmptyView.findViewById(R.id.empty_layout);
        mEmptyActivity = (TextView) mEmptyView.findViewById(R.id.empty_activity);
        mEmptyFeed = (TextView) mEmptyView.findViewById(R.id.empty_feed);
        mEmptyImg = (ImageView) mEmptyView.findViewById(R.id.empty_img);
        mEmptyText = (TextView) mEmptyView.findViewById(R.id.empty_text);
        mEmptyText.setText("TA最近都比较沉默~");
        mEmptyImg.setImageResource(R.drawable.c0_chat_empty);
        mEmptyFeed.setOnClickListener(this);
        mEmptyActivity.setOnClickListener(this);
        mListView.addHeaderView(mEmptyView);
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.feed_comment_submit:
                String content = mEditText.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    closeKeyBoard();
                    mFeedModel.reply(this, mFeedId, content, mToUserId);
                } else {
                    ToastUtil.toastShow(this, "请输入内容");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(int id) {
        mCustomerModel.getCustomerFeedPro(this, userId);
    }

    @Override
    public void onLoadMore(int id) {
        mCustomerModel.getCustomerFeedNext(this, userId);
    }

    /**
     * 显示键盘
     */
    private void showInputView() {
        mInputView.setVisibility(View.VISIBLE);
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        Message msg = new Message();
        msg.what = FeedConstant.FEED_SHOW_INPUT;
        EventBus.getDefault().post(msg);
    }

    /**
     * 关闭键盘
     */
    public void closeKeyBoard() {
        mInputView.setVisibility(View.GONE);
        mEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        Message msg = new Message();
        msg.what = FeedConstant.FEED_CLOSE_INPUT;
        EventBus.getDefault().post(msg);
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(VerFeedCustomerApi.class)) {
            mListView.stopLoadMore();
            mListView.stopRefresh();
            if (mCustomerModel.feedList != null && mCustomerModel.feedList.size() > 0) {
                mEmptyLayout.setVisibility(View.GONE);
                if (mFeedAdapter == null) {
                    mFeedAdapter = new FeedAdapter(this, mCustomerModel.feedList);
                    mListView.setAdapter(mFeedAdapter);
                } else {
                    mFeedAdapter.setData(mCustomerModel.feedList);
                }
                if (mCustomerModel.isMore) {
                    mListView.setPullLoadEnable(true);
                } else {
                    mListView.setPullLoadEnable(false);
                }
            } else {
                mEmptyLayout.setVisibility(View.VISIBLE);
            }
        } else if (api.getClass().equals(VerFeedLikeApi.class)) {
            mCustomerModel.feedList.get(mPosition).like_status = 1;
            mCustomerModel.feedList.get(mPosition).like_total += 1;
            com.user.protocol.USER user = UserAppConst.getUser(this);
            mCustomerModel.feedList.get(mPosition).like.add(0, user);
            mFeedAdapter.setData(mCustomerModel.feedList);
        } else if (api.getClass().equals(VerFeedUnlikeApi.class)) {
            mCustomerModel.feedList.get(mPosition).like_status = 0;
            mCustomerModel.feedList.get(mPosition).like_total -= 1;
            int user_id = shared.getInt(UserAppConst.Colour_User_id, 0);
            for (int i = 0; i < mCustomerModel.feedList.get(mPosition).like.size(); i++) {
                if (mCustomerModel.feedList.get(mPosition).like.get(i).id == user_id) {
                    mCustomerModel.feedList.get(mPosition).like.remove(i);
                    break;
                }
            }
            mFeedAdapter.setData(mCustomerModel.feedList);
        } else if (api.getClass().equals(VerFeedCommentApi.class)) {
            Message msg = new Message();
            msg.what = UserMessageConstant.COMMUNITY_REFRESH;//刷新邻里页面
            EventBus.getDefault().post(msg);

            for (int i = 0; i < mCustomerModel.feedList.size(); i++) {
                if (mCustomerModel.feedList.get(i).id.equals(mFeedId)) {
                    REPLY reply = new REPLY();
                    reply.from_user = UserAppConst.getUser(this);
                    reply.id = mFeedModel.commentId;
                    reply.content = mEditText.getText().toString();
                    if (mToUser != null) {
                        reply.to_user = mToUser;
                    } else {
                        reply.to_user = new USER();
                    }
                    mCustomerModel.feedList.get(i).replys.add(reply);
                    mCustomerModel.feedList.get(i).reply_total += 1;
                    mFeedAdapter.setData(mCustomerModel.feedList);
                    break;
                }
            }
            mFeedId = null;
            mToUserId = null;
            mToUser = null;
            mEditText.setHint("");
            mEditText.setText("");
        } else if (api.getClass().equals(FeedDeleteApi.class)) {

        } else if (api.getClass().equals(FeedCommentDeleteApi.class)) {


        }
    }

    public void onEvent(Object event) {
        Message message = (Message) event;
        if (message.what == FeedConstant.FEED_REPLY) {//评论
            if (!shared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                mToUserId = null;
                mToUser = null;
                mEditText.setHint("");
                int position = message.arg1;
                mFeedId = mCustomerModel.feedList.get(position).id;
                showInputView();

            }
        } else if (message.what == FeedConstant.FEED_LIKE) {//赞
            if (!shared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                mPosition = message.arg1;
                mFeedModel.like(this, mCustomerModel.feedList.get(mPosition).id);
            }
        } else if (message.what == FeedConstant.FEED_UNLIKE) {//取消赞
            if (!shared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                mPosition = message.arg1;
                mFeedModel.unlike(this, mCustomerModel.feedList.get(mPosition).id);
            }
        } else if (message.what == FeedConstant.COMMENT_REPLY_USER) {//回复
            if (!shared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                Bundle bundle = message.getData();
                mToUserId = String.valueOf(bundle.getInt("userId"));
                mFeedId = bundle.getString("mFeedId");
                String userName = bundle.getString("userName");
                mToUser = (USER) bundle.getSerializable("user");
                mEditText.setHint("回复：" + userName);

                showInputView();

            }
        } else if (message.what == FeedConstant.FEED_DELETE_COMMENT) {//删除评论
            if (!shared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                mFeedId = String.valueOf(message.arg1);
                mCommentId = (String) message.obj;
                //删除操作在CommunityFragment中操作
            }
        } else if (message.what == FeedConstant.FEED_DELETE) {//删除feed
            if (!shared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                mPosition = message.arg1;
                mFeedId = mCustomerModel.feedList.get(mPosition).id;
                //删除操作在CommunityFragment中操作
            }
        } else if (message.what == UserMessageConstant.DELETE_FEED) {//删除Feed成功之后刷新
            //CommunityFragment中删除成功发消息，删除集合中的数据
            for (int i = 0; i < mCustomerModel.feedList.size(); i++) {
                if (mCustomerModel.feedList.get(i).id.equals(mFeedId)) {
                    mCustomerModel.feedList.remove(i);
                    mFeedAdapter.setData(mCustomerModel.feedList);
                    break;
                }
            }
        } else if (message.what == UserMessageConstant.DELETE_COMMENT) {//删除评论成功之后刷新
            //CommunityFragment中删除成功发消息，删除集合中的数据
            for (int i = 0; i < mCustomerModel.feedList.size(); i++) {
                if (mCustomerModel.feedList.get(i).id.equals(mFeedId)) {
                    ArrayList<REPLY> replys = mCustomerModel.feedList.get(i).replys;
                    for (int j = 0; j < replys.size(); j++) {
                        if (replys.get(j).id.equals(mCommentId)) {
                            replys.remove(j);
                            mFeedAdapter.setData(mCustomerModel.feedList);
                            break;
                        }
                    }
                }
            }
        }

    }

    private void Login() {
        ToastUtil.toastShow(this, "请先登录");
        LinkParseUtil.parse(this, "", "");
    }
}
