package cn.net.cyberway.home.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.intelspace.library.module.Device;
import com.intelspace.library.module.LocalKey;
import com.youmai.smallvideorecord.utils.StringUtils;

import java.util.HashMap;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.home.activity.LekaiListActivity;
import cn.net.cyberway.utils.LekaiHelper;

/**
 * Created by hxg on 2019/6/20 09:47
 */
public class LekaiAdapter extends RecyclerView.Adapter<LekaiAdapter.DefaultViewHolder> {

    public Context mContext;
    private List<LocalKey> keys;
    private HashMap<String, Device> mParkMap;

    public LekaiAdapter(Context context, List<LocalKey> list) {
        this.mContext = context;
        this.keys = list;
    }

    public void setParkMap(HashMap<String, Device> parkMap) {
        mParkMap = parkMap;
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
        try {
            boolean isCar = false;
            final LocalKey key = keys.get(position);
            if (null != key.getDeviceType()) {
                if (!TextUtils.isEmpty(key.getDeviceType())) {
                    switch (key.getDeviceType()) {
                        case Device.LOCK_VERSION_PARK_LOCK://地锁
                            isCar = true;
                            holder.ll_bg.setVisibility(View.GONE);
                            holder.ll_bg_car.setVisibility(View.VISIBLE);
                            break;
                        case Device.LOCK_VERSION_LIFE_CONTROLLER://电梯
                            holder.ll_bg.setVisibility(View.VISIBLE);
                            holder.ll_bg_car.setVisibility(View.GONE);
                            holder.ll_bg.setBackgroundResource(R.drawable.bg_lekai_elevator);
                            break;
                        case Device.LOCK_VERSION_DOOR://门锁
                        case Device.LOCK_VERSION_BARRIER://道闸
                        case Device.LOCK_VERSION_ENTRANCE://门禁
                        default://未知
                            isCar = false;
                            holder.ll_bg.setVisibility(View.VISIBLE);
                            holder.ll_bg_car.setVisibility(View.GONE);
                            holder.ll_bg.setBackgroundResource(R.drawable.bg_lekai_home);
                            break;
                    }
                } else {
                    holder.ll_bg.setVisibility(View.VISIBLE);
                    holder.ll_bg_car.setVisibility(View.GONE);
                    holder.ll_bg.setBackgroundResource(R.drawable.bg_lekai_home);
                }
            } else {
                holder.ll_bg.setVisibility(View.VISIBLE);
                holder.ll_bg_car.setVisibility(View.GONE);
                holder.ll_bg.setBackgroundResource(R.drawable.bg_lekai_home);
            }
            if (position == keys.size() - 1) {
                holder.v_line.setVisibility(View.GONE);
            } else {
                holder.v_line.setVisibility(View.VISIBLE);
            }

            String valid;
            if (key.getValidBegin() == 0 || key.getValidEnd() == 0) {
                valid = "永久有效";
            } else {
                valid = StringUtils.formatDate(key.getValidBegin() * 1000, "yyyy-MM-dd") + "至" + StringUtils.formatDate(key.getValidEnd() * 1000, "yyyy-MM-dd");
            }
            if (isCar) {
                holder.tv_title_car.setText(key.getName());
                holder.tv_time_car.setText(valid);

                if (mParkMap != null && mParkMap.containsKey(LekaiHelper.formatMacAddress(key.getMac()))) {
                    holder.ll_down.setOnClickListener(v -> ((LekaiListActivity) mContext).parkDown(position));
                    holder.ll_up.setOnClickListener(v -> ((LekaiListActivity) mContext).parkUp(position));
                } else {
                    holder.ll_down.setOnClickListener(v -> ((LekaiListActivity) mContext).parkNoMap());
                    holder.ll_up.setOnClickListener(v -> ((LekaiListActivity) mContext).parkNoMap());
                }
            } else {
                holder.tv_title.setText(key.getName());
                holder.tv_time.setText(valid);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return keys == null ? 0 : keys.size();
    }

    public class DefaultViewHolder extends RecyclerView.ViewHolder {
        LinearLayout ll_bg;
        LinearLayout ll_bg_car;
        ImageView iv_user;
        ImageView iv_electric;
        ImageView iv_title;
        TextView tv_user;
        TextView tv_title;
        TextView tv_title_car;
        TextView tv_time;
        TextView tv_time_car;
        LinearLayout ll_down;
        LinearLayout ll_up;
        View v_line;

        public DefaultViewHolder(View itemView) {
            super(itemView);
            ll_bg = itemView.findViewById(R.id.ll_bg);
            ll_bg_car = itemView.findViewById(R.id.ll_bg_car);
            iv_user = itemView.findViewById(R.id.iv_user);
            iv_electric = itemView.findViewById(R.id.iv_electric);
            iv_title = itemView.findViewById(R.id.iv_title);
            tv_user = itemView.findViewById(R.id.tv_user);
            tv_title = itemView.findViewById(R.id.tv_title);
            tv_title_car = itemView.findViewById(R.id.tv_title_car);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_time_car = itemView.findViewById(R.id.tv_time_car);
            ll_down = itemView.findViewById(R.id.ll_down);
            ll_up = itemView.findViewById(R.id.ll_up);
            v_line = itemView.findViewById(R.id.v_line);
        }
    }
}
