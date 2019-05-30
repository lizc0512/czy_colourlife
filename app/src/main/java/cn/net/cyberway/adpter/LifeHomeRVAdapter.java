package cn.net.cyberway.adpter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nohttp.utils.GlideImageLoader;
import com.user.UserAppConst;
import com.user.activity.UserRegisterAndLoginActivity;

import java.util.ArrayList;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.HomeLifeEntity;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 生活页面搜索适配器
 * Created by lizc on 2017/11/6.
 */
public class LifeHomeRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    private Context mContext;
    public ArrayList<HomeLifeEntity.ContentBean.ListBean> content = new ArrayList<HomeLifeEntity.ContentBean.ListBean>();

    public LifeHomeRVAdapter(Context context, ArrayList<HomeLifeEntity.ContentBean.ListBean> contentBanner) {
        mInflater = LayoutInflater.from(context);
        content = contentBanner;
        this.mContext=context;
    }

    public void setData(ArrayList<HomeLifeEntity.ContentBean.ListBean> content) {
        this.content = content;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sv_findpro_bottomitem, parent, false);
        MyTicketBottomViewHolder myTicketBottomViewHolder = new MyTicketBottomViewHolder(view);
        return myTicketBottomViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((MyTicketBottomViewHolder) holder).name.setText(content.get(position).getName());
        GlideImageLoader.loadImageDefaultDisplay(mContext,content.get(position).getImg(),((MyTicketBottomViewHolder) holder).img,R.drawable.default_image,R.drawable.default_image);
        ((MyTicketBottomViewHolder) holder).rl_findpro_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mShared = mInflater.getContext().getSharedPreferences(UserAppConst.USERINFO, 0);
                if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                    if (content.size()>0) {
                        LinkParseUtil.parse(mInflater.getContext(), content.get(position).getUrl(), content.get(position).getName());
                    }
                } else {
                    Intent intent = new Intent(mInflater.getContext(), UserRegisterAndLoginActivity.class);
                    mInflater.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return content == null ? 0 : content.size();

    }

    private static class MyTicketBottomViewHolder extends RecyclerView.ViewHolder {

        private ImageView img;
        private TextView name;
        private RelativeLayout rl_findpro_bottom;

        public MyTicketBottomViewHolder(View itemView) {
            super(itemView);
            rl_findpro_bottom = (RelativeLayout) itemView.findViewById(R.id.rl_findpro_bottom);
            img = (ImageView) itemView.findViewById(R.id.iv_findpro_bottom);
            name = (TextView) itemView.findViewById(R.id.tv_findpro_bottom);
        }
    }

}
