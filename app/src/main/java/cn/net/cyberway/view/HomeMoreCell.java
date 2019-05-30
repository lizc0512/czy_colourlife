package cn.net.cyberway.view;


import android.content.Context;
import android.content.SharedPreferences;
import android.util.AttributeSet;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.BeeFramework.view.NoScrollGridView;
import com.user.UserAppConst;

import cn.net.cyberway.R;
import cn.net.cyberway.adpter.HomeMoreCellAdapter;
import cn.net.cyberway.protocol.MORE_DATA;

/**
 * 首页更多cell
 */
public class HomeMoreCell extends FrameLayout {
    private TextView name;
    private NoScrollGridView gridView;
    private HomeMoreCellAdapter homeMoreCellAdapter;
    private Context mContext;
    private String homeCathe;
    private SharedPreferences mShared;

    public HomeMoreCell(Context context) {

        super(context);
        mContext = context;
    }

    public HomeMoreCell(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
    }

    public HomeMoreCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
    }



    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }

    private void init() {
        name = (TextView) findViewById(R.id.name);
        gridView = (NoScrollGridView) findViewById(R.id.grid);
        mShared=mContext.getSharedPreferences(UserAppConst.USERINFO, 0);

    }

    /**
     * 绑定数据
     * @param more_data
     */
    public void bindData(final MORE_DATA more_data) {
        name.setText(more_data.name);
        if (homeMoreCellAdapter == null) {
            homeMoreCellAdapter = new HomeMoreCellAdapter(mContext, more_data.attr);
            gridView.setAdapter(homeMoreCellAdapter);
        } else {
            homeMoreCellAdapter.dataList = more_data.attr;
            homeMoreCellAdapter.notifyDataSetChanged();
        }
    }

}
