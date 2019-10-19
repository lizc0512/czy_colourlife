package com.door.activity;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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
 * 授权待批复页面
 *
 * */

public class NewDoorAuthorizeAuditActivity extends BaseActivity implements View.OnClickListener, NewHttpResponse {

    private ImageView user_top_view_back;
    private TextView user_top_view_title;
    private TextView tv_apply_name;
    private TextView tv_apply_identify;
    private TextView tv_apply_room;
    private TextView tv_apply_time;
    private RecyclerView rv_apply_duration;
    private Button btn_agree_authorize;
    private Button btn_refuse_authorize;
    private List<String> dateList = new ArrayList<>();
    private NewDoorAuthorModel newDoorAuthorModel;
    private int choicePos = 0;
    private String autype;
    private String granttype;
    private String type;
    private long starttime;
    private long stoptime;
    private ApplyAuthorizeRecordEntity.ContentBean.ApplyListBean applyListBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_authorize_auditing);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        tv_apply_name = findViewById(R.id.tv_apply_name);
        tv_apply_identify = findViewById(R.id.tv_apply_identify);
        tv_apply_room = findViewById(R.id.tv_apply_room);
        tv_apply_time = findViewById(R.id.tv_apply_time);
        rv_apply_duration = findViewById(R.id.rv_apply_duration);
        btn_agree_authorize = findViewById(R.id.btn_agree_authorize);
        btn_refuse_authorize = findViewById(R.id.btn_refuse_authorize);
        user_top_view_back.setOnClickListener(this::onClick);
        btn_agree_authorize.setOnClickListener(this::onClick);
        btn_refuse_authorize.setOnClickListener(this::onClick);
        user_top_view_title.setText("申请信息");
        dateList.add("7天");
        dateList.add("1个月");
        dateList.add("6个月");
        dateList.add("1年");
        dateList.add("永久");
        GridLayoutManager gridLayoutManager = new GridLayoutManager(NewDoorAuthorizeAuditActivity.this, 3);
        rv_apply_duration.setLayoutManager(gridLayoutManager);
        DoorDateAdapter doorDateAdapter = new DoorDateAdapter(dateList);
        doorDateAdapter.setChoicePos(choicePos);
        rv_apply_duration.setAdapter(doorDateAdapter);
        applyListBean = (ApplyAuthorizeRecordEntity.ContentBean.ApplyListBean) getIntent().getSerializableExtra("applyListBean");
        tv_apply_name.setText(applyListBean.getToname());
        type = applyListBean.getType();
        starttime = System.currentTimeMillis() / 1000;
        stoptime = starttime + 3600 * 24 * 7;
        autype = "1";
        granttype = "0";
        if ("2".equals(type)) {
            btn_agree_authorize.setText("再次授权");
            btn_refuse_authorize.setVisibility(View.GONE);
        }
        String identify_name;
        switch (applyListBean.getUsertype()) {
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
                break;
        }
        tv_apply_identify.setText(identify_name);
        tv_apply_room.setText(applyListBean.getName());
        tv_apply_time.setText(TimeUtil.getDateToString(applyListBean.getCreationtime()));
        newDoorAuthorModel = new NewDoorAuthorModel(NewDoorAuthorizeAuditActivity.this);
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
                if (choicePos == -1) {
                    ToastUtil.toastShow(NewDoorAuthorizeAuditActivity.this, "请选择授权时间");
                } else {
                    if ("2".equals(type)) {
                        newDoorAuthorModel.setDoorAgainAuthorize(0, autype, granttype, starttime, stoptime,
                                applyListBean.getBid(), applyListBean.getUsertype(), applyListBean.getToid(), NewDoorAuthorizeAuditActivity.this);
                    } else {
                        newDoorAuthorModel.approveApplyAuthority(0, applyListBean.getId(), applyListBean.getBid(),
                                "1", applyListBean.getMemo(), autype, granttype, starttime, stoptime, applyListBean.getUsertype(),
                                NewDoorAuthorizeAuditActivity.this);
                    }
                }
                break;
            case R.id.btn_refuse_authorize:
                newDoorAuthorModel.approveApplyAuthority(0, applyListBean.getId(), applyListBean.getBid(),
                        "2", applyListBean.getMemo(), autype, granttype, starttime, stoptime, applyListBean.getUsertype(),
                        NewDoorAuthorizeAuditActivity.this);
                break;
        }
    }

    @Override
    public void OnHttpResponse(int what, String result) {
        ToastUtil.toastShow(NewDoorAuthorizeAuditActivity.this, "操作成功!");
        setResult(200);
        finish();
    }
}
