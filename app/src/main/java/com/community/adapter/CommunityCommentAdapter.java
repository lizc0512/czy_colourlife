package com.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.community.entity.CommunityDynamicsListEntity;
import com.external.eventbus.EventBus;
import com.im.activity.IMCustomerInforActivity;
import com.im.activity.IMFriendInforActivity;
import com.im.activity.IMUserSelfInforActivity;
import com.im.helper.CacheFriendInforHelper;
import com.user.UserAppConst;

import java.util.List;

import cn.net.cyberway.R;

import static com.community.fragment.CommunityDynamicsFragment.DIRECT_DELETE_COMMENT;
import static com.community.fragment.CommunityDynamicsFragment.REPLY_OTHER_COMMENT;
import static com.community.fragment.CommunityDynamicsFragment.TIPOFF_OTHER_COMMENT;
import static com.im.activity.IMFriendInforActivity.USERIDTYPE;

/**
 * @ProjectName:
 * @Package: com.community.adapter
 * @ClassName: CommunityDynamicsAdapter
 * @Description: 社区动态评论列表的
 * @Author: yuansk
 * @CreateDate: 2020/2/25 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/25 9:47
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public class CommunityCommentAdapter extends RecyclerView.Adapter<CommunityCommentAdapter.DefaultViewHolder> {

    private List<CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean> commentBeanList;
    private Context mContext;
    private int dynaimcPos;
    private String userId;

    public CommunityCommentAdapter(Context context, List<CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean> commentBeanList, int dynaimcPos) {
        this.mContext = context;
        this.commentBeanList = commentBeanList;
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        userId = String.valueOf(sharedPreferences.getInt(UserAppConst.Colour_User_id, 0));
        this.dynaimcPos = dynaimcPos;
    }

    @Override
    public CommunityCommentAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommunityCommentAdapter.DefaultViewHolder viewHolder = new CommunityCommentAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dynamics_commentlist, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommunityCommentAdapter.DefaultViewHolder holder, int position) {
        CommunityDynamicsListEntity.ContentBean.DataBean.CommentBean commentBean = commentBeanList.get(position);
        String from_nickname = commentBean.getFrom_nickname();
        String to_nickname = commentBean.getTo_nickname();
        String content = commentBean.getContent();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(from_nickname);
        stringBuffer.append(":");
        String reply = "回复@";
        if (!TextUtils.isEmpty(to_nickname)) {
            stringBuffer.append(reply);
            stringBuffer.append(to_nickname);
            stringBuffer.append(":");
        }
        stringBuffer.append(content);
        String showContent = stringBuffer.toString();
        int startLength = from_nickname.length();
        String from_uuid = commentBean.getFrom_id();
        String sourceId = commentBean.getSource_id();
        String commentId = commentBean.getId();
        SpannableString spannableString = new SpannableString(showContent);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#25282E")), 0, startLength + 1, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(Color.parseColor("#666666")), startLength + 1, showContent.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        holder.tv_dynamics_comment.setText(spannableString);
        holder.tv_dynamics_name.setText(from_nickname + ":");
        holder.tv_dynamics_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = null;
                if (userId.equals(from_uuid)) {
                    intent = new Intent(mContext, IMUserSelfInforActivity.class);
                } else {
                    List<String> friendUserIdList = CacheFriendInforHelper.instance().toQueryFriendUserIdList(mContext);
                    if (friendUserIdList.contains(from_uuid)) {
                        intent = new Intent(mContext, IMFriendInforActivity.class);
                    } else {
                        intent = new Intent(mContext, IMCustomerInforActivity.class);
                    }
                }
                intent.putExtra(USERIDTYPE, 1);
                intent.putExtra(IMFriendInforActivity.USERUUID, from_uuid);
                mContext.startActivity(intent);
            }
        });
        holder.tv_dynamics_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bundle bundle = new Bundle();
                bundle.putString("sourceId", sourceId);
                bundle.putInt("position", dynaimcPos);
                bundle.putString("commentId", commentId);
                Message message = Message.obtain();
                if (userId.equals(from_uuid)) {
                    bundle.putInt("commentPosition", position);
                    message.what = DIRECT_DELETE_COMMENT;
                    //表示用户点击自己发送的评论 弹出删除的操作框   delete_dynamic
                } else {
                    bundle.putString("fromUuid", from_uuid);
                    bundle.putString("fromNickName", from_nickname);
                    //点击别人的评论进行回复操作或举报  comment_dynamic
                    message.obj = holder.itemView;
                    message.what = REPLY_OTHER_COMMENT;
                }
                message.setData(bundle);
                EventBus.getDefault().post(message);
            }
        });
        holder.tv_dynamics_comment.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (!userId.equals(from_uuid)) {
                    Bundle bundle = new Bundle();
                    bundle.putString("sourceId", sourceId);
                    bundle.putInt("position", dynaimcPos);
                    bundle.putString("commentId", commentId);
                    Message message = Message.obtain();
                    message.obj = holder.itemView;
                    message.what = TIPOFF_OTHER_COMMENT;
                    message.setData(bundle);
                    EventBus.getDefault().post(message);
                }
                return false;
            }
        });
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return commentBeanList == null ? 0 : commentBeanList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        TextView tv_dynamics_comment;
        TextView tv_dynamics_name;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            tv_dynamics_comment = itemView.findViewById(R.id.tv_dynamics_comment);
            tv_dynamics_name = itemView.findViewById(R.id.tv_dynamics_name);
        }
    }
}
