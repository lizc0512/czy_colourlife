package com.customerInfo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.customerInfo.adapter.ComBuildUnitRoomAdapter;
import com.customerInfo.model.NewCustomerInfoModel;
import com.customerInfo.protocol.ComBuildUnitRoomEntity;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

/**
 * @name ${yuansk}
 * @class name：com.customerInfo.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/8/3 17:29
 * @change
 * @chang time
 * @class describe
 */

public class CustomerChoiceCommunityActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {
    public static final String CHOICETYPE = "choicetype"; //选择小区 楼栋 单元  房间号
    public static final String PROVINCEID = "province_id"; //
    public static final String PROVINCENAME = "province_name"; //
    public static final String CITYID = "city_id"; //
    public static final String CITYNAME = "city_name"; //
    public static final String REGIONID = "region_id"; //
    public static final String REGIONIDNAME = "region_name"; //
    public static final String COMMUNITYUUID = "community_uuid"; //
    public static final String COMMUNITYNAME = "community_name"; //
    public static final String BUILDINGUUID = "building_uuid"; //
    public static final String UNITUUID = "unit_uuid"; //
    public static final String NAME = "name"; //
    public static final String UUID = "uuid"; //

    private TextView user_top_view_title;
    private ImageView user_top_view_back;
    private XRecyclerView rv_community_build;
    private int choiceType = 0;
    private NewCustomerInfoModel newCustomerInfoModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_community);
        newCustomerInfoModel = new NewCustomerInfoModel(CustomerChoiceCommunityActivity.this);
        initView();
    }

    private void initView() {
        user_top_view_back = (ImageView) findViewById(R.id.user_top_view_back);
        user_top_view_title = (TextView) findViewById(R.id.user_top_view_title);
        rv_community_build = (XRecyclerView) findViewById(R.id.rv_community_build);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rv_community_build.setLayoutManager(linearLayoutManager);
        user_top_view_back.setOnClickListener(this);
        rv_community_build.setItemAnimator(new DefaultItemAnimator());
        rv_community_build.setLoadingMoreEnabled(true);
        rv_community_build.setPullRefreshEnabled(false);
        rv_community_build.setLoadingListener(new XRecyclerView.LoadingListener() {

            @Override
            public void onRefresh() {

            }

            @Override
            public void onLoadMore() {
                page++;
                switch (choiceType) {
                    case 0:
                        getCommunityData(false);
                        break;
                    case 1:
                        getBuildingData(false);
                        break;
                    case 2:
                        getUnitData(false);
                        break;
                    case 3:
                        getRoomData(false);
                        break;
                }
            }
        });
        Intent intent = getIntent();
        choiceType = intent.getIntExtra(CHOICETYPE, 0);
        switch (choiceType) {
            case 0:
                user_top_view_title.setText("选择小区");
                province_name = intent.getStringExtra(PROVINCENAME);
                city_name = intent.getStringExtra(CITYNAME);
                region_name = intent.getStringExtra(REGIONIDNAME);
                getCommunityData(true);
                break;
            case 1:
                user_top_view_title.setText("选择楼栋");
                community_uuid = intent.getStringExtra(COMMUNITYUUID);
                getBuildingData(true);
                break;
            case 2:
                user_top_view_title.setText("选择单元");
                building_uuid = intent.getStringExtra(BUILDINGUUID);
                getUnitData(true);
                break;
            case 3:
                user_top_view_title.setText("选择房间号");
                unit_uuid = intent.getStringExtra(UNITUUID);
                getRoomData(true);
                break;
        }
    }

    private int page = 1;
    private String province_name;
    private String city_name;
    private String region_name;


    private void getCommunityData(boolean isLoading) {
        newCustomerInfoModel.getCommunityData(0, province_name, city_name, region_name, page, 20, isLoading, CustomerChoiceCommunityActivity.this);
    }

    private String community_uuid;

    private void getBuildingData(boolean isLoading) {
        newCustomerInfoModel.getBuildingData(0, community_uuid, page, 20, isLoading, CustomerChoiceCommunityActivity.this);
    }

    private String building_uuid;

    private void getUnitData(boolean isLoading) {
        newCustomerInfoModel.getUnitData(0, building_uuid, page, 20, isLoading, CustomerChoiceCommunityActivity.this);
    }

    private String unit_uuid;

    private void getRoomData(boolean isLoading) {
        newCustomerInfoModel.getRoomData(0, unit_uuid, page, 20, isLoading, CustomerChoiceCommunityActivity.this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    private List<ComBuildUnitRoomEntity.ContentBean.DataBean> dataBeanList = new ArrayList<>();
    private ComBuildUnitRoomAdapter comBuildUnitRoomAdapter;

    @Override
    public void OnHttpResponse(int what, String result) {
        switch (what) {
            case 0:
                if (!TextUtils.isEmpty(result)) {
                    try {
                        ComBuildUnitRoomEntity comBuildUnitRoomEntity = GsonUtils.gsonToBean(result, ComBuildUnitRoomEntity.class);
                        ComBuildUnitRoomEntity.ContentBean contentBean = comBuildUnitRoomEntity.getContent();
                        int totalNumber = 0;
                        if (null != contentBean) {
                            ComBuildUnitRoomEntity.ContentBean.PagingBean pagingBean = contentBean.getPaging();
                            totalNumber = pagingBean.getTotal_record();
                            dataBeanList.addAll(contentBean.getData());
                        }
                        if (null == comBuildUnitRoomAdapter) {
                            comBuildUnitRoomAdapter = new ComBuildUnitRoomAdapter(CustomerChoiceCommunityActivity.this, dataBeanList);
                            rv_community_build.setAdapter(comBuildUnitRoomAdapter);
                        } else {
                            comBuildUnitRoomAdapter.notifyDataSetChanged();
                        }
                        if (dataBeanList.size() >= totalNumber) {
                            rv_community_build.setLoadingMoreEnabled(false);
                        }
                        rv_community_build.loadMoreComplete();
                        setonItemClick();
                    } catch (Exception e) {

                    }
                }
                break;
        }
    }

    private void setonItemClick() {
        comBuildUnitRoomAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                if (i >= 1) {
                    ComBuildUnitRoomEntity.ContentBean.DataBean dataBean = dataBeanList.get(i - 1);
                    Intent intent = new Intent();
                    intent.putExtra(NAME, dataBean.getName());
                    intent.putExtra(UUID, dataBean.getUuid());
                    setResult(200, intent);
                    finish();
                }
            }
        });
    }
}
