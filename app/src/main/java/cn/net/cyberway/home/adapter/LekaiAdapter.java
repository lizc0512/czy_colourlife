package cn.net.cyberway.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intelspace.library.module.Device;
import com.intelspace.library.module.LocalKey;
import com.youmai.smallvideorecord.utils.StringUtils;

import java.util.List;

import cn.net.cyberway.R;

/**
 * Created by hxg on 2019/6/20 09:47
 */
public class LekaiAdapter extends RecyclerView.Adapter<LekaiAdapter.DefaultViewHolder> {

    public Context mContext;
    private List<LocalKey> keys;

    public LekaiAdapter(Context context, List<LocalKey> list) {
        this.mContext = context;
        this.keys = list;
    }

    @NonNull
    @Override
    public LekaiAdapter.DefaultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.adapter_lekai, parent, false);
        return new DefaultViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull LekaiAdapter.DefaultViewHolder holder, int position) {
        final LocalKey key = keys.get(position);
        String valid;
        if (key.getValidBegin() == 0 || key.getValidEnd() == 0) {
            valid = "永久有效";
        } else {
            valid = StringUtils.formatDate(key.getValidBegin() * 1000, "yyyy-MM-dd") + "至" + StringUtils.formatDate(key.getValidEnd() * 1000, "yyyy-MM-dd");
        }
        holder.tv_title.setText(key.getName());
        holder.tv_time.setText(valid);

        switch (key.getDeviceType()) {
            case Device.LOCK_VERSION_PARK_LOCK://地锁
            case Device.LOCK_VERSION_BARRIER://道闸
                holder.ll_bg.setBackgroundResource(R.drawable.bg_lekai_car);
                break;
            case Device.LOCK_VERSION_LIFE_CONTROLLER://电梯
                holder.ll_bg.setBackgroundResource(R.drawable.bg_lekai_elevator);
                break;
            case Device.LOCK_VERSION_DOOR://门锁
            case Device.LOCK_VERSION_ENTRANCE://门禁
            default://未知
                holder.ll_bg.setBackgroundResource(R.drawable.bg_lekai_home);
                break;
        }
        if (position == keys.size() - 1) {
            holder.v_line.setVisibility(View.GONE);
        } else {
            holder.v_line.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return keys == null ? 0 : keys.size();
    }

    public class DefaultViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_bg;
        ImageView iv_user;
        ImageView iv_electric;
        ImageView iv_title;
        TextView tv_user;
        TextView tv_title;
        TextView tv_time;
        View v_line;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ll_bg = itemView.findViewById(R.id.ll_bg);
            iv_user = itemView.findViewById(R.id.iv_user);
            iv_electric = itemView.findViewById(R.id.iv_electric);
            iv_title = itemView.findViewById(R.id.iv_title);
            tv_user = itemView.findViewById(R.id.tv_user);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_time = itemView.findViewById(R.id.tv_time);
            v_line = itemView.findViewById(R.id.v_line);
        }
    }
}
