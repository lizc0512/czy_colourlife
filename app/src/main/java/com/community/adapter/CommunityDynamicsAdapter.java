package com.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.view.CircleImageView;
import com.community.entity.CommunityDynamicsListEntity;
import com.community.view.MoreTextView;
import com.external.eventbus.EventBus;
import com.im.activity.IMCustomerInforActivity;
import com.im.activity.IMFriendInforActivity;
import com.im.activity.IMUserSelfInforActivity;
import com.im.helper.CacheFriendInforHelper;
import com.nohttp.utils.GlideImageLoader;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

import static android.view.View.GONE;
import static cn.net.cyberway.home.view.HomeViewUtils.showCommunityActivity;
import static com.community.fragment.CommunityDynamicsFragment.DIRECT_COMMENT_DYNAMIC;
import static com.community.fragment.CommunityDynamicsFragment.DIRECT_DELETE_DYNAMIC;
import static com.community.fragment.CommunityDynamicsFragment.DIRECT_LIKE_DYNAMIC;
import static com.community.fragment.CommunityDynamicsFragment.TIPOFF_COMMENT_DYNAMIC;
import static com.im.activity.IMFriendInforActivity.USERIDTYPE;

/**
 * @ProjectName:
 * @Package: com.community.adapter
 * @ClassName: CommunityDynamicsAdapter
 * @Description: 社区动态列表的adapter
 * @Author: yuansk
 * @CreateDate: 2020/2/25 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/25 9:47
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public class CommunityDynamicsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<CommunityDynamicsListEntity.ContentBean.DataBean> dynamicContentList;
    private Context mContext;
    private OnItemClickListener onClickListener;
    private String current_user_uuid;

    public CommunityDynamicsAdapter(Context context, List<CommunityDynamicsListEntity.ContentBean.DataBean> dynamicContentList) {
        this.mContext = context;
        this.dynamicContentList = dynamicContentList;

    }

    public void setUserUUId(String userUUId) {
        current_user_uuid = userUUId;
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == 2) {
            CommunityActivityViewHolder viewHolder = new CommunityActivityViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.community_activity_layout, parent, false));
            viewHolder.onClickListener = onClickListener;
            return viewHolder;
        } else {
            DefaultViewHolder viewHolder = new DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dynamics_list, parent, false));
            viewHolder.onClickListener = onClickListener;
            return viewHolder;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return dynamicContentList.get(position).getList_type();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position, @NonNull List<Object> payloads) {
        if (payloads.isEmpty()) {
            //payloads 为 空，说明是更新整个 ViewHolder
            onBindViewHolder(viewHolder, position);
        } else {
            String loadsValue = payloads.get(0).toString();
            CommunityDynamicsListEntity.ContentBean.DataBean dataBean = dynamicContentList.get(position);
            int list_type = dataBean.getList_type();
            String is_zan = dataBean.getIs_zan();
            int zan_count = dataBean.getZan_count();
            String source_id = dataBean.getSource_id();
            if (list_type == 2) {
                //刷新活动的状态  参与人数等
                if ("activity".equals(loadsValue)) {
                    CommunityActivityViewHolder holder = (CommunityActivityViewHolder) viewHolder;
                    showActivityContent(dataBean, holder);
                } else {
                    onBindViewHolder(viewHolder, position);
                }
            } else {
                DefaultViewHolder holder = (DefaultViewHolder) viewHolder;
                if ("like".equals(loadsValue)) {
                    showLikeContent(is_zan, zan_count, source_id, position, holder.tv_dynamics_like);
                } else if ("comment".equals(loadsValue)) {
                    showCommentContent(dataBean, position, holder.tv_dynamics_comment, holder.rv_dynamics_user_comments, holder.dynamic_divider_view, holder.itemView);
                }
            }
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int position) {
        CommunityDynamicsListEntity.ContentBean.DataBean dataBean = dynamicContentList.get(position);
        int list_type = dataBean.getList_type();
        int zan_count = dataBean.getZan_count();
        String publish_uuid = dataBean.getUser_uuid();
        String publish_user_id = dataBean.getUser_id();
        String content = dataBean.getContent();
        String source_id = dataBean.getSource_id();
        String is_zan = dataBean.getIs_zan();
        if (list_type == 2) {
            CommunityActivityViewHolder holder = (CommunityActivityViewHolder) viewHolder;
            String ac_banner = dataBean.getAc_banner();
            String oproperty = dataBean.getAc_oproperty();
            GlideImageLoader.loadTopRightCornerImageView(mContext, ac_banner, holder.iv_activity_image);
            if (TextUtils.isEmpty(oproperty)) {
                holder.iv_activity_type.setText(mContext.getResources().getString(R.string.community_activity_officicl));
            } else {
                holder.iv_activity_type.setText(oproperty);
            }
            String ac_tag = dataBean.getAc_tag();
            if (mContext.getResources().getString(R.string.community_activity_free).equals(ac_tag)) {
                holder.tv_activity_fee.setVisibility(View.VISIBLE);
                holder.tv_activity_fee.setText(ac_tag);
            } else {
                holder.tv_activity_fee.setVisibility(GONE);
            }
            holder.tv_activity_title.setText(dataBean.getAc_title());
            holder.tv_activity_address.setText(mContext.getResources().getString(R.string.community_activity_item_address) + dataBean.getAc_address());
            holder.tv_activity_date.setText(mContext.getResources().getString(R.string.community_activity_item_date) + TimeUtil.getYearTime(dataBean.getStop_apply_time() * 1000, "yyyy年MM月dd日"));
            showActivityContent(dataBean, holder);
        } else {
            int extra_type = dataBean.getExtra_type();
            DefaultViewHolder holder = (DefaultViewHolder) viewHolder;
            if (extra_type == 3) {
                holder.tv_dynamics_text_content.setVisibility(View.GONE);
                holder.dynamic_image_layout.setVisibility(GONE);
                holder.share_activity_layout.setVisibility(View.VISIBLE);
                List<String> shareList = dataBean.getExtra();
                String shareImageLogo = "";
                String shareDesc = "";
                String shareUrl = null;
                if (shareList.size() >= 3) {
                    shareImageLogo = shareList.get(0);
                    shareDesc = shareList.get(1);
                    shareUrl = shareList.get(2);
                } else {
                    shareUrl = "http://m.colourlife.com/doubleCode?code=1390620762&sign=553FCC9A69A55F1BE686F6AF85E42154";
                }
                GlideImageLoader.loadImageDefaultDisplay(mContext, shareImageLogo, holder.iv_share_logo, R.drawable.share_default_logo, R.drawable.share_default_logo);
                holder.tv_share_title.setText(shareDesc);
                String finalShareUrl = shareUrl;
                holder.share_activity_layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LinkParseUtil.parse(mContext, finalShareUrl, "");
                    }
                });
            } else {
                holder.tv_dynamics_text_content.setVisibility(View.VISIBLE);
                holder.dynamic_image_layout.setVisibility(View.VISIBLE);
                holder.share_activity_layout.setVisibility(GONE);
                if (!TextUtils.isEmpty(content)) {
                    holder.tv_dynamics_text_content.setVisibility(View.VISIBLE);
                    holder.tv_dynamics_text_content.setText(content);
                } else {
                    holder.tv_dynamics_text_content.setVisibility(GONE);
                }
                ViewGroup.LayoutParams params = holder.tv_dynamics_text_content.getLayoutParams();
                params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
                holder.tv_dynamics_text_content.setLayoutParams(params);
                List<String> imgList = dataBean.getExtra();
                int imgSize = imgList == null ? 0 : imgList.size();
                if (imgSize == 0) {
                    holder.rv_dynamics_images.setVisibility(GONE);
                } else {
                    holder.rv_dynamics_images.setVisibility(View.VISIBLE);
                    CommunityImageAdapter communityImageAdapter = new CommunityImageAdapter(mContext, (ArrayList<String>) imgList, extra_type, 40);
                    int row = imgSize == 4 ? 2 : 3;//如果4张图片显示2列
                    if (row == 2) {
                        holder.view_dynamics_weight.setVisibility(View.VISIBLE);
                    } else {
                        holder.view_dynamics_weight.setVisibility(GONE);
                    }
                    GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, row);
                    ((SimpleItemAnimator) holder.rv_dynamics_images.getItemAnimator()).setSupportsChangeAnimations(false);
                    holder.rv_dynamics_images.setLayoutManager(gridLayoutManager);
                    holder.rv_dynamics_images.setAdapter(communityImageAdapter);
                }
            }
            showHeadContent(dataBean, publish_uuid, holder.iv_dynamics_user_pics, holder.tv_dynamics_user_name,
                    holder.tv_dynamics_user_community, holder.tv_del_owner_dynamics, holder.tv_dynamics_publish_time, holder.iv_dynamics_user_operate);
            showTipOffContent(source_id, position, holder.iv_dynamics_user_operate, holder.itemView);
            showDelContent(source_id, position, holder.tv_del_owner_dynamics);
            showLikeContent(is_zan, zan_count, source_id, position, holder.tv_dynamics_like);
            showCommentContent(dataBean, position, holder.tv_dynamics_comment, holder.rv_dynamics_user_comments, holder.dynamic_divider_view, holder.itemView);
            handleUserInforClick(publish_uuid, publish_user_id, holder.iv_dynamics_user_pics, holder.tv_dynamics_user_name, holder.tv_dynamics_user_community);
        }
    }


    /***用户点击相关头像 姓名 小区名称**/
    public void handleUserInforClick(String publish_uuid, String publish_user_id, CircleImageView iv_user_pics, TextView tv_user_name, TextView tv_user_community) {
        iv_user_pics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpUserInforPage(publish_uuid, publish_user_id);
            }
        });
        tv_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpUserInforPage(publish_uuid, publish_user_id);
            }
        });
        tv_user_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpUserInforPage(publish_uuid, publish_user_id);
            }
        });
    }

    /***显示头部内容 和操作 删除的显示隐藏**/
    private void showHeadContent(CommunityDynamicsListEntity.ContentBean.DataBean dataBean, String publish_uuid, ImageView iv_user_pics,
                                 TextView tv_user_name, TextView tv_community_name, TextView tv_del_owner, TextView tv_publish_time, ImageView iv_user_operate) {
        GlideImageLoader.loadImageDefaultDisplay(mContext, dataBean.getAvatar(), iv_user_pics, R.drawable.icon_default_portrait, R.drawable.icon_default_portrait);
        tv_user_name.setText(dataBean.getNickname());
        tv_community_name.setText(dataBean.getCommunity_name());
        tv_publish_time.setText(TimeUtil.formatHomeTime(dataBean.getCreated_at()));
        if (current_user_uuid.equals(publish_uuid)) {
            tv_del_owner.setVisibility(View.VISIBLE);
        } else {
            tv_del_owner.setVisibility(GONE);
        }
        if (current_user_uuid.equals(publish_uuid)) {
            iv_user_operate.setVisibility(GONE);
        } else {
            iv_user_operate.setVisibility(View.VISIBLE);
        }
    }

    public void showActivityContent(CommunityDynamicsListEntity.ContentBean.DataBean dataBean, CommunityActivityViewHolder holder) {
        String ac_status = dataBean.getAc_status();
        String is_join = dataBean.getIs_join();
        if ("1".equals(is_join)) {
            //表示已参与活动
            holder.tv_once_join.setText(mContext.getResources().getString(R.string.community_activity_joined));
            holder.iv_activity_status.setVisibility(View.INVISIBLE);
        } else {
            //1正在进行，
            //2人数已满，
            //3报名截止，
            //4已结束
            switch (ac_status) {
                case "2":
                    holder.iv_activity_status.setVisibility(View.VISIBLE);
                    holder.iv_activity_status.setImageResource(R.drawable.community_number_full);
                    holder.tv_once_join.setText(mContext.getResources().getString(R.string.community_activity_round));
                    break;
                case "3":
                    holder.iv_activity_status.setVisibility(View.VISIBLE);
                    holder.iv_activity_status.setImageResource(R.drawable.community_enroll_end);
                    holder.tv_once_join.setText(mContext.getResources().getString(R.string.community_activity_round));
                    break;
                case "4":
                    holder.iv_activity_status.setVisibility(View.VISIBLE);
                    holder.iv_activity_status.setImageResource(R.drawable.community_time_end);
                    holder.tv_once_join.setText(mContext.getResources().getString(R.string.community_activity_round));
                    break;
                default:
                    holder.tv_once_join.setText(mContext.getResources().getString(R.string.community_activity_join));
                    holder.iv_activity_status.setVisibility(View.INVISIBLE);
                    break;
            }
        }

        showCommunityActivity(mContext, dataBean.getJoin_num(), dataBean.getJoin_user(), holder.iv_first_photo, holder.iv_second_photo, holder.iv_third_photo, holder.tv_join_person);
    }


    /***用户操作举报的***/
    private void showTipOffContent(String source_id, int position, ImageView iv_operate, View adapterView) {
        iv_operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sourceId", source_id);
                bundle.putInt("position", position);
                Message message = Message.obtain();
                message.obj = adapterView;
                message.what = TIPOFF_COMMENT_DYNAMIC;
                message.setData(bundle);
                EventBus.getDefault().post(message);
            }
        });
    }

    /***用户操作删除的***/
    private void showDelContent(String source_id, int position, TextView tv_delete) {
        tv_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sourceId", source_id);
                bundle.putInt("position", position);
                Message message = Message.obtain();
                message.what = DIRECT_DELETE_DYNAMIC;
                message.setData(bundle);
                EventBus.getDefault().post(message);
            }
        });
    }

    /***用户点赞的***/
    private void showLikeContent(String is_zan, int zan_count, String source_id, int position, TextView tv_like) {
        if (zan_count == 0) {
            tv_like.setText(mContext.getResources().getString(R.string.community_title_like));
        } else {
            tv_like.setText(String.valueOf(zan_count));
        }
        Drawable dra = null;
        if ("1".equals(is_zan)) {
            //表示用户未点赞
            dra = mContext.getResources().getDrawable(R.drawable.community_dynamics_like);
        } else {
            //用户已经点赞过
            dra = mContext.getResources().getDrawable(R.drawable.community_dynamics_unlike);
        }
        dra.setBounds(0, 0, dra.getMinimumWidth(), dra.getMinimumHeight());
        tv_like.setCompoundDrawables(dra, null, null, null);
        if (zan_count == 0) {
            tv_like.setText(mContext.getResources().getString(R.string.community_title_like));
        } else {
            tv_like.setText(String.valueOf(zan_count));
        }
        tv_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户点赞或取消点赞  like_dynamic
                Bundle bundle = new Bundle();
                bundle.putString("sourceId", source_id);
                bundle.putString("isZan", is_zan);
                bundle.putInt("position", position);
                Message message = Message.obtain();
                message.what = DIRECT_LIKE_DYNAMIC;
                message.setData(bundle);
                EventBus.getDefault().post(message);
            }
        });
    }

    /****显示评论内容的****/
    private void showCommentContent(CommunityDynamicsListEntity.ContentBean.DataBean dataBean, int position, TextView tv_comment, RecyclerView rv_comment, View comment_view, View adapterView) {
        int comment_count = dataBean.getComment_count();
        if (comment_count == 0) {
            tv_comment.setText(mContext.getResources().getString(R.string.community_comment));
        } else {
            tv_comment.setText(String.valueOf(comment_count));
        }
        List<CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean> commentBeanList = dataBean.getComment();
        int commentSize = commentBeanList == null ? 0 : commentBeanList.size();
        if (commentSize == 0) {
            rv_comment.setVisibility(GONE);
            comment_view.setVisibility(GONE);
        } else {
            rv_comment.setVisibility(View.VISIBLE);
            comment_view.setVisibility(View.VISIBLE);
            CommunityCommentAdapter communityCommentAdapter = new CommunityCommentAdapter(mContext, commentBeanList, position);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            ((SimpleItemAnimator) rv_comment.getItemAnimator()).setSupportsChangeAnimations(false);
            rv_comment.setLayoutManager(linearLayoutManager);
            rv_comment.setAdapter(communityCommentAdapter);
        }

        tv_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户对动态进行评论  comment_dynamic
                Bundle bundle = new Bundle();
                bundle.putString("sourceId", dataBean.getSource_id());
                bundle.putInt("position", position);
                Message message = Message.obtain();
                message.what = DIRECT_COMMENT_DYNAMIC;
                message.obj = adapterView;
                message.setData(bundle);
                EventBus.getDefault().post(message);
            }
        });
    }

    private void jumpUserInforPage(String from_uuid, String user_Id) {
        Intent intent = null;
        if (current_user_uuid.equals(from_uuid)) {
            intent = new Intent(mContext, IMUserSelfInforActivity.class);
        } else {
            List<String> friendUserIdList = CacheFriendInforHelper.instance().toQueryFriendUUIdList(mContext);
            if (friendUserIdList.contains(from_uuid)) {
                intent = new Intent(mContext, IMFriendInforActivity.class);
            } else {
                intent = new Intent(mContext, IMCustomerInforActivity.class);
            }
        }
        intent.putExtra(USERIDTYPE, 1);
        intent.putExtra(IMFriendInforActivity.USERUUID, user_Id);
        mContext.startActivity(intent);
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return dynamicContentList == null ? 0 : dynamicContentList.size();
    }

    class CommunityActivityViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView iv_activity_image;
        TextView iv_activity_type;
        TextView tv_activity_fee;
        TextView tv_activity_title;
        TextView tv_activity_address;
        TextView tv_activity_date;
        ImageView iv_activity_status;
        TextView tv_once_join;
        ImageView iv_first_photo;
        ImageView iv_second_photo;
        ImageView iv_third_photo;
        TextView tv_join_person;
        OnItemClickListener onClickListener;

        public CommunityActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_activity_image = itemView.findViewById(R.id.iv_activity_image);
            iv_activity_type = itemView.findViewById(R.id.iv_activity_type);
            tv_activity_fee = itemView.findViewById(R.id.tv_activity_fee);
            tv_activity_title = itemView.findViewById(R.id.tv_activity_title);
            tv_activity_address = itemView.findViewById(R.id.tv_activity_address);
            tv_activity_date = itemView.findViewById(R.id.tv_activity_date);
            iv_activity_status = itemView.findViewById(R.id.iv_activity_status);
            tv_once_join = itemView.findViewById(R.id.tv_once_join);
            iv_first_photo = itemView.findViewById(R.id.iv_first_photo);
            iv_second_photo = itemView.findViewById(R.id.iv_second_photo);
            iv_third_photo = itemView.findViewById(R.id.iv_third_photo);
            tv_join_person = itemView.findViewById(R.id.tv_join_person);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }


    class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CircleImageView iv_dynamics_user_pics;
        TextView tv_dynamics_user_name;
        TextView tv_dynamics_user_community;
        View dynamic_divider_view;

        ImageView iv_dynamics_user_operate;
        MoreTextView tv_dynamics_text_content;
        RelativeLayout share_activity_layout;
        ImageView iv_share_logo;
        TextView tv_share_title;
        LinearLayout dynamic_image_layout;
        RecyclerView rv_dynamics_images;
        View view_dynamics_weight;
        TextView tv_dynamics_publish_time;
        TextView tv_del_owner_dynamics;
        TextView tv_dynamics_comment;
        TextView tv_dynamics_like;
        RecyclerView rv_dynamics_user_comments;
        OnItemClickListener onClickListener;


        public DefaultViewHolder(View itemView) {
            super(itemView);
            iv_dynamics_user_pics = itemView.findViewById(R.id.iv_dynamics_user_pics);
            tv_dynamics_user_name = itemView.findViewById(R.id.tv_dynamics_user_name);
            tv_dynamics_user_community = itemView.findViewById(R.id.tv_dynamics_user_community);
            dynamic_divider_view = itemView.findViewById(R.id.dynamic_divider_view);
            iv_dynamics_user_operate = itemView.findViewById(R.id.iv_dynamics_user_operate);
            tv_dynamics_text_content = itemView.findViewById(R.id.tv_dynamics_text_content);
            share_activity_layout = itemView.findViewById(R.id.share_activity_layout);
            iv_share_logo = itemView.findViewById(R.id.iv_share_logo);
            tv_share_title = itemView.findViewById(R.id.tv_share_title);
            dynamic_image_layout = itemView.findViewById(R.id.dynamic_image_layout);
            rv_dynamics_images = itemView.findViewById(R.id.rv_dynamics_images);
            view_dynamics_weight = itemView.findViewById(R.id.view_dynamics_weight);
            tv_dynamics_publish_time = itemView.findViewById(R.id.tv_dynamics_publish_time);
            tv_del_owner_dynamics = itemView.findViewById(R.id.tv_del_owner_dynamics);
            tv_dynamics_comment = itemView.findViewById(R.id.tv_dynamics_comment);
            tv_dynamics_like = itemView.findViewById(R.id.tv_dynamics_like);
            rv_dynamics_user_comments = itemView.findViewById(R.id.rv_dynamics_user_comments);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (onClickListener != null) {
                onClickListener.onItemClick(getAdapterPosition());
            }
        }
    }
}
