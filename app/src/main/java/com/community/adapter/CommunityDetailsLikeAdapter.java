package com.community.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

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
 * @Description: 社区动态详情点赞列表
 * @Author: yuansk
 * @CreateDate: 2020/2/25 9:47
 * @UpdateUser: 更新者
 * @UpdateDate: 2020/2/25 9:47
 * @UpdateRemark: 更新内容
 * @Version: 1.0
 */
public class CommunityDetailsLikeAdapter extends RecyclerView.Adapter<CommunityDetailsLikeAdapter.DefaultViewHolder> {

    private List<CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean> zanBeanList;
    private Context mContext;
    private String userId;

    public CommunityDetailsLikeAdapter(Context context, List<CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean> zanBeanList) {
        this.mContext = context;
        this.zanBeanList = zanBeanList;
        SharedPreferences sharedPreferences = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        userId = String.valueOf(sharedPreferences.getInt(UserAppConst.Colour_User_id, 0));
    }

    @Override
    public CommunityDetailsLikeAdapter.DefaultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        CommunityDetailsLikeAdapter.DefaultViewHolder viewHolder = new CommunityDetailsLikeAdapter.DefaultViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_dynamics_like, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(CommunityDetailsLikeAdapter.DefaultViewHolder holder, int position) {
        CommunityDynamicsListEntity.ContentBean.DataBean.ZanBean zanBean = zanBeanList.get(position);
        GlideImageLoader.loadImageDefaultDisplay(mContext, zanBean.getFrom_avatar(), holder.iv_dynamics_like_pics, R.drawable.icon_default_portrait, R.drawable.icon_default_portrait);
        holder.iv_dynamics_like_names.setText(zanBean.getFrom_nickname());
        String from_uuid = zanBean.getFrom_id();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
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
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return zanBeanList == null ? 0 : zanBeanList.size();
    }

    static class DefaultViewHolder extends RecyclerView.ViewHolder {
        ImageView iv_dynamics_like_pics;
        TextView iv_dynamics_like_names;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            iv_dynamics_like_pics = itemView.findViewById(R.id.iv_dynamics_like_pics);
            iv_dynamics_like_names = itemView.findViewById(R.id.iv_dynamics_like_names);
        }
    }
}
