package com.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.user.UserAppConst;

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
public class CommunityDynamicsAdapter extends RecyclerView.Adapter<CommunityDynamicsAdapter.DefaultViewHolder> {

    private List<CommunityDynamicsListEntity.ContentBean.DataBean> dynamicContentList;
    private Context mContext;
    private OnItemClickListener onClickListener;
    private String current_user_uuid;

    public CommunityDynamicsAdapter(Context context, List<CommunityDynamicsListEntity.ContentBean.DataBean> dynamicContentList) {
        this.mContext = context;
        this.dynamicContentList = dynamicContentList;
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        current_user_uuid = sharedPreferences.getString(UserAppConst.Colour_User_uuid, "");
    }

    public void setOnItemClickListener(OnItemClickListener onClickListener) {
        this.onClickListener = onClickListener;
    }

    @Override
    public CommunityDynamicsAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommunityDynamicsAdapter.DefaultViewHolder viewHolder = new CommunityDynamicsAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dynamics_list, parent, false));
        viewHolder.onClickListener = onClickListener;
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommunityDynamicsAdapter.DefaultViewHolder holder, int position) {
        CommunityDynamicsListEntity.ContentBean.DataBean dataBean = dynamicContentList.get(position);
        String avatar = dataBean.getAvatar();
        String community_name = dataBean.getCommunity_name();
        String nick_name = dataBean.getNickname();
        int comment_count = dataBean.getComment_count();
        int zan_count = dataBean.getZan_count();
        long create_time = dataBean.getCreated_at();
        String publish_uuid = dataBean.getUser_uuid();
        String content = dataBean.getContent();
        String source_id = dataBean.getSource_id();
        String is_zan = dataBean.getIs_zan();

        GlideImageLoader.loadImageDefaultDisplay(mContext, avatar, holder.iv_dynamics_user_pics, R.drawable.icon_default_portrait, R.drawable.icon_default_portrait);
        holder.tv_dynamics_user_name.setText(nick_name);
        holder.tv_dynamics_user_community.setText(community_name);
        if (!TextUtils.isEmpty(content)) {
            holder.tv_dynamics_text_content.setVisibility(View.VISIBLE);
            holder.tv_dynamics_text_content.setText(content);
        } else {
            holder.tv_dynamics_text_content.setVisibility(GONE);
        }
        ViewGroup.LayoutParams params = holder.tv_dynamics_text_content.getLayoutParams();
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        holder.tv_dynamics_text_content.setLayoutParams(params);
        if (zan_count == 0) {
            holder.tv_dynamics_like.setText("");
        } else {
            holder.tv_dynamics_like.setText(String.valueOf(zan_count));
        }
        if (comment_count == 0) {
            holder.tv_dynamics_comment.setText("");
        } else {
            holder.tv_dynamics_comment.setText(String.valueOf(comment_count));
        }
        holder.tv_dynamics_publish_time.setText(TimeUtil.formatHomeTime(create_time));
        if (current_user_uuid.equals(publish_uuid)) {
            holder.tv_del_owner_dynamics.setVisibility(View.VISIBLE);
        } else {
            holder.tv_del_owner_dynamics.setVisibility(GONE);
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
        holder.tv_dynamics_like.setCompoundDrawables(dra, null, null, null);
        if (current_user_uuid.equals(publish_uuid)) {
            holder.iv_dynamics_user_operate.setVisibility(GONE);
        }else{
            holder.iv_dynamics_user_operate.setVisibility(View.VISIBLE);
        }
        holder.iv_dynamics_user_operate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户自己操作显示删除  delete_dynamic
                Bundle bundle = new Bundle();
                bundle.putString("sourceId", source_id);
                bundle.putInt("position", position);
                Message message = Message.obtain();
                message.obj = holder.itemView;
                message.what = TIPOFF_COMMENT_DYNAMIC;
                message.setData(bundle);
                EventBus.getDefault().post(message);
            }
        });
        holder.tv_del_owner_dynamics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户自己操作显示删除  delete_dynamic
                Bundle bundle = new Bundle();
                bundle.putString("sourceId", source_id);
                bundle.putInt("position", position);
                Message message = Message.obtain();
                message.what = DIRECT_DELETE_DYNAMIC;
                message.setData(bundle);
                EventBus.getDefault().post(message);
            }
        });
        holder.tv_dynamics_like.setOnClickListener(new View.OnClickListener() {
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
        holder.tv_dynamics_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //用户对动态进行评论  comment_dynamic
                Bundle bundle = new Bundle();
                bundle.putString("sourceId", source_id);
                bundle.putInt("position", position);
                Message message = Message.obtain();
                message.what = DIRECT_COMMENT_DYNAMIC;
                message.obj = holder.itemView;
                message.setData(bundle);
                EventBus.getDefault().post(message);
            }
        });
        List<String> imgList = dataBean.getExtra();
        int imgSize = imgList == null ? 0 : imgList.size();
        if (imgSize == 0) {
            holder.rv_dynamics_images.setVisibility(GONE);
        } else {
            holder.rv_dynamics_images.setVisibility(View.VISIBLE);
            int extra_type = dataBean.getExtra_type();
            CommunityImageAdapter communityImageAdapter = new CommunityImageAdapter(mContext, (ArrayList<String>) imgList, extra_type);
            int row = imgSize == 4 ? 2 : 3;//如果4张图片显示2列
            if (row == 2) {
                holder.view_dynamics_weight.setVisibility(View.VISIBLE);
            } else {
                holder.view_dynamics_weight.setVisibility(GONE);
            }
            GridLayoutManager gridLayoutManager = new GridLayoutManager(mContext, row);
            holder.rv_dynamics_images.setLayoutManager(gridLayoutManager);
            holder.rv_dynamics_images.setAdapter(communityImageAdapter);
        }
        List<CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean> commentBeanList = dataBean.getComment();
        int commentSize = commentBeanList == null ? 0 : commentBeanList.size();
        if (commentSize == 0) {
            holder.rv_dynamics_user_comments.setVisibility(GONE);
            holder.dynamic_divider_view.setVisibility(GONE);
        } else {
            holder.rv_dynamics_user_comments.setVisibility(View.VISIBLE);
            holder.dynamic_divider_view.setVisibility(View.VISIBLE);
            CommunityCommentAdapter communityCommentAdapter = new CommunityCommentAdapter(mContext, commentBeanList, position);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
            holder.rv_dynamics_user_comments.setLayoutManager(linearLayoutManager);
            holder.rv_dynamics_user_comments.setAdapter(communityCommentAdapter);
        }
        holder.iv_dynamics_user_pics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpUserInforPage(publish_uuid);
            }
        });
        holder.tv_dynamics_user_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpUserInforPage(publish_uuid);
            }
        });
        holder.tv_dynamics_user_community.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpUserInforPage(publish_uuid);
            }
        });
    }

    private void jumpUserInforPage( String from_uuid) {
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
        intent.putExtra(USERIDTYPE, 0);
        intent.putExtra(IMFriendInforActivity.USERUUID, from_uuid);
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

    static class DefaultViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
