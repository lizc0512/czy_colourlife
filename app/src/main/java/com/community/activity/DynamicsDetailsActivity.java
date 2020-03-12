package com.community.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseFragmentActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.BeeFramework.view.CircleImageView;
import com.BeeFramework.view.Util;
import com.cashier.adapter.ViewPagerAdapter;
import com.community.adapter.CommunityImageAdapter;
import com.community.adapter.CommunityTipOffAdapter;
import com.community.entity.CommunityDynamicIdEntity;
import com.community.entity.CommunityDynamicsListEntity;
import com.community.entity.CommunityTipOffEntity;
import com.community.fragment.CommunityCommentListFragment;
import com.community.fragment.CommunityLikeListFragment;
import com.community.model.CommunityDynamicsModel;
import com.community.utils.RealIdentifyDialogUtil;
import com.community.view.DeleteNoticeDialog;
import com.community.view.TipTypeListDialog;
import com.community.view.TipoffsCommentDialog;
import com.external.eventbus.EventBus;
import com.im.activity.IMCustomerInforActivity;
import com.im.activity.IMFriendInforActivity;
import com.im.activity.IMUserSelfInforActivity;
import com.im.helper.CacheFriendInforHelper;
import com.nohttp.utils.GlideImageLoader;
import com.nohttp.utils.GridSpacingItemDecoration;
import com.nohttp.utils.GsonUtils;
import com.user.UserAppConst;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.csh.colourful.life.utils.KeyBoardUtils;
import cn.net.cyberway.R;

import static android.view.View.GONE;
import static com.community.fragment.CommunityDynamicsFragment.CALLBACL_COMMENT_DYNAMIC;
import static com.im.activity.IMFriendInforActivity.USERIDTYPE;

/**
 * @name ${yuansk}
 * @class name：com.cashier.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2017/12/7 10:35
 * @change
 * @chang time
 * @class describe   动态详情
 */

public class DynamicsDetailsActivity extends BaseFragmentActivity implements View.OnClickListener, NewHttpResponse {


    public static final String DYNAMICS_DETAILS = "dynamics_details";

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private AppBarLayout dynamics_appbar;
    private TabLayout dynamics_tabs;
    private ViewPager dynamics_viewpager;

    private CircleImageView iv_dynamics_user_pics;
    private TextView tv_dynamics_user_name;
    private TextView tv_dynamics_user_community;
    private ImageView iv_dynamics_user_operate;
    private TextView tv_dynamics_content_details;
    private TextView tv_del_owner_dynamics;
    private RecyclerView rv_dynamics_images;
    private View view_dynamics_weight;
    private TextView tv_dynamics_publish_time;
    private TextView tv_dynamics_comment;
    private TextView tv_dynamics_like;

    private LinearLayout ll_comment_input;
    private EditText feed_comment_edittext;
    private TextView feed_comment_submit;


    private String[] tabTitleArray = null;
    private List<Fragment> fragmentList = new ArrayList<>();
    private CommunityLikeListFragment communityLikeListFragment;
    private CommunityCommentListFragment communityCommentListFragment;
    private String current_user_uuid;
    private String current_user_id;
    private CommunityDynamicsModel communityDynamicsModel;
    private CommunityDynamicsListEntity.ContentBean.DataBean dataBean;

