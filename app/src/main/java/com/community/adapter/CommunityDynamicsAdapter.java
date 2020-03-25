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
import android.widget.Button;
import android.widget.ImageView;
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

import static android.view.View.GONE;
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
        if (viewType == 1) {
            CommunityShareViewHolder viewHolder = new CommunityShareViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dynamic_share_layout, parent, false));
            viewHolder.onClickListener = onClickListener;
            return viewHolder;
        } else if (viewType == 2) {
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
            if (list_type == 1) {
                CommunityShareViewHolder holder = (CommunityShareViewHolder) viewHolder;
                if ("like".equals(loadsValue)) {
                    showLikeContent(is_zan, zan_count, source_id, position, holder.tv_share_like);
                } else if ("comment".equals(loadsValue)) {
                    showCommentContent(dataBean, position, holder.tv_share_comment, holder.rv_share_user_comments, holder.share_divider_view, holder.itemView);
                }
            } else if (list_type == 2) {

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
        if (list_type == 1) {
            CommunityShareViewHolder holder = (CommunityShareViewHolder) viewHolder;
            showHeadContent(dataBean, publish_uuid, holder.iv_share_user_pics, holder.tv_share_user_name,
                    holder.tv_share_user_community, holder.tv_del_owner_share, holder.tv_share_publish_time, holder.iv_share_user_pics);
            showTipOffContent(source_id, position, holder.iv_share_user_pics, holder.itemView);
            showDelContent(source_id, position, holder.tv_del_owner_share);
            showLikeContent(is_zan, zan_count, source_id, position, holder.tv_share_like);
            showCommentContent(dataBean, position, holder.tv_share_comment, holder.rv_share_user_comments, holder.share_divider_view, holder.itemView);
            handleUserInforClick(publish_uuid, publish_user_id, holder.iv_share_user_pics, holder.tv_share_user_name, holder.tv_share_user_community);
        } else if (list_type == 2) {


        } else {
            DefaultViewHolder holder = (DefaultViewHolder) viewHolder;
            showHeadContent(dataBean, publish_uuid, holder.iv_dynamics_user_pics, holder.tv_dynamics_user_name,
                    holder.tv_dynamics_user_community, holder.tv_del_owner_dynamics, holder.tv_dynamics_publish_time, holder.iv_dynamics_user_pics);
            if (!TextUtils.isEmpty(content)) {
                holder.tv_dynamics_text_content.setVisibility(View.VISIBLE);
                holder.tv_dynamics_text_content.setText(content);
            } else {
                holder.tv_dynamics_text_content.setVisibility(GONE);
            }
            ViewGroup.LayoutParams params = holder.tv_dynamics_text_content.getLayoutParams();
            params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            holder.tv_dynamics_text_content.setLayoutParams(params);
            showTipOffContent(source_id, position, holder.iv_dynamics_user_operate, holder.itemView);
            showDelContent(source_id, position, holder.tv_del_owner_dynamics);
            showLikeContent(is_zan, zan_count, source_id, position, holder.tv_dynamics_like);
            showCommentContent(dataBean, position, holder.tv_dynamics_comment, holder.rv_dynamics_user_comments, holder.dynamic_divider_view, holder.itemView);
            List<String> imgList = dataBean.getExtra();
            int imgSize = imgList == null ? 0 : imgList.size();
            if (imgSize == 0) {
                holder.rv_dynamics_images.setVisibility(GONE);
            } else {
                holder.rv_dynamics_images.setVisibility(View.VISIBLE);
                int extra_type = dataBean.getExtra_type();
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

    /***用户操作举报的***/
    public void showTipOffContent(String source_id, int position, ImageView iv_operate, View adapterView) {
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
    public void showDelContent(String source_id, int position, TextView tv_delete) {
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
    public void showLikeContent(String is_zan, int zan_count, String source_id, int position, TextView tv_like) {
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
    public void showCommentContent(CommunityDynamicsListEntity.ContentBean.DataBean dataBean, int position, TextView tv_comment, RecyclerView rv_comment, View comment_view, View adapterView) {
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
        TextView iv_activity_sign;
        TextView iv_activity_type;
        TextView tv_activity_fee;
        TextView tv_activity_title;
        TextView tv_activity_address;
        TextView tv_activity_date;
        ImageView iv_activity_status;
        Button tv_once_join;
        ImageView iv_first_photo;
        ImageView iv_second_photo;
        ImageView iv_third_photo;
        TextView tv_join_person;
        OnItemClickListener onClickListener;

        public CommunityActivityViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_activity_image = itemView.findViewById(R.id.iv_activity_image);
            iv_activity_sign = itemView.findViewById(R.id.iv_activity_sign);
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


    class CommunityShareViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        CircleImageView iv_share_user_pics;
        TextView tv_share_user_name;
        TextView tv_share_user_community;
        ImageView iv_share_user_operate;
        ImageView iv_share_logo;
        TextView iv_share_title;
        TextView tv_share_publish_time;
        TextView tv_del_owner_share;
        TextView tv_share_comment;
        TextView tv_share_like;
        View share_divider_view;
        RecyclerView rv_share_user_comments;
        OnItemClickListener onClickListener;

        public CommunityShareViewHolder(@NonNull View itemView) {
            super(itemView);
            iv_share_user_pics = itemView.findViewById(R.id.iv_share_user_pics);
            tv_share_user_name = itemView.findViewById(R.id.tv_share_user_name);
            tv_share_user_community = itemView.findViewById(R.id.tv_share_user_community);
            iv_share_user_operate = itemView.findViewById(R.id.iv_share_user_operate);
            iv_share_logo = itemView.findViewById(R.id.iv_share_logo);
            iv_share_title = itemView.findViewById(R.id.iv_share_title);
            tv_share_publish_time = itemView.findViewById(R.id.tv_share_publish_time);
            tv_del_owner_share = itemView.findViewById(R.id.tv_del_owner_share);
            tv_share_comment = itemView.findViewById(R.id.tv_share_comment);
            tv_share_like = itemView.findViewById(R.id.tv_share_like);
            share_divider_view = itemView.findViewById(R.id.share_divider_view);
            rv_share_user_comments = itemView.findViewById(R.id.rv_share_user_comments);
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
