package com.door.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
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

    private static final String USERID = "userid";
    private static final String RESUlT = "result";

    private SwipeMenuRecyclerView xrv_invite_list;
    private LinearLayout ll_empty;
    private int userId;
    private String result;

    public static IntelligenceDoorFragment newInstance(int userId, String result) {
        Bundle args = new Bundle();
        args.putInt(USERID, userId);
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
        userId = bundle.getInt(USERID);
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
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            initData();
        }
    }

    IntelligenceDoorAdapter mAdapter;
    private List<List<DoorAllEntity.ContentBean.DataBean.ListBean.InvalidUnitBean>>  allUnitBeanList=new ArrayList<>();

    private void initData() {
        isFirst = true;
        try {
            if (!TextUtils.isEmpty(result)) {
                List<DoorAllEntity.ContentBean.DataBean.ListBean> mList = GsonUtils.jsonToList(result, DoorAllEntity.ContentBean.DataBean.ListBean.class);
                mAdapter = new IntelligenceDoorAdapter(getActivity(), userId, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(),new ArrayList<>(), new ArrayList<>());
                xrv_invite_list.setAdapter(mAdapter);
                if (0 < mList.size()) {
                    allUnitBeanList.clear();
                    ll_empty.setVisibility(View.GONE);
                    for (int i = 0; i < mList.size(); i++) {
                        String type = "";
                        switch (mList.get(i).getType()) {
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
                        List<String> tagList = new ArrayList<>();
                        List<String> identifyList = new ArrayList<>();
                        List<String> communityList = new ArrayList<>();
                        DoorAllEntity.ContentBean.DataBean.ListBean  listBean=mList.get(i);
                        for (int j = 0; j < listBean.getKeyList().size(); j++) {
                            if (0 == j) {
                                titleList.add(listBean.getName());
                                tagList.add(listBean.getApply_tag());
                                identifyList.add(listBean.getIdentity_id());
                            } else {
                                titleList.add("");
                                tagList.add("");
                                identifyList.add("");
                            }
                            typeList.add(type);
                            allUnitBeanList.add(listBean.getInvalid_unit())  ;
                            communityList.add(listBean.getCommunity_uuid()+","+listBean.getCommunity_name())  ;
                        }
                        List<DoorAllEntity.ContentBean.DataBean.ListBean.KeyListBean> listData = new ArrayList<>(mList.get(i).getKeyList());
                        mAdapter.setData(titleList, typeList, tagList,identifyList, listData);
                        mAdapter.setData(allUnitBeanList,communityList);
                        mAdapter.notifyDataSetChanged();
                    }
                } else {
                    ll_empty.setVisibility(View.VISIBLE);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
