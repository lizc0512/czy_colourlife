package com.community.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.model.NewHttpResponse;
import com.community.activity.CommunityMessageListActivity;
import com.community.activity.DynamicsDetailsActivity;
import com.community.activity.PublishDynamicsActivity;
import com.community.adapter.CommunityDynamicsAdapter;
import com.community.adapter.CommunityTipOffAdapter;
import com.community.entity.CommunityDynamicIdEntity;
import com.community.entity.CommunityDynamicsListEntity;
import com.community.entity.CommunityTipOffEntity;
import com.community.model.CommunityDynamicsModel;
import com.community.utils.RealIdentifyDialogUtil;
import com.community.view.DeleteNoticeDialog;
import com.community.view.TipTypeListDialog;
import com.community.view.TipoffsCommentDialog;
import com.external.eventbus.EventBus;
import com.im.helper.CacheApplyRecorderHelper;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;
import com.user.UserMessageConstant;
import com.user.model.NewUserModel;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.youmai.hxsdk.HuxinSdkManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.csh.colourful.life.utils.KeyBoardUtils;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import q.rorbin.badgeview.QBadgeView;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_IDLE;
import static com.community.activity.DynamicsDetailsActivity.DYNAMICS_DETAILS;
import static com.user.UserAppConst.COLOUR_DYNAMICS_NEWLIST_CACHE;
import static com.user.UserAppConst.COLOUR_DYNAMICS_NOTICE_NUMBER;
import static com.user.UserMessageConstant.CHANGE_COMMUNITY;
import static com.user.UserMessageConstant.SIGN_IN_SUCCESS;
import static com.youmai.hxsdk.utils.DisplayUtil.getStatusBarHeight;

