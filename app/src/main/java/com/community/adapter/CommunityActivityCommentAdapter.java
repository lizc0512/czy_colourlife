package com.community.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.community.activity.CommunityActivityDetailsActivity;
import com.community.activity.DynamicsDetailsActivity;
import com.community.entity.CommunityActivityListEntity;
import com.community.entity.CommunityDynamicsListEntity;
import com.im.activity.IMCustomerInforActivity;
import com.im.activity.IMFriendInforActivity;
import com.im.activity.IMUserSelfInforActivity;
import com.im.helper.CacheFriendInforHelper;
import com.nohttp.utils.GlideImageLoader;
import com.user.UserAppConst;

import java.util.List;

import cn.net.cyberway.R;

import static com.im.activity.IMFriendInforActivity.USERIDTYPE;

/**
 * @ProjectName:
 * @Package: com.community.adapter
 * @ClassName: CommunityDynamicsAdapter
 * @Description: 社区活动留言列表
 * @Author: yuansk
 * @CreateDate: 2020/3/25 18:00
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/3/25 18:00
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public class CommunityActivityCommentAdapter extends RecyclerView.Adapter<CommunityActivityCommentAdapter.DefaultViewHolder> {

    private List<CommunityActivityListEntity.ContentBean.DataBean> commentBeanList;
    private Activity activity;
    private String userId;

    public CommunityActivityCommentAdapter(Activity activity, List<CommunityActivityListEntity.ContentBean.DataBean> commentBeanList) {
        this.activity = activity;
        this.commentBeanList = commentBeanList;
        SharedPreferences sharedPreferences = activity.getSharedPreferences(UserAppConst.USERINFO, 0);
        userId = String.valueOf(sharedPreferences.getInt(UserAppConst.Colour_User_id, 0));
    }

    @Override
    public CommunityActivityCommentAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommunityActivityCommentAdapter.DefaultViewHolder viewHolder = new CommunityActivityCommentAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dynamics_comment, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommunityActivityCommentAdapter.DefaultViewHolder holder, int position) {
        CommunityActivityListEntity.ContentBean.DataBean  dataBean=commentBeanList.get(position);
        GlideImageLoader.loadImageDefaultDisplay(activity, dataBean.getFrom_avatar(), holder.iv_dynamics_comment_pics, R.drawable.icon_default_portrait, R.drawable.icon_default_portrait);
        holder.tv_dynamics_comment_names.setText(dataBean.getFrom_nickname());
        StringBuffer stringBuffer = new StringBuffer();
        String to_nickname = dataBean.getTo_nickname();
        String from_id=dataBean.getFrom_id();
        String reply = "回复@";
        if (!TextUtils.isEmpty(to_nickname)) {
            stringBuffer.append(reply);
            stringBuffer.append(to_nickname);
            stringBuffer.append(":");
        }
        stringBuffer.append(dataBean.getContent());
        holder.tv_dynamics_comment_content.setText(stringBuffer.toString());
        holder.tv_dynamics_comment_times.setText(TimeUtil.noticeTime(dataBean.getUpdated_at()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!userId.equals(dataBean.getFrom_id())) {
                    ((CommunityActivityDetailsActivity) activity).showInputCommentDialog(holder.itemView,dataBean.getSource_id(), dataBean.getFrom_nickname(),from_id);
                }
            }
        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (userId.equals(dataBean.getFrom_id())) {
                    ((CommunityActivityDetailsActivity) activity).setDelCommentId(dataBean.getId(),position);
                }
                return false;
            }
        });


        holder.iv_dynamics_comment_pics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpUserInforPage(holder.itemView.getContext(),from_id);
            }
        });
        holder.tv_dynamics_comment_names.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumpUserInforPage(holder.itemView.getContext(),from_id);
            }
        });
    }

    private void jumpUserInforPage(Context mContext, String from_uuid) {
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


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return commentBeanList == null ? 0 : commentBeanList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_dynamics_comment_pics;
        TextView tv_dynamics_comment_names;
        TextView tv_dynamics_comment_times;
        TextView tv_dynamics_comment_content;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            iv_dynamics_comment_pics = itemView.findViewById(R.id.iv_dynamics_comment_pics);
            tv_dynamics_comment_names = itemView.findViewById(R.id.tv_dynamics_comment_names);
            tv_dynamics_comment_times = itemView.findViewById(R.id.tv_dynamics_comment_times);
            tv_dynamics_comment_content = itemView.findViewById(R.id.tv_dynamics_comment_content);
        }
    }
}
