package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.adapter.BeeBaseAdapter;
import com.nohttp.utils.GlideImageLoader;
import com.user.UserAppConst;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.home.protocol.OPTIONSDATA;

/**
 * adapter lizc 我的页面item适配器
 * Created by chenql on 17/10/11.
 */
public class MyPageItemListAdapter extends BeeBaseAdapter {
    private Context context;
    private List<OPTIONSDATA> data;

    private SharedPreferences mShared;
    private int customer_id;
    private boolean beanPoint;

    public void setData(List<OPTIONSDATA> lists) {
        this.dataList = lists;
        notifyDataSetChanged();
    }

    public MyPageItemListAdapter(Context context, List<OPTIONSDATA> list) {
        super(context, list);
        this.context = context;
        this.data = list;

        mShared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
    }

    public class ViewHolder extends BeeCellHolder {
        public TextView tv_mypageline;
        public RelativeLayout rl_mypage_item;
        public TextView id;//
        public TextView name;//
        public ImageView image;//
        public TextView url;//
        private TextView view;
        private ImageView iv_point;//小红点
    }

    @Override
    protected BeeCellHolder createCellHolder(View cellView) {
        MyPageItemListAdapter.ViewHolder holder = new MyPageItemListAdapter.ViewHolder();
        holder.tv_mypageline = (TextView) cellView.findViewById(R.id.tv_mypageline);
        holder.image = (ImageView) cellView.findViewById(R.id.iv_mypage_photo);
        holder.name = (TextView) cellView.findViewById(R.id.tv_mypage_nickname);
        holder.rl_mypage_item = (RelativeLayout) cellView.findViewById(R.id.rl_mypage_item);
        holder.view = (TextView) cellView.findViewById(R.id.mypage_line);
        holder.iv_point = cellView.findViewById(R.id.iv_point);
        return holder;
    }

    @Override
    protected View bindData(int position, View cellView, ViewGroup parent, BeeCellHolder h) {
        MyPageItemListAdapter.ViewHolder holder = (MyPageItemListAdapter.ViewHolder) h;
        final OPTIONSDATA list = (OPTIONSDATA) dataList.get(position);
        holder.name.setText(list.name);
        String img = list.img;
        GlideImageLoader.loadImageDefaultDisplay(mContext, img, holder.image, R.drawable.default_image, R.drawable.default_image);
        int currentStr = data.get(position).group_id;//获取ID
        int previewStr = (position - 1) >= 0 ? (data.get(position - 1).group_id) : 0;
        if (previewStr != (currentStr)) {
            holder.tv_mypageline.setVisibility(View.VISIBLE);
            holder.view.setVisibility(View.GONE);
        } else {
            holder.tv_mypageline.setVisibility(View.GONE);//是，隐藏起来
            holder.view.setVisibility(View.VISIBLE);
        }

        if (View.VISIBLE == holder.iv_point.getVisibility()) {
            holder.iv_point.setVisibility(View.GONE);
        }
        if ("我的任务".equals(list.name)) {
            customer_id = mShared.getInt(UserAppConst.Colour_User_id, 0);
            beanPoint = mShared.getBoolean(UserAppConst.COLOUR_BEAN_SIGN_POINT + customer_id, false);
            if (beanPoint) {
                holder.iv_point.setVisibility(View.VISIBLE);
            } else {
                holder.iv_point.setVisibility(View.GONE);
            }
        }
        return cellView;
    }

    @Override
    public View createCellView() {
        return mInflater.inflate(R.layout.mypageitemlist, null);
    }

}