    public String source_id;
    public String is_zan;
    public String comment_id;
    public String to_userid;
    public String to_nickname;
    public int commentPosition = -1;
    private String content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dynamics_details);
        current_user_uuid = shared.getString(UserAppConst.Colour_User_uuid, "");
        current_user_id = String.valueOf(shared.getInt(UserAppConst.Colour_User_id, 0));
        initView();
        communityDynamicsModel = new CommunityDynamicsModel(DynamicsDetailsActivity.this);
    }

    private void initView() {
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        dynamics_appbar = findViewById(R.id.dynamics_appbar);
        dynamics_tabs = findViewById(R.id.dynamics_tabs);
        dynamics_viewpager = findViewById(R.id.dynamics_viewpager);
        iv_dynamics_user_pics = findViewById(R.id.iv_dynamics_user_pics);
        tv_dynamics_user_name = findViewById(R.id.tv_dynamics_user_name);
        tv_dynamics_user_community = findViewById(R.id.tv_dynamics_user_community);
        iv_dynamics_user_operate = findViewById(R.id.iv_dynamics_user_operate);
        tv_dynamics_content_details = findViewById(R.id.tv_dynamics_content_details);
        rv_dynamics_images = findViewById(R.id.rv_dynamics_images);
        view_dynamics_weight = findViewById(R.id.view_dynamics_weight);
        tv_dynamics_publish_time = findViewById(R.id.tv_dynamics_publish_time);
        tv_del_owner_dynamics = findViewById(R.id.tv_del_owner_dynamics);
        tv_dynamics_comment = findViewById(R.id.tv_dynamics_comment);
        tv_dynamics_like = findViewById(R.id.tv_dynamics_like);
        ll_comment_input = findViewById(R.id.ll_comment_input);
        feed_comment_edittext = findViewById(R.id.feed_comment_edittext);
        feed_comment_submit = findViewById(R.id.feed_comment_submit);


        user_top_view_back.setOnClickListener(this::onClick);
        iv_dynamics_user_pics.setOnClickListener(this::onClick);
        tv_dynamics_user_name.setOnClickListener(this::onClick);
        tv_dynamics_user_community.setOnClickListener(this::onClick);
        iv_dynamics_user_operate.setOnClickListener(this::onClick);
        tv_del_owner_dynamics.setOnClickListener(this::onClick);
        tv_dynamics_comment.setOnClickListener(this::onClick);
        tv_dynamics_like.setOnClickListener(this::onClick);
        feed_comment_submit.setOnClickListener(this::onClick);
        user_top_view_title.setText(getResources().getString(R.string.community_dynamics_details));
        dynamics_appbar.post(new Runnable() {
            @Override
            public void run() {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams) dynamics_appbar.getLayoutParams();
                AppBarLayout.Behavior behavior = (AppBarLayout.Behavior) params.getBehavior();
                behavior.setDragCallback(new AppBarLayout.Behavior.DragCallback() {
                    @Override
                    public boolean canDrag(@NonNull AppBarLayout appBarLayout) {
                        return true;
                    }
                });
            }
        });
        Intent intent = getIntent();
        dataBean = (CommunityDynamicsListEntity.ContentBean.DataBean) intent.getSerializableExtra(DYNAMICS_DETAILS);
        communityLikeListFragment = new CommunityLikeListFragment();
        fragmentList.add(communityLikeListFragment);
        communityCommentListFragment = new CommunityCommentListFragment();
        fragmentList.add(communityCommentListFragment);
        tabTitleArray = new String[2];
        tabTitleArray[0] = getResources().getString(R.string.community_like);
        tabTitleArray[1] = getResources().getString(R.string.community_comment);
        for (int i = 0; i < tabTitleArray.length; i++) {
            dynamics_tabs.addTab(dynamics_tabs.newTab().setText(tabTitleArray[i]));
        }
        dynamics_tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        dynamics_tabs.setSelectedTabIndicatorHeight(4);
        dynamics_tabs.setTabIndicatorFullWidth(false);
        dynamics_tabs.setSelectedTabIndicatorColor(Color.parseColor("#3385FF"));
        dynamics_tabs.setTabTextColors(Color.parseColor("#999999"), Color.parseColor("#3385FF"));
        dynamics_tabs.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPagerAdapter adapter = new ViewPagerAdapter(
                getSupportFragmentManager(), this, fragmentList, tabTitleArray);
        dynamics_viewpager.setAdapter(adapter);
        dynamics_viewpager.setOffscreenPageLimit(fragmentList.size());
        dynamics_tabs.setupWithViewPager(dynamics_viewpager);


        String avatar = dataBean.getAvatar();
        String community_name = dataBean.getCommunity_name();
        String nick_name = dataBean.getNickname();
        comment_count = dataBean.getComment_count();
        zan_count = dataBean.getZan_count();
        zanBeanList = dataBean.getZan();
        commentBeanList = dataBean.getComment();
        long create_time = dataBean.getCreated_at();
        String publish_uuid = dataBean.getUser_uuid();
        String content = dataBean.getContent();
        source_id = dataBean.getSource_id();
        is_zan = dataBean.getIs_zan();
        GlideImageLoader.loadImageDefaultDisplay(DynamicsDetailsActivity.this, avatar, iv_dynamics_user_pics, R.drawable.icon_default_portrait, R.drawable.icon_default_portrait);
        tv_dynamics_user_name.setText(nick_name);
        tv_dynamics_user_community.setText(community_name);
        if (!TextUtils.isEmpty(content)) {
            tv_dynamics_content_details.setVisibility(View.VISIBLE);
            tv_dynamics_content_details.setText(content);
        } else {
            tv_dynamics_content_details.setVisibility(GONE);
        }
        tv_dynamics_publish_time.setText(TimeUtil.formatHomeTime(create_time));
        if (current_user_uuid.equals(publish_uuid)) {
            tv_del_owner_dynamics.setVisibility(View.VISIBLE);
            iv_dynamics_user_operate.setVisibility(GONE);
        } else {
            tv_del_owner_dynamics.setVisibility(GONE);
            iv_dynamics_user_operate.setVisibility(View.VISIBLE);
        }

        List<String> imgList = dataBean.getExtra();
        int imgSize = imgList == null ? 0 : imgList.size();
        if (imgSize == 0) {
            rv_dynamics_images.setVisibility(GONE);
        } else {
            rv_dynamics_images.setVisibility(View.VISIBLE);
            int extra_type = dataBean.getExtra_type();
            CommunityImageAdapter communityImageAdapter = new CommunityImageAdapter(DynamicsDetailsActivity.this, (ArrayList<String>) imgList, extra_type, 32);
            int row = imgSize == 4 ? 2 : 3;//如果4张图片显示2列
            if (row == 2) {
                view_dynamics_weight.setVisibility(View.VISIBLE);
            } else {
                view_dynamics_weight.setVisibility(GONE);
            }
            GridLayoutManager gridLayoutManager = new GridLayoutManager(DynamicsDetailsActivity.this, row);
            rv_dynamics_images.setLayoutManager(gridLayoutManager);
            rv_dynamics_images.setAdapter(communityImageAdapter);
        }
        setZanStatus();
        setCommentCount();
        communityLikeListFragment.showLikeList(zanBeanList);
        communityCommentListFragment.showCommentList(commentBeanList);
        KeyBoardUtils.closeKeybord(feed_comment_edittext, DynamicsDetailsActivity.this);
    }

    private List<CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean> zanBeanList;
    private List<CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean> commentBeanList;
    private int zan_count;
    private int comment_count;

    private void setZanStatus() {
        Drawable dra = null;
        if ("1".equals(is_zan)) {
            //表示用户未点赞
            dra = getResources().getDrawable(R.drawable.community_dynamics_like);
        } else {
            //用户已经点赞过
            dra = getResources().getDrawable(R.drawable.community_dynamics_unlike);
        }
        dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
        tv_dynamics_like.setCompoundDrawables(dra, null, null, null);
        if (zan_count == 0) {
            tv_dynamics_like.setText(getResources().getString(R.string.community_title_like));
        } else {
            tv_dynamics_like.setText(String.valueOf(zan_count));
        }
    }

    private void setCommentCount() {
        if (comment_count == 0) {
            tv_dynamics_comment.setText(getResources().getString(R.string.community_comment));
        } else {
            tv_dynamics_comment.setText(String.valueOf(comment_count));
        }
    }

    @Override
    public void onClick(View v) {
        String is_identity = shared.getString(UserAppConst.COLOUR_DYNAMICS_REAL_IDENTITY, "0");
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.iv_dynamics_user_operate://举报
                if ("1".equals(is_identity)) {
                    showTipCommentDelDialog();
                } else {
                    RealIdentifyDialogUtil.showGoIdentifyDialog(DynamicsDetailsActivity.this);
                }
                break;
            case R.id.tv_dynamics_like://点赞
                if ("1".equals(is_identity)) {
                    if ("2".equals(is_zan)) {
                        communityDynamicsModel.likeUserDynamic(6, source_id, DynamicsDetailsActivity.this);
                    } else {
                        communityDynamicsModel.unlikeUserDynamic(7, source_id, DynamicsDetailsActivity.this);
                    }
                } else {
                    RealIdentifyDialogUtil.showGoIdentifyDialog(DynamicsDetailsActivity.this);
                }
                break;
            case R.id.tv_dynamics_comment://键盘弹出
                feed_comment_edittext.setFocusable(true);
                feed_comment_edittext.setFocusableInTouchMode(true);
                feed_comment_edittext.requestFocus();
                feed_comment_edittext.setHint(getResources().getString(R.string.community_comment_hint));
                KeyBoardUtils.openKeybord(feed_comment_edittext, DynamicsDetailsActivity.this);
                break;
            case R.id.tv_del_owner_dynamics://删除自己动态
                if ("1".equals(is_identity)) {
                    showDelDynamics();
                } else {
                    RealIdentifyDialogUtil.showGoIdentifyDialog(DynamicsDetailsActivity.this);
                }
                break;
            case R.id.feed_comment_submit:
                if ("1".equals(is_identity)) {
                    content = feed_comment_edittext.getText().toString().trim();
                    if (!TextUtils.isEmpty(content)) {
                        if (content.length() > 300) {
                            ToastUtil.toastShow(DynamicsDetailsActivity.this, "输入的内容长度不能超过300字");
                        } else {
                            KeyBoardUtils.closeKeybord(feed_comment_edittext, DynamicsDetailsActivity.this);
                            communityDynamicsModel.commentDynamic(5, content, source_id, to_userid, DynamicsDetailsActivity.this::OnHttpResponse);
                        }
                    } else {
                        ToastUtil.toastShow(DynamicsDetailsActivity.this, "输入的内容不能为空");
                    }
                } else {
                    RealIdentifyDialogUtil.showGoIdentifyDialog(DynamicsDetailsActivity.this);
                }
                break;
            case R.id.iv_dynamics_user_pics:
            case R.id.tv_dynamics_user_name:
            case R.id.tv_dynamics_user_community:
                jumpUserInforPage();
                break;
        }
    }

    private void jumpUserInforPage() {
        String from_uuid = dataBean.getUser_uuid();
        Intent intent = null;
        if (current_user_uuid.equals(from_uuid)) {
            intent = new Intent(DynamicsDetailsActivity.this, IMUserSelfInforActivity.class);
        } else {
            List<String> friendUserIdList = CacheFriendInforHelper.instance().toQueryFriendUUIdList(DynamicsDetailsActivity.this);
            if (friendUserIdList.contains(from_uuid)) {
                intent = new Intent(DynamicsDetailsActivity.this, IMFriendInforActivity.class);
            } else {
                intent = new Intent(DynamicsDetailsActivity.this, IMCustomerInforActivity.class);
            }
        }
        intent.putExtra(USERIDTYPE, 0);
        intent.putExtra(IMFriendInforActivity.USERUUID, from_uuid);
        startActivity(intent);
    }


    public void showDelDynamics() {
        DeleteNoticeDialog deleteNoticeDialog = new DeleteNoticeDialog(DynamicsDetailsActivity.this, R.style.custom_dialog_theme);
        deleteNoticeDialog.show();
        deleteNoticeDialog.btn_define.setOnClickListener(v -> {
            //删除动态  删除评论
            deleteNoticeDialog.dismiss();
            if (TextUtils.isEmpty(comment_id)) {
                if (commentPosition == -1) {
                    communityDynamicsModel.delUserDynamic(3, source_id, DynamicsDetailsActivity.this);
                } else { //处理自评论 自删除时 评论的id没生成
                    delDynamicCommentSuccess();
                }
            } else {
                communityDynamicsModel.delOwnerComment(4, source_id, comment_id, DynamicsDetailsActivity.this);
            }
            comment_id = "";
        });
    }

    public void setDelCommentId(String comment_id) {
        this.comment_id = comment_id;
        showDelDynamics();
    }

    public void setTipsOffCommentId(String comment_id) {
        String is_identity = shared.getString(UserAppConst.COLOUR_DYNAMICS_REAL_IDENTITY, "0");
        if ("1".equals(is_identity)) {
            this.comment_id = comment_id;
            showTipCommentDelDialog();
        } else {
            RealIdentifyDialogUtil.showGoIdentifyDialog(DynamicsDetailsActivity.this);
        }

    }

    public void setCommentReply(String user_id, String from_nickename) {
        to_userid = user_id;
        to_nickname = from_nickename;
        feed_comment_edittext.setFocusable(true);
        feed_comment_edittext.setFocusableInTouchMode(true);
        feed_comment_edittext.requestFocus();
        KeyBoardUtils.openKeybord(feed_comment_edittext, DynamicsDetailsActivity.this);
        if (!TextUtils.isEmpty(from_nickename)) {
            feed_comment_edittext.setHint("回复" + from_nickename);
        } else {
            feed_comment_edittext.setHint("回复");
        }
    }

    public void showTipCommentDelDialog() {
        TipoffsCommentDialog tipoffsCommentDialog = new TipoffsCommentDialog(DynamicsDetailsActivity.this, R.style.custom_dialog_theme);
        tipoffsCommentDialog.show();
        tipoffsCommentDialog.tv_dynamics_tipoffs.setText("举报");
        tipoffsCommentDialog.tv_dynamics_tipoffs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //显示举报列表的dialog
                tipoffsCommentDialog.dismiss();
                showTipOffDialog();
            }
        });
    }

    private void showTipOffDialog() {
        TipTypeListDialog tipTypeListDialog = new TipTypeListDialog(DynamicsDetailsActivity.this, R.style.custom_dialog_theme);
        tipTypeListDialog.show();
        String tipOffListCache = shared.getString(UserAppConst.COLOUR_DYNAMICS_TIPOFF_LIST, "");
        if (!TextUtils.isEmpty(tipOffListCache)) {
            try {
                CommunityTipOffEntity communityTipOffEntity = GsonUtils.gsonToBean(tipOffListCache, CommunityTipOffEntity.class);
                List<CommunityTipOffEntity.ContentBean> tipOffContentList = communityTipOffEntity.getContent();
                CommunityTipOffAdapter communityTipOffAdapter = new CommunityTipOffAdapter(DynamicsDetailsActivity.this, tipOffContentList);
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(DynamicsDetailsActivity.this, LinearLayoutManager.VERTICAL, false);
                tipTypeListDialog.rv_tipoffs_type.setLayoutManager(linearLayoutManager);
                tipTypeListDialog.rv_tipoffs_type.setAdapter(communityTipOffAdapter);
                communityTipOffAdapter.setOnItemClickListener(new OnItemClickListener() {
                    @Override
                    public void onItemClick(int i) {
                        if (TextUtils.isEmpty(comment_id)) {
                            communityDynamicsModel.reportViolationDynamic(1, source_id, tipOffContentList.get(i).getReport_type(), DynamicsDetailsActivity.this::OnHttpResponse);
                        } else {
                            communityDynamicsModel.reportViolationComment(2, source_id, comment_id, tipOffContentList.get(i).getReport_type(), DynamicsDetailsActivity.this::OnHttpResponse);
                            comment_id = "";
                        }
                        tipTypeListDialog.dismiss();
                    }
                });
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 1:
                ToastUtil.toastShow(DynamicsDetailsActivity.this, "动态举报成功");
                break;
            case 2:
                ToastUtil.toastShow(DynamicsDetailsActivity.this, "评论举报成功");
                break;
            case 3://删除动态
                ToastUtil.toastShow(DynamicsDetailsActivity.this, "动态删除成功");
                callBackDynamicList(1);
                break;
            case 4://删除评论
                delDynamicCommentSuccess();
                break;
            case 5://评论 回复
                String commentId = "";
                try {
                    CommunityDynamicIdEntity communityDynamicIdEntity = GsonUtils.gsonToBean(result, CommunityDynamicIdEntity.class);
                    commentId = communityDynamicIdEntity.getContent().getComment_id();
                } catch (Exception e) {

                }
                CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean commentBean = new CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean();
                commentBean.setContent(content);
                commentBean.setId(commentId);
                commentBean.setFrom_mobile(shared.getString(UserAppConst.Colour_login_mobile, ""));
                commentBean.setFrom_id(current_user_id);
                commentBean.setSource_id(source_id);
                long currentTime = System.currentTimeMillis() / 1000;
                commentBean.setCreated_at(currentTime);
                commentBean.setFrom_nickname(shared.getString(UserAppConst.Colour_NIACKNAME, ""));
                dataBean.setComment_count(++comment_count);
                if (TextUtils.isEmpty(to_userid)) {
                    ToastUtil.toastShow(DynamicsDetailsActivity.this, "评论成功");
                    if (dynamics_viewpager.getCurrentItem() == 0) {
                        dynamics_viewpager.setCurrentItem(1, true);
                    }
                } else {
                    //回复别人的评论
                    ToastUtil.toastShow(DynamicsDetailsActivity.this, "回复成功");
                    commentBean.setTo_nickname(to_nickname);
                    commentBean.setTo_id(to_userid);
                }
                commentBeanList.add(commentBean);
                dataBean.setComment(commentBeanList);
                setCommentCount();
                communityCommentListFragment.addDelRelay(commentBean);
                callBackDynamicList(2);
                to_userid = "";
                feed_comment_edittext.setHint(getResources().getString(R.string.community_comment_hint));
                feed_comment_edittext.setText("");
                break;
            case 6://点赞
                CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean likeZanBean = new CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean();
                likeZanBean.setFrom_id(current_user_id);
                likeZanBean.setFrom_nickname(shared.getString(UserAppConst.Colour_NIACKNAME, ""));
                likeZanBean.setFrom_avatar(shared.getString(UserAppConst.Colour_head_img, ""));
                zanBeanList.add(likeZanBean);
                is_zan = "1";
                dataBean.setIs_zan(is_zan);
                dataBean.setZan(zanBeanList);
                dataBean.setZan_count(++zan_count);
                setZanStatus();
                communityLikeListFragment.clickZan(likeZanBean);
                callBackDynamicList(2);
                break;
            case 7://取消点赞
                int delPos = 0;
                for (int q = 0; q < zanBeanList.size(); q++) {
                    CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean zanBean = zanBeanList.get(q);
                    if (current_user_id.equals(zanBean.getFrom_id())) {
                        delPos = q;
                        break;
                    }
                }
                zanBeanList.remove(delPos);
                communityLikeListFragment.cancelLike(delPos);
                is_zan = "2";
                dataBean.setZan_count(--zan_count);
                dataBean.setIs_zan(is_zan);
                dataBean.setZan(zanBeanList);
                setZanStatus();
                callBackDynamicList(2);
                break;
        }
    }

    private void delDynamicCommentSuccess() {
        ToastUtil.toastShow(DynamicsDetailsActivity.this, "评论删除成功");
        commentBeanList.remove(commentPosition);
        dataBean.setComment_count(--comment_count);
        communityCommentListFragment.delComment(commentPosition);
        setCommentCount();
        callBackDynamicList(2);
        commentPosition = -1;
    }

    private void callBackDynamicList(int type) {
        Bundle callBackBundle = new Bundle();
        callBackBundle.putString("sourceId", source_id);
        callBackBundle.putInt("position", -1);
        Message callbackMessage = Message.obtain();
        callbackMessage.what = CALLBACL_COMMENT_DYNAMIC;
        callbackMessage.arg1 = type;
        callbackMessage.obj = dataBean;
        callbackMessage.setData(callBackBundle);
        EventBus.getDefault().post(callbackMessage);
        if (type == 1) {
            finish();
        }
    }
}
