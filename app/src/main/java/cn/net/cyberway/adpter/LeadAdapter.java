package cn.net.cyberway.adpter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.user.UserAppConst;

import cn.net.cyberway.R;
import cn.net.cyberway.activity.MainActivity;

/**
 * 引导页adater
 */
public class LeadAdapter extends PagerAdapter {

    private int[] imageBg;
    private LayoutInflater mInflater;
    private Context mContext;
    private SharedPreferences shared;
    private SharedPreferences.Editor editor;

    public LeadAdapter(Context context, int[] imageBg) {
        mInflater = LayoutInflater.from(context);
        this.mContext = context;
        this.imageBg = imageBg;

        shared = context.getSharedPreferences(UserAppConst.USERINFO, 0);
        editor = shared.edit();

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return imageBg.length;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        ((ViewPager) container).removeView((View) object);
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {

        View imageLayout = mInflater.inflate(R.layout.lead_item, null);
        ImageView image = (ImageView) imageLayout.findViewById(R.id.lead_item);
        Button start = (Button) imageLayout.findViewById(R.id.start_expexperience);


        image.setImageResource(imageBg[position]);
        ((ViewPager) view).addView(imageLayout, 0);
        /**
         * 最后一页显示开始按钮 点击跳转到主界面
         */
        if (imageBg.length - 1 == position) {
            start.setVisibility(View.VISIBLE);
//            String versionName= UpdateVerSion.getVersionName(mContext);
//            String handleVersionName=  UpdateVerSion.handleVersionName(versionName);
//            start.setText("开启彩之云"+handleVersionName);
            start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    (mContext).startActivity(intent);
                    ((Activity) mContext).overridePendingTransition(R.anim.push_right_in, R.anim.push_right_out);
                    editor.putBoolean(UserAppConst.IS_CHECK_UPDATE, false);
                    editor.commit();
                    ((Activity) mContext).finish();
                }
            });
        } else {
            start.setVisibility(View.GONE);
        }

        return imageLayout;
    }


}
