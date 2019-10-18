package com.door.fragment;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseFragment;
import com.door.activity.NewDoorAuthorizeAuditActivity;
import com.door.activity.NewDoorAuthorizePassActivity;
import com.door.adapter.NewDoorApplyRecordAdapter;
import com.door.entity.ApplyAuthorizeRecordEntity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/*

授权记录的
 */
public class ApplyRecordFragment extends BaseFragment {

    private XRecyclerView rv_authorize_record;
    private LinearLayout empty_layout;
    private TextView tv_empty_record;
    private NewDoorApplyRecordAdapter newDoorAuthorRecordAdapter;
    private List<ApplyAuthorizeRecordEntity.ContentBean.ApplyListBean> applyListBeanList = new ArrayList<>();

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_authorize_record;
    }

    @Override
    protected void initView(View rootView) {
        rv_authorize_record = rootView.findViewById(R.id.rv_authorize_record);
        empty_layout = rootView.findViewById(R.id.empty_layout);
        tv_empty_record = rootView.findViewById(R.id.tv_empty_record);
        rv_authorize_record.setPullRefreshEnabled(true);
        rv_authorize_record.setLoadingMoreEnabled(false);
        newDoorAuthorRecordAdapter = new NewDoorApplyRecordAdapter(applyListBeanList);
        rv_authorize_record.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        rv_authorize_record.setAdapter(newDoorAuthorRecordAdapter);
        rv_authorize_record.refresh();
    }

    public void setApplyData(List<ApplyAuthorizeRecordEntity.ContentBean.ApplyListBean> applyList) {
        rv_authorize_record.setPullRefreshEnabled(false);
        rv_authorize_record.refreshComplete();
        applyListBeanList.clear();
        applyListBeanList.addAll(applyList);
        if (applyListBeanList.size() == 0) {
            empty_layout.setVisibility(View.VISIBLE);
            tv_empty_record.setText("还没有授权申请呢～");
        } else {
            empty_layout.setVisibility(View.GONE);
        }
        newDoorAuthorRecordAdapter.notifyDataSetChanged();
        newDoorAuthorRecordAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                ApplyAuthorizeRecordEntity.ContentBean.ApplyListBean applyListBean = applyListBeanList.get(i - 1);
                String type = applyListBean.getType();
                String isDeleted = applyListBean.getIsdeleted();
                Intent intent = null;
                if ("2".equals(type) && !("1".equals(isDeleted))) {
                    intent = new Intent(getActivity(), NewDoorAuthorizePassActivity.class);
                } else {
                    intent = new Intent(getActivity(), NewDoorAuthorizeAuditActivity.class);
                }
                intent.putExtra("applyListBean", applyListBean);
                getActivity().startActivityForResult(intent, 1000);
            }
        });

    }
}
