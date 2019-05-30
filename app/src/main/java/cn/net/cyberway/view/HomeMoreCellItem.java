package cn.net.cyberway.view;


import android.content.Context;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.nohttp.utils.GlideImageLoader;

import cn.net.cyberway.R;
import cn.net.cyberway.protocol.HomeLifeEntity;

/**
 * 首页更多每个小item
 */

public class HomeMoreCellItem extends FrameLayout {
    private TextView name;
    private ImageView icon;
    private Context mContext;

    public HomeMoreCellItem(Context context) {
        super(context);
    }

    public HomeMoreCellItem(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;

    }

    public HomeMoreCellItem(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        name = (TextView) findViewById(R.id.name);
        icon = (ImageView) findViewById(R.id.icon);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    /**
     * 绑定数据
     *
     * @param querycl
     */
    public void bindData(final HomeLifeEntity.ContentBean.ListBean querycl) {
        name.setText(querycl.getName());
        GlideImageLoader.loadImageDefaultDisplay(mContext,querycl.getImg(),icon,R.drawable.default_image,R.drawable.default_image);
    }
}
