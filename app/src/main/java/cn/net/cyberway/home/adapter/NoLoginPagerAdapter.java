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
    private String[] titleArr = {"线上缴费", "智能门禁", "投诉", "周边优惠"};
    private String[] descArr = {"买 单 就 送 抵 扣 金", "手 机 开 门 超 方 便", "对 物 业 吐 个 槽", "方 圆 好 物 我 门 清"};


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
        TextView no_login_desc = bannerView.findViewById(R.id.no_login_desc);
        TextView no_login_title = bannerView.findViewById(R.id.no_login_title);
        no_login_iv.setImageResource(imagArr[position]);
        no_login_title.setText(titleArr[position]);
        no_login_desc.setText(descArr[position]);
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
