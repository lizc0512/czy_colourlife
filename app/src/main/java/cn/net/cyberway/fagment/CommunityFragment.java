package cn.net.cyberway.fagment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.BeeFramework.Utils.ThemeStyleHelper;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.BeeFramework.model.NewHttpResponse;
import com.Neighborhood.protocol.FeedAdlistApi;
import com.banner.BannerView;
import com.bumptech.glide.Glide;
import com.external.eventbus.EventBus;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.feed.FeedConstant;
import com.feed.activity.CreateNormalFeedActivity;
import com.feed.activity.FeedOrActivityActivity;
import com.feed.activity.ReleaseActivity;
import com.feed.adapter.FeedAdapter;
import com.feed.model.FeedModel;
import com.feed.protocol.FEED;
import com.feed.protocol.FeedCommentDeleteApi;
import com.feed.protocol.FeedDeleteApi;
import com.feed.protocol.REPLY;
import com.feed.protocol.VerFeedCommentApi;
import com.feed.protocol.VerFeedLikeApi;
import com.feed.protocol.VerFeedListApi;
import com.feed.protocol.VerFeedUnlikeApi;
import com.gem.GemConstant;
import com.gem.util.GemDialogUtil;
import com.im.activity.IMCustomerInforActivity;
import com.im.activity.IMFriendInforActivity;
import com.im.activity.IMUserSelfInforActivity;
import com.im.entity.UserIdInforEntity;
import com.im.helper.CacheFriendInforHelper;
import com.im.model.IMUploadPhoneModel;
import com.nohttp.utils.GsonUtils;
import com.tendcloud.tenddata.TCAgent;
import com.umeng.analytics.MobclickAgent;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.protocol.USER;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.ATTR;
import cn.net.cyberway.utils.CityCustomConst;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 邻里
 */
public class CommunityFragment extends Fragment implements IXListViewListener, HttpApiResponse, NewHttpResponse, View.OnClickListener {
    private static final int COMMUNITY_CHANGE = 1;
    private View mView;
    private XListView mListView;
    private LinearLayout mInputView;
    private EditText mEditText;
    private TextView mSend;
    private ImageView mCreateImg;//发表动态或者活动的按钮
    private TextView mSelectCommunity;

    private View mEmptyView;
    private LinearLayout mEmptyLayout;
    private ImageView mEmptyImg;
    private TextView mEmptyText;
    private TextView mEmptyFeed;
    private TextView mEmptyActivity;
    private FeedAdapter mFeedAdapter;//邻里的apapter
    private FeedModel mFeedModel;
    private SharedPreferences mShared;
    private SharedPreferences.Editor editor;
    private Context mContext;
    private String mCommunityName;
    private String mCommunityId = "03b98def-b5bd-400b-995f-a9af82be01da";

    private int mPosition;
    private String mFeedId;//动态id
    private String mCommentId;//评论id
    private String mToUserId;
    private USER mToUser;
    private String mRegionId;
    private String lat = "0";
    private String lon = "0";
    private ArrayList<ATTR> adlist = new ArrayList<ATTR>();//广告图列表
    private BannerView bannerView;
    private BannerView view;
    private Boolean showBanner;
    private ImageView mImageView;
    private ImageView ivGem;
    private boolean isMore;
    private PopupWindow popupWindow;
    private TextView tv_homelead_know;
    private Activity activity;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.community, null);
        mView.setOnClickListener(null);
        mFeedModel = new FeedModel(getActivity());
        mShared = getActivity().getSharedPreferences(UserAppConst.USERINFO, 0);
        editor = mShared.edit();
        mContext = getActivity();
        activity = getActivity();
        mListView = (XListView) mView.findViewById(R.id.community_listview);
        mEditText = (EditText) mView.findViewById(R.id.feed_comment_edittext);
        mInputView = (LinearLayout) mView.findViewById(R.id.feed_comment_view);
        mSend = (TextView) mView.findViewById(R.id.feed_comment_submit);
        mCreateImg = (ImageView) mView.findViewById(R.id.img_right);
        FrameLayout czy_title_layout = (FrameLayout) mView.findViewById(R.id.czy_title_layout);
        mSelectCommunity = (TextView) mView.findViewById(R.id.user_top_view_title);
        mImageView = (ImageView) mView.findViewById(R.id.user_top_view_back);
        ATTR attr = new ATTR();
        attr.img = "";
        adlist.add(attr);
        mListView.setAdapter(null);
        mListView.setPullRefreshEnable(true);
        addBannerView(adlist);
        mListView.setXListViewListener(this, 0);
        mImageView.setOnClickListener(this);
        mCreateImg.setOnClickListener(this);
        mCreateImg.setVisibility(View.VISIBLE);
        mCreateImg.setImageDrawable(getResources().getDrawable(R.drawable.icon_neighborhood_nav_tj));
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
        mListView.stopRefresh();
        mRegionId = mShared.getString(UserAppConst.Colour_REGION_ID, "");
        lat = mShared.getString(CityCustomConst.LOCATION_LATITUDE, "");
        lon = mShared.getString(CityCustomConst.LOCATION_LONGITUDE, "");
        setCommunityInfo();
        initEmptyView();
        //TODO 2017 1-4 出事状态加载缓存
