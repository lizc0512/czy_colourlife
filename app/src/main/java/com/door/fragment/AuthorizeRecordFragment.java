package com.door.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseFragment;
import com.door.activity.NewDoorAuthorizeAuditActivity;
import com.door.activity.NewDoorAuthorizeCancelActivity;
import com.door.activity.NewDoorAuthorizePassActivity;
import com.door.adapter.NewDoorAuthorRecordAdapter;
import com.door.entity.ApplyAuthorizeRecordEntity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/*

授权记录的
 */
public class AuthorizeRecordFragment extends BaseFragment {

    private XRecyclerView rv_authorize_record;
    private LinearLayout empty_layout;
    private TextView tv_empty_record;
    private ImageView iv_empty;
    private NewDoorAuthorRecordAdapter newDoorAuthorRecordAdapter;
    private List<ApplyAuthorizeRecordEntity.ContentBean.AuthorizationListBean> authorizationBeanList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_authorize_record;
    }

    @Override
    protected void initView(View rootView) {
        rv_authorize_record = rootView.findViewById(R.id.rv_authorize_record);
        empty_layout = rootView.findViewById(R.id.empty_layout);
        tv_empty_record = rootView.findViewById(R.id.tv_empty_record);
        rv_authorize_record.setPullRefreshEnabled(false);
        rv_authorize_record.setLoadingMoreEnabled(false);
        iv_empty = rootView.findViewById(R.id.iv_empty);
        empty_layout.setVisibility(View.VISIBLE);
        iv_empty.setVisibility(View.INVISIBLE);
        tv_empty_record.setText("请稍等,正在获取授权记录...");
        newDoorAuthorRecordAdapter = new NewDoorAuthorRecordAdapter(authorizationBeanList);
        rv_authorize_record.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_authorize_record.setAdapter(newDoorAuthorRecordAdapter);
        empty_layout.setVisibility(View.VISIBLE);
    }

    public void setAuthorData(List<ApplyAuthorizeRecordEntity.ContentBean.AuthorizationListBean> authorizationList) {
        authorizationBeanList.clear();
        authorizationBeanList.addAll(authorizationList);
        if (authorizationBeanList.size() == 0) {
            empty_layout.setVisibility(View.VISIBLE);
            iv_empty.setVisibility(View.VISIBLE);
            tv_empty_record.setText("还没有授权记录呢～");
        } else {
            empty_layout.setVisibility(View.GONE);
        }
        newDoorAuthorRecordAdapter.notifyDataSetChanged();
        newDoorAuthorRecordAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                ApplyAuthorizeRecordEntity.ContentBean.AuthorizationListBean  authorizationListBean = authorizationBeanList.get(i - 1);
                Intent  intent = new Intent(getActivity(), NewDoorAuthorizeCancelActivity.class);
                intent.putExtra("authorizationList", authorizationListBean);
                getActivity().startActivityForResult(intent, 1000);
            }
        });
    }
}
