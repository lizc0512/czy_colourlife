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

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.cashier.protocolchang.PaySuccessBannerEntity;
import com.nohttp.utils.GlideImageLoader;

import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.LinkParseUtil;

/**
 * 支付成功的banner
 */
public class UltraPagerAdapter extends PagerAdapter {
    private boolean isMultiScr;
    private Context mContext;
    private List<PaySuccessBannerEntity.ContentBean.BannerArrBean> bannerDataBeanList;

    public UltraPagerAdapter(Context mContext, boolean isMultiScr, List<PaySuccessBannerEntity.ContentBean.BannerArrBean> bannerDataBeanList) {
        this.isMultiScr = isMultiScr;
        this.mContext = mContext;
        this.bannerDataBeanList = bannerDataBeanList;
    }


    @Override
    public int getCount() {
        return bannerDataBeanList == null ? 0 : bannerDataBeanList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, int position) {
        final PaySuccessBannerEntity.ContentBean.BannerArrBean bannerArrBean = bannerDataBeanList.get(position);
        View bannerView = LayoutInflater.from(container.getContext()).inflate(R.layout.adapter_guesslike_layout_only, null);
        ImageView iv_activity = bannerView.findViewById(R.id.iv_activity);
        GlideImageLoader.loadImageDefaultDisplay(mContext, bannerArrBean.getImg(), iv_activity, R.drawable.icon_style_one, R.drawable.icon_style_one);
        bannerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkParseUtil.parse(mContext, bannerArrBean.getRedirect_uri(), bannerArrBean.getName());
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
