package com.door.activity;

import android.content.Intent;
import android.graphics.Color;
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
import com.door.entity.DoorCommunityEntity;
import com.door.model.NewDoorAuthorModel;
import com.nohttp.utils.GsonUtils;

import java.util.ArrayList;
import java.util.List;

import cn.csh.colourful.life.listener.OnItemClickListener;
import cn.csh.colourful.life.view.pickview.OptionsPickerView;
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
    private LinearLayout layout_apply_room;
    private TextView tv_apply_room;
    private ImageView iv_apply_arrow;
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
    private String usertype;
    private String bid;
    private long starttime;
    private long stoptime;
    private ApplyAuthorizeRecordEntity.ContentBean.ApplyListBean applyListBean;
    private List<DoorCommunityEntity.ContentBean.CommunitylistBean> communityList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_door_authorize_auditing);
        user_top_view_back = findViewById(R.id.user_top_view_back);
        user_top_view_title = findViewById(R.id.user_top_view_title);
        tv_apply_name = findViewById(R.id.tv_apply_name);
        tv_apply_identify = findViewById(R.id.tv_apply_identify);
        layout_apply_room = findViewById(R.id.layout_apply_room);
        tv_apply_room = findViewById(R.id.tv_apply_room);
        iv_apply_arrow = findViewById(R.id.iv_apply_arrow);
        tv_apply_time = findViewById(R.id.tv_apply_time);
        rv_apply_duration = findViewById(R.id.rv_apply_duration);
        btn_agree_authorize = findViewById(R.id.btn_agree_authorize);
        btn_refuse_authorize = findViewById(R.id.btn_refuse_authorize);
        user_top_view_back.setOnClickListener(this::onClick);
        btn_agree_authorize.setOnClickListener(this::onClick);
        btn_refuse_authorize.setOnClickListener(this::onClick);
        layout_apply_room.setOnClickListener(this::onClick);
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
        Intent intent = getIntent();
        applyListBean = (ApplyAuthorizeRecordEntity.ContentBean.ApplyListBean) intent.getSerializableExtra("applyListBean");
        tv_apply_name.setText(applyListBean.getToname());
        type = applyListBean.getType();
        starttime = System.currentTimeMillis() / 1000;
        stoptime = starttime + 3600 * 24 * 7;
        autype = "1";
        granttype = "0";
        bid = applyListBean.getBid();
        if ("2".equals(type)) {
            btn_agree_authorize.setText("再次授权");
            btn_refuse_authorize.setVisibility(View.GONE);
        }
        usertype = applyListBean.getUsertype();
        if (TextUtils.isEmpty(usertype)) {
            usertype = "2";
        }
        String identify_name;
        switch (usertype) {
            case "1":
                identify_name = "业主  ";
                break;
            case "4":
                identify_name = "访客  ";
                break;
            case "3":
                identify_name = "租客  ";
                break;
            default:
                identify_name = "家属  ";
                usertype = "2";
                break;

        }
        tv_apply_identify.setText(identify_name);
        tv_apply_time.setText(TimeUtil.getDateToString(applyListBean.getCreationtime()));
        newDoorAuthorModel = new NewDoorAuthorModel(NewDoorAuthorizeAuditActivity.this);
        doorDateAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(int i) {
                choicePos = i;
                doorDateAdapter.setChoicePos(i);
                switch (choicePos) {
                    case 1://授权时长一个月
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = starttime + 3600 * 24 * 30;
                        autype = "1";
                        granttype = "0";
                        break;
                    case 2://授权时长6个月
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = starttime + 3600 * 24 * 30 * 6;
                        autype = "1";
                        granttype = "0";
                        break;
                    case 3://授权时长1年
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = starttime + 3600 * 24 * 365;
                        autype = "1";
                        granttype = "0";
                        break;
                    case 4://授权时长永久
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = 0;
                        granttype = "1";
                        autype = "2";
                        break;
                    default://授权时长7天
                        starttime = System.currentTimeMillis() / 1000;
                        stoptime = starttime + 3600 * 24 * 7;
                        autype = "1";
                        granttype = "0";
                        break;
                }
            }
        });
        if (TextUtils.isEmpty(bid)) {  //5.1.2版本之前 旧版本申请门禁权限没有小区
            //获取小区
            String communityData = intent.getStringExtra("communityData");
            getCommunityList(communityData);
        } else {
            tv_apply_room.setText(applyListBean.getName());
            layout_apply_room.setEnabled(false);
            iv_apply_arrow.setVisibility(View.INVISIBLE);
        }
    }

    private void getCommunityList(String communityData) {
        try {
            communityList.clear();
            DoorCommunityEntity doorCommunityEntity = GsonUtils.gsonToBean(communityData, DoorCommunityEntity.class);
            communityList.addAll(doorCommunityEntity.getContent().getCommunitylist());
            int size = communityList.size();
            if (size > 0) {
                DoorCommunityEntity.ContentBean.CommunitylistBean communitylistBean = communityList.get(0);
                bid = communitylistBean.getBid();
                tv_apply_room.setText(communitylistBean.getName());
            }
            if (size == 1) {
                layout_apply_room.setEnabled(false);
                iv_apply_arrow.setVisibility(View.INVISIBLE);
            } else {
                layout_apply_room.setEnabled(true);
                iv_apply_arrow.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {

        }
    }

    private void showPickerView(List list, String title) {// 弹出选择器
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                Object object = list.get(options1);
                if (object instanceof DoorCommunityEntity.ContentBean.CommunitylistBean) {
                    DoorCommunityEntity.ContentBean.CommunitylistBean communitylistBean = (DoorCommunityEntity.ContentBean.CommunitylistBean) object;
                    bid = communitylistBean.getBid();
                    tv_apply_room.setText(communitylistBean.getName());
                }
            }
        })
                .setTitleText(title)
                .setTitleColor(Color.parseColor("#81868F"))
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.parseColor("#333333")) //设置选中项文字颜色
                .setCancelColor(Color.parseColor("#81868F"))
                .setSubmitColor(Color.parseColor("#0567FA"))
                .setContentTextSize(20)
                .setOutSideCancelable(false)// default is true
                .build();
        pvOptions.setPicker(list);//一级选择器
        pvOptions.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_top_view_back:
                finish();
                break;
            case R.id.btn_agree_authorize:
                    if ("2".equals(type)) {  //重新授权
                        newDoorAuthorModel.setDoorAgainAuthorize(0, autype, granttype, starttime, stoptime,
                                bid, usertype, applyListBean.getToid(), NewDoorAuthorizeAuditActivity.this);
                    } else { //同意授权
                        newDoorAuthorModel.approveApplyAuthority(0, applyListBean.getId(), bid,
                                "1", applyListBean.getMemo(), autype, granttype, starttime, stoptime, usertype,
                                NewDoorAuthorizeAuditActivity.this);
                    }
                break;
            case R.id.btn_refuse_authorize: //拒绝申请
                newDoorAuthorModel.approveApplyAuthority(0, applyListBean.getId(), bid,
                        "2", applyListBean.getMemo(), autype, granttype, starttime, stoptime, usertype,
                        NewDoorAuthorizeAuditActivity.this);
                break;
            case R.id.layout_apply_room:
                if (communityList.size() > 1) {
                    showPickerView(communityList, "授权小区");
                }
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