//        mListView.startHeaderRefresh();
        initListData();
        ivGem = (ImageView) mView.findViewById(R.id.iv_gem);
        GemDialogUtil.showGemDialog(ivGem, getActivity(), GemConstant.neighborIndex, "");
        ThemeStyleHelper.onlyFrameTitileBar(getActivity().getApplicationContext(), czy_title_layout, mImageView, mSelectCommunity);
        return mView;
    }

    private void initPopup() {
        popupWindow = new PopupWindow(getActivity());
        View contentview = LayoutInflater.from(getActivity()).inflate(R.layout.linlilead, null);
        popupWindow = new PopupWindow(contentview,
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,
                true);
        tv_homelead_know = (TextView) contentview.findViewById(R.id.tv_homelead_know);
        tv_homelead_know.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                setBackgroundAlpha(1.0f);
            }
        });
        setBackgroundAlpha(0.3f);
        popupWindow.setOutsideTouchable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        //设置位置
        View rootview = LayoutInflater.from(getActivity()).inflate(R.layout.community, null);
        popupWindow.showAtLocation(rootview, Gravity.TOP, 0, 0);
        //popupWindow.showAsDropDown(iv_home_logo,0,0);
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                setBackgroundAlpha(1.0f);
            }
        });
        editor.putBoolean(UserAppConst.LINLISHOWPOP, true);
        editor.commit();
    }

    private void setBackgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getActivity().getWindow()
                .getAttributes();
        lp.alpha = bgAlpha;
        getActivity().getWindow().setAttributes(lp);
    }

    /**
     * 设置小区信息
     */
    private void setCommunityInfo() {
        mCommunityName = mShared.getString(UserAppConst.Colour_login_community_name, "互联网社区");
        mCommunityId = mShared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
        if (TextUtils.isEmpty(mCommunityId)) {
            mCommunityId = "03b98def-b5bd-400b-995f-a9af82be01da";
        }
        if (!TextUtils.isEmpty(mCommunityName)) {
            mSelectCommunity.setText(mCommunityName);
        } else {
            mSelectCommunity.setText("互联网社区");
        }

    }

    /**
     * 初始化空白页面
     */
    private void initEmptyView() {
        mEmptyView = LayoutInflater.from(getActivity()).inflate(R.layout.empty_layout, null);
        mEmptyLayout = (LinearLayout) mEmptyView.findViewById(R.id.empty_layout);
        mEmptyActivity = (TextView) mEmptyView.findViewById(R.id.empty_activity);
        mEmptyFeed = (TextView) mEmptyView.findViewById(R.id.empty_feed);
        mEmptyImg = (ImageView) mEmptyView.findViewById(R.id.empty_img);
        mEmptyText = (TextView) mEmptyView.findViewById(R.id.empty_text);

        mEmptyText.setText("最近大家都比较沉默~");
        mEmptyImg.setImageResource(R.drawable.c0_chat_empty);
        mEmptyFeed.setVisibility(View.VISIBLE);
        mEmptyActivity.setVisibility(View.VISIBLE);
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
        MobclickAgent.onPageStart("邻里");
        TCAgent.onPageStart(getActivity(), "邻里");
        Glide.with(this).resumeRequests();

    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd("邻里");
        TCAgent.onPageEnd(getActivity(), "邻里");
        Glide.with(this).pauseRequests();
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 1) {
            if (requestCode == COMMUNITY_CHANGE) {
                mCommunityName = mShared.getString(UserAppConst.Colour_login_community_name, "互联网社区");
                mCommunityId = mShared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
                //mCommunityModel.get(CommunityFragment.this, mRegionId, lat, lon, mCommunityName, mCommunityId);
                //mListView.startHeaderRefresh();
            }
        } else if (resultCode == 10) {

        }

    }


    private void initListData() {
//        mFeedModel.getFeedBanner(this,mCommunityId);
        mCommunityId = mShared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
        int userid = mShared.getInt(UserAppConst.Colour_User_id, 0);
        String cache = mShared.getString(UserAppConst.COMMUNITYFRAGMENTLIST + userid + mCommunityId, "");
        if (!TextUtils.isEmpty(cache)) {
            VerFeedListApi listApi = new VerFeedListApi();
            try {
                JSONObject jsonObject = new JSONObject(cache);
                listApi.response.fromJson(jsonObject);
            } catch (JSONException e) {
                e.printStackTrace();
            }
            ArrayList<FEED> feedList = listApi.response.feeds;
            mListView.stopLoadMore();
            mListView.stopRefresh();
            if (null != listApi.response.paged && listApi.response.paged.more == 1) {
                isMore = true;
            } else {
                isMore = false;
            }
            if (feedList != null && feedList.size() > 0) {
                mListView.loadMoreShow();
                mEmptyLayout.setVisibility(View.GONE);
                if (mFeedAdapter == null) {
                    if (getActivity() != null) {
                        mFeedAdapter = new FeedAdapter(getActivity(), feedList);
                        mListView.setAdapter(mFeedAdapter);
                    }
                } else {
                    mFeedAdapter.setData(feedList);
                }
                if (isMore) {
                    mListView.setPullLoadEnable(true);
                } else {
                    mListView.setPullLoadEnable(false);
                }
            } else {
                if (null != mFeedAdapter) {
                    mFeedAdapter.setData(feedList);
                }
                mListView.loadMoreHide();
                mEmptyLayout.setVisibility(View.VISIBLE);
                bannerView.setVisibility(View.GONE);
            }
        }
        mListView.startHeaderRefresh();
    }

    @Override
    public void OnHttpResponse(HttpApi api) {
        if (api.getClass().equals(VerFeedListApi.class)) {
            mListView.stopLoadMore();
            mListView.stopRefresh();
            if (mFeedModel.feedList != null && mFeedModel.feedList.size() > 0) {
                mListView.loadMoreShow();
                mEmptyLayout.setVisibility(View.GONE);
                if (null != activity) {
                    if (mFeedAdapter == null) {
                        mFeedAdapter = new FeedAdapter(activity, mFeedModel.feedList);
                        mListView.setAdapter(mFeedAdapter);
                    } else {
                        mFeedAdapter.setData(mFeedModel.feedList);
                    }
                }
                if (mFeedModel.isMore) {
                    mListView.setPullLoadEnable(true);
                } else {
                    mListView.setPullLoadEnable(false);
                }
            } else {
                if (null != mFeedAdapter) {
                    mFeedAdapter.setData(mFeedModel.feedList);
                }
                mListView.loadMoreHide();
                mEmptyLayout.setVisibility(View.VISIBLE);
                bannerView.setVisibility(View.GONE);
            }
            mFeedModel.getFeedBanner(this, mCommunityId);
        } else if (api.getClass().equals(VerFeedLikeApi.class)) {
            mFeedModel.feedList.get(mPosition).like_status = 1;
            mFeedModel.feedList.get(mPosition).like_total += 1;
            USER user = UserAppConst.getUser(getActivity());
            mFeedModel.feedList.get(mPosition).like.add(user);
            mFeedAdapter.setData(mFeedModel.feedList);
        } else if (api.getClass().equals(VerFeedUnlikeApi.class)) {
            mFeedModel.feedList.get(mPosition).like_status = 0;
            mFeedModel.feedList.get(mPosition).like_total -= 1;
            int user_id = mShared.getInt(UserAppConst.Colour_User_id, 0);
            if (null != mFeedModel.feedList.get(mPosition)) {
                for (int i = 0; i < mFeedModel.feedList.get(mPosition).like.size(); i++) {
                    if (null != mFeedModel.feedList.get(mPosition).like.get(i)) {
                        if (mFeedModel.feedList.get(mPosition).like.get(i).id == user_id) {
                            mFeedModel.feedList.get(mPosition).like.remove(i);
                            break;
                        }
                    }
                }
            }
            mFeedAdapter.setData(mFeedModel.feedList);
            mListView.startHeaderRefresh();
        } else if (api.getClass().equals(VerFeedCommentApi.class)) {
            for (int i = 0; i < mFeedModel.feedList.size(); i++) {
                if (mFeedModel.feedList.get(i).id.equals(mFeedId)) {
                    REPLY reply = new REPLY();
                    reply.from_user = UserAppConst.getUser(mContext);
                    reply.content = mEditText.getText().toString();
                    reply.id = mFeedModel.commentId;
                    if (mToUser != null) {
                        reply.to_user = mToUser;
                    } else {
                        reply.to_user = new USER();
                    }
                    mFeedModel.feedList.get(i).replys.add(reply);
                    mFeedModel.feedList.get(i).reply_total += 1;
                    mFeedAdapter.setData(mFeedModel.feedList);
                    break;
                }
            }
            mFeedId = null;
            mToUserId = null;
            mToUser = null;
            mEditText.setHint("");
            mEditText.setText("");
        } else if (api.getClass().equals(FeedDeleteApi.class)) {
            for (int i = 0; i < mFeedModel.feedList.size(); i++) {
                if (mFeedModel.feedList.get(i).id.equals(mFeedId)) {
                    mFeedModel.feedList.remove(i);
                    mFeedAdapter.setData(mFeedModel.feedList);
                    break;
                }
            }
            mFeedId = null;
            Message msg = new Message();
            msg.what = UserMessageConstant.DELETE_FEED;
            EventBus.getDefault().post(msg);
        } else if (api.getClass().equals(FeedCommentDeleteApi.class)) {
            for (int i = 0; i < mFeedModel.feedList.size(); i++) {
                if (mFeedModel.feedList.get(i).id.equals(mFeedId)) {
                    ArrayList<REPLY> replys = mFeedModel.feedList.get(i).replys;
                    for (int j = 0; j < replys.size(); j++) {
                        if (replys.get(j).id.equals(mCommentId)) {
                            replys.remove(j);
                            mFeedAdapter.setData(mFeedModel.feedList);
                            break;
                        }
                    }
                }
            }
            Message msg = new Message();
            msg.what = UserMessageConstant.DELETE_COMMENT;
            EventBus.getDefault().post(msg);
        } else if (api.getClass().equals(FeedAdlistApi.class)) {
            mListView.removeHeaderView(bannerView);
            if (((FeedAdlistApi) (api)).response.ok == 1) {
                if (((FeedAdlistApi) (api)).response.adlist.size() > 0) {
                    adlist.clear();
                    for (int i = 0; i < ((FeedAdlistApi) (api)).response.adlist.size(); i++) {
                        ATTR attr = new ATTR();
                        attr.img = ((FeedAdlistApi) (api)).response.adlist.get(i).img;
                        attr.name = ((FeedAdlistApi) (api)).response.adlist.get(i).name;
                        attr.url = ((FeedAdlistApi) (api)).response.adlist.get(i).url;
                        adlist.add(attr);
                    }
                    view.bindData(adlist);

                    if (mEmptyLayout.getVisibility() == View.VISIBLE) {
                        view.setVisibility(View.GONE);
                        bannerView.setVisibility(View.GONE);
                    } else {
                        view.setVisibility(View.VISIBLE);
                        bannerView.setVisibility(View.VISIBLE);
                    }

                    mListView.addHeaderView(bannerView);
                    showBanner = true;
                } else {
                    mListView.removeHeaderView(bannerView);
                    showBanner = false;
                    bannerView.setVisibility(View.GONE);
                    view.setVisibility(View.GONE);
                }
            } else {
                mListView.removeHeaderView(bannerView);
                showBanner = false;
                bannerView.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
            }
        }
    }

    public void onEvent(Object event) {
        Message message = (Message) event;
        if (message.what == UserMessageConstant.SIGN_IN_SUCCESS) {
            setCommunityInfo();
            mListView.startHeaderRefresh();
            GemDialogUtil.showGemDialog(ivGem, getActivity(), GemConstant.neighborIndex, "");
        } else if (message.what == UserMessageConstant.COMMUNITY_REFRESH) {
            mFeedModel.getFeedBanner(this, mShared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da"));
            mListView.startHeaderRefresh();
        } else if (message.what == FeedConstant.FEED_REPLY) {
            if (!mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                mToUserId = null;
                mToUser = null;
                mEditText.setHint("");
                int position = message.arg1;
                mFeedId = mFeedModel.feedList.get(position).id;
                showInputView();
            }
        } else if (message.what == FeedConstant.FEED_LIKE) {
            if (!mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                int userId = mShared.getInt(UserAppConst.Colour_User_id, 0);
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("customer_id", userId + "");
                paramsMap.put("community_id", mCommunityId);
                TCAgent.onEvent(getActivity(), "207001", "", paramsMap);
                mPosition = message.arg1;
                mFeedModel.like(this, mFeedModel.feedList.get(mPosition).id);
            }
        } else if (message.what == FeedConstant.FEED_UNLIKE) {
            if (!mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                int userId = mShared.getInt(UserAppConst.Colour_User_id, 0);
                Map<String, String> paramsMap = new HashMap<>();
                paramsMap.put("customer_id", userId + "");
                paramsMap.put("community_id", mCommunityId);
                TCAgent.onEvent(getActivity(), "207001", "", paramsMap);
                mPosition = message.arg1;
                mFeedModel.unlike(this, mFeedModel.feedList.get(mPosition).id);
            }
        } else if (message.what == FeedConstant.COMMENT_REPLY_USER) {
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
            mListView.startHeaderRefresh();
        } else if (message.what == UserMessageConstant.CHANGE_COMMUNITY) {//切换小区重刷页面
            mFeedModel.getFeedBanner(this, mShared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da"));
            setCommunityInfo();
            mFeedModel.getFeedListPro(this, mCommunityId);
            //mListView.startHeaderRefresh();
        } else if (message.what == FeedConstant.FEED_DELETE) {
            if (!mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                mPosition = message.arg1;
                mFeedId = mFeedModel.feedList.get(mPosition).id;
                mFeedModel.deleteFeed(this, mFeedModel.feedList.get(mPosition).id);
            }
        } else if (message.what == FeedConstant.FEED_DELETE_COMMENT) {
            if (!mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                Login();
            } else {
                mFeedId = (String) message.obj;
                mCommentId = String.valueOf(message.arg1);
                mFeedModel.deleteComment(this, mCommentId);
            }
        } else if (message.what == FeedConstant.FEED_USER_INFOR) {
            friendUUidList.addAll(CacheFriendInforHelper.instance().toQueryFriendUUIdList(getActivity()));
            int userId = (int) message.obj;
            IMUploadPhoneModel imUploadPhoneModel = new IMUploadPhoneModel(getActivity());
            imUploadPhoneModel.getUserInforByUserId(0, userId, this);
        }
    }

    private void showInputView() {
        mInputView.setVisibility(View.VISIBLE);
        mEditText.setFocusable(true);
        mEditText.setFocusableInTouchMode(true);
        mEditText.requestFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(mEditText, InputMethodManager.RESULT_SHOWN);
        imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
        Message msg = new Message();
        msg.what = FeedConstant.FEED_SHOW_INPUT;
        EventBus.getDefault().post(msg);
    }

    // 关闭键盘
    public void closeKeyBoard() {
        mInputView.setVisibility(View.GONE);
        mEditText.clearFocus();
        InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(mEditText.getWindowToken(), 0);
        Message msg = new Message();
        msg.what = FeedConstant.FEED_CLOSE_INPUT;
        EventBus.getDefault().post(msg);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.user_top_view_back:
                getActivity().finish();
                break;
            case R.id.img_right:
                if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                    intent = new Intent(mContext, FeedOrActivityActivity.class);
                    startActivity(intent);
                    getActivity().overridePendingTransition(0, 0);
                } else {
                    LinkParseUtil.parse(getActivity(), "", "");
                }
                break;
            case R.id.empty_activity:
                if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                    intent = new Intent(getActivity(), ReleaseActivity.class);
                    startActivity(intent);
                } else {
                    LinkParseUtil.parse(getActivity(), "", "");
                }
                break;
            case R.id.empty_feed:
                if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                    intent = new Intent(getActivity(), CreateNormalFeedActivity.class);
                    startActivity(intent);
                } else {
                    LinkParseUtil.parse(getActivity(), "", "");
                }
                break;
            case R.id.feed_comment_submit:
                String content = mEditText.getText().toString();
                if (!TextUtils.isEmpty(content)) {
                    closeKeyBoard();
                    int userId = mShared.getInt(UserAppConst.Colour_User_id, 0);
                    Map<String, String> paramsMap = new HashMap<>();
                    paramsMap.put("customer_id", userId + "");
                    paramsMap.put("community_id", mCommunityId);
                    TCAgent.onEvent(getActivity(), "207002", "", paramsMap);
                    mFeedModel.reply(this, mFeedId, content, mToUserId);
                } else {
                    ToastUtil.toastShow(getActivity(), "请输入内容");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            Boolean isfirst = mShared.getBoolean(UserAppConst.LINLISHOWPOP, false);
            Boolean islogin = mShared.getBoolean(UserAppConst.IS_LOGIN, false);
            if (isfirst == false && islogin) {
                initPopup();
            }
        }
    }

    private void Login() {
        ToastUtil.toastShow(mContext, "请先登录");
        LinkParseUtil.parse(mContext, "", "");
    }

    /**
     * 添加广告Banner
     */
    protected void addBannerView(ArrayList<ATTR> list) {
        bannerView = (BannerView) LayoutInflater.from(getActivity()).inflate(R.layout.home_banner, null);
        view = (BannerView) bannerView.findViewById(R.id.banner);
        ViewTreeObserver viewTreeObserver = view.getViewTreeObserver();
        viewTreeObserver.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            public boolean onPreDraw() {
                int width = view.getMeasuredWidth();
                ViewGroup.LayoutParams params = view.getLayoutParams();
                params.height = (int) (width * 174 / 726);
                view.setLayoutParams(params);
                return true;
            }
        });
        view.bindData(list);
        bannerView.setVisibility(View.INVISIBLE);
        view.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onRefresh(int id) {
        mCommunityId = mShared.getString(UserAppConst.Colour_login_community_uuid, "03b98def-b5bd-400b-995f-a9af82be01da");
        mFeedModel.getFeedListPro(this, mCommunityId);
    }

    @Override
    public void onLoadMore(int id) {
        mFeedModel.getFeedListNext(this, mCommunityId);
    }

    private List<String> friendUUidList = new ArrayList<>();

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        UserIdInforEntity userIdInforEntity = GsonUtils.gsonToBean(result, UserIdInforEntity.class);
                        UserIdInforEntity.ContentBean contentBean = userIdInforEntity.getContent();
                        String userUUid = contentBean.getUuid();
                        if (mShared.getString(UserAppConst.Colour_User_uuid, "").equals(userUUid)) {
                            Intent intent = new Intent(getActivity(), IMUserSelfInforActivity.class);
                            intent.putExtra(IMFriendInforActivity.USERUUID, userUUid);
                            startActivity(intent);
                        } else {
                            if (friendUUidList.contains(userUUid)) {
                                Intent intent = new Intent(getActivity(), IMFriendInforActivity.class);
                                intent.putExtra(IMFriendInforActivity.USERUUID, userUUid);
                                startActivity(intent);
                            } else {
                                Intent intent = new Intent(getActivity(), IMCustomerInforActivity.class);
                                intent.putExtra(IMFriendInforActivity.USERUUID, userUUid);
                                startActivity(intent);
                            }
                        }
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }
}