/**
 * @ProjectName:
 * @Package: com.community.fragment
 * @ClassName: CommunityDynamicsFragment
 * @Description: 用户动态的列表
 * @Author: yuansk
 * @CreateDate: 2020/2/21 10:46
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/21 10:46
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public class CommunityDynamicsFragment extends Fragment implements View.OnClickListener, NewHttpResponse {

    public static final int DIRECT_DELETE_DYNAMIC = 2000;//删除自己动态
    public static final int DIRECT_DELETE_COMMENT = 2001;//删除自己的评论
    public static final int DIRECT_LIKE_DYNAMIC = 2002;//点赞或取消点赞
    public static final int DIRECT_COMMENT_DYNAMIC = 2003;//直接评论动态
    public static final int TIPOFF_COMMENT_DYNAMIC = 2004;//举报动态
    public static final int REPLY_OTHER_COMMENT = 2005;//回复评论
    public static final int CALLBACL_COMMENT_DYNAMIC = 2007;//动态详情操作的回调
    public static final int UPDATE_DYNAMIC_REMINDCOUNT = 2008;//更新动态提醒的数目
    public static final int TIPOFF_OTHER_COMMENT = 2009;//举报评论

    private SwipeRefreshLayout dynamics_refresh_layout;
    private ImageView iv_unRead_message;
    private RelativeLayout dynamics_data_layout;
    private SwipeMenuRecyclerView rv_community_dynamics;
    private ImageView iv_publish_dynamics;
    private View community_tabbar_view;
    private QBadgeView unReadBadgeView;
    private RelativeLayout dynamics_empty_layout;
    private TextView tv_send_dynamics;
    private CommunityDynamicsModel communityDynamicsModel;
    private NewUserModel newUserModel;
    private int page = 1;
    private String year = "";
    private int total = 0;
    private SharedPreferences mShared;
    private SharedPreferences.Editor editor;

    private CommunityDynamicsAdapter communityDynamicsAdapter;
    private boolean isFirst;
    private int recycleState = 0;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mShared = getActivity().getSharedPreferences(UserAppConst.USERINFO, 0);
        editor = mShared.edit();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_community_dynamics, container, false);
        EventBus.getDefault().register(this);
        dynamics_refresh_layout = rootView.findViewById(R.id.dynamics_refresh_layout);
        community_tabbar_view = rootView.findViewById(R.id.community_tabbar_view);
        community_tabbar_view.setBackgroundColor(Color.parseColor("#ffffff"));
        setTabViewHeight(community_tabbar_view);
        dynamics_refresh_layout.setColorSchemeColors(Color.parseColor("#3290FF"), Color.parseColor("#6ABDF9"));

        iv_unRead_message = rootView.findViewById(R.id.iv_unRead_message);
        rv_community_dynamics = rootView.findViewById(R.id.rv_community_dynamics);

        dynamics_data_layout = rootView.findViewById(R.id.dynamics_data_layout);
        iv_publish_dynamics = rootView.findViewById(R.id.iv_publish_dynamics);
        dynamics_empty_layout = rootView.findViewById(R.id.dynamics_empty_layout);
        tv_send_dynamics = rootView.findViewById(R.id.tv_send_dynamics);

        dynamics_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                year = "";
                page = 1;
                communityDynamicsModel.getCommunityDynamicList(0, page, year, false, CommunityDynamicsFragment.this);
            }
        });

        SimpleItemAnimator simpleItemAnimator = ((SimpleItemAnimator) rv_community_dynamics.getItemAnimator());
        simpleItemAnimator.setSupportsChangeAnimations(false);
        simpleItemAnimator.setChangeDuration(0);
        rv_community_dynamics.setItemViewSwipeEnabled(false);
        rv_community_dynamics.setLongPressDragEnabled(false);
        rv_community_dynamics.setAutoLoadMore(false);
        rv_community_dynamics.useDefaultLoadMore();
        rv_community_dynamics.setLoadMoreListener(new SwipeMenuRecyclerView.LoadMoreListener() {
            @Override
            public void onLoadMore() {
                if (!TextUtils.isEmpty(year) && total < 10) {
                    page = 1;
                } else {
                    page++;
                }
                communityDynamicsModel.getCommunityDynamicList(0, page, year, false, CommunityDynamicsFragment.this);
            }
        });
        rv_community_dynamics.addOnScrollListener(new RecyclerView.OnScrollListener() {
            View firstChild;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                if (recyclerView != null && recyclerView.getChildCount() > 0) {
                    firstChild = recyclerView.getChildAt(0);
                }
                int firstChildPosition = firstChild == null ? 0 : recyclerView.getChildLayoutPosition(firstChild);
                //如果firstChild处于列表的第一个位置，且top>=0,则下拉刷新控件可用
                dynamics_refresh_layout.setEnabled(firstChildPosition == 0 && firstChild.getTop() >= 0);
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                recycleState = newState;
            }
        });
        unReadBadgeView = new QBadgeView(getActivity());
        unReadBadgeView.bindTarget(iv_unRead_message);
        unReadBadgeView.setBadgeGravity(Gravity.END | Gravity.TOP);
        unReadBadgeView.setBadgeTextSize(8f, true);
        unReadBadgeView.setBadgeBackgroundColor(ContextCompat.getColor(getActivity(), R.color.hx_color_red_tag));
        unReadBadgeView.setShowShadow(false);
        iv_unRead_message.setOnClickListener(this::onClick);
        iv_publish_dynamics.setOnClickListener(this::onClick);
        tv_send_dynamics.setOnClickListener(this::onClick);
        communityDynamicsModel = new CommunityDynamicsModel(getActivity());
        newUserModel = new NewUserModel(getActivity());
        String dynamicCache = mShared.getString(COLOUR_DYNAMICS_NEWLIST_CACHE, "");
        if (!TextUtils.isEmpty(dynamicCache)) {
            dynamicContentList.clear();
            dynamicContentList = GsonUtils.jsonToList(dynamicCache, CommunityDynamicsListEntity.ContentBean.DataBean.class);
            showDynamicData();
            communityDynamicsModel.getCommunityDynamicList(0, page, year, false, this);
        } else {
            communityDynamicsModel.getCommunityDynamicList(0, page, year, true, this);
        }
        newUserModel.getIsRealName(1, false, CommunityDynamicsFragment.this);
        communityDynamicsModel.getReportContent(2, CommunityDynamicsFragment.this);
        return rootView;
    }

    private void setTabViewHeight(View tabBarView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, getStatusBarHeight(getActivity()));
        tabBarView.setLayoutParams(layoutParams);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_unRead_message:
                Intent intent = new Intent(getActivity(), CommunityMessageListActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_send_dynamics://发送动态
            case R.id.iv_publish_dynamics:
                Intent publish_intent = new Intent(getActivity(), PublishDynamicsActivity.class);
                startActivityForResult(publish_intent, 1000);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (EventBus.getDefault().isregister(this)) {
            EventBus.getDefault().unregister(this);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == 200) {
            year = "";
            page = 1;
            communityDynamicsModel.getCommunityDynamicList(0, page, year, true, CommunityDynamicsFragment.this);
        }
    }

    private void setDynamicsListener() {
        if (null != communityDynamicsAdapter) {
            communityDynamicsAdapter.setOnItemClickListener(new OnItemClickListener() {
                @Override
                public void onItemClick(int i) {
                    switch (recycleState) {
                        case SCROLL_STATE_IDLE:
                            CommunityDynamicsListEntity.ContentBean.DataBean dataBean = dynamicContentList.get(i);
                            Intent intent = new Intent(getActivity(), DynamicsDetailsActivity.class);
                            intent.putExtra(DYNAMICS_DETAILS, dataBean);
                            startActivity(intent);
                            break;
                    }
                }
            });
        }
    }

    private List<CommunityDynamicsListEntity.ContentBean.DataBean> dynamicContentList = new ArrayList<>();

    private void showDynamicList(String result) {
        try {
            boolean dataEmpty = true;
            String resultYear = "";
            int resultTotal = 0;
            if (!TextUtils.isEmpty(result)) {
                CommunityDynamicsListEntity communityDynamicsListEntity = GsonUtils.gsonToBean(result, CommunityDynamicsListEntity.class);
                CommunityDynamicsListEntity.ContentBean contentBean = communityDynamicsListEntity.getContent();
                resultTotal = contentBean.getTotal();
                resultYear = contentBean.getYear();
                List<CommunityDynamicsListEntity.ContentBean.DataBean> pageContentList = contentBean.getData();
                dataEmpty = pageContentList == null || pageContentList.size() == 0;
                dynamicContentList.addAll(pageContentList);
            }
            if (dynamicContentList.size() == 0) {
                dynamics_data_layout.setVisibility(View.GONE);
                dynamics_empty_layout.setVisibility(View.VISIBLE);
                //用户没有动态
            } else {
                dynamics_data_layout.setVisibility(View.VISIBLE);
                dynamics_empty_layout.setVisibility(View.GONE);
                boolean hasMore = false;
                if (!TextUtils.isEmpty(resultYear)) {
                    if (year.equals(resultYear)) { //2次请求的时间一样
                        total = resultTotal;
                    } else {
                        year = resultYear;
                        total += resultTotal;
                    }
                } else {
                    total = resultTotal;
                }
                if (total > dynamicContentList.size()) {
                    hasMore = true;
                } else {
                    hasMore = false;
                }
                showDynamicData();
                //进行数据适配器的展示
                rv_community_dynamics.loadMoreFinish(dataEmpty, hasMore);
            }
            saveFristDynamicCache();
        } catch (Exception e) {

        }
    }

    private void showDynamicData() {
        String current_user_uuid = mShared.getString(UserAppConst.Colour_User_uuid, "");
        if (null == communityDynamicsAdapter) {
            communityDynamicsAdapter = new CommunityDynamicsAdapter(getActivity(), dynamicContentList);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            rv_community_dynamics.setLayoutManager(linearLayoutManager);
            communityDynamicsAdapter.setUserUUId(current_user_uuid);
            rv_community_dynamics.setAdapter(communityDynamicsAdapter);
        } else {
            communityDynamicsAdapter.setUserUUId(current_user_uuid);
            communityDynamicsAdapter.notifyItemRangeChanged(0, dynamicContentList.size());
        }
        setDynamicsListener();
    }

    private void saveFristDynamicCache() {
        try {
            if (TextUtils.isEmpty(year) && page == 1) {  //值缓存第一页的数据
                String fristPageCache = GsonUtils.gsonString(dynamicContentList);
                editor.putString(COLOUR_DYNAMICS_NEWLIST_CACHE, fristPageCache).apply();
            }
        } catch (Exception e) {

        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                isFirst = true;
                if (dynamics_refresh_layout.isRefreshing()) {
                    dynamics_refresh_layout.setRefreshing(false);
                }
                if (TextUtils.isEmpty(year) && page == 1) {
                    dynamicContentList.clear();
                }
                showDynamicList(result);
                break;
            case 3:
                ToastUtil.toastShow(getActivity(), "动态举报成功");
                break;
            case 4:
                ToastUtil.toastShow(getActivity(), "评论举报成功");
                break;
            case 5:
                dynamicContentList.remove(position);
                communityDynamicsAdapter.notifyItemRemoved(position);
                communityDynamicsAdapter.notifyItemChanged(position, "");
                ToastUtil.toastShow(getActivity(), "动态删除成功");
                if (dynamicContentList.size() == 0) {
                    dynamics_data_layout.setVisibility(View.GONE);
                    dynamics_empty_layout.setVisibility(View.VISIBLE);
                    editor.putString(COLOUR_DYNAMICS_NEWLIST_CACHE, "").apply();
                    //用户没有动态
                } else {
                    saveFristDynamicCache();
                }
                break;
            case 6:
                delDynamicCommentSuccess();
                saveFristDynamicCache();
                break;
            case 7:
                CommunityDynamicsListEntity.ContentBean.DataBean dataBean = dynamicContentList.get(position);
                List<CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean> zanBeanList = dataBean.getZan();
                String zanUserId = String.valueOf(mShared.getInt(UserAppConst.Colour_User_id, 0));
                int hasZan = 0;
                for (CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean zanBean : zanBeanList) {
                    if (zanBean.getFrom_id().equals(zanUserId)) {
                        hasZan = 1; //已经点赞过了不做处理
                        break;
                    }
                }
                if (hasZan == 0) { //防止多次点击问题
                    int zanCount = dataBean.getZan_count();
                    dataBean.setZan_count(++zanCount);
                    dataBean.setIs_zan("1");
                    CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean likeZanBean = new CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean();
                    likeZanBean.setFrom_id(zanUserId);
                    likeZanBean.setFrom_nickname(mShared.getString(UserAppConst.Colour_NIACKNAME, ""));
                    likeZanBean.setFrom_avatar(mShared.getString(UserAppConst.Colour_head_img, ""));
                    zanBeanList.add(likeZanBean);
                    dataBean.setZan(zanBeanList);
                    communityDynamicsAdapter.notifyItemChanged(position, "like");
                    saveFristDynamicCache();
                }
                break;
            case 8:
                CommunityDynamicsListEntity.ContentBean.DataBean cancelDataBean = dynamicContentList.get(position);
                int cancelCount = cancelDataBean.getZan_count();
                cancelDataBean.setZan_count(--cancelCount);
                cancelDataBean.setIs_zan("2");
                int delPos = -1;
                String cancelZanUserId = String.valueOf(mShared.getInt(UserAppConst.Colour_User_id, 0));
                List<CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean> cancelZanList = cancelDataBean.getZan();
                for (int q = 0; q < cancelZanList.size(); q++) {
                    CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean zanBean = cancelZanList.get(q);
                    if (cancelZanUserId.equals(zanBean.getFrom_id())) {
                        delPos = q;
                        break;
                    }
                }
                if (delPos != -1) {
                    cancelZanList.remove(delPos);
                    cancelDataBean.setZan(cancelZanList);
                    communityDynamicsAdapter.notifyItemChanged(position, "like");
                    saveFristDynamicCache();
                }
                break;
            case 9://新增评论 或回复
                String commentId = "";
                try {
                    CommunityDynamicIdEntity communityDynamicIdEntity = GsonUtils.gsonToBean(result, CommunityDynamicIdEntity.class);
                    commentId = communityDynamicIdEntity.getContent().getComment_id();
                } catch (Exception e) {

                }
                CommunityDynamicsListEntity.ContentBean.DataBean commentDataBean = dynamicContentList.get(position);
                List<CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean> commentDataList = commentDataBean.getComment();
                //对动态进行评论
                CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean commentBean = new CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean();
                commentBean.setContent(content);
                commentBean.setId(commentId);
                commentBean.setSource_id(commentDataBean.getSource_id());
                commentBean.setFrom_avatar(mShared.getString(UserAppConst.Colour_head_img,""));
                commentBean.setFrom_mobile(mShared.getString(UserAppConst.Colour_login_mobile, ""));
                int userId = mShared.getInt(UserAppConst.Colour_User_id, 0);
                String fromSourceId = String.valueOf(userId);
                commentBean.setFrom_id(fromSourceId);
                commentBean.setFrom_nickname(mShared.getString(UserAppConst.Colour_NIACKNAME, ""));
                int comment_count = commentDataBean.getComment_count();
                commentDataBean.setComment_count(++comment_count);
                long currentTime = System.currentTimeMillis() / 1000;
                commentBean.setCreated_at(currentTime);
                if (TextUtils.isEmpty(fromUuid)) {
                    ToastUtil.toastShow(getActivity(), "评论成功");
                } else {
                    //回复别人的评论
                    ToastUtil.toastShow(getActivity(), "回复成功");
                    commentBean.setTo_nickname(fromNickName);
                    commentBean.setTo_id(fromUuid);
                }
                commentDataList.add(commentBean);
                communityDynamicsAdapter.notifyItemChanged(position, "comment");
                saveFristDynamicCache();
                break;
        }
    }

    private int position = 0;//动态的itemPosition
    private int commentPosition = -1;//评论的itemPosition
    private String content;//评论的内容
    private String fromUuid;//回复评论人id
    private String fromNickName;//回复评论人昵称

    public void onEvent(Object event) {
        String is_identity = mShared.getString(UserAppConst.COLOUR_DYNAMICS_REAL_IDENTITY, "0");
        final Message message = (Message) event;
        Bundle bundle = message.getData();
        String sourceId = bundle.getString("sourceId");
        position = bundle.getInt("position");
        switch (message.what) {
            case DIRECT_DELETE_DYNAMIC: //删除自己的动态
                if ("1".equals(is_identity)) {
                    showDelDynamics(sourceId, "");
                } else {
                    RealIdentifyDialogUtil.showGoIdentifyDialog(getActivity());
                }
                break;
            case DIRECT_DELETE_COMMENT://删除自己的评论
                String commentId = bundle.getString("commentId");
                commentPosition = bundle.getInt("commentPosition");
                if ("1".equals(is_identity)) {
                    showDelDynamics(sourceId, commentId);
                } else {
                    RealIdentifyDialogUtil.showGoIdentifyDialog(getActivity());
                }
                break;
            case DIRECT_LIKE_DYNAMIC://点赞取消点赞
                String isZan = bundle.getString("isZan");
                if ("1".equals(is_identity)) {
                    if ("2".equals(isZan)) {
                        communityDynamicsModel.likeUserDynamic(7, sourceId, CommunityDynamicsFragment.this);
                    } else {
                        communityDynamicsModel.unlikeUserDynamic(8, sourceId, CommunityDynamicsFragment.this);
                    }
                } else {
                    RealIdentifyDialogUtil.showGoIdentifyDialog(getActivity());
                }
                break;
            case DIRECT_COMMENT_DYNAMIC://直接评论动态
                if ("1".equals(is_identity)) {
                    fromUuid = "";
                    fromNickName = "";
                    View adapterView = (View) message.obj;
                    showInputCommentDialog(adapterView, sourceId, "");
                } else {
                    RealIdentifyDialogUtil.showGoIdentifyDialog(getActivity());
                }
                break;
            case TIPOFF_COMMENT_DYNAMIC://举报动态
                if ("1".equals(is_identity)) {
                    showTipCommentDelDialog(sourceId, "");
                } else {
                    RealIdentifyDialogUtil.showGoIdentifyDialog(getActivity());
                }
                break;
            case REPLY_OTHER_COMMENT://回复评论
                if ("1".equals(is_identity)) {
                    View adapterView = (View) message.obj;
                    fromUuid = bundle.getString("fromUuid");
                    fromNickName = bundle.getString("fromNickName");
                    showInputCommentDialog(adapterView, sourceId, fromUuid);
                } else {
                    RealIdentifyDialogUtil.showGoIdentifyDialog(getActivity());
                }
                break;
            case TIPOFF_OTHER_COMMENT:
                if ("1".equals(is_identity)) {
                    String tipOffCommentId = bundle.getString("commentId");
                    showTipCommentDelDialog(sourceId, tipOffCommentId);
                } else {
                    RealIdentifyDialogUtil.showGoIdentifyDialog(getActivity());
                }
                break;
            case CHANGE_COMMUNITY:
                year = "";
                page = 1;
                communityDynamicsModel.getCommunityDynamicList(0, page, year, false, CommunityDynamicsFragment.this);
            case SIGN_IN_SUCCESS:
                if (isFirst) {
                    year = "";
                    page = 1;
                    communityDynamicsModel.getCommunityDynamicList(0, page, year, true, CommunityDynamicsFragment.this);
                    newUserModel.getIsRealName(1, false, CommunityDynamicsFragment.this);
                }
                break;
            case CALLBACL_COMMENT_DYNAMIC:
                for (int j = 0; j < dynamicContentList.size(); j++) {
                    CommunityDynamicsListEntity.ContentBean.DataBean dataBean = dynamicContentList.get(j);
                    if (sourceId.equals(dataBean.getSource_id())) {
                        position = j;
                        break;
                    }
                }
                if (position != -1) {  //说明在当前列表有这条动态
                    if (message.arg1 == 1) {
                        dynamicContentList.remove(position);
                        communityDynamicsAdapter.notifyItemRemoved(position);
                        communityDynamicsAdapter.notifyItemChanged(position, "");
                        if (dynamicContentList.size() == 0) {
                            dynamics_data_layout.setVisibility(View.GONE);
                            dynamics_empty_layout.setVisibility(View.VISIBLE);
                            editor.putString(COLOUR_DYNAMICS_NEWLIST_CACHE, "").apply();
                        } else {
                            saveFristDynamicCache();
                        }
                    } else {
                        CommunityDynamicsListEntity.ContentBean.DataBean dataBean = (CommunityDynamicsListEntity.ContentBean.DataBean) message.obj;
                        dynamicContentList.set(position, dataBean);
                        communityDynamicsAdapter.notifyItemChanged(position);
                        saveFristDynamicCache();
                    }
                }
                break;
            case UPDATE_DYNAMIC_REMINDCOUNT:
                communityDynamicsModel.getDynamicRemindCount(10, CommunityDynamicsFragment.this);
                break;
            case UserMessageConstant.GET_APPLY_NUMBER://申请好友的通知
                showTotalUnReadCount();
                break;
        }
    }

    private Dialog inputDialog;

    private void showInputCommentDialog(View itemView, String sourceId, String toUserId) {
        final int itemBottomY = getCoordinateY(itemView) + itemView.getHeight();//item
        inputDialog = new Dialog(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dynamic_comment_layout, null);
        inputDialog.setContentView(view);
        //scrollView 点击事件，点击时将 dialog dismiss，设置 onClick 监听无效
        inputDialog.findViewById(R.id.scrollView).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP)
                    inputDialog.dismiss();
                return true;
            }
        });
        inputDialog.show();
        itemView.postDelayed(new Runnable() {
            @Override
            public void run() {
                LinearLayout llCommentInput = inputDialog.findViewById(R.id.ll_comment_input);
                int y = getCoordinateY(llCommentInput);
                rv_community_dynamics.smoothScrollBy(0, itemBottomY - y);
            }
        }, 300);
        getActivity().getWindow().getDecorView().getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                handleWindowChange();
            }
        });
        EditText feed_comment_edittext = view.findViewById(R.id.feed_comment_edittext);
        feed_comment_edittext.setFocusable(true);
        feed_comment_edittext.setFocusableInTouchMode(true);
        feed_comment_edittext.requestFocus();
        if (!TextUtils.isEmpty(fromNickName)) {
            feed_comment_edittext.setHint("回复" + fromNickName);
        } else {
            feed_comment_edittext.setHint(getResources().getString(R.string.community_comment_hint));
        }
        TextView feed_comment_submit = view.findViewById(R.id.feed_comment_submit);
        feed_comment_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                content = feed_comment_edittext.getText().toString().trim();
                if (!TextUtils.isEmpty(content)) {
                    if (content.length() > 300) {
                        ToastUtil.toastShow(getActivity(), "输入的内容长度不能超过300字");
                    } else {
                        KeyBoardUtils.closeKeybord(feed_comment_edittext, getActivity());
                        inputDialog.dismiss();
                        communityDynamicsModel.commentDynamic(9, content, sourceId, toUserId, CommunityDynamicsFragment.this::OnHttpResponse);
                    }
                } else {
                    ToastUtil.toastShow(getActivity(), "输入的内容不能为空");
                }
            }
        });
    }

    /**
     * 监听键盘的显示和隐藏
     */
    private void handleWindowChange() {
        Rect rect = new Rect();
        getActivity().getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);//获取当前界面显示范围
        int displayHeight = rect.bottom - rect.top;//app内容显示高度，即屏幕高度-状态栏高度-键盘高度
        int totalHeight = getActivity().getWindow().getDecorView().getHeight();
        //显示内容的高度和屏幕高度比大于 0.8 时，dismiss dialog
        if ((float) displayHeight / totalHeight > 0.8)//0.8只是一个大致的比例，可以修改
            if (null != inputDialog && inputDialog.isShowing())
                inputDialog.dismiss();

    }


    private int getCoordinateY(View view) {
        int[] coordinate = new int[2];
        view.getLocationOnScreen(coordinate);
        return coordinate[1];
    }

    private void showTipCommentDelDialog(String sourceId, String commentId) {
        TipoffsCommentDialog tipoffsCommentDialog = new TipoffsCommentDialog(getActivity(), R.style.custom_dialog_theme);
        tipoffsCommentDialog.show();
        tipoffsCommentDialog.tv_dynamics_tipoffs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示举报列表的dialog
                tipoffsCommentDialog.dismiss();
                showTipOffDialog(sourceId, commentId);
            }
        });
    }

    private void delDynamicCommentSuccess() {
        ToastUtil.toastShow(getActivity(), "评论删除成功");
        CommunityDynamicsListEntity.ContentBean.DataBean delDataBean = dynamicContentList.get(position);
        delDataBean.getComment().remove(commentPosition);
        int delComment_Count = delDataBean.getComment_count();
        delDataBean.setComment_count(--delComment_Count);
        communityDynamicsAdapter.notifyItemChanged(position, "comment");
        commentPosition = -1;
    }

    private void showDelDynamics(String sourceId, String commentId) {
        DeleteNoticeDialog deleteNoticeDialog = new DeleteNoticeDialog(getActivity(), R.style.custom_dialog_theme);
        deleteNoticeDialog.show();
        deleteNoticeDialog.btn_define.setOnClickListener(v -> {
            //删除动态  删除评论
            deleteNoticeDialog.dismiss();
            if (TextUtils.isEmpty(commentId)) {
                if (commentPosition == -1) {
                    communityDynamicsModel.delUserDynamic(5, sourceId, CommunityDynamicsFragment.this);
                } else {
                    delDynamicCommentSuccess();
                }
            } else {
                communityDynamicsModel.delOwnerComment(6, sourceId, commentId, CommunityDynamicsFragment.this);
            }
        });
    }


    private void showTipOffDialog(String sourceId, String commentId) {
        TipTypeListDialog tipTypeListDialog = new TipTypeListDialog(getActivity(), R.style.custom_dialog_theme);
        tipTypeListDialog.show();
        String tipOffListCache = mShared.getString(UserAppConst.COLOUR_DYNAMICS_TIPOFF_LIST, "");
        if (!TextUtils.isEmpty(tipOffListCache)) {
            try {
                CommunityTipOffEntity communityTipOffEntity = GsonUtils.gsonToBean(tipOffListCache, CommunityTipOffEntity.class);
                List<CommunityTipOffEntity.ContentBean> tipOffContentList = communityTipOffEntity.getContent();
                CommunityTipOffAdapter communityTipOffAdapter = new CommunityTipOffAdapter(getActivity(), tipOffContentList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                tipTypeListDialog.rv_tipoffs_type.setLayoutManager(linearLayoutManager);
                tipTypeListDialog.rv_tipoffs_type.setAdapter(communityTipOffAdapter);
                communityTipOffAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int i) {
                        if (TextUtils.isEmpty(commentId)) {
                            communityDynamicsModel.reportViolationDynamic(3, sourceId, tipOffContentList.get(i).getReport_type(), CommunityDynamicsFragment.this::OnHttpResponse);
                        } else {
                            communityDynamicsModel.reportViolationComment(4, sourceId, commentId, tipOffContentList.get(i).getReport_type(), CommunityDynamicsFragment.this::OnHttpResponse);
                        }
                        tipTypeListDialog.dismiss();
                    }
                });
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        showTotalUnReadCount();
    }

    private void showTotalUnReadCount() {
        int totalUnReadMsgCount = HuxinSdkManager.instance().unreadBuddyAndCommMessage();
        int newFriendApplyCount = 0;
        if (mShared.getBoolean(UserAppConst.IM_APPLY_FRIEND, false)) {
            newFriendApplyCount = CacheApplyRecorderHelper.instance().toQueryApplyRecordSize(getActivity(), "0");
        }
        int unReadNoticeCount = mShared.getInt(COLOUR_DYNAMICS_NOTICE_NUMBER, 0);
        unReadBadgeView.setBadgeNumber(totalUnReadMsgCount + newFriendApplyCount + unReadNoticeCount);
        ((MainActivity) getActivity()).showUnReadMsg(unReadNoticeCount);
    }

    public void showNoticeMessageNumber(int unReadNoticeCount) {
        int totalUnReadMsgCount = HuxinSdkManager.instance().unreadBuddyAndCommMessage();
        int newFriendApplyCount = 0;
        if (mShared.getBoolean(UserAppConst.IM_APPLY_FRIEND, false)) {
            newFriendApplyCount = CacheApplyRecorderHelper.instance().toQueryApplyRecordSize(getActivity(), "0");
        }
        if (null != unReadBadgeView) {
            unReadBadgeView.setBadgeNumber(totalUnReadMsgCount + newFriendApplyCount + unReadNoticeCount);
        }
    }
}
