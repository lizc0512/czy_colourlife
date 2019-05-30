package cn.net.cyberway.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.banner.viewpager.LoopViewPager;
import com.nohttp.utils.GlideImageLoader;
import com.user.UserAppConst;
import com.user.activity.UserRegisterAndLoginActivity;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.home.protocol.MODULELISTCONTENT;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 横幅BannerView 适配器
 */
public class NewBanerAdapter extends PagerAdapter {
    private List<MODULELISTCONTENT> list;
    private LayoutInflater mInflater;
    private Context mContext;

    public NewBanerAdapter(Context context, List<MODULELISTCONTENT> list) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((LoopViewPager) container).removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        final ViewHolder holder;

        holder = new ViewHolder();
        View imageLayout = mInflater.inflate(R.layout.new_banner_item, null);
        holder.image = (ImageView) imageLayout.findViewById(R.id.news_banner_image);
        if (list.size() > 0) {
            final MODULELISTCONTENT banner = list.get(position);
            GlideImageLoader.loadImageDefaultDisplay(mContext,banner.img,holder.image,R.drawable.home_banner,R.drawable.home_banner);
            ((LoopViewPager) view).addView(imageLayout, 0);
            imageLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences mShared = mContext.getSharedPreferences(UserAppConst.USERINFO, 0);
                    if (mShared.getBoolean(UserAppConst.IS_LOGIN, false)) {
                        LinkParseUtil.parse(mContext, banner.url, banner.name);
                    } else {
                        Intent intent = new Intent(mContext, UserRegisterAndLoginActivity.class);
                        mContext.startActivity(intent);
                    }

                }
            });
        }
        return imageLayout;
    }

    class ViewHolder {
        public ImageView image;
    }
}
