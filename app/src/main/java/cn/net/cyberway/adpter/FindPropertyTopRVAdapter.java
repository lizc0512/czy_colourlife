package cn.net.cyberway.adpter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.BeeFrameworkApp;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.user.UserAppConst;
import com.user.activity.UserRegisterAndLoginActivity;

import java.util.ArrayList;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.FANPIAOCONTENTDATA;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 找物业顶部适配器
 * Created by lizc on 2017/11/6.
 */
public class FindPropertyTopRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    public ArrayList<FANPIAOCONTENTDATA> content = new ArrayList<FANPIAOCONTENTDATA>();

    public FindPropertyTopRVAdapter(Context context, ArrayList<FANPIAOCONTENTDATA> contentBanner) {
        mInflater = LayoutInflater.from(context);
        content = contentBanner;
    }

    public void setData(ArrayList<FANPIAOCONTENTDATA> content) {
        this.content = content;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sv_findpro_topitem, parent, false);
        FindTopViewHolder findTopViewHolder = new FindTopViewHolder(view);
        return findTopViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ((FindTopViewHolder) holder).name.setText(content.get(position).name);
        ((FindTopViewHolder) holder).desc.setText(content.get(position).desc);
        ImageLoader.getInstance().displayImage(content.get(position).img, ((FindTopViewHolder) holder).img, BeeFrameworkApp.optionsImage);
        ((FindTopViewHolder) holder).rl_findpro_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mShared = mInflater.getContext().getSharedPreferences(UserAppConst.USERINFO, 0);
                if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                    LinkParseUtil.parse(mInflater.getContext(), content.get(position).url, content.get(position).name);
                } else {
                    Intent intent = new Intent(mInflater.getContext(), UserRegisterAndLoginActivity.class);
                    mInflater.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return content.size();
    }

    public static class FindTopViewHolder extends RecyclerView.ViewHolder {

        public ImageView img;
        public TextView name;
        public TextView desc;
        public RelativeLayout rl_findpro_top;

        public FindTopViewHolder(View itemView) {
            super(itemView);
            rl_findpro_top = (RelativeLayout) itemView.findViewById(R.id.rl_findpro_top);
            img = (ImageView) itemView.findViewById(R.id.iv_findpro_top);
            name = (TextView) itemView.findViewById(R.id.tv_findpro_topname);
            desc = (TextView) itemView.findViewById(R.id.tv_findpro_topdesc);
        }
    }
}

