package com.community.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Message;
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
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseFragment;
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
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.csh.colourful.life.utils.KeyBoardUtils;
import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;
import q.rorbin.badgeview.QBadgeView;

import static com.community.activity.DynamicsDetailsActivity.DYNAMICS_DETAILS;
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
public class CommunityDynamicsFragment extends BaseFragment implements View.OnClickListener, NewHttpResponse {

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

    private CommunityDynamicsAdapter communityDynamicsAdapter;


    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_community_dynamics;
    }

    @Override
    protected void initView(View rootView) {
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
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                int topRowVerticalPosition =
                        (recyclerView == null || recyclerView.getChildCount() == 0) ? 0 : recyclerView.getChildAt(0).getTop();
                dynamics_refresh_layout.setEnabled(topRowVerticalPosition >= 0);

            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
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
        communityDynamicsModel.getCommunityDynamicList(0, page, year, true, this);
        newUserModel.getIsRealName(1, false, CommunityDynamicsFragment.this);
        communityDynamicsModel.getReportContent(2, CommunityDynamicsFragment.this);
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
                    CommunityDynamicsListEntity.ContentBean.DataBean dataBean = dynamicContentList.get(i);
                    Intent intent = new Intent(getActivity(), DynamicsDetailsActivity.class);
                    intent.putExtra(DYNAMICS_DETAILS, dataBean);
                    startActivity(intent);
                }
            });
        }
    }

    private List<CommunityDynamicsListEntity.ContentBean.DataBean> dynamicContentList = new ArrayList<>();

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
                boolean dataEmpty = false;
                if (!TextUtils.isEmpty(result)) {
                    CommunityDynamicsListEntity communityDynamicsListEntity = GsonUtils.gsonToBean(result, CommunityDynamicsListEntity.class);
                    CommunityDynamicsListEntity.ContentBean contentBean = communityDynamicsListEntity.getContent();
                    total = contentBean.getTotal();
                    year = contentBean.getYear();
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
                    boolean hasMore = true;
                    if (TextUtils.isEmpty(year) && total < 10) {
                        //不能进行分页 暂无更多动态
                        hasMore = false;
                    }
                    if (null == communityDynamicsAdapter) {
                        communityDynamicsAdapter = new CommunityDynamicsAdapter(getActivity(), dynamicContentList);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
                        rv_community_dynamics.setLayoutManager(linearLayoutManager);
                        ((SimpleItemAnimator) rv_community_dynamics.getItemAnimator()).setSupportsChangeAnimations(false);
                        rv_community_dynamics.setAdapter(communityDynamicsAdapter);
                    } else {
                        communityDynamicsAdapter.notifyDataSetChanged();
                    }
                    setDynamicsListener();
                    //进行数据适配器的展示
                    rv_community_dynamics.loadMoreFinish(dataEmpty, hasMore);
                }
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
                communityDynamicsAdapter.notifyItemChanged(position, dynamicContentList.size());
                ToastUtil.toastShow(getActivity(), "动态删除成功");
                if (dynamicContentList.size() == 0) {
                    dynamics_data_layout.setVisibility(View.GONE);
                    dynamics_empty_layout.setVisibility(View.VISIBLE);
                    //用户没有动态
                }
                break;
            case 6:
                ToastUtil.toastShow(getActivity(), "评论删除成功");
                CommunityDynamicsListEntity.ContentBean.DataBean delDataBean = dynamicContentList.get(position);
                delDataBean.getComment().remove(commentPosition);
                int delComment_Count = delDataBean.getComment_count();
                delDataBean.setComment_count(--delComment_Count);
                communityDynamicsAdapter.notifyItemChanged(position);
                break;
            case 7:
                CommunityDynamicsListEntity.ContentBean.DataBean dataBean = dynamicContentList.get(position);
                int zanCount = dataBean.getZan_count();
                dataBean.setZan_count(++zanCount);
                dataBean.setIs_zan("1");
                communityDynamicsAdapter.notifyItemChanged(position);
                break;
            case 8:
                CommunityDynamicsListEntity.ContentBean.DataBean cancelDataBean = dynamicContentList.get(position);
                int cancelCount = cancelDataBean.getZan_count();
                cancelDataBean.setZan_count(--cancelCount);
                cancelDataBean.setIs_zan("2");
                communityDynamicsAdapter.notifyItemChanged(position);
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
                commentBean.setFrom_mobile(shared.getString(UserAppConst.Colour_login_mobile, ""));
                int userId = shared.getInt(UserAppConst.Colour_User_id, 0);
                String fromSourceId = String.valueOf(userId);
                commentBean.setFrom_id(fromSourceId);
                commentBean.setFrom_nickname(shared.getString(UserAppConst.Colour_NIACKNAME, ""));
                int comment_count = commentDataBean.getComment_count();
                commentDataBean.setComment_count(++comment_count);
                if (TextUtils.isEmpty(fromUuid)) {
                    commentDataList.add(commentBean);
                    ToastUtil.toastShow(getActivity(), "评论成功");
                } else {
                    //回复别人的评论
                    ToastUtil.toastShow(getActivity(), "回复成功");
                    int commentSize = commentDataList.size();
                    int insertPos = 0;
                    for (int j = commentSize - 1; j >= 0; j--) {
                        CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean replyCommentBean = commentDataList.get(j);
                        if (replyCommentBean.getFrom_id().equals(fromSourceId)) {
                            insertPos = j;
                            break;
                        }
                    }
                    commentBean.setTo_nickname(fromNickName);
                    commentBean.setTo_id(fromUuid);
                    if (insertPos == commentSize - 1) {
                        commentDataList.add(commentBean);
                    } else {
                        commentDataList.add(insertPos, commentBean);
                    }

                }
                communityDynamicsAdapter.notifyItemChanged(position);
                break;
        }
    }

    private int position = 0;//动态的itemPosition
    private int commentPosition = 0;//评论的itemPosition
    private String content;//评论的内容
    private String fromUuid;//回复评论人id
    private String fromNickName;//回复评论人昵称

    public void onEvent(Object event) {
        String is_identity = shared.getString(UserAppConst.COLOUR_DYNAMICS_REAL_IDENTITY, "0");
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
                communityDynamicsModel.getCommunityDynamicList(0, page, year, true, CommunityDynamicsFragment.this);
            case SIGN_IN_SUCCESS:
                if (isFirst) {
                    year = "";
                    page = 1;
                    communityDynamicsModel.getCommunityDynamicList(0, page, year, true, CommunityDynamicsFragment.this);
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
                        communityDynamicsAdapter.notifyItemChanged(position, dynamicContentList.size());
                        if (dynamicContentList.size() == 0) {
                            dynamics_data_layout.setVisibility(View.GONE);
                            dynamics_empty_layout.setVisibility(View.VISIBLE);
                        }
                    } else {
                        CommunityDynamicsListEntity.ContentBean.DataBean dataBean = (CommunityDynamicsListEntity.ContentBean.DataBean) message.obj;
                        dynamicContentList.set(position, dataBean);
                        communityDynamicsAdapter.notifyItemChanged(position);
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

    private void showDelDynamics(String sourceId, String commentId) {
        DeleteNoticeDialog deleteNoticeDialog = new DeleteNoticeDialog(getActivity(), R.style.custom_dialog_theme);
        deleteNoticeDialog.show();
        deleteNoticeDialog.btn_define.setOnClickListener(v -> {
            //删除动态  删除评论
            deleteNoticeDialog.dismiss();
            if (TextUtils.isEmpty(commentId)) {
                communityDynamicsModel.delUserDynamic(5, sourceId, CommunityDynamicsFragment.this);
            } else {
                communityDynamicsModel.delOwnerComment(6, sourceId, commentId, CommunityDynamicsFragment.this);
            }
        });
    }


    private void showTipOffDialog(String sourceId, String commentId) {
        TipTypeListDialog tipTypeListDialog = new TipTypeListDialog(getActivity(), R.style.custom_dialog_theme);
        tipTypeListDialog.show();
        String tipOffListCache = shared.getString(UserAppConst.COLOUR_DYNAMICS_TIPOFF_LIST, "");
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
        if (shared.getBoolean(UserAppConst.IM_APPLY_FRIEND, false)) {
            newFriendApplyCount = CacheApplyRecorderHelper.instance().toQueryApplyRecordSize(getActivity(), "0");
        }
        int unReadNoticeCount = shared.getInt(COLOUR_DYNAMICS_NOTICE_NUMBER, 0);
        unReadBadgeView.setBadgeNumber(totalUnReadMsgCount + newFriendApplyCount + unReadNoticeCount);
        ((MainActivity) getActivity()).showUnReadMsg(unReadNoticeCount);
    }
}
