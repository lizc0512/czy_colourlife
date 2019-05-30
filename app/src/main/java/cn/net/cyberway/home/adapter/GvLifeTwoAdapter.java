package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.HomeLifeEntity;

/**
 * Created by lizc 2017/10/07.
 * 为你推荐适配器
 */
public class GvLifeTwoAdapter extends BaseAdapter {
    public List<HomeLifeEntity.ContentBean.ListBean> list;
    private Context mContext;

    public GvLifeTwoAdapter(Context context, List<HomeLifeEntity.ContentBean.ListBean> liftMore) {
        this.mContext = context;
        this.list = liftMore;
    }

    public void setData(List<HomeLifeEntity.ContentBean.ListBean> liftMore) {
        this.list = liftMore;

    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        GvLifeTwoAdapter.ViewHolder holder = null;
        final LayoutInflater mInflater = LayoutInflater.from(mContext);
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.gv_two_life_item, null);
            holder = new GvLifeTwoAdapter.ViewHolder();
            holder.rl_layout = (RelativeLayout) convertView.findViewById(R.id.rl_life_ck);
            holder.img = (ImageView) convertView.findViewById(R.id.iv_life_phtot);
            holder.name = (TextView) convertView.findViewById(R.id.tv_life_desc);
            convertView.setTag(holder);
        } else {
            holder = (GvLifeTwoAdapter.ViewHolder) convertView.getTag();
        }
        holder.name.setText(list.get(position).getName());
        GlideImageLoader.loadImageDefaultDisplay(mContext, list.get(position).getImg(), holder.img, R.drawable.default_image, R.drawable.default_image);
        return convertView;
    }

    private class ViewHolder {
        private ImageView img;
        private TextView name;
        private RelativeLayout rl_layout;
    }
}
