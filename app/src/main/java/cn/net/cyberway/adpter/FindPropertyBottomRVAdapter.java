package cn.net.cyberway.adpter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.BeeFrameworkApp;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.user.UserAppConst;

import java.util.ArrayList;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.FANPIAOCONTENTDATA;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 找物业底部适配器
 * Created by lizc on 2017/11/6.
 */
public class FindPropertyBottomRVAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private LayoutInflater mInflater;
    public ArrayList<FANPIAOCONTENTDATA> content = new ArrayList<FANPIAOCONTENTDATA>();

    public FindPropertyBottomRVAdapter(Context context, ArrayList<FANPIAOCONTENTDATA> contentBanner) {
        mInflater = LayoutInflater.from(context);
        content = contentBanner;
    }

    public void setData(ArrayList<FANPIAOCONTENTDATA> content) {
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
        ((MyTicketBottomViewHolder) holder).name.setText(content.get(position).name);
        ImageLoader.getInstance().displayImage(content.get(position).img, ((MyTicketBottomViewHolder) holder).img, BeeFrameworkApp.optionsImage);
        ((MyTicketBottomViewHolder) holder).rl_findpro_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences mShared = mInflater.getContext().getSharedPreferences(UserAppConst.USERINFO, 0);
                if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                    String linkUrl = content.get(position).url;
                    LinkParseUtil.parse(mInflater.getContext(), linkUrl, content.get(position).name);
                } else {
                    LinkParseUtil.parse(mInflater.getContext(), "", "");
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
