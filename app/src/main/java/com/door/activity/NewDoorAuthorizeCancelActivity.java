package com.door.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.BeeFramework.Utils.TimeUtil;
import com.BeeFramework.Utils.ToastUtil;
import com.BeeFramework.activity.BaseActivity;
import com.BeeFramework.model.NewHttpResponse;
import com.door.adapter.DoorDateAdapter;
import com.door.entity.ApplyAuthorizeRecordEntity;
import com.door.model.NewDoorAuthorModel;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.net.cyberway.R;
/*
 * 用户主动授权和取消授权
 *
 * */

public class NewDoorAuthorizeCancelActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_apply_name;
    private TextView tv_apply_room_title;
    private TextView tv_apply_identify;
    private TextView tv_apply_room;

    private LinearLayout layout_apply_room;
    private TextView tv_apply_time_title;
    private LinearLayout layout_apply_time;
    private LinearLayout layout_apply_during;
    private TextView tv_apply_duration;
    private TextView tv_apply_time;
    private LinearLayout layout_apply_duration;
    private RecyclerView rv_apply_duration;
    private Button btn_agree_authorize;
    private Button btn_refuse_authorize;
    private List<String> dateList = new ArrayList<>();
    private NewDoorAuthorModel newDoorAuthorModel;
    private int choicePos = 0;
    private String autype;
    private String usertype;
    private String granttype;
    private String isdelete;
    private long starttime;
    private long stoptime;
    private ApplyAuthorizeRecordEntity.ContentBean.AuthorizationListBean authorizationList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_authorize_auditing);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        tv_apply_name = findViewById(R.id.tv_apply_name);
        tv_apply_identify = findViewById(R.id.tv_apply_identify);
        layout_apply_room = findViewById(R.id.layout_apply_room);
        tv_apply_room_title = findViewById(R.id.tv_apply_room_title);
        tv_apply_room = findViewById(R.id.tv_apply_room);

        layout_apply_time = findViewById(R.id.layout_apply_time);
        tv_apply_time_title = findViewById(R.id.tv_apply_time_title);
        layout_apply_during = findViewById(R.id.layout_apply_during);
        tv_apply_duration = findViewById(R.id.tv_apply_duration);

        tv_apply_time = findViewById(R.id.tv_apply_time);

        layout_apply_duration = findViewById(R.id.layout_apply_duration);
        rv_apply_duration = findViewById(R.id.rv_apply_duration);
        btn_agree_authorize = findViewById(R.id.btn_agree_authorize);
        btn_refuse_authorize = findViewById(R.id.btn_refuse_authorize);
        user_top_view_back.setOnClickListener(this::onClick);
        btn_agree_authorize.setOnClickListener(this::onClick);
        btn_refuse_authorize.setOnClickListener(this::onClick);
        user_top_view_title.setText("申请信息");
        tv_apply_room_title.setText("小区");
        tv_apply_time_title.setText("授权时间");
        dateList.add("7天");
        dateList.add("1个月");
        dateList.add("6个月");
        dateList.add("1年");
        dateList.add("永久");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(NewDoorAuthorizeCancelActivity.this, 3);
        rv_apply_duration.setLayoutManager(gridLayoutManager);
        DoorDateAdapter doorDateAdapter = new DoorDateAdapter(dateList);
        doorDateAdapter.setChoicePos(choicePos);
        rv_apply_duration.setAdapter(doorDateAdapter);
        authorizationList = (ApplyAuthorizeRecordEntity.ContentBean.AuthorizationListBean) getIntent().getSerializableExtra("authorizationList");
        tv_apply_name.setText(authorizationList.getToname());
        isdelete = authorizationList.getIsdeleted();
        usertype = authorizationList.getUsertype();
        starttime = System.currentTimeMillis() / 1000;
        stoptime = starttime + 3600 * 24 * 7;
        autype = "1";
        granttype = "0";
        if ("1".equals(isdelete)) {
            starttime = System.currentTimeMillis() / 1000;
            stoptime = starttime + 3600 * 24 * 7;
            btn_agree_authorize.setText("再次授权");
            layout_apply_time.setVisibility(View.GONE);
        } else {
            btn_agree_authorize.setText("取消授权");
            layout_apply_duration.setVisibility(View.GONE);
            layout_apply_during.setVisibility(View.VISIBLE);
        }
        if (authorizationList.getStoptime() == 0) {
            tv_apply_duration.setText("永久");
        } else {
            tv_apply_duration.setText(TimeUtil.getDateToString(authorizationList.getStarttime()) + "至" +
                    TimeUtil.getDateToString(authorizationList.getStoptime()));
        }
        if (TextUtils.isEmpty(usertype)) {
            usertype = "2";
        }
        btn_refuse_authorize.setVisibility(View.GONE);
        String identify_name;
        switch (usertype) {
            case "1":
                identify_name = "业主  ";
                break;
            case "3":
                identify_name = "租客  ";
                break;
            case "4":
                identify_name = "访客  ";
                break;
            default:
                identify_name = "家属  ";
                usertype = "2";
                break;

        }
        tv_apply_identify.setText(identify_name);
        tv_apply_room.setText(authorizationList.getName());
        tv_apply_time.setText(TimeUtil.getDateToString(authorizationList.getCreationtime()));
        newDoorAuthorModel = new NewDoorAuthorModel(NewDoorAuthorizeCancelActivity.this);
        doorDateAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                choicePos = i;
                doorDateAdapter.setChoicePos(i);
                switch (choicePos) {
                    case 1:
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = starttime + 3600 * 24 * 30;
                        autype = "1";
                        granttype = "0";
                        break;
                    case 2:
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = starttime + 3600 * 24 * 30 * 6;
                        autype = "1";
                        granttype = "0";
                        break;
                    case 3:
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = starttime + 3600 * 24 * 365;
                        autype = "1";
                        granttype = "0";
                        break;
                    case 4:
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = 0;
                        granttype = "1";
                        autype = "2";
                        break;
                    default:
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = starttime + 3600 * 24 * 7;
                        autype = "1";
                        granttype = "0";
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_agree_authorize:
                if ("1".equals(isdelete)) {
                    newDoorAuthorModel.setDoorAgainAuthorize(0, autype, granttype, starttime, stoptime,
                            authorizationList.getBid(), usertype, authorizationList.getToid(), NewDoorAuthorizeCancelActivity.this);
                } else {
                    newDoorAuthorModel.cancelUserAutor(0, authorizationList.getId(), NewDoorAuthorizeCancelActivity.this);
                }
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        ToastUtil.toastShow(NewDoorAuthorizeCancelActivity.this, "操作成功!");
        setResult(200);
        finish();
    }
}
