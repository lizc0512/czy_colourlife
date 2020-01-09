package cn.net.cyberway.home.adapter;

/**
 * @name ${yuansk}
 * @class name：cn.net.cyberway.home.adapter
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/17 14:56
 * @change
 * @chang time
 * @class describe
 */

import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 未登录时的banner图
 */
public class NoLoginPagerAdapter extends PagerAdapter {

    private int[] imagArr = {R.drawable.no_login_payfee, R.drawable.no_login_smartdoor, R.drawable.no_login_complain, R.drawable.no_login_shopping};


    @Override
    public int getCount() {
        return imagArr.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        View bannerView = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_nologin_item, null);
        ImageView no_login_iv = bannerView.findViewById(R.id.no_login_iv);
        no_login_iv.setImageResource(imagArr[position]);
        bannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkParseUtil.parse(container.getContext(), "", "");
            }
        });
        container.addView(bannerView);
        return bannerView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        View view = (View) object;
        container.removeView(view);
    }
}
