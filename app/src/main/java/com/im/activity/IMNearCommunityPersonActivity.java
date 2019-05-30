package com.im.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.external.maxwin.view.IXListViewListener;
import com.external.maxwin.view.XListView;
import com.im.adapter.IMCommunityAdapter;
import com.im.entity.SameComunityPeopleEntity;
import com.im.helper.CacheFriendInforHelper;
import com.im.model.IMCommunityModel;
import com.nohttp.utils.GsonUtils;
import com.permission.AndPermission;
import com.user.UserAppConst;

import java.util.ArrayList;
import java.util.List;

import cn.net.cyberway.R;
import cn.net.cyberway.utils.CityCustomConst;
import cn.net.cyberway.utils.CityManager;

/**
 * @name ${yuansk}
 * @class name：com.im.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/6/15 9:55
 * @change
 * @chang time
 * @class describe  同小区或附近的人
 */

public class IMNearCommunityPersonActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private XListView community_listview;
    private int type = 0;
    private IMCommunityModel imCommunityModel;
    private int page = 1;
    private List<String> friendUUidList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_community);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        community_listview = findViewById(R.id.community_listview);
        user_top_view_back.setOnClickListener(this);
        type = getIntent().getIntExtra("type", 1);
        imCommunityModel = new IMCommunityModel(this);
        if (type == 1) {
            user_top_view_title.setText("同小区的人");
            imCommunityModel.getSameCommunityPeople(0, page, true, IMNearCommunityPersonActivity.this);
        } else {
            user_top_view_title.setText("附近的人");
            getNearPosPeople(true);
        }
        community_listview.setPullLoadEnable(true);
        community_listview.setPullRefreshEnable(true);
        community_listview.setXListViewListener(new IXListViewListener() {
            @Override
            public void onRefresh(int id) {
                page = 1;
                if (type == 1) {
                    imCommunityModel.getSameCommunityPeople(0, page, false, IMNearCommunityPersonActivity.this);
                } else {
                    getNearPosPeople(false);
                }
            }

            @Override
            public void onLoadMore(int id) {
                if (totalPage > page) {
                    page++;
                    if (type == 1) {
                        imCommunityModel.getSameCommunityPeople(0, page, false, IMNearCommunityPersonActivity.this);
                    } else {
                        getNearPosPeople(false);
                    }
                }
            }
        }, 0);
        friendUUidList.addAll(CacheFriendInforHelper.instance().toQueryFriendUUIdList(IMNearCommunityPersonActivity.this));
        community_listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 1) {
                    SameComunityPeopleEntity.ContentBean.ListBean listBean = listBeanData.get(position - 1);
                    String uuid = listBean.getUuid();
                    Intent intent = null;
                    if (shared.getString(UserAppConst.Colour_User_uuid, "").equals(uuid)) {
                        intent = new Intent(IMNearCommunityPersonActivity.this, IMUserSelfInforActivity.class);
                        intent.putExtra(IMFriendInforActivity.USERUUID, uuid);
                        startActivity(intent);
                    } else {
                        String state = listBean.getState();
                        if (friendUUidList.contains(uuid)) {
                            intent = new Intent(IMNearCommunityPersonActivity.this, IMFriendInforActivity.class);
                            intent.putExtra(IMFriendInforActivity.USERUUID, uuid);
                            startActivity(intent);
                        } else {
                            if ("0".equals(state)) {
                                intent = new Intent(IMNearCommunityPersonActivity.this, IMCustomerInforActivity.class);
                                intent.putExtra(IMFriendInforActivity.USERUUID, uuid);
                                startActivity(intent);
                            }
                        }
                    }
                }
            }
        });
    }

    private void getNearPosPeople(boolean isLoading) {
        if (AndPermission.hasPermission(IMNearCommunityPersonActivity.this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            CityManager.getInstance(getApplicationContext()).initLocation();
            String latitude = shared.getString(CityCustomConst.LOCATION_LATITUDE, "");
            String longitude = shared.getString(CityCustomConst.LOCATION_LONGITUDE, "");
            if (!TextUtils.isEmpty(latitude) && !TextUtils.isEmpty(longitude)) {
                imCommunityModel.getNearPeople(0, page, longitude, latitude, isLoading, IMNearCommunityPersonActivity.this);
            }
        } else {
            ToastUtil.toastShow(IMNearCommunityPersonActivity.this, "彩之云未获取定位权限,请去彩之云的权限设置里面将定位权限打开!");
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    private int totalPage;
    private IMCommunityAdapter imCommunityAdapter = null;
    private List<SameComunityPeopleEntity.ContentBean.ListBean> listBeanData = new ArrayList<>();

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (page == 1) {
                    community_listview.stopRefresh();
                } else {
                    community_listview.stopLoadMore();
                }
                if (!TextUtils.isEmpty(result)) {
                    try {
                        SameComunityPeopleEntity sameComunityPeopleEntity = GsonUtils.gsonToBean(result, SameComunityPeopleEntity.class);
                        SameComunityPeopleEntity.ContentBean contentBean = sameComunityPeopleEntity.getContent();
                        totalPage = contentBean.getTotal_page();
                        if (page == 1) {
                            listBeanData.clear();
                        }
                        listBeanData.addAll(contentBean.getList());
                        if (null == imCommunityAdapter) {
                            imCommunityAdapter = new IMCommunityAdapter(IMNearCommunityPersonActivity.this, listBeanData, type);
                            community_listview.setAdapter(imCommunityAdapter);
                        } else {
                            imCommunityAdapter.notifyDataSetChanged();
                        }
                        if (page >= totalPage) {
                            community_listview.loadMoreHide();
                        }
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }
}
