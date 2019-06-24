package cn.net.cyberway.fagment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.BeeFramework.model.HttpApi;
import com.BeeFramework.model.HttpApiResponse;
import com.dashuview.library.keep.MyListener;
import com.external.maxwin.view.IXListViewListener;
import com.user.UserAppConst;

import cn.net.cyberway.R;


/**
 * 生活-彩惠人生
 * hxg 2019.06.11
 */
public class BenefitFragment extends Fragment implements View.OnClickListener, HttpApiResponse, IXListViewListener, MyListener {
    private View mView;
    private SharedPreferences mShared;
    public SharedPreferences.Editor mEditor;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_benefit, container, false);
        getActivity().getWindow().setBackgroundDrawable(null);//减少GPU绘制 布局要设为match_parent
        mShared = getActivity().getSharedPreferences(UserAppConst.USERINFO, 0);
        mEditor = mShared.edit();
        initData();
        return mView;
    }

    public void initData() {

    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void OnHttpResponse(HttpApi api) {

    }

    @Override
    public void authenticationFeedback(String s, int i) {

    }

    @Override
    public void toCFRS(String s) {

    }

    @Override
    public void onRefresh(int id) {

    }

    @Override
    public void onLoadMore(int id) {

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
    }

}
