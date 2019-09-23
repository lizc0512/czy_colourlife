package com.door.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.BeeFramework.activity.BaseFragment;
import com.door.adapter.NewDoorAuthorRecordAdapter;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/*

授权记录的
 */
public class AuthorizeRecordFragment extends BaseFragment {

    private XRecyclerView rv_authorize_record;

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_authorize_record;
    }

    @Override
    protected void initView(View rootView) {
        rv_authorize_record = rootView.findViewById(R.id.rv_authorize_record);
        List<String> data = new ArrayList<>();
        data.add("1调试数据");
        data.add("2调试数据");
        data.add("3调试数据");
        data.add("4调试数据");
        data.add("5调试数据");
        data.add("6调试数据");
        rv_authorize_record.setPullRefreshEnabled(false);
        rv_authorize_record.setLoadingMoreEnabled(true);
        NewDoorAuthorRecordAdapter newDoorAuthorRecordAdapter = new NewDoorAuthorRecordAdapter(getActivity(), data);
        rv_authorize_record.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_authorize_record.setAdapter(newDoorAuthorRecordAdapter);
        rv_authorize_record.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {

            }
        });
    }
}
