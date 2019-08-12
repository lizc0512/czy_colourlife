package com.door.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.BeeFramework.activity.BaseFragment;
import com.door.adapter.IntelligenceDoorAdapter;
import com.door.entity.DoorAllEntity;
import com.nohttp.utils.GsonUtils;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;

/**
 * 智能门禁
 * Created by hxg on 2019/6/5 09:47
 */
public class IntelligenceDoorFragment extends BaseFragment {

    private static final String STATUS = "status";
    private static final String RESUlT = "result";

    private SwipeMenuRecyclerView xrv_invite_list;
    private LinearLayout ll_empty;
    private int status = 0;
    private String result;

    public static IntelligenceDoorFragment newInstance(int status, String result) {
        Bundle args = new Bundle();
        args.putInt(STATUS, status);
        args.putString(RESUlT, result);
        IntelligenceDoorFragment fragment = new IntelligenceDoorFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        assert bundle != null;
        status = bundle.getInt(STATUS);
        result = bundle.getString(RESUlT);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.fragment_intelligent_door_list;
    }

    @Override
    protected void initView(View rootView) {
        xrv_invite_list = rootView.findViewById(R.id.xrv_invite_list);
        ll_empty = rootView.findViewById(R.id.ll_empty);

        xrv_invite_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false));
        initData();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
    }

    public void refresh(int status, String result) {
        this.result = result;
        this.status = status;
        initData();
    }

    private void initData() {
        isFirst = true;
        try {
            DoorAllEntity doorAllEntity = GsonUtils.gsonToBean(result, DoorAllEntity.class);
            List<DoorAllEntity.ContentBean.DataBean.ListBean> list = doorAllEntity.getContent().getData().get(status).getList();

            IntelligenceDoorAdapter mAdapter = new IntelligenceDoorAdapter(getActivity(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
            xrv_invite_list.setAdapter(mAdapter);

            if (0 < list.size()) {
                ll_empty.setVisibility(View.GONE);
                for (int i = 0; i < list.size(); i++) {
                    String type = "";
                    switch (list.get(i).getType()) {
                        case "caihuijuDoor"://远程开门
                            type = "1";
                            break;
                        case "bluetoothDoor"://蓝牙开门
                            type = "2";
                            break;
                        case "carportLock"://车位锁
                            type = "3";
                            break;
                    }

                    List<String> titleList = new ArrayList<>();
                    List<String> typeList = new ArrayList<>();
                    for (int j = 0; j < list.get(i).getKeyList().size(); j++) {
                        if (0 == j) {
                            titleList.add(list.get(i).getName());
                        } else {
                            titleList.add("");
                        }
                        typeList.add(type);
                    }
                    List<DoorAllEntity.ContentBean.DataBean.ListBean.KeyListBean> mList = new ArrayList<>(list.get(i).getKeyList());
                    mAdapter.setData(titleList, typeList, mList);
                    mAdapter.notifyDataSetChanged();
                }
            } else {
                ll_empty.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
