package com.eparking.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.eparking.adapter.ChoiceParkingAddressAdapter;
import com.eparking.helper.ParkingActivityUtils;
import com.eparking.model.ParkingApplyModel;
import com.eparking.protocol.ParkingIdentityEntity;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;

import static com.eparking.activity.ChoiceParkingCommunityActivity.PARKINGROLE;

/**
 * @name ${yuansk}
 * @class name：com.eparking.activity
 * @class describe
 * @anthor ${ysk} QQ:827194927
 * @time 2018/10/17 14:49
 * @change
 * @chang time
 * @class describe  e停绑定月卡  用户选择角色
 */
public class ChoiceParkingUserRoleActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private FrameLayout czy_title_layout;
    private TextView tv_title;      //标题
    private ImageView imageView_back;//返回
    private RelativeLayout search_layout;//返回
    private RecyclerView rv_community;
    private TextView tv_near_parking;
    private List<String> dataSource;
    private ChoiceParkingAddressAdapter choiceParkingAddressAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choice_eparking);
        czy_title_layout = (FrameLayout) findViewById(R.id.czy_title_layout);
        tv_title = (TextView) findViewById(R.id.user_top_view_title);
        tv_title.setText(getResources().getString(R.string.apply_choice_roleinfor));
        imageView_back = (ImageView) findViewById(R.id.user_top_view_back);
        search_layout = findViewById(R.id.search_layout);
        search_layout.setVisibility(View.GONE);
        tv_near_parking = (TextView) findViewById(R.id.tv_near_parking);
        rv_community = (RecyclerView) findViewById(R.id.rv_community);
        imageView_back.setOnClickListener(this);
        dataSource = new ArrayList<>();
        LinearLayoutManager layoutManager = new LinearLayoutManager(ChoiceParkingUserRoleActivity.this, LinearLayoutManager.VERTICAL, false);
        rv_community.setLayoutManager(layoutManager);
        choiceParkingAddressAdapter = new ChoiceParkingAddressAdapter(ChoiceParkingUserRoleActivity.this, dataSource, 1);
        rv_community.setAdapter(choiceParkingAddressAdapter);
        choiceParkingAddressAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                choiceParkingAddressAdapter.setSelectPos(i);
                ParkingIdentityEntity.ContentBean contentBean = identityList.get(i);
                Intent intent = new Intent();
                intent.putExtra(PARKINGROLE, contentBean.getIdentity_name());
                setResult(200, intent);
                finish();
            }
        });
        ParkingApplyModel parkingApplyModel = new ParkingApplyModel(ChoiceParkingUserRoleActivity.this);
        parkingApplyModel.getIdentityList(0, this);
        ParkingActivityUtils.getInstance().addActivity(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
        }
    }

    private List<ParkingIdentityEntity.ContentBean> identityList;

    @Override
    public void OnHttpResponse(int what, String result) {
        try {
            ParkingIdentityEntity parkingIdentityEntity = GsonUtils.gsonToBean(result, ParkingIdentityEntity.class);
            identityList = parkingIdentityEntity.getContent();
            for (ParkingIdentityEntity.ContentBean contentBean : identityList) {
                dataSource.add(contentBean.getIdentity_name());
            }
        } catch (Exception e) {

        }
        choiceParkingAddressAdapter.notifyDataSetChanged();
    }
}
